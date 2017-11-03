/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.ArrayList;
import java.util.List;
import ec.com.atikasoft.proteus.expr.AbstractBinaryOperator;
import ec.com.atikasoft.proteus.expr.AbstractComparisonOperator;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEqual;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprGreaterThan;
import ec.com.atikasoft.proteus.expr.ExprGreaterThanOrEqualTo;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprLessThan;
import ec.com.atikasoft.proteus.expr.ExprLessThanOrEqualTo;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNotEqual;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.function.excel.AND;

public class Condition {
    private List<AbstractBinaryOperator> operators = new ArrayList<AbstractBinaryOperator>();
    private AND func = new AND();

    public static Condition valueOf(Expr arg) {
        if (arg instanceof ExprString) {
            AbstractComparisonOperator operator2;
            String s = ((ExprString)arg).str;
            if ("".equals(s)) {
                return null;
            }
            Condition c = new Condition();
            int offset = 0;
            boolean str = false;
            if (s.startsWith(">=")) {
                operator2 = new ExprGreaterThanOrEqualTo(null, null);
                offset = 2;
            } else if (s.startsWith("<=")) {
                operator2 = new ExprLessThanOrEqualTo(null, null);
                offset = 2;
            } else if (s.startsWith("<>")) {
                operator2 = new ExprNotEqual(null, null);
                offset = 2;
            } else if (s.startsWith("=")) {
                operator2 = new ExprEqual(null, null);
                offset = 1;
            } else if (s.startsWith("<")) {
                operator2 = new ExprLessThan(null, null);
                offset = 1;
            } else if (s.startsWith(">")) {
                operator2 = new ExprGreaterThan(null, null);
                offset = 1;
            } else {
                operator2 = new ExprEqual(null, null);
                str = true;
                offset = 0;
            }
            operator2.setRHS(c.getRHS(s, offset, str));
            c.operators.add(operator2);
            return c;
        }
        if (arg instanceof ExprDouble || arg instanceof ExprInteger) {
            Condition c = new Condition();
            ExprEqual operator = new ExprEqual(null, arg);
            c.operators.add(operator);
            return c;
        }
        return null;
    }

    private Expr getRHS(String text, int offset, boolean str) {
        if (str) {
            return new ExprString(text.substring(offset));
        }
        try {
            return new ExprDouble(Double.parseDouble(text.substring(offset)));
        }
        catch (NumberFormatException e) {
            return ExprMissing.MISSING;
        }
    }

    public boolean eval(Expr arg) throws ExprException {
        if (this.operators.isEmpty()) {
            return true;
        }
        for (AbstractBinaryOperator operator : this.operators) {
            operator.setLHS(arg);
        }
        ExprBoolean v = (ExprBoolean)this.func.evaluate(null, this.operators.toArray(new Expr[0]));
        return v.value;
    }

    public void add(Condition c) {
        this.operators.addAll(c.operators);
    }
}

