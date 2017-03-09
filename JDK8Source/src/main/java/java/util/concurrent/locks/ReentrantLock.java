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
import java.util.Collection;

/**
 * A reentrant mutual exclusion {@link Lock} with the same basic
 * behavior and semantics as the implicit monitor lock accessed using
 * {@code synchronized} methods and statements, but with extended
 * capabilities.
 *
 * <p>A {@code ReentrantLock} is <em>owned</em> by the thread last
 * successfully locking, but not yet unlocking it. A thread invoking
 * {@code lock} will return, successfully acquiring the lock, when
 * the lock is not owned by another thread. The method will return
 * immediately if the current thread already owns the lock. This can
 * be checked using methods {@link #isHeldByCurrentThread}, and {@link
 * #getHoldCount}.
 *
 * <p>The constructor for this class accepts an optional
 * <em>fairness</em> parameter.  When set {@code true}, under
 * contention, locks favor granting access to the longest-waiting
 * thread.  Otherwise this lock does not guarantee any particular
 * access order.  Programs using fair locks accessed by many threads
 * may display lower overall throughput (i.e., are slower; often much
 * slower) than those using the default setting, but have smaller
 * variances in times to obtain locks and guarantee lack of
 * starvation. Note however, that fairness of locks does not guarantee
 * fairness of thread scheduling. Thus, one of many threads using a
 * fair lock may obtain it multiple times in succession while other
 * active threads are not progressing and not currently holding the
 * lock.
 * Also note that the untimed {@link #tryLock()} method does not
 * honor the fairness setting. It will succeed if the lock
 * is available even if other threads are waiting.
 *
 * <p>It is recommended practice to <em>always</em> immediately
 * follow a call to {@code lock} with a {@code try} block, most
 * typically in a before/after construction such as:
 *
 *  <pre> {@code
 * class X {
 *   private final ReentrantLock lock = new ReentrantLock();
 *   // ...
 *
 *   public void m() {
 *     lock.lock();  // block until condition holds
 *     try {
 *       // ... method body
 *     } finally {
 *       lock.unlock()
 *     }
 *   }
 * }}</pre>
 *
 * <p>In addition to implementing the {@link Lock} interface, this
 * class defines a number of {@code public} and {@code protected}
 * methods for inspecting the state of the lock.  Some of these
 * methods are only useful for instrumentation and monitoring.
 *
 * <p>Serialization of this class behaves in the same way as built-in
 * locks: a deserialized lock is in the unlocked state, regardless of
 * its state when serialized.
 *
 * <p>This lock supports a maximum of 2147483647 recursive locks by
 * the same thread. Attempts to exceed this limit result in
 * {@link Error} throws from locking methods.
 *
 * <p>
 *  可重入互斥{@link Lock}具有与使用{@code synchronized}方法和语句访问的隐式监视器锁相同的基本行为和语义,但具有扩展功能。
 * 
 *  <p> {@code ReentrantLock}由线程上次成功锁定</em>拥有,但尚未解锁。调用{@code lock}的线程将返回,成功获取锁,当锁不是由另一个线程拥有。
 * 如果当前线程已经拥有锁,该方法将立即返回。这可以使用{@link #isHeldByCurrentThread}和{@link #getHoldCount}方法检查。
 * 
 * <p>此类的构造函数接受可选的<em>公平</em>参数。当设置为{@code true}时,在争用之下,锁优选允许访问最长等待的线程。否则,此锁不保证任何特定的访问顺序。
 * 使用由许多线程访问的公平锁的程序可以显示比使用默认设置的那些更低的总吞吐量(即,更慢,通常慢得多),但是具有更小的方差以获得锁并且保证缺乏饥饿。然而,请注意,锁的公平性不保证线程调度的公平性。
 * 因此,使用公平锁的许多线程中的一个可以连续多次获得它,而其他活动线程不进行并且当前不持有锁。另请注意,未定义的{@link #tryLock()}方法不符合公平设置。
 * 如果锁可用,即使其他线程正在等待,它也会成功。
 * 
 *  <p>建议使用{@code try}块调用{@code lock},通常在之前/之后的建议,例如：</em>
 * 
 *  <pre> {@code class X {private final ReentrantLock lock = new ReentrantLock(); // ...
 * 
 *  public void m(){lock.lock(); // block until condition hold try {// ... method body} finally {lock.unlock()}
 * }}} </pre>。
 * 
 * <p>除了实现{@link Lock}接口,此类还定义了许多{@code public}和{@code protected}方法来检查锁的状态。这些方法中的一些仅用于仪器和监测。
 * 
 *  <p>此类的序列化与内置锁的行为相同：反序列化锁处于解锁状态,无论序列化时的状态如何。
 * 
 *  <p>此锁定支持同一个线程的最多2147483647个递归锁。尝试超过此限制会导致{@link错误}抛出锁定方法。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public class ReentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;
    /** Synchronizer providing all implementation mechanics */
    private final Sync sync;

    /**
     * Base of synchronization control for this lock. Subclassed
     * into fair and nonfair versions below. Uses AQS state to
     * represent the number of holds on the lock.
     * <p>
     *  此锁的同步控制基础。下面分为公平和非公平版本。使用AQS状态表示锁上的保持数。
     * 
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -5179523762034025860L;

        /**
         * Performs {@link Lock#lock}. The main reason for subclassing
         * is to allow fast path for nonfair version.
         * <p>
         *  执行{@link Lock#lock}。子类化的主要原因是允许非公平版本的快速路径。
         * 
         */
        abstract void lock();

        /**
         * Performs non-fair tryLock.  tryAcquire is implemented in
         * subclasses, but both need nonfair try for trylock method.
         * <p>
         *  执行不公平的tryLock。 tryAcquire是在子类中实现的,但是两者都需要非正确的try try方法。
         * 
         */
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        // Methods relayed from outer class

        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         * <p>
         *  从流重构实例(即,反序列化它)。
         * 
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    /**
     * Sync object for non-fair locks
     * <p>
     *  非公平锁的同步对象
     * 
     */
    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * Performs lock.  Try immediate barge, backing up to normal
         * acquire on failure.
         * <p>
         *  执行锁定。尝试立即插入,在故障时备份到正常获取。
         * 
         */
        final void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    /**
     * Sync object for fair locks
     * <p>
     *  同步对象的公平锁
     * 
     */
    static final class FairSync extends Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lock() {
            acquire(1);
        }

        /**
         * Fair version of tryAcquire.  Don't grant access unless
         * recursive call or no waiters or is first.
         * <p>
         *  公平版的tryAcquire。不要授予访问权限,除非递归调用或没有服务员或第一。
         * 
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    /**
     * Creates an instance of {@code ReentrantLock}.
     * This is equivalent to using {@code ReentrantLock(false)}.
     * <p>
     *  创建{@code ReentrantLock}的实例。这相当于使用{@code ReentrantLock(false)}。
     * 
     */
    public ReentrantLock() {
        sync = new NonfairSync();
    }

    /**
     * Creates an instance of {@code ReentrantLock} with the
     * given fairness policy.
     *
     * <p>
     *  使用给定的公平策略创建{@code ReentrantLock}的实例。
     * 
     * 
     * @param fair {@code true} if this lock should use a fair ordering policy
     */
    public ReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    /**
     * Acquires the lock.
     *
     * <p>Acquires the lock if it is not held by another thread and returns
     * immediately, setting the lock hold count to one.
     *
     * <p>If the current thread already holds the lock then the hold
     * count is incremented by one and the method returns immediately.
     *
     * <p>If the lock is held by another thread then the
     * current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the lock has been acquired,
     * at which time the lock hold count is set to one.
     * <p>
     *  获取锁。
     * 
     * <p>如果锁没有被其他线程占用并立即返回,获取锁,将锁保持计数设置为1。
     * 
     *  <p>如果当前线程已经持有锁,则保持计数增加1,方法立即返回。
     * 
     *  <p>如果锁由另一个线程保持,则当前线程变为禁用以用于线程调度目的,并且处于休眠状态直到获取锁,在该时间锁保持计数被设置为一。
     * 
     */
    public void lock() {
        sync.lock();
    }

    /**
     * Acquires the lock unless the current thread is
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>Acquires the lock if it is not held by another thread and returns
     * immediately, setting the lock hold count to one.
     *
     * <p>If the current thread already holds this lock then the hold count
     * is incremented by one and the method returns immediately.
     *
     * <p>If the lock is held by another thread then the
     * current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of two things happens:
     *
     * <ul>
     *
     * <li>The lock is acquired by the current thread; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread.
     *
     * </ul>
     *
     * <p>If the lock is acquired by the current thread then the lock hold
     * count is set to one.
     *
     * <p>If the current thread:
     *
     * <ul>
     *
     * <li>has its interrupted status set on entry to this method; or
     *
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
     * the lock,
     *
     * </ul>
     *
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>In this implementation, as this method is an explicit
     * interruption point, preference is given to responding to the
     * interrupt over normal or reentrant acquisition of the lock.
     *
     * <p>
     *  获取锁,除非当前线程是{@linkplain线程#中断}。
     * 
     *  <p>如果锁没有被其他线程占用并立即返回,获取锁,将锁保持计数设置为1。
     * 
     *  <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法立即返回。
     * 
     *  <p>如果锁由另一个线程持有,那么当前线程将变为禁用以进行线程调度,并处于休眠状态,直到发生以下两种情况之一：
     * 
     * <ul>
     * 
     *  <li>锁定由当前线程获取;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程。
     * 
     * </ul>
     * 
     *  <p>如果锁由当前线程获取,则锁保持计数设置为1。
     * 
     *  <p>如果当前线程：
     * 
     * <ul>
     * 
     *  <li>在进入此方法时设置了中断状态;要么
     * 
     *  <li>是{@linkplain线程#中断}获取锁时,
     * 
     * </ul>
     * 
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * 在该实现中,因为该方法是显式中断点,所以优选在正常或可重入获取锁时响应中断。
     * 
     * 
     * @throws InterruptedException if the current thread is interrupted
     */
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    /**
     * Acquires the lock only if it is not held by another thread at the time
     * of invocation.
     *
     * <p>Acquires the lock if it is not held by another thread and
     * returns immediately with the value {@code true}, setting the
     * lock hold count to one. Even when this lock has been set to use a
     * fair ordering policy, a call to {@code tryLock()} <em>will</em>
     * immediately acquire the lock if it is available, whether or not
     * other threads are currently waiting for the lock.
     * This &quot;barging&quot; behavior can be useful in certain
     * circumstances, even though it breaks fairness. If you want to honor
     * the fairness setting for this lock, then use
     * {@link #tryLock(long, TimeUnit) tryLock(0, TimeUnit.SECONDS) }
     * which is almost equivalent (it also detects interruption).
     *
     * <p>If the current thread already holds this lock then the hold
     * count is incremented by one and the method returns {@code true}.
     *
     * <p>If the lock is held by another thread then this method will return
     * immediately with the value {@code false}.
     *
     * <p>
     *  只有在调用时它不被另一个线程占用,才获取锁。
     * 
     *  <p>如果锁未被另一个线程占用,则获取锁,并立即返回值{@code true},将锁保持计数设置为1。
     * 即使此锁已设置为使用公平的排序策略,调用{@code tryLock()} <em>将立即获取锁,如果可用,无论是否其他线程当前正在等待锁。这种"驳船"行为在某些情况下可能是有用的,即使它破坏公平。
     * 如果您想要遵守此锁定的公平设置,请使用{@link #tryLock(long,TimeUnit)tryLock(0,TimeUnit.SECONDS)},这几乎是等效的(它也检测到中断)。
     * 
     *  <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法返回{@code true}。
     * 
     *  <p>如果锁由另一个线程持有,那么此方法将立即返回值{@code false}。
     * 
     * 
     * @return {@code true} if the lock was free and was acquired by the
     *         current thread, or the lock was already held by the current
     *         thread; and {@code false} otherwise
     */
    public boolean tryLock() {
        return sync.nonfairTryAcquire(1);
    }

    /**
     * Acquires the lock if it is not held by another thread within the given
     * waiting time and the current thread has not been
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>Acquires the lock if it is not held by another thread and returns
     * immediately with the value {@code true}, setting the lock hold count
     * to one. If this lock has been set to use a fair ordering policy then
     * an available lock <em>will not</em> be acquired if any other threads
     * are waiting for the lock. This is in contrast to the {@link #tryLock()}
     * method. If you want a timed {@code tryLock} that does permit barging on
     * a fair lock then combine the timed and un-timed forms together:
     *
     *  <pre> {@code
     * if (lock.tryLock() ||
     *     lock.tryLock(timeout, unit)) {
     *   ...
     * }}</pre>
     *
     * <p>If the current thread
     * already holds this lock then the hold count is incremented by one and
     * the method returns {@code true}.
     *
     * <p>If the lock is held by another thread then the
     * current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of three things happens:
     *
     * <ul>
     *
     * <li>The lock is acquired by the current thread; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified waiting time elapses
     *
     * </ul>
     *
     * <p>If the lock is acquired then the value {@code true} is returned and
     * the lock hold count is set to one.
     *
     * <p>If the current thread:
     *
     * <ul>
     *
     * <li>has its interrupted status set on entry to this method; or
     *
     * <li>is {@linkplain Thread#interrupt interrupted} while
     * acquiring the lock,
     *
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.  If the time is less than or equal to zero, the method
     * will not wait at all.
     *
     * <p>In this implementation, as this method is an explicit
     * interruption point, preference is given to responding to the
     * interrupt over normal or reentrant acquisition of the lock, and
     * over reporting the elapse of the waiting time.
     *
     * <p>
     *  如果在给定的等待时间内没有被另一个线程占用,并且当前线程尚未{@linkplain线程#中断中断},则获取锁。
     * 
     * <p>如果锁未被另一个线程占用,则获取锁,并立即返回值{@code true},将锁保持计数设置为1。如果这个锁被设置为使用公平的排序策略,则如果任何其他线程正在等待锁,则不会获取可用的锁<em>。
     * 这与{@link #tryLock()}方法形成对比。如果你想要一个定时的{@code tryLock}允许在公平锁定上驳船,然后结合定时和非定时形式在一起：。
     * 
     *  <pre> {@code if(lock.tryLock()|| lock.tryLock(timeout,unit)){...}} </pre>
     * 
     *  <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法返回{@code true}。
     * 
     *  <p>如果锁由另一个线程持有,那么当前线程将被禁用以进行线程调度,并处于休眠状态,直到发生以下三种情况之一：
     * 
     * <ul>
     * 
     *  <li>锁定由当前线程获取;要么
     * 
     *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
     * 
     *  <li>经过指定的等待时间
     * 
     * </ul>
     * 
     *  <p>如果获取锁,则返回值{@code true},锁保持计数设置为1。
     * 
     *  <p>如果当前线程：
     * 
     * <ul>
     * 
     *  <li>在进入此方法时设置了中断状态;要么
     * 
     *  <li>是{@linkplain线程#中断}获取锁时,
     * 
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * <p>如果经过指定的等待时间,则返回值{@code false}。如果时间小于或等于零,则该方法将不会等待。
     * 
     *  <p>在该实现中,由于该方法是明确的中断点,所以优选在正常或可重入获取锁的情况下响应于中断,并且超过报告等待时间的流逝。
     * 
     * 
     * @param timeout the time to wait for the lock
     * @param unit the time unit of the timeout argument
     * @return {@code true} if the lock was free and was acquired by the
     *         current thread, or the lock was already held by the current
     *         thread; and {@code false} if the waiting time elapsed before
     *         the lock could be acquired
     * @throws InterruptedException if the current thread is interrupted
     * @throws NullPointerException if the time unit is null
     */
    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    /**
     * Attempts to release this lock.
     *
     * <p>If the current thread is the holder of this lock then the hold
     * count is decremented.  If the hold count is now zero then the lock
     * is released.  If the current thread is not the holder of this
     * lock then {@link IllegalMonitorStateException} is thrown.
     *
     * <p>
     *  尝试释放此锁定。
     * 
     *  <p>如果当前线程是这个锁的持有者,则保持计数递减。如果保持计数现在为零,则锁定被释放。
     * 如果当前线程不是这个锁的持有者,那么会抛出{@link IllegalMonitorStateException}。
     * 
     * 
     * @throws IllegalMonitorStateException if the current thread does not
     *         hold this lock
     */
    public void unlock() {
        sync.release(1);
    }

    /**
     * Returns a {@link Condition} instance for use with this
     * {@link Lock} instance.
     *
     * <p>The returned {@link Condition} instance supports the same
     * usages as do the {@link Object} monitor methods ({@link
     * Object#wait() wait}, {@link Object#notify notify}, and {@link
     * Object#notifyAll notifyAll}) when used with the built-in
     * monitor lock.
     *
     * <ul>
     *
     * <li>If this lock is not held when any of the {@link Condition}
     * {@linkplain Condition#await() waiting} or {@linkplain
     * Condition#signal signalling} methods are called, then an {@link
     * IllegalMonitorStateException} is thrown.
     *
     * <li>When the condition {@linkplain Condition#await() waiting}
     * methods are called the lock is released and, before they
     * return, the lock is reacquired and the lock hold count restored
     * to what it was when the method was called.
     *
     * <li>If a thread is {@linkplain Thread#interrupt interrupted}
     * while waiting then the wait will terminate, an {@link
     * InterruptedException} will be thrown, and the thread's
     * interrupted status will be cleared.
     *
     * <li> Waiting threads are signalled in FIFO order.
     *
     * <li>The ordering of lock reacquisition for threads returning
     * from waiting methods is the same as for threads initially
     * acquiring the lock, which is in the default case not specified,
     * but for <em>fair</em> locks favors those threads that have been
     * waiting the longest.
     *
     * </ul>
     *
     * <p>
     *  返回与此{@link Lock}实例一起使用的{@link Condition}实例。
     * 
     *  <p>返回的{@link Condition}实例支持与{@link Object}监视方法({@link Object#wait()wait},{@link Object#notify notify}
     * 和{@link Object#notifyAll notifyAll})当与内置的监视器锁一起使用时。
     * 
     * <ul>
     * 
     *  <li>如果在调用{@link Condition} {@linkplain Condition#await()waiting}或{@linkplain Condition#signal signaling}
     * 方法时未锁定此锁定,则会抛出{@link IllegalMonitorStateException} 。
     * 
     *  <li>当调用{@linkplain Condition#await()waiting)方法时,会释放锁定,并且在它们返回之前,将重新获取锁定,并且锁定保持计数将恢复为调用方法时的值。
     * 
     * <li>如果在等待时线程{@linkplain线程#中断},则等待将终止,将抛出一个{@link InterruptedException},并且线程的中断状态将被清除。
     * 
     *  <li>等待线程以FIFO顺序发出信号。
     * 
     *  <li>从等待方法返回的线程的锁重新捕获的排序与初始获取锁的线程相同,这是默认情况下未指定的,但是<em> </em>锁优先考虑那些具有等待时间最长。
     * 
     * </ul>
     * 
     * 
     * @return the Condition object
     */
    public Condition newCondition() {
        return sync.newCondition();
    }

    /**
     * Queries the number of holds on this lock by the current thread.
     *
     * <p>A thread has a hold on a lock for each lock action that is not
     * matched by an unlock action.
     *
     * <p>The hold count information is typically only used for testing and
     * debugging purposes. For example, if a certain section of code should
     * not be entered with the lock already held then we can assert that
     * fact:
     *
     *  <pre> {@code
     * class X {
     *   ReentrantLock lock = new ReentrantLock();
     *   // ...
     *   public void m() {
     *     assert lock.getHoldCount() == 0;
     *     lock.lock();
     *     try {
     *       // ... method body
     *     } finally {
     *       lock.unlock();
     *     }
     *   }
     * }}</pre>
     *
     * <p>
     *  查询当前线程对此锁定的保留数。
     * 
     *  <p>线程对于每个锁定操作都有一个锁定保持,它不与解锁操作匹配。
     * 
     *  <p>保持计数信息通常仅用于测试和调试目的。例如,如果不应该使用已经持有的锁来输入某段代码,那么我们可以断言这一事实​​：
     * 
     *  <pre> {@code class X {ReentrantLock lock = new ReentrantLock(); // ... public void m(){assert lock.getHoldCount()== 0; lock.lock(); try {// ... method body}
     *  finally {lock.unlock(); }}}} </pre>。
     * 
     * 
     * @return the number of holds on this lock by the current thread,
     *         or zero if this lock is not held by the current thread
     */
    public int getHoldCount() {
        return sync.getHoldCount();
    }

    /**
     * Queries if this lock is held by the current thread.
     *
     * <p>Analogous to the {@link Thread#holdsLock(Object)} method for
     * built-in monitor locks, this method is typically used for
     * debugging and testing. For example, a method that should only be
     * called while a lock is held can assert that this is the case:
     *
     *  <pre> {@code
     * class X {
     *   ReentrantLock lock = new ReentrantLock();
     *   // ...
     *
     *   public void m() {
     *       assert lock.isHeldByCurrentThread();
     *       // ... method body
     *   }
     * }}</pre>
     *
     * <p>It can also be used to ensure that a reentrant lock is used
     * in a non-reentrant manner, for example:
     *
     *  <pre> {@code
     * class X {
     *   ReentrantLock lock = new ReentrantLock();
     *   // ...
     *
     *   public void m() {
     *       assert !lock.isHeldByCurrentThread();
     *       lock.lock();
     *       try {
     *           // ... method body
     *       } finally {
     *           lock.unlock();
     *       }
     *   }
     * }}</pre>
     *
     * <p>
     *  查询此锁是否由当前线程持有。
     * 
     *  <p>与内置监视器锁的{@link Thread#holdsLock(Object)}方法类似,此方法通常用于调试和测试。例如,应该仅在锁定被锁定时才调用的方法可以声明这种情况：
     * 
     *  <pre> {@code class X {ReentrantLock lock = new ReentrantLock(); // ...
     * 
     * public void m(){assert lock.isHeldByCurrentThread(); // ... method body}}} </pre>
     * 
     *  <p>它还可以用于确保以不可重入的方式使用可重入锁定,例如：
     * 
     *  <pre> {@code class X {ReentrantLock lock = new ReentrantLock(); // ...
     * 
     *  public void m(){assert！lock.isHeldByCurrentThread(); lock.lock(); try {// ... method body} finally {lock.unlock(); }
     * }}} </pre>。
     * 
     * 
     * @return {@code true} if current thread holds this lock and
     *         {@code false} otherwise
     */
    public boolean isHeldByCurrentThread() {
        return sync.isHeldExclusively();
    }

    /**
     * Queries if this lock is held by any thread. This method is
     * designed for use in monitoring of the system state,
     * not for synchronization control.
     *
     * <p>
     *  查询此锁是否由任何线程持有。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return {@code true} if any thread holds this lock and
     *         {@code false} otherwise
     */
    public boolean isLocked() {
        return sync.isLocked();
    }

    /**
     * Returns {@code true} if this lock has fairness set true.
     *
     * <p>
     *  如果此锁定的公平性设置为true,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this lock has fairness set true
     */
    public final boolean isFair() {
        return sync instanceof FairSync;
    }

    /**
     * Returns the thread that currently owns this lock, or
     * {@code null} if not owned. When this method is called by a
     * thread that is not the owner, the return value reflects a
     * best-effort approximation of current lock status. For example,
     * the owner may be momentarily {@code null} even if there are
     * threads trying to acquire the lock but have not yet done so.
     * This method is designed to facilitate construction of
     * subclasses that provide more extensive lock monitoring
     * facilities.
     *
     * <p>
     *  返回当前拥有此锁的线程,如果不拥有则返回{@code null}。当此方法由不是所有者的线程调用时,返回值反映当前锁状态的尽力而为近似值。
     * 例如,所有者可能会暂时{@code null},即使有线程试图获取锁,但尚未这样做。该方法被设计为便于构建提供更广泛的锁监视设施的子类。
     * 
     * 
     * @return the owner, or {@code null} if not owned
     */
    protected Thread getOwner() {
        return sync.getOwner();
    }

    /**
     * Queries whether any threads are waiting to acquire this lock. Note that
     * because cancellations may occur at any time, a {@code true}
     * return does not guarantee that any other thread will ever
     * acquire this lock.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * <p>
     *  查询任何线程是否正在等待获取此锁。注意,因为取消可能在任何时候发生,{@code true}返回不保证任何其他线程将获得此锁。该方法主要用于监视系统状态。
     * 
     * 
     * @return {@code true} if there may be other threads waiting to
     *         acquire the lock
     */
    public final boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    /**
     * Queries whether the given thread is waiting to acquire this
     * lock. Note that because cancellations may occur at any time, a
     * {@code true} return does not guarantee that this thread
     * will ever acquire this lock.  This method is designed primarily for use
     * in monitoring of the system state.
     *
     * <p>
     * 查询给定的线程是否正在等待获取此锁。注意,因为取消可能在任何时候发生,{@code true}返回不保证此线程将获得此锁。该方法主要用于监视系统状态。
     * 
     * 
     * @param thread the thread
     * @return {@code true} if the given thread is queued waiting for this lock
     * @throws NullPointerException if the thread is null
     */
    public final boolean hasQueuedThread(Thread thread) {
        return sync.isQueued(thread);
    }

    /**
     * Returns an estimate of the number of threads waiting to
     * acquire this lock.  The value is only an estimate because the number of
     * threads may change dynamically while this method traverses
     * internal data structures.  This method is designed for use in
     * monitoring of the system state, not for synchronization
     * control.
     *
     * <p>
     *  返回等待获取此锁的线程数的估计值。该值只是一个估计值,因为线程的数量可能会在此方法遍历内部数据结构时动态更改。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return the estimated number of threads waiting for this lock
     */
    public final int getQueueLength() {
        return sync.getQueueLength();
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire this lock.  Because the actual set of threads may change
     * dynamically while constructing this result, the returned
     * collection is only a best-effort estimate.  The elements of the
     * returned collection are in no particular order.  This method is
     * designed to facilitate construction of subclasses that provide
     * more extensive monitoring facilities.
     *
     * <p>
     *  返回包含可能正在等待获取此锁的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
     * 该方法被设计为便于构建提供更广泛的监测设施的子类。
     * 
     * 
     * @return the collection of threads
     */
    protected Collection<Thread> getQueuedThreads() {
        return sync.getQueuedThreads();
    }

    /**
     * Queries whether any threads are waiting on the given condition
     * associated with this lock. Note that because timeouts and
     * interrupts may occur at any time, a {@code true} return does
     * not guarantee that a future {@code signal} will awaken any
     * threads.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * <p>
     *  查询任何线程是否正在等待与此锁相关联的给定条件。注意,因为超时和中断可能在任何时候发生,{@code true}返回不保证未来的{@code signal}将唤醒任何线程。
     * 该方法主要用于监视系统状态。
     * 
     * 
     * @param condition the condition
     * @return {@code true} if there are any waiting threads
     * @throws IllegalMonitorStateException if this lock is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this lock
     * @throws NullPointerException if the condition is null
     */
    public boolean hasWaiters(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    /**
     * Returns an estimate of the number of threads waiting on the
     * given condition associated with this lock. Note that because
     * timeouts and interrupts may occur at any time, the estimate
     * serves only as an upper bound on the actual number of waiters.
     * This method is designed for use in monitoring of the system
     * state, not for synchronization control.
     *
     * <p>
     * 返回等待与此锁相关联的给定条件的线程数的估计。注意,因为超时和中断可以在任何时间发生,所以估计仅用作实际数量的服务者的上限。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @param condition the condition
     * @return the estimated number of waiting threads
     * @throws IllegalMonitorStateException if this lock is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this lock
     * @throws NullPointerException if the condition is null
     */
    public int getWaitQueueLength(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    /**
     * Returns a collection containing those threads that may be
     * waiting on the given condition associated with this lock.
     * Because the actual set of threads may change dynamically while
     * constructing this result, the returned collection is only a
     * best-effort estimate. The elements of the returned collection
     * are in no particular order.  This method is designed to
     * facilitate construction of subclasses that provide more
     * extensive condition monitoring facilities.
     *
     * <p>
     *  返回包含可能在与此锁相关联的给定条件下等待的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
     * 该方法被设计为便于构建提供更广泛的状态监测设施的子类。
     * 
     * 
     * @param condition the condition
     * @return the collection of threads
     * @throws IllegalMonitorStateException if this lock is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this lock
     * @throws NullPointerException if the condition is null
     */
    protected Collection<Thread> getWaitingThreads(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    /**
     * Returns a string identifying this lock, as well as its lock state.
     * The state, in brackets, includes either the String {@code "Unlocked"}
     * or the String {@code "Locked by"} followed by the
     * {@linkplain Thread#getName name} of the owning thread.
     *
     * <p>
     *  返回标识此锁的字符串及其锁状态。
     * 括号中的状态包括字符串{@code"Unlocked"}或String {@code"Locked by"},后跟拥有线程的{@linkplain Thread#getName name}。
     * 
     * @return a string identifying this lock, as well as its lock state
     */
    public String toString() {
        Thread o = sync.getOwner();
        return super.toString() + ((o == null) ?
                                   "[Unlocked]" :
                                   "[Locked by thread " + o.getName() + "]");
    }
}
