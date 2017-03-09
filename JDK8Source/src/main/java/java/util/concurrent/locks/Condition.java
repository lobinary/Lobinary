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
import java.util.concurrent.TimeUnit;
import java.util.Date;

/**
 * {@code Condition} factors out the {@code Object} monitor
 * methods ({@link Object#wait() wait}, {@link Object#notify notify}
 * and {@link Object#notifyAll notifyAll}) into distinct objects to
 * give the effect of having multiple wait-sets per object, by
 * combining them with the use of arbitrary {@link Lock} implementations.
 * Where a {@code Lock} replaces the use of {@code synchronized} methods
 * and statements, a {@code Condition} replaces the use of the Object
 * monitor methods.
 *
 * <p>Conditions (also known as <em>condition queues</em> or
 * <em>condition variables</em>) provide a means for one thread to
 * suspend execution (to &quot;wait&quot;) until notified by another
 * thread that some state condition may now be true.  Because access
 * to this shared state information occurs in different threads, it
 * must be protected, so a lock of some form is associated with the
 * condition. The key property that waiting for a condition provides
 * is that it <em>atomically</em> releases the associated lock and
 * suspends the current thread, just like {@code Object.wait}.
 *
 * <p>A {@code Condition} instance is intrinsically bound to a lock.
 * To obtain a {@code Condition} instance for a particular {@link Lock}
 * instance use its {@link Lock#newCondition newCondition()} method.
 *
 * <p>As an example, suppose we have a bounded buffer which supports
 * {@code put} and {@code take} methods.  If a
 * {@code take} is attempted on an empty buffer, then the thread will block
 * until an item becomes available; if a {@code put} is attempted on a
 * full buffer, then the thread will block until a space becomes available.
 * We would like to keep waiting {@code put} threads and {@code take}
 * threads in separate wait-sets so that we can use the optimization of
 * only notifying a single thread at a time when items or spaces become
 * available in the buffer. This can be achieved using two
 * {@link Condition} instances.
 * <pre>
 * class BoundedBuffer {
 *   <b>final Lock lock = new ReentrantLock();</b>
 *   final Condition notFull  = <b>lock.newCondition(); </b>
 *   final Condition notEmpty = <b>lock.newCondition(); </b>
 *
 *   final Object[] items = new Object[100];
 *   int putptr, takeptr, count;
 *
 *   public void put(Object x) throws InterruptedException {
 *     <b>lock.lock();
 *     try {</b>
 *       while (count == items.length)
 *         <b>notFull.await();</b>
 *       items[putptr] = x;
 *       if (++putptr == items.length) putptr = 0;
 *       ++count;
 *       <b>notEmpty.signal();</b>
 *     <b>} finally {
 *       lock.unlock();
 *     }</b>
 *   }
 *
 *   public Object take() throws InterruptedException {
 *     <b>lock.lock();
 *     try {</b>
 *       while (count == 0)
 *         <b>notEmpty.await();</b>
 *       Object x = items[takeptr];
 *       if (++takeptr == items.length) takeptr = 0;
 *       --count;
 *       <b>notFull.signal();</b>
 *       return x;
 *     <b>} finally {
 *       lock.unlock();
 *     }</b>
 *   }
 * }
 * </pre>
 *
 * (The {@link java.util.concurrent.ArrayBlockingQueue} class provides
 * this functionality, so there is no reason to implement this
 * sample usage class.)
 *
 * <p>A {@code Condition} implementation can provide behavior and semantics
 * that is
 * different from that of the {@code Object} monitor methods, such as
 * guaranteed ordering for notifications, or not requiring a lock to be held
 * when performing notifications.
 * If an implementation provides such specialized semantics then the
 * implementation must document those semantics.
 *
 * <p>Note that {@code Condition} instances are just normal objects and can
 * themselves be used as the target in a {@code synchronized} statement,
 * and can have their own monitor {@link Object#wait wait} and
 * {@link Object#notify notification} methods invoked.
 * Acquiring the monitor lock of a {@code Condition} instance, or using its
 * monitor methods, has no specified relationship with acquiring the
 * {@link Lock} associated with that {@code Condition} or the use of its
 * {@linkplain #await waiting} and {@linkplain #signal signalling} methods.
 * It is recommended that to avoid confusion you never use {@code Condition}
 * instances in this way, except perhaps within their own implementation.
 *
 * <p>Except where noted, passing a {@code null} value for any parameter
 * will result in a {@link NullPointerException} being thrown.
 *
 * <h3>Implementation Considerations</h3>
 *
 * <p>When waiting upon a {@code Condition}, a &quot;<em>spurious
 * wakeup</em>&quot; is permitted to occur, in
 * general, as a concession to the underlying platform semantics.
 * This has little practical impact on most application programs as a
 * {@code Condition} should always be waited upon in a loop, testing
 * the state predicate that is being waited for.  An implementation is
 * free to remove the possibility of spurious wakeups but it is
 * recommended that applications programmers always assume that they can
 * occur and so always wait in a loop.
 *
 * <p>The three forms of condition waiting
 * (interruptible, non-interruptible, and timed) may differ in their ease of
 * implementation on some platforms and in their performance characteristics.
 * In particular, it may be difficult to provide these features and maintain
 * specific semantics such as ordering guarantees.
 * Further, the ability to interrupt the actual suspension of the thread may
 * not always be feasible to implement on all platforms.
 *
 * <p>Consequently, an implementation is not required to define exactly the
 * same guarantees or semantics for all three forms of waiting, nor is it
 * required to support interruption of the actual suspension of the thread.
 *
 * <p>An implementation is required to
 * clearly document the semantics and guarantees provided by each of the
 * waiting methods, and when an implementation does support interruption of
 * thread suspension then it must obey the interruption semantics as defined
 * in this interface.
 *
 * <p>As interruption generally implies cancellation, and checks for
 * interruption are often infrequent, an implementation can favor responding
 * to an interrupt over normal method return. This is true even if it can be
 * shown that the interrupt occurred after another action that may have
 * unblocked the thread. An implementation should document this behavior.
 *
 * <p>
 *  {@code Condition}将{@code Object}监视方法({@link Object#wait()wait},{@link Object#notify notify}和{@link Object#notifyAll notifyAll}
 * 通过使用任意{@link Lock}实现来组合每个对象具有多个等待集的效果。
 * 当{@code Lock}取代使用{@code synchronized}方法和语句时,{@code Condition}会取代使用对象监视方法。
 * 
 *  <p>条件(也称为<em>条件队列</em>或<em>条件变量</em>)提供了一种线程暂停执行("等待"一些状态条件现在可以是真的。
 * 因为对这个共享状态信息的访问发生在不同的线程中,所以它必须被保护,因此某种形式的锁与条件相关联。
 * 等待条件提供的关键属性是它以原子方式释放相关的锁并挂起当前线程,就像{@code Object.wait}。
 * 
 *  <p> {@code Condition}实例本质上绑定到锁。
 * 要获取特定{@link Lock}实例的{@code Condition}实例,请使用其{@link Lock#newCondition newCondition()}方法。
 * 
 * <p>例如,假设我们有一个有界缓冲区,它支持{@code put}和{@code take}方法。
 * 如果对空缓冲区尝试{@code take},则线程将阻塞,直到一个项目变为可用;如果在一个完整的缓冲区上尝试{@code put},那么线程将阻塞,直到空间可用。
 * 我们希望在单独的等待集中继续等待{@code put}线程和{@code take}线程,这样我们就可以使用优化来在缓冲区中的项目或空间变为可用时只通知单个线程。
 * 这可以使用两个{@link Condition}实例来实现。
 * <pre>
 *  class BoundedBuffer {<b> final Lock lock = new ReentrantLock(); </b> final Condition notFull = <b> lock.newCondition(); </b> final condition notEmpty = <b> lock.newCondition(); </b>。
 * 
 *  final Object [] items = new Object [100]; int putptr,takeptr,count;
 * 
 *  public void put(Object x)throws InterruptedException {<b> lock.lock(); try {</b> while(count == items.length)<b> notFull.await(); </b> items [putptr] = x; if(++ putptr == items.length)putptr = 0; ++ count; <b> notEmpty.signal(); </b> <b>}
 *  finally {lock.unlock(); } </b>}。
 * 
 *  public Object take()throws InterruptedException {<b> lock.lock(); try {</b> while(count == 0)<b> notEmpty.await(); </b> Object x = items [takeptr]; if(++ takeptr == items.length)takeptr = 0; - 计数; <b> notFull.signal(); </b> return x; <b>}
 *  finally {lock.unlock(); } </b>}}。
 * </pre>
 * 
 * ({@link java.util.concurrent.ArrayBlockingQueue}类提供了此功能,因此没有理由实现此示例用法类。)
 * 
 *  <p> {@code Condition}实现可以提供与{@code Object}监视方法不同的行为和语义,例如保证通知的排序,或者不需要在执行通知时保持锁定。
 * 如果实现提供这样的专用语义,则实现必须记录那些语义。
 * 
 *  <p>请注意,{@code Condition}实例只是常规对象,可以在{@code synchronized}语句中用作目标,并且可以有自己的监视器{@link Object#wait wait}和
 * {@link Object#notify notification}方法。
 * 获取{@code Condition}实例的监视锁定或使用其监视方法与获取与该{@code Condition}关联的{@link Lock}或使用其{@linkplain #await等待}和{@linkplain #signal signaling}
 * 方法。
 * 为了避免混淆,建议您不要以这种方式使用{@code Condition}实例,除非在自己的实现中。
 * 
 *  <p>除非另有说明,否则传递任何参数的{@code null}值将导致抛出{@link NullPointerException}。
 * 
 *  <h3>实施注意事项</h3>
 * 
 * <p>当等待{@code Condition}时,"<em>伪唤醒</em>"允许发生,一般来说,作为对底层平台语义的让步。
 * 这对大多数应用程序没有实际影响,因为{@code Condition}应该总是在循环中等待,测试正在等待的状态谓词。
 * 实现可以自由地消除伪唤醒的可能性,但是建议应用程序员总是假定它们可以发生,因此总是在循环中等待。
 * 
 *  <p>条件等待的三种形式(可中断,不可中断和定时)可能在某些平台上的易于实现和它们的性能特性方面不同。特别地,可能难以提供这些特征并维持诸如排序保证的特定语义。
 * 此外,中断线程的实际挂起的能力可能并不总是可行地在所有平台上实现。
 * 
 *  因此,实现不需要为所有三种形式的等待定义完全相同的保证或语义,也不需要支持线程的实际挂起的中断。
 * 
 *  <p>需要一个实现来清楚地记录每个等待方法提供的语义和保证,并且当实现确实支持线程挂起中断时,它必须遵守在该接口中定义的中断语义。
 * 
 * <p>由于中断通常意味着取消,并且检查中断通常很少发生,因此实现可以有利于响应于正常方法返回的中断。这是真的,即使可以显示中断发生在另一个可能已解除线程的操作之后。实现应该记录这种行为。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public interface Condition {

    /**
     * Causes the current thread to wait until it is signalled or
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>The lock associated with this {@code Condition} is atomically
     * released and the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until <em>one</em> of four things happens:
     * <ul>
     * <li>Some other thread invokes the {@link #signal} method for this
     * {@code Condition} and the current thread happens to be chosen as the
     * thread to be awakened; or
     * <li>Some other thread invokes the {@link #signalAll} method for this
     * {@code Condition}; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of thread suspension is supported; or
     * <li>A &quot;<em>spurious wakeup</em>&quot; occurs.
     * </ul>
     *
     * <p>In all cases, before this method can return the current thread must
     * re-acquire the lock associated with this condition. When the
     * thread returns it is <em>guaranteed</em> to hold this lock.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * and interruption of thread suspension is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared. It is not specified, in the first
     * case, whether or not the test for interruption occurs before the lock
     * is released.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The current thread is assumed to hold the lock associated with this
     * {@code Condition} when this method is called.
     * It is up to the implementation to determine if this is
     * the case and if not, how to respond. Typically, an exception will be
     * thrown (such as {@link IllegalMonitorStateException}) and the
     * implementation must document that fact.
     *
     * <p>An implementation can favor responding to an interrupt over normal
     * method return in response to a signal. In that case the implementation
     * must ensure that the signal is redirected to another waiting thread, if
     * there is one.
     *
     * <p>
     *  使当前线程等待,直到它发出信号或{@linkplain线程#中断}。
     * 
     *  <p>与此{@code Condition}相关联的锁定以原子方式释放,并且当前线程因线程调度目的而被禁用,并处于休眠状态,直到发生以下四种情况：<em>
     * <ul>
     *  <li>一些其他线程调用此{@code Condition}的{@link #signal}方法,并且当前线程恰好被选择为要唤醒的线程;或<li>某些其他线程调用此{@code Condition}的
     * {@link #signalAll}方法;或<li>一些其他线程{@linkplain线程#中断中断}当前线程,并且支持中断线程挂起;或<li>"<em>伪唤醒</em>"发生。
     * </ul>
     * 
     *  <p>在所有情况下,在此方法可以返回之前,当前线程必须重新获取与此条件相关联的锁。当线程返回时,<em>确保</em>保持此锁定。
     * 
     *  <p>如果当前线程：
     * <ul>
     * <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断中断},同时等待和中断线程挂起被支持,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。在第一种情况下,没有指定是否在锁被释放之前进行中断测试。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p>当调用此方法时,假定当前线程持有与此{@code Condition}相关联的锁。它是由执行来确定是否是这种情况,如果不是,如何回应。
     * 通常,将抛出异常(例如{@link IllegalMonitorStateException}),实现必须记录该事实。
     * 
     *  <p>响应于信号,实现可以有利于响应于正常方法返回的中断。在这种情况下,实现必须确保信号被重定向到另一个等待线程(如果有的话)。
     * 
     * 
     * @throws InterruptedException if the current thread is interrupted
     *         (and interruption of thread suspension is supported)
     */
    void await() throws InterruptedException;

    /**
     * Causes the current thread to wait until it is signalled.
     *
     * <p>The lock associated with this condition is atomically
     * released and the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until <em>one</em> of three things happens:
     * <ul>
     * <li>Some other thread invokes the {@link #signal} method for this
     * {@code Condition} and the current thread happens to be chosen as the
     * thread to be awakened; or
     * <li>Some other thread invokes the {@link #signalAll} method for this
     * {@code Condition}; or
     * <li>A &quot;<em>spurious wakeup</em>&quot; occurs.
     * </ul>
     *
     * <p>In all cases, before this method can return the current thread must
     * re-acquire the lock associated with this condition. When the
     * thread returns it is <em>guaranteed</em> to hold this lock.
     *
     * <p>If the current thread's interrupted status is set when it enters
     * this method, or it is {@linkplain Thread#interrupt interrupted}
     * while waiting, it will continue to wait until signalled. When it finally
     * returns from this method its interrupted status will still
     * be set.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The current thread is assumed to hold the lock associated with this
     * {@code Condition} when this method is called.
     * It is up to the implementation to determine if this is
     * the case and if not, how to respond. Typically, an exception will be
     * thrown (such as {@link IllegalMonitorStateException}) and the
     * implementation must document that fact.
     * <p>
     *  使当前线程等待,直到它发出信号。
     * 
     *  <p>与此条件相关联的锁定是原子释放的,当前线程因线程调度目的而被禁用,并处于休眠状态,直到发生以下三种情况：<em>
     * <ul>
     * <li>一些其他线程调用此{@code Condition}的{@link #signal}方法,并且当前线程恰好被选择为要唤醒的线程;或<li>某些其他线程调用此{@code Condition}的{@link #signalAll}
     * 方法;或<li>"<em>伪唤醒</em>"发生。
     * </ul>
     * 
     *  <p>在所有情况下,在此方法可以返回之前,当前线程必须重新获取与此条件相关联的锁。当线程返回时,<em>确保</em>保持此锁定。
     * 
     *  <p>如果在进入此方法时设置了当前线程的中断状态,或者在等待期间{@linkplain线程#中断},它将继续等待,直到发出信号。当它最终从这个方法返回时,它的中断状态仍然被设置。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p>当调用此方法时,假定当前线程持有与此{@code Condition}相关联的锁。它是由执行来确定是否是这种情况,如果不是,如何回应。
     * 通常,将抛出异常(例如{@link IllegalMonitorStateException}),实现必须记录该事实。
     * 
     */
    void awaitUninterruptibly();

    /**
     * Causes the current thread to wait until it is signalled or interrupted,
     * or the specified waiting time elapses.
     *
     * <p>The lock associated with this condition is atomically
     * released and the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until <em>one</em> of five things happens:
     * <ul>
     * <li>Some other thread invokes the {@link #signal} method for this
     * {@code Condition} and the current thread happens to be chosen as the
     * thread to be awakened; or
     * <li>Some other thread invokes the {@link #signalAll} method for this
     * {@code Condition}; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of thread suspension is supported; or
     * <li>The specified waiting time elapses; or
     * <li>A &quot;<em>spurious wakeup</em>&quot; occurs.
     * </ul>
     *
     * <p>In all cases, before this method can return the current thread must
     * re-acquire the lock associated with this condition. When the
     * thread returns it is <em>guaranteed</em> to hold this lock.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * and interruption of thread suspension is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared. It is not specified, in the first
     * case, whether or not the test for interruption occurs before the lock
     * is released.
     *
     * <p>The method returns an estimate of the number of nanoseconds
     * remaining to wait given the supplied {@code nanosTimeout}
     * value upon return, or a value less than or equal to zero if it
     * timed out. This value can be used to determine whether and how
     * long to re-wait in cases where the wait returns but an awaited
     * condition still does not hold. Typical uses of this method take
     * the following form:
     *
     *  <pre> {@code
     * boolean aMethod(long timeout, TimeUnit unit) {
     *   long nanos = unit.toNanos(timeout);
     *   lock.lock();
     *   try {
     *     while (!conditionBeingWaitedFor()) {
     *       if (nanos <= 0L)
     *         return false;
     *       nanos = theCondition.awaitNanos(nanos);
     *     }
     *     // ...
     *   } finally {
     *     lock.unlock();
     *   }
     * }}</pre>
     *
     * <p>Design note: This method requires a nanosecond argument so
     * as to avoid truncation errors in reporting remaining times.
     * Such precision loss would make it difficult for programmers to
     * ensure that total waiting times are not systematically shorter
     * than specified when re-waits occur.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The current thread is assumed to hold the lock associated with this
     * {@code Condition} when this method is called.
     * It is up to the implementation to determine if this is
     * the case and if not, how to respond. Typically, an exception will be
     * thrown (such as {@link IllegalMonitorStateException}) and the
     * implementation must document that fact.
     *
     * <p>An implementation can favor responding to an interrupt over normal
     * method return in response to a signal, or over indicating the elapse
     * of the specified waiting time. In either case the implementation
     * must ensure that the signal is redirected to another waiting thread, if
     * there is one.
     *
     * <p>
     *  使当前线程等待,直到它发出信号或中断,或经过指定的等待时间。
     * 
     *  <p>与此条件相关联的锁定以原子方式释放,并且当前线程针对线程调度目标停用,并处于休眠状态,直到发生以下五种情况为止：<em>
     * <ul>
     * <li>一些其他线程调用此{@code Condition}的{@link #signal}方法,并且当前线程恰好被选择为要唤醒的线程;或<li>某些其他线程调用此{@code Condition}的{@link #signalAll}
     * 方法;或<li>一些其他线程{@linkplain线程#中断中断}当前线程,并且支持中断线程挂起;或<li>指定的等待时间已过;或<li>"<em>伪唤醒</em>"发生。
     * </ul>
     * 
     *  <p>在所有情况下,在此方法可以返回之前,当前线程必须重新获取与此条件相关联的锁。当线程返回时,<em>确保</em>保持此锁定。
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断中断},同时等待和中断线程挂起被支持,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。在第一种情况下,没有指定是否在锁被释放之前进行中断测试。
     * 
     *  <p>该方法返回返回时所提供的{@code nanosTimeout}值等待的纳秒数的估计值,如果超时则返回小于或等于零的值。
     * 该值可用于确定在等待返回但等待的条件仍然不成立的情况下是否以及如何重新等待。此方法的典型用途采用以下形式：。
     * 
     * <pre> {@code boolean aMethod(long timeout,TimeUnit unit){long nanos = unit.toNanos(timeout); lock.lock(); try {while(！conditionBeingWaitedFor()){if(nanos <= 0L)return false; nanos = theCondition.awaitNanos(nanos); } // ...} finally {lock.unlock(); }}} </pre>
     * 。
     * 
     *  <p>设计注意：此方法需要纳秒参数,以避免在报告剩余时间时出现截断错误。这种精度损失将使得程序员难以确保当重新等待发生时总的等待时间不会系统地短于指定时间。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p>当调用此方法时,假定当前线程持有与此{@code Condition}相关联的锁。它是由执行来确定是否是这种情况,如果不是,如何回应。
     * 通常,将抛出异常(例如{@link IllegalMonitorStateException}),实现必须记录该事实。
     * 
     *  <p>实现可以有利于响应于对信号的正常方法返回响应中断,或者超过指示经过指定的等待时间。在任一情况下,实现必须确保信号被重定向到另一个等待线程(如果有的话)。
     * 
     * 
     * @param nanosTimeout the maximum time to wait, in nanoseconds
     * @return an estimate of the {@code nanosTimeout} value minus
     *         the time spent waiting upon return from this method.
     *         A positive value may be used as the argument to a
     *         subsequent call to this method to finish waiting out
     *         the desired time.  A value less than or equal to zero
     *         indicates that no time remains.
     * @throws InterruptedException if the current thread is interrupted
     *         (and interruption of thread suspension is supported)
     */
    long awaitNanos(long nanosTimeout) throws InterruptedException;

    /**
     * Causes the current thread to wait until it is signalled or interrupted,
     * or the specified waiting time elapses. This method is behaviorally
     * equivalent to:
     *  <pre> {@code awaitNanos(unit.toNanos(time)) > 0}</pre>
     *
     * <p>
     *  使当前线程等待,直到它发出信号或中断,或经过指定的等待时间。此方法在行为上等效于：<pre> {@code awaitNanos(unit.toNanos(time))> 0} </pre>
     * 
     * 
     * @param time the maximum time to wait
     * @param unit the time unit of the {@code time} argument
     * @return {@code false} if the waiting time detectably elapsed
     *         before return from the method, else {@code true}
     * @throws InterruptedException if the current thread is interrupted
     *         (and interruption of thread suspension is supported)
     */
    boolean await(long time, TimeUnit unit) throws InterruptedException;

    /**
     * Causes the current thread to wait until it is signalled or interrupted,
     * or the specified deadline elapses.
     *
     * <p>The lock associated with this condition is atomically
     * released and the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until <em>one</em> of five things happens:
     * <ul>
     * <li>Some other thread invokes the {@link #signal} method for this
     * {@code Condition} and the current thread happens to be chosen as the
     * thread to be awakened; or
     * <li>Some other thread invokes the {@link #signalAll} method for this
     * {@code Condition}; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of thread suspension is supported; or
     * <li>The specified deadline elapses; or
     * <li>A &quot;<em>spurious wakeup</em>&quot; occurs.
     * </ul>
     *
     * <p>In all cases, before this method can return the current thread must
     * re-acquire the lock associated with this condition. When the
     * thread returns it is <em>guaranteed</em> to hold this lock.
     *
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * and interruption of thread suspension is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared. It is not specified, in the first
     * case, whether or not the test for interruption occurs before the lock
     * is released.
     *
     *
     * <p>The return value indicates whether the deadline has elapsed,
     * which can be used as follows:
     *  <pre> {@code
     * boolean aMethod(Date deadline) {
     *   boolean stillWaiting = true;
     *   lock.lock();
     *   try {
     *     while (!conditionBeingWaitedFor()) {
     *       if (!stillWaiting)
     *         return false;
     *       stillWaiting = theCondition.awaitUntil(deadline);
     *     }
     *     // ...
     *   } finally {
     *     lock.unlock();
     *   }
     * }}</pre>
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The current thread is assumed to hold the lock associated with this
     * {@code Condition} when this method is called.
     * It is up to the implementation to determine if this is
     * the case and if not, how to respond. Typically, an exception will be
     * thrown (such as {@link IllegalMonitorStateException}) and the
     * implementation must document that fact.
     *
     * <p>An implementation can favor responding to an interrupt over normal
     * method return in response to a signal, or over indicating the passing
     * of the specified deadline. In either case the implementation
     * must ensure that the signal is redirected to another waiting thread, if
     * there is one.
     *
     * <p>
     * 使当前线程等待,直到它发出信号或中断,或指定的截止时间已过。
     * 
     *  <p>与此条件相关联的锁定以原子方式释放,并且当前线程针对线程调度目标停用,并处于休眠状态,直到发生以下五种情况为止：<em>
     * <ul>
     *  <li>一些其他线程调用此{@code Condition}的{@link #signal}方法,并且当前线程恰好被选择为要唤醒的线程;或<li>某些其他线程调用此{@code Condition}的
     * {@link #signalAll}方法;或<li>一些其他线程{@linkplain线程#中断中断}当前线程,并且支持中断线程挂起;或<li>指定的截止时间已过;或<li>"<em>伪唤醒</em>"
     * 发生。
     * </ul>
     * 
     *  <p>在所有情况下,在此方法可以返回之前,当前线程必须重新获取与此条件相关联的锁。当线程返回时,<em>确保</em>保持此锁定。
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断中断},同时等待和中断线程挂起被支持,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。在第一种情况下,没有指定是否在锁被释放之前进行中断测试。
     * 
     * <p>返回值表示截止时间是否已过,可以使用如下：<pre> {@code boolean aMethod(Date deadline){boolean stillWaiting = true; lock.lock(); try {while(！conditionBeingWaitedFor()){if(！stillWaiting)return false; stillWaiting = theCondition.awaitUntil(deadline); }
     *  // ...} finally {lock.unlock(); }}} </pre>。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p>当调用此方法时,假定当前线程持有与此{@code Condition}相关联的锁。它是由执行来确定是否是这种情况,如果不是,如何回应。
     * 通常,将抛出异常(例如{@link IllegalMonitorStateException}),实现必须记录该事实。
     * 
     * @param deadline the absolute time to wait until
     * @return {@code false} if the deadline has elapsed upon return, else
     *         {@code true}
     * @throws InterruptedException if the current thread is interrupted
     *         (and interruption of thread suspension is supported)
     */
    boolean awaitUntil(Date deadline) throws InterruptedException;

    /**
     * Wakes up one waiting thread.
     *
     * <p>If any threads are waiting on this condition then one
     * is selected for waking up. That thread must then re-acquire the
     * lock before returning from {@code await}.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>An implementation may (and typically does) require that the
     * current thread hold the lock associated with this {@code
     * Condition} when this method is called. Implementations must
     * document this precondition and any actions taken if the lock is
     * not held. Typically, an exception such as {@link
     * IllegalMonitorStateException} will be thrown.
     * <p>
     * 
     *  <p>实现可以有利于响应于响应于信号的正常方法返回响应中断,或者超过指示所指定的期限的通过。在任一情况下,实现必须确保信号被重定向到另一个等待线程(如果有的话)。
     * 
     */
    void signal();

    /**
     * Wakes up all waiting threads.
     *
     * <p>If any threads are waiting on this condition then they are
     * all woken up. Each thread must re-acquire the lock before it can
     * return from {@code await}.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>An implementation may (and typically does) require that the
     * current thread hold the lock associated with this {@code
     * Condition} when this method is called. Implementations must
     * document this precondition and any actions taken if the lock is
     * not held. Typically, an exception such as {@link
     * IllegalMonitorStateException} will be thrown.
     * <p>
     *  醒来一个等待线程。
     * 
     *  <p>如果任何线程正在等待此条件,则选择一个线程唤醒。然后,该线程必须在从{@code await}返回之前重新获取锁。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     * <p>当调用此方法时,实现可能(通常情况下)要求当前线程保存与此{@code Condition}相关联的锁。实现必须记录这个前提条件,并且如果锁没有被锁住,采取的任何操作。
     * 通常,将抛出异常,例如{@link IllegalMonitorStateException}。
     * 
     */
    void signalAll();
}
