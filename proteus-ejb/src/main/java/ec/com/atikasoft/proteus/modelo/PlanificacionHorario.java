/*
 *  PlanificacionHorario.java
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
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "planificacion_horarios", catalog = "sch_proteus")
@NamedQueries({
     @NamedQuery(name = PlanificacionHorario.BUSCAR_VIGENTES,
            query = "SELECT a FROM PlanificacionHorario a where a.vigente=true "),
          @NamedQuery(name = PlanificacionHorario.BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL_Y_MES,
            query = "SELECT a FROM PlanificacionHorario a where a.vigente=true "
        + " and a.servidor.id = ?1"
        + " and a.institucionEjercicioFiscal.id= ?2 and a.mes =?3 "),
            @NamedQuery(name = PlanificacionHorario.BUSCAR_POR_SERVIDOR_Y_MES,
            query = "SELECT a FROM PlanificacionHorario a where a.vigente=true "
        + " and a.servidor.id = ?1"
        + " and a.mes =?2 "),
            @NamedQuery(name = PlanificacionHorario.BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL,
            query = "SELECT a FROM PlanificacionHorario a where a.vigente=true "
        + " and a.servidor.id = ?1"
        + " and a.institucionEjercicioFiscal.id= ?2 order by a.institucionEjercicioFiscal.id desc, a.mes desc")
})
public class PlanificacionHorario extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "PlanificacionHorario.buscarVigente";
     /**
     * Nombre para busqueda de registros por servidor ejercicio fiscal y mes.
     */
    public static final String BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL_Y_MES = "PlanificacionHorario.buscarServidorEjercicioFiscalYMes";
         /**
     * Nombre para busqueda de registros por servidor y mes.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_MES = "PlanificacionHorario.buscarServidorYMes";
     /**
     * Nombre para busqueda de registros por servidor y ejercicio fiscal.
     */
    public static final String BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL = "PlanificacionHorario.buscarServidorEjercicioFiscal";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Servidor servidor;
    
        /**
     * Referencia a institucion ejercicio fiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;
          /**
     * Campo mes, indica la Planificación de horarios para ese mes de un ejercicio fiscal.
     */
    @Column(name = "mes")
       @NotNull
    private Integer mes;
    
          /**
     * Campo hora de inicio indica la hora de inicio de la jornada laboral.
     */
    @Column(name = "hora_inicio")
    @Temporal(value = TemporalType.TIME)
    private Date horaInicio;
    
    
          /**
     * Campo hora de fin indica la hora de salida de la jornada laboral.
     */
    @Column(name = "hora_fin")
     @Temporal(value = TemporalType.TIME)
    private Date horaFin;
    
          /**
     * Campo hora de inicio del almuerzo.
     */
    @Column(name = "hora_inicio_almuerzo")
     @Temporal(value = TemporalType.TIME)
    private Date horaInicioAlmuerzo;
    
          /**
     * Campo hora fin del almuerzo.
     */
    @Column(name = "hora_fin_almuerzo")
     @Temporal(value = TemporalType.TIME)
    private Date horaFinAlmuerzo;
    
     /**
     * Constructor.
     */
    public PlanificacionHorario() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public PlanificacionHorario(final Long id) {
        super();
        this.id = id;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the mes
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the horaInicioAlmuerzo
     */
    public Date getHoraInicioAlmuerzo() {
        return horaInicioAlmuerzo;
    }

    /**
     * @param horaInicioAlmuerzo the horaInicioAlmuerzo to set
     */
    public void setHoraInicioAlmuerzo(Date horaInicioAlmuerzo) {
        this.horaInicioAlmuerzo = horaInicioAlmuerzo;
    }

    /**
     * @return the horaFinAlmuerzo
     */
    public Date getHoraFinAlmuerzo() {
        return horaFinAlmuerzo;
    }

    /**
     * @param horaFinAlmuerzo the horaFinAlmuerzo to set
     */
    public void setHoraFinAlmuerzo(Date horaFinAlmuerzo) {
        this.horaFinAlmuerzo = horaFinAlmuerzo;
    }


}
