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

public class MAX
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        return MAX.max(context, args);
    }

    public static Expr max(IEvaluationContext context, Expr[] args) throws ExprException {
        double d = -1.7976931348623157E308;
        for (Expr a : args) {
            Expr res = MAX.max(context, a);
            if (res instanceof ExprError) {
                return res;
            }
            double r = ((ExprDouble)res).doubleValue();
            if (r <= d) continue;
            d = r;
        }
        return new ExprDouble(d);
    }

    public static Expr max(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return new ExprDouble(((ExprNumber)arg).doubleValue());
        }
        if (arg instanceof ExprArray) {
            return MAX.max(context, ((ExprArray)arg).getInternalArray());
        }
        if (arg instanceof ExprError) {
            return arg;
        }
        return ExprError.VALUE;
    }
}

