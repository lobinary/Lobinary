package com.lobinary.test.normal;

import com.lobinary.java.多线程.TU;

public class Final变量变线程参数 {
	
	final static int i = 1;

	
	public static void main(String[] args) {
		new TT(i).start();
		TU.s(1000);
		System.out.println(i);
	}
	
	
	static class TT extends Thread{
		int i = 0;

		public TT(int i) {
			super();
			this.i = i;
		}

		@Override
		public synchronized void start() {
			System.out.println(i);
			i = 4;
			System.out.println(i);
		}
		
	}

}