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

package java.util.concurrent.locks;
import sun.misc.Unsafe;

/**
 * Basic thread blocking primitives for creating locks and other
 * synchronization classes.
 *
 * <p>This class associates, with each thread that uses it, a permit
 * (in the sense of the {@link java.util.concurrent.Semaphore
 * Semaphore} class). A call to {@code park} will return immediately
 * if the permit is available, consuming it in the process; otherwise
 * it <em>may</em> block.  A call to {@code unpark} makes the permit
 * available, if it was not already available. (Unlike with Semaphores
 * though, permits do not accumulate. There is at most one.)
 *
 * <p>Methods {@code park} and {@code unpark} provide efficient
 * means of blocking and unblocking threads that do not encounter the
 * problems that cause the deprecated methods {@code Thread.suspend}
 * and {@code Thread.resume} to be unusable for such purposes: Races
 * between one thread invoking {@code park} and another thread trying
 * to {@code unpark} it will preserve liveness, due to the
 * permit. Additionally, {@code park} will return if the caller's
 * thread was interrupted, and timeout versions are supported. The
 * {@code park} method may also return at any other time, for "no
 * reason", so in general must be invoked within a loop that rechecks
 * conditions upon return. In this sense {@code park} serves as an
 * optimization of a "busy wait" that does not waste as much time
 * spinning, but must be paired with an {@code unpark} to be
 * effective.
 *
 * <p>The three forms of {@code park} each also support a
 * {@code blocker} object parameter. This object is recorded while
 * the thread is blocked to permit monitoring and diagnostic tools to
 * identify the reasons that threads are blocked. (Such tools may
 * access blockers using method {@link #getBlocker(Thread)}.)
 * The use of these forms rather than the original forms without this
 * parameter is strongly encouraged. The normal argument to supply as
 * a {@code blocker} within a lock implementation is {@code this}.
 *
 * <p>These methods are designed to be used as tools for creating
 * higher-level synchronization utilities, and are not in themselves
 * useful for most concurrency control applications.  The {@code park}
 * method is designed for use only in constructions of the form:
 *
 *  <pre> {@code
 * while (!canProceed()) { ... LockSupport.park(this); }}</pre>
 *
 * where neither {@code canProceed} nor any other actions prior to the
 * call to {@code park} entail locking or blocking.  Because only one
 * permit is associated with each thread, any intermediary uses of
 * {@code park} could interfere with its intended effects.
 *
 * <p><b>Sample Usage.</b> Here is a sketch of a first-in-first-out
 * non-reentrant lock class:
 *  <pre> {@code
 * class FIFOMutex {
 *   private final AtomicBoolean locked = new AtomicBoolean(false);
 *   private final Queue<Thread> waiters
 *     = new ConcurrentLinkedQueue<Thread>();
 *
 *   public void lock() {
 *     boolean wasInterrupted = false;
 *     Thread current = Thread.currentThread();
 *     waiters.add(current);
 *
 *     // Block while not first in queue or cannot acquire lock
 *     while (waiters.peek() != current ||
 *            !locked.compareAndSet(false, true)) {
 *       LockSupport.park(this);
 *       if (Thread.interrupted()) // ignore interrupts while waiting
 *         wasInterrupted = true;
 *     }
 *
 *     waiters.remove();
 *     if (wasInterrupted)          // reassert interrupt status on exit
 *       current.interrupt();
 *   }
 *
 *   public void unlock() {
 *     locked.set(false);
 *     LockSupport.unpark(waiters.peek());
 *   }
 * }}</pre>
 * <p>
 *  用于创建锁和其他同步类的基本线程阻塞原语。
 * 
 *  <p>此类与使用它的每个线程相关联,一个permit(在{@link java.util.concurrent.Semaphore Semaphore}类的意义上)。
 * 如果许可证可用,则对{@code park}的调用将立即返回,在此过程中消耗它;否则可能会</em>阻止。调用{@code unpark}会使许可证可用,如果它不可用。
 *  (与Semaphores不同,许可不累积,最多有一个。)。
 * 
 * <p>方法{@code park}和{@code unpark}提供了有效的方法来阻止和取消阻止不会导致已弃用的方法{@code Thread.suspend}和{@code Thread.resume}
 * 的问题的线程不可用于这样的目的：一个线程调用{@code park}和另一个线程试图{@code unpark}之间的竞争将保持活力,由于许可。
 * 此外,如果调用者的线程中断,并且支持超时版本,则{@code park}将返回。 {@code park}方法也可以在任何其他时间返回,因为"无理由",因此一般必须在返回时重新检查条件的循环中调用。
 * 从这个意义上说,{@code park}是一个"忙等待"的优化,不会浪费太多时间,但必须配合{@code unpark}才能有效。
 * 
 *  <p> {@code park}的三种形式也支持{@code blocker}对象参数。在线程被阻塞时记录此对象,以允许监视和诊断工具识别线程被阻止的原因。
 *  (这样的工具可以使用方法{@link #getBlocker(Thread)}访问阻止程序。)强烈鼓励使用这些形式而不是没有此参数的原始表单。
 * 在锁实现中作为{@code blocker}提供的正常参数是{@code this}。
 * 
 * <p>这些方法被设计为用作创建更高级别同步实用程序的工具,本身对大多数并发控制应用程序不是有用的。 {@code park}方法仅适用于以下形式的构建：
 * 
 *  <pre> {@code while(！canProceed()){... LockSupport.park(this); }} </pre>
 * 
 *  其中{@code canProceed}或任何其他操作在调用{@code park}之前必须锁定或阻止。
 * 因为只有一个许可证与每个线程相关联,所以{@code park}的任何中间使用都可能干扰其预期效果。
 * 
 *  <p> <b>示例用法</b>这是先进先出的非重入锁类的草图：<pre> {@code class FIFOMutex {private final AtomicBoolean locked = new AtomicBoolean ); private final Queue <Thread> waiters = new ConcurrentLinkedQueue <Thread>();。
 * 
 *  public void lock(){boolean wasInterrupted = false;线程电流= Thread.currentThread(); waiters.add(current);。
 * 
 *  // block while not first in queue or can not acquire lock while(waiters.peek()！= current ||！locked.c
 * ompareAndSet(false,true)){LockSupport.park(this); if(Thread.interrupted())//等待时忽略中断wasInterrupted = true; }
 * }。
 * 
 *  waiters.remove(); if(wasInterrupted)//在退出时重新设置中断状态current.interrupt(); }}
 * 
 *  public void unlock(){locked.set(false); LockSupport.unpark(waiters.peek()); }}} </pre>
 * 
 */
public class LockSupport {
    private LockSupport() {} // Cannot be instantiated.

    private static void setBlocker(Thread t, Object arg) {
        // Even though volatile, hotspot doesn't need a write barrier here.
        UNSAFE.putObject(t, parkBlockerOffset, arg);
    }

    /**
     * Makes available the permit for the given thread, if it
     * was not already available.  If the thread was blocked on
     * {@code park} then it will unblock.  Otherwise, its next call
     * to {@code park} is guaranteed not to block. This operation
     * is not guaranteed to have any effect at all if the given
     * thread has not been started.
     *
     * <p>
     * 使给定线程的许可证(如果它不可用)可用。如果该线程在{@code park}上被阻止,那么它将被取消阻止。否则,它对{@code park}的下一次调用将保证不会阻止。
     * 如果给定的线程尚未启动,这个操作不能保证有任何效果。
     * 
     * 
     * @param thread the thread to unpark, or {@code null}, in which case
     *        this operation has no effect
     */
    public static void unpark(Thread thread) {
        if (thread != null)
            UNSAFE.unpark(thread);
    }

    /**
     * Disables the current thread for thread scheduling purposes unless the
     * permit is available.
     *
     * <p>If the permit is available then it is consumed and the call returns
     * immediately; otherwise
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of three things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread upon return.
     *
     * <p>
     *  除非许可证可用,否则禁用当前线程以进行线程调度。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并处于休眠状态,直到发生以下三种情况之一：
     * 
     * <ul>
     *  <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以例如确定返回时线程的中断状态。
     * 
     * 
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @since 1.6
     */
    public static void park(Object blocker) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        UNSAFE.park(false, 0L);
        setBlocker(t, null);
    }

    /**
     * Disables the current thread for thread scheduling purposes, for up to
     * the specified waiting time, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified waiting time elapses; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the elapsed time
     * upon return.
     *
     * <p>
     *  为线程调度目的禁用当前线程,直到指定的等待时间,除非许可证可用。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并休眠,直到四件事之一发生：
     * 
     * <ul>
     * <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>指定的等待时间已过;要么
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以确定例如线程的中断状态或返回时的经过时间。
     * 
     * 
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @param nanos the maximum number of nanoseconds to wait
     * @since 1.6
     */
    public static void parkNanos(Object blocker, long nanos) {
        if (nanos > 0) {
            Thread t = Thread.currentThread();
            setBlocker(t, blocker);
            UNSAFE.park(false, nanos);
            setBlocker(t, null);
        }
    }

    /**
     * Disables the current thread for thread scheduling purposes, until
     * the specified deadline, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread; or
     *
     * <li>The specified deadline passes; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the current time
     * upon return.
     *
     * <p>
     *  为线程调度目的禁用当前线程,直到指定的截止日期,除非许可证可用。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并休眠,直到四件事之一发生：
     * 
     * <ul>
     *  <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>指定的截止日期通过;要么
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以确定例如线程的中断状态或返回时的当前时间。
     * 
     * 
     * @param blocker the synchronization object responsible for this
     *        thread parking
     * @param deadline the absolute time, in milliseconds from the Epoch,
     *        to wait until
     * @since 1.6
     */
    public static void parkUntil(Object blocker, long deadline) {
        Thread t = Thread.currentThread();
        setBlocker(t, blocker);
        UNSAFE.park(true, deadline);
        setBlocker(t, null);
    }

    /**
     * Returns the blocker object supplied to the most recent
     * invocation of a park method that has not yet unblocked, or null
     * if not blocked.  The value returned is just a momentary
     * snapshot -- the thread may have since unblocked or blocked on a
     * different blocker object.
     *
     * <p>
     * 返回提供给尚未解除阻塞的park方法的最近一次调用的阻塞程序对象,如果未阻塞,则返回null。返回的值只是一个短暂的快照 - 线程可能已经在不同的阻止程序对象上解除阻塞或阻塞。
     * 
     * 
     * @param t the thread
     * @return the blocker
     * @throws NullPointerException if argument is null
     * @since 1.6
     */
    public static Object getBlocker(Thread t) {
        if (t == null)
            throw new NullPointerException();
        return UNSAFE.getObjectVolatile(t, parkBlockerOffset);
    }

    /**
     * Disables the current thread for thread scheduling purposes unless the
     * permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of three
     * things happens:
     *
     * <ul>
     *
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread upon return.
     * <p>
     *  除非许可证可用,否则禁用当前线程以进行线程调度。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并处于休眠状态,直到发生以下三种情况之一：
     * 
     * <ul>
     * 
     *  <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以例如确定返回时线程的中断状态。
     * 
     */
    public static void park() {
        UNSAFE.park(false, 0L);
    }

    /**
     * Disables the current thread for thread scheduling purposes, for up to
     * the specified waiting time, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified waiting time elapses; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the elapsed time
     * upon return.
     *
     * <p>
     *  为线程调度目的禁用当前线程,直到指定的等待时间,除非许可证可用。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并休眠,直到四件事之一发生：
     * 
     * <ul>
     * <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>指定的等待时间已过;要么
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以确定例如线程的中断状态或返回时的经过时间。
     * 
     * 
     * @param nanos the maximum number of nanoseconds to wait
     */
    public static void parkNanos(long nanos) {
        if (nanos > 0)
            UNSAFE.park(false, nanos);
    }

    /**
     * Disables the current thread for thread scheduling purposes, until
     * the specified deadline, unless the permit is available.
     *
     * <p>If the permit is available then it is consumed and the call
     * returns immediately; otherwise the current thread becomes disabled
     * for thread scheduling purposes and lies dormant until one of four
     * things happens:
     *
     * <ul>
     * <li>Some other thread invokes {@link #unpark unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified deadline passes; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     *
     * <p>This method does <em>not</em> report which of these caused the
     * method to return. Callers should re-check the conditions which caused
     * the thread to park in the first place. Callers may also determine,
     * for example, the interrupt status of the thread, or the current time
     * upon return.
     *
     * <p>
     *  为线程调度目的禁用当前线程,直到指定的截止日期,除非许可证可用。
     * 
     *  <p>如果许可证可用,则它被使用,并且呼叫立即返回;否则当前线程变为禁用线程调度目的,并休眠,直到四件事之一发生：
     * 
     * <ul>
     *  <li>其他一些线程调用{@link #unpark unpark}以当前线程为目标;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>指定的截止日期通过;要么
     * 
     * @param deadline the absolute time, in milliseconds from the Epoch,
     *        to wait until
     */
    public static void parkUntil(long deadline) {
        UNSAFE.park(true, deadline);
    }

    /**
     * Returns the pseudo-randomly initialized or updated secondary seed.
     * Copied from ThreadLocalRandom due to package access restrictions.
     * <p>
     * 
     *  <li>无谓地(也就是说,没有理由)返回。
     * </ul>
     * 
     *  <p>此方法不会</em>报告其中哪些导致方法返回。调用者应该重新检查导致线程停在第一位置的条件。调用者还可以确定例如线程的中断状态或返回时的当前时间。
     */
    static final int nextSecondarySeed() {
        int r;
        Thread t = Thread.currentThread();
        if ((r = UNSAFE.getInt(t, SECONDARY)) != 0) {
            r ^= r << 13;   // xorshift
            r ^= r >>> 17;
            r ^= r << 5;
        }
        else if ((r = java.util.concurrent.ThreadLocalRandom.current().nextInt()) == 0)
            r = 1; // avoid zero
        UNSAFE.putInt(t, SECONDARY, r);
        return r;
    }

    // Hotspot implementation via intrinsics API
    private static final sun.misc.Unsafe UNSAFE;
    private static final long parkBlockerOffset;
    private static final long SEED;
    private static final long PROBE;
    private static final long SECONDARY;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            parkBlockerOffset = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("parkBlocker"));
            SEED = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSeed"));
            PROBE = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomProbe"));
            SECONDARY = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("threadLocalRandomSecondarySeed"));
        } catch (Exception ex) { throw new Error(ex); }
    }

}
