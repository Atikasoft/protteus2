package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Registra los tipo de movimientos dependientes.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "tipos_movimientos_precedencias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoPrecedencia.BUSCAR_POR_TIPO_MOVIMIENTO, query =
    "SELECT o FROM TipoMovimientoPrecedencia o WHERE o.vigente=true AND o.tipoMovimiento.id=?1 ORDER BY o.ordinal ASC"),
    @NamedQuery(name = TipoMovimientoPrecedencia.BUSCAR_POR_TIPO_MOVIMIENTO_Y_DEPENDIENTE, query =
    "SELECT o FROM TipoMovimientoPrecedencia o WHERE o.vigente=true AND o.tipoMovimiento.id=?1 "
    + "and o.tipoMovimientoDependiente.id=?2 ORDER BY o.ordinal ASC")
})
public class TipoMovimientoPrecedencia extends EntidadBasica {

    /**
     * Nombre de la consulta que recupera los tipos de movimientos precedentes.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO = "TipoMovimientoPrecedencia.buscarPorTipoMovimiento";

    /**
     * Nombre de la consulta que recupera los tipos de movimientos precedentes.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_Y_DEPENDIENTE = "TipoMovimientoPrecedencia.buscarPorTipoMovimientoYDependiente";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ordinal")
    private Integer ordinal;

    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    @JoinColumn(name = "tipos_movimientos_dependiente_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY )
    private TipoMovimiento tipoMovimientoDependiente;

    /**
     * Constructor.
     */
    public TipoMovimientoPrecedencia() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public TipoMovimientoPrecedencia(final Long id) {
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
     * @return the ordinal
     */
    public Integer getOrdinal() {
        return ordinal;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @return the tipoMovimientoDependiente
     */
    public TipoMovimiento getTipoMovimientoDependiente() {
        return tipoMovimientoDependiente;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param ordinal the ordinal to set
     */
    public void setOrdinal(final Integer ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @param tipoMovimientoDependiente the tipoMovimientoDependiente to set
     */
    public void setTipoMovimientoDependiente(final TipoMovimiento tipoMovimientoDependiente) {
        this.tipoMovimientoDependiente = tipoMovimientoDependiente;
    }
}
