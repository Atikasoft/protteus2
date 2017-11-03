/*
 *  ObligatorioEnum.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum ObligatorioEnum {

    /**
     * si.
     */
    SI(Boolean.TRUE, "Si"),
    /**
     * no.
     */
    NO(Boolean.FALSE, "No");

    /**
     * Código de la jornada.
     */
    private Boolean codigo;

    /**
     * Descripcion de la jornada.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private ObligatorioEnum(final Boolean codigo, final String descripcion) {
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
        for (ObligatorioEnum  o: values()) {
            if (o.getCodigo()) {
                des = o.getDescripcion();
                break;
            }
        }
        return des;
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
    public Boolean getCodigo() {
        return codigo;
    }

}
