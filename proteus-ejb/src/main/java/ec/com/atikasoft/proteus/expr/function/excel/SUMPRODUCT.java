/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class SUMPRODUCT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 2);
        int len = 0;
        Expr[] ea = new Expr[args.length];
        for (int i = 0; i < args.length; ++i) {
            ea[i] = SUMPRODUCT.evalArg(context, args[i]);
            if (i == 0) {
                len = this.getLength(ea[i]);
                continue;
            }
            if (len == this.getLength(ea[i])) continue;
            return ExprError.VALUE;
        }
        double sum = 0.0;
        for (int i2 = 0; i2 < len; ++i2) {
            double p = 1.0;
            for (int j = 0; j < ea.length; ++j) {
                Expr a = this.get(ea[j], i2);
                if (a instanceof ExprDouble || a instanceof ExprInteger) {
                    p *= ((ExprNumber)a).doubleValue();
                    continue;
                }
                p = 0.0;
                break;
            }
            sum += p;
        }
        return new ExprDouble(sum);
    }
}

