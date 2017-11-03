/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "desconcentrados_unidades_organizacionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o"
            + " WHERE o.vigente = true AND o.desconcentrado.id=?1"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_NOMBRE,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o"
            + " WHERE o.vigente = true AND o.desconcentrado.nombre like ?1"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o"
            + " WHERE o.vigente = true AND o.desconcentrado.servidorResponsable.id=?1"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_UNIDAD_ORGANIZACIONAL_ID,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o"
            + " WHERE o.vigente = true AND o.unidadOrganizacional.id=?1 ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_CODIGO_UNIDAD_ORGANIZACIONAL,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o"
            + " WHERE o.vigente = true AND o.unidadOrganizacional.codigo=?1 ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_VIGENTES,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o WHERE o.vigente=true"),
    @NamedQuery(name = DesconcentradoUnidadOrganizacional.BUSCAR_POR_DESCONCENTRADO_UNIDAD_ORGANIZACIONAL,
            query = "SELECT o FROM DesconcentradoUnidadOrganizacional o WHERE o.vigente=true AND"
            + " o.desconcentrado.id=?1 AND o.unidadOrganizacional.id=?2")

})
@Getter
@Setter
public class DesconcentradoUnidadOrganizacional extends EntidadBasica {

    /**
     * Variable para busqueda por desconcentrado id.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_ID
            = "DesconcentradoUnidadOrganizacional.buscarPorDesconcentradoId";

    /**
     * Variable para busqueda por desconcetrado nombre.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_NOMBRE
            = "DesconcentradoUnidadOrganizacional.buscarPorDesconcentradoNombre";

    /**
     * Variable para busqueda por desconcentrado servidor responsable.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID
            = "DesconcentradoUnidadOrganizacional.buscarPorDesconcentradoServidorResponsableId";

    /**
     * Variable para busqueda por unidad organizacional id.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL_ID
            = "DesconcentradoUnidadOrganizacional.buscarPorUnidadOrganizacionalId";

    /**
     * Variable para busqueda por codigo de la unidad organizacional.
     */
    public static final String BUSCAR_POR_CODIGO_UNIDAD_ORGANIZACIONAL
            = "DesconcentradoUnidadOrganizacional.buscarPorCodigoUnidadOrganizacional";

    /**
     * Variable para busqueda por codigo de la unidad organizacional.
     */
    public static final String BUSCAR_POR_CODIGO_UNIDAD_ORGANIZACIONAL_Y_FUNCIONES
            = "DesconcentradoUnidadOrganizacional.buscarPorCodigoUnidadOrganizacionalYFunciones";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DesconcentradoUnidadOrganizacional.buscarVigentes";

    /**
     *
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_UNIDAD_ORGANIZACIONAL
            = "DesconcentradoUnidadOrganizacional.buscarPorDesconcentradoUnidaOrganizacional";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia al desconcentrado
     */
    @JoinColumn(name = "desconcentrado_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Desconcentrado desconcentrado;

    /**
     * Referencia a unidad organizacional
     */
    @JoinColumn(name = "unidad_organizacional_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * Funciones para las cuales se escogen las unidades organizacionales
     */
    @Column(name = "funciones")
    @Basic(optional = false)
    @NotNull
    private String funciones;

    /**
     * Arreglo de codigo de funciones
     */
    @Transient
    private String[] funcionesArreglo;

    /**
     * Constructor.
     */
    public DesconcentradoUnidadOrganizacional() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DesconcentradoUnidadOrganizacional(final Long id) {
        super();
        this.id = id;
    }

    /**
     *
     * @return lista de funciones.
     */
    public String[] getFuncionesArreglo() {
        return this.funciones.split(",");
    }

}
