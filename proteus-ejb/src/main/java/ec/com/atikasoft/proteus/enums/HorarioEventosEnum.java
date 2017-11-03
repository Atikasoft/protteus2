/*
 *  DiasEnum.java
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
 *  29/01/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public enum HorarioEventosEnum {

    /**
     * Evento
     */
    HORA_ENTRADA("Entrada"),
    /**
     * Evento
     */
    HORA_INICIO_ALMUERZO("Inicio Almuerzo"),
    /**
     * Evento
     */
    HORA_FIN_ALMUERZO("Fín Almuerzo"),
    /**
     * Evento
     */
    HORA_SALIDA("Salida"),
    /**
     * 
     */
    TOTAL_HORAS_DIARIAS("Total Horas");

    /**
     * Nombre.
     */
    private final String nombre;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param numero Integer
     */
    private HorarioEventosEnum(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
}
