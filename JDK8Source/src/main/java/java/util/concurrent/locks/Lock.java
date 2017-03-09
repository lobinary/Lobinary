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

/**
 * {@code Lock} implementations provide more extensive locking
 * operations than can be obtained using {@code synchronized} methods
 * and statements.  They allow more flexible structuring, may have
 * quite different properties, and may support multiple associated
 * {@link Condition} objects.
 *
 * <p>A lock is a tool for controlling access to a shared resource by
 * multiple threads. Commonly, a lock provides exclusive access to a
 * shared resource: only one thread at a time can acquire the lock and
 * all access to the shared resource requires that the lock be
 * acquired first. However, some locks may allow concurrent access to
 * a shared resource, such as the read lock of a {@link ReadWriteLock}.
 *
 * <p>The use of {@code synchronized} methods or statements provides
 * access to the implicit monitor lock associated with every object, but
 * forces all lock acquisition and release to occur in a block-structured way:
 * when multiple locks are acquired they must be released in the opposite
 * order, and all locks must be released in the same lexical scope in which
 * they were acquired.
 *
 * <p>While the scoping mechanism for {@code synchronized} methods
 * and statements makes it much easier to program with monitor locks,
 * and helps avoid many common programming errors involving locks,
 * there are occasions where you need to work with locks in a more
 * flexible way. For example, some algorithms for traversing
 * concurrently accessed data structures require the use of
 * &quot;hand-over-hand&quot; or &quot;chain locking&quot;: you
 * acquire the lock of node A, then node B, then release A and acquire
 * C, then release B and acquire D and so on.  Implementations of the
 * {@code Lock} interface enable the use of such techniques by
 * allowing a lock to be acquired and released in different scopes,
 * and allowing multiple locks to be acquired and released in any
 * order.
 *
 * <p>With this increased flexibility comes additional
 * responsibility. The absence of block-structured locking removes the
 * automatic release of locks that occurs with {@code synchronized}
 * methods and statements. In most cases, the following idiom
 * should be used:
 *
 *  <pre> {@code
 * Lock l = ...;
 * l.lock();
 * try {
 *   // access the resource protected by this lock
 * } finally {
 *   l.unlock();
 * }}</pre>
 *
 * When locking and unlocking occur in different scopes, care must be
 * taken to ensure that all code that is executed while the lock is
 * held is protected by try-finally or try-catch to ensure that the
 * lock is released when necessary.
 *
 * <p>{@code Lock} implementations provide additional functionality
 * over the use of {@code synchronized} methods and statements by
 * providing a non-blocking attempt to acquire a lock ({@link
 * #tryLock()}), an attempt to acquire the lock that can be
 * interrupted ({@link #lockInterruptibly}, and an attempt to acquire
 * the lock that can timeout ({@link #tryLock(long, TimeUnit)}).
 *
 * <p>A {@code Lock} class can also provide behavior and semantics
 * that is quite different from that of the implicit monitor lock,
 * such as guaranteed ordering, non-reentrant usage, or deadlock
 * detection. If an implementation provides such specialized semantics
 * then the implementation must document those semantics.
 *
 * <p>Note that {@code Lock} instances are just normal objects and can
 * themselves be used as the target in a {@code synchronized} statement.
 * Acquiring the
 * monitor lock of a {@code Lock} instance has no specified relationship
 * with invoking any of the {@link #lock} methods of that instance.
 * It is recommended that to avoid confusion you never use {@code Lock}
 * instances in this way, except within their own implementation.
 *
 * <p>Except where noted, passing a {@code null} value for any
 * parameter will result in a {@link NullPointerException} being
 * thrown.
 *
 * <h3>Memory Synchronization</h3>
 *
 * <p>All {@code Lock} implementations <em>must</em> enforce the same
 * memory synchronization semantics as provided by the built-in monitor
 * lock, as described in
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4">
 * The Java Language Specification (17.4 Memory Model)</a>:
 * <ul>
 * <li>A successful {@code lock} operation has the same memory
 * synchronization effects as a successful <em>Lock</em> action.
 * <li>A successful {@code unlock} operation has the same
 * memory synchronization effects as a successful <em>Unlock</em> action.
 * </ul>
 *
 * Unsuccessful locking and unlocking operations, and reentrant
 * locking/unlocking operations, do not require any memory
 * synchronization effects.
 *
 * <h3>Implementation Considerations</h3>
 *
 * <p>The three forms of lock acquisition (interruptible,
 * non-interruptible, and timed) may differ in their performance
 * characteristics, ordering guarantees, or other implementation
 * qualities.  Further, the ability to interrupt the <em>ongoing</em>
 * acquisition of a lock may not be available in a given {@code Lock}
 * class.  Consequently, an implementation is not required to define
 * exactly the same guarantees or semantics for all three forms of
 * lock acquisition, nor is it required to support interruption of an
 * ongoing lock acquisition.  An implementation is required to clearly
 * document the semantics and guarantees provided by each of the
 * locking methods. It must also obey the interruption semantics as
 * defined in this interface, to the extent that interruption of lock
 * acquisition is supported: which is either totally, or only on
 * method entry.
 *
 * <p>As interruption generally implies cancellation, and checks for
 * interruption are often infrequent, an implementation can favor responding
 * to an interrupt over normal method return. This is true even if it can be
 * shown that the interrupt occurred after another action may have unblocked
 * the thread. An implementation should document this behavior.
 *
 * <p>
 *  {@code Lock}实现提供了比使用{@code synchronized}方法和语句获得的更广泛的锁定操作。
 * 它们允许更灵活的结构化,可以具有完全不同的属性,并且可以支持多个相关联的{@link Condition}对象。
 * 
 *  <p>锁是用于控制多个线程对共享资源的访问的工具。通常,锁提供对共享资源的独占访问：一次只有一个线程可以获得锁,并且对共享资源的所有访问都需要首先获取锁。
 * 然而,一些锁可以允许对共享资源的并发访问,例如{@link ReadWriteLock}的读锁。
 * 
 *  <p> {@code synchronized}方法或语句的使用提供对与每个对象相关联的隐式监视器锁的访问,但强制所有锁获取和释放以块结构的方式发生：当获取多个锁时,它们必须以相反的顺序释放,并且所有
 * 锁必须在它们被获取的相同的词汇范围内被释放。
 * 
 * <p>虽然{@code synchronized}方法和语句的范围设定机制使得使用监视器锁编程变得更加容易,并且有助于避免涉及锁的许多常见编程错误,但有时您需要使用更灵活的锁来处理锁办法。
 * 例如,用于遍历并发访问的数据结构的一些算法需要使用"手转"(hand-over-hand)或"链锁定"：获得节点A的锁,然后获得节点B,然后释放A并获取C,然后释放B并获取D等等。
 *  {@code Lock}接口的实现使得能够通过允许在不同范围中获取和释放锁来允许使用这样的技术,并且允许以任何顺序获取和释放多个锁。
 * 
 *  <p>随着这种增加的灵活性,额外的责任。没有块结构锁定会删除使用{@code synchronized}方法和语句时发生的锁的自动释放。在大多数情况下,应使用以下成语：
 * 
 *  <pre> {@code Lock l = ...; l.lock(); try {//访问受此锁保护的资源} finally {l.unlock(); }} </pre>
 * 
 *  当在不同范围内发生锁定和解锁时,必须注意确保在锁定被保持时执行的所有代码由try-finally或try-catch保护,以确保在必要时释放锁定。
 * 
 * <p> {@ code Lock}实现通过提供获取锁的非阻塞尝试({@link #tryLock()})提供了使用{@code synchronized}方法和语句的额外功能,尝试获取可以中断的锁({@link #lockInterruptibly}
 * )和尝试获取可以超时的锁({@link #tryLock(long,TimeUnit)})。
 * 
 *  <p> {@code Lock}类还可以提供与隐式监视器锁的行为和语义完全不同的行为和语义,例如保证排序,不可重入使用或死锁检测。如果实现提供这样的专用语义,则实现必须记录那些语义。
 * 
 *  <p>请注意,{@code Lock}实例只是普通对象,可以在{@code synchronized}语句中用作目标。
 * 获取{@code Lock}实例的监视器锁与调用该实例的任何{@link #lock}方法没有指定的关系。为了避免混淆,建议您不要以这种方式使用{@code Lock}实例,除非在自己的实现中。
 * 
 *  <p>除非另有说明,否则传递任何参数的{@code null}值将导致抛出{@link NullPointerException}。
 * 
 *  <h3>内存同步</h3>
 * 
 *  <p>所有{@code Lock}实现<em>必须</em>强制执行与内置监视器锁所提供的相同的内存同步语义,如
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4">
 *  Java语言规范(17.4内存模型)</a>：
 * <ul>
 * <li>成功的{@code lock}操作与成功的<em>锁定</em>操作具有相同的记忆体同步效果。 <li>成功的{@code unlock}操作与成功的<em>解锁操作具有相同的内存同步效果。
 * </ul>
 * 
 *  不成功的锁定和解锁操作以及可重入的锁定/解锁操作不需要任何内存同步效应。
 * 
 *  <h3>实施注意事项</h3>
 * 
 *  <p>三种形式的锁获取(可中断,不可中断和定时)可能在它们的性能特性,顺序保证或其它实现特性方面不同。此外,在给定的{@code Lock}类中可能无法中断正在进行的</em>获取锁的能力。
 * 因此,实现不需要为所有三种形式的锁获取精确地定义相同的保证或语义,也不需要支持正在进行的锁获取的中断。需要实现来清楚地记录每种锁定方法提供的语义和保证。
 * 它还必须遵守在该接口中定义的中断语义,在支持锁获取的中断的程度上：这是完全或仅在方法入口。
 * 
 * <p>由于中断通常意味着取消,并且检查中断通常很少发生,因此实现可以有利于响应于正常方法返回的中断。这是真的,即使可以显示中断发生在另一个动作可能已解除线程之后。实现应该记录这种行为。
 * 
 * 
 * @see ReentrantLock
 * @see Condition
 * @see ReadWriteLock
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface Lock {

    /**
     * Acquires the lock.
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until the
     * lock has been acquired.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>A {@code Lock} implementation may be able to detect erroneous use
     * of the lock, such as an invocation that would cause deadlock, and
     * may throw an (unchecked) exception in such circumstances.  The
     * circumstances and the exception type must be documented by that
     * {@code Lock} implementation.
     * <p>
     *  获取锁。
     * 
     *  <p>如果锁不可用,则当前线程将禁用以用于线程调度目的,并处于休眠状态,直到获取锁。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p> {@code Lock}实现可以检测锁的错误使用,例如可能导致死锁的调用,并且在这种情况下可能抛出(未检查的)异常。 {@code Lock}实现必须记录情境和异常类型。
     * 
     */
    void lock();

    /**
     * Acquires the lock unless the current thread is
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>Acquires the lock if it is available and returns immediately.
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until
     * one of two things happens:
     *
     * <ul>
     * <li>The lock is acquired by the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of lock acquisition is supported.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring the
     * lock, and interruption of lock acquisition is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The ability to interrupt a lock acquisition in some
     * implementations may not be possible, and if possible may be an
     * expensive operation.  The programmer should be aware that this
     * may be the case. An implementation should document when this is
     * the case.
     *
     * <p>An implementation can favor responding to an interrupt over
     * normal method return.
     *
     * <p>A {@code Lock} implementation may be able to detect
     * erroneous use of the lock, such as an invocation that would
     * cause deadlock, and may throw an (unchecked) exception in such
     * circumstances.  The circumstances and the exception type must
     * be documented by that {@code Lock} implementation.
     *
     * <p>
     *  获取锁,除非当前线程是{@linkplain线程#中断}。
     * 
     *  <p>获取锁定,如果可用并立即返回。
     * 
     *  <p>如果锁定不可用,则当前线程将被禁用以进行线程调度,并处于休眠状态,直到发生以下两种情况之一：
     * 
     * <ul>
     *  <li>锁定由当前线程获取;或<li>一些其他线程{@linkplain线程#中断中断}当前线程,并支持中断锁获取。
     * </ul>
     * 
     *  <p>如果当前线程：
     * <ul>
     * <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断}同时获取锁定,并且支持中断锁定获取,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  在一些实现中中断锁获取的能力可能是不可能的,并且如果可能的话可能是昂贵的操作。程序员应该知道可能是这种情况。实现应该记录这种情况。
     * 
     *  <p>实现可以有利于响应正常方法返回的中断。
     * 
     *  <p> {@code Lock}实现可以检测锁的错误使用,例如可能导致死锁的调用,并且在这种情况下可能抛出(未检查的)异常。 {@code Lock}实现必须记录情境和异常类型。
     * 
     * 
     * @throws InterruptedException if the current thread is
     *         interrupted while acquiring the lock (and interruption
     *         of lock acquisition is supported)
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * Acquires the lock only if it is free at the time of invocation.
     *
     * <p>Acquires the lock if it is available and returns immediately
     * with the value {@code true}.
     * If the lock is not available then this method will return
     * immediately with the value {@code false}.
     *
     * <p>A typical usage idiom for this method would be:
     *  <pre> {@code
     * Lock lock = ...;
     * if (lock.tryLock()) {
     *   try {
     *     // manipulate protected state
     *   } finally {
     *     lock.unlock();
     *   }
     * } else {
     *   // perform alternative actions
     * }}</pre>
     *
     * This usage ensures that the lock is unlocked if it was acquired, and
     * doesn't try to unlock if the lock was not acquired.
     *
     * <p>
     *  只有在调用时它是自由的,才获取锁。
     * 
     *  <p>获取锁定(如果可用),并立即返回值{@code true}。如果锁不可用,那么此方法将立即返回值{@code false}。
     * 
     *  <p>这种方法的典型用法是：<pre> {@code Lock lock = ...; if(lock.tryLock()){try {//操纵受保护状态}最终{lock.unlock(); }} e
     * lse {//执行替代动作}} </pre>。
     * 
     * 此使用确保锁被解锁(如果已获取),并且如果未获取锁,则不尝试解锁。
     * 
     * 
     * @return {@code true} if the lock was acquired and
     *         {@code false} otherwise
     */
    boolean tryLock();

    /**
     * Acquires the lock if it is free within the given waiting time and the
     * current thread has not been {@linkplain Thread#interrupt interrupted}.
     *
     * <p>If the lock is available this method returns immediately
     * with the value {@code true}.
     * If the lock is not available then
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of three things happens:
     * <ul>
     * <li>The lock is acquired by the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of lock acquisition is supported; or
     * <li>The specified waiting time elapses
     * </ul>
     *
     * <p>If the lock is acquired then the value {@code true} is returned.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
     * the lock, and interruption of lock acquisition is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.
     * If the time is
     * less than or equal to zero, the method will not wait at all.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The ability to interrupt a lock acquisition in some implementations
     * may not be possible, and if possible may
     * be an expensive operation.
     * The programmer should be aware that this may be the case. An
     * implementation should document when this is the case.
     *
     * <p>An implementation can favor responding to an interrupt over normal
     * method return, or reporting a timeout.
     *
     * <p>A {@code Lock} implementation may be able to detect
     * erroneous use of the lock, such as an invocation that would cause
     * deadlock, and may throw an (unchecked) exception in such circumstances.
     * The circumstances and the exception type must be documented by that
     * {@code Lock} implementation.
     *
     * <p>
     *  如果在给定的等待时间内空闲,并且当前线程尚未{@linkplain线程#中断},则获取锁。
     * 
     *  <p>如果锁可用,此方法将立即返回值{@code true}。如果锁不可用,则当前线程变为禁用以用于线程调度目的,并且处于休眠状态直到发生以下三种情况之一：
     * <ul>
     *  <li>锁定由当前线程获取;或<li>一些其他线程{@linkplain线程#中断中断}当前线程,并且支持中断锁获取;或<li>指定的等待时间已过
     * </ul>
     * 
     *  <p>如果获取锁,则返回值{@code true}。
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断}同时获取锁定,并且支持中断锁定获取,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     *  <p>如果经过指定的等待时间,则返回值{@code false}。如果时间小于或等于零,则该方法将不会等待。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     * 在一些实现中中断锁获取的能力可能是不可能的,并且如果可能的话可能是昂贵的操作。程序员应该知道可能是这种情况。实现应该记录这种情况。
     * 
     * 
     * @param time the maximum time to wait for the lock
     * @param unit the time unit of the {@code time} argument
     * @return {@code true} if the lock was acquired and {@code false}
     *         if the waiting time elapsed before the lock was acquired
     *
     * @throws InterruptedException if the current thread is interrupted
     *         while acquiring the lock (and interruption of lock
     *         acquisition is supported)
     */
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    /**
     * Releases the lock.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>A {@code Lock} implementation will usually impose
     * restrictions on which thread can release a lock (typically only the
     * holder of the lock can release it) and may throw
     * an (unchecked) exception if the restriction is violated.
     * Any restrictions and the exception
     * type must be documented by that {@code Lock} implementation.
     * <p>
     *  <p>实现可以有利于响应正常方法返回的中断,或报告超时。
     * 
     *  <p> {@code Lock}实现可以检测锁的错误使用,例如可能导致死锁的调用,并且在这种情况下可能抛出(未检查的)异常。 {@code Lock}实现必须记录情境和异常类型。
     * 
     */
    void unlock();

    /**
     * Returns a new {@link Condition} instance that is bound to this
     * {@code Lock} instance.
     *
     * <p>Before waiting on the condition the lock must be held by the
     * current thread.
     * A call to {@link Condition#await()} will atomically release the lock
     * before waiting and re-acquire the lock before the wait returns.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The exact operation of the {@link Condition} instance depends on
     * the {@code Lock} implementation and must be documented by that
     * implementation.
     *
     * <p>
     *  释放锁。
     * 
     *  <p> <b>实施注意事项</b>
     * 
     *  <p> {@code Lock}实现通常会限制哪个线程可以释放锁(通常只有锁的持有者可以释放它),并且如果违反了限制,可能会抛出(未检查的)异常。
     * 任何限制和异常类型都必须通过{@code Lock}实现来记录。
     * 
     * 
     * @return A new {@link Condition} instance for this {@code Lock} instance
     * @throws UnsupportedOperationException if this {@code Lock}
     *         implementation does not support conditions
     */
    Condition newCondition();
}
