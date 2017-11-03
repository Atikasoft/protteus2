/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.excel.STDEV;

public class STDEVP
extends STDEV {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        return STDEVP.stdevp(context, args);
    }

    public static Expr stdevp(IEvaluationContext context, Expr[] args) throws ExprException {
        return STDEVP.stdev(context, args, true);
    }
}

