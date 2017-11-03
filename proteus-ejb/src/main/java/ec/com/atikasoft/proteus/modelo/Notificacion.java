/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
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
@Table(name = "notificaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Notificacion.BUSCAR_TODAS_VIGENTES,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_POR_ID,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true AND n.id=?1 ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_TODAS_VIGENTES_POR_REMITENTE_ID,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true AND n.remitente.id=?1"
            + " ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_TODAS_VIGENTES_POR_REMITENTE_IDENTIFICACION,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true AND n.remitente.numeroIdentificacion=?1"
            + " ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true"
            + " AND n.remitente.id=?1 AND n.institucionEjercicioFiscal.id=?2 ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true"
            + " AND n.remitente.numeroIdentificacion=?1 AND n.institucionEjercicioFiscal.id=?2"
            + " ORDER BY n.fechaEnvio DESC"),
    @NamedQuery(name = Notificacion.BUSCAR_POR_INSTITUCION_EJERCICIO_FISCAL_ALCANCE_LECTURA,
            query = "SELECT n FROM Notificacion n WHERE n.vigente=true"
            + " AND n.institucionEjercicioFiscal.id=?1 AND n.enviadaATodos=?2 ORDER BY n.fechaEnvio DESC")
})
@Getter
@Setter
public class Notificacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar registros vigentes.
     */
    public static final String BUSCAR_TODAS_VIGENTES = "Notificacion.buscarVigentes";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_ID = "Notificacion.buscarPorId";

    /**
     * Nombre de la consulta para buscar por id del remitente.
     */
    public static final String BUSCAR_TODAS_VIGENTES_POR_REMITENTE_ID = "Notificacion.buscarPorRemitenteId";

    /**
     * Nombre de la consulta para buscar por identificacion del remitente.
     */
    public static final String BUSCAR_TODAS_VIGENTES_POR_REMITENTE_IDENTIFICACION
            = "Notificacion.buscarPorRemitenteIdentificacion";

    /**
     * Nombre de la consulta para buscar por por id del remitente de institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL
            = "Notificacion.buscarPorRemitenteIdInstitucionEjercicioFiscal";

    /**
     * Nombre de la consulta para buscar por identificacion del remitente e id de la institucion ejercicio fiscal
     */
    public static final String BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL
            = "Notificacion.buscarPorRemitenteIdentificacionInstitucionEjercicioFiscal";
    
    /**
     * Nombre de la consulta para buscar por id del remitente las notificaciones de acuerdo al alcance de lectura
     */
    public static final String BUSCAR_POR_INSTITUCION_EJERCICIO_FISCAL_ALCANCE_LECTURA
            = "Notificacion.buscarPorInstitucionEjercicioFiscalAlcanceLectura";

    /**
     * Nombre de la consulta para buscar por el o los ids de los destinatarios e id de la institucion ejercicio fiscal
     */
//    public static final String BUSCAR_POR_DESTINATARIOS_ID_E_INSTITUCION_EJERCICIO_FISCAL
//            = "Notificacion.buscarPorRemitenteIdInstitucionEjercicioFiscal";
    /**
     * Nombre de la consulta para buscar por identificacion de o de los destinatarios id y de la institucion ejercicio
     * fiscal
     */
//    public static final String BUSCAR_POR_DESTINATARIOS_IDENTIFICACION_E_INSTITUCION_EJERCICIO_FISCAL
//            = "Notificacion.buscarPorRemitenteNroIdentificacionInstitucionEjercicioFiscal";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * id del servidor remitente. en caso de ser null, el remitente es el sistema
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remitente_id")
    private Servidor remitente;

    /**
     * id de la instiucion ejercicio fiscal
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     *
     */
    @Column(name = "asunto", length = 100)
    @NotNull
    private String asunto;

    /**
     *
     */
    @Column(name = "mensaje", length = 2000)
    @NotNull
    private String mensaje;

    /**
     *
     */
    @Column(name = "fecha_envio")
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    
    /**
     *
     */
    @Column(name = "para_todos")
    @NotNull
    private Boolean enviadaATodos;

    /**
     *
     */
    @OneToMany(mappedBy = "notificacion")
    private List<DestinatarioNotificacion> destinatarios;

    /**
     *
     */
    public Notificacion() {
        super();
    }

    /**
     *
     * @param id
     */
    public Notificacion(Long id) {
        super();
        this.id = id;
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
