/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class IO {
    public static String toString(Reader r) throws IOException {
        StringWriter sw = new StringWriter();
        IO.copy(r, sw, true);
        return sw.toString();
    }

    public static String toString(InputStream is) throws IOException {
        return IO.toString(new InputStreamReader(is));
    }

    public static String toString(File f) throws IOException {
        return IO.toString(new FileReader(f));
    }

    public static void copy(Reader r, Writer w, boolean close) throws IOException {
        char[] buf = new char[4096];
        int len = 0;
        while ((len = r.read(buf)) > 0) {
            w.write(buf, 0, len);
        }
        if (close) {
            r.close();
            w.close();
        }
    }

    public static void copy(InputStream r, OutputStream w, boolean close) throws IOException {
        byte[] buf = new byte[4096];
        int len = 0;
        while ((len = r.read(buf)) > 0) {
            w.write(buf, 0, len);
        }
        if (close) {
            r.close();
            w.close();
        }
    }

    public static String getExtension(File f) {
        if (f == null) {
            return null;
        }
        String n = f.getName();
        int idx = n.lastIndexOf(46);
        if (idx == -1) {
            return null;
        }
        return n.substring(idx + 1);
    }

    public static String removeExtension(File f) {
        if (f == null) {
            return null;
        }
        String n = f.getName();
        int idx = n.lastIndexOf(46);
        if (idx == -1) {
            return n;
        }
        return n.substring(0, idx);
    }

    public static byte[] toBytes(File file) throws IOException {
        byte[] b = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(b);
        fis.close();
        return b;
    }

    public static BufferedReader openUrl(String url) throws IOException {
        URL u = new URL(url);
        return new BufferedReader(new InputStreamReader(u.openStream()));
    }

    public static String[] readLines(Reader r) throws IOException {
        ArrayList<String> l = new ArrayList<String>();
        BufferedReader br = new BufferedReader(r);
        String line = null;
        while ((line = br.readLine()) != null) {
            l.add(line);
        }
        return l.toArray(new String[0]);
    }

    public static String[] readLines(InputStream is) throws IOException {
        return IO.readLines(new InputStreamReader(is));
    }

    public static String[] readLines(Class clazz, String resource) throws IOException {
        return IO.readLines(new InputStreamReader(clazz.getResourceAsStream(resource)));
    }

    public static String[] toArray(StringTokenizer st) {
        ArrayList<String> l = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            l.add(st.nextToken());
        }
        return l.toArray(new String[0]);
    }
}

