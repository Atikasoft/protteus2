/*
 *  ImpuestoRenta.java
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
 *  18/02/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
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
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Permite la iteracción con Tabla de Impuesto a la Renta.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "impuestos_renta", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ImpuestoRenta.BUSCAR_VIGENTES, query = "SELECT a FROM ImpuestoRenta a where a.vigente=true order by a.ejercicioFiscal.nombre"),
    @NamedQuery(name = ImpuestoRenta.BUSCAR_ESCALA, query = "SELECT a FROM ImpuestoRenta a WHERE a.vigente=true AND a.ejercicioFiscal.id=?1 AND "
            + " ?2 BETWEEN a.fraccionBasica/12 AND (a.excesoHasta)/12 ORDER BY a.fraccionBasica "),
    @NamedQuery(name = ImpuestoRenta.BUSCAR_EJERCICIO_FISCAL, query = "SELECT a FROM ImpuestoRenta a where a.vigente=true and a.ejercicioFiscal.id=?1 order by a.fraccionBasica asc"),
    @NamedQuery(name = ImpuestoRenta.BUSCAR_FRACCION_BASICA_EXONERACIONES, query = "SELECT a FROM ImpuestoRenta a where a.vigente=true and a.ejercicioFiscal.id=?1 and a.fraccionBasicaExoneracion=true")
})
public class ImpuestoRenta extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "ImpuestoRenta.buscarVigente";

    /**
     *
     */
    public static final String BUSCAR_ESCALA = "ImpuestoRenta.buscarEscala";

    /**
     *
     */
    public static final String BUSCAR_EJERCICIO_FISCAL = "ImpuestoRenta.buscarEjercicioFiscal";

    /**
     *
     */
    public static final String BUSCAR_RANGO_FRACCION_EXCESO_REPETIDO = "ImpuestoRenta.rangoFraccionExcesoRepetido";

    /**
     *
     */
    public static final String BUSCAR_FRACCION_BASICA_EXONERACIONES = "ImpuestoRenta.buscarFraccionBasicaExoneraciones";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Indica el ejercicio fiscal del Impuesto a la renta.
     */
    @JoinColumn(name = "ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EjercicioFiscal ejercicioFiscal;

    /**
     * Fracción Básica para el ejercicio fiscal.
     */
    @Column(name = "fraccion_basica")
    @NotNull
    private BigDecimal fraccionBasica;

    /**
     * Límite de Exceso de Ingresos.
     */
    @Column(name = "exceso_hasta")
    private BigDecimal excesoHasta;

    /**
     * Porcentaje de impuesto sobre la Fracción Básica.
     */
    @Column(name = "porcentaje_impuesto_fraccion_basica")
    @NotNull
    private BigDecimal porcentajeImpuestoSobreFraccionBasica;

    /**
     * Límite de Exceso de Ingresos.
     */
    @Column(name = "porcentaje_impuesto_fraccion_excedente")
    @NotNull
    private BigDecimal porcentajeImpuestoFraccionExcedente;

    /**
     * Permite indicar cual es el rango de la tabla de ir que le corresponde
     * para las exoneraciones de discapacidad y tercera edad , con el fin de
     * recuperar la fraccion basica.
     */
    @Column(name = "fraccion_basica_exoneracion")
    private Boolean fraccionBasicaExoneracion;

    /**
     * Valor que se usa para multiplicar por la fraccion basica y con esto
     * calcular la valor de descontar en la exoneraciones.
     */
    @Column(name = "indice_exoneracion")
    private BigDecimal indiceExoneracion;

    /**
     * Constructor.
     */
    public ImpuestoRenta() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ImpuestoRenta(final Long id) {
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
     * @return the excesoHasta
     */
    public BigDecimal getExcesoHasta() {
        return excesoHasta;
    }

    /**
     * @param excesoHasta the excesoHasta to set
     */
    public void setExcesoHasta(BigDecimal excesoHasta) {
        this.excesoHasta = excesoHasta;
    }

    /**
     * @return the porcentajeImpuestoSobreFraccionBasica
     */
    public BigDecimal getPorcentajeImpuestoSobreFraccionBasica() {
        return porcentajeImpuestoSobreFraccionBasica;
    }

    /**
     * @param porcentajeImpuestoSobreFraccionBasica the
     * porcentajeImpuestoSobreFraccionBasica to set
     */
    public void setPorcentajeImpuestoSobreFraccionBasica(BigDecimal porcentajeImpuestoSobreFraccionBasica) {
        this.porcentajeImpuestoSobreFraccionBasica = porcentajeImpuestoSobreFraccionBasica;
    }

    /**
     * @return the porcentajeImpuestoFraccionExcedente
     */
    public BigDecimal getPorcentajeImpuestoFraccionExcedente() {
        return porcentajeImpuestoFraccionExcedente;
    }

    /**
     * @param porcentajeImpuestoFraccionExcedente the
     * porcentajeImpuestoFraccionExcedente to set
     */
    public void setPorcentajeImpuestoFraccionExcedente(BigDecimal porcentajeImpuestoFraccionExcedente) {
        this.porcentajeImpuestoFraccionExcedente = porcentajeImpuestoFraccionExcedente;
    }

    /**
     * @return the fraccionBasicaExoneracion
     */
    public Boolean getFraccionBasicaExoneracion() {
        return fraccionBasicaExoneracion;
    }

    /**
     * @param fraccionBasicaExoneracion the fraccionBasicaExoneracion to set
     */
    public void setFraccionBasicaExoneracion(Boolean fraccionBasicaExoneracion) {
        this.fraccionBasicaExoneracion = fraccionBasicaExoneracion;
    }

    /**
     * @return the indiceExoneracion
     */
    public BigDecimal getIndiceExoneracion() {
        return indiceExoneracion;
    }

    /**
     * @param indiceExoneracion the indiceExoneracion to set
     */
    public void setIndiceExoneracion(BigDecimal indiceExoneracion) {
        this.indiceExoneracion = indiceExoneracion;
    }
}
