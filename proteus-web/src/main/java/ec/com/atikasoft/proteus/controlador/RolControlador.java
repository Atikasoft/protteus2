/*
 *  RolControlador.java
  *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  16/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.MenuRolControlador.FORMULARIO_ASIGNACION_MENUS;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.RolHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Rol
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rolBean")
@ViewScoped
public class RolControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(RolControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/rol/rol.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/rol/lista_rol.jsf";        

    /**
     * Servicio de administracion de menús y roles.
     */
    @EJB
    private MenuServicio menuServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{rolHelper}")
    private RolHelper rolHelper;
    /**
     * Constructor por defecto.
     */
    public RolControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(rolHelper);
        setRolHelper(rolHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {
            if (rolHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    menuServicio.guardarRol(rolHelper.getRol());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                menuServicio.actualizarRol(rolHelper.getRol());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            rolHelper.getListaRolCodigo().clear();
            rolHelper.setListaRolCodigo(menuServicio.listarTodosRolPorCodigo(
                    rolHelper.getRol().getCodigo()));

            if (rolHelper.getListaRolCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(rolHelper.getRolEditDelete());
            Rol d = (Rol) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            rolHelper.setRol(d);
            rolHelper.setEsNuevo(Boolean.FALSE);

        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        rolHelper.setRol(new Rol());
        rolHelper.iniciador();
        iniciarDatosEntidad(rolHelper.getRol(), Boolean.TRUE);
        rolHelper.setEsNuevo(Boolean.TRUE);

        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            menuServicio.eliminarRol(rolHelper.getRolEditDelete());
            rolHelper.getListaRols().
                    remove(rolHelper.getRolEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            rolHelper.getListaRols().clear();
            rolHelper.setListaRols(
                    menuServicio.listarTodosRolesPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }
    
       /**
     * Permite verificar si un Regimen Laboral tiene Niveles ocupacionales
     * asociados
     *
     * @param menu
     * @return true si hay Nivel ocupacional en ese regimen
     */
    public Boolean existenMenusHijos(Menu menu) {
        Boolean hayEscalas = true;
        try {
            if (!menu.getListaMenuHijos().isEmpty()) {
                List<Menu> lista = menuServicio.listarTodasMenusPorMenuPadre(menu.getId());
                hayEscalas = lista == null || !lista.isEmpty();
            } else {
                hayEscalas = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de menus hijos ", ex);
        }
        return hayEscalas;
    }

    /**
     * Regla de navegacion.
     *
     * @return String
     */
    public String regresar() {
        return LISTA_ENTIDAD;
    }

    /**
     * Regla de navegacion.
     *
     * @return String
     */
    public String irAsignacionMenus() {
        return FORMULARIO_ASIGNACION_MENUS;
    }  
   
    /**
     * @return the rolHelper
     */
    public RolHelper getRolHelper() {
        return rolHelper;
    }

    /**
     * @param rolHelper the rolHelper to set
     */
    public void setRolHelper(RolHelper rolHelper) {
        this.rolHelper = rolHelper;
    }
}
