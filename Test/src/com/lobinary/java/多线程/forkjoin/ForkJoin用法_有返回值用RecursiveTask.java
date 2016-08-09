package com.lobinary.java.多线程.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

import com.lobinary.java.多线程.TU;

public class ForkJoin用法_有返回值用RecursiveTask {

	/**
	 * ForkJoinPool实现了工作窃取算法（work-stealing），线程会主动寻找新创建的任务去执行，从而保证较高的线程利用率。
	 * 它使用守护线程（deamon）来执行任务，因此无需对他显示的调用shutdown()来关闭。
	 * 一般情况下，一个程序只需要唯一的一个ForkJoinPool，因此应该按如下方式创建它
	 */
	static final ForkJoinPool forkJoinPool = new ForkJoinPool();//线程的数目等于CPU的核心数
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long[] la = {111,45321,34134,1};
		CountArray1TimesTask task = new CountArray1TimesTask(la);
		int i = forkJoinPool.invoke(task);
		System.out.println(i);
	}

}

class CountArray1TimesTask extends RecursiveTask<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5467534374358376290L;
	
	long[] la;
	int start;
	int end;
	int THRESHOLD = 1;

	public CountArray1TimesTask(long[] la) {
		super();
		this.la = la;
		this.start = 0;
		this.end = la.length;
	}

	public CountArray1TimesTask(long[] la, int start, int end) {
		super();
		this.la = la;
		this.start = start;
		this.end = end;
		TU.s(1000);
		System.out.println(this);
	}

	@Override
	protected Integer compute() {
		if(end-start>THRESHOLD){
			CountArray1TimesTask preTask = new CountArray1TimesTask(la, start, start+(end-start)/2);
			CountArray1TimesTask postTask = new CountArray1TimesTask(la, start+(end-start)/2, end);
			invokeAll(preTask,postTask);
			try {
				int result = preTask.get()+postTask.get();
				return result;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}else{
			int countor = 0;
			for (int j = start; j < end; j++) {
				String s = ""+la[j];
				char[] ca = s.toCharArray();
				for (int i = 0; i < ca.length; i++) {
					if((""+ca[i]).equals("1")){
//						System.out.println("位置"+j+"包含1");
						countor++;
					}else{
//						System.out.println("位置"+j+"不包含1");
					}
				}
				synchronized (la) {
					System.out.print("start:"+start+",end:"+end);
					System.out.println(",countor:"+countor);
				}
			}
			return countor;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "CountArray1TimesTask [la=" + Arrays.toString(la) + ", start=" + start + ", end=" + end + ", THRESHOLD=" + THRESHOLD + "]";
	}
	
}