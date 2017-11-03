/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.function.excel.AVERAGE;

public class FORECAST
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 3);
        Expr eF = FORECAST.evalArg(context, args[0]);
        if (!(eF instanceof ExprNumber)) {
            return ExprError.VALUE;
        }
        Expr eY = FORECAST.evalArg(context, args[1]);
        if (!(eY instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        Expr eX = FORECAST.evalArg(context, args[2]);
        if (!(eX instanceof ExprArray)) {
            return ExprError.VALUE;
        }
        double forecastX = ((ExprNumber)eF).doubleValue();
        ExprArray knownY = (ExprArray)eY;
        ExprArray knownX = (ExprArray)eX;
        if (knownY.length() != knownX.length()) {
            return ExprError.NA;
        }
        Expr aeY = AVERAGE.average(context, knownY);
        if (aeY instanceof ExprError) {
            return aeY;
        }
        Expr aeX = AVERAGE.average(context, knownX);
        if (aeX instanceof ExprError) {
            return aeX;
        }
        double averageY = ((ExprNumber)aeY).doubleValue();
        double averageX = ((ExprNumber)aeX).doubleValue();
        double bnum = 0.0;
        double bdem = 0.0;
        int len = knownY.length();
        for (int i = 0; i < len; ++i) {
            bnum += (this.asDouble(context, knownX, i) - averageX) * (this.asDouble(context, knownY, i) - averageY);
            bdem += Math.pow(this.asDouble(context, knownX, i) - averageX, 2.0);
        }
        if (bdem == 0.0) {
            return ExprError.DIV0;
        }
        double b = bnum / bdem;
        double a = averageY - b * averageX;
        double res = a + b * forecastX;
        return new ExprDouble(res);
    }
}

