/*
 *  PlanificacionVacacion.java
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
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "planificacion_vacaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = PlanificacionVacacion.BUSCAR_VIGENTES,
            query = "SELECT a FROM PlanificacionVacacion a where a.vigente=true order by a.servidor.apellidosNombres "),
    @NamedQuery(name = PlanificacionVacacion.BUSCAR_POR_SERVIDOR_INSTITUCION_EJERCICIOFISCAL,
            query = "SELECT a FROM PlanificacionVacacion a where a.vigente=true and a.servidorId=?1 "
            + " and a.institucionEjercicioFiscalId=?2 order by a.servidor.apellidosNombres"),
    @NamedQuery(name = PlanificacionVacacion.BUSCAR_POR_INSTITUCION_EJERCICIOFISCAL_Y_ESTADO,
            query = "SELECT a FROM PlanificacionVacacion a where a.vigente=true and a.estado=?1 "
            + " and a.institucionEjercicioFiscalId=?2 order by a.servidor.apellidosNombres")
    
})
public class PlanificacionVacacion extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "PlanificacionVacacion.buscarVigente";
    /**
     * Nombre para busqueda de registros por el servidor y ejercicio fiscal.
     */
    public static final String BUSCAR_POR_SERVIDOR_INSTITUCION_EJERCICIOFISCAL = "PlanificacionVacacion.buscarPorServidorInstitucionEjercicioFiscal";
    /**
     * Nombre para busqueda de registros por ejercicio fiscal y estado.
     */
    public static final String BUSCAR_POR_INSTITUCION_EJERCICIOFISCAL_Y_ESTADO = "PlanificacionVacacion.buscarPorInstitucionEjercicioFiscalYEstado";
          /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Campo identeifidor del número de trámite para la planificación de
     * vacaciones.
     */
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "numero")
    private String numero;
    /**
     * Campo estado: Borrador, Enviado, Validado, Aprobado.
     */
//    @Basic(optional = false)
//    @NotNull
    @Column(name = "estado")
    private String estado;
    /**
     * Referencia a servidor jefe.
     */
    @JoinColumn(name = "servidor_jefe_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorJefe;
    /**
     * servidor jefe id.
     */
    @Column(name = "servidor_jefe_id")
    private Long servidorJefeId;
    /**
     * Referencia a Servidor RRHH Jefe.
     */
    @JoinColumn(name = "servidor_rrhh_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorRRHH;
    /**
     * Servidor RRHH Jefe id.
     */
    @Column(name = "servidor_rrhh_id")
    private Long servidorRRHHId;
    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;
    /**
     * servidor id.
     */
    @Column(name = "servidor_id")
    private Long servidorId;
    
       /**
     * Campo observacion de la validacion.
     */
    @Column(name = "observacion_validacion")
    private String observacionValidacion;
    
        /**
     * Campo observacion de la aprobacion.
     */
    @Column(name = "observacion_aprobacion")
    private String observacionAprobacion;
    /**
    /**
     * Referencia a institucionEjercicioFiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;
    /**
     * institucionEjercicioFiscal id.
     */
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;
    /**
     * Lista de detalles de la planificacion de vacaciones.
     */
    @OneToMany(mappedBy = "planificacionVacacion")
    private List<PlanificacionVacacionDetalle> planificacionVacacionDetalles;
    
    @Transient
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * Constructor.
     */
    public PlanificacionVacacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public PlanificacionVacacion(final Long id) {
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
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
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
     * @return the institucionEjercicioFiscalId
     */
    public Long getInstitucionEjercicioFiscalId() {
        return institucionEjercicioFiscalId;
    }

    /**
     * @param institucionEjercicioFiscalId the institucionEjercicioFiscalId to
     * set
     */
    public void setInstitucionEjercicioFiscalId(Long institucionEjercicioFiscalId) {
        this.institucionEjercicioFiscalId = institucionEjercicioFiscalId;
    }

    /**
     * @return the planificacionVacacionDetalles
     */
    public List<PlanificacionVacacionDetalle> getPlanificacionVacacionDetalles() {
        return planificacionVacacionDetalles;
    }

    /**
     * @param planificacionVacacionDetalles the planificacionVacacionDetalles to
     * set
     */
    public void setPlanificacionVacacionDetalles(List<PlanificacionVacacionDetalle> planificacionVacacionDetalles) {
        this.planificacionVacacionDetalles = planificacionVacacionDetalles;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the servidorJefe
     */
    public Servidor getServidorJefe() {
        return servidorJefe;
    }

    /**
     * @param servidorJefe the servidorJefe to set
     */
    public void setServidorJefe(Servidor servidorJefe) {
        this.servidorJefe = servidorJefe;
    }

    /**
     * @return the servidorJefeId
     */
    public Long getServidorJefeId() {
        return servidorJefeId;
    }

    /**
     * @param servidorJefeId the servidorJefeId to set
     */
    public void setServidorJefeId(Long servidorJefeId) {
        this.servidorJefeId = servidorJefeId;
    }

    /**
     * @return the servidorRRHH
     */
    public Servidor getServidorRRHH() {
        return servidorRRHH;
    }

    /**
     * @param servidorRRHH the servidorRRHH to set
     */
    public void setServidorRRHH(Servidor servidorRRHH) {
        this.servidorRRHH = servidorRRHH;
    }

    /**
     * @return the servidorRRHHId
     */
    public Long getServidorRRHHId() {
        return servidorRRHHId;
    }

    /**
     * @param servidorRRHHId the servidorRRHHId to set
     */
    public void setServidorRRHHId(Long servidorRRHHId) {
        this.servidorRRHHId = servidorRRHHId;
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
     * @return the observacionValidacion
     */
    public String getObservacionValidacion() {
        return observacionValidacion;
    }

    /**
     * @param observacionValidacion the observacionValidacion to set
     */
    public void setObservacionValidacion(String observacionValidacion) {
        this.observacionValidacion = observacionValidacion;
    }

    /**
     * @return the observacionAprobacion
     */
    public String getObservacionAprobacion() {
        return observacionAprobacion;
    }

    /**
     * @param observacionAprobacion the observacionAprobacion to set
     */
    public void setObservacionAprobacion(String observacionAprobacion) {
        this.observacionAprobacion = observacionAprobacion;
    }
}
