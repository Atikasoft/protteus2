/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr;

import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.util.SimpleEvaluationContext;

public class Test {

    public static final void main(String[] args) {
        try {
            //String formula3 = "ANIO(FECHAVALOR(V_FECFINPER))";
            //String formula3 = "(MES(FECHAVALOR(C_FEMADECU)) - MES(FECHAVALOR(V_FECING)))*30 ";
//            String formula3 = "SI(FECHAVALOR(V_FECING)> FECHAVALOR(C_FEMIDECU),(MES(FECHAVALOR(C_FEMADECU)) - MES(FECHAVALOR(V_FECING)))*30 +"
//                    + " (30 - DIA(FECHAVALOR(V_FECING))+1),366)  ";
            String formula3 = "FECHAVALOR(FEFI) - SUMARMES(FEINI,12) + 1";
            formula3 = formula3.replaceAll(" +", "");
            SimpleEvaluationContext context = new SimpleEvaluationContext();
            ExprString ini = new ExprString("01-04-2016"); //42461.0
            ExprString fin = new ExprString("30-04-2017"); //
            
            context.setVariable("FEINI", ini);
            context.setVariable("FEFI", fin);
            Expr e = ExprParser.parse(formula3);
            if (e instanceof ExprEvaluatable) {
                System.out.println(e);
                e = ((ExprEvaluatable) e).evaluate(context);
            }
            System.out.println("e************:" + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
