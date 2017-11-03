/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.utilitarios;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author manu
 */
public class DateUtils {

    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    public static String formatoFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat(FORMATO_FECHA);
        return formato.format(fecha);
    }

    public static String fechaReintegrarse(Date fecha) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(fecha);
//        System.out.println("dia de la semana "+ cal.get(Calendar.DAY_OF_WEEK));

        if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
            cal.add(Calendar.DATE, 2);
        } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
            cal.add(Calendar.DATE, 3);
        } else {
            cal.add(Calendar.DATE, 1);
        }
        return formatoFecha(cal.getTime());
    }

    public static long restaFechas(Date fechaActual, Date fechaFin) {
        long dias = (fechaFin.getTime() - fechaActual.getTime()) / (24 * 60 * 60 * 1000);
        return dias;
    }

//    public static int diferenciaAnios(Date fecha) {
//        Calendar cal = new GregorianCalendar();
//        cal.setTime(fecha);
//        Calendar ahoraCal = Calendar.getInstance();       
//        if(cal.get(Calendar.DATE) == ahoraCal.get(Calendar.DATE) && cal.get(Calendar.MONTH) == ahoraCal.get(Calendar.MONTH)){
//            return ahoraCal.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
//        }        
//        return -1;
//    }
    
    /**
     * Implementado por Daniel Jácome en reamplazo del anterior metodo
     * @param fecha
     * @return 
     */
    public static int diferenciaAnios(Date fecha) {
        
        Date now=new Date();
        Long resta=(now.getTime()-fecha.getTime())/(24 * 60 * 60 * 1000);
        resta=resta/360;
        return resta.intValue();        
        
    }
}
