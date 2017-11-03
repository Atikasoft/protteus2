/*
 *  VacacionSolicitud.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones_solicitudes", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionSolicitud.BUSCAR_POR_TIPO,
            query = "SELECT a FROM VacacionSolicitud a where a.tipo = ?1 and a.vigente=true order by a.id desc "),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_POR_SERVIDOR,
            query = "SELECT a FROM VacacionSolicitud a where a.vigente=true "
            + "and a.servidorInstitucion.servidor.id =?1 order by a.id desc "),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_POR_SERVIDOR_Y_ESTADO,
            query = "SELECT a FROM VacacionSolicitud a where a.vigente=true "
            + "and a.servidorInstitucion.servidor.id =?1 and a.estado =?2 order by a.id desc "),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_VIGENTES,
            query = "SELECT a FROM VacacionSolicitud a where a.vigente=true order by a.id desc "),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_POR_SERVIDOR_EN_TRAMITE,
            query = "SELECT a FROM VacacionSolicitud a where a.estado IN ('V','R') and a.servidorInstitucion.servidor.id"
            + " =?1 and a.vigente=true order by a.id desc "),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_POR_ESTADO,
            query = "SELECT a FROM VacacionSolicitud a where a.estado=?1 and a.vigente=true order by a.id desc"),
    @NamedQuery(name = VacacionSolicitud.BUSCAR_VACACIONES_POR_SERVIDOR_Y_FECHA,
            query = "SELECT a FROM VacacionSolicitud a WHERE a.vigente=true AND (?1 BETWEEN a.fechaCreacion AND "
            + "a.fechaCreacion OR ?2 BETWEEN a.fechaCreacion AND a.fechaCreacion) "
            + " and a.servidorInstitucion.servidor.id =?3 "),
    @NamedQuery(name = VacacionSolicitud.CONTAR_MINUTOS_APROBADAS,
            query = "SELECT SUM(a.cantidadTomadaMinutos) FROM VacacionSolicitud a where a.vigente=true AND"
            + " a.estado='A' AND  a.idServidorInstitucion=?1")
})
@Setter
@Getter
public class VacacionSolicitud extends EntidadBasica {

    /**
     * Variable para busqueda por TIPO.
     */
    public static final String BUSCAR_POR_TIPO = "VacacionSolicitud.buscarporTipo";
    /**
     * Variable para busqueda por estado.
     */
    public static final String BUSCAR_POR_ESTADO = "VacacionSolicitud.buscarporEstado";
    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VacacionSolicitud.buscarVigente";
    /**
     * Nombre para busqueda de identificacion del servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR = "VacacionSolicitud.buscarPorServidor";
    /**
     * Nombre para busqueda de identificacion del servidor y estado de la solicitud.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_ESTADO = "VacacionSolicitud.buscarPorServidorYEstado";
    /**
     * Busca solicitudes en estado REGISTRADO Y VALIDADO de un servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR_EN_TRAMITE = "VacacionSolicitud.buscarPorServidorEnTramite";
    /**
     * Busca solicitudes de Vacaciones (A,V) en estado no negadas de un servidor por fechas.
     */
    public static final String BUSCAR_VACACIONES_POR_SERVIDOR_Y_FECHA = "VacacionSolicitud.buscarPorServidorYFecha";

    /**
     * Contar el numero de dias de solicitudes aprobadas.
     */
    public static final String CONTAR_MINUTOS_APROBADAS = "VacacionSolicitud.contarMinutosAprobadas";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Campo tipo: V-Vacación, A-Adelanto Vacacion, P-Permiso con cargo a vacación
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private String tipo;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    /**
     * Campo cantidad_solicitada.
     */
    @Column(name = "cantidad_solicitada_minutos")
    private Long cantidadSolicitadaMinutos;

    /**
     * Campo cantidad tomada.
     */
    @Column(name = "cantidad_tomada_minutos")
    private Long cantidadTomadaMinutos;

    /**
     * Cantidad solicitda en texto.
     */
    @Column(name = "cantidad_solicitada_texto")
    private String cantidadSolicitadaTexto;

    /**
     * Campo cantidad_solicitada.
     */
    @Column(name = "cantidad_recuperada")
    private Long cantidadRecuperada;

    /**
     * Campo saldoActual efectiva.
     */
    @Column(name = "saldo_vacaciones_efectiva")
    private Long saldoVacacionesEfectiva;

    /**
     * Campo saldoActual efectiva.
     */
    @Column(name = "saldo_vacaciones_proporcional")
    private Long saldoVacacionesProporcial;

    /**
     * Campo estado: <R>egistrado, <N>egado, <A>probado', <D>Anulado
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    /**
     * Campo observacion.
     */
    @Column(name = "observacion")
    private String observacion;
    /**
     * Campo motivo para aprobaciones y validaciones.
     */
    @Column(name = "motivo")
    private String motivo;
    /**
     * Referencia servidorInstitucion.
     */
    @JoinColumn(name = "servidor_institucion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion servidorInstitucion;

    /**
     * Referencia validador.
     */
    @JoinColumn(name = "validador_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion validador;

    /**
     * Referencia recuperador.
     */
    @JoinColumn(name = "recuperador_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor recuperador;

    /**
     *
     */
    @Column(name = "nombre_usuario_creacion")
    private String nombreUsuarioCreacion;

    /**
     * servidorInstitucion id.
     */
    @Column(name = "servidor_institucion_id")
    private Long idServidorInstitucion;

    /**
     * Referencia aprobador.
     */
    @JoinColumn(name = "aprobador_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion aprobador;

    /**
     * aprobador id.
     */
    @Column(name = "aprobador_id")
    private Long idAprobador;

    /**
     * Referencia vacacionParametro.
     */
    @JoinColumn(name = "vacacion_parametro_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VacacionParametro vacacionParametro;

    /**
     * vacacionParametros id.
     */
    @Column(name = "vacacion_parametro_id")
    private Long idVacacionParametro;

    /**
     * Facilita el tipo de periodo seleccionado DIAS/HORAS/MINUTOS.
     */
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Número de Minutos imputados que se incluyen en la solicitud, por los fines de semana.
     */
    @Column(name = "minutos_imputados")
    private BigDecimal minutosImputados;

    /**
     * Referencia a archivo de respaldo.
     */
    @JoinColumn(name = "archivo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     * Campo hora de inicio indica la hora de inicio del permiso.
     */
    @Column(name = "hora_inicio")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date horaInicio;

    /**
     * Campo hora fin indica la hora de fin del permiso.
     */
    @Column(name = "hora_fin")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date horaFin;

    /**
     * Coma separado de las fechas seleccionadas en formato dd/MM/yyyy
     */
    @Column(name = "dias_planificados")
    private String diasPlanificados;

    /**
     * CODIGO DEL DOCUMENTO QUE RESPALDA LA ACCION DE PERSONAL GENERADA AL APROBAR LA SOLICITUD DE VACACIONES
     */
    @Column(name = "codigo_documento_previo")
    private String codigoDocumentoPrevio;

    /**
     *
     */
    @Column(name = "numero_documento_previo")
    private String numeroDocumentoPrevio;

    /**
     *
     */
    @Column(name = "fecha_documento_previo")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaDocumentoPrevio;

    /**
     * Referencia archivoAccionPersonal.
     */
    @JoinColumn(name = "archivo_accion_personal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivoAccionPersonal;

    /**
     *
     */
    @Column(name = "archivo_accion_personal_id")
    private Long idArchivoAccionPersonal;

    /**
     * Indica si la solicitud tomo el valor desde el saldo efectivo o proporcional.
     */
    @Column(name = "es_vacacion_efectiva")
    private Boolean esVacacionEfectiva;

    /**
     * Almacena el calculo de minutos solicitados, Variable transient.
     */
    @Transient
    private Long minutosSolicitados;

    /**
     * Indica unidadOrganizacional, variable transient.
     */
    @Transient
    private DistributivoDetalle distributivoDetalle;

    /**
     * Indica saldo en palabras, variable transient.
     */
    @Transient
    private String cadenaSaldo;

    /**
     *
     */
    @Transient
    private Long cantidadSolicitada;

    /**
     * Constructor.
     */
    public VacacionSolicitud() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionSolicitud(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
