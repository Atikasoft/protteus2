/*
 *  TipoParametroInstitucionCatalogoEnum.java
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
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
public enum TipoParametroInstitucionCatalogoEnum {

    /**
     * NUMERO.
     */
    NUMERICO("N", "Número"),
    /**
     * Texto.
     */
    TEXTO("T", "Texto"),
    /**
     * Enumeracion.
     */
    ENUMERACION("L", "Enumeración"),
    /**
     * Archivo.
     */
    ARCHIVO("A", "Archivo"),
    /**
     * Fecha.
     */
    FECHA("F", "Fecha");

    /**
     * Código del tipo parametro.
     */
    private String codigo;

    /**
     * Descripcion del tipo parametro.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo
     * @param descripcion
     */
    private TipoParametroInstitucionCatalogoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        String des = null;
        for (TipoParametroInstitucionCatalogoEnum tpg : values()) {
            if (tpg.getCodigo().equals(codigo)) {
                des = tpg.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
