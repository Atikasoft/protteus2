/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class TRANSPOSE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr e = TRANSPOSE.evalArg(context, args[0]);
        if (e instanceof ExprArray) {
            return TRANSPOSE.transpose((ExprArray)e);
        }
        return e;
    }

    public static Expr transpose(ExprArray array) throws ExprException {
        int rows = array.columns();
        int cols = array.rows();
        ExprArray a = new ExprArray(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                a.set(i, j, array.get(j, i));
            }
        }
        return a;
    }
}

