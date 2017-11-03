/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.modelo.nomina.NominaDetalleNovedad;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "nominas_detalle", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaDetalle.BUSCAR_SERVIDOR_MINIMO_PAGAR, query = "SELECT o FROM NominaDetalle o WHERE o.vigente=true AND o.nomina.id=?1 AND o.tipoIdentificacion=?2 AND o.numeroIdentificacion=?3 AND o.prioridadConDescuento=true AND o.codigoRubroDescuentos IS NOT NULL ORDER BY o.prioridad DESC   "),
    @NamedQuery(name = NominaDetalle.BUSCAR_POR_NOMINA, query = "SELECT o FROM NominaDetalle o WHERE o.vigente=true AND o.nomina.id=?1 ORDER BY o.prioridad DESC "),
    @NamedQuery(name = NominaDetalle.BUSCAR_POR_NOMINA_Y_SERVIDOR, query = "SELECT o FROM NominaDetalle o WHERE o.vigente=true AND o.nomina.id=?1 AND o.numeroIdentificacion=?2 ORDER BY o.nombreRubroIngreso,o.nombreRubroDescuentos,o.nombreRubroAportes "),
    @NamedQuery(name = NominaDetalle.ELIMINAR, query = "DELETE FROM NominaDetalle o  WHERE o.nomina.id=?1"),
    @NamedQuery(name = NominaDetalle.BUSCAR_POR_PUESTO,
            query = "SELECT DISTINCT nd.nomina.periodoNomina.ejercicioFiscal.nombre, "
                    + "nd.nomina.periodoNomina.nombre,nd.nomina.periodoNomina.numero, "
                    + "nd.nomina.numero,nd.nomina.tipoNomina.nombre,"
                    + "nd.nombres FROM NominaDetalle nd"
                    + " WHERE nd.distributivoDetalle.id=?1 and nd.vigente=true "
                    + " ORDER BY nd.nomina.periodoNomina.ejercicioFiscal.nombre DESC, nd.nomina.periodoNomina.numero DESC,"
                    + " nd.nomina.numero"
    ),
    @NamedQuery(name = NominaDetalle.ELIMINAR_POR_SERVIDOR, query = "DELETE FROM NominaDetalle o WHERE o.nomina.id=?1 AND  o.distributivoDetalle.id=?2  "),
    @NamedQuery(name = NominaDetalle.CONTAR_MENSUALMENTE, query = "SELECT COUNT(o) FROM NominaDetalle o WHERE o.nomina.institucionEjercicioFiscalId=?1 AND  o.nomina.tipoNomina.id=?2 AND o.nomina.periodoNominaId=?3 AND o.nomina.id<>?4 AND o.servidorId=?5 AND o.nomina.estado IN ('A','V','R','P')"),
    @NamedQuery(name = NominaDetalle.CONTAR_ANUALMENTE, query = "SELECT COUNT(o) FROM NominaDetalle o WHERE o.nomina.institucionEjercicioFiscalId=?1 AND  o.nomina.tipoNomina.id=?2 AND o.nomina.id<>?3 AND o.servidorId=?4 AND  o.nomina.estado IN ('A','V','R','P')"),
    @NamedQuery(name = NominaDetalle.CONTAR_SERVIDORES_EJECUTADOS, query = "SELECT COUNT(DISTINCT o.numeroIdentificacion) FROM NominaDetalle o WHERE o.nomina.id=?1 AND o.tipo='SER'"),
    @NamedQuery(name = NominaDetalle.SUMAR_POR_RUBRO_PERIODO_SERVIDOR, query = "SELECT SUM(o.valorDescontadoRubroIngreso) FROM NominaDetalle o WHERE  o.nomina.periodoNomina.id=?1 AND o.servidorId=?2 AND o.codigoRubroIngreso=?3 ")
})
public class NominaDetalle extends EntidadBasica {

    private static final long serialVersionUID = 1L;

    /**
     * Nombre de la consulta que recupera los rubros de un servidor.
     */
    public static final String BUSCAR_SERVIDOR_MINIMO_PAGAR = "NominaDetalle.buscarPorServidorMinimoPagar";

    /**
     * Nombre de la consulta por nomina.
     */
    public static final String BUSCAR_POR_NOMINA = "NominaDetalle.buscarPorNomina";
    /**
     * Nombre de la consulta buscar por puesto.
     */
    public static final String BUSCAR_POR_PUESTO = "NominaDetalle.buscarPorPuesto";

    /**
     * Nombre de la consulta por nomina.
     */
    public static final String BUSCAR_POR_NOMINA_Y_SERVIDOR = "NominaDetalle.buscarPorNominaYServidor";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR = "NominaDetalle.eliminar";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR_POR_SERVIDOR = "NominaDetalle.eliminarPorServidor";

    /**
     * Cuenta los pagos que se realizan en un mes.
     */
    public static final String CONTAR_MENSUALMENTE = "NominaDetalle.contarMensualmente";

    /**
     * Cuenta los pagos que se realizan en un anio.
     */
    public static final String CONTAR_ANUALMENTE = "NominaDetalle.contarAnualmente";

    /**
     * Contar servidores ejecutados.
     */
    public static final String CONTAR_SERVIDORES_EJECUTADOS = "NominaDetalle.contarEjecutados";

    /**
     * Suma el valor de un rubro y periodo.
     */
    public static final String SUMAR_POR_RUBRO_PERIODO_SERVIDOR = "NominaDetalle.buscarPorRubroPeriodoServidor";

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "rubro_ingreso_id")
    private Long rubroIngresoId;

    /**
     *
     */
    @Column(name = "codigo_rubro_ingreso")
    private String codigoRubroIngreso;

    /**
     *
     */
    @Column(name = "nombre_rubro_ingreso")
    private String nombreRubroIngreso;

    /**
     *
     */
    @Column(name = "valor_calculado_rubro_ingreso")
    private BigDecimal valorCalculadoRubroIngreso;

    /**
     *
     */
    @Column(name = "valor_descontado_rubro_ingreso")
    private BigDecimal valorDescontadoRubroIngreso;

    /**
     *
     */
    @Column(name = "rubro_descuentos_id")
    private Long rubroDescuentosId;

    /**
     *
     */
    @Column(name = "codigo_rubro_descuentos")
    private String codigoRubroDescuentos;

    /**
     *
     */
    @Column(name = "nombre_rubro_descuentos")
    private String nombreRubroDescuentos;

    /**
     *
     */
    @Column(name = "valor_calculado_rubro_descuentos")
    private BigDecimal valorCalculadoRubroDescuentos;

    /**
     *
     */
    @Column(name = "valor_descontado_rubro_descuentos")
    private BigDecimal valorDescontadoRubroDescuentos;

    /**
     *
     */
    @Column(name = "rubro_aportes_id")
    private Long rubroAportesId;

    /**
     *
     */
    @Size(max = 20)
    @Column(name = "codigo_rubro_aportes")
    private String codigoRubroAportes;

    /**
     *
     */
    @Size(max = 100)
    @Column(name = "nombre_rubro_aportes")
    private String nombreRubroAportes;

    /**
     *
     */
    @Column(name = "valor_calculado_rubro_aportes")
    private BigDecimal valorCalculadoRubroAportes;

    /**
     *
     */
    @Column(name = "valor_descontado_rubro_aportes")
    private BigDecimal valorDescontadoRubroAportes;

    /**
     *
     */
    @Size(max = 2)
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     *
     */
    @Size(max = 30)
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     *
     */
    @Size(max = 200)
    @Column(name = "nombres")
    private String nombres;

    /**
     *
     */
    @Size(max = 100)
    @Column(name = "codigo_unidad_organizacional")
    private String codigoUnidadOrganizacional;

    /**
     *
     */
    @Size(max = 400)
    @Column(name = "nombre_unidad_organizacional")
    private String nombreUnidadOrganizacional;

    /**
     *
     */
    @Size(max = 100)
    @Column(name = "codigo_modalidad_laboral")
    private String codigoModalidadLaboral;

    /**
     *
     */
    @Size(max = 400)
    @Column(name = "nombre_modalidad_laboral")
    private String nombreModalidadLaboral;

    /**
     *
     */
    @Size(max = 300)
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     *
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     *
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     *
     */
    @Column(name = "valores")
    private String valores;

    /**
     *
     */
    @Column(name = "prioridad_con_descuento")
    private Boolean prioridadConDescuento;

    /**
     *
     */
    @Column(name = "prioridad")
    private Integer prioridad;

    /**
     *
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     *
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     *
     */
    @Column(name = "gravable")
    private Boolean gravable;

    /**
     *
     */
    @Size(max = 2)
    @Column(name = "tipo_identificacion_beneficiario")
    private String tipoIdentificacionBeneficiario;

    /**
     *
     */
    @Size(max = 30)
    @Column(name = "numero_identificacion_beneficiario")
    private String numeroIdentificacionBeneficiario;

    /**
     *
     */
    @Size(max = 200)
    @Column(name = "nombres_beneficiario")
    private String nombresBeneficiario;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distributivo_detalle_id")
    private DistributivoDetalle distributivoDetalle;

    /**
     * Lista de novedades.
     */
    @OneToMany(mappedBy = "nominaDetalle")
    private List<NominaDetalleNovedad> listaNovedades;

    /**
     * Lista de novedades.
     */
    @OneToMany(mappedBy = "nominaDetalle")
    private List<AnticipoPago> listaAnticiposPagos;

    /**
     * Permite identificar si un registro corresponde a una retencion judicial.
     */
    @Column(name = "retencion_judicial")
    private Boolean retencionJudicial;

    /**
     *
     */
    public NominaDetalle() {
        super();
    }

    /**
     *
     * @param id
     */
    public NominaDetalle(final Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCodigoRubroIngreso() {
        return codigoRubroIngreso;
    }

    public void setCodigoRubroIngreso(final String codigoRubroIngreso) {
        this.codigoRubroIngreso = codigoRubroIngreso;
    }

    public String getNombreRubroIngreso() {
        return nombreRubroIngreso;
    }

    public void setNombreRubroIngreso(final String nombreRubroIngreso) {
        this.nombreRubroIngreso = nombreRubroIngreso;
    }

    public BigDecimal getValorCalculadoRubroIngreso() {
        return valorCalculadoRubroIngreso;
    }

    public void setValorCalculadoRubroIngreso(final BigDecimal valorCalculadoRubroIngreso) {
        this.valorCalculadoRubroIngreso = valorCalculadoRubroIngreso;
    }

    public BigDecimal getValorDescontadoRubroIngreso() {
        return valorDescontadoRubroIngreso;
    }

    public void setValorDescontadoRubroIngreso(final BigDecimal valorDescontadoRubroIngreso) {
        this.valorDescontadoRubroIngreso = valorDescontadoRubroIngreso;
    }

    public String getCodigoRubroDescuentos() {
        return codigoRubroDescuentos;
    }

    public void setCodigoRubroDescuentos(final String codigoRubroDescuentos) {
        this.codigoRubroDescuentos = codigoRubroDescuentos;
    }

    public String getNombreRubroDescuentos() {
        return nombreRubroDescuentos;
    }

    public void setNombreRubroDescuentos(final String nombreRubroDescuentos) {
        this.nombreRubroDescuentos = nombreRubroDescuentos;
    }

    public BigDecimal getValorCalculadoRubroDescuentos() {
        return valorCalculadoRubroDescuentos;
    }

    public void setValorCalculadoRubroDescuentos(final BigDecimal valorCalculadoRubroDescuentos) {
        this.valorCalculadoRubroDescuentos = valorCalculadoRubroDescuentos;
    }

    public BigDecimal getValorDescontadoRubroDescuentos() {
        return valorDescontadoRubroDescuentos;
    }

    public void setValorDescontadoRubroDescuentos(final BigDecimal valorDescontadoRubroDescuentos) {
        this.valorDescontadoRubroDescuentos = valorDescontadoRubroDescuentos;
    }

    public String getCodigoRubroAportes() {
        return codigoRubroAportes;
    }

    public void setCodigoRubroAportes(final String codigoRubroAportes) {
        this.codigoRubroAportes = codigoRubroAportes;
    }

    public String getNombreRubroAportes() {
        return nombreRubroAportes;
    }

    public void setNombreRubroAportes(final String nombreRubroAportes) {
        this.nombreRubroAportes = nombreRubroAportes;
    }

    public BigDecimal getValorCalculadoRubroAportes() {
        return valorCalculadoRubroAportes;
    }

    public void setValorCalculadoRubroAportes(final BigDecimal valorCalculadoRubroAportes) {
        this.valorCalculadoRubroAportes = valorCalculadoRubroAportes;
    }

    public BigDecimal getValorDescontadoRubroAportes() {
        return valorDescontadoRubroAportes;
    }

    public void setValorDescontadoRubroAportes(final BigDecimal valorDescontadoRubroAportes) {
        this.valorDescontadoRubroAportes = valorDescontadoRubroAportes;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    public String getCodigoUnidadOrganizacional() {
        return codigoUnidadOrganizacional;
    }

    public void setCodigoUnidadOrganizacional(final String codigoUnidadOrganizacional) {
        this.codigoUnidadOrganizacional = codigoUnidadOrganizacional;
    }

    public String getNombreUnidadOrganizacional() {
        return nombreUnidadOrganizacional;
    }

    public void setNombreUnidadOrganizacional(final String nombreUnidadOrganizacional) {
        this.nombreUnidadOrganizacional = nombreUnidadOrganizacional;
    }

    public String getCodigoModalidadLaboral() {
        return codigoModalidadLaboral;
    }

    public void setCodigoModalidadLaboral(final String codigoModalidadLaboral) {
        this.codigoModalidadLaboral = codigoModalidadLaboral;
    }

    public String getNombreModalidadLaboral() {
        return nombreModalidadLaboral;
    }

    public void setNombreModalidadLaboral(final String nombreModalidadLaboral) {
        this.nombreModalidadLaboral = nombreModalidadLaboral;
    }

    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    public void setPartidaIndividual(final String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NominaDetalle)) {
            return false;
        }
        NominaDetalle other = (NominaDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.atikasoft.proteus.modelo.NominaDetalle[ id=" + id + " ]";
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
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the rmu
     */
    public BigDecimal getRmu() {
        return rmu;
    }

    /**
     * @param rmu the rmu to set
     */
    public void setRmu(final BigDecimal rmu) {
        this.rmu = rmu;
    }

    /**
     * @return the valores
     */
    public String getValores() {
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(final String valores) {
        this.valores = valores;
    }

    /**
     * @return the prioridad
     */
    public Integer getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad the prioridad to set
     */
    public void setPrioridad(final Integer prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the prioridadConDescuento
     */
    public Boolean getPrioridadConDescuento() {
        return prioridadConDescuento;
    }

    /**
     * @param prioridadConDescuento the prioridadConDescuento to set
     */
    public void setPrioridadConDescuento(final Boolean prioridadConDescuento) {
        this.prioridadConDescuento = prioridadConDescuento;
    }

    /**
     * @return the listaNovedades
     */
    public List<NominaDetalleNovedad> getListaNovedades() {
        return listaNovedades;
    }

    /**
     * @param listaNovedades the listaNovedades to set
     */
    public void setListaNovedades(final List<NominaDetalleNovedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    /**
     * @return the retencionJudicial
     */
    public Boolean getRetencionJudicial() {
        return retencionJudicial;
    }

    /**
     * @param retencionJudicial the retencionJudicial to set
     */
    public void setRetencionJudicial(final Boolean retencionJudicial) {
        this.retencionJudicial = retencionJudicial;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(final DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the certificacionPresupuestaria
     */
    public String getCertificacionPresupuestaria() {
        return certificacionPresupuestaria;
    }

    /**
     * @param certificacionPresupuestaria the certificacionPresupuestaria to set
     */
    public void setCertificacionPresupuestaria(final String certificacionPresupuestaria) {
        this.certificacionPresupuestaria = certificacionPresupuestaria;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @return the gravable
     */
    public Boolean getGravable() {
        return gravable;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(final Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @param gravable the gravable to set
     */
    public void setGravable(final Boolean gravable) {
        this.gravable = gravable;
    }

    /**
     * @return the tipoIdentificacionBeneficiario
     */
    public String getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @return the nombresBeneficiario
     */
    public String getNombresBeneficiario() {
        return nombresBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario the tipoIdentificacionBeneficiario
     * to set
     */
    public void setTipoIdentificacionBeneficiario(final String tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario the
     * numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(final String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @param nombresBeneficiario the nombresBeneficiario to set
     */
    public void setNombresBeneficiario(final String nombresBeneficiario) {
        this.nombresBeneficiario = nombresBeneficiario;
    }

    /**
     * @return the listaAnticiposPagos
     */
    public List<AnticipoPago> getListaAnticiposPagos() {
        return listaAnticiposPagos;
    }

    /**
     * @param listaAnticiposPagos the listaAnticiposPagos to set
     */
    public void setListaAnticiposPagos(List<AnticipoPago> listaAnticiposPagos) {
        this.listaAnticiposPagos = listaAnticiposPagos;
    }

    /**
     * @return the rubroIngresoId
     */
    public Long getRubroIngresoId() {
        return rubroIngresoId;
    }

    /**
     * @param rubroIngresoId the rubroIngresoId to set
     */
    public void setRubroIngresoId(Long rubroIngresoId) {
        this.rubroIngresoId = rubroIngresoId;
    }

    /**
     * @return the rubroDescuentosId
     */
    public Long getRubroDescuentosId() {
        return rubroDescuentosId;
    }

    /**
     * @param rubroDescuentosId the rubroDescuentosId to set
     */
    public void setRubroDescuentosId(Long rubroDescuentosId) {
        this.rubroDescuentosId = rubroDescuentosId;
    }

    /**
     * @return the rubroAportesId
     */
    public Long getRubroAportesId() {
        return rubroAportesId;
    }

    /**
     * @param rubroAportesId the rubroAportesId to set
     */
    public void setRubroAportesId(Long rubroAportesId) {
        this.rubroAportesId = rubroAportesId;
    }
}
