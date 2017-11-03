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

public class CEILING
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        double val = this.asDouble(context, args[0], true);
        double rnd = this.asDouble(context, args[1], true);
        if (rnd == 0.0) {
            return ExprDouble.ZERO;
        }
        if (val < 0.0 && rnd > 0.0 || val > 0.0 && rnd < 0.0) {
            return ExprError.NUM;
        }
        double m = val % rnd;
        if (rnd < 0.0) {
            rnd = 0.0;
        }
        return new ExprDouble(val - m + rnd);
    }

    public boolean equalish(double d1, double d2) {
        return Math.abs(d1 - d2) < 1.0E-10;
    }
}

