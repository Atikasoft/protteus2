/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import java.util.Arrays;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprArray
extends Expr {
    private int columns;
    private int rows;
    private Expr[] array;

    public ExprArray(int rows, int columns) {
        super(ExprType.Array, false);
        this.array = new Expr[rows * columns];
        this.columns = columns;
        this.rows = rows;
    }

    public int rows() {
        return this.rows;
    }

    public int columns() {
        return this.columns;
    }

    public int length() {
        return this.array.length;
    }

    public Expr get(int index) {
        return this.array[index];
    }

    public Expr get(int row, int column) {
        return this.array[row * this.columns + column];
    }

    public void set(int index, Expr value) {
        this.array[index] = value;
    }

    public void set(int row, int column, Expr value) {
        this.array[row * this.columns + column] = value;
    }

    public Expr[] getInternalArray() {
        return this.array;
    }

    public int hashCode() {
        return 567 ^ this.rows ^ this.columns ^ this.array.length;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExprArray)) {
            return false;
        }
        ExprArray a = (ExprArray)obj;
        return a.rows == this.rows && a.columns == this.columns && Arrays.equals(a.array, this.array);
    }
}

