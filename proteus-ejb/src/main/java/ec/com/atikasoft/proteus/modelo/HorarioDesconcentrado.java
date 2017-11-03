/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
@Table(name = "horarios_desconcentrados", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = HorarioDesconcentrado.BUSCAR_VIGENTES,
            query = "SELECT o FROM HorarioDesconcentrado o WHERE o.vigente = true"),
    @NamedQuery(name = HorarioDesconcentrado.BUSCAR_VIGENTES_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM HorarioDesconcentrado o WHERE o.vigente = true AND o.desconcentrado.id = ?1"),
    @NamedQuery(name = HorarioDesconcentrado.BUSCAR_TODOS_POR_DESCONCENTRADO_ID,
            query = "SELECT o FROM HorarioDesconcentrado o WHERE o.desconcentrado.id = ?1"),
    @NamedQuery(name = HorarioDesconcentrado.BUSCAR_VIGENTES_POR_HORARIO_ID,
            query = "SELECT o FROM HorarioDesconcentrado o WHERE o.vigente = true AND o.horario.id = ?1")
})
@Setter
@Getter
public class HorarioDesconcentrado extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_VIGENTES = "HorarioDesconcentrado.buscarVigentes";

    /**
     *
     */
    public static final String BUSCAR_VIGENTES_POR_DESCONCENTRADO_ID = 
            "HorarioDesconcentrado.buscarVigentesPorDesconcentradoId";

    /**
     *
     */
    public static final String BUSCAR_TODOS_POR_DESCONCENTRADO_ID = "HorarioDesconcentrado.buscarTodosPorDesconcentradoId";
    
    /**
     *
     */
    public static final String BUSCAR_VIGENTES_POR_HORARIO_ID = "HorarioDesconcentrado.buscarPorHorarioId";

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @JoinColumn(name = "horario_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Horario horario;

    /**
     *
     */
    @JoinColumn(name = "desconcentrado_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Desconcentrado desconcentrado;

    /**
     *
     */
    public HorarioDesconcentrado() {
    }

    /**
     *
     * @param d
     * @param h
     */
    public HorarioDesconcentrado(Desconcentrado d, Horario h) {
        this.desconcentrado = d;
        this.horario = h;
    }

}
