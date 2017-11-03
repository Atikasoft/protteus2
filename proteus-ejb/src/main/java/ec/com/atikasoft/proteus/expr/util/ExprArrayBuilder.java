/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.ArrayList;
import java.util.List;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprMissing;

public class ExprArrayBuilder {
    private int cols = -1;
    private int rows = 0;
    private List<Expr> values = new ArrayList<Expr>();

    public void addRow(Expr[] vals) {
        if (this.cols == -1) {
            this.cols = vals.length;
        }
        for (int i = 0; i < this.cols; ++i) {
            if (i < vals.length) {
                this.values.add(vals[i]);
                continue;
            }
            this.values.add(ExprMissing.MISSING);
        }
        ++this.rows;
    }

    public ExprArray toArray() {
        ExprArray a = new ExprArray(this.rows, this.cols);
        for (int i = 0; i < a.length(); ++i) {
            a.set(i, this.values.get(i));
        }
        return a;
    }
}

