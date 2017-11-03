package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Guarda la auditoria de los tramites.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "tramites_bitacora", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TramiteBitacora.BUSCAR_POR_TRAMITE, query
            = "SELECT o FROM TramiteBitacora o WHERE o.vigente=true AND o.tramite.id=?1")})
public class TramiteBitacora extends EntidadBasica {

    public static final String BUSCAR_POR_TRAMITE = "TramiteBitacora.buscarPorTramite";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Cedula de elaboracion.
     */
    @Column(name = "cedula_elaboracion")
    private String cedulaElaboracion;

    /**
     * Nombre de elaboracion.
     */
    @Column(name = "nombre_elaboracion")
    private String nombreElaboracion;

    /**
     * Cargo de elaboracion.
     */
    @Column(name = "cargo_elaboracion")
    private String cargoElaboracion;

    /*
     *  Fecha de elaboracion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_elaboracion")
    private Date fechaElaboracion;

    /**
     * Cedula de revision.
     */
    @Column(name = "cedula_revision")
    private String cedulaRevision;

    /**
     * Nombre de revision.
     */
    @Column(name = "nombre_revision")
    private String nombreRevision;

    /**
     * Cargo de revision.
     */
    @Column(name = "cargo_revision")
    private String cargoRevision;

    /*
     *  Fecha de revision.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_revision")
    private Date fechaRevision;

    /**
     * Cedula de validacion.
     */
    @Column(name = "cedula_validacion")
    private String cedulaValidacion;

    /**
     * Nombre de validacion.
     */
    @Column(name = "nombre_validacion")
    private String nombreValidacion;

    /**
     * Cargo de validacion.
     */
    @Column(name = "cargo_validacion")
    private String cargoValidacion;

    /*
     *  Fecha de validacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_validacion")
    private Date fechaValidacion;

    /**
     * Cedula de aprobacion.
     */
    @Column(name = "cedula_aprobacion")
    private String cedulaAprobacion;

    /**
     * Nombre de aprobacion.
     */
    @Column(name = "nombre_aprobacion")
    private String nombreAprobacion;

    /**
     * Cargo de aprobacion.
     */
    @Column(name = "cargo_aprobacion")
    private String cargoAprobacion;

    /*
     *  Fecha de aprobacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_aprobacion")
    private Date fechaAprobacion;

    /**
     * Fecha de aprobacion en letras.
     */
    @Column(name = "fecha_aprobacion_letras")
    private String fechaAprobacionLetras;

    /**
     * Cedula de legalizacion.
     */
    @Column(name = "cedula_legalizacion")
    private String cedulaLegalizacion;

    /**
     * Nombre de legalizacion.
     */
    @Column(name = "nombre_legalizacion")
    private String nombreLegalizacion;

    /**
     * Cargo de legalizacion.
     */
    @Column(name = "cargo_legalizacion")
    private String cargoLegalizacion;

    /*
     *  Fecha de legalizacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_legalizacion")
    private Date fechaLegalizacion;

    /**
     * Cedula de eliminacion.
     */
    @Column(name = "cedula_eliminacion")
    private String cedulaEliminacion;

    /**
     * Nombre de eliminacion.
     */
    @Column(name = "nombre_eliminacion")
    private String nombreEliminacion;

    /**
     * Cargo de eliminacion.
     */
    @Column(name = "cargo_eliminacion")
    private String cargoEliminacion;

    /*
     *  Fecha de eliminacion.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_eliminacion")
    private Date fechaEliminacion;

    /**
     * Referencia con tramite.
     */
    @OneToOne
    @JoinColumn(name = "tramites_id")
    private Tramite tramite;

    /**
     * Constructor.
     */
    public TramiteBitacora() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TramiteBitacora(final Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the cedulaElaboracion
     */
    public String getCedulaElaboracion() {
        return cedulaElaboracion;
    }

    /**
     * @return the nombreElaboracion
     */
    public String getNombreElaboracion() {
        return nombreElaboracion;
    }

    /**
     * @return the cargoElaboracion
     */
    public String getCargoElaboracion() {
        return cargoElaboracion;
    }

    /**
     * @return the fechaElaboracion
     */
    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    /**
     * @return the cedulaRevision
     */
    public String getCedulaRevision() {
        return cedulaRevision;
    }

    /**
     * @return the nombreRevision
     */
    public String getNombreRevision() {
        return nombreRevision;
    }

    /**
     * @return the cargoRevision
     */
    public String getCargoRevision() {
        return cargoRevision;
    }

    /**
     * @return the fechaRevision
     */
    public Date getFechaRevision() {
        return fechaRevision;
    }

    /**
     * @return the cedulaValidacion
     */
    public String getCedulaValidacion() {
        return cedulaValidacion;
    }

    /**
     * @return the nombreValidacion
     */
    public String getNombreValidacion() {
        return nombreValidacion;
    }

    /**
     * @return the cargoValidacion
     */
    public String getCargoValidacion() {
        return cargoValidacion;
    }

    /**
     * @return the fechaValidacion
     */
    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    /**
     * @return the cedulaAprobacion
     */
    public String getCedulaAprobacion() {
        return cedulaAprobacion;
    }

    /**
     * @return the nombreAprobacion
     */
    public String getNombreAprobacion() {
        return nombreAprobacion;
    }

    /**
     * @return the cargoAprobacion
     */
    public String getCargoAprobacion() {
        return cargoAprobacion;
    }

    /**
     * @return the fechaAprobacion
     */
    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    /**
     * @return the cedulaLegalizacion
     */
    public String getCedulaLegalizacion() {
        return cedulaLegalizacion;
    }

    /**
     * @return the nombreLegalizacion
     */
    public String getNombreLegalizacion() {
        return nombreLegalizacion;
    }

    /**
     * @return the cargoLegalizacion
     */
    public String getCargoLegalizacion() {
        return cargoLegalizacion;
    }

    /**
     * @return the fechaLegalizacion
     */
    public Date getFechaLegalizacion() {
        return fechaLegalizacion;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param cedulaElaboracion the cedulaElaboracion to set
     */
    public void setCedulaElaboracion(final String cedulaElaboracion) {
        this.cedulaElaboracion = cedulaElaboracion;
    }

    /**
     * @param nombreElaboracion the nombreElaboracion to set
     */
    public void setNombreElaboracion(final String nombreElaboracion) {
        this.nombreElaboracion = nombreElaboracion;
    }

    /**
     * @param cargoElaboracion the cargoElaboracion to set
     */
    public void setCargoElaboracion(final String cargoElaboracion) {
        this.cargoElaboracion = cargoElaboracion;
    }

    /**
     * @param fechaElaboracion the fechaElaboracion to set
     */
    public void setFechaElaboracion(final Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    /**
     * @param cedulaRevision the cedulaRevision to set
     */
    public void setCedulaRevision(final String cedulaRevision) {
        this.cedulaRevision = cedulaRevision;
    }

    /**
     * @param nombreRevision the nombreRevision to set
     */
    public void setNombreRevision(final String nombreRevision) {
        this.nombreRevision = nombreRevision;
    }

    /**
     * @param cargoRevision the cargoRevision to set
     */
    public void setCargoRevision(final String cargoRevision) {
        this.cargoRevision = cargoRevision;
    }

    /**
     * @param fechaRevision the fechaRevision to set
     */
    public void setFechaRevision(final Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    /**
     * @param cedulaValidacion the cedulaValidacion to set
     */
    public void setCedulaValidacion(final String cedulaValidacion) {
        this.cedulaValidacion = cedulaValidacion;
    }

    /**
     * @param nombreValidacion the nombreValidacion to set
     */
    public void setNombreValidacion(final String nombreValidacion) {
        this.nombreValidacion = nombreValidacion;
    }

    /**
     * @param cargoValidacion the cargoValidacion to set
     */
    public void setCargoValidacion(final String cargoValidacion) {
        this.cargoValidacion = cargoValidacion;
    }

    /**
     * @param fechaValidacion the fechaValidacion to set
     */
    public void setFechaValidacion(final Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    /**
     * @param cedulaAprobacion the cedulaAprobacion to set
     */
    public void setCedulaAprobacion(final String cedulaAprobacion) {
        this.cedulaAprobacion = cedulaAprobacion;
    }

    /**
     * @param nombreAprobacion the nombreAprobacion to set
     */
    public void setNombreAprobacion(final String nombreAprobacion) {
        this.nombreAprobacion = nombreAprobacion;
    }

    /**
     * @param cargoAprobacion the cargoAprobacion to set
     */
    public void setCargoAprobacion(final String cargoAprobacion) {
        this.cargoAprobacion = cargoAprobacion;
    }

    /**
     * @param fechaAprobacion the fechaAprobacion to set
     */
    public void setFechaAprobacion(final Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    /**
     * @param cedulaLegalizacion the cedulaLegalizacion to set
     */
    public void setCedulaLegalizacion(final String cedulaLegalizacion) {
        this.cedulaLegalizacion = cedulaLegalizacion;
    }

    /**
     * @param nombreLegalizacion the nombreLegalizacion to set
     */
    public void setNombreLegalizacion(final String nombreLegalizacion) {
        this.nombreLegalizacion = nombreLegalizacion;
    }

    /**
     * @param cargoLegalizacion the cargoLegalizacion to set
     */
    public void setCargoLegalizacion(final String cargoLegalizacion) {
        this.cargoLegalizacion = cargoLegalizacion;
    }

    /**
     * @param fechaLegalizacion the fechaLegalizacion to set
     */
    public void setFechaLegalizacion(final Date fechaLegalizacion) {
        this.fechaLegalizacion = fechaLegalizacion;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the cedulaEliminacion
     */
    public String getCedulaEliminacion() {
        return cedulaEliminacion;
    }

    /**
     * @return the nombreEliminacion
     */
    public String getNombreEliminacion() {
        return nombreEliminacion;
    }

    /**
     * @return the cargoEliminacion
     */
    public String getCargoEliminacion() {
        return cargoEliminacion;
    }

    /**
     * @return the fechaEliminacion
     */
    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    /**
     * @param cedulaEliminacion the cedulaEliminacion to set
     */
    public void setCedulaEliminacion(final String cedulaEliminacion) {
        this.cedulaEliminacion = cedulaEliminacion;
    }

    /**
     * @param nombreEliminacion the nombreEliminacion to set
     */
    public void setNombreEliminacion(final String nombreEliminacion) {
        this.nombreEliminacion = nombreEliminacion;
    }

    /**
     * @param cargoEliminacion the cargoEliminacion to set
     */
    public void setCargoEliminacion(final String cargoEliminacion) {
        this.cargoEliminacion = cargoEliminacion;
    }

    /**
     * @param fechaEliminacion the fechaEliminacion to set
     */
    public void setFechaEliminacion(final Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    /**
     * @return the fechaAprobacionLetras
     */
    public String getFechaAprobacionLetras() {
        return fechaAprobacionLetras;
    }

    /**
     * @param fechaAprobacionLetras the fechaAprobacionLetras to set
     */
    public void setFechaAprobacionLetras(final String fechaAprobacionLetras) {
        this.fechaAprobacionLetras = fechaAprobacionLetras;
    }
}
