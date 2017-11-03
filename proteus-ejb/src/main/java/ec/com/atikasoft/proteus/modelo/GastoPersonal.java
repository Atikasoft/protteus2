/*
 *  GastoPersonal.java
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "gastos_personales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = GastoPersonal.BUSCAR_POR_EJERCICIO_FISCAL, query = "SELECT a FROM GastoPersonal a where a.idEjercicioFiscal =?1 and a.vigente=true order by a.fechaCreacion desc"),
    @NamedQuery(name = GastoPersonal.BUSCAR_POR_SERVIDOR, query = "SELECT a FROM GastoPersonal a where a.idServidor=?1 and a.vigente=true order by a.fechaCreacion desc "),
    @NamedQuery(name = GastoPersonal.BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL, query = "SELECT a FROM GastoPersonal a where a.idServidor=?1 and a.idEjercicioFiscal =?2 and a.vigente=true order by a.fechaCreacion desc "),
    @NamedQuery(name = GastoPersonal.BUSCAR_VIGENTES, query = "SELECT a FROM GastoPersonal a where a.vigente=true order by a.fechaCreacion desc ")
})
public class GastoPersonal extends EntidadBasica {

    /**
     * Variable para busqueda por ejercicio fiscal.
     */
    public static final String BUSCAR_POR_EJERCICIO_FISCAL = "GastoPersonal.buscarporEjercicioFiscal ";

    /*
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "GastoPersonal.buscarVigente";
    /*
     * Nombre para busqueda de fechas vigentes.
     */

    public static final String BUSCAR_POR_SERVIDOR = "GastoPersonal.buscarPorServidor";
    /*
     * Nombre para busqueda de fechas vigentes.
     */

    public static final String BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL = "GastoPersonal.buscarPorServidorYEjericioFiscal";

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
     * Campo total ingresos adicionales: Sobresueldos, comisiones, bonos y otros
     * ingresos gravados.
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
     * Campo Total ingresos que tuvo durante el ejercicio fiscal con otro
     * Empleador.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ingresos_otro_empleador")
    private BigDecimal ingresosOtroEmpleador;

    /**
     * Campo Total iessPersonal.-Valor del aporte personal al iess en la
     * institución durante el ejericio fiscal.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "iess_personal")
    private BigDecimal iessPersonal;

    /**
     * Campo Total iessPersonal.-Valor del aporte personal al iess en otro
     * empleador durante el ejericio fiscal.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "iess_personal_otro_empleador")
    private BigDecimal iessPersonalOtroEmpleador;

    /**
     * Valor monetario de monto por alimentacion para deducir. No debe exceder
     * en 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "alimentacion")
    private BigDecimal alimentacion;

    /**
     * Valor monetario de monto por alimentacion para deducir. No debe exceder
     * en 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "educacion")
    private BigDecimal educacion;

    /**
     * Valor monetario de monto por vivienda para deducir. No debe exceder en
     * 0.325 la Fración Básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "vivienda")
    private BigDecimal vivienda;

    /**
     * Valor monetario de monto por salud para deducir. No debe exceder en 1.3
     * la Fración Básica
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "salud")
    private BigDecimal salud;

    /**
     * Valor monetario de monto por vestimenta para deducir. No debe exceder en
     * 0.325 la Fración Básica
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "vestimenta")
    private BigDecimal vestimenta;

    /**
     * Valor deducible por tercera edad durante el ejericio fiscal. Corresponde
     * al doble de la fracción básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;

    /**
     * Valor deducible por discapacidad durante el ejericio fiscal. Corresponde
     * al doble de la fracción básica.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "exoneracion_discapacidad")
    private BigDecimal exoneracionDiscapacidad;

    /**
     * Valor total monetario a deducir, resultado de los cálculos de atributos
     * deducibles.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_deducible")
    private BigDecimal totalDeducible;

    /**
     * Referencia a InstitucionEjercicioFiscal
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal ejercicioFiscal;

    /**
     * InstitucionEjercicioFiscal id.
     */
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long idEjercicioFiscal;

    /**
     * Referencia a Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Servidor id.
     */
    @Column(name = "servidor_id")
    private Long idServidor;

    @Transient
    private BigDecimal totalIngresos;

    @Transient
    private boolean editable;

    /**
     * Constructor.
     */
    public GastoPersonal() {
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
        totalIngresos = BigDecimal.ZERO;
        totalDeducible = BigDecimal.ZERO;
        editable = false;
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public GastoPersonal(Long id) {
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
     * @return the ejercicioFiscal
     */
    public InstitucionEjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(InstitucionEjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the idEjercicioFiscal
     */
    public Long getIdEjercicioFiscal() {
        return idEjercicioFiscal;
    }

    /**
     * @param idEjercicioFiscal the idEjercicioFiscal to set
     */
    public void setIdEjercicioFiscal(Long idEjercicioFiscal) {
        this.idEjercicioFiscal = idEjercicioFiscal;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the idServidor
     */
    public Long getIdServidor() {
        return idServidor;
    }

    /**
     * @param idServidor the idServidor to set
     */
    public void setIdServidor(Long idServidor) {
        this.idServidor = idServidor;
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
     * @param totalDeducible the totalDeducible to set
     */
    public void setTotalDeducible(BigDecimal totalDeducible) {
        this.totalDeducible = totalDeducible;
    }

    /**
     * @return the totalIngresos
     */
    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    /**
     * @param totalIngresos the totalIngresos to set
     */
    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    /**
     * @return the nombreEjercicioFiscal
     */
    public String getNombreEjercicioFiscal() {
        return ejercicioFiscal.getEjercicioFiscal().getNombre();
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
