/*
 *  TipoConstanteEnum.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
* @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
public enum TipoConstanteEnum {

    /**
     * Numérico.
     */
    NUMERICO("N", "NUMÉRICO"),
    /**
     * Texto.
     */
    TEXTO("T", "TEXTO"),
    
    /**
     * Fecha.
     */
    FECHA("F", "FECHA");

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;
/**
 * Constructor de mi enum.
 * @param codigo
 * @param descripcion 
 */
    private TipoConstanteEnum(String codigo, String descripcion) {
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
