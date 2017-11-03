/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IBinaryOperator;
import ec.com.atikasoft.proteus.expr.util.Reflect;

public abstract class AbstractBinaryOperator
extends ExprEvaluatable
implements IBinaryOperator {
    protected Expr lhs;
    protected Expr rhs;

    public AbstractBinaryOperator(ExprType type, Expr lhs, Expr rhs) {
        super(type);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Expr getLHS() {
        return this.lhs;
    }

    @Override
    public void setLHS(Expr lhs) {
        this.lhs = lhs;
    }

    @Override
    public Expr getRHS() {
        return this.rhs;
    }

    @Override
    public void setRHS(Expr rhs) {
        this.rhs = rhs;
    }

    @Override
    public void validate() throws ExprException {
        if (this.lhs == null) {
            throw new ExprException("LHS of operator missing");
        }
        this.lhs.validate();
        if (this.rhs == null) {
            throw new ExprException("RHS of operator missing");
        }
        this.rhs.validate();
    }

    public int hashCode() {
        int hc = this.type.ordinal();
        if (this.lhs != null) {
            hc ^= this.lhs.hashCode();
        }
        if (this.rhs != null) {
            hc ^= this.rhs.hashCode();
        }
        return hc;
    }

    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        AbstractBinaryOperator b = (AbstractBinaryOperator)obj;
        return Reflect.equals(b.lhs, this.lhs) && Reflect.equals(b.rhs, this.rhs);
    }
}

