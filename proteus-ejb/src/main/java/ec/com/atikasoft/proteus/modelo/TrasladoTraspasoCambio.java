package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "traslados_traspasos_cambios", catalog = "sch_proteus")
public class TrasladoTraspasoCambio extends EntidadBasica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public TrasladoTraspasoCambio() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public TrasladoTraspasoCambio(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
