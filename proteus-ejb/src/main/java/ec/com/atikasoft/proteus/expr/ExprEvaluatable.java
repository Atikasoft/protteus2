/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public abstract class ExprEvaluatable
extends Expr {
    ExprEvaluatable(ExprType type) {
        super(type, true);
    }

    @Override
    public boolean isVolatile() {
        return true;
    }

    public abstract Expr evaluate(IEvaluationContext var1) throws ExprException;
}

