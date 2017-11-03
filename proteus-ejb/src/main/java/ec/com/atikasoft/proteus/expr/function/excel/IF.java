/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class IF
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        if (args.length < 2) {
            throw new ExprException("Too few arguments entered for function IF");
        }
        if (args.length > 3) {
            throw new ExprException("Too many arguments entered for function IF");
        }
        Expr cond = IF.evalArg(context, args[0]);
        Expr yRes = args[1];
        Expr nRes = null;
        nRes = args.length == 2 ? ExprBoolean.FALSE : args[2];
        if (cond instanceof ExprNumber) {
            Expr res = IF.evalArg(context, ((ExprNumber)cond).booleanValue() ? yRes : nRes);
            if (res instanceof ExprMissing) {
                res = ExprDouble.ZERO;
            }
            return res;
        }
        return ExprError.VALUE;
    }
}

