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

/**
 * A {@code ReadWriteLock} maintains a pair of associated {@link
 * Lock locks}, one for read-only operations and one for writing.
 * The {@link #readLock read lock} may be held simultaneously by
 * multiple reader threads, so long as there are no writers.  The
 * {@link #writeLock write lock} is exclusive.
 *
 * <p>All {@code ReadWriteLock} implementations must guarantee that
 * the memory synchronization effects of {@code writeLock} operations
 * (as specified in the {@link Lock} interface) also hold with respect
 * to the associated {@code readLock}. That is, a thread successfully
 * acquiring the read lock will see all updates made upon previous
 * release of the write lock.
 *
 * <p>A read-write lock allows for a greater level of concurrency in
 * accessing shared data than that permitted by a mutual exclusion lock.
 * It exploits the fact that while only a single thread at a time (a
 * <em>writer</em> thread) can modify the shared data, in many cases any
 * number of threads can concurrently read the data (hence <em>reader</em>
 * threads).
 * In theory, the increase in concurrency permitted by the use of a read-write
 * lock will lead to performance improvements over the use of a mutual
 * exclusion lock. In practice this increase in concurrency will only be fully
 * realized on a multi-processor, and then only if the access patterns for
 * the shared data are suitable.
 *
 * <p>Whether or not a read-write lock will improve performance over the use
 * of a mutual exclusion lock depends on the frequency that the data is
 * read compared to being modified, the duration of the read and write
 * operations, and the contention for the data - that is, the number of
 * threads that will try to read or write the data at the same time.
 * For example, a collection that is initially populated with data and
 * thereafter infrequently modified, while being frequently searched
 * (such as a directory of some kind) is an ideal candidate for the use of
 * a read-write lock. However, if updates become frequent then the data
 * spends most of its time being exclusively locked and there is little, if any
 * increase in concurrency. Further, if the read operations are too short
 * the overhead of the read-write lock implementation (which is inherently
 * more complex than a mutual exclusion lock) can dominate the execution
 * cost, particularly as many read-write lock implementations still serialize
 * all threads through a small section of code. Ultimately, only profiling
 * and measurement will establish whether the use of a read-write lock is
 * suitable for your application.
 *
 *
 * <p>Although the basic operation of a read-write lock is straight-forward,
 * there are many policy decisions that an implementation must make, which
 * may affect the effectiveness of the read-write lock in a given application.
 * Examples of these policies include:
 * <ul>
 * <li>Determining whether to grant the read lock or the write lock, when
 * both readers and writers are waiting, at the time that a writer releases
 * the write lock. Writer preference is common, as writes are expected to be
 * short and infrequent. Reader preference is less common as it can lead to
 * lengthy delays for a write if the readers are frequent and long-lived as
 * expected. Fair, or &quot;in-order&quot; implementations are also possible.
 *
 * <li>Determining whether readers that request the read lock while a
 * reader is active and a writer is waiting, are granted the read lock.
 * Preference to the reader can delay the writer indefinitely, while
 * preference to the writer can reduce the potential for concurrency.
 *
 * <li>Determining whether the locks are reentrant: can a thread with the
 * write lock reacquire it? Can it acquire a read lock while holding the
 * write lock? Is the read lock itself reentrant?
 *
 * <li>Can the write lock be downgraded to a read lock without allowing
 * an intervening writer? Can a read lock be upgraded to a write lock,
 * in preference to other waiting readers or writers?
 *
 * </ul>
 * You should consider all of these things when evaluating the suitability
 * of a given implementation for your application.
 *
 * <p>
 *  {@code ReadWriteLock}维护着一对关联的{@link锁定},一个用于只读操作,一个用于写入。
 *  {@link #readLock读取锁定}可以由多个读取器线程同时保持,只要没有写入器。 {@link #writeLock写锁定}是独占的。
 * 
 *  <p>所有{@code ReadWriteLock}实现必须保证{@code writeLock}操作(如{@link Lock}接口中指定的)的内存同步效果对于相关联的{@code readLock}
 * 也成立。
 * 也就是说,成功获取读取锁的线程将看到在写入锁的先前发布时做出的所有更新。
 * 
 * <p>读写锁定允许在访问共享数据时比由互斥锁所允许的更高级别的并发性。
 * 它利用这样的事实,即虽然一次只有一个线程(<em> writer </em>线程)可以修改共享数据,但在许多情况下,任何数量的线程可以同时读取数据(因此< / em> threads)。
 * 在理论上,通过使用读写锁定允许的并发性的增加将导致在使用互斥锁定方面的性能改进。实际上,并发性的这种增加将仅在多处理器上完全实现,并且然后只有在用于共享数据的访问模式合适时才完全实现。
 * 
 * <p>读写锁定是否会提高使用互斥锁定的性能取决于数据读取的频率与被修改的频率,读取和写入操作的持续时间,以及数据 - 即将尝试同时读取或写入数据的线程数。
 * 例如,最初被填充数据并且此后很少被修改,而被频繁搜索(例如某种类型的目录)的集合是使用读写锁定的理想候选。然而,如果更新变得频繁,则数据花费大部分时间被独占锁定,并且并发性很少(如果有的话)。
 * 此外,如果读操作太短,则读写锁实现(其本质上比互斥锁更复杂)的开销可以支配执行成本,特别是因为许多读写锁定实现仍然通过小段代码。最终,只有分析和测量才能确定使用读写锁是否适合您的应用程序。
 * 
 *  <p>虽然读写锁的基本操作是直接的,但实现必须进行许多策略决策,这可能影响给定应用程序中读写锁定的有效性。这些政策的示例包括：
 * <ul>
 * <li>在写入器释放写入锁定时,确定读取器和写入器是否均等待读取锁定或写入锁定。写入器首选项是常见的,因为写入预计短且不频繁。
 * 
 * @see ReentrantReadWriteLock
 * @see Lock
 * @see ReentrantLock
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface ReadWriteLock {
    /**
     * Returns the lock used for reading.
     *
     * <p>
     * 读者偏好不太常见,因为如果读者频繁和长期如预期的那样,它可能导致写入的长时间延迟。公平或"有序"实现也是可能的。
     * 
     *  <li>确定在读取器处于活动状态并且写入器正在等待时请求读取锁定的读取器被授予读取锁定。偏好读者可以无限期地延迟写入者,而偏好写者可以减少并发的可能性。
     * 
     *  <li>确定锁是否可重入：具有写锁的线程是否可以重新获取?它可以在持有写锁定时获取读锁定吗?读锁本身是否可重入?
     * 
     *  <li>写锁定可以降级为读锁定,而不允许介入编写器?读锁是否可以升级到写锁,优先于其他等待的读写器或写入器?
     * 
     * </ul>
     * 
     * @return the lock used for reading
     */
    Lock readLock();

    /**
     * Returns the lock used for writing.
     *
     * <p>
     *  在评估给定实现对于应用程序的适用性时,应该考虑所有这些。
     * 
     * 
     * @return the lock used for writing
     */
    Lock writeLock();
}
