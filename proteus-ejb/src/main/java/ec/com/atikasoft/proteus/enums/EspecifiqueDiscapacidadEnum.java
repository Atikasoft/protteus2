/*
 *  EspecifiqueDiscapacidadEnum.java
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
 *  29/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeración para Especifique Discapacidad.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum EspecifiqueDiscapacidadEnum {

    /**
     * Tipo.
     */
    DISCAPACIDAD("Discapacidad", "D"),
    /**
     * Tipo.
     */
    ENFERMEDAD_CATASTROFICA("Enfermedad Catastrófica", "E");

    /**
     * Nombre de la case.
     */
    private String nombre;

    /**
     * Codigo de la case.
     */
    private String codigo;

    /**
     * Constructor por defecto para enumeración.
     *
     * @param nombre String
     * @param codigo String
     */
    private EspecifiqueDiscapacidadEnum(final String nombre, final String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
