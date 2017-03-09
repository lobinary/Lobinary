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
 * An implementation of {@link ReadWriteLock} supporting similar
 * semantics to {@link ReentrantLock}.
 * <p>This class has the following properties:
 *
 * <ul>
 * <li><b>Acquisition order</b>
 *
 * <p>This class does not impose a reader or writer preference
 * ordering for lock access.  However, it does support an optional
 * <em>fairness</em> policy.
 *
 * <dl>
 * <dt><b><i>Non-fair mode (default)</i></b>
 * <dd>When constructed as non-fair (the default), the order of entry
 * to the read and write lock is unspecified, subject to reentrancy
 * constraints.  A nonfair lock that is continuously contended may
 * indefinitely postpone one or more reader or writer threads, but
 * will normally have higher throughput than a fair lock.
 *
 * <dt><b><i>Fair mode</i></b>
 * <dd>When constructed as fair, threads contend for entry using an
 * approximately arrival-order policy. When the currently held lock
 * is released, either the longest-waiting single writer thread will
 * be assigned the write lock, or if there is a group of reader threads
 * waiting longer than all waiting writer threads, that group will be
 * assigned the read lock.
 *
 * <p>A thread that tries to acquire a fair read lock (non-reentrantly)
 * will block if either the write lock is held, or there is a waiting
 * writer thread. The thread will not acquire the read lock until
 * after the oldest currently waiting writer thread has acquired and
 * released the write lock. Of course, if a waiting writer abandons
 * its wait, leaving one or more reader threads as the longest waiters
 * in the queue with the write lock free, then those readers will be
 * assigned the read lock.
 *
 * <p>A thread that tries to acquire a fair write lock (non-reentrantly)
 * will block unless both the read lock and write lock are free (which
 * implies there are no waiting threads).  (Note that the non-blocking
 * {@link ReadLock#tryLock()} and {@link WriteLock#tryLock()} methods
 * do not honor this fair setting and will immediately acquire the lock
 * if it is possible, regardless of waiting threads.)
 * <p>
 * </dl>
 *
 * <li><b>Reentrancy</b>
 *
 * <p>This lock allows both readers and writers to reacquire read or
 * write locks in the style of a {@link ReentrantLock}. Non-reentrant
 * readers are not allowed until all write locks held by the writing
 * thread have been released.
 *
 * <p>Additionally, a writer can acquire the read lock, but not
 * vice-versa.  Among other applications, reentrancy can be useful
 * when write locks are held during calls or callbacks to methods that
 * perform reads under read locks.  If a reader tries to acquire the
 * write lock it will never succeed.
 *
 * <li><b>Lock downgrading</b>
 * <p>Reentrancy also allows downgrading from the write lock to a read lock,
 * by acquiring the write lock, then the read lock and then releasing the
 * write lock. However, upgrading from a read lock to the write lock is
 * <b>not</b> possible.
 *
 * <li><b>Interruption of lock acquisition</b>
 * <p>The read lock and write lock both support interruption during lock
 * acquisition.
 *
 * <li><b>{@link Condition} support</b>
 * <p>The write lock provides a {@link Condition} implementation that
 * behaves in the same way, with respect to the write lock, as the
 * {@link Condition} implementation provided by
 * {@link ReentrantLock#newCondition} does for {@link ReentrantLock}.
 * This {@link Condition} can, of course, only be used with the write lock.
 *
 * <p>The read lock does not support a {@link Condition} and
 * {@code readLock().newCondition()} throws
 * {@code UnsupportedOperationException}.
 *
 * <li><b>Instrumentation</b>
 * <p>This class supports methods to determine whether locks
 * are held or contended. These methods are designed for monitoring
 * system state, not for synchronization control.
 * </ul>
 *
 * <p>Serialization of this class behaves in the same way as built-in
 * locks: a deserialized lock is in the unlocked state, regardless of
 * its state when serialized.
 *
 * <p><b>Sample usages</b>. Here is a code sketch showing how to perform
 * lock downgrading after updating a cache (exception handling is
 * particularly tricky when handling multiple locks in a non-nested
 * fashion):
 *
 * <pre> {@code
 * class CachedData {
 *   Object data;
 *   volatile boolean cacheValid;
 *   final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
 *
 *   void processCachedData() {
 *     rwl.readLock().lock();
 *     if (!cacheValid) {
 *       // Must release read lock before acquiring write lock
 *       rwl.readLock().unlock();
 *       rwl.writeLock().lock();
 *       try {
 *         // Recheck state because another thread might have
 *         // acquired write lock and changed state before we did.
 *         if (!cacheValid) {
 *           data = ...
 *           cacheValid = true;
 *         }
 *         // Downgrade by acquiring read lock before releasing write lock
 *         rwl.readLock().lock();
 *       } finally {
 *         rwl.writeLock().unlock(); // Unlock write, still hold read
 *       }
 *     }
 *
 *     try {
 *       use(data);
 *     } finally {
 *       rwl.readLock().unlock();
 *     }
 *   }
 * }}</pre>
 *
 * ReentrantReadWriteLocks can be used to improve concurrency in some
 * uses of some kinds of Collections. This is typically worthwhile
 * only when the collections are expected to be large, accessed by
 * more reader threads than writer threads, and entail operations with
 * overhead that outweighs synchronization overhead. For example, here
 * is a class using a TreeMap that is expected to be large and
 * concurrently accessed.
 *
 *  <pre> {@code
 * class RWDictionary {
 *   private final Map<String, Data> m = new TreeMap<String, Data>();
 *   private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
 *   private final Lock r = rwl.readLock();
 *   private final Lock w = rwl.writeLock();
 *
 *   public Data get(String key) {
 *     r.lock();
 *     try { return m.get(key); }
 *     finally { r.unlock(); }
 *   }
 *   public String[] allKeys() {
 *     r.lock();
 *     try { return m.keySet().toArray(); }
 *     finally { r.unlock(); }
 *   }
 *   public Data put(String key, Data value) {
 *     w.lock();
 *     try { return m.put(key, value); }
 *     finally { w.unlock(); }
 *   }
 *   public void clear() {
 *     w.lock();
 *     try { m.clear(); }
 *     finally { w.unlock(); }
 *   }
 * }}</pre>
 *
 * <h3>Implementation Notes</h3>
 *
 * <p>This lock supports a maximum of 65535 recursive write locks
 * and 65535 read locks. Attempts to exceed these limits result in
 * {@link Error} throws from locking methods.
 *
 * <p>
 *  {@link ReadWriteLock}的实现支持与{@link ReentrantLock}类似的语义。 <p>此类具有以下属性：
 * 
 * <ul>
 *  <li> <b>获取订单</b>
 * 
 *  <p>此类别不强制读取者或写入者偏好顺序锁定访问。但是,它支持可选的公平性</em>政策。
 * 
 * <dl>
 *  <dt> <b> <i>非公平模式(默认)</i> </b> <dd>当构造为非公平(默认)时,读写锁的顺序未指定,受到重入限制。
 * 持续竞争的非锁定可以无限期地推迟一个或多个读取器或写入器线程,但通常将具有比公平锁定更高的吞吐量。
 * 
 *  <dt> <b> <i>公平模式</i> </b> <dd>当构建为公平时,线程使用大致到达顺序策略来争用条目。
 * 当当前持有的锁被释放时,最长等待的单个写入程序线程将被分配写锁定,或者如果有一组读取程序线程等待的时间比所有等待的写程序线程长,则该组将被分配读锁定。
 * 
 * <p>尝试获取公平读取锁(不可重入)的线程将阻塞,如果写锁定被保持,或有一个等待写线程。线程将不会获取读锁,直到最老的当前等待的写线程已经获取并释放写锁定。
 * 当然,如果等待的写入器放弃等待,留下一个或多个读取器线程作为队列中的最长等待者,并且写锁定是空闲的,则那些读取器将被分配读取锁定。
 * 
 *  <p>尝试获取公平写锁定(不可重写)的线程将阻塞,除非读锁和写锁都是空闲的(这意味着没有等待的线程)。
 *  (请注意,非阻塞{@link ReadLock#tryLock()}和{@link WriteLock#tryLock()}方法不遵守此公平设置,如果可能,将立即获取锁,而不管等待的线程。
 * <p>
 * </dl>
 * 
 *  <li> <b>重入</b>
 * 
 *  <p>此锁定允许读者和作者以{@link ReentrantLock}的风格重新获取读取或写入锁定。在写线程持有的所有写锁都被释放之前,不允许非可重入读写器。
 * 
 *  <p>此外,写入器可以获取读取锁,但反之亦然。在其他应用程序中,如果在调用期间保持写锁定或在执行读锁定下执行读取的方法的回调时,可重入性会很有用。如果读取器尝试获取写锁定,它将永远不会成功。
 * 
 * <li> <b>锁定降级</b> <p>重入还允许通过获取写锁定,然后读取锁定,然后释放写锁定,从写锁降级为读锁。但是,从读取锁升级到写入锁是<b>不</b>可能。
 * 
 *  <li> <b>锁获取中断</b> <p>读锁和写锁都支持在锁获取期间中断。
 * 
 *  <li> <b> {@ link Condition}支持</b> <p>写锁定提供了一个{@link Condition}实现,其行为方式与写锁定相同,如{@link Condition } {@link ReentrantLock#newCondition}
 * 提供的实现适用于{@link ReentrantLock}。
 * 当然,{@link Condition}只能用于写锁定。
 * 
 *  <p>读锁不支持{@link Condition}和{@code readLock()。
 * newCondition()} throws {@code UnsupportedOperationException}。
 * 
 *  <li> <b>测试</b> <p>此类支持确定锁是否被持有或被争用的方法。这些方法被设计用于监视系统状态,而不是用于同步控制。
 * </ul>
 * 
 *  <p>此类的序列化与内置锁的行为相同：反序列化锁处于解锁状态,无论序列化时的状态如何。
 * 
 *  <p> <b>示例用法</b>。这里是一个代码草图,显示了如何在更新缓存后执行锁定降级(当以非嵌套方式处理多个锁定时,异常处理特别棘手)：
 * 
 * <pre> {@code class CachedData {Object data; volatile boolean cacheValid; final reentrantReadWriteLock rwl = new ReentrantReadWriteLock();。
 * 
 *  void processCachedData(){rwl.readLock()。lock(); if(！cacheValid){//获取写锁前必须释放读锁rwl.readLock()。
 * unlock(); rwl.writeLock()。lock(); try {//重新检查状态,因为另一个线程可能有//获取写锁定和更改状态,我们做之前。
 *  if(！cacheValid){data = ... cacheValid = true; } //通过在释放写锁之前获取读锁降级rwl.readLock()。
 * lock(); } finally {rwl.writeLock()。unlock(); //解锁写入,仍然保持读取}}。
 * 
 *  try {use(data); } finally {rwl.readLock()。unlock(); }}}} </pre>
 * 
 *  ReentrantReadWriteLocks可以用来提高某些类型的集合的一些使用中的并发性。
 * 这通常是值得的,只有当预期集合是大的,由更多的读取器线程访问,而不是写线程,并且需要超过同步开销的开销的操作。例如,这里是一个使用TreeMap的类,它期望大并且被并发访问。
 * 
 *  <pre> {@code class RWDictionary {private final Map <String,Data> m = new TreeMap <String,Data>(); private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); private final Lock r = rwl.readLock(); private final Lock w = rwl.writeLock();。
 * 
 * public Data get(String key){r.lock(); try {return m.get(key); } finally {r.unlock(); }} public String
 *  [] allKeys(){r.lock(); try {return m.keySet()。
 * toArray(); } finally {r.unlock(); }} public Data put(String key,Data value){w.lock(); try {return m.put(key,value); }
 *  finally {w.unlock(); }} public void clear(){w.lock(); try {m.clear(); } finally {w.unlock(); }}}} </pre>
 * 。
 * 
 *  <h3>实施注意事项</h3>
 * 
 *  <p>此锁最多支持65535个递归写锁和65535个读锁。尝试超过这些限制会导致{@link Error}抛出锁定方法。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public class ReentrantReadWriteLock
        implements ReadWriteLock, java.io.Serializable {
    private static final long serialVersionUID = -6992448646407690164L;
    /** Inner class providing readlock */
    private final ReentrantReadWriteLock.ReadLock readerLock;
    /** Inner class providing writelock */
    private final ReentrantReadWriteLock.WriteLock writerLock;
    /** Performs all synchronization mechanics */
    final Sync sync;

    /**
     * Creates a new {@code ReentrantReadWriteLock} with
     * default (nonfair) ordering properties.
     * <p>
     *  使用默认(非正式)排序属性创建新的{@code ReentrantReadWriteLock}。
     * 
     */
    public ReentrantReadWriteLock() {
        this(false);
    }

    /**
     * Creates a new {@code ReentrantReadWriteLock} with
     * the given fairness policy.
     *
     * <p>
     *  根据给定的公平策略创建新的{@code ReentrantReadWriteLock}。
     * 
     * 
     * @param fair {@code true} if this lock should use a fair ordering policy
     */
    public ReentrantReadWriteLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
    }

    public ReentrantReadWriteLock.WriteLock writeLock() { return writerLock; }
    public ReentrantReadWriteLock.ReadLock  readLock()  { return readerLock; }

    /**
     * Synchronization implementation for ReentrantReadWriteLock.
     * Subclassed into fair and nonfair versions.
     * <p>
     *  ReentrantReadWriteLock的同步实现。细分为公平和非商业版本。
     * 
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 6317671515068378041L;

        /*
         * Read vs write count extraction constants and functions.
         * Lock state is logically divided into two unsigned shorts:
         * The lower one representing the exclusive (writer) lock hold count,
         * and the upper the shared (reader) hold count.
         * <p>
         *  读取vs写入计数提取常数和函数。锁定状态在逻辑上分为两个无符号短路：下面的一个代表独占(写)器锁定保持计数,而上部是共享(读取器)保持计数。
         * 
         */

        static final int SHARED_SHIFT   = 16;
        static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
        static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
        static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

        /** Returns the number of shared holds represented in count  */
        static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
        /** Returns the number of exclusive holds represented in count  */
        static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

        /**
         * A counter for per-thread read hold counts.
         * Maintained as a ThreadLocal; cached in cachedHoldCounter
         * <p>
         *  用于每线程读取保持计数的计数器。保持为ThreadLocal;缓存在cachedHoldCounter
         * 
         */
        static final class HoldCounter {
            int count = 0;
            // Use id, not reference, to avoid garbage retention
            final long tid = getThreadId(Thread.currentThread());
        }

        /**
         * ThreadLocal subclass. Easiest to explicitly define for sake
         * of deserialization mechanics.
         * <p>
         *  ThreadLocal子类。最简单明确定义为反序列化力学。
         * 
         */
        static final class ThreadLocalHoldCounter
            extends ThreadLocal<HoldCounter> {
            public HoldCounter initialValue() {
                return new HoldCounter();
            }
        }

        /**
         * The number of reentrant read locks held by current thread.
         * Initialized only in constructor and readObject.
         * Removed whenever a thread's read hold count drops to 0.
         * <p>
         *  当前线程持有的可重入读锁的数量。仅在构造函数和readObject中初始化。在线程的读取保持计数下降到0时移除。
         * 
         */
        private transient ThreadLocalHoldCounter readHolds;

        /**
         * The hold count of the last thread to successfully acquire
         * readLock. This saves ThreadLocal lookup in the common case
         * where the next thread to release is the last one to
         * acquire. This is non-volatile since it is just used
         * as a heuristic, and would be great for threads to cache.
         *
         * <p>Can outlive the Thread for which it is caching the read
         * hold count, but avoids garbage retention by not retaining a
         * reference to the Thread.
         *
         * <p>Accessed via a benign data race; relies on the memory
         * model's final field and out-of-thin-air guarantees.
         * <p>
         * 成功获取readLock的最后一个线程的保持计数。这会在常见的情况下保存ThreadLocal查找,下一个要释放的线程是最后一个要获取的线程。
         * 这是非易失性的,因为它只是用作启发式的,并且对于线程缓存是很好的。
         * 
         *  <p>可以延长线程,它正在缓存读取保持计数,但通过不保留对线程的引用来避免垃圾回收。
         * 
         *  <p>通过良性数据竞赛访问;依赖于内存模型的最终字段和超薄空气保证。
         * 
         */
        private transient HoldCounter cachedHoldCounter;

        /**
         * firstReader is the first thread to have acquired the read lock.
         * firstReaderHoldCount is firstReader's hold count.
         *
         * <p>More precisely, firstReader is the unique thread that last
         * changed the shared count from 0 to 1, and has not released the
         * read lock since then; null if there is no such thread.
         *
         * <p>Cannot cause garbage retention unless the thread terminated
         * without relinquishing its read locks, since tryReleaseShared
         * sets it to null.
         *
         * <p>Accessed via a benign data race; relies on the memory
         * model's out-of-thin-air guarantees for references.
         *
         * <p>This allows tracking of read holds for uncontended read
         * locks to be very cheap.
         * <p>
         *  firstReader是获取读取锁的第一个线程。 firstReaderHoldCount是firstReader的保持计数。
         * 
         *  <p>更精确地说,firstReader是最后一次将共享计数从0更改为1,并且自那以后未释放读锁定的唯一线程; null如果没有这样的线程。
         * 
         *  <p>除非线程终止而不放弃其读取锁,否则不能导致垃圾保留,因为tryReleaseShared将其设置为null。
         * 
         *  <p>通过良性数据竞赛访问;依赖于内存模型对参考的超薄空气保证。
         * 
         *  <p>这允许跟踪非保留读锁的读保持是非常便宜的。
         * 
         */
        private transient Thread firstReader = null;
        private transient int firstReaderHoldCount;

        Sync() {
            readHolds = new ThreadLocalHoldCounter();
            setState(getState()); // ensures visibility of readHolds
        }

        /*
         * Acquires and releases use the same code for fair and
         * nonfair locks, but differ in whether/how they allow barging
         * when queues are non-empty.
         * <p>
         *  获取和释放使用相同的代码公平和非公平锁,但不同是否/如何允许驳船时队列非空。
         * 
         */

        /**
         * Returns true if the current thread, when trying to acquire
         * the read lock, and otherwise eligible to do so, should block
         * because of policy for overtaking other waiting threads.
         * <p>
         *  返回true如果当前线程,当试图获取读取锁,否则有资格这样做,应该阻止,因为超越其他等待线程的策略。
         * 
         */
        abstract boolean readerShouldBlock();

        /**
         * Returns true if the current thread, when trying to acquire
         * the write lock, and otherwise eligible to do so, should block
         * because of policy for overtaking other waiting threads.
         * <p>
         * 返回true,如果当前线程,当试图获取写锁定,否则有资格这样做,应该阻止,因为超越其他等待线程的策略。
         * 
         */
        abstract boolean writerShouldBlock();

        /*
         * Note that tryRelease and tryAcquire can be called by
         * Conditions. So it is possible that their arguments contain
         * both read and write holds that are all released during a
         * condition wait and re-established in tryAcquire.
         * <p>
         *  注意tryRelease和tryAcquire可以被条件调用。因此,它们的参数可能包含读取和写入保持,这些都在条件等待期间释放,并在tryAcquire中重新建立。
         * 
         */

        protected final boolean tryRelease(int releases) {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int nextc = getState() - releases;
            boolean free = exclusiveCount(nextc) == 0;
            if (free)
                setExclusiveOwnerThread(null);
            setState(nextc);
            return free;
        }

        protected final boolean tryAcquire(int acquires) {
            /*
             * Walkthrough:
             * 1. If read count nonzero or write count nonzero
             *    and owner is a different thread, fail.
             * 2. If count would saturate, fail. (This can only
             *    happen if count is already nonzero.)
             * 3. Otherwise, this thread is eligible for lock if
             *    it is either a reentrant acquire or
             *    queue policy allows it. If so, update state
             *    and set owner.
             * <p>
             *  演练：1.如果读取计数非零或写入计数非零,并且所有者是不同的线程,则失败。如果计数饱和,失败。 (这只能发生在计数已经非零。)3.否则,如果它是可重入捕获或队列策略允许,此线程有资格锁定。
             * 如果是,更新状态并设置所有者。
             * 
             */
            Thread current = Thread.currentThread();
            int c = getState();
            int w = exclusiveCount(c);
            if (c != 0) {
                // (Note: if c != 0 and w == 0 then shared count != 0)
                if (w == 0 || current != getExclusiveOwnerThread())
                    return false;
                if (w + exclusiveCount(acquires) > MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
                // Reentrant acquire
                setState(c + acquires);
                return true;
            }
            if (writerShouldBlock() ||
                !compareAndSetState(c, c + acquires))
                return false;
            setExclusiveOwnerThread(current);
            return true;
        }

        protected final boolean tryReleaseShared(int unused) {
            Thread current = Thread.currentThread();
            if (firstReader == current) {
                // assert firstReaderHoldCount > 0;
                if (firstReaderHoldCount == 1)
                    firstReader = null;
                else
                    firstReaderHoldCount--;
            } else {
                HoldCounter rh = cachedHoldCounter;
                if (rh == null || rh.tid != getThreadId(current))
                    rh = readHolds.get();
                int count = rh.count;
                if (count <= 1) {
                    readHolds.remove();
                    if (count <= 0)
                        throw unmatchedUnlockException();
                }
                --rh.count;
            }
            for (;;) {
                int c = getState();
                int nextc = c - SHARED_UNIT;
                if (compareAndSetState(c, nextc))
                    // Releasing the read lock has no effect on readers,
                    // but it may allow waiting writers to proceed if
                    // both read and write locks are now free.
                    return nextc == 0;
            }
        }

        private IllegalMonitorStateException unmatchedUnlockException() {
            return new IllegalMonitorStateException(
                "attempt to unlock read lock, not locked by current thread");
        }

        protected final int tryAcquireShared(int unused) {
            /*
             * Walkthrough:
             * 1. If write lock held by another thread, fail.
             * 2. Otherwise, this thread is eligible for
             *    lock wrt state, so ask if it should block
             *    because of queue policy. If not, try
             *    to grant by CASing state and updating count.
             *    Note that step does not check for reentrant
             *    acquires, which is postponed to full version
             *    to avoid having to check hold count in
             *    the more typical non-reentrant case.
             * 3. If step 2 fails either because thread
             *    apparently not eligible or CAS fails or count
             *    saturated, chain to version with full retry loop.
             * <p>
             *  演练：1.如果写锁由另一个线程持有,失败。 2.否则,此线程适合锁定wrt状态,因此请询问是否应该由于队列策略而阻止。如果没有,尝试通过CASing状态和更新计数授予。
             * 注意,步骤不检查可重入获取,其被推迟到完整版本,以避免在更典型的不可重入的情况下检查保持计数。 3.如果步骤2失败,或者因为线程显然不合格,或者CAS失败或计数饱和,则使用完全重试循环链接到版本。
             * 
             */
            Thread current = Thread.currentThread();
            int c = getState();
            if (exclusiveCount(c) != 0 &&
                getExclusiveOwnerThread() != current)
                return -1;
            int r = sharedCount(c);
            if (!readerShouldBlock() &&
                r < MAX_COUNT &&
                compareAndSetState(c, c + SHARED_UNIT)) {
                if (r == 0) {
                    firstReader = current;
                    firstReaderHoldCount = 1;
                } else if (firstReader == current) {
                    firstReaderHoldCount++;
                } else {
                    HoldCounter rh = cachedHoldCounter;
                    if (rh == null || rh.tid != getThreadId(current))
                        cachedHoldCounter = rh = readHolds.get();
                    else if (rh.count == 0)
                        readHolds.set(rh);
                    rh.count++;
                }
                return 1;
            }
            return fullTryAcquireShared(current);
        }

        /**
         * Full version of acquire for reads, that handles CAS misses
         * and reentrant reads not dealt with in tryAcquireShared.
         * <p>
         *  获取读取的完整版本,用于处理在tryAcquireShared中未处理的CAS未命中和可重入读取。
         * 
         */
        final int fullTryAcquireShared(Thread current) {
            /*
             * This code is in part redundant with that in
             * tryAcquireShared but is simpler overall by not
             * complicating tryAcquireShared with interactions between
             * retries and lazily reading hold counts.
             * <p>
             * 这个代码在tryAcquireShared中是部分冗余的,但是通过不使tryAcquireShared与重试和延迟读取保持计数之间的交互复杂化而更加简单。
             * 
             */
            HoldCounter rh = null;
            for (;;) {
                int c = getState();
                if (exclusiveCount(c) != 0) {
                    if (getExclusiveOwnerThread() != current)
                        return -1;
                    // else we hold the exclusive lock; blocking here
                    // would cause deadlock.
                } else if (readerShouldBlock()) {
                    // Make sure we're not acquiring read lock reentrantly
                    if (firstReader == current) {
                        // assert firstReaderHoldCount > 0;
                    } else {
                        if (rh == null) {
                            rh = cachedHoldCounter;
                            if (rh == null || rh.tid != getThreadId(current)) {
                                rh = readHolds.get();
                                if (rh.count == 0)
                                    readHolds.remove();
                            }
                        }
                        if (rh.count == 0)
                            return -1;
                    }
                }
                if (sharedCount(c) == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
                if (compareAndSetState(c, c + SHARED_UNIT)) {
                    if (sharedCount(c) == 0) {
                        firstReader = current;
                        firstReaderHoldCount = 1;
                    } else if (firstReader == current) {
                        firstReaderHoldCount++;
                    } else {
                        if (rh == null)
                            rh = cachedHoldCounter;
                        if (rh == null || rh.tid != getThreadId(current))
                            rh = readHolds.get();
                        else if (rh.count == 0)
                            readHolds.set(rh);
                        rh.count++;
                        cachedHoldCounter = rh; // cache for release
                    }
                    return 1;
                }
            }
        }

        /**
         * Performs tryLock for write, enabling barging in both modes.
         * This is identical in effect to tryAcquire except for lack
         * of calls to writerShouldBlock.
         * <p>
         *  执行tryLock写入,在两种模式下启用驳接。这与tryAcquire的效果相同,除了对writerShouldBlock的调用不足。
         * 
         */
        final boolean tryWriteLock() {
            Thread current = Thread.currentThread();
            int c = getState();
            if (c != 0) {
                int w = exclusiveCount(c);
                if (w == 0 || current != getExclusiveOwnerThread())
                    return false;
                if (w == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
            }
            if (!compareAndSetState(c, c + 1))
                return false;
            setExclusiveOwnerThread(current);
            return true;
        }

        /**
         * Performs tryLock for read, enabling barging in both modes.
         * This is identical in effect to tryAcquireShared except for
         * lack of calls to readerShouldBlock.
         * <p>
         *  执行tryLock读取,在两种模式下启用驳船。这与tryAcquireShared的效果相同,除了缺少对readerShouldBlock的调用。
         * 
         */
        final boolean tryReadLock() {
            Thread current = Thread.currentThread();
            for (;;) {
                int c = getState();
                if (exclusiveCount(c) != 0 &&
                    getExclusiveOwnerThread() != current)
                    return false;
                int r = sharedCount(c);
                if (r == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
                if (compareAndSetState(c, c + SHARED_UNIT)) {
                    if (r == 0) {
                        firstReader = current;
                        firstReaderHoldCount = 1;
                    } else if (firstReader == current) {
                        firstReaderHoldCount++;
                    } else {
                        HoldCounter rh = cachedHoldCounter;
                        if (rh == null || rh.tid != getThreadId(current))
                            cachedHoldCounter = rh = readHolds.get();
                        else if (rh.count == 0)
                            readHolds.set(rh);
                        rh.count++;
                    }
                    return true;
                }
            }
        }

        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        // Methods relayed to outer class

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        final Thread getOwner() {
            // Must read state before owner to ensure memory consistency
            return ((exclusiveCount(getState()) == 0) ?
                    null :
                    getExclusiveOwnerThread());
        }

        final int getReadLockCount() {
            return sharedCount(getState());
        }

        final boolean isWriteLocked() {
            return exclusiveCount(getState()) != 0;
        }

        final int getWriteHoldCount() {
            return isHeldExclusively() ? exclusiveCount(getState()) : 0;
        }

        final int getReadHoldCount() {
            if (getReadLockCount() == 0)
                return 0;

            Thread current = Thread.currentThread();
            if (firstReader == current)
                return firstReaderHoldCount;

            HoldCounter rh = cachedHoldCounter;
            if (rh != null && rh.tid == getThreadId(current))
                return rh.count;

            int count = readHolds.get().count;
            if (count == 0) readHolds.remove();
            return count;
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
            readHolds = new ThreadLocalHoldCounter();
            setState(0); // reset to unlocked state
        }

        final int getCount() { return getState(); }
    }

    /**
     * Nonfair version of Sync
     * <p>
     *  非法版本的同步
     * 
     */
    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = -8159625535654395037L;
        final boolean writerShouldBlock() {
            return false; // writers can always barge
        }
        final boolean readerShouldBlock() {
            /* As a heuristic to avoid indefinite writer starvation,
             * block if the thread that momentarily appears to be head
             * of queue, if one exists, is a waiting writer.  This is
             * only a probabilistic effect since a new reader will not
             * block if there is a waiting writer behind other enabled
             * readers that have not yet drained from the queue.
             * <p>
             *  阻塞如果暂时显示为队列头的线程(如果存在)是等待写入器。这只是一个概率效应,因为如果在其他已启用的读取器之后还有一个等待写入器尚未从队列中耗尽,新读取器将不会阻塞。
             * 
             */
            return apparentlyFirstQueuedIsExclusive();
        }
    }

    /**
     * Fair version of Sync
     * <p>
     *  公平版的同步
     * 
     */
    static final class FairSync extends Sync {
        private static final long serialVersionUID = -2274990926593161451L;
        final boolean writerShouldBlock() {
            return hasQueuedPredecessors();
        }
        final boolean readerShouldBlock() {
            return hasQueuedPredecessors();
        }
    }

    /**
     * The lock returned by method {@link ReentrantReadWriteLock#readLock}.
     * <p>
     *  由方法{@link ReentrantReadWriteLock#readLock}返回的锁。
     * 
     */
    public static class ReadLock implements Lock, java.io.Serializable {
        private static final long serialVersionUID = -5992448646407690164L;
        private final Sync sync;

        /**
         * Constructor for use by subclasses
         *
         * <p>
         *  子类使用的构造方法
         * 
         * 
         * @param lock the outer lock object
         * @throws NullPointerException if the lock is null
         */
        protected ReadLock(ReentrantReadWriteLock lock) {
            sync = lock.sync;
        }

        /**
         * Acquires the read lock.
         *
         * <p>Acquires the read lock if the write lock is not held by
         * another thread and returns immediately.
         *
         * <p>If the write lock is held by another thread then
         * the current thread becomes disabled for thread scheduling
         * purposes and lies dormant until the read lock has been acquired.
         * <p>
         *  获取读锁。
         * 
         *  <p>如果写锁没有被另一个线程占用并立即返回,则获取读锁。
         * 
         *  <p>如果写锁定由另一个线程保持,则当前线程变为禁用以用于线程调度目的,并且处于休眠状态直到获取读锁定。
         * 
         */
        public void lock() {
            sync.acquireShared(1);
        }

        /**
         * Acquires the read lock unless the current thread is
         * {@linkplain Thread#interrupt interrupted}.
         *
         * <p>Acquires the read lock if the write lock is not held
         * by another thread and returns immediately.
         *
         * <p>If the write lock is held by another thread then the
         * current thread becomes disabled for thread scheduling
         * purposes and lies dormant until one of two things happens:
         *
         * <ul>
         *
         * <li>The read lock is acquired by the current thread; or
         *
         * <li>Some other thread {@linkplain Thread#interrupt interrupts}
         * the current thread.
         *
         * </ul>
         *
         * <p>If the current thread:
         *
         * <ul>
         *
         * <li>has its interrupted status set on entry to this method; or
         *
         * <li>is {@linkplain Thread#interrupt interrupted} while
         * acquiring the read lock,
         *
         * </ul>
         *
         * then {@link InterruptedException} is thrown and the current
         * thread's interrupted status is cleared.
         *
         * <p>In this implementation, as this method is an explicit
         * interruption point, preference is given to responding to
         * the interrupt over normal or reentrant acquisition of the
         * lock.
         *
         * <p>
         *  获取读锁定,除非当前线程是{@linkplain Thread#interrupt interrupted}。
         * 
         *  <p>如果写锁没有被另一个线程占用并立即返回,则获取读锁。
         * 
         * <p>如果写锁定由另一个线程持有,那么当前线程变为禁用以用于线程调度目的,并处于休眠状态,直到发生以下两种情况之一：
         * 
         * <ul>
         * 
         *  <li>读取锁定由当前线程获取;要么
         * 
         *  <li>一些其他线程{@linkplain线程#中断中断}当前线程。
         * 
         * </ul>
         * 
         *  <p>如果当前线程：
         * 
         * <ul>
         * 
         *  <li>在进入此方法时设置了中断状态;要么
         * 
         *  <li>是{@linkplain线程#中断}获取读取锁时,
         * 
         * </ul>
         * 
         *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
         * 
         *  在该实现中,因为该方法是显式中断点,所以优选在正常或可重入获取锁时响应中断。
         * 
         * 
         * @throws InterruptedException if the current thread is interrupted
         */
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireSharedInterruptibly(1);
        }

        /**
         * Acquires the read lock only if the write lock is not held by
         * another thread at the time of invocation.
         *
         * <p>Acquires the read lock if the write lock is not held by
         * another thread and returns immediately with the value
         * {@code true}. Even when this lock has been set to use a
         * fair ordering policy, a call to {@code tryLock()}
         * <em>will</em> immediately acquire the read lock if it is
         * available, whether or not other threads are currently
         * waiting for the read lock.  This &quot;barging&quot; behavior
         * can be useful in certain circumstances, even though it
         * breaks fairness. If you want to honor the fairness setting
         * for this lock, then use {@link #tryLock(long, TimeUnit)
         * tryLock(0, TimeUnit.SECONDS) } which is almost equivalent
         * (it also detects interruption).
         *
         * <p>If the write lock is held by another thread then
         * this method will return immediately with the value
         * {@code false}.
         *
         * <p>
         *  仅当写锁定在调用时未被另一个线程占用时才获取读锁定。
         * 
         * <p>如果写锁定未被另一个线程占用,并立即返回值{@code true},则获取读锁定。
         * 即使此锁已设置为使用公平的排序策略,调用{@code tryLock()} <em>将立即获取读取锁(如果可用),无论其他线程是否正在等待为读锁。
         * 这种"驳船"行为在某些情况下可能是有用的,即使它破坏公平。
         * 如果您要遵守此锁定的公平设置,请使用{@link #tryLock(long,TimeUnit)tryLock(0,TimeUnit.SECONDS)},这几乎是等效的(它也检测到中断)。
         * 
         *  <p>如果写锁定由另一个线程持有,那么此方法将立即返回值{@code false}。
         * 
         * 
         * @return {@code true} if the read lock was acquired
         */
        public boolean tryLock() {
            return sync.tryReadLock();
        }

        /**
         * Acquires the read lock if the write lock is not held by
         * another thread within the given waiting time and the
         * current thread has not been {@linkplain Thread#interrupt
         * interrupted}.
         *
         * <p>Acquires the read lock if the write lock is not held by
         * another thread and returns immediately with the value
         * {@code true}. If this lock has been set to use a fair
         * ordering policy then an available lock <em>will not</em> be
         * acquired if any other threads are waiting for the
         * lock. This is in contrast to the {@link #tryLock()}
         * method. If you want a timed {@code tryLock} that does
         * permit barging on a fair lock then combine the timed and
         * un-timed forms together:
         *
         *  <pre> {@code
         * if (lock.tryLock() ||
         *     lock.tryLock(timeout, unit)) {
         *   ...
         * }}</pre>
         *
         * <p>If the write lock is held by another thread then the
         * current thread becomes disabled for thread scheduling
         * purposes and lies dormant until one of three things happens:
         *
         * <ul>
         *
         * <li>The read lock is acquired by the current thread; or
         *
         * <li>Some other thread {@linkplain Thread#interrupt interrupts}
         * the current thread; or
         *
         * <li>The specified waiting time elapses.
         *
         * </ul>
         *
         * <p>If the read lock is acquired then the value {@code true} is
         * returned.
         *
         * <p>If the current thread:
         *
         * <ul>
         *
         * <li>has its interrupted status set on entry to this method; or
         *
         * <li>is {@linkplain Thread#interrupt interrupted} while
         * acquiring the read lock,
         *
         * </ul> then {@link InterruptedException} is thrown and the
         * current thread's interrupted status is cleared.
         *
         * <p>If the specified waiting time elapses then the value
         * {@code false} is returned.  If the time is less than or
         * equal to zero, the method will not wait at all.
         *
         * <p>In this implementation, as this method is an explicit
         * interruption point, preference is given to responding to
         * the interrupt over normal or reentrant acquisition of the
         * lock, and over reporting the elapse of the waiting time.
         *
         * <p>
         *  如果在给定的等待时间内写锁没有被另一个线程占用,并且当前线程尚未{@linkplain线程#中断中断},则获取读锁。
         * 
         *  <p>如果写锁定未被另一个线程占用,并立即返回值{@code true},则获取读锁定。如果这个锁被设置为使用公平的排序策略,则如果任何其他线程正在等待锁,则不会获取可用的锁<em>。
         * 这与{@link #tryLock()}方法形成对比。如果你想要一个定时的{@code tryLock}允许在公平锁定上驳船,然后结合定时和非定时形式在一起：。
         * 
         *  <pre> {@code if(lock.tryLock()|| lock.tryLock(timeout,unit)){...}} </pre>
         * 
         * <p>如果写锁定由另一个线程持有,则当前线程变为禁用以用于线程调度目的,并且处于休眠状态,直到发生以下三种情况之一：
         * 
         * <ul>
         * 
         *  <li>读取锁定由当前线程获取;要么
         * 
         *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
         * 
         *  <li>经过指定的等待时间。
         * 
         * </ul>
         * 
         *  <p>如果获取了读取锁,则返回值{@code true}。
         * 
         *  <p>如果当前线程：
         * 
         * <ul>
         * 
         *  <li>在进入此方法时设置了中断状态;要么
         * 
         *  <li>是{@linkplain线程#中断}获取读取锁时,
         * 
         *  </ul>,然后{@link InterruptedException}被抛出,当前线程的中断状态被清除。
         * 
         *  <p>如果经过指定的等待时间,则返回值{@code false}。如果时间小于或等于零,则该方法将不会等待。
         * 
         *  <p>在该实现中,由于该方法是明确的中断点,所以优选在正常或可重入获取锁的情况下响应于中断,并且超过报告等待时间的流逝。
         * 
         * 
         * @param timeout the time to wait for the read lock
         * @param unit the time unit of the timeout argument
         * @return {@code true} if the read lock was acquired
         * @throws InterruptedException if the current thread is interrupted
         * @throws NullPointerException if the time unit is null
         */
        public boolean tryLock(long timeout, TimeUnit unit)
                throws InterruptedException {
            return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
        }

        /**
         * Attempts to release this lock.
         *
         * <p>If the number of readers is now zero then the lock
         * is made available for write lock attempts.
         * <p>
         *  尝试释放此锁定。
         * 
         *  <p>如果读取器的数量现在为零,则锁可用于写锁尝试。
         * 
         */
        public void unlock() {
            sync.releaseShared(1);
        }

        /**
         * Throws {@code UnsupportedOperationException} because
         * {@code ReadLocks} do not support conditions.
         *
         * <p>
         *  抛出{@code UnsupportedOperationException},因为{@code ReadLocks}不支持条件。
         * 
         * 
         * @throws UnsupportedOperationException always
         */
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }

        /**
         * Returns a string identifying this lock, as well as its lock state.
         * The state, in brackets, includes the String {@code "Read locks ="}
         * followed by the number of held read locks.
         *
         * <p>
         * 返回标识此锁的字符串及其锁状态。括号中的状态包括字符串{@code"Read locks ="},后跟保持的读取锁的数目。
         * 
         * 
         * @return a string identifying this lock, as well as its lock state
         */
        public String toString() {
            int r = sync.getReadLockCount();
            return super.toString() +
                "[Read locks = " + r + "]";
        }
    }

    /**
     * The lock returned by method {@link ReentrantReadWriteLock#writeLock}.
     * <p>
     *  由方法{@link ReentrantReadWriteLock#writeLock}返回的锁。
     * 
     */
    public static class WriteLock implements Lock, java.io.Serializable {
        private static final long serialVersionUID = -4992448646407690164L;
        private final Sync sync;

        /**
         * Constructor for use by subclasses
         *
         * <p>
         *  子类使用的构造方法
         * 
         * 
         * @param lock the outer lock object
         * @throws NullPointerException if the lock is null
         */
        protected WriteLock(ReentrantReadWriteLock lock) {
            sync = lock.sync;
        }

        /**
         * Acquires the write lock.
         *
         * <p>Acquires the write lock if neither the read nor write lock
         * are held by another thread
         * and returns immediately, setting the write lock hold count to
         * one.
         *
         * <p>If the current thread already holds the write lock then the
         * hold count is incremented by one and the method returns
         * immediately.
         *
         * <p>If the lock is held by another thread then the current
         * thread becomes disabled for thread scheduling purposes and
         * lies dormant until the write lock has been acquired, at which
         * time the write lock hold count is set to one.
         * <p>
         *  获取写锁定。
         * 
         *  <p>如果读取和写入锁定都未被另一个线程占用,并立即返回,将写入锁定保持计数设置为1,则获取写入锁定。
         * 
         *  <p>如果当前线程已经持有写锁定,则保持计数增加1,并且该方法立即返回。
         * 
         *  <p>如果锁由另一个线程保持,则当前线程变为禁用,以用于线程调度目的,并且在获取写锁定之前处于休眠状态,此时写锁定保持计数设置为1。
         * 
         */
        public void lock() {
            sync.acquire(1);
        }

        /**
         * Acquires the write lock unless the current thread is
         * {@linkplain Thread#interrupt interrupted}.
         *
         * <p>Acquires the write lock if neither the read nor write lock
         * are held by another thread
         * and returns immediately, setting the write lock hold count to
         * one.
         *
         * <p>If the current thread already holds this lock then the
         * hold count is incremented by one and the method returns
         * immediately.
         *
         * <p>If the lock is held by another thread then the current
         * thread becomes disabled for thread scheduling purposes and
         * lies dormant until one of two things happens:
         *
         * <ul>
         *
         * <li>The write lock is acquired by the current thread; or
         *
         * <li>Some other thread {@linkplain Thread#interrupt interrupts}
         * the current thread.
         *
         * </ul>
         *
         * <p>If the write lock is acquired by the current thread then the
         * lock hold count is set to one.
         *
         * <p>If the current thread:
         *
         * <ul>
         *
         * <li>has its interrupted status set on entry to this method;
         * or
         *
         * <li>is {@linkplain Thread#interrupt interrupted} while
         * acquiring the write lock,
         *
         * </ul>
         *
         * then {@link InterruptedException} is thrown and the current
         * thread's interrupted status is cleared.
         *
         * <p>In this implementation, as this method is an explicit
         * interruption point, preference is given to responding to
         * the interrupt over normal or reentrant acquisition of the
         * lock.
         *
         * <p>
         *  获取写锁定,除非当前线程为{@linkplain Thread#interrupt interrupted}。
         * 
         *  <p>如果读取和写入锁定都未被另一个线程占用,并立即返回,将写入锁定保持计数设置为1,则获取写入锁定。
         * 
         *  <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法立即返回。
         * 
         *  <p>如果锁由另一个线程持有,那么当前线程将变为禁用以进行线程调度,并处于休眠状态,直到发生以下两种情况之一：
         * 
         * <ul>
         * 
         *  <li>写锁定由当前线程获取;要么
         * 
         * <li>一些其他线程{@linkplain线程#中断中断}当前线程。
         * 
         * </ul>
         * 
         *  <p>如果写锁定由当前线程获取,则锁定保持计数设置为1。
         * 
         *  <p>如果当前线程：
         * 
         * <ul>
         * 
         *  <li>在进入此方法时设置了中断状态;要么
         * 
         *  <li>是{@linkplain线程#中断}获取写锁定时,
         * 
         * </ul>
         * 
         *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
         * 
         *  在该实现中,因为该方法是显式中断点,所以优选在正常或可重入获取锁时响应中断。
         * 
         * 
         * @throws InterruptedException if the current thread is interrupted
         */
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        /**
         * Acquires the write lock only if it is not held by another thread
         * at the time of invocation.
         *
         * <p>Acquires the write lock if neither the read nor write lock
         * are held by another thread
         * and returns immediately with the value {@code true},
         * setting the write lock hold count to one. Even when this lock has
         * been set to use a fair ordering policy, a call to
         * {@code tryLock()} <em>will</em> immediately acquire the
         * lock if it is available, whether or not other threads are
         * currently waiting for the write lock.  This &quot;barging&quot;
         * behavior can be useful in certain circumstances, even
         * though it breaks fairness. If you want to honor the
         * fairness setting for this lock, then use {@link
         * #tryLock(long, TimeUnit) tryLock(0, TimeUnit.SECONDS) }
         * which is almost equivalent (it also detects interruption).
         *
         * <p>If the current thread already holds this lock then the
         * hold count is incremented by one and the method returns
         * {@code true}.
         *
         * <p>If the lock is held by another thread then this method
         * will return immediately with the value {@code false}.
         *
         * <p>
         *  获取写锁定,只有在调用时它没有被另一个线程占用。
         * 
         *  <p>如果读取和写入锁定都未被另一个线程占用,则获取写入锁定,并立即返回值{@code true},将写入锁定保持计数设置为1。
         * 即使此锁已设置为使用公平的排序策略,调用{@code tryLock()} <em>将立即获取锁,如果可用,无论是否其他线程当前正在等待写锁。这种"驳船"行为在某些情况下可能是有用的,即使它破坏公平。
         * 如果您想要遵守此锁定的公平设置,请使用{@link #tryLock(long,TimeUnit)tryLock(0,TimeUnit.SECONDS)},这几乎是等效的(它也检测到中断)。
         * 
         * <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法返回{@code true}。
         * 
         *  <p>如果锁由另一个线程持有,那么此方法将立即返回值{@code false}。
         * 
         * 
         * @return {@code true} if the lock was free and was acquired
         * by the current thread, or the write lock was already held
         * by the current thread; and {@code false} otherwise.
         */
        public boolean tryLock( ) {
            return sync.tryWriteLock();
        }

        /**
         * Acquires the write lock if it is not held by another thread
         * within the given waiting time and the current thread has
         * not been {@linkplain Thread#interrupt interrupted}.
         *
         * <p>Acquires the write lock if neither the read nor write lock
         * are held by another thread
         * and returns immediately with the value {@code true},
         * setting the write lock hold count to one. If this lock has been
         * set to use a fair ordering policy then an available lock
         * <em>will not</em> be acquired if any other threads are
         * waiting for the write lock. This is in contrast to the {@link
         * #tryLock()} method. If you want a timed {@code tryLock}
         * that does permit barging on a fair lock then combine the
         * timed and un-timed forms together:
         *
         *  <pre> {@code
         * if (lock.tryLock() ||
         *     lock.tryLock(timeout, unit)) {
         *   ...
         * }}</pre>
         *
         * <p>If the current thread already holds this lock then the
         * hold count is incremented by one and the method returns
         * {@code true}.
         *
         * <p>If the lock is held by another thread then the current
         * thread becomes disabled for thread scheduling purposes and
         * lies dormant until one of three things happens:
         *
         * <ul>
         *
         * <li>The write lock is acquired by the current thread; or
         *
         * <li>Some other thread {@linkplain Thread#interrupt interrupts}
         * the current thread; or
         *
         * <li>The specified waiting time elapses
         *
         * </ul>
         *
         * <p>If the write lock is acquired then the value {@code true} is
         * returned and the write lock hold count is set to one.
         *
         * <p>If the current thread:
         *
         * <ul>
         *
         * <li>has its interrupted status set on entry to this method;
         * or
         *
         * <li>is {@linkplain Thread#interrupt interrupted} while
         * acquiring the write lock,
         *
         * </ul>
         *
         * then {@link InterruptedException} is thrown and the current
         * thread's interrupted status is cleared.
         *
         * <p>If the specified waiting time elapses then the value
         * {@code false} is returned.  If the time is less than or
         * equal to zero, the method will not wait at all.
         *
         * <p>In this implementation, as this method is an explicit
         * interruption point, preference is given to responding to
         * the interrupt over normal or reentrant acquisition of the
         * lock, and over reporting the elapse of the waiting time.
         *
         * <p>
         *  如果在给定的等待时间内没有被另一个线程占用,并且当前线程尚未{@linkplain线程#中断中断},则获取写锁定。
         * 
         *  <p>如果读取和写入锁定都未被另一个线程占用,则获取写入锁定,并立即返回值{@code true},将写入锁定保持计数设置为1。
         * 如果该锁被设置为使用公平的排序策略,则如果任何其他线程正在等待写锁定,则不会获取可用的锁</em>。这与{@link #tryLock()}方法形成对比。
         * 如果你想要一个定时的{@code tryLock}允许在公平锁定上驳船,然后结合定时和非定时形式在一起：。
         * 
         *  <pre> {@code if(lock.tryLock()|| lock.tryLock(timeout,unit)){...}} </pre>
         * 
         *  <p>如果当前线程已经持有此锁,则保持计数增加1,并且该方法返回{@code true}。
         * 
         *  <p>如果锁由另一个线程持有,那么当前线程将被禁用以进行线程调度,并处于休眠状态,直到发生以下三种情况之一：
         * 
         * <ul>
         * 
         *  <li>写锁定由当前线程获取;要么
         * 
         *  <li>一些其他线程{@linkplain线程#中断中断}当前线程;要么
         * 
         *  <li>经过指定的等待时间
         * 
         * </ul>
         * 
         * <p>如果获得写锁定,则返回值{@code true},写锁定保持计数设置为1。
         * 
         *  <p>如果当前线程：
         * 
         * <ul>
         * 
         *  <li>在进入此方法时设置了中断状态;要么
         * 
         *  <li>是{@linkplain线程#中断}获取写锁定时,
         * 
         * </ul>
         * 
         *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
         * 
         *  <p>如果经过指定的等待时间,则返回值{@code false}。如果时间小于或等于零,则该方法将不会等待。
         * 
         *  <p>在该实现中,由于该方法是明确的中断点,所以优选在正常或可重入获取锁的情况下响应于中断,并且超过报告等待时间的流逝。
         * 
         * 
         * @param timeout the time to wait for the write lock
         * @param unit the time unit of the timeout argument
         *
         * @return {@code true} if the lock was free and was acquired
         * by the current thread, or the write lock was already held by the
         * current thread; and {@code false} if the waiting time
         * elapsed before the lock could be acquired.
         *
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
         * <p>If the current thread is the holder of this lock then
         * the hold count is decremented. If the hold count is now
         * zero then the lock is released.  If the current thread is
         * not the holder of this lock then {@link
         * IllegalMonitorStateException} is thrown.
         *
         * <p>
         *  尝试释放此锁定。
         * 
         *  <p>如果当前线程是这个锁的持有者,则保持计数递减。如果保持计数现在为零,则锁定被释放。
         * 如果当前线程不是这个锁的持有者,那么会抛出{@link IllegalMonitorStateException}。
         * 
         * 
         * @throws IllegalMonitorStateException if the current thread does not
         * hold this lock
         */
        public void unlock() {
            sync.release(1);
        }

        /**
         * Returns a {@link Condition} instance for use with this
         * {@link Lock} instance.
         * <p>The returned {@link Condition} instance supports the same
         * usages as do the {@link Object} monitor methods ({@link
         * Object#wait() wait}, {@link Object#notify notify}, and {@link
         * Object#notifyAll notifyAll}) when used with the built-in
         * monitor lock.
         *
         * <ul>
         *
         * <li>If this write lock is not held when any {@link
         * Condition} method is called then an {@link
         * IllegalMonitorStateException} is thrown.  (Read locks are
         * held independently of write locks, so are not checked or
         * affected. However it is essentially always an error to
         * invoke a condition waiting method when the current thread
         * has also acquired read locks, since other threads that
         * could unblock it will not be able to acquire the write
         * lock.)
         *
         * <li>When the condition {@linkplain Condition#await() waiting}
         * methods are called the write lock is released and, before
         * they return, the write lock is reacquired and the lock hold
         * count restored to what it was when the method was called.
         *
         * <li>If a thread is {@linkplain Thread#interrupt interrupted} while
         * waiting then the wait will terminate, an {@link
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
         *  <p>返回的{@link Condition}实例支持与{@link Object}监视方法({@link Object#wait()wait},{@link Object#notify notify}
         * 和{@link Object#notifyAll notifyAll})当与内置的监视器锁一起使用时。
         *  返回与此{@link Lock}实例一起使用的{@link Condition}实例。
         * 
         * <ul>
         * 
         * <li>如果在调用任何{@link Condition}方法时未锁定此写锁定,则会抛出{@link IllegalMonitorStateException}。
         *  (读锁与写锁无关,因此不会被检查或影响,但是当当前线程也获取了读锁时,调用条件等待方法本质上总是一个错误,因为可以解锁它的其他线程将不会能够获得写锁定。
         * 
         *  <li>当调用条件{@linkplain Condition#await()waiting}方法时,写锁定被释放,并且在它们返回之前,重新获取写锁定,并且锁定保持计数恢复到调用方法时的状态。
         * 
         *  <li>如果在等待时线程{@linkplain线程#中断},则等待将终止,将抛出一个{@link InterruptedException},并且线程的中断状态将被清除。
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
         * Returns a string identifying this lock, as well as its lock
         * state.  The state, in brackets includes either the String
         * {@code "Unlocked"} or the String {@code "Locked by"}
         * followed by the {@linkplain Thread#getName name} of the owning thread.
         *
         * <p>
         *  返回标识此锁的字符串及其锁状态。
         * 括号中的状态包括字符串{@code"Unlocked"}或String {@code"Locked by"},后跟拥有线程的{@linkplain Thread#getName name}。
         * 
         * 
         * @return a string identifying this lock, as well as its lock state
         */
        public String toString() {
            Thread o = sync.getOwner();
            return super.toString() + ((o == null) ?
                                       "[Unlocked]" :
                                       "[Locked by thread " + o.getName() + "]");
        }

        /**
         * Queries if this write lock is held by the current thread.
         * Identical in effect to {@link
         * ReentrantReadWriteLock#isWriteLockedByCurrentThread}.
         *
         * <p>
         * 查询此写锁是否由当前线程持有。与{@link ReentrantReadWriteLock#isWriteLockedByCurrentThread}效果相同。
         * 
         * 
         * @return {@code true} if the current thread holds this lock and
         *         {@code false} otherwise
         * @since 1.6
         */
        public boolean isHeldByCurrentThread() {
            return sync.isHeldExclusively();
        }

        /**
         * Queries the number of holds on this write lock by the current
         * thread.  A thread has a hold on a lock for each lock action
         * that is not matched by an unlock action.  Identical in effect
         * to {@link ReentrantReadWriteLock#getWriteHoldCount}.
         *
         * <p>
         *  查询当前线程对此写锁定的保持数。线程对于每个锁定动作都有一个锁定的保持,它不与解锁动作匹配。
         * 与{@link ReentrantReadWriteLock#getWriteHoldCount}效果相同。
         * 
         * 
         * @return the number of holds on this lock by the current thread,
         *         or zero if this lock is not held by the current thread
         * @since 1.6
         */
        public int getHoldCount() {
            return sync.getWriteHoldCount();
        }
    }

    // Instrumentation and status

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
     * Returns the thread that currently owns the write lock, or
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
     *  返回当前拥有写锁定的线程,如果不拥有则返回{@code null}。当此方法由不是所有者的线程调用时,返回值反映当前锁状态的尽力而为近似值。
     * 例如,所有者可能会暂时{@code null},即使有线程试图获取锁,但尚未这样做。该方法被设计为便于构建提供更广泛的锁监视设施的子类。
     * 
     * 
     * @return the owner, or {@code null} if not owned
     */
    protected Thread getOwner() {
        return sync.getOwner();
    }

    /**
     * Queries the number of read locks held for this lock. This
     * method is designed for use in monitoring system state, not for
     * synchronization control.
     * <p>
     *  查询此锁所持有的读锁数。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return the number of read locks held
     */
    public int getReadLockCount() {
        return sync.getReadLockCount();
    }

    /**
     * Queries if the write lock is held by any thread. This method is
     * designed for use in monitoring system state, not for
     * synchronization control.
     *
     * <p>
     *  查询写锁是否由任何线程持有。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return {@code true} if any thread holds the write lock and
     *         {@code false} otherwise
     */
    public boolean isWriteLocked() {
        return sync.isWriteLocked();
    }

    /**
     * Queries if the write lock is held by the current thread.
     *
     * <p>
     *  查询写锁是否由当前线程持有。
     * 
     * 
     * @return {@code true} if the current thread holds the write lock and
     *         {@code false} otherwise
     */
    public boolean isWriteLockedByCurrentThread() {
        return sync.isHeldExclusively();
    }

    /**
     * Queries the number of reentrant write holds on this lock by the
     * current thread.  A writer thread has a hold on a lock for
     * each lock action that is not matched by an unlock action.
     *
     * <p>
     *  查询当前线程对此锁的可重入写保持数。写线程对于不与解锁动作匹配的每个锁定动作保持锁定。
     * 
     * 
     * @return the number of holds on the write lock by the current thread,
     *         or zero if the write lock is not held by the current thread
     */
    public int getWriteHoldCount() {
        return sync.getWriteHoldCount();
    }

    /**
     * Queries the number of reentrant read holds on this lock by the
     * current thread.  A reader thread has a hold on a lock for
     * each lock action that is not matched by an unlock action.
     *
     * <p>
     * 查询当前线程对此锁定的可重入读取保持数。读取器线程对于不与解锁动作匹配的每个锁定动作保持锁定。
     * 
     * 
     * @return the number of holds on the read lock by the current thread,
     *         or zero if the read lock is not held by the current thread
     * @since 1.6
     */
    public int getReadHoldCount() {
        return sync.getReadHoldCount();
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire the write lock.  Because the actual set of threads may
     * change dynamically while constructing this result, the returned
     * collection is only a best-effort estimate.  The elements of the
     * returned collection are in no particular order.  This method is
     * designed to facilitate construction of subclasses that provide
     * more extensive lock monitoring facilities.
     *
     * <p>
     *  返回包含可能正在等待获取写锁定的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
     * 该方法被设计为便于构建提供更广泛的锁监视设施的子类。
     * 
     * 
     * @return the collection of threads
     */
    protected Collection<Thread> getQueuedWriterThreads() {
        return sync.getExclusiveQueuedThreads();
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire the read lock.  Because the actual set of threads may
     * change dynamically while constructing this result, the returned
     * collection is only a best-effort estimate.  The elements of the
     * returned collection are in no particular order.  This method is
     * designed to facilitate construction of subclasses that provide
     * more extensive lock monitoring facilities.
     *
     * <p>
     *  返回包含可能正在等待获取读取锁的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
     * 该方法被设计为便于构建提供更广泛的锁监视设施的子类。
     * 
     * 
     * @return the collection of threads
     */
    protected Collection<Thread> getQueuedReaderThreads() {
        return sync.getSharedQueuedThreads();
    }

    /**
     * Queries whether any threads are waiting to acquire the read or
     * write lock. Note that because cancellations may occur at any
     * time, a {@code true} return does not guarantee that any other
     * thread will ever acquire a lock.  This method is designed
     * primarily for use in monitoring of the system state.
     *
     * <p>
     *  查询任何线程是否正在等待获取读取或写入锁定。注意,因为取消可能在任何时候发生,{@code true}返回不保证任何其他线程将获得锁。该方法主要用于监视系统状态。
     * 
     * 
     * @return {@code true} if there may be other threads waiting to
     *         acquire the lock
     */
    public final boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    /**
     * Queries whether the given thread is waiting to acquire either
     * the read or write lock. Note that because cancellations may
     * occur at any time, a {@code true} return does not guarantee
     * that this thread will ever acquire a lock.  This method is
     * designed primarily for use in monitoring of the system state.
     *
     * <p>
     * 查询给定线程是否正在等待获取读取或写入锁定。注意,因为取消可能在任何时候发生,{@code true}返回不保证此线程将获得锁。该方法主要用于监视系统状态。
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
     * Returns an estimate of the number of threads waiting to acquire
     * either the read or write lock.  The value is only an estimate
     * because the number of threads may change dynamically while this
     * method traverses internal data structures.  This method is
     * designed for use in monitoring of the system state, not for
     * synchronization control.
     *
     * <p>
     *  返回等待获取读取或写入锁定的线程数的估计值。该值只是一个估计值,因为线程的数量可能会在此方法遍历内部数据结构时动态更改。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return the estimated number of threads waiting for this lock
     */
    public final int getQueueLength() {
        return sync.getQueueLength();
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire either the read or write lock.  Because the actual set
     * of threads may change dynamically while constructing this
     * result, the returned collection is only a best-effort estimate.
     * The elements of the returned collection are in no particular
     * order.  This method is designed to facilitate construction of
     * subclasses that provide more extensive monitoring facilities.
     *
     * <p>
     *  返回包含可能正在等待获取读取或写入锁定的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
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
     * associated with the write lock. Note that because timeouts and
     * interrupts may occur at any time, a {@code true} return does
     * not guarantee that a future {@code signal} will awaken any
     * threads.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * <p>
     *  查询任何线程是否正在等待与写锁定相关联的给定条件。注意,因为超时和中断可能在任何时候发生,{@code true}返回不保证未来的{@code signal}将唤醒任何线程。
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
     * given condition associated with the write lock. Note that because
     * timeouts and interrupts may occur at any time, the estimate
     * serves only as an upper bound on the actual number of waiters.
     * This method is designed for use in monitoring of the system
     * state, not for synchronization control.
     *
     * <p>
     * 返回等待与写锁定相关联的给定条件的线程数的估计。注意,因为超时和中断可以在任何时间发生,所以估计仅用作实际数量的服务者的上限。此方法设计用于监视系统状态,而不是用于同步控制。
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
     * waiting on the given condition associated with the write lock.
     * Because the actual set of threads may change dynamically while
     * constructing this result, the returned collection is only a
     * best-effort estimate. The elements of the returned collection
     * are in no particular order.  This method is designed to
     * facilitate construction of subclasses that provide more
     * extensive condition monitoring facilities.
     *
     * <p>
     *  返回包含可能在与写锁定相关联的给定条件下等待的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
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
     * The state, in brackets, includes the String {@code "Write locks ="}
     * followed by the number of reentrantly held write locks, and the
     * String {@code "Read locks ="} followed by the number of held
     * read locks.
     *
     * <p>
     *  返回标识此锁的字符串及其锁状态。
     * 括号中的状态包括字符串{@code"Write locks ="},后跟可重复保持的写锁的数目,以及String {@code"Read locks ="},后跟保持的读锁的数目。
     * 
     * 
     * @return a string identifying this lock, as well as its lock state
     */
    public String toString() {
        int c = sync.getCount();
        int w = Sync.exclusiveCount(c);
        int r = Sync.sharedCount(c);

        return super.toString() +
            "[Write locks = " + w + ", Read locks = " + r + "]";
    }

    /**
     * Returns the thread id for the given thread.  We must access
     * this directly rather than via method Thread.getId() because
     * getId() is not final, and has been known to be overridden in
     * ways that do not preserve unique mappings.
     * <p>
     */
    static final long getThreadId(Thread thread) {
        return UNSAFE.getLongVolatile(thread, TID_OFFSET);
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long TID_OFFSET;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            TID_OFFSET = UNSAFE.objectFieldOffset
                (tk.getDeclaredField("tid"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
