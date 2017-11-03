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

public class SUM
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        return SUM.sum(context, args);
    }

    public static Expr sum(IEvaluationContext context, Expr[] args) throws ExprException {
        double res = 0.0;
        for (Expr arg : args) {
            res += SUM.sum(context, arg);
        }
        return new ExprDouble(res);
    }

    public static double sum(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return ((ExprNumber)arg).doubleValue();
        }
        if (arg instanceof ExprArray) {
            ExprArray a = (ExprArray)arg;
            int rows = a.rows();
            int cols = a.columns();
            double res = 0.0;
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    res += SUM.sum(context, a.get(i, j));
                }
            }
            return res;
        }
        return 0.0;
    }
}

