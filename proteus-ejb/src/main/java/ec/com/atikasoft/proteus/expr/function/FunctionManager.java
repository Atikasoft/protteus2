/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import java.util.LinkedHashSet;
import java.util.Set;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.IExprFunction;
import ec.com.atikasoft.proteus.expr.function.FunctionMap;
import ec.com.atikasoft.proteus.expr.function.IFunctionProvider;

public class FunctionManager
implements IFunctionProvider {
    private FunctionMap functionMap;
    private Set<IFunctionProvider> providers = new LinkedHashSet<IFunctionProvider>();

    public FunctionManager() {
        this(false);
    }

    public FunctionManager(boolean caseSensitive) {
        this.functionMap = new FunctionMap(caseSensitive);
        this.providers.add(this.functionMap);
    }

    public void add(String name, IExprFunction function) {
        this.functionMap.add(name, function);
    }

    public void add(IFunctionProvider provider) {
        this.providers.add(provider);
    }

    @Override
    public Expr evaluate(IEvaluationContext context, ExprFunction function) throws ExprException {
        for (IFunctionProvider p : this.providers) {
            if (!p.hasFunction(function)) continue;
            return p.evaluate(context, function);
        }
        return null;
    }

    @Override
    public boolean hasFunction(ExprFunction function) {
        for (IFunctionProvider p : this.providers) {
            if (!p.hasFunction(function)) continue;
            return true;
        }
        return false;
    }
}

