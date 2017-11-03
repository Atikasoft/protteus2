package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;

/**
 * Registra los devengamientos que debe realizar los servidores por efectos de
 * licencias de estudio p cualquier otro motivo.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "devengamientos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Devengamiento.BUSCAR_POR_SERVIDOR, query
            = "SELECT o FROM Devengamiento o WHERE o.vigente=true AND o.tipoIdentificacion=?1 AND o.numeroIdentificacion=?2 "
            + " AND o.institucion.id=?3 AND o.pagoAnticipado=false AND ?4 <= o.fechaFinal"),
    @NamedQuery(name = Devengamiento.BUSCAR_POR_NUMERO_IDENTIFICACION, query
            = "SELECT o FROM Devengamiento o WHERE o.vigente=true AND o.numeroIdentificacion=?1 ")
})
public class Devengamiento extends EntidadBasica {

    /**
     * Busca devengaciones pendientes por servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR = "Devengamiento.buscarPorServidor";

    /**
     * Busca devengaciones pendientes por servidor.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION = "Devengamiento.buscarPorNumeroIdentificacion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Numero del devengamiento.
     */
    @Column(name = "numero")
    private String numero;

    /**
     * Fecha de inicio del devengamiento.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    /**
     * Fecha de final del devengamiento.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_final")
    private Date fechaFinal;

    /**
     * Tipo de identificacion del servidor.
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     * Numero de identificacion del servidor.
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     * Nombres completos del servidor.
     */
    @Column(name = "nombres_completos")
    private String nombresCompletos;

    /**
     * Observaciones realizadas en el registro.
     */
    @Column(name = "observacion_registro")
    private String observacionRegistro;

    /**
     * Indicado si el devengamiento fue pagado con anticipacion..
     */
    @Column(name = "pago_anticipado")
    private Boolean pagoAnticipado;

    /**
     * Fecha del pago anticipado.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_pago_anticipado")
    private Date fechaPagoAnticipado;

    /**
     * Observaciones realizadas al pago anticipado.
     */
    @Column(name = "observacion_pago_anticipado")
    private String observacionPagoAnticipado;

    /**
     * Dias que efectivamente se devengo.
     */
    @Column(name = "dias_devengados")
    private Integer diasDevengados;

    /**
     * Dias pendientes por devengar.
     */
    @Column(name = "dias_pendientes")
    private Integer diasPendientes;

    /**
     * Numero de documento que justifica el pago..
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Fecha de documento que justifica el pago..
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_documento")
    private Date fechaDocumento;

    /**
     * Referencia de la institucion.
     */
    @JoinColumn(name = "institucion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Institucion institucion;

    /**
     * Referencia del archivo.
     */
    @JoinColumn(name = "archivo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     * Referencia de licencia.
     */
    @JoinColumn(name = "licencia_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Licencia licencia;

    /**
     * Constructor.
     */
    public Devengamiento() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Devengamiento(final Long id) {
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
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @return the nombresCompletos
     */
    public String getNombresCompletos() {
        return nombresCompletos;
    }

    /**
     * @return the observacionRegistro
     */
    public String getObservacionRegistro() {
        return observacionRegistro;
    }

    /**
     * @return the pagoAnticipado
     */
    public Boolean getPagoAnticipado() {
        return pagoAnticipado;
    }

    /**
     * @return the fechaPagoAnticipado
     */
    public Date getFechaPagoAnticipado() {
        return fechaPagoAnticipado;
    }

    /**
     * @return the observacionPagoAnticipado
     */
    public String getObservacionPagoAnticipado() {
        return observacionPagoAnticipado;
    }

    /**
     * @return the diasDevengados
     */
    public Integer getDiasDevengados() {
        return diasDevengados;
    }

    /**
     * @return the diasPendientes
     */
    public Integer getDiasPendientes() {
        return diasPendientes;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @return the fechaDocumento
     */
    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(final String numero) {
        this.numero = numero;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(final Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @param nombresCompletos the nombresCompletos to set
     */
    public void setNombresCompletos(final String nombresCompletos) {
        this.nombresCompletos = nombresCompletos;
    }

    /**
     * @param observacionRegistro the observacionRegistro to set
     */
    public void setObservacionRegistro(final String observacionRegistro) {
        this.observacionRegistro = observacionRegistro;
    }

    /**
     * @param pagoAnticipado the pagoAnticipado to set
     */
    public void setPagoAnticipado(final Boolean pagoAnticipado) {
        this.pagoAnticipado = pagoAnticipado;
    }

    /**
     * @param fechaPagoAnticipado the fechaPagoAnticipado to set
     */
    public void setFechaPagoAnticipado(final Date fechaPagoAnticipado) {
        this.fechaPagoAnticipado = fechaPagoAnticipado;
    }

    /**
     * @param observacionPagoAnticipado the observacionPagoAnticipado to set
     */
    public void setObservacionPagoAnticipado(final String observacionPagoAnticipado) {
        this.observacionPagoAnticipado = observacionPagoAnticipado;
    }

    /**
     * @param diasDevengados the diasDevengados to set
     */
    public void setDiasDevengados(final Integer diasDevengados) {
        this.diasDevengados = diasDevengados;
    }

    /**
     * @param diasPendientes the diasPendientes to set
     */
    public void setDiasPendientes(final Integer diasPendientes) {
        this.diasPendientes = diasPendientes;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @param fechaDocumento the fechaDocumento to set
     */
    public void setFechaDocumento(final Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final Archivo archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the licencia
     */
    public Licencia getLicencia() {
        return licencia;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(final Licencia licencia) {
        this.licencia = licencia;
    }
}
