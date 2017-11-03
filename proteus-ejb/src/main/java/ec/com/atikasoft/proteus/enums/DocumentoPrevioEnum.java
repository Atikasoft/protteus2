/*
 *  DocumentoPrevioEnum.java
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
 *  18/02/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeracion de Documento Previo.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum DocumentoPrevioEnum {

    /**
     * Tipo.
     */
    DECRETO("DCR", "Decreto"),
    /**
     * Tipo.
     */
    ACUERDO("ACR", "Acuerdo"),
    /**
     * Tipo.
     */
    RESOLUCION("RSL", "Resolución"),
    /**
     * Tipo.
     */
    OFICIO("OFC", "Oficio"),
    /**
     * Tipo.
     */
    SOLICITUD("SLC", "Solicitud"),
    /**
     * Tipo.
     */
    CIRCULAR("CRC", "Circular"),
      /**
     * Tipo.
     */
    COOTAD("COT", "COOTAD");

    /**
     * Codigo del tipo.
     */
    private final String codigo;

    /**
     * Descrpcion del tipo.
     */
    private final String descripcion;

    /**
     * Constructor de enumeracion.
     *
     * @param codigo
     * @param descripcion
     */
    private DocumentoPrevioEnum(final String codigo, final String descripcion) {
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
