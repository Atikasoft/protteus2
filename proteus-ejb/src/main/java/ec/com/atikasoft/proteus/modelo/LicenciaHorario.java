package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.TipoHorarioEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Horarios de licencias.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "licencias_horarios", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = LicenciaHorario.BUSCAR_POR_LICENCIA,
    query = "Select o from LicenciaHorario o where o.licencia.id=?1 and o.vigente=true"),
    @NamedQuery(name = LicenciaHorario.ELIMINAR_POR_LICENCIA,
    query = "Update LicenciaHorario o set o.vigente=false, o.fechaActualizacion=?1, o.usuarioActualizacion=?2 where o.licencia.id=?3")
})
public class LicenciaHorario extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_LICENCIA = "LicenciaHorario.buscarPorLicencia";

    /**
     * Nombre de consulta.
     */
    public static final String ELIMINAR_POR_LICENCIA = "LicenciaHorario.actualizarPorLicencia";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Dia de la semana.
     */
    @Column(name = "dia")
    private Integer dia;

    /**
     * Fecha en la que ocurrio el hecho.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_inicio")
    private Date horaInicio;

    /**
     * Fecha en la que presento la documentacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_fin")
    private Date horaFin;

    /**
     * Total de minutos.
     */
    @Column(name = "total_minutos")
    private Long totalMinutos;

    /**
     * Tipo de horario LICENCIA_PERMISO = 'L' RECUPERACION = 'R'.
     *
     * @see TipoHorarioEnum
     *
     */
    @Column(name = "tipo")
    @NotNull
    private String tipo = TipoHorarioEnum.LICENCIA_PERMISO.getCodigo();

    /**
     * Referencia a licencia.
     */
    @JoinColumn(name = "licencia_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Licencia licencia;

    /**
     * Constructor.
     */
    public LicenciaHorario() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public LicenciaHorario(final Long id) {
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
     * @return the dia
     */
    public Integer getDia() {
        return dia;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * @return the totalMinutos
     */
    public Long getTotalMinutos() {
        return totalMinutos;
    }

    /**
     * @return the licencia
     */
    public Licencia getLicencia() {
        return licencia;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(final Integer dia) {
        this.dia = dia;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(final Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(final Date horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @param totalMinutos the totalMinutos to set
     */
    public void setTotalMinutos(final Long totalMinutos) {
        this.totalMinutos = totalMinutos;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(final Licencia licencia) {
        this.licencia = licencia;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }
}
