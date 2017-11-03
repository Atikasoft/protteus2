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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Conjunto de utilidades que permite realizar manipular las cadenas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public final class UtilCadena {

    /**
     * Constante que define la cadena vacía.
     */
    private static final String CADENA_VACIA = "";
    private static final String CADENA_CLAVES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";
   public static final char[] CARACTERES_CLAVE = new char[]{98,102,50,99,100,48,101,97,103,104,52,111,112,54,113,114,115,49,105,106,107,53,108,109,110,51,117,118,120,121,122,57,116,119,55,56};

    /**
     * Constructor sin argumentos.
     */
    private UtilCadena() {
        super();

    }

    /**
     * Permite concatenar diferentes valores para efectos del log.
     *
     * @param operacion Texto de la operacion que se realiza.
     * @param valores Parametros de la operacion.
     * @return Cadena concatenada.
     */
    public static String concatenarLog(final String operacion, final Object... valores) {
        StringBuilder resultado = new StringBuilder("<<<");
        resultado.append(operacion);
        resultado.append(">>>");
        for (Object obj : valores) {
            resultado.append(obj);
        }
        return resultado.toString();
    }

    /**
     * Concatena objetos.
     *
     * @param valores Lista de valores
     * @return Cadena final.
     */
    public static String concatenar(final Object... valores) {
        StringBuilder resultado = new StringBuilder();
        for (Object obj : valores) {
            resultado.append(obj);
        }
        return resultado.toString();

    }

    /**
     * Duplicar caracteres n veces.
     *
     * @param cadena Cadena a repetir.
     * @param repeticion numero de repeticiones.
     * @return Candena final.
     */
    public static String duplicarCadena(final String cadena, final int repeticion) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < repeticion; i++) {
            sb.append(cadena);
        }
        return sb.toString();
    }

    /**
     * Llena una lista con objetos.
     *
     * @param lista
     * @param indices
     */
    public static void llenarLista(final List<Object> lista, final int indices) {
        for (int i = 0; i < indices; i++) {
            lista.add("");
        }
    }

    /**
     * Se encarga de reemplazar los espacios en blanco por el valor de
     * "reemplazo".
     *
     * @param cadenaOriginal cadena original
     * @param reemplazo el valor que reemplazará a los espacios en blanco
     * @return cadena con los espacios reemplazados
     */
    public static String reemplazarEspacios(final String cadenaOriginal, final String reemplazo) {
        return cadenaOriginal.replaceAll("(\\s){1,}", reemplazo);
    }

    /**
     * Se encarga de convertir las cadenas vacias en nulas.
     *
     * @param cadena la cadena a verificar
     * @return null si la cadena recibida esta vacía, caso contrario retorna la
     * misma cadena recibida.
     */
    public static String convertirEnNullCadenaVacia(final String cadena) {
        String resultado = null;
        if (cadena != null && CADENA_VACIA.equals(cadena.trim())) {
        } else {
            resultado = cadena;
        }
        return resultado;
    }

    /**
     * Se encarga de convertir en mayusculas el primer caracter de la cadena
     * recibida como parámetro.
     *
     * @param cadena cadena original
     * @return la cadena con el primer caracter en mayusculas.
     */
    public static String convertirPrimerCaracterMayusculas(final String cadena) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(cadena.charAt(0)));
        stringBuilder.append(cadena.substring(1));
        return stringBuilder.toString();

    }

    /**
     * Busca una palabra completa dentro d eun texto.
     *
     * @param palabra
     * @param texto
     * @return Indice de la palabra.
     */
    public static int buscarPalabraCompleta(final String palabra, final StringBuilder texto) {
        int i = texto.indexOf(palabra);
        if (i != -1) {
            if (i > 0 && (Character.isLetter(texto.charAt(i - 1)) || texto.charAt(i - 1) == '_')) {
                i = -1;
            } else if (i < texto.length() && texto.length() > (palabra.length() + i) && (Character.isLetter(
                    texto.charAt(i + palabra.length())) || texto.charAt(i + palabra.length()) == '_')) {
                i = -1;
            }
        }
        return i;
    }

    /**
     * Generar un token para ser usado en el progreso de la ejecucion de
     * procesos en backgrouod.
     *
     * @param tipo
     * @return Token.
     */
    public static String generarTokenDeProgreso(final String tipo) {
        return concatenar(tipo, new SimpleDateFormat("ddMMyyyyhhmmssSSS").format(new Date()));
    }
/**
 * 
 * @param largoClave
 * @return 
 */
    public static String generarClaveUsuario(final Long largoClave) {
        StringBuilder clave = new StringBuilder("");
        for (int i = 0; i < largoClave; i++) { 
            int numero = (int) (Math.random() * (CADENA_CLAVES.length())); 
            String caracter = CADENA_CLAVES.substring(numero, numero + 1); 
            clave.append(caracter); 
        }
        return clave.toString();
    }
}
