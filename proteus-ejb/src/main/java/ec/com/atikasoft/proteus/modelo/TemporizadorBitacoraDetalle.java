package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;

/**
 * Contiene el detalles de la bitacora de la ejecucion de temporizadores.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "temporizadores_bitacora_detalle", catalog = "sch_proteus")
public class TemporizadorBitacoraDetalle extends EntidadBasica {

    /**
     *
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
     * Referencia a la bitacora del temporizador.
     */
    @JoinColumn(name = "temporizador_bitacora_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TemporizadorBitacora temporizadorBitacora;

    /**
     * Referencia del movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public TemporizadorBitacoraDetalle() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the temporizadorBitacora
     */
    public TemporizadorBitacora getTemporizadorBitacora() {
        return temporizadorBitacora;
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
     * @param temporizadorBitacora the temporizadorBitacora to set
     */
    public void setTemporizadorBitacora(final TemporizadorBitacora temporizadorBitacora) {
        this.temporizadorBitacora = temporizadorBitacora;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}
