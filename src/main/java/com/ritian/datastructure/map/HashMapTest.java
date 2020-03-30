package com.ritian.datastructure.map;

import java.util.*;

/**
 * @author ritian.Zhang
 * @date 2019/03/25
 **/
public class HashMapTest {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();

        map.put("a","1");
        map.put("b","2");
        map.put("c","3");
        map.put("d","4");
        map.put("e","5");

        map.keySet().forEach(key->{
            int hashCode = key.hashCode();
            System.out.println(String.format("%s的hash值是%s",key,hashCode));
        });
    }
}
