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
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class AVERAGE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        return AVERAGE.average(context, args);
    }

    public static /* varargs */ Expr average(IEvaluationContext context, Expr ... args) throws ExprException {
        double[] values = new double[]{0.0, 0.0};
        for (Expr a : args) {
            AVERAGE.eval(context, a, values, true);
        }
        if (values[1] == 0.0) {
            return ExprError.DIV0;
        }
        return new ExprDouble(values[0] / values[1]);
    }

    public static void eval(IEvaluationContext context, Expr a, double[] values, boolean strict) throws ExprException {
        if (a instanceof ExprEvaluatable) {
            a = ((ExprEvaluatable)a).evaluate(context);
        }
        if (a == null) {
            return;
        }
        if (a instanceof ExprMissing) {
            return;
        }
        if (a instanceof ExprString) {
            if (strict) {
                throw new ExprException("Unexpected argument for AVERAGE: " + a);
            }
            return;
        }
        if (a instanceof ExprDouble || a instanceof ExprInteger) {
            double d = ((ExprNumber)a).doubleValue();
            double[] arrd = values;
            arrd[0] = arrd[0] + d;
            double[] arrd2 = values;
            arrd2[1] = arrd2[1] + 1.0;
            return;
        }
        if (a instanceof ExprArray) {
            ExprArray arr = (ExprArray)a;
            int rows = arr.rows();
            int cols = arr.columns();
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    AVERAGE.eval(context, arr.get(i, j), values, false);
                }
            }
            return;
        }
        throw new ExprException("Unexpected argument for AVERAGE: " + a);
    }
}

