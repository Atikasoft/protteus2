/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
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
@Table(name = "novedades", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Novedad.BUSCAR_VIGENTES,
            query = "SELECT a FROM Novedad a where a.vigente=true"),
    @NamedQuery(name = Novedad.BUSCAR_POR_PERIODO_ID_Y_TIPO_NOMINA_ID,
            query = "SELECT a FROM Novedad a where a.nomina.periodoNomina.id=?1 and "
            + "a.nomina.tipoNomina.id=?2 and a.vigente=true order by a.fechaCreacion DESC"),
    @NamedQuery(name = Novedad.BUSCAR_POR_NOMINA_ID_Y_PERIDO_NOMINA_ID,
            query = "SELECT a FROM Novedad a where a.nomina.id=?1 and "
            + "a.nomina.periodoNomina.id=?2 and a.datoAdicionalId=?3 and a.vigente=true order by a.fechaCreacion DESC"),
    @NamedQuery(name = Novedad.BUSCAR_POR_NOMINA_ID,
            query = "SELECT a FROM Novedad a where a.nomina.id=?1 and a.vigente=true order by a.fechaCreacion DESC")
})
public class Novedad extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "Novedad.buscarVigentes";
    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_PERIODO_ID_Y_TIPO_NOMINA_ID = "Novedad.buscarPorPeriodIdYTipoNominaId";
    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NOMINA_ID = "Novedad.buscarPorNominaId";
    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NOMINA_ID_Y_PERIDO_NOMINA_ID = "Novedad.buscarPorNominaIdYPerisodosNominaId";

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
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
    @Column(name = "numero")
    private String numero;

    /**
     *
     */
    @Transient
    private String nombreUnidadOrganizacional;

    /**
     *
     */
    @Transient
    private Integer totalRegistros;

    /**
     *
     */
    @Transient
    private BigDecimal totalValor;

    /**
     *
     */
    @Column(name = "descripcion")
    private String descripcion;
    /**
     *
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;
    /**
     *
     */
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;
    /**
     *
     */
    @JoinColumn(name = "nomina_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Nomina nomina;
    /**
     *
     */
    @Column(name = "nomina_id")
    private Long nominaId;
    /**
     *
     */
    @JoinColumn(name = "dato_adicional_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DatoAdicional datoAdicional;
    /**
     *
     */
    @Column(name = "dato_adicional_id")
    private Long datoAdicionalId;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;
    /**
     *
     */
    @Column(name = "unidad_organizacional_id")
    private Long unidadOrganizacionalId;

    /**
     *
     */
    @OneToMany(mappedBy = "novedad")
    private List<NovedadDetalle> listaDetalles;

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
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(final String numero) {
        this.numero = numero;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(final InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the institucionEjercicioFiscalId
     */
    public Long getInstitucionEjercicioFiscalId() {
        return institucionEjercicioFiscalId;
    }

    /**
     * @param institucionEjercicioFiscalId the institucionEjercicioFiscalId to
     * set
     */
    public void setInstitucionEjercicioFiscalId(final Long institucionEjercicioFiscalId) {
        this.institucionEjercicioFiscalId = institucionEjercicioFiscalId;
    }

    /**
     * @return the datoAdicional
     */
    public DatoAdicional getDatoAdicional() {
        return datoAdicional;
    }

    /**
     * @param datoAdicional the datoAdicional to set
     */
    public void setDatoAdicional(final DatoAdicional datoAdicional) {
        this.datoAdicional = datoAdicional;
    }

    /**
     * @return the datoAdicionalId
     */
    public Long getDatoAdicionalId() {
        return datoAdicionalId;
    }

    /**
     * @param datoAdicionalId the datoAdicionalId to set
     */
    public void setDatoAdicionalId(final Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(final Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the unidadOrganizacionalId
     */
    public Long getUnidadOrganizacionalId() {
        return unidadOrganizacionalId;
    }

    /**
     * @param unidadOrganizacionalId the unidadOrganizacionalId to set
     */
    public void setUnidadOrganizacionalId(Long unidadOrganizacionalId) {
        this.unidadOrganizacionalId = unidadOrganizacionalId;
    }

    /**
     * @return the nombreUnidadOrganizacional
     */
    public String getNombreUnidadOrganizacional() {
        return nombreUnidadOrganizacional;
    }

    /**
     * @param nombreUnidadOrganizacional the nombreUnidadOrganizacional to set
     */
    public void setNombreUnidadOrganizacional(String nombreUnidadOrganizacional) {
        this.nombreUnidadOrganizacional = nombreUnidadOrganizacional;
    }

    /**
     * @return the totalRegistros
     */
    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the totalValor
     */
    public BigDecimal getTotalValor() {
        return totalValor;
    }

    /**
     * @param totalValor the totalValor to set
     */
    public void setTotalValor(BigDecimal totalValor) {
        this.totalValor = totalValor;
    }

    /**
     * @return the listaDetalles
     */
    public List<NovedadDetalle> getListaDetalles() {
        return listaDetalles;
    }

    /**
     * @param listaDetalles the listaDetalles to set
     */
    public void setListaDetalles(List<NovedadDetalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
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
}
