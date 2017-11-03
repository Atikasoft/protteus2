/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.ValueFormatter;

public class TEXT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr eV = TEXT.evalArg(context, args[0]);
        if (eV instanceof ExprError) {
            return eV;
        }
        if (!this.isNumber(eV)) {
            return ExprError.VALUE;
        }
        double value = ((ExprNumber)eV).doubleValue();
        Expr eF = TEXT.evalArg(context, args[0]);
        if (eF instanceof ExprError) {
            return eF;
        }
        String s = this.asString(context, eF, false);
        String res = ValueFormatter.format(value, s);
        if (res != null) {
            return new ExprString(res);
        }
        return ExprError.VALUE;
    }
}

