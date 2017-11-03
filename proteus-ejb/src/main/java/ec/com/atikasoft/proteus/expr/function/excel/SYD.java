/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class SYD
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 4);
        double cost = this.asDouble(context, args[0], true);
        double salvage = this.asDouble(context, args[1], true);
        double life = this.asDouble(context, args[2], true);
        double per = this.asDouble(context, args[3], true);
        double syd = (cost - salvage) * (life - per + 1.0) * 2.0 / (life * (life + 1.0));
        return new ExprDouble(syd);
    }
}

