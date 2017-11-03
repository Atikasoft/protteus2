/*
 *  TipoAusentismoEnum.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  27/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Contiene los tipos de documentos habilitantes.
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
public enum TipoDocumentoHabilitanteEnum {

    /**
     * ACCION DE PERSONAL.
     */
    ACCION_PERSONAL("ACC", "ACCION DE PERSONAL"),
    /**
     * ACTA.
     */
    ACTA("ACT", "ACTA"),
    /**
     * CONTRATO.
     */
    CONTRATO("CON", "CONTRATO"),
    /**
     * MEMORANDO.
     */
    MEMORANDO("MEM", "MEMORANDO");
    /**
     * Código del campo de configuracion.
     */
    private final String codigo;
    /**
     * Descripcion del campo de configuracion.
     */
    private final String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoDocumentoHabilitanteEnum(final String codigo, final String descripcion) {
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
        for (TipoDocumentoHabilitanteEnum cc : values()) {
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
