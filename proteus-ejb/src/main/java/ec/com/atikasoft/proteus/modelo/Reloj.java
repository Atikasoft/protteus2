/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
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
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "relojes", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Reloj.BUSCAR_TODOS_VIGENTES, 
            query = "SELECT o FROM Reloj o WHERE o.vigente = true ORDER BY o.nombre"),
    @NamedQuery(name = Reloj.BUSCAR_VIGENTES_POR_CODIGO, 
            query = "SELECT o FROM Reloj o WHERE o.vigente = true AND o.codigo=?1")
})
@Getter
@Setter
public class Reloj extends EntidadBasica {

    /**
     * Nombre de la consulta busqueda de todos los relojes vigente
     */
    public static final String BUSCAR_TODOS_VIGENTES = "Reloj.buscarTodosVigentes";

    /**
     * Nombre de la consulta busqueda por codigo
     */
    public static final String BUSCAR_VIGENTES_POR_CODIGO = "Reloj.buscarVegentesPorCodigo";

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 
     */
    @Column(name = "codigo", nullable = false)
    private String codigo;
    
    /**
     * 
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    /**
     * 
     */
    @Column(name = "ip", nullable = false)
    private String ip;
    
    /**
     * 
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalogo_modelo_id")
    private Catalogo catalogoModelo;

    /**
     * 
     */
    @OneToMany(mappedBy = "reloj")
    private List<RelojUnidadOrganizacional> relojesUnidadesOrganizacionales;
    
    /**
     * 
     */
    public Reloj () {
        super();
    }
    
    /**
     * 
     * @param id 
     */
    public Reloj (Long id) {
        this.id = id;
    }

}
