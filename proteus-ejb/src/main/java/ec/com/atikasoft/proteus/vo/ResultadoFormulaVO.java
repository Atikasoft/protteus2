/*
 *  ResultadoFormulaVO.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  18/05/2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author Victor Quimbiamba  <victor.quimbiamba@atikasoft.com.ec>
 */
public class ResultadoFormulaVO {

    /**
     * Variable.
     */
    private BigDecimal valor;

    /**
     * Variable.
     */
    private Map<String, Object> valores;

    /**
     * Variable.
     */
    private String formula;

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the valores
     */
    public Map<String, Object> getValores() {
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(final Map<String, Object> valores) {
        this.valores = valores;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(final String formula) {
        this.formula = formula;
    }
}
