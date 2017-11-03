/*
 *  Menu.java
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
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "menus", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Menu.BUSCAR_VIGENTES,
            query = "SELECT a FROM Menu a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Menu.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM Menu a where a.vigente=true and a.codigo = ?1 order by a.nombre "),
    @NamedQuery(name = Menu.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM Menu a where a.vigente=true and a.nombre = ?1 order by a.nombre "),
    @NamedQuery(name = Menu.BUSCAR_POR_MENU_PADRE,
            query = "SELECT a FROM Menu a where a.vigente=true and a.menu.id = ?1 order by a.nombre "),
    @NamedQuery(name = Menu.BUSCAR_MENUS_PRINCIPALES, 
            query = "Select a FROM Menu a where a.vigente=true and a.menu is null order by a.orden, a.nombre")
})
public class Menu extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Menu.buscarVigente";

    /**
     * Nombre para busqueda de vigentes por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "Menu.buscarPorCodigo";
    /**
     * Nombre para busqueda de vigentes por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Menu.buscarPorNombre";
      /**
     * Nombre para busqueda de vigentes por nombre.
     */
    public static final String BUSCAR_MENUS_PRINCIPALES = "Menu.buscarPrincipales";
      /**
     * Nombre para busqueda de vigentes por padre.
     */
    public static final String BUSCAR_POR_MENU_PADRE = "Menu.buscarPorMenuPadre";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo identificador del número de trámite por ejercicio fiscal e
     * institucion.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;

    /**
     * Especifica la fecha de registro de la solicitud de roles.
     */
    @Column(name = "nombre")
    @NotNull
    @Size(min = 1, max = 400)
    private String nombre;

    /**
     * descripción.
     */
    @Column(name = "etiqueta")
    @NotNull
    @Size(min = 1, max = 400)
    private String etiqueta;
    
     /**
     * Especifica el path de la pagina.
     */
    @Column(name = "url")
    @NotNull
    @Size(min = 1, max = 400)
    private String url;
    
      
     /**
     * Especifica el orden .
     */
    @Column(name = "orden")
    private Integer orden;

    /**
     * Especifica el orden .
     */
    @JoinColumn(name = "menu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;
    
      /**
     * Campo tipo: I:Internas y E-Externas. Solo las de tipo E se dibujan en el menu.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private String tipo;

       /**
     * Lista de menus hijos.
     */
    @OneToMany(mappedBy = "menu")
      private List<Menu> listaMenuHijos;
 
    @Transient
    private String nombreCompleto;
    
      @Transient
    private Boolean seleccionado;
      
      @Transient
    private Boolean almacenado;
    /**
     * Constructor.
     */
    public Menu() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Menu(final Long id) {
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the listaMenuHijos
     */
    public List<Menu> getListaMenuHijos() {
        return listaMenuHijos;
    }

    /**
     * @param listaMenuHijos the listaMenuHijos to set
     */
    public void setListaMenuHijos(List<Menu> listaMenuHijos) {
        this.listaMenuHijos = listaMenuHijos;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
         Menu o = menu;
        nombreCompleto = "";
        while (o != null && o.getMenu() != null) {
            nombreCompleto = o.getNombre().concat(" / ").concat(nombreCompleto);
            o = o.getMenu();
        }
        nombreCompleto = nombreCompleto.concat(nombre);
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * @return the seleccionado
     */
    public Boolean getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * @return the almacenado
     */
    public Boolean getAlmacenado() {
        return almacenado;
    }

    /**
     * @param almacenado the almacenado to set
     */
    public void setAlmacenado(Boolean almacenado) {
        this.almacenado = almacenado;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
