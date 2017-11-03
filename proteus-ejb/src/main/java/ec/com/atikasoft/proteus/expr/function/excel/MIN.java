/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class MIN
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        return MIN.min(context, args);
    }

    public static Expr min(IEvaluationContext context, Expr[] args) throws ExprException {
        double d = Double.MAX_VALUE;
        for (Expr a : args) {
            Expr res = MIN.min(context, a);
            if (res instanceof ExprError) {
                return res;
            }
            double r = ((ExprDouble)res).doubleValue();
            if (r >= d) continue;
            d = r;
        }
        return new ExprDouble(d);
    }

    public static Expr min(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return new ExprDouble(((ExprNumber)arg).doubleValue());
        }
        if (arg instanceof ExprArray) {
            return MIN.min(context, ((ExprArray)arg).getInternalArray());
        }
        if (arg instanceof ExprError) {
            return arg;
        }
        return ExprError.VALUE;
    }
}

