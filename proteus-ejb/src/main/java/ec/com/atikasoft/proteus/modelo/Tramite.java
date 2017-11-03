package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "tramites", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Tramite.BUSCAR_POR_ESTADO_Y_USUARIO,
            query = "SELECT t FROM Tramite t WHERE t.vigente=true AND t.codigoFase=?1 and "
            + "t.usuarioAsignadoCedula=?2  order by t.fechaCreacion DESC"),
    @NamedQuery(name = Tramite.CONTAR_POR_ESTADO_Y_USUARIO,
            query = "SELECT count(t) FROM Tramite t WHERE t.vigente=true AND t.codigoFase=?1 and "
            + "t.usuarioAsignadoCedula=?2 ")
})
@Getter
@Setter
public class Tramite extends EntidadBasica {

    /**
     * Nombre de la consulta que recupera los tramites en estado elaboracion y con usaurio logeado.
     */
    public static final String BUSCAR_POR_ESTADO_Y_USUARIO = "Tramite.buscarPorEstadoYUsuarioCreacion";

    /**
     * Nombre de la consulta que cuenta los tramites en estado elaboracion y con usaurio logeado.
     */
    public static final String CONTAR_POR_ESTADO_Y_USUARIO = "Tramite.contarPorEstadoYUsuarioCreacion";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Numero generado.
     */
    @Column(name = "numerico_tramite")
    private String numericoTramite;

    /**
     * Codigo del proceso que ejecuta el tramite.
     */
    @Column(name = "codigo_proceso")
    private String codigoProceso;

    /**
     * Indica si el tramite se realizo con solicitud.
     */
    @Column(name = "con_solicitud")
    private Boolean conSolicitud;

    @Column(name = "codigo_institucion")
    private String codigoInstitucion;

    /**
     * Nombre de la intitucion elaboradora.
     */
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;

    /**
     *
     */
    @Column(name = "codigo_ejercicio_fiscal")
    private Integer codigoEjercicioFiscal;

    /**
     * Codigo de la institucion aprobador.
     */
    @Column(name = "codigo_institucion_aprobador")
    private String codigoInstitucionAprobador;

    /**
     * Nombre de la institucion aprobadora.
     */
    @Column(name = "nombre_institucion_aprobador")
    private String nombreInstitucionAprobador;

    /**
     * TipoGestionEnum.
     */
    @Column(name = "tipo_gestion")
    private String tipoGestion;

    /**
     * Se toma de la instacia del proceso., cambiara en la base no obligatiorio
     */
    @Column(name = "estado")
    private String estado;

    /**
     * Codigo correspondiente a la fase.
     */
    @Column(name = "codigo_fase")
    private String codigoFase;

    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Este caso lo mismo q despcion.
     */
    @Column(name = "comentario")
    private String comentario;

    /**
     * Campo tipo de accion que trea las acciones.
     */
    @Column(name = "tipo_accion")
    private String tipoAccion;

    /**
     * campo que sustenta el numero de la accion.
     */
    @Column(name = "numero_documento_accion")
    private String numeroDocumento;

    /**
     * Campo que refiere a la fecha de accion.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_documento_accion")
    private Date fechaDocumento;

    /**
     * Indenteidicado unico del la instancia., id de instancia
     */
    @Column(name = "identificador_proceso")
    private Long identificadorProceso;

    /**
     * Cedula del usuario asignado el tramite.
     */
    @Column(name = "usuario_asignado_cedula")
    private String usuarioAsignadoCedula;

    /**
     * Nombre del usuario asignado el tramite.
     */
    @Column(name = "usuario_asignado_nombre")
    private String usuarioAsignadoNombre;

    /**
     * Cedula del usuario elaborador..
     */
    @Column(name = "usuario_asignado_cedula_elaborador")
    private String usuarioAsignadoCedulaElaborador;

    /**
     * Nombre del usuario elaborador.
     */
    @Column(name = "usuario_asignado_nombre_elaborador")
    private String usuarioAsignadoNombreElaborador;

    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    @JoinColumn(name = "instituciones_ejercicio_fiscal_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Referencia con tramite auxiliar.
     */
    @OneToOne(mappedBy = "tramite")
    private TramiteAuxiliar tramiteAuxiliar;

    /**
     * Referencia con tramite bitacora.
     */
    @OneToOne(mappedBy = "tramite")
    private TramiteBitacora tramiteBitacora;

    /**
     * Lista de movimientos.
     */
    @OneToMany(mappedBy = "tramite")
    private List<Movimiento> listaMovimientos;

    /**
     * Lista de bitacora.
     */
    @OneToMany(mappedBy = "tramite")
    private List<TramiteBitacora> listaBitacoras;

    /**
     *
     */
    @JoinColumn(name = "catalogo_representante_rhh_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Catalogo catalogoRepresentanteRRHH;

    @Column(name = "nombre_representante_rhh", nullable = true)
    private String nombreRepresentanteRRHH;

    /**
     *
     */
    @JoinColumn(name = "catalogo_autoridad_nominadora_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Catalogo catalogoAutoridadNominadora;

    @Column(name = "nombre_autoridad_nominadora", nullable = true)
    private String nombreAutoridadNominadora;

    /**
     * Nombre del usuario que anula/rechaza.
     */
    @Column(name = "usuario_rechazo_anulacion")
    private String usuarioAnulacionRechazo;

    /**
     * Comentario de anulación/rechazo.
     */
    @Column(name = "comentario_rechazo_anulacion")
    private String comentarioAnulacionRechazo;

    /**
     * Fecha de anulacion/rechazo.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_rechazo_anulacion")
    private Date fechaAnulacionRechazo;

    /**
     *
     */
    @Transient
    private String nombreUsuarioAnulacionRechazo;

    /**
     * Constructor.
     */
    public Tramite() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Tramite(final Long id) {
        super();
        this.id = id;
    }

}
