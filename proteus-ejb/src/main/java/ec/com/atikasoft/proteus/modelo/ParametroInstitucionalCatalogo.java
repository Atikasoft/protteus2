package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Catalogo de parametros institucionales.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "parametros_institucionales_catalogos", schema = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ParametroInstitucionalCatalogo.BUSCAR_POR_NOMBRE, query =
    "SELECT c FROM ParametroInstitucionalCatalogo c where c.nombre like ?1"),
    @NamedQuery(name = ParametroInstitucionalCatalogo.BUSCAR_POR_NEMONICO, query =
    "SELECT a FROM ParametroInstitucionalCatalogo a where a.nemonico=?1"),
    @NamedQuery(name = ParametroInstitucionalCatalogo.BUSCAR_SIN_PARAMETROS_INSTITUCIONALES,
    query = "SELECT o FROM ParametroInstitucionalCatalogo o WHERE o.vigente=true AND"
    + " o.id NOT IN (SELECT pi.parametroInstitucionalCatalogo.id "
    + "FROM ParametroInstitucional pi WHERE pi.vigente=true AND pi.institucion.id=?1) ORDER BY o.nombre")
})
public class ParametroInstitucionalCatalogo extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una ParametroInstitucionalCatalogo. 
     */
    public static final String BUSCAR_POR_NOMBRE = "ParametroInstitucionalCatalogo.buscarPorNombre";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "ParametroInstitucionalCatalogo.buscarporNemonico ";

    /**
     * Nombre de la consulta que recupera los catalogos que no existen en una institucion.
     */
    public static final String BUSCAR_SIN_PARAMETROS_INSTITUCIONALES =
            "ParametroInstitucionalCatalogo.buscarSinParametrosInstitucionales";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nemonico")
    private String nemonico;

    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @NotNull
    @Column(name = "tipo")
    private String tipo;

    /**
     * Constructor.
     */
    public ParametroInstitucionalCatalogo() {
        super();
    }

    /**
     * Constructor parametro id.
     *
     * @param id.
     */
    public ParametroInstitucionalCatalogo(final Long id) {
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
     * @return the nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param nemonico the nemonico to set
     */
    public void setNemonico(final String nemonico) {
        this.nemonico = nemonico;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }
}
