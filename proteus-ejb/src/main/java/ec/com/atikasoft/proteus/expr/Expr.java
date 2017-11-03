/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

public abstract class Expr {
    public final ExprType type;
    public final boolean evaluatable;

    Expr(ExprType type, boolean evaluatable) {
        this.type = type;
        this.evaluatable = evaluatable;
    }

    public boolean isVolatile() {
        return false;
    }

    public void validate() throws ExprException {
    }

    protected Expr eval(Expr expr, IEvaluationContext context) throws ExprException {
        if (expr instanceof ExprEvaluatable) {
            return ((ExprEvaluatable)expr).evaluate(context);
        }
        return expr;
    }

    protected ExprBoolean bool(boolean bool) {
        return bool ? ExprBoolean.TRUE : ExprBoolean.FALSE;
    }
}

