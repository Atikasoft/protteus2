/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.io.Serializable;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class BusquedaNovedadVO implements Serializable {

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
    private Long tipoNominaId;
  /**
     * Codigo del tipo de nomina.
     */
    private Long datoAdicionalId;
    
    private String numeroDocumento;
    
    private String tipoDocumento;
    
    private String nombres;
    
    /**
     * 
     */
    private UnidadOrganizacional unidadOrganizacional;

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
     * @return the tipoNominaId
     */
    public Long getTipoNominaId() {
        return tipoNominaId;
    }

    /**
     * @param tipoNominaId the tipoNominaId to set
     */
    public void setTipoNominaId(final Long tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
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
    public void setDatoAdicionalId(final Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
