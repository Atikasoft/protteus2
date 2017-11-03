/*
 *  TipoPeriodoDevengarEnum.java
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
 *  21/01/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
public enum TipoPeriodoDevengarEnum {

    /**
     * Años.
     */
    ANIO("A", "Años"),
    /**
     * Mes.
     */
    MES("M", "Meses"),
    /**
     * Dias.
     */
    DIA("D", "Días");

    /**
     * Constructor de la clase.
     *
     * @param codigo
     * @param descripcion
     */
    private TipoPeriodoDevengarEnum(String codigo, String descripcion) {
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
        for (TipoPeriodoDevengarEnum tp : values()) {
            if (tp.getCodigo().equals(codigo)) {
                des = tp.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * Código del tipo de periodo.
     */
    private String codigo;

    /**
     * Descripcion del tipo de periodo.
     */
    private String descripcion;

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
