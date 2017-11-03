/*
 *  MenuRol.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
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
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "menus_roles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = MenuRol.BUSCAR_VIGENTES,
            query = "SELECT a FROM MenuRol a where a.menu.vigente=true and a.vigente=true and a.rol.vigente=true order by a.menu.nombre "),
    @NamedQuery(name = MenuRol.BUSCAR_POR_ROL,
            query = "SELECT a FROM MenuRol a where a.menu.vigente=true and a.vigente=true and a.rol.id = ?1 and a.rol.vigente=true order by a.menu.orden, a.menu.nombre "),
    @NamedQuery(name = MenuRol.BUSCAR_POR_MENU_Y_ROL,
            query = "SELECT a FROM MenuRol a where a.menu.vigente=true and a.vigente=true and a.menu.id = ?1 and a.rol.id = ?2 and a.rol.vigente=true order by a.menu.orden, a.menu.nombre ")
})
public class MenuRol extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "MenuRol.buscarVigente";
    /**
     * Nombre para busqueda de vigentes por id del rol.
     */
    public static final String BUSCAR_POR_ROL = "MenuRol.buscarPorRol";
    /**
     * Nombre para busqueda de vigentes por id de menu.
     */
    public static final String BUSCAR_POR_MENU_Y_ROL = "MenuRol.buscarPorMenuYRol";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo de referencia a la entidad rol.
     */
    @JoinColumn(name = "rol_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Rol rol;

    /**
     * Campo de referencia a la entidad menu.
     */
    @JoinColumn(name = "menu_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;


    /**
     * Constructor.
     */
    public MenuRol() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MenuRol(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
    public void setRol(Rol rol) {
        this.rol = rol;
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
}
