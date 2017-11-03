package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.*;

/**
 * Catalogo de parametros institucionales.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "parametros_institucionales", schema = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ParametroInstitucional.BUSCAR_POR_INSTITUCION_ID,
    query = "SELECT v FROM ParametroInstitucional v where v.vigente=true AND v.institucion.id=?1 "
    + "ORDER BY v.parametroInstitucionalCatalogo.nombre"),
    @NamedQuery(name = ParametroInstitucional.BUSCAR_POR_INSTITUCION_Y_CODIGO,
    query = "SELECT o FROM ParametroInstitucional o WHERE o.vigente=true AND o.institucion.id=?1 AND"
    + " o.parametroInstitucionalCatalogo.nemonico=?2 "),
        @NamedQuery(name = ParametroInstitucional.BUSCAR_POR_CODIGO,
    query = "SELECT o FROM ParametroInstitucional o WHERE o.vigente=true AND"
    + " o.parametroInstitucionalCatalogo.nemonico=?1 "),
    @NamedQuery(name = ParametroInstitucional.BUSCAR_POR_INSTITUCION_PARAMETRO_INSTITUCINAL_CATALOGO,
    query = "Select o from ParametroInstitucional o where o.institucion.id=?1 AND "
    + "o.parametroInstitucionalCatalogo.id=?2 and o.vigente=true ")
})
public class ParametroInstitucional extends EntidadBasica {

    /**
     * Nombre para la consulta de buscar por movimiento id.
     */
    public static final String BUSCAR_POR_INSTITUCION_ID = "ParametroInstitucional.buscarPorInstitucionId";

    /**
     * Nombre para la consulta de buscar por InstitucionId y ParametroInstitucinalCatalogoId.
     */
    public static final String BUSCAR_POR_INSTITUCION_PARAMETRO_INSTITUCINAL_CATALOGO =
            "ParametroInstitucional.buscarPorInstitucion"
            + "parametroInstitucionalCatalogo";

    /**
     * Nombre de la consulta que recupera parametros institucion para una institucion y codigo.
     */
    public static final String BUSCAR_POR_INSTITUCION_Y_CODIGO = "ParametroInstitucional.buscarPorIntitucionYCodigo";
    
    /**
     * Nombre de la consulta que recupera parametros institucion para una institucion y codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "ParametroInstitucional.buscarPorCodigo";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia de institucion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
   // private InstitucionEjercicioFiscal institucion;
    private Institucion institucion;

    /**
     * Referencia de parametro institucional catalogo.
     */
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "parametros_institucionales_catalogos_id")
    private ParametroInstitucionalCatalogo parametroInstitucionalCatalogo;

    /**
     * Referencia a archivos,
     */
    @JoinColumn(name = "valor_archivo_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     * Valor texto.
     */
    @Column(name = "valor_texto")
    private String valorTexto;

    /**
     * Valor fecha.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valor_fecha")
    private Date valorFecha;

    /**
     * Valor numerico
     */
    @Column(name = "valor_numerico")
    private BigInteger valorNumerico;

    /**
     * Constructor.
     */
    public ParametroInstitucional() {
        super();
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
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @return the parametroInstitucionalCatalogo
     */
    public ParametroInstitucionalCatalogo getParametroInstitucionalCatalogo() {
        return parametroInstitucionalCatalogo;
    }

    /**
     * @return the valorTexto
     */
    public String getValorTexto() {
        return valorTexto;
    }

    /**
     * @return the valorFecha
     */
    public Date getValorFecha() {
        return valorFecha;
    }

    /**
     * @return the valorNumerico
     */
    public BigInteger getValorNumerico() {
        return valorNumerico;
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
    public void setInstitucion(final Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @param parametroInstitucionalCatalogo the parametroInstitucionalCatalogo to set
     */
    public void setParametroInstitucionalCatalogo(final ParametroInstitucionalCatalogo parametroInstitucionalCatalogo) {
        this.parametroInstitucionalCatalogo = parametroInstitucionalCatalogo;
    }

    /**
     * @param valorTexto the valorTexto to set
     */
    public void setValorTexto(final String valorTexto) {
        this.valorTexto = valorTexto;
    }

    /**
     * @param valorFecha the valorFecha to set
     */
    public void setValorFecha(final Date valorFecha) {
        this.valorFecha = valorFecha;
    }

    /**
     * @param valorNumerico the valorNumerico to set
     */
    public void setValorNumerico(final BigInteger valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
}
