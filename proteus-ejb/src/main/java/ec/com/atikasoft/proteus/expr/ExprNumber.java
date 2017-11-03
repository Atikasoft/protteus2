/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public abstract class ExprNumber
extends Expr {
    ExprNumber(ExprType type) {
        super(type, false);
    }

    @Override
    public void validate() throws ExprException {
    }

    public boolean booleanValue() {
        return this.intValue() != 0;
    }

    public abstract int intValue();

    public abstract double doubleValue();
}

