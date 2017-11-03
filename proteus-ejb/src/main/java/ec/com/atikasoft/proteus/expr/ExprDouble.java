/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprDouble
extends ExprNumber {
    public static final ExprDouble ZERO = new ExprDouble(0.0);
    public static final ExprDouble PI = new ExprDouble(3.141592653589793);
    public static final ExprDouble E = new ExprDouble(2.718281828459045);
    public final double value;

    public ExprDouble(double value) {
        super(ExprType.Double);
        this.value = value;
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public String toString() {
        return Double.toString(this.value);
    }

    public int hashCode() {
        return (int)this.value * 100;
    }

    public boolean equals(Object obj) {
        return obj instanceof ExprDouble && Math.abs(this.value - ((ExprDouble)obj).value) < 1.0E-10;
    }
}

