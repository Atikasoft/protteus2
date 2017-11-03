/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class MOD
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr n = args[0];
        if (n instanceof ExprEvaluatable) {
            n = ((ExprEvaluatable)n).evaluate(context);
        }
        if (n instanceof ExprArray) {
            ExprArray a = (ExprArray)n;
            if (a.rows() > 1) {
                return ExprError.VALUE;
            }
            n = a.get(0, 0);
        }
        if (!(n instanceof ExprNumber)) {
            return ExprError.VALUE;
        }
        double num = ((ExprNumber)n).doubleValue();
        Expr d = args[1];
        if (d instanceof ExprEvaluatable) {
            d = ((ExprEvaluatable)d).evaluate(context);
        }
        if (!(d instanceof ExprNumber)) {
            return ExprError.VALUE;
        }
        double div = ((ExprNumber)d).doubleValue();
        double mod = num % div;
        if (mod > 0.0 && div < 0.0 || mod < 0.0 && div > 0.0) {
            mod *= -1.0;
        }
        return new ExprDouble(mod);
    }
}

