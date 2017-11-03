/*
 *  BusquedaTareaVO.java
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
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;

/**
 * VO para la busqueda en bandeja de entrada.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class BusquedaTareaVO implements Serializable {

    /**
     * Campo tomado para ordenar.
     */
    private String ordenarPor;

    /**
     * Tipo de ordenamiento.
     */
    private String tipoOrdenamiento;

    /**
     * Actividad de la busqueda.
     */
    private Boolean actividad;

    /**
     * Valor por el cual se va ha buscar.
     */
    private String valor;

    /**
     * @return the ordenarPor
     */
    public String getOrdenarPor() {
        return ordenarPor;
    }

    /**
     * @param ordenarPor the ordenarPor to set
     */
    public void setOrdenarPor(final String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    /**
     * @return the tipoOrdenamiento
     */
    public String getTipoOrdenamiento() {
        return tipoOrdenamiento;
    }

    /**
     * @param tipoOrdenamiento the tipoOrdenamiento to set
     */
    public void setTipoOrdenamiento(final String tipoOrdenamiento) {
        this.tipoOrdenamiento = tipoOrdenamiento;
    }

    /**
     * @return the actividad
     */
    public Boolean getActividad() {
        return actividad;
    }

    /**
     * @param actividad the actividad to set
     */
    public void setActividad(final Boolean actividad) {
        this.actividad = actividad;
    }

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final String valor) {
        this.valor = valor;
    }
}
