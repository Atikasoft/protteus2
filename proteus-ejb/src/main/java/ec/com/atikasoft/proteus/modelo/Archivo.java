package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Contiene los archivos del sistema.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "archivos", catalog = "sch_proteus")
public class Archivo extends EntidadBasica {

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Descripcion del archivo.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Palabras clave del archivo.
     */
    @Column(name = "palabras_claves")
    private String palabrasClave;

    /**
     * Nombre del archivo.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Archivo.
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] archivo;

    /**
     * Constructor.
     */
    public Archivo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Archivo(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
     * @return the palabrasClave
     */
    public String getPalabrasClave() {
        return palabrasClave;
    }

    /**
     * @return the archivo
     */
    public byte[] getArchivo() {
        return archivo;
    }

    /**
     * @param palabrasClave the palabrasClave to set
     */
    public void setPalabrasClave(final String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final byte[] archivo) {
        this.archivo = archivo;
    }
}
