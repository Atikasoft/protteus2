/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Condition;

public class SUMIF
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 2);
        Expr range = SUMIF.evalArg(context, args[0]);
        int len = this.getLength(range);
        Condition cond = Condition.valueOf(SUMIF.evalArg(context, args[1]));
        Expr sumrange = args.length == 3 ? SUMIF.evalArg(context, args[2]) : range;
        double sum = 0.0;
        for (int i = 0; i < len; ++i) {
            sum += this.eval(this.get(range, i), cond, this.get(sumrange, i));
        }
        return new ExprDouble(sum);
    }

    protected double eval(Expr item, Condition c, Expr value) throws ExprException {
        if (c.eval(item) && (value instanceof ExprDouble || value instanceof ExprInteger)) {
            return ((ExprNumber)value).doubleValue();
        }
        return 0.0;
    }
}

