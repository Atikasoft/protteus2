/*
 *  UtilTransformar.java
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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Conjunto de utilidades que permite realizar transformaciones de formatos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class UtilTransformar {

    /**
     * Constructor sin argumentos.
     */
    public UtilTransformar() {
        super();
    }

    /**
     * Transforma desde byte a hexadecimal.
     *
     * @param data Dato a transformar.
     * @return Dato exa.
     */
    public static String bytesToHex(final byte[] data) {
        if (data == null) {
            return null;
        } else {
            final int len = data.length;
            String str = "";
            for (int i = 0; i < len; i++) {
                if ((data[i] & 0xFF) < 16) {
                    str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
                } else {
                    str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
                }
            }
            return str.toUpperCase();
        }
    }

    /**
     * Tranforma un codigo hex a arreglo de byte.
     *
     * @param str Cadena hex.
     * @return Arreglo de bytes.
     */
    public static byte[] hexToBytes(final String str) {
        if (str == null) {
            return null;
        } else if (str.length() < 2) {
            return null;
        } else {
            final int len = str.length() / 2;
            final byte[] buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }

    }

    /**
     * Transforma un string a hex.
     *
     * @param base Cadena.
     * @return Cadena hex.
     */
    public static String stringToHex(final String base) {
        final StringBuffer buffer = new StringBuffer();
        int intValue;
        for (int x = 0; x < base.length(); x++) {
            int cursor = 0;
            intValue = base.charAt(x);
            final String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
            for (int i = 0; i < binaryChar.length(); i++) {
                if (binaryChar.charAt(i) == '1') {
                    cursor += 1;
                }
            }
            if (cursor % 2 > 0) {
                intValue += 128;
            }
            buffer.append(Integer.toHexString(intValue) + " ");
        }
        return buffer.toString();
    }

    /**
     * Copia un objeto a otro.
     *
     * @param o
     * @return
     */
    public static Object copiarA(final Object o) {
        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(o);
        System.out.println("copiarA=>"+xml);
        return xstream.fromXML(xml);
    }
}
