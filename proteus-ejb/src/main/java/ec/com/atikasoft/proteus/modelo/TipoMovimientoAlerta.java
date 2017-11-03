package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "tipos_movimientos_alertas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoAlerta.BUSCAR_POR_TIPO_MOVIMIENTO_ID,
    query = "SELECT tma FROM TipoMovimientoAlerta tma where tma.tipoMovimiento.id=?1 and tma.vigente=true")
})
public class TipoMovimientoAlerta extends EntidadBasica {

    /**
     * Nombre para la consulta de buscar por tipo de movimiento id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_ID = "TipoMovimientoAlerta.buscarPorTipoMovimientoId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "mensaje")
    private String mensaje;

    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    @JoinColumn(name = "alertas_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Alerta alerta;

    /**
     * Constructor.
     */
    public TipoMovimientoAlerta() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public TipoMovimientoAlerta(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(final Alerta alerta) {
        this.alerta = alerta;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
