/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.function.excel.AVERAGE;

public class COVAR
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        ExprArray array1 = this.asArray(context, args[0], false);
        ExprArray array2 = this.asArray(context, args[1], false);
        if (array1.length() != array2.length()) {
            return ExprError.NA;
        }
        if (array1.length() == 0 || array2.length() == 0) {
            return ExprError.DIV0;
        }
        Expr ea1 = AVERAGE.average(context, array1);
        if (ea1 instanceof ExprError) {
            return ea1;
        }
        double average1 = ((ExprNumber)ea1).doubleValue();
        Expr ea2 = AVERAGE.average(context, array2);
        if (ea2 instanceof ExprError) {
            return ea2;
        }
        double average2 = ((ExprNumber)ea2).doubleValue();
        int count = 0;
        double p = 0.0;
        int len = array1.length();
        for (int i = 0; i < len; ++i) {
            Expr x = array1.get(i);
            Expr y = array2.get(i);
            if (!this.isNumber(x) || !this.isNumber(y)) continue;
            p += (this.asDouble(context, x, true) - average1) * (this.asDouble(context, y, true) - average2);
            ++count;
        }
        if (count == 0) {
            return ExprError.DIV0;
        }
        return new ExprDouble(p / (double)count);
    }
}

