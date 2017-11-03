/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.GridReference;
import ec.com.atikasoft.proteus.expr.engine.Range;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class ROW
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        if (args[0] instanceof ExprVariable) {
            ExprVariable v = (ExprVariable)args[0];
            Range r = (Range)v.getAnnotation();
            if (r == null) {
                r = Range.valueOf(v.getName());
            }
            if (r != null && r.getDimension1() != null) {
                return new ExprInteger(r.getDimension1().getRow());
            }
            return ExprError.NAME;
        }
        throw new ExprException("Invalid argument for function ROW");
    }
}

