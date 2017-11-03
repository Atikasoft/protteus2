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

public class KURT
extends ForEachNumberFunction {
    public KURT() {
        this.setIterations(3);
    }

    @Override
    protected void value(Counter counter, double value) {
        switch (counter.iteration) {
            case 1: {
                this.average(counter, value);
                break;
            }
            case 2: {
                this.stdev(counter, value);
                break;
            }
            case 3: {
                this.kurt(counter, value);
            }
        }
    }

    @Override
    protected void iteration(Counter counter) {
        switch (counter.iteration) {
            case 2: {
                if (counter.count < 4) {
                    counter.doit = false;
                    counter.result = ExprError.DIV0;
                }
                counter.value /= (double)counter.count;
                break;
            }
            case 3: {
                counter.value2 /= (double)(counter.count - 1);
                counter.value2 = Math.sqrt(counter.value2);
                if (counter.value2 != 0.0) break;
                counter.doit = false;
                counter.result = ExprError.DIV0;
            }
        }
    }

    private void kurt(Counter counter, double value) {
        counter.value3 += Math.pow((value - counter.value) / counter.value2, 4.0);
    }

    private void stdev(Counter counter, double value) {
        counter.value2 += Math.pow(value - counter.value, 2.0);
    }

    private void average(Counter counter, double value) {
        ++counter.count;
        counter.value += value;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        double n = counter.count;
        double kurt = n * (n + 1.0) / ((n - 1.0) * (n - 2.0) * (n - 3.0)) * counter.value3 - 3.0 * Math.pow(n - 1.0, 2.0) / ((n - 2.0) * (n - 3.0));
        return new ExprDouble(kurt);
    }
}

