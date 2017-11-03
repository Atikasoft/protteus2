/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExcelDate {

    public static final double MS_IN_DAY = 8.64E7;

    public static long toJavaDate(double value) {
        GregorianCalendar c = new GregorianCalendar();
        long days = Math.round(Math.floor(value));
        long millis = Math.round(8.64E7 * (value - (double) days));
        c.setLenient(true);
        c.set(1900, 0, 0, 0, 0, 0);
        c.set(6, (int) days - 1);
        c.set(14, (int) millis);
        return c.getTimeInMillis();
    }

    public static double toExcelDate(long millis) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(millis);
        int year = c.get(1);
        int days = (year - 1601) * 365 - 109207;
        days += (year -= 1601) / 4;
        days -= year / 100;
        days += year / 400;
        double m = c.get(11) * 60;
        m += (double) c.get(12);
        m *= 60.0;
        m += (double) c.get(13);
        m *= 1000.0;
        m += (double) c.get(14);
        return (double) (days += c.get(6) + 1) + (m /= 8.64E7);
    }

    private static int get(double value, int field) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(ExcelDate.toJavaDate(value));
        //c.setTimeInMillis((long) value);
        return c.get(field);
    }

    public static int getDayOfMonth(double value) {
        return ExcelDate.get(value, 5);
    }

    public static int getMonth(double value) {
        return ExcelDate.get(value, 2) + 1;
    }

    public static int getYear(double value) {
        return ExcelDate.get(value, Calendar.YEAR);
    }

    public static int getWeekday(double value) {
        return ExcelDate.get(value, 7);
    }

    public static int getHour(double value) {
        double h = value - Math.floor(value);
        h *= 24.0;
        h = Math.floor(h);
        return (int) h;
    }

    public static int getMinute(double value) {
        double m = value - Math.floor(value);
        m *= 24.0;
        m -= Math.floor(m);
        m *= 60.0;
        m = Math.floor(m);
        return (int) m;
    }

    public static int getSecond(double value) {
        double d = (value - Math.floor(value)) * 1440.0;
        d -= Math.floor(d);
        int s = (int) Math.round(d * 60.0);
        return s;
    }

    public static double date(double y, double m, double d) {
        GregorianCalendar c = new GregorianCalendar();
        c.set((int) (y %= 24.0), (int) m, (int) d, 0, 0, 0);
        double t = ExcelDate.toExcelDate(c.getTimeInMillis());
        if (t > 10000.0) {
            return -1.0;
        }
        return t;
    }

    public static double time(double h, double m, double s) {
        double t = ((h %= 24.0) + m + s) / 8.64E7;
        if (t < 0.0 || t >= 1.0) {
            return -1.0;
        }
        return t;
    }
}
