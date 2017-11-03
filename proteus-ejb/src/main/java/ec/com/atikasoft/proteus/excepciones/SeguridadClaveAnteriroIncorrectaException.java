/*
 *  Proteus V 1.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with Ministerio de Relaciones Laborales.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 15, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.excepciones;

import javax.ejb.ApplicationException;

/**
 * Excepcion que indica que la clave anteriro especificada para verificacion durante el cambio de clave no es correcta
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec> 
 * @version $Revision 1.0 $
 */
@ApplicationException(rollback = true)
public class SeguridadClaveAnteriroIncorrectaException extends ServicioException{

    /**
     * Constructor.
     *
     * @param cause Throwable
     */
    public SeguridadClaveAnteriroIncorrectaException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     *
     * @param message String
     * @param cause Throwable
     */
    public SeguridadClaveAnteriroIncorrectaException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message String
     */
    public SeguridadClaveAnteriroIncorrectaException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public SeguridadClaveAnteriroIncorrectaException() {
    }
}
