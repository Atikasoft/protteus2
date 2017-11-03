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

public class SUMX2MY2
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        Expr eX = SUMX2MY2.evalArg(context, args[0]);
        if (!(eX instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        Expr eY = SUMX2MY2.evalArg(context, args[1]);
        if (!(eY instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        ExprArray arrayX = (ExprArray)eX;
        ExprArray arrayY = (ExprArray)eY;
        if (arrayY.length() != arrayX.length()) {
            return ExprError.NA;
        }
        int len = arrayX.length();
        double sum = 0.0;
        for (int i = 0; i < len; ++i) {
            sum += this.eval(this.asDouble(context, arrayX, i), this.asDouble(context, arrayY, i));
        }
        return new ExprDouble(sum);
    }

    protected double eval(double x, double y) {
        return Math.pow(x, 2.0) - Math.pow(y, 2.0);
    }
}

