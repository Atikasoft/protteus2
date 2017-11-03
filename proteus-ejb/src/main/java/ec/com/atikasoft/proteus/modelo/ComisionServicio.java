package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de movimientos por licencia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "comisiones_servicios", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ComisionServicio.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from ComisionServicio o where o.vigente=true and o.movimiento.id=?1")
})
public class ComisionServicio extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "ComisionServicio.buscarPorMovimiento";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de institucion..
     */
    @Column(name = "institucion")
    private String institucion;

    /**
     * Nombre de unidad administrativa.
     */
    @Column(name = "unidad")
    private String unidad;
    
    /**
     * 
     */
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;
    

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public ComisionServicio() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ComisionServicio(final Long id) {
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
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
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
     * @param institucion the institucion to set
     */
    public void setInstitucion(final String institucion) {
        this.institucion = institucion;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(final String unidad) {
        this.unidad = unidad;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the tipoComisionServicio
     */
    public String getTipoComisionServicio() {
        return tipoComisionServicio;
    }

    /**
     * @param tipoComisionServicio the tipoComisionServicio to set
     */
    public void setTipoComisionServicio(String tipoComisionServicio) {
        this.tipoComisionServicio = tipoComisionServicio;
    }
}
