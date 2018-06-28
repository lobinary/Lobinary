package com.lobinary.java.多线程.concurrent.Phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import com.lobinary.java.多线程.TU;

/**
 * 
 * <pre>
 * 	http://blog.csdn.net/andycpp/article/details/8838820
 * 	    用Phaser替代CyclicBarrier更简单，CyclicBarrier的await()方法可以直接用Phaser的arriveAndAwaitAdvance()方法替代。
 * </pre>
 *
 * @ClassName: Phaser替代CountDownLatch用法
 * @author 919515134@qq.com
 * @date 2016年10月10日 下午5:15:56
 * @version V1.0.0
 */
public class Phaser替代CountDownLatch用法 {

	public static void main(String[] args) {
		Phaser phaser = new Phaser(1); //此处可使用CountDownLatch(1)
		int phase = phaser.getPhase();
		System.out.println("==============="+phase);
		
		for(int i=0; i<3; i++) {
			new MyThread((char)(97+i), phaser).start();
		}
		TU.s(1000);
		/*
		 * Arrives at this phaser, without waiting for others to arrive. 
			It is a usage error for an unregistered party to invoke this method. 
			However, this error may result in an IllegalStateException only upon some subsequent operation on this phaser, if ever.
		 */

		System.out.println("==============="+phaser.getPhase());
		phaser.arrive();//

		for(int i=0; i<3; i++) {
			new MyThread((char)(97+i), phaser).start();
		}
		TU.s(1000);

		System.out.println("==============="+phaser.getPhase());
		phaser.arrive();  //此处可使用latch.countDown()
		//    用Phaser替代CyclicBarrier更简单，CyclicBarrier的await()方法可以直接用Phaser的arriveAndAwaitAdvance()方法替代。

	}
}

class MyThread extends Thread {
	private char c;
	private Phaser phaser;
	
	public MyThread(char c, Phaser phaser) {
		this.c = c;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		int phase = phaser.getPhase();
		System.out.println("==============="+phase);
		phaser.awaitAdvance(phase); //此处可使用latch.await()
		for(int i=0; i<4; i++) {
			TU.l(c+" ");
			if(i % 2 == 1) {
				System.out.println();
			}
		}
	}
}
