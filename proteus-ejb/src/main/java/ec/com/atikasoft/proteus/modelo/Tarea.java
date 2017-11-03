/*
 *  Tarea.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 20, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Actividad especifica que debe atender un usuario
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.tareas")
@NamedQueries({
    @NamedQuery(name = Tarea.CONTAR_TAREAS_POR_USUARIO,
            query = "SELECT count(o) FROM Tarea o WHERE o.vigente=true AND o.usuarioAsignado=?1"
            + "AND o.codigoInstitucion=?2 AND o.asignado=?3 "),
    @NamedQuery(name = Tarea.CONTAR_TAREAS_PENDIENTES,
            query = "SELECT count(o) FROM Tarea o WHERE o.vigente=true AND o.usuarioAsignado=?1"
            + " AND o.asignado=true "),
    @NamedQuery(name = Tarea.CONTAR_TAREAS_POR_USUARIO_UNIDADES_ORGANIZACIONALES_Y_ESTADO,
            query = "SELECT count(o) FROM Tarea o WHERE o.vigente=true AND o.usuarioAsignado=?1"
            + "AND o.codigoInstitucion IN ?2 AND o.asignado=?3 "),
    @NamedQuery(name = Tarea.BUSCAR_TAREAS_POR_USUARIO,
            query = "SELECT o FROM Tarea o WHERE o.vigente=true AND  o.usuarioAsignado=?1 AND o.codigoInstitucion=?2 "),
    @NamedQuery(name = Tarea.BUSCAR_TAREAS_DE_SERVIDORES_INACTIVOS,
            query = "SELECT o FROM Tarea o, Tramite t WHERE o.vigente=true AND o.asignado=true  "
            + " AND (o.usuarioAsignado IN (SELECT s.numeroIdentificacion FROM Servidor s WHERE s.vigente=true "
            + " AND s.estadoPersonal.id=2) AND o.identificadorExterno=t.id AND t.codigoFase NOT IN "
            + " ('ELI','REG','REC','ANU'))"),
    @NamedQuery(name = Tarea.BUSCAR_POR_DESCONCENTRADO,
            query = "SELECT o FROM Tarea o WHERE o.vigente=true AND o.asignado=true AND "
            + "(o.usuarioAsignado IN (SELECT da.servidorApoyo.numeroIdentificacion FROM DesconcentradoApoyo da "
            + " WHERE da.vigente=true AND da.desconcentrado.servidorResponsable.id=?1 )"
            + "OR o.usuarioAsignado IN (SELECT da2.desconcentrado.servidorResponsable.numeroIdentificacion "
            + "FROM DesconcentradoApoyo da2 WHERE da2.vigente=true AND da2.servidorApoyo.id=?2 )"
            + "OR o.usuarioAsignado IN (SELECT s.numeroIdentificacion FROM Servidor s WHERE s.id=?3))")

})
@Getter
@Setter
public class Tarea extends EntidadBasica {

    /**
     * Nombre de consulta para contar tareas por usuario.
     */
    public static final String CONTAR_TAREAS_POR_USUARIO = "Tarea.contarTareasPorUsuario";

    /**
     * Nombre de consulta para contar tareas pendientes por atender.
     */
    public static final String CONTAR_TAREAS_PENDIENTES = "Tarea.contarTareasPendientes";

    /**
     * Nombre de consulta para buscar tareas asignadas a servidores inactivos.
     */
    public static final String BUSCAR_TAREAS_DE_SERVIDORES_INACTIVOS = "Tarea.buscarTareasDeServidoresInactivos";

    /**
     * Nombre de consulta para contar tareas por usuario.
     */
    public static final String CONTAR_TAREAS_POR_USUARIO_UNIDADES_ORGANIZACIONALES_Y_ESTADO
            = "Tarea.contarTareasPorUsuarioUnidadesOrganizacionalesEstado";

    /**
     * Nombre de consulta para buscar tareas por usuario.
     */
    public static final String BUSCAR_TAREAS_POR_USUARIO = "Tarea.buscarTareasPorUsuario";

    /**
     *
     */
    public static final String BUSCAR_POR_DESCONCENTRADO = "Tarea.buscarPorDesconcentrado";

    /**
     * Identificador único de sistema
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifica el origen de la instancia.
     */
    @Column(name = "origen")
    private String origen;

    /**
     * Indica si la tarea se encuentra asignada y pendiente de antender.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "asignado")
    private Boolean asignado;

    /**
     * Fecha de cuando se asigno la tarea al usuario.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;

    /**
     * Fecha de cuando el usuario atendio la tarea.
     */
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;

    /**
     * Usuario asignado para atender la tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "usuario_asignado")
    private String usuarioAsignado;

    /**
     * Nombre del usuario asignado.
     */
    @Column(name = "nombre_usuario_asignado")
    private String nombreUsuarioAsignado;

    /**
     * Nemonico del rol.
     */
    @Column(name = "nemonico_rol")
    private String nemonicoRol;

    /**
     * Identificador único funcional de la institucion.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "codigo_institucion")
    private String codigoInstitucion;

    /**
     * Nombre de la institucion.
     */
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;

    /**
     * Identificador único funcional de la institucion elaboradora.
     */
    @Column(name = "codigo_institucion_elaborador")
    private String codigoInstitucionElaborador;

    /**
     * Nombre de la institucion elaboradora.
     */
    @Column(name = "nombre_institucion_elaborador")
    private String nombreInstitucionElaborador;

    /**
     * Año del ejercicio fiscal.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ejercicio_fiscal")
    private Integer ejercicioFiscal;

    /**
     * Número secuencial de la tarea en el proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numero")
    private String numero;

    /**
     * Identificador externo del proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "identificador_externo")
    private Long identificadorExterno;

    /**
     * Numero externo de la instancia.
     */
    @Column(name = "numero_externo")
    private String numeroExterno;

    /**
     * Identificador del formulario asignado a la tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "identificador_formulario")
    private Long identificadorFormulario;

    /**
     * Descripcion del estado actual de la instancia que representa la tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_estado_actual")
    private String nombreEstadoActual;

    /**
     * Descripcion del estado siguiente de la instancia que representa la tarea.
     */
    @Size(max = 100)
    @Column(name = "nombre_estado_siguiente")
    private String nombreEstadoSiguiente;

    /**
     * Comentarios de la tarea.
     */
    @Size(max = 400)
    @Column(name = "comentario")
    private String comentario;

    /**
     * Descripcion referente de la tarea a realizar.
     */
    @Size(max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Titulo asignado al formulario
     */
    @Column(name = "titulo_formulario")
    private String tituloFormulario;

    /**
     * Correo electronico donde se notificara la tarea.
     */
    @Column(name = "mail")
    private String mail;

    /**
     * Identificacion de la institucion en el core.
     */
    @Column(name = "institucion_core_id")
    private Long institucionCoreId;

    /**
     * Referencia del detalle.
     */
    @JoinColumn(name = "detalles_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Detalle detalle;

    /**
     * Constructor.
     */
    public Tarea() {
        super();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
