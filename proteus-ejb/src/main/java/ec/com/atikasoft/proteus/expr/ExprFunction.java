/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

public class ExprFunction
extends ExprEvaluatable {
    private String name;
    private Expr[] args;
    private Object annotation;
    private IExprFunction implementation;

    public ExprFunction(String name, Expr[] args) {
        super(ExprType.Function);
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int size() {
        return this.args.length;
    }

    public Expr getArg(int index) {
        return this.args[index];
    }

    public Expr[] getArgs() {
        return this.args;
    }

    public void setAnnotation(Object annotation) {
        this.annotation = annotation;
    }

    public Object getAnnotation() {
        return this.annotation;
    }

    public void setImplementation(IExprFunction function) {
        this.implementation = function;
    }

    public IExprFunction getImplementation() {
        return this.implementation;
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        if (this.implementation != null) {
            return this.implementation.evaluate(null, this.args);
        }
        return context.evaluateFunction(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append("(");
        for (int i = 0; i < this.args.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.args[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public void validate() throws ExprException {
        if (this.name == null) {
            throw new ExprException("Function name cannot be empty");
        }
        for (int i = 0; i < this.args.length; ++i) {
            this.args[i].validate();
        }
    }
}

