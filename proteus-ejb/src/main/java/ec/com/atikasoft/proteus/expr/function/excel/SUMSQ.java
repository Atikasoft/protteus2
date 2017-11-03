/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class SUMSQ
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        double res = 0.0;
        for (Expr arg : args) {
            res += this.sumsq(context, arg);
        }
        return new ExprDouble(res);
    }

    private double sumsq(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return Math.pow(((ExprNumber)arg).doubleValue(), 2.0);
        }
        if (arg instanceof ExprArray) {
            ExprArray a = (ExprArray)arg;
            int rows = a.rows();
            int cols = a.columns();
            double res = 0.0;
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    res += this.sumsq(context, a.get(i, j));
                }
            }
            return res;
        }
        return 0.0;
    }
}

