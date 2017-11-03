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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Leydis Garzon
 */
@Entity
@Table(name = "horarios", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Horario.BUSCAR_TODOS_VIGENTES, 
            query = "SELECT o FROM Horario o WHERE o.vigente = true ORDER BY o.nombre"),
    @NamedQuery(name = Horario.BUSCAR_POR_NOMBRE, 
            query = "SELECT o FROM Horario o WHERE o.vigente = true AND o.nombre LIKE ?1 ")
})
@Getter
@Setter
public class Horario extends EntidadBasica {

    /**
     * Nombre de la consulta busqueda de todos los horarios vigente
     */
    public static final String BUSCAR_TODOS_VIGENTES = "Horario.buscarTodosVigentes";

    /**
     * Nombre de la consulta busqueda por nombre
     */
    public static final String BUSCAR_POR_NOMBRE = "Horario.buscarPorNombre";

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
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * 
     */
    @OneToMany(mappedBy = "horario", fetch = FetchType.EAGER)
    private List<HorarioDetalle> horarioDetalles;
    
    /**
     * 
     */
    public Horario () {}
    
    /**
     * 
     * @param id 
     */
    public Horario (Long id) {
        this.id = id;
    }

}
