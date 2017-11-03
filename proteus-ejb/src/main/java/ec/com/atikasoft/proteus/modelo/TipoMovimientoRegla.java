package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "tipos_movimientos_reglas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoRegla.BUSCAR_POR_TIPO_MOVIMIENTOS, query =
    "SELECT o FROM TipoMovimientoRegla o WHERE o.vigente=true AND o.tipoMovimiento.id=?1")
})
public class TipoMovimientoRegla extends EntidadBasica {

    /**
     * Nombre de la consulta que recupera las reglas por tipo de movimiento.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTOS = "TipoMovimientoRegla.buscarPorTipoMovimiento";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Obligatorio.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "obligatorio")
    private boolean obligatorio;

    /**
     * Entidad de tipo de movimiento.
     */
    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    /**
     * Entidad de reglas.
     */
    @JoinColumn(name = "reglas_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Regla regla;

    /**
     * Justificable.
     */
    @Basic(optional = false)
    @Column(name = "justificable")
    private boolean justificable;

    /**
     * *
     * Constructor.
     */
    public TipoMovimientoRegla() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id id
     */
    public TipoMovimientoRegla(final Long id) {
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
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the obligatorio
     */
    public boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(final boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the regla
     */
    public Regla getRegla() {
        return regla;
    }

    /**
     * @param regla the regla to set
     */
    public void setRegla(final Regla regla) {
        this.regla = regla;
    }

    /**
     * @return the justificable
     */
    public boolean getJustificable() {
        return justificable;
    }

    /**
     * @param justificable the justificable to set
     */
    public void setJustificable(final boolean justificable) {
        this.justificable = justificable;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
