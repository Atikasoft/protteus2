/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Financials;

public class DDB
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 4);
        this.assertMaxArgCount(args, 5);
        double cost = this.asDouble(context, args[0], true);
        if (cost < 0.0) {
            return ExprError.NUM;
        }
        double salvage = this.asDouble(context, args[1], true);
        if (salvage < 0.0) {
            return ExprError.NUM;
        }
        int life = this.asInteger(context, args[2], true);
        if (life < 0) {
            return ExprError.NUM;
        }
        int period = this.asInteger(context, args[3], true);
        if (period < 0) {
            return ExprError.NUM;
        }
        double factor = 2.0;
        if (args.length == 5) {
            factor = this.asDouble(context, args[4], true);
        }
        if (cost == 0.0) {
            return ExprDouble.ZERO;
        }
        return new ExprDouble(Financials.ddb(cost, salvage, life, period, factor));
    }
}

