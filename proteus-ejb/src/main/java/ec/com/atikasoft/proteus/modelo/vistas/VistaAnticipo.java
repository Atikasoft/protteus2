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
@Table(name = "vista_anticipos", catalog = "sch_proteus")
public class VistaAnticipo implements Serializable {

    /**
     * Version de la clase.
     */
    private static long serialVersionUID = 1L;
    /**
     * Clave primaria.
     */
    @Id
    @Column(name = "anticipo_id")
    private Long anticipoId;
    
       @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;
    /**
     * Campo del estado del anticipo.
     */
    @Column(name = "estado")
    private String estado;
    /**
     * Número de trámite asigando.
     */
    @Column(name = "numero")
    private Long numero;
    /**
     * Periodo inicio -fin del plan de cuentas del anticipo.
     */
    @Column(name = "periodo_inicio")
    private String periodoInicio;
    /**
     * Periodo inicio -fin del plan de cuentas del anticipo.
     */
    @Column(name = "periodo_fin")
    private String periodoFin;
    /**
     * Campo que indica el plazo en meses.
     */
    @Column(name = "plazo_meses")
    private Integer plazoMeses;
    /**
     * Campo del id del servidor.
     */
    @Column(name = "servidor_id")
    private Long servidorId;
    /**
     * Id del Tipo de Anticipo.
     */
    @Column(name = "tipo_anticipo_id")
    private Long tipoAnticipoId;
    /**
     * Campo del valor del anticipo.
     */
    @Column(name = "valor_anticipo")
    private BigDecimal valorAnticipo;
    /**
     * Campo del saldo por pagar del anticipo.
     */
    @Column(name = "saldo_anticipo")
    private BigDecimal saldoAnticipo;
    /**
     * Campor del id del Servidor garante.
     */
    @Column(name = "servidor_garante_id")
    private Long servidorGaranteId;
    /**
     * Nómina con la cual se asocia el anticipo.
     */
    @Column(name = "nomina_id")
    private Long nominaId;
    /**
     * Campo del id de la institución.
     */
    @Column(name = "institucion_id")
    private Long institucionId;
    /**
     * Campo del codigo de la institución.
     */
    @Column(name = "institucion_codigo")
    private String institucionCodigo;
    /**
     * Campo del nombre de la institución.
     */
    @Column(name = "institucion_nombre")
    private String institucionNombre;
    /**
     * Campo del id del ejercicio fiscal.
     */
    @Column(name = "ejercicio_id")
    private Long ejercicioId;
    /**
     * Campo del anio del ejercicio fiscal.
     */
    @Column(name = "ejercicio_anio")
    private String ejercicioAnio;
    /**
     * Campo del id del tipo de Nómina asociada.
     */
    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;
    /**
     * Nombre del tipo de nómina asociada.
     */
    @Column(name = "tipo_nomina_nombre")
    private String tipoNominaDescripcion;
    /**
     * Número de trámite de la Nómina asociada.
     */
    @Column(name = "nomina_numero")
    private Long nominaNumero;
    /**
     * Descripción de la Nómina asociada.
     */
    @Column(name = "nomina_descripcion")
    private String nominaDescripcion;
    /**
     * Estado de la nómina asociada.
     */
    @Column(name = "nomina_estado")
    private String nominaEstado;
    /**
     * Campo id del distributivo detalle.
     */
    @Column(name = "distributivo_detalle_id")
    private Long distributivoDetalleId;
    
    @Column(name = "escala_ocupacional_id")
    private Long escalaOcupacionalId;
    
    @Column(name = "denominacion_puesto_id")
    private Long denominacionPuestoId;
    
    @Column(name = "estado_puesto_id")
    private Long estadoPuestoId;
    
    @Column(name = "ubicacion_geografica_id")
    private Long ubicacionGeograficaId;
    
    @Column(name = "unidad_organizacional_id")
    private Long unidadOrganizacionalId;
    
    @Column(name = "distributivo_id")
    private Long distributivoId;

    @Column(name = "servidor_apellidos_nombres")
    private String servidorApellidosNombres;
    
    @Column(name = "servidor_numero_identificacion")
    private String servidorNumeroIdentificacion;
    
    @Column(name = "servidor_tipo_identificacion")
    private String servidorTipoIdentificacion;
    
    @Column(name = "garante_apellidos_nombres")
    private String garanteApellidosNombres;
    
    @Column(name = "garante_numero_identificacion")
    private String garanteNumeroIdentificacion;
    
    @Column(name = "garante_tipo_identificacion")
    private String garanteTipoIdentificacion;

     @Column(name = "modalidad_laboral_id ")
    private Long modalidadLaboralId ;
     
        /**
     * Especifica la fecha de registro de la solicitud de anticipos.
     */
    @Column(name = "fecha_solicitud_anticipo")
    @Temporal(value = TemporalType.DATE)
    private Date fechaSolicitudAnticipo;
    /**
     * Constructor sin argumentos.
     */
    public VistaAnticipo() {
        super();
    }

      @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

    /**
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Long numero) {
        this.numero = numero;
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
     * @return the tipoAnticipoId
     */
    public Long getTipoAnticipoId() {
        return tipoAnticipoId;
    }

    /**
     * @param tipoAnticipoId the tipoAnticipoId to set
     */
    public void setTipoAnticipoId(Long tipoAnticipoId) {
        this.tipoAnticipoId = tipoAnticipoId;
    }

    /**
     * @return the valorAnticipo
     */
    public BigDecimal getValorAnticipo() {
        return valorAnticipo;
    }

    /**
     * @param valorAnticipo the valorAnticipo to set
     */
    public void setValorAnticipo(BigDecimal valorAnticipo) {
        this.valorAnticipo = valorAnticipo;
    }

    /**
     * @return the servidorGaranteId
     */
    public Long getServidorGaranteId() {
        return servidorGaranteId;
    }

    /**
     * @param servidorGaranteId the servidorGaranteId to set
     */
    public void setServidorGaranteId(Long servidorGaranteId) {
        this.servidorGaranteId = servidorGaranteId;
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
     * @return the ejercicioId
     */
    public Long getEjercicioId() {
        return ejercicioId;
    }

    /**
     * @param ejercicioId the ejercicioId to set
     */
    public void setEjercicioId(Long ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    /**
     * @return the ejercicioAnio
     */
    public String getEjercicioAnio() {
        return ejercicioAnio;
    }

    /**
     * @param ejercicioAnio the ejercicioAnio to set
     */
    public void setEjercicioAnio(String ejercicioAnio) {
        this.ejercicioAnio = ejercicioAnio;
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
     * @return the tipoNominaDescripcion
     */
    public String getTipoNominaDescripcion() {
        return tipoNominaDescripcion;
    }

    /**
     * @param tipoNominaDescripcion the tipoNominaDescripcion to set
     */
    public void setTipoNominaDescripcion(String tipoNominaDescripcion) {
        this.tipoNominaDescripcion = tipoNominaDescripcion;
    }

    /**
     * @return the nominaNumero
     */
    public Long getNominaNumero() {
        return nominaNumero;
    }

    /**
     * @param nominaNumero the nominaNumero to set
     */
    public void setNominaNumero(Long nominaNumero) {
        this.nominaNumero = nominaNumero;
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
     * @return the distributivoDetalleId
     */
    public Long getDistributivoDetalleId() {
        return distributivoDetalleId;
    }

    /**
     * @param distributivoDetalleId the distributivoDetalleId to set
     */
    public void setDistributivoDetalleId(Long distributivoDetalleId) {
        this.distributivoDetalleId = distributivoDetalleId;
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
     * @return the distributivoId
     */
    public Long getDistributivoId() {
        return distributivoId;
    }

    /**
     * @param distributivoId the distributivoId to set
     */
    public void setDistributivoId(Long distributivoId) {
        this.distributivoId = distributivoId;
    }

    /**
     * @return the servidorApellidosNombres
     */
    public String getServidorApellidosNombres() {
        return servidorApellidosNombres;
    }

    /**
     * @param servidorApellidosNombres the servidorApellidosNombres to set
     */
    public void setServidorApellidosNombres(String servidorApellidosNombres) {
        this.servidorApellidosNombres = servidorApellidosNombres;
    }

    /**
     * @return the servidorNumeroIdentificacion
     */
    public String getServidorNumeroIdentificacion() {
        return servidorNumeroIdentificacion;
    }

    /**
     * @param servidorNumeroIdentificacion the servidorNumeroIdentificacion to set
     */
    public void setServidorNumeroIdentificacion(String servidorNumeroIdentificacion) {
        this.servidorNumeroIdentificacion = servidorNumeroIdentificacion;
    }

    /**
     * @return the servidorTipoIdentificacion
     */
    public String getServidorTipoIdentificacion() {
        return servidorTipoIdentificacion;
    }

    /**
     * @param servidorTipoIdentificacion the servidorTipoIdentificacion to set
     */
    public void setServidorTipoIdentificacion(String servidorTipoIdentificacion) {
        this.servidorTipoIdentificacion = servidorTipoIdentificacion;
    }

    /**
     * @return the garanteApellidosNombres
     */
    public String getGaranteApellidosNombres() {
        return garanteApellidosNombres;
    }

    /**
     * @param garanteApellidosNombres the garanteApellidosNombres to set
     */
    public void setGaranteApellidosNombres(String garanteApellidosNombres) {
        this.garanteApellidosNombres = garanteApellidosNombres;
    }

    /**
     * @return the garanteNumeroIdentificacion
     */
    public String getGaranteNumeroIdentificacion() {
        return garanteNumeroIdentificacion;
    }

    /**
     * @param garanteNumeroIdentificacion the garanteNumeroIdentificacion to set
     */
    public void setGaranteNumeroIdentificacion(String garanteNumeroIdentificacion) {
        this.garanteNumeroIdentificacion = garanteNumeroIdentificacion;
    }

    /**
     * @return the garanteTipoIdentificacion
     */
    public String getGaranteTipoIdentificacion() {
        return garanteTipoIdentificacion;
    }

    /**
     * @param garanteTipoIdentificacion the garanteTipoIdentificacion to set
     */
    public void setGaranteTipoIdentificacion(String garanteTipoIdentificacion) {
        this.garanteTipoIdentificacion = garanteTipoIdentificacion;
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
     * @return the fechaSolicitudAnticipo
     */
    public Date getFechaSolicitudAnticipo() {
        return fechaSolicitudAnticipo;
    }

    /**
     * @param fechaSolicitudAnticipo the fechaSolicitudAnticipo to set
     */
    public void setFechaSolicitudAnticipo(Date fechaSolicitudAnticipo) {
        this.fechaSolicitudAnticipo = fechaSolicitudAnticipo;
    }

    public BigDecimal getSaldoAnticipo() {
        return saldoAnticipo;
    }

    public void setSaldoAnticipo(BigDecimal saldoAnticipo) {
        this.saldoAnticipo = saldoAnticipo;
    }
}