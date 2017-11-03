/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Criteria;
import ec.com.atikasoft.proteus.expr.util.Database;

public abstract class AbstractDatabaseFunction
extends AbstractFunction {
    @Override
    public final Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr edb = AbstractDatabaseFunction.evalArg(context, args[0]);
        if (!(edb instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        Database db = Database.valueOf(context, (ExprArray)edb);
        if (db == null) {
            return ExprError.VALUE;
        }
        Expr ef = AbstractDatabaseFunction.evalArg(context, args[1]);
        String field = null;
        if (ef instanceof ExprString) {
            field = ((ExprString)ef).str;
        } else if (ef instanceof ExprInteger) {
            int col = ((ExprInteger)ef).intValue();
            int cc = db.getColumnCount();
            if (col < 1 || col > cc) {
                return ExprError.VALUE;
            }
            field = db.getColumnName(col - 1);
        }
        Expr ec = AbstractDatabaseFunction.evalArg(context, args[2]);
        if (!(ec instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        Criteria criteria = Criteria.valueOf(context, (ExprArray)ec);
        return this.evaluate(context, db, field, criteria);
    }

    protected abstract Expr evaluate(IEvaluationContext var1, Database var2, String var3, Criteria var4) throws ExprException;
}

