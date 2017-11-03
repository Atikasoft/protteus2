/*
 *  ActivoInactivoEnum.java
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
 *  03/12/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Nelson Jumbo <nelson.jumbo@markasoft.ec>
 */
public enum ErrorCargaServidorNovedadEnum {

    /**
     * Sin Error
     */
    SIN_ERROR("Sin error"),
    /**
     * No existe en distributivo
     */
    NO_EXISTE_EN_DISTRIBUTIVO("No existe en distributivo"),
    /**
     * No pertenece a la unidasd organizacional.
     */
    NO_PERTENECE_UNIDAD_ORGANIZACIONAL("No tiene acceso a la unidad organizacional"),
    /**
     * Valor numerico invalido
     */
    VALOR_NUMERICO_INVALIDO("El valor debe ser numérico"),
    /**
     * Servidor no existe
     */
    SERVIDOR_NO_EXISTE("El servidor no se encuentra registrado"),
    /**
     * Servidor no pertenece al regimen laboral
     */
    NO_PERTENECE_REGIMEN_LABORAL("No pertenece al regimen laboral ");

    /**
     * Descripcion
     */
    private String descripcion;

    private ErrorCargaServidorNovedadEnum(final String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }
}
