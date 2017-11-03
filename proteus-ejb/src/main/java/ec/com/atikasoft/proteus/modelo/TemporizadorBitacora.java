package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;

/**
 * Contiene la bitacora de la ejecucion de temporizadores.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "temporizadores_bitacora", catalog = "sch_proteus")
public class TemporizadorBitacora extends EntidadBasica {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Fecha inicio de la ejecucion.
     */
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    /**
     * Fecha fin de ejecucion.
     */
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    /**
     * Descripcion del proceso.
     */
    @Column(name = "proceso")
    private String proceso;

    /**
     * Indicado de exito o fracaso de la ejecucion del temporizador.
     */
    @Column(name = "con_exito")
    private Boolean conExito;

    /**
     * Descripcion del error en caso de falla.
     */
    @Column(name = "error")
    private String error;

    /**
     * Constructor.
     */
    public TemporizadorBitacora() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @return the proceso
     */
    public String getProceso() {
        return proceso;
    }

    /**
     * @return the conExito
     */
    public Boolean getConExito() {
        return conExito;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(final String proceso) {
        this.proceso = proceso;
    }

    /**
     * @param conExito the conExito to set
     */
    public void setConExito(final Boolean conExito) {
        this.conExito = conExito;
    }

    /**
     * @param error the error to set
     */
    public void setError(final String error) {
        this.error = error;
    }
}
