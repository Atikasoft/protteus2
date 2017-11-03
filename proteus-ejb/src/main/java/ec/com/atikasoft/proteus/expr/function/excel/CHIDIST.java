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

public class CHIDIST
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr eX = CHIDIST.evalArg(context, args[0]);
        if (!this.isNumber(eX)) {
            return ExprError.VALUE;
        }
        double x = ((ExprNumber)eX).doubleValue();
        Expr eDF = CHIDIST.evalArg(context, args[1]);
        if (!this.isNumber(eDF)) {
            return ExprError.VALUE;
        }
        int df = ((ExprNumber)eDF).intValue();
        if (df < 0 || (double)df > 1.0E11) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.chiDist(x, df));
    }
}

