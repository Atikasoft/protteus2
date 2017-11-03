/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public class Exprs {
    public static Expr[] parseValues(String[] values) {
        Expr[] e = new Expr[values.length];
        for (int i = 0; i < e.length; ++i) {
            e[i] = Exprs.parseValue(values[i]);
        }
        return e;
    }

    public static Expr parseValue(String expression) {
        Expr result2;
        try {
            result2 = new ExprInteger(Integer.parseInt(expression));
        }
        catch (Exception e) {
            try {
                result2 = new ExprDouble(Double.parseDouble(expression));
            }
            catch (Exception ex) {
                result2 = new ExprString(expression);
            }
        }
        return result2;
    }

    public static String getString(IEvaluationContext context, Expr expr) throws ExprException {
        if (expr instanceof ExprEvaluatable) {
            expr = ((ExprEvaluatable)expr).evaluate(context);
        }
        if (expr instanceof ExprString) {
            return ((ExprString)expr).str;
        }
        return expr.toString();
    }

    public static Object convertExpr(IEvaluationContext context, Expr e) throws ExprException {
        if (e == null) {
            return null;
        }
        if (e instanceof ExprEvaluatable) {
            e = ((ExprEvaluatable)e).evaluate(context);
        }
        if (e instanceof ExprString) {
            return ((ExprString)e).str;
        }
        if (e instanceof ExprDouble) {
            return ((ExprDouble)e).doubleValue();
        }
        if (e instanceof ExprInteger) {
            return ((ExprInteger)e).intValue();
        }
        if (e instanceof ExprBoolean) {
            return ((ExprBoolean)e).booleanValue();
        }
        return e;
    }

    public static Expr convertObject(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Double) {
            return new ExprDouble((Double)o);
        }
        if (o instanceof Integer) {
            return new ExprInteger((Integer)o);
        }
        if (o instanceof Boolean) {
            return new ExprBoolean((Boolean)o);
        }
        if (o instanceof String) {
            return new ExprString((String)o);
        }
        if (o instanceof Expr) {
            return (Expr)o;
        }
        return null;
    }

    public static Expr[] convertArgs(Object[] args) {
        Expr[] a = new Expr[args.length];
        for (int i = 0; i < args.length; ++i) {
            a[i] = Exprs.convertObject(args[i]);
        }
        return a;
    }

    public static /* varargs */ ExprArray toArray(Object ... args) {
        Expr[] a = Exprs.convertArgs(args);
        ExprArray arr = new ExprArray(1, a.length);
        for (int i = 0; i < a.length; ++i) {
            arr.set(0, i, a[i]);
        }
        return arr;
    }

    public static void toUpperCase(Expr e) {
        if (e instanceof ExprFunction) {
            ExprFunction f = (ExprFunction)e;
            f.setName(f.getName().toUpperCase());
            for (int i = 0; i < f.size(); ++i) {
                Exprs.toUpperCase(f.getArg(i));
            }
        } else if (e instanceof ExprVariable) {
            ExprVariable v = (ExprVariable)e;
            v.setName(v.getName().toUpperCase());
        }
    }
}

