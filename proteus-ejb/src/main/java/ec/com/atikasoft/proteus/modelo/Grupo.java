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
@Table(name = "grupos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Grupo.BUSCAR_POR_NOMBRE, query =
    "SELECT gr FROM Grupo gr where gr.nombre like ?1 order by gr.nombre"),
    @NamedQuery(name = Grupo.BUSCAR_POR_NEMONICO, query = "SELECT gr FROM Grupo gr where gr.nemonico=?1"),
    @NamedQuery(name = Grupo.BUSCAR_ACTIVOS, query = "SELECT o FROM Grupo o where o.vigente=true order by o.nombre"),
    @NamedQuery(name = Grupo.BUSCAR_TODOS_VIGENTE,
    query = "SELECT gr FROM Grupo gr where gr.vigente=true order by gr.nombre")
})
public class Grupo extends EntidadBasica {

    /**
     * Nombre de la consulta de buscar grupos por el nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Grupo.BuscarporNombre";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_ACTIVOS = "Grupo.BuscarActivos";

    /**
     * Nombre de la consulta de buscar grupos vigentes.
     */
    public static final String BUSCAR_TODOS_VIGENTE = "Grupo.BuscarTodosVigente";

    /**
     * Nombre de la consulta de buscar grupos por Nemónico.
     */
    public static final String BUSCAR_POR_NEMONICO = "Grupo.BuscarporNemonico";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico")
    private String nemonico;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Constructor.
     */
    public Grupo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Grupo(final Long id) {
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

    public void setNombre(String nombre) {
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
}
