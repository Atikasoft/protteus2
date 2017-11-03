/*
 *  GastoPersonalHistorico.java
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
 *  14/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "gastos_personales_historicos", catalog = "sch_proteus")
public class GastoPersonalHistorico extends EntidadBasica {

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo tipo <O>riginal, <R>eliquidacion.
     */
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @NotNull
    @Column(name = "tipo")
    private String tipo;

    /**
     * Campo total ingresos adicionales: Sobresueldos, comisiones, bonos y otros ingresos gravados.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingresos_adicionales")
    private BigDecimal ingresosAdicionales;

    /**
     * Campo total ingresos.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingresos")
    private BigDecimal ingresos;

    /**
     * Campo Total ingresos que tuvo durante el ejercicio fiscal con otro Empleador.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingresos_otro_empleador")
    private BigDecimal ingresosOtroEmpleador;

    /**
     * Campo Total iessPersonal.-Valor del aporte personal al iess en la institución durante el ejericio fiscal.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "iess_personal")
    private BigDecimal iessPersonal;

    /**
     * Campo Total iessPersonal.-Valor del aporte personal al iess en otro empleador durante el ejericio fiscal.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "iess_personal_otro_empleador")
    private BigDecimal iessPersonalOtroEmpleador;

    /**
     * Valor monetario de monto por alimentacion para deducir. No debe exceder en 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "alimentacion")
    private BigDecimal alimentacion;

    /**
     * Valor monetario de monto por alimentacion para deducir. No debe exceder en 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "educacion")
    private BigDecimal educacion;

    /**
     * Valor monetario de monto por vivienda para deducir. No debe exceder en 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "vivienda")
    private BigDecimal vivienda;

    /**
     * Valor monetario de monto por salud para deducir. No debe exceder en 1.3 la Fración Básica
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "salud")
    private BigDecimal salud;

    /**
     * Valor monetario de monto por vestimenta para deducir. No debe exceder en 0.325 la Fración Básica
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "vestimenta")
    private BigDecimal vestimenta;

    /**
     * Valor deducible por tercera edad durante el ejericio fiscal. Corresponde al doble de la fracción básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;

    /**
     * Valor deducible por discapacidad durante el ejericio fiscal. Corresponde al doble de la fracción básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "exoneracion_discapacidad")
    private BigDecimal exoneracionDiscapacidad;

    /**
     * Valor total monetario a deducir, resultado de los cálculos de atributos deducibles.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_deducible")
    private BigDecimal totalDeducible;

    /**
     * GastoPersonal id.
     */
    @NotNull
    @Column(name = "gasto_personal_id")
    private Long gastoPersonalId;

    /**
     * Datos del Servidor .
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacionServidor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_identificacion")
    private String numeroIdentificacionServidor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "apellidos")
    private String apellidosServidor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "nombres")
    private String nombresServidor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "apellidos_nombres")
    private String apellidosNombresServidor;

    /**
     * institucion id.
     */
    @Column(name = "institucion_id")
    private Long institucionId;

    @Column(name = "nombre_ejercicio_fiscal")
    private String nombreEjericioFiscal;

    @Column(name = "fecha_inicio_ejercicio_fiscal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioEjericioFiscal;

    @Column(name = "fecha_fin_ejercicio_fiscal")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinEjericioFiscal;

    @Column(name = "fraccion_basica")
    private BigDecimal fraccionBasica;
    
    /**
     * Campo rmu.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;
    
     /**
     * Campo rmu.
     */
    @Column(name = "porcentaje_aporte_otro_empleador")
    private BigDecimal porcentajeAporteOtroEmpleador;
    /**
     * Constructor.
     */
    public GastoPersonalHistorico() {
        super();
        exoneracionDiscapacidad = BigDecimal.ZERO;
        exoneracionTerceraEdad = BigDecimal.ZERO;
        vivienda = BigDecimal.ZERO;
        alimentacion = BigDecimal.ZERO;
        salud = BigDecimal.ZERO;
        educacion = BigDecimal.ZERO;
        vestimenta = BigDecimal.ZERO;
        ingresos = BigDecimal.ZERO;
        ingresosAdicionales = BigDecimal.ZERO;
        ingresosOtroEmpleador = BigDecimal.ZERO;
        iessPersonal = BigDecimal.ZERO;
        iessPersonalOtroEmpleador = BigDecimal.ZERO;
        totalDeducible = BigDecimal.ZERO;
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public GastoPersonalHistorico(Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the ingresosAdicionales
     */
    public BigDecimal getIngresosAdicionales() {
        return ingresosAdicionales;
    }

    /**
     * @param ingresosAdicionales the ingresosAdicionales to set
     */
    public void setIngresosAdicionales(BigDecimal ingresosAdicionales) {
        this.ingresosAdicionales = ingresosAdicionales;
    }

    /**
     * @return the ingresos
     */
    public BigDecimal getIngresos() {
        return ingresos;
    }

    /**
     * @param ingresos the ingresos to set
     */
    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    /**
     * @return the ingresosOtroEmpleador
     */
    public BigDecimal getIngresosOtroEmpleador() {
        return ingresosOtroEmpleador;
    }

    /**
     * @param ingresosOtroEmpleador the ingresosOtroEmpleador to set
     */
    public void setIngresosOtroEmpleador(BigDecimal ingresosOtroEmpleador) {
        this.ingresosOtroEmpleador = ingresosOtroEmpleador;
    }

    /**
     * @return the iessPersonal
     */
    public BigDecimal getIessPersonal() {
        return iessPersonal;
    }

    /**
     * @param iessPersonal the iessPersonal to set
     */
    public void setIessPersonal(BigDecimal iessPersonal) {
        this.iessPersonal = iessPersonal;
    }

    /**
     * @return the iessPersonalOtroEmpleador
     */
    public BigDecimal getIessPersonalOtroEmpleador() {
        return iessPersonalOtroEmpleador;
    }

    /**
     * @param iessPersonalOtroEmpleador the iessPersonalOtroEmpleador to set
     */
    public void setIessPersonalOtroEmpleador(BigDecimal iessPersonalOtroEmpleador) {
        this.iessPersonalOtroEmpleador = iessPersonalOtroEmpleador;
    }

    /**
     * @return the alimentacion
     */
    public BigDecimal getAlimentacion() {
        return alimentacion;
    }

    /**
     * @param alimentacion the alimentacion to set
     */
    public void setAlimentacion(BigDecimal alimentacion) {
        this.alimentacion = alimentacion;
    }

    /**
     * @return the educacion
     */
    public BigDecimal getEducacion() {
        return educacion;
    }

    /**
     * @param educacion the educacion to set
     */
    public void setEducacion(BigDecimal educacion) {
        this.educacion = educacion;
    }

    /**
     * @return the vivienda
     */
    public BigDecimal getVivienda() {
        return vivienda;
    }

    /**
     * @param vivienda the vivienda to set
     */
    public void setVivienda(BigDecimal vivienda) {
        this.vivienda = vivienda;
    }

    /**
     * @return the salud
     */
    public BigDecimal getSalud() {
        return salud;
    }

    /**
     * @param salud the salud to set
     */
    public void setSalud(BigDecimal salud) {
        this.salud = salud;
    }

    /**
     * @return the vestimenta
     */
    public BigDecimal getVestimenta() {
        return vestimenta;
    }

    /**
     * @param vestimenta the vestimenta to set
     */
    public void setVestimenta(BigDecimal vestimenta) {
        this.vestimenta = vestimenta;
    }

    /**
     * @return the exoneracionTerceraEdad
     */
    public BigDecimal getExoneracionTerceraEdad() {
        return exoneracionTerceraEdad;
    }

    /**
     * @param exoneracionTerceraEdad the exoneracionTerceraEdad to set
     */
    public void setExoneracionTerceraEdad(BigDecimal exoneracionTerceraEdad) {
        this.exoneracionTerceraEdad = exoneracionTerceraEdad;
    }

    /**
     * @return the exoneracionDiscapacidad
     */
    public BigDecimal getExoneracionDiscapacidad() {
        return exoneracionDiscapacidad;
    }

    /**
     * @param exoneracionDiscapacidad the exoneracionDiscapacidad to set
     */
    public void setExoneracionDiscapacidad(BigDecimal exoneracionDiscapacidad) {
        this.exoneracionDiscapacidad = exoneracionDiscapacidad;
    }

    /**
     * @return the totalDeducible
     */
    public BigDecimal getTotalDeducible() {
        return totalDeducible;
    }

    /**
     * @return the tipoIdentificacionServidor
     */
    public String getTipoIdentificacionServidor() {
        return tipoIdentificacionServidor;
    }

    /**
     * @param tipoIdentificacionServidor the tipoIdentificacionServidor to set
     */
    public void setTipoIdentificacionServidor(String tipoIdentificacionServidor) {
        this.tipoIdentificacionServidor = tipoIdentificacionServidor;
    }

    /**
     * @return the numeroIdentificacionServidor
     */
    public String getNumeroIdentificacionServidor() {
        return numeroIdentificacionServidor;
    }

    /**
     * @param numeroIdentificacionServidor the numeroIdentificacionServidor to set
     */
    public void setNumeroIdentificacionServidor(String numeroIdentificacionServidor) {
        this.numeroIdentificacionServidor = numeroIdentificacionServidor;
    }

    /**
     * @return the apellidosServidor
     */
    public String getApellidosServidor() {
        return apellidosServidor;
    }

    /**
     * @param apellidosServidor the apellidosServidor to set
     */
    public void setApellidosServidor(String apellidosServidor) {
        this.apellidosServidor = apellidosServidor;
    }

    /**
     * @return the nombresServidor
     */
    public String getNombresServidor() {
        return nombresServidor;
    }

    /**
     * @param nombresServidor the nombresServidor to set
     */
    public void setNombresServidor(String nombresServidor) {
        this.nombresServidor = nombresServidor;
    }

    /**
     * @return the apellidosNombresServidor
     */
    public String getApellidosNombresServidor() {
        return apellidosNombresServidor;
    }

    /**
     * @param apellidosNombresServidor the apellidosNombresServidor to set
     */
    public void setApellidosNombresServidor(String apellidosNombresServidor) {
        this.apellidosNombresServidor = apellidosNombresServidor;
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
     * @return the fraccionBasica
     */
    public BigDecimal getFraccionBasica() {
        return fraccionBasica;
    }

    /**
     * @param fraccionBasica the fraccionBasica to set
     */
    public void setFraccionBasica(BigDecimal fraccionBasica) {
        this.fraccionBasica = fraccionBasica;
    }

    /**
     * @return the gastoPersonalId
     */
    public Long getGastoPersonalId() {
        return gastoPersonalId;
    }

    /**
     * @param gastoPersonalId the gastoPersonalId to set
     */
    public void setGastoPersonalId(Long gastoPersonalId) {
        this.gastoPersonalId = gastoPersonalId;
    }

    /**
     * @return the nombreEjericioFiscal
     */
    public String getNombreEjericioFiscal() {
        return nombreEjericioFiscal;
    }

    /**
     * @param nombreEjericioFiscal the nombreEjericioFiscal to set
     */
    public void setNombreEjericioFiscal(String nombreEjericioFiscal) {
        this.nombreEjericioFiscal = nombreEjericioFiscal;
    }

    /**
     * @return the fechaInicioEjericioFiscal
     */
    public Date getFechaInicioEjericioFiscal() {
        return fechaInicioEjericioFiscal;
    }

    /**
     * @param fechaInicioEjericioFiscal the fechaInicioEjericioFiscal to set
     */
    public void setFechaInicioEjericioFiscal(Date fechaInicioEjericioFiscal) {
        this.fechaInicioEjericioFiscal = fechaInicioEjericioFiscal;
    }

    /**
     * @return the fechaFinEjericioFiscal
     */
    public Date getFechaFinEjericioFiscal() {
        return fechaFinEjericioFiscal;
    }

    /**
     * @param fechaFinEjericioFiscal the fechaFinEjericioFiscal to set
     */
    public void setFechaFinEjericioFiscal(Date fechaFinEjericioFiscal) {
        this.fechaFinEjericioFiscal = fechaFinEjericioFiscal;
    }

    /**
     * @param totalDeducible the totalDeducible to set
     */
    public void setTotalDeducible(BigDecimal totalDeducible) {
        this.totalDeducible = totalDeducible;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the porcentajeAporteOtroEmpleador
     */
    public BigDecimal getPorcentajeAporteOtroEmpleador() {
        return porcentajeAporteOtroEmpleador;
    }

    /**
     * @param porcentajeAporteOtroEmpleador the porcentajeAporteOtroEmpleador to set
     */
    public void setPorcentajeAporteOtroEmpleador(BigDecimal porcentajeAporteOtroEmpleador) {
        this.porcentajeAporteOtroEmpleador = porcentajeAporteOtroEmpleador;
    }
}
