package com.ritian.datastructure.sourcecode.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TEST
 *
 * @author ritian
 * @since 2020/5/11 21:16
 **/
public class HashMapTest {

    public static void main(String[] args) {

        //1. 声明1个 HashMap的对象
        HashMap<String, Integer> map = new HashMap<>(8);

        // 2. 向HashMap添加数据（成对 放入 键 - 值对）
        map.put("JAVA", 1);
        map.put("C++", 2);
        map.put("PHP", 3);
        map.put("IOS", 4);

        //3. 获取 HashMap 的某个数据
        System.out.println("key = JAVA：" + map.get("JAVA"));


        //4. 获取 HashMap 的全部数据：遍历HashMap
        //核心思想：
        //        * 步骤1：获得key-value对（Entry） 或 key 或 value的Set集合
        //        * 步骤2：遍历上述Set集合(使用for循环 、 迭代器（Iterator）均可)
        //        * 方法共有3种：分别针对 key-value对（Entry） 或 key 或 value

        // 方法1：获得key-value的Set集合 再遍历
        System.out.println("获得key-value的Set集合 再遍历");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        //方法2：通过迭代器，先获得key-value对（Entry）的Iterator，再循环遍历
        System.out.println("通过迭代器，先获得key-value对（Entry）的Iterator，再循环遍历");
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // 遍历时，需先获取entry，再分别获取key、value
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println((String) entry.getKey() + ":" + (Integer) entry.getValue());
        }

        // 方法3： 通过迭代器：先获得key的Iterator，再循环遍历
        System.out.println("通过迭代器：先获得key的Iterator，再循环遍历");
        Iterator iter2 = map.keySet().iterator();
        String key = null;
        while (iter2.hasNext()) {
            key = (String) iter2.next();
            System.out.println(key + ":" + map.get(key));
        }

        // 注：对于遍历方式，推荐使用针对 key-value对（Entry）的方式：效率高
        // 原因：
        // 1. 对于 遍历keySet 、valueSet，实质上 = 遍历了2次：1 = 转为 iterator 迭代器遍历、2 = 从 HashMap 中取出 key 的 value 操作（通过 key 值 hashCode 和 equals 索引）
        // 2. 对于 遍历 entrySet ，实质 = 遍历了1次 = 获取存储实体Entry（存储了key 和 value ）


    }
}
