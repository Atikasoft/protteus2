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

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.helper.MenuRolHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.modelo.MenuRol;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Menu Rol .- asignacion de opciones de menú a los diferentes roles
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "menuRolBean")
@ViewScoped
public class MenuRolControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(MenuRolControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/rol/menu_por_rol.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ASIGNACION_MENUS = "/pages/administracion/rol/menu_por_rol.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/rol/lista_rol.jsf";

    /**
     * Servicio de administracion de menu y roles.
     */
    @EJB
    private MenuServicio menuServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{menuRolHelper}")
    private MenuRolHelper menuRolHelper;

    /**
     * Constructor por defecto.
     */
    public MenuRolControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        buscarMenusPorRol();
    }

    /**
     * Este metodo busca todos los menús del rol seleccionado.
     *
     * @return String
     */
    public String buscarMenusPorRol() {
        try {
            if (menuRolHelper.getMenuRol().getRol() != null && menuRolHelper.getMenuRol().getRol().getId() != null) {
                List<MenuRol> listaMenusRoles
                        = menuServicio.listarTodosMenuPorRol(menuRolHelper.getMenuRol().getRol().getId());
                menuRolHelper.setListaMenusRol(listaMenusRoles);
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales hijas.", e);
        }
        return FORMULARIO_ASIGNACION_MENUS;
    }

    /**
     * busca lista de menus por rol, que sean padres.
     */
    private void buscarMenusPrincipales() {
        try {
            menuRolHelper.getListaMenus().clear();
            menuRolHelper.setListaMenus(
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
            TreeNode nodoPadre;
            /*
             * carga el primer registro (nodo principal)
             */
            buscarMenusPrincipales();
            menuRolHelper.getListaMenusEliminacion().clear();
            menuRolHelper.getListaMenusTemporal().clear();
            if (menuRolHelper.getListaMenus().isEmpty()) {
                Menu menuRoot = new Menu();
                menuRoot.setNombre("SISTEMA DE MOVIMIENTOS DE PERSONAL Y NÓMINA");
                menuRoot.setEtiqueta("SISTEMA DE MOVIMIENTOS DE PERSONAL Y NÓMINA");
                menuRoot.setCodigo("PROTEUS");
                menuRoot.setVigente(Boolean.TRUE);
                menuRolHelper.getListaMenus().add(menuRoot);
                nodoPrincipal = new DefaultTreeNode(menuRolHelper.getListaMenus().get(0), null);
                menuRolHelper.setRoot(nodoPrincipal);
                menuRolHelper.getListaMenus().remove(0);
            } else {
                nodoPrincipal = new DefaultTreeNode(menuRolHelper.getListaMenus().get(0), null);
                menuRolHelper.setRoot(nodoPrincipal);
            }
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (Menu menu : menuRolHelper.getListaMenus()) {
                if (menu.getVigente()) {
                    if (esMenuAsignado(menu.getId(), false)) {
                        menu.setSeleccionado(Boolean.TRUE);
                        menu.setAlmacenado(Boolean.TRUE);
                    }
                    nodoPadre = new DefaultTreeNode(menu, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (menu.getId() != null) {
                        obtenerHijos(menu, nodoPadre);
                    }
                }
            }
            ejecutarComandoPrimefaces("dlgMenu.show();");
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
                    if (esMenuAsignado(menu.getId(), false)) {
                        menu.setSeleccionado(Boolean.TRUE);
                        menu.setAlmacenado(Boolean.TRUE);
                    }
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
     * Transacciona el evento change del checkbox para agregar o quitar opciones
     * de menu.
     *
     * @param menu
     */
    public void asignarSeleccionado(Menu menu) {
        if (menu.getSeleccionado()) {
            menuRolHelper.getListaMenusTemporal().add(menu);
            System.out.println(" agregando " + menu.getNombre() + "-" + menuRolHelper.getListaMenusTemporal().size());
            if (menu.getMenu() != null && menu.getMenu().getId() > 1 && !esMenuAsignado(menu.getMenu().getId(), true)) {
                System.out.println(" validadndo que este en la lista de agregados==>" + menu.getMenu().getNombre());
                System.out.println(" id del padre ==>" + menu.getMenu().getId());
                mostrarMensajeEnPantalla("Recuerde agregar el Menú Padre de la opción seleccionada", FacesMessage.SEVERITY_WARN);
            }
        } else if (!menu.getSeleccionado()) {
            menuRolHelper.getListaMenusTemporal().remove(menu);
            if (menu.getAlmacenado() != null && menu.getAlmacenado()) {
                menuRolHelper.getListaMenusEliminacion().add(menu);
                System.out.println(" eliminando " + menu.getNombre() + "-" + menuRolHelper.getListaMenusEliminacion().size());
            }

        }
    }

    /**
     * Almacena en la base de datos los registros agregados.
     *
     * @return
     */
    public String guardar() {
        try {
            //eliminar menus 
            for (Menu m : menuRolHelper.getListaMenusEliminacion()) {
                MenuRol menuRol = buscarMenusRolesPorMenu(m.getId());
                if (menuRol != null) {
                    iniciarDatosEntidad(menuRol, Boolean.FALSE);
                    menuRol.setVigente(Boolean.FALSE);
                    menuServicio.actualizarMenuRol(menuRol);
                    System.out.println(" Ya eliminado ==>" + m.getNombre() + " id menurol:" + menuRol.getId());
                } else {
                    System.out.println(" Objeto nulo " + m.getNombre());
                }
            }
            //agregar menus
            List<MenuRol> lista = new ArrayList<MenuRol>();

            for (Menu m : menuRolHelper.getListaMenusTemporal()) {
                if (!esMenuAsignado(m.getId(), false)) {
                    MenuRol mr = new MenuRol();
                    mr.setRol(menuRolHelper.getMenuRol().getRol());
                    mr.setMenu(m);
                    iniciarDatosEntidad(mr, Boolean.TRUE);
                    lista.add(mr);
                } else {
                    LOG.log(Level.INFO, " Men\u00fa  ya esta aignado {0}", m.getNombre());
                }
            }
            if (!lista.isEmpty()) {
                menuServicio.guardarMenusRoles(lista);
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO_VARIOS, FacesMessage.SEVERITY_INFO);
            }
            menuRolHelper.getListaMenusEliminacion().clear();
            menuRolHelper.getListaMenusTemporal().clear();
            buscarMenusPorRol();
            ejecutarComandoPrimefaces("dlgAgregar.hide();");
            actualizarComponente(":frmPrincipal:panelMenuMain");
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al agregar las opciones de menu.", e);
        }
        return null;
    }

    /**
     * Encuentra el registro MenuRol de un id de menú específico.
     *
     * @param id
     * @return
     */
    public MenuRol buscarMenusRolesPorMenu(Long id) {
        try {
            List<MenuRol> lista = menuServicio.listarTodosMenusRolesPorMenuYRol(id, menuRolHelper.getMenuRol().getRol().getId());
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (ServicioException e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    /**
     * Verifica si el elemento ya se encuentra asignado al rol.
     *
     * @param idMenu
     * @param agregando
     * @return
     */
    public boolean esMenuAsignado(Long idMenu, boolean agregando) {
        boolean asignado = false;
        for (MenuRol m : menuRolHelper.getListaMenusRol()) {
            if (m.getMenu().getId().equals(idMenu)) {
                asignado = true;
                break;
            }
        }
        if (!asignado && agregando) {
            for (Menu m : menuRolHelper.getListaMenusTemporal()) {
                if (m.getId().equals(idMenu)) {
                    asignado = true;
                    break;
                }
            }
        }
        return asignado;
    }

    /**
     * Este metodo elimina un menu de la lista de menus del rol seleccionado
     *
     * @return String
     */
    public String eliminarMenus() {
        try {
            if (menuRolHelper.getMenuRolEdit() != null) {
                iniciarDatosEntidad(menuRolHelper.getMenuRolEdit(), Boolean.FALSE);
                menuRolHelper.getMenuRolEdit().setVigente(Boolean.FALSE);
                if (menuRolHelper.getMenuRolEdit().getId() != null) {
                    menuServicio.actualizarMenuRol(menuRolHelper.getMenuRolEdit());
                    buscarMenusPorRol();
                } else {
                    menuRolHelper.getListaMenusRol().remove(menuRolHelper.getMenuRolEdit());
                }
                actualizarComponente(":frmPrincipal:tablarol");
                ejecutarComandoPrimefaces("confirmEliminacion.hide();");
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al eliminar los menus.", e);
        }
        return null;
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
        menuRolHelper.getListaMenusEliminacion().clear();
        menuRolHelper.getListaMenusTemporal();
        return FORMULARIO_ASIGNACION_MENUS;
    }

    /**
     * @return the menuRolHelper
     */
    public MenuRolHelper getMenuRolHelper() {
        return menuRolHelper;
    }

    /**
     * @param menuRolHelper the menuRolHelper to set
     */
    public void setMenuRolHelper(MenuRolHelper menuRolHelper) {
        this.menuRolHelper = menuRolHelper;
    }

}
