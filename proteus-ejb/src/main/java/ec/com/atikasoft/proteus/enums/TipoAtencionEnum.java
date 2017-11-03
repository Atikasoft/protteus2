/*
 *  ProcesoEnum.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 31, 2012
 *
 *  Copyright (C) 2009-2010 Advance Consulting Cia. Ltda.
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Lista de tipos de atencion de estado.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
public enum TipoAtencionEnum {

    /**
     * Tipo de atencion manual.
     */
    MANUAL("M", "MANUAL"),
    /**
     * Tipo de atencion automatico.
     */
    AUTOMATICO("A", "AUTOMATICO");

    /**
     * Codigo del proceso.
     */
    private String codigo;

    /**
     * Descripcion del proceso.
     */
    private String descripcion;

    /**
     * Constructor.
     */
    private TipoAtencionEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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
