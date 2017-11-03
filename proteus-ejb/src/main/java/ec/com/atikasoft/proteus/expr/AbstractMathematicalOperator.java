/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractBinaryOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.ExprTypes;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public abstract class AbstractMathematicalOperator
extends AbstractBinaryOperator {
    public AbstractMathematicalOperator(ExprType type, Expr lhs, Expr rhs) {
        super(type, lhs, rhs);
    }

    protected double evaluateExpr(Expr e, IEvaluationContext context) throws ExprException {
        if ((e = this.eval(e, context)) == null) {
            return 0.0;
        }
        if (e instanceof ExprMissing) {
            return 0.0;
        }
        ExprTypes.assertType(e, ExprType.Integer, ExprType.Double, ExprType.Boolean);
        if (e.type.equals((Object)ExprType.Boolean)) {
            return 0.0;
        }
        return ((ExprNumber)e).doubleValue();
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        Expr l = this.eval(this.lhs, context);
        if (l instanceof ExprError) {
            return l;
        }
        Expr r = this.eval(this.rhs, context);
        if (r instanceof ExprError) {
            return r;
        }
        return this.evaluate(this.evaluateExpr(l, context), this.evaluateExpr(r, context));
    }

    protected abstract Expr evaluate(double var1, double var3) throws ExprException;
}

