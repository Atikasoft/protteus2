/*
 *  DatosVariablesVO.java
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
 *  24/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;


/**
 * VO para gestión datos variables
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class DatosVariablesVO implements Serializable {
  /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con parametro para inicializacion.
     * @param codigo String
     */
    public DatosVariablesVO(final String codigo) {
        super();
        this.codigo = codigo;
    }

    public DatosVariablesVO(final String codigo, final Object valor) {
        super();
        this.codigo = codigo;
        this.valor = valor;
    }
    /**
     * Codigo de la variable.
     */
    private String codigo;

    /**
     * Valor asignado a la formula.
     */
    private Object valor;

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final Object valor) {
        this.valor = valor;
    }
}
