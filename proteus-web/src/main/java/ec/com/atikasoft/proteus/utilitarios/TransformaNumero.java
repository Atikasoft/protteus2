/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.utilitarios;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 *
 * @author james
 */
public class TransformaNumero {
//    private Integer counter;
//    private String value;
//    private String nombreDeMoneda;

    public TransformaNumero() {
//        nombreDeMoneda = "DÓLARES";
    }

    public String transformar(Integer $num) {
//        this.counter = $num;
        String signo = "";
        if ($num < 0) {
            signo = "MENOS ";
            $num = -$num;
        }
        return signo + doThings($num) + " PUNTOS";
    }
    private String devuelveCantidadEnMoneda(Double valor) {
        DecimalFormatSymbols simbols = new DecimalFormatSymbols();
        simbols.setDecimalSeparator('.');
        simbols.setPatternSeparator(',');
        NumberFormat formato = new DecimalFormat("############0.00", simbols);
        return formato.format(valor);
    }
    public String transformar(Double $num) {
        $num = Double.parseDouble(devuelveCantidadEnMoneda($num));
        String signo = "";
        if ($num < 0) {
            signo = "MENOS ";
            $num = -$num;
        }
        int _counter = $num.intValue();
        Double a = $num;
        Object pasa = a;
        String numero = pasa.toString().replace(".", ",");
        String prueba = numero.split(",")[1];
        if (prueba.length() == 1 && prueba.equals("0")) {
        } else {
            if (prueba.length() == 1) {
                prueba += "0";
            }
        }
        try {
            return signo + doThings(_counter) + " PUNTOS" + (prueba.trim().isEmpty() ? "" : (" CON " + doThings(new Integer(prueba.trim())) + " CENTECIMAS"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public String posicion(Integer posicion) {
        switch (posicion) {
            case 1:
                return "PRIMERO";
            case 2:
                return "SEGUNDO";
            case 3:
                return "TERCERO";
            case 4:
                return "CUARTO";
            case 5:
                return "QUINTO";
            case 6:
                return "SEXTO";
            case 7:
                return "SEPTIMO";
            case 8:
                return "OCTAVO";
            case 9:
                return "NOVENO";
            case 10:
                return "DECIMO";
            default:
                return "NO VALIDO";
        }

    }

    private String doThings(Integer _counter) {
        if (_counter > 2000000) {
            return "DOS MILLONES";
        }

        switch (_counter) {
            case 0:
                return "CERO";
            case 1:
                return "UN"; //UNO
            case 2:
                return "DOS";
            case 3:
                return "TRES";
            case 4:
                return "CUATRO";
            case 5:
                return "CINCO";
            case 6:
                return "SEIS";
            case 7:
                return "SIETE";
            case 8:
                return "OCHO";
            case 9:
                return "NUEVE";
            case 10:
                return "DIEZ";
            case 11:
                return "ONCE";
            case 12:
                return "DOCE";
            case 13:
                return "TRECE";
            case 14:
                return "CATORCE";
            case 15:
                return "QUINCE";
            case 20:
                return "VEINTE";
            case 30:
                return "TREINTA";
            case 40:
                return "CUARENTA";
            case 50:
                return "CINCUENTA";
            case 60:
                return "SESENTA";
            case 70:
                return "SETENTA";
            case 80:
                return "OCHENTA";
            case 90:
                return "NOVENTA";
            case 100:
                return "CIEN";
            case 200:
                return "DOSCIENTOS";
            case 300:
                return "TRESCIENTOS";
            case 400:
                return "CUATROCIENTOS";
            case 500:
                return "QUINIENTOS";
            case 600:
                return "SEISCIENTOS";
            case 700:
                return "SETECIENTOS";
            case 800:
                return "OCHOCIENTOS";
            case 900:
                return "NOVECIENTOS";

            case 1000:
                return "MIL";

            case 1000000:
                return "UN MILLON";
            case 2000000:
                return "DOS MILLONES";
        }
        if (_counter < 20) {
            return "DIECI" + doThings(_counter - 10);
        }
        if (_counter < 30) {
            return "VEINTI" + doThings(_counter - 20);
        }
        if (_counter < 100) {
            return doThings((int) (_counter / 10) * 10) + " Y " + doThings(_counter % 10);
        }
        if (_counter < 200) {
            return "CIENTO " + doThings(_counter - 100);
        }
        if (_counter < 1000) {
            return doThings((int) (_counter / 100) * 100) + " " + doThings(_counter % 100);
        }
        if (_counter < 2000) {
            return "MIL " + doThings(_counter % 1000);
        }
        if (_counter < 1000000) {
            String var = "";
            var = doThings((int) (_counter / 1000)) + " MIL";
            if (_counter % 1000 != 0) {
                var += " " + doThings(_counter % 1000);
            }
            return var;
        }
        if (_counter < 2000000) {
            return "UN MILLON " + doThings(_counter % 1000000);
        }
        return "";
    }
}
