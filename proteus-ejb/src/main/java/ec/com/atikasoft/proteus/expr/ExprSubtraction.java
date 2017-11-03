/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractMathematicalOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprSubtraction
extends AbstractMathematicalOperator {
    public ExprSubtraction(Expr lhs, Expr rhs) {
        super(ExprType.Subtraction, lhs, rhs);
    }

    @Override
    protected Expr evaluate(double lhs, double rhs) throws ExprException {
        return new ExprDouble(lhs - rhs);
    }

    @Override
    public void validate() throws ExprException {
        if (this.rhs == null) {
            throw new ExprException("RHS of operator missing");
        }
    }

    public String toString() {
        if (this.lhs == null) {
            return "-" + this.rhs;
        }
        return this.lhs + "-" + this.rhs;
    }
}

