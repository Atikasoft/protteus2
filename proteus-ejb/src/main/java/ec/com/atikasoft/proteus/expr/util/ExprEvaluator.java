/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.IEvaluationContext;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.util.Exprs;
import ec.com.atikasoft.proteus.expr.util.SimpleEvaluationContext;

public class ExprEvaluator {
    public static void main(String[] args) throws Exception {
        SimpleEvaluationContext context = new SimpleEvaluationContext();
        System.out.println("Expr Evaluator v1.0");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        block2 : do {
            try {
                do {
                    System.out.print(">");
                    String line = br.readLine();
                    if (line == null) break block2;
                    Expr e = ExprParser.parse(line);
                    Exprs.toUpperCase(e);
                    if (e instanceof ExprEvaluatable) {
                        e = ((ExprEvaluatable)e).evaluate(context);
                    }
                    System.out.println(e);
                } while (true);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        } while (true);
    }
}

