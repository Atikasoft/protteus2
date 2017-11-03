/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;

public class ExprAlias {
    private Expr expr;
    private ExprEvaluatable evaluatable;
    private IEvaluationContext delegate;
    private Map<String, Expr> variables = new HashMap<String, Expr>();
    private String text;

    public void setText(String text) throws IOException, ExprException {
        this.text = text;
        this.expr = ExprParser.parse(text);
        this.evaluatable = this.expr instanceof ExprEvaluatable ? (ExprEvaluatable)this.expr : null;
    }

    public String getText() {
        return this.text;
    }

    public synchronized Expr evaluate(Expr[] args, IEvaluationContext context) throws ExprException {
        if (this.evaluatable != null) {
            this.variables.clear();
            for (int i = 0; i < args.length; ++i) {
                this.variables.put("$" + (i + 1), args[i]);
            }
            return this.evaluatable.evaluate(context);
        }
        return this.expr;
    }

    public Expr evaluateFunction(ExprFunction function) throws ExprException {
        return this.delegate.evaluateFunction(function);
    }

    public Expr evaluateVariable(ExprVariable variable) throws ExprException {
        Expr var = this.variables.get(variable.getName());
        if (var != null) {
            return var;
        }
        return this.delegate.evaluateVariable(variable);
    }
}

