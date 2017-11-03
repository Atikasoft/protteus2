package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Henry Molina <henry.molina@marcasoft.ec>
 */
@Entity
@Table(name = "trayectoria_laboral", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TrayectoriaLaboral.BUSCAR_POR_PRIMERO, query
            = "SELECT c FROM TrayectoriaLaboral c where c.tipoIdentificacion = ?1 and c.numeroIdentificacion = ?2 and"
            + " c.vigente=true order by c.fechaDesde asc")
})
@Getter
@Setter
public class TrayectoriaLaboral extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_POR_PRIMERO = "TrayectoriaLaboral.buscarPrimero";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Tipo identificacion.
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     * Numero_identificacion.
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     * Apellidos.
     */
    @Column(name = "apellidos")
    private String apellidos;

    /**
     * Nombres.
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * grupo.
     */
    @Column(name = "grupo")
    private String grupo;

    /**
     * tipo_movimiento.
     */
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    /**
     * numero_movimiento.
     */
    @Column(name = "numero_movimiento")
    private String numeroMovimiento;

    /**
     * regimen_laboral.
     */
    @Column(name = "regimen_laboral")
    private String regimenLaboral;

    /**
     * grado.
     */
    @Column(name = "grado")
    private String grado;

    /**
     * rmu.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * rmu.
     */
    @Column(name = "rmu_sobrevalorado")
    private BigDecimal rmuSobrevalorado;

    /**
     * fecha_desde.
     */
    @Column(name = "fecha_desde")
    private String fechaDesde;

    /**
     * fecha_hasta.
     */
    @Column(name = "fecha_hasta")
    private String fechaHasta;

    /**
     * explicacion.
     */
    @Column(name = "explicacion")
    private String explicacion;

    /**
     *
     */
    @Column(name = "numero_documento_habilitante")
    private String numeroDocumentoHabilitante;

    /**
     *
     */
    @Column(name = "clase")
    private String clase;

    /**
     *
     */
    @Column(name = "unidad_presupuestaria")
    private String unidadPresupuestaria;

    /**
     *
     */
    @Column(name = "unidad_organizacional")
    private String unidadOrganizacional;

    /**
     *
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     *
     */
    @Column(name = "denominacion_puesto")
    private String denominacionPuesto;

    /**
     *
     */
    @Column(name = "elaborador")
    private String elaborador;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_elaborador")
    private Date fechaElaborador;

    /**
     *
     */
    @Column(name = "legalizador")
    private String legalizador;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_legalizador")
    private Date fechaLegalizador;

    /**
     * Constructor.
     */
    public TrayectoriaLaboral() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TrayectoriaLaboral(final Long id) {
        super();
        this.id = id;
    }

}
