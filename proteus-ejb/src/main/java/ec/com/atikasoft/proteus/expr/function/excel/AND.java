/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class AND
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        if (args.length == 0) {
            throw new ExprException("AND function requires at least one argument");
        }
        for (Expr a : args) {
            if (this.eval(context, a, true)) continue;
            return ExprBoolean.FALSE;
        }
        return ExprBoolean.TRUE;
    }

    protected boolean eval(IEvaluationContext context, Expr a, boolean strict) throws ExprException {
        if (a == null) {
            return true;
        }
        if (a instanceof ExprEvaluatable) {
            a = ((ExprEvaluatable)a).evaluate(context);
        }
        if (a instanceof ExprNumber) {
            return ((ExprNumber)a).booleanValue();
        }
        if (a instanceof ExprMissing) {
            return true;
        }
        if (a instanceof ExprArray) {
            ExprArray arr = (ExprArray)a;
            int rows = arr.rows();
            int cols = arr.columns();
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    if (this.eval(context, arr.get(i, j), false)) continue;
                    return false;
                }
            }
        }
        if (strict) {
            throw new ExprException("Unexpected argument to " + this.getClass().getSimpleName() + ": " + a);
        }
        return false;
    }
}

