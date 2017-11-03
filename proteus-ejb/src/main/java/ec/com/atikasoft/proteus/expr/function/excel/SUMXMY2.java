/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.function.excel.SUMX2MY2;

public class SUMXMY2
extends SUMX2MY2 {
    @Override
    protected double eval(double x, double y) {
        return Math.pow(x - y, 2.0);
    }
}

