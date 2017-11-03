/*
 *  Servidor.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Reclutamiento
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "reclutamientos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_NUMERO_IDENTIFICACION, query = "SELECT c FROM Reclutamiento c where c.numeroIdentificacion=?1 and c.vigente=true"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_NOMBRE_SERVIDOR, query = "SELECT c FROM Reclutamiento c where c.apellidoNombre like ?1 and c.vigente=true"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR, query = "SELECT c FROM Reclutamiento c where c.apellidoNombre like ?1 and c.numeroIdentificacion=?2 and c.vigente=true"),
    @NamedQuery(name = Reclutamiento.BUSCAR_VIGENTES, query = "SELECT c FROM Reclutamiento c where c.vigente=true order by c.nombres"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_IDENTIFICACION, query = "SELECT o FROM Reclutamiento o WHERE o.vigente=true AND o.tipoIdentificacion=?1 AND o.numeroIdentificacion=?2"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_IDENTIFICACION_YESTADO, query = "SELECT o FROM Reclutamiento o WHERE o.vigente=true AND o.tipoIdentificacion=?1 AND o.numeroIdentificacion=?2 and o.estado=?3"),
    @NamedQuery(name = Reclutamiento.BUSCAR_DESDE_MOVIM_PERSONAL, query = "SELECT c FROM Reclutamiento c where c.tipoIdentificacion like ?1 and c.numeroIdentificacion=?2 and c.vigente=true and c.modalidadLaboralId =?3 and c.escalaOcupacionalId =?4 and c.unidadOrganizacionalId =?5 and c.institucionId=?6 and c.estado=?7"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_ESCALA_MODALIDAD_UNIDAD, query = "SELECT c FROM Reclutamiento c where c.vigente=true and c.escalaOcupacionalId =?1 and c.modalidadLaboralId =?2 and c.unidadOrganizacionalId =?3 "),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_DISTRIBUTIVO, query = "SELECT o FROM Reclutamiento o WHERE o.vigente=true AND o.distributivoDetalle.id=?1"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_DISTRIBUTIVO_Y_ESTADO, query = "SELECT o FROM Reclutamiento o WHERE o.vigente=true AND o.distributivoDetalle.id=?1 and o.estado=?2"),
    @NamedQuery(name = Reclutamiento.BUSCAR_POR_CODIGO_PUESTO, query = "SELECT o FROM Reclutamiento o WHERE o.vigente=true AND o.distributivoDetalle.codigoPuesto=?1"),})
public class Reclutamiento extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por codigo de puesto.
     */
    public static final String BUSCAR_POR_CODIGO_PUESTO = "Reclutamiento.buscarPorCodigoPuesto";

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION = "Reclutamiento.buscarPorNumeroIdentificacion";

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_ESCALA_MODALIDAD_UNIDAD = "Reclutamiento.buscarPorEscalaModalidadUnidad";

    /**
     * Nombre de la consulta para buscar desde Movimientos de Personal. La
     * búsqueda se hace por: modalidad laboral,escala ocupacional, unidad
     * organizacional, institucion,tipo de doc.,numero de doc.,estado = R
     */
    public static final String BUSCAR_DESDE_MOVIM_PERSONAL = "Reclutamiento.buscarDesdeMovPersonal";

    /**
     * Consulra que recupera Reclutamiento dado su tipo y numero de
     * identificacion.
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "Reclutamiento.buscarPorIdentificacion";
    /**
     * Consulra que recupera Reclutamiento dado su tipo y numero de
     * identificacion y por estado.
     */
    public static final String BUSCAR_POR_IDENTIFICACION_YESTADO = "Reclutamiento.buscarPorIdentificacionYEstado";

    /**
     * Consulra que recupera Reclutamiento dado su tipo y numero de
     * identificacion.
     */
    public static final String BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR
            = "Reclutamiento.buscarPorNombreYIdentificacionServidor";

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "Reclutamiento.buscarVigentes";

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NOMBRE_SERVIDOR = "Reclutamiento.buscarPorNombreServidor";

    /**
     *
     */
    public static final String BUSCAR_POR_DISTRIBUTIVO = "Reclutamiento.buscarPorDistributivo";
    /**
     *
     */
    public static final String BUSCAR_POR_DISTRIBUTIVO_Y_ESTADO = "Reclutamiento.buscarPorDistributivoYEstado";

    public Reclutamiento(final Long id) {
        super();
        this.id = id;

    }

    /**
     * Constructor por defecto.
     */
    public Reclutamiento() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * institucion
     */
    @JoinColumn(name = "institucion_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Institucion institucion;

    /**
     * institucion id.
     */
    @Column(name = "institucion_id")
    private Long institucionId;

    /**
     * escalaOcupacional
     */
    @JoinColumn(name = "escala_ocupacional_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EscalaOcupacional escalaOcupacional;

    /**
     * escalaOcupacionalId id.
     */
    @Column(name = "escala_ocupacional_id")
    private Long escalaOcupacionalId;

    /**
     * modalidadLaboral
     */
    @JoinColumn(name = "modalidad_laboral_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ModalidadLaboral modalidadLaboral;

    /**
     * modalidadLaboral id.
     */
    @Column(name = "modalidad_laboral_id")
    private Long modalidadLaboralId;

    /**
     * institucion
     */
    @JoinColumn(name = "unidad_organizacional_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * institucion id.
     */
    @Column(name = "unidad_organizacional_id")
    private Long unidadOrganizacionalId;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    @Column(name = "apellido_paterno")
    private String apellidosPaterno;

    @Column(name = "apellido_materno")
    private String apellidosMaterno;

    @Column(name = "apellidos_nombres")
    private String apellidoNombre;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "numero_carnet_conadis")
    private String numeroCarnetConadis;

    @Column(name = "persona_con_discapacidad")
    private boolean personaConDiscapacidad;

    @Column(name = "calle_principal")
    private String callePrincipal;

    @Column(name = "calle_secundaria")
    private String calleSecundaria;

    @Column(name = "referencia_domicilio")
    private String referenciaDomicilio;

    @Column(name = "numero_domicilio")
    private String numeroDomicilio;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @Column(name = "correo")
    private String mail;

    @Column(name = "em_movimento_personal")
    private boolean emMovimientoPersonal;

    @Column(name = "porcentaje_discapacidad")
    private BigDecimal porcentajeDiscapacidad;

    /**
     * Campo que indica el estado del reclutamiento: EL estado puede ser
     * <R>egistrado <D>itributivo.
     */
    @Column(name = "estado")
    @NotNull
    @Basic(optional = false)
    private String estado;

    @Transient
    private String apellidosNombres;

    @JoinColumn(name = "catalogo_genero_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoGenero;

    @Column(name = "catalogo_genero_id")
    private Long catalogoGeneroId;

    @JoinColumn(name = "catalogo_tipo_sangre_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoTipoSangre;

    @Column(name = "catalogo_tipo_sangre_id")
    private Long catalogoTipoSangreId;

    @JoinColumn(name = "catalogo_estado_civil_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoEstadoCivil;

    @Column(name = "catalogo_estado_civil_id")
    private Long catalogoEstadoCivilId;

    @JoinColumn(name = "catalogo_etnia_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoEtnia;

    @Column(name = "catalogo_etnia_id")
    private Long catalogoEtniaId;

    @JoinColumn(name = "catalogo_talla_uniforme_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoTallaUniforme;

    @Column(name = "catalogo_talla_uniforme_id")
    private Long catalogoTallaUniformeId;

    @JoinColumn(name = "catalogo_numero_calzado_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoNumeroCalzado;

    @Column(name = "catalogo_numero_calzado_id")
    private Long catalogoNumeroCalzadoId;

    @JoinColumn(name = "catalogo_capacidades_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoCapacidades;

    @Column(name = "catalogo_capacidades_id")
    private Long catalogoCapacidadesId;

    @JoinColumn(name = "catalogo_nacionalidad_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoNacionalidad;

    @Column(name = "catalogo_nacionalidad_id")
    private Long catalogoNacionalidadId;

    @JoinColumn(name = "ubicacion_geografica_canton_nacimiento_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeograficaCantonNacimiento;

    @Column(name = "ubicacion_geografica_canton_nacimiento_id")
    private Long ubicacionGeograficaCantonNacimientoId;

    /**
     * Campo que indica la hora que inicia la jornada laboral para el servidor.
     */
    @Column(name = "hora_entrada")
    @Temporal(value = TemporalType.TIME)
    @NotNull
    @Basic(optional = false)
    private Date horaEntrada;

    /**
     * Campo que indica el número de horas que tiene la jornada laboral.
     */
    @Min(0)
    @Max(8)
    @Column(name = "duracion_jornada")
    private Integer jornada;

    @Column(name = "elaborado_por")
    private String elaboradoPor;

    @Column(name = "aprobado_por")
    private String aprobadoPor;

    @Column(name = "cargo_elaborado_por")
    private String cargoElaboradoPor;

    @Column(name = "cargo_aprobado_por")
    private String cargoAprobadoPor;

    @Column(name = "criterio_tecnico")
    private String criterioTecnico;

    @Column(name = "recomendaciones")
    private String recomendaciones;

    /**
     * Detalle del distributivo.
     */
    @JoinColumn(name = "distributivo_detalle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalle;

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
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(final String mail) {
        this.mail = mail;
    }

    /**
     * @return the numeroCarnetConadis
     */
    public String getNumeroCarnetConadis() {
        return numeroCarnetConadis;
    }

    /**
     * @param numeroCarnetConadis the numeroCarnetConadis to set
     */
    public void setNumeroCarnetConadis(final String numeroCarnetConadis) {
        this.numeroCarnetConadis = numeroCarnetConadis;
    }

    /**
     * @return the referenciaDomicilio
     */
    public String getReferenciaDomicilio() {
        return referenciaDomicilio;
    }

    /**
     * @param referenciaDomicilio the referenciaDomicilio to set
     */
    public void setReferenciaDomicilio(final String referenciaDomicilio) {
        this.referenciaDomicilio = referenciaDomicilio;
    }

    /**
     * @return the numeroDomicilio
     */
    public String getNumeroDomicilio() {
        return numeroDomicilio;
    }

    /**
     * @param numeroDomicilio the numeroDomicilio to set
     */
    public void setNumeroDomicilio(final String numeroDomicilio) {
        this.numeroDomicilio = numeroDomicilio;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public String getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(final String celular) {
        this.celular = celular;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the institucionId
     */
    public Long getInstitucionId() {
        return institucionId;
    }

    /**
     * @param institucionId the institucionId to set
     */
    public void setInstitucionId(final Long institucionId) {
        this.institucionId = institucionId;
    }

    /**
     * @return the escalaOcupacional
     */
    public EscalaOcupacional getEscalaOcupacional() {
        return escalaOcupacional;
    }

    /**
     * @param escalaOcupacional the escalaOcupacional to set
     */
    public void setEscalaOcupacional(final EscalaOcupacional escalaOcupacional) {
        this.escalaOcupacional = escalaOcupacional;
    }

    /**
     * @return the escalaOcupacionalId
     */
    public Long getEscalaOcupacionalId() {
        return escalaOcupacionalId;
    }

    /**
     * @param escalaOcupacionalId the escalaOcupacionalId to set
     */
    public void setEscalaOcupacionalId(final Long escalaOcupacionalId) {
        this.escalaOcupacionalId = escalaOcupacionalId;
    }

    /**
     * @return the modalidadLaboral
     */
    public ModalidadLaboral getModalidadLaboral() {
        return modalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setModalidadLaboral(final ModalidadLaboral modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the modalidadLaboralId
     */
    public Long getModalidadLaboralId() {
        return modalidadLaboralId;
    }

    /**
     * @param modalidadLaboralId the modalidadLaboralId to set
     */
    public void setModalidadLaboralId(final Long modalidadLaboralId) {
        this.modalidadLaboralId = modalidadLaboralId;
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
    public void setUnidadOrganizacional(final UnidadOrganizacional unidadOrganizacional) {
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
    public void setUnidadOrganizacionalId(final Long unidadOrganizacionalId) {
        this.unidadOrganizacionalId = unidadOrganizacionalId;
    }

    /**
     * @return the apellidosPaterno
     */
    public String getApellidosPaterno() {
        return apellidosPaterno;
    }

    /**
     * @param apellidosPaterno the apellidosPaterno to set
     */
    public void setApellidosPaterno(final String apellidosPaterno) {
        this.apellidosPaterno = apellidosPaterno;
    }

    /**
     * @return the apellidosMaterno
     */
    public String getApellidosMaterno() {
        return apellidosMaterno;
    }

    /**
     * @param apellidosMaterno the apellidosMaterno to set
     */
    public void setApellidosMaterno(final String apellidosMaterno) {
        this.apellidosMaterno = apellidosMaterno;
    }

    /**
     * @return the callePrincipal
     */
    public String getCallePrincipal() {
        return callePrincipal;
    }

    /**
     * @param callePrincipal the callePrincipal to set
     */
    public void setCallePrincipal(final String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    /**
     * @return the calleSecundaria
     */
    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    /**
     * @param calleSecundaria the calleSecundaria to set
     */
    public void setCalleSecundaria(final String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    /**
     * @return the emMovimientoPersonal
     */
    public boolean isEmMovimientoPersonal() {
        return emMovimientoPersonal;
    }

    /**
     * @param emMovimientoPersonal the emMovimientoPersonal to set
     */
    public void setEmMovimientoPersonal(final boolean emMovimientoPersonal) {
        this.emMovimientoPersonal = emMovimientoPersonal;
    }

    /**
     * @return the personaConDiscapacidad
     */
    public boolean isPersonaConDiscapacidad() {
        return personaConDiscapacidad;
    }

    /**
     * @param personaConDiscapacidad the personaConDiscapacidad to set
     */
    public void setPersonaConDiscapacidad(final boolean personaConDiscapacidad) {
        this.personaConDiscapacidad = personaConDiscapacidad;
    }

    /**
     * @return the porcentajeDiscapacidad
     */
    public BigDecimal getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    /**
     * @param porcentajeDiscapacidad the porcentajeDiscapacidad to set
     */
    public void setPorcentajeDiscapacidad(final BigDecimal porcentajeDiscapacidad) {
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
    }

    /**
     * @return the catalogoGenero
     */
    public Catalogo getCatalogoGenero() {
        return catalogoGenero;
    }

    /**
     * @param catalogoGenero the catalogoGenero to set
     */
    public void setCatalogoGenero(final Catalogo catalogoGenero) {
        this.catalogoGenero = catalogoGenero;
    }

    /**
     * @return the catalogoGeneroId
     */
    public Long getCatalogoGeneroId() {
        return catalogoGeneroId;
    }

    /**
     * @param catalogoGeneroId the catalogoGeneroId to set
     */
    public void setCatalogoGeneroId(final Long catalogoGeneroId) {
        this.catalogoGeneroId = catalogoGeneroId;
    }

    /**
     * @return the catalogoTipoSangre
     */
    public Catalogo getCatalogoTipoSangre() {
        return catalogoTipoSangre;
    }

    /**
     * @param catalogoTipoSangre the catalogoTipoSangre to set
     */
    public void setCatalogoTipoSangre(final Catalogo catalogoTipoSangre) {
        this.catalogoTipoSangre = catalogoTipoSangre;
    }

    /**
     * @return the catalogoTipoSangreId
     */
    public Long getCatalogoTipoSangreId() {
        return catalogoTipoSangreId;
    }

    /**
     * @param catalogoTipoSangreId the catalogoTipoSangreId to set
     */
    public void setCatalogoTipoSangreId(final Long catalogoTipoSangreId) {
        this.catalogoTipoSangreId = catalogoTipoSangreId;
    }

    /**
     * @return the catalogoEstadoCivil
     */
    public Catalogo getCatalogoEstadoCivil() {
        return catalogoEstadoCivil;
    }

    /**
     * @param catalogoEstadoCivil the catalogoEstadoCivil to set
     */
    public void setCatalogoEstadoCivil(final Catalogo catalogoEstadoCivil) {
        this.catalogoEstadoCivil = catalogoEstadoCivil;
    }

    /**
     * @return the catalogoEstadoCivilId
     */
    public Long getCatalogoEstadoCivilId() {
        return catalogoEstadoCivilId;
    }

    /**
     * @param catalogoEstadoCivilId the catalogoEstadoCivilId to set
     */
    public void setCatalogoEstadoCivilId(final Long catalogoEstadoCivilId) {
        this.catalogoEstadoCivilId = catalogoEstadoCivilId;
    }

    /**
     * @return the catalogoEtnia
     */
    public Catalogo getCatalogoEtnia() {
        return catalogoEtnia;
    }

    /**
     * @param catalogoEtnia the catalogoEtnia to set
     */
    public void setCatalogoEtnia(final Catalogo catalogoEtnia) {
        this.catalogoEtnia = catalogoEtnia;
    }

    /**
     * @return the catalogoEtniaId
     */
    public Long getCatalogoEtniaId() {
        return catalogoEtniaId;
    }

    /**
     * @param catalogoEtniaId the catalogoEtniaId to set
     */
    public void setCatalogoEtniaId(final Long catalogoEtniaId) {
        this.catalogoEtniaId = catalogoEtniaId;
    }

    /**
     * @return the catalogoTallaUniforme
     */
    public Catalogo getCatalogoTallaUniforme() {
        return catalogoTallaUniforme;
    }

    /**
     * @param catalogoTallaUniforme the catalogoTallaUniforme to set
     */
    public void setCatalogoTallaUniforme(final Catalogo catalogoTallaUniforme) {
        this.catalogoTallaUniforme = catalogoTallaUniforme;
    }

    /**
     * @return the catalogoTallaUniformeId
     */
    public Long getCatalogoTallaUniformeId() {
        return catalogoTallaUniformeId;
    }

    /**
     * @param catalogoTallaUniformeId the catalogoTallaUniformeId to set
     */
    public void setCatalogoTallaUniformeId(final Long catalogoTallaUniformeId) {
        this.catalogoTallaUniformeId = catalogoTallaUniformeId;
    }

    /**
     * @return the catalogoNumeroCalzado
     */
    public Catalogo getCatalogoNumeroCalzado() {
        return catalogoNumeroCalzado;
    }

    /**
     * @param catalogoNumeroCalzado the catalogoNumeroCalzado to set
     */
    public void setCatalogoNumeroCalzado(final Catalogo catalogoNumeroCalzado) {
        this.catalogoNumeroCalzado = catalogoNumeroCalzado;
    }

    /**
     * @return the catalogoNumeroCalzadoId
     */
    public Long getCatalogoNumeroCalzadoId() {
        return catalogoNumeroCalzadoId;
    }

    /**
     * @param catalogoNumeroCalzadoId the catalogoNumeroCalzadoId to set
     */
    public void setCatalogoNumeroCalzadoId(final Long catalogoNumeroCalzadoId) {
        this.catalogoNumeroCalzadoId = catalogoNumeroCalzadoId;
    }

    /**
     * @return the catalogoCapacidades
     */
    public Catalogo getCatalogoCapacidades() {
        return catalogoCapacidades;
    }

    /**
     * @param catalogoCapacidades the catalogoCapacidades to set
     */
    public void setCatalogoCapacidades(final Catalogo catalogoCapacidades) {
        this.catalogoCapacidades = catalogoCapacidades;
    }

    /**
     * @return the catalogoCapacidadesId
     */
    public Long getCatalogoCapacidadesId() {
        return catalogoCapacidadesId;
    }

    /**
     * @param catalogoCapacidadesId the catalogoCapacidadesId to set
     */
    public void setCatalogoCapacidadesId(final Long catalogoCapacidadesId) {
        this.catalogoCapacidadesId = catalogoCapacidadesId;
    }

    /**
     * @return the catalogoNacionalidad
     */
    public Catalogo getCatalogoNacionalidad() {
        return catalogoNacionalidad;
    }

    /**
     * @param catalogoNacionalidad the catalogoNacionalidad to set
     */
    public void setCatalogoNacionalidad(final Catalogo catalogoNacionalidad) {
        this.catalogoNacionalidad = catalogoNacionalidad;
    }

    /**
     * @return the catalogoNacionalidadId
     */
    public Long getCatalogoNacionalidadId() {
        return catalogoNacionalidadId;
    }

    /**
     * @param catalogoNacionalidadId the catalogoNacionalidadId to set
     */
    public void setCatalogoNacionalidadId(final Long catalogoNacionalidadId) {
        this.catalogoNacionalidadId = catalogoNacionalidadId;
    }

    /**
     * @return the ubicacionGeograficaCantonNacimiento
     */
    public UbicacionGeografica getUbicacionGeograficaCantonNacimiento() {
        return ubicacionGeograficaCantonNacimiento;
    }

    /**
     * @param ubicacionGeograficaCantonNacimiento the
     * ubicacionGeograficaCantonNacimiento to set
     */
    public void setUbicacionGeograficaCantonNacimiento(final UbicacionGeografica ubicacionGeograficaCantonNacimiento) {
        this.ubicacionGeograficaCantonNacimiento = ubicacionGeograficaCantonNacimiento;
    }

    /**
     * @return the ubicacionGeograficaCantonNacimientoId
     */
    public Long getUbicacionGeograficaCantonNacimientoId() {
        return ubicacionGeograficaCantonNacimientoId;
    }

    /**
     * @param ubicacionGeograficaCantonNacimientoId the
     * ubicacionGeograficaCantonNacimientoId to set
     */
    public void setUbicacionGeograficaCantonNacimientoId(final Long ubicacionGeograficaCantonNacimientoId) {
        this.ubicacionGeograficaCantonNacimientoId = ubicacionGeograficaCantonNacimientoId;
    }

    /**
     * @return the horaEntrada
     */
    public Date getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * @param horaEntrada the horaEntrada to set
     */
    public void setHoraEntrada(final Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * @return the jornada
     */
    public Integer getJornada() {
        return jornada;
    }

    /**
     * @param jornada the jornada to set
     */
    public void setJornada(final Integer jornada) {
        this.jornada = jornada;
    }

    /**
     * @return the elaboradoPor
     */
    public String getElaboradoPor() {
        return elaboradoPor;
    }

    /**
     * @param elaboradoPor the elaboradoPor to set
     */
    public void setElaboradoPor(final String elaboradoPor) {
        this.elaboradoPor = elaboradoPor;
    }

    /**
     * @return the aprobadoPor
     */
    public String getAprobadoPor() {
        return aprobadoPor;
    }

    /**
     * @param aprobadoPor the aprobadoPor to set
     */
    public void setAprobadoPor(final String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    /**
     * @return the cargoElaboradoPor
     */
    public String getCargoElaboradoPor() {
        return cargoElaboradoPor;
    }

    /**
     * @param cargoElaboradoPor the cargoElaboradoPor to set
     */
    public void setCargoElaboradoPor(final String cargoElaboradoPor) {
        this.cargoElaboradoPor = cargoElaboradoPor;
    }

    /**
     * @return the cargoAprobadoPor
     */
    public String getCargoAprobadoPor() {
        return cargoAprobadoPor;
    }

    /**
     * @param cargoAprobadoPor the cargoAprobadoPor to set
     */
    public void setCargoAprobadoPor(final String cargoAprobadoPor) {
        this.cargoAprobadoPor = cargoAprobadoPor;
    }

    /**
     * @return the criterioTecnico
     */
    public String getCriterioTecnico() {
        return criterioTecnico;
    }

    /**
     * @param criterioTecnico the criterioTecnico to set
     */
    public void setCriterioTecnico(String criterioTecnico) {
        this.criterioTecnico = criterioTecnico;
    }

    /**
     * @return the recomendaciones
     */
    public String getRecomendaciones() {
        return recomendaciones;
    }

    /**
     * @param recomendaciones the recomendaciones to set
     */
    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
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
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        UtilCadena.concatenar(
                apellidosPaterno == null ? "" : apellidosPaterno,
                " ", apellidosMaterno, " ", nombres);
        return apellidosNombres;
    }

    /**
     * @param apellidosNombres the apellidosNombres to set
     */
    public void setApellidosNombres(final String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
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
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }
}
