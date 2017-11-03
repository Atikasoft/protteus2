/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.engine;

public class GridReference {
    private int column = -1;
    private int row = -1;
    private boolean columnFixed;
    private boolean rowFixed;

    public GridReference(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public GridReference(int column, boolean columnFixed, int row, boolean rowFixed) {
        this.column = column;
        this.columnFixed = columnFixed;
        this.row = row;
        this.rowFixed = rowFixed;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumnFixed(boolean fixed) {
        this.columnFixed = fixed;
    }

    public boolean isColumnFixed() {
        return this.columnFixed;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public void setRowFixed(boolean fixed) {
        this.rowFixed = fixed;
    }

    public boolean isRowFixed() {
        return this.rowFixed;
    }

    public static GridReference valueOf(String s) {
        if (s == null) {
            return null;
        }
        int len = (s = s.toUpperCase().trim()).length();
        if (len == 0) {
            return null;
        }
        while (len > 0 && Character.isDigit(s.charAt(len - 1))) {
            --len;
        }
        String column = s.substring(0, len);
        int row = 0;
        try {
            String b = s.substring(len);
            row = Integer.parseInt(b);
        }
        catch (Exception e) {
            return null;
        }
        if (column.length() == 0) {
            return null;
        }
        boolean columnFixed = false;
        boolean rowFixed = false;
        if (column.charAt(0) == '$') {
            columnFixed = true;
            column = column.substring(1);
        }
        if (column.endsWith("$")) {
            rowFixed = true;
            column = column.substring(0, column.length() - 1);
        }
        if (!GridReference.isValidVariable(column)) {
            return null;
        }
        GridReference gf = new GridReference(GridReference.toColumnIndex(column), row);
        gf.setColumnFixed(columnFixed);
        gf.setRowFixed(rowFixed);
        return gf;
    }

    public static int toColumnIndex(String columnName) {
        int len = columnName.length();
        int val = 0;
        for (int i = 0; i < len; ++i) {
            int v = columnName.charAt(i) - 65 + 1;
            val *= 26;
            val += v;
        }
        return val;
    }

    public static String toColumnName(int column) {
        if (column <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (column > 0) {
            int val = column % 26;
            if (val == 0) {
                val = 26;
            }
            column -= val;
            sb.insert(0, (char)(65 + val - 1));
            column /= 26;
        }
        return sb.toString();
    }

    public static boolean isValidVariable(String var) {
        if (var == null) {
            return false;
        }
        if ((var = var.trim()).length() == 0) {
            return false;
        }
        if (var.indexOf(36) != -1) {
            return false;
        }
        if (var.indexOf(33) != -1) {
            return false;
        }
        if (var.indexOf(58) != -1) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.column ^ this.row ^ (this.columnFixed ? 57 : 35) ^ (this.rowFixed ? 122 : 989);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GridReference)) {
            return false;
        }
        GridReference gf = (GridReference)obj;
        return this.column == gf.column && this.row == gf.row && gf.columnFixed == this.columnFixed && gf.rowFixed == this.rowFixed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.columnFixed) {
            sb.append('$');
        }
        sb.append(GridReference.toColumnName(this.column));
        if (this.rowFixed) {
            sb.append('$');
        }
        sb.append(this.row);
        return sb.toString();
    }
}

