/*
 *  Test.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Nov 20, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.utilitarios;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.util.SimpleEvaluationContext;
import java.io.IOException;
import java.text.ParseException;

/**
 * Clase de pruebas basica.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class Test {

    /**
     * Constructor sin argumentos.
     */
    public Test() {
        super();
    }

    public static final void main(String args[]) throws ParseException, IOException {
        try {
            testFormulas();

        } catch (Exception e) {
        }
    }

    private static void testFormulas() {
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
