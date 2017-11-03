/*
 *  FaltasRegimenDisciplinarioEnum.java
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
 *  02/01/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * FaltasRegimenDisciplinarioEnum.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum FaltasRegimenDisciplinarioEnum {

    LEVE("LVL", "Leve"),
    GRAVE("GRV", "Grave");

    /**
     * Codigo de la enumeracion.
     */
    private String codigo;

    /**
     * Descripcion de la enumeracion.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private FaltasRegimenDisciplinarioEnum(final String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
