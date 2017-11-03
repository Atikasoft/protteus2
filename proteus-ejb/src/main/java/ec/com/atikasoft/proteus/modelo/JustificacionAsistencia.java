package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Registra las justificaciones por ausentismo u horas extras.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "asistencias_justificaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = JustificacionAsistencia.BUSCAR_VIGENTE,
            query = "Select o from JustificacionAsistencia o where  o.vigente=true  "),
    @NamedQuery(name = JustificacionAsistencia.BUSCAR_POR_ATRASO,
            query = "Select o from JustificacionAsistencia o where  o.vigente=true and o.atraso.id=?1 ")
})
public class JustificacionAsistencia extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_VIGENTE
            = "Justificacion.buscarVigente";
        /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_ATRASO
            = "Justificacion.buscarPorAtraso";
    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a solicitud de vacaciones.
     */
    @JoinColumn(name = "vacacion_solicitud_id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private VacacionSolicitud vacacionSolicitud;

    /**
     * Referencia al atraso/falta/hora extra.
     */
    @JoinColumn(name = "atraso_id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Atraso atraso;

    /**
     * Referencia a institucion_ejercicio_fiscal_id.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @NotNull
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;
    /**
     * Campo que indica la fecha de la falta o atraso.
     */
    @Column(name = "fecha", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    /**
     * Referencia a Servidor.
     */
    @JoinColumn(name = "servidor_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    private Servidor servidor;

    /**
     * Referencia a Servidor.
     */
    @JoinColumn(name = "servidor_aprobador_id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Servidor servidorAprobador;

    /**
     * Unidad de tiempo: DIA, MINUTOS.
     */
    @Size(max = 1)
    @NotNull
    @Column(name = "unidad_tiempo")
    private String unidadTiempo;

    /**
     * Cantidad justificada, en unidad de tiempo.
     */
    @NotNull
    @Column(name = "cantidad_tiempo")
    private Long cantidadTiempo;

    @Size(max = 1000)
    @Column(name = "observacion")
    private String observacion;

    /**
     * Constructor.
     */
    public JustificacionAsistencia() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public JustificacionAsistencia(final Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the vacacionSolicitud
     */
    public VacacionSolicitud getVacacionSolicitud() {
        return vacacionSolicitud;
    }

    /**
     * @param vacacionSolicitud the vacacionSolicitud to set
     */
    public void setVacacionSolicitud(VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
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
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
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
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the servidorAprobador
     */
    public Servidor getServidorAprobador() {
        return servidorAprobador;
    }

    /**
     * @param servidorAprobador the servidorAprobador to set
     */
    public void setServidorAprobador(Servidor servidorAprobador) {
        this.servidorAprobador = servidorAprobador;
    }

    /**
     * @return the unidadTiempo
     */
    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    /**
     * @param unidadTiempo the unidadTiempo to set
     */
    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    /**
     * @return the cantidadTiempo
     */
    public Long getCantidadTiempo() {
        return cantidadTiempo;
    }

    /**
     * @param cantidadTiempo the cantidadTiempo to set
     */
    public void setCantidadTiempo(Long cantidadTiempo) {
        this.cantidadTiempo = cantidadTiempo;
    }

    /**
     * @return the atraso
     */
    public Atraso getAtraso() {
        return atraso;
    }

    /**
     * @param atraso the atraso to set
     */
    public void setAtraso(Atraso atraso) {
        this.atraso = atraso;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
