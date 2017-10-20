package com.lobinary.java.jvm.内存分析.模拟内存溢出;

/**
 * Exception in thread "main" java.lang.StackOverflowError
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.栈溢出.t(栈溢出.java:10)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.栈溢出.t(栈溢出.java:10)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.栈溢出.t(栈溢出.java:10)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.栈溢出.t(栈溢出.java:10)
    
 * @author bin.lv
 * @since create by bin.lv 2017年9月20日 下午6:12:05
 *
 */
public class 栈溢出 {
    
    public static void main(String[] args) {
        t();
    }

    public static void t(){
        t();
    }
    
}
