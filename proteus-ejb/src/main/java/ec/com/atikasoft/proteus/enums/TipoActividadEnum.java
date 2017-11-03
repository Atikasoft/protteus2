/*
 *  TipoActividadEnum.java
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
 *  14/12/2012
 *
 */

package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */

public enum TipoActividadEnum {
    /**
     * Tipo Asignado.
     */
    ASIGNADO("Asignado", Boolean.TRUE),
    /**
     * Tipo Atendido.
     */
    ATENDIDO("Atendido", Boolean.FALSE);

     /**
     * Descripcion de la enumeración.
     */
    private String descripcion;

    /**
     * codigo de la enumeración.
     */
    private Boolean codigo;
    /**
     * Constructor requerido.
     *
     * @param descripcion String
     * @param codigo Boolean
     */
    private TipoActividadEnum(final String descripcion, final Boolean codigo) {
        this.descripcion = descripcion;
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
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public Boolean getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Boolean codigo) {
        this.codigo = codigo;
    }
}
