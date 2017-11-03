/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.GridReference;
import ec.com.atikasoft.proteus.expr.engine.Range;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class COLUMNS
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        return COLUMNS.columnsOrRows(context, args, true);
    }

    public static Expr columnsOrRows(IEvaluationContext context, Expr[] args, boolean cols) throws ExprException {
        if (args[0] instanceof ExprVariable) {
            ExprVariable v = (ExprVariable)args[0];
            Range r = (Range)v.getAnnotation();
            if (r == null) {
                r = Range.valueOf(v.getName());
            }
            if (r != null && r.getDimension1() != null) {
                GridReference dim1 = r.getDimension1();
                GridReference dim2 = r.getDimension2();
                if (dim2 == null) {
                    return new ExprInteger(1);
                }
                if (cols) {
                    return new ExprInteger(Math.abs(dim2.getColumn() - dim1.getColumn()) + 1);
                }
                return new ExprInteger(Math.abs(dim2.getRow() - dim1.getRow()) + 1);
            }
            return ExprError.NAME;
        }
        Expr a = args[0];
        if (a instanceof ExprEvaluatable) {
            a = ((ExprEvaluatable)a).evaluate(context);
        }
        if (a instanceof ExprInteger || a instanceof ExprDouble) {
            return new ExprInteger(1);
        }
        if (a instanceof ExprError) {
            return a;
        }
        return ExprError.VALUE;
    }
}

