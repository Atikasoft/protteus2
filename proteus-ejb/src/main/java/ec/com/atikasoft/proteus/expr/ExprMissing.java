/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprMissing
extends Expr {
    public static final ExprMissing MISSING = new ExprMissing();

    public ExprMissing() {
        super(ExprType.Missing, false);
    }

    public String toString() {
        return "";
    }
}

