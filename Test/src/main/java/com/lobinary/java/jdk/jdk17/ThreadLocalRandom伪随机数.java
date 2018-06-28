package com.lobinary.java.jdk.jdk17;

import java.util.concurrent.ThreadLocalRandom;

/*
 * ThreadLocalRandom 类提供了线程安全的伪随机数生成器。
 */
public class ThreadLocalRandom伪随机数 {

	public static void main(String[] args) {
		float nextFloat = ThreadLocalRandom.current().nextFloat();
		System.out.println(nextFloat);
		System.out.println(nextFloat*100000000l);
	}
	
}
