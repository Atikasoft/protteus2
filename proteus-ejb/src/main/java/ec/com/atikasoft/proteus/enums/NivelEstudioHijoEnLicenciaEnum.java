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
 * Define los nivel de estudio de hijos usado en licencia.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum NivelEstudioHijoEnLicenciaEnum {

    /**
     * Nivel basico.
     */
    NIVEL_BASICO("A", "NIVEL BÁSICO"),
    /**
     * Bachillerato.
     */
    BACHILLERATO("B", "BACHILLERATO");

    /**
     * Codigo.
     */
    private String codigo;

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param numero Integer
     */
    private NivelEstudioHijoEnLicenciaEnum(final String codigo, final String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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
