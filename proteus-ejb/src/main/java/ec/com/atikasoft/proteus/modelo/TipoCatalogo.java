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
@Table(name = "tipo_catalogo", catalog = "sch_proteus")
@NamedQueries({@NamedQuery(name = TipoCatalogo.BUSCAR_VIGENTES, query =
    "SELECT o FROM TipoCatalogo o WHERE o.vigente=true ORDER BY o.nombre ")})
public class TipoCatalogo extends EntidadBasica {

    /**
     * Consulta para recuperar todos los tipos de catalogos vigentes.
     */
    public static final String BUSCAR_VIGENTES = "TipoCatalogo.buscarVigentes";

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
     * constructor.
     */
    public TipoCatalogo() {
    }

    /**
     * constructor.
     *
     * @param id Long
     */
    public TipoCatalogo(final Long id) {
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

    @Override
    public String toString() {
        return "ec.markasoft.claseabierta.persistencia.entidades.TipoCatalogo[ id=" + id + " ]";
    }
}
