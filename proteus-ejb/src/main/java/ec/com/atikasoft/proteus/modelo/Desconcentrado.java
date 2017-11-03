/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "desconcentrados", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Desconcentrado.BUSCAR_POR_NOMBRE,
            query = "SELECT o FROM Desconcentrado o WHERE o.vigente = true AND o.nombre like ?1 order by o.nombre"),
    @NamedQuery(name = Desconcentrado.BUSCAR_POR_SERVIDOR_RESPONSABLE_ID,
            query = "SELECT o FROM Desconcentrado o WHERE o.vigente = true AND o.servidorResponsable.id=?1"),
    @NamedQuery(name = Desconcentrado.BUSCAR_VIGENTES,
            query = "SELECT o FROM Desconcentrado o WHERE o.vigente=true")
})
@Getter
@Setter
public class Desconcentrado extends EntidadBasica {

    /**
     * Variable para busqueda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Desconcentrado.buscarPorNombre ";

    /**
     * Variable para busqueda por servidor responsable.
     */
    public static final String BUSCAR_POR_SERVIDOR_RESPONSABLE_ID = "Desconcentrado.buscarPorServidorResponsableId";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Desconcentrado.buscarVigentes";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Referencia a servidor responsable.
     */
    @JoinColumn(name = "servidor_responsable_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorResponsable;

    /**
     *
     */
    @OneToMany(mappedBy = "desconcentrado")
    List<DesconcentradoUnidadOrganizacional> listaUnidadOrganizacional;

    /**
     *
     */
    @OneToMany(mappedBy = "desconcentrado")
    List<DesconcentradoApoyo> listaApoyos;

    /**
     * Constructor.
     */
    public Desconcentrado() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Desconcentrado(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(UtilCadena.concatenar(id, "-desconcentrado- ", nombre,
                "-responsable-", servidorResponsable != null ? servidorResponsable.getApellidosNombres() : null));
    }

}
