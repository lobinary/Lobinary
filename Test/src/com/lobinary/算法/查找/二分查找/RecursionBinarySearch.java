package com.lobinary.算法.查找.二分查找;

import com.lobinary.算法.查找.BinarySearchInterface;

/**
 * 递归实现的二分查找
 * @author bin.lv
 * @since create by bin.lv 2017年11月27日 下午6:04:19
 *
 */
public class RecursionBinarySearch implements BinarySearchInterface{

    @Override
    public int search(int[] array, int target) {
        return recursionBinarySearch(array, target,0,array.length-1);
    }

    /**
     * 通过递归实现
     * 
     * @since add by bin.lv 2017年11月27日 下午6:04:52
     * @param array
     * @param target
     * @param start
     * @param end
     * @return
     */
    private static int recursionBinarySearch(int[] array, int target,int start, int end) {
        int mid = (end-start) / 2;
        mid = mid + start;
        if(target==array[mid]){
            return mid;
        }else if(target<array[mid]){
            return recursionBinarySearch(array, target, 0, mid-1);
        }else{
            return recursionBinarySearch(array, target, mid+1, end);
        }
    }
    
}
