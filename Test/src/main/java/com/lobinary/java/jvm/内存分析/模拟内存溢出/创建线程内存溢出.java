package com.lobinary.java.jvm.内存分析.模拟内存溢出;

/**
 * JVM args:    -Xss 2M
 * 
 * Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
    at java.lang.Thread.start0(Native Method)
    at java.lang.Thread.start(Unknown Source)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.创建线程内存溢出.t(创建线程内存溢出.java:34)
    at com.lobinary.java.jvm.内存分析.模拟内存溢出.创建线程内存溢出.main(创建线程内存溢出.java:19)
    
    

 * @author bin.lv
 * @since create by bin.lv 2017年9月20日 下午6:58:23
 *
 */
public class 创建线程内存溢出 {

    private static void dontStop() throws InterruptedException {
        while (true) {
            Thread.sleep(100000);
        }
    }

    public static void main(String[] args) {
                    创建线程内存溢出 tt = new 创建线程内存溢出();
        tt.t();
    }

    private static void t() {
        while (true) {
            new Thread() {
                private String[] s = new String[1000];
                public void run() {
                    try {
                        dontStop();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                };
            }.start();
        }
    }

}
