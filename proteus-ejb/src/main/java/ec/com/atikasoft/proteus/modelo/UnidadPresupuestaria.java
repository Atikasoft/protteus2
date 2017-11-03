/*
 *  UnidadPresupuestaria.java
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
 *  20/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
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
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "unidades_presupuestarias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_ORDENADA,
            query = "Select u FROM UnidadPresupuestaria u where u.vigente=true and u.unidadPresupuestaria is null"),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_POR_NIVEL,
            query = "Select u FROM UnidadPresupuestaria u where u.vigente=true and u.nivel=?1 and u.id=?2"),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_POR_SOCIEDAD,
            query = "Select u FROM UnidadPresupuestaria u where u.vigente=true and u.sociedad=?1"
            + " AND EXISTS(Select o from DistributivoDetalle o where o.vigente=true "
            + "AND o.unidadPresupuestaria.id=u.id AND o.servidor.numeroIdentificacion =?2 and o.servidor is not null )"),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_UNIDAD_PRESUPUESTARIA_PADRE, query
            = "Select u FROM UnidadPresupuestaria "
            + "u where u.vigente=true and u.unidadPresupuestaria.id =?1   "),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_VIGENTES, query = "Select u FROM UnidadPresupuestaria "
            + "u where u.vigente=true order by u.sector.nombre, u.nombre "),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_POR_NOMBRE, query = "Select u FROM UnidadPresupuestaria "
            + "u where u.vigente=true and u.nombre like ?1   "),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_POR_CODIGO, query
            = "SELECT u FROM UnidadPresupuestaria u where u.codigo=?1 order by u.nombre"),
    @NamedQuery(name = UnidadPresupuestaria.BUSCAR_POR_CODIGO_Y_SECTOR, query
            = "SELECT u FROM UnidadPresupuestaria u where u.codigo=?1 and u.sector.id=?2 order by u.id"),
    @NamedQuery(name = UnidadPresupuestaria.CONTAR_POR_CODIGO_INTERNO, query
            = "SELECT COUNT(u) FROM UnidadPresupuestaria u where u.codigoInterno = ?1 and u.vigente = true "),
    @NamedQuery(name = UnidadPresupuestaria.CONTAR_POR_CODIGO_INTERNO_IGNORAR_ID, query
            = "SELECT COUNT(u) FROM UnidadPresupuestaria u where u.codigoInterno = ?1 and u.id != ?2 and u.vigente = true "),
})
@Getter
@Setter
public class UnidadPresupuestaria extends EntidadBasica {

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_ORDENADA = "UnidadPresupuestaria.buscarOrdenada";

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_POR_NIVEL = "UnidadPresupuestaria.buscarPorNivel";

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_UNIDAD_PRESUPUESTARIA_PADRE = "UnidadPresupuestaria.buscarPorIdPadre";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_CODIGO = "UnidadPresupuestaria.buscarPorCodigo ";
    
    /**
     * Nombre de consulta para buscar por codigo y sector.
     */
    public static final String BUSCAR_POR_CODIGO_Y_SECTOR = "UnidadPresupuestaria.buscarPorCodigoYSector";

    /**
     * Nombre de consulta para buscar por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "UnidadPresupuestaria.buscarPorNombre";

    /**
     * Nombre de consulta para buscar vigentes.
     */
    public static final String BUSCAR_VIGENTES = "UnidadPresupuestaria.buscarVigente";

    /**
     * Nombre de consulta para buscar vigentes.
     */
    public static final String BUSCAR_POR_SOCIEDAD = "UnidadPresupuestaria.buscarPorSociedad";
    
    /**
     * Contar por codigo interno.
     */
    public static final String CONTAR_POR_CODIGO_INTERNO = "UnidadPresupuestaria.contarPorCodigoInterno";
    /**
     * Contar por codigo interno ignorando un id.
     */
    public static final String CONTAR_POR_CODIGO_INTERNO_IGNORAR_ID = "UnidadPresupuestaria.contarPorCodigoInternoIgnorandoId";

    /**
     * Id.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "codigo_interno")
    private String codigoInterno;

    /**
     * nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "es_rrhh")
    private Boolean rrhh;

    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;

    /**
     * unidad organizacional.
     */
    @JoinColumn(name = "unidad_presupuestaria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * nivel.
     */
    @Column(name = "nivel")
    private Long nivel;

    /**
     * Grupo presupuestario.
     */
    @Column(name = "grupo_presupuestario")
    private String grupoPresupuestario;

    /**
     * Sociedad.
     */
    @Column(name = "sociedad")
    private String sociedad;

    /**
     * Centro de costo.
     */
    @Column(name = "centro_costo")
    private String centroCosto;

    /**
     * Centro gestor.
     */
    @Column(name = "centro_gestor")
    private String centroGestor;

    /**
     * Proyecto.
     */
    @Column(name = "proyecto")
    private String proyecto;

    /**
     *
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     *
     */
    @Column(name = "fondo")
    private String fondo;

    /**
     * SEctor.
     */
    @JoinColumn(name = "sector_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Sector sector;

    /**
     * programa.
     */
    @Column(name = "programa")
    @NotNull
    private String programa;

    /**
     *
     */
    @Column(name = "ruc")
    private String ruc;

    /**
     *
     */
    @Transient
    private Boolean seleccionado;
    /**
     *
     */
    @Transient
    private String nombreCompleto;
    
    /**
     * Lista de Certificaciones Presupuestarias de la Unidad Presupuestaria
     */
    @OneToMany(mappedBy = "unidadPresupuestaria")   
    private List<CertificacionPresupuestaria> listaCertificacionesPresupuestarias;
    

    /**
     * Constructor por defecto.
     */
    public UnidadPresupuestaria() {
        super();
        seleccionado = Boolean.FALSE;
    }

    public UnidadPresupuestaria(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        UnidadPresupuestaria o = unidadPresupuestaria;
        nombreCompleto = "";
        while (o != null) {
            nombreCompleto = o.getNombre().concat(" / ").concat(nombreCompleto);
            o = o.getUnidadPresupuestaria();
        }
        nombreCompleto = nombreCompleto.concat(nombre);
        return nombreCompleto;
    }

    /**
     * @param nombreCompleto the nombreCompleto to set
     */
    public void setNombreCompleto(final String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
 

}
