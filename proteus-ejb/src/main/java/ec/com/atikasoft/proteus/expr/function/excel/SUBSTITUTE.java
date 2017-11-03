/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class SUBSTITUTE
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        this.assertMinArgCount(args, 3);
        this.assertMaxArgCount(args, 4);
        String str = this.asString(context, args[0], false);
        String old = this.asString(context, args[1], false);
        String nu = this.asString(context, args[2], false);
        int inum = 1;
        if (args.length == 4) {
            inum = this.asInteger(context, args[3], true);
        }
        int idx = str.indexOf(old);
        for (int i = 1; i < inum && idx != -1; ++i) {
            idx = str.indexOf(old, idx + 1);
        }
        if (idx != -1) {
            StringBuilder sb = new StringBuilder();
            if (idx > 0) {
                sb.append(str.substring(0, idx));
            }
            sb.append(nu);
            sb.append(str.substring(idx + old.length()));
            return new ExprString(sb.toString());
        }
        return new ExprString(str);
    }
}

