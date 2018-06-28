package com.lobinary.算法.查找.二分查找;

import com.lobinary.算法.查找.BinarySearchInterface;

/**
 * 循环方式实现的二分查找
 * @author bin.lv
 * @since create by bin.lv 2017年11月27日 下午6:26:13
 *
 */
public class CirculationBinarySearch implements BinarySearchInterface {

    @Override
    public int search(int[] array, int target) {
        int start = 0;
        int end = array.length - 1;
        while (start <= end) {
            int mid = (end - start) / 2;
            mid = start + mid;
            int midValue = array[mid];
            if (midValue == target) {
                return mid;
            } else if (midValue < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

}
