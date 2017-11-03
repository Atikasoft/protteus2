/*
 *  TipoTemporadaEnum.java
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
 * Enumeración de TipoTemporada.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoTemporadaEnum {

    /**
     * Tipo.
     */
    ESCOLAR("Escolar", "ESC"),
    /**
     * Tipo.
     */
    NAVIDENIA("Navideña", "NAV"),
    /**
     * Tipo.
     */
    VACACIONES("Vacaciones", "VAC"),
    /**
     * Tipo.
     */
    OTROS("Otros", "OTR");

    /**
     * Nombre del tipo.
     */
    private String nombre;

    /**
     * Codigo del case.
     */
    private String codigo;

    /**
     * Constructor para enumeración.
     *
     * @param nombre String
     * @param codigo String
     */
    private TipoTemporadaEnum(final String nombre, final String codigo) {
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
