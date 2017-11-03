/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Counter;

public abstract class ForEachFunction
extends AbstractFunction {
    protected int iterations = 1;

    protected void setIterations(int iterations) {
        this.iterations = iterations;
    }

    @Override
    public final Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        Counter c = new Counter();
        this.initialize(c);
        block0 : for (int i = 0; i < this.iterations; ++i) {
            c.iteration = i + 1;
            this.iteration(c);
            for (Expr e : args) {
                this.evalItem(context, e, c);
                if (!c.doit) break block0;
            }
        }
        return this.evaluate(c);
    }

    private void evalItem(IEvaluationContext context, Expr e, Counter c) throws ExprException {
        if (e instanceof ExprEvaluatable) {
            e = ((ExprEvaluatable)e).evaluate(context);
        }
        if (e instanceof ExprArray) {
            Expr[] ai;
            ExprArray a = (ExprArray)e;
            for (Expr aie : ai = a.getInternalArray()) {
                this.evalItem(context, aie, c);
                if (c.doit) {
                    continue;
                }
                break;
            }
        } else {
            this.value(c, e);
        }
    }

    protected abstract void initialize(Counter var1) throws ExprException;

    protected abstract void iteration(Counter var1);

    protected abstract void value(Counter var1, Expr var2) throws ExprException;

    protected abstract Expr evaluate(Counter var1) throws ExprException;
}

