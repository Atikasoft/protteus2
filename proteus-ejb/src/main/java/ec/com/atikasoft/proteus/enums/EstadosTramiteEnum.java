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
 * Definicion de estados de tramites.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum EstadosTramiteEnum {

    /**
     * ELABORACIÓN.
     */
    ELABORACION("ELA", "ELABORACIÓN"),
    /**
     * REVISIÓN.
     */
    REVISION("REV", "REVISIÓN"),
    /**
     * VALIDACIÓN.
     */
    VALIDACION("VAL", "VALIDACIÓN"),
    /**
     * APROBACIÓN.
     */
    APROBACION("APR", "APROBACIÓN"),
    /**
     * LEGALIZACIÓN.
     */
    LEGALIZACION("LEG", "LEGALIZACIÓN"),
    /**
     * LEGALIZACIÓN NOMBRE.
     */
    LEGALIZACION_NOMINA("LEN", "LEGALIZACIÓN NOMINA"),
    /**
     * DEVOLUCIÓN.
     */
    DEVOLUCION("DEV", "DEVOLUCIÓN"),
    /**
     * ANULADO.
     */
    ANULADO("ANU", "ANULADO"),
    /**
     * RECHAZADO.
     */
    RECHAZADO("REC", "RECHAZADO"),
    /**
     * ELIMINADO.
     */
    ELIMINADO("ELI", "ELIMINADO"),
    /**
     * regiSTRADO.
     */
    REGISTRADO("REG", "REGISTRADO");

    // TAREA("Tarea","");
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
    private EstadosTramiteEnum(final String codigo, final String descripcion) {
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
