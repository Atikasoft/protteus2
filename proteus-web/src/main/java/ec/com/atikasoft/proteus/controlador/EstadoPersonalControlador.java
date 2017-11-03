/*
 *  EstadoPuestoControlador.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.EstadoPersonalHelper;
import ec.com.atikasoft.proteus.controlador.helper.EstadoPuestoHelper;
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "estadoPersonalBean")
@ViewScoped
public class EstadoPersonalControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(EstadoPersonalControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/estado_personal/estado_personal.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/estado_personal/lista_estado_personal.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{estadoPersonalHelper}")
    private EstadoPersonalHelper estadoPersonalHelper;

    /**
     * Constructor por defecto.
     */
    public EstadoPersonalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(estadoPersonalHelper);
        setEstadoPersonalHelper(estadoPersonalHelper);        
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {
            if (estadoPersonalHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdmServicio().guardarEstadoPersonal(estadoPersonalHelper.getEstadoPersonal());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getAdmServicio().actualizarEstadoPersonal(estadoPersonalHelper.getEstadoPersonal());

                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            estadoPersonalHelper.getListaAlertaNemonico().clear();
            estadoPersonalHelper.setListaAlertaNemonico(admServicio.listarEstadoPersonalPorNemonico(
                    estadoPersonalHelper.getEstadoPersonal().getCodigo()));
            if (estadoPersonalHelper.getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(estadoPersonalHelper.getEstadoPersonalEditDelete());
            EstadoPersonal d = (EstadoPersonal) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            estadoPersonalHelper.setEstadoPersonal(d);
            estadoPersonalHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        estadoPersonalHelper.setEstadoPersonal(new EstadoPersonal());
        estadoPersonalHelper.iniciador();
        iniciarDatosEntidad(estadoPersonalHelper.getEstadoPersonal(), Boolean.TRUE);
        estadoPersonalHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarEstadoPersonal(estadoPersonalHelper.getEstadoPersonalEditDelete());
            estadoPersonalHelper.getListaEstadoPersonal().
                    remove(estadoPersonalHelper.getEstadoPersonalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            estadoPersonalHelper.getListaEstadoPersonal().clear();
            estadoPersonalHelper.setListaEstadoPersonal(
                    admServicio.listarTodosEstadoPersonalPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
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
     * @return the estadoPersonalHelper
     */
    public EstadoPersonalHelper getEstadoPersonalHelper() {
        return estadoPersonalHelper;
    }

    /**
     * @param estadoPersonalHelper the estadoPersonalHelper to set
     */
    public void setEstadoPersonalHelper(EstadoPersonalHelper estadoPersonalHelper) {
        this.estadoPersonalHelper = estadoPersonalHelper;
    }
}
