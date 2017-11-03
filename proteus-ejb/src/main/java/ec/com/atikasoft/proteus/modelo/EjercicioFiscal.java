package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "ejercicios_fiscales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EjercicioFiscal.BUSCAR_POR_NOMBRE, query = "SELECT ef FROM EjercicioFiscal ef where ef.nombre like ?1"),
    @NamedQuery(name = EjercicioFiscal.BUSCAR_ACTIVO, query = "Select o from EjercicioFiscal o where o.vigente=true order by o.fechaInicio desc"),
    @NamedQuery(name = EjercicioFiscal.BUSCAR_EJERCICIOS_SON_PROXIMOS_EJERCICIOS, query = "Select o from EjercicioFiscal o where o.esProximoEjercicio = true")
})
public class EjercicioFiscal extends EntidadBasica {

    /**
     * Nombre de la consulta buscar ejercicios fiscales por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "EjercicioFiscal.BuscarporNombre";

    /**
     * Nombre de la consulta.
     */
    public static final String BUSCAR_ACTIVO = "EjercicioFiscal.buscarActivo";
    
    /**
     * BUSCAR EJERCICIO FISCAL SIGUIENTE AL ACTUAL
     */
    public static final String BUSCAR_EJERCICIOS_SON_PROXIMOS_EJERCICIOS = "EjercicioFiscal.buscarEjerciciosFiscalesSonProximosEjercicios";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "fraccion_basica")
    private BigDecimal fraccionBasica;

    /**
     *
     */
    @Column(name = "maximo_deducible_sobre_fracion_basica")
    private BigDecimal maximoDeducibleSobreFracionBasica;

    /**
     *
     */
    @Column(name = "porcentaje_maximo_deducible_sobre_ingresos")
    private BigDecimal porcentajeMaximoDeducibleSobreIngresos;

    /**
     *
     */
    @Column(name = "exoneracion_sobre_discapacidad")
    private BigDecimal exoneracionSobreDiscapacidad;

    /**
     *
     */
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;

    /**
     *
     */
    @Column(name = "numero_anios_tercera_edad")
    private BigDecimal numeroAniosTerceraEdad;
    
    /**
     * EL VALOR ES TRUE CUANDO SE AGREGA UN NUEVO EJERCICIO FISCAL ANTES DE QUE TERMINE EL ACTUAL
     * CUANDO ESE NUEVO EJERCICIO PASA A SER EL ACTUAL O VENCIO EL VALOR DEBE SER SSETEADO A FALSO
     */
    @Column(name = "es_proximo_ejercicio")
    private Boolean esProximoEjercicio;

    /**
     * EL VALOR ES TRUE CUANDO ESTE APROBADO EL IMPUESTO A LA RENTA PARA EL EJERCICIO FISCAL
     */
    @Column(name = "impuesto_renta_completado")
    private Boolean impuestoRentaCompletado;

    /**
     * RUC DEL CONTADOR PARA EL IMPUESTO A LA RENTA DEL EJERCICIO FISCAL
     */
    @Column(name = "ruc_contador")
    private String rucContador;
    
    /**
     * FIRMA DEL CONTADOR PARA EL IMPUESTO A LA RENTA DEL EJERCICIO FISCAL
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "firma_contador_imagen")
    private byte[] firmaContador;
    
    /**
     * FIRMA DEL AGENTE DE RETENCION PARA EL IMPUESTO A LA RENTA DEL EJERCICIO FISCAL
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "firma_agente_retencion_imagen")
    private byte[] firmaAgenteRetencion;

    /**
     * FECHA DE ENTREGA DEL FORMULARIO 107
     */
    @Column(name = "fecha_entrega_formulario_107")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntregaFormulario107;

    /**
     * Constructor.
     */
    public EjercicioFiscal() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public EjercicioFiscal(final Long id) {
        super();
        this.id = id;
    }

    /**
     * Constructor.
     *
     * @param id
     * @param nombre
     * @param fechaInicio
     * @param fechaFin
     * @param fechaCreacion
     * @param usuarioCreacion
     * @param vigente
     */
    public EjercicioFiscal(final Long id, final String nombre, final Date fechaInicio, final Date fechaFin) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the fraccionBasica
     */
    public BigDecimal getFraccionBasica() {
        return fraccionBasica;
    }

    /**
     * @param fraccionBasica the fraccionBasica to set
     */
    public void setFraccionBasica(BigDecimal fraccionBasica) {
        this.fraccionBasica = fraccionBasica;
    }

    /**
     * @return the maximoDeducibleSobreFracionBasica
     */
    public BigDecimal getMaximoDeducibleSobreFracionBasica() {
        return maximoDeducibleSobreFracionBasica;
    }

    /**
     * @param maximoDeducibleSobreFracionBasica the maximoDeducibleSobreFracionBasica to set
     */
    public void setMaximoDeducibleSobreFracionBasica(BigDecimal maximoDeducibleSobreFracionBasica) {
        this.maximoDeducibleSobreFracionBasica = maximoDeducibleSobreFracionBasica;
    }

    /**
     * @return the porcentajeMaximoDeducibleSobreIngresos
     */
    public BigDecimal getPorcentajeMaximoDeducibleSobreIngresos() {
        return porcentajeMaximoDeducibleSobreIngresos;
    }

    /**
     * @param porcentajeMaximoDeducibleSobreIngresos the porcentajeMaximoDeducibleSobreIngresos to set
     */
    public void setPorcentajeMaximoDeducibleSobreIngresos(BigDecimal porcentajeMaximoDeducibleSobreIngresos) {
        this.porcentajeMaximoDeducibleSobreIngresos = porcentajeMaximoDeducibleSobreIngresos;
    }

    /**
     * @return the exoneracionSobreDiscapacidad
     */
    public BigDecimal getExoneracionSobreDiscapacidad() {
        return exoneracionSobreDiscapacidad;
    }

    /**
     * @param exoneracionSobreDiscapacidad the exoneracionSobreDiscapacidad to set
     */
    public void setExoneracionSobreDiscapacidad(BigDecimal exoneracionSobreDiscapacidad) {
        this.exoneracionSobreDiscapacidad = exoneracionSobreDiscapacidad;
    }

    /**
     * @return the exoneracionTerceraEdad
     */
    public BigDecimal getExoneracionTerceraEdad() {
        return exoneracionTerceraEdad;
    }

    /**
     * @param exoneracionTerceraEdad the exoneracionTerceraEdad to set
     */
    public void setExoneracionTerceraEdad(BigDecimal exoneracionTerceraEdad) {
        this.exoneracionTerceraEdad = exoneracionTerceraEdad;
    }

    /**
     * @return the numeroAniosTerceraEdad
     */
    public BigDecimal getNumeroAniosTerceraEdad() {
        return numeroAniosTerceraEdad;
    }

    /**
     * @param numeroAniosTerceraEdad the numeroAniosTerceraEdad to set
     */
    public void setNumeroAniosTerceraEdad(BigDecimal numeroAniosTerceraEdad) {
        this.numeroAniosTerceraEdad = numeroAniosTerceraEdad;
    }

    /**
     * 
     * @return 
     */
    public Boolean getEsProximoEjercicio() {
        return esProximoEjercicio;
    }

    /**
     * 
     * @param esProximoEjercicio 
     */
    public void setEsProximoEjercicio(Boolean esProximoEjercicio) {
        this.esProximoEjercicio = esProximoEjercicio;
    }

    /**
     * 
     * @return impuestoRentaCompletado
     */
    public Boolean getImpuestoRentaCompletado() {
        return impuestoRentaCompletado;
    }

    /**
     * 
     * @param impuestoRentaCompletado 
     */
    public void setImpuestoRentaCompletado(Boolean impuestoRentaCompletado) {
        this.impuestoRentaCompletado = impuestoRentaCompletado;
    }

    /**
     * 
     * @return rucContador
     */
    public String getRucContador() {
        return rucContador;
    }

    /**
     * 
     * @param rucContador 
     */
    public void setRucContador(String rucContador) {
        this.rucContador = rucContador;
    }

    /**
     * 
     * @return firmaContador
     */
    public byte[] getFirmaContador() {
        return firmaContador;
    }

    /**
     * 
     * @param firmaContador 
     */
    public void setFirmaContador(byte[] firmaContador) {
        this.firmaContador = firmaContador;
    }

    /**
     * 
     * @return firmaAgenteRetencion
     */
    public byte[] getFirmaAgenteRetencion() {
        return firmaAgenteRetencion;
    }

    /**
     * 
     * @param firmaAgenteRetencion 
     */
    public void setFirmaAgenteRetencion(byte[] firmaAgenteRetencion) {
        this.firmaAgenteRetencion = firmaAgenteRetencion;
    }

    /**
     * 
     * @return fechaEntregaFormulario107
     */
    public Date getFechaEntregaFormulario107() {
        return fechaEntregaFormulario107;
    }

    /**
     * 
     * @param fechaEntregaFormulario107 
     */
    public void setFechaEntregaFormulario107(Date fechaEntregaFormulario107) {
        this.fechaEntregaFormulario107 = fechaEntregaFormulario107;
    }
    
    
}
