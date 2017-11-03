/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunction;
import ec.com.atikasoft.proteus.expr.util.Maths;

public class ASINH
extends DoubleInOutFunction {
    @Override
    protected double evaluate(double value) throws ExprException {
        return Maths.asinh(value);
    }
}

