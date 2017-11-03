package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Registra las justificaciones por reglas no cumplidas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "justificaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Justificacion.BUSCAR_POR_MOVIMIENTO_TIPO_MOVIMIENTO_REGLA,
    query = "Select o from Justificacion o where o.movimiento.id=?1 and o.tipoMovimientoRegla.id=?2 and o.vigente=true ")
})
public class Justificacion extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_MOVIMIENTO_TIPO_MOVIMIENTO_REGLA =
            "Justificacion.buscarPorMovimientoYTipoMovimientoRegla";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Numero de documento.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Fecha del documento
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_documento")
    private Date fechaDocumento;

    /**
     * Observacion.
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * Referencia a tipo de movimiento x regla.
     */
    @JoinColumn(name = "tipos_movimientos_reglas_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimientoRegla tipoMovimientoRegla;

    /**
     * Referencia a movimiento,
     */
    @JoinColumn(name = "movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Referencia a archivos,
     */
    @JoinColumn(name = "archivos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Archivo archivo;

    /**
     * Constructor.
     */
    public Justificacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Justificacion(final Long id) {
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
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @return the fechaDocumento
     */
    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @param fechaDocumento the fechaDocumento to set
     */
    public void setFechaDocumento(final Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final Archivo archivo) {
        this.archivo = archivo;
    }
}
