/*
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
 *  Mar 15, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.singlenton;

import javax.servlet.ServletContext;

/**
 * Contiene valores de variables generales del sistema.
 *
 * @author Henry Molina  <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class VariablesSistema {

    /**
     * Servlet context.
     */
    private ServletContext servletContext;

    /**
     * Constructor sin argumentos.
     */
    public VariablesSistema() {
        super();
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
