/*
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
public enum MetadataTablaEnum {

    /**
     * DISTRIBUTIVOS.
     */
    DISTRIBUTIVOS("DISTRIBUTIVOS"),
    /**
     * PERIODOS.
     */
    PERIODOS("PERIODOS"),
    /**
     * NOMINAS.
     */
    NOMINAS("NOMINAS"),
    /**
     * LIQUIDACIONES.
     */
    LIQUIDACIONES("LIQUIDACIONES");

    /**
     * Código del campo de configuracion.
     */
    private String codigo;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private MetadataTablaEnum(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

}
