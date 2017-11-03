/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@Table(name = "desconcentrados_apoyos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_TODOS_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM DesconcentradoApoyo o WHERE o.desconcentrado.id=?1"),
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM DesconcentradoApoyo o WHERE o.vigente = true AND o.desconcentrado.id=?1"),
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_POR_DESCONCENTRADO_NOMBRE,
            query = "SELECT o FROM DesconcentradoApoyo o WHERE o.vigente = true AND o.desconcentrado.nombre like ?1"),
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID,
            query = "SELECT o FROM DesconcentradoApoyo o"
                    + " WHERE o.vigente = true AND o.desconcentrado.servidorResponsable.id=?1"),
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_POR_SERVIDOR_APOYO_ID,
            query = "SELECT o FROM DesconcentradoApoyo o WHERE o.vigente = true AND o.servidorApoyo.id=?1"),
    @NamedQuery(name = DesconcentradoApoyo.BUSCAR_VIGENTES,
            query = "SELECT o FROM DesconcentradoApoyo o WHERE o.vigente=true")
})
@Getter
@Setter
public class DesconcentradoApoyo extends EntidadBasica {

    /**
     * Variable para busqueda por desconcetrado Id.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_ID = "DesconcentradoApoyo.buscarPorDesconcentradoId";
    
    /**
     * Variable para busqueda por desconcetrado Id.
     */
    public static final String BUSCAR_TODOS_POR_DESCONCENTRADO_ID = "DesconcentradoApoyo.buscarTodosPorDesconcentradoId";
    
    /**
     * Variable para busqueda por desconcetrado nombre.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_NOMBRE = "DesconcentradoApoyo.buscarPorDesconcentradoNombre";

    /**
     * Variable para busqueda por desconcentrado servidor responsable.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_SERVIDOR_RESPONSABLE_ID
            = "DesconcentradoApoyo.buscarPorDesconcentradoServidorResponsableId";

    /**
     * Variable para busqueda por listado de servidores de apoyo.
     */
    public static final String BUSCAR_POR_SERVIDOR_APOYO_ID = "DesconcentradoApoyo.buscarPorServidorApoyoId";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DesconcentradoApoyo.buscarVigentes";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia al desconcetrado
     */
    @JoinColumn(name = "desconcentrado_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Desconcentrado desconcentrado;

    /**
     * Referencia a servidor de apoyo.
     */
    @JoinColumn(name = "servidor_apoyo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorApoyo;

    /**
     * Constructor.
     */
    public DesconcentradoApoyo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DesconcentradoApoyo(final Long id) {
        super();
        this.id = id;
    }

}
