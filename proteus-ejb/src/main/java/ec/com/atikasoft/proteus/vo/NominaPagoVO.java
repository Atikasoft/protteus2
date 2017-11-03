/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.math.BigDecimal;

/**
 *
 * @author henry
 */
public class NominaPagoVO {

    /**
     *
     */
    private String tipoIdentificacion;
    /**
     *
     */
    private String numeroIdentificacion;
    /**
     *
     */
    private String apellidosNombres;

    /**
     *
     */
    private Long servidorId;

    /**
     *
     */
    private Long unidadOrganizacionalId;

    /**
     *
     */
    private Long unidadPresupuestarioId;
    /**
     *
     */
    private BigDecimal valorIngresos;
    /**
     *
     */
    private BigDecimal valorDescuentos;
    /**
     *
     */
    private BigDecimal valorAportes;
    /**
     *
     */
    private BigDecimal valorLiquidoAPagar;

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres the apellidosNombres to set
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
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
     * @return the valorIngresos
     */
    public BigDecimal getValorIngresos() {
        return valorIngresos;
    }

    /**
     * @param valorIngresos the valorIngresos to set
     */
    public void setValorIngresos(BigDecimal valorIngresos) {
        this.valorIngresos = valorIngresos;
    }

    /**
     * @return the valorDescuentos
     */
    public BigDecimal getValorDescuentos() {
        return valorDescuentos;
    }

    /**
     * @param valorDescuentos the valorDescuentos to set
     */
    public void setValorDescuentos(BigDecimal valorDescuentos) {
        this.valorDescuentos = valorDescuentos;
    }

    /**
     * @return the valorAportes
     */
    public BigDecimal getValorAportes() {
        return valorAportes;
    }

    /**
     * @param valorAportes the valorAportes to set
     */
    public void setValorAportes(BigDecimal valorAportes) {
        this.valorAportes = valorAportes;
    }

    /**
     * @return the valorLiquidoAPagar
     */
    public BigDecimal getValorLiquidoAPagar() {
        return valorLiquidoAPagar;
    }

    /**
     * @param valorLiquidoAPagar the valorLiquidoAPagar to set
     */
    public void setValorLiquidoAPagar(BigDecimal valorLiquidoAPagar) {
        this.valorLiquidoAPagar = valorLiquidoAPagar;
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
     * @return the unidadPresupuestarioId
     */
    public Long getUnidadPresupuestarioId() {
        return unidadPresupuestarioId;
    }

    /**
     * @param unidadPresupuestarioId the unidadPresupuestarioId to set
     */
    public void setUnidadPresupuestarioId(Long unidadPresupuestarioId) {
        this.unidadPresupuestarioId = unidadPresupuestarioId;
    }

}
