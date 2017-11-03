/*
 *  AnticipoPago.java
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
import java.math.BigDecimal;
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
@Table(name = "anticipos_pagos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = AnticipoPago.BUSCAR_VIGENTES, query = "SELECT a FROM AnticipoPago a where a.vigente=true order by a.fechaCreacion desc"),
    @NamedQuery(name = AnticipoPago.BUSCAR_POR_PLAN_PAGO, query = "SELECT a FROM AnticipoPago a where a.vigente=true and a.anticipoPlanPagoId=?1 order by a.fechaCreacion desc"),
    @NamedQuery(name = AnticipoPago.ELIMINAR, query = "DELETE FROM AnticipoPago o  WHERE o.nominaDetalle.nomina.id=?1 "),
    @NamedQuery(name = AnticipoPago.ELIMINAR_POR_SERVIDOR, query = "DELETE FROM AnticipoPago o WHERE o.nominaDetalle.nomina.id=?1 AND o.nominaDetalle.distributivoDetalle.id=?2"),
    @NamedQuery(name = AnticipoPago.SUMAR_PAGOS, query = "SELECT SUM(o.montoPagado) FROM AnticipoPago o WHERE o.anticipoPlanPago.id=?1 AND o.vigente=true"),
    @NamedQuery(name = AnticipoPago.TOTAL_PAGADO_ANTICIPO, query = "SELECT SUM(o.montoPagado) FROM AnticipoPago o WHERE o.anticipoPlanPago.anticipo.id=?1 AND o.vigente=true"),
    @NamedQuery(name = AnticipoPago.BUSCAR_NOMINA, query = "SELECT o FROM AnticipoPago o WHERE o.nominaDetalle.nomina.id=?1 AND o.vigente=true"),
    @NamedQuery(name = AnticipoPago.BUSCAR_NOMINA_SERVIDOR, query = "SELECT o FROM AnticipoPago o WHERE o.nominaDetalle.nomina.id=?1 AND o.anticipoPlanPago.anticipo.servidorId=?2 AND o.vigente=true")

})
public class AnticipoPago extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "AnticipoPago.buscarVigente";
    /**
     * Nombre para busqueda de vigentes por id del plan pago.
     */
    public static final String BUSCAR_POR_PLAN_PAGO = "AnticipoPago.buscarPorPlanPago";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR = "AnticipoPago.eliminar";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR_POR_SERVIDOR = "AnticipoPago.eliminarPorServidor";

    /**
     * Suma los pagos realizados de una cuota.
     */
    public static final String SUMAR_PAGOS = "AnticipoPagoDao.sumarPagos";
    
    public static final String TOTAL_PAGADO_ANTICIPO = "AnticipoPagoDao.totalPagadoAnticipo";

    /**
     *
     */
    public static final String BUSCAR_NOMINA = "AnticipoPagoDao.buscarPorNomina";
    /**
     *
     */
    public static final String BUSCAR_NOMINA_SERVIDOR = "AnticipoPagoDao.buscarPorNominaServidor";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a AnticipoPlanPago.
     */
    @JoinColumn(name = "anticipo_planpago_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AnticipoPlanPago anticipoPlanPago;

    /**
     * AnticipoPlanPago id.
     */
    @Column(name = "anticipo_planpago_id")
    private Long anticipoPlanPagoId;

    /**
     * AnticipoPlanPago id.
     */
    @Column(name = "nomina_detalle_id")
    private Long nominaDetalleId;

    /**
     * AnticipoPlanPago id.
     */
    @JoinColumn(name = "nomina_detalle_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NominaDetalle nominaDetalle;

    /**
     * Registra el valor pagado.
     */
    @Column(name = "monto_pagado")
    @NotNull
    private BigDecimal montoPagado;

    /**
     * Constructor.
     */
    public AnticipoPago() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public AnticipoPago(final Long id) {
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
     * @return the anticipoPlanPago
     */
    public AnticipoPlanPago getAnticipoPlanPago() {
        return anticipoPlanPago;
    }

    /**
     * @param anticipoPlanPago the anticipoPlanPago to set
     */
    public void setAnticipoPlanPago(AnticipoPlanPago anticipoPlanPago) {
        this.anticipoPlanPago = anticipoPlanPago;
    }

    /**
     * @return the anticipoPlanPagoId
     */
    public Long getAnticipoPlanPagoId() {
        return anticipoPlanPagoId;
    }

    /**
     * @param anticipoPlanPagoId the anticipoPlanPagoId to set
     */
    public void setAnticipoPlanPagoId(Long anticipoPlanPagoId) {
        this.anticipoPlanPagoId = anticipoPlanPagoId;
    }

    /**
     * @return the montoPagado
     */
    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    /**
     * @param montoPagado the montoPagado to set
     */
    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    /**
     * @return the nominaDetalleId
     */
    public Long getNominaDetalleId() {
        return nominaDetalleId;
    }

    /**
     * @param nominaDetalleId the nominaDetalleId to set
     */
    public void setNominaDetalleId(Long nominaDetalleId) {
        this.nominaDetalleId = nominaDetalleId;
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
}
