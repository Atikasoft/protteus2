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
@Table(name = "requisitos", catalog = "sch_proteus")
@NamedQueries({@NamedQuery(name = Requisito.BUSCAR_POR_NOMBRE, query = "SELECT rq FROM Requisito rq where rq.nombre like ?1"),
    @NamedQuery(name = Requisito.BUSCAR_POR_GRUPO_ID, query = "SELECT r FROM Requisito r where r.grupo.id=?1 and r.vigente=true"),
    @NamedQuery(name = Requisito.BUSCAR_POR_NEMONICO, query = "SELECT rq FROM Requisito rq where rq.nemonico=?1")
})
public class Requisito extends EntidadBasica {

    /**
     * Nombre de la consulta de buscar requisitos por el nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Requisito.BuscarporNombre";

    /**
     * Nombre de la consulta de buscar requisitos vigentes.
     */
    public static final String BUSCAR_POR_GRUPO_ID = "Requisito.BuscarPorGrupoId";
    /**
     * Nombre de la consulta de buscar requisitos requisitos por nemónico.
     */
    public static final String BUSCAR_POR_NEMONICO="Requisito.BuscarPorNemonico";

    /**
     * Id.
     */
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
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiene_calificacion")
    private boolean tieneCalificacion;

    /**
     * Sustento Legal.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "sustento_legal")
    private String sustentoLegal;

    @JoinColumn(name = "grupos_id", updatable=false, insertable=false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo grupo;
    
    @Column(name = "grupos_id")
    private Long grupoId;
    
    /**
     * Constructor.
     */
    public Requisito() {
        super();
    }

    /**
     * constructor.
     *
     * @param id
     */
    public Requisito(final Long id) {
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

    public boolean getTieneCalificacion() {
        return tieneCalificacion;
    }

    public void setTieneCalificacion(final boolean tieneCalificacion) {
        this.tieneCalificacion = tieneCalificacion;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(final Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
}
