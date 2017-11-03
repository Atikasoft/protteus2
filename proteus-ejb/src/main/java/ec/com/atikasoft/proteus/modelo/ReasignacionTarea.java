/*
 *  ReasignacionTarea.java
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
 *  07/01/2013
 *
 */

package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *Bitacora de reasignacion de tareas.
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.reasignacion_tareas")
public class ReasignacionTarea extends EntidadBasica {
    /**
     * Identificador único de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Fecha de cuando se asigno la tarea al usuario.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_asignacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    /**
     * Usuario asignado anterior de la tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "usuario_asignado_anterior")
    private String usuarioAsignadoAnterior;
    /**
     * Usuario reasignado para atender la tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "usuario_reasignado")
    private String usuarioReAsignado;
    /**
     * Motivo de la reasignación.
     */
    @Size(max = 400)
    @Column(name = "motivo")
    private String motivo;
    /**
     * Referencia a la tarea.
     */
    @JoinColumn(name = "tareas_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tarea tarea;
/**
 * Constructor.
 */
    public ReasignacionTarea() {
        super();
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
     * @return the fechaAsignacion
     */
    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * @param fechaAsignacion the fechaAsignacion to set
     */
    public void setFechaAsignacion(final Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * @return the usuarioAsignadoAnterior
     */
    public String getUsuarioAsignadoAnterior() {
        return usuarioAsignadoAnterior;
    }

    /**
     * @param usuarioAsignadoAnterior the usuarioAsignadoAnterior to set
     */
    public void setUsuarioAsignadoAnterior(final String usuarioAsignadoAnterior) {
        this.usuarioAsignadoAnterior = usuarioAsignadoAnterior;
    }

    /**
     * @return the usuarioReAsignado
     */
    public String getUsuarioReAsignado() {
        return usuarioReAsignado;
    }

    /**
     * @param usuarioReAsignado the usuarioReAsignado to set
     */
    public void setUsuarioReAsignado(final String usuarioReAsignado) {
        this.usuarioReAsignado = usuarioReAsignado;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(final String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the tarea
     */
    public Tarea getTarea() {
        return tarea;
    }

    /**
     * @param tarea the tarea to set
     */
    public void setTarea(final Tarea tarea) {
        this.tarea = tarea;
    }
@Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
