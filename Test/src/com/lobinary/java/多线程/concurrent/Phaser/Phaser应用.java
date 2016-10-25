package com.lobinary.java.多线程.concurrent.Phaser;

import java.util.concurrent.Phaser;

public class Phaser应用 {
	
	public static void main(String[] args) {
		
		System.out.println("程序开始执行");
		
		Phaser phaser = new Phaser(3){//共有3个工作线程，因此在构造函数中赋值为3  
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println();
				System.out.println("=============第"+phase+"阶段运行结束,共有"+registeredParties+"个线程正在执行.===============");
				if(registeredParties==1)return true;
				return super.onAdvance(phase, registeredParties);
				 //本例中，当只剩一个线程时，这个线程必定是主线程，返回true表示终结  
			}
		};
		
		System.out.println("准备将主线程注册到phaser中,当前注册线程数量为："+phaser.getRegisteredParties());
		phaser.register();
		System.out.println("主线程注册phaser完毕,当前注册线程数量为："+phaser.getRegisteredParties());
		System.out.println("==========当前注册线程数量为："+phaser.getRegisteredParties()+"，准备启动3个线程执行该程序==========");
		for (int i = 0; i < 3; i++) {
			输出26字母线程 输出26字母线程 = new 输出26字母线程(phaser,(char)(97+i));
			输出26字母线程.start();
		}
		while(!phaser.isTerminated()){
			phaser.arriveAndAwaitAdvance();
		}
		
		System.out.println("程序执行结束");
	}

}

class 输出26字母线程 extends Thread{

	Phaser phaser;
	char c;
	
	public 输出26字母线程(Phaser phaser, char c) {
		super();
		this.phaser = phaser;
		this.c = c;
	}

	@Override
	public void run() {
		while(!phaser.isTerminated()){
			for (int i = 0; i < 5; i++) {
				System.out.print(c);
			}
			c += 3;
			if(c>'z'){
				phaser.arriveAndDeregister();
				break;
			}
			phaser.arriveAndAwaitAdvance();
		}
	}
	
}