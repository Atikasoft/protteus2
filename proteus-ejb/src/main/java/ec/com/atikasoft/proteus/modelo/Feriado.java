/*
 *  Feriado.java
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
 *  25/10/2013
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
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "feriados", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Feriado.BUSCAR_POR_NOMBRE_POR_REGIMEN, query = "SELECT a FROM Feriado a where a.descripcion LIKE ?1 and a.regimenLaboral.id=?2 and a.vigente=true order by a.fecha desc, a.descripcion"),
    @NamedQuery(name = Feriado.BUSCAR_VIGENTES_POR_REGIMEN, query = "SELECT a FROM Feriado a where a.vigente=true and a.regimenLaboral.id=?1 order by a.fecha desc "),
    @NamedQuery(name = Feriado.BUSCAR_VIGENTES, query = "SELECT a FROM Feriado a where a.vigente=true order by a.fecha desc "),
    @NamedQuery(name = Feriado.BUSCAR_POR_PERIODO, query = "SELECT o FROM Feriado o WHERE o.vigente=true AND o.idEjercicioFiscal=?1 AND o.fecha BETWEEN ?2 AND ?3 ")
})
public class Feriado extends EntidadBasica {
    /*
     * Nombre para busqueda de vigentes.
     */

    public static final String BUSCAR_VIGENTES_POR_REGIMEN = "Feriado.buscarVigentePorRegimen";
    /*
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Feriado.buscarVigente";

    /*
     * Nombre para busqueda de fechas vigentes.
     */
    public static final String BUSCAR_POR_NOMBRE_POR_REGIMEN = "Feriado.buscarPorNombrePorRegimenLaboral";

    /**
     * Nombre de la consulta que busca feriados en un periodo.
     */
    public static final String BUSCAR_POR_PERIODO = "Feriado.buscarPorPeriodo";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo descripción.
     */
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @NotNull
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fecha;

    /**
     * Referencia a ejercicioFiscal
     */
    @JoinColumn(name = "ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EjercicioFiscal ejercicioFiscal;

    /**
     * Referencia a regimen laboral
     */
    @JoinColumn(name = "regimen_laboral_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;

    /**
     * ejercicioFiscal id.
     */
    @Column(name = "ejercicio_fiscal_id")
    private Long idEjercicioFiscal;

    /**
     * Constructor.
     */
    public Feriado() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Feriado(Long id) {
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
     * @return the ejercicioFiscal
     */
    public EjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(EjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the idEjercicioFiscal
     */
    public Long getIdEjercicioFiscal() {
        return idEjercicioFiscal;
    }

    /**
     * @param idEjercicioFiscal the idEjercicioFiscal to set
     */
    public void setIdEjercicioFiscal(Long idEjercicioFiscal) {
        this.idEjercicioFiscal = idEjercicioFiscal;
    }

    /**
     * @return the regimenLaboral
     */
    public RegimenLaboral getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(RegimenLaboral regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }
}
