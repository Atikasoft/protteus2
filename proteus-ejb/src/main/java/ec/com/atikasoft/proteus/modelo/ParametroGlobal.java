package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigInteger;
import java.util.Date;
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
@Table(name = "parametros_globales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ParametroGlobal.BUSCAR_POR_NOMBRE, query =
    "SELECT p FROM ParametroGlobal p where p.nombre like ?1 ORDER BY p.nombre"),
    @NamedQuery(name = ParametroGlobal.BUSCAR_POR_NENOMICO, query =
    "SELECT o FROM ParametroGlobal o WHERE o.nemonico=?1")
})
public class ParametroGlobal extends EntidadBasica {

    /**
     * Nombre de la consulta que busca parametros globales por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "ParametroGlobal.buscarPorNombre";

    /**
     * Nombre de la consulta que busca parametros globales por el nemonico.
     */
    public static final String BUSCAR_POR_NENOMICO = "ParametroGlobal.bucarPorNemonico";

    private static final long serialVersionUID = 1L;

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
    @Column(name = "tipo_dato")
    private char tipoDato;

    @Column(name = "valor_numerico")
    private BigInteger valorNumerico;

    @Size(max = 500)
    @Column(name = "valor_texto")
    private String valorTexto;

    @Column(name = "valor_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valorFecha;

    public ParametroGlobal() {
    }

    public ParametroGlobal(Long id) {
        this.id = id;
    }

    public ParametroGlobal(Long id, String nemonico, String nombre, String descripcion, char tipoDato,
            Date fechaCreacion, String usuarioCreacion, boolean vigente) {
        this.id = id;
        this.nemonico = nemonico;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoDato = tipoDato;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNemonico() {
        return nemonico;
    }

    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(char tipoDato) {
        this.tipoDato = tipoDato;
    }

    public BigInteger getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(BigInteger valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public String getValorTexto() {
        return valorTexto;
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    public Date getValorFecha() {
        return valorFecha;
    }

    public void setValorFecha(Date valorFecha) {
        this.valorFecha = valorFecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroGlobal)) {
            return false;
        }
        ParametroGlobal other = (ParametroGlobal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
