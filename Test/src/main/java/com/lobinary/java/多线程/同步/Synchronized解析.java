package com.lobinary.java.多线程.同步;

import com.lobinary.java.多线程.TU;
import com.lobinary.工具类.LU;

/**
 * 经过这个测试，证明：
 *  一个对象Synchronized解析中，虽然是两个方法t1、t2两个方法上分别放的synchronized标识
 *  但是当t1被访问时，t2是不能被访问的，也在等待t1执行完，等t1被执行完毕后，再从等待t1或t2的程序随机选一个执行
 * @author bin.lv
 * @since create by bin.lv 2017年9月8日 下午4:08:43
 *
 */
public class Synchronized解析 {
    
    private static int i = 0 ;
    
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(){ @Override public void run() { super.run(); Synchronized解析.t1();  } }.start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(){ @Override public void run() { super.run(); Synchronized解析.t2();  } }.start();
        }
        TU.s(100000);
    }
    
    public synchronized static void t1(){
        LU.l("t1进入get");
        i++;
        LU.l("t1睡觉");
        TU.s(5000);
        LU.l("t1睡醒"+i);
    }
    
    public synchronized static void t2(){
        LU.l("t2进入get");
        i++;
        LU.l("t2睡觉");
        TU.s(5000);
        LU.l("t2睡醒"+i);
    }

    
}
