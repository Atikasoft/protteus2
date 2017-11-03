package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "instituciones_ejercicios_fiscales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_POR_NOMBRE, query
            = "SELECT ins FROM InstitucionEjercicioFiscal ins "
            + "where ins.vigente=true and ins.institucion.nombre like ?1"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_POR_CODIGO_Y_EJERCICIO_FISCAL,
            query
            = "Select o from InstitucionEjercicioFiscal o where o.vigente=true AND o.institucion.codigo=?1 AND o.ejercicioFiscal.id=?2"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_POR_CODIGO_CORE_Y_EJERCICIO_FISCAL,
            query
            = "Select o from InstitucionEjercicioFiscal o where o.vigente=true AND o.institucion.id=?1 AND o.ejercicioFiscal.id=?2"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_VIGENTES,
            query = "SELECT ins FROM InstitucionEjercicioFiscal ins where ins.vigente=true"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_TODOS_POR_INSTITUCION, query
            = "SELECT ins FROM InstitucionEjercicioFiscal ins "
            + "where ins.institucion.id = ?1 order by ins.ejercicioFiscal.fechaInicio desc"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_VIGENTES_POR_INSTITUCION, query
            = "SELECT ins FROM InstitucionEjercicioFiscal ins "
            + "where ins.institucion.id = ?1 and ins.vigente=true order by ins.ejercicioFiscal.fechaInicio desc"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_VIGENTES_POR_INSTITUCION_Y_EJERCICIO_FISCAL, query
            = "SELECT ins FROM InstitucionEjercicioFiscal ins "
            + "where ins.institucion.id = ?1 and ins.ejercicioFiscal.id = ?2 "
            + "and ins.vigente=true order by ins.ejercicioFiscal.fechaInicio desc"),
    @NamedQuery(name = InstitucionEjercicioFiscal.BUSCAR_POR_EJERCICIO_FISCAL, query
            = "SELECT ins FROM InstitucionEjercicioFiscal ins where ins.ejercicioFiscal.nombre = ?1")
})
public class InstitucionEjercicioFiscal extends EntidadBasica {

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "InstitucionEjercicioFiscal.buscarVigente";

    /**
     * Nombre de la consulta de buscar Instituciones por el nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "InstitucionEjercicioFiscal.BuscarporNombre";

    /**
     * variable para buscar en la consulta nombrada por Codigo y ejercicio
     * fiscal.
     */
    public static final String BUSCAR_POR_CODIGO_Y_EJERCICIO_FISCAL
            = "InstitucionEjercicioFiscal.buscarPorCodigoYEjercicioFiscal";

    /**
     * Nombre de la consulta que recupera institucion por codigo core y
     * ejercicio fiscal.
     */
    public static final String BUSCAR_POR_CODIGO_CORE_Y_EJERCICIO_FISCAL
            = "InstitucionEjercicioFiscal.buscarPorCodigoCoreYEjercicioFiscal";

    /**
     * variable para buscar en la consulta nombrada por id Institucion (vigentes
     * y no vigentes).
     */
    public static final String BUSCAR_TODOS_POR_INSTITUCION = "InstitucionEjercicioFiscal.buscarTodosPorInstitucion";

    /**
     * variable para buscar en la consulta nombrada por id Institucion (vigentes
     * y no vigentes).
     */
    public static final String BUSCAR_VIGENTES_POR_INSTITUCION = "InstitucionEjercicioFiscal.buscarVigentesPorInstitucion";

    /**
     * variable para buscar en la consulta nombrada por id Institucion, id
     * ejercicioFiscal (vigentes y no vigentes).
     */
    public static final String BUSCAR_VIGENTES_POR_INSTITUCION_Y_EJERCICIO_FISCAL = "InstitucionEjercicioFiscal.buscarVigentesPorInstitucionEjercicioFiscal";

    /**
     * variable para buscar en la consulta nombrada por ejercicio fiscal
     * (vigentes y no vigentes).
     */
    public static final String BUSCAR_POR_EJERCICIO_FISCAL = "InstitucionEjercicioFiscal.buscarPorEjerecicioFiscal";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_tramite")
    private Long contadorTramite;

    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_acto_administrativo")
    private Long contadorActoAdministrativo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_registro")
    private Long contadorRegistro;

    /**
     *
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_nomina")
    private Long contadorNomina;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ejercicio_fiscal_id")
    private EjercicioFiscal ejercicioFiscal;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "institucion_id")
    private Institucion institucion;

    /**
     * Constructor.
     */
    public InstitucionEjercicioFiscal() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public InstitucionEjercicioFiscal(final Long id) {
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
     * @return the contadorTramite
     */
    public Long getContadorTramite() {
        return contadorTramite;
    }

    /**
     * @return the contadorActoAdministrativo
     */
    public Long getContadorActoAdministrativo() {
        return contadorActoAdministrativo;
    }

    /**
     * @return the contadorRegistro
     */
    public Long getContadorRegistro() {
        return contadorRegistro;
    }

    /**
     * @return the ejercicioFiscal
     */
    public EjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param contadorTramite the contadorTramite to set
     */
    public void setContadorTramite(final Long contadorTramite) {
        this.contadorTramite = contadorTramite;
    }

    /**
     * @param contadorActoAdministrativo the contadorActoAdministrativo to set
     */
    public void setContadorActoAdministrativo(final Long contadorActoAdministrativo) {
        this.contadorActoAdministrativo = contadorActoAdministrativo;
    }

    /**
     * @param contadorRegistro the contadorRegistro to set
     */
    public void setContadorRegistro(final Long contadorRegistro) {
        this.contadorRegistro = contadorRegistro;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(final EjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the contadorNomina
     */
    public Long getContadorNomina() {
        return contadorNomina;
    }

    /**
     * @param contadorNomina the contadorNomina to set
     */
    public void setContadorNomina(final Long contadorNomina) {
        this.contadorNomina = contadorNomina;
    }

}
