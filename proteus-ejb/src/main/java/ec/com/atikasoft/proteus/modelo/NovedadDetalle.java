/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "novedades_detalles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NovedadDetalle.BUSCAR_VIGENTES, query = "SELECT a FROM NovedadDetalle a where a.vigente=true"),
    @NamedQuery(name = NovedadDetalle.BUSCAR_VIGENTES_POR_NOMINA_ID, query = "SELECT a FROM NovedadDetalle a where a.novedad.nominaId =?1 and a.vigente=true "),
    @NamedQuery(name = NovedadDetalle.ENCERAR_VIGENTES_POR_NOMINA_ID, query = "UPDATE NovedadDetalle a SET a.valorCalculado=0,a.valorDescontado=0 WHERE a.novedad.nominaId =?1 and a.vigente=true "),
    @NamedQuery(name = NovedadDetalle.ENCERAR_VIGENTES_POR_NOMINA_SERVIDOR, query = "UPDATE NovedadDetalle a SET a.valorCalculado=0,a.valorDescontado=0 WHERE a.novedad.nominaId =?1 AND a.servidorId=?2 AND a.vigente=true "),
    @NamedQuery(name = NovedadDetalle.BUSCAR_VIGENTES_POR_NOVEDAD, query = "SELECT a FROM NovedadDetalle a where a.novedad.id =?1 and a.vigente=true "),
    @NamedQuery(name = NovedadDetalle.BUSCAR_POR_SERVIDOR_NOMINA_DATOADICIONAL, query = "SELECT o FROM NovedadDetalle o WHERE o.vigente=true AND o.novedad.institucionEjercicioFiscalId=?1 AND o.novedad.nominaId=?2 AND o.novedad.datoAdicionalId=?3 AND o.servidorId=?4 AND o.novedad.vigente=true"),
    @NamedQuery(name = NovedadDetalle.BUSCAR_DUPLICADOS, query = "SELECT o FROM NovedadDetalle o WHERE o.vigente=true AND o.novedad.institucionEjercicioFiscalId=?1 AND o.novedad.nominaId=?2 AND o.novedad.datoAdicionalId=?3 AND o.servidorId=?4 AND o.id<>?5 AND o.novedad.vigente=true")

})
public class NovedadDetalle extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "NovedadDetalle.buscarVigentes";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES_POR_NOMINA_ID = "NovedadDetalle.buscarPorNomina";

    /**
     * BUSCAR_VIGENTES_POR_NOMINA_ID Nombre de la consulta para buscar por
     * nombre una clase.
     */
    public static final String ENCERAR_VIGENTES_POR_NOMINA_ID = "NovedadDetalle.encerarPorNomina";

    /**
     * BUSCAR_VIGENTES_POR_NOMINA_ID Nombre de la consulta para buscar por
     * nombre una clase.
     */
    public static final String ENCERAR_VIGENTES_POR_NOMINA_SERVIDOR = "NovedadDetalle.encerarPorNominaServidor";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES_POR_NOVEDAD = "NovedadDetalle.buscarPorNovedad";

    /**
     * Recuperar servidores por nomina, y datos adicional.
     */
    public static final String BUSCAR_POR_SERVIDOR_NOMINA_DATOADICIONAL = "NovedadDetalle.buscarPorServidorNominaDatoAdicional";

    /**
     *
     */
    public static final String BUSCAR_DUPLICADOS = "NovedadDetalle.buscarDuplicados";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo valor.
     */
    @Basic(optional = false)
    @Column(name = "valor")
    @NotNull
    private BigDecimal valor;

    /**
     * Valor descontado.
     */
    @Basic(optional = false)
    @Column(name = "valor_descontado")
    private BigDecimal valorDescontado;

    /**
     * Valor calculado.
     */
    @Basic(optional = false)
    @Column(name = "valor_calculado")
    private BigDecimal valorCalculado;

    /**
     *
     */
    @JoinColumn(name = "servidor_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     *
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     *
     */
    @JoinColumn(name = "novedad_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Novedad novedad;

    /**
     *
     */
    @Column(name = "novedad_id")
    private Long novedadId;

    @Transient
    private boolean editado;

    @Transient
    private String valorNumericoEnCSV;

    @Transient
    private List<String> erroresAlCargarServidor = new ArrayList<String>();

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
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the valorDescontado
     */
    public BigDecimal getValorDescontado() {
        return valorDescontado;
    }

    /**
     * @param valorDescontado the valorDescontado to set
     */
    public void setValorDescontado(final BigDecimal valorDescontado) {
        this.valorDescontado = valorDescontado;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(final Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the novedad
     */
    public Novedad getNovedad() {
        return novedad;
    }

    /**
     * @param novedad the novedad to set
     */
    public void setNovedad(final Novedad novedad) {
        this.novedad = novedad;
    }

    /**
     * @return the novedadId
     */
    public Long getNovedadId() {
        return novedadId;
    }

    /**
     * @param novedadId the novedadId to set
     */
    public void setNovedadId(final Long novedadId) {
        this.novedadId = novedadId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override

    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!obj.getClass().getName().equals(getClass().getName())) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        NovedadDetalle otro = (NovedadDetalle) obj;

        if ((id != null && otro.getId() != null) && id.equals(otro.getId())) {
            return true;
        }

        if (otro.getServidor() != null && this.servidor != null) {
            if (otro.getServidor().getNumeroIdentificacion().equals(this.servidor.getNumeroIdentificacion())) {
                return true;
            }
        }

        return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.servidor != null ? this.servidor.hashCode() : 0);
        return hash;
    }

    /**
     * @return the editado
     */
    public boolean isEditado() {
        return editado;
    }

    /**
     * @param editado the editado to set
     */
    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public String getValorNumericoEnCSV() {
        return valorNumericoEnCSV;
    }

    public void setValorNumericoEnCSV(String valorNumericoEnCSV) {
        this.valorNumericoEnCSV = valorNumericoEnCSV;
    }

    public List<String> getErroresAlCargarServidor() {
        return erroresAlCargarServidor;
    }

    public void setErroresAlCargarServidor(List<String> erroresAlCargarServidor) {
        this.erroresAlCargarServidor = erroresAlCargarServidor;
    }

    /**
     * @return the valorCalculado
     */
    public BigDecimal getValorCalculado() {
        return valorCalculado;
    }

    /**
     * @param valorCalculado the valorCalculado to set
     */
    public void setValorCalculado(BigDecimal valorCalculado) {
        this.valorCalculado = valorCalculado;
    }

}
