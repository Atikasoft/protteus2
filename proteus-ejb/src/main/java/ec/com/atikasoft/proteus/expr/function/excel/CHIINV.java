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

public class CHIINV
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr eP = CHIINV.evalArg(context, args[0]);
        if (!this.isNumber(eP)) {
            return ExprError.VALUE;
        }
        double p = ((ExprNumber)eP).doubleValue();
        if (p < 0.0 || p > 1.0) {
            return ExprError.NUM;
        }
        Expr eDF = CHIINV.evalArg(context, args[1]);
        if (!this.isNumber(eDF)) {
            return ExprError.VALUE;
        }
        int df = ((ExprNumber)eDF).intValue();
        if (df < 0 || (double)df > 1.0E11) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.chiInv(p, df));
    }
}

