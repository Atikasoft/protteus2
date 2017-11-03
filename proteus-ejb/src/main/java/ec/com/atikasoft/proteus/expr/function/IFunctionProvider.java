/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public interface IFunctionProvider {
    public boolean hasFunction(ExprFunction var1);

    public Expr evaluate(IEvaluationContext var1, ExprFunction var2) throws ExprException;
}

