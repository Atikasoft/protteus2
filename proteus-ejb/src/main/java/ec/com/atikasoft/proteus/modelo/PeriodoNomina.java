/*
 *  PeriodoNomina.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "periodos_nominas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = PeriodoNomina.BUSCAR_VIGENTES, query
            = "SELECT c FROM PeriodoNomina c where c.vigente=true order by c.numero,c.ejercicioFiscal.fechaInicio ")
    ,
    @NamedQuery(name = PeriodoNomina.BUSCAR_POR_FECHA,
            query = "SELECT o FROM PeriodoNomina o WHERE o.vigente=true AND ?1 BETWEEN o.fechaInicio AND o.fechaFinal")
    ,
    @NamedQuery(name = PeriodoNomina.BUSCAR_POR_EJERCICIO,
            query = "SELECT o FROM PeriodoNomina o WHERE o.vigente=true AND o.ejercicioFiscalId=?1 ORDER BY o.numero")
})
public class PeriodoNomina extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "PeriodoNomina.buscarVigentes";

    /**
     * Nombre de la consulta que recupera un periodo de fecha.
     */
    public static final String BUSCAR_POR_FECHA = "PeriodoNomina.buscarPorFecha";

    public static final String BUSCAR_POR_EJERCICIO = "PeriodoNomina.buscarPorEjercicio";

    /**
     * Constructor por defecto.
     */
    public PeriodoNomina() {
        super();
    }
    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de periodo.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * numero
     */
    @Column(name = "numero")
    private Long numero;

    /**
     * fecha final.
     */
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;

    /**
     * fecha inicio.
     */
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    /**
     * Tipo del catalogo.
     */
    @JoinColumn(name = "ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    //@Fetch(FetchMode.JOIN)
    private EjercicioFiscal ejercicioFiscal;

    /**
     * Id de tipo de catalogo.
     */
    @Column(name = "ejercicio_fiscal_id")
    private Long ejercicioFiscalId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    /**
     * Para representar los periodos seleccionados en la pantalla de rubros
     */
    @Transient
    private Boolean seleccionado;

    /**
     * Lista de rubro-PeriodoNomina
     */
    @OneToMany(mappedBy = "periodoNomina")
    private List<RubroPeriodoNomina> listaPeriodoTipoNomina;

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
     * @return the numero
     */
    public Long getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(final Long numero) {
        this.numero = numero;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(final Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the ejercicioFiscal
     */
    public EjercicioFiscal getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(EjercicioFiscal ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the ejercicioFiscalId
     */
    public Long getEjercicioFiscalId() {
        return ejercicioFiscalId;
    }

    /**
     * @param ejercicioFiscalId the ejercicioFiscalId to set
     */
    public void setEjercicioFiscalId(Long ejercicioFiscalId) {
        this.ejercicioFiscalId = ejercicioFiscalId;
    }

    /**
     * @return the seleccionado
     */
    public Boolean getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * @return the listaPeriodoTipoNomina
     */
    public List<RubroPeriodoNomina> getListaPeriodoTipoNomina() {
        return listaPeriodoTipoNomina;
    }

    /**
     * @param listaPeriodoTipoNomina the listaPeriodoTipoNomina to set
     */
    public void setListaPeriodoTipoNomina(List<RubroPeriodoNomina> listaPeriodoTipoNomina) {
        this.listaPeriodoTipoNomina = listaPeriodoTipoNomina;
    }
}
