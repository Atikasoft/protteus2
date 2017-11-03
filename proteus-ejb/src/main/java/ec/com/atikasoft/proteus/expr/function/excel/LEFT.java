/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class LEFT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        this.assertMaxArgCount(args, 2);
        String str = this.asString(context, args[0], false);
        int r = 1;
        if (args.length == 2) {
            r = this.asInteger(context, args[1], true);
        }
        if (r > str.length()) {
            r = str.length();
        }
        return new ExprString(str.substring(0, r));
    }
}

