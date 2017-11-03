/*
 *  Rol.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "roles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Rol.BUSCAR_VIGENTES,
            query = "SELECT a FROM Rol a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Rol.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM Rol a where a.vigente=true and a.codigo = ?1 order by a.nombre "),
    @NamedQuery(name = Rol.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM Rol a where a.vigente=true and a.nombre = ?1 order by a.nombre ")
})
public class Rol extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Rol.buscarVigente";

    /**
     * Nombre para busqueda de vigentes por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "Rol.buscarPorCodigo";
    /**
     * Nombre para busqueda de vigentes por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Rol.buscarPorNombre";

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
    @Basic(optional = false)
    @Size(min = 1, max = 400)
    private String nombre;

    /**
     * descripción.
     */
    @Column(name = "descripcion")
    @Size(min = 1, max = 800)
    @Basic(optional = false)
    @NotNull
    private String descripcion;
    
//     @OneToMany(mappedBy = "rol")
//    private List<MenuRol> listaMenusPorRoles;
//
//     @OneToMany(mappedBy = "rol")
//    private List<MenuRol> listaRolesPorServidor;
    /**
     * Constructor.
     */
    public Rol() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Rol(final Long id) {
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

}
