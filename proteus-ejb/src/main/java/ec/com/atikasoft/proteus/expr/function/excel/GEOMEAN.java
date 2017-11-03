/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.ForEachNumberFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class GEOMEAN
extends ForEachNumberFunction {
    @Override
    protected void initialize(Counter counter) throws ExprException {
        counter.value = 1.0;
    }

    @Override
    protected void value(Counter counter, double value) {
        ++counter.count;
        counter.value *= value;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        return new ExprDouble(Math.pow(counter.value, 1.0 / (double)counter.count));
    }
}

