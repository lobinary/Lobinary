package com.lobinary.java.多线程.threadlocal;

/**
 * ThreadLocal变量用的不多，多半都用在管理session这些需要每个线程单独拥有独立对象的情况下，或者类似spring管理service一样，拥有自己独立的对象
 * 否则轻易自己写代码很少会用这个
 * 
 * threadlocal的用途可以归结为以下：
 * 		当很多线程共用一个对象的时候，那么这个对象就会被所有线程一起拥有，因此这个对象的成员变量也会被所有线程共享，一个线程更改了这个成员变量，所有线程的成员变量均一起更改
 * 		如果这个时候需要这个变量对每个线程所独立拥有(每个线程自己创建自己的成员变量、互相不影响[如果初始化是1，那么所有线程都对这个threadlocal+1的话，那么所有线程的这个threadlocal变量是2，而不是线程加起来的总和])
 *		就可以使用threadlocal技术了
 *		但是如果想要让所有线程共享这个变量，那可能就不适合这个技术了，还是需要sychronized来实现
 *		threadlocal容易出现内存溢出，当线程池的时候，因为有时候一些线程池里的线程不被释放，所以他所持有的threadlocal就会一直叠加，直到内存溢出，使用时还需注意
 *
 *ThreadLocal是Java里一种特殊的变量。每个线程都有一个ThreadLocal就是每个线程都拥有了自己独立的一个变量，竞争条件被彻底消除了。如果为每个线程提供一个自己独有的变量拷贝，将大大提高效率。
 *首先，通过复用减少了代价高昂的对象的创建个数。其次，你在没有使用高代价的同步或者不变性的情况下获得了线程安全。
 */
public class TreadLocal用法 {

	public static void main(String[] args) {
		final Test t = new Test();
		
		for (int j = 0; j < 5; j++) {
			new Thread() {

				@Override
				public void run() {
					super.run();
					t.outFiled();
					t.outThreadLocalData();
				}

			}.start();
		}
		
	}

}

class Test{
	
	Obj obj;
	ThreadLocal<Obj> 线程变量 = new ThreadLocal<Obj>();

	public void outFiled() {//普通变量必须使用同步锁，否则会出现初始化多次变量的情况
		synchronized (Test.class) {
			if(obj==null){
				System.out.println(Thread.currentThread().getName()+"准备初始化普通变量");
				obj = new Obj(10);
			}
		}
		
		obj.i++;//发现增加变量的时候出现了线程错误 更改的值变化了
		System.out.println("普通变量"+obj.i);
		
	}
	
	public void outThreadLocalData(){
		synchronized (Test.class) {//虽然我们和普通变量一样锁定了初始化，但是依然会初始化5次，代表每个线程拥有自己的threadlocal变量，即使其他线程初始化了自己的，别人也需要再初始化自己的threadlocal变量
			if(线程变量.get()==null){
				线程变量.set(new Obj(20));
				System.out.println(Thread.currentThread().getName()+"准备初始化ThreadLocal变量");
			}
		}
		
		Obj obj2 = 线程变量.get();
		obj2.i++;
		System.out.println("ThreadLocal变量"+obj2.i);
	}
	
	class Obj{
		int i;

		public Obj(int i) {
			super();
			this.i = i;
		}
	}

}


