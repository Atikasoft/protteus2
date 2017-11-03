/*
 *  VariableCondicion.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "variables_condiciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VariableCondicion.BUSCAR_POR_NOMBRE,
    query = "SELECT a FROM VariableCondicion a where a.ordinal like ?1 and a.vigente=true order by a.literal"),
    @NamedQuery(name = VariableCondicion.BUSCAR_VIGENTES,
    query = "SELECT a FROM VariableCondicion a where a.vigente=true order by a.literal "),
    @NamedQuery(name = VariableCondicion.BUSCAR_POR_CODIGO,
    query = "SELECT a FROM VariableCondicion a where a.literal=?1 and a.vigente=true order by a.literal")
})
public class VariableCondicion extends EntidadBasica implements Comparable {

    /**
     * Variable para busqueda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "VariableCondicion.buscarporNombre ";

    /**
     * Variable parabusqeda por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "VariableCondicion.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VariableCondicion.buscarVigente";

    /**
     * Cuenta el numero de variables que usan el campo de acceso.
     */
    public static final String CONTAR_POR_CAMPO_ACCESO = "Variable.contarPorCampoAcceso";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Secuencia de la condicion.
     */
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "ordinal")
    private Integer ordinal;

    /**
     * Indicador si existe o no parentesis.
     */
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "cantidad_parentesis_izq")
    private Integer cantidadParentesisIzq;

    /**
     * Indicador si existe o no parentesis.
     */
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "cantidad_parentesis_der")
    private Integer cantidadParentesisDer;

    /**
     * Valor fijo.
     */
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "literal")
    private String literal;

    /**
     * Operador logico AND / OR.
     */
    // @Size(min = 2, max = 3)
    @Column(name = "operador_logico")
    private String operadorLogico;

    /**
     * Campo de acceso.
     */
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "campos_accesos_id", insertable = false, updatable = false)
    private CampoAcceso campoAcceso;

    /**
     * Campo de acceso.
     */
    @Column(name = "campos_accesos_id")
    private Long campoAccesoId;

    /**
     * Tipo de operacion comparación.
     */
    @Column(name = "tipo_operacion_comparacion")
    private String tipoOperacionComparacion;

    /**
     * Campo de acceso.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "variables_id", insertable = false, updatable = false)
    private Variable variableP;

    /**
     * Campo de acceso.
     */
    @Column(name = "variables_id")
    private Long variablePId;

    @Transient
    private String parentesisIzq;

    @Transient
    private String parentesisDer;

    @Transient
    private String cadenaCondicion;

    /**
     * Constructor.
     */
    public VariableCondicion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VariableCondicion(Long id) {
        super();
        this.id = id;
    }

    /**
     * Indica el campo por el cual se ordena una lista
     *
     * @param o objeto a comparar
     * @return >1 si el objeto a comparar es mayor al objeto actual, 0 si son iguales y <1 si es menor
     */
    public int compareTo(Object o) {
        VariableCondicion vc = (VariableCondicion) o;

        return this.ordinal - vc.getOrdinal();

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
     * @return the cantidadParentesisIzq
     */
    public Integer getCantidadParentesisIzq() {
        return cantidadParentesisIzq;
    }

    /**
     * @param cantidadParentesisIzq the cantidadParentesisIzq to set
     */
    public void setCantidadParentesisIzq(final Integer cantidadParentesisIzq) {
        this.cantidadParentesisIzq = cantidadParentesisIzq;
    }

    /**
     * @return the cantidadParentesisDer
     */
    public Integer getCantidadParentesisDer() {
        return cantidadParentesisDer;
    }

    /**
     * @param cantidadParentesisDer the cantidadParentesisDer to set
     */
    public void setCantidadParentesisDer(final Integer cantidadParentesisDer) {
        this.cantidadParentesisDer = cantidadParentesisDer;
    }

    /**
     * @return the literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * @param literal the literal to set
     */
    public void setLiteral(final String literal) {
        this.literal = literal;
    }

    /**
     * @return the operadorLogico
     */
    public String getOperadorLogico() {
        return operadorLogico;
    }

    /**
     * @param operadorLogico the operadorLogico to set
     */
    public void setOperadorLogico(final String operadorLogico) {
        this.operadorLogico = operadorLogico;
    }

    /**
     * @return the campoAcceso
     */
    public CampoAcceso getCampoAcceso() {
        return campoAcceso;
    }

    /**
     * @param campoAcceso the campoAcceso to set
     */
    public void setCampoAcceso(final CampoAcceso campoAcceso) {
        this.campoAcceso = campoAcceso;
    }

    /**
     * @return the campoAccesoId
     */
    public Long getCampoAccesoId() {
        return campoAccesoId;
    }

    /**
     * @param campoAccesoId the campoAccesoId to set
     */
    public void setCampoAccesoId(final Long campoAccesoId) {
        this.campoAccesoId = campoAccesoId;
    }

    /**
     * @return the tipoOperacionComparacion
     */
    public String getTipoOperacionComparacion() {
        return tipoOperacionComparacion;
    }

    /**
     * @param tipoOperacionComparacion the tipoOperacionComparacion to set
     */
    public void setTipoOperacionComparacion(final String tipoOperacionComparacion) {
        this.tipoOperacionComparacion = tipoOperacionComparacion;
    }

    /**
     * @return the variable
     */
    public Variable getVariableP() {
        return variableP;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariableP(Variable variable) {
        this.variableP = variableP;
    }

    /**
     * @return the variableId
     */
    public Long getVariablePId() {
        return variablePId;
    }

    /**
     * @param variableId the variableId to set
     */
    public void setVariablePId(Long variablePId) {
        this.variablePId = variablePId;
    }

    /**
     * @return the parentesisIzq
     */
    public String getParentesisIzq() {
        return parentesisIzq;
    }

    /**
     * @param parentesisIzq the parentesisIzq to set
     */
    public void setParentesisIzq(String parentesisIzq) {
        this.parentesisIzq = parentesisIzq;
    }

    /**
     * @return the parentesisDer
     */
    public String getParentesisDer() {
        return parentesisDer;
    }

    /**
     * @param parentesisDer the parentesisDer to set
     */
    public void setParentesisDer(String parentesisDer) {
        this.parentesisDer = parentesisDer;
    }

    /**
     * @return the cadenaCondicion
     */
    public String getCadenaCondicion() {
        return cadenaCondicion;
    }

    /**
     * @param cadenaCondicion the cadenaCondicion to set
     */
    public void setCadenaCondicion(String cadenaCondicion) {
        this.cadenaCondicion = cadenaCondicion;
    }
}
