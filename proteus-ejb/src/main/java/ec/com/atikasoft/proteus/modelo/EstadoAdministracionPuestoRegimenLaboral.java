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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "estados_administracion_puestos_regimen_laboral", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EstadoAdministracionPuestoRegimenLaboral.BUSCAR_TODOS_VIGENTES,
    query = "SELECT er FROM EstadoAdministracionPuestoRegimenLaboral er where er.vigente=true"),
    @NamedQuery(name = EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_ESTADO_ADMINISTRACION_PUESTO_ID,
    query = "SELECT er FROM EstadoAdministracionPuestoRegimenLaboral er where er.estadoAdministracionPuesto.id=?1 and er.vigente=true"),
    @NamedQuery(name = EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_REGIMEN_LABORAL_ID,
    query = "SELECT er FROM EstadoAdministracionPuestoRegimenLaboral er where er.regimenLaboral.id=?1 and "
    + "er.vigente=true"),
    @NamedQuery(name = EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_ESTADO_ADMINISTRACION_ID_Y_REGIMEN_LABORAL_ID,
    query = "SELECT er FROM EstadoAdministracionPuestoRegimenLaboral er WHERE er.estadoAdministracionPuesto.id=?1 AND er.regimenLaboral.id=?2 AND er.vigente=true")
})
@Getter
@Setter
public class EstadoAdministracionPuestoRegimenLaboral extends EntidadBasica {

    /**
     * 
     */
    public static final String BUSCAR_TODOS_VIGENTES = "EstadoAdministracionPuestoRegimenLaboral.buscarTodosVigentes";
    /**
     * Nombre para la consulta de buscar por estado administracion puesto id.
     */
    public static final String BUSCAR_POR_ESTADO_ADMINISTRACION_PUESTO_ID = "EstadoAdministracionPuestoRegimenLaboral.buscarPorEstadoAdministracionPuestoId";

    /**
     * Nombre para la consulta de buscar por regimen laboral id.
     */
    public static final String BUSCAR_POR_REGIMEN_LABORAL_ID =
            "EstadoAdministracionPuestoRegimenLaboral.buscarPorRegimenLaboralId";

    /**
     * Nombre de la consulta para buscar por estado administracion puesto id y regimen laboral id.
     */
    public static final String BUSCAR_POR_ESTADO_ADMINISTRACION_ID_Y_REGIMEN_LABORAL_ID =
            "EstadoAdministracionPuestoRegimenLaboral.buscarPorEstadoAdministracionIdYRegimenLaboralId";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia del estado administración de puesto.
     */
    @JoinColumn(name = "estados_administracion_puestos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EstadoAdministracionPuesto estadoAdministracionPuesto;

    /**
     * Referencia de regimen laboral.
     */
    @JoinColumn(name = "regimen_laboral_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;

    /**
     * Constructor.
     */
    public EstadoAdministracionPuestoRegimenLaboral() {
        super();
    }

    /**
     * constructor.
     *
     * @param id id
     */
    public EstadoAdministracionPuestoRegimenLaboral(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
