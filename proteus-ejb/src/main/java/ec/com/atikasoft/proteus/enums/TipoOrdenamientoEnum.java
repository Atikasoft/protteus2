/*
 *  TipoOrdenamientoEnum.java
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
 *  07/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeración para tipo de orden en consultas.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoOrdenamientoEnum {

    /**
     * Tipo Ascendente.
     */
    ASCENDENTE("Ascendente", "ASC"),
    /**
     * Tipo Descendente.
     */
    DESCENDENTE("Descendente", "DESC");

    /**
     * Descripcion del tipo ordenamiento.
     */
    private String descripcion;

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Constructor requerido.
     *
     * @param descripcion String
     * @param codigo String
     */
    private TipoOrdenamientoEnum(final String descripcion, final String codigo) {
        this.descripcion = descripcion;
        this.codigo = codigo;
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
