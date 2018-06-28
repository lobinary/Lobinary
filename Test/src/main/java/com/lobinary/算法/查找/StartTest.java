package com.lobinary.算法.查找;

import com.lobinary.算法.查找.二分查找.CirculationBinarySearch;
import com.lobinary.算法.查找.二分查找.RecursionBinarySearch;
/*
 * 参考文档
 * http://www.cnblogs.com/ider/archive/2012/04/01/binary_search.html
 */
public class StartTest {
    
    public static void main(String[] args) {
        System.out.println("========================递归二分法实现========================");
        test(new RecursionBinarySearch());
        System.out.println("========================循环二分法实现========================");
        test(new CirculationBinarySearch());
    }
    
    public static void test(BinarySearchInterface search){
        for(TestData td :Data.BINARY_SEARCH_TEST_DATA){
            int[] array = td.array;
            int target = td.target;
            int index = search.search(array, target);
            System.out.println("期望结果：" + td.result);
            System.out.println("查找结果：" + index);
            System.out.println("是否正确:" + (index == td.result ? "正确" : "不正确########################################"));
            System.out.println("-----------------------------------------------------------");
        }
    }

}
