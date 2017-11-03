/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Henry Molina <henry.molina@atikasoft.com.ec>
 */
@Entity
@Table(name = "nominas_pagos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaPago.ELIMINAR_POR_NOMINA, query = "DELETE FROM NominaPago o WHERE o.nomina.id=?1"),
    @NamedQuery(name = NominaPago.ELIMINAR_POR_NOMINA_SERVIDOR, query = "DELETE FROM NominaPago o WHERE o.nomina.id=?1 AND o.tipoIdentificacion=?2 AND o.numeroIdentificacion=?3")
})
public class NominaPago extends EntidadBasica {

    /**
     *
     */
    public static final String ELIMINAR_POR_NOMINA = "NominaPago.eliminarPorNomina";

    /**
     *
     */
    public static final String ELIMINAR_POR_NOMINA_SERVIDOR = "NominaPago.eliminarPorNominaServidor";

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     *
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     *
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;

    /**
     *
     */
    @Column(name = "valor_ingresos")
    private BigDecimal valorIngresos;

    /**
     *
     */
    @Column(name = "valor_descuentos")
    private BigDecimal valorDescuentos;

    /**
     *
     */
    @Column(name = "valor_aportes")
    private BigDecimal valorAportes;

    /**
     *
     */
    @Column(name = "valor_liquido_a_pagar")
    private BigDecimal valorLiquidoAPagar;

    /**
     *
     */
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    /**
     *
     */
    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banco_id")
    private Banco banco;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_organizacional_id")
    private UnidadOrganizacional unidadOrganizacional;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_presupuestaria_id")
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     *
     */
    public NominaPago() {
        super();
    }

    /**
     *
     * @param id
     */
    public NominaPago(final Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta the tipoCuenta to set
     */
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the banco
     */
    public Banco getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(Banco banco) {
        this.banco = banco;
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

    /**
     * @return the unidadPresupuestaria
     */
    public UnidadPresupuestaria getUnidadPresupuestaria() {
        return unidadPresupuestaria;
    }

    /**
     * @param unidadPresupuestaria the unidadPresupuestaria to set
     */
    public void setUnidadPresupuestaria(UnidadPresupuestaria unidadPresupuestaria) {
        this.unidadPresupuestaria = unidadPresupuestaria;
    }

}
