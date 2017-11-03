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

public class REPLACE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 4);
        String str = this.asString(context, args[0], false);
        int start = this.asInteger(context, args[1], true);
        if (start < 1) {
            return ExprError.VALUE;
        }
        int len = this.asInteger(context, args[2], true);
        if (len < 0) {
            return ExprError.VALUE;
        }
        String rep = this.asString(context, args[3], false);
        StringBuilder sb = new StringBuilder();
        int stlen = str.length();
        if (start < stlen) {
            sb.append(str.substring(0, start - 1));
        }
        sb.append(rep);
        if (start + len <= stlen) {
            sb.append(str.substring(start + len - 1));
        }
        return new ExprString(sb.toString());
    }
}

