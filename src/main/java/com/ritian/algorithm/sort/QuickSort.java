package com.ritian.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 快速排序
 * <p>
 *
 * </p>
 *
 * @author ritian.Zhang
 * @date 2019/04/15
 **/
@Slf4j
public class QuickSort {

    public static int[] sort(int[] array, int start, int end) {
        while (start < end) {
            //基准值
            int baseNum = array[start];
            int midNum;
            int i = start;
            int j = end;
            do {
                while (array[i] < baseNum && i < end) {
                    i++;
                }
                while (array[j] > baseNum && j > start) {
                    j--;
                }
                if (i <= j) {
                    midNum = array[i];
                    array[i] = array[j];
                    array[j] = midNum;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j) {
                sort(array, start, j);
            }
            if (end > i) {
                sort(array, i, end);
            }
        }
        return array;
    }
}
