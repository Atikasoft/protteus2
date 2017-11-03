/*
 *  OperacionEnum.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  
 *  04/10/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
* @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
public enum OperacionEnum {

    /**
     * Sumatoria.
     */
    SUMATORIA("S", "SUMATORIA"),
    /**
     * Contar.
     */
    CONTAR("C", "CONTAR"),
    
    /**
     * Promedio.
     */
    PROMEDIO("P", "PROMEDIO"),
    
    /**
     * Valor.
     */
    VALOR("V", "VALOR"),
     /**
     * Mínimo.
     */
    MINIMO("I", "MÍNIMO"),
    /**
     * Máximo.
     */
    MAXIMO("M", "MÁXIMO");

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
    private OperacionEnum(String codigo, String descripcion) {
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
        for (OperacionEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getDescripcion();
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
