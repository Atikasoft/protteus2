/*
 *  ServidorExisteComoJubiladoException.java
 *  MEF $Revision 1.0 $
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
 *  Oct 10, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.excepciones;

import javax.ejb.ApplicationException;

/**
 * Error de division para cero al evaluar una formual.
 * @author Henry Molina  <hmolina@atikasoft.com.ec>
 */
@ApplicationException(rollback = false)
public class ErrorDivisionCeroException extends Exception {

    /**
     * .
     * @param string
     * Parametro.
     */
    public ErrorDivisionCeroException(final String string) {
        super(string);
    }

    /**
     * .
     * @param cause
     * Parametro.
     */
    public ErrorDivisionCeroException(final Throwable cause) {
        super(cause);
    }

    /**
     * .
     * @param message
     * Parametro.
     * @param cause
     * Parametro.
     */
    public ErrorDivisionCeroException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * .
     */
    public ErrorDivisionCeroException() {
        super();
    }
}
