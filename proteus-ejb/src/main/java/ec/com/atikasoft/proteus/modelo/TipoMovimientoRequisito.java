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
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@Entity
@Table(name = "tipos_movimientos_requisitos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID,
    query = "SELECT tmr FROM TipoMovimientoRequisito tmr where tmr.tipoMovimiento.id=?1 and tmr.vigente=true"),
    @NamedQuery(name = TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID_SERVIDOR_PUBLICO,
    query = "SELECT tmr FROM TipoMovimientoRequisito tmr where tmr.tipoMovimiento.id=?1 and "
    + "tmr.vigente=true and tmr.presentaServidorPublico=true"),
    @NamedQuery(name = TipoMovimientoRequisito.BUSCAR_NO__VALIDADOS_POR_TIPO_MOVIMIENTO, query =
    "SELECT o FROM TipoMovimientoRequisito o WHERE o.vigente=true AND o.tipoMovimiento.id=?1"
    + " AND o.obligatorio=true AND"
    + " o.id NOT IN (SELECT v.tipoMovimientoRequisito.id FROM Validacion v "
    + "WHERE v.movimiento.id=?2 AND v.vigente=true)"),
    @NamedQuery(name = TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID_Y_REQUISITO_ID,
    query = "SELECT tmr FROM TipoMovimientoRequisito tmr "
    + "WHERE tmr.tipoMovimiento.id=?1 AND tmr.requisito.id=?2 AND tmr.vigente=true")
})
public class TipoMovimientoRequisito extends EntidadBasica {

    /**
     * Nombre para la consulta de buscar por tipo de movimiento id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_ID = "TipoMovimientoRequisito.buscarPorTipoMovimientoId";

    /**
     * Nombre para la consulta de buscar por tipo de movimiento id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_ID_SERVIDOR_PUBLICO =
            "TipoMovimientoRequisito.buscarPorTipoMovimientoIdServidorPublico";

    /**
     * Nombre de la consulta que busca los requisitos no validados.
     */
    public static final String BUSCAR_NO__VALIDADOS_POR_TIPO_MOVIMIENTO =
            "TipoMovimientoRequisito.buscarNoIncluidosValidacionesPorTipoMovimiento";

    /**
     * Nombre de la consulta para buscar por tipo de movimiento id y por requisito id.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_ID_Y_REQUISITO_ID =
            "TipoMovimientoRequisito.buscarPorTipoMovimientoIdYRequisitoId";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Indica si el requisito es obligatorio.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "obligatorio")
    private boolean obligatorio;

    /**
     * Referencia del tipo de movimiento.
     */
    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    /**
     * Referencia de requisitos.
     */
    @JoinColumn(name = "requisitos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Requisito requisito;

    /**
     * Presenta Servidor Publico.
     */
    @Column(name = "presenta_servidor_publico")
    private boolean presentaServidorPublico;

    /**
     * Subir Archivo Obligatorio.
     */
    @Column(name = "subir_archivo_obligatorio")
    private boolean subirArchivoObligatorio;

    /**
     * Constructor.
     */
    public TipoMovimientoRequisito() {
        super();
    }

    /**
     * constructor.
     *
     * @param id id
     */
    public TipoMovimientoRequisito(final Long id) {
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
     * @return the requisito
     */
    public Requisito getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(final Requisito requisito) {
        this.requisito = requisito;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the presentaServidorPublico
     */
    public Boolean getPresentaServidorPublico() {
        return presentaServidorPublico;
    }

    /**
     * @param presentaServidorPublico the presentaServidorPublico to set
     */
    public void setPresentaServidorPublico(final Boolean presentaServidorPublico) {
        this.presentaServidorPublico = presentaServidorPublico;
    }

    /**
     * @return the subirArchivoObligatorio
     */
    public Boolean getSubirArchivoObligatorio() {
        return subirArchivoObligatorio;
    }

    /**
     * @param subirArchivoObligatorio the subirArchivoObligatorio to set
     */
    public void setSubirArchivoObligatorio(final Boolean subirArchivoObligatorio) {
        this.subirArchivoObligatorio = subirArchivoObligatorio;
    }
}
