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

/**
 * Utility classes commonly useful in concurrent programming.  This
 * package includes a few small standardized extensible frameworks, as
 * well as some classes that provide useful functionality and are
 * otherwise tedious or difficult to implement.  Here are brief
 * descriptions of the main components.  See also the
 * {@link java.util.concurrent.locks} and
 * {@link java.util.concurrent.atomic} packages.
 *
 * <h2>Executors</h2>
 *
 * <b>Interfaces.</b>
 *
 * {@link java.util.concurrent.Executor} is a simple standardized
 * interface for defining custom thread-like subsystems, including
 * thread pools, asynchronous I/O, and lightweight task frameworks.
 * Depending on which concrete Executor class is being used, tasks may
 * execute in a newly created thread, an existing task-execution thread,
 * or the thread calling {@link java.util.concurrent.Executor#execute
 * execute}, and may execute sequentially or concurrently.
 *
 * {@link java.util.concurrent.ExecutorService} provides a more
 * complete asynchronous task execution framework.  An
 * ExecutorService manages queuing and scheduling of tasks,
 * and allows controlled shutdown.
 *
 * The {@link java.util.concurrent.ScheduledExecutorService}
 * subinterface and associated interfaces add support for
 * delayed and periodic task execution.  ExecutorServices
 * provide methods arranging asynchronous execution of any
 * function expressed as {@link java.util.concurrent.Callable},
 * the result-bearing analog of {@link java.lang.Runnable}.
 *
 * A {@link java.util.concurrent.Future} returns the results of
 * a function, allows determination of whether execution has
 * completed, and provides a means to cancel execution.
 *
 * A {@link java.util.concurrent.RunnableFuture} is a {@code Future}
 * that possesses a {@code run} method that upon execution,
 * sets its results.
 *
 * <p>
 *
 * <b>Implementations.</b>
 *
 * Classes {@link java.util.concurrent.ThreadPoolExecutor} and
 * {@link java.util.concurrent.ScheduledThreadPoolExecutor}
 * provide tunable, flexible thread pools.
 *
 * The {@link java.util.concurrent.Executors} class provides
 * factory methods for the most common kinds and configurations
 * of Executors, as well as a few utility methods for using
 * them.  Other utilities based on {@code Executors} include the
 * concrete class {@link java.util.concurrent.FutureTask}
 * providing a common extensible implementation of Futures, and
 * {@link java.util.concurrent.ExecutorCompletionService}, that
 * assists in coordinating the processing of groups of
 * asynchronous tasks.
 *
 * <p>Class {@link java.util.concurrent.ForkJoinPool} provides an
 * Executor primarily designed for processing instances of {@link
 * java.util.concurrent.ForkJoinTask} and its subclasses.  These
 * classes employ a work-stealing scheduler that attains high
 * throughput for tasks conforming to restrictions that often hold in
 * computation-intensive parallel processing.
 *
 * <h2>Queues</h2>
 *
 * The {@link java.util.concurrent.ConcurrentLinkedQueue} class
 * supplies an efficient scalable thread-safe non-blocking FIFO queue.
 * The {@link java.util.concurrent.ConcurrentLinkedDeque} class is
 * similar, but additionally supports the {@link java.util.Deque}
 * interface.
 *
 * <p>Five implementations in {@code java.util.concurrent} support
 * the extended {@link java.util.concurrent.BlockingQueue}
 * interface, that defines blocking versions of put and take:
 * {@link java.util.concurrent.LinkedBlockingQueue},
 * {@link java.util.concurrent.ArrayBlockingQueue},
 * {@link java.util.concurrent.SynchronousQueue},
 * {@link java.util.concurrent.PriorityBlockingQueue}, and
 * {@link java.util.concurrent.DelayQueue}.
 * The different classes cover the most common usage contexts
 * for producer-consumer, messaging, parallel tasking, and
 * related concurrent designs.
 *
 * <p>Extended interface {@link java.util.concurrent.TransferQueue},
 * and implementation {@link java.util.concurrent.LinkedTransferQueue}
 * introduce a synchronous {@code transfer} method (along with related
 * features) in which a producer may optionally block awaiting its
 * consumer.
 *
 * <p>The {@link java.util.concurrent.BlockingDeque} interface
 * extends {@code BlockingQueue} to support both FIFO and LIFO
 * (stack-based) operations.
 * Class {@link java.util.concurrent.LinkedBlockingDeque}
 * provides an implementation.
 *
 * <h2>Timing</h2>
 *
 * The {@link java.util.concurrent.TimeUnit} class provides
 * multiple granularities (including nanoseconds) for
 * specifying and controlling time-out based operations.  Most
 * classes in the package contain operations based on time-outs
 * in addition to indefinite waits.  In all cases that
 * time-outs are used, the time-out specifies the minimum time
 * that the method should wait before indicating that it
 * timed-out.  Implementations make a &quot;best effort&quot;
 * to detect time-outs as soon as possible after they occur.
 * However, an indefinite amount of time may elapse between a
 * time-out being detected and a thread actually executing
 * again after that time-out.  All methods that accept timeout
 * parameters treat values less than or equal to zero to mean
 * not to wait at all.  To wait "forever", you can use a value
 * of {@code Long.MAX_VALUE}.
 *
 * <h2>Synchronizers</h2>
 *
 * Five classes aid common special-purpose synchronization idioms.
 * <ul>
 *
 * <li>{@link java.util.concurrent.Semaphore} is a classic concurrency tool.
 *
 * <li>{@link java.util.concurrent.CountDownLatch} is a very simple yet
 * very common utility for blocking until a given number of signals,
 * events, or conditions hold.
 *
 * <li>A {@link java.util.concurrent.CyclicBarrier} is a resettable
 * multiway synchronization point useful in some styles of parallel
 * programming.
 *
 * <li>A {@link java.util.concurrent.Phaser} provides
 * a more flexible form of barrier that may be used to control phased
 * computation among multiple threads.
 *
 * <li>An {@link java.util.concurrent.Exchanger} allows two threads to
 * exchange objects at a rendezvous point, and is useful in several
 * pipeline designs.
 *
 * </ul>
 *
 * <h2>Concurrent Collections</h2>
 *
 * Besides Queues, this package supplies Collection implementations
 * designed for use in multithreaded contexts:
 * {@link java.util.concurrent.ConcurrentHashMap},
 * {@link java.util.concurrent.ConcurrentSkipListMap},
 * {@link java.util.concurrent.ConcurrentSkipListSet},
 * {@link java.util.concurrent.CopyOnWriteArrayList}, and
 * {@link java.util.concurrent.CopyOnWriteArraySet}.
 * When many threads are expected to access a given collection, a
 * {@code ConcurrentHashMap} is normally preferable to a synchronized
 * {@code HashMap}, and a {@code ConcurrentSkipListMap} is normally
 * preferable to a synchronized {@code TreeMap}.
 * A {@code CopyOnWriteArrayList} is preferable to a synchronized
 * {@code ArrayList} when the expected number of reads and traversals
 * greatly outnumber the number of updates to a list.
 *
 * <p>The "Concurrent" prefix used with some classes in this package
 * is a shorthand indicating several differences from similar
 * "synchronized" classes.  For example {@code java.util.Hashtable} and
 * {@code Collections.synchronizedMap(new HashMap())} are
 * synchronized.  But {@link
 * java.util.concurrent.ConcurrentHashMap} is "concurrent".  A
 * concurrent collection is thread-safe, but not governed by a
 * single exclusion lock.  In the particular case of
 * ConcurrentHashMap, it safely permits any number of
 * concurrent reads as well as a tunable number of concurrent
 * writes.  "Synchronized" classes can be useful when you need
 * to prevent all access to a collection via a single lock, at
 * the expense of poorer scalability.  In other cases in which
 * multiple threads are expected to access a common collection,
 * "concurrent" versions are normally preferable.  And
 * unsynchronized collections are preferable when either
 * collections are unshared, or are accessible only when
 * holding other locks.
 *
 * <p id="Weakly">Most concurrent Collection implementations
 * (including most Queues) also differ from the usual {@code java.util}
 * conventions in that their {@linkplain java.util.Iterator Iterators}
 * and {@linkplain java.util.Spliterator Spliterators} provide
 * <em>weakly consistent</em> rather than fast-fail traversal:
 * <ul>
 * <li>they may proceed concurrently with other operations
 * <li>they will never throw {@link java.util.ConcurrentModificationException
 * ConcurrentModificationException}
 * <li>they are guaranteed to traverse elements as they existed upon
 * construction exactly once, and may (but are not guaranteed to)
 * reflect any modifications subsequent to construction.
 * </ul>
 *
 * <h2 id="MemoryVisibility">Memory Consistency Properties</h2>
 *
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4.5">
 * Chapter 17 of the Java Language Specification</a> defines the
 * <i>happens-before</i> relation on memory operations such as reads and
 * writes of shared variables.  The results of a write by one thread are
 * guaranteed to be visible to a read by another thread only if the write
 * operation <i>happens-before</i> the read operation.  The
 * {@code synchronized} and {@code volatile} constructs, as well as the
 * {@code Thread.start()} and {@code Thread.join()} methods, can form
 * <i>happens-before</i> relationships.  In particular:
 *
 * <ul>
 *   <li>Each action in a thread <i>happens-before</i> every action in that
 *   thread that comes later in the program's order.
 *
 *   <li>An unlock ({@code synchronized} block or method exit) of a
 *   monitor <i>happens-before</i> every subsequent lock ({@code synchronized}
 *   block or method entry) of that same monitor.  And because
 *   the <i>happens-before</i> relation is transitive, all actions
 *   of a thread prior to unlocking <i>happen-before</i> all actions
 *   subsequent to any thread locking that monitor.
 *
 *   <li>A write to a {@code volatile} field <i>happens-before</i> every
 *   subsequent read of that same field.  Writes and reads of
 *   {@code volatile} fields have similar memory consistency effects
 *   as entering and exiting monitors, but do <em>not</em> entail
 *   mutual exclusion locking.
 *
 *   <li>A call to {@code start} on a thread <i>happens-before</i> any
 *   action in the started thread.
 *
 *   <li>All actions in a thread <i>happen-before</i> any other thread
 *   successfully returns from a {@code join} on that thread.
 *
 * </ul>
 *
 *
 * The methods of all classes in {@code java.util.concurrent} and its
 * subpackages extend these guarantees to higher-level
 * synchronization.  In particular:
 *
 * <ul>
 *
 *   <li>Actions in a thread prior to placing an object into any concurrent
 *   collection <i>happen-before</i> actions subsequent to the access or
 *   removal of that element from the collection in another thread.
 *
 *   <li>Actions in a thread prior to the submission of a {@code Runnable}
 *   to an {@code Executor} <i>happen-before</i> its execution begins.
 *   Similarly for {@code Callables} submitted to an {@code ExecutorService}.
 *
 *   <li>Actions taken by the asynchronous computation represented by a
 *   {@code Future} <i>happen-before</i> actions subsequent to the
 *   retrieval of the result via {@code Future.get()} in another thread.
 *
 *   <li>Actions prior to "releasing" synchronizer methods such as
 *   {@code Lock.unlock}, {@code Semaphore.release}, and
 *   {@code CountDownLatch.countDown} <i>happen-before</i> actions
 *   subsequent to a successful "acquiring" method such as
 *   {@code Lock.lock}, {@code Semaphore.acquire},
 *   {@code Condition.await}, and {@code CountDownLatch.await} on the
 *   same synchronizer object in another thread.
 *
 *   <li>For each pair of threads that successfully exchange objects via
 *   an {@code Exchanger}, actions prior to the {@code exchange()}
 *   in each thread <i>happen-before</i> those subsequent to the
 *   corresponding {@code exchange()} in another thread.
 *
 *   <li>Actions prior to calling {@code CyclicBarrier.await} and
 *   {@code Phaser.awaitAdvance} (as well as its variants)
 *   <i>happen-before</i> actions performed by the barrier action, and
 *   actions performed by the barrier action <i>happen-before</i> actions
 *   subsequent to a successful return from the corresponding {@code await}
 *   in other threads.
 *
 * </ul>
 *
 * <p>
 *  实用程序类通常用于并发编程。这个包包括几个小的标准化可扩展框架,以及一些提供有用功能的类,否则乏味或难以实现。以下是主要组件的简要说明。
 * 另请参阅{@link java.util.concurrent.locks}和{@link java.util.concurrent.atomic}软件包。
 * 
 *  <h2>执行者</h2>
 * 
 *  <b>接口。</b>
 * 
 *  {@link java.util.concurrent.Executor}是一个简单的标准化接口,用于定义自定义线程子系统,包括线程池,异步I / O和轻量级任务框架。
 * 根据使用哪个具体的Executor类,任务可以在新创建的线程,现有的任务执行线程或调用{@link java.util.concurrent.Executor#execute execute}的线程中执
 * 行,并且可以顺序执行或同时。
 *  {@link java.util.concurrent.Executor}是一个简单的标准化接口,用于定义自定义线程子系统,包括线程池,异步I / O和轻量级任务框架。
 * 
 *  {@link java.util.concurrent.ExecutorService}提供了一个更完整的异步任务执行框架。 ExecutorService管理任务的排队和调度,并允许受控关闭。
 * 
 * {@link java.util.concurrent.ScheduledExecutorService}子接口和相关联的接口添加了对延迟和周期性任务执行的支持。
 *  ExecutorServices提供了安排异步执行任何表示为{@link java.util.concurrent.Callable}的函数的方法,这是{@link java.lang.Runnable}
 * 的结果类比。
 * {@link java.util.concurrent.ScheduledExecutorService}子接口和相关联的接口添加了对延迟和周期性任务执行的支持。
 * 
 *  {@link java.util.concurrent.Future}返回函数的结果,允许确定执行是否已完成,并提供取消执行的方法。
 * 
 *  {@link java.util.concurrent.RunnableFuture}是一个{@code Future},拥有一个{@code run}方法,在执行时,设置其结果。
 * 
 * <p>
 * 
 *  <b>实施。</b>
 * 
 *  类{@link java.util.concurrent.ThreadPoolExecutor}和{@link java.util.concurrent.ScheduledThreadPoolExecutor}
 * 提供可调,灵活的线程池。
 * 
 *  {@link java.util.concurrent.Executors}类为最常见的Executors类型和配置提供了工厂方法,以及一些使用它们的实用程序方法。
 * 基于{@code Executors}的其他实用程序包括提供Futures的常见可扩展实现的具体类{@link java.util.concurrent.FutureTask}和{@link java.util.concurrent.ExecutorCompletionService}
 * ,它有助于协调处理异步任务组。
 *  {@link java.util.concurrent.Executors}类为最常见的Executors类型和配置提供了工厂方法,以及一些使用它们的实用程序方法。
 * 
 * <p>类{@link java.util.concurrent.ForkJoinPool}提供了一个主要用于处理{@link java.util.concurrent.ForkJoinTask}及其子类
 * 的实例的Executor。
 * 这些类使用工作窃取调度器,其对于符合通常在计算密集型并行处理中保持的限制的任务获得高吞吐量。
 * 
 *  <h2>队列</h2>
 * 
 *  {@link java.util.concurrent.ConcurrentLinkedQueue}类提供了一个高效的可扩展线程安全无阻塞FIFO队列。
 *  {@link java.util.concurrent.ConcurrentLinkedDeque}类是类似的,但是还支持{@link java.util.Deque}接口。
 * 
 *  <p> {@code java.util.concurrent}中的五个实现支持扩展的{@link java.util.concurrent.BlockingQueue}接口,它定义了put和take
 * 的阻塞版本：{@link java.util.concurrent.LinkedBlockingQueue },{@link java.util.concurrent.ArrayBlockingQueue}
 * ,{@link java.util.concurrent.SynchronousQueue},{@link java.util.concurrent.PriorityBlockingQueue}和{@link java.util.concurrent.DelayQueue}
 *  。
 * 不同的类涵盖了生产者 - 消费者,消息,并行任务和相关并发设计的最常见的使用上下文。
 * 
 *  <p>扩展界面{@link java.util.concurrent.TransferQueue}和实现{@link java.util.concurrent.LinkedTransferQueue}
 * 引入了一个同步{@code transfer}方法(以及相关功能),其中生产者可以可选地阻止等待其消费者。
 * 
 * <p> {@link java.util.concurrent.BlockingDeque}接口扩展{@code BlockingQueue}以支持FIFO和LIFO(基于堆栈)操作。
 * 类{@link java.util.concurrent.LinkedBlockingDeque}提供了一个实现。
 * 
 *  <h2>计时</h2>
 * 
 *  {@link java.util.concurrent.TimeUnit}类提供了用于指定和控制基于超时的操作的多个粒度(包括纳秒)。包中的大多数类包含除了不确定等待之外基于超时的操作。
 * 在使用超时的所有情况下,超时指定方法在指示其超时之前应该等待的最小时间。实现做出"最大努力"以在它们发生之后尽快检测到超时。
 * 然而,在检测到的超时和在该超时之后实际再次执行的线程之间可能经过不确定的时间量。所有接受超时参数的方法都会将小于或等于零的值视为不等待。
 * 要等待"永远",您可以使用{@code Long.MAX_VALUE}的值。
 * 
 *  <h2>同步器</h2>
 * 
 *  五个类支持常见的专用同步习语。
 * <ul>
 * 
 *  <li> {@ link java.util.concurrent.Semaphore}是一种经典的并发工具。
 * 
 *  <li> {@ link java.util.concurrent.CountDownLatch}是一个非常简单但非常常见的实用程序,用于阻塞,直到给定数量的信号,事件或条件成立。
 * 
 * <li> {@link java.util.concurrent.CyclicBarrier}是一个可重置的多路同步点,在某些并行编程风格中很有用。
 * 
 *  <li> {@link java.util.concurrent.Phaser}提供了一种更灵活的屏障形式,可用于控制多个线程之间的相位计算。
 * 
 * @since 1.5
 */
package java.util.concurrent;
