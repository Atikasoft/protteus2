package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Entity
@Table(name = "alertas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Alerta.BUSCAR_POR_NOMBRE,
    query = "SELECT a FROM Alerta a where a.nombre like ?1 order by a.nombre"),
    @NamedQuery(name = Alerta.BUSCAR_VIGENTES,
    query = "SELECT a FROM Alerta a where a.vigente=true"),
    @NamedQuery(name = Alerta.BUSCAR_POR_NEMONICO,
    query = "SELECT a FROM Alerta a where a.nemonico=?1")
})
public class Alerta extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Alerta.buscarporNombre ";

    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "Alerta.buscarporNemonico ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Alerta.buscarVigente";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo nemónico.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico")
    private String nemonico;

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

    /**
     * Campo tipo periodo.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Campo valor periodo.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_periodo")
    private Integer valorPeriodo;

    /**
     * Campo valor periodo2.
     */
    @Column(name = "valor_periodo2")
    private Integer valorPeriodo2;

    /**
     * Campo valor periodo3.
     */
    @Column(name = "valor_periodo3")
    private Integer valorPeriodo3;

    /**
     * Campo valor periodo4.
     */
    @Column(name = "valor_periodo4")
    private Integer valorPeriodo4;

    /**
     * Campo valor periodo5.
     */
    @Column(name = "valor_periodo5")
    private Integer valorPeriodo5;

    /**
     * Constructor.
     */
    public Alerta() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Alerta(final Long id) {
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
     * get nemónico.
     *
     * @return nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * set nemónico.
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

    /**
     * get tipo periodo.
     *
     * @return tipoPeriodo
     */
    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * set tipo periodo.
     *
     * @param tipoPeriodo String
     */
    public void setTipoPeriodo(final String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * get valor periodo.
     *
     * @return valorPeriodo
     */
    public Integer getValorPeriodo() {
        return valorPeriodo;
    }

    /**
     * set valor periodo.
     *
     * @param valorPeriodo int
     */
    public void setValorPeriodo(final Integer valorPeriodo) {
        this.valorPeriodo = valorPeriodo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the valorPeriodo2
     */
    public Integer getValorPeriodo2() {
        return valorPeriodo2;
    }

    /**
     * @return the valorPeriodo3
     */
    public Integer getValorPeriodo3() {
        return valorPeriodo3;
    }

    /**
     * @return the valorPeriodo4
     */
    public Integer getValorPeriodo4() {
        return valorPeriodo4;
    }

    /**
     * @return the valorPeriodo5
     */
    public Integer getValorPeriodo5() {
        return valorPeriodo5;
    }

    /**
     * @param valorPeriodo2 the valorPeriodo2 to set
     */
    public void setValorPeriodo2(final Integer valorPeriodo2) {
        this.valorPeriodo2 = valorPeriodo2;
    }

    /**
     * @param valorPeriodo3 the valorPeriodo3 to set
     */
    public void setValorPeriodo3(final Integer valorPeriodo3) {
        this.valorPeriodo3 = valorPeriodo3;
    }

    /**
     * @param valorPeriodo4 the valorPeriodo4 to set
     */
    public void setValorPeriodo4(final Integer valorPeriodo4) {
        this.valorPeriodo4 = valorPeriodo4;
    }

    /**
     * @param valorPeriodo5 the valorPeriodo5 to set
     */
    public void setValorPeriodo5(final Integer valorPeriodo5) {
        this.valorPeriodo5 = valorPeriodo5;
    }
}
