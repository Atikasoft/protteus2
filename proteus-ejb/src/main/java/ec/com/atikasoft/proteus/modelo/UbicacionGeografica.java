package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "ubicacion_geografica", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = UbicacionGeografica.BUSCAR_ORDENADA, query = "Select ug FROM UbicacionGeografica "
            + "ug where ug.vigente=true order by ug.nombre"),
    @NamedQuery(name = UbicacionGeografica.BUSCAR_POR_PADRE, query = "Select ug FROM UbicacionGeografica "
            + "ug where ug.vigente=true and ug.idUbicacionGeografica =?1  order by ug.nombre"),
    @NamedQuery(name = UbicacionGeografica.BUSCAR_PAISES, query = "Select ug FROM UbicacionGeografica "
            + "ug where ug.vigente=true and ug.idUbicacionGeografica is null  order by ug.nombre"),
    @NamedQuery(name = UbicacionGeografica.BUSCAR_POR_NEMONICO, query = "SELECT a FROM UbicacionGeografica a where a.codigo=?1")
})
public class UbicacionGeografica extends EntidadBasica {

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_ORDENADA = "UbicacionGeografica.buscarOrdenada";

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_POR_PADRE = "UbicacionGeografica.buscarPorId";
    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_PAISES = "UbicacionGeografica.buscarPaises";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "UbicacionGeografica.buscarporNemonico ";

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;

    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;

    /**
     * ubicación geográfica.
     */
    @JoinColumn(name = "id_ubicacion_geografica", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeografica;

    /**
     * ubicación geográfica.
     */
    @Column(name = "id_ubicacion_geografica")
    private Long idUbicacionGeografica;

    @Transient
    private String nombreCompleto;

    /**
     * constructor.
     */
    public UbicacionGeografica() {
    }

    /**
     * constructor.
     *
     * @param id Long
     */
    public UbicacionGeografica(final Long id) {
        this.id = id;
    }

    @PostLoad
    public void postLoad() {
        
        UbicacionGeografica o = ubicacionGeografica;
        nombreCompleto = "";
        while (o != null) {
            nombreCompleto = o.getNombre().concat(" / ").concat(nombreCompleto);
            o = o.getUbicacionGeografica();
        }
        nombreCompleto = nombreCompleto.concat(nombre);
    }

    /**
     * get.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * set.
     *
     * @param id Long
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * get.
     *
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * set.
     *
     * @param nombre String
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * get.
     *
     * @return String
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * set.
     *
     * @param tipo String
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the idUbicacionGeografica
     */
    public Long getIdUbicacionGeografica() {
        return idUbicacionGeografica;
    }

    /**
     * @param idUbicacionGeografica the idUbicacionGeografica to set
     */
    public void setIdUbicacionGeografica(final Long idUbicacionGeografica) {
        this.idUbicacionGeografica = idUbicacionGeografica;
    }

    /**
     * @return the ubicacionGeografica
     */
    public UbicacionGeografica getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(final UbicacionGeografica ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
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
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {

        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
