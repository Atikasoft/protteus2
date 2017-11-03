/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "nominas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Nomina.BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID_ESTADO,
            query = "SELECT a FROM Nomina a where a.periodoNominaId=?1 and "
            + "a.institucionEjercicioFiscalId=?2 and a.estado=?3 and a.vigente=true "),
    @NamedQuery(name = Nomina.BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID,
            query = "SELECT a FROM Nomina a where a.periodoNominaId=?1 and "
            + "a.institucionEjercicioFiscalId=?2 and a.vigente=true "),
    @NamedQuery(name = Nomina.BUSCAR_POR_FILTRO,
            query = "SELECT a FROM Nomina a where a.periodoNominaId=?1 and "
            + "a.institucionEjercicioFiscalId=?2 and a.tipoNomina.codigo=?3"),
    @NamedQuery(name = Nomina.QUITAR_CALCULANDO,query = "UPDATE Nomina o SET o.calculando=false WHERE o.calculando=true")
})
public class Nomina extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID = "Nomina.buscarVigentesPorPeriodoNominaId";
    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID_ESTADO = "Nomina.buscarVigentesPorPeriodoNominaIdEstado";
    /**
     * Nombre de la consulta.
     */
    public static final String BUSCAR_POR_FILTRO = "Nomina.buscarPorFiltroNomina";
    /**
     *
     */
    public static final String QUITAR_CALCULANDO = "Nomina.quitarCalculando";

    @Override
    public String toString() {
        //return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return UtilCadena.concatenar(id, "-", descripcion, "-", tipoNomina.getNombre(), "-", periodoNomina.getNombre());
    }
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo codigo.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private String numero;

    /**
     * Campo descripcion.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "institucion_ejercicio_fiscal_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;

    @JoinColumn(name = "periodo_nomina_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PeriodoNomina periodoNomina;

    @Column(name = "periodo_nomina_id")
    private Long periodoNominaId;

    @JoinColumn(name = "tipo_nomina_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoNomina tipoNomina;

    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_generacion")
    private Date fechaGeneracion;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_aprovacion")
    private Date fechaAprovacion;

    @Column(name = "estado", nullable = false)
    private String estado;

    /**
     * Lista de archivos sipari.
     */
    @OneToMany(mappedBy = "nomina")
    private List<ArchivoSipari> listaArchivoSIPARI;

    /**
     * Nombre del estado.
     */
    @Transient
    private String estadoNombre;

    /**
     *
     */
    @Column(name = "minimo_pagar_tipo")
    private String minimoPagarTipo;

    /**
     *
     */
    @Column(name = "minimo_pagar_valor")
    private BigDecimal minimoPagarValor;

    /**
     *
     */
    @Column(name = "calculando")
    private Boolean calculando;

    /**
     *
     */
    @Column(name = "bloqueado")
    private Boolean bloqueado;

    /**
     *
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     *
     * @param id
     */
    public Nomina(Long id) {
        super();
        this.id = id;

    }

    /**
     *
     */
    public Nomina() {
        super();
    }

    /**
     *
     */
    @PostLoad
    public void postLoad() {
        if (estado.equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
            estadoNombre = EstadoNominaEnum.ABIERTO.getDescripcion();
        } else if (estado.equals(EstadoNominaEnum.VALIDADO.getCodigo())) {
            estadoNombre = EstadoNominaEnum.VALIDADO.getDescripcion();
        } else if (estado.equals(EstadoNominaEnum.APROBADO.getCodigo())) {
            estadoNombre = EstadoNominaEnum.APROBADO.getDescripcion();
        } else if (estado.equals(EstadoNominaEnum.RECHAZAR.getCodigo())) {
            estadoNombre = EstadoNominaEnum.RECHAZAR.getDescripcion();
        } else if (estado.equals(EstadoNominaEnum.ANULAR.getCodigo())) {
            estadoNombre = EstadoNominaEnum.ANULAR.getDescripcion();
        }
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
     * @return the periodoNomina
     */
    public PeriodoNomina getPeriodoNomina() {
        return periodoNomina;
    }

    /**
     * @param periodoNomina the periodoNomina to set
     */
    public void setPeriodoNomina(final PeriodoNomina periodoNomina) {
        this.periodoNomina = periodoNomina;
    }

    /**
     * @return the periodoNominaId
     */
    public Long getPeriodoNominaId() {
        return periodoNominaId;
    }

    /**
     * @param periodoNominaId the periodoNominaId to set
     */
    public void setPeriodoNominaId(final Long periodoNominaId) {
        this.periodoNominaId = periodoNominaId;
    }

    /**
     * @return the tipoNomina
     */
    public TipoNomina getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(final TipoNomina tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the tipoNominaId
     */
    public Long getTipoNominaId() {
        return tipoNominaId;
    }

    /**
     * @param tipoNominaId the tipoNominaId to set
     */
    public void setTipoNominaId(final Long tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
    }

    /**
     * @return the fechaGeneracion
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * @param fechaGeneracion the fechaGeneracion to set
     */
    public void setFechaGeneracion(final Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * @return the fechaAprovacion
     */
    public Date getFechaAprovacion() {
        return fechaAprovacion;
    }

    /**
     * @param fechaAprovacion the fechaAprovacion to set
     */
    public void setFechaAprovacion(final Date fechaAprovacion) {
        this.fechaAprovacion = fechaAprovacion;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * @return the minimoPagarTipo
     */
    public String getMinimoPagarTipo() {
        return minimoPagarTipo;
    }

    /**
     * @param minimoPagarTipo the minimoPagarTipo to set
     */
    public void setMinimoPagarTipo(final String minimoPagarTipo) {
        this.minimoPagarTipo = minimoPagarTipo;
    }

    /**
     * @return the minimoPagarValor
     */
    public BigDecimal getMinimoPagarValor() {
        return minimoPagarValor;
    }

    /**
     * @param minimoPagarValor the minimoPagarValor to set
     */
    public void setMinimoPagarValor(final BigDecimal minimoPagarValor) {
        this.minimoPagarValor = minimoPagarValor;
    }

    /**
     * @return the estadoNombre
     */
    public String getEstadoNombre() {
        return estadoNombre;
    }

    /**
     * @param estadoNombre the estadoNombre to set
     */
    public void setEstadoNombre(final String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    /**
     * @return the listaArchivoSIPARI
     */
    public List<ArchivoSipari> getListaArchivoSIPARI() {
        return listaArchivoSIPARI;
    }

    /**
     * @param listaArchivoSIPARI the listaArchivoSIPARI to set
     */
    public void setListaArchivoSIPARI(List<ArchivoSipari> listaArchivoSIPARI) {
        this.listaArchivoSIPARI = listaArchivoSIPARI;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the calculando
     */
    public Boolean getCalculando() {
        return calculando;
    }

    /**
     * @param calculando the calculando to set
     */
    public void setCalculando(Boolean calculando) {
        this.calculando = calculando;
    }

    /**
     * @return the bloqueado
     */
    public Boolean getBloqueado() {
        return bloqueado;
    }

    /**
     * @param bloqueado the bloqueado to set
     */
    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

}
