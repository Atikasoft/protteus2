/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "notificaciones_destinatarios", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_VIGENTES,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_NOTIFICACION_ID,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true AND dn.notificacion.id=?1"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_NOTIFICACION_Y_DESTINATARIO_IDS,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true AND dn.notificacion.id=?1"
            + " AND dn.destinatario.id=?2 ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_TODAS_POR_REMITENTE_ID,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " AND dn.notificacion.remitente.id=?1"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_TODAS_POR_DESTINATARIO_ID,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true AND dn.destinatario.id=?1"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " AND dn.notificacion.remitente.numeroIdentificacion=?1"
            + " AND dn.notificacion.institucionEjercicioFiscal.id=?2"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " AND dn.notificacion.remitente.id=?1"
            + " AND dn.notificacion.institucionEjercicioFiscal.id=?2"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_DESTINATARIO_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " AND dn.destinatario.numeroIdentificacion=?1"
            + " AND dn.notificacion.institucionEjercicioFiscal.id=?2"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_DESTINATARIO_ID_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true "
            + "AND dn.destinatario.id=?1 AND dn.notificacion.institucionEjercicioFiscal.id=?2"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.BUSCAR_POR_DESTINATARIO_REMITENTE_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT dn FROM DestinatarioNotificacion dn WHERE dn.vigente=true AND dn.destinatario.id=?1 "
            + "AND dn.notificacion.remitente.id=?2 AND dn.notificacion.institucionEjercicioFiscal.id=?3"
            + " ORDER BY dn.notificacion.fechaEnvio DESC"),
    @NamedQuery(name = DestinatarioNotificacion.CONTAR_NOTIFICACIONES_POR_DESTINATARIO_ID_Y_ESTADO_LECTURA,
            query = "SELECT COUNT(dn.id) FROM DestinatarioNotificacion dn WHERE dn.vigente=true"
            + " AND dn.destinatario.id=?1 AND dn.estado=?2")
})
@Getter
@Setter
public class DestinatarioNotificacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar registros vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DestinatarioNotificacion.buscarVigentes";

    /**
     * Nombre de la consulta para buscar por id de notificacion.
     */
    public static final String BUSCAR_POR_NOTIFICACION_ID = "DestinatarioNotificacion.buscarPorNotificacionId";

    /**
     * Nombre de la consulta para buscar por id de notificacion y de destinatario.
     */
    public static final String BUSCAR_POR_NOTIFICACION_Y_DESTINATARIO_IDS
            = "DestinatarioNotificacion.buscarPorNotificacionYDestinatarioIds";

    /**
     * Nombre de la consulta para buscar por remitente.
     */
    public static final String BUSCAR_TODAS_POR_REMITENTE_ID = "DestinatarioNotificacion.buscarPorRemitente";

    /**
     * Nombre de la consulta para buscar por id del destinatario.
     */
    public static final String BUSCAR_TODAS_POR_DESTINATARIO_ID = "DestinatarioNotificacion.buscarPorDestinatario";

    /**
     * Nombre de la consulta para buscar por por id del remitente de institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL
            = "DestinatarioNotificacion.buscarPorRemitenteIdInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para buscar por identificacion del remitente e id de la institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL
            = "DestinatarioNotificacion.buscarPorRemitenteNroIdentificacionInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para buscar por por id del destinatario de institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_DESTINATARIO_ID_INSTITUCION_EJERCICIO_FISCAL
            = "DestinatarioNotificacion.buscarPorDestinatarioIdInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para buscar por identificacion del destinatario e id de la institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_DESTINATARIO_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL
            = "DestinatarioNotificacion.buscarPorDestinatarioIdentificacionInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para buscar por destinatario, remitente e institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_DESTINATARIO_REMITENTE_INSTITUCION_EJERCICIO_FISCAL
            = "DestinatarioNotificacion.buscarPorDestintarioRemitenteInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para contar las notificaciones asociadas a un destinatario de acuerdo a su estado de
     * lectura
     */
    public static final String CONTAR_NOTIFICACIONES_POR_DESTINATARIO_ID_Y_ESTADO_LECTURA
            = "DestinatarioNotificacion.contarNotificacionesPorDestinatarioIdYEstadoLectura";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificacion_id")
    @NotNull
    private Notificacion notificacion;

    /**
     * id del servidor destinatario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id")
    @NotNull
    private Servidor destinatario;

    /**
     *
     */
    @Column(name = "estado")
    @NotNull
    private String estado;

    /**
     *
     */
    @Column(name = "fecha_lectura")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaLectura;

    /**
     *
     */
    public DestinatarioNotificacion() {
    }

    /**
     *
     * @param id
     */
    public DestinatarioNotificacion(Long id) {
        this.id = id;
    }

    /**
     *
     * @param destinatario
     */
    public DestinatarioNotificacion(Servidor destinatario) {
        this.destinatario = destinatario;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
