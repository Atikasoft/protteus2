package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * situacion actual de movimientos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "movimientos_situacion_actual", catalog = "sch_proteus")
public class MovimientoSituacionActual extends EntidadBasica {

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "apellidos")
    private String apellidos;

    /**
     *
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     *
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;

    /**
     * Campo partidaIndividual.
     */
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * Campo rmu.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * Campo rmuOriginal.
     */
    @Column(name = "rmu_original")
    private BigDecimal rmuOriginal;

    /**
     * Campo sueldo básico.
     */
    @Column(name = "sueldo_basico")
    private BigDecimal sueldoBasico;

    /**
     * Campo rmuEscala.
     */
    @Column(name = "rmu_escala")
    private BigDecimal rmuEscala;

    /**
     * Campo rmuSobrevalorado.
     */
    @Column(name = "rmu_sobrevalorado")
    private BigDecimal rmuSobrevalorado;

    /**
     * Campo rmuSobrevalorado.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    /**
     * Campo fechaFin.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    /**
     * Campo fechaIngreso.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    /**
     * Campo tipoComisionServicio.
     */
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;

    /**
     * Campo fechaInicioComisionServicio.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_comision_servicio")
    private Date fechaInicioComisionServicio;

    /**
     * Campo fechaFinComisionServicio.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin_comision_servicio")
    private Date fechaFinComisionServicio;

    /**
     * Referencia del puesto.
     */
    @Column(name = "distributivo_detalle_id")
    private Long distributivoDetalleId;

    /**
     *
     */
    @JoinColumn(name = "distributivo_detalle_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalle;

    /**
     * Referencia ServidorComisionServicio.
     */
    @JoinColumn(name = "servidor_comision_servicio_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorComisionServicio;

    /**
     * Referencia servidorComisionServicio.
     */
    @Column(name = "servidor_comision_servicio_id")
    private Long idServidorComisionServicio;

    /**
     * Referencia Escalas Ocupacionales.
     */
    @JoinColumn(name = "escala_ocupacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EscalaOcupacional escalaOcupacional;

    /**
     * Referencia Escalas Ocupacionales.
     */
    @Column(name = "escala_ocupacional_id")
    private Long idEscalaOcupacional;

    /**
     * Referencia denominacionPuesto.
     */
    @JoinColumn(name = "denominacion_puesto_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DenominacionPuesto denominacionPuesto;

    /**
     * Referencia denominacionPuesto.
     */
    @Column(name = "denominacion_puesto_id")
    private Long idDenominacionPuesto;

    /**
     * Referencia estadoPuesto.
     */
    @JoinColumn(name = "estado_puesto_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPuesto estadoPuesto;

    /**
     * Referencia estadoPuesto.
     */
    @Column(name = "estado_puesto_id")
    private Long idEstadoPuesto;

    /**
     * Referencia estadoServidor.
     */
    @JoinColumn(name = "estado_servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPersonal estadoPersonal;

    /**
     * Referencia estadoServidor.
     */
    @Column(name = "estado_servidor_id")
    private Long idEstadoPersonal;

    /**
     * Referencia ubicacionGeografica.
     */
    @JoinColumn(name = "ubicacion_geografica_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeografica;

    /**
     * Referencia ubicacionGeografica.
     */
    @Column(name = "ubicacion_geografica_id")
    private Long idUbicacionGeografica;

    /**
     * Referencia servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Referencia servidor.
     */
    @Column(name = "servidor_id")
    private Long idServidor;

    /**
     * Referencia con tramite
     */
    @OneToOne
    @JoinColumn(name = "movimiento_id", insertable = false, updatable = false)
    private Movimiento movimiento;

    /**
     *
     */
    @Column(name = "movimiento_id")
    private Long movimientoId;

    /**
     * Constructor.
     */
    public MovimientoSituacionActual() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MovimientoSituacionActual(final Long id) {
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
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @return the rmuOriginal
     */
    public BigDecimal getRmuOriginal() {
        return rmuOriginal;
    }

    /**
     * @return the sueldoBasico
     */
    public BigDecimal getSueldoBasico() {
        return sueldoBasico;
    }

    /**
     * @return the rmuEscala
     */
    public BigDecimal getRmuEscala() {
        return rmuEscala;
    }

    /**
     * @return the rmuSobrevalorado
     */
    public BigDecimal getRmuSobrevalorado() {
        return rmuSobrevalorado;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @return the tipoComisionServicio
     */
    public String getTipoComisionServicio() {
        return tipoComisionServicio;
    }

    /**
     * @return the fechaInicioComisionServicio
     */
    public Date getFechaInicioComisionServicio() {
        return fechaInicioComisionServicio;
    }

    /**
     * @return the fechaFinComisionServicio
     */
    public Date getFechaFinComisionServicio() {
        return fechaFinComisionServicio;
    }

    /**
     * @return the distributivoDetalleId
     */
    public Long getDistributivoDetalleId() {
        return distributivoDetalleId;
    }

    /**
     * @return the servidorComisionServicio
     */
    public Servidor getServidorComisionServicio() {
        return servidorComisionServicio;
    }

    /**
     * @return the idServidorComisionServicio
     */
    public Long getIdServidorComisionServicio() {
        return idServidorComisionServicio;
    }

    /**
     * @return the escalaOcupacional
     */
    public EscalaOcupacional getEscalaOcupacional() {
        return escalaOcupacional;
    }

    /**
     * @return the idEscalaOcupacional
     */
    public Long getIdEscalaOcupacional() {
        return idEscalaOcupacional;
    }

    /**
     * @return the denominacionPuesto
     */
    public DenominacionPuesto getDenominacionPuesto() {
        return denominacionPuesto;
    }

    /**
     * @return the idDenominacionPuesto
     */
    public Long getIdDenominacionPuesto() {
        return idDenominacionPuesto;
    }

    /**
     * @return the estadoPuesto
     */
    public EstadoPuesto getEstadoPuesto() {
        return estadoPuesto;
    }

    /**
     * @return the idEstadoPuesto
     */
    public Long getIdEstadoPuesto() {
        return idEstadoPuesto;
    }

    /**
     * @return the estadoPersonal
     */
    public EstadoPersonal getEstadoPersonal() {
        return estadoPersonal;
    }

    /**
     * @return the idEstadoPersonal
     */
    public Long getIdEstadoPersonal() {
        return idEstadoPersonal;
    }

    /**
     * @return the ubicacionGeografica
     */
    public UbicacionGeografica getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @return the idUbicacionGeografica
     */
    public Long getIdUbicacionGeografica() {
        return idUbicacionGeografica;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @return the idServidor
     */
    public Long getIdServidor() {
        return idServidor;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(final String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    /**
     * @param apellidosNombres the apellidosNombres to set
     */
    public void setApellidosNombres(final String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(final String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @param rmuOriginal the rmuOriginal to set
     */
    public void setRmuOriginal(final BigDecimal rmuOriginal) {
        this.rmuOriginal = rmuOriginal;
    }

    /**
     * @param sueldoBasico the sueldoBasico to set
     */
    public void setSueldoBasico(final BigDecimal sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    /**
     * @param rmuEscala the rmuEscala to set
     */
    public void setRmuEscala(final BigDecimal rmuEscala) {
        this.rmuEscala = rmuEscala;
    }

    /**
     * @param rmuSobrevalorado the rmuSobrevalorado to set
     */
    public void setRmuSobrevalorado(final BigDecimal rmuSobrevalorado) {
        this.rmuSobrevalorado = rmuSobrevalorado;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(final Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @param tipoComisionServicio the tipoComisionServicio to set
     */
    public void setTipoComisionServicio(final String tipoComisionServicio) {
        this.tipoComisionServicio = tipoComisionServicio;
    }

    /**
     * @param fechaInicioComisionServicio the fechaInicioComisionServicio to set
     */
    public void setFechaInicioComisionServicio(final Date fechaInicioComisionServicio) {
        this.fechaInicioComisionServicio = fechaInicioComisionServicio;
    }

    /**
     * @param fechaFinComisionServicio the fechaFinComisionServicio to set
     */
    public void setFechaFinComisionServicio(final Date fechaFinComisionServicio) {
        this.fechaFinComisionServicio = fechaFinComisionServicio;
    }

    /**
     * @param distributivoDetalleId the distributivoDetalleId to set
     */
    public void setDistributivoDetalleId(final Long distributivoDetalleId) {
        this.distributivoDetalleId = distributivoDetalleId;
    }

    /**
     * @param servidorComisionServicio the servidorComisionServicio to set
     */
    public void setServidorComisionServicio(final Servidor servidorComisionServicio) {
        this.servidorComisionServicio = servidorComisionServicio;
    }

    /**
     * @param idServidorComisionServicio the idServidorComisionServicio to set
     */
    public void setIdServidorComisionServicio(final Long idServidorComisionServicio) {
        this.idServidorComisionServicio = idServidorComisionServicio;
    }

    /**
     * @param escalaOcupacional the escalaOcupacional to set
     */
    public void setEscalaOcupacional(final EscalaOcupacional escalaOcupacional) {
        this.escalaOcupacional = escalaOcupacional;
    }

    /**
     * @param idEscalaOcupacional the idEscalaOcupacional to set
     */
    public void setIdEscalaOcupacional(final Long idEscalaOcupacional) {
        this.idEscalaOcupacional = idEscalaOcupacional;
    }

    /**
     * @param denominacionPuesto the denominacionPuesto to set
     */
    public void setDenominacionPuesto(final DenominacionPuesto denominacionPuesto) {
        this.denominacionPuesto = denominacionPuesto;
    }

    /**
     * @param idDenominacionPuesto the idDenominacionPuesto to set
     */
    public void setIdDenominacionPuesto(final Long idDenominacionPuesto) {
        this.idDenominacionPuesto = idDenominacionPuesto;
    }

    /**
     * @param estadoPuesto the estadoPuesto to set
     */
    public void setEstadoPuesto(final EstadoPuesto estadoPuesto) {
        this.estadoPuesto = estadoPuesto;
    }

    /**
     * @param idEstadoPuesto the idEstadoPuesto to set
     */
    public void setIdEstadoPuesto(final Long idEstadoPuesto) {
        this.idEstadoPuesto = idEstadoPuesto;
    }

    /**
     * @param estadoPersonal the estadoPersonal to set
     */
    public void setEstadoPersonal(final EstadoPersonal estadoPersonal) {
        this.estadoPersonal = estadoPersonal;
    }

    /**
     * @param idEstadoPersonal the idEstadoPersonal to set
     */
    public void setIdEstadoPersonal(final Long idEstadoPersonal) {
        this.idEstadoPersonal = idEstadoPersonal;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(final UbicacionGeografica ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    /**
     * @param idUbicacionGeografica the idUbicacionGeografica to set
     */
    public void setIdUbicacionGeografica(final Long idUbicacionGeografica) {
        this.idUbicacionGeografica = idUbicacionGeografica;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @param idServidor the idServidor to set
     */
    public void setIdServidor(final Long idServidor) {
        this.idServidor = idServidor;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the movimientoId
     */
    public Long getMovimientoId() {
        return movimientoId;
    }

    /**
     * @param movimientoId the movimientoId to set
     */
    public void setMovimientoId(final Long movimientoId) {
        this.movimientoId = movimientoId;
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
}
