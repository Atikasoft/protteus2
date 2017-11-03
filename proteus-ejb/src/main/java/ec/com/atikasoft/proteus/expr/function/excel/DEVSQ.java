/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.ForEachNumberFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class DEVSQ
extends ForEachNumberFunction {
    public DEVSQ() {
        this.setIterations(2);
    }

    @Override
    protected void value(Counter counter, double value) {
        switch (counter.iteration) {
            case 1: {
                this.avg(counter, value);
                break;
            }
            case 2: {
                if (!counter.flag) {
                    counter.value /= (double)counter.count;
                    counter.flag = true;
                }
                this.devsq(counter, value);
            }
        }
    }

    private void devsq(Counter counter, double value) {
        counter.value2 += Math.pow(value - counter.value, 2.0);
    }

    private void avg(Counter counter, double value) {
        counter.value += value;
        ++counter.count;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        return new ExprDouble(counter.value2);
    }
}

