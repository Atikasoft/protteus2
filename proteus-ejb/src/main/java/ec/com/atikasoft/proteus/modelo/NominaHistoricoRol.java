package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Henry Molina <henry.molina@marcasoft.ec>
 */
@Entity
@Table(name = "nominas_historicos_roles", catalog = "sch_proteus")

public class NominaHistoricoRol extends EntidadBasica {

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
     * Nombres.
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * partidaIndividual.
     */
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * sueldo.
     */
    @Column(name = "sueldo")
    private BigDecimal sueldo;

    /**
     * codigo_dependencia.
     */
    @Column(name = "codigo_dependencia")
    private String codigoDependencia;

    /**
     * nombre_dependencia.
     */
    @Column(name = "nombre_dependencia")
    private String nombreDependencia;

    /**
     * tipo_rubro
     */
    @Column(name = "tipo_rubro")
    private String tipoRubro;

    /**
     * codigo_rubro.
     */
    @Column(name = "codigo_rubro")
    private String codigoRubro;

    /**
     * nombre_rubro.
     */
    @Column(name = "nombre_rubro")
    private String nombreRubro;

    /**
     * tipo_nomina.
     */
    @Column(name = "tipo_nomina")
    private String tipoNomina;

    /**
     * numero_nomina.
     */
    @Column(name = "numero_nomina")
    private String numeroNomina;

    /**
     * fecha_generacion.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_generacion")
    private Date fechaGeneracion;

    /**
     * Anio
     */
    @Column(name = "anio")
    private Integer anio;

    /**
     * Mes
     */
    @Column(name = "mes")
    private Integer mes;

    /**
     * valor.
     */
    @Column(name = "valor")
    private BigDecimal valor;

    /**
     * tipo_identificacion_beneficiario.
     */
    @Column(name = "tipo_identificacion_beneficiario")
    private String tipoIdentificacionBeneficiario;

    /**
     * numero_identificacion_beneficiario.
     */
    @Column(name = "numero_identificacion_beneficiario")
    private String numeroIdentificacionBeneficiario;

    /**
     * nombres_beneficiario.
     */
    @Column(name = "nombres_beneficiario")
    private String nombresBeneficiario;

    /**
     *
     */
    @Column(name = "gravable")
    private Boolean gravable;

    /**
     * Constructor.
     */
    public NominaHistoricoRol() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public NominaHistoricoRol(final Long id) {
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
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @return the sueldo
     */
    public BigDecimal getSueldo() {
        return sueldo;
    }

    /**
     * @param sueldo the sueldo to set
     */
    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    /**
     * @return the codigoDependencia
     */
    public String getCodigoDependencia() {
        return codigoDependencia;
    }

    /**
     * @param codigoDependencia the codigoDependencia to set
     */
    public void setCodigoDependencia(String codigoDependencia) {
        this.codigoDependencia = codigoDependencia;
    }

    /**
     * @return the nombreDependencia
     */
    public String getNombreDependencia() {
        return nombreDependencia;
    }

    /**
     * @param nombreDependencia the nombreDependencia to set
     */
    public void setNombreDependencia(String nombreDependencia) {
        this.nombreDependencia = nombreDependencia;
    }

    /**
     * @return the tipoRubro
     */
    public String getTipoRubro() {
        return tipoRubro;
    }

    /**
     * @param tipoRubro the tipoRubro to set
     */
    public void setTipoRubro(String tipoRubro) {
        this.tipoRubro = tipoRubro;
    }

    /**
     * @return the codigoRubro
     */
    public String getCodigoRubro() {
        return codigoRubro;
    }

    /**
     * @param codigoRubro the codigoRubro to set
     */
    public void setCodigoRubro(String codigoRubro) {
        this.codigoRubro = codigoRubro;
    }

    /**
     * @return the nombreRubro
     */
    public String getNombreRubro() {
        return nombreRubro;
    }

    /**
     * @param nombreRubro the nombreRubro to set
     */
    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    /**
     * @return the tipoNomina
     */
    public String getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the numeroNomina
     */
    public String getNumeroNomina() {
        return numeroNomina;
    }

    /**
     * @param numeroNomina the numeroNomina to set
     */
    public void setNumeroNomina(String numeroNomina) {
        this.numeroNomina = numeroNomina;
    }

    /**
     * @return the fechaGeneracion
     */
    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * @param fechaGeneracion the fechaGeneracion to set
     */
    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * @return the anio
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * @return the mes
     */
    public Integer getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Integer mes) {
        this.mes = mes;
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
     * @return the tipoIdentificacionBeneficiario
     */
    public String getTipoIdentificacionBeneficiario() {
        return tipoIdentificacionBeneficiario;
    }

    /**
     * @param tipoIdentificacionBeneficiario the tipoIdentificacionBeneficiario
     * to set
     */
    public void setTipoIdentificacionBeneficiario(String tipoIdentificacionBeneficiario) {
        this.tipoIdentificacionBeneficiario = tipoIdentificacionBeneficiario;
    }

    /**
     * @return the numeroIdentificacionBeneficiario
     */
    public String getNumeroIdentificacionBeneficiario() {
        return numeroIdentificacionBeneficiario;
    }

    /**
     * @param numeroIdentificacionBeneficiario the
     * numeroIdentificacionBeneficiario to set
     */
    public void setNumeroIdentificacionBeneficiario(String numeroIdentificacionBeneficiario) {
        this.numeroIdentificacionBeneficiario = numeroIdentificacionBeneficiario;
    }

    /**
     * @return the nombresBeneficiario
     */
    public String getNombresBeneficiario() {
        return nombresBeneficiario;
    }

    /**
     * @param nombresBeneficiario the nombresBeneficiario to set
     */
    public void setNombresBeneficiario(String nombresBeneficiario) {
        this.nombresBeneficiario = nombresBeneficiario;
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
    public void setGravable(Boolean gravable) {
        this.gravable = gravable;
    }

}
