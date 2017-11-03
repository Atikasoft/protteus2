/*
 *  EstadosTramiteEnum.java
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
package ec.com.atikasoft.proteus.enums;

import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;

/**
 * Definicion los peridos para el tiempo maximo del tipo de movimiento.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum PeriodoTiempoMaximoEnum {

    /**
     * Año.
     */
    ANIO("A", "AÑO"),
    /**
     * Mes.
     */
    MES("M", "MES"),
    /**
     * Semana.
     */
    SEMANA("S", "SEMANA"),
    /**
     * Dia.
     */
    DIA("D", "DÍA"),
    /**
     * Hora
     */
    HORA("H","HORA");

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private PeriodoTiempoMaximoEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
