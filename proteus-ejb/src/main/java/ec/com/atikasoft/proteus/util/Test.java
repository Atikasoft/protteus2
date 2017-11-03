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
package ec.com.atikasoft.proteus.util;

import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import java.io.IOException;
import java.text.ParseException;
import ec.com.atikasoft.proteus.expr.ExprException;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.util.SimpleEvaluationContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static final void main(String args[]) throws ParseException, IOException, ExprException {
        Date fechaIngreso = new SimpleDateFormat("ddMMyyyy").parse("15092016");
        Date fechaProceso = new SimpleDateFormat("ddMMyyyy").parse("30082017");
        System.out.println(fechaIngreso);
        System.out.println(UtilFechas.obtenerMes(fechaIngreso));
        System.out.println(UtilFechas.obtenerMes(fechaProceso));
        System.out.println(UtilFechas.obtenerDiaDelMes(fechaIngreso));
        System.out.println(UtilFechas.obtenerDiaDelMes(fechaProceso));

        if (UtilFechas.obtenerMes(fechaIngreso).compareTo(UtilFechas.obtenerMes(fechaProceso)) == 0
                && UtilFechas.obtenerDiaDelMes(fechaProceso).compareTo(UtilFechas.obtenerDiaDelMes(
                                fechaIngreso)) == 0) {
            System.out.println("entro");
        } else {
            System.out.println("no entro");
        }

//        Date f1 = new SimpleDateFormat("dd/MM/yyyy").parse("03/07/2017");
//        Date f2 = new SimpleDateFormat("dd/MM/yyyy").parse("17/07/2017");
//        System.out.println(UtilFechas.calcularDiferenciaDiasEntreFechas(f1, f2));
//        Date f1 = new SimpleDateFormat("dd/MM/yyyy").parse("05/04/2016");
//        Date f2 = new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2017");
//        System.out.println(UtilFechas.calculaNumMesesEntreDosFechas(f1,f2));
        //testFormulas();
//        Date ultimaFecha = new SimpleDateFormat("dd/MM/yyyy").parse("31/05/2017");
//        Date fechaProceso = UtilFechas.truncarFecha(UtilFechas.sumarDias(ultimaFecha, 2));
//        System.out.println(fechaProceso);
//
//        String claveEncriptada = DigestUtils.md5Hex("mq123mq");
//        System.out.println(claveEncriptada);
//
//        Date fechaInicio = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/2016");
//        Date fechafin = new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2017");
//
//        double r = UtilFechas.calculaNumMesesEntreDosFechas(fechaInicio, fechafin);
//        System.out.println("meses = " + r);
//        long ini = new SimpleDateFormat("ddMMyyyy").parse("01011970").getTime();
//        long fin = new SimpleDateFormat("ddMMyyyy").parse("01011980").getTime();
//        System.out.println(ini+":"+fin+":"+(fin-ini)/3600/60/60/24);
//        SimpleEvaluationContext context = new SimpleEvaluationContext();
//        StringBuilder formula = new StringBuilder("(SI(V_NUMDIALABPV=0,V_RMU,0)  ) * 0.0025 * \n"
//                + " (SI(FECHAVALOR(V_FECING)<FECHAVALOR(C_FEINSUANCT),"
//                + "SI(MONTH(HOY())>=MONTH(FECHAVALOR(C_FEINSUANCT)) , YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT)) ,YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT))-1),\n"
//                + "SI(MONTH(HOY())>=MONTH(FECHAVALOR(V_FECING)) , YEAR(HOY())-YEAR(FECHAVALOR(V_FECING)) ,YEAR(HOY())-YEAR(FECHAVALOR(V_FECING))-1)     ))");
//        StringBuilder formula = new StringBuilder("SI(FECHAVALOR(V_FECING)<FECHAVALOR(C_FEINSUANCT),"
//                + "SI(MONTH(HOY())>=MONTH(FECHAVALOR(C_FEINSUANCT)) , YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT)) ,YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT))-1),\n"
//                + "SI(MONTH(HOY())>=MONTH(FECHAVALOR(V_FECING)) , YEAR(HOY())-YEAR(FECHAVALOR(V_FECING)) ,YEAR(HOY())-YEAR(FECHAVALOR(V_FECING))-1))");
        //     String formula = "SI(\n" +
//"   FECHAVALOR(V_FECING)<FECHAVALOR(C_FEINSUANCT),\n" +
//"   SI(\n" +
//"      MONTH(HOY())>=MONTH(FECHAVALOR(C_FEINSUANCT)) , \n" +
//"      YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT)) ,\n" +
//"      YEAR(HOY())-YEAR(FECHAVALOR(C_FEINSUANCT))-1\n" +
//"     ),\n" +
//"   SI(\n" +
//"       MONTH(HOY())>=MONTH(FECHAVALOR(V_FECING)) , \n" +
//"       YEAR(HOY())-YEAR(FECHAVALOR(V_FECING)) ,\n" +
//"       YEAR(HOY())-YEAR(FECHAVALOR(V_FECING))-1\n" +
//"    )\n" +
//" )";
//        context.setVariable("V_FECING", new ExprString("01/01/2004"));
//        context.setVariable("C_FEINSUANCT", new ExprString("01/01/2004"));
//        context.setVariable("V_FECING", new ExprString("11-11-2003"));
//        context.setVariable("C_FEINSUANCT", new ExprString("01-01-2004"));
//
//        Expr e = ExprParser.parse(formula.toString().replaceAll(" +", ""));
//        e = ((ExprEvaluatable) e).evaluate(context);
//        System.out.println(e);
    }

    private static void testFormulas() {
        try {
            //String formula3 = "ANIO(FECHAVALOR(V_FECFINPER))";
            //String formula3 = "(MES(FECHAVALOR(C_FEMADECU)) - MES(FECHAVALOR(V_FECING)))*30 ";
//            String formula3 = "SI(FECHAVALOR(V_FECING)> FECHAVALOR(C_FEMIDECU),(MES(FECHAVALOR(C_FEMADECU)) - MES(FECHAVALOR(V_FECING)))*30 +"
//                    + " (30 - DIA(FECHAVALOR(V_FECING))+1),366)  ";
            String formula3 = "TRUNC(5.4564) ";
//            String formula3 = "((30 - DAY(FECHAVALOR(FEINI)) +1) * 1000 * 0.0833)/30";
            formula3 = formula3.replaceAll(" +", "");
            SimpleEvaluationContext context = new SimpleEvaluationContext();
            ExprString ini = new ExprString("05-01-2016"); //42461.0
            ExprString fin = new ExprString("31-01-2017"); //

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
