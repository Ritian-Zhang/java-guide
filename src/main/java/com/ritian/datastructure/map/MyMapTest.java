package com.ritian.datastructure.map;

/**
 * @author ritian.Zhang
 * @date 2019/03/25
 **/
public class MyMapTest {
    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put("jack" + i, "" + i);
        }
        map.put("jack8","rose");

        System.out.println(map.get("jack8"));

    }
}
