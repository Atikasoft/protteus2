/*
 *  Rubro.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "rubros", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Rubro.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM Rubro a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Rubro.BUSCAR_VIGENTES,
            query = "SELECT a FROM Rubro a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Rubro.BUSCAR_POR_TIPO,
            query = "SELECT a FROM Rubro a where a.vigente=true and a.tipo=?1 and a.beneficiarioUnico=false order by a.nombre"),
    @NamedQuery(name = Rubro.BUSCAR_POR_TIPO_BENEFICIARIO,
            query = "SELECT a FROM Rubro a where a.vigente=true and a.tipoBeneficiario = ?1 order by a.nombre"),
    @NamedQuery(name = Rubro.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM Rubro a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class Rubro extends EntidadBasica {

    /**
     * Variable para busqueda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Rubro.buscarporNombre ";

    /**
     * Variable para busqueda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "Rubro.buscarporCodigo ";

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Rubro.buscarVigente";

    /**
     * Nombre para busqueda por Tipo de Rubro.
     */
    public static final String BUSCAR_POR_TIPO = "Rubro.buscarPorTipo";

    /**
     * Nombre para busqueda por Tipo de Beneficiario.
     */
    public static final String BUSCAR_POR_TIPO_BENEFICIARIO = "Rubro.buscarPorTipoBeneficiario";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo código.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo Tipo de rubro: <I>ngreso de Servidores,Ingresos de <A>nticipos,
     * <D>escuentos,
     * <P>
     * Aporte institucional,
     * <R>ecuperacion Anticipos
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * Referencia a codigoContable
     */
    @JoinColumn(name = "codigo_contable_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CodigoContable codigoContable;

    /**
     * codigoContable id.
     */
    @Column(name = "codigo_contable_id")
    private Long idCodigoContable;

    /**
     * Referencia a codigoContable
     */
    @JoinColumn(name = "dato_adicional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DatoAdicional datoAdicional;

    /**
     * codigoContable id.
     */
    @Column(name = "dato_adicional_id")
    private Long idDatoAdicional;

    /**
     * Referencia a codigoContable
     */
    @JoinColumn(name = "formula_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Formula formula;

    /**
     * codigoContable id.
     */
    @Column(name = "formula_id")
    private Long idFormula;

    /**
     * Partida.
     */
    @JoinColumn(name = "partida_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Partida partida;

    /**
     * Partida.
     */
    @Column(name = "partida_id")
    private Long partidaId;

    /**
     * Campo Prioridad de descuento 1-10
     */
    @Column(name = "prioridad_descuento")
    private Integer prioridadDescuento;

    /**
     * Indica si se realiza decuento a la prioridad.
     */
    @Column(name = "con_descuento_parcial")
    private Boolean conDescuentoParcial;

    /**
     * Campo que identifica si es beneficiario unico.
     */
    @Column(name = "beneficiario_unico")
    private Boolean beneficiarioUnico;

    /**
     * Campo tipo de beneficiario: <N>Normal o <E> Especial.
     */
    @Column(name = "tipo_beneficiario")
    private String tipoBeneficiario;

    /**
     * Campo Identificacion del beneficiario
     */
    @Column(name = "identificacion_beneficiario")
    private String identificacionBeneficiario;

    /**
     * Campo Identificacion del beneficiario
     */
    @Column(name = "nombre_beneficiario")
    private String nombreBeneficiario;

    /**
     * Campo que identifica si es con enlaces al presupuesto
     */
    @Column(name = "con_enlaces")
    private Boolean conEnlaces;

    /**
     * Campo uso: <F>ormula, <D>ato adicional, <N>inguno
     */
    @Column(name = "uso")
    private String uso;

    /**
     * Campo utilizado para calculo capacidad de pago
     */
    @Column(name = "calculo_capacidad_pago")
    private Boolean calculoCapacidadPago;

    /**
     * Campo Prioridad Determina si un rubro es un ingreso gravable.
     */
    @Column(name = "gravable")
    private Boolean gravable;

    /**
     * Campo Décimo Tercero, determina si es un rubro considerado como Décimo
     * Tercero.
     */
    @Column(name = "es_decimo_tercero")
    private Boolean esDecimoTercero;

    /**
     * Campo Décimo Cuarto, determina si es un rubro considerado como Décimo
     * Cuarto.
     */
    @Column(name = "es_decimo_cuarto")
    private Boolean esDecimoCuarto;

    /**
     * Campo esImpuestoRenta Determina si es un rubro de Impuesto a la renta.
     */
    @Column(name = "es_impuesto_renta")
    private Boolean esImpuestoRenta;

    /**
     * Campo esFondoReserva Determina si un rubro de fondos de reserva.
     */
    @Column(name = "es_fondos_reserva")
    private Boolean esFondoReserva;
    /**
     * Campo esRmu Determina si un rubro de sueldos.
     */
    @Column(name = "es_rmu")
    private Boolean esRmu;
    /**
     * Usado en la proyeccion del impuesto a la renta.
     */
    @Column(name = "es_proyeccion_impuesto_renta")
    private Boolean esProyeccionImpuestoRenta;

    /**
     *
     */
    @Column(name = "clasificador_gasto_a")
    private String clasificadorGastoA;

    /**
     *
     */
    @Column(name = "clasificador_gasto_b")
    private String clasificadorGastoB;

    /**
     * Campo ordinal.
     */
    @Column(name = "ordinal")
    private Integer ordinal;

    /**
     * Indica la fase donde se ejecutar el rubro, es util para calculo de rubros
     * que dependen de otros calculos.
     */
    @Column(name = "fase")
    private Integer fase;

    /**
     * Lista de rubro periodo nomina
     */
    @OneToMany(mappedBy = "rubro")
    private List<RubroPeriodoNomina> listaRubrosPeriodoNomina;

    /**
     * Lista de rubro tipo nomina
     */
    @OneToMany(mappedBy = "rubro")
    private List<RubroTipoNomina> listaRubrosTipoNomina;

    /**
     * Campo que identifica si es beneficiario unico.
     */
    @Column(name = "tipo_identificacion_beneficiario")
    private String tipoIdentificacionBeneficiario;

    /**
     * Constructor.
     */
    public Rubro() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Rubro(final Long id) {
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
     * @return the codigoContable
     */
    public CodigoContable getCodigoContable() {
        return codigoContable;
    }

    /**
     * @param codigoContable the codigoContable to set
     */
    public void setCodigoContable(final CodigoContable codigoContable) {
        this.codigoContable = codigoContable;
    }

    /**
     * @return the idCodigoContable
     */
    public Long getIdCodigoContable() {
        return idCodigoContable;
    }

    /**
     * @param idCodigoContable the idCodigoContable to set
     */
    public void setIdCodigoContable(final Long idCodigoContable) {
        this.idCodigoContable = idCodigoContable;
    }

    /**
     * @return the datoAdicional
     */
    public DatoAdicional getDatoAdicional() {
        return datoAdicional;
    }

    /**
     * @param datoAdicional the datoAdicional to set
     */
    public void setDatoAdicional(final DatoAdicional datoAdicional) {
        this.datoAdicional = datoAdicional;
    }

    /**
     * @return the idDatoAdicional
     */
    public Long getIdDatoAdicional() {
        return idDatoAdicional;
    }

    /**
     * @param idDatoAdicional the idDatoAdicional to set
     */
    public void setIdDatoAdicional(final Long idDatoAdicional) {
        this.idDatoAdicional = idDatoAdicional;
    }

    /**
     * @return the formula
     */
    public Formula getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    /**
     * @return the idFormula
     */
    public Long getIdFormula() {
        return idFormula;
    }

    /**
     * @param idFormula the idFormula to set
     */
    public void setIdFormula(final Long idFormula) {
        this.idFormula = idFormula;
    }

    /**
     * @return the prioridadDescuento
     */
    public Integer getPrioridadDescuento() {
        return prioridadDescuento;
    }

    /**
     * @param prioridadDescuento the prioridadDescuento to set
     */
    public void setPrioridadDescuento(final Integer prioridadDescuento) {
        this.prioridadDescuento = prioridadDescuento;
    }

    /**
     * @return the beneficiarioUnico
     */
    public Boolean getBeneficiarioUnico() {
        return beneficiarioUnico;
    }

    /**
     * @param beneficiarioUnico the beneficiarioUnico to set
     */
    public void setBeneficiarioUnico(final Boolean beneficiarioUnico) {
        this.beneficiarioUnico = beneficiarioUnico;
    }

    /**
     * @return the tipoBeneficiario
     */
    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(final String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @return the identificacionBeneficiario
     */
    public String getIdentificacionBeneficiario() {
        return identificacionBeneficiario;
    }

    /**
     * @param identificacionBeneficiario the identificacionBeneficiario to set
     */
    public void setIdentificacionBeneficiario(final String identificacionBeneficiario) {
        this.identificacionBeneficiario = identificacionBeneficiario;
    }

    /**
     * @return the nombreBeneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * @param nombreBeneficiario the nombreBeneficiario to set
     */
    public void setNombreBeneficiario(final String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the conEnlaces
     */
    public Boolean getConEnlaces() {
        return conEnlaces;
    }

    /**
     * @param conEnlaces the conEnlaces to set
     */
    public void setConEnlaces(final Boolean conEnlaces) {
        this.conEnlaces = conEnlaces;
    }

    /**
     * @return the uso
     */
    public String getUso() {
        return uso;
    }

    /**
     * @param uso the uso to set
     */
    public void setUso(final String uso) {
        this.uso = uso;
    }

    /**
     * @return the calculoCapacidadPago
     */
    public Boolean getCalculoCapacidadPago() {
        return calculoCapacidadPago;
    }

    /**
     * @param calculoCapacidadPago the calculoCapacidadPago to set
     */
    public void setCalculoCapacidadPago(final Boolean calculoCapacidadPago) {
        this.calculoCapacidadPago = calculoCapacidadPago;
    }

    /**
     * @return the listaRubrosPeriodoNomina
     */
    public List<RubroPeriodoNomina> getListaRubrosPeriodoNomina() {
        return listaRubrosPeriodoNomina;
    }

    /**
     * @param listaRubrosPeriodoNomina the listaRubrosPeriodoNomina to set
     */
    public void setListaRubrosPeriodoNomina(final List<RubroPeriodoNomina> listaRubrosPeriodoNomina) {
        this.listaRubrosPeriodoNomina = listaRubrosPeriodoNomina;
    }

    /**
     * @return the listaRubrosTipoNomina
     */
    public List<RubroTipoNomina> getListaRubrosTipoNomina() {
        return listaRubrosTipoNomina;
    }

    /**
     * @param listaRubrosTipoNomina the listaRubrosTipoNomina to set
     */
    public void setListaRubrosTipoNomina(final List<RubroTipoNomina> listaRubrosTipoNomina) {
        this.listaRubrosTipoNomina = listaRubrosTipoNomina;
    }

    /**
     * @return the gravable
     */
    public Boolean getGravable() {
        return gravable;
    }

    /**
     * @param gravable the gravable to set
     */
    public void setGravable(final Boolean gravable) {
        this.gravable = gravable;
    }

    /**
     * @return the conDescuentoParcial
     */
    public Boolean getConDescuentoParcial() {
        return conDescuentoParcial;
    }

    /**
     * @param conDescuentoParcial the conDescuentoParcial to set
     */
    public void setConDescuentoParcial(final Boolean conDescuentoParcial) {
        this.conDescuentoParcial = conDescuentoParcial;
    }

    /**
     * @return the clasificadorGastoA
     */
    public String getClasificadorGastoA() {
        return clasificadorGastoA;
    }

    /**
     * @return the clasificadorGastoB
     */
    public String getClasificadorGastoB() {
        return clasificadorGastoB;
    }

    /**
     * @param clasificadorGastoA the clasificadorGastoA to set
     */
    public void setClasificadorGastoA(final String clasificadorGastoA) {
        this.clasificadorGastoA = clasificadorGastoA;
    }

    /**
     * @param clasificadorGastoB the clasificadorGastoB to set
     */
    public void setClasificadorGastoB(final String clasificadorGastoB) {
        this.clasificadorGastoB = clasificadorGastoB;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public String getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario the tipoIdentificacionBeneficiario
     * to set
     */
    public void setTipoIdentificacionBeneficiario(final String tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the ordinal
     */
    public Integer getOrdinal() {
        return ordinal;
    }

    /**
     * @param ordinal the ordinal to set
     */
    public void setOrdinal(final Integer ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @return the partida
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * @return the partidaId
     */
    public Long getPartidaId() {
        return partidaId;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(final Partida partida) {
        this.partida = partida;
    }

    /**
     * @param partidaId the partidaId to set
     */
    public void setPartidaId(final Long partidaId) {
        this.partidaId = partidaId;
    }

    /**
     * @return the esDecimoTercero
     */
    public Boolean getEsDecimoTercero() {
        return esDecimoTercero;
    }

    /**
     * @param esDecimoTercero the esDecimoTercero to set
     */
    public void setEsDecimoTercero(Boolean esDecimoTercero) {
        this.esDecimoTercero = esDecimoTercero;
    }

    /**
     * @return the esDecimoCuarto
     */
    public Boolean getEsDecimoCuarto() {
        return esDecimoCuarto;
    }

    /**
     * @param esDecimoCuarto the esDecimoCuarto to set
     */
    public void setEsDecimoCuarto(Boolean esDecimoCuarto) {
        this.esDecimoCuarto = esDecimoCuarto;
    }

    /**
     * @return the esImpuestoRenta
     */
    public Boolean getEsImpuestoRenta() {
        return esImpuestoRenta;
    }

    /**
     * @param esImpuestoRenta the esImpuestoRenta to set
     */
    public void setEsImpuestoRenta(Boolean esImpuestoRenta) {
        this.esImpuestoRenta = esImpuestoRenta;
    }

    /**
     * @return the esFondoReserva
     */
    public Boolean getEsFondoReserva() {
        return esFondoReserva;
    }

    /**
     * @param esFondoReserva the esFondoReserva to set
     */
    public void setEsFondoReserva(Boolean esFondoReserva) {
        this.esFondoReserva = esFondoReserva;
    }

    /**
     * @return the esRmu
     */
    public Boolean getEsRmu() {
        return esRmu;
    }

    /**
     * @param esRmu the esRmu to set
     */
    public void setEsRmu(Boolean esRmu) {
        this.esRmu = esRmu;
    }

    /**
     * @return the fase
     */
    public Integer getFase() {
        return fase;
    }

    /**
     * @param fase the fase to set
     */
    public void setFase(Integer fase) {
        this.fase = fase;
    }

    /**
     * @return the esProyeccionImpuestoRenta
     */
    public Boolean getEsProyeccionImpuestoRenta() {
        return esProyeccionImpuestoRenta;
    }

    /**
     * @param esProyeccionImpuestoRenta the esProyeccionImpuestoRenta to set
     */
    public void setEsProyeccionImpuestoRenta(Boolean esProyeccionImpuestoRenta) {
        this.esProyeccionImpuestoRenta = esProyeccionImpuestoRenta;
    }
}
