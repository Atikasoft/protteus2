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
 * Enumeración para Corresponde Discapacidad.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum CorrespondeDiscapacidadEnum {

    /**
     * Tipo.
     */
    SERVIDOR_CON_DISCAPACIDAD_ENFERMEDAD_CATASTROFICA("Servidor con Discapacidad o Enfermedad Catastrófica", "S"),
    /**
     * Tipo.
     */
    FAMILIAR_CON_DISCAPACIDAD_ENFERMEDAD_CATASTROFICA("Familiar con Discapacidad o Enfermedad Catastrófica", "F");

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
    private CorrespondeDiscapacidadEnum(final String nombre, final String codigo) {
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
