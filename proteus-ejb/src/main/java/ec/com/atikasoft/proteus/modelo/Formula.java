/*
 *  Formula.java
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
 *  07/10/2013
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
@Table(name = "formulas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Formula.BUSCAR_POR_NOMBRE,
    query = "SELECT a FROM Formula a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Formula.BUSCAR_VIGENTES,
    query = "SELECT a FROM Formula a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Formula.BUSCAR_EN_FORMULA,
    query = "SELECT a FROM Formula a where a.formula like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Formula.BUSCAR_POR_CODIGO,
    query = "SELECT a FROM Formula a where a.codigo=?1  and a.vigente=true order by a.nombre")
})
public class Formula extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Formula.buscarporNombre ";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "Formula.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Formula.buscarVigente";

    /**
     * Nombre para buscar codigo de formula en campo formula
     */
    public static final String BUSCAR_EN_FORMULA = "Formula.buscarEnFormula";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo código.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo tipo.
     */
    @Column(name = "formula")
    private String formula;

    /**
     * Constructor.
     */
    public Formula() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Formula(final Long id) {
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
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }
}
