/*
 *  RubroPeriodoNomina.java
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
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
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

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "rubros_periodo_nomina", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = RubroPeriodoNomina.BUSCAR_VIGENTES,
            query = "SELECT a FROM RubroPeriodoNomina a where a.vigente=true order by a.idRubro"),
    @NamedQuery(name = RubroPeriodoNomina.BUSCAR_POR_RUBRO,
            query = "SELECT a FROM RubroPeriodoNomina a where a.idRubro=?1 and a.vigente=true order by a.idRubro"),
     @NamedQuery(name = RubroPeriodoNomina.BUSCAR_POR_PERIODO_NOMINA,
            query = "SELECT a FROM RubroPeriodoNomina a where a.idPeriodoNomina=?1 and a.vigente=true order by a.idPeriodoNomina")
})
public class RubroPeriodoNomina extends EntidadBasica {

    /**
     * Variable para búsqueda por id de Rubro.
     */
    public static final String BUSCAR_POR_RUBRO = "RubroPeriodoNomina.buscarporRubro ";
    /**
     * Variable para búsqueda por id de Tipo de Nomina.
     */
    public static final String BUSCAR_POR_PERIODO_NOMINA = "RubroPeriodoNomina.buscarporPeriodoNomina ";
    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "RubroPeriodoNomina.buscarVigente";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
       /**
     * Referencia a codigoContable
     */
    @JoinColumn(name = "rubro_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Rubro rubro;
    
     /**
     * codigoContable id.
     */
    @Column(name = "rubro_id")
    private Long idRubro;
    
    
          /**
     * Referencia a codigoContable
     */
    @JoinColumn(name = "periodo_nomina_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PeriodoNomina periodoNomina;
    
     /**
     * codigoContable id.
     */
    @Column(name = "periodo_nomina_id")
    private Long idPeriodoNomina;
    
    
     /**
     * Constructor.
     */
    public RubroPeriodoNomina() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public RubroPeriodoNomina(final Long id) {
        super();
        this.id = id;
    }
    
    @Override
    public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return (UtilCadena.concatenar("rubro-periodoNom:",this.getId(),"-",this.getRubro().getNombre(),"-",this.getPeriodoNomina().getNombre()));
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
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the idRubro
     */
    public Long getIdRubro() {
        return idRubro;
    }

    /**
     * @param idRubro the idRubro to set
     */
    public void setIdRubro(Long idRubro) {
        this.idRubro = idRubro;
    }

    /**
     * @return the periodoNomina
     */
    public PeriodoNomina getPeriodoNomina() {
        return periodoNomina;
    }

    /**
     * @param periodoNomina the periodoNomina to set
     */
    public void setPeriodoNomina(PeriodoNomina periodoNomina) {
        this.periodoNomina = periodoNomina;
    }

    /**
     * @return the idPeriodoNomina
     */
    public Long getIdPeriodoNomina() {
        return idPeriodoNomina;
    }

    /**
     * @param idPeriodoNomina the idPeriodoNomina to set
     */
    public void setIdPeriodoNomina(Long idPeriodoNomina) {
        this.idPeriodoNomina = idPeriodoNomina;
    }
   
}
