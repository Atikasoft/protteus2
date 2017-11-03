/*
 *  TareaOrdenarPorEnum.java
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
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TareaOrdenarPorEnum {

    /**
     * Fecha de Asigmación.
     */
    FECHA_ASIGNACION("Fecha de Asignación", "fechaAsignacion"),
    /**
     * Fecha de Atención.
     */
    FECHA_ATENCION("Fecha de Atención", "fechaAtencion"),
    /**
     * Número.
     */
    NUMERO_TRAMITE("No. Trámite", "numeroExterno");

    // TAREA("Tarea","");
    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Columna del campo ordenamiento.
     */
    private String columna;

    /**
     * Constructor requerido.
     *
     * @param descripcion String
     * @param columna String
     */
    private TareaOrdenarPorEnum(final String descripcion, final String columna) {
        this.descripcion = descripcion;
        this.columna = columna;
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
