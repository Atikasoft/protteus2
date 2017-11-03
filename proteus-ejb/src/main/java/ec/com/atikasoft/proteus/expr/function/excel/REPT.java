/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class REPT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        int rep;
        this.assertArgCount(args, 2);
        Expr str = REPT.evalArg(context, args[0]);
        if (str instanceof ExprArray) {
            str = ((ExprArray)str).get(0);
        }
        if ((rep = this.asInteger(context, args[1], true)) < 0) {
            return ExprError.VALUE;
        }
        switch (rep) {
            case 0: {
                return ExprString.EMPTY;
            }
            case 1: {
                return REPT.evalArg(context, args[0]);
            }
        }
        String s = str.toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rep; ++i) {
            sb.append(s);
        }
        return new ExprString(sb.toString());
    }
}

