/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import java.util.ArrayList;
import java.util.List;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprExpression;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IBinaryOperator;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;

public class ExprVariable
extends ExprEvaluatable {
    private String name;
    private Object annotation;
    private Expr constantValue;

    public ExprVariable(String name) {
        super(ExprType.Variable);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnnotation(Object annotation) {
        this.annotation = annotation;
    }

    public Object getAnnotation() {
        return this.annotation;
    }

    public void setConstantValue(Expr value) {
        this.constantValue = value;
    }

    public Expr getConstantValue() {
        return this.constantValue;
    }

    @Override
    public Expr evaluate(IEvaluationContext context) throws ExprException {
        if (this.constantValue != null) {
            return this.constantValue;
        }
        return context.evaluateVariable(this);
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExprVariable)) {
            return false;
        }
        ExprVariable ev = (ExprVariable)obj;
        return ev.name.equals(this.name);
    }

    public static ExprVariable[] findVariables(Expr expr) {
        ArrayList<ExprVariable> vars = new ArrayList<ExprVariable>();
        ExprVariable.findVariables(expr, vars);
        return vars.toArray(new ExprVariable[0]);
    }

    public static void findVariables(Expr expr, List<ExprVariable> vars) {
        if (expr instanceof ExprFunction) {
            ExprFunction f = (ExprFunction)expr;
            for (int i = 0; i < f.size(); ++i) {
                ExprVariable.findVariables(f.getArg(i), vars);
            }
        } else if (expr instanceof ExprExpression) {
            ExprVariable.findVariables(((ExprExpression)expr).getChild(), vars);
        } else if (expr instanceof IBinaryOperator) {
            IBinaryOperator bo = (IBinaryOperator)((Object)expr);
            ExprVariable.findVariables(bo.getLHS(), vars);
            ExprVariable.findVariables(bo.getRHS(), vars);
        } else if (expr instanceof ExprVariable) {
            vars.add((ExprVariable)expr);
        }
    }

    @Override
    public void validate() throws ExprException {
        if (this.name == null) {
            throw new ExprException("Variable name is empty");
        }
    }
}

