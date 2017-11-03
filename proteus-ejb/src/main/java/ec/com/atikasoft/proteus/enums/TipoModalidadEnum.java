/*
 *  TipoModalidadEnum.java
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
 *  29/01/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enum de Dias.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public enum TipoModalidadEnum {

    /**
     * Nombramiento.
     */
    NOMBRAMIENTO("N", "NOMBRAMIENTO"),
    /**
     * Contrato.
     */
    CONTRATO("C", "CONTRATO"),
    /**
     * Jubilado.
     */
    JUBILADO("J", "JUBILADO"),
    /**
     * Pasante.
     */
    PASANTE("P", "PASANTE"),
    /**
     * Concejales Alternos.
     */
    CONCEJALES_ALTERNOS("O", "CONCEJALES ALTERNOS"),
    /**
     * Servicios Profesionales.
     */
    SERVICIOS_PROFESIONALES("S", "SERVICIOS PROFESIONALES");

     /**
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
    private TipoModalidadEnum(final String codigo, final String descripcion) {
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
        for (TipoModalidadEnum cc : values()) {
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
