/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class CONCATENATE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        StringBuilder sb = new StringBuilder();
        for (Expr a : args) {
            if ((a = CONCATENATE.evalArg(context, a)) instanceof ExprString) {
                sb.append(((ExprString)a).str);
                continue;
            }
            if (!(a instanceof ExprNumber)) continue;
            sb.append(a.toString());
        }
        return new ExprString(sb.toString());
    }
}

