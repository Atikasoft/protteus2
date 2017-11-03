/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractMathematicalOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprPower
extends AbstractMathematicalOperator {
    public ExprPower(Expr lhs, Expr rhs) {
        super(ExprType.Power, lhs, rhs);
    }

    @Override
    protected Expr evaluate(double lhs, double rhs) throws ExprException {
        return new ExprDouble(Math.pow(lhs, rhs));
    }

    public String toString() {
        return this.lhs + "^" + this.rhs;
    }
}

