/*
 *  MenuHelper.java
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
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Menu;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Menu
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "menuHelper")
@SessionScoped
public class MenuHelper extends CatalogoHelper {
    /**
     * clase menu.
     */
    private Menu menu;

    /**
     * lista de menus.
     */
    private List<Menu> listaMenus;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Menu> listaMenuCodigo;
    
     /**
     * Variable para listar tipos de menus.
     */
    private List<SelectItem> listaTipoMenu;
    /**
     * Variable para dibujar el árbol.
     */
    private TreeNode root;
    /**
     * Menu Seleccionado del árbol.
     */
    private TreeNode menuSeleccionado;

    /**
     * Constructor por defecto.
     */
    public MenuHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del MenuHelper.
     */
    public final void iniciador() {
        setMenu(new Menu());
        setListaMenus(new ArrayList<Menu>());
        setListaMenuCodigo(new ArrayList<Menu>());
        root = new DefaultTreeNode();
        listaTipoMenu= new ArrayList<SelectItem>();
        menuSeleccionado = new DefaultTreeNode();
    }

    /**
     * @return the menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(final Menu menu) {
        this.menu = menu;
    }
   
    /**
     * @return the listaMenus
     */
    public List<Menu> getListaMenus() {
        return listaMenus;
    }

    /**
     * @param listaMenus the listaMenus to set
     */
    public void setListaMenus(final List<Menu> listaMenus) {
        this.listaMenus = listaMenus;
    }

    /**
     * @return the listaMenuCodigo
     */
    public List<Menu> getListaMenuCodigo() {
        return listaMenuCodigo;
    }

    /**
     * @param listaMenuCodigo the listaMenuCodigo to set
     */
    public void setListaMenuCodigo(final List<Menu> listaMenuCodigo) {
        this.listaMenuCodigo = listaMenuCodigo;
    }

    /**
     * @return the menuSeleccionado
     */
    public TreeNode getMenuSeleccionado() {
        return menuSeleccionado;
    }

    /**
     * @param menuSeleccionado the menuSeleccionado to set
     */
    public void setMenuSeleccionado(TreeNode menuSeleccionado) {
        this.menuSeleccionado = menuSeleccionado;
    }
    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the listaTipoMenu
     */
    public List<SelectItem> getListaTipoMenu() {
        return listaTipoMenu;
    }

    /**
     * @param listaTipoMenu the listaTipoMenu to set
     */
    public void setListaTipoMenu(List<SelectItem> listaTipoMenu) {
        this.listaTipoMenu = listaTipoMenu;
    }
}
