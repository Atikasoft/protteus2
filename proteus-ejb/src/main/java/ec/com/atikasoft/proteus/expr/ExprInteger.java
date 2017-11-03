/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprInteger
extends ExprNumber {
    public static final ExprInteger ZERO = new ExprInteger(0);
    public final int value;

    public ExprInteger(int value) {
        super(ExprType.Integer);
        this.value = value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(Object obj) {
        return obj instanceof ExprInteger && this.value == ((ExprInteger)obj).value;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}

