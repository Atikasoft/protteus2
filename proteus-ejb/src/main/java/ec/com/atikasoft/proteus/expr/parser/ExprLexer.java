/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import ec.com.atikasoft.proteus.expr.parser.ExprToken;
import ec.com.atikasoft.proteus.expr.parser.ExprTokenType;
import ec.com.atikasoft.proteus.expr.parser.TokenReader;

public class ExprLexer {
    private TokenReader reader;
    private int lastChar;

    public ExprLexer(BufferedReader reader) {
        this.reader = new TokenReader(reader);
    }

    public ExprLexer(Reader reader) {
        this(new BufferedReader(reader));
    }

    public ExprLexer(String str) {
        this(new StringReader(str));
    }

    public ExprToken next() throws IOException {
        if (this.lastChar == 0 || Character.isWhitespace(this.lastChar)) {
            this.lastChar = this.reader.ignoreWhitespace();
        }
        return this.readToken();
    }

    private ExprToken readToken() throws IOException {
        if (Character.isDigit(this.lastChar)) {
            return this.readNumber();
        }
        switch (this.lastChar) {
            case 34: {
                return this.readString();
            }
            case 40: {
                this.lastChar = 0;
                return ExprToken.OPEN_BRACKET;
            }
            case 41: {
                this.lastChar = 0;
                return ExprToken.CLOSE_BRACKET;
            }
            case 43: {
                this.lastChar = 0;
                return ExprToken.PLUS;
            }
            case 45: {
                this.lastChar = 0;
                return ExprToken.MINUS;
            }
            case 42: {
                this.lastChar = 0;
                return ExprToken.MULTIPLY;
            }
            case 47: {
                this.lastChar = 0;
                return ExprToken.DIVIDE;
            }
            case 44: {
                this.lastChar = 0;
                return ExprToken.COMMA;
            }
            case 38: {
                this.lastChar = 0;
                return ExprToken.STRING_CONCAT;
            }
            case 60: 
            case 61: 
            case 62: {
                return this.readComparisonOperator();
            }
            case 39: {
                return this.readQuotedVariable();
            }
            case 123: {
                this.lastChar = 0;
                return ExprToken.OPEN_BRACE;
            }
            case 125: {
                this.lastChar = 0;
                return ExprToken.CLOSE_BRACE;
            }
            case 59: {
                this.lastChar = 0;
                return ExprToken.SEMI_COLON;
            }
            case 94: {
                this.lastChar = 0;
                return ExprToken.POWER;
            }
            case -1: 
            case 65535: {
                return null;
            }
        }
        if (!Character.isJavaIdentifierStart(this.lastChar)) {
            throw new IOException("Invalid token found: " + this.lastChar);
        }
        return this.readVariableOrFunction();
    }

    private ExprToken readQuotedVariable() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append('\'');
        while (this.lastChar != -1) {
            this.lastChar = this.reader.read();
            if (this.lastChar == 39) {
                this.lastChar = this.reader.read();
                if (this.lastChar != 39) break;
                sb.append('\'');
                continue;
            }
            sb.append((char)this.lastChar);
        }
        sb.append('\'');
        while (this.isVariablePart(this.lastChar)) {
            sb.append((char)this.lastChar);
            this.lastChar = this.reader.read();
        }
        return new ExprToken(ExprTokenType.Variable, sb.toString());
    }

    private ExprToken readVariableOrFunction() throws IOException {
        StringBuilder sb = new StringBuilder();
        while (this.isVariablePart(this.lastChar)) {
            sb.append((char)this.lastChar);
            this.lastChar = this.reader.read();
        }
        if (Character.isWhitespace(this.lastChar)) {
            this.lastChar = this.reader.ignoreWhitespace();
        }
        if (this.lastChar == 40) {
            this.lastChar = 0;
            return new ExprToken(ExprTokenType.Function, sb.toString());
        }
        return new ExprToken(ExprTokenType.Variable, sb.toString());
    }

    private boolean isVariablePart(int lastChar) {
        return Character.isJavaIdentifierPart(lastChar) || lastChar == 33 || lastChar == 58;
    }

    private ExprToken readString() throws IOException {
        String str = this.unescapeString(this.reader);
        this.lastChar = 0;
        return new ExprToken(ExprTokenType.String, str);
    }

    private ExprToken readNumber() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append((char)this.lastChar);
        this.lastChar = this.reader.read();
        boolean decimal = false;
        while (Character.isDigit(this.lastChar) || 46 == this.lastChar) {
            sb.append((char)this.lastChar);
            if (this.lastChar == 46) {
                decimal = true;
            }
            this.lastChar = this.reader.read();
        }
        if (this.lastChar == 69 || this.lastChar == 101) {
            sb.append((char)this.lastChar);
            this.lastChar = this.reader.read();
            if (this.lastChar == 45 || this.lastChar == 43) {
                sb.append((char)this.lastChar);
                this.lastChar = this.reader.read();
            }
            while (Character.isDigit(this.lastChar)) {
                sb.append((char)this.lastChar);
                this.lastChar = this.reader.read();
            }
        }
        String val = sb.toString();
        if (decimal) {
            return new ExprToken(val, Double.parseDouble(val));
        }
        try {
            return new ExprToken(val, Integer.parseInt(val));
        }
        catch (NumberFormatException e) {
            return new ExprToken(val, Double.parseDouble(val));
        }
    }

    public static String escapeString(String str) {
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        block3 : for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            switch (c) {
                case '\"': {
                    sb.append("\"\"");
                    continue block3;
                }
                default: {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private String unescapeString(TokenReader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = '\u0000';
        block3 : while (c != '\"') {
            c = (char)r.read();
            switch (c) {
                case '\"': {
                    int v = r.peek();
                    if (v != 34) continue block3;
                    r.read();
                    sb.append('\"');
                    c = '\u0000';
                    continue block3;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private ExprToken readComparisonOperator() throws IOException {
        int current = this.lastChar;
        int peek = this.reader.peek();
        this.lastChar = 0;
        if (current == 60) {
            if (peek == 61) {
                this.reader.read();
                return ExprToken.LESS_THAN_EQUAL;
            }
            if (peek == 62) {
                this.reader.read();
                return ExprToken.NOT_EQUAL;
            }
            return ExprToken.LESS_THAN;
        }
        if (current == 62) {
            if (peek == 61) {
                this.reader.read();
                return ExprToken.GREATER_THAN_EQUAL;
            }
            return ExprToken.GREATER_THAN;
        }
        if (current == 61) {
            return ExprToken.EQUAL;
        }
        return null;
    }
}

