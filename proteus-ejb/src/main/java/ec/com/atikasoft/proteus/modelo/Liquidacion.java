/*
 *  Liquidacion.java
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
 *  10/02/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "liquidaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Liquidacion.BUSCAR_VIGENTES, query = "SELECT a FROM Liquidacion a where a.vigente=true order by a.fechaCreacion"),
    @NamedQuery(name = Liquidacion.BUSCAR_POR_ESTADO, query = "SELECT a FROM Liquidacion a where a.vigente=true and a.nomina is null and a.estado = ?1 order by a.servidor.apellidos "),
    @NamedQuery(name = Liquidacion.BUSCAR_POR_SERVIDOR, query = "SELECT a FROM Liquidacion a where a.vigente=true and a.servidor.id = ?1 order by a.fechaCreacion desc"),
    @NamedQuery(name = Liquidacion.BUSCAR_UNIDAD_PRESUPUESTARIA_SOCIEDAD, query = "SELECT a.distributivoDetalle.unidadPresupuestaria FROM Liquidacion a where a.vigente=true and a.distributivoDetalle.unidadPresupuestaria.sociedad=?1 and a.servidor.numeroIdentificacion = ?2 order by a.fechaCreacion desc"),
    @NamedQuery(name = Liquidacion.QUITAR_NOMINA, query = "UPDATE Liquidacion a SET a.nominaId=null WHERE a.vigente=true AND a.nominaId=?1"),
    @NamedQuery(name = Liquidacion.QUITAR_NOMINA_SERVIDOR, query = "UPDATE Liquidacion a SET a.nominaId=null WHERE a.vigente=true AND a.nominaId=?1 AND a.servidorId=?2")

})
public class Liquidacion extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Liquidacion.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_SERVIDOR = "Liquidacion.buscarPorServidor";
    /**
     * Nombre para busqueda de vigentes en un estado específico.
     */
    public static final String BUSCAR_POR_ESTADO = "Liquidacion.buscarPorEstado";
    /**
     * Nombre para obtener las unidadesPresupuestarias por sociedad.
     */
    public static final String BUSCAR_UNIDAD_PRESUPUESTARIA_SOCIEDAD = "Liquidacion.buscarPorSociedad";

    /**
     *
     */
    public static final String QUITAR_NOMINA = "Liquidacion.quitarNomina";

    /**
     *
     */
    public static final String QUITAR_NOMINA_SERVIDOR = "Liquidacion.quitarNominaServidor";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Referencia a Servidor
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     * Referencia a Nomina.
     */
    @JoinColumn(name = "nomina_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Nomina nomina;

    /**
     * Referencia a Nomina.
     */
    @Column(name = "nomina_id")
    private Long nominaId;

    /**
     * Campo estado: <R>egistrado.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;

    /**
     * Registra el número de dias pendientes de vacaciones.
     */
    @Column(name = "num_dias_saldo_vacaciones")
    @NotNull
    private BigDecimal numeroDiasSaldoVacaciones;

    /**
     * Registra el número de dias trabajados en el ultimo año, contando desde
     * enero.
     */
    @Column(name = "num_dias_trabajados_anio")
    @NotNull
    private Integer numeroDiasTrabajadosAnio;

    /**
     * Registra el número de meses trabajados en el ultimo año, contando desde
     * enero.
     */
    @Column(name = "num_meses_trabajados_anio")
    @NotNull
    private Integer numeroMesesTrabajadosAnio;

    /**
     * Registra el número de dias trabajados en el ultimo año laboral.
     */
    @Column(name = "num_dias_trabajados_laboral")
    @NotNull
    private Integer numeroDiasTrabajadosLaboral;

    /**
     * Registra el número de meses trabajados en el ultimo año laboral.
     */
    @Column(name = "num_meses_trabajados_laboral")
    @NotNull
    private Integer numeroMesesTrabajadosLaboral;

    /**
     * Registra el rmu del servidor el momento de la renuncia.
     */
    @Column(name = "sueldo")
    @NotNull
    private BigDecimal sueldo;
    /**
     * Registra el número de dias trabajados en el ultimo mes.
     */
    @Column(name = "num_dias_trabajados_ultimo_mes")
    @NotNull
    private Integer numeroDiasTrabajadosUltimoMes;

    /**
     * Registra el número de meses de servicio.
     */
    @Column(name = "num_meses_servicio")
    @NotNull
    private Integer numeroMesesServicio;
    /**
     * Indica si se debe o no pagar el mes completo.
     */
    @Column(name = "paga_mes_completo")
    @NotNull
    private Boolean pagaMesCompleto;
    /**
     * Distributivo Detall.
     */
    @JoinColumn(name = "distributivo_detalle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalle;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha_ingreso")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaIngreso;
    /**
     * Campo fecha.
     */
    @Column(name = "fecha_legalizacion")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaLegalizacion;
    /**
     * Referencia a InstitucionEjercicioFiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Indica si el puesto esta seleccionado.
     */
    @Transient
    private Boolean selecto;

    /**
     * Constructor.
     */
    public Liquidacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Liquidacion(final Long id) {
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
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the numeroDiasSaldoVacaciones
     */
    public BigDecimal getNumeroDiasSaldoVacaciones() {
        return numeroDiasSaldoVacaciones;
    }

    /**
     * @param numeroDiasSaldoVacaciones the numeroDiasSaldoVacaciones to set
     */
    public void setNumeroDiasSaldoVacaciones(BigDecimal numeroDiasSaldoVacaciones) {
        this.numeroDiasSaldoVacaciones = numeroDiasSaldoVacaciones;
    }

    /**
     * @return the numeroDiasTrabajadosAnio
     */
    public Integer getNumeroDiasTrabajadosAnio() {
        return numeroDiasTrabajadosAnio;
    }

    /**
     * @param numeroDiasTrabajadosAnio the numeroDiasTrabajadosAnio to set
     */
    public void setNumeroDiasTrabajadosAnio(Integer numeroDiasTrabajadosAnio) {
        this.numeroDiasTrabajadosAnio = numeroDiasTrabajadosAnio;
    }

    /**
     * @return the numeroMesesTrabajadosAnio
     */
    public Integer getNumeroMesesTrabajadosAnio() {
        return numeroMesesTrabajadosAnio;
    }

    /**
     * @param numeroMesesTrabajadosAnio the numeroMesesTrabajadosAnio to set
     */
    public void setNumeroMesesTrabajadosAnio(Integer numeroMesesTrabajadosAnio) {
        this.numeroMesesTrabajadosAnio = numeroMesesTrabajadosAnio;
    }

    /**
     * @return the sueldo
     */
    public BigDecimal getSueldo() {
        return sueldo;
    }

    /**
     * @param sueldo the sueldo to set
     */
    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    /**
     * @return the numeroDiasTrabajadosUltimoMes
     */
    public Integer getNumeroDiasTrabajadosUltimoMes() {
        return numeroDiasTrabajadosUltimoMes;
    }

    /**
     * @param numeroDiasTrabajadosUltimoMes the numeroDiasTrabajadosUltimoMes to
     * set
     */
    public void setNumeroDiasTrabajadosUltimoMes(Integer numeroDiasTrabajadosUltimoMes) {
        this.numeroDiasTrabajadosUltimoMes = numeroDiasTrabajadosUltimoMes;
    }

    /**
     * @return the numeroMesesServicio
     */
    public Integer getNumeroMesesServicio() {
        return numeroMesesServicio;
    }

    /**
     * @param numeroMesesServicio the numeroMesesServicio to set
     */
    public void setNumeroMesesServicio(Integer numeroMesesServicio) {
        this.numeroMesesServicio = numeroMesesServicio;
    }

    /**
     * @return the pagaMesCompleto
     */
    public Boolean getPagaMesCompleto() {
        return pagaMesCompleto;
    }

    /**
     * @param pagaMesCompleto the pagaMesCompleto to set
     */
    public void setPagaMesCompleto(Boolean pagaMesCompleto) {
        this.pagaMesCompleto = pagaMesCompleto;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaLegalizacion
     */
    public Date getFechaLegalizacion() {
        return fechaLegalizacion;
    }

    /**
     * @param fechaLegalizacion the fechaLegalizacion to set
     */
    public void setFechaLegalizacion(Date fechaLegalizacion) {
        this.fechaLegalizacion = fechaLegalizacion;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the numeroDiasTrabajadosLaboral
     */
    public Integer getNumeroDiasTrabajadosLaboral() {
        return numeroDiasTrabajadosLaboral;
    }

    /**
     * @param numeroDiasTrabajadosLaboral the numeroDiasTrabajadosLaboral to set
     */
    public void setNumeroDiasTrabajadosLaboral(Integer numeroDiasTrabajadosLaboral) {
        this.numeroDiasTrabajadosLaboral = numeroDiasTrabajadosLaboral;
    }

    /**
     * @return the numeroMesesTrabajadosLaboral
     */
    public Integer getNumeroMesesTrabajadosLaboral() {
        return numeroMesesTrabajadosLaboral;
    }

    /**
     * @param numeroMesesTrabajadosLaboral the numeroMesesTrabajadosLaboral to
     * set
     */
    public void setNumeroMesesTrabajadosLaboral(Integer numeroMesesTrabajadosLaboral) {
        this.numeroMesesTrabajadosLaboral = numeroMesesTrabajadosLaboral;
    }

    /**
     * @return the selecto
     */
    public Boolean getSelecto() {
        return selecto;
    }

    /**
     * @param selecto the selecto to set
     */
    public void setSelecto(Boolean selecto) {
        this.selecto = selecto;
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
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(Long nominaId) {
        this.nominaId = nominaId;
    }

}
