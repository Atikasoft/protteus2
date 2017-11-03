package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "instituciones", catalog = "sch_proteus")
@Setter
@Getter
public class Institucion extends EntidadBasica {

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ruc")
    private String ruc;

    /**
     * Permite contar el número de puestos.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_puestos")
    private Long contadorPuestos;

    /**
     * Permite contar el número de partida individua;.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_partida_individual")
    private Long contadorPartidaIndividual;

    /**
     * Permite contar el número de partida individua;.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_accion_personal_vacaciones")
    private Long contadorAccionPersonalVacaciones;

    /**
     * Permite contar el numero de accion de personal de liquidaciones de vacaciones.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_accion_personal_liquidacion_vacaciones")
    private Long contadorAccionPersonalLiquidacionVacaciones;

    /**
     * Permite contar el numero de accion de personal de otras instituciones registro.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_accion_personal_otras_instituciones_registro")
    private Long contadorAccionPersonalOtrasInstitucionesRegistro;

    /**
     * Permite contar el numero de accion de personal de otras instituciones terminacion.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_accion_personal_otras_instituciones_terminacion")
    private Long contadorAccionPersonalOtrasInstitucionesTerminacion;

    /**
     * Constructor.
     */
    public Institucion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Institucion(Long id) {
        super();
        this.id = id;
    }

}
