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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos de tipos de gastos personales.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "tipos_gastos_personales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoGastoPersonal.BUSCAR_POR_EJERCICIO_FISCAL, query = "SELECT o FROM TipoGastoPersonal o WHERE o.vigente=true AND o.ejercicioFiscal.id=?1")
})
public class TipoGastoPersonal extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_POR_EJERCICIO_FISCAL = "TipoGastoPersonal.buscarPorEjercicioFiscal";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     *
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Porcentaje para el ejercicio fiscal.
     */
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;

    /**
     * Indica el ejercicio fiscal del Impuesto a la renta.
     */
    @JoinColumn(name = "ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private EjercicioFiscal ejercicioFiscal;

    /**
     * Constructor.
     */
    public TipoGastoPersonal() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public TipoGastoPersonal(final Long id) {
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
     * @return the porcentaje
     */
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
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

}
