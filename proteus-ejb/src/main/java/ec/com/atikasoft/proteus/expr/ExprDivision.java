/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.AbstractMathematicalOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprDivision
extends AbstractMathematicalOperator {
    public ExprDivision(Expr lhs, Expr rhs) {
        super(ExprType.Division, lhs, rhs);
    }

    @Override
    protected Expr evaluate(double lhs, double rhs) throws ExprException {
        if (rhs == 0.0) {
            return ExprError.DIV0;
        }
        return new ExprDouble(lhs / rhs);
    }

    public String toString() {
        return this.lhs + "/" + this.rhs;
    }
}

