/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprType;

public class ExprError
extends Expr {
    public static final Expr NULL = new ExprError("#NULL!", "Null Error");
    public static final Expr DIV0 = new ExprError("#DIV/0!", "Divide by Zero Error");
    public static final Expr VALUE = new ExprError("#VALUE", "Error in Value");
    public static final Expr REF = new ExprError("#REF!", "Reference Error");
    public static final Expr NAME = new ExprError("#NAME?", "Invalid Name Error");
    public static final Expr NUM = new ExprError("#NUM!", "Number Error");
    public static final Expr NA = new ExprError("#N/A", "Value not Available");
    private String errType;
    private String message;

    public ExprError(String type, String message) {
        super(ExprType.Error, false);
        this.errType = type;
        this.message = message;
    }

    public String getErrType() {
        return this.errType;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "#" + this.message;
    }
}

