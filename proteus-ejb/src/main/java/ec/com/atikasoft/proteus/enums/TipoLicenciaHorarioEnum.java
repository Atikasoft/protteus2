/*
 *  TipoLicenciaHorarioEnum.java
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
 * Enum de Tipo Licencia Horario.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoLicenciaHorarioEnum {

    /**
     * Tipo.
     */
    LICENCIA_PERMISO("Licencia / Permisos", "L"),
    /**
     * Tipo.
     */
    RECUPERACION("Recuperación", "R");

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Codigo.
     */
    private String codigo;

    /**
     * Constructor.
     *
     * @param nombre String
     * @param codigo String
     */
    private TipoLicenciaHorarioEnum(final String nombre, final String codigo) {
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
