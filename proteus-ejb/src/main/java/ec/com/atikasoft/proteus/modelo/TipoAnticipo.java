/*
 *  TipoAnticipo.java
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
 *  02/02/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "tipos_anticipos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoAnticipo.BUSCAR_POR_NOMBRE, query = "SELECT a FROM TipoAnticipo a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = TipoAnticipo.BUSCAR_VIGENTES, query = "SELECT a FROM TipoAnticipo a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = TipoAnticipo.BUSCAR_POR_CODIGO, query = "SELECT a FROM TipoAnticipo a where a.codigo=?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = TipoAnticipo.BUSCAR_POR_REGIMEN, query = "SELECT a FROM TipoAnticipo a where a.vigente=true and a.regimenId=?1 order by a.nombre")
})
public class TipoAnticipo extends EntidadBasica {

    /**
     * Variable para búsqueda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "TipoAnticipo.buscarporNombre";
    /**
     * Variable para búsqueda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "TipoAnticipo.buscarporCodigo";
    /**
     * Nombre para búsqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "TipoAnticipo.buscarVigente";
    /**
     * Nombre para búsqueda de vigentes por regimen.
     */
    public static final String BUSCAR_POR_REGIMEN = "TipoAnticipo.buscarPorRegimen";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Identificador Funcional.
     */
    @NotNull
    @Column(name = "codigo")
    private String codigo;

    /**
     * Es la designación o denominación verbal que se le da a un tipo de
     * anticipo para distinguirlo de otros.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Es la explicación, de forma detallada y ordenada, de cómo es un tipo de
     * anticipo.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Día del mes desde cuando se puede otorgar el anticipo.
     */
    @Column(name = "dia_desde")
    private Integer diaDesde;

    /**
     * Dia del mes hasta cuando se puede otorgar el anticipo, si este dia esta
     * dentro del fin de semana o feriados se debe extender el plazo hasta el
     * siguiente día laborable.
     */
    @Column(name = "dia_hasta")
    private Integer diaHasta;

    /**
     * Mes del año desde cuando se puede otorgar el anticipo.
     */
    @Column(name = "mes_desde")
    private Integer mesDesde;

    /**
     * Mes del año hasta cuando se puede otorgar el anticipo.
     */
    @Column(name = "mes_hasta")
    private Integer mesHasta;

    /**
     * Indica el maximo plazo en meses que se pueden conceder un prestamos, este
     * no puede ser mayor a 12 meses, y puede tener ceros, este limite esta
     * condicionado al fin del año.
     */
    @Column(name = "plazo_maximo_meses")
    private Integer plazoMaximoMeses;

    /**
     * Indica si el tipo de anticipo transciende el ejercicio fiscal.
     */
    @Column(name = "transciende_ejercicio_fiscal")
    private Boolean transciendeEjercicioFiscal;

    /**
     * Monto maximo para el tipo de anticipo, aplica para los tipos de
     * porcentaje.
     */
    @Column(name = "valor_maximo")
    private BigDecimal valorMaximo;

    /**
     * Indica si el techo para el tipo de anticipo se usara porcentaje de un
     * rubro o valor fijo. Porcentaje: P Valor Fijo: V
     */
    @Column(name = "tipo_techo_anticipo")
    private String tipoTechoAnticipo;

    /**
     * Indica un valor el cual sera el techo del tipo del anticipo.
     */
    @Column(name = "valor_techo_anticipo")
    private BigDecimal valorTechoAnticipo;

    /**
     * Indica los tipos de garantia. E : Si el servidor no tienen estabilidad
     * laboral, se necesita garante. R : Si valor anticipo es mayor a RMU, se
     * requiere garante. G : Siempre require garante.
     */
    @Column(name = "tipo_garantia")
    private String tipoGarantia;

    /**
     * Minimo porcentaje del saldo del anticipo para renovacion.
     */
    @Column(name = "porcentaje_maximo_saldo_renovacion")
    private BigDecimal porcentajeMaximoSaldoRenovacion;

    /**
     * Porcentaje que se debe descontar en diciembre.
     */
    @Column(name = "porcentaje_descuento_diciembre")
    private BigDecimal porcentajeDescuentoDiciembre;

    /**
     * Indica si el techo para el tipo de cuota: Porcentaje <R>MU, Porcentaje
     * <c>apacidad de pago.. Porcentaje: P Valor Fijo: V
     */
    @Column(name = "tipo_techo_couta")
    private String tipoTechoCuota;

    /**
     * Indica un valor el cual sera el techo del tipo de la cuota.
     */
    @Column(name = "valor_techo_cuota")
    private BigDecimal valorTechoCuota;

    /**
     * Rubro de ingresos que se asocian al tipo de anticipo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubro_id", insertable = false, updatable = false)
    private Rubro rubro;

    /**
     * Regimen.
     */
    @Column(name = "rubro_id")
    private Long rubroId;

    /**
     * Rubro de ingresos que se asocian al tipo de anticipo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regimen_laboral_id", insertable = false, updatable = false)
    private RegimenLaboral regimen;

    /**
     * Rubro.
     */
    @Column(name = "regimen_laboral_id")
    private Long regimenId;

    /**
     * Constructor.
     */
    public TipoAnticipo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TipoAnticipo(final Long id) {
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
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
    public void setNombre(String nombre) {
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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the diaDesde
     */
    public Integer getDiaDesde() {
        return diaDesde;
    }

    /**
     * @param diaDesde the diaDesde to set
     */
    public void setDiaDesde(Integer diaDesde) {
        this.diaDesde = diaDesde;
    }

    /**
     * @return the diaHasta
     */
    public Integer getDiaHasta() {
        return diaHasta;
    }

    /**
     * @param diaHasta the diaHasta to set
     */
    public void setDiaHasta(Integer diaHasta) {
        this.diaHasta = diaHasta;
    }

    /**
     * @return the mesDesde
     */
    public Integer getMesDesde() {
        return mesDesde;
    }

    /**
     * @param mesDesde the mesDesde to set
     */
    public void setMesDesde(Integer mesDesde) {
        this.mesDesde = mesDesde;
    }

    /**
     * @return the mesHasta
     */
    public Integer getMesHasta() {
        return mesHasta;
    }

    /**
     * @param mesHasta the mesHasta to set
     */
    public void setMesHasta(Integer mesHasta) {
        this.mesHasta = mesHasta;
    }

    /**
     * @return the plazoMaximoMeses
     */
    public Integer getPlazoMaximoMeses() {
        return plazoMaximoMeses;
    }

    /**
     * @param plazoMaximoMeses the plazoMaximoMeses to set
     */
    public void setPlazoMaximoMeses(Integer plazoMaximoMeses) {
        this.plazoMaximoMeses = plazoMaximoMeses;
    }

    /**
     * @return the transciendeEjercicioFiscal
     */
    public Boolean getTransciendeEjercicioFiscal() {
        return transciendeEjercicioFiscal;
    }

    /**
     * @param transciendeEjercicioFiscal the transciendeEjercicioFiscal to set
     */
    public void setTransciendeEjercicioFiscal(Boolean transciendeEjercicioFiscal) {
        this.transciendeEjercicioFiscal = transciendeEjercicioFiscal;
    }

    /**
     * @return the valorMaximo
     */
    public BigDecimal getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param valorMaximo the valorMaximo to set
     */
    public void setValorMaximo(BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    /**
     * @return the tipoTechoAnticipo
     */
    public String getTipoTechoAnticipo() {
        return tipoTechoAnticipo;
    }

    /**
     * @param tipoTechoAnticipo the tipoTechoAnticipo to set
     */
    public void setTipoTechoAnticipo(String tipoTechoAnticipo) {
        this.tipoTechoAnticipo = tipoTechoAnticipo;
    }

    /**
     * @return the valorTechoAnticipo
     */
    public BigDecimal getValorTechoAnticipo() {
        return valorTechoAnticipo;
    }

    /**
     * @param valorTechoAnticipo the valorTechoAnticipo to set
     */
    public void setValorTechoAnticipo(BigDecimal valorTechoAnticipo) {
        this.valorTechoAnticipo = valorTechoAnticipo;
    }

    /**
     * @return the tipoGarantia
     */
    public String getTipoGarantia() {
        return tipoGarantia;
    }

    /**
     * @param tipoGarantia the tipoGarantia to set
     */
    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    /**
     * @return the porcentajeMaximoSaldoRenovacion
     */
    public BigDecimal getPorcentajeMaximoSaldoRenovacion() {
        return porcentajeMaximoSaldoRenovacion;
    }

    /**
     * @param porcentajeMaximoSaldoRenovacion the
     * porcentajeMaximoSaldoRenovacion to set
     */
    public void setPorcentajeMaximoSaldoRenovacion(BigDecimal porcentajeMaximoSaldoRenovacion) {
        this.porcentajeMaximoSaldoRenovacion = porcentajeMaximoSaldoRenovacion;
    }

    /**
     * @return the tipoTechoCuota
     */
    public String getTipoTechoCuota() {
        return tipoTechoCuota;
    }

    /**
     * @param tipoTechoCuota the tipoTechoCuota to set
     */
    public void setTipoTechoCuota(String tipoTechoCuota) {
        this.tipoTechoCuota = tipoTechoCuota;
    }

    /**
     * @return the valorTechoCuota
     */
    public BigDecimal getValorTechoCuota() {
        return valorTechoCuota;
    }

    /**
     * @param valorTechoCuota the valorTechoCuota to set
     */
    public void setValorTechoCuota(BigDecimal valorTechoCuota) {
        this.valorTechoCuota = valorTechoCuota;
    }

    /**
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the rubroId
     */
    public Long getRubroId() {
        return rubroId;
    }

    /**
     * @param rubroId the rubroId to set
     */
    public void setRubroId(Long rubroId) {
        this.rubroId = rubroId;
    }

    /**
     * @return the regimen
     */
    public RegimenLaboral getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the regimen to set
     */
    public void setRegimen(RegimenLaboral regimen) {
        this.regimen = regimen;
    }

    /**
     * @return the regimenId
     */
    public Long getRegimenId() {
        return regimenId;
    }

    /**
     * @param regimenId the regimenId to set
     */
    public void setRegimenId(Long regimenId) {
        this.regimenId = regimenId;
    }

    /**
     * @return the porcentajeDescuentoDiciembre
     */
    public BigDecimal getPorcentajeDescuentoDiciembre() {
        return porcentajeDescuentoDiciembre;
    }

    /**
     * @param porcentajeDescuentoDiciembre the porcentajeDescuentoDiciembre to
     * set
     */
    public void setPorcentajeDescuentoDiciembre(BigDecimal porcentajeDescuentoDiciembre) {
        this.porcentajeDescuentoDiciembre = porcentajeDescuentoDiciembre;
    }

}
