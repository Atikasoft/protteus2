/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Maths;

public class LOG
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 1);
        this.assertMaxArgCount(args, 2);
        double num = this.asDouble(context, args[0], true);
        if (num < 0.0) {
            return ExprError.NUM;
        }
        int base = 10;
        if (args.length == 2) {
            base = this.asInteger(context, args[1], true);
        }
        if (base < 1) {
            return ExprError.NUM;
        }
        if (base == 1) {
            return ExprError.DIV0;
        }
        return new ExprDouble(Maths.log(num, base));
    }
}

