/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.parser.ExprLexer;

public class ExprString
extends Expr {
    public static final ExprString EMPTY = new ExprString("");
    public final String str;

    public ExprString(String str) {
        super(ExprType.String, false);
        this.str = str;
    }

    public String toString() {
        return ExprLexer.escapeString(this.str);
    }

    public int hashCode() {
        return this.str.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof ExprString && this.str.equals(((ExprString)obj).str);
    }
}

