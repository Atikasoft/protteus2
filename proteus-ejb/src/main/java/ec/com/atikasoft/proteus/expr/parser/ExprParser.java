/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.parser;

import java.io.IOException;
import java.util.ArrayList;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprAddition;
import ec.com.atikasoft.proteus.expr.ExprArray;
import ec.com.atikasoft.proteus.expr.ExprDivision;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEqual;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprExpression;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprGreaterThan;
import ec.com.atikasoft.proteus.expr.ExprGreaterThanOrEqualTo;
import ec.com.atikasoft.proteus.expr.ExprInteger;
import ec.com.atikasoft.proteus.expr.ExprLessThan;
import ec.com.atikasoft.proteus.expr.ExprLessThanOrEqualTo;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprMultiplication;
import ec.com.atikasoft.proteus.expr.ExprNotEqual;
import ec.com.atikasoft.proteus.expr.ExprPower;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.ExprStringConcat;
import ec.com.atikasoft.proteus.expr.ExprSubtraction;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IBinaryOperator;

public class ExprParser {
    private Expr current;
    private IParserVisitor visitor;

    public static Expr parse(String text) throws IOException, ExprException {
        ExprParser p = new ExprParser();
        p.parse(new ExprLexer(text));
        return p.get();
    }

    public void setParserVisitor(IParserVisitor visitor) {
        this.visitor = visitor;
    }

    public void parse(ExprLexer lexer) throws IOException, ExprException {
        ExprToken e = null;
        while ((e = lexer.next()) != null) {
            this.parseToken(lexer, e);
        }
    }

    private void parseToken(ExprLexer lexer, ExprToken token) throws ExprException, IOException {
        switch (token.type) {
            case Plus: 
            case Minus: 
            case Multiply: 
            case Divide: 
            case Power: 
            case StringConcat: 
            case LessThan: 
            case LessThanOrEqualTo: 
            case GreaterThan: 
            case GreaterThanOrEqualTo: 
            case Equal: 
            case NotEqual: {
                this.parseOperator(token);
                break;
            }
            case Decimal: 
            case Integer: 
            case String: 
            case Variable: {
                this.parseValue(token);
                break;
            }
            case OpenBracket: {
                this.parseExpression(lexer);
                break;
            }
            case Function: {
                this.parseFunction(token, lexer);
                break;
            }
            case OpenBrace: {
                this.parseArray(lexer);
                break;
            }
            default: {
                throw new ExprException("Unexpected " + (Object)((Object)token.type) + " found");
            }
        }
    }

    private void parseFunction(ExprToken token, ExprLexer lexer) throws ExprException, IOException {
        Expr c = this.current;
        this.current = null;
        ExprToken e = null;
        ArrayList<Expr> args = new ArrayList<Expr>();
        while ((e = lexer.next()) != null) {
            if (e.type.equals((Object)ExprTokenType.Comma)) {
                if (this.current == null) {
                    args.add(ExprMissing.MISSING);
                } else {
                    args.add(this.current);
                }
                this.current = null;
                continue;
            }
            if (e.type.equals((Object)ExprTokenType.CloseBracket)) {
                if (this.current != null) {
                    args.add(this.current);
                }
                this.current = c;
                break;
            }
            this.parseToken(lexer, e);
        }
        ExprFunction f = new ExprFunction(token.val, args.toArray(new Expr[0]));
        if (this.visitor != null) {
            this.visitor.annotateFunction(f);
        }
        this.setValue(f);
    }

    private void parseExpression(ExprLexer lexer) throws IOException, ExprException {
        Expr c = this.current;
        this.current = null;
        ExprToken e = null;
        while ((e = lexer.next()) != null) {
            if (e.type.equals((Object)ExprTokenType.CloseBracket)) {
                Expr t = this.current;
                this.current = c;
                this.setValue(new ExprExpression(t));
                break;
            }
            this.parseToken(lexer, e);
        }
    }

    private void parseArray(ExprLexer lexer) throws ExprException, IOException {
        Expr c = this.current;
        this.current = null;
        ExprToken e = null;
        int cols = -1;
        int count = 0;
        ArrayList<Expr> args = new ArrayList<Expr>();
        while ((e = lexer.next()) != null) {
            if (e.type.equals((Object)ExprTokenType.Comma)) {
                if (this.current == null) {
                    throw new ExprException("Arrays cannot contain empty values");
                }
                args.add(this.current);
                this.current = null;
                ++count;
                continue;
            }
            if (e.type.equals((Object)ExprTokenType.SemiColon)) {
                if (this.current == null) {
                    throw new ExprException("Arrays cannot contain empty values");
                }
                args.add(this.current);
                this.current = null;
                if (++count == 0) {
                    throw new ExprException("Array rows must contain at least one element");
                }
                if (cols != -1 && count != cols) {
                    throw new ExprException("Array rows must be equal sizes");
                }
                cols = count;
                count = 0;
                continue;
            }
            if (e.type.equals((Object)ExprTokenType.CloseBrace)) {
                if (this.current != null) {
                    args.add(this.current);
                }
                this.current = c;
                break;
            }
            this.parseToken(lexer, e);
        }
        int rows = 1;
        if (cols == -1) {
            cols = args.size();
        } else {
            rows = args.size() / cols;
        }
        ExprArray a = new ExprArray(rows, cols);
        for (int i = 0; i < args.size(); ++i) {
            a.set(0, i, (Expr)args.get(i));
        }
        this.setValue(a);
    }

    private void parseValue(ExprToken e) throws ExprException {
        Expr value = null;
        switch (e.type) {
            case Decimal: {
                value = new ExprDouble(e.doubleValue);
                break;
            }
            case Integer: {
                value = new ExprInteger(e.integerValue);
                break;
            }
            case String: {
                value = new ExprString(e.val);
                break;
            }
            case Variable: {
                value = new ExprVariable(e.val);
                if (this.visitor == null) break;
                this.visitor.annotateVariable((ExprVariable)value);
            }
        }
        this.setValue(value);
    }

    private void setValue(Expr value) throws ExprException {
        Expr rhs;
        if (this.current == null) {
            this.current = value;
            return;
        }
        Expr c = this.current;
        do {
            if (!(c instanceof IBinaryOperator)) {
                throw new ExprException("Expected operator not found");
            }
            rhs = ((IBinaryOperator)((Object)c)).getRHS();
            if (rhs != null) continue;
            ((IBinaryOperator)((Object)c)).setRHS(value);
            return;
        } while ((c = rhs) != null);
        throw new ExprException("Unexpected token found");
    }

    private void parseOperator(ExprToken e) throws ExprException {
        switch (e.type) {
            case Plus: {
                Expr lhs = this.current;
                this.current = new ExprAddition(lhs, null);
                break;
            }
            case Minus: {
                Expr lhs = this.current;
                this.current = new ExprSubtraction(lhs, null);
                break;
            }
            case Multiply: {
                this.parseMultiplyDivide(new ExprMultiplication(null, null));
                break;
            }
            case Divide: {
                this.parseMultiplyDivide(new ExprDivision(null, null));
                break;
            }
            case Power: {
                this.parseMultiplyDivide(new ExprPower(null, null));
                break;
            }
            case StringConcat: {
                this.parseMultiplyDivide(new ExprStringConcat(null, null));
                break;
            }
            case LessThan: {
                this.current = new ExprLessThan(this.current, null);
                break;
            }
            case LessThanOrEqualTo: {
                this.current = new ExprLessThanOrEqualTo(this.current, null);
                break;
            }
            case GreaterThan: {
                this.current = new ExprGreaterThan(this.current, null);
                break;
            }
            case GreaterThanOrEqualTo: {
                this.current = new ExprGreaterThanOrEqualTo(this.current, null);
                break;
            }
            case NotEqual: {
                this.current = new ExprNotEqual(this.current, null);
                break;
            }
            case Equal: {
                this.current = new ExprEqual(this.current, null);
                break;
            }
            default: {
                throw new ExprException("Unhandled operator type: " + (Object)((Object)e.type));
            }
        }
    }

    private void parseMultiplyDivide(IBinaryOperator md) throws ExprException {
        if (this.current == null) {
            throw new ExprException("Unexpected null token");
        }
        Expr c = this.current;
        Expr prev = null;
        while (c != null) {
            if (c instanceof ExprAddition || c instanceof ExprSubtraction) {
                prev = c;
                c = ((IBinaryOperator)((Object)c)).getRHS();
                continue;
            }
            if (prev == null) {
                md.setLHS(this.current);
                this.current = (Expr)((Object)md);
                break;
            }
            IBinaryOperator b = (IBinaryOperator)((Object)prev);
            md.setLHS(b.getRHS());
            b.setRHS((Expr)((Object)md));
            break;
        }
    }

    public Expr get() {
        return this.current;
    }

}

