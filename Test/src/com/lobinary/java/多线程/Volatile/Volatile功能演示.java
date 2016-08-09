package com.lobinary.java.多线程.Volatile;

import java.util.concurrent.atomic.AtomicInteger;

import com.lobinary.java.多线程.TU;

/**
 * 
 * <pre>
 * http://lib.csdn.net/article/javase/4726
 * </pre>
 *
 * @ClassName: Volatile功能演示
 * @author 919515134@qq.com
 * @date 2016年8月9日 上午9:28:56
 * @version V1.0.0
 */
public class Volatile功能演示 {
	
	public static void main(String[] args) {
		VoleatileModel vm = new VoleatileModel();
		TU.start(Volatile功能演示线程.class, 50, vm);
	}

	public static class Volatile功能演示线程 implements Runnable{
		
		VoleatileModel vm;

		public Volatile功能演示线程(VoleatileModel vm) {
			super();
			this.vm = vm;
		}

		@Override
		public void run() {
			TU.l("(volatile线程安全,不会出现重复值):"+vm.i.getAndIncrement());
			TU.s(5000);
			TU.l("(线程不安全变量,会出现重复值):"+vm.j.getAndIncrement());
		}
		
	}
}

class VoleatileModel {
	public volatile AtomicInteger i = new AtomicInteger(0);
	public AtomicInteger j = new AtomicInteger(0);
}


