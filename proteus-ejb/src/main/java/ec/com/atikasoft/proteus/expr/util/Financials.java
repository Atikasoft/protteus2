/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import ec.com.atikasoft.proteus.expr.util.Maths;

public class Financials {
    public static double db(double cost, double salvage, int life, int period, int month) {
        double rate = 1.0 - Math.pow(salvage / cost, 1.0 / (double)life);
        rate = Maths.round(rate, 3);
        double dep = 0.0;
        double total = 0.0;
        for (int i = 0; i < period; ++i) {
            if (i == 0) {
                total = dep = cost * rate * (double)month / 12.0;
                continue;
            }
            if (i == life) {
                dep = (cost - total) * rate * (double)(12 - month) / 12.0;
                continue;
            }
            dep = (cost - total) * rate;
            total += dep;
        }
        return dep;
    }

    public static double ddb(double cost, double salvage, int life, int period, double factor) {
        double dep = 0.0;
        double total = 0.0;
        double rate = factor / (double)life;
        for (int i = 0; i < period; ++i) {
            dep = (cost - salvage - total) * rate;
            total += dep;
        }
        return dep;
    }

    public static double vdb(double cost, double salvage, int life, double start_period, double end_period, double factor, boolean no_switch) {
        return 0.0;
    }
}

