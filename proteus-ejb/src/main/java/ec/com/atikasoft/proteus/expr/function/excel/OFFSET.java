/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprError;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprMissing;
import ec.com.atikasoft.proteus.expr.ExprVariable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.engine.GridReference;
import ec.com.atikasoft.proteus.expr.engine.Range;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;

public class OFFSET
extends AbstractFunction {
    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        Expr w;
        Expr h;
        this.assertMinArgCount(args, 3);
        this.assertMaxArgCount(args, 5);
        Expr r = args[0];
        if (!(r instanceof ExprVariable)) {
            throw new ExprException("First argument to OFFSET not a reference");
        }
        ExprVariable ref = (ExprVariable)r;
        Range range = (Range)ref.getAnnotation();
        if (range == null) {
            range = Range.valueOf(ref.getName());
        }
        if (range == null) {
            throw new ExprException("First argument to OFFSET not a reference");
        }
        GridReference gf = range.getDimension1();
        int x = gf.getColumn();
        int y = gf.getRow();
        int rows = this.asInteger(context, args[1], true);
        int cols = this.asInteger(context, args[2], true);
        int height = 1;
        int width = 1;
        if (args.length > 3 && !((h = args[3]) instanceof ExprMissing)) {
            height = this.asInteger(context, h, true);
        }
        if (height < 1) {
            return ExprError.VALUE;
        }
        if (args.length > 4 && !((w = args[4]) instanceof ExprMissing)) {
            width = this.asInteger(context, w, true);
        }
        if (width < 1) {
            return ExprError.VALUE;
        }
        GridReference dim1 = new GridReference(x + cols, y + rows);
        if (dim1.getColumn() < 1 || dim1.getRow() < 1) {
            return ExprError.REF;
        }
        GridReference dim2 = null;
        if (height > 1 || width > 1) {
            dim2 = new GridReference(x + cols + width - 1, y + rows + height - 1);
        }
        Range result = new Range(range.getNamespace(), dim1, dim2);
        ExprVariable var = new ExprVariable(result.toString());
        var.setAnnotation(result);
        return var;
    }
}

