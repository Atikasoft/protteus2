/*
 *  VistaAnticipo.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Persistencia de la vista de nominas para el ir.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vista_nominas_ir", catalog = "sch_proteus")
public class NominaDetalleIR implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Column(name = "ejercicio_fiscal")
    private String ejercicioFiscal;

    /**
     *
     */
    @Id
    @Column(name = "ejercicio_fiscal_id")
    private Long ejercicioFiscalId;

    /**
     *
     */
    @Column(name = "periodo")
    private Long periodo;

    /**
     *
     */
    @Id
    @Column(name = "periodo_id")
    private Long periodoId;

    /**
     *
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     *
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     *
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * Campo del id del servidor.
     */
    @Id
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     *
     */
    @Column(name = "valor_ingresos")
    private BigDecimal valorIngresos;

    /**
     *
     */
    @Column(name = "valor_descuentos")
    private BigDecimal valorDescuentos;

    /**
     * Constructor sin argumentos.
     */
    public NominaDetalleIR() {
        super();
    }

    /**
     * @return the ejercicioFiscal
     */
    public String getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @return the ejercicioFiscalId
     */
    public Long getEjercicioFiscalId() {
        return ejercicioFiscalId;
    }

    /**
     * @return the periodo
     */
    public Long getPeriodo() {
        return periodo;
    }

    /**
     * @return the periodoId
     */
    public Long getPeriodoId() {
        return periodoId;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @return the valorIngresos
     */
    public BigDecimal getValorIngresos() {
        return valorIngresos;
    }

    /**
     * @return the valorDescuentos
     */
    public BigDecimal getValorDescuentos() {
        return valorDescuentos;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(final String ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscalId the ejercicioFiscalId to set
     */
    public void setEjercicioFiscalId(final Long ejercicioFiscalId) {
        this.ejercicioFiscalId = ejercicioFiscalId;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(final Long periodo) {
        this.periodo = periodo;
    }

    /**
     * @param periodoId the periodoId to set
     */
    public void setPeriodoId(final Long periodoId) {
        this.periodoId = periodoId;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(final Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @param valorIngresos the valorIngresos to set
     */
    public void setValorIngresos(final BigDecimal valorIngresos) {
        this.valorIngresos = valorIngresos;
    }

    /**
     * @param valorDescuentos the valorDescuentos to set
     */
    public void setValorDescuentos(final BigDecimal valorDescuentos) {
        this.valorDescuentos = valorDescuentos;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}