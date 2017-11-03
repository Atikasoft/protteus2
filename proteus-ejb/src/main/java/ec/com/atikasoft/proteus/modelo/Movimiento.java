package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@Entity
@Table(name = "movimientos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Movimiento.BUSCAR_POR_TRAMITE, query = "Select o from Movimiento o where o.tramite.id=?1 and"
            + "  o.vigente=true AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_PERSONAS_DUPLICADAS, query = "SELECT o FROM Movimiento o WHERE o.vigente=true "
            + " AND o.distributivoDetalle.servidor.tipoIdentificacion=?1 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?2  AND o.id <> ?3 AND o.tramite.codigoFase IN"
            + " ('ELA','VAL','REV','APR') AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_PERSONAS_POR_TIPO_MOVIMIENTO_DUPLICADAS, query = "SELECT o FROM Movimiento o "
            + " WHERE o.vigente=true AND o.tipoIdentificacion=?1 AND o.numeroIdentificacion=?2 AND "
            + " o.tramite.tipoMovimiento.id=?3 AND o.tramite.id<>?4  AND o.tramite.codigoFase IN "
            + " ('ELA','VAL','REV','APR')  AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_PUESTOS_DUPLICADOS, query = "SELECT o FROM Movimiento o WHERE "
            + " o.vigente=true AND o.distributivoDetalleId=?1  AND o.id <> ?2 AND o.tramite.codigoFase "
            + " IN ('ELA','VAL','REV','APR') AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_PUESTOS_DUPLICADOS_VALIDACION, query = "SELECT o FROM Movimiento o WHERE "
            + " o.vigente=true AND o.distributivoDetalleId=?1 AND o.tramite.codigoFase IN ('ELA','VAL','REV','APR') "
            + " AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.QUITAR_VIGENCIA, query = "UPDATE Movimiento o SET o.vigente=false WHERE "
            + " o.vigente=true AND o.tramite.id = ?1"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_SERVIDOR_Y_ESTADO_TRAMITE, query = "SELECT o FROM Movimiento o WHERE "
            + " o.vigente=true AND o.distributivoDetalle.servidor.tipoIdentificacion=?1 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?2 AND o.tramite.tipoMovimiento.nemonico=?3  AND"
            + "  o.tramite.codigoFase =?4 AND o.tramite.vigente=true  ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_DOCUMENTO_HABILITANTE, query = "Select o from Movimiento o where "
            + " o.numeroDocumentoHabilitante=?1"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_SERVIDOR_ESTADO_FECHALEGALIZACION, query = "SELECT o FROM Movimiento o "
            + " WHERE o.vigente=true AND o.tramite.institucionEjercicioFiscal.institucion.id=?1 AND "
            + " o.distributivoDetalle.servidor.tipoIdentificacion=?2 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?3 AND o.tramite.tipoMovimiento.nemonico=?4 AND "
            + " o.tramite.codigoFase =?5 AND o.tramite.id IN (SELECT tb.tramite.id FROM TramiteBitacora tb "
            + " WHERE tb.fechaLegalizacion>=?6)"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_CEDULA_LICENCIA_ACADEMICA,
            query = "Select o from Movimiento o where o.vigente=true and "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?1 and "
            + " o.tramite.tipoMovimiento.clase.grupo.nemonico=?2 AND o.tramite.vigente=true and"
            + "  o.tramite.tipoMovimiento.areaTiempoPorDevengar=true ORDER BY o.id DESC"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_CEDULA,
            query = "Select o from Movimiento o where o.vigente=true and "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?1"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_SUSPENSION_TEMPORAL_VIGENTE,
            query = "SELECT o FROM Movimiento o WHERE o.vigente=true AND  o.tramite.tipoMovimiento.nemonico=?1 AND "
            + " o.tramite.codigoFase =?2  AND  o.fechaHasta>=?3 AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_PERIODO_POR_TIPO_MOVIMIENTO, query = "SELECT o FROM Movimiento o WHERE "
            + " o.vigente=true AND o.distributivoDetalle.servidor.tipoIdentificacion=?1 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?2 AND o.tramite.tipoMovimiento.id=?3 AND "
            + " o.tramite.codigoFase =?4 AND o.tramite.vigente=true AND o.rigeApartirDe BETWEEN ?5 AND ?6"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_PERIODO, query = "SELECT o FROM Movimiento o WHERE o.vigente=true AND "
            + " o.distributivoDetalle.servidor.tipoIdentificacion=?1 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?2 AND o.tramite.codigoFase =?3 AND  "
            + " o.rigeApartirDe BETWEEN ?4 AND ?5 AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_PUESTO_Y_TIPO_MOVIMIENTO, query = "SELECT o FROM Movimiento o WHERE "
            + " o.vigente=true AND o.distributivoDetalleId=?1 AND o.tramite.tipoMovimiento.nemonico=?2 AND "
            + " o.tramite.codigoFase =?3 AND o.tramite.vigente=true"),
    @NamedQuery(name = Movimiento.ACTUALIZAR_POR_TRAMITE, query = "update Movimiento m set m.rigeApartirDe=?1, "
            + " m.fechaHasta=?2, m.accionPersonalExplicacion=?3, m.accionPersonalDocumentoPrevio=?4, "
            + " m.accionPersonalNumeroDocumento=?5, m.accionPersonalFechaDocumento=?6, m.asuntoMemo=?7, "
            + " m.contenidoMemo=?8,m.tipoPeriodo=?9 where m.tramite.id=?10 and m.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_POR_NUMERO_IDENTIFICACION, query = "SELECT c FROM Movimiento c where "
            + " c.numeroIdentificacion=?1 and c.vigente=true"),
    @NamedQuery(name = Movimiento.BUSCAR_PARA_FINALIZACION, query = "SELECT o FROM Movimiento o WHERE o.vigente=true "
            + " AND o.fechaHasta IS NOT NULL AND o.distributivoDetalle.servidor.tipoIdentificacion=?1 AND "
            + " o.distributivoDetalle.servidor.numeroIdentificacion=?2 AND "
            + " o.tramite.tipoMovimiento.tipoMovimientoFinalizacionId=?3 AND o.tramite.codigoFase='REG' AND "
            + " o.finalizado=false ORDER BY o.fechaHasta"),
    @NamedQuery(name = Movimiento.BUSCAR_INGRESOS_REGISTRADOS_POR_IDENTIFICACION, query = "SELECT o FROM Movimiento o"
            + "  WHERE o.vigente=true AND o.numeroIdentificacion=?1 AND "
            + " o.tramite.tipoMovimiento.clase.grupo.nemonico='GRU.ING' AND o.tramite.codigoFase='REG'")

})
public class Movimiento extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar identificacion.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION = "Movimiento.buscarPorNumeroIdentificacion";

    /**
     * Nombre de consulta.
     */
    public static final String ACTUALIZAR_POR_TRAMITE = "Movimiento.actualizarPorTramite";

    /**
     * Nombre de la consulta que recupera movimientos en un periodo de tiempo por servidor.
     */
    public static final String BUSCAR_POR_PERIODO_POR_TIPO_MOVIMIENTO = "Movimiento.buscarPorPeriodoPorTipoMovimiento";

    /**
     * Nombre de la consulta que recupera movimientos en un periodo de tiempo por servidor.
     */
    public static final String BUSCAR_POR_PERIODO = "Movimiento.buscarPorPeriodo";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_TRAMITE = "Movimiento.buscarPorTramite";

    /**
     * buscar movimientos por cedula y licencias. BUSCAR_POR_CEDULA
     */
    public static final String BUSCAR_POR_CEDULA_LICENCIA_ACADEMICA = "Movimiento.buscarPorCedulaLicenciaAcademica";

    /**
     * buscar movimientos por cedulas.
     */
    public static final String BUSCAR_POR_CEDULA = "Movimiento.buscarPorCedula";

    /**
     * Nombre de la consulta que recuperar movimientos duplicados (contienen la misma persona).
     */
    public static final String BUSCAR_PERSONAS_DUPLICADAS = "Movimiento.buscarDuplicados";

    /**
     * Nombre de la consulta que recuperar movimientos duplicados (contienen la misma persona y tipo de movimiento).
     */
    public static final String BUSCAR_PERSONAS_POR_TIPO_MOVIMIENTO_DUPLICADAS
            = "Movimiento.buscarPorTipoMovimientoDuplicados";

    /**
     * Nombre de la consulta que recupera movimiento duplicados (contienen el mismo puesto).
     */
    public static final String BUSCAR_PUESTOS_DUPLICADOS = "Movimiento.buscarPuestoDuplicado";

    /**
     * Nombre de la consulta que recupera movimiento duplicados (contienen el mismo puesto).
     */
    public static final String BUSCAR_PUESTOS_DUPLICADOS_VALIDACION = "Movimiento.buscarPuestoDuplicadoValidacion";

    /**
     * Busca un movimientos segun su numero de documento habilitante.
     */
    public static final String BUSCAR_POR_DOCUMENTO_HABILITANTE = "Movimiento.buscarPorNumeroDocumentoHabilitante";

    /**
     * Nombre de la consulta que recupera movimientos por servidor y estado del tramite.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_ESTADO_TRAMITE = "Movimiento.buscarPorServidorYEstadoTramite";

    /**
     * Nombre de la consulta que recupera movimientos por tipo de movimientos, servidor y estado.
     */
    public static final String BUSCAR_POR_SERVIDOR_ESTADO_FECHALEGALIZACION
            = "Movimiento.buscarPorServidorEstadoFechaLegalizacion";

    /**
     * Nombre de la consulta que recupera movimientos de regimen disciplinario que se encuentren vigentes.
     */
    public static final String BUSCAR_POR_SUSPENSION_TEMPORAL_VIGENTE = "Movimiento.buscarPorSuspensionTemporalVigente";

    /**
     * Nombre de la consulta que quita la vigencia de los movimientos de un tramite.
     */
    public static final String QUITAR_VIGENCIA = "Movimiento.quitarVigencia";

    /**
     * Nombre de la consulta que recupera movimientos dado el identificador del puesto y tipo de movimiento.
     */
    public static final String BUSCAR_POR_PUESTO_Y_TIPO_MOVIMIENTO = "Movimiento.buscarPorPuestoYTipoMovimiento";

    /**
     * Nombre de la consulta que recupera los movimientos que estan aptos para su finalizacion.
     */
    public static final String BUSCAR_PARA_FINALIZACION = "Movimiento.buscarParaFinalizacion";

    /**
     * Nombre de la consulta que buscara movimientos por ingresos.
     */
    public static final String BUSCAR_INGRESOS_REGISTRADOS_POR_IDENTIFICACION = "Movimiento.buscarIngresosPorIdentificacion";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    /**
     *
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;
    /**
     *
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;
    /**
     *
     */
    @Column(name = "apellidos")
    private String apellidos;
    /**
     *
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * Archivo del movimiento.
     */
    @JoinColumn(name = "archivo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     *
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     *
     */
    @Column(name = "rmu_sobrevalorado")
    private BigDecimal rmuSobrevalorado;

    /**
     *
     */
    @Column(name = "rige_apartir_de")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rigeApartirDe;

    /**
     * Copia del valor original de la fecha rige a partir de.
     */
    @Transient
    private Date rigeApartirDeCopia;

    /**
     *
     */
    @Column(name = "fecha_hasta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;

    /**
     *
     */
    @Column(name = "numero_registro")
    private String numeroRegistro;

    /**
     *
     */
    @Column(name = "numero_documento_habilitante")
    private String numeroDocumentoHabilitante;
    /**
     *
     */
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    /**
     *
     */
    @Column(name = "justificacion")
    private String justificacion;
    /**
     *
     */
    @Column(name = "numero_acto_administrativo")
    private String numeroActoAdministrativo;
    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_documento_gano_concurso")
    private Date fechaDocumentoGanoConcurso;
    /**
     *
     */
    @Column(name = "numero_documento_gano_concurso")
    private String numeroDocumentoGanoConcurso;
    /**
     *
     */
    @JoinColumn(name = "tramites_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tramite tramite;

    /**
     * Fecha inicio.
     */
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    /**
     * Fecha fin.
     */
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    /**
     * valor del rmu en letras.
     */
    @Column(name = "rmu_letras")
    private String rmuLetras;

    /**
     * Asunto del memorandu.
     */
    @Column(name = "asunto_memo")
    private String asuntoMemo;

    /**
     * Contenido del memorandu.
     */
    @Column(name = "contenido_memo")
    private String contenidoMemo;

    /**
     * Numero de memo.
     */
    @Column(name = "numero_memo")
    private String numeroMemo;

    /**
     * Situacion actual de accion de personal.
     */
    @Column(name = "situacion_actual")
    private Boolean situacionActual;

    /**
     * Situacion propuesta de accional de personal.
     */
    @Column(name = "situacion_propuesta")
    private Boolean situacionPropuesta;

    /**
     * Indica si el movimiento tiene justificacion.
     */
    @Column(name = "con_justificacion")
    private Boolean conJustificacion;

    /**
     * Explicacion de la accion de personal.
     */
    @Column(name = "accion_personal_explicacion")
    private String accionPersonalExplicacion;

    /**
     * documento previo de la accion de personal.
     */
    @Column(name = "accion_personal_documento_previo")
    private String accionPersonalDocumentoPrevio;

    /**
     * numero de documento de la accion de personal.
     */
    @Column(name = "accion_personal_numero_documento")
    private String accionPersonalNumeroDocumento;

    /**
     * fecha de documento de la accion de personal.
     */
    @Column(name = "accion_personal_fecha_documento")
    @Temporal(TemporalType.DATE)
    private Date accionPersonalFechaDocumento;

    /**
     * Antecedentes del adendum.
     */
    @Column(name = "adendum_antedecentes")
    private String adendumAntecedentes;

    /**
     * Indica se el movimiento se encuentra o no finalizado.
     */
    @Column(name = "finalizado")
    private Boolean finalizado;

    /**
     * Referencia de la escala remunerativa
     */
    @JoinColumn(name = "escala_ocupacional_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EscalaOcupacional escalaOcupacional;

    /**
     * Referencia de la unidad organizacional
     */
    @JoinColumn(name = "unidad_organizacional_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * Referencia de la unidad presupuestaria
     */
    @JoinColumn(name = "unidad_presupuestaria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * Referencia con los reintegros del movimientos.
     */
    @OneToOne(mappedBy = "movimiento")
    private MovimientoReintegro movimientoReintegro;

    /**
     * Referencia del detalle del distributivo.
     */
    @JoinColumn(name = "distributivo_detalle_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalle;

    /**
     * Distributivo detalle.
     */
    @Column(name = "distributivo_detalle_id")
    private Long distributivoDetalleId;

    /**
     * Referencia al a situacion actual del movimiento.
     */
    @OneToOne(mappedBy = "movimiento")
    private MovimientoSituacionActual movimientoSituacionActual;

    /**
     * Referencia al a situacion propuesta del movimiento.
     */
    @OneToOne(mappedBy = "movimiento")
    private MovimientoSituacionPropuesta movimientoSituacionPropuesta;

    /**
     * Lista de cesaciones.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Cesacion> listaCesaciones;

    /**
     * Lista de ingresos.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Ingreso> listaIngresos;

    /**
     * Lista de regimenes disciplinarios.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<RegimenDisciplinario> listaRegimenesDisciplinarios;

    /**
     * Lista de licencias.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Licencia> listaLicencias;

    /**
     * Lista de comisiones de servicios.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<ComisionServicio> listaComisionServicio;

    /**
     * Lista de cambios administrativos.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<CambioAdministrativo> listaCambioAdministrativo;

    /**
     * Lista de traslados administrativos.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<TrasladoAdministrativo> listaTrasladoAdministrativo;

    /**
     * Lista de traspaso
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Traspaso> listaTraspaso;

    /**
     * Lista de subrogaciones.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Subrogacion> listaSubrogacion;

    /**
     * Lista de encargos.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Encargo> listaEncargo;

    /**
     * Lista de finalizaciones.
     */
    @OneToMany(mappedBy = "movimiento")
    private List<Finalizacion> listaFinalizacion;

    /**
     * Variable con el nombre del genero de la persoan.
     */
    @Column(name = "genero")
    private String genero;

    /**
     * Tipode periodo usado en el movimiento.
     *
     * <F>echa Su <N>otificacion Su
     * <P>
     * osesion
     */
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Certificacion presupuestario.
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     * Puesto Institucional.
     */
    @Transient
    private String puestoInstitucionalDescripcion;

    @Transient
    private String nombreTipoDocumento;

    @Transient
    private Boolean existeDistributivo;

    /**
     * Constructor.
     */
    public Movimiento() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Movimiento(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "listaFinalizacion", "listaEncargo", "listaSubrogacion",
                "listaTraspaso", "listaTrasladoAdministrativo", "listaCambioAdministrativo", "listaComisionServicio",
                "listaLicencias", "listaRegimenesDisciplinarios", "listaIngresos", "listaCesaciones",
                "distributivoDetalle");
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the puestoInstitucionalDescripcion
     */
    public String getPuestoInstitucionalDescripcion() {
        return puestoInstitucionalDescripcion;
    }

    /**
     * @param puestoInstitucionalDescripcion the puestoInstitucionalDescripcion to set
     */
    public void setPuestoInstitucionalDescripcion(final String puestoInstitucionalDescripcion) {
        this.puestoInstitucionalDescripcion = puestoInstitucionalDescripcion;
    }

    /**
     * @return the fechaDocumentoGanoConcurso
     */
    public Date getFechaDocumentoGanoConcurso() {
        return fechaDocumentoGanoConcurso;
    }

    /**
     * @return the numeroDocumentoGanoConcurso
     */
    public String getNumeroDocumentoGanoConcurso() {
        return numeroDocumentoGanoConcurso;
    }

    /**
     * @param fechaDocumentoGanoConcurso the fechaDocumentoGanoConcurso to set
     */
    public void setFechaDocumentoGanoConcurso(final Date fechaDocumentoGanoConcurso) {
        this.fechaDocumentoGanoConcurso = fechaDocumentoGanoConcurso;
    }

    /**
     * @param numeroDocumentoGanoConcurso the numeroDocumentoGanoConcurso to set
     */
    public void setNumeroDocumentoGanoConcurso(final String numeroDocumentoGanoConcurso) {
        this.numeroDocumentoGanoConcurso = numeroDocumentoGanoConcurso;
    }

    /**
     * @return the rmuLetras
     */
    public String getRmuLetras() {
        return rmuLetras;
    }

    /**
     * @param rmuLetras the rmuLetras to set
     */
    public void setRmuLetras(final String rmuLetras) {
        this.rmuLetras = rmuLetras;
    }

    /**
     * @return the numeroDocumentoHabilitante
     */
    public String getNumeroDocumentoHabilitante() {
        return numeroDocumentoHabilitante;
    }

    /**
     * @param numeroDocumentoHabilitante the numeroDocumentoHabilitante to set
     */
    public void setNumeroDocumentoHabilitante(final String numeroDocumentoHabilitante) {
        this.numeroDocumentoHabilitante = numeroDocumentoHabilitante;
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
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres the apellidosNombres to set
     */
    public void setApellidosNombres(final String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(final String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
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
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the rmuSobrevalorado
     */
    public BigDecimal getRmuSobrevalorado() {
        return rmuSobrevalorado;
    }

    /**
     * @param rmuSobrevalorado the rmuSobrevalorado to set
     */
    public void setRmuSobrevalorado(final BigDecimal rmuSobrevalorado) {
        this.rmuSobrevalorado = rmuSobrevalorado;
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
     * @return the numeroRegistro
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @param numeroRegistro the numeroRegistro to set
     */
    public void setNumeroRegistro(final String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(final Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the justificacion
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * @param justificacion the justificacion to set
     */
    public void setJustificacion(final String justificacion) {
        this.justificacion = justificacion;
    }

    /**
     * @return the numeroActoAdministrativo
     */
    public String getNumeroActoAdministrativo() {
        return numeroActoAdministrativo;
    }

    /**
     * @param numeroActoAdministrativo the numeroActoAdministrativo to set
     */
    public void setNumeroActoAdministrativo(final String numeroActoAdministrativo) {
        this.numeroActoAdministrativo = numeroActoAdministrativo;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the listaCesaciones
     */
    public List<Cesacion> getListaCesaciones() {
        return listaCesaciones;
    }

    /**
     * @return the listaIngresos
     */
    public List<Ingreso> getListaIngresos() {
        return listaIngresos;
    }

    /**
     * @param listaCesaciones the listaCesaciones to set
     */
    public void setListaCesaciones(final List<Cesacion> listaCesaciones) {
        this.listaCesaciones = listaCesaciones;
    }

    /**
     * @param listaIngresos the listaIngresos to set
     */
    public void setListaIngresos(final List<Ingreso> listaIngresos) {
        this.listaIngresos = listaIngresos;
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
    public void setArchivo(final Archivo archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the asuntoMemo
     */
    public String getAsuntoMemo() {
        return asuntoMemo;
    }

    /**
     * @param asuntoMemo the asuntoMemo to set
     */
    public void setAsuntoMemo(final String asuntoMemo) {
        this.asuntoMemo = asuntoMemo;
    }

    /**
     * @return the situacionActual
     */
    public Boolean getSituacionActual() {
        return situacionActual;
    }

    /**
     * @return the situacionPropuesta
     */
    public Boolean getSituacionPropuesta() {
        return situacionPropuesta;
    }

    /**
     * @param situacionActual the situacionActual to set
     */
    public void setSituacionActual(final Boolean situacionActual) {
        this.situacionActual = situacionActual;
    }

    /**
     * @param situacionPropuesta the situacionPropuesta to set
     */
    public void setSituacionPropuesta(final Boolean situacionPropuesta) {
        this.situacionPropuesta = situacionPropuesta;
    }

    /**
     * @return the conJustificacion
     */
    public Boolean getConJustificacion() {
        return conJustificacion;
    }

    /**
     * @param conJustificacion the conJustificacion to set
     */
    public void setConJustificacion(final Boolean conJustificacion) {
        this.conJustificacion = conJustificacion;
    }

    /**
     * @return the contenidoMemo
     */
    public String getContenidoMemo() {
        return contenidoMemo;
    }

    /**
     * @return the numeroMemo
     */
    public String getNumeroMemo() {
        return numeroMemo;
    }

    /**
     * @param contenidoMemo the contenidoMemo to set
     */
    public void setContenidoMemo(final String contenidoMemo) {
        this.contenidoMemo = contenidoMemo;
    }

    /**
     * @param numeroMemo the numeroMemo to set
     */
    public void setNumeroMemo(final String numeroMemo) {
        this.numeroMemo = numeroMemo;
    }

    /**
     * @return the listaRegimenesDisciplinarios
     */
    public List<RegimenDisciplinario> getListaRegimenesDisciplinarios() {
        return listaRegimenesDisciplinarios;
    }

    /**
     * @param listaRegimenesDisciplinarios the listaRegimenesDisciplinarios to set
     */
    public void setListaRegimenesDisciplinarios(final List<RegimenDisciplinario> listaRegimenesDisciplinarios) {
        this.listaRegimenesDisciplinarios = listaRegimenesDisciplinarios;
    }

    /**
     * @return the movimientoReintegro
     */
    public MovimientoReintegro getMovimientoReintegro() {
        return movimientoReintegro;
    }

    /**
     * @param movimientoReintegro the movimientoReintegro to set
     */
    public void setMovimientoReintegro(final MovimientoReintegro movimientoReintegro) {
        this.movimientoReintegro = movimientoReintegro;
    }

    /**
     * @return the listaLicencias
     */
    public List<Licencia> getListaLicencias() {
        return listaLicencias;
    }

    /**
     * @param listaLicencias the listaLicencias to set
     */
    public void setListaLicencias(final List<Licencia> listaLicencias) {
        this.listaLicencias = listaLicencias;
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
     * @return the listaComisionServicio
     */
    public List<ComisionServicio> getListaComisionServicio() {
        return listaComisionServicio;
    }

    /**
     * @param listaComisionServicio the listaComisionServicio to set
     */
    public void setListaComisionServicio(final List<ComisionServicio> listaComisionServicio) {
        this.listaComisionServicio = listaComisionServicio;
    }

    /**
     * @return the movimientoSituacionActual
     */
    public MovimientoSituacionActual getMovimientoSituacionActual() {
        return movimientoSituacionActual;
    }

    /**
     * @param movimientoSituacionActual the movimientoSituacionActual to set
     */
    public void setMovimientoSituacionActual(final MovimientoSituacionActual movimientoSituacionActual) {
        this.movimientoSituacionActual = movimientoSituacionActual;
    }

    /**
     * @return the listaCambioAdministrativo
     */
    public List<CambioAdministrativo> getListaCambioAdministrativo() {
        return listaCambioAdministrativo;
    }

    /**
     * @return the listaTrasladoAdministrativo
     */
    public List<TrasladoAdministrativo> getListaTrasladoAdministrativo() {
        return listaTrasladoAdministrativo;
    }

    /**
     * @param listaCambioAdministrativo the listaCambioAdministrativo to set
     */
    public void setListaCambioAdministrativo(final List<CambioAdministrativo> listaCambioAdministrativo) {
        this.listaCambioAdministrativo = listaCambioAdministrativo;
    }

    /**
     * @param listaTrasladoAdministrativo the listaTrasladoAdministrativo to set
     */
    public void setListaTrasladoAdministrativo(final List<TrasladoAdministrativo> listaTrasladoAdministrativo) {
        this.listaTrasladoAdministrativo = listaTrasladoAdministrativo;
    }

    /**
     * @return the listaTraspaso
     */
    public List<Traspaso> getListaTraspaso() {
        return listaTraspaso;
    }

    /**
     * @param listaTraspaso the listaTraspaso to set
     */
    public void setListaTraspaso(final List<Traspaso> listaTraspaso) {
        this.listaTraspaso = listaTraspaso;
    }

    /**
     * @return the movimientoSituacionPropuesta
     */
    public MovimientoSituacionPropuesta getMovimientoSituacionPropuesta() {
        return movimientoSituacionPropuesta;
    }

    /**
     * @param movimientoSituacionPropuesta the movimientoSituacionPropuesta to set
     */
    public void setMovimientoSituacionPropuesta(final MovimientoSituacionPropuesta movimientoSituacionPropuesta) {
        this.movimientoSituacionPropuesta = movimientoSituacionPropuesta;
    }

    /**
     * @return the accionPersonalExplicacion
     */
    public String getAccionPersonalExplicacion() {
        return accionPersonalExplicacion;
    }

    /**
     * @return the accionPersonalDocumentoPrevio
     */
    public String getAccionPersonalDocumentoPrevio() {
        return accionPersonalDocumentoPrevio;
    }

    /**
     * @return the accionPersonalNumeroDocumento
     */
    public String getAccionPersonalNumeroDocumento() {
        return accionPersonalNumeroDocumento;
    }

    /**
     * @return the accionPersonalFechaDocumento
     */
    public Date getAccionPersonalFechaDocumento() {
        return accionPersonalFechaDocumento;
    }

    /**
     * @param accionPersonalExplicacion the accionPersonalExplicacion to set
     */
    public void setAccionPersonalExplicacion(final String accionPersonalExplicacion) {
        this.accionPersonalExplicacion = accionPersonalExplicacion;
    }

    /**
     * @param accionPersonalDocumentoPrevio the accionPersonalDocumentoPrevio to set
     */
    public void setAccionPersonalDocumentoPrevio(final String accionPersonalDocumentoPrevio) {
        this.accionPersonalDocumentoPrevio = accionPersonalDocumentoPrevio;
    }

    /**
     * @param accionPersonalNumeroDocumento the accionPersonalNumeroDocumento to set
     */
    public void setAccionPersonalNumeroDocumento(final String accionPersonalNumeroDocumento) {
        this.accionPersonalNumeroDocumento = accionPersonalNumeroDocumento;
    }

    /**
     * @param accionPersonalFechaDocumento the accionPersonalFechaDocumento to set
     */
    public void setAccionPersonalFechaDocumento(final Date accionPersonalFechaDocumento) {
        this.accionPersonalFechaDocumento = accionPersonalFechaDocumento;
    }

    /**
     * @return the listaSubrogacion
     */
    public List<Subrogacion> getListaSubrogacion() {
        return listaSubrogacion;
    }

    /**
     * @param listaSubrogacion the listaSubrogacion to set
     */
    public void setListaSubrogacion(final List<Subrogacion> listaSubrogacion) {
        this.listaSubrogacion = listaSubrogacion;
    }

    /**
     * @return the listaEncargo
     */
    public List<Encargo> getListaEncargo() {
        return listaEncargo;
    }

    /**
     * @param listaEncargo the listaEncargo to set
     */
    public void setListaEncargo(final List<Encargo> listaEncargo) {
        this.listaEncargo = listaEncargo;
    }

    /**
     * @return the rigeApartirDeCopia
     */
    public Date getRigeApartirDeCopia() {
        return rigeApartirDeCopia;
    }

    /**
     * @param rigeApartirDeCopia the rigeApartirDeCopia to set
     */
    public void setRigeApartirDeCopia(final Date rigeApartirDeCopia) {
        this.rigeApartirDeCopia = rigeApartirDeCopia;
    }

    @PostLoad
    public void postLoad() {
        this.rigeApartirDeCopia = this.rigeApartirDe;
    }

    /**
     * @return the listaFinalizacion
     */
    public List<Finalizacion> getListaFinalizacion() {
        return listaFinalizacion;
    }

    /**
     * @param listaFinalizacion the listaFinalizacion to set
     */
    public void setListaFinalizacion(final List<Finalizacion> listaFinalizacion) {
        this.listaFinalizacion = listaFinalizacion;
    }

    /**
     * @return the finalizado
     */
    public Boolean getFinalizado() {
        return finalizado;
    }

    /**
     * @param finalizado the finalizado to set
     */
    public void setFinalizado(final Boolean finalizado) {
        this.finalizado = finalizado;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @return the distributivoDetalleId
     */
    public Long getDistributivoDetalleId() {
        return distributivoDetalleId;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @param distributivoDetalleId the distributivoDetalleId to set
     */
    public void setDistributivoDetalleId(final Long distributivoDetalleId) {
        this.distributivoDetalleId = distributivoDetalleId;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(final String genero) {
        this.genero = genero;
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
    public void setTipoPeriodo(final String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * @return the adendumAntecedentes
     */
    public String getAdendumAntecedentes() {
        return adendumAntecedentes;
    }

    /**
     * @param adendumAntecedentes the adendumAntecedentes to set
     */
    public void setAdendumAntecedentes(final String adendumAntecedentes) {
        this.adendumAntecedentes = adendumAntecedentes;
    }

    public String getNombreTipoDocumento() {
        return nombreTipoDocumento;
    }

    public void setNombreTipoDocumento(String nombreTipoDocumento) {
        this.nombreTipoDocumento = nombreTipoDocumento;
    }

    /**
     * @return the escalaOcupacional
     */
    public EscalaOcupacional getEscalaOcupacional() {
        if (escalaOcupacional == null) {
            escalaOcupacional = new EscalaOcupacional();
        }
        return escalaOcupacional;
    }

    /**
     * @param escalaOcupacional the escalaOcupacional to set
     */
    public void setEscalaOcupacional(EscalaOcupacional escalaOcupacional) {
        this.escalaOcupacional = escalaOcupacional;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        if (unidadOrganizacional == null) {
            unidadOrganizacional = new UnidadOrganizacional();
        }
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the unidadPresupuestaria
     */
    public UnidadPresupuestaria getUnidadPresupuestaria() {
        if (unidadPresupuestaria == null) {
            unidadPresupuestaria = new UnidadPresupuestaria();
        }
        return unidadPresupuestaria;
    }

    /**
     * @param unidadPresupuestaria the unidadPresupuestaria to set
     */
    public void setUnidadPresupuestaria(UnidadPresupuestaria unidadPresupuestaria) {
        this.unidadPresupuestaria = unidadPresupuestaria;
    }

    /**
     * @return the existeDistributivo
     */
    public Boolean getExisteDistributivo() {
        return existeDistributivo;
    }

    /**
     * @param existeDistributivo the existeDistributivo to set
     */
    public void setExisteDistributivo(Boolean existeDistributivo) {
        this.existeDistributivo = existeDistributivo;
    }

    /**
     * @return the certificacionPresupuestaria
     */
    public String getCertificacionPresupuestaria() {
        return certificacionPresupuestaria;
    }

    /**
     * @param certificacionPresupuestaria the certificacionPresupuestaria to set
     */
    public void setCertificacionPresupuestaria(String certificacionPresupuestaria) {
        this.certificacionPresupuestaria = certificacionPresupuestaria;
    }

}
