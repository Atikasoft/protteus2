/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Servidor;
import java.io.Serializable;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class BusquedaNominaVO implements Serializable {

    /**
     * Id del periodo fiscal.
     */
    private Long periodoFiscal;

    /**
     * Id de periodo de nomina.
     */
    private Long periodoNomina;

    /**
     * Codigo del tipo de nomina.
     */
    private Long tipoNomina;
    /**
     * número de la nomina.
     */
    private String numeroNomina;
    /**
     * descripcion de la nomina.
     */
    private String descripcionNomina;
    private Long servidorLogueado;
    /**
     * estado de la nomina.
     */
    private String estado;
    /**
     * Campo para el unidadAdministrativaNombre.
     */
    private String unidadAdministrativaNombre;

    /**
     * Campo para el unidadAdministrativaId.
     */
    private Long unidadAdministrativaId;
    
    /**
     * Campo para el id de la nomina.
     */
    private Long idNomina;
        /**
     * Campo para rubrosCapacidadPago.
     */
    private Boolean esCapacidadPago;

    /**
     * @return the periodoFiscal
     */
    public Long getPeriodoFiscal() {
        return periodoFiscal;
    }

    /**
     * @param periodoFiscal the periodoFiscal to set
     */
    public void setPeriodoFiscal(final Long periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    /**
     * @return the periodoNomina
     */
    public Long getPeriodoNomina() {
        return periodoNomina;
    }

    /**
     * @param periodoNomina the periodoNomina to set
     */
    public void setPeriodoNomina(final Long periodoNomina) {
        this.periodoNomina = periodoNomina;
    }

    /**
     * @return the tipoNomina
     */
    public Long getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(final Long tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the numeroNomina
     */
    public String getNumeroNomina() {
        return numeroNomina;
    }

    /**
     * @param numeroNomina the numeroNomina to set
     */
    public void setNumeroNomina(final String numeroNomina) {
        this.numeroNomina = numeroNomina;
    }

    /**
     * @return the descripcionNomina
     */
    public String getDescripcionNomina() {
        return descripcionNomina;
    }

    /**
     * @param descripcionNomina the descripcionNomina to set
     */
    public void setDescripcionNomina(final String descripcionNomina) {
        this.descripcionNomina = descripcionNomina;
    }

    /**
     * @return the servidorLogueado
     */
    public Long getServidorLogueado() {
        return servidorLogueado;
    }

    /**
     * @param servidorLogueado the servidorLogueado to set
     */
    public void setServidorLogueado(Long servidorLogueado) {
        this.servidorLogueado = servidorLogueado;
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
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * @return the unidadAdministrativaNombre
     */
    public String getUnidadAdministrativaNombre() {
        return unidadAdministrativaNombre;
    }

    /**
     * @param unidadAdministrativaNombre the unidadAdministrativaNombre to set
     */
    public void setUnidadAdministrativaNombre(final String unidadAdministrativaNombre) {
        this.unidadAdministrativaNombre = unidadAdministrativaNombre;
    }

    /**
     * @return the unidadAdministrativaId
     */
    public Long getUnidadAdministrativaId() {
        return unidadAdministrativaId;
    }

    /**
     * @param unidadAdministrativaId the unidadAdministrativaId to set
     */
    public void setUnidadAdministrativaId(final Long unidadAdministrativaId) {
        this.unidadAdministrativaId = unidadAdministrativaId;
    }

    /**
     * @return the idNomina
     */
    public Long getIdNomina() {
        return idNomina;
    }

    /**
     * @param idNomina the idNomina to set
     */
    public void setIdNomina(Long idNomina) {
        this.idNomina = idNomina;
    }

    /**
     * @return the esCapacidadPago
     */
    public Boolean getEsCapacidadPago() {
        return esCapacidadPago;
    }

    /**
     * @param esCapacidadPago the esCapacidadPago to set
     */
    public void setEsCapacidadPago(Boolean esCapacidadPago) {
        this.esCapacidadPago = esCapacidadPago;
    }
}
