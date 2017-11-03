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
@Table(name = "reglas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Regla.BUSCAR_POR_NOMBRE,
    query = "SELECT re FROM Regla re where re.nombre like ?1"),
     @NamedQuery(name = Regla.BUSCAR_POR_NEMONICO,
    query = "SELECT a FROM Regla a where a.nemonico=?1"),
    @NamedQuery(name = Regla.BUSCAR_VIGENTES,
    query = "SELECT re FROM Regla re where re.vigente=true")
})
public class Regla extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una regla.
     */
    public static final String BUSCAR_POR_NOMBRE = "Regla.buscarporNombre";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Regla.buscarVigente";
        /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "Regla.buscarporNemonico ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico")
    private String nemonico;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    
    @Size(min = 1, max = 20)
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    @Column(name = "valor_periodo")
    private Integer valorPeriodo;

    /**
     * Constructor.
     */
    public Regla() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Regla(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNemonico() {
        return nemonico;
    }

    public void setNemonico(final String nemonico) {
        this.nemonico = nemonico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the tipoPeriodo
     */
    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo the tipoPeriodo to set
     */
    public void setTipoPeriodo(final String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * @return the valorPeriodo
     */
    public Integer getValorPeriodo() {
        return valorPeriodo;
    }

    /**
     * @param valorPeriodo the valorPeriodo to set
     */
    public void setValorPeriodo(final Integer valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }
}
