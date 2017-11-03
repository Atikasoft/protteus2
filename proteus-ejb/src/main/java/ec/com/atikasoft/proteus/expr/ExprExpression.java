/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public class ExprExpression
extends ExprEvaluatable {
    private Expr child;

    public ExprExpression(Expr child) {
        super(ExprType.Expression);
        this.child = child;
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        if (this.child instanceof ExprEvaluatable) {
            return ((ExprEvaluatable)this.child).evaluate(context);
        }
        return this.child;
    }

    public Expr getChild() {
        return this.child;
    }

    public String toString() {
        return "(" + this.child + ")";
    }

    @Override
    public void validate() throws ExprException {
        this.child.validate();
    }
}

