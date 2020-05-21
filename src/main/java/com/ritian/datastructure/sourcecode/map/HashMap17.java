package com.ritian.datastructure.sourcecode.map;

import java.io.Serializable;
import java.util.*;

/**
 * HashMap JDK 1.7源码实现
 *
 * @author ritian
 * @since 2020/5/12 19:14
 **/
public class HashMap17 {

    static class HashMap<K, V>
            extends AbstractMap<K, V>
            implements Map<K, V>, Cloneable, Serializable {
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////属性值////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 1. 容量（capacity）： HashMap中数组的长度
         * 容量范围：必须是2的幂 & < 最大容量（2的30次方）
         * 初始容量 = 哈希表创建时的容量
         * 默认容量 = 16 = 1<<4 = 00001中的1向左移4位 = 10000 = 十进制的2^4=16
         */
        static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
        /**
         * 最大容量 =  2的30次方（若传入的容量过大，将被最大值替换）
         */
        static final int MAXIMUM_CAPACITY = 1 << 30;

        /**
         * 2. 加载因子(Load factor)：HashMap在其容量自动增加前可达到多满的一种尺度
         * a. 加载因子越大、填满的元素越多 = 空间利用率高、但冲突的机会加大、查找效率变低（因为链表变长了）
         * b. 加载因子越小、填满的元素越少 = 空间利用率小、冲突的机会减小、查找效率高（链表不长）
         * 实际加载因子
         */
        final float loadFactor;
        // 默认加载因子 = 0.75
        static final float DEFAULT_LOAD_FACTOR = 0.75f;

        /**
         * 3. 扩容阈值（threshold）：当哈希表的大小 ≥ 扩容阈值时，就会扩容哈希表（即扩充HashMap的容量）
         * // a. 扩容 = 对哈希表进行resize操作（即重建内部数据结构），从而哈希表将具有大约两倍的桶数
         * // b. 扩容阈值 = 容量 x 加载因子
         */
        int threshold;

        /**
         * 4. 存储单链表的table
         * // 存储数据的Entry类型 数组，长度 = 2的幂
         * // HashMap的实现方式 = 拉链法，Entry数组上的每个元素本质上是一个单向链表
         */
        static final Entry<?, ?>[] EMPTY_TABLE = {};

        transient Entry<K, V>[] table = (Entry<K, V>[]) EMPTY_TABLE;
        /**
         * HashMap的大小，即 HashMap中存储的键值对的数量
         */
        transient int size;

        transient int modCount;


        //////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////源码分析：主要是HashMap的构造函数 = 4个  仅贴出关于HashMap构造函数的源码//////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 构造函数1：默认构造函数（无参）
         * 加载因子 & 容量 = 默认 = 0.75、16
         */
        public HashMap() {
            // 实际上是调用构造函数3：指定“容量大小”和“加载因子”的构造函数
            // 传入的指定容量 & 加载因子 = 默认
            this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        }

        /**
         * 构造函数2：指定“容量大小”的构造函数
         * 加载因子 = 默认 = 0.75 、容量 = 指定大小
         */
        public HashMap(int initialCapacity) {
            // 实际上是调用指定“容量大小”和“加载因子”的构造函数
            // 只是在传入的加载因子参数 = 默认加载因子
            this(initialCapacity, DEFAULT_LOAD_FACTOR);

        }

        /**
         * 构造函数3：指定“容量大小”和“加载因子”的构造函数
         * 加载因子 & 容量 = 自己指定
         */
        public HashMap(int initialCapacity, float loadFactor) {

            // HashMap的最大容量只能是MAXIMUM_CAPACITY，哪怕传入的 > 最大容量
            if (initialCapacity > MAXIMUM_CAPACITY)
                initialCapacity = MAXIMUM_CAPACITY;

            // 设置 加载因子
            this.loadFactor = loadFactor;
            // 设置 扩容阈值 = 初始容量
            // 注：此处不是真正的阈值，是为了扩展table，该阈值后面会重新计算，下面会详细讲解
            threshold = initialCapacity;

            // 注意（jdk1.7.0.17 table初始化是在这里）
//            int capacity = 1;
//            while (capacity < initialCapacity)
//                capacity <<= 1;
//
//            this.loadFactor = loadFactor;
//            threshold = (int)Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
//            table = new Entry[capacity];

            init(); // 一个空方法用于未来的子对象扩展
        }

        /**
         * 构造函数4：包含“子Map”的构造函数
         * 即 构造出来的HashMap包含传入Map的映射关系
         * 加载因子 & 容量 = 默认
         */
        public HashMap(Map<? extends K, ? extends V> m) {

            // 设置容量大小 & 加载因子 = 默认
            this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1,
                    DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);

            // 该方法用于初始化 数组 & 阈值
            inflateTable(threshold);

            // 将传入的子Map中的全部元素逐个添加到HashMap中
            //putAllForCreate(m);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////源码分析：put、get//////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        public V put(K key, V value) {
            // 1. 若 哈希表未初始化（即 table为空) (注意jdk1.7.0.17初始化是在new HashMap()的时候就初始化了,其它版本有待验证)
            // 则使用 构造函数时设置的阈值(即初始容量) 初始化 数组table
            if (table == EMPTY_TABLE) {
                inflateTable(threshold);
            }
            //  2. 判断key是否为空值null
            // 2.1 若key == null，则将该键-值 存放到数组table 中的第1个位置，即table [0]
            // （本质：key = null，hash值 = 0，故存放到table[0]中）
            // 该位置永远只有1个value，新传进来的value会覆盖旧的value
            if (key == null)
                return putForNullKey(value);

            // 2.2 若 key ≠ null，则计算存放数组 table 中的位置（下标、索引）
            // a. 根据键值key计算hash值
            int hash = hash(key);
            // b. 根据hash值 最终获得 key对应存放的数组Table中位置
            int i = indexFor(hash, table.length);

            //3.判断该key对应的值是否已存在（通过遍历 以该数组元素为头结点的链表 逐个判断）
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                Object k;
                // 3.1 若该key已存在（即 key-value已存在 ），则用 新value 替换 旧value
                if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                    V oldValue = e.value;
                    e.value = value;
                    e.recordAccess(this);
                    return oldValue;
                }
            }
            modCount++;
            // 3.2 若 该key不存在，则将“key-value”添加到table中
            addEntry(hash, key, value, i);
            return null;
        }

        /**
         * 源码分析：putForNullKey(value)
         */
        private V putForNullKey(V value) {
            // 遍历以table[0]为首的链表，寻找是否存在key==null 对应的键值对
            // 1. 若有：则用新value 替换 旧value；同时返回旧的value值
            for (Entry<K, V> e = table[0]; e != null; e = e.next) {
                if (e.key == null) {
                    V oldValue = e.value;
                    e.value = value;
                    e.recordAccess(this);
                    return oldValue;
                }
            }
            modCount++;
            //2 .若无key==null的键，那么调用addEntry（），将空键 & 对应的值封装到Entry中，并放到table[0]中
            addEntry(0, null, value, 0);
            // 注：
            // a. addEntry（）的第1个参数 = hash值 = 传入0
            // b. 即 说明：当key = null时，也有hash值 = 0，所以HashMap的key 可为null
            // c. 对比HashTable，由于HashTable对key直接hashCode（），若key为null时，会抛出异常，所以HashTable的key不可为null
            return null;
        }

        /**
         * 源码分析：addEntry(hash, key, value, i)
         * 作用：添加键值对（Entry ）到 HashMap中
         */
        void addEntry(int hash, K key, V value, int bucketIndex) {
            // 1. 插入前，先判断容量是否足够
            // 1.1 若不足够，则进行扩容（2倍）、重新计算Hash值、重新计算存储数组下标
            if ((size >= threshold) && (null != table[bucketIndex])) {
                //扩容2倍
                resize(2 * table.length);
                hash = (null != key) ? hash(key) : 0;
                bucketIndex = indexFor(hash, table.length);
            }
           //1.2 若容量足够，则创建1个新的数组元素（Entry） 并放入到数组中
            createEntry(hash, key, value, bucketIndex);
        }

        void createEntry(int hash, K key, V value, int bucketIndex) {
            Entry<K, V> e = table[bucketIndex];
            table[bucketIndex] = new Entry<>(hash, key, value, e);
            size++;
        }

        /**
         * 分析1：resize(2 * table.length)
         * 作用：当容量不足时（容量 > 阈值），则扩容（扩到2倍）
         */
        void resize(int newCapacity) {
            // 1. 保存旧数组（old table）
            Entry[] oldTable = table;
            // 2. 保存旧容量（old capacity ），即数组长度
            int oldCapacity = oldTable.length;
            // 3. 若旧容量已经是系统默认最大容量了，那么将阈值设置成整型的最大值，退出
            if (oldCapacity == MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return;
            }
            // 4. 根据新容量（2倍容量）新建1个数组，即新table
            Entry[] newTable = new Entry[newCapacity];
//            boolean oldAltHashing = useAltHashing;
//            useAltHashing |= sun.misc.VM.isBooted() &&
//                    (newCapacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
//            boolean rehash = oldAltHashing ^ useAltHashing;
//            transfer(newTable, rehash);
            // 5. 将旧数组上的数据（键值对）转移到新table中，从而完成扩容
            transfer(newTable, true);
            // 6. 新数组table引用到HashMap的table属性上
            table = newTable;
            // 7. 重新设置阈值
            threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
        }

        /**
         * 分析1.1：transfer(newTable);
         * 作用：将旧数组上的数据（键值对）转移到新table中，从而完成扩容
         * 过程：按旧链表的正序遍历链表、在新链表的头部依次插入
         */
        void transfer(Entry[] newTable, boolean rehash) {
            // 2. 获取新数组的大小 = 获取新容量大小
            int newCapacity = newTable.length;
            // 3. 通过遍历 旧数组，将旧数组上的数据（键值对）转移到新数组中
            for (Entry<K, V> e : table) {
                while (null != e) {
                    // 3.3 遍历 以该数组元素为首 的链表
                    // 注：转移链表时，因是单链表，故要保存下1个结点，否则转移后链表会断开
                    Entry<K, V> next = e.next;
                    if (rehash) {
                        e.hash = null == e.key ? 0 : hash(e.key);
                    }
                    // 3.4 重新计算每个元素的存储位置
                    int i = indexFor(e.hash, newCapacity);
                    // 3.5 将元素放在数组上：采用单链表的头插入方式 = 在链表头上存放数据 = 将数组位置的原有数据放在后1个指针、将需放入的数据放到数组位置中
                    // 即 扩容后，可能出现逆序：按旧链表的正序遍历链表、在新链表的头部依次插入
                    e.next = newTable[i];
                    // 3.6 访问下1个Entry链上的元素，如此不断循环，直到遍历完该链表上的所有节点
                    newTable[i] = e;
                    e = next;
                }
            }
        }

        /**
         * Returns index for hash code h.
         */
        static int indexFor(int h, int length) {
            return h & (length - 1);
        }

        /**
         * 源码分析1：hash(key)
         * 该函数在JDK 1.7 和 1.8 中的实现不同，但原理一样 = 扰动函数 = 使得根据key生成的哈希码（hash值）分布更加均匀、更具备随机性，避免出现hash值冲突（即指不同key但生成同1个hash值）
         * JDK 1.7 做了9次扰动处理 = 4次位运算 + 5次异或运算
         * JDK 1.8 简化了扰动函数 = 只做了2次扰动 = 1次位运算 + 1次异或运算
         */
        final int hash(Object k) {
            int h = 0;
            if (useAltHashing) {
                //hashSeed也是一个非常重要的角色，可以把它看成一个开关，如果开关打开，
                // 并且key的类型是String时可以采取sun.misc.Hashing.stringHash32方法获取其hash值。
//                if (k instanceof String) {
//                    return sun.misc.Hashing.stringHash32((String) k);
//                }
                h = hashSeed;
            }

            h ^= k.hashCode();

            // This function ensures that hashCodes that differ only by
            // constant multiples at each bit position have a bounded
            // number of collisions (approximately 8 at default load factor).
            h ^= (h >>> 20) ^ (h >>> 12);
            return h ^ (h >>> 7) ^ (h >>> 4);
        }

        void init() {
        }

        /**
         * Inflates the table.(膨胀table)
         */
        private void inflateTable(int toSize) {
            // 1. 将传入的容量大小转化为：>传入容量大小的最小的2的次幂
            // 即如果传入的是容量大小是19，那么转化后，初始化容量大小为32（即2的5次幂）
            int capacity = roundUpToPowerOf2(toSize);
            //2. 重新计算阈值 threshold = 容量 * 加载因子
            threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
            //3. 使用计算后的初始容量（已经是2的次幂） 初始化数组table（作为数组长度）
            // 即 哈希表的容量大小 = 数组大小（长度)
            table = new Entry[capacity];////用该容量初始化table

            //更新一下rehash的判断条件，便于以后判断是否rehash
            initHashSeedAsNeeded(capacity);
        }

        /**
         * Initialize the hashing mask value. We defer initialization until we
         * really need it.
         */
        final boolean initHashSeedAsNeeded(int capacity) {
            // hashSeed降低hash碰撞的hash种子，初始值为0
            boolean currentAltHashing = hashSeed != 0;
            //ALTERNATIVE_HASHING_THRESHOLD： 当map的capacity容量大于这个值的时候并满足其他条件时候进行重新hash
            boolean useAltHashing = sun.misc.VM.isBooted() && (capacity >= Holder.ALTERNATIVE_HASHING_THRESHOLD);
            // 异或操作，二者满足一个条件即可rehash
            boolean switching = currentAltHashing ^ useAltHashing;
            if (switching) {
                // 更新hashseed的值
//                hashSeed = useAltHashing ? sun.misc.Hashing.randomHashSeed(this) : 0;
            }
            return switching;
        }

        private static int roundUpToPowerOf2(int number) {
            // assert number >= 0 : "number must be non-negative";
            return number >= MAXIMUM_CAPACITY
                    ? MAXIMUM_CAPACITY
                    : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
        }

        /**
         * If {@code true} then perform alternative hashing of String keys to reduce
         * the incidence of collisions due to weak hash code calculation.
         */
        transient boolean useAltHashing;

        /**
         * A randomizing value associated with this instance that is applied to
         * hash code of keys to make hash collisions harder to find.
         */
//        transient final int hashSeed = sun.misc.Hashing.randomHashSeed(this);
        /**
         * 标志位，是jdk为了解决hashMap安全隐患做的补丁
         */
        transient final int hashSeed = 0;

        static final int ALTERNATIVE_HASHING_THRESHOLD_DEFAULT = Integer.MAX_VALUE;

        /**
         * holds values which can't be initialized until after VM is booted.
         */
        private static class Holder {

            // Unsafe mechanics
            /**
             * Unsafe utilities
             */
            static final sun.misc.Unsafe UNSAFE;

            /**
             * Offset of "final" hashSeed field we must set in readObject() method.
             */
            static final long HASHSEED_OFFSET;

            /**
             * Table capacity above which to switch to use alternative hashing.
             */
            static final int ALTERNATIVE_HASHING_THRESHOLD;

            static {
                String altThreshold = java.security.AccessController.doPrivileged(
                        new sun.security.action.GetPropertyAction(
                                "jdk.map.althashing.threshold"));

                int threshold;
                try {
                    threshold = (null != altThreshold)
                            ? Integer.parseInt(altThreshold)
                            : ALTERNATIVE_HASHING_THRESHOLD_DEFAULT;

                    // disable alternative hashing if -1
                    if (threshold == -1) {
                        threshold = Integer.MAX_VALUE;
                    }

                    if (threshold < 0) {
                        throw new IllegalArgumentException("value must be positive integer.");
                    }
                } catch (IllegalArgumentException failed) {
                    throw new Error("Illegal value for 'jdk.map.althashing.threshold'", failed);
                }
                ALTERNATIVE_HASHING_THRESHOLD = threshold;

                try {
                    UNSAFE = sun.misc.Unsafe.getUnsafe();
                    HASHSEED_OFFSET = UNSAFE.objectFieldOffset(
                            HashMap.class.getDeclaredField("hashSeed"));
                } catch (NoSuchFieldException | SecurityException e) {
                    throw new Error("Failed to record hashSeed offset", e);
                }
            }
        }


        /**
         * Entry类实现了Map.Entry接口
         * 即 实现了getKey()、getValue()、equals(Object o)和hashCode()等方法
         **/
        static class Entry<K, V> implements Map.Entry<K, V> {
            final K key;  // 键
            V value;  // 值
            Entry<K, V> next; // 指向下一个节点 ，也是一个Entry对象，从而形成解决hash冲突的单链表
            int hash;  // hash值

            /**
             * 构造方法，创建一个Entry
             * 参数：哈希值h，键值k，值v、下一个节点n
             */
            Entry(int h, K k, V v, Entry<K, V> n) {
                value = v;
                next = n;
                key = k;
                hash = h;
            }

            // 返回 与 此项 对应的键
            public final K getKey() {
                return key;
            }

            // 返回 与 此项 对应的值
            public final V getValue() {
                return value;
            }

            public final V setValue(V newValue) {
                V oldValue = value;
                value = newValue;
                return oldValue;
            }

            /**
             * equals（）
             * 作用：判断2个Entry是否相等，必须key和value都相等，才返回true
             */
            public final boolean equals(Object o) {
                if (!(o instanceof Map.Entry))
                    return false;
                Map.Entry e = (Map.Entry) o;
                Object k1 = getKey();
                Object k2 = e.getKey();
                if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                    Object v1 = getValue();
                    Object v2 = e.getValue();
                    if (v1 == v2 || (v1 != null && v1.equals(v2)))
                        return true;
                }
                return false;
            }

            /**
             * hashCode（）
             */
            public final int hashCode() {
                return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
            }

            public final String toString() {
                return getKey() + "=" + getValue();
            }

            /**
             * 当向HashMap中添加元素时，即调用put(k,v)时，
             * 对已经在HashMap中k位置进行v的覆盖时，会调用此方法
             * 此处没做任何处理
             */
            void recordAccess(HashMap<K, V> m) {
            }

            /**
             * 当从HashMap中删除了一个Entry时，会调用该函数
             * 此处没做任何处理
             */
            void recordRemoval(HashMap<K, V> m) {
            }

        }


        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return null;
        }
    }


}
