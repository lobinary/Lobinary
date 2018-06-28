package com.lobinary.java.多线程.线程间协作;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 生产者生产满了就wait消费者，消费者消费完了就notify生产者
 *
 */
public class 生产者消费者Condition版本示例 {
	private int queueSize = 10;
	private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();

	public static void main(String[] args) {
		生产者消费者Condition版本示例 test = new 生产者消费者Condition版本示例();
		Producer producer = test.new Producer();
		Consumer consumer = test.new Consumer();

		producer.start();
		consumer.start();
	}

	class Consumer extends Thread {

		@Override
		public void run() {
			consume();
		}

		private void consume() {
			while (true) {
				lock.lock();
				try {
					
					while (queue.size() == 0) {
						/**
						 * 处于等待状态的线程可能会收到错误警报和伪唤醒，如果不在循环中检查等待条件，程序就会在没有满足结束条件的情况下退出。
						 * 因此，当一个等待线程醒来时，不能认为它原来的等待状态仍然是有效的，在notify()方法调用之后和等待线程醒来之前这段时间它可能会改变。
						 * 这就是在循环中使用wait()方法效果更好的原因，
						 * 你可以在Eclipse中创建模板调用wait和notify试一试。
						 * 如果你想了解更多关于这个问题的内容，推荐你阅读《Effective Java》这本书中的线程和同步章节。
						 */
						try {
							System.out.println("队列空，等待数据");
							notEmpty.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					queue.poll(); // 每次移走队首元素
					notFull.signal();
					System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
				} finally {
					lock.unlock();
				}
			}
		}
	}

	class Producer extends Thread {

		@Override
		public void run() {
			produce();
		}

		private void produce() {
			while (true) {
				lock.lock();
				try {
					while (queue.size() == queueSize) {
						try {
							System.out.println("队列满，等待有空余空间");
							notFull.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					queue.offer(1); // 每次插入一个元素
					notEmpty.signal();
					System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
