package com.tencent.chiris.projectrank.util;

import java.util.Comparator;
import java.util.List;

public class SortUtil {

    /**
     * 希尔排序
     * 时间复杂度 O(NlgN)
     * 空间复杂度O(1)
     * 在排序属性大量重复的场景下，在200万以下的数量级能在毫秒级完成
     * @param list
     * @param comparator
     * @param <T>
     */
    public static <T> void shellSort(List<T> list, Comparator<T> comparator){
        int N = list.size();
        int h = 1;
        while(h < N / 3){
            h = h * 3 + 1;
        }
        while(h >= 1){
            for (int i = h; i < N; i ++){
                for (int j = i; j >= h && more(comparator, list.get(j), list.get(j - h)); j -= h){
                    swap(list, j , j - h);
                }
            }
            h /= 3;
        }
    }

    private static  <T> void swap(List<T> list, int i, int j){
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    private <T>boolean less (Comparator<T> comparator, T o1, T o2){
        int compare = comparator.compare(o1, o2);
        return compare < 0;
    }
    private static <T>boolean more (Comparator<T> comparator, T o1, T o2){
        int compare = comparator.compare(o1, o2);
        return compare > 0;
    }
}