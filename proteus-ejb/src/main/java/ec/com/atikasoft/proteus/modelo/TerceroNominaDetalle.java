/*
 *  Tercero.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "pagos_terceros_nomina_detalles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TerceroNominaDetalle.BUSCAR_VIGENTES,
            query = "SELECT a FROM TerceroNominaDetalle a where a.vigente=true order by a.fechaCreacion desc"),
    @NamedQuery(name = TerceroNominaDetalle.BUSCAR_POR_TERCERO_Y_NOMINA_DET,
            query = "SELECT a FROM TerceroNominaDetalle a where a.vigente=true and a.tercero.id=?1 and a.nominaDetalle.id = ?2  order by a.fechaCreacion desc"),
    @NamedQuery(name = TerceroNominaDetalle.BUSCAR_POR_TERCERO,
            query = "SELECT a FROM TerceroNominaDetalle a where a.vigente=true and a.tercero.id=?1 order by a.fechaCreacion desc"),
    @NamedQuery(name = TerceroNominaDetalle.ELIMINAR_POR_NOMINA,
            query = "DELETE FROM TerceroNominaDetalle o WHERE o.nominaDetalle.nomina.id=?1"),
    @NamedQuery(name = TerceroNominaDetalle.BUSCAR_POR_IDENTIFICACION,
            query = "SELECT a FROM TerceroNominaDetalle a where a.vigente=true and a.tercero.tipoIdentificacion = ?1 and a.tercero.numeroIdentificacion=?2 order by a.fechaCreacion desc")
})
public class TerceroNominaDetalle extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "TerceroNominaDetalle.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "TerceroNominaDetalle.buscarPorIdentificacion";
    /**
     * Nombre para busqueda de vigentes por tercero.
     */
    public static final String BUSCAR_POR_TERCERO = "TerceroNominaDetalle.buscarPorTercero";
    /**
     * Nombre para busqueda de vigentes por estado.
     */
    public static final String BUSCAR_POR_TERCERO_Y_NOMINA_DET = "TerceroNominaDetalle.buscarPorTerceroYNominaDetalle";
    /**
     * Nombre para busqueda de vigentes por estado.
     */
    public static final String ELIMINAR_POR_NOMINA = "TerceroNominaDetalle.buscarPorNomina";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a institucion_ejercicio_fiscal_id .
     */
    @JoinColumn(name = "pago_tercero_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tercero tercero;

    /**
     * Referencia a detalle de nomina mediante la cual se hace el pago .
     */
    @JoinColumn(name = "nomina_detalle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaDetalle nominaDetalle;

    /**
     * Campo estado: <R>egistrado, <N>En Nomina,
     * <P>
     * Pagado.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;

    /**
     * Constructor.
     */
    public TerceroNominaDetalle() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TerceroNominaDetalle(final Long id) {
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
     * @return the tercero
     */
    public Tercero getTercero() {
        return tercero;
    }

    /**
     * @param tercero the tercero to set
     */
    public void setTercero(Tercero tercero) {
        this.tercero = tercero;
    }

    /**
     * @return the nominaDetalle
     */
    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }

    /**
     * @param nominaDetalle the nominaDetalle to set
     */
    public void setNominaDetalle(NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
