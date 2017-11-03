/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunction;

public class SIGN
extends DoubleInOutFunction {
    @Override
    protected double evaluate(double value) throws ExprException {
        if (value < 0.0) {
            return -1.0;
        }
        if (value > 0.0) {
            return 1.0;
        }
        return 0.0;
    }
}

