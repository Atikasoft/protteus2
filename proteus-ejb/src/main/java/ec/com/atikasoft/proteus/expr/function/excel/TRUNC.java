/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class TRUNC
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        this.assertMaxArgCount(args, 2);
        double num = this.asDouble(context, args[0], true);
        int dig = 1;
        if (args.length == 2) {
            dig = this.asInteger(context, args[1], true);
        }
        if (dig == 1) {
            return new ExprDouble((int)num);
        }
        int div = (int)Math.pow(10.0, dig);
        int v = (int)(num * (double)div);
        double d = (double)v / (double)div;
        return new ExprDouble(d);
    }
}

