/*
 *  BeanLocator.java
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
 *  Dec 30, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

import java.io.Serializable;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Innstacia EJB
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class BeanLocator implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constante que define el contexto de la aplicacion ear.
     */
    private static final String NOMBRE_CONTEXTO_EAR = "gestor-ear";

    /**
     * Constante que define el contecto del jar.
     */
    private static final String NOMBRE_CONTEXTO_JAR = "gestor-ejb";

    /**
     * Constante que define el contecto raiz para recuperacion de los ejbs.
     */
    private static final String CONTEXTO_RAIZ = "java:global";

    /**
     * Constructor sin argumentos.
     */
    private BeanLocator() {
        super();
    }

    /**
     * Metodo para recuperacion de Session Beans remotos.
     *
     * @param clazzRemote Interfaz remota.
     * @param clazzImpl ejb.
     * @return Instancia del ejb.
     * @throws NamingException Error de nombrado.
     */
//    public static Object getServicioRemoto(final Class clazzRemote, final Class clazzImpl)
//            throws NamingException {
//        Properties props = new Properties();
//        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost"); // default!
//        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700"); // default!
//        InitialContext context = new InitialContext(props);
//        Object o = context.lookup(obtenerNombreServicio(clazzRemote, clazzImpl));
//        context.close();
//        return o;
//    }

    /**
     * Metodo para recuperacion de Session Beans locales.
     *
     * @param clazzLocal Interfaz remota.
     * @param clazzImpl ejb.
     * @return Instancia del ejb.
     * @throws NamingException Error de nombrado.
     */
    public static Object getServicioLocal(final Class clazz)
            throws NamingException {
        InitialContext context = new InitialContext();
        Object o = context.lookup(obtenerNombreServicio(clazz));
        context.close();
        return o;
    }

    /**
     * Generar el nombre jndi de un ejb.
     *
     * @param interfaceRemota Interfaz remota.
     * @param implementacion EJB.
     * @return Nombre jndi.
     */
    public static String obtenerNombreServicio(final Class implementacion) {
        StringBuilder sb = new StringBuilder(16);
        sb.append(CONTEXTO_RAIZ).append('/').append(NOMBRE_CONTEXTO_EAR).append('/').append(
                NOMBRE_CONTEXTO_JAR).append('/').append(implementacion.getSimpleName()).append('!');
        sb.append(implementacion.getName());
        return sb.toString();
    }
}
