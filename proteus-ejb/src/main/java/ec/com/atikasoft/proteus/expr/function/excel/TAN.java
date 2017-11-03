/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunction;

public class TAN
extends DoubleInOutFunction {
    @Override
    protected double evaluate(double value) throws ExprException {
        return Math.tan(value);
    }
}

