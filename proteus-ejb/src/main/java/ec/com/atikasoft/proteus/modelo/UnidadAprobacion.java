/*
 *  UnidadAprobacion.java
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
 *  17/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "unidades_aprobacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = UnidadAprobacion.BUSCAR_VIGENTES,
            query = "SELECT a FROM UnidadAprobacion a where a.vigente=true order by a.idUnidadOrganizacionalAprobador"),
    @NamedQuery(name = UnidadAprobacion.BUSCAR_DUPLICADOS,
            query = "SELECT a FROM UnidadAprobacion a where a.idUnidadOrganizacionalAprobador=?1 and a.idUnidadOrganizacional=?2" 
        + " and a.vigente=true order by a.unidadOrganizacionalAprobador.nombre"),
    @NamedQuery(name = UnidadAprobacion.BUSCAR_POR_APROBADOR,
            query = "SELECT a FROM UnidadAprobacion a where a.idUnidadOrganizacionalAprobador=?1 and a.vigente=true order by a.idUnidadOrganizacionalAprobador"),
     @NamedQuery(name = UnidadAprobacion.BUSCAR_POR_UNIDAD_ORGANIZACIONAL,
            query = "SELECT a FROM UnidadAprobacion a where a.idUnidadOrganizacional=?1 and a.vigente=true order by a.idUnidadOrganizacionalAprobador")
})
public class UnidadAprobacion extends EntidadBasica {

    /**
     * Variable para búsqueda por id de Rubro.
     */
    public static final String BUSCAR_POR_APROBADOR = "UnidadAprobacion.buscarPorAprobador ";
    /**
     * Variable para búsqueda por id de Tipo de Nomina.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL = "UnidadAprobacion.buscarPorUnidadOrganizacional ";
    /**
     * Nombre para búsqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "UnidadAprobacion.buscarVigente";
     /**
     * Nombre para búsqueda de vigentes para evitar duplicados, busca por Ids
     */
    public static final String BUSCAR_DUPLICADOS = "UnidadAprobacion.buscarExistenciaRegistros";
    
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * Referencia a unidadOrganizacional
     */
    @JoinColumn(name = "unidad_organizacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;
    /**
     * unidadOrganizacional id.
     */
    @Column(name = "unidad_organizacional_id")
    private Long idUnidadOrganizacional;
    
    
          /**
     * Referencia a unidadOrganizacionalAprobador
     */
    @JoinColumn(name = "unidad_organizacional_aprobador_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacionalAprobador;
    
     /**
     * unidadOrganizacionalAprobador id.
     */
    @Column(name = "unidad_organizacional_aprobador_id")
    private Long idUnidadOrganizacionalAprobador;
    
    
     /**
     * Constructor.
     */
    public UnidadAprobacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public UnidadAprobacion(final Long id) {
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
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the idUnidadOrganizacional
     */
    public Long getIdUnidadOrganizacional() {
        return idUnidadOrganizacional;
    }

    /**
     * @param idUnidadOrganizacional the idUnidadOrganizacional to set
     */
    public void setIdUnidadOrganizacional(Long idUnidadOrganizacional) {
        this.idUnidadOrganizacional = idUnidadOrganizacional;
    }

    /**
     * @return the unidadOrganizacionalAprobador
     */
    public UnidadOrganizacional getUnidadOrganizacionalAprobador() {
        return unidadOrganizacionalAprobador;
    }

    /**
     * @param unidadOrganizacionalAprobador the unidadOrganizacionalAprobador to set
     */
    public void setUnidadOrganizacionalAprobador(UnidadOrganizacional unidadOrganizacionalAprobador) {
        this.unidadOrganizacionalAprobador = unidadOrganizacionalAprobador;
    }

    /**
     * @return the idUnidadOrganizacionalAprobador
     */
    public Long getIdUnidadOrganizacionalAprobador() {
        return idUnidadOrganizacionalAprobador;
    }

    /**
     * @param idUnidadOrganizacionalAprobador the idUnidadOrganizacionalAprobador to set
     */
    public void setIdUnidadOrganizacionalAprobador(Long idUnidadOrganizacionalAprobador) {
        this.idUnidadOrganizacionalAprobador = idUnidadOrganizacionalAprobador;
    }

  
   
}
