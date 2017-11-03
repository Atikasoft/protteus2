/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractBinaryOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public class ExprStringConcat
extends AbstractBinaryOperator {
    public ExprStringConcat(Expr lhs, Expr rhs) {
        super(ExprType.StringConcat, lhs, rhs);
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        Expr r;
        Expr l = this.lhs;
        if (l instanceof ExprEvaluatable) {
            l = ((ExprEvaluatable)this.lhs).evaluate(context);
        }
        if (l instanceof ExprNumber) {
            l = new ExprString(l.toString());
        }
        if ((r = this.rhs) instanceof ExprEvaluatable) {
            r = ((ExprEvaluatable)this.rhs).evaluate(context);
        }
        if (r instanceof ExprNumber) {
            r = new ExprString(r.toString());
        }
        if (l.type.equals((Object)ExprType.String) && r.type.equals((Object)ExprType.String)) {
            return new ExprString(((ExprString)l).str + ((ExprString)r).str);
        }
        throw new ExprException("Unexpected arguments for string concatenation");
    }

    public String toString() {
        return this.lhs + "&" + this.rhs;
    }
}

