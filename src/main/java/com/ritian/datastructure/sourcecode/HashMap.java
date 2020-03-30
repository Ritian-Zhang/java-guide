package com.ritian.datastructure.sourcecode;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * HashMap源码分析
 * <p>
 * HashMap是一种散列表，用于存储 key-value 键值对的数据结构，一般翻译为“哈希表”，提供平均时间复杂度为 O(1) 的、基于 key 级别的 get/put 等操作。
 *
 * <a href="https://www.jianshu.com/p/8b372f3a195d">参考文档</a>
 * <a href="https://www.jianshu.com/p/ee0de4c99f87">参考文档</a>
 * <a href="http://svip.iocoder.cn/JDK/Collection-HashMap/">参考文档</a>
 *
 * @author ritian
 * @since 2019/12/31 10:30
 **/
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {


    private static final long serialVersionUID = 3943095358217396223L;

    /***
     * 默认初始容量 16
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 最大容量
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认加载因子 0.75
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     *
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     *
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     *
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     *
     */
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    /* ---------------- Static utilities -------------- */

    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType) t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes", "unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable) k).compareTo(x));
    }

    /**
     * 返回一个大于等于给定整数且最接近的2的幂次方整数，如给定10，返回2的4次方16.
     */
    static final int tableSizeFor(int cap) {
        //防止cap已经是2的幂时
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }


    /* ---------------- Fields -------------- */

    //******* 补充知识点transient 关键字：
    //1.transient是短暂的意思。对于transient 修饰的成员变量，在类实例的序列化处理过程中会被忽略
    //2.一个静态变量不管是否被transient修饰，均不能被序列化。
    // ********//

    transient Node<K, V>[] table;

    transient Set<Map.Entry<K, V>> entrySet;

    transient int size;
    /**
     * HashMap 的修改次数
     */
    transient int modCount;
    /**
     * 所能容纳的key-value对极限  = length * loadFactor
     * 阀值，当 {@link #size} 超过 {@link #threshold} 时，会进行扩容
     */
    int threshold;
    /**
     * 负载因子
     */
    final float loadFactor;

    /* ---------------- Public operations -------------- */

    /**
     * 构造方法
     */
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 默认构造函数
     */
    public HashMap() {
        // all other fields defaulted
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
//        putMapEntries(m, false);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {

        Node<K,V>[] tab;
        Node<K,V> p;
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0){
            n = (tab = resize()).length;
        }
        return null;
    }

    /**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                    (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
//                    else if (e instanceof TreeNode)
//                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }



    static class HashMapTest{
        public static void main(String[] args) {
            java.util.Map<String, String> map = new java.util.HashMap<>();
            map.put("key1", "value1");
        }
    }

}
