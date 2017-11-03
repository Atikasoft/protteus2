/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.function.DoubleInOutFunction;
import ec.com.atikasoft.proteus.expr.util.ExcelDate;

public class MINUTE
extends DoubleInOutFunction {
    @Override
    protected double evaluate(double value) throws ExprException {
        return ExcelDate.getMinute(value);
    }
}

