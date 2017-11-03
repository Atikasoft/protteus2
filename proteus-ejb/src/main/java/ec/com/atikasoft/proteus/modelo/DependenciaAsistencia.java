/*
 *  TipoCatalogo.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  01/10/2013
 *
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

/**
 * TipoCatalogo
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "dependencias_asistencia", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DependenciaAsistencia.BUSCAR_POR_CODIGO, query = "SELECT o FROM DependenciaAsistencia o WHERE o.codigo=?1 AND o.vigente=true")
})
public class DependenciaAsistencia extends EntidadBasica {

    /**
     * Consulta para recuperar todos los tipos de catalogos vigentes.
     */
    public static final String BUSCAR_POR_CODIGO = "DependenciaAsistencia.buscarPorCodigo";

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    /**
     * codigo.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * codigo.
     */
    @Column(name = "codigo_unidad_organizacional")
    private String codigoUnidadOrganizacional;

    /**
     * constructor.
     */
    public DependenciaAsistencia() {
        super();
    }

    /**
     * constructor.
     *
     * @param id Long
     */
    public DependenciaAsistencia(final Long id) {
        super();
        this.id = id;
    }

    /**
     * get.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * set.
     *
     * @param id Long
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * get.
     *
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * set.
     *
     * @param nombre String
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * get.
     *
     * @return String
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * set.
     *
     * @param codigo String
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigoUnidadOrganizacional
     */
    public String getCodigoUnidadOrganizacional() {
        return codigoUnidadOrganizacional;
    }

    /**
     * @param codigoUnidadOrganizacional the codigoUnidadOrganizacional to set
     */
    public void setCodigoUnidadOrganizacional(final String codigoUnidadOrganizacional) {
        this.codigoUnidadOrganizacional = codigoUnidadOrganizacional;
    }
}
