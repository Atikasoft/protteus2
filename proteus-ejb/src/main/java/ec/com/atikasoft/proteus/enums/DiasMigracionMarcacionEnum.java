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
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
public enum DiasMigracionMarcacionEnum {

    /**
     * Domingo.
     */
    DOMINGO("DOM", 1),
    /**
     * Lunes.
     */
    LUNES("LUN", 2),
    /**
     * Martes.
     */
    MARTES("MAR", 3),
    /**
     * Miercoles.
     */
    MIERCOLES("MIE", 4),
    /**
     * Jueves.
     */
    JUEVES("JUE", 5),
    /**
     * Viernes.
     */
    VIERNES("VIE", 6),
    /**
     * Sabado.
     */
    SABADO("SAB", 7), /**
     *
     */
    ;

    /**
     * Nombre en formato LUN.
     */
    private final String nombre;

    /**
     * Ordinal de Calendar DOM = 1.
     */
    private final Integer numero;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param numero Integer
     */
    private DiasMigracionMarcacionEnum(final String nombre, final Integer numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    /**
     * Este metodo retorna un dia enum segun el nombre del dia
     *
     * @param nombre
     * @return
     */
    public static DiasMigracionMarcacionEnum obtenerDiaPorNombre(final String nombre) {
        DiasMigracionMarcacionEnum diaMigracion = null;
        for (DiasMigracionMarcacionEnum d : values()) {
            if (d.getNombre().equals(nombre)) {
                diaMigracion = d;
                break;
            }
        }
        return diaMigracion;
    }

    /**
     * Este metodo retorna un dia enum segun el numero del dia
     *
     * @param numero
     * @return
     */
    public static DiasMigracionMarcacionEnum obtenerDiaPorNumero(final Integer numero) {
        DiasMigracionMarcacionEnum diaMigracion = null;
        for (DiasMigracionMarcacionEnum d : values()) {
            if (d.getNumero().equals(numero)) {
                diaMigracion = d;
                break;
            }
        }
        return diaMigracion;
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
