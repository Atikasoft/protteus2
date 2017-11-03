/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.engine;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.Range;

public interface EngineProvider {
    public void validate(ExprVariable var1) throws ExprException;

    public void inputChanged(Range var1, Expr var2);

    public void valueChanged(Range var1, Expr var2);

    public Expr evaluateFunction(IEvaluationContext var1, ExprFunction var2) throws ExprException;

    public Expr evaluateVariable(IEvaluationContext var1, ExprVariable var2) throws ExprException;
}

