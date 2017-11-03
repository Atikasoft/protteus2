/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.engine;

import java.util.Map;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.AbstractCalculationEngine;
import ec.com.atikasoft.proteus.expr.engine.EngineProvider;
import ec.com.atikasoft.proteus.expr.engine.GridMap;
import ec.com.atikasoft.proteus.expr.engine.Range;
import ec.com.atikasoft.proteus.expr.parser.IParserVisitor;
import ec.com.atikasoft.proteus.expr.util.Edge;
import ec.com.atikasoft.proteus.expr.util.Graph;
import ec.com.atikasoft.proteus.expr.util.GraphCycleException;
import ec.com.atikasoft.proteus.expr.util.GraphTraversalListener;

public class DependencyEngine
extends AbstractCalculationEngine
implements IParserVisitor,
IEvaluationContext,
GraphTraversalListener {
    private Graph graph = new Graph();

    public DependencyEngine(EngineProvider provider) {
        super(provider);
        this.graph.setIncludeEdges(false);
    }

    @Override
    public void calculate(boolean force) throws ExprException {
        if (this.autoCalculate && !force) {
            return;
        }
        this.graph.sort();
//        for (Range r : this.graph) {
//            Expr input = this.inputs.get(r);
//            if (!(input instanceof ExprEvaluatable)) continue;
//            Expr eval = ((ExprEvaluatable)input).evaluate(this);
//            this.provider.valueChanged(r, eval);
//            this.values.put(r, eval);
//        }
    }

    @Override
    public void set(Range range, String expression) throws ExprException {
        this.validateRange(range);
        if (expression == null) {
            this.rawInputs.remove(range);
            this.values.remove(range);
            this.inputs.remove(range);
            this.updateDependencies(range, null);
            return;
        }
        this.rawInputs.put(range, expression);
        Expr expr = this.parseExpression(expression);
        this.updateDependencies(range, expr);
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
            this.graph.traverse(range, this);
        }
    }

    private void updateDependencies(Range range, Expr expr) throws ExprException {
        ExprVariable[] vars;
        this.graph.clearInbounds(range);
        for (ExprVariable var : vars = ExprVariable.findVariables(expr)) {
            Range source = (Range)var.getAnnotation();
            try {
                this.addDependencies(source, range);
                continue;
            }
            catch (GraphCycleException ex) {
                for (ExprVariable v : vars) {
                    this.removeDependencies((Range)v.getAnnotation(), range);
                }
                throw new ExprException(ex);
            }
        }
    }

    private void addDependencies(Range source, Range target) throws GraphCycleException {
        if (source.isArray()) {
            Range[] r;
            for (Range rs : r = source.split()) {
                this.graph.add(new Edge(rs, target));
            }
        } else {
            this.graph.add(new Edge(source, target));
        }
    }

    private void removeDependencies(Range source, Range target) {
        if (source.isArray()) {
            Range[] r;
            for (Range rs : r = source.split()) {
                this.graph.remove(new Edge(rs, target));
            }
        } else {
            this.graph.remove(new Edge(source, target));
        }
    }

    @Override
    public void traverse(Object node) {
        Range r = (Range)node;
        Expr input = this.inputs.get(r);
        if (input instanceof ExprEvaluatable) {
            try {
                Expr eval = ((ExprEvaluatable)input).evaluate(this);
                this.provider.valueChanged(r, eval);
                this.values.put(r, eval);
            }
            catch (ExprException e) {
                e.printStackTrace();
            }
        }
    }
}

