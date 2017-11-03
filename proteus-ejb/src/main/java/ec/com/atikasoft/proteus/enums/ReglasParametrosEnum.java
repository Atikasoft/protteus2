/*
 *  EstadoPersonalEnum.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Contiene la definicion de los objetos que se usaran en la validacion de reglas.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum ReglasParametrosEnum {

    /**
     * Activo.
     */
    MOVIMIENTO("MOV", "Movimientos"),
    /**
     * Pasivo.
     */
    PERSONA("REGCIV", "Registro Civil"),
    /**
     * Puesto.
     */
    PUESTO("PU", "Puesto"),
    /**
     * Parametro globales.
     */
    PARAMETROS_GLOBALES("PARGLO", "Parametro Globales");

    /**
     * Código .
     */
    private final String codigo;

    /**
     * Descripcion .
     */
    private final String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private ReglasParametrosEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        String des = null;
        for (ReglasParametrosEnum j : values()) {
            if (j.getCodigo().equals(codigo)) {
                des = j.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
