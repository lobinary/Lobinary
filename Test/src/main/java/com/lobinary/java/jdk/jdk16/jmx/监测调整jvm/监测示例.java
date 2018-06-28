package com.lobinary.java.jdk.jdk16.jmx.监测调整jvm;

import java.lang.management.ManagementFactory;

import com.lobinary.java.多线程.TU;


/**
 * 
 * <pre>
 * 	http://www.ibm.com/developerworks/cn/java/j-lo-jse63/index.html
 * </pre>
 *
 * @ClassName: 监测示例
 * @author 919515134@qq.com
 * @date 2016年10月8日 下午5:18:45
 * @version V1.0.0
 */
public class 监测示例 {
	
	/**
	 * JMX 提供的虚拟机检测 API
	 * 
		检测虚拟机当前的状态总是 Java 开放人员所关心的，也正是因为如此，出现了大量的 profiler 工具来检测当前的虚拟机状态。
		从 Java SE 5 之后，在 JDK 中，我们有了一些 Java 的虚拟机检测 API，即 java.lang.management包。
		Management 包里面包括了许多 MXBean 的接口类和 LockInfo、MemoryUsage、MonitorInfo 和 ThreadInfo 等类。
		从名字可以看出，该包提供了虚拟机内存分配、垃圾收集（GC）情况、操作系统层、线程调度和共享锁，甚至编译情况的检测机制。
		这样一来，Java 的开发人员就可以很简单地为自己做一些轻量级的系统检测，来确定当前程序的各种状态，以便随时调整。
		
		要获得这些信息，我们首先通过 java.lang.management.ManagementFactory这个工厂类来获得一系列的 MXBean。包括：
		
		ClassLoadingMXBean
		ClassLoadMXBean 包括一些类的装载信息，比如有多少类已经装载 / 卸载（unloaded），虚拟机类装载的 verbose 选项（即命令行中的 Java – verbose:class 选项）是否打开，还可以帮助用户打开 / 关闭该选项。
		
		CompilationMXBean
		CompilationMXBean 帮助用户了解当前的编译器和编译情况，该 mxbean 提供的信息不多。
		
		GarbageCollectorMXBean
		相对于开放人员对 GC 的关注程度来说，该 mxbean 提供的信息十分有限，仅仅提供了 GC 的次数和 GC 花费总时间的近似值。但是这个包中还提供了三个的内存管理检测类：MemoryManagerMXBean，MemoryMXBean 和 MemoryPoolMXBean。
		
		MemoryManagerMXBean
		这个类相对简单，提供了内存管理类和内存池（memory pool）的名字信息。
		
		MemoryMXBean
		这个类提供了整个虚拟机中内存的使用情况，包括 Java 堆（heap）和非 Java 堆所占用的内存，提供当前等待 finalize 的对象数量，它甚至可以做 gc（实际上是调用 System.gc）。
		
		MemoryPoolMXBean
		该信息提供了大量的信息。在 JVM 中，可能有几个内存池，因此有对应的内存池信息，因此，在工厂类中，getMemoryPoolMXBean() 得到是一个 MemoryPoolMXBean 的 list。每一个 MemoryPoolMXBean 都包含了该内存池的详细信息，如是否可用、当前已使用内存 / 最大使用内存值、以及设置最大内存值等等。
		
		OperatingSystemMXBean
		该类提供的是操作系统的简单信息，如构架名称、当前 CPU 数、最近系统负载等。
		
		RuntimeMXBean
		运行时信息包括当前虚拟机的名称、提供商、版本号，以及 classpath、bootclasspath 和系统参数等等。
		
		ThreadMXBean
		在 Java 这个多线程的系统中，对线程的监控是相当重要的。ThreadMXBean 就是起到这个作用。ThreadMXBean 可以提供的信息包括各个线程的各种状态，CPU 占用情况，以及整个系统中的线程状况。
		从 ThreadMXBean 可以得到某一个线程的 ThreadInfo 对象。这个对象中则包含了这个线程的所有信息。
		java.lang.management 和虚拟机的关系
		我们知道，management 和底层虚拟机的关系是非常紧密的。其实，有一些的是直接依靠虚拟机提供的公开 API 实现的，比如 JVMTI；
		而另外一些则不然，很大一块都是由虚拟机底层提供某些不公开的 API / Native Code 提供的。
		这样的设计方式，保证了 management 包可以提供足够的信息，并且使这些信息的提供又有足够的效率；也使 management 包和底层的联系非常紧密。
		
		========================================================================================================================================================================================================
		
		Java 6 中的 API 改进
		Management 在 Java SE 5 被提出之后，受到了欢迎。在 Java 6 当中，这个包提供更多的 API 来更好地提供信息。
		OperatingSystemMXBean. getSystemLoadAverage()
		Java 程序通常关注是虚拟机内部的负载、内存等状况，而不考虑整个系统的状况。
		但是很多情况下，Java 程序在运行过程中，整个计算机系统的系统负荷情况也会对虚拟机造成一定的影响。
		随着 Java 的发展，Java 程序已经覆盖了各个行业，这一点也必须得到关注。
		在以前，利用 Native 代码来检测系统负载往往是唯一的选择，但是在 Java 6 当中，JDK 自己提供了一个轻量级的系统负载检测 API，即 OperatingSystemMXBean.getSystemLoadAverage()。
		当然这个 API 事实上仅仅返回一个对前一分钟系统负载的简单的估测。它设计的主要目标是简单快速地估测当前系统负荷，因此它首先保证了这个 API 的效率是非常高的；也因为如此，这个 API 事实上并不适用于所有的系统。
		
		锁检测
		
		我们知道，同步是 Java 语言很重要的一个特性。在 Java SE 中，最主要的同步机制是依靠 synchronize 关键字对某一个对象加锁实现的；在 Java SE 5 之后的版本中，concurrent 包的加入，大大强化了 Java 语言的同步能力，concurrent 提供了很多不同类型的锁机制可供扩展。因此，要更好地观测当前的虚拟机状况和不同线程的运行态，去观察虚拟机中的各种锁，以及线程与锁的关系是非常必要的。很可惜的是，在过去的 JDK 中，我们并没有非常方便的 API 以供使用。一个比较直接的检测方式是查看线程的 stack trace，更为强大全面（但是也更复杂并且效率低下）的方案是得到一个 VM 所有对象的快照并查找之，这些策略的代价都比较大，而且往往需要编写复杂的 Native 代码。
		JDK 6 里提供了一些相当简单的 API 来提供这个服务。首先了解两个新类，LockInfo 和 MonitorInfo 这两个类承载了锁的信息。LockInfo 可以是任何的 Java 锁，包括简单 Java 锁和 java.util.concurrent包中所使用的锁（包括 AbstractOwnableSynchronizer 和 Condition 的实现类 / 子类），而 MonitorInfo 是简单的 Java 对象所代表的锁。要检测一个线程所拥有的锁和等待的锁，首先，要得到一个线程的 ThreadInfo，然后可以简单地调用：
		getLockedMonitors()
		返回一个所有当前线程已经掌握的锁对象的列表。
		getLockedSynchronizers()
		对于使用 concurrent 包的线程，返回一个该线程所掌握的“ownable synchronizer”（即 AbstractOwnableSynchronizer 及其子类）所组成的列表。
		getLockInfo()
		当前线程正在等待的那个锁对象的信息就可以知道线程所有的锁信息。通过这些锁信息，我们很方便的可以知道当前虚拟机的所有线程的锁信息。由此，我们还可以推导出更多的信息。
		
		死锁检测
		
		死锁检测一直以来是软件工程师所重视的，显然一个死锁的系统永远是工程师最大的梦魇。Java 程序的死锁检测也一直以来是 Java 程序员所头痛的。为了解决线程间死锁问题，一般都有预防（代码实现阶段）和死锁后恢复（运行时）两种方式。以前 Java 程序员都重视前者，因为在运行态再来检测和恢复系统是相当麻烦的，缺少许多必要的信息；但是，对于一些比较复杂的系统，采取后者或者运行时调试死锁信息也是非常重要的。由上面所说，现在我们已经可以知道每一个线程所拥有和等待的锁，因此要计算出当前系统中是否有死锁的线程也是可行的了。当然，Java 6 里面也提供了一个 API 来完成这个功能，即：
		ThreadMXBean.findDeadlockedThreads()
		这个函数的功能就是检测出当前系统中已经死锁的线程。当然，这个功能复杂，因此比较费时。基本上仅仅将之用于调试，以便对复杂系统线程调用的改进。
	 
	 * @param args
	 */
	public static void main(String[] args) {
//		int loadedClassCount = new ClassLoadingMXBean().getLoadedClassCount();
		
		System.out.println("当前可用线程："+ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors());
		
//		DateUtil.getCurrentTime();//可以通过注释开关这段代码，来检测装载类的数量的变化
		System.out.println("当前装载的类的数量："+ManagementFactory.getClassLoadingMXBean().getLoadedClassCount());
		
		Thread tt = new Thread(){
			@Override
			public void run() {
				super.run();
				TU.s(1000);
			}
			
		};
		System.out.println(tt);
//		tt.start();//可以通过开关该注释，观察数量变化
		System.out.println("当前jvm运行线程总数："+ManagementFactory.getThreadMXBean().getThreadCount());
		
	}
	

}
