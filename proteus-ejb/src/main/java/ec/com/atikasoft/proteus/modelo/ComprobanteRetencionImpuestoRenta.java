/*
 *  ComprobanteRetencionImpuestoRenta.java
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
 *  18/02/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Formulario 107 para nuevos Servidores
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@Entity
@Table(name = "comprobante_retenciones_ir", catalog = "sch_proteus")
public class ComprobanteRetencionImpuestoRenta extends EntidadBasica {

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Indica el ejercicio fiscal del Impuesto a la renta.
     */
    @JoinColumn(name = "ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EjercicioFiscal ejercicioFiscal;

    @JoinColumn(name = "servidor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false)
    protected Date fechaRegistro;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_entrega", nullable = false)
    protected Date fechaEntrega;

    @Column(name = "ruc_empleador", length = 255)
    private String rucEmpleador;

    @Column(name = "nombre_empleador", length = 255)
    private String nombreEmpleador;

    /**
     * Sueldos y Salarios 301
     */
    @Column(name = "sueldos_salarios")
    private BigDecimal sueldosSalarios;

    /**
     * Sobre Sueldos 303
     */
    @Column(name = "sobre_sueldos")
    private BigDecimal sobreSueldos;

    /**
     * Participacion Utilidades 305
     */
    @Column(name = "participacion_utilidades")
    private BigDecimal participacionUtilidades;

    /**
     * Ingresos Gravados Otro Empleador 307
     */
    @Column(name = "ingresos_otro_empleador")
    private BigDecimal ingresosOtroEmpleador;

    /**
     * Decimo Tercero 311
     */
    @Column(name = "decimo_tercero")
    private BigDecimal decimoTercero;

    /**
     * Decimo Cuarto 313
     */
    @Column(name = "decimo_cuarto")
    private BigDecimal decimoCuarto;

    /**
     * Fondo Reserva 315
     */
    @Column(name = "fondo_reserva")
    private BigDecimal fondoReserva;

    /**
     * Otros Ingresos que no constituyen Renta Gravada 317
     */
    @Column(name = "otros_ingresos")
    private BigDecimal otrosIngresos;

    /**
     * Aporte Personal IEES este empleador 351
     */
    @Column(name = "aporte_personal")
    private BigDecimal aportePersonal;

    /**
     * Aporte Personal IEES con otros empleadores 353
     */
    @Column(name = "aporte_personal_otro_empleador")
    private BigDecimal aportePersonalOtroEmpleador;

    /**
     * Gastos Vivienda 361
     */
    @Column(name = "vivienda")
    private BigDecimal vivienda;

    /**
     * Gastos Salud 363
     */
    @Column(name = "salud")
    private BigDecimal salud;

    /**
     * Gastos Educacion 365
     */
    @Column(name = "educacion")
    private BigDecimal educacion;

    /**
     * Gastos Alimentacion 367
     */
    @Column(name = "alimentacion")
    private BigDecimal alimentacion;

    /**
     * Gastos Vestimenta 369
     */
    @Column(name = "vestimenta")
    private BigDecimal vestimenta;

    /**
     * Exoneracion por discapacidad 371
     */
    @Column(name = "exoneracion_discapacidad")
    private BigDecimal exoneracionDiscapacidad;

    /**
     * Exoneracion por tercera edad 373
     */
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;

    /**
     * Exoneracion por tercera edad 381
     */
    @Column(name = "impuesto_renta")
    private BigDecimal impuestoRenta;

    /**
     * Impuesto a la Renta 401
     */
    @Column(name = "impuesto_renta_causado")
    private BigDecimal impuestoRentaCausado;

    /**
     * Impuesto a la Renta retenido y asumido por otros empleadores 403
     */
    @Column(name = "impuesto_retenido_otro_empleador")
    private BigDecimal impuestoRetenidoOtroEmpleador;

    /**
     * Impuesto asumido por este empleador 405
     */
    @Column(name = "impuesto_asumido")
    private BigDecimal impuestoAsumido;

    /**
     * Impuesto retenido por este empleador 407
     */
    @Column(name = "impuesto_retenido")
    private BigDecimal impuestoRetenido;

    @JoinColumn(name = "archivo_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Archivo archivo;


    @Column(name = "ruc_contador", length = 255)
    private String rucContador;

    /**
     * Base Imponible Gravada 399
     */
    @Transient
    private BigDecimal baseImponibleGravada;

    /**
     * Ingresos Gravados 349
     */
    @Transient
    private BigDecimal ingresosGravados;

    /**
     * Constructor.
     */
    public ComprobanteRetencionImpuestoRenta() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ComprobanteRetencionImpuestoRenta(final Long id) {
        super();
        this.id = id;
    }

    /**
     * Obtiene una instancia nueva de ComprobanteRetencionImpuestoRenta
     *
     * @return
     */
    public static ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRentaFabrica() {
        ComprobanteRetencionImpuestoRenta obj = new ComprobanteRetencionImpuestoRenta();
        obj.setAlimentacion(BigDecimal.ZERO);
        obj.setAportePersonal(BigDecimal.ZERO);
        obj.setAportePersonalOtroEmpleador(BigDecimal.ZERO);
        obj.setBaseImponibleGravada(BigDecimal.ZERO);
        obj.setDecimoCuarto(BigDecimal.ZERO);
        obj.setDecimoTercero(BigDecimal.ZERO);
        obj.setEducacion(BigDecimal.ZERO);
        obj.setExoneracionDiscapacidad(BigDecimal.ZERO);
        obj.setExoneracionTerceraEdad(BigDecimal.ZERO);
        obj.setFondoReserva(BigDecimal.ZERO);
        obj.setImpuestoAsumido(BigDecimal.ZERO);
        obj.setImpuestoRenta(BigDecimal.ZERO);
        obj.setImpuestoRentaCausado(BigDecimal.ZERO);
        obj.setImpuestoRetenido(BigDecimal.ZERO);
        obj.setImpuestoRetenidoOtroEmpleador(BigDecimal.ZERO);
        obj.setIngresosGravados(BigDecimal.ZERO);
        obj.setIngresosOtroEmpleador(BigDecimal.ZERO);
        obj.setOtrosIngresos(BigDecimal.ZERO);
        obj.setParticipacionUtilidades(BigDecimal.ZERO);
        obj.setSalud(BigDecimal.ZERO);
        obj.setSobreSueldos(BigDecimal.ZERO);
        obj.setSueldosSalarios(BigDecimal.ZERO);
        obj.setVestimenta(BigDecimal.ZERO);
        obj.setVivienda(BigDecimal.ZERO);
        return obj;
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
     * @return the ejercicioFiscal
     */
    public EjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(EjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getRucEmpleador() {
        return rucEmpleador;
    }

    public void setRucEmpleador(String rucEmpleador) {
        this.rucEmpleador = rucEmpleador;
    }

    public String getNombreEmpleador() {
        return nombreEmpleador;
    }

    public void setNombreEmpleador(String nombreEmpleador) {
        this.nombreEmpleador = nombreEmpleador;
    }

    public BigDecimal getSueldosSalarios() {
        return sueldosSalarios;
    }

    public void setSueldosSalarios(BigDecimal sueldosSalarios) {
        this.sueldosSalarios = sueldosSalarios;
    }

    public BigDecimal getSobreSueldos() {
        return sobreSueldos;
    }

    public void setSobreSueldos(BigDecimal sobreSueldos) {
        this.sobreSueldos = sobreSueldos;
    }

    public BigDecimal getParticipacionUtilidades() {
        return participacionUtilidades;
    }

    public void setParticipacionUtilidades(BigDecimal participacionUtilidades) {
        this.participacionUtilidades = participacionUtilidades;
    }

    public BigDecimal getIngresosOtroEmpleador() {
        return ingresosOtroEmpleador;
    }

    public void setIngresosOtroEmpleador(BigDecimal ingresosOtroEmpleador) {
        this.ingresosOtroEmpleador = ingresosOtroEmpleador;
    }

    public BigDecimal getDecimoTercero() {
        return decimoTercero;
    }

    public void setDecimoTercero(BigDecimal decimoTercero) {
        this.decimoTercero = decimoTercero;
    }

    public BigDecimal getDecimoCuarto() {
        return decimoCuarto;
    }

    public void setDecimoCuarto(BigDecimal decimoCuarto) {
        this.decimoCuarto = decimoCuarto;
    }

    public BigDecimal getFondoReserva() {
        return fondoReserva;
    }

    public void setFondoReserva(BigDecimal fondoReserva) {
        this.fondoReserva = fondoReserva;
    }

    public BigDecimal getOtrosIngresos() {
        return otrosIngresos;
    }

    public void setOtrosIngresos(BigDecimal otrosIngresos) {
        this.otrosIngresos = otrosIngresos;
    }

    public BigDecimal getAportePersonal() {
        return aportePersonal;
    }

    public void setAportePersonal(BigDecimal aportePersonal) {
        this.aportePersonal = aportePersonal;
    }

    public BigDecimal getAportePersonalOtroEmpleador() {
        return aportePersonalOtroEmpleador;
    }

    public void setAportePersonalOtroEmpleador(BigDecimal aportePersonalOtroEmpleador) {
        this.aportePersonalOtroEmpleador = aportePersonalOtroEmpleador;
    }

    public BigDecimal getVivienda() {
        return vivienda;
    }

    public void setVivienda(BigDecimal vivienda) {
        this.vivienda = vivienda;
    }

    public BigDecimal getSalud() {
        return salud;
    }

    public void setSalud(BigDecimal salud) {
        this.salud = salud;
    }

    public BigDecimal getEducacion() {
        return educacion;
    }

    public void setEducacion(BigDecimal educacion) {
        this.educacion = educacion;
    }

    public BigDecimal getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(BigDecimal alimentacion) {
        this.alimentacion = alimentacion;
    }

    public BigDecimal getVestimenta() {
        return vestimenta;
    }

    public void setVestimenta(BigDecimal vestimenta) {
        this.vestimenta = vestimenta;
    }

    public BigDecimal getExoneracionDiscapacidad() {
        return exoneracionDiscapacidad;
    }

    public void setExoneracionDiscapacidad(BigDecimal exoneracionDiscapacidad) {
        this.exoneracionDiscapacidad = exoneracionDiscapacidad;
    }

    public BigDecimal getExoneracionTerceraEdad() {
        return exoneracionTerceraEdad;
    }

    public void setExoneracionTerceraEdad(BigDecimal exoneracionTerceraEdad) {
        this.exoneracionTerceraEdad = exoneracionTerceraEdad;
    }

    public BigDecimal getImpuestoRenta() {
        return impuestoRenta;
    }

    public void setImpuestoRenta(BigDecimal impuestoRenta) {
        this.impuestoRenta = impuestoRenta;
    }

    public BigDecimal getImpuestoRentaCausado() {
        return impuestoRentaCausado;
    }

    public void setImpuestoRentaCausado(BigDecimal impuestoRentaCausado) {
        this.impuestoRentaCausado = impuestoRentaCausado;
    }

    public BigDecimal getImpuestoRetenidoOtroEmpleador() {
        return impuestoRetenidoOtroEmpleador;
    }

    public void setImpuestoRetenidoOtroEmpleador(BigDecimal impuestoRetenidoOtroEmpleador) {
        this.impuestoRetenidoOtroEmpleador = impuestoRetenidoOtroEmpleador;
    }

    public BigDecimal getImpuestoAsumido() {
        return impuestoAsumido;
    }

    public void setImpuestoAsumido(BigDecimal impuestoAsumido) {
        this.impuestoAsumido = impuestoAsumido;
    }

    public BigDecimal getImpuestoRetenido() {
        return impuestoRetenido;
    }

    public void setImpuestoRetenido(BigDecimal impuestoRetenido) {
        this.impuestoRetenido = impuestoRetenido;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public String getRucContador() {
        return rucContador;
    }

    public void setRucContador(String rucContador) {
        this.rucContador = rucContador;
    }

    public BigDecimal getBaseImponibleGravada() {
        return baseImponibleGravada;
    }

    public void setBaseImponibleGravada(BigDecimal baseImponibleGravada) {
        this.baseImponibleGravada = baseImponibleGravada;
    }

    public BigDecimal getIngresosGravados() {
        return ingresosGravados;
    }

    public void setIngresosGravados(BigDecimal ingresosGravados) {
        this.ingresosGravados = ingresosGravados;
    }
}
