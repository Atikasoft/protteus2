/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Statistics;

public class BINOMDIST
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 4);
        Expr ens = BINOMDIST.evalArg(context, args[0]);
        if (!this.isNumber(ens)) {
            return ExprError.VALUE;
        }
        int nums = ((ExprNumber)ens).intValue();
        if (nums < 0) {
            return ExprError.NUM;
        }
        Expr et = BINOMDIST.evalArg(context, args[1]);
        if (!this.isNumber(et)) {
            return ExprError.VALUE;
        }
        int trials = ((ExprNumber)et).intValue();
        if (nums > trials) {
            return ExprError.NUM;
        }
        Expr ep = BINOMDIST.evalArg(context, args[2]);
        if (!this.isNumber(ep)) {
            return ExprError.VALUE;
        }
        double p = ((ExprNumber)ep).doubleValue();
        if (p < 0.0 || p > 1.0) {
            return ExprError.NUM;
        }
        Expr ec = BINOMDIST.evalArg(context, args[3]);
        if (!(ec instanceof ExprNumber)) {
            return ExprError.VALUE;
        }
        boolean c = ((ExprNumber)ec).booleanValue();
        return new ExprDouble(Statistics.binomDist(nums, trials, p, c));
    }
}

