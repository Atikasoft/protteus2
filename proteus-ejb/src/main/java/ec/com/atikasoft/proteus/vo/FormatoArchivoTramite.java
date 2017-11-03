/*
 *  FormatoArchivoTramite.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Jun 28, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;


/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class FormatoArchivoTramite {

    private String grupo;

    /**
     * Numero de columna.
     */
    private int columna;

    /**
     * Descripcion.
     */
    private String descripcion;

    /**
     * Tipo.
     */
    private String tipo;

    /**
     * obligatoriedad.
     */
    private String obligatoriedad;

    /**
     * dominio.
     */
    private String dominio;

    /**
     * catalogo.
     */
    private String catalogo;

    /**
     * Constructor sin argumentos.
     */
    public FormatoArchivoTramite() {
        super();

    }
}
