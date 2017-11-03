/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.ForEachNumberFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class HARMEAN
extends ForEachNumberFunction {
    @Override
    protected void value(Counter counter, double value) {
        if (value <= 0.0) {
            counter.doit = false;
            counter.result = ExprError.NUM;
            return;
        }
        ++counter.count;
        counter.value += 1.0 / value;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        if (counter.result != null) {
            return counter.result;
        }
        return new ExprDouble(1.0 / (counter.value / (double)counter.count));
    }
}

