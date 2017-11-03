/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunction;

public class ODD
extends DoubleInOutFunction {
    @Override
    protected double evaluate(double value) throws ExprException {
        double res = (value + 1.0) % 2.0;
        if (res < 0.0) {
            return value - 2.0 - res;
        }
        if (res == 0.0) {
            return value;
        }
        return value + 2.0 - res;
    }
}

