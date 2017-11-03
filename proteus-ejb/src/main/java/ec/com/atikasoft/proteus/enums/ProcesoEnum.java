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

/**
 * Definicion de los procesos que usa el sistema.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum ProcesoEnum {

    /**
     * Movimientos de personal completo.
     */
    MOVIMIENTO_PERSONAL_REGULAR("MOVPERREG", "MOVIMIENTOS DE PERSONAL REGULAR"),
    /**
     * Movimientos de personal regular.
     */
    MOVIMIENTO_PERSONAL_CORTO("MOVPERCOR", "MOVIMIENTOS DE PERSONAL CORTO"),
    /**
     * Movimientos de personal corto.
     */
    MOVIMIENTO_PERSONAL_DIRECTO("MOVPERDIR", "MOVIMIENTOS DE PERSONAL DIRECTO ");

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
    private ProcesoEnum(final String codigo, final String descripcion) {
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
