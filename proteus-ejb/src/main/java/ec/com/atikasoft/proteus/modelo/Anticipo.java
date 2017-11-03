/*
 *  Anticipo.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "anticipos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Anticipo.BUSCAR_VIGENTES, query = "SELECT a FROM Anticipo a where a.vigente=true order by a.fechaSolicitud"),
    @NamedQuery(name = Anticipo.BUSCAR_POR_ESTADO_INST_EJERCICIO, query = "SELECT a FROM Anticipo a where a.vigente=true and a.nominaId is null and a.estado = ?1 and a.institucionEjercicioFiscalId=?2 order by a.servidor.apellidos"),
    @NamedQuery(name = Anticipo.BUSCAR_POR_SERVIDOR, query = "SELECT a FROM Anticipo a where a.vigente=true and a.servidorId = ?1 order by a.fechaSolicitud desc"),
    @NamedQuery(name = Anticipo.BUSCAR_POR_NOMINA, query = "SELECT a FROM Anticipo a where a.vigente=true AND a.nominaId=?1"),
    @NamedQuery(name = Anticipo.BUSCAR_POR_NOMINA_SERVIDOR, query = "SELECT a FROM Anticipo a where a.vigente=true AND a.nominaId=?1 AND a.servidorId=?2"),
    @NamedQuery(name = Anticipo.QUITAR_NOMINA, query = "UPDATE Anticipo a SET a.nominaId=null WHERE a.vigente=true AND a.nominaId=?1"),
    @NamedQuery(name = Anticipo.QUITAR_NOMINA_SERVIDOR, query = "UPDATE Anticipo a SET a.nominaId=null WHERE a.vigente=true AND a.nominaId=?1 AND a.servidorId=?2")
})
public class Anticipo extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Anticipo.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_SERVIDOR = "Anticipo.buscarPorServidor";
    /**
     * Nombre para busqueda de vigentes por estado.
     */
    public static final String BUSCAR_POR_ESTADO_INST_EJERCICIO = "Anticipo.buscarPorEstadoInstitucionEjercicioFiscal";

    /**
     *
     */
    public static final String BUSCAR_POR_NOMINA = "Anticipo.buscarPorNomina";
    /**
     *
     */
    public static final String BUSCAR_POR_NOMINA_SERVIDOR = "Anticipo.buscarPorNominaServidor";
    /**
     *
     */
    public static final String QUITAR_NOMINA = "Anticipo.quitarNomina";

    /**
     *
     */
    public static final String QUITAR_NOMINA_SERVIDOR = "Anticipo.quitarNominaServidor";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo identificador del número de trámite por ejercicio fiscal e
     * institucion.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private String numero;

    /**
     * Especifica la fecha de registro de la solicitud de anticipos.
     */
    @Column(name = "fecha_solicitud")
    @Temporal(value = TemporalType.TIMESTAMP)
    @NotNull
    private Date fechaSolicitud;

    /**
     * Referencia a servidor beneficiario.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * servidor id.
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     * Referencia a servidor aprobador.
     */
    @JoinColumn(name = "aprobador_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor aprobador;

    /**
     * servidor id.
     */
    @Column(name = "observaciones")
    private String observaciones;

    /**
     * Referencia a servidor garante.
     */
    @JoinColumn(name = "servidor_garante_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorGarante;

    /**
     * servidor garante id.
     */
    @Column(name = "servidor_garante_id")
    private Long servidorGaranteId;

    /**
     * Referencia a TipoAnticipo.
     */
    @JoinColumn(name = "tipo_anticipo_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoAnticipo tipoAnticipo;

    /**
     * TipoAnticipo id.
     */
    @Column(name = "tipo_anticipo_id")
    private Long tipoAnticipoId;

    /**
     * Referencia a InstitucionEjercicioFiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * InstitucionEjercicioFiscal id.
     */
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;

    /**
     * Referencia a Nomina.
     */
    @JoinColumn(name = "nomina_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Nomina nomina;

    /**
     * NOmina id.
     */
    @Column(name = "nomina_id")
    private Long nominaId;

    /**
     * Campo estado: <R>egistrado.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;

    /**
     * Registra el valor del anticipo.
     */
    @Column(name = "valor")
    @NotNull
    private BigDecimal valor;
    
    /**
     * Registra el saldo que resta por pagar tras hacerse un pago
     */
    @Column(name = "saldo")
    @NotNull
    private BigDecimal saldo;

    /**
     * Registra el plazo en meses para cancelar el total del anticipo.
     */
    @Column(name = "plazo_meses")
    @NotNull
    private Integer plazoMeses;

    /**
     * Registra el valor capacidad de pago.
     */
    @Column(name = "valor_capacidad_pago")
    @NotNull
    private BigDecimal valorCapacidadPago;

    /**
     * Registra el periodo inicio mes/anio.
     */
    @Column(name = "periodo_inicio")
    @NotNull
    private String periodoInicio;

    /**
     * Registra el periodo fin mes/anio.
     */
    @Column(name = "periodo_fin")
    @NotNull
    private String periodoFin;

    /**
     * Lista de cuotas.
     */
    @OneToMany(mappedBy = "anticipo")
    private List<AnticipoPlanPago> listaAnticipoPlaPlago;

    /**
     * Indica si el puesto esta seleccionado.
     */
    @Transient
    private Boolean selecto;

    /**
     * Constructor.
     */
    public Anticipo() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Anticipo(final Long id) {
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
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the servidorGarante
     */
    public Servidor getServidorGarante() {
        return servidorGarante;
    }

    /**
     * @param servidorGarante the servidorGarante to set
     */
    public void setServidorGarante(Servidor servidorGarante) {
        this.servidorGarante = servidorGarante;
    }

    /**
     * @return the servidorGaranteId
     */
    public Long getServidorGaranteId() {
        return servidorGaranteId;
    }

    /**
     * @param servidorGaranteId the servidorGaranteId to set
     */
    public void setServidorGaranteId(Long servidorGaranteId) {
        this.servidorGaranteId = servidorGaranteId;
    }

    /**
     * @return the tipoAnticipo
     */
    public TipoAnticipo getTipoAnticipo() {
        return tipoAnticipo;
    }

    /**
     * @param tipoAnticipo the tipoAnticipo to set
     */
    public void setTipoAnticipo(TipoAnticipo tipoAnticipo) {
        this.tipoAnticipo = tipoAnticipo;
    }

    /**
     * @return the tipoAnticipoId
     */
    public Long getTipoAnticipoId() {
        return tipoAnticipoId;
    }

    /**
     * @param tipoAnticipoId the tipoAnticipoId to set
     */
    public void setTipoAnticipoId(Long tipoAnticipoId) {
        this.tipoAnticipoId = tipoAnticipoId;
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
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the institucionEjercicioFiscalId
     */
    public Long getInstitucionEjercicioFiscalId() {
        return institucionEjercicioFiscalId;
    }

    /**
     * @param institucionEjercicioFiscalId the institucionEjercicioFiscalId to
     * set
     */
    public void setInstitucionEjercicioFiscalId(Long institucionEjercicioFiscalId) {
        this.institucionEjercicioFiscalId = institucionEjercicioFiscalId;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the plazoMeses
     */
    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    /**
     * @param plazoMeses the plazoMeses to set
     */
    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    /**
     * @return the valorCapacidadPago
     */
    public BigDecimal getValorCapacidadPago() {
        return valorCapacidadPago;
    }

    /**
     * @param valorCapacidadPago the valorCapacidadPago to set
     */
    public void setValorCapacidadPago(BigDecimal valorCapacidadPago) {
        this.valorCapacidadPago = valorCapacidadPago;
    }

    /**
     * @return the periodoInicio
     */
    public String getPeriodoInicio() {
        return periodoInicio;
    }

    /**
     * @param periodoInicio the periodoInicio to set
     */
    public void setPeriodoInicio(String periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    /**
     * @return the periodoFin
     */
    public String getPeriodoFin() {
        return periodoFin;
    }

    /**
     * @param periodoFin the periodoFin to set
     */
    public void setPeriodoFin(String periodoFin) {
        this.periodoFin = periodoFin;
    }

    /**
     * @return the listaAnticipoPlaPlago
     */
    public List<AnticipoPlanPago> getListaAnticipoPlaPlago() {
        return listaAnticipoPlaPlago;
    }

    /**
     * @param listaAnticipoPlaPlago the listaAnticipoPlaPlago to set
     */
    public void setListaAnticipoPlaPlago(List<AnticipoPlanPago> listaAnticipoPlaPlago) {
        this.listaAnticipoPlaPlago = listaAnticipoPlaPlago;
    }

    /**
     * @return the aprobador
     */
    public Servidor getAprobador() {
        return aprobador;
    }

    /**
     * @param aprobador the aprobador to set
     */
    public void setAprobador(Servidor aprobador) {
        this.aprobador = aprobador;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the selecto
     */
    public Boolean getSelecto() {
        return selecto;
    }

    /**
     * @param selecto the selecto to set
     */
    public void setSelecto(Boolean selecto) {
        this.selecto = selecto;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
