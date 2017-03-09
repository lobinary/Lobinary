/***** Lobxxx Translate Finished ******/
/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * A synchronization aid that allows one or more threads to wait until
 * a set of operations being performed in other threads completes.
 *
 * <p>A {@code CountDownLatch} is initialized with a given <em>count</em>.
 * The {@link #await await} methods block until the current count reaches
 * zero due to invocations of the {@link #countDown} method, after which
 * all waiting threads are released and any subsequent invocations of
 * {@link #await await} return immediately.  This is a one-shot phenomenon
 * -- the count cannot be reset.  If you need a version that resets the
 * count, consider using a {@link CyclicBarrier}.
 *
 * <p>A {@code CountDownLatch} is a versatile synchronization tool
 * and can be used for a number of purposes.  A
 * {@code CountDownLatch} initialized with a count of one serves as a
 * simple on/off latch, or gate: all threads invoking {@link #await await}
 * wait at the gate until it is opened by a thread invoking {@link
 * #countDown}.  A {@code CountDownLatch} initialized to <em>N</em>
 * can be used to make one thread wait until <em>N</em> threads have
 * completed some action, or some action has been completed N times.
 *
 * <p>A useful property of a {@code CountDownLatch} is that it
 * doesn't require that threads calling {@code countDown} wait for
 * the count to reach zero before proceeding, it simply prevents any
 * thread from proceeding past an {@link #await await} until all
 * threads could pass.
 *
 * <p><b>Sample usage:</b> Here is a pair of classes in which a group
 * of worker threads use two countdown latches:
 * <ul>
 * <li>The first is a start signal that prevents any worker from proceeding
 * until the driver is ready for them to proceed;
 * <li>The second is a completion signal that allows the driver to wait
 * until all workers have completed.
 * </ul>
 *
 *  <pre> {@code
 * class Driver { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch startSignal = new CountDownLatch(1);
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       new Thread(new Worker(startSignal, doneSignal)).start();
 *
 *     doSomethingElse();            // don't let run yet
 *     startSignal.countDown();      // let all threads proceed
 *     doSomethingElse();
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class Worker implements Runnable {
 *   private final CountDownLatch startSignal;
 *   private final CountDownLatch doneSignal;
 *   Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
 *     this.startSignal = startSignal;
 *     this.doneSignal = doneSignal;
 *   }
 *   public void run() {
 *     try {
 *       startSignal.await();
 *       doWork();
 *       doneSignal.countDown();
 *     } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }}</pre>
 *
 * <p>Another typical usage would be to divide a problem into N parts,
 * describe each part with a Runnable that executes that portion and
 * counts down on the latch, and queue all the Runnables to an
 * Executor.  When all sub-parts are complete, the coordinating thread
 * will be able to pass through await. (When threads must repeatedly
 * count down in this way, instead use a {@link CyclicBarrier}.)
 *
 *  <pre> {@code
 * class Driver2 { // ...
 *   void main() throws InterruptedException {
 *     CountDownLatch doneSignal = new CountDownLatch(N);
 *     Executor e = ...
 *
 *     for (int i = 0; i < N; ++i) // create and start threads
 *       e.execute(new WorkerRunnable(doneSignal, i));
 *
 *     doneSignal.await();           // wait for all to finish
 *   }
 * }
 *
 * class WorkerRunnable implements Runnable {
 *   private final CountDownLatch doneSignal;
 *   private final int i;
 *   WorkerRunnable(CountDownLatch doneSignal, int i) {
 *     this.doneSignal = doneSignal;
 *     this.i = i;
 *   }
 *   public void run() {
 *     try {
 *       doWork(i);
 *       doneSignal.countDown();
 *     } catch (InterruptedException ex) {} // return;
 *   }
 *
 *   void doWork() { ... }
 * }}</pre>
 *
 * <p>Memory consistency effects: Until the count reaches
 * zero, actions in a thread prior to calling
 * {@code countDown()}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions following a successful return from a corresponding
 * {@code await()} in another thread.
 *
 * <p>
 *  允许一个或多个线程等待直到在其他线程中执行的一组操作完成的同步辅助。
 * 
 *  <p>使用给定的<em>计数</em>初始化{@code CountDownLatch}。
 *  {@link #await await}方法阻塞,直到当前计数由于调用{@link #countDown}方法而达到零,之后所有等待的线程都被释放,并且任何后续的{@link #await await}
 * 调用立即返回。
 *  <p>使用给定的<em>计数</em>初始化{@code CountDownLatch}。这是一次性的现象 - 计数不能重置。
 * 如果您需要重置计数的版本,请考虑使用{@link CyclicBarrier}。
 * 
 *  <p> {@code CountDownLatch}是一种多功能同步工具,可用于多种用途。
 * 以计数为1初始化的{@code CountDownLatch}用作简单的开/关锁存器或门：所有调用{@link #await await}的线程在门处等待,直到它被调用{@link#倒数}。
 * 初始化为<em> N </em>的{@code CountDownLatch}可用于使一个线程等待,直到<em> N </em>个线程已完成某些操作,或某些操作已完成N次。
 * 
 * <p> {@code CountDownLatch}的一个有用的属性是它不需要调用{@code countDown}的线程等待计数达到零,然后继续,它只是阻止任何线程通过{@link #await await}
 * ,直到所有的线程都能通过。
 * 
 *  <p> <b>示例用法：</b>这是一对类,其中一组工作线程使用两个倒计时锁存：
 * <ul>
 *  <li>第一个是启动信号,防止任何工作人员继续进行,直到驱动程序准备好继续进行; <li>第二个是完成信号,允许驾驶员等待,直到所有工人完成。
 * </ul>
 * 
 *  <pre> {@code class Driver {// ... void main()throws InterruptedException {CountDownLatch startSignal = new CountDownLatch(1); CountDownLatch doneSignal = new CountDownLatch(N);。
 * 
 *  for(int i = 0; i <N; ++ i)//创建和启动线程new Thread(new Worker(startSignal,doneSignal))。
 * 
 *  doSomethingElse(); //不让运行startSignal.countDown(); //让所有线程继续doSomethingElse(); doneSignal.await(); //
 * 等待所有完成}}。
 * 
 *  类Worker实现Runnable {private final CountDownLatch startSignal; private final CountDownLatch doneSignal; Worker(CountDownLatch startSignal,CountDownLatch doneSignal){this.startSignal = startSignal; this.doneSignal = doneSignal; }
 *  public void run(){try {startSignal.await();做工作(); doneSignal.countDown(); } catch(InterruptedExcepti
 * on ex){} // return; }}。
 * 
 * void doWork(){...}}} </pre>
 * 
 *  <p>另一个典型的用法是将问题分成N个部分,用Runnable描述每个部分,执行该部分并对锁存器计数,并将所有Runnables排队到执行器。当所有子部分完成时,协调线程将能够通过等待。
 *  (当线程必须以这种方式重复倒计时,而不是使用{@link CyclicBarrier}。)。
 * 
 *  <pre> {@code class Driver2 {// ... void main()throws InterruptedException {CountDownLatch doneSignal = new CountDownLatch(N);执行器e = ...。
 * 
 *  for(int i = 0; i <N; ++ i)//创建和启动线程e.execute(new WorkerRunnable(doneSignal,i));
 * 
 *  doneSignal.await(); //等待所有完成}}
 * 
 *  类WorkerRunnable实现Runnable {private final CountDownLatch doneSignal; private final int i; WorkerRunnable(CountDownLatch doneSignal,int i){this.doneSignal = doneSignal; this.i = i; }
 *  public void run(){try {doWork(i); doneSignal.countDown(); } catch(InterruptedException ex){} // retu
 * rn; }}。
 * 
 *  void doWork(){...}}} </pre>
 * 
 *  <p>内存一致性影响：在计数达到零之前,在调用{@code countDown()} <a href="package-summary.html#MemoryVisibility"> <i>之前发生的
 * 线程中的操作</i > </a>在另一个线程中从相应的{@code await()}成功返回后的操作。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public class CountDownLatch {
    /**
     * Synchronization control For CountDownLatch.
     * Uses AQS state to represent count.
     * <p>
     *  同步控件CountDownLatch。使用AQS状态来表示计数。
     * 
     */
    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        protected int tryAcquireShared(int acquires) {
            return (getState() == 0) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int releases) {
            // Decrement count; signal when transition to zero
            for (;;) {
                int c = getState();
                if (c == 0)
                    return false;
                int nextc = c-1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }

    private final Sync sync;

    /**
     * Constructs a {@code CountDownLatch} initialized with the given count.
     *
     * <p>
     * 构造一个用给定计数初始化的{@code CountDownLatch}。
     * 
     * 
     * @param count the number of times {@link #countDown} must be invoked
     *        before threads can pass through {@link #await}
     * @throws IllegalArgumentException if {@code count} is negative
     */
    public CountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");
        this.sync = new Sync(count);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted}.
     *
     * <p>If the current count is zero then this method returns immediately.
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of two things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>
     *  导致当前线程等待,直到锁存器计数到零,除非线程{@linkplain线程#中断}。
     * 
     *  <p>如果当前计数为零,则此方法立即返回。
     * 
     *  <p>如果当前计数大于零,则当前线程变为禁用以用于线程调度目的,并处于休眠状态,直到发生以下两种情况之一：
     * <ul>
     *  <li>由于调用了{@link #countDown}方法,因此计数值为零;或<li>一些其他线程{@linkplain线程#中断中断}当前线程。
     * </ul>
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断}在等待时,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * 
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * Causes the current thread to wait until the latch has counted down to
     * zero, unless the thread is {@linkplain Thread#interrupt interrupted},
     * or the specified waiting time elapses.
     *
     * <p>If the current count is zero then this method returns immediately
     * with the value {@code true}.
     *
     * <p>If the current count is greater than zero then the current
     * thread becomes disabled for thread scheduling purposes and lies
     * dormant until one of three things happen:
     * <ul>
     * <li>The count reaches zero due to invocations of the
     * {@link #countDown} method; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>The specified waiting time elapses.
     * </ul>
     *
     * <p>If the count reaches zero then the method returns with the
     * value {@code true}.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.  If the time is less than or equal to zero, the method
     * will not wait at all.
     *
     * <p>
     *  导致当前线程等待,直到锁存器计数到零,除非线程{@linkplain线程#中断},或指定的等待时间过去。
     * 
     *  <p>如果当前计数为零,则此方法立即返回值{@code true}。
     * 
     *  <p>如果当前计数大于零,则当前线程因线程调度而被禁用,并处于休眠状态,直到发生以下三种情况之一：
     * <ul>
     * <li>由于调用了{@link #countDown}方法,因此计数值为零;或<li>一些其他线程{@linkplain线程#中断中断}当前线程;或<li>指定的等待时间已过。
     * </ul>
     * 
     *  <p>如果计数到达零,那么方法返回值为{@code true}。
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断}在等待时,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * 
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return {@code true} if the count reached zero and {@code false}
     *         if the waiting time elapsed before the count reached zero
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    public boolean await(long timeout, TimeUnit unit)
        throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
    }

    /**
     * Decrements the count of the latch, releasing all waiting threads if
     * the count reaches zero.
     *
     * <p>If the current count is greater than zero then it is decremented.
     * If the new count is zero then all waiting threads are re-enabled for
     * thread scheduling purposes.
     *
     * <p>If the current count equals zero then nothing happens.
     * <p>
     *  <p>如果经过指定的等待时间,则返回值{@code false}。如果时间小于或等于零,则该方法将不会等待。
     * 
     */
    public void countDown() {
        sync.releaseShared(1);
    }

    /**
     * Returns the current count.
     *
     * <p>This method is typically used for debugging and testing purposes.
     *
     * <p>
     *  减少锁存器的计数,如果计数达到零,则释放所有等待的线程。
     * 
     *  <p>如果当前计数大于零,则递减。如果新计数为零,则为线程调度目的重新启用所有等待的线程。
     * 
     *  <p>如果当前计数等于零,那么什么也不发生。
     * 
     * 
     * @return the current count
     */
    public long getCount() {
        return sync.getCount();
    }

    /**
     * Returns a string identifying this latch, as well as its state.
     * The state, in brackets, includes the String {@code "Count ="}
     * followed by the current count.
     *
     * <p>
     *  返回当前计数。
     * 
     *  <p>此方法通常用于调试和测试目的。
     * 
     * 
     * @return a string identifying this latch, as well as its state
     */
    public String toString() {
        return super.toString() + "[Count = " + sync.getCount() + "]";
    }
}
