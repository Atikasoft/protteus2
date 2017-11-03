/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import java.util.HashMap;
import java.util.Map;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.IExprFunction;
import ec.com.atikasoft.proteus.expr.function.IFunctionProvider;

public class FunctionMap
implements IFunctionProvider {
    private boolean caseSensitive;
    private Map<String, IExprFunction> functions = new HashMap<String, IExprFunction>();

    public FunctionMap() {
    }

    public FunctionMap(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void add(String name, IExprFunction function) {
        this.functions.put(this.caseSensitive ? name : name.toUpperCase(), function);
    }

    @Override
    public Expr evaluate(IEvaluationContext context, ExprFunction function) throws ExprException {
        IExprFunction f = this.functions.get(function.getName());
        if (f != null) {
            return f.evaluate(context, function.getArgs());
        }
        return null;
    }

    @Override
    public boolean hasFunction(ExprFunction function) {
        return this.functions.containsKey(function.getName());
    }
}

