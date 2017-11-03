/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunctionErr;
import ec.com.atikasoft.proteus.expr.util.Statistics;

public class NORMSDIST
extends DoubleInOutFunctionErr {
    @Override
    protected double evaluate(double value) throws ExprException {
        return Statistics.normsDist(value);
    }
}

