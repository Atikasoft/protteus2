package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * situacion propuesta de movimientos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "movimientos_situacion_propuesta", catalog = "sch_proteus")
public class MovimientoSituacionPropuesta extends EntidadBasica {

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Proceso.
     */
    @Column(name = "proceso")
    private String proceso;

    /**
     * Subproceso.
     */
    @Column(name = "subproceso")
    private String subproceso;

    /**
     * Puesto.
     */
    @Column(name = "puesto")
    private String puesto;

    /**
     * Lugar de trabajo
     */
    @Column(name = "lugar_trabajo")
    private String lugarTrabajo;

    /**
     * Campo partida presupuestaria.
     */
    @Column(name = "partida_presupuestaria")
    private String partidaPresupuestaria;

    /**
     * Campo rmu.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

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
    public MovimientoSituacionPropuesta() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MovimientoSituacionPropuesta(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "movimiento", "distributivoDetalle");
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @return the subproceso
     */
    public String getSubproceso() {
        return subproceso;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @return the lugarTrabajo
     */
    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @return the distributivoDetalleId
     */
    public Long getDistributivoDetalleId() {
        return distributivoDetalleId;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @return the movimientoId
     */
    public Long getMovimientoId() {
        return movimientoId;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(final String proceso) {
        this.proceso = proceso;
    }

    /**
     * @param subproceso the subproceso to set
     */
    public void setSubproceso(final String subproceso) {
        this.subproceso = subproceso;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(final String puesto) {
        this.puesto = puesto;
    }

    /**
     * @param lugarTrabajo the lugarTrabajo to set
     */
    public void setLugarTrabajo(final String lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @param distributivoDetalleId the distributivoDetalleId to set
     */
    public void setDistributivoDetalleId(final Long distributivoDetalleId) {
        this.distributivoDetalleId = distributivoDetalleId;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @param movimientoId the movimientoId to set
     */
    public void setMovimientoId(final Long movimientoId) {
        this.movimientoId = movimientoId;
    }

    /**
     * @return the partidaPresupuestaria
     */
    public String getPartidaPresupuestaria() {
        return partidaPresupuestaria;
    }

    /**
     * @param partidaPresupuestaria the partidaPresupuestaria to set
     */
    public void setPartidaPresupuestaria(final String partidaPresupuestaria) {
        this.partidaPresupuestaria = partidaPresupuestaria;
    }
}
