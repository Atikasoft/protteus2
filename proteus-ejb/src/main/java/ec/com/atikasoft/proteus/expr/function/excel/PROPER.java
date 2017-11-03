/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class PROPER
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertArgCount(args, 1);
        String str = this.asString(context, args[0], false);
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        boolean proper = true;
        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(proper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                proper = false;
                continue;
            }
            proper = true;
            sb.append(c);
        }
        return new ExprString(sb.toString());
    }
}

