/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.HashMap;
import java.util.Map;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.function.ExcelFunctionProvider;
import ec.com.atikasoft.proteus.expr.function.FunctionManager;

/**
 * 
 * @author hmolina
 */
public class SimpleEvaluationContext
implements IEvaluationContext {
    private FunctionManager functions = new FunctionManager();
    private Map<String, Expr> variables = new HashMap<String, Expr>();

    public SimpleEvaluationContext() {
        this.functions.add(new ExcelFunctionProvider());
        this.functions.add("SetVar", new SetVarFunction());
    }

    public void setVariable(String name, Expr value) {
        this.variables.put(name, value);
    }

    @Override
    public Expr evaluateFunction(ExprFunction function) throws ExprException {
        return this.functions.evaluate(this, function);
    }

    @Override
    public Expr evaluateVariable(ExprVariable variable) throws ExprException {
        return this.variables.get(variable.getName());
    }

    private class SetVarFunction
    extends AbstractFunction {
        private SetVarFunction() {
        }

        @Override
        public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
            this.assertArgCount(args, 2);
            String v = this.asString(context, args[0], false).toUpperCase();
            Expr e = SetVarFunction.evalArg(context, args[1]);
            SimpleEvaluationContext.this.variables.put(v, e);
            return e;
        }
    }

}

