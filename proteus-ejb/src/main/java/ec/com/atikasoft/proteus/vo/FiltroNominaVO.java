/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import java.io.Serializable;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class FiltroNominaVO implements Serializable {

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
     * codigo de cobertura de nomina.
     */
    private String coberturaNomina;
    
    /**
     * 
     */
    private Long regimenLaboral;
    
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
     * @return the coberturaNomina
     */
    public String getCoberturaNomina() {
        return coberturaNomina;
    }

    /**
     * @param coberturaNomina the coberturaNomina to set
     */
    public void setCoberturaNomina(String coberturaNomina) {
        this.coberturaNomina = coberturaNomina;
    }

    /**
     * @return the regimenLaboral
     */
    public Long getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(Long regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }
}
