/*
 *  MenuRolHelper.java
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
 *  17/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.modelo.MenuRol;
import ec.com.atikasoft.proteus.modelo.Rol;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

/**
 * MenuRol
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "menuRolHelper")
@SessionScoped
public class MenuRolHelper extends CatalogoHelper {
    /**
     * clase menuRol.
     */
    private Menu menu;

    /**
     * lista de menuRols.
     */
    private List<Menu> listaMenus;
 
    /**
     * Variable para dibujar el árbol.
     */
    private TreeNode root;
   
        /**
     * clase menuRol.
     */
    private MenuRol menuRol = new MenuRol();
    private MenuRol menuRolEdit;

    /**
     * lista de menuRols.
     */
    private List<MenuRol> listaMenusRol;
    private Set<Menu> listaMenusTemporal;
    private Set<Menu> listaMenusEliminacion;
    
      private  LazyDataModel<Menu> lazyModel;  

    /**
     * Constructor por defecto.
     */
    public MenuRolHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del MenuRolHelper.
     */
    public final void iniciador() {
        menu = new Menu();
        listaMenus = new ArrayList<Menu>();
        menuRol = new MenuRol();
        menuRol.setRol(new Rol());
        menuRol.setMenu(new Menu());
        listaMenusRol = new ArrayList<MenuRol>();                
        root = new DefaultTreeNode();
        menuRolEdit =new MenuRol();
        listaMenusTemporal = new HashSet<Menu>();
        listaMenusEliminacion= new HashSet<Menu>();
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
     * @return the menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(Menu menu) {
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
    public void setListaMenus(List<Menu> listaMenus) {
        this.listaMenus = listaMenus;
    }

    /**
     * @return the menuRol
     */
    public MenuRol getMenuRol() {
        return menuRol;
    }

    /**
     * @param menuRol the menuRol to set
     */
    public void setMenuRol(MenuRol menuRol) {
        this.menuRol = menuRol;
    }

    /**
     * @return the listaMenusRolVO
     */
    public List<MenuRol> getListaMenusRol() {
        return listaMenusRol;
    }

    /**
     * @param listaMenusRol the listaMenusRol to set
     */
    public void setListaMenusRol(List<MenuRol> listaMenusRol) {
        this.listaMenusRol = listaMenusRol;
    }

    /**
     * @return the lazyModel
     */
    public LazyDataModel<Menu> getLazyModel() {
        return lazyModel;
    }

    /**
     * @param lazyModel the lazyModel to set
     */
    public void setLazyModel(LazyDataModel<Menu> lazyModel) {
        this.lazyModel = lazyModel;
    }

    /**
     * @return the menuRolEdit
     */
    public MenuRol getMenuRolEdit() {
        return menuRolEdit;
    }

    /**
     * @param menuRolEdit the menuRolEdit to set
     */
    public void setMenuRolEdit(MenuRol menuRolEdit) {
        this.menuRolEdit = menuRolEdit;
    }

    /**
     * @return the listaMenusTemporal
     */
    public Set<Menu> getListaMenusTemporal() {
        return listaMenusTemporal;
    }

    /**
     * @param listaMenusTemporal the listaMenusTemporal to set
     */
    public void setListaMenusTemporal(Set<Menu> listaMenusTemporal) {
        this.listaMenusTemporal = listaMenusTemporal;
    }

    /**
     * @return the listaMenusEliminacion
     */
    public Set<Menu> getListaMenusEliminacion() {
        return listaMenusEliminacion;
    }

    /**
     * @param listaMenusEliminacion the listaMenusEliminacion to set
     */
    public void setListaMenusEliminacion(Set<Menu> listaMenusEliminacion) {
        this.listaMenusEliminacion = listaMenusEliminacion;
    }
}
