/*
 *  Asignacion.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 20, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Roles asignados a una transición especifica.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.asignaciones")
@NamedQueries({@NamedQuery(name = Asignacion.BUSCAR_POR_TRANSICION, query =
    "SELECT o FROM Asignacion o WHERE o.vigente=true AND o.transicion.id=?1")})
public class Asignacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar asignaciones por transicion.
     */
    public static final String BUSCAR_POR_TRANSICION = "Asignacion.buscarPorTransicion";

    /**
     * Identificador único de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del rol.
     */
    @Column(name = "identificador_rol")
    private Long identificadorRol;

    /**
     * Nemonico del rol.
     */
    @Column(name = "nemonico_rol")
    private String nemonicoRol;

    /**
     * Referencia de la transicion.
     */
    @JoinColumn(name = "transiciones_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Transicion transicion;

    /**
     * Constructor.
     */
    public Asignacion() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the identificadorRol
     */
    public Long getIdentificadorRol() {
        return identificadorRol;
    }

    /**
     * @return the transicion
     */
    public Transicion getTransicion() {
        return transicion;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param identificadorRol the identificadorRol to set
     */
    public void setIdentificadorRol(final Long identificadorRol) {
        this.identificadorRol = identificadorRol;
    }

    /**
     * @param transicion the transicion to set
     */
    public void setTransicion(final Transicion transicion) {
        this.transicion = transicion;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the nemonicoRol
     */
    public String getNemonicoRol() {
        return nemonicoRol;
    }

    /**
     * @param nemonicoRol the nemonicoRol to set
     */
    public void setNemonicoRol(final String nemonicoRol) {
        this.nemonicoRol = nemonicoRol;
    }
}
