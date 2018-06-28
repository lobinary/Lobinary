package com.lobinary.算法.查找;

public class TestData{
    public TestData(int[] array,int target, int result) {
        super();
        this.target = target;
        this.result = result;
        this.array = array;
    }
    /**
     * 查找的值
     */
    public int target;
    /**
     * 期望结果
     */
    public int result;
    /**
     * 查找的数组 顺序递增序列
     */
    public int[] array;
}
