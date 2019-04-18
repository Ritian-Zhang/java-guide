package com.ritian.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 冒泡排序
 * <p>
 * 原理：比较两个相邻的元素，将值大的元素交换至右端
 * 特点：效率低，实现简单
 * 总数据长度为N，N个数字要完成排序，总共要进行N-1趟排序，第i趟的排序次数为(N-i)次；
 * </p>
 *
 * @author ritian.Zhang
 * @date 2019/04/15
 **/
@Slf4j
public class BubbleSort {

    public static int[] sort(int[] array, boolean isAsc) {
        log.info("排序前数组：{}", Arrays.toString(array));
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                //升序
                if (isAsc) {
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                } else {
                    //降序
                    if (array[j] < array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
            }
        }
        log.info("排序后数组：{}", Arrays.toString(array));
        return array;

    }

    public static void main(String[] args) {
        int[] array = {4, 3, 6, 9, 1, 2};
        sort(array, true);
    }
}
