/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PartidaHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.modelo.Partida;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador para la administración de Partida.
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "partidaBean")
@ViewScoped
public class PartidaControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/partidas/partida.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/partidas/lista_partida.jsf";

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{partidaHelper}")
    private PartidaHelper partidaHelper;

    @Override
    @PostConstruct
    public void init() {
        /*setCatalogoHelper(getPartidaHelper());*/
        buscar();
        //getCatalogoHelper().setCampoBusqueda("");

    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.GRUPOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE GRUPOS");
        generarUrlDeReporte();
    }

    /**
     * guarda la partida presupuestaria(creación y edición)
     * @return 
     */
    @Override
    public String guardar() {
        try {
            if (getPartidaHelper().getEsNuevo()) {
                if (validarCodigoYNombre()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdministracionServicio().guardarPartida(getPartidaHelper().getPartida());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }
            } else if(validarNombre()){
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
            } else{
                getAdministracionServicio().actualizarPartida(getPartidaHelper().getPartida());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el partida", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getPartidaHelper().getPartidaEditDelete());
            Partida partida = (Partida) cloneBean;
            iniciarDatosEntidad(partida, Boolean.FALSE);
            getPartidaHelper().setPartida(partida);
            getPartidaHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getPartidaHelper().setPartida(new Partida());
        iniciarDatosEntidad(getPartidaHelper().getPartida(), Boolean.TRUE);
        getPartidaHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Realiza el borrado logico de la partida presupuestaria
     * @return 
     */
    @Override
    public String borrar() {
        try {
                getAdministracionServicio().eliminarPartida(getPartidaHelper().getPartidaEditDelete());
                getPartidaHelper().getListaPartida().remove(getPartidaHelper().getPartidaEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String buscar() {
        try {
            partidaHelper.getListaPartida().clear();
            partidaHelper.setListaPartida(getAdministracionServicio().buscarPartidasVigentes());
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * valida que no se repita el nombre
     * @return 
     */
    public Boolean validarNombre() {
        Boolean existe = true;
        try {
            List<Partida> lista = administracionServicio.listarPartidasPorNombre(partidaHelper.getPartida().getNombre(),
                    partidaHelper.getPartida().getId());
            if (lista.isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del nombre", ex);
        }
        return existe;
    }
    
    /**
     * valida que no se repida codigo y nombre
     * @return 
     */
    public Boolean validarCodigoYNombre() {
        Boolean existe = true;
        try {
            List<Partida> lista = administracionServicio.listarPartidasPorCodigoONombre(partidaHelper.getPartida().
                    getCodigo(),partidaHelper.getPartida().getNombre());
            if (lista.isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del codigo y nombre", ex);
        }
        return existe;
    }

    /**
     * @return the partidaHelper
     */
    public PartidaHelper getPartidaHelper() {
        return partidaHelper;
    }

    /**
     * @param partidaHelper the partidaHelper to set
     */
    public void setPartidaHelper(PartidaHelper partidaHelper) {
        this.partidaHelper = partidaHelper;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getAdministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setAdministracionServicio(AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
