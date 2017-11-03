/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprBoolean;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.IExprFunction;

public abstract class AbstractFunction
implements IExprFunction {
    @Override
    public boolean isVolatile() {
        return false;
    }

    protected void assertArgCount(Expr[] args, int count) throws ExprException {
        if (args == null && count != 0) {
            throw new ExprException(this.getClass().getSimpleName() + " function takes no arguments");
        }
        if (args.length != count) {
            throw new ExprException(this.getClass().getSimpleName() + " function takes " + count + " arguments");
        }
    }

    protected /* varargs */ void assertArgTypes(Expr[] args, ExprType ... types) throws ExprException {
        this.assertArgCount(args, types.length);
        for (int i = 0; i < args.length; ++i) {
            if (args[i].type.equals((Object)types[i])) continue;
            throw new ExprException("Invalid argument (" + i + 1 + ") to function: " + this.getClass().getSimpleName());
        }
    }

    protected double asDouble(IEvaluationContext context, Expr arg, boolean strict) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return ((ExprNumber)arg).doubleValue();
        }
        if (!strict) {
            return 0.0;
        }
        throw new ExprException("Invalid argument type for function " + this.getClass().getSimpleName());
    }

    protected int asInteger(IEvaluationContext context, Expr arg, boolean strict) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return ((ExprNumber)arg).intValue();
        }
        if (!strict) {
            return 0;
        }
        throw new ExprException("Invalid argument type for function " + this.getClass().getSimpleName());
    }

    protected boolean asBoolean(IEvaluationContext context, Expr arg, boolean strict) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprNumber) {
            return ((ExprNumber)arg).booleanValue();
        }
        if (!strict) {
            return false;
        }
        throw new ExprException("Invalid argument type for function " + this.getClass().getSimpleName());
    }

    protected String asString(IEvaluationContext context, Expr arg, boolean strict) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            arg = ((ExprEvaluatable)arg).evaluate(context);
        }
        if (arg instanceof ExprString) {
            return ((ExprString)arg).str;
        }
        if (!strict) {
            if (arg instanceof ExprNumber) {
                return arg.toString();
            }
            return "";
        }
        throw new ExprException("Invalid argument type for function " + this.getClass().getSimpleName());
    }

    protected void assertArgType(Expr expr, ExprType type) throws ExprException {
        if (expr == null) {
            if (type != null) {
                throw new ExprException("Invalid empty argument for function " + this.getClass().getSimpleName());
            }
        } else if (!expr.type.equals((Object)type)) {
            throw new ExprException("Invalid argument type for function " + this.getClass().getSimpleName());
        }
    }

    protected void assertArgCount(Expr[] args, int min, int max) throws ExprException {
        this.assertMinArgCount(args, min);
        this.assertMaxArgCount(args, max);
    }

    protected void assertMinArgCount(Expr[] args, int count) throws ExprException {
        if (args.length < count) {
            throw new ExprException("Too few arguments to function " + this.getClass().getSimpleName());
        }
    }

    protected void assertMaxArgCount(Expr[] args, int count) throws ExprException {
        if (args.length > count) {
            throw new ExprException("Too many arguments to function " + this.getClass().getSimpleName());
        }
    }

    public static Expr evalArg(IEvaluationContext context, Expr arg) throws ExprException {
        if (arg instanceof ExprEvaluatable) {
            return ((ExprEvaluatable)arg).evaluate(context);
        }
        return arg;
    }

    protected int getLength(Expr range) {
        if (range instanceof ExprArray) {
            return ((ExprArray)range).length();
        }
        return 1;
    }

    protected Expr get(Expr range, int index) {
        if (range instanceof ExprArray) {
            ExprArray a = (ExprArray)range;
            if (index >= 0 && index < a.length()) {
                return a.get(index);
            }
        } else if (index == 0) {
            return range;
        }
        return ExprMissing.MISSING;
    }

    protected ExprBoolean bool(boolean bool) {
        return bool ? ExprBoolean.TRUE : ExprBoolean.FALSE;
    }

    protected double asDouble(IEvaluationContext context, ExprArray knownY, int index) throws ExprException {
        if (index < 0 || index >= knownY.length()) {
            return 0.0;
        }
        Expr e = knownY.get(index);
        return this.asDouble(context, e, false);
    }

    protected /* varargs */ boolean isOneOf(Expr expr, ExprType ... types) {
        for (ExprType t : types) {
            if (!expr.type.equals((Object)t)) continue;
            return true;
        }
        return false;
    }

    protected ExprArray asArray(IEvaluationContext context, Expr expr, boolean strict) throws ExprException {
        if (expr instanceof ExprEvaluatable) {
            expr = ((ExprEvaluatable)expr).evaluate(context);
        }
        if (expr instanceof ExprArray) {
            return (ExprArray)expr;
        }
        if (strict) {
            throw new ExprException("Argument not an array for function: " + this.getClass().getSimpleName());
        }
        ExprArray ea = new ExprArray(1, 1);
        ea.set(0, expr);
        return ea;
    }

    protected boolean isNumber(Expr x) {
        return x instanceof ExprDouble || x instanceof ExprInteger;
    }
}

