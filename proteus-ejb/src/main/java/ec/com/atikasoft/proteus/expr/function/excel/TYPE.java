/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class TYPE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr a = TYPE.evalArg(context, args[0]);
        if (a instanceof ExprString) {
            return new ExprDouble(2.0);
        }
        if (a instanceof ExprInteger || a instanceof ExprDouble) {
            return new ExprDouble(1.0);
        }
        if (a instanceof ExprBoolean) {
            return new ExprDouble(4.0);
        }
        if (a instanceof ExprArray) {
            return new ExprDouble(64.0);
        }
        return new ExprDouble(16.0);
    }
}

