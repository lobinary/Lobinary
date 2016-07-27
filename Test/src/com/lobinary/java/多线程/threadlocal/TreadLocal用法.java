package com.lobinary.java.多线程.threadlocal;

public class TreadLocal用法 {

	public static void main(String[] args) {
		final Test t = new Test();
		final Test t2 = new Test();
		final ThreadLocal<Test> tl = new ThreadLocal<Test>();
		
		for (int j = 0; j < 100; j++) {
			new Thread() {

				@Override
				public void run() {
					super.run();
					tl.set(t2);
					t.i++;
					System.out.println("t:"+t.i);
					Test test = tl.get();
					test.i++;
					System.out.println("test"+test.i);
				}

			}.start();
		}
		
		System.out.println(t.i);

		Test test = tl.get();
	}

}

class Test {
	int i;
}
