/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.parser;

public enum ExprTokenType {
    Decimal,
    Integer,
    String,
    Variable,
    Function,
    Plus,
    Minus,
    Multiply,
    Divide,
    OpenBracket,
    CloseBracket,
    Comma,
    StringConcat,
    GreaterThan,
    GreaterThanOrEqualTo,
    LessThan,
    LessThanOrEqualTo,
    NotEqual,
    Equal,
    OpenBrace,
    CloseBrace,
    SemiColon,
    Power;
    

    private ExprTokenType() {
    }
}

