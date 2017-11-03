/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class MID
        extends AbstractFunction {

    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        String str = this.asString(context, args[0], false);
        int start = this.asInteger(context, args[1], true);
        if (start < 1) {
            return ExprError.VALUE;
        }
        int len = this.asInteger(context, args[2], true);
        if (len < 0) {
            return ExprError.VALUE;
        }
        int stlen = str.length();
        if (start > stlen) {
            start = stlen + 1;
        }
        if (start + len > stlen) {
            len = stlen - start + 1;
        }
        if (len < 0) {
            len = 0;
        }
        return new ExprString(str.substring(start - 1, start + len - 1));
    }
}
