/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class LOOKUP
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 2);
        this.assertMaxArgCount(args, 3);
        if (args.length == 2) {
            return LOOKUP.arrayLookup(context, args);
        }
        return LOOKUP.vectorLookup(context, args);
    }

    public static Expr vectorLookup(IEvaluationContext context, Expr[] args) throws ExprException {
        Expr ev = LOOKUP.evalArg(context, args[0]);
        Expr el = LOOKUP.evalArg(context, args[1]);
        Expr er = LOOKUP.evalArg(context, args[2]);
        return null;
    }

    public static Expr arrayLookup(IEvaluationContext context, Expr[] args) {
        return null;
    }
}

