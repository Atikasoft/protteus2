/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprBoolean
extends ExprNumber {
    public static final ExprBoolean TRUE = new ExprBoolean(true);
    public static final ExprBoolean FALSE = new ExprBoolean(false);
    public final boolean value;

    public ExprBoolean(boolean value) {
        super(ExprType.Boolean);
        this.value = value;
    }

    @Override
    public boolean booleanValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.intValue();
    }

    @Override
    public int intValue() {
        return this.value ? 1 : 0;
    }

    public int hashCode() {
        return this.value ? 1 : 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof ExprBoolean && this.value == ((ExprBoolean)obj).value;
    }

    public String toString() {
        return Boolean.toString(this.value).toUpperCase();
    }
}

