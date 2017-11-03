/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import ec.com.atikasoft.proteus.expr.util.ExcelDate;

public class DATEVALUE extends AbstractFunction {

    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        try {
            this.assertArgCount(args, 1);
            Expr eF = evalArg(context, args[0]);
            return new ExprDouble(ExcelDate.toExcelDate(new SimpleDateFormat("dd-MM-yyyy").parse(eF.toString()).getTime()));
        } catch (ParseException ex) {
            return new ExprDouble(-1);
        }
    }
}
