package com.lobinary.java.多线程.concurrent.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrier重用用法 {
	public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N);
         
        for(int i=0;i<N;i++) {
            new Writer(barrier,"第一批线程").start();
        }
         
        System.out.println("============");
         
        System.out.println("CyclicBarrier重用");
         
        for(int i=0;i<N;i++) {
            new Writer(barrier,"第二批线程").start();
        }
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        private String threadName;
        public Writer(CyclicBarrier cyclicBarrier,String threadName) {
            this.cyclicBarrier = cyclicBarrier;
            this.threadName = threadName;
        }
 
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch(BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"所有线程写入完毕，继续处理其他任务...");
        }
    }
}
