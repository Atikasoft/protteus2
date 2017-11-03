/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class COUNT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        int count = 0;
        for (Expr a : args) {
            count += COUNT.count(context, a, false);
        }
        return new ExprInteger(count);
    }

    public static int count(IEvaluationContext context, Expr arg, boolean any) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprDouble || arg instanceof ExprInteger) {
            return 1;
        }
        if (arg instanceof ExprArray) {
            int count = 0;
            ExprArray arr = (ExprArray)arg;
            int rows = arr.rows();
            int cols = arr.columns();
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    count += COUNT.count(context, arr.get(i, j), any);
                }
            }
            return count;
        }
        if (any && (arg instanceof ExprString || arg instanceof ExprBoolean || arg instanceof ExprError)) {
            return 1;
        }
        return 0;
    }
}

