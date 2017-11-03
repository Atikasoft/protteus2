/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprNumber;
import ec.com.atikasoft.proteus.expr.ExprType;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.AbstractCalculationEngine;
import ec.com.atikasoft.proteus.expr.engine.EngineProvider;
import ec.com.atikasoft.proteus.expr.engine.GridMap;
import ec.com.atikasoft.proteus.expr.engine.Range;

public class IterationEngine
extends AbstractCalculationEngine {
    private int maxIterations = 100;
    private double maxChange = 1.0E-4;
    private Map<Range, Expr> inputExprs = new HashMap<Range, Expr>();

    public IterationEngine(EngineProvider provider) {
        super(provider);
    }

    public void setMaxIterations(int iterations) {
        this.maxIterations = iterations;
    }

    public void setMaxChange(double change) {
        this.maxChange = Math.abs(change);
    }

    @Override
    public void calculate(boolean force) throws ExprException {
        if (this.autoCalculate && !force) {
            return;
        }
        this.calc();
    }

    private void calc() throws ExprException {
        Set<Range> inputs = this.getInputRanges();
        HashMap<Range, Expr> valueChanges = new HashMap<Range, Expr>();
        for (int i = 0; i < this.maxIterations; ++i) {
            double change = 0.0;
            for (Range r : inputs) {
                Expr e = this.inputExprs.get(r);
                if (!(e instanceof ExprEvaluatable)) continue;
                e = ((ExprEvaluatable)e).evaluate(this);
                this.values.put(r, e);
                valueChanges.put(r, e);
                double c = this.findChange(this.values.get(r), e);
                if (c <= change) continue;
                change = c;
            }
            if (change < this.maxChange) break;
        }
        for (Range r : valueChanges.keySet()) {
            this.provider.valueChanged(r, (Expr)valueChanges.get(r));
        }
    }

    private double findChange(Expr old, Expr nu) {
        if (old == null || nu == null) {
            return 0.0;
        }
        if (old.type != nu.type) {
            return 0.0;
        }
        if (nu instanceof ExprNumber) {
            return Math.abs(((ExprNumber)old).doubleValue() - ((ExprNumber)nu).doubleValue());
        }
        if (nu instanceof ExprArray) {
            Expr[] oldA = ((ExprArray)old).getInternalArray();
            Expr[] nuA = ((ExprArray)nu).getInternalArray();
            double change = 0.0;
            for (int i = 0; i < oldA.length && i < nuA.length; ++i) {
                double c = this.findChange(oldA[i], nuA[i]);
                if (c <= change) continue;
                change = c;
            }
            return change;
        }
        return 0.0;
    }

    @Override
    public void set(Range range, String expression) throws ExprException {
        this.validateRange(range);
        if (expression == null) {
            this.rawInputs.remove(range);
            this.inputExprs.remove(range);
            this.values.remove(range);
            this.inputs.remove(range);
            return;
        }
        this.rawInputs.put(range, expression);
        Expr expr = this.parseExpression(expression);
        this.inputExprs.put(range, expr);
        this.provider.inputChanged(range, expr);
        this.inputs.put(range, expr);
        if (expr.evaluatable) {
            Expr eval = ((ExprEvaluatable)expr).evaluate(this);
            this.provider.valueChanged(range, eval);
            this.values.put(range, eval);
        } else {
            this.provider.valueChanged(range, expr);
            this.values.put(range, expr);
        }
        if (this.autoCalculate) {
            this.calc();
        }
    }
}

