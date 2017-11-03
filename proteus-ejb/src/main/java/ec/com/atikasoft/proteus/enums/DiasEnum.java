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
 * Enum de Dias.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum DiasEnum {

    /**
     * Dia.
     */
    LUNER("Lunes", 1),
    /**
     * Dia.
     */
    MARTES("Martes", 2),
    /**
     * Dia.
     */
    MIERCOLES("Miércoles", 3),
    /**
     * Dia.
     */
    JUEVES("Jueves", 4),
    /**
     * Dia.
     */
    VIERNES("Viernes", 5),
    /**
     * Dia.
     */
    SABADO("Sábado", 6),
    /**
     * Dia.
     */
    DOMINGO("Domingo", 7);

    /**
     * Nombre.
     */
    private final String nombre;

    /**
     * Numero.
     */
    private final Integer numero;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param numero Integer
     */
    private DiasEnum(final String nombre, final Integer numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    /**
     * Este metodo retorna un dia enum segun el dia.
     *
     * @param dia Integer
     * @return String
     */
    public static String obtenerNombre(final Integer dia) {
        String n = "";
        for (DiasEnum d : values()) {
            if (d.getNumero() == dia) {
                n = d.getNombre();
                break;
            }
        }
        return n;
    }
    
    /**
     * Este metodo retorna un dia enum segun el dia.
     *
     * @param nombreDia Integer
     * @return String
     */
    public static Integer obtenerNumeroDadoNombre(final String nombreDia) {
        for (DiasEnum d : values()) {
            if (d.getNombre().equals(nombreDia)) {
                return d.getNumero();
            }
        }
        return null;
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
