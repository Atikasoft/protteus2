/*
 *  UnidadOrganizacional.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  26/09/2013
 *
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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@XmlRootElement(name = "unidadOrganizacional")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "unidades_organizacionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_ORDENADA,
            query = "Select ug FROM UnidadOrganizacional ug where ug.vigente=true and ug.idUnidadOrganizacional is null"),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_POR_NIVEL,
            query = "Select ug FROM UnidadOrganizacional ug where ug.vigente=true and ug.nivel=?1 and ug.id=?2"),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_ID_UBICACION,
            query = "Select ug FROM UnidadOrganizacional ug where ug.vigente=true and ug.idUnidadOrganizacional =?1   "),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_VIGENTES,
            query = "Select ug FROM UnidadOrganizacional ug where ug.vigente=true order by ug.ruta "),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_POR_NOMBRE,
            query = "Select ug FROM UnidadOrganizacional ug where ug.vigente=true and ug.ruta like ?1 order by ug.ruta "),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_POR_NEMONICO,
            query = "SELECT a FROM UnidadOrganizacional a where a.vigente=true and a.codigo=?1"),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM UnidadOrganizacional a where a.vigente=true and a.codigo LIKE ?1 order by a.ruta"),
    @NamedQuery(name = UnidadOrganizacional.BUSCAR_POR_RELOJ_ID,
            query = "SELECT a FROM UnidadOrganizacional a INNER JOIN a.relojesUnidadesOrganizacionales ruo"
            + " WHERE a.vigente=true AND ruo.vigente=true AND ruo.reloj.id=?1")

})
@Getter
@Setter
public class UnidadOrganizacional extends EntidadBasica {

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_ORDENADA = "UnidadOrganizacional.buscarOrdenada";

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_POR_NIVEL = "UnidadOrganizacional.buscarPorNivel";

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_ID_UBICACION = "UnidadOrganizacional.buscarPorId";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "UnidadOrganizacional.buscarporNemonico ";

    /**
     * Nombre de consulta para buscar por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "UnidadOrganizacional.buscarPorNombre";

    /**
     * Nombre de consulta para buscar vigentes.
     */
    public static final String BUSCAR_VIGENTES = "UnidadOrganizacional.buscarVigente";
    /**
     *
     */
    public static final String BUSCAR_POR_CODIGO = "UnidadOrganizacional.buscarPorCodigo";

    /**
     *
     */
    public static final String BUSCAR_POR_RELOJ_ID = "UnidadOrganizacional.buscarPorRelojId";

    /**
     * Id.
     */
    @Id
    @XmlElement
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * nombre.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    /**
     * tipo.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "es_rrhh")
    private Boolean rrhh;

    /**
     * es agrupador.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "es_agrupador")
    private Boolean esAgrupador;

    /**
     * control.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "control")
    private Boolean control;

    /**
     * legaliza.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "legaliza")
    private Boolean legaliza;

    /**
     * codigo.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;

    /**
     * unidad organizacional.
     */
    @JoinColumn(name = "unidad_organizacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * unidad organizacional.
     */
    @XmlElement
    @Column(name = "unidad_organizacional_id")
    private Long idUnidadOrganizacional;

    /**
     * nivel.
     */
    @XmlElement
    @Column(name = "nivel")
    private Long nivel;

    /**
     * descentralizado.
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "desconcentrado")
    private Boolean desconcentrado;

    @Column(name = "ruta")
    private String ruta;

    /**
     * Lista de nivel Ocupacional.
     */
    @OneToMany(mappedBy = "unidadOrganizacional")
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales;

    /**
     * Relojes asignados a la Unidad Organizacional
     */
    @OneToMany(mappedBy = "unidadOrganizacional")
    private List<RelojUnidadOrganizacional> relojesUnidadesOrganizacionales;

    /**
     *
     */
    @XmlElement
    @Transient
    private Boolean seleccionado;

    /**
     *
     */
    @XmlElement
    @Transient
    private String nombreCompleto;

    /**
     *
     */
    @XmlElement
    @Transient
    private Boolean tieneConfiguracionInterna;
    
    /**
     * 
     */
    @XmlElement
    @Transient
    private Boolean tieneRelojesAsignadosEnBaseDatos;

    /**
     * Constructor por defecto.
     */
    public UnidadOrganizacional() {
        super();
        seleccionado = Boolean.FALSE;
    }

    public UnidadOrganizacional(Long id) {
        this.id = id;
    }

    /**
     * Indica si hay relojes asociados a la unidad organizacional en base de datos
     *
     * @return
     */
    public Boolean getTieneRelojesAsignadosEnBaseDatos() {
        if (relojesUnidadesOrganizacionales != null) {
            for (RelojUnidadOrganizacional ruo : this.relojesUnidadesOrganizacionales ) {
                if (ruo.getVigente()) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

}
