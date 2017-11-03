/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.ArrayList;
import java.util.Iterator;

public class CSV {
    public static String[] parseLine(String line) {
        return CSV.parseLine(line, ',', false);
    }

    public static String[] parseLine(String line, char delim, boolean hasQuotes) {
        ArrayList<String> items = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean inQuote = false;
        int length = line.length();
        for (int i = 0; i < length; ++i) {
            char c = line.charAt(i);
            if (c == delim && !inQuote) {
                items.add(sb.toString());
                sb.setLength(0);
                continue;
            }
            if (hasQuotes && c == '\"') {
                inQuote = !inQuote;
                continue;
            }
            sb.append(c);
        }
        items.add(sb.toString());
        return items.toArray(new String[0]);
    }

    public static String toCSV(Iterator it) {
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
            if (!it.hasNext()) continue;
            sb.append(',');
        }
        return sb.toString();
    }

    public static String toCSV(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(arr[i]);
            if (i >= arr.length - 1) continue;
            sb.append(",");
        }
        return sb.toString();
    }
}

