/*
 *  UtilMail.java
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
 *  Oct 10, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

/**
 * Conjunto de metodos de utilidades para la gestion de direcciones de mail.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class UtilMail {

    /**
     * Constructor sin argumentos.
     */
    public UtilMail() {
        super();
    }

    /**
     * Verifica si una direccion de correo electronico esta bien formada.
     *
     * @param correoElectronico Direccion del correo electronico.
     * @return S/N.
     */
    public static boolean esCorreoElectronico(final String correoElectronico) {
        if (correoElectronico == null) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(correoElectronico);
        return m.matches();
    }
}
