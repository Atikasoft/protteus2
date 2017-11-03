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
@Table(name = "clases", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Clase.BUSCAR_POR_NOMBRE, query = "SELECT c FROM Clase c where c.nombre like ?1"),
    @NamedQuery(name = Clase.BUSCAR_POR_GRUPO_ID, query = "SELECT c FROM Clase c where c.grupo.id=?1 and c.vigente=true order by c.nombre"),
    @NamedQuery(name = Clase.BUSCAR_POR_NEMONICO, query = "SELECT a FROM Clase a where a.nemonico=?1")
})
public class Clase extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NOMBRE = "Clase.buscarPorNombre";

    /**
     * Nombre de la consulta para buscar las clases por el id del grupo.
     */
    public static final String BUSCAR_POR_GRUPO_ID = "Clase.buscarPorGrupoId";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "Clase.buscarporNemonico ";

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

    @JoinColumn(name = "grupos_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo grupo;

    @Column(name = "grupos_id")
    private Long grupoId;

    /**
     *
     *
     */
    @JoinColumn(name = "regimen_laboral_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;

    /**
     *
     *
     */
    @JoinColumn(name = "regimen_laboral2_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral2;

    /**
     * Constructor.
     */
    public Clase() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Clase(final Long id) {
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

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(final Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the grupoId
     */
    public Long getGrupoId() {
        return grupoId;
    }

    /**
     * @param grupoId the grupoId to set
     */
    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    /**
     * @return the regimenLaboral
     */
    public RegimenLaboral getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(RegimenLaboral regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    /**
     * @return the regimenLaboral2
     */
    public RegimenLaboral getRegimenLaboral2() {
        return regimenLaboral2;
    }

    /**
     * @param regimenLaboral2 the regimenLaboral2 to set
     */
    public void setRegimenLaboral2(RegimenLaboral regimenLaboral2) {
        this.regimenLaboral2 = regimenLaboral2;
    }
}
