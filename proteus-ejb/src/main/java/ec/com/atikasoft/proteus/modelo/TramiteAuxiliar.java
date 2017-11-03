package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos auxiliares del tramite.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "tramites_auxiliar", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TramiteAuxiliar.BUSCAR_POR_TRAMITE, query
            = "SELECT o FROM TramiteAuxiliar o WHERE o.tramite.id=?1")
})
public class TramiteAuxiliar extends EntidadBasica {

    /**
     * Nombre de la consulta que permite buscar por tramite.
     */
    public static final String BUSCAR_POR_TRAMITE = "TramiteAuxiliar.buscarPorTramite";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * tipo del flujo.
     */
    @Column(name = "tipo_flujo")
    private String tipoFlujo;

    /**
     * Tiempo maximo de duracion del movimiento.
     */
    @Column(name = "tiempo_maximo")
    private Integer tiempoMaximo;

    /**
     * Indicador si usa o no horario.
     */
    @Column(name = "horario")
    private Boolean horario;

    /**
     * Valor del horario.
     */
    @Column(name = "valor_horario")
    private Integer valorHorario;

    /**
     * Periodo del horario.
     */
    @Column(name = "periodo_horario")
    private String periodoHorario;

    /**
     * periodo Tiempo maximo de duracion del movimiento.
     */
    @Column(name = "periodo_tiempo_maximo")
    private String periodoTiempoMaximo;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "rige_apartir_de")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rigeApartirDe;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "fecha_hasta")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaHasta;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "numero_documento_accion")
    private String numeroDocumentoAccion;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "fecha_documento_accion")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDocumentoAccion;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "explicacion")
    private String explicacion;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "documento_previo")
    private String documentoPrevio;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "antecedentes_contrato")
    private String antecedentesContrato;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "actividades_contrato")
    private String actividadesContrato;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "asunto_memo")
    private String asuntoMeno;

    /**
     * Campo de generacion masiva.
     */
    @Column(name = "contenido_memo")
    private String contenidoMemo;

    /**
     * Detalle de la solicitud.
     */
    @Column(name = "detalle_solicitud")
    private String detalleSolicitud;

    /**
     * Referencia con tramite.
     */
    @OneToOne
    @JoinColumn(name = "tramite_id")
    private Tramite tramite;

    /**
     *
     */
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Constructor.
     */
    public TramiteAuxiliar() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TramiteAuxiliar(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the tiempoMaximo
     */
    public Integer getTiempoMaximo() {
        return tiempoMaximo;
    }

    /**
     * @return the horario
     */
    public Boolean getHorario() {
        return horario;
    }

    /**
     * @return the valorHorario
     */
    public Integer getValorHorario() {
        return valorHorario;
    }

    /**
     * @return the periodoHorario
     */
    public String getPeriodoHorario() {
        return periodoHorario;
    }

    /**
     * @return the periodoTiempoMaximo
     */
    public String getPeriodoTiempoMaximo() {
        return periodoTiempoMaximo;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param tiempoMaximo the tiempoMaximo to set
     */
    public void setTiempoMaximo(final Integer tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(final Boolean horario) {
        this.horario = horario;
    }

    /**
     * @param valorHorario the valorHorario to set
     */
    public void setValorHorario(final Integer valorHorario) {
        this.valorHorario = valorHorario;
    }

    /**
     * @param periodoHorario the periodoHorario to set
     */
    public void setPeriodoHorario(final String periodoHorario) {
        this.periodoHorario = periodoHorario;
    }

    /**
     * @param periodoTiempoMaximo the periodoTiempoMaximo to set
     */
    public void setPeriodoTiempoMaximo(final String periodoTiempoMaximo) {
        this.periodoTiempoMaximo = periodoTiempoMaximo;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the tipoFlujo
     */
    public String getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * @param tipoFlujo the tipoFlujo to set
     */
    public void setTipoFlujo(final String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    /**
     * @return the rigeApartirDe
     */
    public Date getRigeApartirDe() {
        return rigeApartirDe;
    }

    /**
     * @param rigeApartirDe the rigeApartirDe to set
     */
    public void setRigeApartirDe(final Date rigeApartirDe) {
        this.rigeApartirDe = rigeApartirDe;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(final Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @return the numeroDocumentoAccion
     */
    public String getNumeroDocumentoAccion() {
        return numeroDocumentoAccion;
    }

    /**
     * @param numeroDocumentoAccion the numeroDocumentoAccion to set
     */
    public void setNumeroDocumentoAccion(final String numeroDocumentoAccion) {
        this.numeroDocumentoAccion = numeroDocumentoAccion;
    }

    /**
     * @return the fechaDocumentoAccion
     */
    public Date getFechaDocumentoAccion() {
        return fechaDocumentoAccion;
    }

    /**
     * @param fechaDocumentoAccion the fechaDocumentoAccion to set
     */
    public void setFechaDocumentoAccion(final Date fechaDocumentoAccion) {
        this.fechaDocumentoAccion = fechaDocumentoAccion;
    }

    /**
     * @return the explicacion
     */
    public String getExplicacion() {
        return explicacion;
    }

    /**
     * @param explicacion the explicacion to set
     */
    public void setExplicacion(final String explicacion) {
        this.explicacion = explicacion;
    }

    /**
     * @return the documentoPrevio
     */
    public String getDocumentoPrevio() {
        return documentoPrevio;
    }

    /**
     * @param documentoPrevio the documentoPrevio to set
     */
    public void setDocumentoPrevio(final String documentoPrevio) {
        this.documentoPrevio = documentoPrevio;
    }

    /**
     * @return the antecedentesContrato
     */
    public String getAntecedentesContrato() {
        return antecedentesContrato;
    }

    /**
     * @param antecedentesContrato the antecedentesContrato to set
     */
    public void setAntecedentesContrato(final String antecedentesContrato) {
        this.antecedentesContrato = antecedentesContrato;
    }

    /**
     * @return the actividadesContrato
     */
    public String getActividadesContrato() {
        return actividadesContrato;
    }

    /**
     * @param actividadesContrato the actividadesContrato to set
     */
    public void setActividadesContrato(final String actividadesContrato) {
        this.actividadesContrato = actividadesContrato;
    }

    /**
     * @return the asuntoMeno
     */
    public String getAsuntoMeno() {
        return asuntoMeno;
    }

    /**
     * @param asuntoMeno the asuntoMeno to set
     */
    public void setAsuntoMeno(final String asuntoMeno) {
        this.asuntoMeno = asuntoMeno;
    }

    /**
     * @return the contenidoMemo
     */
    public String getContenidoMemo() {
        return contenidoMemo;
    }

    /**
     * @param contenidoMemo the contenidoMemo to set
     */
    public void setContenidoMemo(final String contenidoMemo) {
        this.contenidoMemo = contenidoMemo;
    }

    /**
     * @return the detalleSolicitud
     */
    public String getDetalleSolicitud() {
        return detalleSolicitud;
    }

    /**
     * @param detalleSolicitud the detalleSolicitud to set
     */
    public void setDetalleSolicitud(final String detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }

    /**
     * @return the tipoPeriodo
     */
    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo the tipoPeriodo to set
     */
    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }
}
