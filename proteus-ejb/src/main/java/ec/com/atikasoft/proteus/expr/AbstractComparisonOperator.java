/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractBinaryOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public abstract class AbstractComparisonOperator
extends AbstractBinaryOperator {
    public AbstractComparisonOperator(ExprType type, Expr lhs, Expr rhs) {
        super(type, lhs, rhs);
    }

    protected double compare(IEvaluationContext context) throws ExprException {
        Expr l = this.eval(this.lhs, context);
        Expr r = this.eval(this.rhs, context);
        if (l instanceof ExprString || r instanceof ExprString) {
            return l.toString().compareTo(r.toString());
        }
        if (l instanceof ExprNumber && r instanceof ExprNumber) {
            return ((ExprNumber)l).doubleValue() - ((ExprNumber)r).doubleValue();
        }
        return 0.0;
    }
}

