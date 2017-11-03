package ec.com.atikasoft.proteus.modelo.vistas;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the sch_proteus.vista_nominas database view.
 */
@Entity
@Table(name = "vista_nominas", catalog = "sch_proteus")
public class VistaNomina extends EntidadBasica implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clave primaria.
     */
    @Id
    @Column(name = "nomina_id")
    private Long nominaId;

    /**
     *
     */
    @Column(name = "institucion_id")
    private Long institucionId;

    /**
     *
     */
    @Column(name = "institucion_codigo")
    private String institucionCodigo;

    /**
     *
     */
    @Column(name = "institucion_nombre")
    private String institucionNombre;

    /**
     *
     */
    @Column(name = "ejercicio_id")
    private Long ejercicioId;

    /**
     *
     */
    @Column(name = "ejercicio_anio")
    private String ejercicioAnio;

    /**
     *
     */
    @Column(name = "periodo_nomina_id")
    private Long periodoNominaId;

    /**
     *
     */
    @Column(name = "periodo_nomina_numero")
    private String periodoNominaNumero;

    /**
     *
     */
    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;

    /**
     *
     */
    @Column(name = "tipo_nomina_descripcion")
    private String tipoNominaDescripcion;

    /**
     *
     */
    @Column(name = "nomina_numero")
    private String nominaNumero;

    /**
     *
     */
    @Column(name = "nomina_descripcion")
    private String nominaDescripcion;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "nomina_fecha_creacion")
    private Date nominaFechaCreacion;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "nomina_fecha_generacion")
    private Date nominaFechaGeneracion;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "nomina_fecha_aprobacion")
    private Date nominaFechaAprobacion;

    /**
     *
     */
    @Column(name = "nomina_estado")
    private String nominaEstado;

    /**
     * Constructor sin argumentos.
     */
    public VistaNomina() {
        super();
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @return the institucionId
     */
    public Long getInstitucionId() {
        return institucionId;
    }

    /**
     * @return the institucionCodigo
     */
    public String getInstitucionCodigo() {
        return institucionCodigo;
    }

    /**
     * @return the institucionNombre
     */
    public String getInstitucionNombre() {
        return institucionNombre;
    }

    /**
     * @return the ejercicioId
     */
    public Long getEjercicioId() {
        return ejercicioId;
    }

    /**
     * @return the ejercicioAnio
     */
    public String getEjercicioAnio() {
        return ejercicioAnio;
    }

    /**
     * @return the periodoNominaId
     */
    public Long getPeriodoNominaId() {
        return periodoNominaId;
    }

    /**
     * @return the periodoNominaNumero
     */
    public String getPeriodoNominaNumero() {
        return periodoNominaNumero;
    }

    /**
     * @return the tipoNominaId
     */
    public Long getTipoNominaId() {
        return tipoNominaId;
    }

    /**
     * @return the tipoNominaDescripcion
     */
    public String getTipoNominaDescripcion() {
        return tipoNominaDescripcion;
    }

    /**
     * @return the nominaNumero
     */
    public String getNominaNumero() {
        return nominaNumero;
    }

    /**
     * @return the nominaDescripcion
     */
    public String getNominaDescripcion() {
        return nominaDescripcion;
    }

    /**
     * @return the nominaFechaCreacion
     */
    public Date getNominaFechaCreacion() {
        return nominaFechaCreacion;
    }

    /**
     * @return the nominaFechaGeneracion
     */
    public Date getNominaFechaGeneracion() {
        return nominaFechaGeneracion;
    }

    /**
     * @return the nominaFechaAprobacion
     */
    public Date getNominaFechaAprobacion() {
        return nominaFechaAprobacion;
    }

    /**
     * @return the nominaEstado
     */
    public String getNominaEstado() {
        return nominaEstado;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(final Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @param institucionId the institucionId to set
     */
    public void setInstitucionId(final Long institucionId) {
        this.institucionId = institucionId;
    }

    /**
     * @param institucionCodigo the institucionCodigo to set
     */
    public void setInstitucionCodigo(final String institucionCodigo) {
        this.institucionCodigo = institucionCodigo;
    }

    /**
     * @param institucionNombre the institucionNombre to set
     */
    public void setInstitucionNombre(final String institucionNombre) {
        this.institucionNombre = institucionNombre;
    }

    /**
     * @param ejercicioId the ejercicioId to set
     */
    public void setEjercicioId(final Long ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    /**
     * @param ejercicioAnio the ejercicioAnio to set
     */
    public void setEjercicioAnio(final String ejercicioAnio) {
        this.ejercicioAnio = ejercicioAnio;
    }

    /**
     * @param periodoNominaId the periodoNominaId to set
     */
    public void setPeriodoNominaId(final Long periodoNominaId) {
        this.periodoNominaId = periodoNominaId;
    }

    /**
     * @param periodoNominaNumero the periodoNominaNumero to set
     */
    public void setPeriodoNominaNumero(final String periodoNominaNumero) {
        this.periodoNominaNumero = periodoNominaNumero;
    }

    /**
     * @param tipoNominaId the tipoNominaId to set
     */
    public void setTipoNominaId(final Long tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
    }

    /**
     * @param tipoNominaDescripcion the tipoNominaDescripcion to set
     */
    public void setTipoNominaDescripcion(final String tipoNominaDescripcion) {
        this.tipoNominaDescripcion = tipoNominaDescripcion;
    }

    /**
     * @param nominaNumero the nominaNumero to set
     */
    public void setNominaNumero(final String nominaNumero) {
        this.nominaNumero = nominaNumero;
    }

    /**
     * @param nominaDescripcion the nominaDescripcion to set
     */
    public void setNominaDescripcion(final String nominaDescripcion) {
        this.nominaDescripcion = nominaDescripcion;
    }

    /**
     * @param nominaFechaCreacion the nominaFechaCreacion to set
     */
    public void setNominaFechaCreacion(final Date nominaFechaCreacion) {
        this.nominaFechaCreacion = nominaFechaCreacion;
    }

    /**
     * @param nominaFechaGeneracion the nominaFechaGeneracion to set
     */
    public void setNominaFechaGeneracion(final Date nominaFechaGeneracion) {
        this.nominaFechaGeneracion = nominaFechaGeneracion;
    }

    /**
     * @param nominaFechaAprobacion the nominaFechaAprobacion to set
     */
    public void setNominaFechaAprobacion(final Date nominaFechaAprobacion) {
        this.nominaFechaAprobacion = nominaFechaAprobacion;
    }

    /**
     * @param nominaEstado the nominaEstado to set
     */
    public void setNominaEstado(final String nominaEstado) {
        this.nominaEstado = nominaEstado;
    }
}