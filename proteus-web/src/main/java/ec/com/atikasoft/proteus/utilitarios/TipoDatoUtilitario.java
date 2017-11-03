/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.utilitarios;

import java.util.List;

/**
 *
 * @author oscardavid
 */
public class TipoDatoUtilitario {

    public static String contatenarValoresDeLista(List<String> coll, String delimiter) {
        if (coll != null && coll.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String x : coll) {
            sb.append(x).append(delimiter);
        }
        sb.delete(sb.length() - delimiter.length(), sb.length());
        return sb.toString();
    }

    public static String obtenerListaEmails(List<String> listaEmails) {
        return contatenarValoresDeLista(listaEmails, ";");
    }

    public static boolean compararLong(Long a, Long b) {
        boolean result = false;
        if (a == null && b == null) {
            result = true;
        } else if (a != null && b!=null && a.compareTo(b) == 0) {
            result = true;
        } 
        return result;
    }
}
