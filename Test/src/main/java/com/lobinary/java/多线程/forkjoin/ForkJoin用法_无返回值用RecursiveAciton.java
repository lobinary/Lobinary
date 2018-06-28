package com.lobinary.java.多线程.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import com.lobinary.算法.排序.SortUtil;

public class ForkJoin用法_无返回值用RecursiveAciton {
	
	/**
	 * ForkJoinPool实现了工作窃取算法（work-stealing），线程会主动寻找新创建的任务去执行，从而保证较高的线程利用率。
	 * 它使用守护线程（deamon）来执行任务，因此无需对他显示的调用shutdown()来关闭。
	 * 一般情况下，一个程序只需要唯一的一个ForkJoinPool，因此应该按如下方式创建它
	 */
	static final ForkJoinPool forkJoinPool = new ForkJoinPool();//线程的数目等于CPU的核心数
	
	public static void main(String[] args) throws InterruptedException {
		long[] la = {0,1,2,3,4,5,6,7,8,9};
		SortUtil.out(la);
		SortTask sortTask = new SortTask(la);
		forkJoinPool.invoke(sortTask);//invoke 调用属于同步调用，一定会等到forkJoin任务执行完，本main方法才会继续往下走，如果想要异步调用，请使用execute
		forkJoinPool.shutdown();
		
		TimeUnit.SECONDS.sleep(3);

		//Blocks until all tasks have completed execution after a shutdown request, 
		//or the timeout occurs, or the current thread is interrupted, 
		//whichever happens first. Because the commonPool() never terminates until program shutdown, 
		//when applied to the common pool, this method is equivalent to awaitQuiescence(long, TimeUnit) but always returns false.
		
		System.out.print("最后结果为：");SortUtil.out(la);
	}

}

class SortTask extends RecursiveAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5122641586701777134L;
	
	long[] la;
	int start;
	int end;
	int 默认分割大小 = 2;

	public SortTask(long[] la) {
		super();
		this.la = la;
		start = 0;
		end = la.length;
	}

	public SortTask(long[] la, int start, int end) {
		super();
		this.la = la;
		this.start = start;
		this.end = end;
	}


	/**
	 * 	0,1,2,3,4,5,6,7,8,9
		start:0,midle:5,end:10
		start:0,midle:2,end:5
		1,2,2,3,4,5,6,7,8,9
		start:2,midle:3,end:5
		1,2,3,3,4,5,6,7,8,9
		1,2,3,4,5,5,6,7,8,9
		start:5,midle:7,end:10
		start:7,midle:8,end:10
		1,2,3,4,5,5,6,7,9,10
		1,2,3,4,5,6,7,7,9,10
		1,2,3,4,5,6,7,8,9,10
		最后结果为：1,2,3,4,5,6,7,8,9,10
		
		0,1,2,3,4,5,6,7,8,9
		start:0,midle:5,end:10
		start:0,midle:2,end:5
		1,2,2,3,4,5,6,7,8,9
		start:2,midle:3,end:5
		1,2,2,4,5,5,6,7,8,9
		start:5,midle:7,end:10
		1,2,3,4,5,5,6,7,8,9
		start:7,midle:8,end:10
		1,2,3,4,5,5,6,7,9,10
		1,2,3,4,5,6,7,7,9,10
		1,2,3,4,5,6,7,8,9,10
		最后结果为：1,2,3,4,5,6,7,8,9,10
		
		如何判断多线程一起执行的呢：每次执行结果不一样
	 */
	@Override
	protected void compute() {
		if(end-start>默认分割大小){//如果分割尺寸不够小就继续分割，然后通过invokeAll执行
			System.out.println("start:"+start+",midle:"+(start+(end-start)/2)+",end:"+end);
			SortTask preTask = new SortTask(la, start, start+(end-start)/2);
			SortTask postTask = new SortTask(la, start+(end-start)/2, end);
			invokeAll(preTask,postTask);
		}else{
			for (int i = start; i < end; i++) {
				la[i] += 1;
			}
			SortUtil.out(la);
		}
	}

	
}
