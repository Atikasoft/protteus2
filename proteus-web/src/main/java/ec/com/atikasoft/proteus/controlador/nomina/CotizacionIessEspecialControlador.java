/*
 *  CotizacionIessControlador.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.nomina;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CotizacionIessEspecialHelper;
import ec.com.atikasoft.proteus.modelo.nomina.CotizacionIessEspecial;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * CotizacionIessControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "cotizacionIessEspecialBean")
@ViewScoped
public class CotizacionIessEspecialControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CotizacionIessEspecialControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/cotizacion_iess/cotizacion_iess_especial.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/cotizacion_iess/lista_cotizacion_iess_especial.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cotizacionIessEspecialHelper}")
    private CotizacionIessEspecialHelper cotizacionIessEspecialHelper;

    /**
     * Constructor por defecto.
     */
    public CotizacionIessEspecialControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            CotizacionIessEspecial cotizacionIessEspecial = cotizacionIessEspecialHelper.getCotizacionIessEspecial();
            if (validar(cotizacionIessEspecial)) {
                if (cotizacionIessEspecialHelper.getEsNuevo()) {
                    admServicio.guardarCotizacionIessEspecial(cotizacionIessEspecialHelper.getCotizacionIessEspecial());
                    iniciarNuevo();
                } else {
                    admServicio.actualizarCotizacionIessEspecial(cotizacionIessEspecialHelper.getCotizacionIessEspecial());
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Validar la CotizacionIessEspecial antes de guardar
     *
     * @param cotizacionIessEspecial
     * @return
     * @throws Exception
     */
    private boolean validar(final CotizacionIessEspecial cotizacionIessEspecial) throws Exception {
        boolean sinErrores = true;
        try {
            if (admServicio.existeNombreDeCotizacionIessEspeciales(cotizacionIessEspecial.getNombre(),
                    cotizacionIessEspecial.getId())) {
                sinErrores = false;
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cotizacionIessEspecial.msg.errores.nombre", FacesMessage.SEVERITY_ERROR);
            }
            if (cotizacionIessEspecial.getPorcentajeAportePatronal().compareTo(new BigDecimal(100)) > 0
                    || cotizacionIessEspecial.getPorcentajeAportePatronal().compareTo(new BigDecimal(0)) < 0) {
                sinErrores = false;
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cotizacionIessEspecial.msg.errores.porcentaje.patronal", FacesMessage.SEVERITY_ERROR);
            }
            if (cotizacionIessEspecial.getPorcentajeAporteIndividual().compareTo(new BigDecimal(100)) > 0
                    || cotizacionIessEspecial.getPorcentajeAporteIndividual().compareTo(new BigDecimal(0)) < 0) {
                sinErrores = false;
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.cotizacionIessEspecial.msg.errores.porcentaje.individual", FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error realizando las validaciones", e);
            throw e;
        }
        return sinErrores;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(cotizacionIessEspecialHelper.getCotizacionIessEspecialEdirDelete());
            CotizacionIessEspecial d = (CotizacionIessEspecial) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            cotizacionIessEspecialHelper.setCotizacionIessEspecial(d);
            cotizacionIessEspecialHelper.setEsNuevo(Boolean.FALSE);
            return FORMULARIO_ENTIDAD;
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        cotizacionIessEspecialHelper.setCotizacionIessEspecial(new CotizacionIessEspecial());
        cotizacionIessEspecialHelper.iniciador();
        iniciarDatosEntidad(cotizacionIessEspecialHelper.getCotizacionIessEspecial(), Boolean.TRUE);
        cotizacionIessEspecialHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            iniciarDatosEntidad(cotizacionIessEspecialHelper.getCotizacionIessEspecialEdirDelete(), Boolean.FALSE);
            admServicio.eliminarCotizacionIessEspecial(cotizacionIessEspecialHelper.getCotizacionIessEspecialEdirDelete());
            cotizacionIessEspecialHelper.getListaCotizacionIessEspecial().
                    remove(cotizacionIessEspecialHelper.getCotizacionIessEspecialEdirDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), ERROR_REGISTRO_ELIMINADO, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            cotizacionIessEspecialHelper.getListaCotizacionIessEspecial().clear();
            cotizacionIessEspecialHelper.getListaCotizacionIessEspecial().addAll(
                    admServicio.listarTodosCotizacionIessEspeciales()
            );
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    public CotizacionIessEspecialHelper getCotizacionIessEspecialHelper() {
        return cotizacionIessEspecialHelper;
    }

    public void setCotizacionIessEspecialHelper(CotizacionIessEspecialHelper cotizacionIessEspecialHelper) {
        this.cotizacionIessEspecialHelper = cotizacionIessEspecialHelper;
    }
}
