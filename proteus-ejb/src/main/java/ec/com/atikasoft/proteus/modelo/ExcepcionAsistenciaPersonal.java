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
@Table(name = "excepciones_asistencia_personal", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ExcepcionAsistenciaPersonal.BUSCAR_TODOS_VIGENTES, 
            query = "SELECT o FROM ExcepcionAsistenciaPersonal o WHERE o.vigente = true"
                    + " ORDER BY o.servidor.apellidosNombres"),
    @NamedQuery(name = ExcepcionAsistenciaPersonal.BUSCAR_VIGENTES_POR_SERVIDOR_ID, 
            query = "SELECT o FROM ExcepcionAsistenciaPersonal o WHERE o.vigente = true AND o.servidor.id=?1")
})
@Getter
@Setter
public class ExcepcionAsistenciaPersonal extends EntidadBasica {

    /**
     * Nombre de la consulta busqueda de todos los relojes vigente
     */
    public static final String BUSCAR_TODOS_VIGENTES = "ExcepcionAsistenciaPersonal.buscarTodosVigentes";

    /**
     * Nombre de la consulta busqueda por codigo
     */
    public static final String BUSCAR_VIGENTES_POR_SERVIDOR_ID = "ExcepcionAsistenciaPersonal.buscarPorServidorId";

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;
    
    @Column(name = "justificacion", nullable = false)   
    private String justificacion;
    
    /**
     * Constructor por defecto
     */
    public ExcepcionAsistenciaPersonal() {
        super();
    }
    
    public ExcepcionAsistenciaPersonal(final Servidor servidor, final String justifucacion) {
        this.setServidor(servidor);
        this.setJustificacion(justificacion);
    }

}
