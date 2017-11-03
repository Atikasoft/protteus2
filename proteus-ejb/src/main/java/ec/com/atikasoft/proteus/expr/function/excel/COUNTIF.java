/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Condition;

public class COUNTIF
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        ExprArray array = this.asArray(context, args[0], true);
        Condition cond = Condition.valueOf(args[1]);
        if (cond == null) {
            return ExprError.VALUE;
        }
        Expr[] a = array.getInternalArray();
        int count = 0;
        for (int i = 0; i < a.length; ++i) {
            if (!cond.eval(a[i])) continue;
            ++count;
        }
        return new ExprDouble(count);
    }
}

