/*
 *  ProcesoNominaProblema.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Mar 3, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.nomina;

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Contiene los datos del calculo de ir en la nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "nominas_ir", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaIR.ELIMINAR, query = "DELETE FROM NominaIR o  WHERE o.nomina.id=?1"),
    @NamedQuery(name = NominaIR.ELIMINAR_POR_SERVIDOR, query = "DELETE FROM NominaIR o WHERE o.nomina.id=?1 AND  o.servidor.id=?2  "),
    @NamedQuery(name = NominaIR.BUSCAR_POR_SERVIDOR_NOMINA, query = "SELECT o FROM NominaIR o WHERE o.nomina.id=?1 AND  o.servidor.id=?2  ")
})

public class NominaIR extends EntidadBasica implements Serializable {

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR = "NominaIR.eliminar";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR_POR_SERVIDOR = "NominaIR.eliminarPorServidor";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_NOMINA = "NominaIR.buscarPorServidorNomina";

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "rmu")
    private BigDecimal rmu;
    /**
     *
     */
    @Column(name = "ingresos_proyeccion_mensual")
    private BigDecimal ingresosProyeccionMensual;
    /**
     *
     */
    @Column(name = "ingresos_anterior")
    private BigDecimal ingresosAnterior;
    /**
     *
     */
    @Column(name = "ingresos_actual")
    private BigDecimal ingresosActual;
    /**
     *
     */
    @Column(name = "ingresos_proyeccion")
    private BigDecimal ingresosProyeccion;

    /**
     *
     */
    @Column(name = "ingresos_otros_empleadores")
    private BigDecimal ingresosOtrosEmpleadores;
    /**
     *
     */
    @Column(name = "meses_proyeccion")
    private BigDecimal mesesProyeccion;
    /**
     *
     */
    @Column(name = "gastos_personal_vestimenta")
    private BigDecimal gastosPersonalVestimenta;
    /**
     *
     */
    @Column(name = "gastos_personal_salud")
    private BigDecimal gastosPersonalSalud;
    /**
     *
     */
    @Column(name = "gastos_personal_alimentacion")
    private BigDecimal gastosPersonalAlimentacion;
    /**
     *
     */
    @Column(name = "gastos_personal_vivienda")
    private BigDecimal gastosPersonalVivienda;
    /**
     *
     */
    @Column(name = "gastos_personal_educacion")
    private BigDecimal gastosPersonalEducacion;
    /**
     *
     */
    @Column(name = "gastos_personal_total")
    private BigDecimal gastosPersonalTotal;
    /**
     *
     */
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;
    /**
     *
     */
    @Column(name = "exoneracion_discapacidad")
    private BigDecimal exoneracionDiscapacidad;
    /**
     *
     */
    @Column(name = "saldo")
    private BigDecimal saldo;

    /**
     *
     */
    @Column(name = "porcentaje_impuesto_sobre_fraccion_basica")
    private BigDecimal porcentajeImpuestoSobreFraccionBasica;

    /**
     *
     */
    @Column(name = "fraccion_basica")
    private BigDecimal fraccionBasica;

    /**
     *
     */
    @Column(name = "porcentaje_impuesto_fraccion_excedente")
    private BigDecimal porcentajeImpuestoFraccionExcedente;
    /**
     *
     */
    @Column(name = "ir_retenido")
    private BigDecimal irRetenido;
    /**
     *
     */
    @Column(name = "ir_causado")
    private BigDecimal irCausado;

    /**
     *
     */
    @Column(name = "ir_causado_mensual")
    private BigDecimal irCausadoMensual;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "unidad_presupuestaria_id")
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * Servidor
     */
    @ManyToOne
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;

    /**
     * Nominas.
     */
    @ManyToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    /**
     * Constructor sin argumentos.
     */
    public NominaIR() {
        super();
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
     * @return the ingresosProyeccionMensual
     */
    public BigDecimal getIngresosProyeccionMensual() {
        return ingresosProyeccionMensual;
    }

    /**
     * @param ingresosProyeccionMensual the ingresosProyeccionMensual to set
     */
    public void setIngresosProyeccionMensual(BigDecimal ingresosProyeccionMensual) {
        this.ingresosProyeccionMensual = ingresosProyeccionMensual;
    }

    /**
     * @return the ingresosAnterior
     */
    public BigDecimal getIngresosAnterior() {
        return ingresosAnterior;
    }

    /**
     * @param ingresosAnterior the ingresosAnterior to set
     */
    public void setIngresosAnterior(BigDecimal ingresosAnterior) {
        this.ingresosAnterior = ingresosAnterior;
    }

    /**
     * @return the ingresosActual
     */
    public BigDecimal getIngresosActual() {
        return ingresosActual;
    }

    /**
     * @param ingresosActual the ingresosActual to set
     */
    public void setIngresosActual(BigDecimal ingresosActual) {
        this.ingresosActual = ingresosActual;
    }

    /**
     * @return the ingresosProyeccion
     */
    public BigDecimal getIngresosProyeccion() {
        return ingresosProyeccion;
    }

    /**
     * @param ingresosProyeccion the ingresosProyeccion to set
     */
    public void setIngresosProyeccion(BigDecimal ingresosProyeccion) {
        this.ingresosProyeccion = ingresosProyeccion;
    }

    /**
     * @return the mesesProyeccion
     */
    public BigDecimal getMesesProyeccion() {
        return mesesProyeccion;
    }

    /**
     * @param mesesProyeccion the mesesProyeccion to set
     */
    public void setMesesProyeccion(BigDecimal mesesProyeccion) {
        this.mesesProyeccion = mesesProyeccion;
    }

    /**
     * @return the gastosPersonalVestimenta
     */
    public BigDecimal getGastosPersonalVestimenta() {
        return gastosPersonalVestimenta;
    }

    /**
     * @param gastosPersonalVestimenta the gastosPersonalVestimenta to set
     */
    public void setGastosPersonalVestimenta(BigDecimal gastosPersonalVestimenta) {
        this.gastosPersonalVestimenta = gastosPersonalVestimenta;
    }

    /**
     * @return the gastosPersonalSalud
     */
    public BigDecimal getGastosPersonalSalud() {
        return gastosPersonalSalud;
    }

    /**
     * @param gastosPersonalSalud the gastosPersonalSalud to set
     */
    public void setGastosPersonalSalud(BigDecimal gastosPersonalSalud) {
        this.gastosPersonalSalud = gastosPersonalSalud;
    }

    /**
     * @return the gastosPersonalAlimentacion
     */
    public BigDecimal getGastosPersonalAlimentacion() {
        return gastosPersonalAlimentacion;
    }

    /**
     * @param gastosPersonalAlimentacion the gastosPersonalAlimentacion to set
     */
    public void setGastosPersonalAlimentacion(BigDecimal gastosPersonalAlimentacion) {
        this.gastosPersonalAlimentacion = gastosPersonalAlimentacion;
    }

    /**
     * @return the gastosPersonalVivienda
     */
    public BigDecimal getGastosPersonalVivienda() {
        return gastosPersonalVivienda;
    }

    /**
     * @param gastosPersonalVivienda the gastosPersonalVivienda to set
     */
    public void setGastosPersonalVivienda(BigDecimal gastosPersonalVivienda) {
        this.gastosPersonalVivienda = gastosPersonalVivienda;
    }

    /**
     * @return the gastosPersonalEducacion
     */
    public BigDecimal getGastosPersonalEducacion() {
        return gastosPersonalEducacion;
    }

    /**
     * @param gastosPersonalEducacion the gastosPersonalEducacion to set
     */
    public void setGastosPersonalEducacion(BigDecimal gastosPersonalEducacion) {
        this.gastosPersonalEducacion = gastosPersonalEducacion;
    }

    /**
     * @return the gastosPersonalTotal
     */
    public BigDecimal getGastosPersonalTotal() {
        return gastosPersonalTotal;
    }

    /**
     * @param gastosPersonalTotal the gastosPersonalTotal to set
     */
    public void setGastosPersonalTotal(BigDecimal gastosPersonalTotal) {
        this.gastosPersonalTotal = gastosPersonalTotal;
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
     * @return the saldo
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the irRetenido
     */
    public BigDecimal getIrRetenido() {
        return irRetenido;
    }

    /**
     * @param irRetenido the irRetenido to set
     */
    public void setIrRetenido(BigDecimal irRetenido) {
        this.irRetenido = irRetenido;
    }

    /**
     * @return the irCausado
     */
    public BigDecimal getIrCausado() {
        return irCausado;
    }

    /**
     * @param irCausado the irCausado to set
     */
    public void setIrCausado(BigDecimal irCausado) {
        this.irCausado = irCausado;
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
     * @return the irCausadoMensual
     */
    public BigDecimal getIrCausadoMensual() {
        return irCausadoMensual;
    }

    /**
     * @param irCausadoMensual the irCausadoMensual to set
     */
    public void setIrCausadoMensual(BigDecimal irCausadoMensual) {
        this.irCausadoMensual = irCausadoMensual;
    }

    /**
     * @return the porcentajeImpuestoSobreFraccionBasica
     */
    public BigDecimal getPorcentajeImpuestoSobreFraccionBasica() {
        return porcentajeImpuestoSobreFraccionBasica;
    }

    /**
     * @param porcentajeImpuestoSobreFraccionBasica the
     * porcentajeImpuestoSobreFraccionBasica to set
     */
    public void setPorcentajeImpuestoSobreFraccionBasica(BigDecimal porcentajeImpuestoSobreFraccionBasica) {
        this.porcentajeImpuestoSobreFraccionBasica = porcentajeImpuestoSobreFraccionBasica;
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
     * @return the porcentajeImpuestoFraccionExcedente
     */
    public BigDecimal getPorcentajeImpuestoFraccionExcedente() {
        return porcentajeImpuestoFraccionExcedente;
    }

    /**
     * @param porcentajeImpuestoFraccionExcedente the
     * porcentajeImpuestoFraccionExcedente to set
     */
    public void setPorcentajeImpuestoFraccionExcedente(BigDecimal porcentajeImpuestoFraccionExcedente) {
        this.porcentajeImpuestoFraccionExcedente = porcentajeImpuestoFraccionExcedente;
    }

    /**
     * @return the ingresosOtrosEmpleadores
     */
    public BigDecimal getIngresosOtrosEmpleadores() {
        return ingresosOtrosEmpleadores;
    }

    /**
     * @param ingresosOtrosEmpleadores the ingresosOtrosEmpleadores to set
     */
    public void setIngresosOtrosEmpleadores(BigDecimal ingresosOtrosEmpleadores) {
        this.ingresosOtrosEmpleadores = ingresosOtrosEmpleadores;
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
