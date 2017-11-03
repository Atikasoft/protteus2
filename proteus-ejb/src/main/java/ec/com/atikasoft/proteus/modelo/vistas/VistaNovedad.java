/*
 *  VistaNovedad.java
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
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Persistencia de la vista de anticipos, utilizada para generar consultas.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vista_novedades", catalog = "sch_proteus")
public class VistaNovedad implements Serializable {

    /**
     * Version de la clase.
     *
     */
    private static long serialVersionUID = 1L;

    /**
     * Clave primaria.
     */
    @Id
    @Column(name = "novedad_detalle_id")
    private Long novedadDetalleId;
    /**
     * Campo del id de la institucion.
     */
    @Column(name = "institucion_id")
    private Long institucionId;
    /**
     * Campo del codigo de la institucion.
     */
    @Column(name = "institucion_codigo")
    private String institucionCodigo;
    /**
     * Campo del nombre de la institucion.
     */
    @Column(name = "institucion_nombre")
    private String institucionNombre;
    /**
     * Campo del id del ejericio fiscal.
     */
    @Column(name = "ejercicio_fiscal_id")
    private Long ejercicioFiscalId;

    /**
     * Campo del id del ejericio fiscal.
     */
    @Column(name = "ejercicio_fiscal_anio")
    private String ejercicioFiscalAnio;
    /**
     * Campo del id de la institucion ejericio fiscal.
     */
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;

    /**
     * Campo del id de la nomina asociada.
     */
    @Column(name = "nomina_id")
    private Long nominaId;
    
    /**
     * Campo del id de la NOVEDAD.
     */
    @Column(name = "novedad_id")
    private Long novedadId;

    /**
     * Campo del id del dato adicional asociado.
     */
    @Column(name = "dato_adicional_id")
    private Long datoAdicionalId;
        /**
     * Campo del id del dato adicional asociado.
     */
    @Column(name = "dato_adicional_nombre")
    private String datoAdicionalNombre;
    /**
     * Campo del número de trámite de la novedad.
     */
    @Column(name = "novedad_numero")
    private String novedadNumero;
    /**
     * Campo del numero de trámite de la nómina.
     */
    @Column(name = "nomina_numero")
    private String nominaNumero;
    /**
     * Campo que indica si la novedad ya ha sido pagada.
     */
    @Column(name = "novedad_detalle_pagado")
    private Boolean novedadDetallePagado;

    /**
     * Campo del id del servidor.
     */
    @Column(name = "servidor_id")
    private Long servidorId;
 
    /**
     * Campo valor descontado del detalle de la novedad.
     */
    @Column(name = "novedad_detalle_valor")
    private BigDecimal novedadDetalleValor;
        
    /**
     * Campo descripcion de la nómina asociada.
     */
    @Column(name = "nomina_descripcion")
    private String nominaDescripcion;

    /**
     * Campo descripcion de la nómina asociada.
     */
    @Column(name = "nomina_estado")
    private String nominaEstado;

         /**
     * Especifica la fecha de registro .
     */
    @Column(name = "novedad_fecha_creacion")
    @Temporal(value = TemporalType.DATE)
    private Date novedadFechaCreacion;
    
        /**
     * Campo del id del tipo nomina.
     */
    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;
    
            /**
     * Campo del id del periodo nomina.
     */
    @Column(name = "periodo_nomina_id")
    private Long periodoNominaId;
     /**
     * Campo descripcion de tipo de nomina.
     */
    @Column(name = "tipo_nomina_nombre")
    private String tipoNominaNombre;
      /**
     * Campo descripcion de periodo de nomina.
     */
    @Column(name = "periodo_nomina_nombre")
    private String periodoNominaNombre;
    
    /**
     * Constructor sin argumentos.
     */

    public VistaNovedad() {
        super();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the novedadDetalleId
     */
    public Long getNovedadDetalleId() {
        return novedadDetalleId;
    }

    /**
     * @param novedadDetalleId the novedadDetalleId to set
     */
    public void setNovedadDetalleId(Long novedadDetalleId) {
        this.novedadDetalleId = novedadDetalleId;
    }

    /**
     * @return the institucionId
     */
    public Long getInstitucionId() {
        return institucionId;
    }

    /**
     * @param institucionId the institucionId to set
     */
    public void setInstitucionId(Long institucionId) {
        this.institucionId = institucionId;
    }

    /**
     * @return the institucionCodigo
     */
    public String getInstitucionCodigo() {
        return institucionCodigo;
    }

    /**
     * @param institucionCodigo the institucionCodigo to set
     */
    public void setInstitucionCodigo(String institucionCodigo) {
        this.institucionCodigo = institucionCodigo;
    }

    /**
     * @return the institucionNombre
     */
    public String getInstitucionNombre() {
        return institucionNombre;
    }

    /**
     * @param institucionNombre the institucionNombre to set
     */
    public void setInstitucionNombre(String institucionNombre) {
        this.institucionNombre = institucionNombre;
    }

    /**
     * @return the ejercicioFiscalId
     */
    public Long getEjercicioFiscalId() {
        return ejercicioFiscalId;
    }

    /**
     * @param ejercicioFiscalId the ejercicioFiscalId to set
     */
    public void setEjercicioFiscalId(Long ejercicioFiscalId) {
        this.ejercicioFiscalId = ejercicioFiscalId;
    }

    /**
     * @return the ejercicioFiscalAnio
     */
    public String getEjercicioFiscalAnio() {
        return ejercicioFiscalAnio;
    }

    /**
     * @param ejercicioFiscalAnio the ejercicioFiscalAnio to set
     */
    public void setEjercicioFiscalAnio(String ejercicioFiscalAnio) {
        this.ejercicioFiscalAnio = ejercicioFiscalAnio;
    }

    /**
     * @return the institucionEjercicioFiscalId
     */
    public Long getInstitucionEjercicioFiscalId() {
        return institucionEjercicioFiscalId;
    }

    /**
     * @param institucionEjercicioFiscalId the institucionEjercicioFiscalId to set
     */
    public void setInstitucionEjercicioFiscalId(Long institucionEjercicioFiscalId) {
        this.institucionEjercicioFiscalId = institucionEjercicioFiscalId;
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
     * @return the novedadId
     */
    public Long getNovedadId() {
        return novedadId;
    }

    /**
     * @param novedadId the novedadId to set
     */
    public void setNovedadId(Long novedadId) {
        this.novedadId = novedadId;
    }

    /**
     * @return the datoAdicionalId
     */
    public Long getDatoAdicionalId() {
        return datoAdicionalId;
    }

    /**
     * @param datoAdicionalId the datoAdicionalId to set
     */
    public void setDatoAdicionalId(Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
    }

    /**
     * @return the novedadNumero
     */
    public String getNovedadNumero() {
        return novedadNumero;
    }

    /**
     * @param novedadNumero the novedadNumero to set
     */
    public void setNovedadNumero(String novedadNumero) {
        this.novedadNumero = novedadNumero;
    }

    /**
     * @return the nominaNumero
     */
    public String getNominaNumero() {
        return nominaNumero;
    }

    /**
     * @param nominaNumero the nominaNumero to set
     */
    public void setNominaNumero(String nominaNumero) {
        this.nominaNumero = nominaNumero;
    }

    /**
     * @return the novedadDetallePagado
     */
    public Boolean getNovedadDetallePagado() {
        return novedadDetallePagado;
    }

    /**
     * @param novedadDetallePagado the novedadDetallePagado to set
     */
    public void setNovedadDetallePagado(Boolean novedadDetallePagado) {
        this.novedadDetallePagado = novedadDetallePagado;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the novedadDetalleValor
     */
    public BigDecimal getNovedadDetalleValor() {
        return novedadDetalleValor;
    }

    /**
     * @param novedadDetalleValor the novedadDetalleValor to set
     */
    public void setNovedadDetalleValor(BigDecimal novedadDetalleValor) {
        this.novedadDetalleValor = novedadDetalleValor;
    }
    /**
     * @return the nominaDescripcion
     */
    public String getNominaDescripcion() {
        return nominaDescripcion;
    }

    /**
     * @param nominaDescripcion the nominaDescripcion to set
     */
    public void setNominaDescripcion(String nominaDescripcion) {
        this.nominaDescripcion = nominaDescripcion;
    }

    /**
     * @return the nominaEstado
     */
    public String getNominaEstado() {
        return nominaEstado;
    }

    /**
     * @param nominaEstado the nominaEstado to set
     */
    public void setNominaEstado(String nominaEstado) {
        this.nominaEstado = nominaEstado;
    }

    /**
     * @return the novedadFechaCreacion
     */
    public Date getNovedadFechaCreacion() {
        return novedadFechaCreacion;
    }

    /**
     * @param novedadFechaCreacion the novedadFechaCreacion to set
     */
    public void setNovedadFechaCreacion(Date novedadFechaCreacion) {
        this.novedadFechaCreacion = novedadFechaCreacion;
    }

    /**
     * @return the tipoNominaId
     */
    public Long getTipoNominaId() {
        return tipoNominaId;
    }

    /**
     * @param tipoNominaId the tipoNominaId to set
     */
    public void setTipoNominaId(Long tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
    }

    /**
     * @return the periodoNominaId
     */
    public Long getPeriodoNominaId() {
        return periodoNominaId;
    }

    /**
     * @param periodoNominaId the periodoNominaId to set
     */
    public void setPeriodoNominaId(Long periodoNominaId) {
        this.periodoNominaId = periodoNominaId;
    }

    /**
     * @return the tipoNominaNombre
     */
    public String getTipoNominaNombre() {
        return tipoNominaNombre;
    }

    /**
     * @param tipoNominaNombre the tipoNominaNombre to set
     */
    public void setTipoNominaNombre(String tipoNominaNombre) {
        this.tipoNominaNombre = tipoNominaNombre;
    }

    /**
     * @return the periodoNominaNombre
     */
    public String getPeriodoNominaNombre() {
        return periodoNominaNombre;
    }

    /**
     * @param periodoNominaNombre the periodoNominaNombre to set
     */
    public void setPeriodoNominaNombre(String periodoNominaNombre) {
        this.periodoNominaNombre = periodoNominaNombre;
    }

    /**
     * @return the datoAdicionalNombre
     */
    public String getDatoAdicionalNombre() {
        return datoAdicionalNombre;
    }

    /**
     * @param datoAdicionalNombre the datoAdicionalNombre to set
     */
    public void setDatoAdicionalNombre(String datoAdicionalNombre) {
        this.datoAdicionalNombre = datoAdicionalNombre;
    }

}
