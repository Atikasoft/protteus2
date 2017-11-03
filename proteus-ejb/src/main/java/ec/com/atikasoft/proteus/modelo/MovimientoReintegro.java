package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Reintegros de movimientos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "movimientos_reintegros", catalog = "sch_proteus")
public class MovimientoReintegro extends EntidadBasica {

    /**
     * Id de clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Indicador si el servidor del movimiento fue reintegrado manualmente.
     */
    @Column(name = "reintegrado_manualmente")
    private Boolean reintegradoManualmente;

    /**
     * Usuario que realizo el reintegro manualmente.
     */
    @Column(name = "usuario_reintegrado_manualmente")
    private String usuarioReintegradoManualmente;

    /**
     * Fecha cuando se realizo el reintegro manualmente.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_reintegrado_manualmente")
    private Date fechaReintegradoManualmente;

    /**
     * Indicador si el servidor del movimiento fue reintegrado automaticamente.
     */
    @Column(name = "reintegrado_automaticamente")
    private Boolean reintegradoAutomaticamente;

    /**
     * Usuario que realizo el reintegro automaticamente.
     */
    @Column(name = "usuario_reintegrado_automaticamente")
    private String usuarioReintegradoAutomaticamente;

    /**
     * Fecha cuando se realizo el reintegro automaticamente.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_reintegrado_automaticamente")
    private Date fechaReintegradoAutomaticamente;

    /**
     * Referencia con tramite
     */
    @OneToOne
    @JoinColumn(name = "movimiento_id")
    private Movimiento movimiento;

    /**
     * Constructor.
     */
    public MovimientoReintegro() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MovimientoReintegro(final Long id) {
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
     * @return the reintegradoManualmente
     */
    public Boolean getReintegradoManualmente() {
        return reintegradoManualmente;
    }

    /**
     * @return the usuarioReintegradoManualmente
     */
    public String getUsuarioReintegradoManualmente() {
        return usuarioReintegradoManualmente;
    }

    /**
     * @return the fechaReintegradoManualmente
     */
    public Date getFechaReintegradoManualmente() {
        return fechaReintegradoManualmente;
    }

    /**
     * @return the reintegradoAutomaticamente
     */
    public Boolean getReintegradoAutomaticamente() {
        return reintegradoAutomaticamente;
    }

    /**
     * @return the usuarioReintegradoAutomaticamente
     */
    public String getUsuarioReintegradoAutomaticamente() {
        return usuarioReintegradoAutomaticamente;
    }

    /**
     * @return the fechaReintegradoAutomaticamente
     */
    public Date getFechaReintegradoAutomaticamente() {
        return fechaReintegradoAutomaticamente;
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
     * @param reintegradoManualmente the reintegradoManualmente to set
     */
    public void setReintegradoManualmente(final Boolean reintegradoManualmente) {
        this.reintegradoManualmente = reintegradoManualmente;
    }

    /**
     * @param usuarioReintegradoManualmente the usuarioReintegradoManualmente to set
     */
    public void setUsuarioReintegradoManualmente(final String usuarioReintegradoManualmente) {
        this.usuarioReintegradoManualmente = usuarioReintegradoManualmente;
    }

    /**
     * @param fechaReintegradoManualmente the fechaReintegradoManualmente to set
     */
    public void setFechaReintegradoManualmente(final Date fechaReintegradoManualmente) {
        this.fechaReintegradoManualmente = fechaReintegradoManualmente;
    }

    /**
     * @param reintegradoAutomaticamente the reintegradoAutomaticamente to set
     */
    public void setReintegradoAutomaticamente(final Boolean reintegradoAutomaticamente) {
        this.reintegradoAutomaticamente = reintegradoAutomaticamente;
    }

    /**
     * @param usuarioReintegradoAutomaticamente the usuarioReintegradoAutomaticamente to set
     */
    public void setUsuarioReintegradoAutomaticamente(final String usuarioReintegradoAutomaticamente) {
        this.usuarioReintegradoAutomaticamente = usuarioReintegradoAutomaticamente;
    }

    /**
     * @param fechaReintegradoAutomaticamente the fechaReintegradoAutomaticamente to set
     */
    public void setFechaReintegradoAutomaticamente(final Date fechaReintegradoAutomaticamente) {
        this.fechaReintegradoAutomaticamente = fechaReintegradoAutomaticamente;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}
