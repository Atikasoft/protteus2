/*
 *  TipoNomina.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo.distributivo;

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

/**
 * Distributivo
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@XmlRootElement(name = "distributivo")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "distributivo", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Distributivo.BUSCAR_POR_NOMBRE, query = "SELECT a FROM Distributivo a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Distributivo.BUSCAR_VIGENTES, query = "SELECT a FROM Distributivo a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Distributivo.BUSCAR_SECUENCIA_PARTIDA_POR_ID, query = "SELECT a FROM Distributivo a where a.vigente=true AND a.id=?1 order by a.contadorPartida desc"),
    @NamedQuery(name = Distributivo.BUSCAR_POR_MODALIDAD_Y_UNIDAD_ORGANIZACIONAL, query = "SELECT a FROM Distributivo a where a.unidadOrganizacional.codigo=?1 and  a.modalidadLaboral.codigo=?2 and a.idInstitucionEjercicioFiscal=?3 and  a.vigente=true"),
    @NamedQuery(name = Distributivo.BUSCAR_POR_MODALIDAD_ID_Y_UNIDAD_ORGANIZACIONAL_ID, query = "SELECT a FROM Distributivo a where a.unidadOrganizacional.id=?1 and  a.modalidadLaboral.id=?2 and a.idInstitucionEjercicioFiscal=?3 and  a.vigente=true"),
    @NamedQuery(name = Distributivo.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, query = "SELECT a FROM Distributivo a where a.unidadOrganizacional.codigo=?1 and a.idInstitucionEjercicioFiscal=?2 and a.vigente=true")
})
public class Distributivo extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Distributivo.buscarporNombre";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_MODALIDAD_Y_UNIDAD_ORGANIZACIONAL = "Distributivo.buscarporModalidadYUnidadOrganizacional";
    
    /**
     * Variable para busqeda por ids de modalidad y unidad.
     */
    public static final String BUSCAR_POR_MODALIDAD_ID_Y_UNIDAD_ORGANIZACIONAL_ID = "Distributivo.buscarPorModalidadIdYUnidadOrganizacionalId";
    

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL = "Distributivo.buscarporUnidadOrganizacional";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Distributivo.buscarVigente";

    /**
     * Nombre para busqueda de secuencia de partida por su id.
     */
    public static final String BUSCAR_SECUENCIA_PARTIDA_POR_ID = "Distributivo.buscarContadorPartida";

    /**
     * Constructor por defecto.
     */
    public Distributivo() {
        super();
    }
    @Id
    @XmlElement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @XmlElement
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @XmlElement
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_partida")
    private Long contadorPartida;

    /**
     * Referencia con institucion x ejercicio fiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * institucion x ejercicio fiscal.
     */
    @XmlElement
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long idInstitucionEjercicioFiscal;

    /**
     * Referencia con modalidad laboral
     */
    @JoinColumn(name = "modalidad_laboral_id", insertable = false, updatable = false)
    @ManyToOne
    private ModalidadLaboral modalidadLaboral;

    /**
     * Modalidad Laboral
     */
    @XmlElement
    @Column(name = "modalidad_laboral_id")
    private Long idModalidadLaboral;

    /**
     * Referencia con unidadOrganizacional
     */
    @XmlElement
    @JoinColumn(name = "unidad_organizacional_id", insertable = false, updatable = false)
    @ManyToOne
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * unidadOrganizacional
     */
    @XmlElement
    @Column(name = "unidad_organizacional_id")
    private Long idUnidadOrganizacional;

    /**
     * Lista de Distributivo Detalle.
     */
    @OneToMany(mappedBy = "distributivo")
    private List<DistributivoDetalle> distributivoDetalles;

    @Transient
    private Long totalDetalles;

    @Override
    public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return UtilCadena.concatenar(id, ":modalidad:", modalidadLaboral.getNombre(), ":unidad:", unidadOrganizacional.
                getRuta(), ":ejercicioFiscal:", getInstitucionEjercicioFiscal().getEjercicioFiscal().getNombre());
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
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(final InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the modalidadLaboral
     */
    public ModalidadLaboral getModalidadLaboral() {
        return modalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setModalidadLaboral(ModalidadLaboral modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the idModalidadLaboral
     */
    public Long getIdModalidadLaboral() {
        return idModalidadLaboral;
    }

    /**
     * @param idModalidadLaboral the idModalidadLaboral to set
     */
    public void setIdModalidadLaboral(Long idModalidadLaboral) {
        this.idModalidadLaboral = idModalidadLaboral;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the idUnidadOrganizacional
     */
    public Long getIdUnidadOrganizacional() {
        return idUnidadOrganizacional;
    }

    /**
     * @param idUnidadOrganizacional the idUnidadOrganizacional to set
     */
    public void setIdUnidadOrganizacional(Long idUnidadOrganizacional) {
        this.idUnidadOrganizacional = idUnidadOrganizacional;
    }

    /**
     * @return the distributivoDetalles
     */
    public List<DistributivoDetalle> getDistributivoDetalles() {
        return distributivoDetalles;
    }

    /**
     * @param distributivoDetalles the distributivoDetalles to set
     */
    public void setDistributivoDetalles(List<DistributivoDetalle> distributivoDetalles) {
        this.distributivoDetalles = distributivoDetalles;
    }

    /**
     * @return the contadorPartidad
     */
    public Long getContadorPartida() {
        return contadorPartida;
    }

    /**
     * @param contadorPartidad the contadorPartidad to set
     */
    public void setContadorPartida(Long contadorPartidad) {
        this.contadorPartida = contadorPartidad;
    }

    /**
     * @return the idInstitucionEjercicioFiscal
     */
    public Long getIdInstitucionEjercicioFiscal() {
        return idInstitucionEjercicioFiscal;
    }

    /**
     * @param idInstitucionEjercicioFiscal the idInstitucionEjercicioFiscal to
     * set
     */
    public void setIdInstitucionEjercicioFiscal(Long idInstitucionEjercicioFiscal) {
        this.idInstitucionEjercicioFiscal = idInstitucionEjercicioFiscal;
    }

    /**
     * @return the totalDetalles
     */
    public Long getTotalDetalles() {
        return totalDetalles;
    }

    /**
     * @param totalDetalles the totalDetalles to set
     */
    public void setTotalDetalles(Long totalDetalles) {
        this.totalDetalles = totalDetalles;
    }
    
    public static Distributivo getDistributivoVacio(){
        Distributivo distributivo = new Distributivo();
        distributivo.setVigente(Boolean.TRUE);
        distributivo.setFechaCreacion(new Date());
        distributivo.setUsuarioCreacion("atk");
        distributivo.setInstitucionEjercicioFiscal(new InstitucionEjercicioFiscal());
        distributivo.getInstitucionEjercicioFiscal().setEjercicioFiscal(new EjercicioFiscal());
        distributivo.setModalidadLaboral(new ModalidadLaboral());
        //distributivo.getModalidadLaboral().setId(0l);
        distributivo.setUnidadOrganizacional(new UnidadOrganizacional());
        return distributivo;
    }
    
}

