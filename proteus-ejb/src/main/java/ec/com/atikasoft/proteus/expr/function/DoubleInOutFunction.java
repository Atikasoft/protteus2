/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public abstract class DoubleInOutFunction
extends AbstractFunction {
    @Override
    public final Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        return new ExprDouble(this.evaluate(this.asDouble(context, args[0], true)));
    }

    protected abstract double evaluate(double var1) throws ExprException;
}

