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
import ec.com.atikasoft.proteus.controlador.helper.EstadoAdministracionPuestoHelper;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
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
@ManagedBean(name = "estadoAdminPuestoBean")
@ViewScoped
public class EstadoAdministracionPuestoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(EstadoAdministracionPuestoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/estado_administracion_puesto/estado_administracion_puesto.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/estado_administracion_puesto/lista_estado_administracion_puesto.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{estadoAdminPuestoHelper}")
    private EstadoAdministracionPuestoHelper estadoAdministracionPuestoHelper;

    /**
     * Constructor por defecto.
     */
    public EstadoAdministracionPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(estadoAdministracionPuestoHelper);
        System.out.println("------------ catalogo helper " + estadoAdministracionPuestoHelper);
        setEstadoAdministracionPuestoHelper(estadoAdministracionPuestoHelper);        
        getCatalogoHelper().setCampoBusqueda("");
        buscar();
    }

    @Override
    public String guardar() {
        try {
            if (estadoAdministracionPuestoHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla("El código ingresado ya existe", FacesMessage.SEVERITY_WARN);
                } else {
                    getAdmServicio().guardarEstadoAdministracionPuesto(estadoAdministracionPuestoHelper.getEstadoAdministracionPuesto());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getAdmServicio().actualizarEstadoAdministracionPuesto(estadoAdministracionPuestoHelper.getEstadoAdministracionPuesto());

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
    public Boolean validarCodigo() {
        try {
            estadoAdministracionPuestoHelper.getListaEstadosEncontrados().clear();
            estadoAdministracionPuestoHelper.setListaEstadosEncontrados(admServicio.listarEstadoAdministracionPuestoPorCodigo(
                    estadoAdministracionPuestoHelper.getEstadoAdministracionPuesto().getCodigo()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }

        return !estadoAdministracionPuestoHelper.getListaEstadosEncontrados().isEmpty();
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(estadoAdministracionPuestoHelper.getEstadoAdministracionPuestoEditDelete());
            EstadoAdministracionPuesto d = (EstadoAdministracionPuesto) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            estadoAdministracionPuestoHelper.setEstadoAdministracionPuesto(d);
            estadoAdministracionPuestoHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        estadoAdministracionPuestoHelper.setEstadoAdministracionPuesto(new EstadoAdministracionPuesto());
        estadoAdministracionPuestoHelper.iniciador();
        iniciarDatosEntidad(estadoAdministracionPuestoHelper.getEstadoAdministracionPuesto(), Boolean.TRUE);
        estadoAdministracionPuestoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarEstadoAdministracionPuesto(estadoAdministracionPuestoHelper.getEstadoAdministracionPuestoEditDelete());
            estadoAdministracionPuestoHelper.getListaEstadoAdministracionPuesto().
                    remove(estadoAdministracionPuestoHelper.getEstadoAdministracionPuestoEditDelete());
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
            estadoAdministracionPuestoHelper.getListaEstadoAdministracionPuesto().clear();
            estadoAdministracionPuestoHelper.setListaEstadoAdministracionPuesto(
                    admServicio.listarTodosEstadoAdministracionPuestoPorNombre(
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
     * @return the estadoAdministracionPuestoHelper
     */
    public EstadoAdministracionPuestoHelper getEstadoAdministracionPuestoHelper() {
        return estadoAdministracionPuestoHelper;
    }

    /**
     * @param estadoAdministracionPuestoHelper the estadoAdministracionPuestoHelper to set
     */
    public void setEstadoAdministracionPuestoHelper(EstadoAdministracionPuestoHelper estadoAdministracionPuestoHelper) {
        this.estadoAdministracionPuestoHelper = estadoAdministracionPuestoHelper;
    }
}
