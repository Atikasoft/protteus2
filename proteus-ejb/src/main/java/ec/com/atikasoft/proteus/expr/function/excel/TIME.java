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
import ec.com.atikasoft.proteus.expr.util.ExcelDate;

public class TIME
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr eH = TIME.evalArg(context, args[0]);
        if (!this.isNumber(eH)) {
            return ExprError.VALUE;
        }
        double h = ((ExprNumber)eH).doubleValue();
        Expr eM = TIME.evalArg(context, args[1]);
        if (!this.isNumber(eM)) {
            return ExprError.VALUE;
        }
        double m = ((ExprNumber)eM).doubleValue();
        Expr eS = TIME.evalArg(context, args[1]);
        if (!this.isNumber(eS)) {
            return ExprError.VALUE;
        }
        double s = ((ExprNumber)eS).doubleValue();
        double r = ExcelDate.time(h, m, s);
        if (r < 0.0) {
            return ExprError.NUM;
        }
        return new ExprDouble(r);
    }
}

