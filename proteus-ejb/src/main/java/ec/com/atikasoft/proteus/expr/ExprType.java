/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

public enum ExprType {
    Double,
    Integer,
    Boolean,
    String,
    Addition,
    Subtraction,
    Multiplication,
    Division,
    Function,
    Variable,
    Expression,
    StringConcat,
    Error,
    Array,
    Missing,
    LessThan,
    LessThanOrEqualTo,
    GreaterThan,
    GreaterThanOrEqualTo,
    NotEqual,
    Equal,
    Power;
    

    private ExprType() {
    }
}

