/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.parser;

import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprFunction;
import ec.com.atikasoft.proteus.expr.ExprVariable;

public interface IParserVisitor {
    public void annotateVariable(ExprVariable var1) throws ExprException;

    public void annotateFunction(ExprFunction var1) throws ExprException;
}

