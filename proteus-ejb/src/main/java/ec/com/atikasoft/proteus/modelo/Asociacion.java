package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Entity
@Table(name = "asociaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Asociacion.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, query = "SELECT o FROM Asociacion o WHERE o.vigente=true AND (o.unidadOrganizacional1.id=?1 OR o.unidadOrganizacional2.id=?2 OR o.unidadOrganizacional3.id=?3 OR o.unidadOrganizacional4.id=?4 OR o.unidadOrganizacional5.id=?5 ) ")
})
public class Asociacion extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL = "Asociacion.buscarPorUnidadOrganizacional ";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional1_id")
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional1;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional2_id")
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional2;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional3_id")
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional3;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional4_id")
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional4;

    /**
     *
     */
    @JoinColumn(name = "unidad_organizacional5_id")
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional5;

    /**
     * Constructor.
     */
    public Asociacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Asociacion(final Long id) {
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
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the unidadOrganizacional1
     */
    public UnidadOrganizacional getUnidadOrganizacional1() {
        return unidadOrganizacional1;
    }

    /**
     * @param unidadOrganizacional1 the unidadOrganizacional1 to set
     */
    public void setUnidadOrganizacional1(UnidadOrganizacional unidadOrganizacional1) {
        this.unidadOrganizacional1 = unidadOrganizacional1;
    }

    /**
     * @return the unidadOrganizacional2
     */
    public UnidadOrganizacional getUnidadOrganizacional2() {
        return unidadOrganizacional2;
    }

    /**
     * @param unidadOrganizacional2 the unidadOrganizacional2 to set
     */
    public void setUnidadOrganizacional2(UnidadOrganizacional unidadOrganizacional2) {
        this.unidadOrganizacional2 = unidadOrganizacional2;
    }

    /**
     * @return the unidadOrganizacional3
     */
    public UnidadOrganizacional getUnidadOrganizacional3() {
        return unidadOrganizacional3;
    }

    /**
     * @param unidadOrganizacional3 the unidadOrganizacional3 to set
     */
    public void setUnidadOrganizacional3(UnidadOrganizacional unidadOrganizacional3) {
        this.unidadOrganizacional3 = unidadOrganizacional3;
    }

    /**
     * @return the unidadOrganizacional4
     */
    public UnidadOrganizacional getUnidadOrganizacional4() {
        return unidadOrganizacional4;
    }

    /**
     * @param unidadOrganizacional4 the unidadOrganizacional4 to set
     */
    public void setUnidadOrganizacional4(UnidadOrganizacional unidadOrganizacional4) {
        this.unidadOrganizacional4 = unidadOrganizacional4;
    }

    /**
     * @return the unidadOrganizacional5
     */
    public UnidadOrganizacional getUnidadOrganizacional5() {
        return unidadOrganizacional5;
    }

    /**
     * @param unidadOrganizacional5 the unidadOrganizacional5 to set
     */
    public void setUnidadOrganizacional5(UnidadOrganizacional unidadOrganizacional5) {
        this.unidadOrganizacional5 = unidadOrganizacional5;
    }

}
