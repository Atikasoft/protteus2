package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.NivelEstudioHijoEnLicenciaEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de movimientos por licencia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "licencias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Licencia.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Licencia o where o.vigente=true and o.movimiento.id=?1"),
    @NamedQuery(name = Licencia.BUSCAR_LICENCIAS_EN_CURSO,
    query = "Select o from Licencia o where o.vigente=true and o.movimiento.tipoIdentificacion=?1 and "
    + " o.movimiento.numeroIdentificacion=?2 AND o.movimiento.tramite.codigoFase='REG' AND "
    + " o.movimiento.tramite.tipoMovimiento.id = ?3 AND "
    + " ?4 BETWEEN o.movimiento.rigeApartirDe AND o.movimiento.fechaHasta")
})
public class Licencia extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Licencia.buscarPorMovimiento";

    /**
     * Nombre de la consulta que recupera las licencias registradas por servidor.
     */
    public final static String BUSCAR_LICENCIAS_EN_CURSO = "Licencia.buscarLicenciasEnCurso";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Fecha en la que ocurrio el hecho.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ocurrio_hecho")
    private Date fechaOcurrioHecho;

    /**
     * Fecha en la que presento la documentacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_presenta_documento")
    private Date fechaPresentaDocumento;

    /**
     * Indica si o no devenga.
     */
    @Column(name = "devengar")
    private Boolean devengar;

    /**
     * Indica el periodo a devengar.
     */
    @Column(name = "devengar_periodo")
    private String devengarPeriodo;

    /**
     * Indica el valor a devengar.
     */
    @Column(name = "devengar_valor")
    private Integer devengarValor;

    /**
     * Observacion del devengamiento.
     */
    @Column(name = "devengar_observacion")
    private String devengarObservacion;

    /**
     * Indica el tipo de nacimiento (NORMAL, CESAREA, MULTIPLE )
     */
    @Column(name = "tipo_nacimiento")
    private String tipoNacimiento;

    /**
     * Fecha inicio que devenga.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio_a_devengar")
    private Date fechaInicioADevengar;

    /**
     * Fecha inicio que devenga.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_final_a_devengar")
    private Date fechaFinalADevengar;

    /**
     * Horas mensuales de representacion laboral.
     */
    @Column(name = "horas_mensuales_representacion_laboral")
    private Integer horasMensualesRepresentacionLaboral;

    /**
     * Nombre del hijo para permiso por matricula.
     */
    @Column(name = "matricula_nombre_hijo")
    private String matriculaNombreHijo;

    /**
     * Nivel de estudio del hijo para permiso por matricula.
     *
     * @see NivelEstudioHijoEnLicenciaEnum
     */
    @Column(name = "matricula_nivel_estudio")
    private String matriculaNivelEstudio;

    /**
     * Referencia a movimiento.
     */
    @JoinColumn(name = "movimiento_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Lista de licencias.
     */
    @OneToMany(mappedBy = "licencia")
    private List<LicenciaHorario> listaLicenciasHorarios;

    /**
     * Constructor.
     */
    public Licencia() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Licencia(final Long id) {
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
     * @return the fechaOcurrioHecho
     */
    public Date getFechaOcurrioHecho() {
        return fechaOcurrioHecho;
    }

    /**
     * @return the fechaPresentaDocumento
     */
    public Date getFechaPresentaDocumento() {
        return fechaPresentaDocumento;
    }

    /**
     * @return the devengar
     */
    public Boolean getDevengar() {
        return devengar;
    }

    /**
     * @return the devengarPeriodo
     */
    public String getDevengarPeriodo() {
        return devengarPeriodo;
    }

    /**
     * @return the devengarValor
     */
    public Integer getDevengarValor() {
        return devengarValor;
    }

    /**
     * @return the devengarObservacion
     */
    public String getDevengarObservacion() {
        return devengarObservacion;
    }

    /**
     * @return the tipoNacimiento
     */
    public String getTipoNacimiento() {
        return tipoNacimiento;
    }

    /**
     * @return the fechaInicioADevengar
     */
    public Date getFechaInicioADevengar() {
        return fechaInicioADevengar;
    }

    /**
     * @return the fechaFinalADevengar
     */
    public Date getFechaFinalADevengar() {
        return fechaFinalADevengar;
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
     * @param fechaOcurrioHecho the fechaOcurrioHecho to set
     */
    public void setFechaOcurrioHecho(final Date fechaOcurrioHecho) {
        this.fechaOcurrioHecho = fechaOcurrioHecho;
    }

    /**
     * @param fechaPresentaDocumento the fechaPresentaDocumento to set
     */
    public void setFechaPresentaDocumento(final Date fechaPresentaDocumento) {
        this.fechaPresentaDocumento = fechaPresentaDocumento;
    }

    /**
     * @param devengar the devengar to set
     */
    public void setDevengar(final Boolean devengar) {
        this.devengar = devengar;
    }

    /**
     * @param devengarPeriodo the devengarPeriodo to set
     */
    public void setDevengarPeriodo(final String devengarPeriodo) {
        this.devengarPeriodo = devengarPeriodo;
    }

    /**
     * @param devengarValor the devengarValor to set
     */
    public void setDevengarValor(final Integer devengarValor) {
        this.devengarValor = devengarValor;
    }

    /**
     * @param devengarObservacion the devengarObservacion to set
     */
    public void setDevengarObservacion(final String devengarObservacion) {
        this.devengarObservacion = devengarObservacion;
    }

    /**
     * @param tipoNacimiento the tipoNacimiento to set
     */
    public void setTipoNacimiento(final String tipoNacimiento) {
        this.tipoNacimiento = tipoNacimiento;
    }

    /**
     * @param fechaInicioADevengar the fechaInicioADevengar to set
     */
    public void setFechaInicioADevengar(final Date fechaInicioADevengar) {
        this.fechaInicioADevengar = fechaInicioADevengar;
    }

    /**
     * @param fechaFinalADevengar the fechaFinalADevengar to set
     */
    public void setFechaFinalADevengar(final Date fechaFinalADevengar) {
        this.fechaFinalADevengar = fechaFinalADevengar;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the listaLicenciasHorarios
     */
    public List<LicenciaHorario> getListaLicenciasHorarios() {
        return listaLicenciasHorarios;
    }

    /**
     * @param listaLicenciasHorarios the listaLicenciasHorarios to set
     */
    public void setListaLicenciasHorarios(final List<LicenciaHorario> listaLicenciasHorarios) {
        this.listaLicenciasHorarios = listaLicenciasHorarios;
    }

    /**
     * @return the horasMensualesRepresentacionLaboral
     */
    public Integer getHorasMensualesRepresentacionLaboral() {
        return horasMensualesRepresentacionLaboral;
    }

    /**
     * @param horasMensualesRepresentacionLaboral the horasMensualesRepresentacionLaboral to set
     */
    public void setHorasMensualesRepresentacionLaboral(final Integer horasMensualesRepresentacionLaboral) {
        this.horasMensualesRepresentacionLaboral = horasMensualesRepresentacionLaboral;
    }

    /**
     * @return the matriculaNombreHijo
     */
    public String getMatriculaNombreHijo() {
        return matriculaNombreHijo;
    }

    /**
     * @return the matriculaNivelEstudio
     */
    public String getMatriculaNivelEstudio() {
        return matriculaNivelEstudio;
    }

    /**
     * @param matriculaNombreHijo the matriculaNombreHijo to set
     */
    public void setMatriculaNombreHijo(final String matriculaNombreHijo) {
        this.matriculaNombreHijo = matriculaNombreHijo;
    }

    /**
     * @param matriculaNivelEstudio the matriculaNivelEstudio to set
     */
    public void setMatriculaNivelEstudio(final String matriculaNivelEstudio) {
        this.matriculaNivelEstudio = matriculaNivelEstudio;
    }
}
