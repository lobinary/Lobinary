package com.lobinary.java.多线程.Thread常用方法;

public class Join用法 implements Runnable {

	public static int a = 0;

	public synchronized void inc() {
		a++;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {
			inc();
		}
	}

	public static void main(String[] args) throws Exception {

		Runnable r = new Join用法();
		Thread t1 = new Thread(r);
		t1.start();

		t1.join();//Waits for this thread to die.  可以让a的结果是预期结果
		System.out.println(a);
	}
}