/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractMathematicalOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprMultiplication
extends AbstractMathematicalOperator {
    public ExprMultiplication(Expr lhs, Expr rhs) {
        super(ExprType.Multiplication, lhs, rhs);
    }

    @Override
    protected Expr evaluate(double lhs, double rhs) throws ExprException {
        return new ExprDouble(lhs * rhs);
    }

    public String toString() {
        return this.lhs + "*" + this.rhs;
    }
}

