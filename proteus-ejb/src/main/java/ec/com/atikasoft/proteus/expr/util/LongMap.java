/*
 * Decompiled with CFR 0_110.
 */
package ec.com.atikasoft.proteus.expr.util;

public class LongMap {
    private Entry[] hashtable;
    private int size;

    public LongMap() {
        this(16);
    }

    public LongMap(int size) {
        this.hashtable = new Entry[size];
    }

    public void remove(long key) {
        int pos = Math.abs((int)(key % (long)this.hashtable.length));
        Entry e = this.hashtable[pos];
        if (e == null) {
            return;
        }
        if (e.key == key) {
            this.hashtable[pos] = e.next;
            --this.size;
        } else {
            Entry prev = e;
            e = e.next;
            while (e != null) {
                if (e.key == key) {
                    prev.next = e.next;
                    --this.size;
                }
                prev = e;
                e = e.next;
            }
        }
    }

    public void put(long key, Object value) {
        int pos = Math.abs((int)(key % (long)this.hashtable.length));
        Entry e = this.hashtable[pos];
        if (e == null) {
            this.hashtable[pos] = new Entry(key, value);
            ++this.size;
        } else if (e.key == key) {
            e.value = value;
        } else {
            while (e.next != null) {
                if (e.next.key == key) {
                    e.next.value = value;
                    return;
                }
                e = e.next;
            }
            e.next = new Entry(key, value);
            ++this.size;
        }
    }

    public Object get(long key) {
        int pos = Math.abs((int)(key % (long)this.hashtable.length));
        Entry e = this.hashtable[pos];
        if (e == null) {
            return null;
        }
        if (e.key == key) {
            return e.value;
        }
        while (e.next != null) {
            if (e.next.key == key) {
                return e.next.value;
            }
            e = e.next;
        }
        return null;
    }

    public long[] keySet() {
        long[] keys = new long[this.size];
        int idx = 0;
        for (int i = 0; i < this.hashtable.length; ++i) {
            Entry e = this.hashtable[i];
            while (e != null) {
                keys[idx++] = e.key;
                e = e.next;
            }
        }
        return keys;
    }

    public int size() {
        return this.size;
    }

    private class Entry {
        public long key;
        public Object value;
        public Entry next;

        public Entry(long key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

}

