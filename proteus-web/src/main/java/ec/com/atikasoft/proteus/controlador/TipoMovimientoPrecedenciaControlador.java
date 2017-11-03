/*
 *  TipoMovimientoPrecedenciaControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  29/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoMovimientoPrecedenciaHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoPrecedencia;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoPrecedenciaBean")
@ViewScoped
public class TipoMovimientoPrecedenciaControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TipoMovimientoPrecedenciaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_TIPO_MOVIMIENTO_PRECEDENCIA =
            "/pages/administracion/tipo_movimiento/tipo_movimiento_precedencia.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_TIPO_MOVIMIENTO =
            "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf";

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tipoMovimientoPrecedenciaHelper}")
    private TipoMovimientoPrecedenciaHelper tipoMovimientoPrecedenciaHelper;

    /**
     * Servicio de Administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoPrecedenciaControlador() {
        super();
        tipoMovimientoPrecedenciaHelper = new TipoMovimientoPrecedenciaHelper();
    }

    @Override
    @PostConstruct
    public void init() {
        llenarComboGrupo();
    }

    /**
     * Metodo que se encarga de iniciar la pantalla de configuracion de la presedencia del tipo de movimiento.
     *
     * @return String Regla de navegacion para el formulario de la pantalla
     */
    public String iniciarConfiguracionPrecedencia() {
        tipoMovimientoPrecedenciaHelper.setTipoMovimiento(new TipoMovimiento());
        tipoMovimientoPrecedenciaHelper.getTipoMovimiento().setClase(new Clase());
        tipoMovimientoPrecedenciaHelper.getTipoMovimiento().getClase().setGrupo(new Grupo());
        cargarTablaPrecedencia();
        return FORMULARIO_TIPO_MOVIMIENTO_PRECEDENCIA;
    }

    /**
     * Metodo que sirve para cargar la tabla de tipo de movimiento precedencia con los registros q se encuentre en la
     * base de datos.
     */
    public void cargarTablaPrecedencia() {
        try {
            tipoMovimientoPrecedenciaHelper.setListaTipoMovimientoPrecedencia(
                    new ArrayList<TipoMovimientoPrecedencia>());
            List<TipoMovimientoPrecedencia> lista =
                    administracionServicio.listarTipoMovimientoPrecedenciaPorTipoMovimientoId(
                    tipoMovimientoPrecedenciaHelper.getTipoMovimientoEdit().getId());
            tipoMovimientoPrecedenciaHelper.setListaTipoMovimientoPrecedencia(lista);
        } catch (ServicioException ex) {
            info(getClass().getName(), "No se puede cargar la lista de tipo de movimiento precedencia " + ex);
        }
    }

    /**
     * Metodo que se encarga de regresar a la pantalla de la lista de tipo de movimiento.
     *
     *  @return Regla de Navegacion
     */
    public String regresar() {
        return LISTA_TIPO_MOVIMIENTO;
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la tabla grupos.
     */
    public void llenarComboGrupo() {
        try {
            tipoMovimientoPrecedenciaHelper.getListaGrupo().clear();
            iniciarCombos(tipoMovimientoPrecedenciaHelper.getListaGrupo());
            List<Grupo> listaGrupo = administracionServicio.listarTodosVigente();
            for (Grupo g : listaGrupo) {
                tipoMovimientoPrecedenciaHelper.getListaGrupo().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de grupo" + ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de clase desde la tabla clases.
     */
    public void llenarComboClase() {
        try {
            tipoMovimientoPrecedenciaHelper.getListaClase().clear();
            iniciarCombos(tipoMovimientoPrecedenciaHelper.getListaClase());
            List<Clase> listaClase = administracionServicio.listarClasePorGrupoId(
                    tipoMovimientoPrecedenciaHelper.getTipoMovimiento().getClase().getGrupoId());
            for (Clase c : listaClase) {
                tipoMovimientoPrecedenciaHelper.getListaClase().add(new SelectItem(c.getId(), c.getNombre()));
            }
            tipoMovimientoPrecedenciaHelper.getListaTipoMovimiento().clear();
            iniciarCombos(tipoMovimientoPrecedenciaHelper.getListaTipoMovimiento());
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo de movimiento.
     */
    public void llenarComboTipoMovimiento() {
        try {
            tipoMovimientoPrecedenciaHelper.getListaTipoMovimiento().clear();
            iniciarCombos(tipoMovimientoPrecedenciaHelper.getListaTipoMovimiento());
            List<TipoMovimiento> listaTipoMovimiento =
                    administracionServicio.listarTipoMovimientoActivosPorClaseSinPadre(
                    tipoMovimientoPrecedenciaHelper.getTipoMovimiento().getClaseId(),
                    tipoMovimientoPrecedenciaHelper.getTipoMovimientoEdit().getId());
            for (TipoMovimiento tm : listaTipoMovimiento) {
                tipoMovimientoPrecedenciaHelper.getListaTipoMovimiento().add(
                        new SelectItem(tm.getId(), tm.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de tipo de movimiento " + ex);
        }
    }

    /**
     * Metodo que selecciona el tipo de movimiento y lo agrega a la tabla de precedencia.
     */
    public void seleccionarTipoMovimiento(ValueChangeEvent event) {
        try {
            Long evento = (Long) event.getNewValue();
            Boolean existe = Boolean.FALSE;
            if (evento != null) {
                List<TipoMovimientoPrecedencia> lista =
                        tipoMovimientoPrecedenciaHelper.getListaTipoMovimientoPrecedencia();
                TipoMovimiento tm = administracionServicio.buscarTipoMovimientoPorId(evento);
                for (TipoMovimientoPrecedencia tmp : lista) {
                    if (tmp.getTipoMovimientoDependiente().getId().equals(evento)) {
                        existe = Boolean.TRUE;
                    }
                }
                if (existe) {
                    String tipoMovimiento = UtilCadena.concatenar(
                            "El Tipo de Movimiento ", tm.getNombre(),
                            " ya se encuentra asignado. Porfavor seleccione otro.");
                    mostrarMensajeEnPantalla(tipoMovimiento, FacesMessage.SEVERITY_WARN);
                } else {
                    TipoMovimientoPrecedencia tmpGuardar = new TipoMovimientoPrecedencia();
                    tmpGuardar.setTipoMovimientoDependiente(tm);
                    tmpGuardar.setOrdinal(0);
                    tipoMovimientoPrecedenciaHelper.getListaTipoMovimientoPrecedencia().add(tmpGuardar);
                }
            }
        } catch (Exception ex) {
            info(getClass().getName(), "Error al buscar el tipo de movimiento por id " + ex);
        }
    }

    /**
     * Metodo que se encarga de guardar los tipos de movimientos precedencia.
     */
    public void guardar() {
        try {
            administracionServicio.guardarTipoMovimientoPrecedencia(
                    obtenerUsuario(),
                    tipoMovimientoPrecedenciaHelper.getTipoMovimientoEdit(),
                    tipoMovimientoPrecedenciaHelper.getListaTipoMovimientoPrecedencia());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "No se pudo grabar el tipo de movimiento precedencia " + ex);
        }
    }

    /**
     * Metodo que quita de la lista un tipo de movimiento.
     */
    public void borrar() {
        tipoMovimientoPrecedenciaHelper.getListaTipoMovimientoPrecedencia().remove(
                tipoMovimientoPrecedenciaHelper.getTipoMovimientoPrecedenciaDelete());
    }

    /**
     * @return the tipoMovimientoPrecedenciaHelper
     */
    public TipoMovimientoPrecedenciaHelper getTipoMovimientoPrecedenciaHelper() {
        return tipoMovimientoPrecedenciaHelper;
    }

    /**
     * @param tipoMovimientoPrecedenciaHelper the tipoMovimientoPrecedenciaHelper to set
     */
    public void setTipoMovimientoPrecedenciaHelper(
            final TipoMovimientoPrecedenciaHelper tipoMovimientoPrecedenciaHelper) {
        this.tipoMovimientoPrecedenciaHelper = tipoMovimientoPrecedenciaHelper;
    }
}
