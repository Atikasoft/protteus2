/*
 *  TipoDocumentoEnum.java
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
 *  30/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeración de Tipo Documento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoDocumentoEnum {

    /**
     * Tipo cedula.
     */
    CEDULA("CÉDULA", "C", 10),
    /**
     * Tipo pasaporte.
     */
    PASAPORTE("PASAPORTE", "P", 30),
    /**
     * Tipo ruc.
     */
    RUC("RUC","R",13);

    /**
     * Nombre del tipo documento.
     */
    private final String nombre;

    /**
     * Nemonico del tipo documento.
     */
    private final String nemonico;

    /**
     * Variable a amacena la longitud del documento.
     */
    private final Integer longuitud;

    /**
     * Constructor para enumeración.
     *
     * @param nombre String
     * @param nemonico String
     * @param id Long
     */
    private TipoDocumentoEnum(final String nombre, final String nemonico, final Integer longuitud) {
        this.nombre = nombre;
        this.nemonico = nemonico;
        this.longuitud = longuitud;
    }

     /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerNombre(final String codigo) {
        String des = null;
        for (TipoDocumentoEnum cc : values()) {
            if (cc.getNemonico().equals(codigo)) {
                des = cc.getNombre();
                break;
            }
        }
        return des;
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * @return the longuitud
     */
    public Integer getLonguitud() {
        return longuitud;
    }
}
