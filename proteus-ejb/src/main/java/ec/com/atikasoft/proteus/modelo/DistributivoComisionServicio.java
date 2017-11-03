package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de comision de servidos en el distributivo.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "distributivos_comisiones_servicios", catalog = "sch_proteus")
public class DistributivoComisionServicio extends EntidadBasica {

    /**
     * Nombre de consulta.
     */
    public final static String BUSCAR_POR_MOVIMIENTO = "ComisionServicio.buscarPorMovimiento";

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de entidad a donde se traslada.
     */
    @Column(name = "entidad")
    private String entidad;

    /**
     *
     */
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_finalizado")
    private Date fechaFinalizado;

    /**
     *
     */
    @Column(name = "finalizado")
    private Boolean finalizado;

    /**
     * Referencia a distributivo detalle.
     */
    @JoinColumn(name = "distributivo_detalle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalle;

    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Constructor.
     */
    public DistributivoComisionServicio() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DistributivoComisionServicio(final Long id) {
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
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the entidad
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * @param entidad the entidad to set
     */
    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    /**
     * @return the tipoComisionServicio
     */
    public String getTipoComisionServicio() {
        return tipoComisionServicio;
    }

    /**
     * @param tipoComisionServicio the tipoComisionServicio to set
     */
    public void setTipoComisionServicio(String tipoComisionServicio) {
        this.tipoComisionServicio = tipoComisionServicio;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the fechaFinalizado
     */
    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    /**
     * @param fechaFinalizado the fechaFinalizado to set
     */
    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    /**
     * @return the finalizado
     */
    public Boolean getFinalizado() {
        return finalizado;
    }

    /**
     * @param finalizado the finalizado to set
     */
    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }


}
