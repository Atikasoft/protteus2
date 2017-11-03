/*
 *  RelojUnidadOrganizacionaljava
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
 *  16/01/2014
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
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "relojes_unidades_organizacionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = RelojUnidadOrganizacional.BUSCAR_VIGENTES,
            query = "SELECT a FROM RelojUnidadOrganizacional a WHERE a.vigente=true"),
    @NamedQuery(name = RelojUnidadOrganizacional.BUSCAR_POR_RELOJ_ID,
            query = "SELECT a FROM RelojUnidadOrganizacional a WHERE a.vigente=true AND a.reloj.id=?1"),
    @NamedQuery(name = RelojUnidadOrganizacional.BUSCAR_POR_UNIDAD_ORGANIZACIONAL_ID,
            query = "SELECT a FROM RelojUnidadOrganizacional a WHERE a.vigente=true AND a.unidadOrganizacional.id=?1")
})
@Getter
@Setter
public class RelojUnidadOrganizacional extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "RelojUnidadOrganizacional.buscarVigente";

    /**
     * Nombre para busqueda de vigentes por codigo.
     */
    public static final String BUSCAR_POR_RELOJ_ID = "RelojUnidadOrganizacional.buscarPorRelojId";
    /**
     * Nombre para busqueda de vigentes por nombre.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL_ID = 
            "RelojUnidadOrganizacional.buscarPorUnidadOrganizacionalId";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relojes_id")
    private Reloj reloj;
    
    /**
     * 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidades_organizacionales_id")
    private UnidadOrganizacional unidadOrganizacional;
    
    /**
     * 
     */
    public RelojUnidadOrganizacional() {
        super();
    }
    
    /**
     * 
     * @param reloj
     * @param uo 
     */
    public RelojUnidadOrganizacional(final Reloj reloj, final UnidadOrganizacional uo) {
        this.reloj = reloj;
        this.unidadOrganizacional = uo;
    }
    
}
