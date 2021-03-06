/*
 *  EstadosTramiteEnum.java
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
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Definicio de los tipo de nacimientos que se usa en licencia por maternidad / paternidad.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
public enum TipoNacimientoEnum {

    /**
     * Tipo de nacimiento normal.
     */
    NORMAL("N", "NORMAL"),
    /**
     * Tipo de nacimiento cesarea.
     */
    CESAREA("C", "CESÁREA"),
    /**
     * Tipo de nacimiento multiple.
     */
    MULTIPLE("M", "MULTIPLE");

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoNacimientoEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
