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

public class PRODUCT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        return PRODUCT.product(context, args);
    }

    public static Expr product(IEvaluationContext context, Expr[] args) throws ExprException {
        double res = 1.0;
        for (Expr arg : args) {
            res *= PRODUCT.product(context, arg);
        }
        return new ExprDouble(res);
    }

    public static double product(IEvaluationContext context, Expr arg) throws ExprException {
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
            double res = 1.0;
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    res *= PRODUCT.product(context, a.get(i, j));
                }
            }
            return res;
        }
        return 1.0;
    }
}

