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

import java.io.Serializable;
import java.util.Date;

/**
 * VO para la Busqueda de Servidor.
 *
 *
 */
public class BusquedaServidorVO implements Serializable {

    /**
     *
     */
    private Long intitucionEjercicioFiscalId;
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
     * Campo para el filtro, se utiliza para discriminar si es de RRHH o no.
     */
    private String codUnidadAdministrativa;
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
    private Long estadoServidorId;
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
     * Campo para el filtro.
     */
    private Long catalogoGeneroId;
    /**
     * Campo para el filtro.
     */
    private Long catalogoEstadoCivil;

    /**
     * Campo para el filtro, para ver si el puesto del distributivo esta vacante
     * o no, si es null se considera vacante.
     */
    private Boolean puestoVacante;

    /**
     * Campo para el codigoPuesto.
     */
    private Long codigoPuesto;

     /**
     * Campo para el id de la institucion.
     */
    private Long idInstitucion;
        /**
     *
     */
    private Date fechaInicioIns;
    /**
     *
     */
    private Date fechaFinIns;
        /**
     *Filtra solo servidores con clave.
     */
    private Boolean conClave;
    /**
     * 
     */
    private Boolean conHorarioAsignado;
    /**
     * Constructor por defecto.
     */
    public BusquedaServidorVO() {
        super();
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
     * @return the estadoServidorId
     */
    public Long getEstadoServidorId() {
        return estadoServidorId;
    }

    /**
     * @param estadoServidorId the estadoServidorId to set
     */
    public void setEstadoServidorId(final Long estadoServidorId) {
        this.estadoServidorId = estadoServidorId;
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
     * @return the catalogoGeneroId
     */
    public Long getCatalogoGeneroId() {
        return catalogoGeneroId;
    }

    /**
     * @param catalogoGeneroId the catalogoGeneroId to set
     */
    public void setCatalogoGeneroId(final Long catalogoGeneroId) {
        this.catalogoGeneroId = catalogoGeneroId;
    }

    /**
     * @return the catalogoEstadoCivil
     */
    public Long getCatalogoEstadoCivil() {
        return catalogoEstadoCivil;
    }

    /**
     * @param catalogoEstadoCivil the catalogoEstadoCivil to set
     */
    public void setCatalogoEstadoCivil(final Long catalogoEstadoCivil) {
        this.catalogoEstadoCivil = catalogoEstadoCivil;
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
     * @return the codigoPuesto
     */
    public Long getCodigoPuesto() {
        return codigoPuesto;
    }

    /**
     * @param codigoPuesto the codigoPuesto to set
     */
    public void setCodigoPuesto(final Long codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    /**
     * @return the idInstitucion
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(final Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the codUnidadAdministrativa
     */
    public String getCodUnidadAdministrativa() {
        return codUnidadAdministrativa;
    }

    /**
     * @param codUnidadAdministrativa the codUnidadAdministrativa to set
     */
    public void setCodUnidadAdministrativa(final String codUnidadAdministrativa) {
        this.codUnidadAdministrativa = codUnidadAdministrativa;
    }

    /**
     * @return the fechaInicioIns
     */
    public Date getFechaInicioIns() {
        return fechaInicioIns;
    }

    /**
     * @param fechaInicioIns the fechaInicioIns to set
     */
    public void setFechaInicioIns(final Date fechaInicioIns) {
        this.fechaInicioIns = fechaInicioIns;
    }

    /**
     * @return the fechaFinIns
     */
    public Date getFechaFinIns() {
        return fechaFinIns;
    }

    /**
     * @param fechaFinIns the fechaFinIns to set
     */
    public void setFechaFinIns(final Date fechaFinIns) {
        this.fechaFinIns = fechaFinIns;
    }

    /**
     * 
     * @return 
     */
    public Boolean getConHorarioAsignado() {
        return conHorarioAsignado;
    }

    /**
     * 
     * @param conHorarioAsignado 
     */
    public void setConHorarioAsignado(Boolean conHorarioAsignado) {
        this.conHorarioAsignado = conHorarioAsignado;
    }

    /**
     * @return the conClave
     */
    public Boolean getConClave() {
        return conClave;
    }

    /**
     * @param conClave the conClave to set
     */
    public void setConClave(Boolean conClave) {
        this.conClave = conClave;
    }
}
