/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public abstract class DoubleInOutFunctionErr
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr a = DoubleInOutFunctionErr.evalArg(context, args[0]);
        if (a instanceof ExprError) {
            return a;
        }
        if (!this.isNumber(a)) {
            return ExprError.VALUE;
        }
        return new ExprDouble(this.evaluate(((ExprNumber)a).doubleValue()));
    }

    protected abstract double evaluate(double var1) throws ExprException;
}

