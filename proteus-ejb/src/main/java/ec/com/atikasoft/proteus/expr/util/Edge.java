/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

public class Edge {
    public final Object source;
    public final Object target;
    public final Object data;

    public Edge(Object source, Object target, Object data) {
        this.source = source;
        this.target = target;
        this.data = data;
    }

    public Edge(Object source, Object target) {
        this.source = source;
        this.target = target;
        this.data = null;
    }

    public boolean equals(Object obj) {
        boolean same;
        if (!(obj instanceof Edge)) {
            return false;
        }
        Edge e = (Edge)obj;
        boolean bl = same = e.source.equals(this.source) && e.target.equals(this.target);
        if (!same) {
            return false;
        }
        if (this.data != null) {
            return this.data.equals(e.data);
        }
        if (e.data != null) {
            return e.data.equals(this.data);
        }
        return true;
    }

    public int hashCode() {
        int h = this.source.hashCode() ^ this.target.hashCode();
        if (this.data != null) {
            h ^= this.data.hashCode();
        }
        return h;
    }

    public String toString() {
        return "[" + this.source + "," + this.target + "]";
    }
}

