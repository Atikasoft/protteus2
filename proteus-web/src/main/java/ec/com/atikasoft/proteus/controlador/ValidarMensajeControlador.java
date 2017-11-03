/*
 *  ValidarMensajeControlador.java
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
 *  03/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.controlador.helper.ValidarMensajeHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ReglaServicio;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "validarMensajeBean")
@ViewScoped
public class ValidarMensajeControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(ValidarMensajeControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/procesos/tramite/tramite.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_MENSAJE
            = "/pages/procesos/tramite/lista_mensaje.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String TRAMITE_BORRADOR
            = "/pages/procesos/tramite/tramite_borrador.jsf";

    /**
     * varible que muestra el mensaje.
     */
    public static final String MENSAJE = "ec.gob.mrl.smp.tramite.mensaje.validarRequisitos";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{validarMensajeHelper}")
    private ValidarMensajeHelper validarMensajeHelper;

    /**
     * Helper de la clase tramite.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Servicio de regla.
     */
    @EJB
    private ReglaServicio reglaServicio;

    /**
     * Administracion Servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * iniciar el usuario.
     */
    String nombreUsuario = obtenerNombreUsuario();

    /**
     * Costructor.
     */
    public ValidarMensajeControlador() {
        super();
    }

    /**
     * Metodo para validar el tramite.
     *
     * @return Regla de navegacion
     */
    public String validarMensaje() {
        String resultado = null;
        try {
            List<ParametroGlobal> parametros = (List<ParametroGlobal>) getRequest().getServletContext().getAttribute(CacheEnum.PARAMETROS_GLOBALES.getCodigo());
            Boolean mensaje = reglaServicio.ejecutar(tramiteHelper.getTramite().getId(), getValidarMensajeHelper().getListaMensajes(), nombreUsuario, false, parametros);
            if (mensaje) {
                mostrarMensajeEnPantalla(MENSAJE, FacesMessage.SEVERITY_INFO);
            } else {
                reglaNavegacionDirecta(LISTA_MENSAJE);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al validar el mensaje", e);
        }
        return resultado;
    }

    /**
     * regresar a la pantalla de tramite.
     *
     * @return Regla de navegacion
     */
    public String pantallaTramiteBorrador() {
        String regresar = null;
        try {
            regresar = FORMULARIO_ENTIDAD;
        } catch (Exception e) {
            error(getClass().getName(), "Error al regresar a la pantalla tramite", e);
        }
        return regresar;
    }

    /**
     * Este médoto procesa el inicio de la edicion.
     *
     * @return String
     */
    public String iniciarEdicion() {
        //try {
        reglaNavegacionDirecta(FORMULARIO_ENTIDAD.concat("?est=edt"));
        //FacesContext.getCurrentInstance().getExternalContext().redirect("tramite.jsf?est=edt");
        tramiteHelper.setCampoAccion(Boolean.FALSE);
//        } catch (IOException ex) {
//            Logger.getLogger(ValidarMensajeControlador.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return null;
    }

    /**
     * valida obligatorio.
     *
     * @param obligatorio Obligatorio
     * @return Resultado
     */
    public String verificarObligatorio(final Boolean obligatorio) {
        String resultado = "NO";
        if (obligatorio) {
            resultado = "SI";
        }
        return resultado;
    }

    /**
     * Controlar el estado del combo para activar campos.
     */
    public void cambioEstadoCombo() {
        if (tramiteHelper.getTramite().getTipoAccion() != null) {
            tramiteHelper.setCampoAccion(Boolean.TRUE);
        } else {
            tramiteHelper.setCampoAccion(Boolean.FALSE);
            tramiteHelper.getTramite().setFechaDocumento(null);
            tramiteHelper.getTramite().setNumeroDocumento("");
        }
    }

    /**
     * @return the tramiteHelper
     */
    public TramiteHelper getTramiteHelper() {
        return tramiteHelper;
    }

    /**
     * @param tramiteHelper the tramiteHelper to set
     */
    public void setTramiteHelper(final TramiteHelper tramiteHelper) {
        this.tramiteHelper = tramiteHelper;
    }

    /**
     * @return the validarMensajeHelper
     */
    public ValidarMensajeHelper getValidarMensajeHelper() {
        return validarMensajeHelper;
    }

    /**
     * @param validarMensajeHelper the validarMensajeHelper to set
     */
    public void setValidarMensajeHelper(final ValidarMensajeHelper validarMensajeHelper) {
        this.validarMensajeHelper = validarMensajeHelper;
    }

    @Override
    @PostConstruct
    public void init() {
        tramiteHelper.setCampoAccion(Boolean.FALSE);
    }
}
