/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "desconcentrados_historicos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DesconcentradoHistorico.BUSCAR_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM DesconcentradoHistorico o"
                    + " WHERE o.vigente = true AND o.desconcentrado.id like ?1 ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = DesconcentradoHistorico.BUSCAR_POR_DESCONCENTRADO_NOMBRE,
            query = "SELECT o FROM DesconcentradoHistorico o"
                    + " WHERE o.vigente = true AND o.desconcentrado.nombre like ?1 ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = DesconcentradoHistorico.BUSCAR_POR_SERVIDOR_RESPONSABLE_ID,
            query = "SELECT o FROM DesconcentradoHistorico o"
                    + " WHERE o.vigente = true AND o.servidorResponsable.id=?1 ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = DesconcentradoHistorico.BUSCAR_VIGENTES,
            query = "SELECT o FROM DesconcentradoHistorico o WHERE o.vigente=true ORDER BY o.fechaCreacion DESC")
})
@Getter
@Setter
public class DesconcentradoHistorico extends EntidadBasica {

    /**
     * Variable para busqueda por desconcentrado.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_ID = "DesconcentradoHistorico.buscarPorDesconcentradoId";

    /**
     * Variable para busqueda por desconcentradoNombre.
     */
    public static final String BUSCAR_POR_DESCONCENTRADO_NOMBRE = "DesconcentradoHistorico.buscarPorDesconcentradoNombre ";

    /**
     * Variable para busqueda por servidor responsable.
     */
    public static final String BUSCAR_POR_SERVIDOR_RESPONSABLE_ID = "DesconcentradoHistorico.buscarPorServidorResponsableId";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DesconcentradoHistorico.buscarVigentes";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia al desconcentrado del cual es historico
     */
    @JoinColumn(name = "desconcentrado_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Desconcentrado desconcentrado;

    /**
     * Referencia a servidor responsable.
     */
    @JoinColumn(name = "servidor_responsable_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorResponsable;
    
    /**
     * Campo Nombre del desconcentrado.
     * 
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "desconcentrado_nombre")
    private String desconcentradoNombre;
    
    /**
     * Campo Nombre del desconcentrado.
     * 
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "servidor_responsable_nombre")
    private String servidorResponsableNombre;

    /**
     * Constructor.
     */
    public DesconcentradoHistorico() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DesconcentradoHistorico(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(UtilCadena.concatenar(id, "-desconcentrado- ", desconcentradoNombre,
                "-responsable-", servidorResponsable != null ? servidorResponsable.getApellidosNombres() : null));
    }

}
