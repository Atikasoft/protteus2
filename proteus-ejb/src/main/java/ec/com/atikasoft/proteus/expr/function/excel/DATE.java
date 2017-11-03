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

public class DATE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr eY = DATE.evalArg(context, args[0]);
        if (!this.isNumber(eY)) {
            return ExprError.VALUE;
        }
        double y = ((ExprNumber)eY).doubleValue();
        Expr eM = DATE.evalArg(context, args[1]);
        if (!this.isNumber(eM)) {
            return ExprError.VALUE;
        }
        double m = ((ExprNumber)eM).doubleValue();
        Expr eD = DATE.evalArg(context, args[1]);
        if (!this.isNumber(eD)) {
            return ExprError.VALUE;
        }
        double d = ((ExprNumber)eD).doubleValue();
        double r = ExcelDate.date(y, m, d);
        if (r < 0.0) {
            return ExprError.NUM;
        }
        return new ExprDouble(r);
    }
}

