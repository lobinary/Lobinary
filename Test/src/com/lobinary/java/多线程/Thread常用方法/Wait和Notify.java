package com.lobinary.java.多线程.Thread常用方法;

public class Wait和Notify {
    public static Object object = new Object();
    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread1 thread3 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread3.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (object) {//想调用notify，必须在sychronized中
        	System.out.println(Thread.currentThread().getName()+"调用notify");
        	object.notify();
        }
         
        thread2.start();
    }
     
    static class Thread1 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                try {
                	System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
                	System.out.println("线程"+Thread.currentThread().getName()+"准备wait()");
                    object.wait();
                	System.out.println("线程"+Thread.currentThread().getName()+"收到notify唤醒请求");
                } catch (InterruptedException e) {
                }
                System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
            }
        }
    }
     
    static class Thread2 extends Thread{
        @Override
        public void run() {
            synchronized (object) {
            	System.out.println("线程"+Thread.currentThread().getName()+"获取到了锁");
                object.notify();
                System.out.println("线程"+Thread.currentThread().getName()+"调用了object.notify()");
            }
            System.out.println("线程"+Thread.currentThread().getName()+"释放了锁");
        }
    }
}