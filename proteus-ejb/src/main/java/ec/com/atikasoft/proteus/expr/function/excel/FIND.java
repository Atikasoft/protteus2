/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class FIND
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        int i;
        this.assertMinArgCount(args, 2);
        this.assertMaxArgCount(args, 3);
        String f = this.asString(context, args[0], true);
        String s = this.asString(context, args[1], true);
        int pos = 0;
        if (args.length == 3) {
            pos = this.asInteger(context, args[2], true);
        }
        if ((i = s.indexOf(f, pos)) == -1) {
            return ExprError.VALUE;
        }
        return new ExprInteger(i + 1);
    }
}

