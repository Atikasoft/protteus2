package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "ingresos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Ingreso.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Ingreso o where o.vigente=true and o.movimiento.id=?1"),
    @NamedQuery(name = Ingreso.ACTUALIZACION_MASIVA_TRAMITE,
    query = "update Ingreso i set i.antecedentesContrato=?1, i.actividadesContrato=?2 "
    + " where i.movimiento.tramite.id=?3 and i.vigente=true"),
    @NamedQuery(name = Ingreso.BUSCAR_POR_TRAMITE,
    query = "Select o from Ingreso o where o.vigente=true and o.movimiento.vigente=true and o.movimiento.tramite.id=?1")
})
public class Ingreso extends EntidadBasica {

    /**
     * Nombre de consuntal.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Ingreso.buscarPorMovimientoId";

    /**
     *
     */
    public final static String BUSCAR_POR_TRAMITE = "Ingreso.buscarPorTramite";

    /**
     * Nombre de consuntal.
     */
    public final static String ACTUALIZACION_MASIVA_TRAMITE = "Ingreso.actualizacionMasivaTramite";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "fecha_inicio_puesto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPuesto;

    @Column(name = "fecha_fin_puesto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinPuesto;

    @Column(name = "nacionalidad_nombre")
    private String nacionalidadNombre;

    @Column(name = "nacionalidad_id")
    private Long nacionalidadId;

    @Column(name = "nacionalidad_descripcion")
    private String nacionalidadDescripcion;

    @Column(name = "anios_residencia")
    private BigDecimal aniosResidencia;

    @Column(name = "estado_civil")
    private Long estadoCivil;

    @Column(name = "identificacion_etnica")
    private Long identificacionEtnica;

    /**
     * Nombre de la identificacion etnica.
     */
    @Column(name = "identificacion_etnica_nombre")
    private String identificacionEtnicaNombre;

    @Column(name = "estado_civil_nombre")
    private String estadoCivilDescripcion;

    @Column(name = "genero")
    private Long genero;

    @Column(name = "pais")
    private Long pais;

    @Column(name = "region")
    private Long region;

    @Column(name = "provincia")
    private Long provincia;

    @Column(name = "canton")
    private Long canton;

    @Column(name = "parroquia")
    private Long parroquia;

    @Column(name = "motivo_ingreso")
    private Long motivoIngreso;

    /**
     * Domicilio Calle Secundaria.
     */
    @Column(name = "domicilio_calle_secundaria")
    private String domicilioCalleSecudaria;

    /**
     * Domicilio Calle Primaria.
     */
    @Column(name = "domicilio_calle_principal")
    private String domicilioCallePrincipal;

    /**
     * Numero.
     */
    @Column(name = "domicilio_numero")
    private String domicilioNumero;

    /**
     *
     */
    @Column(name = "domicilio_referencia")
    private String domicilioReferencia;

    @Column(name = "fecha_ingreso_institucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoInstitucion;

    @Column(name = "fecha_ingreso_sector_publico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoSectorPublico;

    @Column(name = "servidor_pasante")
    private Boolean servidorPasante;

    @Column(name = "servidor_carrera")
    private Boolean servidorCarrera;

    @Column(name = "numero_carrera")
    private String numeroCarrera;

    @Column(name = "telefono_domicilio")
    private String telefonoDomicilio;

    @Column(name = "telefono_trabajo")
    private String telefonoTrabajo;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "discapacidad")
    private Boolean discapacidad;

    @Column(name = "especifique_discapacidad")
    private String especifiqueDiscapacidad;

    @Column(name = "tipo_discapacidad")
    private String tipoDiscapacidad;

    @Column(name = "porcentaje_discapacidad")
    private Long porcentajeDiscapacidad;

    @Column(name = "nivel_instruccion")
    private Long nivelInstruccion;

    @Column(name = "tipo_periodo_formacion_academica")
    private Long tipoPeriodoFormacionAcademica;

    @Column(name = "pais_formacion_academica")
    private Long paisFormacionAcademica;

    @Column(name = "anios_estudio")
    private Integer aniosEstudio;

    /**
     * Tipo de designacion.
     */
    @Column(name = "tipo_designacion")
    private String tipoDesignacion;

    /**
     * Tiempo de jornada diaria, debe ser mayor a cero hasta 420 minutos.
     */
    @Column(name = "tiempo_jornada_diaria")
    private Integer tiempoJornadaDiaria;

    /**
     * Indica el tipo de temporada, escolar, navidena, vacaciones, otros.
     */
    @Column(name = "tipo_temporada")
    private String tipoTemporada;

    /**
     * Numero de registro del conadis.
     */
    @Column(name = "numero_conadis")
    private String numeroConadis;

    /**
     * Corresponde a .
     */
    @Column(name = "corresponde_discapacidad")
    private String correspondeDiscapacidad;

    /**
     * Corresponde a .
     */
    @Column(name = "justificacion_discapacidad")
    @Size(max = 400)
    private String justificacionDiscapacidad;

    /**
     * Antecentes del contrato.
     */
    @Column(name = "antecedentes_contrato")
    @Size(max = 4000)
    private String antecedentesContrato;

    /**
     * Antecentes del contrato.
     */
    @Column(name = "siglas_titulo_academico")
    private String siglasTituloAcademico;

    /**
     * Activiadades del contrato.
     */
    @Column(name = "actividades_contrato")
    @Size(max = 4000)
    private String actividadesContrato;

    /**
     * Indicar si contrato es una renovacion.
     */
    @Column(name = "renovacion_contrato")
    private Boolean renovacionContrato;

    @JoinColumn(name = "movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public Ingreso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Ingreso(final Long id) {
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
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @return the fechaInicioPuesto
     */
    public Date getFechaInicioPuesto() {
        return fechaInicioPuesto;
    }

    /**
     * @return the fechaFinPuesto
     */
    public Date getFechaFinPuesto() {
        return fechaFinPuesto;
    }

    /**
     * @return the nacionalidadNombre
     */
    public String getNacionalidadNombre() {
        return nacionalidadNombre;
    }

    /**
     * @return the nacionalidadId
     */
    public Long getNacionalidadId() {
        return nacionalidadId;
    }

    /**
     * @return the nacionalidadDescripcion
     */
    public String getNacionalidadDescripcion() {
        return nacionalidadDescripcion;
    }

    /**
     * @return the aniosResidencia
     */
    public BigDecimal getAniosResidencia() {
        return aniosResidencia;
    }

    /**
     * @return the estadoCivil
     */
    public Long getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @return the identificacionEtnica
     */
    public Long getIdentificacionEtnica() {
        return identificacionEtnica;
    }

    /**
     * @return the identificacionEtnicaNombre
     */
    public String getIdentificacionEtnicaNombre() {
        return identificacionEtnicaNombre;
    }

    /**
     * @return the estadoCivilDescripcion
     */
    public String getEstadoCivilDescripcion() {
        return estadoCivilDescripcion;
    }

    /**
     * @return the genero
     */
    public Long getGenero() {
        return genero;
    }

    /**
     * @return the pais
     */
    public Long getPais() {
        return pais;
    }

    /**
     * @return the region
     */
    public Long getRegion() {
        return region;
    }

    /**
     * @return the provincia
     */
    public Long getProvincia() {
        return provincia;
    }

    /**
     * @return the canton
     */
    public Long getCanton() {
        return canton;
    }

    /**
     * @return the parroquia
     */
    public Long getParroquia() {
        return parroquia;
    }

    /**
     * @return the motivoIngreso
     */
    public Long getMotivoIngreso() {
        return motivoIngreso;
    }

    /**
     * @return the domicilioCalleSecudaria
     */
    public String getDomicilioCalleSecudaria() {
        return domicilioCalleSecudaria;
    }

    /**
     * @return the domicilioCallePrincipal
     */
    public String getDomicilioCallePrincipal() {
        return domicilioCallePrincipal;
    }

    /**
     * @return the domicilioNumero
     */
    public String getDomicilioNumero() {
        return domicilioNumero;
    }

    /**
     * @return the domicilioReferencia
     */
    public String getDomicilioReferencia() {
        return domicilioReferencia;
    }

    /**
     * @return the fechaIngresoInstitucion
     */
    public Date getFechaIngresoInstitucion() {
        return fechaIngresoInstitucion;
    }

    /**
     * @return the fechaIngresoSectorPublico
     */
    public Date getFechaIngresoSectorPublico() {
        return fechaIngresoSectorPublico;
    }

    /**
     * @return the servidorPasante
     */
    public Boolean getServidorPasante() {
        return servidorPasante;
    }

    /**
     * @return the servidorCarrera
     */
    public Boolean getServidorCarrera() {
        return servidorCarrera;
    }

    /**
     * @return the numeroCarrera
     */
    public String getNumeroCarrera() {
        return numeroCarrera;
    }

    /**
     * @return the telefonoDomicilio
     */
    public String getTelefonoDomicilio() {
        return telefonoDomicilio;
    }

    /**
     * @return the telefonoTrabajo
     */
    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @return the discapacidad
     */
    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    /**
     * @return the especifiqueDiscapacidad
     */
    public String getEspecifiqueDiscapacidad() {
        return especifiqueDiscapacidad;
    }

    /**
     * @return the tipoDiscapacidad
     */
    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    /**
     * @return the porcentajeDiscapacidad
     */
    public Long getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    /**
     * @return the nivelInstruccion
     */
    public Long getNivelInstruccion() {
        return nivelInstruccion;
    }

    /**
     * @return the tipoPeriodoFormacionAcademica
     */
    public Long getTipoPeriodoFormacionAcademica() {
        return tipoPeriodoFormacionAcademica;
    }

    /**
     * @return the paisFormacionAcademica
     */
    public Long getPaisFormacionAcademica() {
        return paisFormacionAcademica;
    }

    /**
     * @return the aniosEstudio
     */
    public Integer getAniosEstudio() {
        return aniosEstudio;
    }

    /**
     * @return the tipoDesignacion
     */
    public String getTipoDesignacion() {
        return tipoDesignacion;
    }

    /**
     * @return the tiempoJornadaDiaria
     */
    public Integer getTiempoJornadaDiaria() {
        return tiempoJornadaDiaria;
    }

    /**
     * @return the tipoTemporada
     */
    public String getTipoTemporada() {
        return tipoTemporada;
    }

    /**
     * @return the numeroConadis
     */
    public String getNumeroConadis() {
        return numeroConadis;
    }

    /**
     * @return the correspondeDiscapacidad
     */
    public String getCorrespondeDiscapacidad() {
        return correspondeDiscapacidad;
    }

    /**
     * @return the justificacionDiscapacidad
     */
    public String getJustificacionDiscapacidad() {
        return justificacionDiscapacidad;
    }

    /**
     * @return the antecedentesContrato
     */
    public String getAntecedentesContrato() {
        return antecedentesContrato;
    }

    /**
     * @return the siglasTituloAcademico
     */
    public String getSiglasTituloAcademico() {
        return siglasTituloAcademico;
    }

    /**
     * @return the actividadesContrato
     */
    public String getActividadesContrato() {
        return actividadesContrato;
    }

    /**
     * @return the renovacionContrato
     */
    public Boolean getRenovacionContrato() {
        return renovacionContrato;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(final Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(final Integer edad) {
        this.edad = edad;
    }

    /**
     * @param fechaInicioPuesto the fechaInicioPuesto to set
     */
    public void setFechaInicioPuesto(final Date fechaInicioPuesto) {
        this.fechaInicioPuesto = fechaInicioPuesto;
    }

    /**
     * @param fechaFinPuesto the fechaFinPuesto to set
     */
    public void setFechaFinPuesto(final Date fechaFinPuesto) {
        this.fechaFinPuesto = fechaFinPuesto;
    }

    /**
     * @param nacionalidadNombre the nacionalidadNombre to set
     */
    public void setNacionalidadNombre(final String nacionalidadNombre) {
        this.nacionalidadNombre = nacionalidadNombre;
    }

    /**
     * @param nacionalidadId the nacionalidadId to set
     */
    public void setNacionalidadId(final Long nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }

    /**
     * @param nacionalidadDescripcion the nacionalidadDescripcion to set
     */
    public void setNacionalidadDescripcion(final String nacionalidadDescripcion) {
        this.nacionalidadDescripcion = nacionalidadDescripcion;
    }

    /**
     * @param aniosResidencia the aniosResidencia to set
     */
    public void setAniosResidencia(BigDecimal aniosResidencia) {
        this.aniosResidencia = aniosResidencia;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(final Long estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @param identificacionEtnica the identificacionEtnica to set
     */
    public void setIdentificacionEtnica(final Long identificacionEtnica) {
        this.identificacionEtnica = identificacionEtnica;
    }

    /**
     * @param identificacionEtnicaNombre the identificacionEtnicaNombre to set
     */
    public void setIdentificacionEtnicaNombre(final String identificacionEtnicaNombre) {
        this.identificacionEtnicaNombre = identificacionEtnicaNombre;
    }

    /**
     * @param estadoCivilDescripcion the estadoCivilDescripcion to set
     */
    public void setEstadoCivilDescripcion(final String estadoCivilDescripcion) {
        this.estadoCivilDescripcion = estadoCivilDescripcion;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(final Long genero) {
        this.genero = genero;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(final Long pais) {
        this.pais = pais;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(final Long region) {
        this.region = region;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(final Long provincia) {
        this.provincia = provincia;
    }

    /**
     * @param canton the canton to set
     */
    public void setCanton(final Long canton) {
        this.canton = canton;
    }

    /**
     * @param parroquia the parroquia to set
     */
    public void setParroquia(final Long parroquia) {
        this.parroquia = parroquia;
    }

    /**
     * @param motivoIngreso the motivoIngreso to set
     */
    public void setMotivoIngreso(final Long motivoIngreso) {
        this.motivoIngreso = motivoIngreso;
    }

    /**
     * @param domicilioCalleSecudaria the domicilioCalleSecudaria to set
     */
    public void setDomicilioCalleSecudaria(final String domicilioCalleSecudaria) {
        this.domicilioCalleSecudaria = domicilioCalleSecudaria;
    }

    /**
     * @param domicilioCallePrincipal the domicilioCallePrincipal to set
     */
    public void setDomicilioCallePrincipal(final String domicilioCallePrincipal) {
        this.domicilioCallePrincipal = domicilioCallePrincipal;
    }

    /**
     * @param domicilioNumero the domicilioNumero to set
     */
    public void setDomicilioNumero(final String domicilioNumero) {
        this.domicilioNumero = domicilioNumero;
    }

    /**
     * @param domicilioReferencia the domicilioReferencia to set
     */
    public void setDomicilioReferencia(final String domicilioReferencia) {
        this.domicilioReferencia = domicilioReferencia;
    }

    /**
     * @param fechaIngresoInstitucion the fechaIngresoInstitucion to set
     */
    public void setFechaIngresoInstitucion(final Date fechaIngresoInstitucion) {
        this.fechaIngresoInstitucion = fechaIngresoInstitucion;
    }

    /**
     * @param fechaIngresoSectorPublico the fechaIngresoSectorPublico to set
     */
    public void setFechaIngresoSectorPublico(final Date fechaIngresoSectorPublico) {
        this.fechaIngresoSectorPublico = fechaIngresoSectorPublico;
    }

    /**
     * @param servidorPasante the servidorPasante to set
     */
    public void setServidorPasante(final Boolean servidorPasante) {
        this.servidorPasante = servidorPasante;
    }

    /**
     * @param servidorCarrera the servidorCarrera to set
     */
    public void setServidorCarrera(final Boolean servidorCarrera) {
        this.servidorCarrera = servidorCarrera;
    }

    /**
     * @param numeroCarrera the numeroCarrera to set
     */
    public void setNumeroCarrera(final String numeroCarrera) {
        this.numeroCarrera = numeroCarrera;
    }

    /**
     * @param telefonoDomicilio the telefonoDomicilio to set
     */
    public void setTelefonoDomicilio(final String telefonoDomicilio) {
        this.telefonoDomicilio = telefonoDomicilio;
    }

    /**
     * @param telefonoTrabajo the telefonoTrabajo to set
     */
    public void setTelefonoTrabajo(final String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(final String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @param discapacidad the discapacidad to set
     */
    public void setDiscapacidad(final Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    /**
     * @param especifiqueDiscapacidad the especifiqueDiscapacidad to set
     */
    public void setEspecifiqueDiscapacidad(final String especifiqueDiscapacidad) {
        this.especifiqueDiscapacidad = especifiqueDiscapacidad;
    }

    /**
     * @param tipoDiscapacidad the tipoDiscapacidad to set
     */
    public void setTipoDiscapacidad(final String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    /**
     * @param porcentajeDiscapacidad the porcentajeDiscapacidad to set
     */
    public void setPorcentajeDiscapacidad(final Long porcentajeDiscapacidad) {
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
    }

    /**
     * @param nivelInstruccion the nivelInstruccion to set
     */
    public void setNivelInstruccion(final Long nivelInstruccion) {
        this.nivelInstruccion = nivelInstruccion;
    }

    /**
     * @param tipoPeriodoFormacionAcademica the tipoPeriodoFormacionAcademica to set
     */
    public void setTipoPeriodoFormacionAcademica(final Long tipoPeriodoFormacionAcademica) {
        this.tipoPeriodoFormacionAcademica = tipoPeriodoFormacionAcademica;
    }

    /**
     * @param paisFormacionAcademica the paisFormacionAcademica to set
     */
    public void setPaisFormacionAcademica(final Long paisFormacionAcademica) {
        this.paisFormacionAcademica = paisFormacionAcademica;
    }

    /**
     * @param aniosEstudio the aniosEstudio to set
     */
    public void setAniosEstudio(final Integer aniosEstudio) {
        this.aniosEstudio = aniosEstudio;
    }

    /**
     * @param tipoDesignacion the tipoDesignacion to set
     */
    public void setTipoDesignacion(final String tipoDesignacion) {
        this.tipoDesignacion = tipoDesignacion;
    }

    /**
     * @param tiempoJornadaDiaria the tiempoJornadaDiaria to set
     */
    public void setTiempoJornadaDiaria(final Integer tiempoJornadaDiaria) {
        this.tiempoJornadaDiaria = tiempoJornadaDiaria;
    }

    /**
     * @param tipoTemporada the tipoTemporada to set
     */
    public void setTipoTemporada(final String tipoTemporada) {
        this.tipoTemporada = tipoTemporada;
    }

    /**
     * @param numeroConadis the numeroConadis to set
     */
    public void setNumeroConadis(final String numeroConadis) {
        this.numeroConadis = numeroConadis;
    }

    /**
     * @param correspondeDiscapacidad the correspondeDiscapacidad to set
     */
    public void setCorrespondeDiscapacidad(final String correspondeDiscapacidad) {
        this.correspondeDiscapacidad = correspondeDiscapacidad;
    }

    /**
     * @param justificacionDiscapacidad the justificacionDiscapacidad to set
     */
    public void setJustificacionDiscapacidad(final String justificacionDiscapacidad) {
        this.justificacionDiscapacidad = justificacionDiscapacidad;
    }

    /**
     * @param antecedentesContrato the antecedentesContrato to set
     */
    public void setAntecedentesContrato(final String antecedentesContrato) {
        this.antecedentesContrato = antecedentesContrato;
    }

    /**
     * @param siglasTituloAcademico the siglasTituloAcademico to set
     */
    public void setSiglasTituloAcademico(final String siglasTituloAcademico) {
        this.siglasTituloAcademico = siglasTituloAcademico;
    }

    /**
     * @param actividadesContrato the actividadesContrato to set
     */
    public void setActividadesContrato(final String actividadesContrato) {
        this.actividadesContrato = actividadesContrato;
    }

    /**
     * @param renovacionContrato the renovacionContrato to set
     */
    public void setRenovacionContrato(final Boolean renovacionContrato) {
        this.renovacionContrato = renovacionContrato;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}
