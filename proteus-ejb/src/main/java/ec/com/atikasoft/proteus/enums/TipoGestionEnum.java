/*
 *  TipoGestionEnum.java
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
 *  12/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoGestionEnum {

    /**
     * Tipo Gestion.
     */
    CENTRALIZADA("Centralizada", "C"),
    /**
     * Tipo Gestion.
     */
    DESCONCETRADA("Desconcentrada", "D");

    /**
     * Descripcion del tipo gestion.
     */
    private String descripcion;

    /**
     * Codigo del tipo gestion.
     */
    private String codigo;

    /**
     * Constructor segun la enumeración.
     *
     * @param descripcion String
     * @param codigo String
     */
    private TipoGestionEnum(final String descripcion, final String codigo) {
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
