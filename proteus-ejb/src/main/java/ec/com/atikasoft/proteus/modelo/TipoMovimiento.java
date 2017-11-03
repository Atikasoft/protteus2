package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "tipos_movimientos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_NOMBRE,
            query = "SELECT t FROM TipoMovimiento t where t.nombre like ?1 "
            + " ORDER BY t.clase.grupo.nombre,t.clase.nombre,t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_ACTIVOS_POR_CLASE,
            query = "SELECT t FROM TipoMovimiento t where t.clase.id=?1 and t.vigente=true ORDER BY t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_NEMONICO,
            query = "SELECT t FROM TipoMovimiento t where t.nemonico=?1"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_ACTIVOS,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true "
            + " ORDER BY t.clase.grupo.nombre,t.clase.nombre,t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_ACTIVOS_ORDEN_NOMBRE,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true "
            + " ORDER BY t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_TIPO_GESTION_DESCONCENTRADO,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true AND t.tipoGestionDesconcentrado=true "
            + " AND t.claseId=?1"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_TIPO_GESTION_CENTRALIZADO,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true AND t.tipoGestionDesconcentrado=false "
            + " AND t.claseId=?1"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_TODOS_VIGENTE,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true order by t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_TODOS_VIGENTE_ORDENADO,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true order by t.clase.grupo.nombre,t.clase.nombre,t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_GRUPO_CON_SOLICITUD,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true and t.clase.grupo.nemonico=?1"
            + " and t.conSolicitud=true order by t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_GRUPO_CON_SOLICITUD_MODALIDAD_LABORAL,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true AND t.clase.grupo.nemonico=?1 AND "
            + "t.modalidadLaboralCore=?2 AND t.conSolicitud=true ORDER BY t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_ACTIVOS_POR_CLASE_SIN_PADRE,
            query = "SELECT t FROM TipoMovimiento t where t.clase.id=?1 and t.vigente=true and t.id<>?2 ORDER BY t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_GRUPO,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true and t.clase.grupo.nemonico=?1 ORDER BY t.nombre"),
    @NamedQuery(name = TipoMovimiento.BUSCAR_POR_ID_GRUPO,
            query = "SELECT t FROM TipoMovimiento t where t.vigente=true and t.clase.grupo.id=?1 ORDER BY t.nombre")
})
public class TipoMovimiento extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_ACTIVOS_POR_CLASE = "TipoMovimiento.buscarActivosPorClase";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NOMBRE = "TipoMovimiento.buscarPorNombre";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NEMONICO = "TipoMovimiento.buscarPorNemonico";

    /**
     * Nombre d ela consulta que reucpera tipos de movimientos activos.
     */
    public static final String BUSCAR_ACTIVOS = "TipoMovimiento.buscarActivos";

    /**
     * Nombre de la consulta que recupera los tipos d emovimientos por grupo y modalidad con solicitud.
     */
    public static final String BUSCAR_POR_GRUPO_CON_SOLICITUD_MODALIDAD_LABORAL
            = "TipoMovimiento.buscarPorGrupoConSolicitudModalidLaboral";

    /**
     * Nombre consulta.
     */
    public static final String BUSCAR_ACTIVOS_ORDEN_NOMBRE = "TipoMovimiento.buscarActivosOrdenNombre";

    /**
     * Nombre de la consulta de buscar tipos movimientos vigentes.
     */
    public static final String BUSCAR_TODOS_VIGENTE = "TipoMovimiento.BuscarTodosVigente";

    /**
     * Nombre de la consulta de buscar tipos movimientos vigentes ordenados grupo, clase,tipo movimiento.
     */
    public static final String BUSCAR_TODOS_VIGENTE_ORDENADO = "TipoMovimiento.BuscarTodosVigenteOrdenado";

    /**
     * Nombre de la consulta de buscar tipos movimientos vigentes para solicitud.
     */
    public static final String BUSCAR_POR_GRUPO_CON_SOLICITUD = "TipoMovimiento.BuscarPorGruposConSolicitud";

    /**
     * Nombre de la consulta para buscar tipos de movimientos de acuerdo al tipo de gestion.
     */
    public static final String BUSCAR_POR_TIPO_GESTION_DESCONCENTRADO
            = "TipoMovimiento.buscarPorTipoGestionDesconcentrado";

    /**
     * Nombre de la consulta para buscar tipos de movimientos de acuerdo al tipo de gestion.
     */
    public static final String BUSCAR_POR_TIPO_GESTION_CENTRALIZADO
            = "TipoMovimiento.buscarPorTipoGestionCentralizado";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_ACTIVOS_POR_CLASE_SIN_PADRE = "TipoMovimiento.buscarActivosPorClaseNotInPadre";

    /**
     * Nombre de la consulta que permite buscar tipos de movimientos por grupo.
     */
    public static final String BUSCAR_POR_GRUPO = "TipoMovimiento.buscarPorGrupo";

    /**
     * listar tipos de movimientos recibiendo como parametro el id del grupo
     */
    public static final String BUSCAR_POR_ID_GRUPO = "TipoMovimiento.buscarPorIdGrupo";

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nemonico.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nemonico")
    private String nemonico;

    /**
     * Nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Descripcion.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Sustento Legal.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "sustento_legal")
    private String sustentoLegal;

    /**
     * Id de modalidad laboral traida del siith core.
     */
    @Column(name = "modalidad_laboral_core")
    private Long modalidadLaboralCore;

    /**
     * Id de nivel ocupacional traida del siith core.
     */
    @Column(name = "nivel_ocupacional_core")
    private Long nivelOcupacionalCore;

    /**
     * Control fecha presenta documento.
     */
    @Column(name = "control_fecha_presenta_documento")
    private String controlFechaPresentaDocumento;

    /**
     * Tipo de Gestion Desconcentrado por defecto.
     */
    @Column(name = "tipo_gestion_desconcentrado")
    private Boolean tipoGestionDesconcentrado;

    /**
     * Area Puesto.
     */
    @Column(name = "area_puesto")
    private Boolean areaPuesto;

    /**
     * fecha Inicio.
     */
    @Column(name = "fecha_inicio")
    private String fechaInicio;

    /**
     * fecha fin.
     */
    @Column(name = "fecha_fin")
    private String fechaFin;

    /**
     * Regimen laboral.
     */
    @Column(name = "regimen_laboral")
    private String regimenLaboral;

    /**
     * Partida Individual.
     */
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * Escala Ocupacional.
     */
    @Column(name = "escala_ocupacional")
    private String escalaOcupacional;

    /**
     * RMU.
     */
    @Column(name = "rmu")
    private String rmu;

    /**
     * Puesto Insticucional.
     */
    @Column(name = "puesto_institucional")
    private String puestoInstitucional;

    /**
     * RMU Sobrevalorado.
     */
    @Column(name = "rmu_sobrevalorado")
    private String rmuSobrevalorado;

    /**
     * Sueldo basico.
     */
    @Column(name = "sueldo_basico")
    private String sueldoBasico;

    /**
     * Puesto Adicional.
     */
    @Column(name = "puesto_adicional")
    private String puestoAdicional;

    /**
     * Unidad Organizacional. Reemplazo unidad_organizacional por unidad_administrativa en el mapeo.
     */
    @Column(name = "unidad_administrativa")
    private String unidadOrganizacional;

    /**
     * Modalidad laboral.
     */
    @Column(name = "modalidad_laboral")
    private String modalidadLaboral;

    /**
     * Ubicacion Geografica.
     */
    @Column(name = "ubicacion_geografica")
    private String ubicacionGeografica;

    /**
     * Area Rige a partir de.
     */
    @Column(name = "area_rige_a_partir_de")
    private Boolean areaRigeAPartirDe;

    /**
     * Fecha de rige a partir de.
     */
    @Column(name = "fecha_rige_a_partir_de")
    private String fechaRigeAPartirDe;

    /**
     * Fecha de rige a partir de.
     */
    @Column(name = "fecha_hasta")
    private String fechaHasta;

    /**
     * Ingresa con Horas y Minutos.
     */
    @Column(name = "ingresa_hora_minuto")
    private String ingresaHoraMinuto;

    /**
     * Codigo del puesto.
     */
    @Column(name = "codigo_puesto")
    private String codigoPuesto;

    /**
     * Area Periodo Fijo.
     */
    @Column(name = "area_periodo_fijo")
    private Boolean areaPeriodoFijo;

    /**
     * Tipo designacion.
     */
    @Column(name = "tipo_designacion")
    private String tipoDesignacion;

    /**
     * Area Informacion Salida.
     */
    @Column(name = "area_informacion_salida")
    private Boolean areaInformacionSalida;

    /**
     * Area Informacion de liquidacion de Salida.
     */
    @Column(name = "area_con_salida_liquidacion")
    private Boolean areaInformacionLiquidacionSalida;

    /**
     * Fecha Efectiva Salida.
     */
    @Column(name = "fecha_efectiva_salida")
    private String fechaEfectivaSalida;

    /**
     * Indica si el movimiento de salida tiene o no liquidación asociada.
     */
    @Column(name = "con_liquidacion")
    private Boolean conLiquidacion;

    /**
     * Fecha Presenta Renuncia Voluntaria.
     */
    @Column(name = "fecha_presenta_renuncia_voluntaria")
    private String fechaPresentaRenunciaVoluntaria;

    /**
     * Fecha Presenta Renuncia.
     */
    @Column(name = "fecha_presenta_renuncia")
    private String fechaPresentaRenuncia;

    /**
     * Fecha aceptacion renuncia.
     */
    @Column(name = "fecha_aceptacion_renuncia")
    private String fechaAceptacionRenuncia;

    /**
     * Area Servidor.
     */
    @Column(name = "area_servidor")
    private Boolean areaServidor;

    /**
     * Tipo de documento.
     */
    @Column(name = "tipo_documento")
    private String tipoDocumento;

    /**
     * Numero de documento.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Apellidos y Nombres.
     */
    @Column(name = "apellido_nombre")
    private String apellidoNombre;

    /**
     * Fecha Ingreso institucion.
     */
    @Column(name = "fecha_ingreso_institucion")
    private String fechaIngresoInstitucion;

    /**
     * Fecha ingreso sector publico.
     */
    @Column(name = "fecha_ingreso_sector_publico")
    private String fechaIngresoSectorPublico;

    /**
     * Area Fallecimiento.
     */
    @Column(name = "area_fallecimiento")
    private Boolean areaFallecimiento;

    /**
     * Fecha Fallecimiento.
     */
    @Column(name = "fecha_fallecimiento")
    private String fechaFallecimiento;

    /**
     * Caso Fallecimiento.
     */
    @Column(name = "caso_fallecimiento")
    private String casoFallecimiento;

    /**
     * Area Contratos CT.
     */
    @Column(name = "area_contrato_ct")
    private Boolean areaContratoCT;

    /**
     * Tiempo Jornada diaria.
     */
    @Column(name = "tiempo_jornada_diaria")
    private String tiempoJornadaDiaria;

    /**
     * Tipo de temporada.
     */
    @Column(name = "tipo_temporada")
    private String tipoTemporada;

    /**
     * Area Contratos LOSEP.
     */
    @Column(name = "area_contrato_losep")
    private Boolean areaContratoLosep;

    /**
     * Antecedentes del contrato.
     */
    @Column(name = "antecedentes_contrato")
    private String antecedentesContrato;

    /**
     * Activiades del contrato.
     */
    @Column(name = "actividades_contrato")
    private String actividadesContrato;

    /**
     * Siglas de titulo Academico.
     */
    @Column(name = "siglas_titulo_academico")
    private String siglasTituloAcademico;

    /**
     * Area discapacidad.
     */
    @Column(name = "area_discapacidad")
    private Boolean areaDiscapacidad;

    /**
     * Discapacidad.
     */
    @Column(name = "discapacidad")
    private String discapacidad;

    /**
     * Clase discapacidad.
     */
    @Column(name = "clase_discapacidad")
    private String claseDiscapacidad;

    /**
     * Tipo discapacidad.
     */
    @Column(name = "tipo_discapacidad")
    private String tipoDiscapacidad;

    /**
     * Porcentaje discapacidad.
     */
    @Column(name = "porcentaje_discapacidad")
    private String porcentajeDiscapacidad;

    /**
     * Numero conadis.
     */
    @Column(name = "numero_conadis")
    private String numeroConadis;

    /**
     * Area formacion academica.
     */
    @Column(name = "area_formacion_academica")
    private Boolean areaFormacionAcademica;

    /**
     * Nivel instruccion.
     */
    @Column(name = "nivel_instruccion")
    private String nivelInstruccion;

    /**
     * Tipo periodo.
     */
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Pais Formacion academica.
     */
    @Column(name = "pais_formacion_academica")
    private String paisFormacionAcademica;

    /**
     * Anos de estudio.
     */
    @Column(name = "anios_estudio")
    private String aniosEstudio;

    /**
     * Area Terminacion del contrato.
     */
    @Column(name = "area_terminacion_contrato")
    private Boolean areaTerminacionContrato;

    /**
     * Numero de contrato anterior.
     */
    @Column(name = "numero_contrato_anterior")
    private String numeroContratoAnterior;

    /**
     * Fecha de inicio del contrato anterior.
     */
    @Column(name = "fecha_inicio_contrato_anterior")
    private String fechaInicioContratoAnterior;

    /**
     * Area Estado Puesto.
     */
    @Column(name = "area_estado_puesto")
    private Boolean areaEstadoPuesto;

    /**
     * Estado puesto inicial core.
     */
    @Column(name = "estado_puesto_inicial_core")
    private Long estadoPuestoInicialCore;

    /**
     * Estado puesto final core.
     */
    @Column(name = "estado_puesto_final_core")
    private Long estadoPuestoFinalCore;

    /**
     * Area Estado personal.
     */
    @Column(name = "area_estado_personal")
    private Boolean areaEstadoPersonal;

    /**
     * Estado personal inicial core.
     */
    @Column(name = "estado_personal_inicial_core")
    private Long estadoPersonalInicialCore;

    /**
     * Estado personal final core.
     */
    @Column(name = "estado_personal_final_core")
    private Long estadoPersonalFinalCore;

    /**
     * Area Estado puesto propuesto.
     */
    @Column(name = "area_estado_puesto_propuesto")
    private Boolean areaEstadoPuestoPropuesto;

    /**
     * Estado puesto inicial propuesta core.
     */
    @Column(name = "estado_puesto_inicial_propuesta_core")
    private Long estadoPuestoInicialPropuestaCore;

    /**
     * Estado puesto final propuesta core.
     */
    @Column(name = "estado_puesto_final_propuesta_core")
    private Long estadoPuestoFinalPropuestaCore;

    /**
     * Entidad Documento Habilitante.
     */
    @JoinColumn(name = "documentos_habilitantes_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentoHabilitante documentoHabilitante;

    /**
     * Columna para Id de documento habilitante.
     */
    @Column(name = "documentos_habilitantes_id")
    private Long documentoHabilitanteId;

    /**
     * Entidad Clase.
     */
    @JoinColumn(name = "clases_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Clase clase;

    /**
     * Columna para el id de la clase seleccionada.
     */
    @Column(name = "clases_id")
    private Long claseId;

    /**
     * tipo de flujo.
     */
    @NotNull
    @Column(name = "tipo_flujo")
    private String tipoFlujo;

    /**
     * Tiempo maximo.
     */
    @Column(name = "tiempo_maximo")
    private Integer tiempoMaximo;

    /**
     * Tiempo maximo.
     */
    @Column(name = "periodo_tiempo_maximo")
    private String periodoTiempoMaximo;

    /**
     * Valor de multa.
     */
    @Column(name = "valor_multa")
    private String valorMulta;

    /**
     * Lista de alertas.
     */
    @OneToMany(mappedBy = "tipoMovimiento")
    private List<TipoMovimientoAlerta> listaAlerta;

    /**
     * Lista de reglas.
     */
    @OneToMany(mappedBy = "tipoMovimiento")
    private List<TipoMovimientoRegla> listaReglas;

    /**
     * Lista de requisitos.
     */
    @OneToMany(mappedBy = "tipoMovimiento")
    private List<TipoMovimientoRequisito> listaRequisitos;

    /**
     * Modalidad Laboral Core Nombre.
     */
    @Column(name = "modalidad_laboral_core_nombre")
    private String modalidadLaboralCoreNombre;

    /**
     * Nivel Ocupacional Core Nombre.
     */
    @Column(name = "nivel_ocupacional_core_nombre")
    private String nivelOcupacionalCoreNombre;

    /**
     * Estado puesto inicial Core Nombre.
     */
    @Column(name = "estado_puesto_inicial_core_nombre")
    private String estadoPuestoInicialCoreNombre;

    /**
     * Estado puesto final Core Nombre.
     */
    @Column(name = "estado_puesto_final_core_nombre")
    private String estadoPuestoFinalCoreNombre;

    /**
     * Estado puesto final Core Nombre propuesta.
     */
    @Column(name = "estado_puesto_inicial_propuesta_core_nombre")
    private String estadoPuestoInicialPropuestaCoreNombre;

    /**
     * Estado personal inicial Core Nombre propuesta.
     */
    @Column(name = "estado_puesto_final_propuesta_core_nombre")
    private String estadoPuestoFinalPropuestaCoreNombre;

    /**
     * Estado personal inicial Core Nombre.
     */
    @Column(name = "estado_personal_inicial_core_nombre")
    private String estadoPersonalInicialCoreNombre;

    /**
     * Estado personal final Core Nombre.
     */
    @Column(name = "estado_personal_final_core_nombre")
    private String estadoPersonalFinalCoreNombre;

    /**
     * Area Accion de personal.
     */
    @Column(name = "area_accion_personal")
    private Boolean areaAccionPersonal;

    /**
     * Situacion Actual.
     */
    @Column(name = "situacion_actual")
    private Boolean situacionActual;

    /**
     * Situacion Propuesta.
     */
    @Column(name = "situacion_propuesta")
    private Boolean situacionPropuesta;

    /**
     * Reemplazo.
     */
    @Column(name = "reemplazo")
    private Boolean reemplazo;

    /**
     * Posesion Cargo.
     */
    @Column(name = "posesion_cargo")
    private Boolean posesionCargo;

    /**
     * Explicacion de la accion de personal.
     */
    @Column(name = "ap_explicacion")
    private String apExplicacion;

    /**
     * Documento previo de la accion de personal.
     */
    @Column(name = "ap_documento_previo")
    private String apDocumentoPrevio;

    /**
     * Numero de documento de la accion de personal.
     */
    @Column(name = "ap_numero_documento")
    private String apNumeroDocumento;

    /**
     * Fecha de documento de la accion de personal.
     */
    @Column(name = "ap_fecha_documento")
    private String apFechaDocumento;

    /**
     * Numero Registro Senescyt.
     */
    @Column(name = "numero_registro_senescyt")
    private String numeroRegistroSenescyt;

    /**
     * Renovacion.
     */
    @Column(name = "renovacion")
    private String renovacion;

    /**
     * Explicacion de la accion de personal.
     */
    @Column(name = "explicacion")
    private String explicacion;

    /**
     * Area Memorando.
     */
    @Column(name = "area_memorando")
    private Boolean areaMemorando;

    /**
     * Numero de Memorando.
     */
    @Column(name = "numero_memorando")
    private String numeroMemorando;

    /**
     * Asunto de Memorando.
     */
    @Column(name = "asunto_memorando")
    private String asuntoMemorando;

    /**
     * Contenido de Memorando.
     */
    @Column(name = "contenido_memorando")
    private String contenidoMemorando;

    /**
     * Area Regimen Disciplinario.
     */
    @Column(name = "area_regimen_disciplinario")
    private Boolean areaRegimenDisciplinario;

    /**
     * Falta.
     */
    @Column(name = "rd_falta")
    private String rdfalta;

    /**
     * Descripcion de regimen disciplinario.
     */
    @Column(name = "rd_descripcion")
    private String rdDescripcion;

    /**
     * Area Licencias Maternidad/Paternidad.
     */
    @Column(name = "area_licencias_maternidad_paternidad")
    private Boolean areaLicenciasMaternidadPaternidad;

    /**
     * Tipo de Nacimiento.
     */
    @Column(name = "tipo_nacimiento")
    private String tipoNacimiento;

    /**
     * Dias adicionales para la madre por nacimiento multiple.
     */
    @Column(name = "dias_adicionales_madre_nacimiento_multiple")
    private Integer diasAdicionalesMadreNacimientoMultiple;

    /**
     * Dias adicionales para el padre por nacimiento multiple o cesarea.
     */
    @Column(name = "dias_adicionales_padre_nacimiento_multiple")
    private Integer diasAdicionalesPadreNacimientoMultiple;

    /**
     * Area configuracion licencias permisos.
     */
    @Column(name = "area_configuracion_licencias_permisos")
    private Boolean areaConfiguracionLicenciasPermisos;

    /**
     * Horario.
     */
    @Column(name = "horario")
    private String horario;

    /**
     * Total de Horas semana.
     */
    @Column(name = "total_horas_semana")
    private Integer totalHorasSemana;

    /**
     * Periodo para control.
     */
    @Column(name = "periodo_control")
    private String periodoControl;

    /**
     * Valor periodo control.
     */
    @Column(name = "valor_periodo_control")
    private Integer valorPeriodoControl;

    /**
     * Horario a devengar.
     */
    @Column(name = "horario_devengar")
    private String horarioDevengar;

    /**
     * Area Licencias.
     */
    @Column(name = "area_licencias")
    private Boolean areaLicencias;

    /**
     * Fecha Ocurrio hecho.
     */
    @Column(name = "fecha_ocurrio_hecho")
    private String fechaOcurrioHecho;

    /**
     * Fecha Presenta documento.
     */
    @Column(name = "fecha_presenta_documento")
    private String fechaPresentaDocumento;

    /**
     * Area tiempo por devengar.
     */
    @Column(name = "area_tiempo_por_devengar")
    private Boolean areaTiempoPorDevengar;

    /**
     * Devengar.
     */
    @Column(name = "devengar")
    private String devengar;

    /**
     * Periodo por devengar.
     */
    @Column(name = "periodo_devengar")
    private String periodoDevengar;

    /**
     * Valor por devengar.
     */
    @Column(name = "valor_devengar")
    private String valorDevengar;

    /**
     * Considere calculo.
     */
    @Column(name = "considere_calculo")
    @Size(max = 2000)
    private String considereCalculo;

    /**
     * observacion.
     */
    @Column(name = "observacion")
    private String observacionDevengar;

    /**
     * Area Puesto.
     */
    @Column(name = "con_solicitud")
    private Boolean conSolicitud;

    /**
     * Area Representacion de una asociacion Laboral.
     */
    @Column(name = "area_representacion_asociacion_laboral")
    private Boolean areaRepresentacionAsociacionLaboral;

    /**
     * Numero de Horas Mensuales.
     */
    @Column(name = "numero_horas_mensuales")
    private String numeroHorasMensuales;

    /**
     * Maximo numero de horas mensuales.
     */
    @Column(name = "maximo_numero_horas_mensuales")
    @Max(value = 200)
    private Integer maximoNumeroHorasMensuales;

    /**
     * Area Permiso Matricula Hijos.
     */
    @Column(name = "area_permiso_matricula_hijos")
    private Boolean areaPermisoMatriculaHijos;

    /**
     * Nombre del Hijo(a).
     */
    @Column(name = "nombre_hijo")
    private String nombreHijo;

    /**
     * Nivel de Estudio.
     */
    @Column(name = "nivel_estudio")
    private String nivelEstudio;

    /**
     * Area Comision de Servicio - Institucion Requiriente.
     */
    @Column(name = "area_comision_servicio_institucion_requiriente")
    private Boolean areaComisionServicioInstitucionRequiriente;

    /**
     * Institucion.
     */
    @Column(name = "institucion")
    private String institucion;

    /**
     * Unidad Administrativa Institucion.
     */
    @Column(name = "unidad_administrativa_institucion")
    private String unidadAdministrativaInstitucion;

    /**
     * Direccion de Unidad Administrativa Institucion.
     */
    @Column(name = "unidad_administrativa_direccion_institucion")
    private String unidadAdministrativaDireccionInstitucion;

    /**
     * Unidad Administrativa Institucion por cambio administrativo.
     */
    @Column(name = "unidad_administrativa_institucion_cambio")
    private String unidadAdministrativaInstitucionCambio;

    /**
     * Direccion Unidad Administrativa Institucion por cambio administrativo.
     */
    @Column(name = "unidad_administrativa_direccion_institucion_cambio")
    private String unidadAdministrativaDireccionInstitucionCambio;

    /**
     * Partida individual.
     */
    @Column(name = "ta_partida_individual")
    private String taPartidaIndividual;

    /**
     * Unidad Administrativa de traspaso
     */
    @Column(name = "t_unidad_administrativa")
    private String tUnidadAdministrativa;

    /**
     * Direccion Unidad Administrativa del traspaso.
     */
    @Column(name = "t_unidad_administrativa_direccion")
    private String tUnidadAdministrativaDireccion;

    /**
     * Denominacion del puesto del traspaso.
     */
    @Column(name = "t_denominacion_puesto")
    private String tDenominacionPuesto;

    /**
     * Area Puesto Destino.
     */
    @Column(name = "area_puesto_destino")
    private Boolean areaPuestoDestino;

    /**
     * Partida individual de puesto destino.
     */
    @Column(name = "i_partida_individual")
    private String partidaIndividualPuestDest;

    /**
     * Area de Informacion del Movimiento para reintegro.
     */
    @Column(name = "area_informacion_movimiento_reintegro")
    private Boolean areaInformacionMovimientoReintegro;

    /**
     *
     */
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;

    /**
     *
     */
    @Column(name = "renovacion_comision_servicio")
    private Boolean renovacionComisionServicio;

    /**
     * Area de finalizacion.
     */
    @Column(name = "area_finalizacion")
    private Boolean areaFinalizacion;

    /**
     * tipo de movimiento inicial del area de finalizacion.
     */
    @Column(name = "af_tipo_movimiento_inicial")
    private String afTipoMovimientoInicial;

    /**
     * Relacion de con tipo e movimiento por finalizacion.
     */
    @JoinColumn(name = "tipo_movimiento_finalizacion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimientoFinalizacion;

    /**
     * Identificador del tipo de movimiento de finalizacion.
     */
    @Column(name = "tipo_movimiento_finalizacion_id")
    private Long tipoMovimientoFinalizacionId;

    /**
     * Indica si la persona ingresara por reclutamiento o no..
     */
    @Column(name = "ingreso_x_reclutamiento")
    private Boolean ingresoPorReclutamiento;

    /**
     * Tipo de rotacion. <C>ambio administrativo <T>raslado Administrativo T<R>aspaso misma institucion. <E>ncargo
     * <S>ubrogacion
     */
    @Column(name = "tipo_rotacion")
    private String tipoRotacion;

    /**
     * Area adendum.
     */
    @Column(name = "area_adendum")
    private Boolean areaAdendum;

    /**
     * Antecedentes del adendum.
     */
    @Column(name = "antecedentes")
    private String antecedentes;

    @Column(name = "publicado")
    private Boolean publicado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_publicacion")
    private Date fechaPublicacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_despublicacion")
    private Date fechaDespublicacion;

    @Column(name = "usuario_publicacion")
    private String usuarioPublicacion;

    @Column(name = "usuario_despublicacion")
    private String usuarioDespublicacion;

    /**
     *
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     *
     */
    @Column(name = "cargar_documento_habilitante_obligatorio")
    private Boolean cargarDocumentoHabilitanteObligatorio;

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimiento() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Id
     */
    public TipoMovimiento(final Long id) {
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
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * @param nemonico the nemonico to set
     */
    public void setNemonico(final String nemonico) {
        this.nemonico = nemonico;
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
    public void setNombre(final String nombre) {
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
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the sustentoLegal
     */
    public String getSustentoLegal() {
        return sustentoLegal;
    }

    /**
     * @param sustentoLegal the sustentoLegal to set
     */
    public void setSustentoLegal(final String sustentoLegal) {
        this.sustentoLegal = sustentoLegal;
    }

    /**
     * @return the modalidadLaboralCore
     */
    public Long getModalidadLaboralCore() {
        return modalidadLaboralCore;
    }

    /**
     * @param modalidadLaboralCore the modalidadLaboralCore to set
     */
    public void setModalidadLaboralCore(final Long modalidadLaboralCore) {
        this.modalidadLaboralCore = modalidadLaboralCore;
    }

    /**
     * @return the nivelOcupacionalCore
     */
    public Long getNivelOcupacionalCore() {
        return nivelOcupacionalCore;
    }

    /**
     * @param nivelOcupacionalCore the nivelOcupacionalCore to set
     */
    public void setNivelOcupacionalCore(final Long nivelOcupacionalCore) {
        this.nivelOcupacionalCore = nivelOcupacionalCore;
    }

    /**
     * @return the tipoGestionDesconcentrado
     */
    public Boolean getTipoGestionDesconcentrado() {
        return tipoGestionDesconcentrado;
    }

    /**
     * @param tipoGestionDesconcentrado the tipoGestionDesconcentrado to set
     */
    public void setTipoGestionDesconcentrado(final Boolean tipoGestionDesconcentrado) {
        this.tipoGestionDesconcentrado = tipoGestionDesconcentrado;
    }

    /**
     * @return the areaPuesto
     */
    public Boolean getAreaPuesto() {
        return areaPuesto;
    }

    /**
     * @param areaPuesto the areaPuesto to set
     */
    public void setAreaPuesto(final Boolean areaPuesto) {
        this.areaPuesto = areaPuesto;
    }

    /**
     * @return the fechaInicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the regimenLaboral
     */
    public String getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(final String regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    /**
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(final String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @return the escalaOcupacional
     */
    public String getEscalaOcupacional() {
        return escalaOcupacional;
    }

    /**
     * @param escalaOcupacional the escalaOcupacional to set
     */
    public void setEscalaOcupacional(final String escalaOcupacional) {
        this.escalaOcupacional = escalaOcupacional;
    }

    /**
     * @return the rmu
     */
    public String getRmu() {
        return rmu;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final String rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the puestoInstitucional
     */
    public String getPuestoInstitucional() {
        return puestoInstitucional;
    }

    /**
     * @param puestoInstitucional the puestoInstitucional to set
     */
    public void setPuestoInstitucional(final String puestoInstitucional) {
        this.puestoInstitucional = puestoInstitucional;
    }

    /**
     * @return the rmuSobrevalorado
     */
    public String getRmuSobrevalorado() {
        return rmuSobrevalorado;
    }

    /**
     * @param rmuSobrevalorado the rmuSobrevalorado to set
     */
    public void setRmuSobrevalorado(final String rmuSobrevalorado) {
        this.rmuSobrevalorado = rmuSobrevalorado;
    }

    /**
     * @return the puestoAdicional
     */
    public String getPuestoAdicional() {
        return puestoAdicional;
    }

    /**
     * @param puestoAdicional the puestoAdicional to set
     */
    public void setPuestoAdicional(final String puestoAdicional) {
        this.puestoAdicional = puestoAdicional;
    }

    /**
     * @return the modalidadLaboral
     */
    public String getModalidadLaboral() {
        return modalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setModalidadLaboral(final String modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the ubicacionGeografica
     */
    public String getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(final String ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    /**
     * @return the areaRigeAPartirDe
     */
    public Boolean getAreaRigeAPartirDe() {
        return areaRigeAPartirDe;
    }

    /**
     * @param areaRigeAPartirDe the areaRigeAPartirDe to set
     */
    public void setAreaRigeAPartirDe(final Boolean areaRigeAPartirDe) {
        this.areaRigeAPartirDe = areaRigeAPartirDe;
    }

    /**
     * @return the areaPeriodoFijo
     */
    public Boolean getAreaPeriodoFijo() {
        return areaPeriodoFijo;
    }

    /**
     * @param areaPeriodoFijo the areaPeriodoFijo to set
     */
    public void setAreaPeriodoFijo(final Boolean areaPeriodoFijo) {
        this.areaPeriodoFijo = areaPeriodoFijo;
    }

    /**
     * @return the tipoDesignacion
     */
    public String getTipoDesignacion() {
        return tipoDesignacion;
    }

    /**
     * @param tipoDesignacion the tipoDesignacion to set
     */
    public void setTipoDesignacion(final String tipoDesignacion) {
        this.tipoDesignacion = tipoDesignacion;
    }

    /**
     * @return the areaInformacionSalida
     */
    public Boolean getAreaInformacionSalida() {
        return areaInformacionSalida;
    }

    /**
     * @param areaInformacionSalida the areaInformacionSalida to set
     */
    public void setAreaInformacionSalida(final Boolean areaInformacionSalida) {
        this.areaInformacionSalida = areaInformacionSalida;
    }

    /**
     * @return the fechaEfectivaSalida
     */
    public String getFechaEfectivaSalida() {
        return fechaEfectivaSalida;
    }

    /**
     * @param fechaEfectivaSalida the fechaEfectivaSalida to set
     */
    public void setFechaEfectivaSalida(final String fechaEfectivaSalida) {
        this.fechaEfectivaSalida = fechaEfectivaSalida;
    }

    /**
     * @return the fechaPresentaRenunciaVoluntaria
     */
    public String getFechaPresentaRenunciaVoluntaria() {
        return fechaPresentaRenunciaVoluntaria;
    }

    /**
     * @param fechaPresentaRenunciaVoluntaria the fechaPresentaRenunciaVoluntaria to set
     */
    public void setFechaPresentaRenunciaVoluntaria(final String fechaPresentaRenunciaVoluntaria) {
        this.fechaPresentaRenunciaVoluntaria = fechaPresentaRenunciaVoluntaria;
    }

    /**
     * @return the fechaPresentaRenuncia
     */
    public String getFechaPresentaRenuncia() {
        return fechaPresentaRenuncia;
    }

    /**
     * @param fechaPresentaRenuncia the fechaPresentaRenuncia to set
     */
    public void setFechaPresentaRenuncia(final String fechaPresentaRenuncia) {
        this.fechaPresentaRenuncia = fechaPresentaRenuncia;
    }

    /**
     * @return the areaServidor
     */
    public Boolean getAreaServidor() {
        return areaServidor;
    }

    /**
     * @param areaServidor the areaServidor to set
     */
    public void setAreaServidor(final Boolean areaServidor) {
        this.areaServidor = areaServidor;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the apellidoNombre
     */
    public String getApellidoNombre() {
        return apellidoNombre;
    }

    /**
     * @param apellidoNombre the apellidoNombre to set
     */
    public void setApellidoNombre(final String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    /**
     * @return the fechaIngresoInstitucion
     */
    public String getFechaIngresoInstitucion() {
        return fechaIngresoInstitucion;
    }

    /**
     * @param fechaIngresoInstitucion the fechaIngresoInstitucion to set
     */
    public void setFechaIngresoInstitucion(final String fechaIngresoInstitucion) {
        this.fechaIngresoInstitucion = fechaIngresoInstitucion;
    }

    /**
     * @return the fechaIngresoSectorPublico
     */
    public String getFechaIngresoSectorPublico() {
        return fechaIngresoSectorPublico;
    }

    /**
     * @param fechaIngresoSectorPublico the fechaIngresoSectorPublico to set
     */
    public void setFechaIngresoSectorPublico(final String fechaIngresoSectorPublico) {
        this.fechaIngresoSectorPublico = fechaIngresoSectorPublico;
    }

    /**
     * @return the areaFallecimiento
     */
    public Boolean getAreaFallecimiento() {
        return areaFallecimiento;
    }

    /**
     * @param areaFallecimiento the areaFallecimiento to set
     */
    public void setAreaFallecimiento(final Boolean areaFallecimiento) {
        this.areaFallecimiento = areaFallecimiento;
    }

    /**
     * @return the fechaFallecimiento
     */
    public String getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    /**
     * @param fechaFallecimiento the fechaFallecimiento to set
     */
    public void setFechaFallecimiento(final String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    /**
     * @return the casoFallecimiento
     */
    public String getCasoFallecimiento() {
        return casoFallecimiento;
    }

    /**
     * @param casoFallecimiento the casoFallecimiento to set
     */
    public void setCasoFallecimiento(final String casoFallecimiento) {
        this.casoFallecimiento = casoFallecimiento;
    }

    /**
     * @return the tiempoJornadaDiaria
     */
    public String getTiempoJornadaDiaria() {
        return tiempoJornadaDiaria;
    }

    /**
     * @param tiempoJornadaDiaria the tiempoJornadaDiaria to set
     */
    public void setTiempoJornadaDiaria(final String tiempoJornadaDiaria) {
        this.tiempoJornadaDiaria = tiempoJornadaDiaria;
    }

    /**
     * @return the tipoTemporada
     */
    public String getTipoTemporada() {
        return tipoTemporada;
    }

    /**
     * @param tipoTemporada the tipoTemporada to set
     */
    public void setTipoTemporada(final String tipoTemporada) {
        this.tipoTemporada = tipoTemporada;
    }

    /**
     * @return the areaDiscapacidad
     */
    public Boolean getAreaDiscapacidad() {
        return areaDiscapacidad;
    }

    /**
     * @param areaDiscapacidad the areaDiscapacidad to set
     */
    public void setAreaDiscapacidad(final Boolean areaDiscapacidad) {
        this.areaDiscapacidad = areaDiscapacidad;
    }

    /**
     * @return the discapacidad
     */
    public String getDiscapacidad() {
        return discapacidad;
    }

    /**
     * @param discapacidad the discapacidad to set
     */
    public void setDiscapacidad(final String discapacidad) {
        this.discapacidad = discapacidad;
    }

    /**
     * @return the claseDiscapacidad
     */
    public String getClaseDiscapacidad() {
        return claseDiscapacidad;
    }

    /**
     * @param claseDiscapacidad the claseDiscapacidad to set
     */
    public void setClaseDiscapacidad(final String claseDiscapacidad) {
        this.claseDiscapacidad = claseDiscapacidad;
    }

    /**
     * @return the tipoDiscapacidad
     */
    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    /**
     * @param tipoDiscapacidad the tipoDiscapacidad to set
     */
    public void setTipoDiscapacidad(final String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    /**
     * @return the porcentajeDiscapacidad
     */
    public String getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    /**
     * @param porcentajeDiscapacidad the porcentajeDiscapacidad to set
     */
    public void setPorcentajeDiscapacidad(final String porcentajeDiscapacidad) {
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
    }

    /**
     * @return the numeroConadis
     */
    public String getNumeroConadis() {
        return numeroConadis;
    }

    /**
     * @param numeroConadis the numeroConadis to set
     */
    public void setNumeroConadis(final String numeroConadis) {
        this.numeroConadis = numeroConadis;
    }

    /**
     * @return the areaFormacionAcademica
     */
    public Boolean getAreaFormacionAcademica() {
        return areaFormacionAcademica;
    }

    /**
     * @param areaFormacionAcademica the areaFormacionAcademica to set
     */
    public void setAreaFormacionAcademica(final Boolean areaFormacionAcademica) {
        this.areaFormacionAcademica = areaFormacionAcademica;
    }

    /**
     * @return the nivelnstruccion
     */
    public String getNivelInstruccion() {
        return nivelInstruccion;
    }

    /**
     * @param nivelInstruccion the nivelInstruccion to set
     */
    public void setNivelInstruccion(final String nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
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
     * @return the paisFormacionAcademica
     */
    public String getPaisFormacionAcademica() {
        return paisFormacionAcademica;
    }

    /**
     * @param paisFormacionAcademica the paisFormacionAcademica to set
     */
    public void setPaisFormacionAcademica(final String paisFormacionAcademica) {
        this.paisFormacionAcademica = paisFormacionAcademica;
    }

    /**
     * @return the aniosEstudio
     */
    public String getAniosEstudio() {
        return aniosEstudio;
    }

    /**
     * @param aniosEstudio the aniosEstudio to set
     */
    public void setAniosEstudio(final String aniosEstudio) {
        this.aniosEstudio = aniosEstudio;
    }

    /**
     * @return the documentoHabilitante
     */
    public DocumentoHabilitante getDocumentoHabilitante() {
        return documentoHabilitante;
    }

    /**
     * @param documentoHabilitante the documentoHabilitante to set
     */
    public void setDocumentoHabilitante(final DocumentoHabilitante documentoHabilitante) {
        this.documentoHabilitante = documentoHabilitante;
    }

    /**
     * @return the clase
     */
    public Clase getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(final Clase clase) {
        this.clase = clase;
    }

    /**
     * @return the areaEstadoPuesto
     */
    public Boolean getAreaEstadoPuesto() {
        return areaEstadoPuesto;
    }

    /**
     * @param areaEstadoPuesto the areaEstadoPuesto to set
     */
    public void setAreaEstadoPuesto(final Boolean areaEstadoPuesto) {
        this.areaEstadoPuesto = areaEstadoPuesto;
    }

    /**
     * @return the estadoPuestoInicialCore
     */
    public Long getEstadoPuestoInicialCore() {
        return estadoPuestoInicialCore;
    }

    /**
     * @param estadoPuestoInicialCore the estadoPuestoInicialCore to set
     */
    public void setEstadoPuestoInicialCore(final Long estadoPuestoInicialCore) {
        this.estadoPuestoInicialCore = estadoPuestoInicialCore;
    }

    /**
     * @return the estadoPuestoFinalCore
     */
    public Long getEstadoPuestoFinalCore() {
        return estadoPuestoFinalCore;
    }

    /**
     * @param estadoPuestoFinalCore the estadoPuestoFinalCore to set
     */
    public void setEstadoPuestoFinalCore(final Long estadoPuestoFinalCore) {
        this.estadoPuestoFinalCore = estadoPuestoFinalCore;
    }

    /**
     * @return the areaEstadoPersonal
     */
    public Boolean getAreaEstadoPersonal() {
        return areaEstadoPersonal;
    }

    /**
     * @param areaEstadoPersonal the areaEstadoPersonal to set
     */
    public void setAreaEstadoPersonal(final Boolean areaEstadoPersonal) {
        this.areaEstadoPersonal = areaEstadoPersonal;
    }

    /**
     * @return the estadoPersonalInicialCore
     */
    public Long getEstadoPersonalInicialCore() {
        return estadoPersonalInicialCore;
    }

    /**
     * @param estadoPersonalInicialCore the estadoPersonalInicialCore to set
     */
    public void setEstadoPersonalInicialCore(final Long estadoPersonalInicialCore) {
        this.estadoPersonalInicialCore = estadoPersonalInicialCore;
    }

    /**
     * @return the estadoPersonalFinalCore
     */
    public Long getEstadoPersonalFinalCore() {
        return estadoPersonalFinalCore;
    }

    /**
     * @param estadoPersonalFinalCore the estadoPersonalFinalCore to set
     */
    public void setEstadoPersonalFinalCore(final Long estadoPersonalFinalCore) {
        this.estadoPersonalFinalCore = estadoPersonalFinalCore;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the claseId
     */
    public Long getClaseId() {
        return claseId;
    }

    /**
     * @param claseId the claseId to set
     */
    public void setClaseId(final Long claseId) {
        this.claseId = claseId;
    }

    /**
     * @return the documentoHabilitanteId
     */
    public Long getDocumentoHabilitanteId() {
        return documentoHabilitanteId;
    }

    /**
     * @param documentoHabilitanteId the documentoHabilitanteId to set
     */
    public void setDocumentoHabilitanteId(final Long documentoHabilitanteId) {
        this.documentoHabilitanteId = documentoHabilitanteId;
    }

    /**
     * @return the listaAlerta
     */
    public List<TipoMovimientoAlerta> getListaAlerta() {
        return listaAlerta;
    }

    /**
     * @return the listaReglas
     */
    public List<TipoMovimientoRegla> getListaReglas() {
        return listaReglas;
    }

    /**
     * @return the listaRequisitos
     */
    public List<TipoMovimientoRequisito> getListaRequisitos() {
        return listaRequisitos;
    }

//    /**
//     * @param listaAlerta the listaAlerta to set
//     */
//    public void setListaAlerta(final List<TipoMovimientoAlerta> listaAlerta) {
//        this.setListaAlerta(listaAlerta);
//    }
//
//    /**
//     * @param listaReglas the listaReglas to set
//     */
//    public void setListaReglas(final List<TipoMovimientoRegla> listaReglas) {
//        this.setListaReglas(listaReglas);
//    }
//
//    /**
//     * @param listaRequisitos the listaRequisitos to set
//     */
//    public void setListaRequisitos(final List<TipoMovimientoRequisito> listaRequisitos) {
//        this.setListaRequisitos(listaRequisitos);
//    }
    /**
     * @return the modalidadLaboralCoreNombre
     */
    public String getModalidadLaboralCoreNombre() {
        return modalidadLaboralCoreNombre;
    }

    /**
     * @param modalidadLaboralCoreNombre the modalidadLaboralCoreNombre to set
     */
    public void setModalidadLaboralCoreNombre(final String modalidadLaboralCoreNombre) {
        this.modalidadLaboralCoreNombre = modalidadLaboralCoreNombre;
    }

    /**
     * @return the nivelOcupacionalCoreNombre
     */
    public String getNivelOcupacionalCoreNombre() {
        return nivelOcupacionalCoreNombre;
    }

    /**
     * @param nivelOcupacionalCoreNombre the nivelOcupacionalCoreNombre to set
     */
    public void setNivelOcupacionalCoreNombre(final String nivelOcupacionalCoreNombre) {
        this.nivelOcupacionalCoreNombre = nivelOcupacionalCoreNombre;
    }

    /**
     * @return the estadoPuestoInicialCoreNombre
     */
    public String getEstadoPuestoInicialCoreNombre() {
        return estadoPuestoInicialCoreNombre;
    }

    /**
     * @param estadoPuestoInicialCoreNombre the estadoPuestoInicialCoreNombre to set
     */
    public void setEstadoPuestoInicialCoreNombre(final String estadoPuestoInicialCoreNombre) {
        this.estadoPuestoInicialCoreNombre = estadoPuestoInicialCoreNombre;
    }

    /**
     * @return the estadoPuestoFinalCoreNombre
     */
    public String getEstadoPuestoFinalCoreNombre() {
        return estadoPuestoFinalCoreNombre;
    }

    /**
     * @param estadoPuestoFinalCoreNombre the estadoPuestoFinalCoreNombre to set
     */
    public void setEstadoPuestoFinalCoreNombre(final String estadoPuestoFinalCoreNombre) {
        this.estadoPuestoFinalCoreNombre = estadoPuestoFinalCoreNombre;
    }

    /**
     * @return the estadoPersonalInicialCoreNombre
     */
    public String getEstadoPersonalInicialCoreNombre() {
        return estadoPersonalInicialCoreNombre;
    }

    /**
     * @param estadoPersonalInicialCoreNombre the estadoPersonalInicialCoreNombre to set
     */
    public void setEstadoPersonalInicialCoreNombre(final String estadoPersonalInicialCoreNombre) {
        this.estadoPersonalInicialCoreNombre = estadoPersonalInicialCoreNombre;
    }

    /**
     * @return the estadoPersonalFinalCoreNombre
     */
    public String getEstadoPersonalFinalCoreNombre() {
        return estadoPersonalFinalCoreNombre;
    }

    /**
     * @param estadoPersonalFinalCoreNombre the estadoPersonalFinalCoreNombre to set
     */
    public void setEstadoPersonalFinalCoreNombre(final String estadoPersonalFinalCoreNombre) {
        this.estadoPersonalFinalCoreNombre = estadoPersonalFinalCoreNombre;
    }

    /**
     * @param antecedentesContrato the antecedentesContrato to set
     */
    public void setAntecedentesContrato(final String antecedentesContrato) {
        this.antecedentesContrato = antecedentesContrato;
    }

    /**
     * @return the antecedentesContrato
     */
    public String getAntecedentesContrato() {
        return antecedentesContrato;
    }

    /**
     * @return the areaAccionPersonal
     */
    public Boolean getAreaAccionPersonal() {
        return areaAccionPersonal;
    }

    /**
     * @param areaAccionPersonal the areaAccionPersonal to set
     */
    public void setAreaAccionPersonal(final Boolean areaAccionPersonal) {
        this.areaAccionPersonal = areaAccionPersonal;
    }

    /**
     * @return the situacionActual
     */
    public Boolean getSituacionActual() {
        return situacionActual;
    }

    /**
     * @param situacionActual the situacionActual to set
     */
    public void setSituacionActual(final Boolean situacionActual) {
        this.situacionActual = situacionActual;
    }

    /**
     * @return the situacionPropuesta
     */
    public Boolean getSituacionPropuesta() {
        return situacionPropuesta;
    }

    /**
     * @param situacionPropuesta the situacionPropuesta to set
     */
    public void setSituacionPropuesta(final Boolean situacionPropuesta) {
        this.situacionPropuesta = situacionPropuesta;
    }

    /**
     * @return the reemplazo
     */
    public Boolean getReemplazo() {
        return reemplazo;
    }

    /**
     * @param reemplazo the reemplazo to set
     */
    public void setReemplazo(final Boolean reemplazo) {
        this.reemplazo = reemplazo;
    }

    /**
     * @return the posesionCargo
     */
    public Boolean getPosesionCargo() {
        return posesionCargo;
    }

    /**
     * @param posesionCargo the posesionCargo to set
     */
    public void setPosesionCargo(final Boolean posesionCargo) {
        this.posesionCargo = posesionCargo;
    }

    /**
     * @return the fechaAceptacionRenuncia
     */
    public String getFechaAceptacionRenuncia() {
        return fechaAceptacionRenuncia;
    }

    /**
     * @param fechaAceptacionRenuncia the fechaAceptacionRenuncia to set
     */
    public void setFechaAceptacionRenuncia(final String fechaAceptacionRenuncia) {
        this.fechaAceptacionRenuncia = fechaAceptacionRenuncia;
    }

    /**
     * @return the areaContratoLosep
     */
    public Boolean getAreaContratoLosep() {
        return areaContratoLosep;
    }

    /**
     * @param areaContratoLosep the areaContratoLosep to set
     */
    public void setAreaContratoLosep(final Boolean areaContratoLosep) {
        this.areaContratoLosep = areaContratoLosep;
    }

    /**
     * @return the siglasTituloAcademico
     */
    public String getSiglasTituloAcademico() {
        return siglasTituloAcademico;
    }

    /**
     * @param siglasTituloAcademico the siglasTituloAcademico to set
     */
    public void setSiglasTituloAcademico(final String siglasTituloAcademico) {
        this.siglasTituloAcademico = siglasTituloAcademico;
    }

    /**
     * @return the areaContratoCT
     */
    public Boolean getAreaContratoCT() {
        return areaContratoCT;
    }

    /**
     * @param areaContratoCT the areaContratoCT to set
     */
    public void setAreaContratoCT(final Boolean areaContratoCT) {
        this.areaContratoCT = areaContratoCT;
    }

    /**
     * @return the numeroRegistroSenescyt
     */
    public String getNumeroRegistroSenescyt() {
        return numeroRegistroSenescyt;
    }

    /**
     * @param numeroRegistroSenescyt the numeroRegistroSenescyt to set
     */
    public void setNumeroRegistroSenescyt(final String numeroRegistroSenescyt) {
        this.numeroRegistroSenescyt = numeroRegistroSenescyt;
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
     * @return the renovacion
     */
    public String getRenovacion() {
        return renovacion;
    }

    /**
     * @param renovacion the renovacion to set
     */
    public void setRenovacion(final String renovacion) {
        this.renovacion = renovacion;
    }

    /**
     * @return the areaMemorando
     */
    public Boolean getAreaMemorando() {
        return areaMemorando;
    }

    /**
     * @param areaMemorando the areaMemorando to set
     */
    public void setAreaMemorando(final Boolean areaMemorando) {
        this.areaMemorando = areaMemorando;
    }

    /**
     * @return the numeroMemorando
     */
    public String getNumeroMemorando() {
        return numeroMemorando;
    }

    /**
     * @param numeroMemorando the numeroMemorando to set
     */
    public void setNumeroMemorando(final String numeroMemorando) {
        this.numeroMemorando = numeroMemorando;
    }

    /**
     * @return the asuntoMemorando
     */
    public String getAsuntoMemorando() {
        return asuntoMemorando;
    }

    /**
     * @param asuntoMemorando the asuntoMemorando to set
     */
    public void setAsuntoMemorando(final String asuntoMemorando) {
        this.asuntoMemorando = asuntoMemorando;
    }

    /**
     * @return the contenidoMemorando
     */
    public String getContenidoMemorando() {
        return contenidoMemorando;
    }

    /**
     * @param contenidoMemorando the contenidoMemorando to set
     */
    public void setContenidoMemorando(final String contenidoMemorando) {
        this.contenidoMemorando = contenidoMemorando;
    }

    /**
     * @return the areaRegimenDisciplinario
     */
    public Boolean getAreaRegimenDisciplinario() {
        return areaRegimenDisciplinario;
    }

    /**
     * @param areaRegimenDisciplinario the areaRegimenDisciplinario to set
     */
    public void setAreaRegimenDisciplinario(final Boolean areaRegimenDisciplinario) {
        this.areaRegimenDisciplinario = areaRegimenDisciplinario;
    }

    /**
     * @return the fechaHasta
     */
    public String getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(final String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @return the fechaRigeAPartirDe
     */
    public String getFechaRigeAPartirDe() {
        return fechaRigeAPartirDe;
    }

    /**
     * @param fechaRigeAPartirDe the fechaRigeAPartirDe to set
     */
    public void setFechaRigeAPartirDe(final String fechaRigeAPartirDe) {
        this.fechaRigeAPartirDe = fechaRigeAPartirDe;
    }

    /**
     * @return the tipoFlujo
     */
    public String getTipoFlujo() {
        return tipoFlujo;
    }

    /**
     * @return the tiempoMaximo
     */
    public Integer getTiempoMaximo() {
        return tiempoMaximo;
    }

    /**
     * @return the periodoTiempoMaximo
     */
    public String getPeriodoTiempoMaximo() {
        return periodoTiempoMaximo;
    }

    /**
     * @param tipoFlujo the tipoFlujo to set
     */
    public void setTipoFlujo(final String tipoFlujo) {
        this.tipoFlujo = tipoFlujo;
    }

    /**
     * @param tiempoMaximo the tiempoMaximo to set
     */
    public void setTiempoMaximo(final Integer tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    /**
     * @param periodoTiempoMaximo the periodoTiempoMaximo to set
     */
    public void setPeriodoTiempoMaximo(final String periodoTiempoMaximo) {
        this.periodoTiempoMaximo = periodoTiempoMaximo;
    }

    /**
     * @return the areaLicenciasMaternidadPaternidad
     */
    public Boolean getAreaLicenciasMaternidadPaternidad() {
        return areaLicenciasMaternidadPaternidad;
    }

    /**
     * @param areaLicenciasMaternidadPaternidad the areaLicenciasMaternidadPaternidad to set
     */
    public void setAreaLicenciasMaternidadPaternidad(final Boolean areaLicenciasMaternidadPaternidad) {
        this.areaLicenciasMaternidadPaternidad = areaLicenciasMaternidadPaternidad;
    }

    /**
     * @return the tipoNacimiento
     */
    public String getTipoNacimiento() {
        return tipoNacimiento;
    }

    /**
     * @param tipoNacimiento the tipoNacimiento to set
     */
    public void setTipoNacimiento(final String tipoNacimiento) {
        this.tipoNacimiento = tipoNacimiento;
    }

    /**
     * @return the diasAdicionalesMadreNacimientoMultiple
     */
    public Integer getDiasAdicionalesMadreNacimientoMultiple() {
        return diasAdicionalesMadreNacimientoMultiple;
    }

    /**
     * @param diasAdicionalesMadreNacimientoMultiple the diasAdicionalesMadreNacimientoMultiple to set
     */
    public void setDiasAdicionalesMadreNacimientoMultiple(final Integer diasAdicionalesMadreNacimientoMultiple) {
        this.diasAdicionalesMadreNacimientoMultiple = diasAdicionalesMadreNacimientoMultiple;
    }

    /**
     * @return the diasAdicionalesPadreNacimientoMultiple
     */
    public Integer getDiasAdicionalesPadreNacimientoMultiple() {
        return diasAdicionalesPadreNacimientoMultiple;
    }

    /**
     * @param diasAdicionalesPadreNacimientoMultiple the diasAdicionalesPadreNacimientoMultiple to set
     */
    public void setDiasAdicionalesPadreNacimientoMultiple(final Integer diasAdicionalesPadreNacimientoMultiple) {
        this.diasAdicionalesPadreNacimientoMultiple = diasAdicionalesPadreNacimientoMultiple;
    }
//    /**
//     * @return the tiempoMaximo
//     */
//    public String getTiempoMaximo() {
//        return tiempoMaximo;
//    }
//
//    /**
//     * @param tiempoMaximo the tiempoMaximo to set
//     */
//    public void setTiempoMaximo(final String tiempoMaximo) {
//        this.tiempoMaximo = tiempoMaximo;
//    }

    /**
     * @return the areaConfiguracionLicenciasPermisos
     */
    public Boolean getAreaConfiguracionLicenciasPermisos() {
        return areaConfiguracionLicenciasPermisos;
    }

    /**
     * @param areaConfiguracionLicenciasPermisos the areaConfiguracionLicenciasPermisos to set
     */
    public void setAreaConfiguracionLicenciasPermisos(final Boolean areaConfiguracionLicenciasPermisos) {
        this.areaConfiguracionLicenciasPermisos = areaConfiguracionLicenciasPermisos;
    }

    /**
     * @return the horario
     */
    public String getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(final String horario) {
        this.horario = horario;
    }

    /**
     * @return the periodoControl
     */
    public String getPeriodoControl() {
        return periodoControl;
    }

    /**
     * @param periodoControl the periodoControl to set
     */
    public void setPeriodoControl(final String periodoControl) {
        this.periodoControl = periodoControl;
    }

    /**
     * @return the valorPeriodoControl
     */
    public Integer getValorPeriodoControl() {
        return valorPeriodoControl;
    }

    /**
     * @param valorPeriodoControl the valorPeriodoControl to set
     */
    public void setValorPeriodoControl(final Integer valorPeriodoControl) {
        this.valorPeriodoControl = valorPeriodoControl;
    }

    /**
     * @return the areaLicencias
     */
    public Boolean getAreaLicencias() {
        return areaLicencias;
    }

    /**
     * @param areaLicencias the areaLicencias to set
     */
    public void setAreaLicencias(final Boolean areaLicencias) {
        this.areaLicencias = areaLicencias;
    }

    /**
     * @return the fechaOcurrioHecho
     */
    public String getFechaOcurrioHecho() {
        return fechaOcurrioHecho;
    }

    /**
     * @param fechaOcurrioHecho the fechaOcurrioHecho to set
     */
    public void setFechaOcurrioHecho(final String fechaOcurrioHecho) {
        this.fechaOcurrioHecho = fechaOcurrioHecho;
    }

    /**
     * @return the areaTiempoPorDevengar
     */
    public Boolean getAreaTiempoPorDevengar() {
        return areaTiempoPorDevengar;
    }

    /**
     * @param areaTiempoPorDevengar the areaTiempoPorDevengar to set
     */
    public void setAreaTiempoPorDevengar(final Boolean areaTiempoPorDevengar) {
        this.areaTiempoPorDevengar = areaTiempoPorDevengar;
    }

    /**
     * @return the periodoDevengar
     */
    public String getPeriodoDevengar() {
        return periodoDevengar;
    }

    /**
     * @param periodoDevengar the periodoDevengar to set
     */
    public void setPeriodoDevengar(final String periodoDevengar) {
        this.periodoDevengar = periodoDevengar;
    }

    /**
     * @return the valorDevengar
     */
    public String getValorDevengar() {
        return valorDevengar;
    }

    /**
     * @param valorDevengar the valorDevengar to set
     */
    public void setValorDevengar(final String valorDevengar) {
        this.valorDevengar = valorDevengar;
    }

    /**
     * @return the considereCalculo
     */
    public String getConsidereCalculo() {
        return considereCalculo;
    }

    /**
     * @param considereCalculo the considereCalculo to set
     */
    public void setConsidereCalculo(final String considereCalculo) {
        this.considereCalculo = considereCalculo;
    }

    /**
     * @return the devengar
     */
    public String getDevengar() {
        return devengar;
    }

    /**
     * @param devengar the devengar to set
     */
    public void setDevengar(final String devengar) {
        this.devengar = devengar;
    }

    /**
     * @return the fechaPresentaDocumento
     */
    public String getFechaPresentaDocumento() {
        return fechaPresentaDocumento;
    }

    /**
     * @param fechaPresentaDocumento the fechaPresentaDocumento to set
     */
    public void setFechaPresentaDocumento(final String fechaPresentaDocumento) {
        this.fechaPresentaDocumento = fechaPresentaDocumento;
    }

    /**
     * @return the observacionDevengar
     */
    public String getObservacionDevengar() {
        return observacionDevengar;
    }

    /**
     * @param observacionDevengar the observacionDevengar to set
     */
    public void setObservacionDevengar(final String observacionDevengar) {
        this.observacionDevengar = observacionDevengar;
    }

    /**
     * @return the controlFechaPresentaDocumento
     */
    public String getControlFechaPresentaDocumento() {
        return controlFechaPresentaDocumento;
    }

    /**
     * @param controlFechaPresentaDocumento the controlFechaPresentaDocumento to set
     */
    public void setControlFechaPresentaDocumento(final String controlFechaPresentaDocumento) {
        this.controlFechaPresentaDocumento = controlFechaPresentaDocumento;
    }

    /**
     * @return the totalHorasSemana
     */
    public Integer getTotalHorasSemana() {
        return totalHorasSemana;
    }

    /**
     * @param totalHorasSemana the totalHorasSemana to set
     */
    public void setTotalHorasSemana(final Integer totalHorasSemana) {
        this.totalHorasSemana = totalHorasSemana;
    }

    /**
     * @return the conSolicitud
     */
    public Boolean getConSolicitud() {
        return conSolicitud;
    }

    /**
     * @param conSolicitud the conSolicitud to set
     */
    public void setConSolicitud(final Boolean conSolicitud) {
        this.conSolicitud = conSolicitud;
    }

    /**
     * @return the horarioDevengar
     */
    public String getHorarioDevengar() {
        return horarioDevengar;
    }

    /**
     * @param horarioDevengar the horarioDevengar to set
     */
    public void setHorarioDevengar(final String horarioDevengar) {
        this.horarioDevengar = horarioDevengar;
    }

    /**
     * @return the ingresaHoraMinuto
     */
    public String getIngresaHoraMinuto() {
        return ingresaHoraMinuto;
    }

    /**
     * @param ingresaHoraMinuto the ingresaHoraMinuto to set
     */
    public void setIngresaHoraMinuto(final String ingresaHoraMinuto) {
        this.ingresaHoraMinuto = ingresaHoraMinuto;
    }

    /**
     * @return the areaRepresentacionAsociacionLaboral
     */
    public Boolean getAreaRepresentacionAsociacionLaboral() {
        return areaRepresentacionAsociacionLaboral;
    }

    /**
     * @param areaRepresentacionAsociacionLaboral the areaRepresentacionAsociacionLaboral to set
     */
    public void setAreaRepresentacionAsociacionLaboral(final Boolean areaRepresentacionAsociacionLaboral) {
        this.areaRepresentacionAsociacionLaboral = areaRepresentacionAsociacionLaboral;
    }

    /**
     * @return the areaPermisoMatriculaHijos
     */
    public Boolean getAreaPermisoMatriculaHijos() {
        return areaPermisoMatriculaHijos;
    }

    /**
     * @param areaPermisoMatriculaHijos the areaPermisoMatriculaHijos to set
     */
    public void setAreaPermisoMatriculaHijos(final Boolean areaPermisoMatriculaHijos) {
        this.areaPermisoMatriculaHijos = areaPermisoMatriculaHijos;
    }

    /**
     * @return the nombreHijo
     */
    public String getNombreHijo() {
        return nombreHijo;
    }

    /**
     * @param nombreHijo the nombreHijo to set
     */
    public void setNombreHijo(final String nombreHijo) {
        this.nombreHijo = nombreHijo;
    }

    /**
     * @return the nivelEstudio
     */
    public String getNivelEstudio() {
        return nivelEstudio;
    }

    /**
     * @param nivelEstudio the nivelEstudio to set
     */
    public void setNivelEstudio(final String nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    /**
     * @return the numeroHorasMensuales
     */
    public String getNumeroHorasMensuales() {
        return numeroHorasMensuales;
    }

    /**
     * @param numeroHorasMensuales the numeroHorasMensuales to set
     */
    public void setNumeroHorasMensuales(final String numeroHorasMensuales) {
        this.numeroHorasMensuales = numeroHorasMensuales;
    }

    /**
     * @return the maximoNumeroHorasMensuales
     */
    public Integer getMaximoNumeroHorasMensuales() {
        return maximoNumeroHorasMensuales;
    }

    /**
     * @param maximoNumeroHorasMensuales the maximoNumeroHorasMensuales to set
     */
    public void setMaximoNumeroHorasMensuales(final Integer maximoNumeroHorasMensuales) {
        this.maximoNumeroHorasMensuales = maximoNumeroHorasMensuales;
    }

    /**
     * @return the areaComisionServicioInstitucionRequiriente
     */
    public Boolean getAreaComisionServicioInstitucionRequiriente() {
        return areaComisionServicioInstitucionRequiriente;
    }

    /**
     * @param areaComisionServicioInstitucionRequiriente the areaComisionServicioInstitucionRequiriente to set
     */
    public void setAreaComisionServicioInstitucionRequiriente(final Boolean areaComisionServicioInstitucionRequiriente) {
        this.areaComisionServicioInstitucionRequiriente = areaComisionServicioInstitucionRequiriente;
    }

    /**
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final String institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the unidadAdministrativaInstitucion
     */
    public String getUnidadAdministrativaInstitucion() {
        return unidadAdministrativaInstitucion;
    }

    /**
     * @param unidadAdministrativaInstitucion the unidadAdministrativaInstitucion to set
     */
    public void setUnidadAdministrativaInstitucion(final String unidadAdministrativaInstitucion) {
        this.unidadAdministrativaInstitucion = unidadAdministrativaInstitucion;
    }

    /**
     * @return the unidadAdministrativaDireccionInstitucion
     */
    public String getUnidadAdministrativaDireccionInstitucion() {
        return unidadAdministrativaDireccionInstitucion;
    }

    /**
     * @param unidadAdministrativaDireccionInstitucion the unidadAdministrativaDireccionInstitucion to set
     */
    public void setUnidadAdministrativaDireccionInstitucion(final String unidadAdministrativaDireccionInstitucion) {
        this.unidadAdministrativaDireccionInstitucion = unidadAdministrativaDireccionInstitucion;
    }

    /**
     * @return the unidadAdministrativaInstitucionCambio
     */
    public String getUnidadAdministrativaInstitucionCambio() {
        return unidadAdministrativaInstitucionCambio;
    }

    /**
     * @param unidadAdministrativaInstitucionCambio the unidadAdministrativaInstitucionCambio to set
     */
    public void setUnidadAdministrativaInstitucionCambio(final String unidadAdministrativaInstitucionCambio) {
        this.unidadAdministrativaInstitucionCambio = unidadAdministrativaInstitucionCambio;
    }

    /**
     * @return the unidadAdministrativaDireccionInstitucionCambio
     */
    public String getUnidadAdministrativaDireccionInstitucionCambio() {
        return unidadAdministrativaDireccionInstitucionCambio;
    }

    /**
     * @param unidadAdministrativaDireccionInstitucionCambio the unidadAdministrativaDireccionInstitucionCambio to set
     */
    public void setUnidadAdministrativaDireccionInstitucionCambio(
            final String unidadAdministrativaDireccionInstitucionCambio) {
        this.unidadAdministrativaDireccionInstitucionCambio = unidadAdministrativaDireccionInstitucionCambio;
    }

    /**
     * @return the rdfalta
     */
    public String getRdfalta() {
        return rdfalta;
    }

    /**
     * @return the rdDescripcion
     */
    public String getRdDescripcion() {
        return rdDescripcion;
    }

    /**
     * @param rdfalta the rdfalta to set
     */
    public void setRdfalta(final String rdfalta) {
        this.rdfalta = rdfalta;
    }

    /**
     * @param rdDescripcion the rdDescripcion to set
     */
    public void setRdDescripcion(final String rdDescripcion) {
        this.rdDescripcion = rdDescripcion;
    }

    /**
     * @return the taPartidaIndividual
     */
    public String getTaPartidaIndividual() {
        return taPartidaIndividual;
    }

    /**
     * @param taPartidaIndividual the taPartidaIndividual to set
     */
    public void setTaPartidaIndividual(final String taPartidaIndividual) {
        this.taPartidaIndividual = taPartidaIndividual;
    }

    /**
     * @return the tUnidadAdministrativa
     */
    public String gettUnidadAdministrativa() {
        return tUnidadAdministrativa;
    }

    /**
     * @return the tUnidadAdministrativaDireccion
     */
    public String gettUnidadAdministrativaDireccion() {
        return tUnidadAdministrativaDireccion;
    }

    /**
     * @return the tDenominacionPuesto
     */
    public String gettDenominacionPuesto() {
        return tDenominacionPuesto;
    }

    /**
     * @param tUnidadAdministrativa the tUnidadAdministrativa to set
     */
    public void settUnidadAdministrativa(final String tUnidadAdministrativa) {
        this.tUnidadAdministrativa = tUnidadAdministrativa;
    }

    /**
     * @param tUnidadAdministrativaDireccion the tUnidadAdministrativaDireccion to set
     */
    public void settUnidadAdministrativaDireccion(final String tUnidadAdministrativaDireccion) {
        this.tUnidadAdministrativaDireccion = tUnidadAdministrativaDireccion;
    }

    /**
     * @param tDenominacionPuesto the tDenominacionPuesto to set
     */
    public void settDenominacionPuesto(final String tDenominacionPuesto) {
        this.tDenominacionPuesto = tDenominacionPuesto;
    }

    /**
     * @return the areaPuestoDestino
     */
    public Boolean getAreaPuestoDestino() {
        return areaPuestoDestino;
    }

    /**
     * @param areaPuestoDestino the areaPuestoDestino to set
     */
    public void setAreaPuestoDestino(final Boolean areaPuestoDestino) {
        this.areaPuestoDestino = areaPuestoDestino;
    }

    /**
     * @return the areaEstadoPuestoPropuesto
     */
    public Boolean getAreaEstadoPuestoPropuesto() {
        return areaEstadoPuestoPropuesto;
    }

    /**
     * @param areaEstadoPuestoPropuesto the areaEstadoPuestoPropuesto to set
     */
    public void setAreaEstadoPuestoPropuesto(final Boolean areaEstadoPuestoPropuesto) {
        this.areaEstadoPuestoPropuesto = areaEstadoPuestoPropuesto;
    }

    /**
     * @return the estadoPuestoFinalPropuestaCore
     */
    public Long getEstadoPuestoFinalPropuestaCore() {
        return estadoPuestoFinalPropuestaCore;
    }

    /**
     * @param estadoPuestoFinalPropuestaCore the estadoPuestoFinalPropuestaCore to set
     */
    public void setEstadoPuestoFinalPropuestaCore(final Long estadoPuestoFinalPropuestaCore) {
        this.estadoPuestoFinalPropuestaCore = estadoPuestoFinalPropuestaCore;
    }

    /**
     * @return the estadoPuestoInicialPropuestaCore
     */
    public Long getEstadoPuestoInicialPropuestaCore() {
        return estadoPuestoInicialPropuestaCore;
    }

    /**
     * @param estadoPuestoInicialPropuestaCore the estadoPuestoInicialPropuestaCore to set
     */
    public void setEstadoPuestoInicialPropuestaCore(final Long estadoPuestoInicialPropuestaCore) {
        this.estadoPuestoInicialPropuestaCore = estadoPuestoInicialPropuestaCore;
    }

    /**
     * @return the estadoPuestoInicialPropuestaCoreNombre
     */
    public String getEstadoPuestoInicialPropuestaCoreNombre() {
        return estadoPuestoInicialPropuestaCoreNombre;
    }

    /**
     * @return the estadoPuestoFinalPropuestaCoreNombre
     */
    public String getEstadoPuestoFinalPropuestaCoreNombre() {
        return estadoPuestoFinalPropuestaCoreNombre;
    }

    /**
     * @param estadoPuestoInicialPropuestaCoreNombre the estadoPuestoInicialPropuestaCoreNombre to set
     */
    public void setEstadoPuestoInicialPropuestaCoreNombre(final String estadoPuestoInicialPropuestaCoreNombre) {
        this.estadoPuestoInicialPropuestaCoreNombre = estadoPuestoInicialPropuestaCoreNombre;
    }

    /**
     * @param estadoPuestoFinalPropuestaCoreNombre the estadoPuestoFinalPropuestaCoreNombre to set
     */
    public void setEstadoPuestoFinalPropuestaCoreNombre(final String estadoPuestoFinalPropuestaCoreNombre) {
        this.estadoPuestoFinalPropuestaCoreNombre = estadoPuestoFinalPropuestaCoreNombre;
    }

    /**
     * @return the apExplicacion
     */
    public String getApExplicacion() {
        return apExplicacion;
    }

    /**
     * @return the apDocumento_previo
     */
    public String getApDocumentoPrevio() {
        return apDocumentoPrevio;
    }

    /**
     * @return the apNumeroDocumento
     */
    public String getApNumeroDocumento() {
        return apNumeroDocumento;
    }

    /**
     * @return the apFechaDocumento
     */
    public String getApFechaDocumento() {
        return apFechaDocumento;
    }

    /**
     * @param apExplicacion the apExplicacion to set
     */
    public void setApExplicacion(final String apExplicacion) {
        this.apExplicacion = apExplicacion;
    }

    /**
     * @param apDocumento_previo the apDocumento_previo to set
     */
    public void setApDocumentoPrevio(final String apDocumentoPrevio) {
        this.apDocumentoPrevio = apDocumentoPrevio;
    }

    /**
     * @param apNumeroDocumento the apNumeroDocumento to set
     */
    public void setApNumeroDocumento(final String apNumeroDocumento) {
        this.apNumeroDocumento = apNumeroDocumento;
    }

    /**
     * @param apFechaDocumento the apFechaDocumento to set
     */
    public void setApFechaDocumento(final String apFechaDocumento) {
        this.apFechaDocumento = apFechaDocumento;
    }

    /**
     * @return the partidaIndividualPuestDest
     */
    public String getPartidaIndividualPuestDest() {
        return partidaIndividualPuestDest;
    }

    /**
     * @param partidaIndividualPuestDest the partidaIndividualPuestDest to set
     */
    public void setPartidaIndividualPuestDest(final String partidaIndividualPuestDest) {
        this.partidaIndividualPuestDest = partidaIndividualPuestDest;
    }

    /**
     * @return the areaInformacionMovimientoReintegro
     */
    public Boolean getAreaInformacionMovimientoReintegro() {
        return areaInformacionMovimientoReintegro;
    }

    /**
     * @param areaInformacionMovimientoReintegro the areaInformacionMovimientoReintegro to set
     */
    public void setAreaInformacionMovimientoReintegro(final Boolean areaInformacionMovimientoReintegro) {
        this.areaInformacionMovimientoReintegro = areaInformacionMovimientoReintegro;
    }

    /**
     * @return the tipoMovimientoFinalizacion
     */
    public TipoMovimiento getTipoMovimientoFinalizacion() {
        return tipoMovimientoFinalizacion;
    }

    /**
     * @param tipoMovimientoFinalizacion the tipoMovimientoFinalizacion to set
     */
    public void setTipoMovimientoFinalizacion(final TipoMovimiento tipoMovimientoFinalizacion) {
        this.tipoMovimientoFinalizacion = tipoMovimientoFinalizacion;
    }

    /**
     * @return the areaFinalizacion
     */
    public Boolean getAreaFinalizacion() {
        return areaFinalizacion;
    }

    /**
     * @return the afTipoMovimientoInicial
     */
    public String getAfTipoMovimientoInicial() {
        return afTipoMovimientoInicial;
    }

    /**
     * @param areaFinalizacion the areaFinalizacion to set
     */
    public void setAreaFinalizacion(final Boolean areaFinalizacion) {
        this.areaFinalizacion = areaFinalizacion;
    }

    /**
     * @param afTipoMovimientoInicial the afTipoMovimientoInicial to set
     */
    public void setAfTipoMovimientoInicial(final String afTipoMovimientoInicial) {
        this.afTipoMovimientoInicial = afTipoMovimientoInicial;
    }

    /**
     * @return the tipoMovimientoFinalizacionId
     */
    public Long getTipoMovimientoFinalizacionId() {
        return tipoMovimientoFinalizacionId;
    }

    /**
     * @param tipoMovimientoFinalizacionId the tipoMovimientoFinalizacionId to set
     */
    public void setTipoMovimientoFinalizacionId(final Long tipoMovimientoFinalizacionId) {
        this.tipoMovimientoFinalizacionId = tipoMovimientoFinalizacionId;
    }

    /**
     * @return the sueldoBasico
     */
    public String getSueldoBasico() {
        return sueldoBasico;
    }

    /**
     * @param sueldoBasico the sueldoBasico to set
     */
    public void setSueldoBasico(final String sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    /**
     * @return the unidadOrganizacional
     */
    public String getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(final String unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the conLiquidacion
     */
    public Boolean getConLiquidacion() {
        return conLiquidacion;
    }

    /**
     * @param conLiquidacion the conLiquidacion to set
     */
    public void setConLiquidacion(Boolean conLiquidacion) {
        this.conLiquidacion = conLiquidacion;
    }

    /**
     * @return the areaInformacionLiquidacionSalida
     */
    public Boolean getAreaInformacionLiquidacionSalida() {
        return areaInformacionLiquidacionSalida;
    }

    /**
     * @param areaInformacionLiquidacionSalida the areaInformacionLiquidacionSalida to set
     */
    public void setAreaInformacionLiquidacionSalida(Boolean areaInformacionLiquidacionSalida) {
        this.areaInformacionLiquidacionSalida = areaInformacionLiquidacionSalida;
    }

    /**
     * @return the ingresoPorReclutamiento
     */
    public Boolean getIngresoPorReclutamiento() {
        return ingresoPorReclutamiento;
    }

    /**
     * @param ingresoPorReclutamiento the ingresoPorReclutamiento to set
     */
    public void setIngresoPorReclutamiento(Boolean ingresoPorReclutamiento) {
        this.ingresoPorReclutamiento = ingresoPorReclutamiento;
    }

    /**
     * @return the numeroContratoAnterior
     */
    public String getNumeroContratoAnterior() {
        return numeroContratoAnterior;
    }

    /**
     * @return the fechaInicioContratoAnterior
     */
    public String getFechaInicioContratoAnterior() {
        return fechaInicioContratoAnterior;
    }

    /**
     * @param numeroContratoAnterior the numeroContratoAnterior to set
     */
    public void setNumeroContratoAnterior(final String numeroContratoAnterior) {
        this.numeroContratoAnterior = numeroContratoAnterior;
    }

    /**
     * @param fechaInicioContratoAnterior the fechaInicioContratoAnterior to set
     */
    public void setFechaInicioContratoAnterior(final String fechaInicioContratoAnterior) {
        this.fechaInicioContratoAnterior = fechaInicioContratoAnterior;
    }

    /**
     * @return the valorMulta
     */
    public String getValorMulta() {
        return valorMulta;
    }

    /**
     * @param valorMulta the valorMulta to set
     */
    public void setValorMulta(final String valorMulta) {
        this.valorMulta = valorMulta;
    }

    /**
     * @return the tipoRotacion
     */
    public String getTipoRotacion() {
        return tipoRotacion;
    }

    /**
     * @param tipoRotacion the tipoRotacion to set
     */
    public void setTipoRotacion(final String tipoRotacion) {
        this.tipoRotacion = tipoRotacion;
    }

    /**
     * @return the codigoPuesto
     */
    public String getCodigoPuesto() {
        return codigoPuesto;
    }

    /**
     * @param codigoPuesto the codigoPuesto to set
     */
    public void setCodigoPuesto(final String codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    /**
     * @return the areaAdendum
     */
    public Boolean getAreaAdendum() {
        return areaAdendum;
    }

    /**
     * @return the antecedentes
     */
    public String getAntecedentes() {
        return antecedentes;
    }

    /**
     * @param areaAdendum the areaAdendum to set
     */
    public void setAreaAdendum(final Boolean areaAdendum) {
        this.areaAdendum = areaAdendum;
    }

    /**
     * @param antecedentes the antecedentes to set
     */
    public void setAntecedentes(final String antecedentes) {
        this.antecedentes = antecedentes;
    }

    /**
     * @return the areaTerminacionContrato
     */
    public Boolean getAreaTerminacionContrato() {
        return areaTerminacionContrato;
    }

    /**
     * @param areaTerminacionContrato the areaTerminacionContrato to set
     */
    public void setAreaTerminacionContrato(final Boolean areaTerminacionContrato) {
        this.areaTerminacionContrato = areaTerminacionContrato;
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
     * @return the publicado
     */
    public Boolean getPublicado() {
        return publicado;
    }

    /**
     * @param publicado the publicado to set
     */
    public void setPublicado(Boolean publicado) {
        this.publicado = publicado;
    }

    /**
     * @return the fechaPublicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * @return the fechaDespublicacion
     */
    public Date getFechaDespublicacion() {
        return fechaDespublicacion;
    }

    /**
     * @param fechaDespublicacion the fechaDespublicacion to set
     */
    public void setFechaDespublicacion(Date fechaDespublicacion) {
        this.fechaDespublicacion = fechaDespublicacion;
    }

    /**
     * @return the usuarioPublicacion
     */
    public String getUsuarioPublicacion() {
        return usuarioPublicacion;
    }

    /**
     * @param usuarioPublicacion the usuarioPublicacion to set
     */
    public void setUsuarioPublicacion(String usuarioPublicacion) {
        this.usuarioPublicacion = usuarioPublicacion;
    }

    /**
     * @return the usuarioDespublicacion
     */
    public String getUsuarioDespublicacion() {
        return usuarioDespublicacion;
    }

    /**
     * @param usuarioDespublicacion the usuarioDespublicacion to set
     */
    public void setUsuarioDespublicacion(String usuarioDespublicacion) {
        this.usuarioDespublicacion = usuarioDespublicacion;
    }

    /**
     * @return the cargarDocumentoHabilitanteObligatorio
     */
    public Boolean getCargarDocumentoHabilitanteObligatorio() {
        return cargarDocumentoHabilitanteObligatorio;
    }

    /**
     * @param cargarDocumentoHabilitanteObligatorio the cargarDocumentoHabilitanteObligatorio to set
     */
    public void setCargarDocumentoHabilitanteObligatorio(Boolean cargarDocumentoHabilitanteObligatorio) {
        this.cargarDocumentoHabilitanteObligatorio = cargarDocumentoHabilitanteObligatorio;
    }

    /**
     * @return the tipoComisionServicio
     */
    public String getTipoComisionServicio() {
        return tipoComisionServicio;
    }

    /**
     * @param tipoComisionServicio the tipoComisionServicio to set
     */
    public void setTipoComisionServicio(String tipoComisionServicio) {
        this.tipoComisionServicio = tipoComisionServicio;
    }

    /**
     * @return the renovacionComisionServicio
     */
    public Boolean getRenovacionComisionServicio() {
        return renovacionComisionServicio;
    }

    /**
     * @param renovacionComisionServicio the renovacionComisionServicio to set
     */
    public void setRenovacionComisionServicio(Boolean renovacionComisionServicio) {
        this.renovacionComisionServicio = renovacionComisionServicio;
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
