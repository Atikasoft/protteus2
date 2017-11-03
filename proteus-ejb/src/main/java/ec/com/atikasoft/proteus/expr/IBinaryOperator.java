/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;

public interface IBinaryOperator {
    public Expr getLHS();

    public void setLHS(Expr var1);

    public Expr getRHS();

    public void setRHS(Expr var1);
}

