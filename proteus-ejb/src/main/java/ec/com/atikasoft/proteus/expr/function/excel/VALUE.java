/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.ValueParser;

public class VALUE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr e = VALUE.evalArg(context, args[0]);
        if (e instanceof ExprError) {
            return e;
        }
        if (e instanceof ExprArray) {
            return ExprError.VALUE;
        }
        String s = this.asString(context, e, false);
        Double d = ValueParser.parse(s.replaceAll("\"", ""));
        if (d != null) {
            return new ExprDouble(d);
        }
        return ExprError.VALUE;
    }
}

