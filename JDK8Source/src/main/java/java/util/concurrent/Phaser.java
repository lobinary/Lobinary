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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * A reusable synchronization barrier, similar in functionality to
 * {@link java.util.concurrent.CyclicBarrier CyclicBarrier} and
 * {@link java.util.concurrent.CountDownLatch CountDownLatch}
 * but supporting more flexible usage.
 *
 * <p><b>Registration.</b> Unlike the case for other barriers, the
 * number of parties <em>registered</em> to synchronize on a phaser
 * may vary over time.  Tasks may be registered at any time (using
 * methods {@link #register}, {@link #bulkRegister}, or forms of
 * constructors establishing initial numbers of parties), and
 * optionally deregistered upon any arrival (using {@link
 * #arriveAndDeregister}).  As is the case with most basic
 * synchronization constructs, registration and deregistration affect
 * only internal counts; they do not establish any further internal
 * bookkeeping, so tasks cannot query whether they are registered.
 * (However, you can introduce such bookkeeping by subclassing this
 * class.)
 *
 * <p><b>Synchronization.</b> Like a {@code CyclicBarrier}, a {@code
 * Phaser} may be repeatedly awaited.  Method {@link
 * #arriveAndAwaitAdvance} has effect analogous to {@link
 * java.util.concurrent.CyclicBarrier#await CyclicBarrier.await}. Each
 * generation of a phaser has an associated phase number. The phase
 * number starts at zero, and advances when all parties arrive at the
 * phaser, wrapping around to zero after reaching {@code
 * Integer.MAX_VALUE}. The use of phase numbers enables independent
 * control of actions upon arrival at a phaser and upon awaiting
 * others, via two kinds of methods that may be invoked by any
 * registered party:
 *
 * <ul>
 *
 *   <li> <b>Arrival.</b> Methods {@link #arrive} and
 *       {@link #arriveAndDeregister} record arrival.  These methods
 *       do not block, but return an associated <em>arrival phase
 *       number</em>; that is, the phase number of the phaser to which
 *       the arrival applied. When the final party for a given phase
 *       arrives, an optional action is performed and the phase
 *       advances.  These actions are performed by the party
 *       triggering a phase advance, and are arranged by overriding
 *       method {@link #onAdvance(int, int)}, which also controls
 *       termination. Overriding this method is similar to, but more
 *       flexible than, providing a barrier action to a {@code
 *       CyclicBarrier}.
 *
 *   <li> <b>Waiting.</b> Method {@link #awaitAdvance} requires an
 *       argument indicating an arrival phase number, and returns when
 *       the phaser advances to (or is already at) a different phase.
 *       Unlike similar constructions using {@code CyclicBarrier},
 *       method {@code awaitAdvance} continues to wait even if the
 *       waiting thread is interrupted. Interruptible and timeout
 *       versions are also available, but exceptions encountered while
 *       tasks wait interruptibly or with timeout do not change the
 *       state of the phaser. If necessary, you can perform any
 *       associated recovery within handlers of those exceptions,
 *       often after invoking {@code forceTermination}.  Phasers may
 *       also be used by tasks executing in a {@link ForkJoinPool},
 *       which will ensure sufficient parallelism to execute tasks
 *       when others are blocked waiting for a phase to advance.
 *
 * </ul>
 *
 * <p><b>Termination.</b> A phaser may enter a <em>termination</em>
 * state, that may be checked using method {@link #isTerminated}. Upon
 * termination, all synchronization methods immediately return without
 * waiting for advance, as indicated by a negative return value.
 * Similarly, attempts to register upon termination have no effect.
 * Termination is triggered when an invocation of {@code onAdvance}
 * returns {@code true}. The default implementation returns {@code
 * true} if a deregistration has caused the number of registered
 * parties to become zero.  As illustrated below, when phasers control
 * actions with a fixed number of iterations, it is often convenient
 * to override this method to cause termination when the current phase
 * number reaches a threshold. Method {@link #forceTermination} is
 * also available to abruptly release waiting threads and allow them
 * to terminate.
 *
 * <p><b>Tiering.</b> Phasers may be <em>tiered</em> (i.e.,
 * constructed in tree structures) to reduce contention. Phasers with
 * large numbers of parties that would otherwise experience heavy
 * synchronization contention costs may instead be set up so that
 * groups of sub-phasers share a common parent.  This may greatly
 * increase throughput even though it incurs greater per-operation
 * overhead.
 *
 * <p>In a tree of tiered phasers, registration and deregistration of
 * child phasers with their parent are managed automatically.
 * Whenever the number of registered parties of a child phaser becomes
 * non-zero (as established in the {@link #Phaser(Phaser,int)}
 * constructor, {@link #register}, or {@link #bulkRegister}), the
 * child phaser is registered with its parent.  Whenever the number of
 * registered parties becomes zero as the result of an invocation of
 * {@link #arriveAndDeregister}, the child phaser is deregistered
 * from its parent.
 *
 * <p><b>Monitoring.</b> While synchronization methods may be invoked
 * only by registered parties, the current state of a phaser may be
 * monitored by any caller.  At any given moment there are {@link
 * #getRegisteredParties} parties in total, of which {@link
 * #getArrivedParties} have arrived at the current phase ({@link
 * #getPhase}).  When the remaining ({@link #getUnarrivedParties})
 * parties arrive, the phase advances.  The values returned by these
 * methods may reflect transient states and so are not in general
 * useful for synchronization control.  Method {@link #toString}
 * returns snapshots of these state queries in a form convenient for
 * informal monitoring.
 *
 * <p><b>Sample usages:</b>
 *
 * <p>A {@code Phaser} may be used instead of a {@code CountDownLatch}
 * to control a one-shot action serving a variable number of parties.
 * The typical idiom is for the method setting this up to first
 * register, then start the actions, then deregister, as in:
 *
 *  <pre> {@code
 * void runTasks(List<Runnable> tasks) {
 *   final Phaser phaser = new Phaser(1); // "1" to register self
 *   // create and start threads
 *   for (final Runnable task : tasks) {
 *     phaser.register();
 *     new Thread() {
 *       public void run() {
 *         phaser.arriveAndAwaitAdvance(); // await all creation
 *         task.run();
 *       }
 *     }.start();
 *   }
 *
 *   // allow threads to start and deregister self
 *   phaser.arriveAndDeregister();
 * }}</pre>
 *
 * <p>One way to cause a set of threads to repeatedly perform actions
 * for a given number of iterations is to override {@code onAdvance}:
 *
 *  <pre> {@code
 * void startTasks(List<Runnable> tasks, final int iterations) {
 *   final Phaser phaser = new Phaser() {
 *     protected boolean onAdvance(int phase, int registeredParties) {
 *       return phase >= iterations || registeredParties == 0;
 *     }
 *   };
 *   phaser.register();
 *   for (final Runnable task : tasks) {
 *     phaser.register();
 *     new Thread() {
 *       public void run() {
 *         do {
 *           task.run();
 *           phaser.arriveAndAwaitAdvance();
 *         } while (!phaser.isTerminated());
 *       }
 *     }.start();
 *   }
 *   phaser.arriveAndDeregister(); // deregister self, don't wait
 * }}</pre>
 *
 * If the main task must later await termination, it
 * may re-register and then execute a similar loop:
 *  <pre> {@code
 *   // ...
 *   phaser.register();
 *   while (!phaser.isTerminated())
 *     phaser.arriveAndAwaitAdvance();}</pre>
 *
 * <p>Related constructions may be used to await particular phase numbers
 * in contexts where you are sure that the phase will never wrap around
 * {@code Integer.MAX_VALUE}. For example:
 *
 *  <pre> {@code
 * void awaitPhase(Phaser phaser, int phase) {
 *   int p = phaser.register(); // assumes caller not already registered
 *   while (p < phase) {
 *     if (phaser.isTerminated())
 *       // ... deal with unexpected termination
 *     else
 *       p = phaser.arriveAndAwaitAdvance();
 *   }
 *   phaser.arriveAndDeregister();
 * }}</pre>
 *
 *
 * <p>To create a set of {@code n} tasks using a tree of phasers, you
 * could use code of the following form, assuming a Task class with a
 * constructor accepting a {@code Phaser} that it registers with upon
 * construction. After invocation of {@code build(new Task[n], 0, n,
 * new Phaser())}, these tasks could then be started, for example by
 * submitting to a pool:
 *
 *  <pre> {@code
 * void build(Task[] tasks, int lo, int hi, Phaser ph) {
 *   if (hi - lo > TASKS_PER_PHASER) {
 *     for (int i = lo; i < hi; i += TASKS_PER_PHASER) {
 *       int j = Math.min(i + TASKS_PER_PHASER, hi);
 *       build(tasks, i, j, new Phaser(ph));
 *     }
 *   } else {
 *     for (int i = lo; i < hi; ++i)
 *       tasks[i] = new Task(ph);
 *       // assumes new Task(ph) performs ph.register()
 *   }
 * }}</pre>
 *
 * The best value of {@code TASKS_PER_PHASER} depends mainly on
 * expected synchronization rates. A value as low as four may
 * be appropriate for extremely small per-phase task bodies (thus
 * high rates), or up to hundreds for extremely large ones.
 *
 * <p><b>Implementation notes</b>: This implementation restricts the
 * maximum number of parties to 65535. Attempts to register additional
 * parties result in {@code IllegalStateException}. However, you can and
 * should create tiered phasers to accommodate arbitrarily large sets
 * of participants.
 *
 * <p>
 *  可重用的同步屏障,功能类似于{@link java.util.concurrent.CyclicBarrier CyclicBarrier}和{@link java.util.concurrent.CountDownLatch CountDownLatch}
 * ,但支持更灵活的使用。
 * 
 *  <p> <b>注册。</b>与其他障碍的情况不同,在移动电话上注册的用户<em> </em>同步的数量可能会随时间而变化。
 * 任务可以随时注册(使用方法{@link #register},{@link #bulkRegister}或构建方初始数量的形式),并且可选择在任何到达时取消注册(使用{@link #arriveAndDeregister}
 * ) 。
 *  <p> <b>注册。</b>与其他障碍的情况不同,在移动电话上注册的用户<em> </em>同步的数量可能会随时间而变化。
 * 与大多数基本同步结构的情况一样,注册和注销仅影响内部计数;他们不建立任何进一步的内部簿记,所以任务不能查询他们是否注册。 (但是,您可以通过继承此类来介绍此类记账。)。
 * 
 * <p> <b>同步。</b>与{@code CyclicBarrier}一样,{@code Phaser}可能会重复等待。
 * 方法{@link #arriveAndAwaitAdvance}的效果类似于{@link java.util.concurrent.CyclicBarrier#await CyclicBarrier.await}
 * 。
 * <p> <b>同步。</b>与{@code CyclicBarrier}一样,{@code Phaser}可能会重复等待。移相器的每一代具有相关联的相位数。
 * 阶段号从零开始,并且当所有各方到达移相器时递增,在达到{@code Integer.MAX_VALUE}后绕回零。
 * 相位编号的使用使得能够通过两种可由任何注册方调用的方法来独立控制到达移相器处和等待其他操作时的动作：。
 * 
 * <ul>
 * 
 *  <li> <b>到达。</b>方法{@link #arrive}和{@link #arriveAndDeregister}记录到达。
 * 这些方法不会阻塞,但返回相关的<em>到达阶段号码</em>;即施加到达的移相器的相位编号。当给定阶段的最后一方到达时,执行可选动作并且阶段前进。
 * 这些动作由触发相位超前的一方执行,并通过重写方法{@link #onAdvance(int,int)}进行排列,这也控制终止。
 * 覆盖此方法与向{@code CyclicBarrier}提供屏障操作类似,但更灵活。
 * 
 * <li> <b>等待。</b>方法{@link #awaitAdvance}需要一个指示到达阶段编号的参数,并且在移动台前进到(或已经处于)其他阶段时返回。
 * 与使用{@code CyclicBarrier}的类似构造不同,方法{@code awaitAdvance}继续等待,即使等待的线程被中断。
 * 中断和超时版本也可用,但是任务等待可中断或超时时遇到的异常不会更改移相器的状态。
 * 如果有必要,您可以在这些异常的处理程序内执行任何关联恢复,通常是在调用{@code forceTermination}之后。
 * 相移也可以由在{@link ForkJoinPool}中执行的任务使用,这将确保足够的并行性以在其他被阻塞等待一个阶段前进时执行任务。
 * 
 * </ul>
 * 
 * <p> <b>终止。</b>移动者可以输入<em>终止</em>状态,可以使用{@link #isTerminated}方法进行检查。在终止时,所有同步方法立即返回,而不等待提前,如负返回值所示。
 * 同样,终止注册的尝试也没有效果。当{@code onAdvance}的调用返回{@code true}时,触发终止。如果注销已导致注册方数量变为零,则默认实现将返回{@code true}。
 * 如下所示,当相位器控制具有固定次数的迭代的动作时,通常方便的是重写该方法以在当前相位数达到阈值时引起终止。
 * 方法{@link #forceTermination}也可以突然释放等待的线程,并允许它们终止。
 * 
 *  <p> <b>分层。</b> Phasers可以<em>分层</em>(即以树结构构造),以减少争用。可以替代地设置具有大量方的会产生大量同步争用成本的对等体,使得子阶段的组共享共同的父亲。
 * 这可以大大增加吞吐量,即使其引起更大的每操作开销。
 * 
 * <p>在分层相移树中,儿童移动器与其父代的注册和注销将自动管理。
 * 每当儿童移动器的注册方数量变为非零({@link #Phaser(Phaser,int)}构造函数{@link #register}或{@link #bulkRegister}中确定的值)时,子phas
 * er已向其父级注册。
 * <p>在分层相移树中,儿童移动器与其父代的注册和注销将自动管理。每当调用{@link #arriveAndDeregister}的结果,注册的对象数量变为零时,子移动器将从其父对象中注销。
 * 
 *  <p> <b>监视。</b>虽然同步方法只能由注册方调用,但移动器的当前状态可能由任何呼叫方监视。
 * 在任何给定时刻,总共有{@link #getRegisteredParties}方,其中{@link #getArrivedParties}已到达当前阶段({@link #getPhase})。
 * 当剩余的({@link #getUnarrivedParties})方到达时,阶段前进。由这些方法返回的值可以反映瞬时状态,因此对于同步控制通常不是有用的。
 * 方法{@link #toString}以便于非正式监控的形式返回这些状态查询的快照。
 * 
 *  <p> <b>示例用法：</b>
 * 
 *  <p>可以使用{@code Phaser}而不是{@code CountDownLatch}来控制服务可变数量的参与者的一次性操作。典型的习惯是为方法设置这个首先注册,然后启动动作,然后注销,如：
 * 
 * <pre> {@code void runTasks(List <Runnable> tasks){final Phaser phaser = new Phaser(1); //"1"注册自我创建和启动线程(最终Runnable任务：任务){phaser.register(); new Thread(){public void run(){phaser.arriveAndAwaitAdvance(); //等待所有创建task.run(); }
 * } .start(); }}。
 * 
 *  //允许线程启动和注销自phaser.arriveAndDeregister(); }} </pre>
 * 
 *  <p>导致一组线程针对给定数量的迭代重复执行操作的一种方法是覆盖{@code onAdvance}：
 * 
 *  <pre> {@code void startTasks(List <Runnable> tasks,final int iterations){final Phaser phaser = new Phaser(){protected boolean onAdvance(int phase,int registeredParties){return phase> = iterations || registeredParties == 0; }
 * }; phaser.register(); for(final Runnable task：tasks){phaser.register(); new Thread(){public void run(){do {task.run(); phaser.arriveAndAwaitAdvance(); }
 *  while(！phaser.isTerminated()); }} .start(); } phaser.arriveAndDeregister(); // deregister self,do no
 * t wait}} </pre>。
 * 
 *  如果主任务以后必须等待终止,它可以重新注册,然后执行一个类似的循环：<pre> {@code // ... phaser.register(); while(！phaser.isTerminated())phaser.arriveAndAwaitAdvance();}
 *  </pre>。
 * 
 *  <p>相关构造可用于在上下文中等待特定的阶段号,其中确保阶段永远不会绕过{@code Integer.MAX_VALUE}。例如：
 * 
 * <pre> {@code void awaitPhase(Phaser phaser,int phase){int p = phaser.register(); //假设调用者未注册while(p <phase){if(phaser.isTerminated())// ...处理意外终止else p = phaser.arriveAndAwaitAdvance(); } phaser.arriveAndDeregister(); }} </pre>
 * 。
 * 
 *  <p>要使用phasers树创建一组{@code n}任务,您可以使用以下形式的代码,假设任务类具有接受构造函数注册的{@code Phaser}的构造函数。
 * 在调用{@code build(new Task [n],0,n,new Phaser())}之后,可以启动这些任务,例如通过提交到池：。
 * 
 *  <pre> {@ code void build(Task [] tasks,int lo,int hi,Phaser ph){if(hi  -  lo> TASKS_PER_PHASER){for(int i = lo; i <hi; i + = TASKS_PER_PHASER){ int j = Math.min(i + TASKS_PER_PHASER,hi); build(tasks,i,j,new Phaser(ph)); }} else {for(int i = lo; i <hi; ++ i)tasks [i] = new Task(ph); //假设新的任务(ph)执行ph.register()}}} </pre>
 * 。
 * 
 *  {@code TASKS_PER_PHASER}的最佳值主要取决于预期的同步率。低至4的值可能适用于极小的每相任务主体(因此高速率),或者对于非常大的任务主体可达到数百。
 * 
 * <p> <b>实现注释</b>：此实现将最大数量限制为65535.尝试注册其他方会导致{@code IllegalStateException}。
 * 但是,您可以并且应该创建分层相移器以适应任意大的参与者集合。
 * 
 * 
 * @since 1.7
 * @author Doug Lea
 */
public class Phaser {
    /*
     * This class implements an extension of X10 "clocks".  Thanks to
     * Vijay Saraswat for the idea, and to Vivek Sarkar for
     * enhancements to extend functionality.
     * <p>
     *  这个类实现了X10"clocks"的扩展。感谢Vijay Saraswat的想法,和Vivek Sarkar的增强功能扩展。
     * 
     */

    /**
     * Primary state representation, holding four bit-fields:
     *
     * unarrived  -- the number of parties yet to hit barrier (bits  0-15)
     * parties    -- the number of parties to wait            (bits 16-31)
     * phase      -- the generation of the barrier            (bits 32-62)
     * terminated -- set if barrier is terminated             (bit  63 / sign)
     *
     * Except that a phaser with no registered parties is
     * distinguished by the otherwise illegal state of having zero
     * parties and one unarrived parties (encoded as EMPTY below).
     *
     * To efficiently maintain atomicity, these values are packed into
     * a single (atomic) long. Good performance relies on keeping
     * state decoding and encoding simple, and keeping race windows
     * short.
     *
     * All state updates are performed via CAS except initial
     * registration of a sub-phaser (i.e., one with a non-null
     * parent).  In this (relatively rare) case, we use built-in
     * synchronization to lock while first registering with its
     * parent.
     *
     * The phase of a subphaser is allowed to lag that of its
     * ancestors until it is actually accessed -- see method
     * reconcileState.
     * <p>
     *  主状态表示,保存四个位字段：
     * 
     *  未命中 - 尚未达到屏障的数目(比特0-15)方 - 要等待的方的数目(比特16-31)相位 - 生成屏障(比特32-62)终止集如果屏障终止(位63 /符号)
     * 
     *  除非没有注册方的移动者通过具有零方和非移动方的非法状态(在下面被编码为EMPTY)来区分。
     * 
     *  为了有效地维持原子性,这些值被打包成单个(原子)长。良好的性能依赖于保持状态解码和编码简单,并保持竞态窗口短。
     * 
     *  通过CAS执行所有状态更新,除了子移相器的初始注册(即,具有非空父亲的移动器)。在这种(相对罕见的)情况下,我们使用内置同步锁定,而首次注册与其父。
     * 
     * 子相位的相位允许滞后于其祖先的相位,直到它被实际访问 - 参见方法reconcileState。
     * 
     */
    private volatile long state;

    private static final int  MAX_PARTIES     = 0xffff;
    private static final int  MAX_PHASE       = Integer.MAX_VALUE;
    private static final int  PARTIES_SHIFT   = 16;
    private static final int  PHASE_SHIFT     = 32;
    private static final int  UNARRIVED_MASK  = 0xffff;      // to mask ints
    private static final long PARTIES_MASK    = 0xffff0000L; // to mask longs
    private static final long COUNTS_MASK     = 0xffffffffL;
    private static final long TERMINATION_BIT = 1L << 63;

    // some special values
    private static final int  ONE_ARRIVAL     = 1;
    private static final int  ONE_PARTY       = 1 << PARTIES_SHIFT;
    private static final int  ONE_DEREGISTER  = ONE_ARRIVAL|ONE_PARTY;
    private static final int  EMPTY           = 1;

    // The following unpacking methods are usually manually inlined

    private static int unarrivedOf(long s) {
        int counts = (int)s;
        return (counts == EMPTY) ? 0 : (counts & UNARRIVED_MASK);
    }

    private static int partiesOf(long s) {
        return (int)s >>> PARTIES_SHIFT;
    }

    private static int phaseOf(long s) {
        return (int)(s >>> PHASE_SHIFT);
    }

    private static int arrivedOf(long s) {
        int counts = (int)s;
        return (counts == EMPTY) ? 0 :
            (counts >>> PARTIES_SHIFT) - (counts & UNARRIVED_MASK);
    }

    /**
     * The parent of this phaser, or null if none
     * <p>
     *  此phaser的父级,如果没有,则为null
     * 
     */
    private final Phaser parent;

    /**
     * The root of phaser tree. Equals this if not in a tree.
     * <p>
     *  移相器树的根。等于这,如果不是在树中。
     * 
     */
    private final Phaser root;

    /**
     * Heads of Treiber stacks for waiting threads. To eliminate
     * contention when releasing some threads while adding others, we
     * use two of them, alternating across even and odd phases.
     * Subphasers share queues with root to speed up releases.
     * <p>
     *  头的Treiber堆等待的螺纹。为了在添加其他线程的同时释放某些线程时消除争用,我们使用它们中的两个,在偶数和奇数阶段交替。子进程使用root共享队列以加速发布。
     * 
     */
    private final AtomicReference<QNode> evenQ;
    private final AtomicReference<QNode> oddQ;

    private AtomicReference<QNode> queueFor(int phase) {
        return ((phase & 1) == 0) ? evenQ : oddQ;
    }

    /**
     * Returns message string for bounds exceptions on arrival.
     * <p>
     *  返回到达时的边界异常的消息字符串。
     * 
     */
    private String badArrive(long s) {
        return "Attempted arrival of unregistered party for " +
            stateToString(s);
    }

    /**
     * Returns message string for bounds exceptions on registration.
     * <p>
     *  返回注册时边界异常的消息字符串。
     * 
     */
    private String badRegister(long s) {
        return "Attempt to register more than " +
            MAX_PARTIES + " parties for " + stateToString(s);
    }

    /**
     * Main implementation for methods arrive and arriveAndDeregister.
     * Manually tuned to speed up and minimize race windows for the
     * common case of just decrementing unarrived field.
     *
     * <p>
     *  主要实现方法到达和arrivalAndDeregister。手动调整以加快和最小化种族窗口,只是减少未到达字段的常见情况。
     * 
     * 
     * @param adjust value to subtract from state;
     *               ONE_ARRIVAL for arrive,
     *               ONE_DEREGISTER for arriveAndDeregister
     */
    private int doArrive(int adjust) {
        final Phaser root = this.root;
        for (;;) {
            long s = (root == this) ? state : reconcileState();
            int phase = (int)(s >>> PHASE_SHIFT);
            if (phase < 0)
                return phase;
            int counts = (int)s;
            int unarrived = (counts == EMPTY) ? 0 : (counts & UNARRIVED_MASK);
            if (unarrived <= 0)
                throw new IllegalStateException(badArrive(s));
            if (UNSAFE.compareAndSwapLong(this, stateOffset, s, s-=adjust)) {
                if (unarrived == 1) {
                    long n = s & PARTIES_MASK;  // base of next state
                    int nextUnarrived = (int)n >>> PARTIES_SHIFT;
                    if (root == this) {
                        if (onAdvance(phase, nextUnarrived))
                            n |= TERMINATION_BIT;
                        else if (nextUnarrived == 0)
                            n |= EMPTY;
                        else
                            n |= nextUnarrived;
                        int nextPhase = (phase + 1) & MAX_PHASE;
                        n |= (long)nextPhase << PHASE_SHIFT;
                        UNSAFE.compareAndSwapLong(this, stateOffset, s, n);
                        releaseWaiters(phase);
                    }
                    else if (nextUnarrived == 0) { // propagate deregistration
                        phase = parent.doArrive(ONE_DEREGISTER);
                        UNSAFE.compareAndSwapLong(this, stateOffset,
                                                  s, s | EMPTY);
                    }
                    else
                        phase = parent.doArrive(ONE_ARRIVAL);
                }
                return phase;
            }
        }
    }

    /**
     * Implementation of register, bulkRegister
     *
     * <p>
     *  寄存器的实现,批量注册
     * 
     * 
     * @param registrations number to add to both parties and
     * unarrived fields. Must be greater than zero.
     */
    private int doRegister(int registrations) {
        // adjustment to state
        long adjust = ((long)registrations << PARTIES_SHIFT) | registrations;
        final Phaser parent = this.parent;
        int phase;
        for (;;) {
            long s = (parent == null) ? state : reconcileState();
            int counts = (int)s;
            int parties = counts >>> PARTIES_SHIFT;
            int unarrived = counts & UNARRIVED_MASK;
            if (registrations > MAX_PARTIES - parties)
                throw new IllegalStateException(badRegister(s));
            phase = (int)(s >>> PHASE_SHIFT);
            if (phase < 0)
                break;
            if (counts != EMPTY) {                  // not 1st registration
                if (parent == null || reconcileState() == s) {
                    if (unarrived == 0)             // wait out advance
                        root.internalAwaitAdvance(phase, null);
                    else if (UNSAFE.compareAndSwapLong(this, stateOffset,
                                                       s, s + adjust))
                        break;
                }
            }
            else if (parent == null) {              // 1st root registration
                long next = ((long)phase << PHASE_SHIFT) | adjust;
                if (UNSAFE.compareAndSwapLong(this, stateOffset, s, next))
                    break;
            }
            else {
                synchronized (this) {               // 1st sub registration
                    if (state == s) {               // recheck under lock
                        phase = parent.doRegister(1);
                        if (phase < 0)
                            break;
                        // finish registration whenever parent registration
                        // succeeded, even when racing with termination,
                        // since these are part of the same "transaction".
                        while (!UNSAFE.compareAndSwapLong
                               (this, stateOffset, s,
                                ((long)phase << PHASE_SHIFT) | adjust)) {
                            s = state;
                            phase = (int)(root.state >>> PHASE_SHIFT);
                            // assert (int)s == EMPTY;
                        }
                        break;
                    }
                }
            }
        }
        return phase;
    }

    /**
     * Resolves lagged phase propagation from root if necessary.
     * Reconciliation normally occurs when root has advanced but
     * subphasers have not yet done so, in which case they must finish
     * their own advance by setting unarrived to parties (or if
     * parties is zero, resetting to unregistered EMPTY state).
     *
     * <p>
     *  如有必要,解决从根的滞后相位传播。
     * 调节通常发生在root已经提前,但是subphasers还没有这样做,在这种情况下,他们必须通过设置unarrived到各方(或如果方为零,重置为未注册的EMPTY状态)完成自己的提前。
     * 
     * 
     * @return reconciled state
     */
    private long reconcileState() {
        final Phaser root = this.root;
        long s = state;
        if (root != this) {
            int phase, p;
            // CAS to root phase with current parties, tripping unarrived
            while ((phase = (int)(root.state >>> PHASE_SHIFT)) !=
                   (int)(s >>> PHASE_SHIFT) &&
                   !UNSAFE.compareAndSwapLong
                   (this, stateOffset, s,
                    s = (((long)phase << PHASE_SHIFT) |
                         ((phase < 0) ? (s & COUNTS_MASK) :
                          (((p = (int)s >>> PARTIES_SHIFT) == 0) ? EMPTY :
                           ((s & PARTIES_MASK) | p))))))
                s = state;
        }
        return s;
    }

    /**
     * Creates a new phaser with no initially registered parties, no
     * parent, and initial phase number 0. Any thread using this
     * phaser will need to first register for it.
     * <p>
     *  创建一个新的移相器,没有初始注册方,没有父,并且初始阶段编号为0.任何使用此移相器的线程将需要首先注册它。
     * 
     */
    public Phaser() {
        this(null, 0);
    }

    /**
     * Creates a new phaser with the given number of registered
     * unarrived parties, no parent, and initial phase number 0.
     *
     * <p>
     *  创建一个新的移相器,其中包含给定数量的已注册未移动方,无父项,初始阶段编号0。
     * 
     * 
     * @param parties the number of parties required to advance to the
     * next phase
     * @throws IllegalArgumentException if parties less than zero
     * or greater than the maximum number of parties supported
     */
    public Phaser(int parties) {
        this(null, parties);
    }

    /**
     * Equivalent to {@link #Phaser(Phaser, int) Phaser(parent, 0)}.
     *
     * <p>
     *  相当于{@link #Phaser(Phaser,int)Phaser(parent,0)}。
     * 
     * 
     * @param parent the parent phaser
     */
    public Phaser(Phaser parent) {
        this(parent, 0);
    }

    /**
     * Creates a new phaser with the given parent and number of
     * registered unarrived parties.  When the given parent is non-null
     * and the given number of parties is greater than zero, this
     * child phaser is registered with its parent.
     *
     * <p>
     * 使用给定的父级和已注册未移动方的数量创建新移动器。当给定父节点非空且给定数量的参与方大于零时,此子节点phaser将向其父节点注册。
     * 
     * 
     * @param parent the parent phaser
     * @param parties the number of parties required to advance to the
     * next phase
     * @throws IllegalArgumentException if parties less than zero
     * or greater than the maximum number of parties supported
     */
    public Phaser(Phaser parent, int parties) {
        if (parties >>> PARTIES_SHIFT != 0)
            throw new IllegalArgumentException("Illegal number of parties");
        int phase = 0;
        this.parent = parent;
        if (parent != null) {
            final Phaser root = parent.root;
            this.root = root;
            this.evenQ = root.evenQ;
            this.oddQ = root.oddQ;
            if (parties != 0)
                phase = parent.doRegister(1);
        }
        else {
            this.root = this;
            this.evenQ = new AtomicReference<QNode>();
            this.oddQ = new AtomicReference<QNode>();
        }
        this.state = (parties == 0) ? (long)EMPTY :
            ((long)phase << PHASE_SHIFT) |
            ((long)parties << PARTIES_SHIFT) |
            ((long)parties);
    }

    /**
     * Adds a new unarrived party to this phaser.  If an ongoing
     * invocation of {@link #onAdvance} is in progress, this method
     * may await its completion before returning.  If this phaser has
     * a parent, and this phaser previously had no registered parties,
     * this child phaser is also registered with its parent. If
     * this phaser is terminated, the attempt to register has
     * no effect, and a negative value is returned.
     *
     * <p>
     *  给这个移动者添加一个新的无名派对。如果正在进行{@link #onAdvance}的调用,则此方法可能会在返回前等待其完成。
     * 如果此移动器有父级,并且此移相器以前没有注册方,则此子移动器也向其父级注册。如果该移相器终止,则注册尝试不起作用,并返回负值。
     * 
     * 
     * @return the arrival phase number to which this registration
     * applied.  If this value is negative, then this phaser has
     * terminated, in which case registration has no effect.
     * @throws IllegalStateException if attempting to register more
     * than the maximum supported number of parties
     */
    public int register() {
        return doRegister(1);
    }

    /**
     * Adds the given number of new unarrived parties to this phaser.
     * If an ongoing invocation of {@link #onAdvance} is in progress,
     * this method may await its completion before returning.  If this
     * phaser has a parent, and the given number of parties is greater
     * than zero, and this phaser previously had no registered
     * parties, this child phaser is also registered with its parent.
     * If this phaser is terminated, the attempt to register has no
     * effect, and a negative value is returned.
     *
     * <p>
     *  将给定数量的新未携带方添加到此移相器。如果正在进行{@link #onAdvance}的调用,则此方法可能会在返回前等待其完成。
     * 如果此移动器具有父项,并且给定数量的参与方大于零,并且此移动器以前没有注册方,则此子项移动器也向其父项注册。如果该移相器终止,则注册尝试不起作用,并返回负值。
     * 
     * 
     * @param parties the number of additional parties required to
     * advance to the next phase
     * @return the arrival phase number to which this registration
     * applied.  If this value is negative, then this phaser has
     * terminated, in which case registration has no effect.
     * @throws IllegalStateException if attempting to register more
     * than the maximum supported number of parties
     * @throws IllegalArgumentException if {@code parties < 0}
     */
    public int bulkRegister(int parties) {
        if (parties < 0)
            throw new IllegalArgumentException();
        if (parties == 0)
            return getPhase();
        return doRegister(parties);
    }

    /**
     * Arrives at this phaser, without waiting for others to arrive.
     *
     * <p>It is a usage error for an unregistered party to invoke this
     * method.  However, this error may result in an {@code
     * IllegalStateException} only upon some subsequent operation on
     * this phaser, if ever.
     *
     * <p>
     *  到达这个相位器,不等待别人到达。
     * 
     *  <p>这是未注册方调用此方法的使用错误。但是,此错误可能会导致{@code IllegalStateException}只有一些后续操作此移动器,如果有。
     * 
     * 
     * @return the arrival phase number, or a negative value if terminated
     * @throws IllegalStateException if not terminated and the number
     * of unarrived parties would become negative
     */
    public int arrive() {
        return doArrive(ONE_ARRIVAL);
    }

    /**
     * Arrives at this phaser and deregisters from it without waiting
     * for others to arrive. Deregistration reduces the number of
     * parties required to advance in future phases.  If this phaser
     * has a parent, and deregistration causes this phaser to have
     * zero parties, this phaser is also deregistered from its parent.
     *
     * <p>It is a usage error for an unregistered party to invoke this
     * method.  However, this error may result in an {@code
     * IllegalStateException} only upon some subsequent operation on
     * this phaser, if ever.
     *
     * <p>
     * 到达这个phaser和deregisters从它,而不等待别人到达。注销减少了在未来阶段推进的各方的数量。如果此移动器具有父级,并且注销导致此移动者具有零个参与方,则该移相器也从其父级注销。
     * 
     *  <p>这是未注册方调用此方法的使用错误。但是,此错误可能会导致{@code IllegalStateException}只有一些后续操作此移动器,如果有。
     * 
     * 
     * @return the arrival phase number, or a negative value if terminated
     * @throws IllegalStateException if not terminated and the number
     * of registered or unarrived parties would become negative
     */
    public int arriveAndDeregister() {
        return doArrive(ONE_DEREGISTER);
    }

    /**
     * Arrives at this phaser and awaits others. Equivalent in effect
     * to {@code awaitAdvance(arrive())}.  If you need to await with
     * interruption or timeout, you can arrange this with an analogous
     * construction using one of the other forms of the {@code
     * awaitAdvance} method.  If instead you need to deregister upon
     * arrival, use {@code awaitAdvance(arriveAndDeregister())}.
     *
     * <p>It is a usage error for an unregistered party to invoke this
     * method.  However, this error may result in an {@code
     * IllegalStateException} only upon some subsequent operation on
     * this phaser, if ever.
     *
     * <p>
     *  到达这个相位器,等待别人。等效于{@code awaitAdvance(arrival())}。
     * 如果您需要等待中断或超时,您可以使用{@code awaitAdvance}方法的其他形式之一类似的结构进行排列。
     * 如果您需要在抵达时取消注册,请使用{@code awaitAdvance(arrivalAndDeregister())}。
     * 
     *  <p>这是未注册方调用此方法的使用错误。但是,此错误可能会导致{@code IllegalStateException}只有一些后续操作此移动器,如果有。
     * 
     * 
     * @return the arrival phase number, or the (negative)
     * {@linkplain #getPhase() current phase} if terminated
     * @throws IllegalStateException if not terminated and the number
     * of unarrived parties would become negative
     */
    public int arriveAndAwaitAdvance() {
        // Specialization of doArrive+awaitAdvance eliminating some reads/paths
        final Phaser root = this.root;
        for (;;) {
            long s = (root == this) ? state : reconcileState();
            int phase = (int)(s >>> PHASE_SHIFT);
            if (phase < 0)
                return phase;
            int counts = (int)s;
            int unarrived = (counts == EMPTY) ? 0 : (counts & UNARRIVED_MASK);
            if (unarrived <= 0)
                throw new IllegalStateException(badArrive(s));
            if (UNSAFE.compareAndSwapLong(this, stateOffset, s,
                                          s -= ONE_ARRIVAL)) {
                if (unarrived > 1)
                    return root.internalAwaitAdvance(phase, null);
                if (root != this)
                    return parent.arriveAndAwaitAdvance();
                long n = s & PARTIES_MASK;  // base of next state
                int nextUnarrived = (int)n >>> PARTIES_SHIFT;
                if (onAdvance(phase, nextUnarrived))
                    n |= TERMINATION_BIT;
                else if (nextUnarrived == 0)
                    n |= EMPTY;
                else
                    n |= nextUnarrived;
                int nextPhase = (phase + 1) & MAX_PHASE;
                n |= (long)nextPhase << PHASE_SHIFT;
                if (!UNSAFE.compareAndSwapLong(this, stateOffset, s, n))
                    return (int)(state >>> PHASE_SHIFT); // terminated
                releaseWaiters(phase);
                return nextPhase;
            }
        }
    }

    /**
     * Awaits the phase of this phaser to advance from the given phase
     * value, returning immediately if the current phase is not equal
     * to the given phase value or this phaser is terminated.
     *
     * <p>
     *  等待该移相器的相位从给定相位值提前,如果当前相位不等于给定相位值则立即返回,或者该移相器终止。
     * 
     * 
     * @param phase an arrival phase number, or negative value if
     * terminated; this argument is normally the value returned by a
     * previous call to {@code arrive} or {@code arriveAndDeregister}.
     * @return the next arrival phase number, or the argument if it is
     * negative, or the (negative) {@linkplain #getPhase() current phase}
     * if terminated
     */
    public int awaitAdvance(int phase) {
        final Phaser root = this.root;
        long s = (root == this) ? state : reconcileState();
        int p = (int)(s >>> PHASE_SHIFT);
        if (phase < 0)
            return phase;
        if (p == phase)
            return root.internalAwaitAdvance(phase, null);
        return p;
    }

    /**
     * Awaits the phase of this phaser to advance from the given phase
     * value, throwing {@code InterruptedException} if interrupted
     * while waiting, or returning immediately if the current phase is
     * not equal to the given phase value or this phaser is
     * terminated.
     *
     * <p>
     * 等待该移相器的阶段从给定的相位值提前,如果在等待期间中断则抛出{@code InterruptedException},或者如果当前相位不等于给定相位值则立即返回,或者该移相器被终止。
     * 
     * 
     * @param phase an arrival phase number, or negative value if
     * terminated; this argument is normally the value returned by a
     * previous call to {@code arrive} or {@code arriveAndDeregister}.
     * @return the next arrival phase number, or the argument if it is
     * negative, or the (negative) {@linkplain #getPhase() current phase}
     * if terminated
     * @throws InterruptedException if thread interrupted while waiting
     */
    public int awaitAdvanceInterruptibly(int phase)
        throws InterruptedException {
        final Phaser root = this.root;
        long s = (root == this) ? state : reconcileState();
        int p = (int)(s >>> PHASE_SHIFT);
        if (phase < 0)
            return phase;
        if (p == phase) {
            QNode node = new QNode(this, phase, true, false, 0L);
            p = root.internalAwaitAdvance(phase, node);
            if (node.wasInterrupted)
                throw new InterruptedException();
        }
        return p;
    }

    /**
     * Awaits the phase of this phaser to advance from the given phase
     * value or the given timeout to elapse, throwing {@code
     * InterruptedException} if interrupted while waiting, or
     * returning immediately if the current phase is not equal to the
     * given phase value or this phaser is terminated.
     *
     * <p>
     *  等待该移相器的阶段从给定的相位值或给定超时提前,如果在等待期间中断则抛出{@code InterruptedException},或者如果当前相位不等于给定相位值则立即返回,或者该移相器是终止。
     * 
     * 
     * @param phase an arrival phase number, or negative value if
     * terminated; this argument is normally the value returned by a
     * previous call to {@code arrive} or {@code arriveAndDeregister}.
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the next arrival phase number, or the argument if it is
     * negative, or the (negative) {@linkplain #getPhase() current phase}
     * if terminated
     * @throws InterruptedException if thread interrupted while waiting
     * @throws TimeoutException if timed out while waiting
     */
    public int awaitAdvanceInterruptibly(int phase,
                                         long timeout, TimeUnit unit)
        throws InterruptedException, TimeoutException {
        long nanos = unit.toNanos(timeout);
        final Phaser root = this.root;
        long s = (root == this) ? state : reconcileState();
        int p = (int)(s >>> PHASE_SHIFT);
        if (phase < 0)
            return phase;
        if (p == phase) {
            QNode node = new QNode(this, phase, true, true, nanos);
            p = root.internalAwaitAdvance(phase, node);
            if (node.wasInterrupted)
                throw new InterruptedException();
            else if (p == phase)
                throw new TimeoutException();
        }
        return p;
    }

    /**
     * Forces this phaser to enter termination state.  Counts of
     * registered parties are unaffected.  If this phaser is a member
     * of a tiered set of phasers, then all of the phasers in the set
     * are terminated.  If this phaser is already terminated, this
     * method has no effect.  This method may be useful for
     * coordinating recovery after one or more tasks encounter
     * unexpected exceptions.
     * <p>
     *  强制该移相器进入终止状态。注册方的数量不受影响。如果该移相器是移相器的分层集合的成员,则集合中的所有移相器被终止。如果此移相器已终止,则此方法无效。
     * 此方法可能有助于在一个或多个任务遇到意外异常后协调恢复。
     * 
     */
    public void forceTermination() {
        // Only need to change root state
        final Phaser root = this.root;
        long s;
        while ((s = root.state) >= 0) {
            if (UNSAFE.compareAndSwapLong(root, stateOffset,
                                          s, s | TERMINATION_BIT)) {
                // signal all threads
                releaseWaiters(0); // Waiters on evenQ
                releaseWaiters(1); // Waiters on oddQ
                return;
            }
        }
    }

    /**
     * Returns the current phase number. The maximum phase number is
     * {@code Integer.MAX_VALUE}, after which it restarts at
     * zero. Upon termination, the phase number is negative,
     * in which case the prevailing phase prior to termination
     * may be obtained via {@code getPhase() + Integer.MIN_VALUE}.
     *
     * <p>
     *  返回当前阶段编号。最大阶段数为{@code Integer.MAX_VALUE},之后它将从零开始重新开始。
     * 在终止时,阶段号为负,在这种情况下,终止之前的主要阶段可以通过{@code getPhase()+ Integer.MIN_VALUE}获得。
     * 
     * 
     * @return the phase number, or a negative value if terminated
     */
    public final int getPhase() {
        return (int)(root.state >>> PHASE_SHIFT);
    }

    /**
     * Returns the number of parties registered at this phaser.
     *
     * <p>
     *  返回在此移动器处注册的对方的数量。
     * 
     * 
     * @return the number of parties
     */
    public int getRegisteredParties() {
        return partiesOf(state);
    }

    /**
     * Returns the number of registered parties that have arrived at
     * the current phase of this phaser. If this phaser has terminated,
     * the returned value is meaningless and arbitrary.
     *
     * <p>
     *  返回已到达此移相器当前阶段的已注册参与方的数量。如果这个移相器已经终止,返回的值是无意义的和任意的。
     * 
     * 
     * @return the number of arrived parties
     */
    public int getArrivedParties() {
        return arrivedOf(reconcileState());
    }

    /**
     * Returns the number of registered parties that have not yet
     * arrived at the current phase of this phaser. If this phaser has
     * terminated, the returned value is meaningless and arbitrary.
     *
     * <p>
     * 返回尚未到达此移相器的当前阶段的已注册参与方的数量。如果这个移相器已经终止,返回的值是无意义的和任意的。
     * 
     * 
     * @return the number of unarrived parties
     */
    public int getUnarrivedParties() {
        return unarrivedOf(reconcileState());
    }

    /**
     * Returns the parent of this phaser, or {@code null} if none.
     *
     * <p>
     *  返回此phaser的父级,或{@code null}(如果没有)。
     * 
     * 
     * @return the parent of this phaser, or {@code null} if none
     */
    public Phaser getParent() {
        return parent;
    }

    /**
     * Returns the root ancestor of this phaser, which is the same as
     * this phaser if it has no parent.
     *
     * <p>
     *  返回此phaser的根祖先,如果没有父,则与此phaser相同。
     * 
     * 
     * @return the root ancestor of this phaser
     */
    public Phaser getRoot() {
        return root;
    }

    /**
     * Returns {@code true} if this phaser has been terminated.
     *
     * <p>
     *  如果此移动器已终止,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this phaser has been terminated
     */
    public boolean isTerminated() {
        return root.state < 0L;
    }

    /**
     * Overridable method to perform an action upon impending phase
     * advance, and to control termination. This method is invoked
     * upon arrival of the party advancing this phaser (when all other
     * waiting parties are dormant).  If this method returns {@code
     * true}, this phaser will be set to a final termination state
     * upon advance, and subsequent calls to {@link #isTerminated}
     * will return true. Any (unchecked) Exception or Error thrown by
     * an invocation of this method is propagated to the party
     * attempting to advance this phaser, in which case no advance
     * occurs.
     *
     * <p>The arguments to this method provide the state of the phaser
     * prevailing for the current transition.  The effects of invoking
     * arrival, registration, and waiting methods on this phaser from
     * within {@code onAdvance} are unspecified and should not be
     * relied on.
     *
     * <p>If this phaser is a member of a tiered set of phasers, then
     * {@code onAdvance} is invoked only for its root phaser on each
     * advance.
     *
     * <p>To support the most common use cases, the default
     * implementation of this method returns {@code true} when the
     * number of registered parties has become zero as the result of a
     * party invoking {@code arriveAndDeregister}.  You can disable
     * this behavior, thus enabling continuation upon future
     * registrations, by overriding this method to always return
     * {@code false}:
     *
     * <pre> {@code
     * Phaser phaser = new Phaser() {
     *   protected boolean onAdvance(int phase, int parties) { return false; }
     * }}</pre>
     *
     * <p>
     *  可覆盖方法,用于在即将到来的相位超前时执行动作,并控制终止。这个方法在一个移动者前进时被调用(当所有其他等待方都处于休眠状态时)。
     * 如果这个方法返回{@code true},这个phaser将在提前设置为最终终止状态,并且对{@link #isTerminated}的后续调用将返回true。
     * 调用此方法时抛出的任何(未检查的)异常或错误将传播到尝试提前此移相器的一方,在这种情况下不会发生提前。
     * 
     *  <p>此方法的参数提供了当前转换的主要状态。在{@code onAdvance}中调用到达,注册和等待方法对此移相器的影响未指定,不应依赖。
     * 
     *  <p>如果此移相器是分层的一组移相器的成员,则{@code onAdvance}仅在每个移动上的根移相器被调用。
     * 
     * <p>为了支持最常见的用例,当方调用{@code arrivalAndDeregister}的结果,注册方数量变为零时,此方法的默认实现将返回{@code true}。
     * 您可以禁用此行为,从而在以后注册时启用继续,通过覆盖此方法始终返回{@code false}：。
     * 
     *  <pre> {@code Phaser phaser = new Phaser(){protected boolean onAdvance(int phase,int parties){return false; }
     * }} </pre>。
     * 
     * 
     * @param phase the current phase number on entry to this method,
     * before this phaser is advanced
     * @param registeredParties the current number of registered parties
     * @return {@code true} if this phaser should terminate
     */
    protected boolean onAdvance(int phase, int registeredParties) {
        return registeredParties == 0;
    }

    /**
     * Returns a string identifying this phaser, as well as its
     * state.  The state, in brackets, includes the String {@code
     * "phase = "} followed by the phase number, {@code "parties = "}
     * followed by the number of registered parties, and {@code
     * "arrived = "} followed by the number of arrived parties.
     *
     * <p>
     *  返回一个字符串标识这个phaser,以及它的状态。
     * 括号中的状态包括字符串{@code"phase ="},后跟阶段编号{@code"parties ="},后面跟注注册方数量,{@code"arrived ="}后跟到达方数量。
     * 
     * 
     * @return a string identifying this phaser, as well as its state
     */
    public String toString() {
        return stateToString(reconcileState());
    }

    /**
     * Implementation of toString and string-based error messages
     * <p>
     *  实现基于toString和字符串的错误消息
     * 
     */
    private String stateToString(long s) {
        return super.toString() +
            "[phase = " + phaseOf(s) +
            " parties = " + partiesOf(s) +
            " arrived = " + arrivedOf(s) + "]";
    }

    // Waiting mechanics

    /**
     * Removes and signals threads from queue for phase.
     * <p>
     *  从队列中删除和信号线程的阶段。
     * 
     */
    private void releaseWaiters(int phase) {
        QNode q;   // first element of queue
        Thread t;  // its thread
        AtomicReference<QNode> head = (phase & 1) == 0 ? evenQ : oddQ;
        while ((q = head.get()) != null &&
               q.phase != (int)(root.state >>> PHASE_SHIFT)) {
            if (head.compareAndSet(q, q.next) &&
                (t = q.thread) != null) {
                q.thread = null;
                LockSupport.unpark(t);
            }
        }
    }

    /**
     * Variant of releaseWaiters that additionally tries to remove any
     * nodes no longer waiting for advance due to timeout or
     * interrupt. Currently, nodes are removed only if they are at
     * head of queue, which suffices to reduce memory footprint in
     * most usages.
     *
     * <p>
     *  releaseWaiters的变体,另外尝试删除任何节点,不再等待超时或中断提前。目前,只有当它们在队列的头部时,才移除节点,这足以在大多数使用中减少存储器占用。
     * 
     * 
     * @return current phase on exit
     */
    private int abortWait(int phase) {
        AtomicReference<QNode> head = (phase & 1) == 0 ? evenQ : oddQ;
        for (;;) {
            Thread t;
            QNode q = head.get();
            int p = (int)(root.state >>> PHASE_SHIFT);
            if (q == null || ((t = q.thread) != null && q.phase == p))
                return p;
            if (head.compareAndSet(q, q.next) && t != null) {
                q.thread = null;
                LockSupport.unpark(t);
            }
        }
    }

    /** The number of CPUs, for spin control */
    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * The number of times to spin before blocking while waiting for
     * advance, per arrival while waiting. On multiprocessors, fully
     * blocking and waking up a large number of threads all at once is
     * usually a very slow process, so we use rechargeable spins to
     * avoid it when threads regularly arrive: When a thread in
     * internalAwaitAdvance notices another arrival before blocking,
     * and there appear to be enough CPUs available, it spins
     * SPINS_PER_ARRIVAL more times before blocking. The value trades
     * off good-citizenship vs big unnecessary slowdowns.
     * <p>
     * 在等待提前,每个到达时等待时阻塞之前旋转的次数。
     * 在多处理器上,完全阻塞和一次唤醒大量线程通常是一个非常缓慢的过程,因此当线程定期到达时,我们使用可充电自旋来避免它：当internalAwaitAdvance中的线程在阻塞之前注意到另一个到达,要有足够
     * 的CPU可用,它会阻塞前旋转SPINS_PER_ARRIVAL多次。
     * 在等待提前,每个到达时等待时阻塞之前旋转的次数。价值交易的良好公民vs大不必要的放缓。
     * 
     */
    static final int SPINS_PER_ARRIVAL = (NCPU < 2) ? 1 : 1 << 8;

    /**
     * Possibly blocks and waits for phase to advance unless aborted.
     * Call only on root phaser.
     *
     * <p>
     *  可能阻塞并等待相位提前,除非中止。仅在根移相器上调用。
     * 
     * 
     * @param phase current phase
     * @param node if non-null, the wait node to track interrupt and timeout;
     * if null, denotes noninterruptible wait
     * @return current phase
     */
    private int internalAwaitAdvance(int phase, QNode node) {
        // assert root == this;
        releaseWaiters(phase-1);          // ensure old queue clean
        boolean queued = false;           // true when node is enqueued
        int lastUnarrived = 0;            // to increase spins upon change
        int spins = SPINS_PER_ARRIVAL;
        long s;
        int p;
        while ((p = (int)((s = state) >>> PHASE_SHIFT)) == phase) {
            if (node == null) {           // spinning in noninterruptible mode
                int unarrived = (int)s & UNARRIVED_MASK;
                if (unarrived != lastUnarrived &&
                    (lastUnarrived = unarrived) < NCPU)
                    spins += SPINS_PER_ARRIVAL;
                boolean interrupted = Thread.interrupted();
                if (interrupted || --spins < 0) { // need node to record intr
                    node = new QNode(this, phase, false, false, 0L);
                    node.wasInterrupted = interrupted;
                }
            }
            else if (node.isReleasable()) // done or aborted
                break;
            else if (!queued) {           // push onto queue
                AtomicReference<QNode> head = (phase & 1) == 0 ? evenQ : oddQ;
                QNode q = node.next = head.get();
                if ((q == null || q.phase == phase) &&
                    (int)(state >>> PHASE_SHIFT) == phase) // avoid stale enq
                    queued = head.compareAndSet(q, node);
            }
            else {
                try {
                    ForkJoinPool.managedBlock(node);
                } catch (InterruptedException ie) {
                    node.wasInterrupted = true;
                }
            }
        }

        if (node != null) {
            if (node.thread != null)
                node.thread = null;       // avoid need for unpark()
            if (node.wasInterrupted && !node.interruptible)
                Thread.currentThread().interrupt();
            if (p == phase && (p = (int)(state >>> PHASE_SHIFT)) == phase)
                return abortWait(phase); // possibly clean up on abort
        }
        releaseWaiters(phase);
        return p;
    }

    /**
     * Wait nodes for Treiber stack representing wait queue
     * <p>
     *  等待Treiber堆栈的节点表示等待队列
     */
    static final class QNode implements ForkJoinPool.ManagedBlocker {
        final Phaser phaser;
        final int phase;
        final boolean interruptible;
        final boolean timed;
        boolean wasInterrupted;
        long nanos;
        final long deadline;
        volatile Thread thread; // nulled to cancel wait
        QNode next;

        QNode(Phaser phaser, int phase, boolean interruptible,
              boolean timed, long nanos) {
            this.phaser = phaser;
            this.phase = phase;
            this.interruptible = interruptible;
            this.nanos = nanos;
            this.timed = timed;
            this.deadline = timed ? System.nanoTime() + nanos : 0L;
            thread = Thread.currentThread();
        }

        public boolean isReleasable() {
            if (thread == null)
                return true;
            if (phaser.getPhase() != phase) {
                thread = null;
                return true;
            }
            if (Thread.interrupted())
                wasInterrupted = true;
            if (wasInterrupted && interruptible) {
                thread = null;
                return true;
            }
            if (timed) {
                if (nanos > 0L) {
                    nanos = deadline - System.nanoTime();
                }
                if (nanos <= 0L) {
                    thread = null;
                    return true;
                }
            }
            return false;
        }

        public boolean block() {
            if (isReleasable())
                return true;
            else if (!timed)
                LockSupport.park(this);
            else if (nanos > 0L)
                LockSupport.parkNanos(this, nanos);
            return isReleasable();
        }
    }

    // Unsafe mechanics

    private static final sun.misc.Unsafe UNSAFE;
    private static final long stateOffset;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> k = Phaser.class;
            stateOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
