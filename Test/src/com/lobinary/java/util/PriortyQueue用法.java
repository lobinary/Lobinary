package com.lobinary.java.util;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 *	个人理解：
 *			对一些任务有执行先后顺序的，可以多考虑这个，可用于生产者消费者模式中的仓库
 *
 */
public class PriortyQueue用法 {
	private String name;
	private int population;

	public PriortyQueue用法(String name, int population) {
		this.name = name;
		this.population = population;
	}

	public String getName() {
		return this.name;
	}

	public int getPopulation() {
		return this.population;
	}

	public String toString() {
		return getName() + " - " + getPopulation();
	}

	public static void main(String args[]) {
		Comparator<PriortyQueue用法> OrderIsdn = new Comparator<PriortyQueue用法>() {
			public int compare(PriortyQueue用法 o1, PriortyQueue用法 o2) {
				int number1 = o1.getPopulation();
				int number2 = o2.getPopulation();
				if (number2 > number1) {
					return 1;
				} else if (number2 < number1) {
					return -1;
				} else {
					return 0;
				}

			}

		};
		Queue<PriortyQueue用法> priorityQueue = new PriorityQueue<PriortyQueue用法>(11, OrderIsdn);

		PriortyQueue用法 t1 = new PriortyQueue用法("t1", 1);
		PriortyQueue用法 t3 = new PriortyQueue用法("t3", 3);
		PriortyQueue用法 t2 = new PriortyQueue用法("t2", 2);
		PriortyQueue用法 t4 = new PriortyQueue用法("t4", 0);
		priorityQueue.add(t1);
		priorityQueue.add(t3);
		priorityQueue.add(t2);
		priorityQueue.add(t4);
		System.out.println(priorityQueue.poll().toString());
	}
}