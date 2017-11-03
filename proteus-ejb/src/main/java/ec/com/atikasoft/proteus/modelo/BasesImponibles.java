/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arandrade
 */
@Entity
@Table(name = "bases_imponibles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BasesImponibles.findAll", query = "SELECT b FROM BasesImponibles b")
    , @NamedQuery(name = "BasesImponibles.findById", query = "SELECT b FROM BasesImponibles b WHERE b.id = :id")})

/**
 *
 * @author arandrade
 */
public class BasesImponibles implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mes")
    private short mes;
    @Size(max = 255)
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @Size(max = 255)
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;
    @Size(max = 255)
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;
    @Size(max = 255)
    @Column(name = "regimen_laboral")
    private String regimenLaboral;
    @Column(name = "sueldos_salarios")
    private BigDecimal sueldosSalarios;
    @Column(name = "fondo_reserva")
    private BigDecimal fondoReserva;
    @Column(name = "decimo_cuarto")
    private BigDecimal decimoCuarto;
    @Column(name = "decimo_tercero")
    private BigDecimal decimoTercero;
    @Column(name = "aporte_personal")
    private BigDecimal aportePersonal;
    @Column(name = "gastos_personal_vivienda")
    private BigDecimal gastosPersonalVivienda;
    @Column(name = "gastos_personal_salud")
    private BigDecimal gastosPersonalSalud;
    @Column(name = "gastos_personal_educacion")
    private BigDecimal gastosPersonalEducacion;
    @Column(name = "gastos_personal_alimentacion")
    private BigDecimal gastosPersonalAlimentacion;
    @Column(name = "gastos_personal_vestimenta")
    private BigDecimal gastosPersonalVestimenta;
    @Size(max = 255)
    @Column(name = "rebaja_por_discapacidad")
    private String rebajaPorDiscapacidad;
    @Column(name = "otros_ingresos")
    private BigDecimal otrosIngresos;
    @Column(name = "base_imponible")
    private BigDecimal baseImponible;
    @Column(name = "impuesto_renta")
    private BigDecimal impuestoRenta;
    @JoinColumn(name = "nomina_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nomina nominaId;

    /**
     *
     */
    public BasesImponibles() {
    }

    /**
     *
     * @param id
     */
    public BasesImponibles(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @param id
     * @param anio
     * @param mes
     */
    public BasesImponibles(BigDecimal id, short anio, short mes) {
        this.id = id;
        this.anio = anio;
        this.mes = mes;
    }

    /**
     *
     * @return
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public short getAnio() {
        return anio;
    }

    /**
     *
     * @param anio
     */
    public void setAnio(short anio) {
        this.anio = anio;
    }

    /**
     *
     * @return
     */
    public short getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(short mes) {
        this.mes = mes;
    }

    /**
     *
     * @return
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     *
     * @param tipoIdentificacion
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     *
     * @return
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     *
     * @param numeroIdentificacion
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     *
     * @return
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     *
     * @param apellidosNombres
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     *
     * @return
     */
    public String getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     *
     * @param regimenLaboral
     */
    public void setRegimenLaboral(String regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSueldosSalarios() {
        return sueldosSalarios;
    }

    /**
     *
     * @param sueldosSalarios
     */
    public void setSueldosSalarios(BigDecimal sueldosSalarios) {
        this.sueldosSalarios = sueldosSalarios;
    }

    /**
     *
     * @return
     */
    public BigDecimal getFondoReserva() {
        return fondoReserva;
    }

    /**
     *
     * @param fondoReserva
     */
    public void setFondoReserva(BigDecimal fondoReserva) {
        this.fondoReserva = fondoReserva;
    }

    /**
     *
     * @return
     */
    public BigDecimal getDecimoCuarto() {
        return decimoCuarto;
    }

    /**
     *
     * @param decimoCuarto
     */
    public void setDecimoCuarto(BigDecimal decimoCuarto) {
        this.decimoCuarto = decimoCuarto;
    }

    /**
     *
     * @return
     */
    public BigDecimal getDecimoTercero() {
        return decimoTercero;
    }

    /**
     *
     * @param decimoTercero
     */
    public void setDecimoTercero(BigDecimal decimoTercero) {
        this.decimoTercero = decimoTercero;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAportePersonal() {
        return aportePersonal;
    }

    /**
     *
     * @param aportePersonal
     */
    public void setAportePersonal(BigDecimal aportePersonal) {
        this.aportePersonal = aportePersonal;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGastosPersonalVivienda() {
        return gastosPersonalVivienda;
    }

    /**
     *
     * @param gastosPersonalVivienda
     */
    public void setGastosPersonalVivienda(BigDecimal gastosPersonalVivienda) {
        this.gastosPersonalVivienda = gastosPersonalVivienda;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGastosPersonalSalud() {
        return gastosPersonalSalud;
    }

    /**
     *
     * @param gastosPersonalSalud
     */
    public void setGastosPersonalSalud(BigDecimal gastosPersonalSalud) {
        this.gastosPersonalSalud = gastosPersonalSalud;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGastosPersonalEducacion() {
        return gastosPersonalEducacion;
    }

    /**
     *
     * @param gastosPersonalEducacion
     */
    public void setGastosPersonalEducacion(BigDecimal gastosPersonalEducacion) {
        this.gastosPersonalEducacion = gastosPersonalEducacion;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGastosPersonalAlimentacion() {
        return gastosPersonalAlimentacion;
    }

    /**
     *
     * @param gastosPersonalAlimentacion
     */
    public void setGastosPersonalAlimentacion(BigDecimal gastosPersonalAlimentacion) {
        this.gastosPersonalAlimentacion = gastosPersonalAlimentacion;
    }

    /**
     *
     * @return
     */
    public BigDecimal getGastosPersonalVestimenta() {
        return gastosPersonalVestimenta;
    }

    /**
     *
     * @param gastosPersonalVestimenta
     */
    public void setGastosPersonalVestimenta(BigDecimal gastosPersonalVestimenta) {
        this.gastosPersonalVestimenta = gastosPersonalVestimenta;
    }

    /**
     *
     * @return
     */
    public String getRebajaPorDiscapacidad() {
        return rebajaPorDiscapacidad;
    }

    /**
     *
     * @param rebajaPorDiscapacidad
     */
    public void setRebajaPorDiscapacidad(String rebajaPorDiscapacidad) {
        this.rebajaPorDiscapacidad = rebajaPorDiscapacidad;
    }

    /**
     *
     * @return
     */
    public BigDecimal getOtrosIngresos() {
        return otrosIngresos;
    }

    /**
     *
     * @param otrosIngresos
     */
    public void setOtrosIngresos(BigDecimal otrosIngresos) {
        this.otrosIngresos = otrosIngresos;
    }

    /**
     *
     * @return
     */
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    /**
     *
     * @param baseImponible
     */
    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    /**
     *
     * @return
     */
    public BigDecimal getImpuestoRenta() {
        return impuestoRenta;
    }

    /**
     *
     * @param impuestoRenta
     */
    public void setImpuestoRenta(BigDecimal impuestoRenta) {
        this.impuestoRenta = impuestoRenta;
    }

    /**
     *
     * @return
     */
    public Nomina getNominaId() {
        return nominaId;
    }

    /**
     *
     * @param nominaId
     */
    public void setNominaId(Nomina nominaId) {
        this.nominaId = nominaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BasesImponibles)) {
            return false;
        }
        BasesImponibles other = (BasesImponibles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.atikasoft.proteus.modelo.BasesImponibles[ id=" + id + " ]";
    }
    
}
