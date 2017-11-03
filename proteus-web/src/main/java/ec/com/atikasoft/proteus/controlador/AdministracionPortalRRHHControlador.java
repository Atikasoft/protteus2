/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.AdministracionPortalRrhhHelper;
import ec.com.atikasoft.proteus.dao.PortalRhhDao;
import ec.com.atikasoft.proteus.modelo.PortalRhh;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

@ManagedBean(name = "adminPortalRrhhBean")
@ViewScoped
public class AdministracionPortalRRHHControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/portal_rrhh/lista_portal_rrhh.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/portal_rrhh/portal_rrhh.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{adminPortalRrhhHelper}")
    private AdministracionPortalRrhhHelper admininistracionPortalRrhhHelper;

    /**
     * Dao de paratro institucional.
     */
    @EJB
    private PortalRhhDao portalRhhDao;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor por defecto.
     */
    public AdministracionPortalRRHHControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(admininistracionPortalRrhhHelper.getPortalRrhhSeleccionado());
            PortalRhh portal = (PortalRhh) cloneBean;
            iniciarDatosEntidad(portal, Boolean.FALSE);
            admininistracionPortalRrhhHelper.setPortalRrhhSeleccionado(portal);
            admininistracionPortalRrhhHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    public AdministracionPortalRrhhHelper getAdmininistracionPortalRrhhHelper() {
        return admininistracionPortalRrhhHelper;
    }

    public void setAdmininistracionPortalRrhhHelper(AdministracionPortalRrhhHelper admininistracionPortalRrhhHelper) {
        this.admininistracionPortalRrhhHelper = admininistracionPortalRrhhHelper;
    }

    @Override
    public String guardar() {
        try {
            if (admininistracionPortalRrhhHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla("El código ingresado ya existe", FacesMessage.SEVERITY_WARN);
                } else {
                    administracionServicio.guardarPortalRrhh(admininistracionPortalRrhhHelper.getPortalRrhhSeleccionado());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                administracionServicio.actualizarPortalRrhh(admininistracionPortalRrhhHelper.getPortalRrhhSeleccionado());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        admininistracionPortalRrhhHelper.setPortalRrhhSeleccionado(new PortalRhh());
        iniciarDatosEntidad(admininistracionPortalRrhhHelper.getPortalRrhhSeleccionado(), Boolean.TRUE);
        admininistracionPortalRrhhHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String buscar() {
        try {
            admininistracionPortalRrhhHelper.getListaPortalRhhs().clear();
            admininistracionPortalRrhhHelper.setListaPortalRhhs(portalRhhDao.buscarTodos());
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            portalRhhDao.eliminar(admininistracionPortalRrhhHelper.getPortalRrhhSeleccionado());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    public Boolean validarCodigo() {
        Boolean existe = true;
        try {
            existe = !portalRhhDao.listarPortalRhhPorCodigo(admininistracionPortalRrhhHelper.
                    getPortalRrhhSeleccionado().getCodigo()).isEmpty();

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del codigo", ex);
        }
        return existe;
    }

}
