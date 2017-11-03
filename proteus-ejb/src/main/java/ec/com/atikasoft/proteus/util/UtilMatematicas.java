/*
 *  UtilMatematicas.java
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
 * Utilidades para realizar calculo matematicos.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class UtilMatematicas {

    /**
     * Constructor sin argumentos.
     */
    public UtilMatematicas() {
        super();
    }

    /**
     * Trunca un numero.
     *
     * @param nD Parte decimal.
     * @param nDec
     * @return Numero truncado.
     */
    public static double truncar(double nD, final int nDec) {
        if (nD > 0) {
            nD = Math.floor(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
        } else {
            nD = Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
        }

        return nD;
    }

    /**
     * Rellenas una numero con ceros a la izquierdad dado su longitud.
     *
     * @param valor valor.
     * @param digitos digitos.
     * @return Cadena.
     */
    public static String rellenarConCerosIzq(final String valor, final int digitos) {
        final StringBuffer sb = new StringBuffer(valor);
        if (valor.trim().length() < digitos) {
            for (int i = 0; i < digitos - valor.trim().length(); i++) {
                sb.insert(0, "0");
            }
        }
        return sb.toString();
    }

    /**
     * Rellenas una numero con ceros a la izquierdad dado su longitud.
     *
     * @param valor valor.
     * @param digitos digitos.
     * @return Cadena.
     */
    public static String rellenarConCerosIzq(final Integer valor, final int digitos) {
        final StringBuffer sb = new StringBuffer(valor.toString());
        if (valor.toString().trim().length() < digitos) {
            for (int i = 0; i < digitos - valor.toString().trim().length(); i++) {
                sb.insert(0, "0");
            }
        }
        return sb.toString();
    }

    /**
     * Rellenas una numero con ceros a la izquierdad dado su longitud.
     *
     * @param valor valor.
     * @param digitos digitos.
     * @return Cadena.
     */
    public static String rellenarConCerosIzq(Long valor, final int digitos) {
        final StringBuffer sb = new StringBuffer();
        if (valor == null) {
            valor = 0L;
        }
        sb.append(valor.toString());
        if (valor.toString().trim().length() < digitos) {
            for (int i = 0; i < digitos - valor.toString().trim().length(); i++) {
                sb.insert(0, "0");
            }
        }
        return sb.toString();
    }

    /**
     * Redondea un numero.
     *
     * @param nD nd.
     * @param nDec ndec.
     * @return Numero truncado.
     */
    public static double redondear(final double nD, final int nDec) {
        return Math.round(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
    }

    /**
     * Siempre redondea al inmediato superior.
     *
     * @param nD nd.
     * @param nDec ndec.
     * @return Numero redondeado.
     */
    public static double ceil(final double nD, final int nDec) {
        return Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);

    }
}
