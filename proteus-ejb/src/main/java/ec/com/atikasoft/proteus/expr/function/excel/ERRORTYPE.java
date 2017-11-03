/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class ERRORTYPE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        if (args[0] instanceof ExprError) {
            if (ExprError.NULL.equals(args[0])) {
                return new ExprInteger(1);
            }
            if (ExprError.DIV0.equals(args[0])) {
                return new ExprInteger(2);
            }
            if (ExprError.VALUE.equals(args[0])) {
                return new ExprInteger(3);
            }
            if (ExprError.REF.equals(args[0])) {
                return new ExprInteger(4);
            }
            if (ExprError.NAME.equals(args[0])) {
                return new ExprInteger(5);
            }
            if (ExprError.NUM.equals(args[0])) {
                return new ExprInteger(6);
            }
            if (ExprError.NA.equals(args[0])) {
                return new ExprInteger(7);
            }
        }
        return ExprError.NA;
    }
}

