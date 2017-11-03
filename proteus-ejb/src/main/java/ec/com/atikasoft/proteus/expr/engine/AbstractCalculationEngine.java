/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.EngineProvider;
import ec.com.atikasoft.proteus.expr.engine.GridMap;
import ec.com.atikasoft.proteus.expr.engine.GridReference;
import ec.com.atikasoft.proteus.expr.engine.Range;
import ec.com.atikasoft.proteus.expr.parser.ExprLexer;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.parser.IParserVisitor;
import ec.com.atikasoft.proteus.expr.util.Exprs;

public abstract class AbstractCalculationEngine
implements IParserVisitor,
IEvaluationContext {
    protected EngineProvider provider;
    protected Map<Range, String> rawInputs = new HashMap<Range, String>();
    protected GridMap inputs = new GridMap();
    protected GridMap values = new GridMap();
    protected Map<String, Range> aliases = new TreeMap<String, Range>();
    protected ExprMissing MISSING = new ExprMissing();
    protected boolean autoCalculate = true;
    protected String namespace;

    public AbstractCalculationEngine(EngineProvider provider) {
        this.provider = provider;
    }

    public EngineProvider getProvider() {
        return this.provider;
    }

    public void setAutoCalculate(boolean auto) {
        this.autoCalculate = auto;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void addAlias(String name, Range range) {
        if (name != null) {
            this.aliases.put(name.toUpperCase(), range);
        }
    }

    public void removeAlias(String name) {
        if (name != null) {
            this.aliases.remove(name.toUpperCase());
        }
    }

    public Range getAlias(String name) {
        if (name == null) {
            return null;
        }
        return this.aliases.get(name.toUpperCase());
    }

    public void set(String name, String expression) throws ExprException {
        this.set(Range.valueOf(name), expression);
    }

    protected abstract void set(Range var1, String var2) throws ExprException;

    public Set<Range> getInputRanges() {
        return this.rawInputs.keySet();
    }

    public String getInput(Range range) {
        return this.rawInputs.get(range);
    }

    public Expr getValue(Range range) {
        return this.values.get(range);
    }

    public abstract void calculate(boolean var1) throws ExprException;

    @Override
    public void annotateFunction(ExprFunction function) throws ExprException {
    }

    @Override
    public void annotateVariable(ExprVariable variable) throws ExprException {
        Range r = null;
        try {
            r = Range.valueOf(variable.getName());
            this.updateAliasedRange(r);
        }
        catch (ExprException e) {
            // empty catch block
        }
        variable.setAnnotation(r);
    }

    @Override
    public Expr evaluateFunction(ExprFunction function) throws ExprException {
        return this.provider.evaluateFunction(this, function);
    }

    @Override
    public Expr evaluateVariable(ExprVariable variable) throws ExprException {
        Range r = (Range)variable.getAnnotation();
        if (r == null) {
            this.provider.validate(variable);
            return this.MISSING;
        }
        String ns = r.getNamespace();
        if (ns != null && !ns.equals(this.namespace)) {
            Expr e = this.provider.evaluateVariable(this, variable);
            if (e == null) {
                e = this.MISSING;
            }
            return e;
        }
        Expr e = this.values.get(r);
        if (e == null && (e = this.provider.evaluateVariable(null, variable)) == null) {
            e = this.MISSING;
        }
        return e;
    }

    protected void updateAliasedRange(Range range) throws ExprException {
        Range dim2A;
        if (range == null) {
            return;
        }
        Range dim1A = this.getAlias(range.getDimension1Name());
        if (dim1A != null) {
            range.setDimension1(dim1A.getDimension1());
        }
        if ((dim2A = this.getAlias(range.getDimension2Name())) != null) {
            range.setDimension2(range.getDimension1());
        }
    }

    protected void validateRange(Range range) throws ExprException {
        this.updateAliasedRange(range);
        GridReference dim1 = range.getDimension1();
        GridReference dim2 = range.getDimension2();
        int x1 = dim1.getColumn();
        int x2 = dim2 == null ? x1 : dim2.getColumn();
        int y1 = dim1.getRow();
        int y2 = dim2 == null ? y1 : dim2.getRow();
        int width = x2 - x1 + 1;
        int height = y2 - y1 + 1;
        if (width <= 0 || height <= 0) {
            throw new ExprException("Invalid range: " + range);
        }
    }

    protected Expr parseExpression(String expression) throws ExprException {
        Expr result;
        if (!expression.startsWith("=")) {
            result = Exprs.parseValue(expression);
        } else {
            expression = expression.substring(1);
            ExprParser p = new ExprParser();
            p.setParserVisitor(this);
            try {
                p.parse(new ExprLexer(expression));
            }
            catch (IOException e) {
                throw new ExprException(e);
            }
            result = p.get();
        }
        return result;
    }
}

