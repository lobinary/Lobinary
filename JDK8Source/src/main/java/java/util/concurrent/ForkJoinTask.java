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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.reflect.Constructor;

/**
 * Abstract base class for tasks that run within a {@link ForkJoinPool}.
 * A {@code ForkJoinTask} is a thread-like entity that is much
 * lighter weight than a normal thread.  Huge numbers of tasks and
 * subtasks may be hosted by a small number of actual threads in a
 * ForkJoinPool, at the price of some usage limitations.
 *
 * <p>A "main" {@code ForkJoinTask} begins execution when it is
 * explicitly submitted to a {@link ForkJoinPool}, or, if not already
 * engaged in a ForkJoin computation, commenced in the {@link
 * ForkJoinPool#commonPool()} via {@link #fork}, {@link #invoke}, or
 * related methods.  Once started, it will usually in turn start other
 * subtasks.  As indicated by the name of this class, many programs
 * using {@code ForkJoinTask} employ only methods {@link #fork} and
 * {@link #join}, or derivatives such as {@link
 * #invokeAll(ForkJoinTask...) invokeAll}.  However, this class also
 * provides a number of other methods that can come into play in
 * advanced usages, as well as extension mechanics that allow support
 * of new forms of fork/join processing.
 *
 * <p>A {@code ForkJoinTask} is a lightweight form of {@link Future}.
 * The efficiency of {@code ForkJoinTask}s stems from a set of
 * restrictions (that are only partially statically enforceable)
 * reflecting their main use as computational tasks calculating pure
 * functions or operating on purely isolated objects.  The primary
 * coordination mechanisms are {@link #fork}, that arranges
 * asynchronous execution, and {@link #join}, that doesn't proceed
 * until the task's result has been computed.  Computations should
 * ideally avoid {@code synchronized} methods or blocks, and should
 * minimize other blocking synchronization apart from joining other
 * tasks or using synchronizers such as Phasers that are advertised to
 * cooperate with fork/join scheduling. Subdividable tasks should also
 * not perform blocking I/O, and should ideally access variables that
 * are completely independent of those accessed by other running
 * tasks. These guidelines are loosely enforced by not permitting
 * checked exceptions such as {@code IOExceptions} to be
 * thrown. However, computations may still encounter unchecked
 * exceptions, that are rethrown to callers attempting to join
 * them. These exceptions may additionally include {@link
 * RejectedExecutionException} stemming from internal resource
 * exhaustion, such as failure to allocate internal task
 * queues. Rethrown exceptions behave in the same way as regular
 * exceptions, but, when possible, contain stack traces (as displayed
 * for example using {@code ex.printStackTrace()}) of both the thread
 * that initiated the computation as well as the thread actually
 * encountering the exception; minimally only the latter.
 *
 * <p>It is possible to define and use ForkJoinTasks that may block,
 * but doing do requires three further considerations: (1) Completion
 * of few if any <em>other</em> tasks should be dependent on a task
 * that blocks on external synchronization or I/O. Event-style async
 * tasks that are never joined (for example, those subclassing {@link
 * CountedCompleter}) often fall into this category.  (2) To minimize
 * resource impact, tasks should be small; ideally performing only the
 * (possibly) blocking action. (3) Unless the {@link
 * ForkJoinPool.ManagedBlocker} API is used, or the number of possibly
 * blocked tasks is known to be less than the pool's {@link
 * ForkJoinPool#getParallelism} level, the pool cannot guarantee that
 * enough threads will be available to ensure progress or good
 * performance.
 *
 * <p>The primary method for awaiting completion and extracting
 * results of a task is {@link #join}, but there are several variants:
 * The {@link Future#get} methods support interruptible and/or timed
 * waits for completion and report results using {@code Future}
 * conventions. Method {@link #invoke} is semantically
 * equivalent to {@code fork(); join()} but always attempts to begin
 * execution in the current thread. The "<em>quiet</em>" forms of
 * these methods do not extract results or report exceptions. These
 * may be useful when a set of tasks are being executed, and you need
 * to delay processing of results or exceptions until all complete.
 * Method {@code invokeAll} (available in multiple versions)
 * performs the most common form of parallel invocation: forking a set
 * of tasks and joining them all.
 *
 * <p>In the most typical usages, a fork-join pair act like a call
 * (fork) and return (join) from a parallel recursive function. As is
 * the case with other forms of recursive calls, returns (joins)
 * should be performed innermost-first. For example, {@code a.fork();
 * b.fork(); b.join(); a.join();} is likely to be substantially more
 * efficient than joining {@code a} before {@code b}.
 *
 * <p>The execution status of tasks may be queried at several levels
 * of detail: {@link #isDone} is true if a task completed in any way
 * (including the case where a task was cancelled without executing);
 * {@link #isCompletedNormally} is true if a task completed without
 * cancellation or encountering an exception; {@link #isCancelled} is
 * true if the task was cancelled (in which case {@link #getException}
 * returns a {@link java.util.concurrent.CancellationException}); and
 * {@link #isCompletedAbnormally} is true if a task was either
 * cancelled or encountered an exception, in which case {@link
 * #getException} will return either the encountered exception or
 * {@link java.util.concurrent.CancellationException}.
 *
 * <p>The ForkJoinTask class is not usually directly subclassed.
 * Instead, you subclass one of the abstract classes that support a
 * particular style of fork/join processing, typically {@link
 * RecursiveAction} for most computations that do not return results,
 * {@link RecursiveTask} for those that do, and {@link
 * CountedCompleter} for those in which completed actions trigger
 * other actions.  Normally, a concrete ForkJoinTask subclass declares
 * fields comprising its parameters, established in a constructor, and
 * then defines a {@code compute} method that somehow uses the control
 * methods supplied by this base class.
 *
 * <p>Method {@link #join} and its variants are appropriate for use
 * only when completion dependencies are acyclic; that is, the
 * parallel computation can be described as a directed acyclic graph
 * (DAG). Otherwise, executions may encounter a form of deadlock as
 * tasks cyclically wait for each other.  However, this framework
 * supports other methods and techniques (for example the use of
 * {@link Phaser}, {@link #helpQuiesce}, and {@link #complete}) that
 * may be of use in constructing custom subclasses for problems that
 * are not statically structured as DAGs. To support such usages, a
 * ForkJoinTask may be atomically <em>tagged</em> with a {@code short}
 * value using {@link #setForkJoinTaskTag} or {@link
 * #compareAndSetForkJoinTaskTag} and checked using {@link
 * #getForkJoinTaskTag}. The ForkJoinTask implementation does not use
 * these {@code protected} methods or tags for any purpose, but they
 * may be of use in the construction of specialized subclasses.  For
 * example, parallel graph traversals can use the supplied methods to
 * avoid revisiting nodes/tasks that have already been processed.
 * (Method names for tagging are bulky in part to encourage definition
 * of methods that reflect their usage patterns.)
 *
 * <p>Most base support methods are {@code final}, to prevent
 * overriding of implementations that are intrinsically tied to the
 * underlying lightweight task scheduling framework.  Developers
 * creating new basic styles of fork/join processing should minimally
 * implement {@code protected} methods {@link #exec}, {@link
 * #setRawResult}, and {@link #getRawResult}, while also introducing
 * an abstract computational method that can be implemented in its
 * subclasses, possibly relying on other {@code protected} methods
 * provided by this class.
 *
 * <p>ForkJoinTasks should perform relatively small amounts of
 * computation. Large tasks should be split into smaller subtasks,
 * usually via recursive decomposition. As a very rough rule of thumb,
 * a task should perform more than 100 and less than 10000 basic
 * computational steps, and should avoid indefinite looping. If tasks
 * are too big, then parallelism cannot improve throughput. If too
 * small, then memory and internal task maintenance overhead may
 * overwhelm processing.
 *
 * <p>This class provides {@code adapt} methods for {@link Runnable}
 * and {@link Callable}, that may be of use when mixing execution of
 * {@code ForkJoinTasks} with other kinds of tasks. When all tasks are
 * of this form, consider using a pool constructed in <em>asyncMode</em>.
 *
 * <p>ForkJoinTasks are {@code Serializable}, which enables them to be
 * used in extensions such as remote execution frameworks. It is
 * sensible to serialize tasks only before or after, but not during,
 * execution. Serialization is not relied on during execution itself.
 *
 * <p>
 *  在{@link ForkJoinPool}中运行的任务的抽象基类。 {@code ForkJoinTask}是一个线程实体,比普通线程轻得多。
 * 巨大数量的任务和子任务可以由ForkJoinPool中的少量实际线程托管,以一些使用限制的代价。
 * 
 *  <p>"main"{@code ForkJoinTask}在显式提交到{@link ForkJoinPool}时开始执行,或者如果尚未从事ForkJoin计算,则在{@link ForkJoinPool#commonPool()}
 * 中开始执行,通过{@link #fork},{@link #invoke}或相关方法。
 * 一旦开始,它通常会开始其他子任务。
 * 如这个类的名称所示,许多使用{@code ForkJoinTask}的程序只使用方法{@link #fork}和{@link #join},或者衍生物,例如{@link #invokeAll(ForkJoinTask ...)invokeAll }
 * 。
 * 一旦开始,它通常会开始其他子任务。然而,这个类还提供了许多其他方法,可以在高级用法,以及允许支持新的形式的fork / join处理的扩展力学。
 * 
 * <p> {@code ForkJoinTask}是一种轻量级的{@link Future}。
 *  {@code ForkJoinTask}的效率来自一组限制(只能部分静态强制执行),反映了它们作为计算任务的主要用途,用于计算纯函数或对纯粹隔离的对象进行操作。
 * 主协调机制是{@link #fork}(用于安排异步执行)和{@link #join},在计算任务的结果之前不会继续。
 * 理想情况下,计算应避免{@code synchronized}方法或块,并且除了加入其他任务或使用同步器(如通告与fork / join调度协同工作的Phasers)之外,应该最小化其他阻塞同步。
 * 可细分的任务也不应该执行阻塞I / O,并且应该理想地访问与其他正在运行的任务所访问的完全独立的变量。通过不允许引发{@code IOExceptions}等检查异常,这些准则被松散地强制执行。
 * 然而,计算仍然可能遇到未检查的异常,这些异常被重新引用到尝试加入它们的调用者。
 * 这些异常可能另外包括源于内部资源耗尽的{@link RejectedExecutionException},例如无法分配内部任务队列。
 *  Rethrown异常以与常规异常相同的方式运行,但是,如果可能,包含启动计算的线程以及线程实际遇到的堆栈跟踪(例如使用{@code ex.printStackTrace()}显示)异常;最低限度只有后
 * 者。
 * 这些异常可能另外包括源于内部资源耗尽的{@link RejectedExecutionException},例如无法分配内部任务队列。
 * 
 * <p>可以定义和使用可能阻塞的ForkJoinTasks,但这样做需要进一步考虑三个因素：(1)完成少数(如果有的话)其他</em>任务应该依赖于在外部阻塞的任务同步或I / O。
 * 从未连接的事件样式异步任务(例如,{@link CountedCompleter}的子类)通常属于此类别。 (2)为尽量减少资源影响,任务应小;理想地仅执行(可能)阻塞动作。
 *  (3)除非使用{@link ForkJoinPool.ManagedBlocker} API,或者已知可能阻塞的任务的数量少于池的{@link ForkJoinPool#getParallelism}
 * 级别,池不能保证足够的线程可用以确保进展或良好的性能。
 * 从未连接的事件样式异步任务(例如,{@link CountedCompleter}的子类)通常属于此类别。 (2)为尽量减少资源影响,任务应小;理想地仅执行(可能)阻塞动作。
 * 
 * <p>等待完成和提取任务结果的主要方法是{@link #join},但有多种变体：{@link Future#get}方法支持可中断和/或定时等待完成和报告结果使用{@code Future}约定。
 * 方法{@link #invoke}在语义上等同于{@code fork(); join()},但总是尝试在当前线程中开始执行。这些方法的"<em> quiet </em>"形式不提取结果或报告异常。
 * 这些可能在执行一组任务时很有用,您需要延迟处理结果或异常,直到所有完成。方法{@code invokeAll}(提供多个版本)执行最常见的并行调用形式：分离一组任务并加入所有任务。
 * 
 *  <p>在最典型的用法中,一个fork-join对就像一个调用(fork)和return(join)从一个并行递归函数。与其他形式的递归调用一样,返回(联接)应该在最内层执行。
 * 例如,{@code a.fork(); b.fork(); b.join(); a.join();}可能比在{@code b}之前加入{@code a}要有效得多。
 * 
 * <p>任务的执行状态可以在几个细节级别查询：{@link #isDone}如果任务以任何方式完成(包括任务被取消而不执行的情况)为真; {@link #isCompletedNormally}如果任务在
 * 没有取消或遇到异常的情况下完成,则为true; {@link #isCancelled}如果任务已取消(在这种情况下,{@link #getException}返回一个{@link java.util.concurrent.CancellationException}
 * ),则为true;如果某个任务被取消或遇到异常,{@link #isCompletedAbnormally}为真,则{@link #getException}将返回遇到的异常或{@link java.util.concurrent.CancellationException}
 * 。
 * 
 *  <p> ForkJoinTask类通常不直接子类化。
 * 相反,你子类化一个支持特定样式的fork / join处理的抽象类,通常{@link RecursiveAction}对于大多数不返回结果的计算,{@link RecursiveTask}对于那些做的,
 * 和{@link CountedCompleter }对于完成的操作触发其他操作的用户。
 *  <p> ForkJoinTask类通常不直接子类化。
 * 通常,一个具体的ForkJoinTask子类声明包含其在构造函​​数中建立的参数的字段,然后定义{@code compute}方法,该方法以某种方式使用由该基类提供的控制方法。
 * 
 * <p>方法{@link #join}及其变体仅适用于完成依赖关系为非循环时使用;也就是说,并行计算可以被描述为有向无环图(DAG)。否则,执行可能会遇到一种形式的死锁,因为任务循环等待彼此。
 * 但是,此框架支持其他方法和技术(例如使用{@link Phaser},{@link #helpQuiesce}和{@link #complete}),这些方法和技术可能用于构造不是问题的自定义子类静态结
 * 构为DAG。
 * <p>方法{@link #join}及其变体仅适用于完成依赖关系为非循环时使用;也就是说,并行计算可以被描述为有向无环图(DAG)。否则,执行可能会遇到一种形式的死锁,因为任务循环等待彼此。
 * 为了支持这种用法,可以使用{@link #setForkJoinTaskTag}或{@link #compareAndSetForkJoinTaskTag}以{@code short}值将ForkJoi
 * nTask原子地标记</em>并使用{@link #getForkJoinTaskTag}进行检查。
 * <p>方法{@link #join}及其变体仅适用于完成依赖关系为非循环时使用;也就是说,并行计算可以被描述为有向无环图(DAG)。否则,执行可能会遇到一种形式的死锁,因为任务循环等待彼此。
 *  ForkJoinTask实现不会将这些{@code protected}方法或标签用于任何目的,但它们可能用于构建专门的子类。例如,并行图遍历可以使用提供的方法来避免重新访问已经处理的节点/任务。
 *  (标记的方法名称部分是笨重的,以鼓励定义反映其使用模式的方法。)。
 * 
 * <p>大多数基本支持方法是{@code final},以防止重写与内在的轻量级任务调度框架紧密相关的实现。
 * 开发人员创建新的基本样式的fork / join处理应该最小化实现{@code protected}方法{@link #exec},{@link #setRawResult}和{@link #getRawResult}
 * ,同时还引入一个抽象计算方法在其子类中实现,可能依赖于此类提供的其他{@code protected}方法。
 * <p>大多数基本支持方法是{@code final},以防止重写与内在的轻量级任务调度框架紧密相关的实现。
 * 
 *  <p> ForkJoinTasks应该执行相对少量的计算。大任务应该被拆分成更小的子任务,通常通过递归分解。
 * 作为一个非常粗略的经验法则,任务应执行超过100个且小于10000个基本计算步骤,并应避免不确定的循环。如果任务太大,则并行性无法提高吞吐量。如果太小,则内存和内部任务维护开销可能会压倒处理。
 * 
 *  <p>此类为{@link Runnable}和{@link Callable}提供了{@code adapt}方法,在将{@code ForkJoinTasks}与其他类型的任务混合执行时可能有用。
 * 当所有任务都是这种形式时,请考虑使用在<em> asyncMode </em>中构造的池。
 * 
 * <p> ForkJoinTasks是{@code Serializable},这使它们可以在远程执行框架等扩展中使用。只有在执行之前或之后,而不是在执行期间,序列化任务是明智的。
 * 在执行过程中不依赖于序列化。
 * 
 * 
 * @since 1.7
 * @author Doug Lea
 */
public abstract class ForkJoinTask<V> implements Future<V>, Serializable {

    /*
     * See the internal documentation of class ForkJoinPool for a
     * general implementation overview.  ForkJoinTasks are mainly
     * responsible for maintaining their "status" field amidst relays
     * to methods in ForkJoinWorkerThread and ForkJoinPool.
     *
     * The methods of this class are more-or-less layered into
     * (1) basic status maintenance
     * (2) execution and awaiting completion
     * (3) user-level methods that additionally report results.
     * This is sometimes hard to see because this file orders exported
     * methods in a way that flows well in javadocs.
     * <p>
     *  有关一般实现概述,请参阅ForkJoinPool类的内部文档。
     *  ForkJoinTasks主要负责在ForkJoinWorkerThread和ForkJoinPool中的方法的继电器中维护它们的"状态"字段。
     * 
     *  这个类的方法或多或少地分层到(1)基本状态维护(2)执行和等待完成(3)用户级方法,额外报告结果。这有时很难看到,因为这个文件以javadoc中流畅的方式订购导出的方法。
     * 
     */

    /*
     * The status field holds run control status bits packed into a
     * single int to minimize footprint and to ensure atomicity (via
     * CAS).  Status is initially zero, and takes on nonnegative
     * values until completed, upon which status (anded with
     * DONE_MASK) holds value NORMAL, CANCELLED, or EXCEPTIONAL. Tasks
     * undergoing blocking waits by other threads have the SIGNAL bit
     * set.  Completion of a stolen task with SIGNAL set awakens any
     * waiters via notifyAll. Even though suboptimal for some
     * purposes, we use basic builtin wait/notify to take advantage of
     * "monitor inflation" in JVMs that we would otherwise need to
     * emulate to avoid adding further per-task bookkeeping overhead.
     * We want these monitors to be "fat", i.e., not use biasing or
     * thin-lock techniques, so use some odd coding idioms that tend
     * to avoid them, mainly by arranging that every synchronized
     * block performs a wait, notifyAll or both.
     *
     * These control bits occupy only (some of) the upper half (16
     * bits) of status field. The lower bits are used for user-defined
     * tags.
     * <p>
     * 状态字段保持运行控制状态位打包成单个int,以最小化足迹和确保原子性(通过CAS)。
     * 状态最初为零,并且在完成之前采用非负值,其状态(与DONE_MASK一起)保持值NORMAL,CANCELLED或EXCEPTIONAL。正在进行阻塞等待的任务由其他线程设置了SIGNAL位。
     * 使用SIGNAL设置完成被盗任务通过notifyAll唤醒任何服务器。
     * 即使对于某些目的来说不是最佳的,我们使用基本的内置等待/通知来利用JVM中的"监视通货膨胀",否则我们需要模拟,以避免进一步增加每个任务的记帐开销。
     * 我们希望这些监视器是"胖的",即不使用偏置或薄锁定技术,因此使用一些倾向于避免它们的奇数编码习语,主要是通过安排每个同步块执行等待,notifyAll或两者。
     * 
     *  这些控制位仅占用(某些)状态字段的上半部分(16位)。低位用于用户定义的变量。
     * 
     */

    /** The run status of this task */
    volatile int status; // accessed directly by pool and workers
    static final int DONE_MASK   = 0xf0000000;  // mask out non-completion bits
    static final int NORMAL      = 0xf0000000;  // must be negative
    static final int CANCELLED   = 0xc0000000;  // must be < NORMAL
    static final int EXCEPTIONAL = 0x80000000;  // must be < CANCELLED
    static final int SIGNAL      = 0x00010000;  // must be >= 1 << 16
    static final int SMASK       = 0x0000ffff;  // short bits for tags

    /**
     * Marks completion and wakes up threads waiting to join this
     * task.
     *
     * <p>
     *  标记完成并唤醒等待加入此任务的线程。
     * 
     * 
     * @param completion one of NORMAL, CANCELLED, EXCEPTIONAL
     * @return completion status on exit
     */
    private int setCompletion(int completion) {
        for (int s;;) {
            if ((s = status) < 0)
                return s;
            if (U.compareAndSwapInt(this, STATUS, s, s | completion)) {
                if ((s >>> 16) != 0)
                    synchronized (this) { notifyAll(); }
                return completion;
            }
        }
    }

    /**
     * Primary execution method for stolen tasks. Unless done, calls
     * exec and records status if completed, but doesn't wait for
     * completion otherwise.
     *
     * <p>
     *  被盗任务的主要执行方法。除非执行,调用exec和记录状态如果完成,但不等待完成否则。
     * 
     * 
     * @return status on exit from this method
     */
    final int doExec() {
        int s; boolean completed;
        if ((s = status) >= 0) {
            try {
                completed = exec();
            } catch (Throwable rex) {
                return setExceptionalCompletion(rex);
            }
            if (completed)
                s = setCompletion(NORMAL);
        }
        return s;
    }

    /**
     * If not done, sets SIGNAL status and performs Object.wait(timeout).
     * This task may or may not be done on exit. Ignores interrupts.
     *
     * <p>
     *  如果未完成,则设置SIGNAL状态并执行Object.wait(timeout)。此任务可能在退出时也可能不会完成。忽略中断。
     * 
     * 
     * @param timeout using Object.wait conventions.
     */
    final void internalWait(long timeout) {
        int s;
        if ((s = status) >= 0 && // force completer to issue notify
            U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
            synchronized (this) {
                if (status >= 0)
                    try { wait(timeout); } catch (InterruptedException ie) { }
                else
                    notifyAll();
            }
        }
    }

    /**
     * Blocks a non-worker-thread until completion.
     * <p>
     *  阻塞非工作线程,直到完成。
     * 
     * 
     * @return status upon completion
     */
    private int externalAwaitDone() {
        int s = ((this instanceof CountedCompleter) ? // try helping
                 ForkJoinPool.common.externalHelpComplete(
                     (CountedCompleter<?>)this, 0) :
                 ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0);
        if (s >= 0 && (s = status) >= 0) {
            boolean interrupted = false;
            do {
                if (U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
                    synchronized (this) {
                        if (status >= 0) {
                            try {
                                wait(0L);
                            } catch (InterruptedException ie) {
                                interrupted = true;
                            }
                        }
                        else
                            notifyAll();
                    }
                }
            } while ((s = status) >= 0);
            if (interrupted)
                Thread.currentThread().interrupt();
        }
        return s;
    }

    /**
     * Blocks a non-worker-thread until completion or interruption.
     * <p>
     *  阻止非工作线程,直到完成或中断。
     * 
     */
    private int externalInterruptibleAwaitDone() throws InterruptedException {
        int s;
        if (Thread.interrupted())
            throw new InterruptedException();
        if ((s = status) >= 0 &&
            (s = ((this instanceof CountedCompleter) ?
                  ForkJoinPool.common.externalHelpComplete(
                      (CountedCompleter<?>)this, 0) :
                  ForkJoinPool.common.tryExternalUnpush(this) ? doExec() :
                  0)) >= 0) {
            while ((s = status) >= 0) {
                if (U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
                    synchronized (this) {
                        if (status >= 0)
                            wait(0L);
                        else
                            notifyAll();
                    }
                }
            }
        }
        return s;
    }

    /**
     * Implementation for join, get, quietlyJoin. Directly handles
     * only cases of already-completed, external wait, and
     * unfork+exec.  Others are relayed to ForkJoinPool.awaitJoin.
     *
     * <p>
     * 实现join,get,quietlyJoin。直接处理只有已完成,外部等待和unfork + exec的情况。其他人被转发到ForkJoinPool.awaitJoin。
     * 
     * 
     * @return status upon completion
     */
    private int doJoin() {
        int s; Thread t; ForkJoinWorkerThread wt; ForkJoinPool.WorkQueue w;
        return (s = status) < 0 ? s :
            ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            (w = (wt = (ForkJoinWorkerThread)t).workQueue).
            tryUnpush(this) && (s = doExec()) < 0 ? s :
            wt.pool.awaitJoin(w, this, 0L) :
            externalAwaitDone();
    }

    /**
     * Implementation for invoke, quietlyInvoke.
     *
     * <p>
     *  实现invoke,quietlyInvoke。
     * 
     * 
     * @return status upon completion
     */
    private int doInvoke() {
        int s; Thread t; ForkJoinWorkerThread wt;
        return (s = doExec()) < 0 ? s :
            ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            (wt = (ForkJoinWorkerThread)t).pool.
            awaitJoin(wt.workQueue, this, 0L) :
            externalAwaitDone();
    }

    // Exception table support

    /**
     * Table of exceptions thrown by tasks, to enable reporting by
     * callers. Because exceptions are rare, we don't directly keep
     * them with task objects, but instead use a weak ref table.  Note
     * that cancellation exceptions don't appear in the table, but are
     * instead recorded as status values.
     *
     * Note: These statics are initialized below in static block.
     * <p>
     *  任务抛出的异常表,以允许调用者报告。因为异常是罕见的,我们不直接保持它们与任务对象,而是使用弱引用表。请注意,取消异常不会出现在表中,而是记录为状态值。
     * 
     *  注意：这些静态在下面的静态块中初始化。
     * 
     */
    private static final ExceptionNode[] exceptionTable;
    private static final ReentrantLock exceptionTableLock;
    private static final ReferenceQueue<Object> exceptionTableRefQueue;

    /**
     * Fixed capacity for exceptionTable.
     * <p>
     *  exceptionTable的固定容量。
     * 
     */
    private static final int EXCEPTION_MAP_CAPACITY = 32;

    /**
     * Key-value nodes for exception table.  The chained hash table
     * uses identity comparisons, full locking, and weak references
     * for keys. The table has a fixed capacity because it only
     * maintains task exceptions long enough for joiners to access
     * them, so should never become very large for sustained
     * periods. However, since we do not know when the last joiner
     * completes, we must use weak references and expunge them. We do
     * so on each operation (hence full locking). Also, some thread in
     * any ForkJoinPool will call helpExpungeStaleExceptions when its
     * pool becomes isQuiescent.
     * <p>
     *  异常表的键值节点。链接的哈希表对密钥使用身份比较,完全锁定和弱引用。该表具有固定容量,因为它只保留任务异常足够长时间以供加入者访问它们,因此在持续时间内不应该变得非常大。
     * 然而,由于我们不知道什么时候最后一个木匠完成,我们必须使用弱引用并清除它们。我们这样做每个操作(因此完全锁定)。
     * 此外,任何ForkJoinPool中的某些线程将在其池变为isQuiescent时调用helpExpungeStaleExceptions。
     * 
     */
    static final class ExceptionNode extends WeakReference<ForkJoinTask<?>> {
        final Throwable ex;
        ExceptionNode next;
        final long thrower;  // use id not ref to avoid weak cycles
        final int hashCode;  // store task hashCode before weak ref disappears
        ExceptionNode(ForkJoinTask<?> task, Throwable ex, ExceptionNode next) {
            super(task, exceptionTableRefQueue);
            this.ex = ex;
            this.next = next;
            this.thrower = Thread.currentThread().getId();
            this.hashCode = System.identityHashCode(task);
        }
    }

    /**
     * Records exception and sets status.
     *
     * <p>
     *  记录异常并设置状态。
     * 
     * 
     * @return status on exit
     */
    final int recordExceptionalCompletion(Throwable ex) {
        int s;
        if ((s = status) >= 0) {
            int h = System.identityHashCode(this);
            final ReentrantLock lock = exceptionTableLock;
            lock.lock();
            try {
                expungeStaleExceptions();
                ExceptionNode[] t = exceptionTable;
                int i = h & (t.length - 1);
                for (ExceptionNode e = t[i]; ; e = e.next) {
                    if (e == null) {
                        t[i] = new ExceptionNode(this, ex, t[i]);
                        break;
                    }
                    if (e.get() == this) // already present
                        break;
                }
            } finally {
                lock.unlock();
            }
            s = setCompletion(EXCEPTIONAL);
        }
        return s;
    }

    /**
     * Records exception and possibly propagates.
     *
     * <p>
     *  记录异常并可能传播。
     * 
     * 
     * @return status on exit
     */
    private int setExceptionalCompletion(Throwable ex) {
        int s = recordExceptionalCompletion(ex);
        if ((s & DONE_MASK) == EXCEPTIONAL)
            internalPropagateException(ex);
        return s;
    }

    /**
     * Hook for exception propagation support for tasks with completers.
     * <p>
     *  钩子用于具有完成者的任务的异常传播支持。
     * 
     */
    void internalPropagateException(Throwable ex) {
    }

    /**
     * Cancels, ignoring any exceptions thrown by cancel. Used during
     * worker and pool shutdown. Cancel is spec'ed not to throw any
     * exceptions, but if it does anyway, we have no recourse during
     * shutdown, so guard against this case.
     * <p>
     * 取消,忽略由cancel抛出的任何异常。在工作和池关闭期间使用。取消规定不会抛出任何异常,但如果它确实,我们没有追索权在关机,所以防范这种情况。
     * 
     */
    static final void cancelIgnoringExceptions(ForkJoinTask<?> t) {
        if (t != null && t.status >= 0) {
            try {
                t.cancel(false);
            } catch (Throwable ignore) {
            }
        }
    }

    /**
     * Removes exception node and clears status.
     * <p>
     *  删除异常节点并清除状态。
     * 
     */
    private void clearExceptionalCompletion() {
        int h = System.identityHashCode(this);
        final ReentrantLock lock = exceptionTableLock;
        lock.lock();
        try {
            ExceptionNode[] t = exceptionTable;
            int i = h & (t.length - 1);
            ExceptionNode e = t[i];
            ExceptionNode pred = null;
            while (e != null) {
                ExceptionNode next = e.next;
                if (e.get() == this) {
                    if (pred == null)
                        t[i] = next;
                    else
                        pred.next = next;
                    break;
                }
                pred = e;
                e = next;
            }
            expungeStaleExceptions();
            status = 0;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns a rethrowable exception for the given task, if
     * available. To provide accurate stack traces, if the exception
     * was not thrown by the current thread, we try to create a new
     * exception of the same type as the one thrown, but with the
     * recorded exception as its cause. If there is no such
     * constructor, we instead try to use a no-arg constructor,
     * followed by initCause, to the same effect. If none of these
     * apply, or any fail due to other exceptions, we return the
     * recorded exception, which is still correct, although it may
     * contain a misleading stack trace.
     *
     * <p>
     *  返回给定任务的可重复抛出异常(如果可用)。为了提供精确的堆栈跟踪,如果当前线程没有抛出异常,我们尝试创建一个与抛出的异常类型相同的新异常,但将记录的异常作为其原因。
     * 如果没有这样的构造函数,我们尝试使用no-arg构造函数,然后使用initCause,达到相同的效果。
     * 如果这些都不适用,或由于其他异常导致的任何失败,我们返回记录的异常,这仍然是正确的,虽然它可能包含误导的堆栈跟踪。
     * 
     * 
     * @return the exception, or null if none
     */
    private Throwable getThrowableException() {
        if ((status & DONE_MASK) != EXCEPTIONAL)
            return null;
        int h = System.identityHashCode(this);
        ExceptionNode e;
        final ReentrantLock lock = exceptionTableLock;
        lock.lock();
        try {
            expungeStaleExceptions();
            ExceptionNode[] t = exceptionTable;
            e = t[h & (t.length - 1)];
            while (e != null && e.get() != this)
                e = e.next;
        } finally {
            lock.unlock();
        }
        Throwable ex;
        if (e == null || (ex = e.ex) == null)
            return null;
        if (e.thrower != Thread.currentThread().getId()) {
            Class<? extends Throwable> ec = ex.getClass();
            try {
                Constructor<?> noArgCtor = null;
                Constructor<?>[] cs = ec.getConstructors();// public ctors only
                for (int i = 0; i < cs.length; ++i) {
                    Constructor<?> c = cs[i];
                    Class<?>[] ps = c.getParameterTypes();
                    if (ps.length == 0)
                        noArgCtor = c;
                    else if (ps.length == 1 && ps[0] == Throwable.class) {
                        Throwable wx = (Throwable)c.newInstance(ex);
                        return (wx == null) ? ex : wx;
                    }
                }
                if (noArgCtor != null) {
                    Throwable wx = (Throwable)(noArgCtor.newInstance());
                    if (wx != null) {
                        wx.initCause(ex);
                        return wx;
                    }
                }
            } catch (Exception ignore) {
            }
        }
        return ex;
    }

    /**
     * Poll stale refs and remove them. Call only while holding lock.
     * <p>
     *  投票失败并删除它们。只在握住锁时才来电。
     * 
     */
    private static void expungeStaleExceptions() {
        for (Object x; (x = exceptionTableRefQueue.poll()) != null;) {
            if (x instanceof ExceptionNode) {
                int hashCode = ((ExceptionNode)x).hashCode;
                ExceptionNode[] t = exceptionTable;
                int i = hashCode & (t.length - 1);
                ExceptionNode e = t[i];
                ExceptionNode pred = null;
                while (e != null) {
                    ExceptionNode next = e.next;
                    if (e == x) {
                        if (pred == null)
                            t[i] = next;
                        else
                            pred.next = next;
                        break;
                    }
                    pred = e;
                    e = next;
                }
            }
        }
    }

    /**
     * If lock is available, poll stale refs and remove them.
     * Called from ForkJoinPool when pools become quiescent.
     * <p>
     *  如果锁定可用,则轮询陈旧引用并将其删除。当池变得静止时从ForkJoinPool调用。
     * 
     */
    static final void helpExpungeStaleExceptions() {
        final ReentrantLock lock = exceptionTableLock;
        if (lock.tryLock()) {
            try {
                expungeStaleExceptions();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * A version of "sneaky throw" to relay exceptions
     * <p>
     *  "sneaky throw"的版本以中继异常
     * 
     */
    static void rethrow(Throwable ex) {
        if (ex != null)
            ForkJoinTask.<RuntimeException>uncheckedThrow(ex);
    }

    /**
     * The sneaky part of sneaky throw, relying on generics
     * limitations to evade compiler complaints about rethrowing
     * unchecked exceptions
     * <p>
     *  偷偷摸摸的偷偷摸摸的部分,依靠泛型限制逃避编译器投诉关于重新抛出未检查的异常
     * 
     */
    @SuppressWarnings("unchecked") static <T extends Throwable>
        void uncheckedThrow(Throwable t) throws T {
        throw (T)t; // rely on vacuous cast
    }

    /**
     * Throws exception, if any, associated with the given status.
     * <p>
     *  抛出与给定状态相关联的异常(如果有)。
     * 
     */
    private void reportException(int s) {
        if (s == CANCELLED)
            throw new CancellationException();
        if (s == EXCEPTIONAL)
            rethrow(getThrowableException());
    }

    // public methods

    /**
     * Arranges to asynchronously execute this task in the pool the
     * current task is running in, if applicable, or using the {@link
     * ForkJoinPool#commonPool()} if not {@link #inForkJoinPool}.  While
     * it is not necessarily enforced, it is a usage error to fork a
     * task more than once unless it has completed and been
     * reinitialized.  Subsequent modifications to the state of this
     * task or any data it operates on are not necessarily
     * consistently observable by any thread other than the one
     * executing it unless preceded by a call to {@link #join} or
     * related methods, or a call to {@link #isDone} returning {@code
     * true}.
     *
     * <p>
     * 安排在当前任务正在运行的池中异步执行此任务(如果适用),或使用{@link ForkJoinPool#commonPool()}(如果不是{@link #inForkJoinPool})。
     * 虽然它不一定强制执行,但它是一个使用错误,多次fork一个任务,除非它已完成和重新初始化。
     * 对此任务的状态或其操作的任何数据的后续修改不一定由除执行它之外的任何线程一致地观察到,除非先调用{@link #join}或相关方法,或调用{@ link #isDone}返回{@code true}。
     * 虽然它不一定强制执行,但它是一个使用错误,多次fork一个任务,除非它已完成和重新初始化。
     * 
     * 
     * @return {@code this}, to simplify usage
     */
    public final ForkJoinTask<V> fork() {
        Thread t;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            ((ForkJoinWorkerThread)t).workQueue.push(this);
        else
            ForkJoinPool.common.externalPush(this);
        return this;
    }

    /**
     * Returns the result of the computation when it {@link #isDone is
     * done}.  This method differs from {@link #get()} in that
     * abnormal completion results in {@code RuntimeException} or
     * {@code Error}, not {@code ExecutionException}, and that
     * interrupts of the calling thread do <em>not</em> cause the
     * method to abruptly return by throwing {@code
     * InterruptedException}.
     *
     * <p>
     *  返回当{@link #isDone完成}时的计算结果。
     * 此方法与{@link #get()}不同,因为异常完成导致{@code RuntimeException}或{@code Error},而不是{@code ExecutionException},并且调
     * 用线程的中断不会< / em>导致方法通过抛出{@code InterruptedException}突然返回。
     *  返回当{@link #isDone完成}时的计算结果。
     * 
     * 
     * @return the computed result
     */
    public final V join() {
        int s;
        if ((s = doJoin() & DONE_MASK) != NORMAL)
            reportException(s);
        return getRawResult();
    }

    /**
     * Commences performing this task, awaits its completion if
     * necessary, and returns its result, or throws an (unchecked)
     * {@code RuntimeException} or {@code Error} if the underlying
     * computation did so.
     *
     * <p>
     *  开始执行此任务,如有必要,等待其完成,并返回其结果,或者如果基础计算这样做,则会抛出(未检查的){@code RuntimeException}或{@code Error}。
     * 
     * 
     * @return the computed result
     */
    public final V invoke() {
        int s;
        if ((s = doInvoke() & DONE_MASK) != NORMAL)
            reportException(s);
        return getRawResult();
    }

    /**
     * Forks the given tasks, returning when {@code isDone} holds for
     * each task or an (unchecked) exception is encountered, in which
     * case the exception is rethrown. If more than one task
     * encounters an exception, then this method throws any one of
     * these exceptions. If any task encounters an exception, the
     * other may be cancelled. However, the execution status of
     * individual tasks is not guaranteed upon exceptional return. The
     * status of each task may be obtained using {@link
     * #getException()} and related methods to check if they have been
     * cancelled, completed normally or exceptionally, or left
     * unprocessed.
     *
     * <p>
     * 分配给定任务,当{@code isDone}对每个任务保持时返回,或遇到(未检查)异常,在这种情况下异常被重新抛出。如果多个任务遇到异常,则此方法将抛出这些异常中的任何一个。
     * 如果任何任务遇到异常,则可能取消另一个任务。但是,在异常返回时,不能保证单个任务的执行状态。
     * 可以使用{@link #getException()}和相关方法获取每个任务的状态,以检查它们是否已被取消,正常完成或异常完成,或未处理。
     * 
     * 
     * @param t1 the first task
     * @param t2 the second task
     * @throws NullPointerException if any task is null
     */
    public static void invokeAll(ForkJoinTask<?> t1, ForkJoinTask<?> t2) {
        int s1, s2;
        t2.fork();
        if ((s1 = t1.doInvoke() & DONE_MASK) != NORMAL)
            t1.reportException(s1);
        if ((s2 = t2.doJoin() & DONE_MASK) != NORMAL)
            t2.reportException(s2);
    }

    /**
     * Forks the given tasks, returning when {@code isDone} holds for
     * each task or an (unchecked) exception is encountered, in which
     * case the exception is rethrown. If more than one task
     * encounters an exception, then this method throws any one of
     * these exceptions. If any task encounters an exception, others
     * may be cancelled. However, the execution status of individual
     * tasks is not guaranteed upon exceptional return. The status of
     * each task may be obtained using {@link #getException()} and
     * related methods to check if they have been cancelled, completed
     * normally or exceptionally, or left unprocessed.
     *
     * <p>
     *  分配给定任务,当{@code isDone}对每个任务保持时返回,或遇到(未检查)异常,在这种情况下异常被重新抛出。如果多个任务遇到异常,则此方法将抛出这些异常中的任何一个。
     * 如果任何任务遇到异常,其他任务可能会被取消。但是,在异常返回时,不能保证单个任务的执行状态。
     * 可以使用{@link #getException()}和相关方法获取每个任务的状态,以检查它们是否已被取消,正常完成或异常完成,或未处理。
     * 
     * 
     * @param tasks the tasks
     * @throws NullPointerException if any task is null
     */
    public static void invokeAll(ForkJoinTask<?>... tasks) {
        Throwable ex = null;
        int last = tasks.length - 1;
        for (int i = last; i >= 0; --i) {
            ForkJoinTask<?> t = tasks[i];
            if (t == null) {
                if (ex == null)
                    ex = new NullPointerException();
            }
            else if (i != 0)
                t.fork();
            else if (t.doInvoke() < NORMAL && ex == null)
                ex = t.getException();
        }
        for (int i = 1; i <= last; ++i) {
            ForkJoinTask<?> t = tasks[i];
            if (t != null) {
                if (ex != null)
                    t.cancel(false);
                else if (t.doJoin() < NORMAL)
                    ex = t.getException();
            }
        }
        if (ex != null)
            rethrow(ex);
    }

    /**
     * Forks all tasks in the specified collection, returning when
     * {@code isDone} holds for each task or an (unchecked) exception
     * is encountered, in which case the exception is rethrown. If
     * more than one task encounters an exception, then this method
     * throws any one of these exceptions. If any task encounters an
     * exception, others may be cancelled. However, the execution
     * status of individual tasks is not guaranteed upon exceptional
     * return. The status of each task may be obtained using {@link
     * #getException()} and related methods to check if they have been
     * cancelled, completed normally or exceptionally, or left
     * unprocessed.
     *
     * <p>
     * 分叉指定集合中的所有任务,当{@code isDone}保存每个任务或遇到(未检查)异常时返回,在这种情况下异常被重新抛出。如果多个任务遇到异常,则此方法将抛出这些异常中的任何一个。
     * 如果任何任务遇到异常,其他任务可能会被取消。但是,在异常返回时,不能保证单个任务的执行状态。
     * 可以使用{@link #getException()}和相关方法获取每个任务的状态,以检查它们是否已被取消,正常完成或异常完成,或未处理。
     * 
     * 
     * @param tasks the collection of tasks
     * @param <T> the type of the values returned from the tasks
     * @return the tasks argument, to simplify usage
     * @throws NullPointerException if tasks or any element are null
     */
    public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> tasks) {
        if (!(tasks instanceof RandomAccess) || !(tasks instanceof List<?>)) {
            invokeAll(tasks.toArray(new ForkJoinTask<?>[tasks.size()]));
            return tasks;
        }
        @SuppressWarnings("unchecked")
        List<? extends ForkJoinTask<?>> ts =
            (List<? extends ForkJoinTask<?>>) tasks;
        Throwable ex = null;
        int last = ts.size() - 1;
        for (int i = last; i >= 0; --i) {
            ForkJoinTask<?> t = ts.get(i);
            if (t == null) {
                if (ex == null)
                    ex = new NullPointerException();
            }
            else if (i != 0)
                t.fork();
            else if (t.doInvoke() < NORMAL && ex == null)
                ex = t.getException();
        }
        for (int i = 1; i <= last; ++i) {
            ForkJoinTask<?> t = ts.get(i);
            if (t != null) {
                if (ex != null)
                    t.cancel(false);
                else if (t.doJoin() < NORMAL)
                    ex = t.getException();
            }
        }
        if (ex != null)
            rethrow(ex);
        return tasks;
    }

    /**
     * Attempts to cancel execution of this task. This attempt will
     * fail if the task has already completed or could not be
     * cancelled for some other reason. If successful, and this task
     * has not started when {@code cancel} is called, execution of
     * this task is suppressed. After this method returns
     * successfully, unless there is an intervening call to {@link
     * #reinitialize}, subsequent calls to {@link #isCancelled},
     * {@link #isDone}, and {@code cancel} will return {@code true}
     * and calls to {@link #join} and related methods will result in
     * {@code CancellationException}.
     *
     * <p>This method may be overridden in subclasses, but if so, must
     * still ensure that these properties hold. In particular, the
     * {@code cancel} method itself must not throw exceptions.
     *
     * <p>This method is designed to be invoked by <em>other</em>
     * tasks. To terminate the current task, you can just return or
     * throw an unchecked exception from its computation method, or
     * invoke {@link #completeExceptionally(Throwable)}.
     *
     * <p>
     *  尝试取消此任务的执行。如果任务已完成或由于某些其他原因无法取消,则此尝试将失败。如果成功,并且调用{@code cancel}时此任务尚未启动,则会禁止执行此任务。
     * 此方法成功返回后,除非有对{@link #reinitialize}的中间调用,对{@link #isCancelled},{@link #isDone}和{@code cancel}的后续调用将返回{@code true}
     * 而调用{@link #join}和相关方法将导致{@code CancellationException}。
     *  尝试取消此任务的执行。如果任务已完成或由于某些其他原因无法取消,则此尝试将失败。如果成功,并且调用{@code cancel}时此任务尚未启动,则会禁止执行此任务。
     * 
     *  <p>此方法可能在子类中被覆盖,但如果是这样,仍必须确保这些属性成立。特别是,{@code cancel}方法本身不能抛出异常。
     * 
     * <p>此方法旨在由<em>其他</em>任务调用。要终止当前任务,您只需从其计算方法返回或抛出未检查的异常,或调用{@link #completeExceptionally(Throwable)}。
     * 
     * 
     * @param mayInterruptIfRunning this value has no effect in the
     * default implementation because interrupts are not used to
     * control cancellation.
     *
     * @return {@code true} if this task is now cancelled
     */
    public boolean cancel(boolean mayInterruptIfRunning) {
        return (setCompletion(CANCELLED) & DONE_MASK) == CANCELLED;
    }

    public final boolean isDone() {
        return status < 0;
    }

    public final boolean isCancelled() {
        return (status & DONE_MASK) == CANCELLED;
    }

    /**
     * Returns {@code true} if this task threw an exception or was cancelled.
     *
     * <p>
     *  如果此任务抛出异常或被取消,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this task threw an exception or was cancelled
     */
    public final boolean isCompletedAbnormally() {
        return status < NORMAL;
    }

    /**
     * Returns {@code true} if this task completed without throwing an
     * exception and was not cancelled.
     *
     * <p>
     *  如果此任务在没有抛出异常并且未被取消的情况下完成,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this task completed without throwing an
     * exception and was not cancelled
     */
    public final boolean isCompletedNormally() {
        return (status & DONE_MASK) == NORMAL;
    }

    /**
     * Returns the exception thrown by the base computation, or a
     * {@code CancellationException} if cancelled, or {@code null} if
     * none or if the method has not yet completed.
     *
     * <p>
     *  返回基本计算抛出的异常,如果取消则返回{@code CancellationException},如果没有则返回{@code null}或如果方法尚未完成。
     * 
     * 
     * @return the exception, or {@code null} if none
     */
    public final Throwable getException() {
        int s = status & DONE_MASK;
        return ((s >= NORMAL)    ? null :
                (s == CANCELLED) ? new CancellationException() :
                getThrowableException());
    }

    /**
     * Completes this task abnormally, and if not already aborted or
     * cancelled, causes it to throw the given exception upon
     * {@code join} and related operations. This method may be used
     * to induce exceptions in asynchronous tasks, or to force
     * completion of tasks that would not otherwise complete.  Its use
     * in other situations is discouraged.  This method is
     * overridable, but overridden versions must invoke {@code super}
     * implementation to maintain guarantees.
     *
     * <p>
     *  完成此任务异常,如果尚未中止或取消,将导致它抛出给定的异常{@code join}和相关操作。此方法可用于在异步任务中引入异常,或强制完成本来不会完成的任务。它在其他情况下的使用是不鼓励的。
     * 此方法是可覆盖的,但是覆盖的版本必须调用{@code super}实现以保持保证。
     * 
     * 
     * @param ex the exception to throw. If this exception is not a
     * {@code RuntimeException} or {@code Error}, the actual exception
     * thrown will be a {@code RuntimeException} with cause {@code ex}.
     */
    public void completeExceptionally(Throwable ex) {
        setExceptionalCompletion((ex instanceof RuntimeException) ||
                                 (ex instanceof Error) ? ex :
                                 new RuntimeException(ex));
    }

    /**
     * Completes this task, and if not already aborted or cancelled,
     * returning the given value as the result of subsequent
     * invocations of {@code join} and related operations. This method
     * may be used to provide results for asynchronous tasks, or to
     * provide alternative handling for tasks that would not otherwise
     * complete normally. Its use in other situations is
     * discouraged. This method is overridable, but overridden
     * versions must invoke {@code super} implementation to maintain
     * guarantees.
     *
     * <p>
     * 完成此任务,如果尚未中止或取消,则返回给定值作为后续调用{@code join}和相关操作的结果。该方法可以用于为异步任务提供结果,或者为否则将不会正常完成的任务提供替代处理。
     * 它在其他情况下的使用是不鼓励的。此方法是可覆盖的,但是覆盖的版本必须调用{@code super}实现以保持保证。
     * 
     * 
     * @param value the result value for this task
     */
    public void complete(V value) {
        try {
            setRawResult(value);
        } catch (Throwable rex) {
            setExceptionalCompletion(rex);
            return;
        }
        setCompletion(NORMAL);
    }

    /**
     * Completes this task normally without setting a value. The most
     * recent value established by {@link #setRawResult} (or {@code
     * null} by default) will be returned as the result of subsequent
     * invocations of {@code join} and related operations.
     *
     * <p>
     *  正常完成此任务,而不设置值。 {@link #setRawResult}(或默认为{@code null})建立的最新值将作为后续调用{@code join}和相关操作的结果返回。
     * 
     * 
     * @since 1.8
     */
    public final void quietlyComplete() {
        setCompletion(NORMAL);
    }

    /**
     * Waits if necessary for the computation to complete, and then
     * retrieves its result.
     *
     * <p>
     *  如果需要等待计算完成,然后检索其结果。
     * 
     * 
     * @return the computed result
     * @throws CancellationException if the computation was cancelled
     * @throws ExecutionException if the computation threw an
     * exception
     * @throws InterruptedException if the current thread is not a
     * member of a ForkJoinPool and was interrupted while waiting
     */
    public final V get() throws InterruptedException, ExecutionException {
        int s = (Thread.currentThread() instanceof ForkJoinWorkerThread) ?
            doJoin() : externalInterruptibleAwaitDone();
        Throwable ex;
        if ((s &= DONE_MASK) == CANCELLED)
            throw new CancellationException();
        if (s == EXCEPTIONAL && (ex = getThrowableException()) != null)
            throw new ExecutionException(ex);
        return getRawResult();
    }

    /**
     * Waits if necessary for at most the given time for the computation
     * to complete, and then retrieves its result, if available.
     *
     * <p>
     *  如果有必要,最多等待计算完成的给定时间,然后检索其结果(如果可用)。
     * 
     * 
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return the computed result
     * @throws CancellationException if the computation was cancelled
     * @throws ExecutionException if the computation threw an
     * exception
     * @throws InterruptedException if the current thread is not a
     * member of a ForkJoinPool and was interrupted while waiting
     * @throws TimeoutException if the wait timed out
     */
    public final V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        int s;
        long nanos = unit.toNanos(timeout);
        if (Thread.interrupted())
            throw new InterruptedException();
        if ((s = status) >= 0 && nanos > 0L) {
            long d = System.nanoTime() + nanos;
            long deadline = (d == 0L) ? 1L : d; // avoid 0
            Thread t = Thread.currentThread();
            if (t instanceof ForkJoinWorkerThread) {
                ForkJoinWorkerThread wt = (ForkJoinWorkerThread)t;
                s = wt.pool.awaitJoin(wt.workQueue, this, deadline);
            }
            else if ((s = ((this instanceof CountedCompleter) ?
                           ForkJoinPool.common.externalHelpComplete(
                               (CountedCompleter<?>)this, 0) :
                           ForkJoinPool.common.tryExternalUnpush(this) ?
                           doExec() : 0)) >= 0) {
                long ns, ms; // measure in nanosecs, but wait in millisecs
                while ((s = status) >= 0 &&
                       (ns = deadline - System.nanoTime()) > 0L) {
                    if ((ms = TimeUnit.NANOSECONDS.toMillis(ns)) > 0L &&
                        U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
                        synchronized (this) {
                            if (status >= 0)
                                wait(ms); // OK to throw InterruptedException
                            else
                                notifyAll();
                        }
                    }
                }
            }
        }
        if (s >= 0)
            s = status;
        if ((s &= DONE_MASK) != NORMAL) {
            Throwable ex;
            if (s == CANCELLED)
                throw new CancellationException();
            if (s != EXCEPTIONAL)
                throw new TimeoutException();
            if ((ex = getThrowableException()) != null)
                throw new ExecutionException(ex);
        }
        return getRawResult();
    }

    /**
     * Joins this task, without returning its result or throwing its
     * exception. This method may be useful when processing
     * collections of tasks when some have been cancelled or otherwise
     * known to have aborted.
     * <p>
     *  加入此任务,不返回其结果或抛出其异常。当某些已被取消或以其他方式已知已中止时,此方法在处理任务集合时可能是有用的。
     * 
     */
    public final void quietlyJoin() {
        doJoin();
    }

    /**
     * Commences performing this task and awaits its completion if
     * necessary, without returning its result or throwing its
     * exception.
     * <p>
     *  开始执行此任务,如有必要,等待其完成,而不返回其结果或抛出异常。
     * 
     */
    public final void quietlyInvoke() {
        doInvoke();
    }

    /**
     * Possibly executes tasks until the pool hosting the current task
     * {@link ForkJoinPool#isQuiescent is quiescent}. This method may
     * be of use in designs in which many tasks are forked, but none
     * are explicitly joined, instead executing them until all are
     * processed.
     * <p>
     * 可能执行任务,直到托管当前任务的池{@link ForkJoinPool#isQuiescent is quiescent}。
     * 此方法可用于其中许多任务被分叉,但没有明确连接的设计,而是执行它们直到所有任务被处理。
     * 
     */
    public static void helpQuiesce() {
        Thread t;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            ForkJoinWorkerThread wt = (ForkJoinWorkerThread)t;
            wt.pool.helpQuiescePool(wt.workQueue);
        }
        else
            ForkJoinPool.quiesceCommonPool();
    }

    /**
     * Resets the internal bookkeeping state of this task, allowing a
     * subsequent {@code fork}. This method allows repeated reuse of
     * this task, but only if reuse occurs when this task has either
     * never been forked, or has been forked, then completed and all
     * outstanding joins of this task have also completed. Effects
     * under any other usage conditions are not guaranteed.
     * This method may be useful when executing
     * pre-constructed trees of subtasks in loops.
     *
     * <p>Upon completion of this method, {@code isDone()} reports
     * {@code false}, and {@code getException()} reports {@code
     * null}. However, the value returned by {@code getRawResult} is
     * unaffected. To clear this value, you can invoke {@code
     * setRawResult(null)}.
     * <p>
     *  重置此任务的内部记帐状态,允许后续的{@code fork}。此方法允许重复重复使用此任务,但仅当此任务从未被分叉或已被分叉,然后完成并且此任务的所有未完成连接也已完成时才发生重用。
     * 在任何其他使用条件下的效果不保证。当在循环中执行预构建的子任务树时,此方法可能很有用。
     * 
     *  <p>完成此方法后,{@code isDone()}报告{@code false}和{@code getException()}报告{@code null}。
     * 但是,{@code getRawResult}返回的值不受影响。要清除此值,可以调用{@code setRawResult(null)}。
     * 
     */
    public void reinitialize() {
        if ((status & DONE_MASK) == EXCEPTIONAL)
            clearExceptionalCompletion();
        else
            status = 0;
    }

    /**
     * Returns the pool hosting the current task execution, or null
     * if this task is executing outside of any ForkJoinPool.
     *
     * <p>
     *  返回托管当前任务执行的池,如果此任务在任何ForkJoinPool外部执行,则返回null。
     * 
     * 
     * @see #inForkJoinPool
     * @return the pool, or {@code null} if none
     */
    public static ForkJoinPool getPool() {
        Thread t = Thread.currentThread();
        return (t instanceof ForkJoinWorkerThread) ?
            ((ForkJoinWorkerThread) t).pool : null;
    }

    /**
     * Returns {@code true} if the current thread is a {@link
     * ForkJoinWorkerThread} executing as a ForkJoinPool computation.
     *
     * <p>
     *  如果当前线程是作为ForkJoinPool计算执行的{@link ForkJoinWorkerThread},则返回{@code true}。
     * 
     * 
     * @return {@code true} if the current thread is a {@link
     * ForkJoinWorkerThread} executing as a ForkJoinPool computation,
     * or {@code false} otherwise
     */
    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }

    /**
     * Tries to unschedule this task for execution. This method will
     * typically (but is not guaranteed to) succeed if this task is
     * the most recently forked task by the current thread, and has
     * not commenced executing in another thread.  This method may be
     * useful when arranging alternative local processing of tasks
     * that could have been, but were not, stolen.
     *
     * <p>
     * 尝试取消调度此任务以执行。如果此任务是当前线程的最近分叉任务,并且尚未在另一个线程中开始执行,则此方法通常(但不能保证)成功。当安排可能已经但不是被盗的任务的替代本地处理时,该方法可能是有用的。
     * 
     * 
     * @return {@code true} if unforked
     */
    public boolean tryUnfork() {
        Thread t;
        return (((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
                ((ForkJoinWorkerThread)t).workQueue.tryUnpush(this) :
                ForkJoinPool.common.tryExternalUnpush(this));
    }

    /**
     * Returns an estimate of the number of tasks that have been
     * forked by the current worker thread but not yet executed. This
     * value may be useful for heuristic decisions about whether to
     * fork other tasks.
     *
     * <p>
     *  返回由当前工作线程分叉但尚未执行的任务数量的估计值。该值可能对于是否分叉其他任务的启发式决定有用。
     * 
     * 
     * @return the number of tasks
     */
    public static int getQueuedTaskCount() {
        Thread t; ForkJoinPool.WorkQueue q;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            q = ((ForkJoinWorkerThread)t).workQueue;
        else
            q = ForkJoinPool.commonSubmitterQueue();
        return (q == null) ? 0 : q.queueSize();
    }

    /**
     * Returns an estimate of how many more locally queued tasks are
     * held by the current worker thread than there are other worker
     * threads that might steal them, or zero if this thread is not
     * operating in a ForkJoinPool. This value may be useful for
     * heuristic decisions about whether to fork other tasks. In many
     * usages of ForkJoinTasks, at steady state, each worker should
     * aim to maintain a small constant surplus (for example, 3) of
     * tasks, and to process computations locally if this threshold is
     * exceeded.
     *
     * <p>
     *  返回当前工作线程持有的本地排队任务的数量超过可能会窃取它们的其他工作线程的估计值,如果此线程未在ForkJoinPool中操作,则返回0。该值可能对于是否分叉其他任务的启发式决定有用。
     * 在ForkJoinTasks的许多用法中,在稳定状态下,每个工作者应当旨在维持小的恒定剩余(例如,3)任务,并且如果超过该阈值则在本地处理计算。
     * 
     * 
     * @return the surplus number of tasks, which may be negative
     */
    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.getSurplusQueuedTaskCount();
    }

    // Extension methods

    /**
     * Returns the result that would be returned by {@link #join}, even
     * if this task completed abnormally, or {@code null} if this task
     * is not known to have been completed.  This method is designed
     * to aid debugging, as well as to support extensions. Its use in
     * any other context is discouraged.
     *
     * <p>
     *  返回由{@link #join}(即使此任务异常完成)返回的结果,如果此任务未完成,则返回{@code null}。此方法旨在帮助调试,以及支持扩展。它在任何其他情况下的使用被劝阻。
     * 
     * 
     * @return the result, or {@code null} if not completed
     */
    public abstract V getRawResult();

    /**
     * Forces the given value to be returned as a result.  This method
     * is designed to support extensions, and should not in general be
     * called otherwise.
     *
     * <p>
     *  强制返回给定值作为结果。这种方法被设计为支持扩展,并且一般不应该被称为否则。
     * 
     * 
     * @param value the value
     */
    protected abstract void setRawResult(V value);

    /**
     * Immediately performs the base action of this task and returns
     * true if, upon return from this method, this task is guaranteed
     * to have completed normally. This method may return false
     * otherwise, to indicate that this task is not necessarily
     * complete (or is not known to be complete), for example in
     * asynchronous actions that require explicit invocations of
     * completion methods. This method may also throw an (unchecked)
     * exception to indicate abnormal exit. This method is designed to
     * support extensions, and should not in general be called
     * otherwise.
     *
     * <p>
     * 立即执行此任务的基本操作,并返回true,如果从此方法返回时,此任务被保证已正常完成。否则,此方法可能返回false,以指示此任务不一定完成(或未知完成),例如在需要显式调用完成方法的异步操作中。
     * 这个方法也可以抛出一个(未检查的)异常来指示异常退出。这种方法被设计为支持扩展,并且一般不应该被称为否则。
     * 
     * 
     * @return {@code true} if this task is known to have completed normally
     */
    protected abstract boolean exec();

    /**
     * Returns, but does not unschedule or execute, a task queued by
     * the current thread but not yet executed, if one is immediately
     * available. There is no guarantee that this task will actually
     * be polled or executed next. Conversely, this method may return
     * null even if a task exists but cannot be accessed without
     * contention with other threads.  This method is designed
     * primarily to support extensions, and is unlikely to be useful
     * otherwise.
     *
     * <p>
     *  返回,但不会取消调度或执行由当前线程排队但尚未执行的任务(如果一个任务立即可用)。不能保证此任务实际上将被轮询或执行。相反,此方法可能返回null,即使任务存在,但无法访问,而不与其他线程争用。
     * 此方法主要用于支持扩展,并且不太可能有用。
     * 
     * 
     * @return the next task, or {@code null} if none are available
     */
    protected static ForkJoinTask<?> peekNextLocalTask() {
        Thread t; ForkJoinPool.WorkQueue q;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
            q = ((ForkJoinWorkerThread)t).workQueue;
        else
            q = ForkJoinPool.commonSubmitterQueue();
        return (q == null) ? null : q.peek();
    }

    /**
     * Unschedules and returns, without executing, the next task
     * queued by the current thread but not yet executed, if the
     * current thread is operating in a ForkJoinPool.  This method is
     * designed primarily to support extensions, and is unlikely to be
     * useful otherwise.
     *
     * <p>
     *  如果当前线程在ForkJoinPool中操作,则取消调度并返回而不执行由当前线程排队但尚未执行的下一个任务。此方法主要用于支持扩展,并且不太可能有用。
     * 
     * 
     * @return the next task, or {@code null} if none are available
     */
    protected static ForkJoinTask<?> pollNextLocalTask() {
        Thread t;
        return ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            ((ForkJoinWorkerThread)t).workQueue.nextLocalTask() :
            null;
    }

    /**
     * If the current thread is operating in a ForkJoinPool,
     * unschedules and returns, without executing, the next task
     * queued by the current thread but not yet executed, if one is
     * available, or if not available, a task that was forked by some
     * other thread, if available. Availability may be transient, so a
     * {@code null} result does not necessarily imply quiescence of
     * the pool this task is operating in.  This method is designed
     * primarily to support extensions, and is unlikely to be useful
     * otherwise.
     *
     * <p>
     * 如果当前线程在ForkJoinPool中操作,则在不执行的情况下解除调度并返回由当前线程排队但尚未执行的下一个任务(如果一个线程可用或者如果不可用),则由某个其他线程分叉的任务,如果可供使用的话。
     * 可用性可能是暂时的,因此{@code null}结果不一定意味着此任务正在操作的池的静态。此方法主要设计为支持扩展,否则不可能有用。
     * 
     * 
     * @return a task, or {@code null} if none are available
     */
    protected static ForkJoinTask<?> pollTask() {
        Thread t; ForkJoinWorkerThread wt;
        return ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            (wt = (ForkJoinWorkerThread)t).pool.nextTaskFor(wt.workQueue) :
            null;
    }

    // tag operations

    /**
     * Returns the tag for this task.
     *
     * <p>
     *  返回此任务的标记。
     * 
     * 
     * @return the tag for this task
     * @since 1.8
     */
    public final short getForkJoinTaskTag() {
        return (short)status;
    }

    /**
     * Atomically sets the tag value for this task.
     *
     * <p>
     *  以原子方式设置此任务的标记值。
     * 
     * 
     * @param tag the tag value
     * @return the previous value of the tag
     * @since 1.8
     */
    public final short setForkJoinTaskTag(short tag) {
        for (int s;;) {
            if (U.compareAndSwapInt(this, STATUS, s = status,
                                    (s & ~SMASK) | (tag & SMASK)))
                return (short)s;
        }
    }

    /**
     * Atomically conditionally sets the tag value for this task.
     * Among other applications, tags can be used as visit markers
     * in tasks operating on graphs, as in methods that check: {@code
     * if (task.compareAndSetForkJoinTaskTag((short)0, (short)1))}
     * before processing, otherwise exiting because the node has
     * already been visited.
     *
     * <p>
     *  以原子方式有条件地设置此任务的标记值。
     * 在其他应用程序中,标记可以用作在图上操作的任务中的访问标记,如处理之前检查{@code if(task.compareAndSetForkJoinTaskTag((short)0,(short)1))}
     * 该节点已经被访问。
     *  以原子方式有条件地设置此任务的标记值。
     * 
     * 
     * @param e the expected tag value
     * @param tag the new tag value
     * @return {@code true} if successful; i.e., the current value was
     * equal to e and is now tag.
     * @since 1.8
     */
    public final boolean compareAndSetForkJoinTaskTag(short e, short tag) {
        for (int s;;) {
            if ((short)(s = status) != e)
                return false;
            if (U.compareAndSwapInt(this, STATUS, s,
                                    (s & ~SMASK) | (tag & SMASK)))
                return true;
        }
    }

    /**
     * Adaptor for Runnables. This implements RunnableFuture
     * to be compliant with AbstractExecutorService constraints
     * when used in ForkJoinPool.
     * <p>
     *  Runnables适配器。这实现了RunnableFuture在ForkJoinPool中使用时符合AbstractExecutorService约束。
     * 
     */
    static final class AdaptedRunnable<T> extends ForkJoinTask<T>
        implements RunnableFuture<T> {
        final Runnable runnable;
        T result;
        AdaptedRunnable(Runnable runnable, T result) {
            if (runnable == null) throw new NullPointerException();
            this.runnable = runnable;
            this.result = result; // OK to set this even before completion
        }
        public final T getRawResult() { return result; }
        public final void setRawResult(T v) { result = v; }
        public final boolean exec() { runnable.run(); return true; }
        public final void run() { invoke(); }
        private static final long serialVersionUID = 5232453952276885070L;
    }

    /**
     * Adaptor for Runnables without results
     * <p>
     *  适用于没有结果的Runnables
     * 
     */
    static final class AdaptedRunnableAction extends ForkJoinTask<Void>
        implements RunnableFuture<Void> {
        final Runnable runnable;
        AdaptedRunnableAction(Runnable runnable) {
            if (runnable == null) throw new NullPointerException();
            this.runnable = runnable;
        }
        public final Void getRawResult() { return null; }
        public final void setRawResult(Void v) { }
        public final boolean exec() { runnable.run(); return true; }
        public final void run() { invoke(); }
        private static final long serialVersionUID = 5232453952276885070L;
    }

    /**
     * Adaptor for Runnables in which failure forces worker exception
     * <p>
     *  用于Runnables的适配器,其中失败会强制工作程序异常
     * 
     */
    static final class RunnableExecuteAction extends ForkJoinTask<Void> {
        final Runnable runnable;
        RunnableExecuteAction(Runnable runnable) {
            if (runnable == null) throw new NullPointerException();
            this.runnable = runnable;
        }
        public final Void getRawResult() { return null; }
        public final void setRawResult(Void v) { }
        public final boolean exec() { runnable.run(); return true; }
        void internalPropagateException(Throwable ex) {
            rethrow(ex); // rethrow outside exec() catches.
        }
        private static final long serialVersionUID = 5232453952276885070L;
    }

    /**
     * Adaptor for Callables
     * <p>
     *  Callables适配器
     * 
     */
    static final class AdaptedCallable<T> extends ForkJoinTask<T>
        implements RunnableFuture<T> {
        final Callable<? extends T> callable;
        T result;
        AdaptedCallable(Callable<? extends T> callable) {
            if (callable == null) throw new NullPointerException();
            this.callable = callable;
        }
        public final T getRawResult() { return result; }
        public final void setRawResult(T v) { result = v; }
        public final boolean exec() {
            try {
                result = callable.call();
                return true;
            } catch (Error err) {
                throw err;
            } catch (RuntimeException rex) {
                throw rex;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        public final void run() { invoke(); }
        private static final long serialVersionUID = 2838392045355241008L;
    }

    /**
     * Returns a new {@code ForkJoinTask} that performs the {@code run}
     * method of the given {@code Runnable} as its action, and returns
     * a null result upon {@link #join}.
     *
     * <p>
     *  返回一个新的{@code ForkJoinTask},它执行给定{@code Runnable}的{@code run}方法作为其操作,并在{@link #join}返回null结果。
     * 
     * 
     * @param runnable the runnable action
     * @return the task
     */
    public static ForkJoinTask<?> adapt(Runnable runnable) {
        return new AdaptedRunnableAction(runnable);
    }

    /**
     * Returns a new {@code ForkJoinTask} that performs the {@code run}
     * method of the given {@code Runnable} as its action, and returns
     * the given result upon {@link #join}.
     *
     * <p>
     *  返回一个新的{@code ForkJoinTask},它执行给定{@code Runnable}的{@code run}方法作为其操作,并返回{@link #join}的给定结果。
     * 
     * 
     * @param runnable the runnable action
     * @param result the result upon completion
     * @param <T> the type of the result
     * @return the task
     */
    public static <T> ForkJoinTask<T> adapt(Runnable runnable, T result) {
        return new AdaptedRunnable<T>(runnable, result);
    }

    /**
     * Returns a new {@code ForkJoinTask} that performs the {@code call}
     * method of the given {@code Callable} as its action, and returns
     * its result upon {@link #join}, translating any checked exceptions
     * encountered into {@code RuntimeException}.
     *
     * <p>
     * 返回一个新的{@code ForkJoinTask},它执行给定{@code Callable}作为其操作的{@code call}方法,并在{@link #join}返回其结果,将遇到的任何检查异常转
     * 换为{@code RuntimeException}。
     * 
     * 
     * @param callable the callable action
     * @param <T> the type of the callable's result
     * @return the task
     */
    public static <T> ForkJoinTask<T> adapt(Callable<? extends T> callable) {
        return new AdaptedCallable<T>(callable);
    }

    // Serialization support

    private static final long serialVersionUID = -7721805057305804111L;

    /**
     * Saves this task to a stream (that is, serializes it).
     *
     * <p>
     *  将此任务保存到流(即,将其序列化)。
     * 
     * 
     * @param s the stream
     * @throws java.io.IOException if an I/O error occurs
     * @serialData the current run status and the exception thrown
     * during execution, or {@code null} if none
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
        s.writeObject(getException());
    }

    /**
     * Reconstitutes this task from a stream (that is, deserializes it).
     * <p>
     *  从流重构此任务(即,反序列化它)。
     * 
     * @param s the stream
     * @throws ClassNotFoundException if the class of a serialized object
     *         could not be found
     * @throws java.io.IOException if an I/O error occurs
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        Object ex = s.readObject();
        if (ex != null)
            setExceptionalCompletion((Throwable)ex);
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe U;
    private static final long STATUS;

    static {
        exceptionTableLock = new ReentrantLock();
        exceptionTableRefQueue = new ReferenceQueue<Object>();
        exceptionTable = new ExceptionNode[EXCEPTION_MAP_CAPACITY];
        try {
            U = sun.misc.Unsafe.getUnsafe();
            Class<?> k = ForkJoinTask.class;
            STATUS = U.objectFieldOffset
                (k.getDeclaredField("status"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
