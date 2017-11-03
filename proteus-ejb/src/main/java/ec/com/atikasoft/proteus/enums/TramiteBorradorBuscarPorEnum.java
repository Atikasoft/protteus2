/*
 *  TramiteBorradorBuscarPorEnum.java
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
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum TramiteBorradorBuscarPorEnum {

    /**
     * Grupo.
     */
    GRUPO("grupo", "Grupo"),
    /**
     * Clase.
     */
    CLASE("clase", "Clase"),
    /**
     * Movimiento.
     */
    MOVIMIENTO("movimiento", "Movimiento"),
    /**
     * Numero de tramite.
     */
    NUMERO_TRAMITE("numeroT", "");

    // TAREA("Tarea","");
    /**
     * Columna del campo ordenamiento.
     */
    private String columna;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param columna String
     * @param descripcion String
     */
    private TramiteBorradorBuscarPorEnum(final String columna, final String descripcion) {
        this.columna = columna;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the columna
     */
    public String getColumna() {
        return columna;
    }
}
