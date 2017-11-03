/*
 *  AnticipoPlanPago.java
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
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "anticipos_planpago", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = AnticipoPlanPago.BUSCAR_VIGENTES, query = "SELECT a FROM AnticipoPlanPago a where a.vigente=true order by a.anio, a.mes"),
    @NamedQuery(name = AnticipoPlanPago.BUSCAR_POR_ANTICIPO, query = "SELECT a FROM AnticipoPlanPago a where a.vigente=true and a.anticipoId=?1 order by a.anio, a.mes")
})
public class AnticipoPlanPago extends EntidadBasica {

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "AnticipoPlanPago.buscarVigente";
    /**
     * Nombre para busqueda de vigentes por id de anticipo.
     */
    public static final String BUSCAR_POR_ANTICIPO = "AnticipoPlanPago.buscarPorAnticipo";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a Anticipo.
     */
    @JoinColumn(name = "anticipo_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Anticipo anticipo;

    /**
     * Anticipo id.
     */
    @Column(name = "anticipo_id")
    private Long anticipoId;

    /**
     * NOmina id.
     */
    @Column(name = "nomina_id")
    private Long nominaId;

    /**
     * Campo estado:
     * <P>
     * endiente, p<A>gado.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_pago")
    private String estadoPago;

    /**
     * Registra el valor pagado en la Nómina.
     */
    @Column(name = "monto_nomina")
    @NotNull
    private BigDecimal montoNomina;

    /**
     * Registra el valor de la cuota.
     */
    @Column(name = "monto")
    @NotNull
    private BigDecimal monto;

    /**
     * Año que toca cancelar la cuota.
     */
    @Column(name = "anio")
    @NotNull
    private Integer anio;

    /**
     * Mes que toca cancelar la cuota..
     */
    @Column(name = "mes")
    @NotNull
    private Integer mes;

    /**
     * Registra el ordinal de la cuota.
     */
    @Column(name = "dividendo")
    @NotNull
    private Integer dividendo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_maxima_pago")
    private Date fechaMaximaPago;

    @Transient
    private String mesPalabras;

    @Transient
    private BigDecimal valorAPagar;

    /**
     * Constructor.
     */
    public AnticipoPlanPago() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public AnticipoPlanPago(final Long id) {
        super();
        this.id = id;
    }

    @PostLoad
    public void postLoad() {
        if (mes != null) {
            mesPalabras = UtilFechas.obtenerNombreMes(mes);
        }
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
     * @return the anticipo
     */
    public Anticipo getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(Anticipo anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the anticipoId
     */
    public Long getAnticipoId() {
        return anticipoId;
    }

    /**
     * @param anticipoId the anticipoId to set
     */
    public void setAnticipoId(Long anticipoId) {
        this.anticipoId = anticipoId;
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @return the estadoPago
     */
    public String getEstadoPago() {
        return estadoPago;
    }

    /**
     * @param estadoPago the estadoPago to set
     */
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    /**
     * @return the montoNomina
     */
    public BigDecimal getMontoNomina() {
        return montoNomina;
    }

    /**
     * @param montoNomina the montoNomina to set
     */
    public void setMontoNomina(BigDecimal montoNomina) {
        this.montoNomina = montoNomina;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the anio
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * @return the mes
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     * @return the dividendo
     */
    public Integer getDividendo() {
        return dividendo;
    }

    /**
     * @param dividendo the dividendo to set
     */
    public void setDividendo(Integer dividendo) {
        this.dividendo = dividendo;
    }

    /**
     * @return the mesPalabras
     */
    public String getMesPalabras() {
        return mesPalabras;
    }

    /**
     * @param mesPalabras the mesPalabras to set
     */
    public void setMesPalabras(String mesPalabras) {
        this.mesPalabras = mesPalabras;
    }

    /**
     * @return the fechaMaximaPago
     */
    public Date getFechaMaximaPago() {
        return fechaMaximaPago;
    }

    /**
     * @param fechaMaximaPago the fechaMaximaPago to set
     */
    public void setFechaMaximaPago(Date fechaMaximaPago) {
        this.fechaMaximaPago = fechaMaximaPago;
    }

    /**
     * @return the valorAPagar
     */
    public BigDecimal getValorAPagar() {
        return valorAPagar;
    }

    /**
     * @param valorAPagar the valorAPagar to set
     */
    public void setValorAPagar(BigDecimal valorAPagar) {
        this.valorAPagar = valorAPagar;
    }
}
