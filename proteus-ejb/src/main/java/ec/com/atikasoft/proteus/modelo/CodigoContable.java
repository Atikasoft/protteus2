/*
 *  CodigoContable.java
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
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "codigos_contables", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CodigoContable.BUSCAR_POR_CODIGO_Y_NOMBRE,
            query = "SELECT a FROM CodigoContable a where a.codigo like ?1 or a.nombre like ?2 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = CodigoContable.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM CodigoContable a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = CodigoContable.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM CodigoContable a where a.codigo like ?1 and a.vigente=true order by a.codigo"),
    @NamedQuery(name = CodigoContable.BUSCAR_VIGENTES,
            query = "SELECT a FROM CodigoContable a where a.vigente=true order by a.nombre")
})
public class CodigoContable extends EntidadBasica {

    /**
     * Variable para busqueda por codigo y nombre.
     */
    public static final String BUSCAR_POR_CODIGO_Y_NOMBRE = "CodigoContable.buscarPorCodigoNombre ";

    /**
     * Variable para busqueda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "CodigoContable.buscarPorNombre ";
    
        /**
     * Variable para busqueda por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "CodigoContable.buscarPorCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "CodigoContable.buscarVigente";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo codigo.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    /**
     * Constructor.
     */
    public CodigoContable() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public CodigoContable(final Long id) {
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
