/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class INT
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr a = args[0];
        if (a instanceof ExprEvaluatable) {
            a = ((ExprEvaluatable)a).evaluate(context);
        }
        if (a instanceof ExprInteger) {
            return a;
        }
        if (a instanceof ExprBoolean) {
            return new ExprInteger(((ExprNumber)a).intValue());
        }
        if (a instanceof ExprDouble) {
            return new ExprInteger((int)Math.floor(((ExprDouble)a).doubleValue()));
        }
        return ExprError.VALUE;
    }
}

