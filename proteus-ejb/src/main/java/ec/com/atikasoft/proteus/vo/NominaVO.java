/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tercero;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class NominaVO implements Serializable {

    /**
     * Nomina.
     */
    private Nomina nomina;

    /**
     * Tipo calculo.
     */
    private String tipoCalculo;

    /**
     * Id de regimen laboral.
     */
    private Long regimenLaboralId;

    /**
     * Id de niviel ocupacional.
     */
    private Long nivelOcupacionalId;

    /**
     * Id de modalidad laboral.
     */
    private Long modalidadLaboralId;

    /**
     * Lista de servidorew.
     */
    private List<Servidor> servidores = new ArrayList<Servidor>();
        /**
     * Lista de terceros.
     */
    private List<Tercero> terceros = new ArrayList<Tercero>();
    //private 

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the tipoCalculo
     */
    public String getTipoCalculo() {
        return tipoCalculo;
    }

    /**
     * @param tipoCalculo the tipoCalculo to set
     */
    public void setTipoCalculo(final String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    /**
     * @return the regimenLaboralId
     */
    public Long getRegimenLaboralId() {
        return regimenLaboralId;
    }

    /**
     * @param regimenLaboralId the regimenLaboralId to set
     */
    public void setRegimenLaboralId(final Long regimenLaboralId) {
        this.regimenLaboralId = regimenLaboralId;
    }

    /**
     * @return the nivelOcupacionalId
     */
    public Long getNivelOcupacionalId() {
        return nivelOcupacionalId;
    }

    /**
     * @param nivelOcupacionalId the nivelOcupacionalId to set
     */
    public void setNivelOcupacionalId(final Long nivelOcupacionalId) {
        this.nivelOcupacionalId = nivelOcupacionalId;
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
    public void setModalidadLaboralId(final Long modalidadLaboralId) {
        this.modalidadLaboralId = modalidadLaboralId;
    }

    /**
     * @return the servidores
     */
    public List<Servidor> getServidores() {
        return servidores;
    }

    /**
     * @param servidores the servidores to set
     */
    public void setServidores(final List<Servidor> servidores) {
        this.servidores = servidores;
    }

    /**
     * @return the terceros
     */
    public List<Tercero> getTerceros() {
        return terceros;
    }

    /**
     * @param terceros the terceros to set
     */
    public void setTerceros(List<Tercero> terceros) {
        this.terceros = terceros;
    }
}
