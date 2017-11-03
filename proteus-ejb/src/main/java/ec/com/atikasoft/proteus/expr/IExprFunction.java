/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public interface IExprFunction {
    public Expr evaluate(IEvaluationContext var1, Expr[] var2) throws ExprException;

    public boolean isVolatile();
}

