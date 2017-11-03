/*
 *  MenuControlador.java
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

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.MenuHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Menu
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(MenuControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/menu/menu.jsf";

    /**
     * Servicio de administracion de menu y roles.
     */
    @EJB
    private MenuServicio menuServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{menuHelper}")
    private MenuHelper menuHelper;

    /**
     * Constructor por defecto.
     */
    public MenuControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(menuHelper);
        setMenuHelper(menuHelper);

        llenarArbolMenu();
        iniciarComboTipoMenu();
    }

    @Override
    public String guardar() {
        try {
            if (menuHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    menuServicio.guardarMenu(menuHelper.getMenu());
                    llenarArbolMenu();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    ejecutarComandoPrimefaces("dlgMenu.hide()");                    
                }

            } else {
                menuServicio.actualizarMenu(menuHelper.getMenu());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("dlgMenu.hide()");
                llenarArbolMenu();
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
            menuHelper.getListaMenuCodigo().clear();
            menuHelper.setListaMenuCodigo(menuServicio.listarTodosMenuPorCodigo(
                    menuHelper.getMenu().getCodigo()));

            if (menuHelper.getListaMenuCodigo().isEmpty()) {
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
            if (menuHelper.getMenuSeleccionado().getData() != null) {
                menuHelper.setMenu((Menu) menuHelper.getMenuSeleccionado().getData());
                iniciarDatosEntidad(menuHelper.getMenu(), Boolean.FALSE);
                menuHelper.setEsNuevo(Boolean.FALSE);
            }
            if (menuHelper.getMenu().getId() != null) {
                ejecutarComandoPrimefaces("dlgMenu.show()");
            } else {
                ejecutarComandoPrimefaces("msgDialog.show()");
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        menuHelper.setMenu(new Menu());
        if (menuHelper.getMenuSeleccionado().getData() != null) {
            menuHelper.getMenu().setMenu((Menu) menuHelper.getMenuSeleccionado().getData());
            iniciarDatosEntidad(menuHelper.getMenu(), Boolean.TRUE);
            menuHelper.setEsNuevo(Boolean.TRUE);
            ejecutarComandoPrimefaces("dlgMenu.show()");
        }
        return FORMULARIO_ENTIDAD;
    }
/**
     * Este metodo llena las opciones para el combo de tipo vacacion.
     */
    private void iniciarComboTipoMenu() {
        iniciarCombos(menuHelper.getListaTipoMenu());
            menuHelper.getListaTipoMenu().add(new SelectItem("E","Externo"));
            menuHelper.getListaTipoMenu().add(new SelectItem("I","Interno"));
    }

    @Override
    public String borrar() {
        try {
            menuHelper.setMenu((Menu) menuHelper.getMenuSeleccionado().getData());
            if (menuHelper.getMenu().getId() != null) {
                 if (existenMenusHijos(menuHelper.getMenu())) {
                    mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
                } else {
                    menuServicio.eliminarMenu(menuHelper.getMenu());
                    menuHelper.getListaMenus().remove(menuHelper.getMenu());
                    quitarNodo();
                    mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                }
            }else{
                 mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
                    ejecutarComandoPrimefaces("msgDialog.show()");
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * Elimina fisicamente el registro del arbol.
     */
    public void quitarNodo() {
        menuHelper.getMenuSeleccionado().getChildren().clear();
        menuHelper.getMenuSeleccionado().getParent().getChildren().remove(menuHelper.
                getMenuSeleccionado());
        menuHelper.getMenuSeleccionado().setParent(null);
        menuHelper.setMenuSeleccionado(null);
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
            if(!menu.getListaMenuHijos().isEmpty()){
                List<Menu> lista =menuServicio.listarTodasMenusPorMenuPadre(menu.getId());                
                hayEscalas = lista == null || !lista.isEmpty();
            }else{
                hayEscalas = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de menus hijos ", ex);
        }
        return hayEscalas;
    }

    @Override
    public String buscar() {
        try {
            menuHelper.getListaMenus().clear();
            menuHelper.setListaMenus(
                    menuServicio.listarTodasMenusPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return null;
    }

    private void buscarMenus() {
        try {
            menuHelper.getListaMenus().clear();
            menuHelper.setListaMenus(
                    menuServicio.listarTodosMenusVigentesPrincipales());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda menus", ex);
        }
    }

    /**
     * llena ubiacion geografica.
     *
     * @return
     */
    public String llenarArbolMenu() {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre, nodoHijo;
            /*
             * carga el primer registro (nodo principal)
             */
            buscarMenus();
            if (menuHelper.getListaMenus().isEmpty()) {
                Menu menuRoot = new Menu();
                menuRoot.setNombre("SISTEMA DE MOVIMIENTOS DE PERSONAL Y NÓMINA");
                menuRoot.setEtiqueta("SISTEMA DE MOVIMIENTOS DE PERSONAL Y NÓMINA");
                menuRoot.setCodigo("PROTEUS");
                menuRoot.setVigente(Boolean.TRUE);
                menuHelper.getListaMenus().add(menuRoot);
                nodoPrincipal = new DefaultTreeNode(menuHelper.getListaMenus().get(0), null);
                menuHelper.setRoot(nodoPrincipal);
                menuHelper.getListaMenus().remove(0);
            } else {
                nodoPrincipal = new DefaultTreeNode(menuHelper.getListaMenus().get(0), null);
                menuHelper.setRoot(nodoPrincipal);
            }
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (Menu un : menuHelper.getListaMenus()) {
                if (un.getVigente()) {
                    nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (un.getId() != null) {
                        obtenerHijos(un, nodoPadre);
                    }
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return FORMULARIO_ENTIDAD;
    }
 
    /**
     * buscar hijos del padre.
     *
     * @param registroPadre
     * @param nodoPadre
     */
    public void obtenerHijos(Menu registroPadre, TreeNode nodoPadre) {

        try {
            for (Menu menu : registroPadre.getListaMenuHijos()) {            
                if (menu.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(menu, nodoPadre);
                    if (!menu.getListaMenuHijos().isEmpty()) {
                        obtenerHijos(menu, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * @return the menuHelper
     */
    public MenuHelper getMenuHelper() {
        return menuHelper;
    }

    /**
     * @param menuHelper the menuHelper to set
     */
    public void setMenuHelper(MenuHelper menuHelper) {
        this.menuHelper = menuHelper;
    }
}
