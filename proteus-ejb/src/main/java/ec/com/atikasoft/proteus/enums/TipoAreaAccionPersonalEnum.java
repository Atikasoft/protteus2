/*
 *  TipoAreaAccionPersonalEnum.java
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
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeracion que contiene los valores para la lista de los campos del area de 
 * accion de personal para la pantalla de tipos de movimiento.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum TipoAreaAccionPersonalEnum {

    /**
     * Imprime.
     */
    IMPRIME(Boolean.TRUE, "Imprime"),
    /**
     * No Imprime.
     */
    NO_IMPRIME(Boolean.FALSE, "No Imprime");

    /**
     * Columna del campo ordenamiento.
     */
    private Boolean codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo Boolean
     * @param descripcion String
     */
    private TipoAreaAccionPersonalEnum(final boolean codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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
