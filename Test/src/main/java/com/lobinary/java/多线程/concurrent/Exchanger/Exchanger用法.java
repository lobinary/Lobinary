package com.lobinary.java.多线程.concurrent.Exchanger;

import java.util.concurrent.Exchanger;

import com.lobinary.java.多线程.TU;

public class Exchanger用法 {

	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<String>();
		new Exchanger用法线程A(exchanger).start();
		new Exchanger用法线程B(exchanger).start();
	}

	static class Exchanger用法线程A extends Thread{
		
		Exchanger<String> exchanger;
        
        public Exchanger用法线程A(Exchanger<String> exchanger) {
			super();
			this.exchanger = exchanger;
		}

		@Override
        public void run() {
			TU.l("线程A","开始执行");
			String s = "线程A的物品";
			TU.l("生成变量："+s);
			TU.l("准备等待交换变量："+s);
			try {
				s = exchanger.exchange(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TU.l("拿到变量："+s);
        }
    }
	

	static class Exchanger用法线程B extends Thread{
		
		Exchanger<String> exchanger;
        
        public Exchanger用法线程B(Exchanger<String> exchanger) {
			super();
			this.exchanger = exchanger;
		}

		@Override
        public void run() {
			TU.l("线程B","开始执行");
			String s = "线程B的物品";
			TU.l("生成变量："+s);
			TU.s(2000);
			TU.l("准备等待交换变量："+s);
			try {
				s = exchanger.exchange(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TU.l("拿到变量："+s);
        }
    }
}
