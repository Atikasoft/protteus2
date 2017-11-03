/*
 *  ConsultaAnticipoVO.java
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
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VO para la Busqueda de Anticipos.
 *
 *  @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>>
 */
public class ConsultaNovedadVO implements Serializable {

    /**
     * ****************************************************************************************************************************************************
     */
    
 /**
     * Variable del id de la institucion.
     */
    private Long institucionId;
    /**
     * Variable del codigo de la institucion.
     */
    private String institucionCodigo;
    /**
     * Variable del nombre de la institucion.
     */
    private String institucionNombre;
    /**
     * Variable del id del ejercicio fiscal.
     */
    private Long ejercicioFiscalId;

    /**
     * Variable del id del ejericio fiscal.
     */
    private String ejercicioFiscalAnio;
    /**
     * Variable del id de la institucion ejericio fiscal.
     */
    private Long institucionEjercicioFiscalId;

    /**
     * Variable del id de la nomina asociada.
     */
    private Long nominaId;

    /**
     * Variable del id de la NOVEDAD.
     */
     private Long novedadId;

    /**
     * Variable del id del dato adicional asociado.
     */
    private Long datoAdicionalId;
    /**
     * Variable del número de trámite de la novedad.
     */
    private String novedadNumero;
    /**
     * Variable del numero de trámite de la nómina.
     */
    private String nominaNumero;
    /**
     * Variable que indica si la novedad ya ha sido pagada.
     */
    private Boolean novedadDetallePagado;

    /**
     * Variable del id del servidor.
     */
    private Long servidorId;
    private String nombresServidor;
    /**
     * Variable valor descontado del detalle de la novedad.
     */
    private BigDecimal novedadDetalleValorDesde;
    private BigDecimal novedadDetalleValorHasta;

    /**
     * Variable descripcion de la nómina asociada.
     */
     private String nominaDescripcion;

    /**
     * Variable descripcion de la nómina asociada.
     */
    private String nominaEstado;

    /**
     * Variables para busqueda por fecha de creacion.
     */
    private Date novedadFechaCreacionDesde;
    private Date novedadFechaCreacionHasta;
    
    /**
     * Variables para busqueda por campos de nomina.
     */
    private Long periodoNominaId;
    private Long tipoNominaId;
    
    /**
     * Paginacion.
     */
    private Integer inicioConsulta;
    private Integer finConsulta;
    /**
     * Constructor por defecto.
     */
    public ConsultaNovedadVO() {
        super();
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
     * @return the novedadDetalleValorDesde
     */
    public BigDecimal getNovedadDetalleValorDesde() {
        return novedadDetalleValorDesde;
    }

    /**
     * @param novedadDetalleValorDesde the novedadDetalleValorDesde to set
     */
    public void setNovedadDetalleValorDesde(BigDecimal novedadDetalleValorDesde) {
        this.novedadDetalleValorDesde = novedadDetalleValorDesde;
    }

    /**
     * @return the novedadDetalleValorHasta
     */
    public BigDecimal getNovedadDetalleValorHasta() {
        return novedadDetalleValorHasta;
    }

    /**
     * @param novedadDetalleValorHasta the novedadDetalleValorHasta to set
     */
    public void setNovedadDetalleValorHasta(BigDecimal novedadDetalleValorHasta) {
        this.novedadDetalleValorHasta = novedadDetalleValorHasta;
    }
    /**
     * @return the novedadFechaCreacionDesde
     */
    public Date getNovedadFechaCreacionDesde() {
        return novedadFechaCreacionDesde;
    }

    /**
     * @param novedadFechaCreacionDesde the novedadFechaCreacionDesde to set
     */
    public void setNovedadFechaCreacionDesde(Date novedadFechaCreacionDesde) {
        this.novedadFechaCreacionDesde = novedadFechaCreacionDesde;
    }

    /**
     * @return the novedadFechaCreacionHasta
     */
    public Date getNovedadFechaCreacionHasta() {
        return novedadFechaCreacionHasta;
    }

    /**
     * @param novedadFechaCreacionHasta the novedadFechaCreacionHasta to set
     */
    public void setNovedadFechaCreacionHasta(Date novedadFechaCreacionHasta) {
        this.novedadFechaCreacionHasta = novedadFechaCreacionHasta;
    }

    /**
     * @return the inicioConsulta
     */
    public Integer getInicioConsulta() {
        return inicioConsulta;
    }

    /**
     * @param inicioConsulta the inicioConsulta to set
     */
    public void setInicioConsulta(Integer inicioConsulta) {
        this.inicioConsulta = inicioConsulta;
    }

    /**
     * @return the finConsulta
     */
    public Integer getFinConsulta() {
        return finConsulta;
    }

    /**
     * @param finConsulta the finConsulta to set
     */
    public void setFinConsulta(Integer finConsulta) {
        this.finConsulta = finConsulta;
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
     * @return the nombresServidor
     */
    public String getNombresServidor() {
        return nombresServidor;
    }

    /**
     * @param nombresServidor the nombresServidor to set
     */
    public void setNombresServidor(String nombresServidor) {
        this.nombresServidor = nombresServidor;
    }


   }
