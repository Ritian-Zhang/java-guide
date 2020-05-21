package com.ritian.datastructure.sourcecode.map;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * HashMap 1.8
 *
 * @author ritian
 * @since 2020/5/12 19:23
 **/
public class HashMap18 {

    static class HashMap<K, V> extends AbstractMap<K, V>
            implements Map<K, V>, Cloneable, Serializable {

        private static final long serialVersionUID = 1429431345167593049L;

        /**
         * 默认初始容量 - MUST be a power of two.
         */
        static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

        /**
         * 最大容量
         */
        static final int MAXIMUM_CAPACITY = 1 << 30;

        /**
         * 默认加载因子 = 0.75
         */
        static final float DEFAULT_LOAD_FACTOR = 0.75f;

        /**
         * 1. 桶的树化阈值：即 链表转成红黑树的阈值，在存储数据时，当链表长度 > 该值时，则将链表转换成红黑树
         */
        static final int TREEIFY_THRESHOLD = 8;

        /**
         * 2. 桶的链表还原阈值：即 红黑树转为链表的阈值，当在扩容（resize（））时（此时HashMap的数据存储位置会重新计算），
         * 在重新计算存储位置后，当原有的红黑树内数量 < 6时，则将 红黑树转换成链表
         */
        static final int UNTREEIFY_THRESHOLD = 6;

        /**
         * 3. 最小树形化容量阈值：即 当哈希表中的容量 > 该值时，才允许树形化链表 （即 将链表 转换成红黑树）
         * 否则，若桶内元素太多时，则直接扩容，而不是树形化
         * 为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
         */
        static final int MIN_TREEIFY_CAPACITY = 64;

        /**
         * 存储数据的Node类型 数组，长度 = 2的幂；数组的每个元素 = 1个单链表
         */
        transient Node<K, V>[] table;

        /**
         *
         */
        transient Set<Map.Entry<K, V>> entrySet;

        /**
         * HashMap 大小
         */
        transient int size;

        /**
         *
         */
        transient int modCount;

        /**
         *
         */
        int threshold;

        /**
         * The load factor for the hash table.
         *
         * @serial
         */
        final float loadFactor = 0.75f;


        public V put(K key, V value) {
            return putVal(hash(key), key, value, false, true);
        }

        static final int hash(Object key) {
            int h;
            return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        }

        /**
         * onlyIfAbsent if true 该链表中保存的有相同key的值，那么就不会对当前的value进行保存
         * evict evict在HashMap中无意义 evict为false时表示构造初始HashMap
         */
        final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                       boolean evict) {
            Node<K, V>[] tab;
            Node<K, V> p;
            int n, i;
            // 1. 若哈希表的数组tab为空，则 通过resize() 创建
            // 所以，初始化哈希表的时机 = 第1次调用put函数时，即调用resize() 初始化创建
            if ((tab = table) == null || (n = tab.length) == 0)
                n = (tab = resize()).length;
            // 2. 计算插入存储的数组索引i：根据键值key计算的hash值

            // 3. 插入时，需判断是否存在Hash冲突：
            // 若不存在（即当前table[i] == null），则直接在该数组位置新建节点，插入完毕
            if ((p = tab[i = (n - 1) & hash]) == null)
                tab[i] = newNode(hash, key, value, null);
            else {
                // 否则，代表存在Hash冲突，即当前存储位置已存在节点，则依次往下判断：
                // a. 当前位置的key是否与需插入的key相同、b. 判断需插入的数据结构是否为红黑树 or 链表
                Node<K, V> e;
                K k;
                // a. 判断 table[i]的元素的key是否与 需插入的key一样，若相同则 直接用新value 覆盖 旧value
                if (p.hash == hash &&
                        ((k = p.key) == key || (key != null && key.equals(k))))
                    e = p;
                    // b. 继续判断：需插入的数据结构是否为红黑树 or 链表
                    // 若是红黑树，则直接在树中插入 or 更新键值对
                else if (p instanceof TreeNode)
                    e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);

                    // 若是链表,则在链表中插入 or 更新键值对
                    // i.  遍历table[i]，判断Key是否已存在：采用equals（） 对比当前遍历节点的key 与 需插入数据的key：
                    // 若已存在，则直接用新value 覆盖 旧value
                    // ii. 遍历完毕后仍无发现上述情况，则直接在链表尾部插入数据
                    // 注：新增节点后，需判断链表长度是否>8（8 = 桶的树化阈值）：若是，则把链表转换为红黑树
                else {
                    for (int binCount = 0; ; ++binCount) {
                        // 对于ii：若数组的下1个位置，表示已到表尾也没有找到key值相同节点，则新建节点 = 插入节点
                        if ((e = p.next) == null) {
                            // 注：此处是从链表尾插入，与JDK 1.7不同（从链表头插入，即永远都是添加到数组的位置，原来数组位置的数据则往后移）
                            p.next = newNode(hash, key, value, null);
                            // 插入节点后，若链表节点>数阈值，则将链表转换为红黑树
                            if (binCount >= TREEIFY_THRESHOLD - 1)
                                treeifyBin(tab, hash);
                            break;
                        }
                        if (e.hash == hash &&
                                ((k = e.key) == key || (key != null && key.equals(k))))
                            break;
                        p = e;
                    }
                }
                // 表示在桶中找到key值、hash值与插入元素相等的结点
                if (e != null) {
                    V oldValue = e.value;
                    if (!onlyIfAbsent || oldValue == null)
                        e.value = value;
                    afterNodeAccess(e);
                    return oldValue;
                }
            }
            // 结构性修改
            ++modCount;
            // 实际大小大于阈值则扩容
            if (++size > threshold)
                resize();
            // 插入后回调
            afterNodeInsertion(evict);
            return null;
        }

        /**
         * 分析3：putTreeVal(this, tab, hash, key, value)
         * 作用：向红黑树插入 or 更新数据（键值对）
         * 过程：遍历红黑树判断该节点的key是否与需插入的key 相同：
         * a. 若相同，则新value覆盖旧value
         * b. 若不相同，则插入
         */
        final void treeifyBin(Node<K, V>[] tab, int hash) {
            int n, index;
            Node<K, V> e;
            if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
                resize();
            else if ((e = tab[index = (n - 1) & hash]) != null) {
                TreeNode<K, V> hd = null,
                        tl = null;
                do {
                    TreeNode<K, V> p = replacementTreeNode(e, null);
                    if (tl == null)
                        hd = p;
                    else {
                        p.prev = tl;
                        tl.next = p;
                    }
                    tl = p;
                } while ((e = e.next) != null);
                if ((tab[index] = hd) != null)
                    hd.treeify(tab);
            }
        }

        Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
            return new Node<>(hash, key, value, next);
        }


        final Node<K, V>[] resize() {
            Node<K, V>[] oldTab = table;
            int oldCap = (oldTab == null) ? 0 : oldTab.length;
            int oldThr = threshold;
            int newCap, newThr = 0;
            if (oldCap > 0) {
                if (oldCap >= MAXIMUM_CAPACITY) {
                    threshold = Integer.MAX_VALUE;
                    return oldTab;
                } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                        oldCap >= DEFAULT_INITIAL_CAPACITY)
                    newThr = oldThr << 1; // double threshold
            } else if (oldThr > 0) // initial capacity was placed in threshold
                newCap = oldThr;
            else {               // zero initial threshold signifies using defaults
                newCap = DEFAULT_INITIAL_CAPACITY;
                newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
            }
            if (newThr == 0) {
                float ft = (float) newCap * loadFactor;
                newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ?
                        (int) ft : Integer.MAX_VALUE);
            }
            threshold = newThr;
            @SuppressWarnings({"rawtypes", "unchecked"})
            Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
            table = newTab;
            if (oldTab != null) {
                for (int j = 0; j < oldCap; ++j) {
                    Node<K, V> e;
                    if ((e = oldTab[j]) != null) {
                        oldTab[j] = null;
                        if (e.next == null)
                            newTab[e.hash & (newCap - 1)] = e;
                        else if (e instanceof TreeNode)
                            ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                        else { // preserve order
                            Node<K, V> loHead = null, loTail = null;
                            Node<K, V> hiHead = null, hiTail = null;
                            Node<K, V> next;
                            do {
                                next = e.next;
                                if ((e.hash & oldCap) == 0) {
                                    if (loTail == null)
                                        loHead = e;
                                    else
                                        loTail.next = e;
                                    loTail = e;
                                } else {
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


        /**
         * Returns a power of two size for the given target capacity.
         */
        static final int tableSizeFor(int cap) {
            int n = cap - 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n |= n >>> 16;
            return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        }


        /**
         * 1.7中叫Entry 1.8中叫Node
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

            public final K getKey() {
                return key;
            }

            public final V getValue() {
                return value;
            }

            public final String toString() {
                return key + "=" + value;
            }

            public final int hashCode() {
                return Objects.hashCode(key) ^ Objects.hashCode(value);
            }

            public final V setValue(V newValue) {
                V oldValue = value;
                value = newValue;
                return oldValue;
            }

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


        /**
         * 红黑树节点 实现类：继承自LinkedHashMap.Entry<K,V>类
         */
        static final class TreeNode<K, V> extends Entry {

            // 属性 = 父节点、左子树、右子树、删除辅助节点 + 颜色
            TreeNode<K, V> parent;
            TreeNode<K, V> left;
            TreeNode<K, V> right;
            TreeNode<K, V> prev;
            boolean red;

            // 构造函数
            TreeNode(int hash, K key, V val, Node<K, V> next) {
                super(hash, key, val, next);
            }

            // 返回当前节点的根节点
            final TreeNode<K, V> root() {
                for (TreeNode<K, V> r = this, p; ; ) {
                    if ((p = r.parent) == null)
                        return r;
                    r = p;
                }
            }

            /**
             * Ensures that the given root is the first node of its bin.
             */
            static <K, V> void moveRootToFront(Node<K, V>[] tab, TreeNode<K, V> root) {
                int n;
                if (root != null && tab != null && (n = tab.length) > 0) {
                    int index = (n - 1) & root.hash;
                    TreeNode<K, V> first = (TreeNode<K, V>) tab[index];
                    if (root != first) {
                        Node<K, V> rn;
                        tab[index] = root;
                        TreeNode<K, V> rp = root.prev;
                        if ((rn = root.next) != null)
                            ((TreeNode<K, V>) rn).prev = rp;
                        if (rp != null)
                            rp.next = rn;
                        if (first != null)
                            first.prev = root;
                        root.next = first;
                        root.prev = null;
                    }
                    assert checkInvariants(root);
                }
            }

            final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
                return null;
            }

            /**
             * Finds the node starting at root p with the given hash and key.
             * The kc argument caches comparableClassFor(key) upon first use
             * comparing keys.
             */
//            final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
//                TreeNode<K, V> p = this;
//                do {
//                    int ph, dir;
//                    K pk;
//                    TreeNode<K, V> pl = p.left, pr = p.right, q;
//                    if ((ph = p.hash) > h)
//                        p = pl;
//                    else if (ph < h)
//                        p = pr;
//                    else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                        return p;
//                    else if (pl == null)
//                        p = pr;
//                    else if (pr == null)
//                        p = pl;
//                    else if ((kc != null ||
//                            (kc = comparableClassFor(k)) != null) &&
//                            (dir = compareComparables(kc, k, pk)) != 0)
//                        p = (dir < 0) ? pl : pr;
//                    else if ((q = pr.find(h, k, kc)) != null)
//                        return q;
//                    else
//                        p = pl;
//                } while (p != null);
//                return null;
//            }

            /**
             * Calls find for root node.
             */
            final TreeNode<K, V> getTreeNode(int h, Object k) {
                return ((parent != null) ? root() : this).find(h, k, null);
            }

            /**
             * Tie-breaking utility for ordering insertions when equal
             * hashCodes and non-comparable. We don't require a total
             * order, just a consistent insertion rule to maintain
             * equivalence across rebalancings. Tie-breaking further than
             * necessary simplifies testing a bit.
             */
            static int tieBreakOrder(Object a, Object b) {
                int d;
                if (a == null || b == null ||
                        (d = a.getClass().getName().
                                compareTo(b.getClass().getName())) == 0)
                    d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                            -1 : 1);
                return d;
            }

            final void treeify(Node<K, V>[] tab) {

            }

            /**
             * Forms tree of the nodes linked from this node.
             *
             * @return root of tree
             */
//            final void treeify(Node<K, V>[] tab) {
//                TreeNode<K, V> root = null;
//                for (TreeNode<K, V> x = this, next; x != null; x = next) {
//                    next = (TreeNode<K, V>) x.next;
//                    x.left = x.right = null;
//                    if (root == null) {
//                        x.parent = null;
//                        x.red = false;
//                        root = x;
//                    } else {
//                        K k = x.key;
//                        int h = x.hash;
//                        Class<?> kc = null;
//                        for (TreeNode<K, V> p = root; ; ) {
//                            int dir, ph;
//                            K pk = p.key;
//                            if ((ph = p.hash) > h)
//                                dir = -1;
//                            else if (ph < h)
//                                dir = 1;
//                            else if ((kc == null &&
//                                    (kc = comparableClassFor(k)) == null) ||
//                                    (dir = compareComparables(kc, k, pk)) == 0)
//                                dir = tieBreakOrder(k, pk);
//
//                            TreeNode<K, V> xp = p;
//                            if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                                x.parent = xp;
//                                if (dir <= 0)
//                                    xp.left = x;
//                                else
//                                    xp.right = x;
//                                root = balanceInsertion(root, x);
//                                break;
//                            }
//                        }
//                    }
//                }
//                moveRootToFront(tab, root);
//            }

            /**
             * Returns a list of non-TreeNodes replacing those linked from
             * this node.
             */
            final Node<K, V> untreeify(HashMap<K, V> map) {
                Node<K, V> hd = null, tl = null;
                for (Node<K, V> q = this; q != null; q = q.next) {
                    Node<K, V> p = map.replacementNode(q, null);
                    if (tl == null)
                        hd = p;
                    else
                        tl.next = p;
                    tl = p;
                }
                return hd;
            }

            final TreeNode<K, V> putTreeVal(HashMap<K, V> map, Node<K, V>[] tab,
                                            int h, K k, V v) {
                return null;
            }

            /**
             * Tree version of putVal.
             */
//            final TreeNode<K, V> putTreeVal(HashMap<K, V> map, Node<K, V>[] tab,
//                                            int h, K k, V v) {
//                Class<?> kc = null;
//                boolean searched = false;
//                TreeNode<K, V> root = (parent != null) ? root() : this;
//                for (TreeNode<K, V> p = root; ; ) {
//                    int dir, ph;
//                    K pk;
//                    if ((ph = p.hash) > h)
//                        dir = -1;
//                    else if (ph < h)
//                        dir = 1;
//                    else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                        return p;
//                    else if ((kc == null &&
//                            (kc = comparableClassFor(k)) == null) ||
//                            (dir = compareComparables(kc, k, pk)) == 0) {
//                        if (!searched) {
//                            TreeNode<K, V> q, ch;
//                            searched = true;
//                            if (((ch = p.left) != null &&
//                                    (q = ch.find(h, k, kc)) != null) ||
//                                    ((ch = p.right) != null &&
//                                            (q = ch.find(h, k, kc)) != null))
//                                return q;
//                        }
//                        dir = tieBreakOrder(k, pk);
//                    }
//
//                    TreeNode<K, V> xp = p;
//                    if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                        Node<K, V> xpn = xp.next;
//                        TreeNode<K, V> x = map.newTreeNode(h, k, v, xpn);
//                        if (dir <= 0)
//                            xp.left = x;
//                        else
//                            xp.right = x;
//                        xp.next = x;
//                        x.parent = x.prev = xp;
//                        if (xpn != null)
//                            ((TreeNode<K, V>) xpn).prev = x;
//                        moveRootToFront(tab, balanceInsertion(root, x));
//                        return null;
//                    }
//                }
//            }

            /**
             * Removes the given node, that must be present before this call.
             * This is messier than typical red-black deletion code because we
             * cannot swap the contents of an interior node with a leaf
             * successor that is pinned by "next" pointers that are accessible
             * independently during traversal. So instead we swap the tree
             * linkages. If the current tree appears to have too few nodes,
             * the bin is converted back to a plain bin. (The test triggers
             * somewhere between 2 and 6 nodes, depending on tree structure).
             */
            final void removeTreeNode(HashMap<K, V> map, Node<K, V>[] tab,
                                      boolean movable) {
                int n;
                if (tab == null || (n = tab.length) == 0)
                    return;
                int index = (n - 1) & hash;
                TreeNode<K, V> first = (TreeNode<K, V>) tab[index], root = first, rl;
                TreeNode<K, V> succ = (TreeNode<K, V>) next, pred = prev;
                if (pred == null)
                    tab[index] = first = succ;
                else
                    pred.next = succ;
                if (succ != null)
                    succ.prev = pred;
                if (first == null)
                    return;
                if (root.parent != null)
                    root = root.root();
                if (root == null || root.right == null ||
                        (rl = root.left) == null || rl.left == null) {
                    tab[index] = first.untreeify(map);  // too small
                    return;
                }
                TreeNode<K, V> p = this, pl = left, pr = right, replacement;
                if (pl != null && pr != null) {
                    TreeNode<K, V> s = pr, sl;
                    while ((sl = s.left) != null) // find successor
                        s = sl;
                    boolean c = s.red;
                    s.red = p.red;
                    p.red = c; // swap colors
                    TreeNode<K, V> sr = s.right;
                    TreeNode<K, V> pp = p.parent;
                    if (s == pr) { // p was s's direct parent
                        p.parent = s;
                        s.right = p;
                    } else {
                        TreeNode<K, V> sp = s.parent;
                        if ((p.parent = sp) != null) {
                            if (s == sp.left)
                                sp.left = p;
                            else
                                sp.right = p;
                        }
                        if ((s.right = pr) != null)
                            pr.parent = s;
                    }
                    p.left = null;
                    if ((p.right = sr) != null)
                        sr.parent = p;
                    if ((s.left = pl) != null)
                        pl.parent = s;
                    if ((s.parent = pp) == null)
                        root = s;
                    else if (p == pp.left)
                        pp.left = s;
                    else
                        pp.right = s;
                    if (sr != null)
                        replacement = sr;
                    else
                        replacement = p;
                } else if (pl != null)
                    replacement = pl;
                else if (pr != null)
                    replacement = pr;
                else
                    replacement = p;
                if (replacement != p) {
                    TreeNode<K, V> pp = replacement.parent = p.parent;
                    if (pp == null)
                        root = replacement;
                    else if (p == pp.left)
                        pp.left = replacement;
                    else
                        pp.right = replacement;
                    p.left = p.right = p.parent = null;
                }

                TreeNode<K, V> r = p.red ? root : balanceDeletion(root, replacement);

                if (replacement == p) {  // detach
                    TreeNode<K, V> pp = p.parent;
                    p.parent = null;
                    if (pp != null) {
                        if (p == pp.left)
                            pp.left = null;
                        else if (p == pp.right)
                            pp.right = null;
                    }
                }
                if (movable)
                    moveRootToFront(tab, r);
            }

            /**
             * Splits nodes in a tree bin into lower and upper tree bins,
             * or untreeifies if now too small. Called only from resize;
             * see above discussion about split bits and indices.
             *
             * @param map   the map
             * @param tab   the table for recording bin heads
             * @param index the index of the table being split
             * @param bit   the bit of hash to split on
             */
            final void split(HashMap<K, V> map, Node<K, V>[] tab, int index, int bit) {
                TreeNode<K, V> b = this;
                // Relink into lo and hi lists, preserving order
                TreeNode<K, V> loHead = null, loTail = null;
                TreeNode<K, V> hiHead = null, hiTail = null;
                int lc = 0, hc = 0;
                for (TreeNode<K, V> e = b, next; e != null; e = next) {
                    next = (TreeNode<K, V>) e.next;
                    e.next = null;
                    if ((e.hash & bit) == 0) {
                        if ((e.prev = loTail) == null)
                            loHead = e;
                        else
                            loTail.next = e;
                        loTail = e;
                        ++lc;
                    } else {
                        if ((e.prev = hiTail) == null)
                            hiHead = e;
                        else
                            hiTail.next = e;
                        hiTail = e;
                        ++hc;
                    }
                }

                if (loHead != null) {
                    if (lc <= UNTREEIFY_THRESHOLD)
                        tab[index] = loHead.untreeify(map);
                    else {
                        tab[index] = loHead;
                        if (hiHead != null) // (else is already treeified)
                            loHead.treeify(tab);
                    }
                }
                if (hiHead != null) {
                    if (hc <= UNTREEIFY_THRESHOLD)
                        tab[index + bit] = hiHead.untreeify(map);
                    else {
                        tab[index + bit] = hiHead;
                        if (loHead != null)
                            hiHead.treeify(tab);
                    }
                }
            }

            /* ------------------------------------------------------------ */
            // Red-black tree methods, all adapted from CLR

            static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> root,
                                                    TreeNode<K, V> p) {
                TreeNode<K, V> r, pp, rl;
                if (p != null && (r = p.right) != null) {
                    if ((rl = p.right = r.left) != null)
                        rl.parent = p;
                    if ((pp = r.parent = p.parent) == null)
                        (root = r).red = false;
                    else if (pp.left == p)
                        pp.left = r;
                    else
                        pp.right = r;
                    r.left = p;
                    p.parent = r;
                }
                return root;
            }

            static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> root,
                                                     TreeNode<K, V> p) {
                TreeNode<K, V> l, pp, lr;
                if (p != null && (l = p.left) != null) {
                    if ((lr = p.left = l.right) != null)
                        lr.parent = p;
                    if ((pp = l.parent = p.parent) == null)
                        (root = l).red = false;
                    else if (pp.right == p)
                        pp.right = l;
                    else
                        pp.left = l;
                    l.right = p;
                    p.parent = l;
                }
                return root;
            }

            static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> root,
                                                          TreeNode<K, V> x) {
                x.red = true;
                for (TreeNode<K, V> xp, xpp, xppl, xppr; ; ) {
                    if ((xp = x.parent) == null) {
                        x.red = false;
                        return x;
                    } else if (!xp.red || (xpp = xp.parent) == null)
                        return root;
                    if (xp == (xppl = xpp.left)) {
                        if ((xppr = xpp.right) != null && xppr.red) {
                            xppr.red = false;
                            xp.red = false;
                            xpp.red = true;
                            x = xpp;
                        } else {
                            if (x == xp.right) {
                                root = rotateLeft(root, x = xp);
                                xpp = (xp = x.parent) == null ? null : xp.parent;
                            }
                            if (xp != null) {
                                xp.red = false;
                                if (xpp != null) {
                                    xpp.red = true;
                                    root = rotateRight(root, xpp);
                                }
                            }
                        }
                    } else {
                        if (xppl != null && xppl.red) {
                            xppl.red = false;
                            xp.red = false;
                            xpp.red = true;
                            x = xpp;
                        } else {
                            if (x == xp.left) {
                                root = rotateRight(root, x = xp);
                                xpp = (xp = x.parent) == null ? null : xp.parent;
                            }
                            if (xp != null) {
                                xp.red = false;
                                if (xpp != null) {
                                    xpp.red = true;
                                    root = rotateLeft(root, xpp);
                                }
                            }
                        }
                    }
                }
            }

            static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> root,
                                                         TreeNode<K, V> x) {
                for (TreeNode<K, V> xp, xpl, xpr; ; ) {
                    if (x == null || x == root)
                        return root;
                    else if ((xp = x.parent) == null) {
                        x.red = false;
                        return x;
                    } else if (x.red) {
                        x.red = false;
                        return root;
                    } else if ((xpl = xp.left) == x) {
                        if ((xpr = xp.right) != null && xpr.red) {
                            xpr.red = false;
                            xp.red = true;
                            root = rotateLeft(root, xp);
                            xpr = (xp = x.parent) == null ? null : xp.right;
                        }
                        if (xpr == null)
                            x = xp;
                        else {
                            TreeNode<K, V> sl = xpr.left, sr = xpr.right;
                            if ((sr == null || !sr.red) &&
                                    (sl == null || !sl.red)) {
                                xpr.red = true;
                                x = xp;
                            } else {
                                if (sr == null || !sr.red) {
                                    if (sl != null)
                                        sl.red = false;
                                    xpr.red = true;
                                    root = rotateRight(root, xpr);
                                    xpr = (xp = x.parent) == null ?
                                            null : xp.right;
                                }
                                if (xpr != null) {
                                    xpr.red = (xp == null) ? false : xp.red;
                                    if ((sr = xpr.right) != null)
                                        sr.red = false;
                                }
                                if (xp != null) {
                                    xp.red = false;
                                    root = rotateLeft(root, xp);
                                }
                                x = root;
                            }
                        }
                    } else { // symmetric
                        if (xpl != null && xpl.red) {
                            xpl.red = false;
                            xp.red = true;
                            root = rotateRight(root, xp);
                            xpl = (xp = x.parent) == null ? null : xp.left;
                        }
                        if (xpl == null)
                            x = xp;
                        else {
                            TreeNode<K, V> sl = xpl.left, sr = xpl.right;
                            if ((sl == null || !sl.red) &&
                                    (sr == null || !sr.red)) {
                                xpl.red = true;
                                x = xp;
                            } else {
                                if (sl == null || !sl.red) {
                                    if (sr != null)
                                        sr.red = false;
                                    xpl.red = true;
                                    root = rotateLeft(root, xpl);
                                    xpl = (xp = x.parent) == null ?
                                            null : xp.left;
                                }
                                if (xpl != null) {
                                    xpl.red = (xp == null) ? false : xp.red;
                                    if ((sl = xpl.left) != null)
                                        sl.red = false;
                                }
                                if (xp != null) {
                                    xp.red = false;
                                    root = rotateRight(root, xp);
                                }
                                x = root;
                            }
                        }
                    }
                }
            }

            /**
             * Recursive invariant check
             */
            static <K, V> boolean checkInvariants(TreeNode<K, V> t) {
                TreeNode<K, V> tp = t.parent, tl = t.left, tr = t.right,
                        tb = t.prev, tn = (TreeNode<K, V>) t.next;
                if (tb != null && tb.next != t)
                    return false;
                if (tn != null && tn.prev != t)
                    return false;
                if (tp != null && t != tp.left && t != tp.right)
                    return false;
                if (tl != null && (tl.parent != t || tl.hash > t.hash))
                    return false;
                if (tr != null && (tr.parent != t || tr.hash < t.hash))
                    return false;
                if (t.red && tl != null && tl.red && tr != null && tr.red)
                    return false;
                if (tl != null && !checkInvariants(tl))
                    return false;
                if (tr != null && !checkInvariants(tr))
                    return false;
                return true;
            }
        }


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

        // For conversion from TreeNodes to plain nodes
        Node<K, V> replacementNode(Node<K, V> p, Node<K, V> next) {
            return new Node<>(p.hash, p.key, p.value, next);
        }

        // Create a tree bin node
        TreeNode<K, V> newTreeNode(int hash, K key, V value, Node<K, V> next) {
            return new TreeNode<>(hash, key, value, next);
        }

        // For treeifyBin
        TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
            return new TreeNode<>(p.hash, p.key, p.value, next);
        }

        // Callbacks to allow LinkedHashMap post-actions
        void afterNodeAccess(Node<K, V> p) {
        }

        void afterNodeInsertion(boolean evict) {
        }

        void afterNodeRemoval(Node<K, V> p) {
        }

        static class Entry<K, V> extends HashMap.Node<K, V> {
            Entry<K, V> before, after;

            Entry(int hash, K key, V value, Node<K, V> next) {
                super(hash, key, value, next);
            }
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return null;
        }
    }
}
