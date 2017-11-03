/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph implements Iterable {
    private boolean wantsEdges = true;
    private Set nodes = new HashSet();
    private Set edges = new HashSet();
    private Map outbounds = new HashMap();
    private Map inbounds = new HashMap();
    private List ordered = null;
    private Set traversed = null;

    public void setIncludeEdges(boolean include) {
        this.wantsEdges = include;
    }

    public void add(Object node) {
        this.nodes.add(node);
    }

    public Set getInbounds(Object node) {
        return (Set)this.inbounds.get(node);
    }

    public Set getOutbounds(Object node) {
        return (Set)this.outbounds.get(node);
    }

    public void clearOutbounds(Object node) {
        Set s = (Set)this.outbounds.get(node);
        if (s != null) {
            Iterator i = s.iterator();
            while (i.hasNext()) {
                this.remove((Edge)i.next());
            }
        }
    }

    public void clearInbounds(Object node) {
        Set s = (Set)this.inbounds.get(node);
        if (s != null) {
            Iterator i = s.iterator();
            while (i.hasNext()) {
                this.remove((Edge)i.next());
            }
        }
    }

    public void remove(Object node) {
        this.nodes.remove(node);
        this.clearInbounds(node);
        this.clearOutbounds(node);
    }

    public void add(Edge e) throws GraphCycleException {
        this.checkCycle(e);
        this.nodes.add(e.source);
        this.nodes.add(e.target);
        this.edges.add(e);
        HashSet<Edge> in = (HashSet<Edge>)this.inbounds.get(e.target);
        if (in == null) {
            in = new HashSet<Edge>();
            this.inbounds.put(e.target, in);
        }
        in.add(e);
        HashSet<Edge> out = (HashSet<Edge>)this.outbounds.get(e.source);
        if (out == null) {
            out = new HashSet<Edge>();
            this.outbounds.put(e.source, out);
        }
        out.add(e);
    }

    public void checkCycle(Edge e) throws GraphCycleException {
        HashSet<Object> visited = new HashSet<Object>();
        visited.add(e.source);
        this.checkCycle(e, visited);
    }

    private void checkCycle(Edge e, HashSet visited) throws GraphCycleException {
        if (visited.contains(e.target)) {
            throw new GraphCycleException("Circular reference found: " + e.source + " - " + e.target);
        }
        visited.add(e.target);
        Set out = (Set)this.outbounds.get(e.target);
        if (out != null) {
            Iterator i = out.iterator();
            while (i.hasNext()) {
                this.checkCycle((Edge)i.next(), visited);
            }
        }
    }

    public void remove(Edge e) {
        Set out;
        this.edges.remove(e);
        Set in = (Set)this.inbounds.get(e.target);
        if (in != null) {
            in.remove(e);
        }
        if ((out = (Set)this.outbounds.get(e.source)) != null) {
            out.remove(e);
        }
    }

    public void sort() {
        this.ordered = new ArrayList();
        this.traversed = new HashSet();
        Iterator i = this.nodes.iterator();
        HashSet remains = new HashSet(this.nodes);
        while (i.hasNext()) {
            Object o = i.next();
            Set in = (Set)this.inbounds.get(o);
            if (in != null && !in.isEmpty()) continue;
            this.traverse(o);
            remains.remove(o);
        }
        for (Object o : remains) {
            if (this.traversed.contains(o)) continue;
            this.traverse(o);
        }
    }

    private void traverse(Object node) {
        Set out;
        Set in = (Set)this.inbounds.get(node);
        if (in != null) {
//            for (Edge e : in) {
//                if (!this.traversed.contains(e.source)) {
//                    return;
//                }
//                if (!this.wantsEdges) continue;
//                this.ordered.add(e);
//            }
        }
        if (!this.traversed.contains(node)) {
            this.traversed.add(node);
            this.ordered.add(node);
        }
        if ((out = (Set)this.outbounds.get(node)) == null || out.isEmpty()) {
            return;
        }
        HashSet<Object> avoid = new HashSet<Object>();
//        for (Edge e : out) {
//            if (this.traversed.contains(e) || !this.traversed.contains(e.target)) continue;
//            avoid.add(e.target);
//        }
        Iterator i = out.iterator();
        while (i.hasNext()) {
            Object n = ((Edge)i.next()).target;
            if (avoid.contains(n)) continue;
            this.traverse(n);
        }
    }

    public void clear() {
        this.edges.clear();
        this.inbounds.clear();
        this.outbounds.clear();
        this.nodes.clear();
        this.traversed = null;
        this.ordered.clear();
    }

    public Iterator iterator() {
        if (this.ordered == null) {
            this.sort();
        }
        return this.ordered.iterator();
    }

    public void traverse(Object node, GraphTraversalListener listener) {
        HashSet subgraph = new HashSet();
        this.walk(node, subgraph);
        HashSet<Object> hs = new HashSet<Object>();
        hs.add(node);
        this.traverse(node, listener, hs, subgraph);
    }

    private void walk(Object node, Set traversed) {
        traversed.add(node);
        Set out = (Set)this.outbounds.get(node);
        if (out != null) {
//            for (Edge e : out) {
//                this.walk(e.target, traversed);
//            }
        }
    }

    private void traverse(Object node, GraphTraversalListener listener, Set traversed, Set subgraph) {
        Set edges = (Set)this.outbounds.get(node);
        if (edges != null) {
//            for (Edge e : edges) {
//                Set ins = (Set)this.inbounds.get(e.target);
//                Iterator j = ins.iterator();
//                boolean traverse = true;
//                while (j.hasNext()) {
//                    Edge in = (Edge)j.next();
//                    if (!subgraph.contains(in.source) || traversed.contains(in.source) || node.equals(in.source)) continue;
//                    traverse = false;
//                    break;
//                }
//                if (!traverse) continue;
//                listener.traverse(e.target);
//                traversed.add(e.target);
//                this.traverse(e.target, listener, traversed, subgraph);
//            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator i = this.edges.iterator();
        while (i.hasNext()) {
            sb.append(i.next());
            sb.append("\n");
        }
        return sb.toString();
    }
}

