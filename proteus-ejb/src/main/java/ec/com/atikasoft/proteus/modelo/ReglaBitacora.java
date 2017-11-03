package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Identifica las reglas que se aplican al un movimiento especifico.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "reglas_bitacora", catalog = "sch_proteus")
@NamedQueries({@NamedQuery(name = ReglaBitacora.QUITAR_VIGENCIA_MOVIMIENTO, query =
    "UPDATE ReglaBitacora o SET o.vigente=false WHERE o.vigente=true AND o.movimiento.id=?1")})
public class ReglaBitacora extends EntidadBasica {

    /**
     * Nombre de la consulta para quitae la vigencia por movimiento.
     */
    public static final String QUITAR_VIGENCIA_MOVIMIENTO = "ReglaBitacora.quitaVigenciaPorMovimiento";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Mensaje del incumplimiento de la regla.
     */
    @Column(name = "mensaje")
    private String mensaje;

    /**
     * Referencia a tipo de movimiento x regla.
     */
    @JoinColumn(name = "tipos_movimientos_reglas_id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private TipoMovimientoRegla tipoMovimientoRegla;

    /**
     * Referencia a movimiento,
     */
    @JoinColumn(name = "movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public ReglaBitacora() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public ReglaBitacora(final Long id) {
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
     * @return the tipoMovimientoRegla
     */
    public TipoMovimientoRegla getTipoMovimientoRegla() {
        return tipoMovimientoRegla;
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
     * @param tipoMovimientoRegla the tipoMovimientoRegla to set
     */
    public void setTipoMovimientoRegla(final TipoMovimientoRegla tipoMovimientoRegla) {
        this.tipoMovimientoRegla = tipoMovimientoRegla;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
}
