/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractComparisonOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public class ExprLessThanOrEqualTo
extends AbstractComparisonOperator {
    public ExprLessThanOrEqualTo(Expr lhs, Expr rhs) {
        super(ExprType.LessThanOrEqualTo, lhs, rhs);
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        return this.bool(this.compare(context) <= 0.0);
    }

    public String toString() {
        return this.lhs + "<=" + this.rhs;
    }
}

