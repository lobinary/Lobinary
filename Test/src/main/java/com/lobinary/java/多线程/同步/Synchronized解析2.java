package com.lobinary.java.多线程.同步;

import com.lobinary.java.多线程.TU;

/**
 * 经过这个测试，证明：
 *  一个对象Synchronized解析中，虽然是两个方法t1、t2两个方法上分别放的synchronized标识
 *  但是当t1被访问时，t2是不能被访问的，也在等待t1执行完，等t1被执行完毕后，再从等待t1或t2的程序随机选一个执行
 * @author bin.lv
 * @since create by bin.lv 2017年9月8日 下午4:08:43
 *
 */
public class Synchronized解析2 {

    private static String s = "";
    private static String s2 = "1";
    
    private static Integer i = 0;
    
    /**
     * 在测试过程中发现一个问题，当s和s2同时都为""空时，输出的是下方①①①①①①①①①①①①①①①①①①①①①①①①①①
     * 后来经过分析，原来是s的""和s2的""是一个""，所以导致t1和t2共用的是一个锁
     * 当给s和s2变成不同字符串之后，如""和"1"，则输出变成下方②②②②②②②②②②②②②②②②②②②②②②②②②
     * 这样就达到了t1和t2一起串行执行的结果
      -----------------①①①①①①①①①①①①①①①①①①①①①①①①①①-----------------------------
        Thread-0 : t1进入get
        Thread-2 : t2进入get
        Thread-1 : t1进入get
        Thread-3 : t2进入get
        Thread-0 : t1睡觉
        
        Thread-0 : t1睡醒1
        Thread-3 : t2睡觉
        
        Thread-3 : t2睡醒2
        Thread-1 : t1睡觉
        
        Thread-1 : t1睡醒3
        Thread-2 : t2睡觉
        
        Thread-2 : t2睡醒4
        
        ---------------②②②②②②②②②②②②②②②②②②②②②②②②②---------------------------------------
        Thread-3 : t2进入get
        Thread-1 : t1进入get
        Thread-2 : t2进入get
        Thread-0 : t1进入get
        Thread-1 : t1睡觉
        Thread-3 : t2睡觉
        
        Thread-1 : t1睡醒2
        Thread-0 : t1睡觉
        Thread-3 : t2睡醒3
        Thread-2 : t2睡觉
        
        Thread-0 : t1睡醒4
        Thread-2 : t2睡醒4
        

     * 
     * @since add by bin.lv 2017年9月8日 下午5:21:01
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(){ @Override public void run() { super.run(); Synchronized解析2.t1();  } }.start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(){ @Override public void run() { super.run(); Synchronized解析2.t2();  } }.start();
        }
        TU.s(100000);
    }
    
    public static void t1(){
        TU.l("t1进入get");
        synchronized(s){
            i++;
            TU.l("t1睡觉");
            TU.s(5000);
            TU.l("t1睡醒"+i);
        }
    }
    
    public static void t2(){
        TU.l("t2进入get");
        synchronized(s2){
            i++;
            TU.l("t2睡觉");
            TU.s(5000);
            TU.l("t2睡醒"+i);
        }
    }

    
}
