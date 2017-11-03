package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de movimientos por traspasos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "traspasos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Traspaso.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Traspaso o where o.vigente=true and o.movimiento.id=?1")
})
public class Traspaso extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Traspaso.buscarPorMovimiento";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Unidad organizacional.
     */
    @JoinColumn(name = "unidad_organizacional_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * Unidad presupuestaria.
     */
    @JoinColumn(name = "unidad_presupuestaria_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * Unidad presupuestaria.
     */
    @Column(name = "unidad_presupuestaria_id")
    private Long unidadPresupuestariaId;

    /**
     * Constructor.
     */
    public Traspaso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Traspaso(final Long id) {
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
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @return the unidadPresupuestaria
     */
    public UnidadPresupuestaria getUnidadPresupuestaria() {
        return unidadPresupuestaria;
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
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(final UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @param unidadPresupuestaria the unidadPresupuestaria to set
     */
    public void setUnidadPresupuestaria(final UnidadPresupuestaria unidadPresupuestaria) {
        this.unidadPresupuestaria = unidadPresupuestaria;
    }

    /**
     * @return the unidadPresupuestariaId
     */
    public Long getUnidadPresupuestariaId() {
        return unidadPresupuestariaId;
    }

    /**
     * @param unidadPresupuestariaId the unidadPresupuestariaId to set
     */
    public void setUnidadPresupuestariaId(Long unidadPresupuestariaId) {
        this.unidadPresupuestariaId = unidadPresupuestariaId;
    }
}
