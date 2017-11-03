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

public class PERMUT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr eNum = PERMUT.evalArg(context, args[0]);
        if (!this.isNumber(eNum)) {
            return ExprError.VALUE;
        }
        int num = ((ExprNumber)eNum).intValue();
        Expr eCho = PERMUT.evalArg(context, args[1]);
        if (!this.isNumber(eCho)) {
            return ExprError.VALUE;
        }
        int cho = ((ExprNumber)eCho).intValue();
        if (num < 0 || cho < 0 || num < cho) {
            return ExprError.NUM;
        }
        return new ExprDouble(Statistics.permut(num, cho));
    }
}

