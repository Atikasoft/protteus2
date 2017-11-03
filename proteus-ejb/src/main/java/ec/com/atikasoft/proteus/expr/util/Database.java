/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.HashMap;
import java.util.Map;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.util.Exprs;

public class Database {
    private String[] columnNames;
    private Map<String, Expr>[] values;

    public Database(String[] columnNames, Map<String, Expr>[] values) {
        this.columnNames = columnNames;
        this.values = values;
    }

    public int size() {
        return this.values.length;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public String getColumnName(int index) {
        return this.columnNames[index];
    }

    public Expr get(int row, String name) {
        return this.values[row].get(name);
    }

    public static Database valueOf(IEvaluationContext context, ExprArray array) throws ExprException {
        int rows = array.rows();
        int cols = array.columns();
        if (rows < 2 || cols < 1) {
            return null;
        }
        String[] dc = new String[cols];
        for (int i = 0; i < dc.length; ++i) {
            dc[i] = Exprs.getString(context, array.get(0, i));
        }
        HashMap[] dr = new HashMap[rows - 1];
        for (int i2 = 1; i2 < rows; ++i2) {
            dr[i2 - 1] = new HashMap();
            for (int j = 0; j < cols; ++j) {
                dr[i2 - 1].put(dc[j], array.get(i2, j));
            }
        }
        return new Database(dc, dr);
    }
}

