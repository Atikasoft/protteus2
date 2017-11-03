/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

public class Maths {
    public static double acosh(double value) {
        return Math.log(value + Math.sqrt(Math.pow(value, 2.0) - 1.0));
    }

    public static double asinh(double value) {
        return Math.log(value + Math.sqrt(Math.pow(value, 2.0) + 1.0));
    }

    public static double atanh(double value) {
        return 0.5 * Math.log((1.0 + value) / (1.0 - value));
    }

    public static double round(double value, int dps) {
        boolean n;
        boolean bl = n = value < 0.0;
        if (n) {
            value *= -1.0;
        }
        double r = (double)Math.round(value * Math.pow(10.0, dps)) / Math.pow(10.0, dps);
        if (n) {
            r *= -1.0;
        }
        return r;
    }

    public static double roundDown(double value, int dps) {
        boolean n;
        boolean bl = n = value < 0.0;
        if (n) {
            value *= -1.0;
        }
        double r = Math.floor(value * Math.pow(10.0, dps)) / Math.pow(10.0, dps);
        if (n) {
            r *= -1.0;
        }
        return r;
    }

    public static double roundUp(double value, int dps) {
        boolean n;
        boolean bl = n = value < 0.0;
        if (n) {
            value *= -1.0;
        }
        double r = Math.ceil(value * Math.pow(10.0, dps)) / Math.pow(10.0, dps);
        if (n) {
            r *= -1.0;
        }
        return r;
    }

    public static double log(double num, double base) {
        return Math.log(num) / Math.log(base);
    }
}

