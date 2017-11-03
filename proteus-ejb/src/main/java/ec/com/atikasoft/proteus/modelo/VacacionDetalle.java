/*
 *  VacacionDetalle.java
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones_detalle", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionDetalle.BUSCAR_POR_DOCUMENTO_SERVIDOR, query = "SELECT a FROM VacacionDetalle a where "
            + " a.vacacion.servidorInstitucion.servidor.tipoIdentificacion = ?1 "
            + " and a.vacacion.servidorInstitucion.servidor.numeroIdentificacion = ?2 and a.vigente=true order by "
            + " a.fechaCreacion desc "),
    @NamedQuery(name = VacacionDetalle.BUSCAR_POR_VACACION, query = "SELECT a FROM VacacionDetalle a where "
            + " a.vacacion.id = ?1 and a.vigente=true order by a.fechaCreacion desc "),
    @NamedQuery(name = VacacionDetalle.BUSCAR_POR_VACACION_POR_TIPO_VACACION, query = "SELECT a FROM VacacionDetalle a "
            + " where a.vacacion.id = ?1 and a.tipo IN ('V','D') AND a.vigente=true order by a.fechaCreacion desc "),
    @NamedQuery(name = VacacionDetalle.BUSCAR_POR_VACACION_POR_TIPO_PROPORCIONAL, query = "SELECT a FROM "
            + " VacacionDetalle a where a.vacacion.id = ?1 and a.tipo IN ('P') AND a.vigente=true order by "
            + " a.fechaCreacion desc "),
    @NamedQuery(name = VacacionDetalle.BUSCAR_VIGENTES, query = "SELECT a FROM VacacionDetalle a where a.vigente=true "
            + " order by a.fechaInicio desc "),
    @NamedQuery(name = VacacionDetalle.QUITAR_VIGENCIA, query = "UPDATE VacacionDetalle o SET o.vigente=false"
            + " WHERE o.vacacion.id=?1"),
    @NamedQuery(name = VacacionDetalle.ACTUALIZAR_TIPO, query = "UPDATE VacacionDetalle o SET o.tipo=?1 WHERE "
            + " o.tipo=?2 AND o.vacacion.id=?3 AND o.vigente=true ")
})
public class VacacionDetalle extends EntidadBasica {

    /**
     * Variable para busqueda por numero de documento del servidor.
     */
    public static final String BUSCAR_POR_DOCUMENTO_SERVIDOR = "VacacionDetalle.buscarporDocumentoServidor";

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VacacionDetalle.buscarVigente";

    /**
     * Variable para busqueda por id de vacacion
     */
    public static final String BUSCAR_POR_VACACION = "VacacionDetalle.buscarPorVacacion";
    /**
     *
     */
    public static final String BUSCAR_POR_VACACION_POR_TIPO_VACACION = "VacacionDetalle.buscarPorVacacionPorTipoVacacion";
    /**
     *
     */
    public static final String BUSCAR_POR_VACACION_POR_TIPO_PROPORCIONAL = "VacacionDetalle.buscarPorVacacionPorTipoProporcional";

    /**
     *
     */
    public static final String QUITAR_VIGENCIA = "VacacionDetalle.quitarVigencia";
    /**
     *
     */
    public static final String ACTUALIZAR_TIPO = "VacacionDetalle.actualizarTipo";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Referencia VacacionSolicitud.
     */
    @JoinColumn(name = "vacacion_solicitud_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VacacionSolicitud vacacionSolicitud;
    /**
     * Referencia Vacacion.
     */
    @JoinColumn(name = "vacacion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vacacion vacacion;

    /**
     * Campo fechaInicio.
     */
    @Column(name = "fecha_inicio")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaInicio;
    /**
     * Campo fechaFin.
     */
    @Column(name = "fecha_fin")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaFin;
    /**
     * Campo debito.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "debito")
    private Long debito;
    /**
     * Campo credito.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "credito")
    private Long credito;
    /**
     * Campo observacion.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "observacion")
    private String observacion;

    /**
     * Identifica el tipo de registro: V : Vacacion P : Proporcional D : Descuento.
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * verifica si es un ajuste manual de saldo de vacaciones
     */
    @Column(name = "ajuste_manual")
    private Boolean esAjusteManual;

    /**
     * Constructor.
     */
    public VacacionDetalle() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionDetalle(final Long id) {
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
     * @return the vacacionSolicitud
     */
    public VacacionSolicitud getVacacionSolicitud() {
        return vacacionSolicitud;
    }

    /**
     * @param vacacionSolicitud the vacacionSolicitud to set
     */
    public void setVacacionSolicitud(VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
    }

    /**
     * @return the debito
     */
    public Long getDebito() {
        return debito;
    }

    /**
     * @param debito the debito to set
     */
    public void setDebito(Long debito) {
        this.debito = debito;
    }

    /**
     * @return the credito
     */
    public Long getCredito() {
        return credito;
    }

    /**
     * @param credito the credito to set
     */
    public void setCredito(Long credito) {
        this.credito = credito;
    }

    /**
     * @return the vacacion
     */
    public Vacacion getVacacion() {
        return vacacion;
    }

    /**
     * @param vacacion the vacacion to set
     */
    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return es ajuste manual
     */
    public Boolean getEsAjusteManual() {
        return esAjusteManual;
    }

    /**
     *
     * @param esAjusteManual es ajuste manual
     */
    public void setEsAjusteManual(Boolean esAjusteManual) {
        this.esAjusteManual = esAjusteManual;
    }
}
