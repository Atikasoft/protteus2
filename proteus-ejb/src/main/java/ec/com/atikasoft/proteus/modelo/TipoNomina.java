/*
 *  TipoNomina.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.config.QueryHints;

/**
 * TipoNomina
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "tipos_nominas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoNomina.BUSCAR_POR_NOMBRE, query = "SELECT c FROM TipoNomina c where c.nombre like ?1"),
    @NamedQuery(name = TipoNomina.BUSCAR_POR_NEMONICO, query = "SELECT a FROM TipoNomina a where a.codigo=?1"),
    @NamedQuery(name = TipoNomina.BUSCAR_VIGENTES, query = "SELECT c FROM TipoNomina c where c.vigente=true order by c.nombre ")
})
public class TipoNomina extends EntidadBasica {

    /**
     * Constructor por defecto.
     */
    public TipoNomina() {
        super();
    }
    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_POR_NOMBRE = "TipoNomina.buscarPorNombre";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "TipoNomina.buscarporNemonico ";

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "TipoNomina.buscarVigentes";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * des.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * tipo.
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * cobertura.
     */
    @Column(name = "cobertura")
    private String cobertura;

    /**
     * requierePagoIess.
     */
    @Column(name = "requiere_pago_iess")
    private boolean requierePagoIess;

    /**
     * 
     */
    @Column(name = "valida_activo_periodo")
    private Boolean validaActivoPeriodo;

    /**
     * periodicidadOcurrencia.
     */
    @Column(name = "periodicidad_ocurrencia")
    private String periodicidadOcurrencia;

    /**
     * numeroMaximoOcurrencia.
     */
    @Column(name = "numero_maximo_ocurrencia")
    private Long numeroMaximoOcurrencia;

    /**
     * totalIngresoMaximo.
     */
    @Column(name = "total_ingreso_maximo")
    private BigDecimal totalIngresoMaximo;

    /**
     *
     */
    @JoinColumn(name = "regimen_laboral_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;

    /**
     *
     */
    @Column(name = "regimen_laboral_id")
    private Long regimenLaboralId;

    /**
     * Lista de tipoNomina-TipoNominaEstadoPuesto
     */
    @OneToMany(mappedBy = "tipoNomina")
    private List<TipoNominaEstadoPuesto> listaTipoNominaEstadoPuestos;

    /**
     * Lista de tipoNomina-TipoNominaEstadoPersonal
     */
    @OneToMany(mappedBy = "tipoNomina")
    private List<TipoNominaEstadoPersonal> listaTipoNominaEstadoPersonales;

    /**
     * Lista de rubro-TipoNomina
     */
    @OneToMany(mappedBy = "tipoNomina")
    private List<RubroTipoNomina> listaRubroTipoNomina;

    /**
     * Permite representar los tipos de nomina seleccionados
     */
    @Transient
    private Boolean seleccionado;

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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the cobertura
     */
    public String getCobertura() {
        return cobertura;
    }

    /**
     * @param cobertura the cobertura to set
     */
    public void setCobertura(final String cobertura) {
        this.cobertura = cobertura;
    }

    /**
     * @return the requierePagoIess
     */
    public boolean isRequierePagoIess() {
        return requierePagoIess;
    }

    /**
     * @param requierePagoIess the requierePagoIess to set
     */
    public void setRequierePagoIess(final boolean requierePagoIess) {
        this.requierePagoIess = requierePagoIess;
    }

    /**
     * @return the periodicidadOcurrencia
     */
    public String getPeriodicidadOcurrencia() {
        return periodicidadOcurrencia;
    }

    /**
     * @param periodicidadOcurrencia the periodicidadOcurrencia to set
     */
    public void setPeriodicidadOcurrencia(final String periodicidadOcurrencia) {
        this.periodicidadOcurrencia = periodicidadOcurrencia;
    }

    /**
     * @return the numeroMaximoOcurrencia
     */
    public Long getNumeroMaximoOcurrencia() {
        return numeroMaximoOcurrencia;
    }

    /**
     * @param numeroMaximoOcurrencia the numeroMaximoOcurrencia to set
     */
    public void setNumeroMaximoOcurrencia(final Long numeroMaximoOcurrencia) {
        this.numeroMaximoOcurrencia = numeroMaximoOcurrencia;
    }

    /**
     * @return the totalIngresoMaximo
     */
    public BigDecimal getTotalIngresoMaximo() {
        return totalIngresoMaximo;
    }

    /**
     * @param totalIngresoMaximo the totalIngresoMaximo to set
     */
    public void setTotalIngresoMaximo(final BigDecimal totalIngresoMaximo) {
        this.totalIngresoMaximo = totalIngresoMaximo;
    }

    /**
     * @return the listaTipoNominaEstadoPuestos
     */
    public List<TipoNominaEstadoPuesto> getListaTipoNominaEstadoPuestos() {
        return listaTipoNominaEstadoPuestos;
    }

    /**
     * @param listaTipoNominaEstadoPuestos the listaTipoNominaEstadoPuestos to
     * set
     */
    public void setListaTipoNominaEstadoPuestos(List<TipoNominaEstadoPuesto> listaTipoNominaEstadoPuestos) {
        this.listaTipoNominaEstadoPuestos = listaTipoNominaEstadoPuestos;
    }

    /**
     * @return the listaTipoNominaEstadoPersonales
     */
    public List<TipoNominaEstadoPersonal> getListaTipoNominaEstadoPersonales() {
        return listaTipoNominaEstadoPersonales;
    }

    /**
     * @param listaTipoNominaEstadoPersonales the
     * listaTipoNominaEstadoPersonales to set
     */
    public void setListaTipoNominaEstadoPersonales(List<TipoNominaEstadoPersonal> listaTipoNominaEstadoPersonales) {
        this.listaTipoNominaEstadoPersonales = listaTipoNominaEstadoPersonales;
    }

    /**
     * @return the Seleccionado
     */
    public Boolean getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param Seleccionado the Seleccionado to set
     */
    public void setSeleccionado(Boolean Seleccionado) {
        this.seleccionado = Seleccionado;
    }

    /**
     * @return the listaRubroTipoNomina
     */
    public List<RubroTipoNomina> getListaRubroTipoNomina() {
        return listaRubroTipoNomina;
    }

    /**
     * @param listaRubroTipoNomina the listaRubroTipoNomina to set
     */
    public void setListaRubroTipoNomina(List<RubroTipoNomina> listaRubroTipoNomina) {
        this.listaRubroTipoNomina = listaRubroTipoNomina;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the regimenLaboral
     */
    public RegimenLaboral getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(final RegimenLaboral regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    /**
     * @return the regimenLaboralId
     */
    public Long getRegimenLaboralId() {
        return regimenLaboralId;
    }

    /**
     * @param regimenLaboralId the regimenLaboralId to set
     */
    public void setRegimenLaboralId(final Long regimenLaboralId) {
        this.regimenLaboralId = regimenLaboralId;
    }

    /**
     * @return the validaActivoPeriodo
     */
    public Boolean getValidaActivoPeriodo() {
        return validaActivoPeriodo;
    }

    /**
     * @param validaActivoPeriodo the validaActivoPeriodo to set
     */
    public void setValidaActivoPeriodo(Boolean validaActivoPeriodo) {
        this.validaActivoPeriodo = validaActivoPeriodo;
    }
}
