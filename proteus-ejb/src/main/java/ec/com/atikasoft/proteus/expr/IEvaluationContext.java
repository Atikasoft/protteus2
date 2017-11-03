/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

public interface IEvaluationContext {
    public Expr evaluateFunction(ExprFunction var1) throws ExprException;

    public Expr evaluateVariable(ExprVariable var1) throws ExprException;
}

