/*
 *  MessEnum.java
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

import java.util.Objects;

/**
 * Enum de Mess.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum MesesEnum {

    /**
     * Mes.
     */
    ENERO("ENERO", 1),
    /**
     * Mes.
     */
    FEBRERO("FEBRERO", 2),
    /**
     * Mes.
     */
    MARZO("MARZO", 3),
    /**
     * Mes.
     */
    ABRIL("ABRIL", 4),
    /**
     * Mes.
     */
    MAYO("MAYO", 5),
    /**
     * Mes.
     */
    JUNIO("JUNIO", 6),
    /**
     * Mes.
     */
    JULIO("JULIO", 7),
    /**
     * Mes.
     */
    AGOSTO("AGOSTO", 8),
    /**
     * Mes.
     */
    SEPTIEMBRE("SEPTIEMBRE", 9),
    /**
     * Mes.
     */
    OCTUBRE("OCTUBRE", 10),
    /**
     * Mes.
     */
    NOVIEMBRE("NOVIEMBRE", 11),
    /**
     * Mes.
     */
    DICIEMBRE("DICIEMBRE", 12);

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Numero.
     */
    private Integer numero;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param numero Integer
     */
    private MesesEnum(final String nombre, final Integer numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    /**
     * Este metodo retorna un mes enum segun el mes.
     *
     * @param mes Integer
     * @return String
     */
    public static String obtenerNombre(final Integer mes) {
        String n = "";
        for (MesesEnum d : values()) {
            if (Objects.equals(d.getNumero(), mes)) {
                n = d.getNombre();
                break;
            }
        }
        return n;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }
}
