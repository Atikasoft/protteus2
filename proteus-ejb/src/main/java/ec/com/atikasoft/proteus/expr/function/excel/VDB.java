/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.Financials;

public class VDB
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 5, 7);
        Expr eC = VDB.evalArg(context, args[0]);
        if (eC instanceof ExprError) {
            return eC;
        }
        if (!this.isNumber(eC)) {
            return ExprError.VALUE;
        }
        double cost = ((ExprNumber)eC).doubleValue();
        Expr eS = VDB.evalArg(context, args[1]);
        if (eS instanceof ExprError) {
            return eS;
        }
        if (!this.isNumber(eS)) {
            return ExprError.VALUE;
        }
        double salvage = ((ExprNumber)eS).doubleValue();
        Expr eL = VDB.evalArg(context, args[2]);
        if (eL instanceof ExprError) {
            return eL;
        }
        if (!this.isNumber(eL)) {
            return ExprError.VALUE;
        }
        int life = ((ExprNumber)eL).intValue();
        Expr eP0 = VDB.evalArg(context, args[3]);
        if (eP0 instanceof ExprError) {
            return eP0;
        }
        if (!this.isNumber(eP0)) {
            return ExprError.VALUE;
        }
        double start_period = ((ExprNumber)eP0).doubleValue();
        Expr eP1 = VDB.evalArg(context, args[4]);
        if (eP1 instanceof ExprError) {
            return eP1;
        }
        if (!this.isNumber(eP1)) {
            return ExprError.VALUE;
        }
        double end_period = ((ExprNumber)eP1).doubleValue();
        double factor = 2.0;
        if (args.length > 5) {
            Expr eF = VDB.evalArg(context, args[5]);
            if (eF instanceof ExprError) {
                return eF;
            }
            if (!this.isNumber(eF)) {
                return ExprError.VALUE;
            }
            factor = ((ExprNumber)eF).doubleValue();
        }
        boolean no_switch = true;
        if (args.length > 6) {
            Expr eN = VDB.evalArg(context, args[6]);
            if (eN instanceof ExprError) {
                return eN;
            }
            if (!(eN instanceof ExprNumber)) {
                return ExprError.VALUE;
            }
            no_switch = ((ExprNumber)eN).booleanValue();
        }
        double vdb = Financials.vdb(cost, salvage, life, start_period, end_period, factor, no_switch);
        return new ExprDouble(vdb);
    }
}

