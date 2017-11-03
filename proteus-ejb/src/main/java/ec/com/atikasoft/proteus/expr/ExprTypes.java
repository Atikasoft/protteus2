/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprTypes {
    public static /* varargs */ void assertType(Expr value, ExprType ... types) throws ExprException {
        for (ExprType t : types) {
            if (!t.equals((Object)value.type)) continue;
            return;
        }
        throw new ExprException("Unexpected type: " + (Object)((Object)value.type));
    }
}

