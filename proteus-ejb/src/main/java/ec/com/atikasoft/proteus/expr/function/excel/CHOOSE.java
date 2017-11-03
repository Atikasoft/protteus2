/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class CHOOSE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        if (args.length < 2) {
            throw new ExprException("Too few arguments for CHOOSE");
        }
        Expr index = args[0];
        if (index instanceof ExprEvaluatable) {
            index = ((ExprEvaluatable)index).evaluate(context);
        }
        if (!(index instanceof ExprNumber)) {
            throw new ExprException("First argument must be a number for CHOOSE");
        }
        int idx = ((ExprNumber)index).intValue();
        if (idx < 1 || idx >= args.length) {
            throw new ExprException("Invalid index for CHOOSE");
        }
        return args[idx];
    }
}

