/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.util.Condition;
import ec.com.atikasoft.proteus.expr.util.Database;
import ec.com.atikasoft.proteus.expr.util.Exprs;

public class Criteria {
    private String[] columnNames;
    private Map<String, Condition>[] conditions;

    public Criteria(String[] columnNames, Map<String, Condition>[] values) {
        this.columnNames = columnNames;
        this.conditions = values;
    }

    public int size() {
        return this.conditions.length;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public String getColumnName(int index) {
        return this.columnNames[index];
    }

    public static Criteria valueOf(IEvaluationContext context, ExprArray array) throws ExprException {
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
                Expr e = array.get(i2, j);
                Condition c = (Condition)dr[i2 - 1].get(dc[j]);
                if (c != null) {
                    c.add(Condition.valueOf(e));
                    continue;
                }
                dr[i2 - 1].put(dc[j], Condition.valueOf(e));
            }
        }
        return new Criteria(dc, dr);
    }

    public boolean matches(Database db, int row) throws ExprException {
        for (int i = 0; i < this.conditions.length; ++i) {
            boolean res = true;
            for (String key : this.conditions[i].keySet()) {
                Condition c = this.conditions[i].get(key);
                if (c == null || c.eval(db.get(row, key))) continue;
                res = false;
                break;
            }
            if (!res) continue;
            return true;
        }
        return false;
    }
}

