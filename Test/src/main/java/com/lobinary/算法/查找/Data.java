package com.lobinary.算法.查找;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<TestData> BINARY_SEARCH_TEST_DATA;
    
    static {
        BINARY_SEARCH_TEST_DATA = new ArrayList<TestData>();
        int[] a1 = {1,5,6,7,8,9,10};
        BINARY_SEARCH_TEST_DATA.add(new TestData(a1,9,5));
        int[] a2 = {7,8,9,10};
        BINARY_SEARCH_TEST_DATA.add(new TestData(a2,9,2));
        int[] a3 = {10};
        BINARY_SEARCH_TEST_DATA.add(new TestData(a3,10,0));
        int[] a4 = {9,10};
        BINARY_SEARCH_TEST_DATA.add(new TestData(a4,10,1));
        int[] a5 = {9,10,11};
        BINARY_SEARCH_TEST_DATA.add(new TestData(a4,9,0));
    }
    
    
}
