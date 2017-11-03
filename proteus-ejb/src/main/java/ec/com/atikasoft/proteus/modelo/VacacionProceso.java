/*
 *  VacacionProceso.java
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
 *  26/12/2013
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones_procesos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionProceso.BUSCAR_VIGENTES,
    query = "SELECT a FROM VacacionProceso a where a.vigente=true order by a.fecha desc"),
    @NamedQuery(name = VacacionProceso.BUSCAR_ULTIMA_FECHA_PROCESADA,
    query = "SELECT a FROM VacacionProceso a where a.vigente=true and a.fecha in ("
        + " select max(b.fecha) from VacacionProceso b where b.vigente=true AND b.numeroRegistros>0)")
})
public class VacacionProceso extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VacacionProceso.buscarVigente";
    
      /**
     * Nombre para busqueda la ultima fecha procesada.
     */
    public static final String BUSCAR_ULTIMA_FECHA_PROCESADA = "VacacionProceso.buscarUltimaFechaProcesada";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo que indica el número de registros procesados.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_servidores")
    private Long numeroRegistros;
    
       /**
     *
     * /**
     * Referencia servidorInstitucion.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Especifica la fecha procesada.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.DATE)
    @NotNull
    private Date fecha;
  
    /**
     * Constructor.
     */
    public VacacionProceso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionProceso(final Long id) {
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
     * @return the numeroRegistros
     */
    public Long getNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * @param numeroRegistros the numeroRegistros to set
     */
    public void setNumeroRegistros(Long numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
}
