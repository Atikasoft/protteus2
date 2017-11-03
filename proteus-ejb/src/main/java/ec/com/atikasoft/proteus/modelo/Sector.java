package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TIPO_problema database table.
 */
@Entity
@Table(name = "sectores", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Sector.BUSCAR_VIGENTES, query = "Select u FROM Sector "
    + "u where u.vigente=true order by u.nombre ")
})
public class Sector extends EntidadBasica implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

     /**
     * Nombre de consulta para buscar vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Sector.buscarVigente";

    /**
     * Clave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador funcional.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Descripción clara de lo que representa el registro.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Constructor sin argumentos.
     */
    public Sector() {
        super();
    }

    /**
     *
     * @param id
     */
    public Sector(final Long id) {
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
}