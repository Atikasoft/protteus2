package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "documentos_habilitantes", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DocumentoHabilitante.BUSCAR_POR_NOMBRE,
            query = "SELECT d FROM DocumentoHabilitante d where d.nombre like ?1 order by d.nombre"),
    @NamedQuery(name = DocumentoHabilitante.BUSCAR_POR_NEMONICO,
            query = "SELECT a FROM DocumentoHabilitante a where a.nemonico=?1"),
    @NamedQuery(name = DocumentoHabilitante.BUSCAR_VIGENTES,
            query = "SELECT a FROM DocumentoHabilitante a where a.vigente=true")
})
public class DocumentoHabilitante extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "DocumentoHabilitante.buscarporNombre ";

    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "DocumentoHabilitante.buscarporNemonico ";

    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_VIGENTES = "DocumentoHabilitante.buscarVigentes ";

    /**
     * Id de la clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nemónico.
     */
    @Column(name = "nemonico")
    private String nemonico;

    /**
     * Campo Nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Contador de documento habilitantes.
     */
    @Column(name = "contador")
    private Long contador;

    /**
     * Indica si el numero se genera automaticamente.
     */
    @Column(name = "numero_automatico")
    private Boolean numeroAutomatico;

    /**
     *
     */
    @JoinColumn(name = "catalogo_tipo_documento_habilitante_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoTipoDocumentoHabilitante;

    /**
     * *
     *
     */
    @Column(name = "catalogo_tipo_documento_habilitante_id")
    private Long catalogoTipoDocumentoHabilitanteId;

    /**
     * constructor.
     */
    public DocumentoHabilitante() {
        super();
    }

    /**
     * constructor.
     *
     * @param id Long
     */
    public DocumentoHabilitante(final Long id) {
        super();
        this.id = id;
    }

    /**
     * get Id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * set Id.
     *
     * @param id Long
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * get nemonico.
     *
     * @return nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * set nemonico.
     *
     * @param nemonico String
     */
    public void setNemonico(final String nemonico) {
        this.nemonico = nemonico;
    }

    /**
     * get nombre.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * set nombre.
     *
     * @param nombre String
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * get descripción.
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * set descripción.
     *
     * @param descripcion String
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the contador
     */
    public Long getContador() {
        return contador;
    }

    /**
     * @return the numeroAutomatico
     */
    public Boolean getNumeroAutomatico() {
        return numeroAutomatico;
    }

    /**
     * @param contador the contador to set
     */
    public void setContador(final Long contador) {
        this.contador = contador;
    }

    /**
     * @param numeroAutomatico the numeroAutomatico to set
     */
    public void setNumeroAutomatico(final Boolean numeroAutomatico) {
        this.numeroAutomatico = numeroAutomatico;
    }

    /**
     * @return the catalogoTipoDocumentoHabilitante
     */
    public Catalogo getCatalogoTipoDocumentoHabilitante() {
        return catalogoTipoDocumentoHabilitante;
    }

    /**
     * @param catalogoTipoDocumentoHabilitante the
     * catalogoTipoDocumentoHabilitante to set
     */
    public void setCatalogoTipoDocumentoHabilitante(Catalogo catalogoTipoDocumentoHabilitante) {
        this.catalogoTipoDocumentoHabilitante = catalogoTipoDocumentoHabilitante;
    }

    /**
     * @return the catalogoTipoDocumentoHabilitanteId
     */
    public Long getCatalogoTipoDocumentoHabilitanteId() {
        return catalogoTipoDocumentoHabilitanteId;
    }

    /**
     * @param catalogoTipoDocumentoHabilitanteId the
     * catalogoTipoDocumentoHabilitanteId to set
     */
    public void setCatalogoTipoDocumentoHabilitanteId(Long catalogoTipoDocumentoHabilitanteId) {
        this.catalogoTipoDocumentoHabilitanteId = catalogoTipoDocumentoHabilitanteId;
    }
}
