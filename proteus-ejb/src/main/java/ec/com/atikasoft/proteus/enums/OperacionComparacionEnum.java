/*
 *  OperacionComparacionEnum.java
 *  proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with proteus.
 *
 *  proteus
 *  Quito - Ecuador
 *
 *  08/10/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
public enum OperacionComparacionEnum {

    /**
     * MENOR QUE.
     */
    MENOR_QUE("<", "<"),
    /*
     * MENOR IGUAL QUE.
     */
    MENOR_IGUAL_QUE("<=", "<="),
    /**
     * DIFERENTE
     */
    DIFERENTE("<>", "<>"),
    /**
     * IGUAL .
     */
    IGUAL("=", "="),
    /**
     * MAYOR QUE
     */
    MAYOR_QUE(">", ">"),
    /**
     * MAYOR IGUAL QUE.
     */
    MAYOR_IGUAL_QUE(">=", ">=");
    /**
     *
     *
     * /**
     * Código del campo de configuracion.
     */
    private String codigo;
    /**
     * Descripcion del campo de configuracion.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private OperacionComparacionEnum(final String codigo, final String descripcion) {
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
        for (OperacionComparacionEnum cc : values()) {
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
