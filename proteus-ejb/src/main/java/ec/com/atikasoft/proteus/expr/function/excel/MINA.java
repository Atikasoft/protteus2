/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.ForEachNumberAFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class MINA
extends ForEachNumberAFunction {
    @Override
    protected void initialize(Counter counter) throws ExprException {
        counter.value = Double.MAX_VALUE;
    }

    @Override
    protected void value(Counter counter, double value) {
        if (value < counter.value) {
            counter.value = value;
        }
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        return new ExprDouble(counter.value);
    }
}

