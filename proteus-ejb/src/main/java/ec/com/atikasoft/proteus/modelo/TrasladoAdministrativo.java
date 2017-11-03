package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de traslados administrativos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "traslados_administrativos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TrasladoAdministrativo.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from TrasladoAdministrativo o where o.vigente=true and o.movimiento.id=?1")
})
public class TrasladoAdministrativo extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_MOVIMIENTO = "TrasladoAdministrativo.buscarPorMovimiento";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Partida individual.
     */
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * Partida general.
     */
    @Column(name = "partida_general")
    private String partidaGeneral;

    /**
     * Denominacion Puesto Nombre.
     */
    @Column(name = "denominacion_puesto_nombre")
    private String denominacionPuestoNombre;

    /**
     * Id de Denominacion puesto.
     */
    @Column(name = "denominacion_puesto_id")
    private Long denominacionPuestoId;

    /**
     * Grupo ocupacional.
     */
    @Column(name = "grupo_ocupacional_nombre")
    private String grupoOcupacionalNombre;

    /**
     * Grupo ocupacional id.
     */
    @Column(name = "grupo_ocupacional_id")
    private Long grupoOcupacionalId;

    /**
     * RMU.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * Lugar nombre.
     */
    @Column(name = "lugar_nombre")
    private String lugarNombre;

    /**
     * lugar id.
     */
    @Column(name = "lugar_id")
    private Long lugarId;

    /**
     * Identificador de unidad administrativa en el core.
     */
    @Column(name = "unidad_adminitrativa_core_id")
    private Long unidadAdminitrativaCoreId;

    /**
     * Nombre de unidad administrativa en el core.
     */
    @Column(name = "unidad_administrativa_core_nombre")
    private String unidadAdministrativaCoreNombre;

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public TrasladoAdministrativo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TrasladoAdministrativo(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @return the partidaGeneral
     */
    public String getPartidaGeneral() {
        return partidaGeneral;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(final String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @param partidaGeneral the partidaGeneral to set
     */
    public void setPartidaGeneral(final String partidaGeneral) {
        this.partidaGeneral = partidaGeneral;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the denominacionPuestoNombre
     */
    public String getDenominacionPuestoNombre() {
        return denominacionPuestoNombre;
    }

    /**
     * @param denominacionPuestoNombre the denominacionPuestoNombre to set
     */
    public void setDenominacionPuestoNombre(final String denominacionPuestoNombre) {
        this.denominacionPuestoNombre = denominacionPuestoNombre;
    }

    /**
     * @return the denominacionPuestoId
     */
    public Long getDenominacionPuestoId() {
        return denominacionPuestoId;
    }

    /**
     * @param denominacionPuestoId the denominacionPuestoId to set
     */
    public void setDenominacionPuestoId(final Long denominacionPuestoId) {
        this.denominacionPuestoId = denominacionPuestoId;
    }

    /**
     * @return the grupoOcupacionalNombre
     */
    public String getGrupoOcupacionalNombre() {
        return grupoOcupacionalNombre;
    }

    /**
     * @param grupoOcupacionalNombre the grupoOcupacionalNombre to set
     */
    public void setGrupoOcupacionalNombre(final String grupoOcupacionalNombre) {
        this.grupoOcupacionalNombre = grupoOcupacionalNombre;
    }

    /**
     * @return the grupoOcupacionalId
     */
    public Long getGrupoOcupacionalId() {
        return grupoOcupacionalId;
    }

    /**
     * @param grupoOcupacionalId the grupoOcupacionalId to set
     */
    public void setGrupoOcupacionalId(final Long grupoOcupacionalId) {
        this.grupoOcupacionalId = grupoOcupacionalId;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the lugarNombre
     */
    public String getLugarNombre() {
        return lugarNombre;
    }

    /**
     * @param lugarNombre the lugarNombre to set
     */
    public void setLugarNombre(final String lugarNombre) {
        this.lugarNombre = lugarNombre;
    }

    /**
     * @return the lugarId
     */
    public Long getLugarId() {
        return lugarId;
    }

    /**
     * @param lugarId the lugarId to set
     */
    public void setLugarId(final Long lugarId) {
        this.lugarId = lugarId;
    }

    /**
     * @return the unidadAdminitrativaCoreId
     */
    public Long getUnidadAdminitrativaCoreId() {
        return unidadAdminitrativaCoreId;
    }

    /**
     * @return the unidadAdministrativaCoreNombre
     */
    public String getUnidadAdministrativaCoreNombre() {
        return unidadAdministrativaCoreNombre;
    }

    /**
     * @param unidadAdminitrativaCoreId the unidadAdminitrativaCoreId to set
     */
    public void setUnidadAdminitrativaCoreId(final Long unidadAdminitrativaCoreId) {
        this.unidadAdminitrativaCoreId = unidadAdminitrativaCoreId;
    }

    /**
     * @param unidadAdministrativaCoreNombre the unidadAdministrativaCoreNombre to set
     */
    public void setUnidadAdministrativaCoreNombre(final String unidadAdministrativaCoreNombre) {
        this.unidadAdministrativaCoreNombre = unidadAdministrativaCoreNombre;
    }
}
