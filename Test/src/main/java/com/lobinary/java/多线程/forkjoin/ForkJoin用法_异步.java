package com.lobinary.java.多线程.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import com.lobinary.java.多线程.TU;

/**
 * 
 * <pre>
 * 异步执行任务。前面两个例子都是同步执行任务，当启动任务后，主线程陷入了阻塞状态，直到任务执行完毕。
 * 若创建新任务后，希望当前线程能继续执行而非陷入阻塞，则需要异步执行。ForkJoinPool线程池提供了execute()方法来异步启动任务，
 * 而作为任务本身，可以调用fork()方法异步启动新的子任务，并调用子任务的join()方法来取得计算结果。需要注意的是，
 * 异步使用ForkJoin框架，无法使用“工作窃取”算法来提高线程的利用率，针对每个子任务，系统都会启动一个新的线程。
 * </pre>
 *
 * @ClassName: ForkJoin用法_异步
 * @author 919515134@qq.com
 * @date 2016年8月8日 上午10:30:36
 * @version V1.0.0
 */
public class ForkJoin用法_异步 {
	/**
	 * ForkJoinPool实现了工作窃取算法（work-stealing），线程会主动寻找新创建的任务去执行，从而保证较高的线程利用率。
	 * 它使用守护线程（deamon）来执行任务，因此无需对他显示的调用shutdown()来关闭。
	 * 一般情况下，一个程序只需要唯一的一个ForkJoinPool，因此应该按如下方式创建它
	 */
	static final ForkJoinPool forkJoinPool = new ForkJoinPool();//线程的数目等于CPU的核心数
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		String root = "F:";
		String fileType = "exe";
		ForkJoin用法_异步线程 task = new ForkJoin用法_异步线程(root, fileType);
		forkJoinPool.execute(task);
		System.out.print("搜索中");
		while(!task.isCompletedNormally()){
			TU.s(1000);
			System.out.print(".");
		}
		List<String> list = task.get();
		System.out.println(".");
		System.out.println("搜索完毕，以下为搜索结果：");
		for (String f : list) {
			System.out.println(f);
		}
	}

}

class ForkJoin用法_异步线程 extends RecursiveTask<List<String>>{
	
	private String root;
	private String fileType;

	public ForkJoin用法_异步线程(String root,String fileType) {
		super();
		this.root = root;
		this.fileType = fileType;
//		System.out.println("准备搜索"+root);
	}

	private static final long serialVersionUID = 1295678083546909385L;

	@Override
	protected List<String> compute() {
//		System.out.println("开始搜索"+root);
		List<String> result = new ArrayList<String>();
		try {
			List<ForkJoin用法_异步线程> subTaskList = new ArrayList<ForkJoin用法_异步线程>();
			File rootFolder = new File(root);
			String[] list = rootFolder.list();
			for (String fileStr : list) {
				if(fileStr.contains("RECYCLE.BIN")||fileStr.contains("System Volume Information"))continue;
				File f = new File(root+"/"+fileStr);
				if(f.isFile()){
						if(f.getName().toLowerCase().endsWith(fileType.toLowerCase())){
//							System.out.print("√");
							result.add(f.getAbsolutePath());
						}
				}else{
					TU.s(300);
//					System.out.print("#");
					ForkJoin用法_异步线程 task = new ForkJoin用法_异步线程(f.getPath(),fileType);
					task.fork();
					subTaskList.add(task);
				}
			}
//			System.out.println("任务调用完毕，等待子任务执行完毕");
			for (ForkJoin用法_异步线程 subTask : subTaskList) {
				result.addAll(subTask.join());
			}
		} catch (Exception e) {
			System.out.println("异常中："+root);
			e.printStackTrace();
		}
		return result;
	}
	
}
