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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Victor Quimbiamba  <victor.quimbiamba@atikasoft.com.ec>
 */
public class ResultadoRecuperacionAnticipoVO {

    /**
     * Valor descontar por anticipo
     */
    private BigDecimal valor;

    /**
     * Valores de cuotas.
     */
    private Map<String, Object> valores;

    /**
     * Numero del anticipo.
     */
    private String numeroAnticipo;

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
        if (valores == null) {
            valores = new HashMap<String, Object>();
        }
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(final Map<String, Object> valores) {
        this.valores = valores;
    }

    /**
     * @return the numeroAnticipo
     */
    public String getNumeroAnticipo() {
        return numeroAnticipo;
    }

    /**
     * @param numeroAnticipo the numeroAnticipo to set
     */
    public void setNumeroAnticipo(String numeroAnticipo) {
        this.numeroAnticipo = numeroAnticipo;
    }
}
