/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.function.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.function.AbstractFunction;
import static ec.com.atikasoft.proteus.expr.function.AbstractFunction.evalArg;
import ec.com.atikasoft.proteus.expr.util.ExcelDate;

public class ADDMONTH
        extends AbstractFunction {

    @Override
    public Expr evaluate(IEvaluationContext context, Expr[] args) throws ExprException {
        try {
            this.assertArgCount(args, 2);
            Expr eDate = evalArg(context, args[0]);
            Expr amount = evalArg(context, args[1]);
            Calendar date = Calendar.getInstance();
            date.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(eDate.toString()));
            date.add(Calendar.MONTH, Integer.valueOf(amount.toString()));
            return new ExprDouble(ExcelDate.toExcelDate(date.getTimeInMillis()));
        } catch (ParseException ex) {
            return new ExprDouble(-1);
        }
    }
}
