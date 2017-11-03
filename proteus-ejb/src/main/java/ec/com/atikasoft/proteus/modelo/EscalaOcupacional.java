/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "escalas_ocupacionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EscalaOcupacional.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM EscalaOcupacional a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = EscalaOcupacional.BUSCAR_VIGENTES,
            query = "SELECT a FROM EscalaOcupacional a where a.vigente=true"),
    @NamedQuery(name = EscalaOcupacional.BUSCAR_POR_NIVEL_OCUPACIONAL,
            query = "SELECT a FROM EscalaOcupacional a where a.vigente=true and a.nivelOcupacional.id=?1"),
    @NamedQuery(name = EscalaOcupacional.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM EscalaOcupacional a where a.codigo=?1 and a.vigente=true"),
    @NamedQuery(name = EscalaOcupacional.BUSCAR_POR_CODIGOS,
            query = "SELECT a FROM EscalaOcupacional a where a.codigo=?1 and a.nivelOcupacional.codigo=?2  and "
            + " a.nivelOcupacional.regimenLaboral.codigo=?3 and a.vigente=true")
})
public class EscalaOcupacional extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "EscalaOcupacional.buscarporNombre ";

    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_CODIGO = "EscalaOcupacional.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "EscalaOcupacional.buscarVigente";

    /**
     * Nombre para busqeda por codigo del Nivel Ocupacional.
     */
    public static final String BUSCAR_POR_NIVEL_OCUPACIONAL = "EscalaOcupacional.buscarPorNivelOcupacional";

    /**
     *
     */
    public static final String BUSCAR_POR_CODIGOS = "EscalaOcupacional.buscarPorCodigos";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo código.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    @Min(value = 0)
    @NotNull
    @Basic(optional = false)
    @Column(name = "rmu")
    private BigDecimal rmu;

    @Column(name = "rmu_maximo")
    private BigDecimal rmuMaximo;

    @Column(name = "grado")
    private String grado;

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "niveles_ocupacionales_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NivelOcupacional nivelOcupacional;

    /**
     * NIveles ocupacionales id.
     */
    @Column(name = "niveles_ocupacionales_id")
    private Long idNivelOcupacional;

    @Transient
    private String nombreCompleto;

    @Column(name = "marcacion_obligatoria")
    private boolean marcacionObligatoria;

    @Column(name = "recibe_horas_extra")
    private boolean recibeHorasExtra;

    /**
     * Constructor.
     *
     */
    public EscalaOcupacional() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public EscalaOcupacional(Long id) {
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
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the rmuMaximo
     */
    public BigDecimal getRmuMaximo() {
        return rmuMaximo;
    }

    /**
     * @param rmuMaximo the rmuMaximo to set
     */
    public void setRmuMaximo(BigDecimal rmuMaximo) {
        this.rmuMaximo = rmuMaximo;
    }

    @Override
    public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return (UtilCadena.concatenar("escala ocup:", this.getId(), "-", this.getNombre(), "-", this.getRmu()));
    }

    /**
     * @return the nivelOcupacional
     */
    public NivelOcupacional getNivelOcupacional() {
        return nivelOcupacional;
    }

    /**
     * @param nivelOcupacional the nivelOcupacional to set
     */
    public void setNivelOcupacional(NivelOcupacional nivelOcupacional) {
        this.nivelOcupacional = nivelOcupacional;
    }

    /**
     * @return the grado
     */
    public String getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(String grado) {
        this.grado = grado;
    }

    /**
     * @return the idNivelOcupacional
     */
    public Long getIdNivelOcupacional() {
        return idNivelOcupacional;
    }

    /**
     * @param idNivelOcupacional the idNivelOcupacional to set
     */
    public void setIdNivelOcupacional(Long idNivelOcupacional) {
        this.idNivelOcupacional = idNivelOcupacional;
    }

    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        nombreCompleto = nivelOcupacional.getRegimenLaboral().getNombre().concat(" / ").
                concat(nivelOcupacional.getNombre()).concat(" / ").concat(nombre);

        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     *
     * @return
     */
    public boolean getMarcacionObligatoria() {
        return marcacionObligatoria;
    }

    /**
     *
     * @param marcacionObligatoria
     */
    public void setMarcacionObligatoria(boolean marcacionObligatoria) {
        this.marcacionObligatoria = marcacionObligatoria;
    }

    /**
     *
     * @return
     */
    public boolean getRecibeHorasExtra() {
        return recibeHorasExtra;
    }

    /**
     *
     * @param recibeHorasExtra
     */
    public void setRecibeHorasExtra(boolean recibeHorasExtra) {
        this.recibeHorasExtra = recibeHorasExtra;
    }
}
