/*
 *  ReglaControlador.java
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
import ec.com.atikasoft.proteus.controlador.helper.ReglaHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "reglaBean")
@ViewScoped
public class ReglaControlador extends CatalogoControlador {

    /**
     * Logger de reglas.
     */
    private Logger LOG = Logger.getLogger(ReglaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/regla/regla.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/regla/lista_regla.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{reglaHelper}")
    private ReglaHelper reglaHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(reglaHelper);
        setReglaHelper(reglaHelper);
        iniciarComboTipoPeriodo();
        cambioEstadoCombo();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipoPeriodo() {
        iniciarCombos(reglaHelper.getTipoPeriodo());
        for (TipoPeriodoAlertaEnum t : TipoPeriodoAlertaEnum.values()) {
            reglaHelper.getTipoPeriodo().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Controlar el estado del combo para activar campos.
     */
    public void cambioEstadoCombo() {
        if (reglaHelper.getRegla().getTipoPeriodo() != null) {
            reglaHelper.setCampoTipoPeriodo(Boolean.TRUE);
        } else {
            reglaHelper.setCampoTipoPeriodo(Boolean.FALSE);
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo periodo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoPeriodo(final String codigo) {
        return TipoPeriodoAlertaEnum.obtenerDescripcion(codigo);
    }

    public ReglaControlador() {
        super();
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.REGLAS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE REGLAS");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (reglaHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarRegla(reglaHelper.getRegla());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarRegla(reglaHelper.getRegla());
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
            Object cloneBean =
                    BeanUtils.cloneBean(reglaHelper.getReglaEditDelete());
            Regla re = (Regla) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            reglaHelper.setRegla(re);
            reglaHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        reglaHelper.setRegla(new Regla());
        iniciarDatosEntidad(reglaHelper.getRegla(), Boolean.TRUE);
        reglaHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((admServicio.tieneRestricciones("regla.id",
                    "Regla", "TipoMovimientoRegla",
                    reglaHelper.getReglaEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Tipo Movimiento Regla");
            }
            if (cont == 0) {
                admServicio.eliminarRegla(reglaHelper.getReglaEditDelete());
                reglaHelper.getListaRegla().
                        remove(reglaHelper.getReglaEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, mensaje);
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
            reglaHelper.getListaRegla().clear();
            reglaHelper.setListaRegla(
                    admServicio.listarTodasReglaPorNombre(
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
            reglaHelper.getListaAlertaNemonico().clear();
            reglaHelper.setListaAlertaNemonico(
                    admServicio.listarTodosReglaPorNemonico(
                    reglaHelper.getRegla().getNemonico()));
            if (reglaHelper.getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }

    /**
     * @return the reglaHelper
     */
    public ReglaHelper getReglaHelper() {
        return reglaHelper;
    }

    /**
     * @param reglaHelper the reglaHelper to set
     */
    public void setReglaHelper(ReglaHelper reglaHelper) {
        this.reglaHelper = reglaHelper;
    }
}
