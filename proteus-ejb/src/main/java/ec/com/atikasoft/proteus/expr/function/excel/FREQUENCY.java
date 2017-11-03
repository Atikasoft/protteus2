/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class FREQUENCY
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 2);
        double[] samples = this.asArray(context, FREQUENCY.evalArg(context, args[0]));
        double[] bins = this.asArray(context, FREQUENCY.evalArg(context, args[0]));
        return null;
    }

    double[] asArray(IEvaluationContext context, Expr vals) throws ExprException {
        if (vals instanceof ExprInteger || vals instanceof ExprDouble) {
            return new double[]{((ExprNumber)vals).doubleValue()};
        }
        if (vals instanceof ExprArray) {
            ExprArray a = (ExprArray)vals;
            double[] arr = new double[a.length()];
            int index = 0;
            for (int i = 0; i < arr.length; ++i) {
                Expr e = FREQUENCY.evalArg(context, a.get(i));
                if (!(e instanceof ExprNumber)) continue;
                arr[index++] = ((ExprNumber)e).doubleValue();
            }
            if (arr.length == index) {
                return arr;
            }
            double[] a2 = new double[index];
            System.arraycopy(arr, 0, a2, 0, index);
        }
        return null;
    }
}

