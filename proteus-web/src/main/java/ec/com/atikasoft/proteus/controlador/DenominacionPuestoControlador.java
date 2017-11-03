/*
 *  DenominacionPuestoControlador.java
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
 *  19/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ClaseHelper;
import ec.com.atikasoft.proteus.controlador.helper.DenominacionPuestoHelper;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
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
 * DenominacionPuesto
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@ManagedBean(name = "denominacionPuestoBean")
@ViewScoped
public class DenominacionPuestoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(DenominacionPuestoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/denominacion_puesto/denominacion_puesto.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/denominacion_puesto/lista_denominacion_puesto.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{denominacionPuestoHelper}")
    private DenominacionPuestoHelper denominacionPuestoHelper;

    /**
     * Constructor por defecto.
     */
    public DenominacionPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(denominacionPuestoHelper);
        setDenominacionPuestoHelper(denominacionPuestoHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {
            if (denominacionPuestoHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarDenominacionPuesto(denominacionPuestoHelper.getDenominacionPuesto());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }

            } else {
                admServicio.actualizarDenominacionPuesto(denominacionPuestoHelper.getDenominacionPuesto());
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
            denominacionPuestoHelper.getListaAlertaNemonico().clear();
            denominacionPuestoHelper.setListaAlertaNemonico(admServicio.listarDenominacionPuestoPorNemonico(
                    denominacionPuestoHelper.getDenominacionPuesto().getCodigo()));
            if (denominacionPuestoHelper.getListaAlertaNemonico().isEmpty()) {
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
                    BeanUtils.cloneBean(denominacionPuestoHelper.getDenominacionPuestoEditDelete());
            DenominacionPuesto d = (DenominacionPuesto) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            denominacionPuestoHelper.setDenominacionPuesto(d);
            denominacionPuestoHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        denominacionPuestoHelper.setDenominacionPuesto(new DenominacionPuesto());
        denominacionPuestoHelper.iniciador();
        iniciarDatosEntidad(denominacionPuestoHelper.getDenominacionPuesto(), Boolean.TRUE);
        denominacionPuestoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarDenominacionPuesto(denominacionPuestoHelper.getDenominacionPuestoEditDelete());
            denominacionPuestoHelper.getListaDenominacionPuestos().
                    remove(denominacionPuestoHelper.getDenominacionPuestoEditDelete());
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
            denominacionPuestoHelper.getListaDenominacionPuestos().clear();
            denominacionPuestoHelper.setListaDenominacionPuestos(
                    admServicio.listarTodosDenominacionPuestoPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * @return the denominacionPuestoHelper
     */
    public DenominacionPuestoHelper getDenominacionPuestoHelper() {
        return denominacionPuestoHelper;
    }

    /**
     * @param denominacionPuestoHelper the denominacionPuestoHelper to set
     */
    public void setDenominacionPuestoHelper(DenominacionPuestoHelper denominacionPuestoHelper) {
        this.denominacionPuestoHelper = denominacionPuestoHelper;
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
}
