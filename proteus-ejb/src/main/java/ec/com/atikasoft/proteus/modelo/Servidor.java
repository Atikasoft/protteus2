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
 *  Quito - Ecuadormr
 *  
 *
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.EstadosPersonalEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorParienteInstitucion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Servidor
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@XmlRootElement(name = "servidor")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "servidor", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Servidor.BUSCAR_POR_NUMERO_IDENTIFICACION,
            query = "SELECT c FROM Servidor c where c.numeroIdentificacion like ?1 and c.tipo='I' and c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_POR_USERNAME_CLAVE,
            query = "SELECT c FROM Servidor c where c.numeroIdentificacion =?1 and c.clave=?2 and c.tipo='I' and"
            + "  c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_EXTERNO_POR_USERNAME_CLAVE,
            query = "SELECT c FROM Servidor c where c.numeroIdentificacion =?1 and c.clave=?2 and c.tipo='E' and"
            + " c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_POR_CODIGO,
            query = "SELECT c FROM Servidor c where c.codigoEmpleado = ?1 and c.tipo='I' and c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_POR_APELLIDOS_NOMBRES_SERVIDOR,
            query = "SELECT c FROM Servidor c where c.apellidosNombres like ?1 and c.tipo='I' and c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_CON_PUESTO,
            query = "SELECT c FROM Servidor c where c.apellidosNombres like ?1 and c.tipo='I' and c.vigente=true and"
            + " ( select COUNT(o.id) from DistributivoDetalle o where o.servidor.id = c.id and"
            + " o.vigente = true ) > 0 "),
    @NamedQuery(name = Servidor.BUSCAR_POR_IDENTIFICACION_SERVIDOR_EXTERNO_O_INTERNO_CON_PUESTO,
            query = "SELECT c FROM Servidor c where c.tipoIdentificacion = ?1 and c.numeroIdentificacion = ?2"
            + " and c.vigente=true and"
            + " ( select COUNT(o.id) from DistributivoDetalle o where o.servidor.id = c.id and"
            + " o.vigente = true ) > 0 "),
    @NamedQuery(name = Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO,
            query = "SELECT c FROM Servidor c INNER JOIN c.listaDistributivoDetalle sdd  where "
            + " c.apellidosNombres like ?1 and c.vigente=?2 AND "
            + " sdd.distributivo.idInstitucionEjercicioFiscal=?3 AND sdd.vigente=?4 and c.tipo='I' "),
    @NamedQuery(name = Servidor.BUSCAR_POR_NOMBRE_E_IDENTIFICACION_SERVIDOR_DISTRIBUTIVO,
            query = "SELECT c FROM Servidor c INNER JOIN c.listaDistributivoDetalle sdd  where "
            + " c.apellidosNombres like ?1 and c.numeroIdentificacion like ?2 and c.vigente=?3 AND"
            + " sdd.distributivo.institucionEjercicioFiscal.ejercicioFiscal.id=?4 AND"
            + " sdd.vigente=?5 and c.tipo='I'  "),
    @NamedQuery(name = Servidor.BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO_UNIDAD_ORGANIZACIONAL,
            query = "SELECT c FROM Servidor c INNER JOIN c.listaDistributivoDetalle sdd  where "
            + " c.apellidosNombres like ?1 and c.vigente=?2 AND "
            + " sdd.distributivo.idInstitucionEjercicioFiscal=?3 AND sdd.vigente=?4 AND "
            + " sdd.distributivo.unidadOrganizacional.codigo LIKE ?5 and c.tipo='I' "),
    @NamedQuery(name = Servidor.BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR,
            query = "SELECT c FROM Servidor c where c.apellidosNombres like ?1 and c.numeroIdentificacion like ?2 and"
            + " c.tipo='I' and c.vigente=true"),
    @NamedQuery(name = Servidor.BUSCAR_VIGENTES,
            query = "SELECT c FROM Servidor c where c.vigente=true and c.tipo='I'  order by c.apellidosNombres"),
    @NamedQuery(name = Servidor.BUSCAR_POR_IDENTIFICACION,
            query = "SELECT o FROM Servidor o WHERE o.tipoIdentificacion=?1 AND o.numeroIdentificacion like ?2 and"
            + " o.tipo='I' "),
    @NamedQuery(name = Servidor.BUSCAR_EXTERNO_POR_IDENTIFICACION,
            query = "SELECT o FROM Servidor o WHERE o.tipoIdentificacion=?1 AND o.numeroIdentificacion like ?2 and"
            + " o.tipo='E' "),
    @NamedQuery(name = Servidor.BUSCAR_POR_IDENTIFICACION_DISTRIBUTIVO,
            query = "SELECT c FROM Servidor c INNER JOIN c.listaDistributivoDetalle sdd  where"
            + " c.tipoIdentificacion = ?1 and c.numeroIdentificacion = ?2 and c.vigente=true AND"
            + " sdd.distributivo.institucionEjercicioFiscal.ejercicioFiscal.id=?3 AND sdd.vigente=true and"
            + " c.tipo='I' "),
    @NamedQuery(name = Servidor.BUSCAR_ACTIVOS_POR_HORARIO_ID,
            query = "SELECT c FROM Servidor c INNER JOIN c.listaDistributivoDetalle sdd"
            + " WHERE c.vigente=true AND sdd.vigente=true AND c.horario.id=?1"
            + " AND sdd.estadoPuesto.codigo='EP1' AND c.estadoPersonal.codigo='EPE1'"
            + " AND sdd.distributivo.institucionEjercicioFiscal.ejercicioFiscal.id=?2")
})
@Getter
@Setter
public class Servidor extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION = "Servidor.buscarPorNumeroIdentificacion";

    /**
     * Consulra que recupera servidor dado su tipo y numero de identificacion.
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "Servidor.buscarPorIdentificacion";

    /**
     * Consulra que recupera servidor dado su tipo y numero de identificacion.
     */
    public static final String BUSCAR_EXTERNO_POR_IDENTIFICACION = "Servidor.buscarExternoPorIdentificacion";
    /**
     * Consulra que recupera servidor dado su tipo y numero de identificacion. activo en distributivo
     */
    public static final String BUSCAR_POR_IDENTIFICACION_DISTRIBUTIVO = "Servidor.buscarPorIdentificacionDistributivo";

    /**
     * Consulra que recupera servidor dado su tipo y numero de identificacion.
     */
    public static final String BUSCAR_POR_NOMBRE_Y_IDENTIFICACION_SERVIDOR
            = "Servidor.buscarPorNombreYIdentificacionServidor";

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "Servidor.buscarVigentes";

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_CODIGO = "Servidor.buscarPorCodigo";

    /**
     * Nombre de la consulta para buscar por nombre un servidor.
     */
    public static final String BUSCAR_POR_APELLIDOS_NOMBRES_SERVIDOR = "Servidor.buscarPorNombreServidor";

    /**
     * Nombre de la consulta para buscar por nombre un servidor con puesto asignado.
     */
    public static final String BUSCAR_POR_NOMBRE_SERVIDOR_CON_PUESTO = "Servidor.buscarPorNombreServidorConPuesto";

    /**
     * Nombre de la consulta para buscar por nombre un servidor con puesto asignado ya sea externo o interno.
     */
    public static final String BUSCAR_POR_IDENTIFICACION_SERVIDOR_EXTERNO_O_INTERNO_CON_PUESTO
            = "Servidor.buscarPorIdentificacionServidorExternoInternoConPuesto";

    /**
     * Nombre de la consulta para buscar por nombre un servidor que exista en el distributivo
     */
    public static final String BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO = "Servidor.buscarPorNombreServidorDistributivo";
    /**
     * Nombre de la consulta para buscar por nombre e identificacion un servidor que exista en el distributivo
     */
    public static final String BUSCAR_POR_NOMBRE_E_IDENTIFICACION_SERVIDOR_DISTRIBUTIVO
            = "Servidor.buscarPorNombreEIdentificacionServidorDistributivo";
    /**
     *
     */
    public static final String BUSCAR_POR_NOMBRE_SERVIDOR_DISTRIBUTIVO_UNIDAD_ORGANIZACIONAL
            = "Servidor.buscarPorNombreServidorDistributivoUnidadOrganizacional";
    /**
     * NOMBRE PARA LA CONSULTA DE LISTAR POR USERNAME Y CLAVE.
     */
    public static final String BUSCAR_POR_USERNAME_CLAVE = "Usuario.buscarPorUsernameYClave";

    /**
     *
     */
    public static final String BUSCAR_EXTERNO_POR_USERNAME_CLAVE = "Usuario.buscarExternoPorUsernameYClave";

    /**
     *
     */
    public static final String BUSCAR_ACTIVOS_POR_HORARIO_ID = "Usuario.buscarActivosPorHorarioId";

    /**
     *
     * @param id
     */
    public Servidor(final Long id) {
        super();
        this.id = id;

    }

    /**
     * Constructor por defecto.
     */
    public Servidor() {
        super();
    }

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @XmlElement
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @XmlElement
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    @XmlElement
    @Column(name = "apellidos")
    private String apellidos;

    @XmlElement
    @Column(name = "nombres")
    private String nombres;

    @XmlElement
    @Column(name = "mail")
    private String mail;

    @XmlElement
    @Column(name = "numero_carnet_conadis")
    private String numeroCarnetConadis;

    @XmlElement
    @Column(name = "porcentaje_discapacidad")
    private BigDecimal porcentajeDiscapacidad;

    @XmlElement
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    /**
     * Fecha de ingreso al sector publico.
     */
    @XmlElement
    @Column(name = "fecha_ingreso_sector_publico")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoSectorPublico;

    /**
     * Fecha de ingreso a la institucion.
     */
    @XmlElement
    @Column(name = "fecha_ingreso_institucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngresoInstitucion;

    @XmlElement
    @Column(name = "barrio_domicilio")
    private String barrioDomicilio;

    @XmlElement
    @Column(name = "calle_domicilio")
    private String calleDomicilio;

    @XmlElement
    @Column(name = "referencia_domicilio")
    private String referenciaDomicilio;

    @XmlElement
    @Column(name = "numero_domicilio")
    private String numeroDomicilio;

    @XmlElement
    @Column(name = "telefono")
    private String telefono;

    @XmlElement
    @Column(name = "no_ext")
    private String noExt;

    @XmlElement
    @Column(name = "celular")
    private String celular;

    @XmlElement
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;

    /**
     *
     */
    @XmlElement
    @Column(name = "recibe_fr")
    private Boolean recibeFondoReserva;

    /**
     *
     */
    @XmlElement
    @Column(name = "toma_transporte")
    private Boolean tomaTransporte;

    /**
     *
     */
    @XmlElement
    @Column(name = "mensualiza_decimo_tercero")
    private Boolean mensualizaDecimoTercero;

    /**
     *
     */
    @XmlElement
    @Column(name = "mensualiza_decimo_cuarto")
    private Boolean mensualizaDecimoCuarto;

    /**
     *
     */
    @XmlElement
    @Column(name = "usuario_mensualiza_decimo_cuarto")
    private String usuarioMensualizaDecimoCuarto;

    /**
     *
     */
    @XmlElement
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_mensualiza_decimo_cuarto")
    private Date fechaMensualizaDecimoCuarto;

    /**
     *
     */
    @XmlElement
    @Column(name = "usuario_mensualiza_decimo_tercero")
    private String usuarioMensualizaDecimoTercero;

    /**
     *
     */
    @XmlElement
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_mensualiza_decimo_tercero")
    private Date fechaMensualizaDecimoTercero;

    /**
     *
     */
    @XmlElement
    @Column(name = "usuario_recibe_fondo_reserva")
    private String usuarioRecibeFondoReserva;

    /**
     *
     */
    @XmlElement
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_recibe_fondo_reserva")
    private Date fechaRecibeFondoReserva;

    /**
     *
     */
    @XmlElement
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_salida")
    private Date fechaSalida;

    /**
     *
     */
    @JoinColumn(name = "catalogo_genero_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoGenero;

    @XmlElement
    @Column(name = "catalogo_genero_id")
    private Long catalogoGeneroId;

    @JoinColumn(name = "catalogo_tipo_sangre_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoTipoSangre;

    @XmlElement
    @Column(name = "catalogo_tipo_sangre_id")
    private Long catalogoTipoSangreId;

    @JoinColumn(name = "catalogo_estado_civil_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoEstadoCivil;

    @XmlElement
    @Column(name = "catalogo_estado_civil_id")
    private Long catalogoEstadoCivilId;

    @JoinColumn(name = "catalogo_etnia_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoEtnia;

    @XmlElement
    @Column(name = "catalogo_etnia_id")
    private Long catalogoEtniaId;

    @JoinColumn(name = "catalogo_talla_uniforme_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoTallaUniforme;

    @XmlElement
    @Column(name = "catalogo_talla_uniforme_id")
    private Long catalogoTallaUniformeId;

    @JoinColumn(name = "catalogo_numero_calzado_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoNumeroCalzado;

    @XmlElement
    @Column(name = "catalogo_numero_calzado_id")
    private Long catalogoNumeroCalzadoId;

    @JoinColumn(name = "catalogo_capacidades_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoCapacidades;

    @XmlElement
    @Column(name = "catalogo_capacidades_id")
    private Long catalogoCapacidadesId;

    @JoinColumn(name = "catalogo_nacionalidad_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoNacionalidad;

    @XmlElement
    @Column(name = "catalogo_nacionalidad_id")
    private Long catalogoNacionalidadId;

    @JoinColumn(name = "ubicacion_geografica_canton_nacimiento_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeograficaCantonNacimiento;

    @XmlElement
    @Column(name = "ubicacion_geografica_canton_nacimiento_id")
    private Long ubicacionGeograficaCantonNacimientoId;

    @JoinColumn(name = "ubicacion_geografica_parroquia_domicilio_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeograficaParroquiDomicilio;

    @XmlElement
    @Column(name = "ubicacion_geografica_parroquia_domicilio_id")
    private Long ubicacionGeograficaParroquiDomicilioId;

    @JoinColumn(name = "archivo_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivo;

    @XmlElement
    @Column(name = "archivo_id")
    private Long archivoId;

    /**
     * Estado de personal.
     */
    @JoinColumn(name = "estado_personal_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPersonal estadoPersonal;

    /**
     * Estado de personal.
     */
    @XmlElement
    @Column(name = "estado_personal_id")
    private Long estadoPersonalId;

    /**
     * Permite identificar al servidor en la tabla de Asistencias, para determinar la asistencia del mismo dentro de la
     * jornada laboral.
     */
    @XmlElement
    @Column(name = "codigo")
    private Long codigoEmpleado;

    /**
     * Campo que indica la hora que inicia la jornada laboral para el servidor.
     */
    @XmlElement
    @Column(name = "hora_entrada", columnDefinition = "datetime2")
    @Temporal(value = TemporalType.TIME)
    private Date horaEntrada;

    /**
     * Campo que indica el número de horas que tiene la jornada laboral.
     */
    @XmlElement
    @Column(name = "jornada")
    private Integer jornada;

    /**
     * Campo que almacena la clave del servidor.
     */
    @XmlElement
    @Column(name = "clave")
    @Size(min = 5, max = 50)
    private String clave;

    /**
     * Campo que almacena la fecha de caducidad de la clave del servidor.
     */
    @XmlElement
    @Column(name = "fecha_caducidad")
    @Temporal(value = TemporalType.DATE)
    private Date fechaCaducidad;

    /**
     * Lista de detalles de distributivos.
     */
    @OneToMany(mappedBy = "servidor")
    private List<DistributivoDetalle> listaDistributivoDetalle;

    /**
     * Lista de servidores en la institucion.
     */
    @OneToMany(mappedBy = "servidor")
    private List<ServidorInstitucion> listaServidorInstituicion;

    /**
     * Lista de detalles de distributivos.
     */
    @OneToMany(mappedBy = "servidor")
    private List<ServidorParienteInstitucion> listaParienteInstitucion;

    @JoinColumn(name = "foto_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivoFoto;

    @XmlElement
    @Column(name = "foto_id")
    private Long archivoFotoId;

    @XmlElement
    @Column(name = "sueldo_basico")
    private BigDecimal sueldoBasico;

    @XmlElement
    @Column(name = "tipo")
    private String tipo;

    @XmlElement
    @JoinColumn(name = "horario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Horario horario;

    /**
     * Indica datos del puesto que ocupa el servidort.
     */
    @Transient
    private DistributivoDetalle distributivoDetalle;

    /**
     *
     */
    @Column(name = "motivo_cambio_jornada")
    private String motivoCambioJornada;

    /**
     *
     */
    @OneToMany(mappedBy = "servidor")
    private List<ServidorHistoricosJornada> historicosJornada;

    /**
     *
     */
    @OneToMany(mappedBy = "servidor")
    private List<CuentaBancaria> cuentasBancarias;

    /**
     * Indica si el puesto esta seleccionado.
     */
    @Transient
    private Boolean selecto;

    /**
     *
     */
    @Transient
    private String nombreTipoIdentificacion;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the jornada
     */
    public Integer getJornada() {
        return 8;
    }

    /**
     *
     */
    @PostLoad
    public void postLoad() {
        if (!this.listaDistributivoDetalle.isEmpty()) {
            this.distributivoDetalle = this.listaDistributivoDetalle.get(0);
        }
    }

}
