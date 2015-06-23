package com.boce.test;

public class 线程测试辅助类 {
	
	public void test(){
		System.out.println("开始");
		a();
		System.out.println("结束");
	}
	
	public void a(){
		new Thread(){
			public void run() {
				for (int i = 0; i < 10000000; i++) {
					System.out.println(i);
				}
			};
		}.start();
	}

}
