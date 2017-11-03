/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import java.util.ArrayList;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractDatabaseFunction;
import ec.com.atikasoft.proteus.expr.util.Criteria;
import ec.com.atikasoft.proteus.expr.util.Database;

public abstract class SimpleDatabaseFunction
extends AbstractDatabaseFunction {
    @Override
    protected final Expr evaluate(IEvaluationContext context, Database db, String field, Criteria criteria) throws ExprException {
        ArrayList<Expr> results = new ArrayList<Expr>();
        for (int i = 0; i < db.size(); ++i) {
            if (!criteria.matches(db, i)) continue;
            results.add(db.get(i, field));
        }
        return this.evaluateMatches(context, results.toArray(new Expr[0]));
    }

    protected abstract Expr evaluateMatches(IEvaluationContext var1, Expr[] var2) throws ExprException;
}

