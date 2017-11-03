/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractMathematicalOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprAddition
extends AbstractMathematicalOperator {
    public ExprAddition(Expr lhs, Expr rhs) {
        super(ExprType.Addition, lhs, rhs);
    }

    @Override
    protected Expr evaluate(double lhs, double rhs) throws ExprException {
        return new ExprDouble(lhs + rhs);
    }

    @Override
    public void validate() throws ExprException {
        if (this.lhs != null) {
            this.lhs.validate();
        }
        if (this.rhs == null) {
            throw new ExprException("RHS of operator missing");
        }
        this.rhs.validate();
    }

    public String toString() {
        if (this.lhs == null) {
            return this.rhs.toString();
        }
        return this.lhs + "+" + this.rhs;
    }
}

