/*
 *  AsistenciaProceso.java
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
@Table(name = "asistencia_procesos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = AsistenciaProceso.BUSCAR_VIGENTES,
            query = "SELECT a FROM AsistenciaProceso a where a.vigente=true order by a.fecha desc"),
    @NamedQuery(name = AsistenciaProceso.BUSCAR_ULTIMA_FECHA_PROCESADA,
            query = "SELECT a FROM AsistenciaProceso a where a.vigente=true and a.fecha in ("
            + " select max(b.fecha) from AsistenciaProceso b where b.vigente=true)")
})
public class AsistenciaProceso extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "AsistenciaProceso.buscarVigente";

    /**
     * Nombre para busqueda la ultima fecha procesada.
     */
    public static final String BUSCAR_ULTIMA_FECHA_PROCESADA = "AsistenciaProceso.buscarUltimaFechaProcesada";

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
    @Column(name = "numero_registros")
    private Long numeroRegistros;

    /**
     * Especifica la fecha procesada.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.DATE)
    @NotNull
    private Date fecha;
    /**
     * Referencia a nomina.
     */
    @JoinColumn(name = "nomina_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Nomina nomina;

    /**
     * Constructor.
     */
    public AsistenciaProceso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public AsistenciaProceso(final Long id) {
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
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

}
