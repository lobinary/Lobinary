package com.lobinary.java.多线程.Thread常用方法;

/**
 * 	1.所谓守护线程就是运行在程序后台的线程，程序的主线程Main（比方java程序一开始启动时创建的那个线程）不会是守护线程 
	2.Daemon thread在Java里面的定义是，如果虚拟机中只有Daemon thread在运行，则虚拟机退出。 
	 虚拟机中可能会同时有很多个线程在运行，只有当所有的非守护线程都结束的时候，虚拟机的进程才会结束，不管在运行的线程是不是main()线程。
	3.Main主线程结束了（Non-daemon thread）,如果此时正在运行的其他threads是daemonthreads,JVM会使得这个threads停止,JVM也停下
	  如果此时正在运行的其他threads有Non-daemonthreads,那么必须等所有的Non daemon线程结束了，JVM才会停下来
	4.总之,必须等所有的Non-daemon线程都运行结束了，只剩下daemon的时候，JVM才会停下来，注意Main主程序是Non-daemon 线程
	默认产生的线程全部是Non-daemon线程。
	5.JVM的资源回收线程就是这类线程。
	6.在该类线程中产生的其他线程不用设置，默认都是守护线程。
	5.Thread.setDaemon（）用法使用：
		1. setDaemon需要在start方法调用之前使用
		2. 线程划分为用户线程和后台(daemon)进程，setDaemon将线程设置为后台进程
		3. 如果jvm中都是后台进程，当前jvm将exit。（随之而来的，所有的一切烟消云散，包括后台线程啦）
		4. 主线程结束后，
	      	1）用户线程将会继续运行
	     	2） 如果没有用户线程，都是后台进程的话，那么jvm结束
	另外：
	setDaemon方法把Java的线程设置为守护线程，此方法的调用必须在线程启动之前执行。只有在当前jvm中所有的线程都为守护线程时，jvm才会退出。
	如果创建的线程没有显示调用此方法，这默认为用户线程。
 */
public class Daemon {
	
	public static void main(String[] args) {
		Thread t = new Thread(){

			@Override
			public void run() {
				super.run();
				for (int i = 0; i < 5; i++) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(i);
				}
			}
			
		};
//		t.setDaemon(true);
		t.start();
		System.out.println("------");
	}

}
