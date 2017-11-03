/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ExcepcionAsistenciaPersonalHelper;
import ec.com.atikasoft.proteus.converter.ServidorConverter;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.EstadosPersonalEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ExcepcionAsistenciaPersonal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "excepcionAsistenciaPersonalBean")
@ViewScoped
public class ExcepcionAsistenciaPersonalControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/administracion/asistencia_de_personal/excepcion/excepcion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD
            = "/pages/administracion/asistencia_de_personal/excepcion/lista_excepcion.jsf";

    /**
     * Servicio de Administracion
     */
    @EJB
    private AdministracionServicio admServicio;
    
    /**
     * Servicio de Asistencia De Personal
     */
    @EJB
    private AsistenciaDePersonalServicio asistenciaPersonalServicio;

    /**
     * Servicio de Undiades Desconcentradas
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Dao de DistributivoDetalle
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{excepcionAsistenciaPersonalHelper}")
    private ExcepcionAsistenciaPersonalHelper excepcionAsistenciaPersonalHelper;

    /**
     * Método que se ejecuta luego del contructor de la clase
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(excepcionAsistenciaPersonalHelper);
        getCatalogoHelper().setCampoBusqueda("");
        buscar();
    }

    /**
     * Recupera todos los registros vigentes
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            excepcionAsistenciaPersonalHelper.getListaExcepciones().clear();
            excepcionAsistenciaPersonalHelper.getListaExcepciones().addAll(asistenciaPersonalServicio
                    .listarExcepcionesAsistenciaPersonalPorUsuarioLogueado(obtenerUsuarioConectado()));

        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la búsqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Iniciar variables para nueva excepcion
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        excepcionAsistenciaPersonalHelper.setServidorExcepcionActual(new Servidor());
        excepcionAsistenciaPersonalHelper.setExcepcion(new ExcepcionAsistenciaPersonal());
        excepcionAsistenciaPersonalHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Iniciar variables para edicion de excepcion
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            excepcionAsistenciaPersonalHelper.setServidorExcepcionActual(
                    excepcionAsistenciaPersonalHelper.getExcepcion().getServidor());
            excepcionAsistenciaPersonalHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Guardar
     *
     * @return
     */
    @Override
    public String guardar() {
        try {
            iniciarDatosEntidad(excepcionAsistenciaPersonalHelper.getExcepcion(),
                    excepcionAsistenciaPersonalHelper.getEsNuevo());
            asistenciaPersonalServicio.crearActualizarExcepcionAsistenciaPersonal(
                    excepcionAsistenciaPersonalHelper.getExcepcion());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar la excepción", e);
            return null;
        }
        return null;
    }

    /**
     * Elimina el registro
     *
     * @return
     */
    @Override
    public String borrar() {
        try {
            iniciarDatosEntidad(excepcionAsistenciaPersonalHelper.getExcepcion(), Boolean.FALSE);
            excepcionAsistenciaPersonalHelper.getExcepcion().setVigente(Boolean.FALSE);
            asistenciaPersonalServicio.crearActualizarExcepcionAsistenciaPersonal(
                    excepcionAsistenciaPersonalHelper.getExcepcion());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            buscar();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al intentar eliminar el registro", ex);
        }
        return null;
    }

    /**
     * recupera los servidores que estén activos y ocupando un puesto en la unidad desconcentrada del usuario conectado
     * dado un nombre
     *
     * @param nombre nombre del servidor
     * @return
     */
    public List<Servidor> buscarServidoresActivosEnPuestosOcupadosPorNombre(String nombre) {
        List<Servidor> servidores = new ArrayList();
        try {
            if (nombre != null && !nombre.isEmpty()) {
                for (UnidadOrganizacional uo : desconcentradoServicio.buscarUnidadesDeAcceso(
                        obtenerUsuarioConectado().getServidor().getId(),
                        FuncionesDesconcentradosEnum.ASISTENCIA_DE_PERSONAL.getCodigo())) {
                    BusquedaPuestoVO b = new BusquedaPuestoVO();
                    b.setNombreServidor(nombre);
                    b.setInicioConsulta(0);
                    b.setFinConsulta(10);
                    b.setPuestoVacante(Boolean.FALSE);
                    b.setEstadoPuestoCodigo(EstadoPuestoEnum.OCUPADO.getCodigo());
                    b.setEstadoServidorCodigo(EstadosPersonalEnum.ACTIVO.getCodigo());
                    b.setUnidadAdministrativaId(uo.getId());
                    List<DistributivoDetalle> puestos = distributivoDetalleDao.buscar(b, false);

                    for (DistributivoDetalle puesto : puestos) {
                        servidores.add(puesto.getServidor());
                    }
                }

                if (servidores.isEmpty()) {
                    Servidor s = new Servidor(0L);
                    s.setApellidosNombres("No se encontraron resultados...");
                    servidores.add(s);
                }

                List<Servidor> s = new ArrayList<>(servidores);
                s.add(excepcionAsistenciaPersonalHelper.getExcepcion().getServidor());

                ponerAtributoSession(ServidorConverter.CLAVE_SESSION, s);
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar servidores", e);
        }

        return servidores;
    }

    /**
     *
     * @return
     */
    public ExcepcionAsistenciaPersonalHelper getExcepcionAsistenciaPersonalHelper() {
        return excepcionAsistenciaPersonalHelper;
    }

    /**
     *
     * @param excepcionAsistenciaPersonalHelper
     */
    public void setExcepcionAsistenciaPersonalHelper(
            ExcepcionAsistenciaPersonalHelper excepcionAsistenciaPersonalHelper) {
        this.excepcionAsistenciaPersonalHelper = excepcionAsistenciaPersonalHelper;
    }

}
