/*
 *  RolHelper.java
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
import ec.com.atikasoft.proteus.modelo.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Rol
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rolHelper")
@SessionScoped
public class RolHelper extends CatalogoHelper {

    /**
     * clase rol.
     */
    private Rol rol;

    /**
     * clase rol puesto para editar.
     */
    private Rol rolEditDelete;

    /**
     * lista de rols.
     */
    private List<Rol> listaRols;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Rol> listaRolCodigo;

    /**
     * Constructor por defecto.
     */
    public RolHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del RolHelper.
     */
    public final void iniciador() {
        setRol(new Rol());
        setRolEditDelete(new Rol());
        setListaRols(new ArrayList<Rol>());
       setListaRolCodigo(new ArrayList<Rol>());
        
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(final Rol rol) {
        this.rol = rol;
    }

    /**
     * @return the rolEditDelete
     */
    public Rol getRolEditDelete() {
        return rolEditDelete;
    }

    /**
     * @param rolEditDelete the rolEditDelete to set
     */
    public void setRolEditDelete(final Rol rolEditDelete) {
        this.rolEditDelete = rolEditDelete;
    }

    /**
     * @return the listaRols
     */
    public List<Rol> getListaRols() {
        return listaRols;
    }

    /**
     * @param listaRols the listaRols to set
     */
    public void setListaRols(final List<Rol> listaRols) {
        this.listaRols = listaRols;
    }

    /**
     * @return the listaRolCodigo
     */
    public List<Rol> getListaRolCodigo() {
        return listaRolCodigo;
    }

    /**
     * @param listaRolCodigo the listaRolCodigo to set
     */
    public void setListaRolCodigo(final List<Rol> listaRolCodigo) {
        this.listaRolCodigo = listaRolCodigo;
    }
}
