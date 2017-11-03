package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "cesaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Cesacion.BUSCAR_POR_MOVIMIENTO,
    query = "Select o from Cesacion o where o.vigente=true and o.movimiento.id=?1"),
    @NamedQuery(name = Cesacion.BUSCAR_POR_TRAMITE,
    query = "Select o from Cesacion o where o.vigente=true and o.movimiento.vigente=true and o.movimiento.tramite.id=?1")
})
public class Cesacion extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "Cesacion.buscarPorMovimientoId";

    /**
     *
     */
    public final static String BUSCAR_POR_TRAMITE = "Cesacion.buscarPorTramite";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_fallecimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFallecimiento;

    @Column(name = "caso_fallecimiento")
    private String casoFallecimiento;

    @Column(name = "fecha_inicio_puesto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicioPuesto;

    @Column(name = "fecha_fin_puesto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinPuesto;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Column(name = "edad")
    private long edad;

    /**
     * Identificador del motivo de salida.
     */
    @Column(name = "motivo_salida_core_id")
    private Long motivoSalidaCoreId;

    /**
     * Nombre del motivo de salida.
     */
    @Column(name = "motivo_salida_core_nombre")
    private String motivoSalidaCoreNombre;

    /**
     * Fecha de la aceptacion de la renuncia.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_aceptacion_renuncia")
    private Date fechaAceptacionRenuncia;

    /**
     * Fecha de presentacion de la renuncia.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_presenta_renuncia")
    private Date fechaPresentaRenuncia;

    /**
     * Numero del contrato anterior.
     */
    @Column(name = "numero_contrato_anterior")
    private String numeroContratoAnterior;

    /**
     * Fecha de inicio del contrato anterior.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_contrato_anterior")
    private Date fechaInicioContratoAnterior;

    /**
     * Referencia del movimiento.
     */
    @JoinColumn(name = "movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public Cesacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Cesacion(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(final Date fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public String getCasoFallecimiento() {
        return casoFallecimiento;
    }

    public void setCasoFallecimiento(final String casoFallecimiento) {
        this.casoFallecimiento = casoFallecimiento;
    }

    public Date getFechaInicioPuesto() {
        return fechaInicioPuesto;
    }

    public void setFechaInicioPuesto(final Date fechaInicioPuesto) {
        this.fechaInicioPuesto = fechaInicioPuesto;
    }

    public Date getFechaFinPuesto() {
        return fechaFinPuesto;
    }

    public void setFechaFinPuesto(final Date fechaFinPuesto) {
        this.fechaFinPuesto = fechaFinPuesto;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(final Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public long getEdad() {
        return edad;
    }

    public void setEdad(final long edad) {
        this.edad = edad;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the motivoSalidaCoreId
     */
    public Long getMotivoSalidaCoreId() {
        return motivoSalidaCoreId;
    }

    /**
     * @return the motivoSalidaCoreNombre
     */
    public String getMotivoSalidaCoreNombre() {
        return motivoSalidaCoreNombre;
    }

    /**
     * @return the fechaAceptacionRenuncia
     */
    public Date getFechaAceptacionRenuncia() {
        return fechaAceptacionRenuncia;
    }

    /**
     * @param motivoSalidaCoreId the motivoSalidaCoreId to set
     */
    public void setMotivoSalidaCoreId(final Long motivoSalidaCoreId) {
        this.motivoSalidaCoreId = motivoSalidaCoreId;
    }

    /**
     * @param motivoSalidaCoreNombre the motivoSalidaCoreNombre to set
     */
    public void setMotivoSalidaCoreNombre(final String motivoSalidaCoreNombre) {
        this.motivoSalidaCoreNombre = motivoSalidaCoreNombre;
    }

    /**
     * @param fechaAceptacionRenuncia the fechaAceptacionRenuncia to set
     */
    public void setFechaAceptacionRenuncia(final Date fechaAceptacionRenuncia) {
        this.fechaAceptacionRenuncia = fechaAceptacionRenuncia;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the fechaPresentaRenuncia
     */
    public Date getFechaPresentaRenuncia() {
        return fechaPresentaRenuncia;
    }

    /**
     * @param fechaPresentaRenuncia the fechaPresentaRenuncia to set
     */
    public void setFechaPresentaRenuncia(final Date fechaPresentaRenuncia) {
        this.fechaPresentaRenuncia = fechaPresentaRenuncia;
    }

    /**
     * @return the numeroContratoAnterior
     */
    public String getNumeroContratoAnterior() {
        return numeroContratoAnterior;
    }

    /**
     * @param numeroContratoAnterior the numeroContratoAnterior to set
     */
    public void setNumeroContratoAnterior(final String numeroContratoAnterior) {
        this.numeroContratoAnterior = numeroContratoAnterior;
    }

    /**
     * @return the fechaInicioContratoAnterior
     */
    public Date getFechaInicioContratoAnterior() {
        return fechaInicioContratoAnterior;
    }

    /**
     * @param fechaInicioContratoAnterior the fechaInicioContratoAnterior to set
     */
    public void setFechaInicioContratoAnterior(final Date fechaInicioContratoAnterior) {
        this.fechaInicioContratoAnterior = fechaInicioContratoAnterior;
    }
}
