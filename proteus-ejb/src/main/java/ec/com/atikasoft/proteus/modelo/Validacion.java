package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "validaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Validacion.BUSCAR_POR_TIPO_MOVIMIENTO_ID,
    query = "SELECT v FROM Validacion v where v.tipoMovimientoRequisito.tipoMovimiento.id=?1 and v.vigente=true"),
    @NamedQuery(name = Validacion.BUSCAR_POR_MOVIMIENTO_ID,
    query = "SELECT v FROM Validacion v where v.movimiento.id=?1 and v.vigente=true"),
    @NamedQuery(name = Validacion.BUSCAR_REQUISITOS_INCUMPLIDOS_POR_MOVIMIENTO,
    query = "SELECT o FROM Validacion o WHERE o.vigente=true AND o.tipoMovimientoRequisito.obligatorio=true AND "
    + " o.cumple=false AND o.tipoMovimientoRequisito.vigente=true AND o.movimiento.id=?1"),
    @NamedQuery(name = Validacion.BUSCAR_REQUISITOS_CUMPLIDOS_SIN_ARCHIVOS_POR_MOVIMIENTO,
    query = "SELECT o FROM Validacion o WHERE o.vigente=true AND o.tipoMovimientoRequisito.obligatorio=true AND "
    + " o.cumple=true AND o.archivo IS NULL AND o.tipoMovimientoRequisito.vigente=true AND o.movimiento.id=?1"),
    @NamedQuery(name = Validacion.BUSCAR_POR_TIPO_MOVIMIENTO_REQUISITO_ID_Y_MOVIMIENTO_ID, query =
    "SELECT v FROM Validacion v WHERE v.vigente=true AND v.tipoMovimientoRequisito.id=?1 AND "
    + " v.movimiento.id=?2")
})
public class Validacion extends EntidadBasica {

    /**
     * Nombre para la consulta de buscar por tipo de movimiento id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_ID = "Validacion.buscarPorTipoMovimientoId";

    /**
     * Nombre para la consulta de buscar por movimiento id.
     */
    public static final String BUSCAR_POR_MOVIMIENTO_ID = "Validacion.buscarPorMovimientoId";

    /**
     * Nombre de la consulta que busca requisitos incumplidos por movimientos.
     */
    public static final String BUSCAR_REQUISITOS_INCUMPLIDOS_POR_MOVIMIENTO =
            "Validacion.buscarRequisitosIncumplidosPorMovimiento";

    /**
     * Nombre de la consulta que busca requisitos complido sin archivo por movimientos.
     */
    public static final String BUSCAR_REQUISITOS_CUMPLIDOS_SIN_ARCHIVOS_POR_MOVIMIENTO =
            "Validacion.buscarRequisitoscumplidossSinArchivoPorMovimiento";

    /**
     * Nombre de la consulta que busca requisitos por tipo de movimiento requisito id y por movimiento id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_REQUISITO_ID_Y_MOVIMIENTO_ID =
            "Validacion.buscarRequisitosPorTipoMovimientoRequisitoIdYMovimientoId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cumple")
    private boolean cumple;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "fecha_documento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;

    /**
     * Referencia a archivos,
     */
    @JoinColumn(name = "archivos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Archivo archivo;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "calificacion")
    private String calificacion;

    /**
     * Sustento Legal.
     */
    @Column(name = "sustento_legal")
    private String sustentoLegal;

    @JoinColumn(name = "tipos_movimientos_requisitos_id")
    @OneToOne(optional = false)
    private TipoMovimientoRequisito tipoMovimientoRequisito;

    @JoinColumn(name = "movimientos_id")
    @OneToOne(optional = false)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public Validacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Validacion(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public boolean getCumple() {
        return cumple;
    }

    public void setCumple(final boolean cumple) {
        this.cumple = cumple;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(final Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(final String calificacion) {
        this.calificacion = calificacion;
    }

    public TipoMovimientoRequisito getTipoMovimientoRequisito() {
        return tipoMovimientoRequisito;
    }

    public void setTipoMovimientoRequisito(final TipoMovimientoRequisito tipoMovimientoRequisito) {
        this.tipoMovimientoRequisito = tipoMovimientoRequisito;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the sustentoLegal
     */
    public String getSustentoLegal() {
        return sustentoLegal;
    }

    /**
     * @param sustentoLegal the sustentoLegal to set
     */
    public void setSustentoLegal(final String sustentoLegal) {
        this.sustentoLegal = sustentoLegal;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
}
