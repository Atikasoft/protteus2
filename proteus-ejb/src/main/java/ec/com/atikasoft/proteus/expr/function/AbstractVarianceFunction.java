/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.function.ForEachFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public class AbstractVarianceFunction
extends ForEachFunction {
    private final boolean includeLogical;
    private final boolean allPopulation;

    public AbstractVarianceFunction(boolean includeLogical, boolean allPopulation) {
        this.includeLogical = includeLogical;
        this.allPopulation = allPopulation;
        this.setIterations(2);
    }

    @Override
    protected void iteration(Counter c) {
        switch (c.iteration) {
            case 2: {
                c.value /= (double)c.count;
            }
        }
    }

    protected void value(Counter counter, double value) {
        switch (counter.iteration) {
            case 1: {
                this.average(counter, value);
                break;
            }
            case 2: {
                this.var(counter, value);
            }
        }
    }

    private void var(Counter counter, double value) {
        counter.value2 += Math.pow(value - counter.value, 2.0);
    }

    private void average(Counter counter, double value) {
        counter.value += value;
        ++counter.count;
    }

    @Override
    protected Expr evaluate(Counter counter) throws ExprException {
        return new ExprDouble(counter.value2 / (double)(counter.count - (this.allPopulation ? 0 : 1)));
    }

    @Override
    protected void initialize(Counter counter) throws ExprException {
    }

    @Override
    protected void value(Counter counter, Expr value) throws ExprException {
        if (this.includeLogical) {
            if (value instanceof ExprNumber) {
                this.value(counter, ((ExprNumber)value).doubleValue());
            } else if (value instanceof ExprString) {
                this.value(counter, 0.0);
            }
        } else if (value instanceof ExprDouble || value instanceof ExprInteger) {
            this.value(counter, ((ExprNumber)value).doubleValue());
        }
    }
}

