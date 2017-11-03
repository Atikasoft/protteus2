/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.function.ForEachFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public abstract class ForEachNumberFunction
extends ForEachFunction {
    @Override
    protected void initialize(Counter counter) throws ExprException {
    }

    @Override
    protected void iteration(Counter counter) {
    }

    @Override
    protected final void value(Counter counter, Expr value) throws ExprException {
        if (value instanceof ExprInteger || value instanceof ExprDouble) {
            this.value(counter, ((ExprNumber)value).doubleValue());
        }
    }

    protected abstract void value(Counter var1, double var2);
}

