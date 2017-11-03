/*
 *  UtilFechas.java
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

import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Conjunto de metodos de utilidades para la gestion de fechas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public final class UtilFechas {

    /**
     * Formato de fecha:dd/MM/yyyy.
     */
    public static final String FORMATO_FECHA = "dd/MM/yyyy";

    /**
     * Formato de fecha:dd/MM/yyyy HH:mm:ss.
     */
    public static final String FORMATO_FECHA_LARGO = "dd/MM/yyyy HH:mm:ss";
    /**
     * Formato de fecha:yyyy-MM-dd HH:mm:ss.SSS.
     */
    public static final String FORMATO_FECHA_COMPLETO = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * *
     * Formato de fecha: yyyy-MM-dd.
     */
    public static final String FORMATO_FECHA_SQL_CORTA = "MM/dd/yyyy hh:mm:ss PM";

    /**
     * Formato de fecha:yyyy/MM/dd.
     */
    public static final String FORMATO_FECHA_1 = "yyyy/MM/dd";

    /**
     * Formato de fecha: ddMMyyyy.
     */
    public static final String FORMATO_FECHA_2 = "ddMMyyyy";

    /**
     * Formato de fecha: yyyyMMddHHmmssSSS.
     */
    public static final String FORMATO_FECHA_COMPLETO_1 = "yyyyMMddHHmmssSSS";

    /**
     * Formato de Hora: HH:mm:ss.
     */
    public static final String FORMATO_HORA = "HH:mm:ss";

    /**
     * Catalogo del nombre de los meses.
     */
    private static final String[] MESES = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto",
        "septiembre", "octubre", "noviembre", "diciembre"};

    /**
     * Constante para calculo de dias en el año.
     */
    public static final double DIAS_AÑO = 365.25f;

    /**
     * Número de meses en el año.
     */
    public static final Integer MESES_EN_ANIO = 12;

    /**
     * Número de minutos en una hora.
     */
    public static final double MIN_EN_HORA = 60;

    /**
     * Número de dias de fin de semana en un mes comercial.
     */
    public static final Integer DIAS_FINES_SEMANA_EN_MES_COMERCIAL = 8;

    /**
     * Número de dias laborales en un mes comercial.
     */
    public static final Integer DIAS_LABORABLES_EN_MES_COMERCIAL = 22;

    /**
     * Constructor sin argumentos.
     */
    private UtilFechas() {
        super();
    }

    /**
     * Formato dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public static String formatear(final Date fecha) {
        if (fecha == null) {
            return "**/**/****";
        } else {
            return new SimpleDateFormat(FORMATO_FECHA).format(fecha);
        }

    }

    /**
     * Formato dd/MM/yyyy
     *
     * @param fecha
     * @return
     */
    public static Date formatear(final String fecha) throws ParseException {
        return new SimpleDateFormat(FORMATO_FECHA).parse(fecha);
    }

    /**
     * Formato ddMMyyyy
     *
     * @param fecha
     * @return
     */
    public static String formatear2(final Date fecha) {
        return new SimpleDateFormat(FORMATO_FECHA_2).format(fecha);
    }

    /**
     * Formato yyyyMMddHHmmssSSS
     *
     * @param fecha
     * @return
     */
    public static String formatearCompleto1(final Date fecha) {
        return new SimpleDateFormat(FORMATO_FECHA_COMPLETO_1).format(fecha);
    }

    /**
     * Formato yyyyMMddHHmmssSSS
     *
     * @param fecha
     * @return
     */
    public static String formatearSQLCorta(final Date fecha) {
        return new SimpleDateFormat(FORMATO_FECHA_SQL_CORTA).format(fecha);
    }

    /**
     * Formato dd/MM/yyyy HH:mm:ss
     *
     * @param fecha
     * @return
     */
    public static String formatearLargo(final Date fecha) {
        return new SimpleDateFormat(FORMATO_FECHA_LARGO).format(fecha);
    }

    /**
     * Formato HH:mm:ss
     *
     * @param fecha
     * @return
     */
    public static String formatearEnTiempo(final Date fecha) {
        return new SimpleDateFormat(FORMATO_HORA).format(fecha);
    }

    /**
     * Convierte cadenas a formato fecha (Date)
     *
     * @param fechaString
     * @param formato
     * @return
     */
    public static Date convertirFechaStringEnDate(String fechaString, String formato) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat(formato);
        Date fecha = null;
        try {
            fecha = formatoDelTexto.parse(fechaString);
        } catch (ParseException ex) {
            System.out.println(" Error al convertir fecha string a tipo Date " + ex);
        }
        return fecha;
    }

    /**
     * Calcula la edad a partir de la fecha de nacimiento.
     *
     * @param fechaNacimiento Fecha de nacimiento.
     * @return Edad en anios.
     */
    public static int calcularEdadEnAnios(final Date fechaNacimiento) {
        int age = 0;
        if (fechaNacimiento != null) {
            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTime(fechaNacimiento);
            Calendar today = Calendar.getInstance();
            age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            dateOfBirth.add(Calendar.YEAR, age);
            if (today.before(dateOfBirth)) {
                age--;
            }
        }
        return age;
    }

    /**
     * Suma un numero de horas a una fecha.
     *
     * @param fecha Fecha inicial.
     * @param horas horas a sumarse
     * @return Fecha sumada.
     */
    public static Date sumarHoras(final Date fecha, final Integer horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR, horas);
        return calendar.getTime();
    }

    /**
     * Suma un numero de dias a una fecha.
     *
     * @param fecha Fecha inicial.
     * @param dias Dias a sumarse
     * @return Fecha sumada.
     */
    public static Date sumarDias(final Date fecha, final Integer dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias - 1); //hay codigo en función de este cálculo en vacaciones y asistencia.
        return calendar.getTime();
    }

    /**
     * Suma un numero de semanas a una fecha.
     *
     * @param fecha Fecha inicial.
     * @param semanas Dias a sumarse
     * @return Fecha sumada.
     */
    public static Date sumarSemanas(final Date fecha, final Integer semanas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.WEEK_OF_YEAR, semanas);
        return calendar.getTime();
    }

    /**
     * Sumar meses.
     *
     * @param fecha Fecha inicial.
     * @param meses Numero de meses.
     * @return Fecha final.
     */
    public static Date sumarMeses(final Date fecha, final Integer meses) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }

    /**
     * Sumar anios.
     *
     * @param fecha Fecha inicial.
     * @param anios Numero de anios.
     * @return Fecha final.
     */
    public static Date sumarAnios(final Date fecha, final Integer anios) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, anios);
        return calendar.getTime();
    }

    /**
     * Permite sumar periodos para reglas y alertas.
     *
     * @param fechaDesde
     * @param tipoPeriodo
     * @param valorPeriodo
     * @return
     */
    public static Date sumarPeriodos(final Date fechaDesde, final String tipoPeriodo, final Integer valorPeriodo) {
        Date fechaHasta = fechaDesde;
        if (tipoPeriodo.equals(TipoPeriodoAlertaEnum.ANIO.getCodigo())) {
            fechaHasta = UtilFechas.sumarAnios(fechaDesde, valorPeriodo);
        } else if (tipoPeriodo.equals(TipoPeriodoAlertaEnum.MES.getCodigo())) {
            fechaHasta = UtilFechas.sumarMeses(fechaDesde, valorPeriodo);
        } else if (tipoPeriodo.equals(TipoPeriodoAlertaEnum.SEMANA.getCodigo())) {
            fechaHasta = UtilFechas.sumarSemanas(fechaDesde, valorPeriodo);
        } else if (tipoPeriodo.equals(TipoPeriodoAlertaEnum.DIA.getCodigo())) {
            fechaHasta = UtilFechas.sumarDias(fechaDesde, valorPeriodo);
        } else if (tipoPeriodo.equals(TipoPeriodoAlertaEnum.HORA.getCodigo())) {
            fechaHasta = UtilFechas.sumarHoras(fechaDesde, valorPeriodo);
        }
        return fechaHasta;
    }

    public static long calcularNumeroDiasDePeriodo(final Date fechaInicial, final String tipoPeriodo,
            final Integer valorPeriodo) {
        Date fechaFinal = sumarPeriodos(fechaInicial, tipoPeriodo, valorPeriodo);
        return calcularDiferenciaDiasEntreFechas(fechaInicial, fechaFinal);
    }

    /**
     * Calcula la diferencia de dias entre dos fechas.
     *
     * @param fechaInicial Fecha inicial.
     * @param fechaFinal Fecha final.
     * @return Dias de diferencia.
     */
    public static Long calcularDiferenciaDiasEntreFechas(final Date fechaInicial, final Date fechaFinal) {
        final long millsecsPerDay = 24 * 60 * 60 * 1000; //Milisegundos al dia
        long dias = 0;
        if (fechaFinal != null && fechaInicial != null) {
            dias = (fechaFinal.getTime() - fechaInicial.getTime()) / millsecsPerDay + 1;
        }
        return dias;
    }

    public static Long calcularMinutosEntreHoras(final Time horaInicial, final Time horaFinal) {
        final long millsecsPerDay = 60 * 1000; //Milisegundos en un minuto
        long dias = 0;
        if (horaFinal != null && horaInicial != null) {
            dias = (horaFinal.getTime() - horaInicial.getTime()) / millsecsPerDay;
        }
        return dias;
    }

    /**
     * Este metodo calcula la diferencia de fechas en minutos.
     *
     * @param fechaInicial fecha inicio
     * @param fechaFinal fecha fin
     * @return Diferencia entre fechas en minutos.
     */
    public static Long calcularDiferenciaHorasEntreFechas(final Date fechaInicial, final Date fechaFinal) {
        return calcularDiferenciaMinutosEntreFechas(fechaInicial, fechaFinal) / 60;
    }

    /**
     * Este metodo calcula la diferencia de fechas en minutos.
     *
     * @param fechaInicial fecha inicio
     * @param fechaFinal fecha fin
     * @return Diferencia entre fechas en minutos.
     */
    public static Long calcularDiferenciaMinutosEntreFechas(final Date fechaInicial, final Date fechaFinal) {
        long minutos = 0;
        if (fechaFinal != null && fechaInicial != null) {
            long milis1 = fechaInicial.getTime();
            long milis2 = fechaFinal.getTime();
            long diff = milis2 - milis1;
            minutos = diff / (60 * 1000);
        }
        return minutos;

    }

    /**
     * Obtiene el dia del mes de una fecha.
     *
     * @param fecha Fecha.
     * @return Dia del mes.
     */
    public static Integer obtenerDiaDelMes(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Obtiene el dia del mes de una fecha.
     *
     * @param fecha Fecha.
     * @return Dia del mes.
     */
    public static Integer obtenerUltimoDiaDelMes(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Obtiene la hora del dia de una fecha.
     *
     * @param fecha Fecha.
     * @return Dia del mes.
     */
    public static Integer obtenerHoraDelDia(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Obtiene la hora del dia de una fecha.
     *
     * @param fecha Fecha.
     * @return Dia del mes.
     */
    public static Integer obtenerMinutoDeHora(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Obtiene el mes de una fecha.
     *
     * @param fecha Fecha.
     * @return Numero de mes.
     */
    public static Integer obtenerMes(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Obtiene el mes de una fecha.
     *
     * @param fecha Fecha.
     * @return Numero de mes.
     */
    public static Integer obtenerNumeroRealDelMes(final Date fecha) {
        return obtenerMes(fecha) + 1;
    }

    /**
     * Obtiene el anio de una fecha.
     *
     * @param fecha fecha de la que se extraerá el año
     * @return el año de la fecha pasada como parámetro
     */
    public static Integer obtenerAnio(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR);

    }

    /**
     * Determinar el tiempo en milisegundos correspondiente al primero de enero del año en curso.
     *
     * @param hoy fecha atual
     * @return el valor en milisegundos del primero de enero
     */
    public static long timePrimeroEnero(final GregorianCalendar hoy) {
        int anioActual = hoy.get(Calendar.YEAR);
        GregorianCalendar primeroEnero = new GregorianCalendar(anioActual, Calendar.JANUARY, 1, 0, 0, 0);
        primeroEnero.set(Calendar.MILLISECOND, 0);
        return primeroEnero.getTimeInMillis();
    }

    /**
     * Determina el tiempo en milisegundos del 31 de diciembre del año en curso.
     *
     * @param hoy fecha actual
     * @return el valor en milisegundos del 31 de diciembre
     */
    public static long timeTreintaYUnoDeDiciembre(final GregorianCalendar hoy) {
        int anioActual = hoy.get(Calendar.YEAR);
        GregorianCalendar treitaYUnoDeDiciembre = new GregorianCalendar(anioActual, Calendar.DECEMBER, 31, 23, 59, 59);
        treitaYUnoDeDiciembre.set(Calendar.MILLISECOND, 999);
        return treitaYUnoDeDiciembre.getTimeInMillis();
    }

    /**
     * Se encarga de devolver una fecha con la hora inicial del día.
     *
     * @param fecha fecha usada para la determinación de la hora inicial
     * @return fecha con la hora inicial del día
     */
    public static Date obtenerInicioDelDia(final Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Se encarga de determinar el tiempo en milisegundos de la hora inicial del día.
     *
     * @param fecha fecha usada para la determinación del tiempo de la hora inicial.
     * @return tiempo en milisegundos.
     */
    public static long obtenerTiempoInicioDelDia(final Date fecha) {
        return obtenerInicioDelDia(fecha).getTime();
    }

    /**
     * Se encarga de devolver una fecha con la hora final del día.
     *
     * @param fecha fecha usada para la determinación de la hora final
     * @return fecha con la hora final del día
     */
    public static Date obtenerFinDelDia(final Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * Se encarga de devolver una fecha con la hora inicial del día.
     *
     * @param fecha fecha usada para la determinación de la hora inicial
     * @return fecha con la hora inicial del día
     */
    public static Date obtenerPrimerDiaDelMes(final Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Obtiene el ultimo día del mes deuna fecha especifica.
     *
     * @param fecha
     * @return
     */
    public static Date obtenerUltimaFechaDelMes(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                cal.getMaximum(Calendar.HOUR_OF_DAY),
                cal.getMaximum(Calendar.MINUTE),
                cal.getMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    /**
     * Se encarga de determinar el tiempo en milisegundos de la hora final del día.
     *
     * @param fecha fecha usada para la determinación del tiempo de la hora final.
     * @return tiempo en milisegundos.
     */
    public static long obtenerTiempoFinDelDia(final Date fecha) {
        return obtenerFinDelDia(fecha).getTime();
    }

    /**
     * Verifica si una fecha se encuentra entre un periodo de fechas.
     *
     * @param fecha Fecha actual.
     * @param fechaInicio Fecha inicio.
     * @param fechaFinal Fecha final.
     * @return Resultado de la comparacion.
     */
    public static Boolean between(final Date fecha, final Date fechaInicio, final Date fechaFinal) {
        Boolean resultado = Boolean.FALSE;
        if (fecha.getTime() >= fechaInicio.getTime() && fecha.getTime() <= fechaFinal.getTime()) {
            resultado = Boolean.TRUE;
        }
        return resultado;
    }

    /**
     * Este metodo valida que la primera fecha sea menor a la segunda.
     *
     * @param fechaInicio Date
     * @param fechaCreacion Date
     * @return boolean
     */
    public static boolean validarFechaInicioFin(final Date fechaInicio, final Date fechaCreacion) {
        Calendar fc = new GregorianCalendar();
        fc.setTime(fechaCreacion);
        fc.set(Calendar.HOUR_OF_DAY, 0);
        fc.set(Calendar.MINUTE, 0);
        fc.set(Calendar.SECOND, 0);
        fc.set(Calendar.MILLISECOND, 0);
        Calendar fi = new GregorianCalendar();
        fi.setTime(fechaInicio);
        fi.set(Calendar.HOUR_OF_DAY, 0);
        fi.set(Calendar.MINUTE, 0);
        fi.set(Calendar.SECOND, 0);
        fi.set(Calendar.MILLISECOND, 0);
        boolean resultado = Boolean.TRUE;
        if (String.valueOf(fi.getTimeInMillis()).equals(String.valueOf(fc.getTimeInMillis()))) {
            resultado = Boolean.TRUE;
        } else if (fi.getTimeInMillis() > fc.getTimeInMillis()) {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    /**
     * Metodo que recibe un munero y lo transforma en letras.
     *
     * @param fecha
     * @return numeros en letras
     */
    public static String transformarFechasLetras(final Date fecha) {
        CnvNumsLets nl = new CnvNumsLets();
        StringBuilder letras = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        letras.append(nl.toLetras(calendar.get(Calendar.DAY_OF_MONTH)).toLowerCase());
        letras.append(" días del mes de ");
        letras.append(MESES[calendar.get(Calendar.MONTH)]);
        letras.append(" del año ");
        letras.append(nl.toLetras(calendar.get(Calendar.YEAR)).toLowerCase());
        return letras.toString();
    }

    /**
     * Se encarga se encerar las horas, minutos, segundos y milisegundos.
     *
     * @param fecha
     * @return
     */
    public static Date truncarFecha(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Determina si la fecha es fin de semana.
     *
     * @param fecha
     * @return
     */
    public static boolean esFinSemana(final Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }
        return false;
    }

    /**
     * Se encarga se encerar las horas, minutos, segundos y milisegundos.
     *
     * @param fecha
     * @return
     */
    public static Date redondearFecha(final Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * Calcula la fecha de inicio del ultimo anio de labores del servidor.
     *
     * @param fechaIngreso
     * @return
     */
    public static Date calcularFechaInicioUltimoAnioDeLabores(final Date fechaIngreso) {
        Date fechaInicio = fechaIngreso;
        Date fechaFin = sumarDias(sumarAnios(fechaInicio, 1), -1);
        Date fechaActual = truncarFecha(new Date());
        while (!between(fechaActual, fechaInicio, fechaFin)) {
            fechaInicio = sumarDias(fechaFin, 1);
            fechaFin = sumarDias(sumarAnios(fechaInicio, 1), -1);
        }
        return fechaInicio;
    }

    /**
     * Convierte Minutos en horas
     *
     * @param minutos cantidad en números
     * @return
     */
    public static double convertirMinutosEnHoras(final long minutos) {
        return minutos / 60;
    }

    /**
     * Convierte Minutos en dias
     *
     * @param minutos cantidad en números
     * @param jornada cantidad de horas de una jornada laboral
     * @return
     */
    public static double convertirMinutosEnDias(final long minutos, int jornada) {
        return (minutos / MIN_EN_HORA) / jornada;
    }

    /**
     * Convierte Horas en dias de 8 horas (dia laborable)
     *
     * @param hh
     * @return
     */
    public static double convertirHoraEnDiasLaboral(final long hh) {
        return hh / 8;
    }

    /**
     * Convierte dias y horas en minutos
     *
     * @param dd cantidad de dias
     * @param hh cantidad de horas
     * @return el total de minutos entre los dias y las horas
     */
    public static long convertirDiasLabYHorasEnMinutos(final long dd, final long hh) {
        return hh * 60 + dd * 8 * 60;
    }

    /**
     * Convierte dias en minutos
     *
     * @param dd cantidad de dias
     * @return el total de minutos
     */
    public static long convertirDiasEnMinutos(final double dd) {
        return (long) (dd * 8 * 60);
    }

    /**
     * Convierte los minutos en dias laborables de 8 horas, horas y minutos
     *
     * @param minutos
     * @param jornada
     * @return String del tiempo obtenido
     */
    public static String convertirMinutosA_ddHHmmPalabras(final long minutos, final int jornada) {
        StringBuffer tiempoPalabras = new StringBuffer();
        double horas, resto, dias, hh, mm, dd;

        dias = (minutos / MIN_EN_HORA) / jornada;
        dd = (Double.valueOf(dias)).longValue();
        resto = dias - dd;

        tiempoPalabras = tiempoPalabras.append(Double.valueOf(dd).intValue()).append(" Días ");

        horas = (resto * jornada);
        hh = (Double.valueOf(horas)).longValue();
        resto = horas - hh;
        tiempoPalabras = tiempoPalabras.append(Double.valueOf(hh).intValue()).append(" Horas ");

        mm = Math.round(resto * MIN_EN_HORA);
        tiempoPalabras = tiempoPalabras.append(Double.valueOf(mm).intValue()).append(" Minutos ");
        return tiempoPalabras.toString();
    }

    /**
     * Convierte los minutos en dias laborables de 8 horas, horas y minutos
     *
     * @param minutos
     * @param jornada
     * @return String del tiempo obtenido
     */
    public static Integer[] convertirMinutosA_ddHHmm(final long minutos, int jornada) {
        double horas, resto, dias, hh, mm, dd;
        Integer tiempo[] = new Integer[3];
        dias = (minutos / MIN_EN_HORA) / jornada;
        dd = (Double.valueOf(dias)).longValue();
        tiempo[0] = (int) dd;
        resto = dias - dd;

        horas = (resto * jornada);
        hh = (Double.valueOf(horas)).longValue();
        resto = horas - hh;
        tiempo[1] = (int) hh;

        mm = Math.round(resto * MIN_EN_HORA);
        tiempo[2] = (int) mm;
        return tiempo;
    }

    /**
     * Calcula los anios , meses y dias entre dos fechas
     *
     * @param fechaInicio fecha inicio. Debe ser menor a la fecha fin
     * @param fechaFin fecha fin. Debe ser mayo o igual a la fecha inicio
     * @return arreglo con año,mes, dia
     */
    public static Integer[] calcularAniosMesesDiasEntreFechas(Date fechaInicio, Date fechaFin) {
        Calendar fechaNacimiento = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaNacimiento.setTime(fechaInicio);
        fechaActual.setTime(fechaFin);
        Integer tiempo[] = new Integer[3];
        int aa, mm, dd;

        int ai = fechaNacimiento.get(Calendar.YEAR);
        int af = fechaActual.get(Calendar.YEAR);

        int mi = fechaNacimiento.get(Calendar.MONTH);
        int mf = fechaActual.get(Calendar.MONTH);

        int di = fechaNacimiento.get(Calendar.DATE);
        int df = fechaActual.get(Calendar.DATE);

        if (mf <= mi && df < di) {
            int finMes = fechaNacimiento.getActualMaximum(Calendar.DAY_OF_MONTH);

            dd = finMes - di + df;
            if (mf == mi) {
                mm = (mf + MESES_EN_ANIO) - (mi + 1);
            } else {
                mm = (mf + MESES_EN_ANIO) - mi;
            }
            aa = af - (ai + 1);
        } else if (mf < mi && df > di) {
            dd = df - di;
            mm = (mf + MESES_EN_ANIO) - mi;
            aa = af - (ai + 1);
        } else if (mf > mi && df < di) {
            int finMes = fechaNacimiento.getActualMaximum(Calendar.DAY_OF_MONTH);
            dd = finMes - di + df;
            mm = mf - (mi + 1);
            aa = af - ai;
        } else {
            dd = df - di;
            mm = mf - mi;
            aa = af - ai;
        }

        /* int anioNac =fechaNacimiento.get(Calendar.YEAR);
         int mesNac =fechaNacimiento.get(Calendar.MONTH);
         int diaNac = fechaNacimiento.get(Calendar.DATE);
    

         //Se ajusta el año dependiendo el mes y el día
         if (mes < 0 || (mes == 0 && dia < 0)) {
         año--;
         }*/
        tiempo[0] = aa;
        tiempo[1] = mm;
        tiempo[2] = dd;
        //Regresa la edad en base a la fecha de inicio
        return tiempo;
    }

    /**
     * Calcula el numero de meses entre dos fechas usando la constante de 365.25 dias para el año.
     *
     * @param fechaInicio fecha inicio. Debe ser menor a la fecha fin
     * @param fechaFin fecha fin. Debe ser mayo o igual a la fecha inicio
     * @return numero de meses
     */
    public static Double calculaNumMesesEntreDosFechas(Date fechaInicio, Date fechaFin) {
        return Double.valueOf(calcularDiferenciaDiasEntreFechas(fechaInicio, fechaFin)) / DIAS_AÑO * MESES_EN_ANIO;
    }

    /*
     * Devuelve el número de días que se encuentran en la cantidad enviada.
     *
     * @param a : D-Dias, H-Horas, M-Minutos @param cantidad cantidad de tiempo @return valor en dias
     */
    public static Long convertirEnDiasPorTipoUnidadTiempo(char a, Long cantidad, int jornada) {
        Long numeroDias = 0L;
        double num = 0;
        switch (a) {
            case 'D':
                num = cantidad;
                break;
            case 'H':
                num = cantidad / jornada;
                break;
            case 'M':
                num = cantidad / (UtilFechas.MIN_EN_HORA * jornada);
                break;
        }
        numeroDias = (long) num;
        return numeroDias;
    }

    /*
     * Convierte en minutos una cantidad determinada
     *
     * @param a : D-Dias, H-Horas, M-Minutos @param cantidad @return valor en minutos
     */
    public static Long convertirEnMinutosPorTipoUnidadTiempo(char a, Long cantidad, int jornada) {
        Long numeroMin = 0L;
        double numeroMinutos;
        numeroMinutos = UtilFechas.MIN_EN_HORA;
        switch (a) {
            case 'D':
                numeroMinutos = cantidad * UtilFechas.MIN_EN_HORA * jornada;
                break;
            case 'H':
                numeroMinutos = cantidad * UtilFechas.MIN_EN_HORA;
                break;
            case 'M':
                numeroMinutos = cantidad;
                break;
        }
        numeroMin = (long) numeroMinutos;
        return numeroMin;
    }

    /**
     * Obtiene el promedio diario en minutos q se debe agregar para cargar por fines de semana en vacaciones.
     *
     * @param numeroMinutos
     * @param jornada
     * @return
     */
    public static BigDecimal calcularPromedioDiarioEnMinutosCargoVacacion(Long numeroMinutos, int jornada) {
        BigDecimal proporcionEnDias;
        proporcionEnDias = new BigDecimal(DIAS_FINES_SEMANA_EN_MES_COMERCIAL).divide(new BigDecimal(
                DIAS_LABORABLES_EN_MES_COMERCIAL), 5, RoundingMode.HALF_UP);

        proporcionEnDias = new BigDecimal(numeroMinutos / (UtilFechas.MIN_EN_HORA * jornada)).
                multiply(proporcionEnDias);

        BigDecimal min = proporcionEnDias.multiply(new BigDecimal(UtilFechas.MIN_EN_HORA * jornada));

        return min;
    }

    /**
     * Obtiene el nombre del mes.
     *
     * @param mes
     * @return
     */
    public static String obtenerNombreMes(int mes) {
        String mm = null;
        switch (mes) {
            case 1:
                mm = "Enero";
                break;
            case 2:
                mm = "Febrero";
                break;
            case 3:
                mm = "Marzo";
                break;
            case 4:
                mm = "Abril";
                break;
            case 5:
                mm = "Mayo";
                break;
            case 6:
                mm = "Junio";
                break;
            case 7:
                mm = "Julio";
                break;
            case 8:
                mm = "Agosto";
                break;
            case 9:
                mm = "Septiembre";
                break;
            case 10:
                mm = "Octubre";
                break;
            case 11:
                mm = "Noviembre";
                break;
            case 12:
                mm = "Diciembre";
                break;
        }
        return mm;
    }

    /**
     * Permite convertir una hora de tipo de dato String a tipo Date Creado para tratar valores como : 00:00:00 (1) =>
     * timbres de asistencias
     *
     * @param timbre
     * @return
     */
    public static Date convertirHoraTimbreStringADate(String timbre) {
        if (timbre != null && !timbre.isEmpty()) {
            Date t;
            StringBuilder timbreAux = new StringBuilder(timbre);
            int posicion = timbreAux.lastIndexOf(":");
            timbreAux = new StringBuilder(timbreAux.substring(0, posicion + 3).trim());
            t = UtilFechas.convertirFechaStringEnDate(timbreAux.toString(), UtilFechas.FORMATO_HORA);
            return t;
        } else {
            return null;
        }
    }

    /**
     * Permite convertir una hora de tipo de dato String a tipo Time Creado para tratar valores como : 00:00:00 (1) =>
     * timbres de asistencias
     *
     * @param timbre
     * @return
     */
    public static Time convertirHoraTimbreStringATime(String timbre) {
        Time t = null;
        if (timbre != null && !timbre.isEmpty()) {
            t = new Time(convertirHoraTimbreStringADate(timbre).getTime());
        } else {
            return null;
        }
        return t;
    }

    /**
     * Calcula los dias laborables.
     *
     * @param fechaInicio
     * @param fechafin
     * @param feriados
     * @return
     */
    public static Integer calcularNumeroDiasLaborables(final Date fechaInicio, final Date fechafin,
            final List<Integer> feriados) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(fechaInicio);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(fechafin);
        int workDays = 0;
        while (startCal.getTimeInMillis() < endCal.getTimeInMillis()) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                    && !feriados.contains((Integer) startCal.get(Calendar.DAY_OF_YEAR))) {
                ++workDays;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return workDays;
    }

    /**
     * Determina el total de dias solicitados segun una lista de dias, si se pasa el parametro imputarVacaciones en true
     * cada 5 dias habiles (de lunes a viernes) se sumaran 2 dias adicionales
     *
     * @param fechas
     * @param imputarVacaciones
     * @return
     */
    public static Integer calcularDiasSolicitados(final List<Date> fechas, final boolean imputarVacaciones) {
        Integer dias = 0;
        int c = 0;
        Calendar cal = Calendar.getInstance();
        for (Date d : fechas) {
            cal.setTime(d);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
                c++;
                dias++;
            }
            if (c == 5 && imputarVacaciones) {
                c = 0;
                dias += 2;
            }
        }

        return dias;
    }

    /**
     * Depura una lista de fechas dejando solo las que son dias de semana, o sea de lunes a viernes
     *
     * @param fechas
     * @return
     */
    public static List<Date> excluirFinesDeSemana(final List<Date> fechas) {
        List<Date> l = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (Date d : fechas) {
            cal.setTime(d);
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day != Calendar.SATURDAY && day != Calendar.SUNDAY) {
                l.add(d);
            }
        }
        return l;
    }

    /**
     * Parsea una lista de String que representan fechas a los Date correspondientes
     *
     * @param fechas
     * @param patron
     * @return
     * @throws ParseException
     */
    public static List<Date> parsearLista(final List<String> fechas, final String patron) throws ParseException {
        List<Date> l = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(patron);
        for (String d : fechas) {
            l.add(sdf.parse(d));
        }
        return l;
    }

    /**
     * Formatea una lista de Date a un patron especifico
     *
     * @param fechas
     * @param patron
     * @return
     */
    public static List<String> formatearLista(final List<Date> fechas, final String patron) {
        List<String> l = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(patron);
        for (Date d : fechas) {
            l.add(sdf.format(d));
        }
        return l;
    }

    /**
     * Convertir una Lista de Date a CSV en un patron dado
     *
     * @param fechas
     * @param patron
     * @return
     */
    public static String convertirCSV(final List<Date> fechas, final String patron) {
        StringBuilder sb = new StringBuilder();
        List<String> asString = formatearLista(fechas, patron);
        for (int i = 0; i < asString.size(); i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(asString.get(i));
        }
        return sb.toString();
    }

    /**
     * Calcula el total de minutos involucrados en la lista de fechas segun la jornada del servidor
     *
     * @param fechas
     * @param jornada
     * @return
     */
    public static Long calcularTotalMinutos(final List<Date> fechas, final int jornada) {
        Long minutos = 0L;
        for (Date d : fechas) {
            minutos += (long) (jornada * MIN_EN_HORA);
        }
        return minutos;
    }
}
