package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "regimenes_disciplinarios", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = RegimenDisciplinario.BUSCAR_POR_MOVIMIENTO,
            query = "Select o from RegimenDisciplinario o where o.vigente=true and o.movimiento.id=?1")
})
public class RegimenDisciplinario extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "RegimenDisciplinario.buscarPorMovimiento";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    @Column(name = "falta")
    private String falta;

    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Valor de la multa.
     */
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;

    /**
     * Valor de la multa.
     */
    @Column(name = "valor_nomina_multa")
    private BigDecimal valorNominaMulta;

    /**
     * Constructor.
     */
    public RegimenDisciplinario() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public RegimenDisciplinario(final Long id) {
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

    /**
     * @return the falta
     */
    public String getFalta() {
        return falta;
    }

    /**
     * @param falta the falta to set
     */
    public void setFalta(final String falta) {
        this.falta = falta;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the valorMulta
     */
    public BigDecimal getValorMulta() {
        return valorMulta;
    }

    /**
     * @param valorMulta the valorMulta to set
     */
    public void setValorMulta(final BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    /**
     * @return the valorNominaMulta
     */
    public BigDecimal getValorNominaMulta() {
        return valorNominaMulta;
    }

    /**
     * @param valorNominaMulta the valorNominaMulta to set
     */
    public void setValorNominaMulta(BigDecimal valorNominaMulta) {
        this.valorNominaMulta = valorNominaMulta;
    }
}
