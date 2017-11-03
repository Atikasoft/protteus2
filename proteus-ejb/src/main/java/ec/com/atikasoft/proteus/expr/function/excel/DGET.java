/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.SimpleDatabaseFunction;

public class DGET
extends SimpleDatabaseFunction {
    @Override
    protected Expr evaluateMatches(IEvaluationContext context, Expr[] matches) throws ExprException {
        if (matches.length != 1) {
            return ExprError.NUM;
        }
        return matches[0];
    }
}

