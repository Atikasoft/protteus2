/*
 *  BusquedaPuestoVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.enums.TiposCertificacionesPresupuestariasEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VO para la Busqueda de Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class BusquedaPuestoVO implements Serializable {

    /**
     *
     */
    private Long intitucionEjercicioFiscalId;

    /**
     *
     */
    private Long distributivoDetalleId;

    /**
     * Campo para el filtro.
     */
    private String regimenLaboralNombre;

    /**
     *
     */
    private Long regimenLaboralId;

    /**
     * Campo para el filtro.
     */
    private String nivelOcupacionalNombre;

    /**
     * Campo para el filtro.
     */
    private Long nivelOcupacionalId;

    /**
     * Campo para el filtro.
     */
    private String escalaOcupacionalNombre;

    /**
     * Campo para el filtro.
     */
    private Long escalaOcupacionalId;

    /**
     * Campo para el filtro.
     */
    private String modalidadLaboralNombre;

    /**
     * Campo para el filtro.
     */
    private Long modalidadLaboralId;

    /**
     *
     */
    private Long denominacionPuestoId;

    /**
     * Campo para el filtro.
     */
    private String paisNombre;

    /**
     * Campo para el filtro.
     */
    private Long paisId;

    /**
     * Campo para el filtro.
     */
    private String provinciaNombre;

    /**
     * Campo para el filtro.
     */
    private Long provinciaId;

    /**
     * Campo para el filtro.
     */
    private String cantonNombre;

    /**
     * Campo para el filtro.
     */
    private Long cantonId;

    /**
     * Campo para el filtro.
     */
    private String parroquiaNombre;

    /**
     * Campo para el filtro.
     */
    private Long parroquiaId;

    /**
     * Campo para el filtro.
     */
    private String ubicacionGeograficaNombre;

    /**
     * Campo para el filtro.
     */
    private Long ubicacionGeograficaId;

    /**
     * Indica el tipo de ubiacion geografica.
     */
    private String ubicacionGeograficaTipo;

    /**
     * Campo para el filtro.
     */
    private String unidadAdministrativaNombre;

    /**
     * Campo para el filtro.
     */
    private Long unidadAdministrativaId;

    /**
     *
     */
    private BigDecimal rmu;

    /**
     *
     */
    private BigDecimal rmuSobrevalorado;

    /**
     *
     */
    private BigDecimal sueldoBasico;

    /**
     *
     */
    private Date fechaInicio;

    /**
     *
     */
    private Date fechaFin;

    /**
     * Campo para el filtro.
     */
    private String partidaIndividual;

    /**
     * Campo para el filtro.
     */
    private Long estadoPuestoId;
    
    /**
     * Campo para el filtro.
     */
    private String estadoPuestoCodigo;
    
    /**
     * Campo para el filtro.
     */
    private Long estadoAdmPuestoRegLabId;

    /**
     * Campo para el filtro.
     */
    private Long estadoServidorId;
    
    /**
     * Campo para el filtro.
     */
    private String estadoServidorCodigo;

    /**
     * Campo para el filtro.
     */
    private String nombreServidor;

    /**
     * Campo para el filtro.
     */
    private String tipoDocumentoServidor;

    /**
     * Campo para el filtro.
     */
    private String numeroDocumentoServidor;

    /**
     * Campo para el filtro, para garantes anticipos.
     */
    private String tipoModalidad;

    /**
     * codigo puesto.
     */
    private Long codigoPuesto;

    /**
     * Variables para la paginacion.
     */
    private Integer inicioConsulta;
    private Integer finConsulta;

    /**
     * Campo para el filtro, para ver si el puesto del distributivo esta vacante
     * o no, si es null se considera vacante.
     */
    private Boolean puestoVacante;

    /**
     * tipo de certificacion presupuestaria.
     */
    private TiposCertificacionesPresupuestariasEnum tipoCertificacionPresupuestaria;
    /**
     * certificacion presupuestaria.
     */
    private String certificacionPresupuestaria;
    /**
     * Variable que indica si debe o no timbrar un servidor, utilizado en
     * Asistencia.
     */
    private Boolean obligadoTimbrar;

    /**
     *
     */
    private Boolean renovacionComisionServicio;

    /**
     * Constructor por defecto.
     */
    public BusquedaPuestoVO() {
        super();
    }

    /**
     * @return the regimenLaboralNombre
     */
    public String getRegimenLaboralNombre() {
        return regimenLaboralNombre;
    }

    /**
     * @return the regimenLaboralId
     */
    public Long getRegimenLaboralId() {
        return regimenLaboralId;
    }

    /**
     * @return the nivelOcupacionalNombre
     */
    public String getNivelOcupacionalNombre() {
        return nivelOcupacionalNombre;
    }

    /**
     * @return the nivelOcupacionalId
     */
    public Long getNivelOcupacionalId() {
        return nivelOcupacionalId;
    }

    /**
     * @return the escalaOcupacionalNombre
     */
    public String getEscalaOcupacionalNombre() {
        return escalaOcupacionalNombre;
    }

    /**
     * @return the escalaOcupacionalId
     */
    public Long getEscalaOcupacionalId() {
        return escalaOcupacionalId;
    }

    /**
     * @return the modalidadLaboralNombre
     */
    public String getModalidadLaboralNombre() {
        return modalidadLaboralNombre;
    }

    /**
     * @return the modalidadLaboralId
     */
    public Long getModalidadLaboralId() {
        return modalidadLaboralId;
    }

    /**
     * @return the paisNombre
     */
    public String getPaisNombre() {
        return paisNombre;
    }

    /**
     * @return the paisId
     */
    public Long getPaisId() {
        return paisId;
    }

    /**
     * @return the provinciaNombre
     */
    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    /**
     * @return the provinciaId
     */
    public Long getProvinciaId() {
        return provinciaId;
    }

    /**
     * @return the cantonNombre
     */
    public String getCantonNombre() {
        return cantonNombre;
    }

    /**
     * @return the cantonId
     */
    public Long getCantonId() {
        return cantonId;
    }

    /**
     * @return the parroquiaNombre
     */
    public String getParroquiaNombre() {
        return parroquiaNombre;
    }

    /**
     * @return the parroquiaId
     */
    public Long getParroquiaId() {
        return parroquiaId;
    }

    /**
     * @return the ubicacionGeograficaNombre
     */
    public String getUbicacionGeograficaNombre() {
        return ubicacionGeograficaNombre;
    }

    /**
     * @return the ubicacionGeograficaId
     */
    public Long getUbicacionGeograficaId() {
        return ubicacionGeograficaId;
    }

    /**
     * @return the unidadAdministrativaNombre
     */
    public String getUnidadAdministrativaNombre() {
        return unidadAdministrativaNombre;
    }

    /**
     * @return the unidadAdministrativaId
     */
    public Long getUnidadAdministrativaId() {
        return unidadAdministrativaId;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @return the rmuSobrevalorado
     */
    public BigDecimal getRmuSobrevalorado() {
        return rmuSobrevalorado;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @return the tipoDocumentoServidor
     */
    public String getTipoDocumentoServidor() {
        return tipoDocumentoServidor;
    }

    /**
     * @return the numeroDocumentoServidor
     */
    public String getNumeroDocumentoServidor() {
        return numeroDocumentoServidor;
    }

    /**
     * @param regimenLaboralNombre the regimenLaboralNombre to set
     */
    public void setRegimenLaboralNombre(final String regimenLaboralNombre) {
        this.regimenLaboralNombre = regimenLaboralNombre;
    }

    /**
     * @param regimenLaboralId the regimenLaboralId to set
     */
    public void setRegimenLaboralId(final Long regimenLaboralId) {
        this.regimenLaboralId = regimenLaboralId;
    }

    /**
     * @param nivelOcupacionalNombre the nivelOcupacionalNombre to set
     */
    public void setNivelOcupacionalNombre(final String nivelOcupacionalNombre) {
        this.nivelOcupacionalNombre = nivelOcupacionalNombre;
    }

    /**
     * @param nivelOcupacionalId the nivelOcupacionalId to set
     */
    public void setNivelOcupacionalId(final Long nivelOcupacionalId) {
        this.nivelOcupacionalId = nivelOcupacionalId;
    }

    /**
     * @param escalaOcupacionalNombre the escalaOcupacionalNombre to set
     */
    public void setEscalaOcupacionalNombre(final String escalaOcupacionalNombre) {
        this.escalaOcupacionalNombre = escalaOcupacionalNombre;
    }

    /**
     * @param escalaOcupacionalId the escalaOcupacionalId to set
     */
    public void setEscalaOcupacionalId(final Long escalaOcupacionalId) {
        this.escalaOcupacionalId = escalaOcupacionalId;
    }

    /**
     * @param modalidadLaboralNombre the modalidadLaboralNombre to set
     */
    public void setModalidadLaboralNombre(final String modalidadLaboralNombre) {
        this.modalidadLaboralNombre = modalidadLaboralNombre;
    }

    /**
     * @param modalidadLaboralId the modalidadLaboralId to set
     */
    public void setModalidadLaboralId(final Long modalidadLaboralId) {
        this.modalidadLaboralId = modalidadLaboralId;
    }

    /**
     * @param paisNombre the paisNombre to set
     */
    public void setPaisNombre(final String paisNombre) {
        this.paisNombre = paisNombre;
    }

    /**
     * @param paisId the paisId to set
     */
    public void setPaisId(final Long paisId) {
        this.paisId = paisId;
    }

    /**
     * @param provinciaNombre the provinciaNombre to set
     */
    public void setProvinciaNombre(final String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    /**
     * @param provinciaId the provinciaId to set
     */
    public void setProvinciaId(final Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    /**
     * @param cantonNombre the cantonNombre to set
     */
    public void setCantonNombre(final String cantonNombre) {
        this.cantonNombre = cantonNombre;
    }

    /**
     * @param cantonId the cantonId to set
     */
    public void setCantonId(final Long cantonId) {
        this.cantonId = cantonId;
    }

    /**
     * @param parroquiaNombre the parroquiaNombre to set
     */
    public void setParroquiaNombre(final String parroquiaNombre) {
        this.parroquiaNombre = parroquiaNombre;
    }

    /**
     * @param parroquiaId the parroquiaId to set
     */
    public void setParroquiaId(final Long parroquiaId) {
        this.parroquiaId = parroquiaId;
    }

    /**
     * @param ubicacionGeograficaNombre the ubicacionGeograficaNombre to set
     */
    public void setUbicacionGeograficaNombre(final String ubicacionGeograficaNombre) {
        this.ubicacionGeograficaNombre = ubicacionGeograficaNombre;
    }

    /**
     * @param ubicacionGeograficaId the ubicacionGeograficaId to set
     */
    public void setUbicacionGeograficaId(final Long ubicacionGeograficaId) {
        this.ubicacionGeograficaId = ubicacionGeograficaId;
    }

    /**
     * @param unidadAdministrativaNombre the unidadAdministrativaNombre to set
     */
    public void setUnidadAdministrativaNombre(final String unidadAdministrativaNombre) {
        this.unidadAdministrativaNombre = unidadAdministrativaNombre;
    }

    /**
     * @param unidadAdministrativaId the unidadAdministrativaId to set
     */
    public void setUnidadAdministrativaId(final Long unidadAdministrativaId) {
        this.unidadAdministrativaId = unidadAdministrativaId;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @param rmuSobrevalorado the rmuSobrevalorado to set
     */
    public void setRmuSobrevalorado(final BigDecimal rmuSobrevalorado) {
        this.rmuSobrevalorado = rmuSobrevalorado;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(final String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(final String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * @param tipoDocumentoServidor the tipoDocumentoServidor to set
     */
    public void setTipoDocumentoServidor(final String tipoDocumentoServidor) {
        this.tipoDocumentoServidor = tipoDocumentoServidor;
    }

    /**
     * @param numeroDocumentoServidor the numeroDocumentoServidor to set
     */
    public void setNumeroDocumentoServidor(final String numeroDocumentoServidor) {
        this.numeroDocumentoServidor = numeroDocumentoServidor;
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
    public void setDenominacionPuestoId(final Long denominacionPuestoId) {
        this.denominacionPuestoId = denominacionPuestoId;
    }

    /**
     * @return the sueldoBasico
     */
    public BigDecimal getSueldoBasico() {
        return sueldoBasico;
    }

    /**
     * @param sueldoBasico the sueldoBasico to set
     */
    public void setSueldoBasico(final BigDecimal sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    /**
     * @return the estadoPuestoId
     */
    public Long getEstadoPuestoId() {
        return estadoPuestoId;
    }

    /**
     * @return the estadoServidorId
     */
    public Long getEstadoServidorId() {
        return estadoServidorId;
    }

    /**
     * @param estadoPuestoId the estadoPuestoId to set
     */
    public void setEstadoPuestoId(final Long estadoPuestoId) {
        this.estadoPuestoId = estadoPuestoId;
    }

    /**
     * 
     * @return 
     */
    public String getEstadoPuestoCodigo() {
        return estadoPuestoCodigo;
    }

    /**
     * 
     * @param estadoPuestoCodigo 
     */
    public void setEstadoPuestoCodigo(String estadoPuestoCodigo) {
        this.estadoPuestoCodigo = estadoPuestoCodigo;
    }

    /**
     * 
     * @return 
     */
    public Long getEstadoAdmPuestoRegLabId() {
        return estadoAdmPuestoRegLabId;
    }

    /**'
     * 
     * @param estadoAdmPuestoRegLabId 
     */
    public void setEstadoAdmPuestoRegLabId(Long estadoAdmPuestoRegLabId) {
        this.estadoAdmPuestoRegLabId = estadoAdmPuestoRegLabId;
    }

    /**
     * @param estadoServidorId the estadoServidorId to set
     */
    public void setEstadoServidorId(final Long estadoServidorId) {
        this.estadoServidorId = estadoServidorId;
    }

    /**
     * 
     * @return 
     */
    public String getEstadoServidorCodigo() {
        return estadoServidorCodigo;
    }

    /**
     * 
     * @param estadoServidorCodigo 
     */
    public void setEstadoServidorCodigo(String estadoServidorCodigo) {
        this.estadoServidorCodigo = estadoServidorCodigo;
    }

    /**
     * @return the intitucionEjercicioFiscalId
     */
    public Long getIntitucionEjercicioFiscalId() {
        return intitucionEjercicioFiscalId;
    }

    /**
     * @param intitucionEjercicioFiscalId the intitucionEjercicioFiscalId to set
     */
    public void setIntitucionEjercicioFiscalId(final Long intitucionEjercicioFiscalId) {
        this.intitucionEjercicioFiscalId = intitucionEjercicioFiscalId;
    }

    /**
     * @return the ubicacionGeograficaTipo
     */
    public String getUbicacionGeograficaTipo() {
        return ubicacionGeograficaTipo;
    }

    /**
     * @param ubicacionGeograficaTipo the ubicacionGeograficaTipo to set
     */
    public void setUbicacionGeograficaTipo(final String ubicacionGeograficaTipo) {
        this.ubicacionGeograficaTipo = ubicacionGeograficaTipo;
    }

    /**
     * @return the tipoModalidad
     */
    public String getTipoModalidad() {
        return tipoModalidad;
    }

    /**
     * @param tipoModalidad the tipoModalidad to set
     */
    public void setTipoModalidad(final String tipoModalidad) {
        this.tipoModalidad = tipoModalidad;
    }

    /**
     * @return the puestoVacante
     */
    public Boolean getPuestoVacante() {
        return puestoVacante;
    }

    /**
     * @param puestoVacante the puestoVacante to set
     */
    public void setPuestoVacante(final Boolean puestoVacante) {
        this.puestoVacante = puestoVacante;
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
    public void setDistributivoDetalleId(final Long distributivoDetalleId) {
        this.distributivoDetalleId = distributivoDetalleId;
    }

    /**
     * @return the codigoPuesto
     */
    public Long getCodigoPuesto() {
        return codigoPuesto;
    }

    /**
     * @param codigoPuesto the codigoPuesto to set
     */
    public void setCodigoPuesto(Long codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    /**
     * @return the certificacionPresupuestaria
     */
    public String getCertificacionPresupuestaria() {
        return certificacionPresupuestaria;
    }

    /**
     * @param certificacionPresupuestaria the certificacionPresupuestaria to set
     */
    public void setCertificacionPresupuestaria(String certificacionPresupuestaria) {
        this.certificacionPresupuestaria = certificacionPresupuestaria;
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
     * @return the obligadoTimbrar
     */
    public Boolean getObligadoTimbrar() {
        return obligadoTimbrar;
    }

    /**
     * @param obligadoTimbrar the obligadoTimbrar to set
     */
    public void setObligadoTimbrar(Boolean obligadoTimbrar) {
        this.obligadoTimbrar = obligadoTimbrar;
    }

    /**
     * @return the renovacionComisionServicio
     */
    public Boolean getRenovacionComisionServicio() {
        return renovacionComisionServicio;
    }

    /**
     * @param renovacionComisionServicio the renovacionComisionServicio to set
     */
    public void setRenovacionComisionServicio(Boolean renovacionComisionServicio) {
        this.renovacionComisionServicio = renovacionComisionServicio;
    }

    public TiposCertificacionesPresupuestariasEnum getTipoCertificacionPresupuestaria() {
        return tipoCertificacionPresupuestaria;
    }

    public void setTipoCertificacionPresupuestaria(TiposCertificacionesPresupuestariasEnum tipoCertificacionPresupuestaria) {
        this.tipoCertificacionPresupuestaria = tipoCertificacionPresupuestaria;
    }
    
    

}
