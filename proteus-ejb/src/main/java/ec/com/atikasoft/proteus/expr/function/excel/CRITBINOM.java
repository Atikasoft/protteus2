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

public class CRITBINOM
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr et = CRITBINOM.evalArg(context, args[0]);
        if (!this.isNumber(et)) {
            return ExprError.VALUE;
        }
        int trials = ((ExprNumber)et).intValue();
        if (trials < 0) {
            return ExprError.NUM;
        }
        Expr ep = CRITBINOM.evalArg(context, args[1]);
        if (!this.isNumber(ep)) {
            return ExprError.VALUE;
        }
        double p = ((ExprNumber)ep).doubleValue();
        if (p < 0.0 || p > 1.0) {
            return ExprError.NUM;
        }
        Expr ea = CRITBINOM.evalArg(context, args[2]);
        if (!this.isNumber(ea)) {
            return ExprError.VALUE;
        }
        double alpha = ((ExprNumber)ea).doubleValue();
        if (alpha < 0.0 || alpha > 1.0) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.critBinom(trials, p, alpha));
    }
}

