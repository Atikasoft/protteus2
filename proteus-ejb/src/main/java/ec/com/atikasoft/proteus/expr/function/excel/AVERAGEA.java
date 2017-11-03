/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.ForEachNumberAFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class AVERAGEA
extends ForEachNumberAFunction {
    @Override
    protected void value(Counter counter, double value) {
        ++counter.count;
        counter.value += value;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        return new ExprDouble(counter.value / (double)counter.count);
    }
}

