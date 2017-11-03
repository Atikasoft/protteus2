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

public class BETADIST
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 3);
        this.assertMaxArgCount(args, 5);
        Expr eX = BETADIST.evalArg(context, args[0]);
        if (!this.isNumber(eX)) {
            return ExprError.VALUE;
        }
        double x = ((ExprNumber)eX).doubleValue();
        Expr eAlpha = BETADIST.evalArg(context, args[1]);
        if (!this.isNumber(eAlpha)) {
            return ExprError.VALUE;
        }
        double alpha = ((ExprNumber)eAlpha).doubleValue();
        if (alpha <= 0.0) {
            return ExprError.NUM;
        }
        Expr eBeta = BETADIST.evalArg(context, args[2]);
        if (!this.isNumber(eBeta)) {
            return ExprError.VALUE;
        }
        double beta = ((ExprNumber)eBeta).doubleValue();
        if (beta <= 0.0) {
            return ExprError.NUM;
        }
        double a = 0.0;
        double b = 1.0;
        if (args.length > 3) {
            Expr eA = BETADIST.evalArg(context, args[3]);
            if (!this.isNumber(eA)) {
                return ExprError.VALUE;
            }
            a = ((ExprNumber)eA).doubleValue();
        }
        if (args.length > 4) {
            Expr eB = BETADIST.evalArg(context, args[4]);
            if (!this.isNumber(eB)) {
                return ExprError.VALUE;
            }
            b = ((ExprNumber)eB).doubleValue();
        }
        if (x < a || x > b || a == b) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.betaDist(x, alpha, beta, a, b));
    }
}

