package com.ritian.datastructure.map;

/**
 * @author ritian.Zhang
 * @date 2019/03/25
 **/
public class MyHashMap<K, V> {

    private static Integer CAPACITY = 1 << 3;

    private Entry<K, V>[] table;

    private Integer size = 0;

    public MyHashMap() {
        this.table = new Entry[CAPACITY];
    }

    public Integer size() {
        return this.size;
    }

    public V get(K key) {
        Integer hashCode = key.hashCode();
        int index = hashCode % this.table.length;
        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (key.equals(entry.k)) {
                return entry.v;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        Integer hashCode = key.hashCode();
        int index = hashCode % this.table.length;
        index = Math.abs(index);
        try {
            for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
                if (key.equals(entry.k)) {
                    V oldValue = entry.v;
                    entry.v = value;
                    return oldValue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(String.format("key:%s,index:%s,hashCode:%s",key,index,hashCode));
        }

        addEntry(key, value, index);
        return null;

    }

    private void addEntry(K key, V value, int index) {
        table[index] = new Entry<>(key, value, table[index]);
        size ++;
    }

    class Entry<K, V> {
        public K k;
        public V v;
        public Entry<K, V> next;

        public Entry(K k, V v, Entry<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

    }


}
