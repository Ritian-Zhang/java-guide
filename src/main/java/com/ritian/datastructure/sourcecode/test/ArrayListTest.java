package com.ritian.datastructure.sourcecode.test;

import java.util.ArrayList;

/**
 * TEST
 *
 * @author ritian
 * @since 2020/4/2 10:12
 **/
public class ArrayListTest {


    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");

        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
            System.out.println("删除元素");
        }
        System.out.println("size:"+list.size()+" list="+list);
    }
}
