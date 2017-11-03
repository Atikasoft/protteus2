package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;

/**
 * Identifica las alertas que se aplican al un movimiento especifico.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "alertas_bitacora", catalog = "sch_proteus")
public class AlertaBitacora extends EntidadBasica {

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Mensaje de la alerta.
     */
    @Column(name = "mensaje")
    private String mensaje;

    /**
     * Asunto de la alerta.
     */
    @Column(name = "asunto")
    private String asunto;

    /**
     * Destinatarios de la alerta.
     */
    @Column(name = "destinatarios")
    private String destinatarios;

    /*
     * Referencia a tipo de movimiento x alerta.
     */
    @JoinColumn(name = "tipos_movimientos_alertas_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimientoAlerta tipoMovimientoAlerta;

    /*
     * Referencia a institucion..
     */
    @JoinColumn(name = "institucion_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucion;

    /**
     * Constructor.
     */
    public AlertaBitacora() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public AlertaBitacora(final Long id) {
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
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @return the destinatarios
     */
    public String getDestinatarios() {
        return destinatarios;
    }

    /**
     * @return the tipoMovimientoAlerta
     */
    public TipoMovimientoAlerta getTipoMovimientoAlerta() {
        return tipoMovimientoAlerta;
    }

    /**
     * @return the institucion
     */
    public InstitucionEjercicioFiscal getInstitucion() {
        return institucion;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(final String asunto) {
        this.asunto = asunto;
    }

    /**
     * @param destinatarios the destinatarios to set
     */
    public void setDestinatarios(final String destinatarios) {
        this.destinatarios = destinatarios;
    }

    /**
     * @param tipoMovimientoAlerta the tipoMovimientoAlerta to set
     */
    public void setTipoMovimientoAlerta(final TipoMovimientoAlerta tipoMovimientoAlerta) {
        this.tipoMovimientoAlerta = tipoMovimientoAlerta;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final InstitucionEjercicioFiscal institucion) {
        this.institucion = institucion;
    }
}
