/*
 *  CotizacionIess.java
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
 *  09/10/2013
 *
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CotizacionIess
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "cotizaciones_iess", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CotizacionIess.BUSCAR_POR_NOMBRE, query = "SELECT c FROM CotizacionIess c where c.nivelOcupacional.nombre like ?1 and c.vigente=true"),
    @NamedQuery(name = CotizacionIess.BUSCAR_VIGENTES, query = "SELECT a FROM CotizacionIess a where a.vigente=true"),
    @NamedQuery(name = CotizacionIess.BUSCAR_POR_NIVEL_OCUPACIONAL, query = "SELECT a FROM CotizacionIess a where a.nivelOcupacionalId=?1")
})
public class CotizacionIess extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NOMBRE = "CotizacionIess.buscarPorNombre";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "CotizacionIess.buscarVigente";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NIVEL_OCUPACIONAL = "CotizacionIess.buscarporNemonico ";

    /**
     * Constructor por defecto.
     */
    public CotizacionIess() {
        super();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * PORCENTAJE ADICINAL APORTE INDIVIDUAL de cotizacion IESS.
     */
    @Column(name = "porcentaj_adicional_aporte_individual")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAdicionalAporteIndividual;

    /**
     * PORCENTAJE ADICINAL APORTE PATRONAL de cotizacion IESS.
     */
    @Column(name = "porcentaje_adicional_aporte_patronal")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAdicionalAportePatronal;

    /**
     * PORCENTAJE APORTE INDIVIDUAL de cotizacion IESS.
     */
    @Column(name = "porcentaje_aporte_individual")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAporteIndividual;

    /**
     * PORCENTAJE APORTE PATRONAL de cotizacion IESS.
     */
    @Column(name = "porcentaje_aporte_patronal")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAportePatronal;

    /**
     * PORCENTAJE IECE de cotizacion IESS.
     */
    @Column(name = "porcentaje_iece")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeIece;

    /**
     * PORCENTAJE SECAP de cotizacion IESS.
     */
    @Column(name = "porcentaje_secap")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeSecap;

    @JoinColumn(name = "nivel_ocupacional_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NivelOcupacional nivelOcupacional;

    @Column(name = "nivel_ocupacional_id")
    private Long nivelOcupacionalId;

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
     * @return the porcentajeAdicionalAporteIndividual
     */
    public BigDecimal getPorcentajeAdicionalAporteIndividual() {
        return porcentajeAdicionalAporteIndividual;
    }

    /**
     * @param porcentajeAdicionalAporteIndividual the
     * porcentajeAdicionalAporteIndividual to set
     */
    public void setPorcentajeAdicionalAporteIndividual(final BigDecimal porcentajeAdicionalAporteIndividual) {
        this.porcentajeAdicionalAporteIndividual = porcentajeAdicionalAporteIndividual;
    }

    /**
     * @return the porcentajeAdicionalAportePatronal
     */
    public BigDecimal getPorcentajeAdicionalAportePatronal() {
        return porcentajeAdicionalAportePatronal;
    }

    /**
     * @param porcentajeAdicionalAportePatronal the
     * porcentajeAdicionalAportePatronal to set
     */
    public void setPorcentajeAdicionalAportePatronal(final BigDecimal porcentajeAdicionalAportePatronal) {
        this.porcentajeAdicionalAportePatronal = porcentajeAdicionalAportePatronal;
    }

    /**
     * @return the porcentajeAporteIndividual
     */
    public BigDecimal getPorcentajeAporteIndividual() {
        return porcentajeAporteIndividual;
    }

    /**
     * @param porcentajeAporteIndividual the porcentajeAporteIndividual to set
     */
    public void setPorcentajeAporteIndividual(final BigDecimal porcentajeAporteIndividual) {
        this.porcentajeAporteIndividual = porcentajeAporteIndividual;
    }

    /**
     * @return the porcentajeAportePatronal
     */
    public BigDecimal getPorcentajeAportePatronal() {
        return porcentajeAportePatronal;
    }

    /**
     * @param porcentajeAportePatronal the porcentajeAportePatronal to set
     */
    public void setPorcentajeAportePatronal(final BigDecimal porcentajeAportePatronal) {
        this.porcentajeAportePatronal = porcentajeAportePatronal;
    }

    /**
     * @return the porcentajeIece
     */
    public BigDecimal getPorcentajeIece() {
        return porcentajeIece;
    }

    /**
     * @param porcentajeIece the porcentajeIece to set
     */
    public void setPorcentajeIece(final BigDecimal porcentajeIece) {
        this.porcentajeIece = porcentajeIece;
    }

    /**
     * @return the porcentajeSecap
     */
    public BigDecimal getPorcentajeSecap() {
        return porcentajeSecap;
    }

    /**
     * @param porcentajeSecap the porcentajeSecap to set
     */
    public void setPorcentajeSecap(final BigDecimal porcentajeSecap) {
        this.porcentajeSecap = porcentajeSecap;
    }

    /**
     * @return the nivelOcupacional
     */
    public NivelOcupacional getNivelOcupacional() {
        return nivelOcupacional;
    }

    /**
     * @param nivelOcupacional the nivelOcupacional to set
     */
    public void setNivelOcupacional(final NivelOcupacional nivelOcupacional) {
        this.nivelOcupacional = nivelOcupacional;
    }

    /**
     * @return the nivelOcupacionalId
     */
    public Long getNivelOcupacionalId() {
        return nivelOcupacionalId;
    }

    /**
     * @param nivelOcupacionalId the nivelOcupacionalId to set
     */
    public void setNivelOcupacionalId(final Long nivelOcupacionalId) {
        this.nivelOcupacionalId = nivelOcupacionalId;
    }
}
