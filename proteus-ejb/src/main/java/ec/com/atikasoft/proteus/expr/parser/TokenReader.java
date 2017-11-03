/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.parser;

import java.io.IOException;
import java.io.Reader;

public class TokenReader
extends Reader {
    private Reader r;
    private int peek = -1;

    public TokenReader(Reader r) {
        this.r = r;
    }

    @Override
    public void close() throws IOException {
        this.r.close();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (this.peek != -1) {
            int read = 1;
            cbuf[off] = (char)this.peek;
            if (len > 1) {
                read = this.r.read(cbuf, off + 1, len - 1);
            }
            if (read != -1) {
                ++read;
            }
            this.peek = -1;
            return read;
        }
        return this.r.read(cbuf, off, len);
    }

    public char ignoreWhitespace() throws IOException {
        char c;
        if (this.peek != -1 && !Character.isWhitespace(this.peek)) {
            char ret = (char)this.peek;
            this.peek = -1;
            return ret;
        }
        while (Character.isWhitespace(c = (char)this.r.read())) {
        }
        return c;
    }

    public int peek() throws IOException {
        if (this.peek != -1) {
            return this.peek;
        }
        this.peek = this.read();
        return this.peek;
    }

    public String readUntil(char token) throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = '\u0000';
        while ((c = (char)this.r.read()) != token) {
            sb.append(c);
        }
        return sb.toString();
    }
}

