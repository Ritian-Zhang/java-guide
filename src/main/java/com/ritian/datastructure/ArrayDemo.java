package com.ritian.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组是可以再内存中连续存储多个元素的结构，在内存中的分配也是连续的，数组中的元素通过数组下标进行访问，数组下标从0开始。
 * 优点： ①按照索引查询元素速度快
 * ②按照索引遍历数组方便
 * 缺点： ①数组的大小固定后就无法扩容了
 * ②数组只能存储一种类型的数据
 * ③添加，删除的操作慢，因为要移动其他的元素。
 * 适用场景： 频繁查询，对存储空间要求不大，很少增加和删除的情况
 *
 * @author ritian.Zhang
 * @date 2019/03/27
 **/
public class ArrayDemo {

    public void copy() {
        int[] arr = {1, 2, 4,0};


        System.arraycopy(arr, 1, arr, 1 + 1, 2);

        arr[2] = 3;
        for (int i : arr) {
            System.out.println(i);
        }

    }


    public static void main(String[] args) {
        ArrayDemo demo = new ArrayDemo();

        demo.copy();
    }
}
