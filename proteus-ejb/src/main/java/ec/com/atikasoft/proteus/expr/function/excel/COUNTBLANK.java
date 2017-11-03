/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class COUNTBLANK
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        int count = 0;
        for (Expr a : args) {
            count += COUNTBLANK.count(context, a);
        }
        return new ExprInteger(count);
    }

    public static int count(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprMissing) {
            return 1;
        }
        if (arg instanceof ExprArray) {
            int count = 0;
            ExprArray arr = (ExprArray)arg;
            int rows = arr.rows();
            int cols = arr.columns();
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    count += COUNTBLANK.count(context, arr.get(i, j));
                }
            }
            return count;
        }
        return 0;
    }
}

