/*
 *  PlanificacionVacacionDetalle.java
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
 *  18/10/2013
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "planificacion_vacaciones_detalles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = PlanificacionVacacionDetalle.BUSCAR_VIGENTES,
            query = "SELECT a FROM PlanificacionVacacionDetalle a where a.vigente=true order by a.fechaInicio desc"),
    @NamedQuery(name = PlanificacionVacacionDetalle.BUSCAR_POR_PLANIFICACION_VACACION,
            query = "SELECT a FROM PlanificacionVacacionDetalle a where a.vigente=true "
            + " and a.planificacionVacacionId=?1 order by a.fechaInicio desc"),
    @NamedQuery(name = PlanificacionVacacionDetalle.BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL,
            query = "SELECT a FROM PlanificacionVacacionDetalle a where a.vigente=true and a.planificacionVacacion.servidorId=?1 "
            + " and a.planificacionVacacion.vigente =true "
            + " and a.planificacionVacacion.institucionEjercicioFiscalId=?2"
            + " order by a.planificacionVacacion.servidor.apellidosNombres"),
    @NamedQuery(name = PlanificacionVacacionDetalle.BUSCAR_POR_SERVIDOR_Y_ESTADO,
            query = "SELECT a FROM PlanificacionVacacionDetalle a where a.vigente=true and a.planificacionVacacion.servidorId=?1 "
            + " and a.planificacionVacacion.vigente =true and a.estado =?2 order by a.fechaInicio desc"),
    @NamedQuery(name = PlanificacionVacacionDetalle.BUSCAR_APROBADAS_MES,
            query = "SELECT p FROM PlanificacionVacacionDetalle p, DistributivoDetalle o "
            + " WHERE p.vigente=true AND o.servidor.id = p.planificacionVacacion.servidor.id "
            + " and p.planificacionVacacion.vigente=true "
            + " and o.distributivo.unidadOrganizacional.id = :idUnidadOrganizacional "
            + " and p.fechaInicio between :fechaDesde and :fechaHasta "
            + " and p.planificacionVacacion.estado = 'A' "
            + " order by p.fechaInicio desc ")
})
public class PlanificacionVacacionDetalle extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "PlanificacionVacacionDetalle.buscarVigente";
    /**
     * Nombre para busqueda de registros por la planificacion de vacaciones id.
     */
    public static final String BUSCAR_POR_PLANIFICACION_VACACION = "PlanificacionVacacionDetalle.buscarPorPlanificacionVacacion";
    /**
     * Nombre para busqueda de registros por la planificacion de vacaciones id.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL = "PlanificacionVacacionDetalle.buscarPorServidorYEjercicioFiscal";
    /**
     * Nombre para busqueda de registros por la planificacion de vacaciones id.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_ESTADO = "PlanificacionVacacionDetalle.buscarPorServidorYEstado";

    public static final String BUSCAR_APROBADAS_MES = "PlanificacionVacacionDetalle.buscarAprobadasParaMes";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Referencia a planificacionVacacion.
     */
    @JoinColumn(name = "planificacion_vacacion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanificacionVacacion planificacionVacacion;
    /**
     * planificacionVacacion id.
     */
    @Column(name = "planificacion_vacacion_id")
    private Long planificacionVacacionId;
    /**
     * Especifica la fecha de inicio del periodo de vacaciones.
     */
    @Column(name = "fecha_inicio")
    @Temporal(value = TemporalType.TIMESTAMP)
//    @NotNull
    private Date fechaInicio;
    /**
     * Especifica la fecha de fin del periodo de vacaciones.
     */
    @Column(name = "fecha_fin")
    @Temporal(value = TemporalType.TIMESTAMP)
//    @NotNull
    private Date fechaFin;
    /**
     * Registra el número de dias de vacaciones.
     */
    @Column(name = "numero_dias")
//    @NotNull
    private Long numeroDias;
    /**
     * Registra cuales de dias fueron seleccionados.
     */
    @Column(name = "dias_planificados")
//    @NotNull
    private String diasPlanificados;
    /**
     * Registra el estado.
     */
    @Column(name = "estado")
    @NotNull
    private String estado;
    /**
     *
     */
    @Column(name = "observacion")
    private String observacion;
    /**
     * Indica unidadOrganizacional.
     */
    @Transient
    private DistributivoDetalle distributivoDetalle;

    /**
     * Constructor.
     */
    public PlanificacionVacacionDetalle() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public PlanificacionVacacionDetalle(final Long id) {
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the planificacionVacacion
     */
    public PlanificacionVacacion getPlanificacionVacacion() {
        return planificacionVacacion;
    }

    /**
     * @param planificacionVacacion the planificacionVacacion to set
     */
    public void setPlanificacionVacacion(PlanificacionVacacion planificacionVacacion) {
        this.planificacionVacacion = planificacionVacacion;
    }

    /**
     * @return the planificacionVacacionId
     */
    public Long getPlanificacionVacacionId() {
        return planificacionVacacionId;
    }

    /**
     * @param planificacionVacacionId the planificacionVacacionId to set
     */
    public void setPlanificacionVacacionId(Long planificacionVacacionId) {
        this.planificacionVacacionId = planificacionVacacionId;
    }

    /**
     * @return the numeroDias
     */
    public Long getNumeroDias() {
        return numeroDias;
    }

    /**
     * @param numeroDias the numeroDias to set
     */
    public void setNumeroDias(Long numeroDias) {
        this.numeroDias = numeroDias;
    }

    /**
     *
     * @return dias planificados
     */
    public String getDiasPlanificados() {
        return diasPlanificados;
    }

    /**
     *
     * @param diasPlanificados dias planificados
     */
    public void setDiasPlanificados(String diasPlanificados) {
        this.diasPlanificados = diasPlanificados;
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
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }
}
