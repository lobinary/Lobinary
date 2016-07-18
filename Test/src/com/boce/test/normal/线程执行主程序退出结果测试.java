package com.boce.test.normal;

public class 线程执行主程序退出结果测试 {
	
	public static void main(String[] args) {
		System.out.println("start");
		new 线程执行主程序退出结果测试().a();
		System.out.println("end");
	}
	
	public void a(){
		线程测试辅助类 a = new 线程测试辅助类();
		a.test();
	}

}
