/*
 *  UtilNumeros.java
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
 *  Feb 27, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Juan Carlos Vaca <jvaca@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public final class UtilNumeros implements Serializable {

    /**
     * Fomarteador de número.
     */
    private final DecimalFormat formateadorMonedas;

    /**
     * Formato de presentación de monedas.
     */
    public static final String FORMATO_MONEDA = "###,##0.00";

    /**
     * Instancia de la clase para formato de números.
     */
    private static final UtilNumeros INSTANCIA = new UtilNumeros();

    /**
     * Número cien.
     */
    public static final Integer NUMERO_CIEN = 100;

    /**
     * Constructor sin argumentos.
     */
    private UtilNumeros() {
        super();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormatSymbols.setGroupingSeparator(',');
        formateadorMonedas = new DecimalFormat(FORMATO_MONEDA, decimalFormatSymbols);
        formateadorMonedas.setGroupingSize(3);

    }

    /**
     * Se encarga de retornar una instancia de la clase util números.
     *
     * @return instancia
     */
    public static UtilNumeros getInstancia() {
        return INSTANCIA;
    }

    /**
     * @return the formateadorMonedas
     */
    public DecimalFormat getFormateadorMonedas() {
        return formateadorMonedas;
    }

    /**
     * Se encarga de formatear un número como moneda.
     *
     * @param numero numero a dar formato
     * @return numero en formato de moneda
     */
    public String formatearValorMonetario(final Number numero) {
        String resultado;
        if (numero == null) {
            //LOG.debug("El número recibido es null");
            resultado = "";
        } else {
            resultado = this.formateadorMonedas.format(numero);
        }
        return resultado;
    }

    /**
     * Se encarga de parsear a Number una cadena numérica.
     *
     * @param cadenaNumerica cadena numerica
     * @return instancia de Number
     * @throws ParseException encaso de no poder parsear la cadena numérica.
     */
    public Number parsearCadena(final String cadenaNumerica) throws ParseException {
        if (cadenaNumerica == null) {
            throw new ParseException("La cadena recibida no puede ser null", -1);
        }
        return this.formateadorMonedas.parse(cadenaNumerica);
    }

    /**
     * Se encarga de convertir una cadena en Bigdecimal.
     *
     * @param cadenaNumerica cadena numerica
     * @return instancia de bigdecimal
     * @throws ParseException en caso de que la cadena no pueda ser parseada a
     * numero.
     */
    public BigDecimal obtenerBigDecimal(final String cadenaNumerica) throws ParseException {
        Number number = parsearCadena(cadenaNumerica);
        return new BigDecimal(number.toString());
    }

    /**
     * Formatea monedas.
     */
    public String formatearMoneda(final Object valor) {
        if (valor instanceof BigDecimal) {
            return getFormateadorMonedas().format(valor);
        } else {
            return valor.toString();
        }
    }

    /**
     * Metodo que recibe un munero y lo transforma en letras.
     *
     * @param cantidad en numeros
     * @return numeros en letras
     */
    public String transformarNumerosLetras(final BigDecimal cantidad) {
        CnvNumsLets nl = new CnvNumsLets();
        String cantidaCadena = cantidad.toString();
        String valor[] = cantidaCadena.split("\\.");
        if (valor.length == 2) {
            cantidaCadena = nl.toLetras(Integer.parseInt(valor[0])).trim().toUpperCase() + " DOLARES CON "
                    + nl.toLetras(Integer.parseInt(valor[1])).
                    trim().toUpperCase() + " CENTAVOS";
        } else if (valor.length == 1) {
            cantidaCadena = nl.toLetras(Integer.parseInt(valor[0])).trim().toUpperCase() + " DOLARES";
        }
        return cantidaCadena;
    }

    public static boolean validadorDeCedula(final String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }

    /**
     *
     * @param d
     * @param scale
     * @param roundUp
     * @return
     */
    public static BigDecimal redondear(final BigDecimal d, final int scale, final boolean roundUp) {
        int mode = (roundUp) ? BigDecimal.ROUND_HALF_DOWN : BigDecimal.ROUND_HALF_UP;
        return d.setScale(scale, mode);
    }

    /**
     *
     * @param max
     * @return
     */
    public static int indiceRandomico(int max) {
        int valor = (int) ((max * Math.random()));
        if (valor < 0 || valor > max) {
            indiceRandomico(max);
        }
        return valor;

    }

    /**
     * 
     * @param valor
     * @param minimo
     * @param maximo
     * @return 
     */
    public static Boolean between(BigDecimal valor, BigDecimal minimo, BigDecimal maximo) {
        return valor.compareTo(minimo) >= 0 && valor.compareTo(maximo) <= 0;
    }
}
