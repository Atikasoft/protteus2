package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "finalizaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Finalizacion.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Finalizacion o where o.vigente=true and o.movimiento.id=?1")
})
public class Finalizacion extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Finalizacion.buscarPorMovimientoId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia del movimiento.
     */
    @JoinColumn(name = "movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Referencia del movimiento inicial.
     */
    @JoinColumn(name = "movimiento_inicial_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimientoInicial;

    /**
     * Constructor.
     */
    public Finalizacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Finalizacion(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the movimientoInicial
     */
    public Movimiento getMovimientoInicial() {
        return movimientoInicial;
    }

    /**
     * @param movimientoInicial the movimientoInicial to set
     */
    public void setMovimientoInicial(final Movimiento movimientoInicial) {
        this.movimientoInicial = movimientoInicial;
    }
}
