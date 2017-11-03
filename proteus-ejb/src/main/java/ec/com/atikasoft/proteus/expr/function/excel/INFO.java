/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class INFO
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        Expr a = INFO.evalArg(context, args[0]);
        if (!(a instanceof ExprString)) {
            return ExprError.VALUE;
        }
        String s = ((ExprString)a).str;
        if ("directory".equalsIgnoreCase(s)) {
            return new ExprString(System.getProperty("user.dir"));
        }
        if ("memavail".equalsIgnoreCase(s)) {
            return new ExprDouble(Runtime.getRuntime().freeMemory());
        }
        if ("memused".equalsIgnoreCase(s)) {
            return new ExprDouble(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        }
        if ("numfile".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("origin".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("osversion".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("recalc".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("release".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("system".equalsIgnoreCase(s)) {
            return ExprError.REF;
        }
        if ("totmem".equalsIgnoreCase(s)) {
            return new ExprDouble(Runtime.getRuntime().totalMemory());
        }
        return ExprError.VALUE;
    }
}

