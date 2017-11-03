/*
 *  TipoDesignacionEnum.java
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
 * Enumeración de Tipo Designacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoDesignacionEnum {

    /**
     * Tipo.
     */
    POPULAR("Elección Popular", "EP"),
    /**
     * Tipo.
     */
    CONCURSO("Concurso", "CC");

    /**
     * Nombre del tipo.
     */
    private String nombre;

    /**
     * Código del tipo.
     */
    private String codigo;

    /**
     * Constructor de enumeración.
     *
     * @param nombre String
     * @param codigo String
     */
    private TipoDesignacionEnum(final String nombre, final String codigo) {
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
