package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de movimientos por subrogaciones.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "subrogaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Subrogacion.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Subrogacion o where o.vigente=true and o.movimiento.id=?1")
})
public class Subrogacion extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Subrogacion.buscarPorMovimiento";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del servidor x puesto a subrogar.
     *
     *
     */
    @Column(name = "servidor_puesto_id")
    private Long servidorPuestoId;

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public Subrogacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Subrogacion(final Long id) {
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
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the servidorPuestoId
     */
    public Long getServidorPuestoId() {
        return servidorPuestoId;
    }

    /**
     * @param servidorPuestoId the servidorPuestoId to set
     */
    public void setServidorPuestoId(final Long servidorPuestoId) {
        this.servidorPuestoId = servidorPuestoId;
    }
}
