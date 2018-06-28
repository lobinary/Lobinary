package com.lobinary.java.多线程.线程间协作;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 1. ArrayBlockingQueue
     	基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护了一个定长数组，以便缓存队列中的数据对象，
     	这是一个常用的阻塞队列，除了一个定长数组外，ArrayBlockingQueue内部还保存着两个整形变量，分别标识着队列的头部和尾部在数组中的位置。
　　		ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象，由此也意味着两者无法真正并行运行，
		这点尤其不同于LinkedBlockingQueue；按照实现原理来分析，ArrayBlockingQueue完全可以采用分离锁，
		从而实现生产者和消费者操作的完全并行运行。Doug Lea之所以没这样去做，也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，
		以至于引入独立的锁机制，除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。 
		ArrayBlockingQueue和LinkedBlockingQueue间还有一个明显的不同之处在于，前者在插入或删除元素时不会产生或销毁任何额外的对象实例，
		而后者则会生成一个额外的Node对象。这在长时间内需要高效并发地处理大批量数据的系统中，其对于GC的影响还是存在一定的区别。
		而在创建ArrayBlockingQueue时，我们还可以控制对象的内部锁是否采用公平锁，默认采用非公平锁。

	2. LinkedBlockingQueue
		基于链表的阻塞队列，同ArrayListBlockingQueue类似，其内部也维持着一个数据缓冲队列（该队列由一个链表构成），当生产者往队列中放入一个数据时，
		队列会从生产者手中获取数据，并缓存在队列内部，而生产者立即返回；只有当队列缓冲区达到最大值缓存容量时（LinkedBlockingQueue可以通过构造函数指定该值），
		才会阻塞生产者队列，直到消费者从队列中消费掉一份数据，生产者线程会被唤醒，反之对于消费者这端的处理也基于同样的原理。
		而LinkedBlockingQueue之所以能够高效的处理并发数据，还因为其对于生产者端和消费者端分别采用了独立的锁来控制数据同步，
		这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。
		
		【注意：】作为开发者，我们需要注意的是，如果构造一个LinkedBlockingQueue对象，而没有指定其容量大小，
		LinkedBlockingQueue会默认一个类似无限大小的容量（Integer.MAX_VALUE），这样的话，如果生产者的速度一旦大于消费者的速度，
		也许还没有等到队列满阻塞产生，系统内存就有可能已被消耗殆尽了。
	
		ArrayBlockingQueue和LinkedBlockingQueue是两个最普通也是最常用的阻塞队列，一般情况下，在处理多线程间的生产者消费者问题，使用这两个类足以。
 */
public class 生产者消费者BlockingQueue {
	
	/**
	 * 
	 * 
	  	放入数据：
		　　offer(anObject):表示如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,
		　　　　则返回true,否则返回false.（本方法不阻塞当前执行方法的线程）
		　　offer(E o, long timeout, TimeUnit unit),可以设定等待的时间，如果在指定的时间内，还不能往队列中
		　　　　加入BlockingQueue，则返回失败。
		　　put(anObject):把anObject加到BlockingQueue里,如果BlockQueue没有空间,则调用此方法的线程被阻断
		　　　　直到BlockingQueue里面有空间再继续.
		
		获取数据：
		   poll():如果有就返回，没有返回null 	【注意：因为返回null，所以在使用时应该注意，在消费者模式中多使用take，因为它可以阻塞住】源码注释：Retrieves and removes the head of this queue, or returns null if this queue is empty.
		　　poll(time):取走BlockingQueue里排在首位的对象,若不能立即取出,则可以等time参数规定的时间,
		　　　　取不到时返回null;
		　　poll(long timeout, TimeUnit unit)：从BlockingQueue取出一个队首的对象，如果在指定时间内，
		　　　　队列一旦有数据可取，则立即返回队列中的数据。否则知道时间超时还没有数据可取，返回失败。
		　　take():取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到
		　　　　BlockingQueue有新的数据被加入; 
		　　drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数）， 
		　　　　通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
		
	 */
	public static void main(String[] args) {
		BlockingQueue<Long> 阻塞队列 = new ArrayBlockingQueue<Long>(10);
		for (int i = 0; i <5; i++) {
			new 生产者(阻塞队列).start();
			new 消费者(阻塞队列).start();
		}
	}
	
	

}

class 生产者 extends Thread{
	
	BlockingQueue<Long> 阻塞队列 = null;
	

	public 生产者(BlockingQueue<Long> 阻塞队列) {
		super();
		this.阻塞队列 = 阻塞队列;
	}

	@Override
	public void run() {
		super.run();
		while(true){
			try {
				Thread.sleep(2000);
				Long e = (long) (Math.random()*10);
				阻塞队列.put(e);
				System.out.println(Thread.currentThread().getName()+"生产者插入数据:"+e);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class 消费者 extends Thread{
	
	BlockingQueue<Long> 阻塞队列 = null;
	

	public 消费者(BlockingQueue<Long> 阻塞队列) {
		super();
		this.阻塞队列 = 阻塞队列;
	}

	@Override
	public void run() {
		super.run();
		while(true){
			try {
				Long poll = 阻塞队列.take();//此处应该用take不能用没有参数的poll否则容易返回null
				System.out.println(Thread.currentThread().getName()+"消费者取出值："+poll);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
