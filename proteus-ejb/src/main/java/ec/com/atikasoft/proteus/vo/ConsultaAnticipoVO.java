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
 *  @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>c>
 */
public class ConsultaAnticipoVO implements Serializable {

    /**
     * ****************************************************************************************************************************************************
     */
    /**
     * Variable para el Ejercicio Fiscal
     */
    private Long ejercicioFiscalId;
    
      /**
     * Variable para el Ejercicio Fiscal
     */
    private Long institucionEjercicioFiscalId;

    /**
     * Variable para la Nomina
     */
    private Long nominaId;

    /**
     * Variable para el Tipo Nominas
     */
    private Long tipoNomimaId;

    /**
     * Variable para el Tipo de Documento del servidor.
     */
    private String tipoDocumentoServidor;

    /**
     * Variable para el Número de Documento.
     */
    private String numeroDocumentoServidor;
      /**
     * Variable para el Nombre del Servidor.
     */
    private String apellidosNombresServidor;

    private String estadoAnticipo;
    
    private Long anticipoId;
    
    private String numeroAnticipo;
    
    private String periodoInicio;
    private String periodoFin;
    
    private Integer plazoMeses;
    
    private Long servidorId;
    private Long garanteId;
    
    private BigDecimal valorDesde;
    private BigDecimal valorHasta;
    
    private Long TipoAnticipoId;
    
  
     private String estadoNomina;
     
     
    /**
     * Variable para el estado del puesto.
     */
    private Long  estadoPuestoId;
      /**
     * 
     */
    private Long  denominacionPuestoId;
      /**
     * Variables para busqueda por escala, modalidad y unidad organizacional.
     */
    private Long  escalaOcupacionalId;
    private String  escalaOcupacionalNombre;
    private Long  ubicacionGeograficaId;
    private Long  unidadOrganizacionalId;
    private String  unidadOrganizacionalNombre;
    private Long modalidadLaboralId ;
    
    /**
     * Variables para ejecutar busqueda por fecha de creación de la solicitud.
     */
    private Date fechaSolicitudDesde;
    private Date fechaSolicitudHasta;
    
    /**
     * Constructor por defecto.
     */
    public ConsultaAnticipoVO() {
        super();
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
     * @return the tipoNomimaId
     */
    public Long getTipoNomimaId() {
        return tipoNomimaId;
    }

    /**
     * @param tipoNomimaId the tipoNomimaId to set
     */
    public void setTipoNomimaId(Long tipoNomimaId) {
        this.tipoNomimaId = tipoNomimaId;
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
     * @return the tipoDocumentoServidor
     */
    public String getTipoDocumentoServidor() {
        return tipoDocumentoServidor;
    }

    /**
     * @param tipoDocumentoServidor the tipoDocumentoServidor to set
     */
    public void setTipoDocumentoServidor(String tipoDocumentoServidor) {
        this.tipoDocumentoServidor = tipoDocumentoServidor;
    }

    /**
     * @return the numeroDocumentoServidor
     */
    public String getNumeroDocumentoServidor() {
        return numeroDocumentoServidor;
    }

    /**
     * @param numeroDocumentoServidor the numeroDocumentoServidor to set
     */
    public void setNumeroDocumentoServidor(String numeroDocumentoServidor) {
        this.numeroDocumentoServidor = numeroDocumentoServidor;
    }

    /**
     * @return the apellidosNombresServidor
     */
    public String getApellidosNombresServidor() {
        return apellidosNombresServidor;
    }

    /**
     * @param apellidosNombresServidor the apellidosNombresServidor to set
     */
    public void setApellidosNombresServidor(String apellidosNombresServidor) {
        this.apellidosNombresServidor = apellidosNombresServidor;
    }

    /**
     * @return the estadoAnticipo
     */
    public String getEstadoAnticipo() {
        return estadoAnticipo;
    }

    /**
     * @param estadoAnticipo the estadoAnticipo to set
     */
    public void setEstadoAnticipo(String estadoAnticipo) {
        this.estadoAnticipo = estadoAnticipo;
    }
 

    /**
     * @return the numeroAnticipo
     */
    public String getNumeroAnticipo() {
        return numeroAnticipo;
    }

    /**
     * @param numeroAnticipo the numeroAnticipo to set
     */
    public void setNumeroAnticipo(String numeroAnticipo) {
        this.numeroAnticipo = numeroAnticipo;
    }

    /**
     * @return the periodoInicio
     */
    public String getPeriodoInicio() {
        return periodoInicio;
    }

    /**
     * @param periodoInicio the periodoInicio to set
     */
    public void setPeriodoInicio(String periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    /**
     * @return the periodoFin
     */
    public String getPeriodoFin() {
        return periodoFin;
    }

    /**
     * @param periodoFin the periodoFin to set
     */
    public void setPeriodoFin(String periodoFin) {
        this.periodoFin = periodoFin;
    }

    /**
     * @return the plazoMeses
     */
    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    /**
     * @param plazoMeses the plazoMeses to set
     */
    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
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
     * @return the garanteId
     */
    public Long getGaranteId() {
        return garanteId;
    }

    /**
     * @param garanteId the garanteId to set
     */
    public void setGaranteId(Long garanteId) {
        this.garanteId = garanteId;
    }

    /**
     * @return the valorDesde
     */
    public BigDecimal getValorDesde() {
        return valorDesde;
    }

    /**
     * @param valorDesde the valorDesde to set
     */
    public void setValorDesde(BigDecimal valorDesde) {
        this.valorDesde = valorDesde;
    }

    /**
     * @return the valorHasta
     */
    public BigDecimal getValorHasta() {
        return valorHasta;
    }

    /**
     * @param valorHasta the valorHasta to set
     */
    public void setValorHasta(BigDecimal valorHasta) {
        this.valorHasta = valorHasta;
    }

    /**
     * @return the TipoAnticipoId
     */
    public Long getTipoAnticipoId() {
        return TipoAnticipoId;
    }

    /**
     * @param TipoAnticipoId the TipoAnticipoId to set
     */
    public void setTipoAnticipoId(Long TipoAnticipoId) {
        this.TipoAnticipoId = TipoAnticipoId;
    }

    /**
     * @return the estadoNomina
     */
    public String getEstadoNomina() {
        return estadoNomina;
    }

    /**
     * @param estadoNomina the estadoNomina to set
     */
    public void setEstadoNomina(String estadoNomina) {
        this.estadoNomina = estadoNomina;
    }

    /**
     * @return the estadoPuestoId
     */
    public Long getEstadoPuestoId() {
        return estadoPuestoId;
    }

    /**
     * @param estadoPuestoId the estadoPuestoId to set
     */
    public void setEstadoPuestoId(Long estadoPuestoId) {
        this.estadoPuestoId = estadoPuestoId;
    }

    /**
     * @return the denominacionPuestoId
     */
    public Long getDenominacionPuestoId() {
        return denominacionPuestoId;
    }

    /**
     * @param denominacionPuestoId the denominacionPuestoId to set
     */
    public void setDenominacionPuestoId(Long denominacionPuestoId) {
        this.denominacionPuestoId = denominacionPuestoId;
    }

    /**
     * @return the escalaOcupacionalId
     */
    public Long getEscalaOcupacionalId() {
        return escalaOcupacionalId;
    }

    /**
     * @param escalaOcupacionalId the escalaOcupacionalId to set
     */
    public void setEscalaOcupacionalId(Long escalaOcupacionalId) {
        this.escalaOcupacionalId = escalaOcupacionalId;
    }

    /**
     * @return the ubicacionGeograficaId
     */
    public Long getUbicacionGeograficaId() {
        return ubicacionGeograficaId;
    }

    /**
     * @param ubicacionGeograficaId the ubicacionGeograficaId to set
     */
    public void setUbicacionGeograficaId(Long ubicacionGeograficaId) {
        this.ubicacionGeograficaId = ubicacionGeograficaId;
    }

    /**
     * @return the unidadOrganizacionalId
     */
    public Long getUnidadOrganizacionalId() {
        return unidadOrganizacionalId;
    }

    /**
     * @param unidadOrganizacionalId the unidadOrganizacionalId to set
     */
    public void setUnidadOrganizacionalId(Long unidadOrganizacionalId) {
        this.unidadOrganizacionalId = unidadOrganizacionalId;
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
     * @return the modalidadLaboralId
     */
    public Long getModalidadLaboralId() {
        return modalidadLaboralId;
    }

    /**
     * @param modalidadLaboralId the modalidadLaboralId to set
     */
    public void setModalidadLaboralId(Long modalidadLaboralId) {
        this.modalidadLaboralId = modalidadLaboralId;
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
     * @return the unidadOrganizacionalNombre
     */
    public String getUnidadOrganizacionalNombre() {
        return unidadOrganizacionalNombre;
    }

    /**
     * @param unidadOrganizacionalNombre the unidadOrganizacionalNombre to set
     */
    public void setUnidadOrganizacionalNombre(String unidadOrganizacionalNombre) {
        this.unidadOrganizacionalNombre = unidadOrganizacionalNombre;
    }

    /**
     * @return the escalaOcupacionalNombre
     */
    public String getEscalaOcupacionalNombre() {
        return escalaOcupacionalNombre;
    }

    /**
     * @param escalaOcupacionalNombre the escalaOcupacionalNombre to set
     */
    public void setEscalaOcupacionalNombre(String escalaOcupacionalNombre) {
        this.escalaOcupacionalNombre = escalaOcupacionalNombre;
    }

    /**
     * @return the fechaSolicitudDesde
     */
    public Date getFechaSolicitudDesde() {
        return fechaSolicitudDesde;
    }

    /**
     * @param fechaSolicitudDesde the fechaSolicitudDesde to set
     */
    public void setFechaSolicitudDesde(Date fechaSolicitudDesde) {
        this.fechaSolicitudDesde = fechaSolicitudDesde;
    }

    /**
     * @return the fechaSolicitudHasta
     */
    public Date getFechaSolicitudHasta() {
        return fechaSolicitudHasta;
    }

    /**
     * @param fechaSolicitudHasta the fechaSolicitudHasta to set
     */
    public void setFechaSolicitudHasta(Date fechaSolicitudHasta) {
        this.fechaSolicitudHasta = fechaSolicitudHasta;
    }
     
}
