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
import ec.com.atikasoft.proteus.expr.util.Statistics;

public class CONFIDENCE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr ea = CONFIDENCE.evalArg(context, args[0]);
        if (!this.isNumber(ea)) {
            return ExprError.VALUE;
        }
        double alpha = this.asDouble(context, ea, true);
        if (alpha <= 0.0 || alpha >= 1.0) {
            return ExprError.NUM;
        }
        Expr es = CONFIDENCE.evalArg(context, args[1]);
        if (!this.isNumber(es)) {
            return ExprError.VALUE;
        }
        double stdev = this.asDouble(context, es, true);
        if (stdev <= 0.0) {
            return ExprError.NUM;
        }
        Expr esi = CONFIDENCE.evalArg(context, args[2]);
        if (!this.isNumber(esi)) {
            return ExprError.VALUE;
        }
        int size = this.asInteger(context, esi, true);
        if (size < 1) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.confidence(alpha, stdev, size));
    }
}

