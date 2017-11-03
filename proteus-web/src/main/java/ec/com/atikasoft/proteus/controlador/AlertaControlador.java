/*
 *  AlertaControlador.java
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
 *  08/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.AlertaHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.modelo.Alerta;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador para la Administracion de Alertas.
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "alertaBean")
@ViewScoped
public class AlertaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AlertaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion"
            + "/alerta/alerta.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion"
            + "/alerta/lista_alerta.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{alertaHelper}")
    private AlertaHelper alertaHelper;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(alertaHelper);
        iniciarComboTipoPeriodo();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipoPeriodo() {
        alertaHelper.getTipoPeriodo().clear();
        for (TipoPeriodoAlertaEnum tp : TipoPeriodoAlertaEnum.values()) {
            alertaHelper.getTipoPeriodo().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoPeriodo(final String codigo) {
        return TipoPeriodoAlertaEnum.obtenerDescripcion(codigo);
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.ALERTAS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE ALERTAS");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (getAlertaHelper().getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    getadministracionServicio().guardarAlerta(getAlertaHelper().getAlerta());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getadministracionServicio().actualizarAlerta(getAlertaHelper().getAlerta());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object alerta =
                    BeanUtils.cloneBean(getAlertaHelper().getAlertaEditDelete());
            Alerta a = (Alerta) alerta;
            iniciarDatosEntidad(a, Boolean.FALSE);
            getAlertaHelper().setAlerta(a);
            getAlertaHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getAlertaHelper().setAlerta(new Alerta());
        iniciarDatosEntidad(getAlertaHelper().getAlerta(), Boolean.TRUE);
        getAlertaHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            if (!(administracionServicio.tieneRestricciones("alerta.id",
                    "Alerta", "TipoMovimientoAlerta",
                    alertaHelper.getAlertaEditDelete().getId()))) {
                administracionServicio.eliminarAlerta(getAlertaHelper().getAlertaEditDelete());
                getAlertaHelper().getListaAlerta().
                        remove(getAlertaHelper().getAlertaEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, " en Tipo de Movimiento - Alertas");
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            alertaHelper.getListaAlerta().clear();
            alertaHelper.setListaAlerta(
                    administracionServicio.listarTodosAlertaPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            alertaHelper.getListaAlertaNemonico().clear();
            alertaHelper.setListaAlertaNemonico(
                    administracionServicio.listarTodosAlertaPorNemonico(
                    alertaHelper.getAlerta().getNemonico()));
            if (alertaHelper.getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the alertaHelper
     */
    public AlertaHelper getAlertaHelper() {
        return alertaHelper;
    }

    /**
     * @param alertaHelper the alertaHelper to set
     */
    public void setAlertaHelper(final AlertaHelper alertaHelper) {
        this.alertaHelper = alertaHelper;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getadministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setadministracionServicio(final AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
