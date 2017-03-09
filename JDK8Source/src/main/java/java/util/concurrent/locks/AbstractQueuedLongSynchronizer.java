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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import sun.misc.Unsafe;

/**
 * A version of {@link AbstractQueuedSynchronizer} in
 * which synchronization state is maintained as a {@code long}.
 * This class has exactly the same structure, properties, and methods
 * as {@code AbstractQueuedSynchronizer} with the exception
 * that all state-related parameters and results are defined
 * as {@code long} rather than {@code int}. This class
 * may be useful when creating synchronizers such as
 * multilevel locks and barriers that require
 * 64 bits of state.
 *
 * <p>See {@link AbstractQueuedSynchronizer} for usage
 * notes and examples.
 *
 * <p>
 *  {@link AbstractQueuedSynchronizer}的版本,其中同步状态保留为{@code long}。
 * 这个类与{@code AbstractQueuedSynchronizer}具有完全相同的结构,属性和方法,除了所有状态相关的参数和结果被定义为{@code long}而不是{@code int}。
 * 在创建需要64位状态的同步器(例如多级锁和障碍)时,此类可能很有用。
 * 
 *  <p>有关使用说明和示例,请参阅{@link AbstractQueuedSynchronizer}。
 * 
 * 
 * @since 1.6
 * @author Doug Lea
 */
public abstract class AbstractQueuedLongSynchronizer
    extends AbstractOwnableSynchronizer
    implements java.io.Serializable {

    private static final long serialVersionUID = 7373984972572414692L;

    /*
      To keep sources in sync, the remainder of this source file is
      exactly cloned from AbstractQueuedSynchronizer, replacing class
      name and changing ints related with sync state to longs. Please
      keep it that way.
    /* <p>
    /*  为了保持源同步,此源文件的其余部分从AbstractQueuedSynchronizer中完全克隆,替换类名称并将与同步状态相关的int更改为longs。请保持这样。
    /* 
    */

    /**
     * Creates a new {@code AbstractQueuedLongSynchronizer} instance
     * with initial synchronization state of zero.
     * <p>
     *  创建一个初始同步状态为零的新{@code AbstractQueuedLongSynchronizer}实例。
     * 
     */
    protected AbstractQueuedLongSynchronizer() { }

    /**
     * Wait queue node class.
     *
     * <p>The wait queue is a variant of a "CLH" (Craig, Landin, and
     * Hagersten) lock queue. CLH locks are normally used for
     * spinlocks.  We instead use them for blocking synchronizers, but
     * use the same basic tactic of holding some of the control
     * information about a thread in the predecessor of its node.  A
     * "status" field in each node keeps track of whether a thread
     * should block.  A node is signalled when its predecessor
     * releases.  Each node of the queue otherwise serves as a
     * specific-notification-style monitor holding a single waiting
     * thread. The status field does NOT control whether threads are
     * granted locks etc though.  A thread may try to acquire if it is
     * first in the queue. But being first does not guarantee success;
     * it only gives the right to contend.  So the currently released
     * contender thread may need to rewait.
     *
     * <p>To enqueue into a CLH lock, you atomically splice it in as new
     * tail. To dequeue, you just set the head field.
     * <pre>
     *      +------+  prev +-----+       +-----+
     * head |      | <---- |     | <---- |     |  tail
     *      +------+       +-----+       +-----+
     * </pre>
     *
     * <p>Insertion into a CLH queue requires only a single atomic
     * operation on "tail", so there is a simple atomic point of
     * demarcation from unqueued to queued. Similarly, dequeuing
     * involves only updating the "head". However, it takes a bit
     * more work for nodes to determine who their successors are,
     * in part to deal with possible cancellation due to timeouts
     * and interrupts.
     *
     * <p>The "prev" links (not used in original CLH locks), are mainly
     * needed to handle cancellation. If a node is cancelled, its
     * successor is (normally) relinked to a non-cancelled
     * predecessor. For explanation of similar mechanics in the case
     * of spin locks, see the papers by Scott and Scherer at
     * http://www.cs.rochester.edu/u/scott/synchronization/
     *
     * <p>We also use "next" links to implement blocking mechanics.
     * The thread id for each node is kept in its own node, so a
     * predecessor signals the next node to wake up by traversing
     * next link to determine which thread it is.  Determination of
     * successor must avoid races with newly queued nodes to set
     * the "next" fields of their predecessors.  This is solved
     * when necessary by checking backwards from the atomically
     * updated "tail" when a node's successor appears to be null.
     * (Or, said differently, the next-links are an optimization
     * so that we don't usually need a backward scan.)
     *
     * <p>Cancellation introduces some conservatism to the basic
     * algorithms.  Since we must poll for cancellation of other
     * nodes, we can miss noticing whether a cancelled node is
     * ahead or behind us. This is dealt with by always unparking
     * successors upon cancellation, allowing them to stabilize on
     * a new predecessor, unless we can identify an uncancelled
     * predecessor who will carry this responsibility.
     *
     * <p>CLH queues need a dummy header node to get started. But
     * we don't create them on construction, because it would be wasted
     * effort if there is never contention. Instead, the node
     * is constructed and head and tail pointers are set upon first
     * contention.
     *
     * <p>Threads waiting on Conditions use the same nodes, but
     * use an additional link. Conditions only need to link nodes
     * in simple (non-concurrent) linked queues because they are
     * only accessed when exclusively held.  Upon await, a node is
     * inserted into a condition queue.  Upon signal, the node is
     * transferred to the main queue.  A special value of status
     * field is used to mark which queue a node is on.
     *
     * <p>Thanks go to Dave Dice, Mark Moir, Victor Luchangco, Bill
     * Scherer and Michael Scott, along with members of JSR-166
     * expert group, for helpful ideas, discussions, and critiques
     * on the design of this class.
     * <p>
     *  等待队列节点类。
     * 
     * <p>等待队列是"CLH"(Craig,Landin和Hagersten)锁队列的变体。 CLH锁通常用于自旋锁。
     * 我们使用它们来阻塞同步器,但是使用相同的基本策略来保存关于其节点的前导中的线程的一些控制信息。每个节点中的"状态"字段跟踪线程是否应该阻塞。节点在其前任释放时发出信号。
     * 否则队列的每个节点用作保持单个等待线程的特定通知类型的监视器。状态字段不控制线程是否授予锁等。线程可以尝试获取它是否在队列中的第一个。但是首先不能保证成功;它只给予权利来抗衡。
     * 所以当前发布的竞争者线程可能需要等待。
     * 
     *  <p>要加入到CLH锁中,您将其作为新尾部进行原子拼接。要出列,您只需设置head字段。
     * <pre>
     *  + ------ + prev + ----- + + ----- + head | | <---- | | <---- | |尾+ ------ + + ----- + + ----- +
     * </pre>
     * 
     *  <p>插入到CLH队列中只需要对"tail"执行单个原子操作,因此有一个从未排队到排队的简单原子分界点。类似地,出列仅涉及更新"头部"。
     * 然而,节点需要更多的工作来确定他们的后继者是谁,部分地处理由于超时和中断而可能的取消。
     * 
     * <p>"prev"链接(不在原始CLH锁中使用)主要用于处理取消。如果节点被取消,其后继者(通常)重新链接到未取消的前导。
     * 对于在自旋锁的情况下类似的力学的解释,参见Scott和Scherer在http://www.cs.rochester.edu/u/scott/synchronization/的论文。
     * 
     *  <p>我们还使用"下一个"链接来实现阻止力学。每个节点的线程ID保存在其自己的节点中,因此前趋信号通知下一个节点通过遍历下一个链路来确定哪个线程。
     * 后继者的确定必须避免与新排队节点的比赛,以设置其前辈的"下一个"字段。当必要时,当节点的后继者看起来为空时,通过从原子更新的"尾部"向后检查来解决这个问题。
     *  (或者,换句话说,下一链接是优化,使得我们通常不需要反向扫描。)。
     * 
     *  <p>取消向基本算法引入了一些保守性。由于我们必须轮询其他节点的取消,所以我们可能会错过注意一个被取消的节点是否在我们之前或之后。
     * 这是由取消时总是不公平的继任者处理的,允许他们稳定在一个新的前辈,除非我们能找到一个未履行的前任谁将承担这个责任。
     * 
     * <p> CLH队列需要一个虚标头节点才能开始。但是我们不会在建设上创建它们,因为如果没有争用,那么这是浪费的努力。相反,构造节点,并且在第一争用时设置头和尾指针。
     * 
     *  <p>等待的线程条件使用相同的节点,但使用额外的链接。条件仅需要链接简单(非并发)链接队列中的节点,因为它们仅在被独占保持时才被访问。等待时,将节点插入条件队列中。根据信号,节点被传送到主队列。
     * 状态字段的特殊值用于标记节点所在的队列。
     * 
     *  <p>感谢Dave Dice,Mark Moir,Victor Luchangco,Bill Scherer和Michael Scott,以及JSR-166专家组的成员,对这个课程的设计提出了有益的想
     * 法,讨论和批评。
     * 
     */
    static final class Node {
        /** Marker to indicate a node is waiting in shared mode */
        static final Node SHARED = new Node();
        /** Marker to indicate a node is waiting in exclusive mode */
        static final Node EXCLUSIVE = null;

        /** waitStatus value to indicate thread has cancelled */
        static final int CANCELLED =  1;
        /** waitStatus value to indicate successor's thread needs unparking */
        static final int SIGNAL    = -1;
        /** waitStatus value to indicate thread is waiting on condition */
        static final int CONDITION = -2;
        /**
         * waitStatus value to indicate the next acquireShared should
         * unconditionally propagate
         * <p>
         *  waitStatus值指示下一个acquireShared应无条件传播
         * 
         */
        static final int PROPAGATE = -3;

        /**
         * Status field, taking on only the values:
         *   SIGNAL:     The successor of this node is (or will soon be)
         *               blocked (via park), so the current node must
         *               unpark its successor when it releases or
         *               cancels. To avoid races, acquire methods must
         *               first indicate they need a signal,
         *               then retry the atomic acquire, and then,
         *               on failure, block.
         *   CANCELLED:  This node is cancelled due to timeout or interrupt.
         *               Nodes never leave this state. In particular,
         *               a thread with cancelled node never again blocks.
         *   CONDITION:  This node is currently on a condition queue.
         *               It will not be used as a sync queue node
         *               until transferred, at which time the status
         *               will be set to 0. (Use of this value here has
         *               nothing to do with the other uses of the
         *               field, but simplifies mechanics.)
         *   PROPAGATE:  A releaseShared should be propagated to other
         *               nodes. This is set (for head node only) in
         *               doReleaseShared to ensure propagation
         *               continues, even if other operations have
         *               since intervened.
         *   0:          None of the above
         *
         * The values are arranged numerically to simplify use.
         * Non-negative values mean that a node doesn't need to
         * signal. So, most code doesn't need to check for particular
         * values, just for sign.
         *
         * The field is initialized to 0 for normal sync nodes, and
         * CONDITION for condition nodes.  It is modified using CAS
         * (or when possible, unconditional volatile writes).
         * <p>
         * 状态字段,只接受值：SIGNAL：此节点的后继节点(或将很快)被阻塞(通过公园),因此当它释放或取消时,当前节点必须取消其后继。
         * 为了避免竞争,获取方法必须首先指示他们需要一个信号,然后重试原子获取,然后,在失败时,阻止。 CANCELED：由于超时或中断,此节点被取消。节点永远不会离开这个状态。
         * 特别地,具有被取消节点的线程从不再次阻塞。 CONDITION：此节点当前处于条件队列。它不会被用作同步队列节点,直到被传送,此时状态将被设置为0.(使用此值与字段的其他用法无关,但简化了机制。
         * )PROPAGATE：A releaseShared应该传播到其他节点。这在doReleaseShared中设置(仅用于头节点),以确保传播继续,即使其他操作已干预。 0：上面没有。
         * 
         *  这些值以数字排列以简化使用。非负值意味着节点不需要信号。所以,大多数代码不需要检查特定的值,只是为了。
         * 
         *  该字段对于正常同步节点初始化为0,对于条件节点初始化为CONDITION。它使用CAS(或在可能的情况下,无条件的易失性写入)修改。
         * 
         */
        volatile int waitStatus;

        /**
         * Link to predecessor node that current node/thread relies on
         * for checking waitStatus. Assigned during enqueuing, and nulled
         * out (for sake of GC) only upon dequeuing.  Also, upon
         * cancellation of a predecessor, we short-circuit while
         * finding a non-cancelled one, which will always exist
         * because the head node is never cancelled: A node becomes
         * head only as a result of successful acquire. A
         * cancelled thread never succeeds in acquiring, and a thread only
         * cancels itself, not any other node.
         * <p>
         * 链接到前任节点,当前节点/线程依赖它来检查waitStatus。在入队期间分配,并且只有在出队时才归零(为了GC的目的)。
         * 此外,在取消前任者时,我们在找到未取消的一个时进行短路,这将始终存在,因为头节点从未被取消：节点仅作为成功获取的结果而变为头。被取消的线程永远不会成功获取,线程只取消自身,而不是任何其他节点。
         * 
         */
        volatile Node prev;

        /**
         * Link to the successor node that the current node/thread
         * unparks upon release. Assigned during enqueuing, adjusted
         * when bypassing cancelled predecessors, and nulled out (for
         * sake of GC) when dequeued.  The enq operation does not
         * assign next field of a predecessor until after attachment,
         * so seeing a null next field does not necessarily mean that
         * node is at end of queue. However, if a next field appears
         * to be null, we can scan prev's from the tail to
         * double-check.  The next field of cancelled nodes is set to
         * point to the node itself instead of null, to make life
         * easier for isOnSyncQueue.
         * <p>
         *  链接到后续节点,当前节点/线程释放时释放。在入队期间分配,在绕过取消的前趋时调整,并在出列时取消(为了GC的目的)。
         *  enq操作不会分配前导的next字段,直到附加之后,因此看到null下一个字段不一定意味着该节点在队列的末尾。但是,如果下一个字段显示为空,我们可以从尾部扫描prev,仔细检查。
         * 被取消的节点的下一个字段被设置为指向节点本身而不是null,以使isOnSyncQueue的生活更容易。
         * 
         */
        volatile Node next;

        /**
         * The thread that enqueued this node.  Initialized on
         * construction and nulled out after use.
         * <p>
         *  将此节点入列的线程。初始化建设,使用后清零。
         * 
         */
        volatile Thread thread;

        /**
         * Link to next node waiting on condition, or the special
         * value SHARED.  Because condition queues are accessed only
         * when holding in exclusive mode, we just need a simple
         * linked queue to hold nodes while they are waiting on
         * conditions. They are then transferred to the queue to
         * re-acquire. And because conditions can only be exclusive,
         * we save a field by using special value to indicate shared
         * mode.
         * <p>
         * 链接到下一个节点等待条件,或特殊值SHARED。因为条件队列只有在保持在独占模式时才被访问,所以我们只需要一个简单的链接队列来保存节点,同时等待条件。然后将它们转移到队列中以重新获取。
         * 并且因为条件只能是排他的,我们通过使用特殊的值来指示共享模式来保存一个字段。
         * 
         */
        Node nextWaiter;

        /**
         * Returns true if node is waiting in shared mode.
         * <p>
         *  如果节点在共享模式下等待,则返回true。
         * 
         */
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        /**
         * Returns previous node, or throws NullPointerException if null.
         * Use when predecessor cannot be null.  The null check could
         * be elided, but is present to help the VM.
         *
         * <p>
         *  返回上一个节点,如果为null,则抛出NullPointerException。当前导不能为null时使用。空检查可以省略,但是存在以帮助VM。
         * 
         * 
         * @return the predecessor of this node
         */
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        Node() {    // Used to establish initial head or SHARED marker
        }

        Node(Thread thread, Node mode) {     // Used by addWaiter
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) { // Used by Condition
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    /**
     * Head of the wait queue, lazily initialized.  Except for
     * initialization, it is modified only via method setHead.  Note:
     * If head exists, its waitStatus is guaranteed not to be
     * CANCELLED.
     * <p>
     *  头等待队列,懒惰初始化。除了初始化,它只通过方法setHead修改。注意：如果头存在,它的waitStatus被保证不被CANCELLED。
     * 
     */
    private transient volatile Node head;

    /**
     * Tail of the wait queue, lazily initialized.  Modified only via
     * method enq to add new wait node.
     * <p>
     *  尾的等待队列,延迟初始化。仅通过方法enq修改以添加新的等待节点。
     * 
     */
    private transient volatile Node tail;

    /**
     * The synchronization state.
     * <p>
     *  同步状态。
     * 
     */
    private volatile long state;

    /**
     * Returns the current value of synchronization state.
     * This operation has memory semantics of a {@code volatile} read.
     * <p>
     *  返回同步状态的当前值。此操作具有{@code volatile}读取的内存语义。
     * 
     * 
     * @return current state value
     */
    protected final long getState() {
        return state;
    }

    /**
     * Sets the value of synchronization state.
     * This operation has memory semantics of a {@code volatile} write.
     * <p>
     *  设置同步状态的值。此操作具有{@code volatile}写入的存储器语义。
     * 
     * 
     * @param newState the new state value
     */
    protected final void setState(long newState) {
        state = newState;
    }

    /**
     * Atomically sets synchronization state to the given updated
     * value if the current state value equals the expected value.
     * This operation has memory semantics of a {@code volatile} read
     * and write.
     *
     * <p>
     *  如果当前状态值等于预期值,则将同步状态原子地设置为给定的更新值。此操作具有{@code volatile}读取和写入的存储器语义。
     * 
     * 
     * @param expect the expected value
     * @param update the new value
     * @return {@code true} if successful. False return indicates that the actual
     *         value was not equal to the expected value.
     */
    protected final boolean compareAndSetState(long expect, long update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapLong(this, stateOffset, expect, update);
    }

    // Queuing utilities

    /**
     * The number of nanoseconds for which it is faster to spin
     * rather than to use timed park. A rough estimate suffices
     * to improve responsiveness with very short timeouts.
     * <p>
     * 纳秒的数量,它旋转更快,而不是使用定时公园。粗略估计足以用非常短的超时提高响应性。
     * 
     */
    static final long spinForTimeoutThreshold = 1000L;

    /**
     * Inserts node into queue, initializing if necessary. See picture above.
     * <p>
     *  将节点插入队列,必要时进行初始化。见上图。
     * 
     * 
     * @param node the node to insert
     * @return node's predecessor
     */
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }

    /**
     * Creates and enqueues node for current thread and given mode.
     *
     * <p>
     *  创建和排队当前线程和给定模式的节点。
     * 
     * 
     * @param mode Node.EXCLUSIVE for exclusive, Node.SHARED for shared
     * @return the new node
     */
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    /**
     * Sets head of queue to be node, thus dequeuing. Called only by
     * acquire methods.  Also nulls out unused fields for sake of GC
     * and to suppress unnecessary signals and traversals.
     *
     * <p>
     *  将队列头设置为节点,从而出队。仅由获取方法调用。也为null为了GC和为了抑制不必要的信号和遍历的未使用的字段。
     * 
     * 
     * @param node the node
     */
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     * Wakes up node's successor, if one exists.
     *
     * <p>
     *  唤醒节点的后继(如果存在)。
     * 
     * 
     * @param node the node
     */
    private void unparkSuccessor(Node node) {
        /*
         * If status is negative (i.e., possibly needing signal) try
         * to clear in anticipation of signalling.  It is OK if this
         * fails or if status is changed by waiting thread.
         * <p>
         *  如果状态为负(即,可能需要信号)尝试在预期的信令中清除。如果这失败或者如果状态通过等待线程而改变,则是OK。
         * 
         */
        int ws = node.waitStatus;
        if (ws < 0)
            compareAndSetWaitStatus(node, ws, 0);

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         * <p>
         *  取消挂起的线程保持在后继,这通常只是下一个节点。但是如果取消或明显为空,从尾部向后遍历以找到实际的未取消后继。
         * 
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            LockSupport.unpark(s.thread);
    }

    /**
     * Release action for shared mode -- signals successor and ensures
     * propagation. (Note: For exclusive mode, release just amounts
     * to calling unparkSuccessor of head if it needs signal.)
     * <p>
     *  释放共享模式的操作 - 信号后继并确保传播。 (注意：对于独占模式,释放只是等于调用unparkSuccessor的head,如果它需要信号)。
     * 
     */
    private void doReleaseShared() {
        /*
         * Ensure that a release propagates, even if there are other
         * in-progress acquires/releases.  This proceeds in the usual
         * way of trying to unparkSuccessor of head if it needs
         * signal. But if it does not, status is set to PROPAGATE to
         * ensure that upon release, propagation continues.
         * Additionally, we must loop in case a new node is added
         * while we are doing this. Also, unlike other uses of
         * unparkSuccessor, we need to know if CAS to reset status
         * fails, if so rechecking.
         * <p>
         * 确保释放传播,即使有其他进行中的获取/释放。如果它需要信号,则以通常的方式尝试解除头的处理器。但如果不是,状态设置为PROPAGATE以确保在释放后,传播继续。
         * 另外,我们必须循环,以便在我们这样做的时候添加一个新的节点。此外,与unparkSuccessor的其他用法不同,我们需要知道CAS是否重置状态失败,如果这样重新检查。
         * 
         */
        for (;;) {
            Node h = head;
            if (h != null && h != tail) {
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0))
                        continue;            // loop to recheck cases
                    unparkSuccessor(h);
                }
                else if (ws == 0 &&
                         !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // loop on failed CAS
            }
            if (h == head)                   // loop if head changed
                break;
        }
    }

    /**
     * Sets head of queue, and checks if successor may be waiting
     * in shared mode, if so propagating if either propagate > 0 or
     * PROPAGATE status was set.
     *
     * <p>
     *  设置队列头,并检查后继者是否可能在共享模式下等待,如果传播> 0或PROPAGATE状态,则传播。
     * 
     * 
     * @param node the node
     * @param propagate the return value from a tryAcquireShared
     */
    private void setHeadAndPropagate(Node node, long propagate) {
        Node h = head; // Record old head for check below
        setHead(node);
        /*
         * Try to signal next queued node if:
         *   Propagation was indicated by caller,
         *     or was recorded (as h.waitStatus either before
         *     or after setHead) by a previous operation
         *     (note: this uses sign-check of waitStatus because
         *      PROPAGATE status may transition to SIGNAL.)
         * and
         *   The next node is waiting in shared mode,
         *     or we don't know, because it appears null
         *
         * The conservatism in both of these checks may cause
         * unnecessary wake-ups, but only when there are multiple
         * racing acquires/releases, so most need signals now or soon
         * anyway.
         * <p>
         *  尝试向下一个排队节点发出信号if：传播由呼叫者指示,或者由前一个操作记录(作为在setHead之前或之后的h.waitStatus)(注意：这使用waitStatus的符号检查,因为PROPAGATE
         * 状态可能转换为SIGNAL。
         *  )和下一个节点正在等待共享模式,或者我们不知道,因为它显示为null。
         * 
         *  这些检查中的保守性可能导致不必要的唤醒,但是只有当有多个赛车获取/释放时,所以大多数现在或很快需要信号。
         * 
         */
        if (propagate > 0 || h == null || h.waitStatus < 0 ||
            (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared())
                doReleaseShared();
        }
    }

    // Utilities for various versions of acquire

    /**
     * Cancels an ongoing attempt to acquire.
     *
     * <p>
     *  取消正在进行的尝试获取。
     * 
     * 
     * @param node the node
     */
    private void cancelAcquire(Node node) {
        // Ignore if node doesn't exist
        if (node == null)
            return;

        node.thread = null;

        // Skip cancelled predecessors
        Node pred = node.prev;
        while (pred.waitStatus > 0)
            node.prev = pred = pred.prev;

        // predNext is the apparent node to unsplice. CASes below will
        // fail if not, in which case, we lost race vs another cancel
        // or signal, so no further action is necessary.
        Node predNext = pred.next;

        // Can use unconditional write instead of CAS here.
        // After this atomic step, other Nodes can skip past us.
        // Before, we are free of interference from other threads.
        node.waitStatus = Node.CANCELLED;

        // If we are the tail, remove ourselves.
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            // If successor needs signal, try to set pred's next-link
            // so it will get one. Otherwise wake it up to propagate.
            int ws;
            if (pred != head &&
                ((ws = pred.waitStatus) == Node.SIGNAL ||
                 (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
                pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0)
                    compareAndSetNext(pred, predNext, next);
            } else {
                unparkSuccessor(node);
            }

            node.next = node; // help GC
        }
    }

    /**
     * Checks and updates status for a node that failed to acquire.
     * Returns true if thread should block. This is the main signal
     * control in all acquire loops.  Requires that pred == node.prev.
     *
     * <p>
     *  检查和更新未能获取的节点的状态。如果线程应阻塞,则返回true。这是所有采集环路中的主要信号控制。需要pred == node.prev。
     * 
     * 
     * @param pred node's predecessor holding status
     * @param node the node
     * @return {@code true} if thread should block
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * This node has already set status asking a release
             * to signal it, so it can safely park.
             * <p>
             *  此节点已设置状态,要求发布信号,以便它可以安全地停放。
             * 
             */
            return true;
        if (ws > 0) {
            /*
             * Predecessor was cancelled. Skip over predecessors and
             * indicate retry.
             * <p>
             * 前导已取消。跳过前辈并指示重试。
             * 
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * waitStatus must be 0 or PROPAGATE.  Indicate that we
             * need a signal, but don't park yet.  Caller will need to
             * retry to make sure it cannot acquire before parking.
             * <p>
             *  waitStatus必须为0或PROPAGATE。表示我们需要一个信号,但不停泊。来电者需要重试,以确保在停车之前无法获取。
             * 
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    /**
     * Convenience method to interrupt current thread.
     * <p>
     *  方便中断当前线程。
     * 
     */
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    /**
     * Convenience method to park and then check if interrupted
     *
     * <p>
     *  方便的方法停车,然后检查是否中断
     * 
     * 
     * @return {@code true} if interrupted
     */
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    /*
     * Various flavors of acquire, varying in exclusive/shared and
     * control modes.  Each is mostly the same, but annoyingly
     * different.  Only a little bit of factoring is possible due to
     * interactions of exception mechanics (including ensuring that we
     * cancel if tryAcquire throws exception) and other control, at
     * least not without hurting performance too much.
     * <p>
     *  各种风味的获取,不同的排他/共享和控制模式。每个都大体相同,但令人讨厌的不同。
     * 只有一点因子分解是可能的,因为异常机制的交互(包括确保我们取消,如果tryAcquire抛出异常)和其他控制,至少不会伤害性能太多。
     * 
     */

    /**
     * Acquires in exclusive uninterruptible mode for thread already in
     * queue. Used by condition wait methods as well as acquire.
     *
     * <p>
     *  以线程已经在队列中的独占不间断模式获取。由条件等待方法使用以及获取。
     * 
     * 
     * @param node the node
     * @param arg the acquire argument
     * @return {@code true} if interrupted while waiting
     */
    final boolean acquireQueued(final Node node, long arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in exclusive interruptible mode.
     * <p>
     *  以独占中断模式获取。
     * 
     * 
     * @param arg the acquire argument
     */
    private void doAcquireInterruptibly(long arg)
        throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in exclusive timed mode.
     *
     * <p>
     *  以独占定时模式获取。
     * 
     * 
     * @param arg the acquire argument
     * @param nanosTimeout max wait time
     * @return {@code true} if acquired
     */
    private boolean doAcquireNanos(long arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                    nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in shared uninterruptible mode.
     * <p>
     *  以共享不间断模式获取。
     * 
     * 
     * @param arg the acquire argument
     */
    private void doAcquireShared(long arg) {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    long r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in shared interruptible mode.
     * <p>
     *  在共享可中断模式下获取。
     * 
     * 
     * @param arg the acquire argument
     */
    private void doAcquireSharedInterruptibly(long arg)
        throws InterruptedException {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    long r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in shared timed mode.
     *
     * <p>
     *  以共享定时模式获取。
     * 
     * 
     * @param arg the acquire argument
     * @param nanosTimeout max wait time
     * @return {@code true} if acquired
     */
    private boolean doAcquireSharedNanos(long arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    long r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                    nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    // Main exported methods

    /**
     * Attempts to acquire in exclusive mode. This method should query
     * if the state of the object permits it to be acquired in the
     * exclusive mode, and if so to acquire it.
     *
     * <p>This method is always invoked by the thread performing
     * acquire.  If this method reports failure, the acquire method
     * may queue the thread, if it is not already queued, until it is
     * signalled by a release from some other thread. This can be used
     * to implement method {@link Lock#tryLock()}.
     *
     * <p>The default
     * implementation throws {@link UnsupportedOperationException}.
     *
     * <p>
     *  尝试以独占模式获取。此方法应查询对象的状态是否允许在独占模式下获取该对象的状态,如果是,则查询是否获取该对象的状态。
     * 
     *  <p>此方法总是由执行获取的线程调用。如果此方法报告失败,则获取方法可以对线程排队,如果它还没有排队,直到它被来自某个其他线程的释放发信号通知。
     * 这可以用于实现方法{@link Lock#tryLock()}。
     * 
     * <p>默认实现会抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @param arg the acquire argument. This value is always the one
     *        passed to an acquire method, or is the value saved on entry
     *        to a condition wait.  The value is otherwise uninterpreted
     *        and can represent anything you like.
     * @return {@code true} if successful. Upon success, this object has
     *         been acquired.
     * @throws IllegalMonitorStateException if acquiring would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if exclusive mode is not supported
     */
    protected boolean tryAcquire(long arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to set the state to reflect a release in exclusive
     * mode.
     *
     * <p>This method is always invoked by the thread performing release.
     *
     * <p>The default implementation throws
     * {@link UnsupportedOperationException}.
     *
     * <p>
     *  尝试设置状态以反映独占模式下的发布。
     * 
     *  <p>此方法总是由执行释放的线程调用。
     * 
     *  <p>默认实现会抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @param arg the release argument. This value is always the one
     *        passed to a release method, or the current state value upon
     *        entry to a condition wait.  The value is otherwise
     *        uninterpreted and can represent anything you like.
     * @return {@code true} if this object is now in a fully released
     *         state, so that any waiting threads may attempt to acquire;
     *         and {@code false} otherwise.
     * @throws IllegalMonitorStateException if releasing would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if exclusive mode is not supported
     */
    protected boolean tryRelease(long arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to acquire in shared mode. This method should query if
     * the state of the object permits it to be acquired in the shared
     * mode, and if so to acquire it.
     *
     * <p>This method is always invoked by the thread performing
     * acquire.  If this method reports failure, the acquire method
     * may queue the thread, if it is not already queued, until it is
     * signalled by a release from some other thread.
     *
     * <p>The default implementation throws {@link
     * UnsupportedOperationException}.
     *
     * <p>
     *  尝试在共享模式下获取。此方法应查询对象的状态是否允许在共享模式下获取它,如果是这样,则获取它。
     * 
     *  <p>此方法总是由执行获取的线程调用。如果此方法报告失败,则获取方法可以对线程排队,如果它还没有排队,直到它被来自某个其他线程的释放发信号通知。
     * 
     *  <p>默认实现会抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @param arg the acquire argument. This value is always the one
     *        passed to an acquire method, or is the value saved on entry
     *        to a condition wait.  The value is otherwise uninterpreted
     *        and can represent anything you like.
     * @return a negative value on failure; zero if acquisition in shared
     *         mode succeeded but no subsequent shared-mode acquire can
     *         succeed; and a positive value if acquisition in shared
     *         mode succeeded and subsequent shared-mode acquires might
     *         also succeed, in which case a subsequent waiting thread
     *         must check availability. (Support for three different
     *         return values enables this method to be used in contexts
     *         where acquires only sometimes act exclusively.)  Upon
     *         success, this object has been acquired.
     * @throws IllegalMonitorStateException if acquiring would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if shared mode is not supported
     */
    protected long tryAcquireShared(long arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Attempts to set the state to reflect a release in shared mode.
     *
     * <p>This method is always invoked by the thread performing release.
     *
     * <p>The default implementation throws
     * {@link UnsupportedOperationException}.
     *
     * <p>
     *  尝试设置状态以反映共享模式下的释放。
     * 
     *  <p>此方法总是由执行释放的线程调用。
     * 
     *  <p>默认实现会抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @param arg the release argument. This value is always the one
     *        passed to a release method, or the current state value upon
     *        entry to a condition wait.  The value is otherwise
     *        uninterpreted and can represent anything you like.
     * @return {@code true} if this release of shared mode may permit a
     *         waiting acquire (shared or exclusive) to succeed; and
     *         {@code false} otherwise
     * @throws IllegalMonitorStateException if releasing would place this
     *         synchronizer in an illegal state. This exception must be
     *         thrown in a consistent fashion for synchronization to work
     *         correctly.
     * @throws UnsupportedOperationException if shared mode is not supported
     */
    protected boolean tryReleaseShared(long arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if synchronization is held exclusively with
     * respect to the current (calling) thread.  This method is invoked
     * upon each call to a non-waiting {@link ConditionObject} method.
     * (Waiting methods instead invoke {@link #release}.)
     *
     * <p>The default implementation throws {@link
     * UnsupportedOperationException}. This method is invoked
     * internally only within {@link ConditionObject} methods, so need
     * not be defined if conditions are not used.
     *
     * <p>
     *  如果同步相对于当前(调用)线程排他地保持,则返回{@code true}。每次调用不等待的{@link ConditionObject}方法时,将调用此方法。
     *  (等待方法调用{@link #release}。)。
     * 
     *  <p>默认实现会抛出{@link UnsupportedOperationException}。
     * 此方法仅在{@link ConditionObject}方法内部调用,因此如果不使用条件,则不需要定义。
     * 
     * 
     * @return {@code true} if synchronization is held exclusively;
     *         {@code false} otherwise
     * @throws UnsupportedOperationException if conditions are not supported
     */
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    /**
     * Acquires in exclusive mode, ignoring interrupts.  Implemented
     * by invoking at least once {@link #tryAcquire},
     * returning on success.  Otherwise the thread is queued, possibly
     * repeatedly blocking and unblocking, invoking {@link
     * #tryAcquire} until success.  This method can be used
     * to implement method {@link Lock#lock}.
     *
     * <p>
     * 以独占模式获取,忽略中断。通过调用至少一次{@link #tryAcquire}实现,返回成功。否则线程排队,可能反复阻塞和解除阻塞,调用{@link #tryAcquire}直到成功。
     * 此方法可用于实现方法{@link Lock#lock}。
     * 
     * 
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     */
    public final void acquire(long arg) {
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }

    /**
     * Acquires in exclusive mode, aborting if interrupted.
     * Implemented by first checking interrupt status, then invoking
     * at least once {@link #tryAcquire}, returning on
     * success.  Otherwise the thread is queued, possibly repeatedly
     * blocking and unblocking, invoking {@link #tryAcquire}
     * until success or the thread is interrupted.  This method can be
     * used to implement method {@link Lock#lockInterruptibly}.
     *
     * <p>
     *  以独占模式获取,如果中断,则中止。实现通过首先检查中断状态,然后调用至少一次{@link #tryAcquire},成功返回。
     * 否则线程排队,可能重复阻塞和解除阻塞,调用{@link #tryAcquire},直到成功或线程中断。此方法可用于实现方法{@link Lock#lockInterruptibly}。
     * 
     * 
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     * @throws InterruptedException if the current thread is interrupted
     */
    public final void acquireInterruptibly(long arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    /**
     * Attempts to acquire in exclusive mode, aborting if interrupted,
     * and failing if the given timeout elapses.  Implemented by first
     * checking interrupt status, then invoking at least once {@link
     * #tryAcquire}, returning on success.  Otherwise, the thread is
     * queued, possibly repeatedly blocking and unblocking, invoking
     * {@link #tryAcquire} until success or the thread is interrupted
     * or the timeout elapses.  This method can be used to implement
     * method {@link Lock#tryLock(long, TimeUnit)}.
     *
     * <p>
     *  尝试以独占模式获取,如果中断则中止,如果超过给定超时,则失败。实现通过首先检查中断状态,然后调用至少一次{@link #tryAcquire},成功返回。
     * 否则,线程排队,可能反复阻塞和解除阻塞,调用{@link #tryAcquire},直到成功或线程中断或超时。
     * 此方法可用于实现方法{@link Lock#tryLock(long,TimeUnit)}。
     * 
     * 
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquire} but is otherwise uninterpreted and
     *        can represent anything you like.
     * @param nanosTimeout the maximum number of nanoseconds to wait
     * @return {@code true} if acquired; {@code false} if timed out
     * @throws InterruptedException if the current thread is interrupted
     */
    public final boolean tryAcquireNanos(long arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) ||
            doAcquireNanos(arg, nanosTimeout);
    }

    /**
     * Releases in exclusive mode.  Implemented by unblocking one or
     * more threads if {@link #tryRelease} returns true.
     * This method can be used to implement method {@link Lock#unlock}.
     *
     * <p>
     *  以独占模式发布。如果{@link #tryRelease}返回true,则通过取消屏蔽一个或多个线程来实现。这个方法可以用来实现{@link Lock#unlock}的方法。
     * 
     * 
     * @param arg the release argument.  This value is conveyed to
     *        {@link #tryRelease} but is otherwise uninterpreted and
     *        can represent anything you like.
     * @return the value returned from {@link #tryRelease}
     */
    public final boolean release(long arg) {
        if (tryRelease(arg)) {
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    /**
     * Acquires in shared mode, ignoring interrupts.  Implemented by
     * first invoking at least once {@link #tryAcquireShared},
     * returning on success.  Otherwise the thread is queued, possibly
     * repeatedly blocking and unblocking, invoking {@link
     * #tryAcquireShared} until success.
     *
     * <p>
     * 在共享模式下获取,忽略中断。通过首先调用至少一次{@link #tryAcquireShared}实现,返回成功。
     * 否则线程排队,可能反复阻塞和解除阻塞,调用{@link #tryAcquireShared}直到成功。
     * 
     * 
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquireShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     */
    public final void acquireShared(long arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    /**
     * Acquires in shared mode, aborting if interrupted.  Implemented
     * by first checking interrupt status, then invoking at least once
     * {@link #tryAcquireShared}, returning on success.  Otherwise the
     * thread is queued, possibly repeatedly blocking and unblocking,
     * invoking {@link #tryAcquireShared} until success or the thread
     * is interrupted.
     * <p>
     *  在共享模式下获取,如果中断,则中止。通过首先检查中断状态,然后调用至少一次{@link #tryAcquireShared},返回成功。
     * 否则,线程排队,可能反复阻塞和解除阻塞,调用{@link #tryAcquireShared}直到成功或线程中断。
     * 
     * 
     * @param arg the acquire argument.
     * This value is conveyed to {@link #tryAcquireShared} but is
     * otherwise uninterpreted and can represent anything
     * you like.
     * @throws InterruptedException if the current thread is interrupted
     */
    public final void acquireSharedInterruptibly(long arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }

    /**
     * Attempts to acquire in shared mode, aborting if interrupted, and
     * failing if the given timeout elapses.  Implemented by first
     * checking interrupt status, then invoking at least once {@link
     * #tryAcquireShared}, returning on success.  Otherwise, the
     * thread is queued, possibly repeatedly blocking and unblocking,
     * invoking {@link #tryAcquireShared} until success or the thread
     * is interrupted or the timeout elapses.
     *
     * <p>
     *  尝试在共享模式下获取,如果中断,则中止,如果给定的超时时间失败。通过首先检查中断状态,然后调用至少一次{@link #tryAcquireShared},返回成功。
     * 否则,线程排队,可能反复阻塞和解除阻塞,调用{@link #tryAcquireShared}直到成功或线程中断或超时。
     * 
     * 
     * @param arg the acquire argument.  This value is conveyed to
     *        {@link #tryAcquireShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     * @param nanosTimeout the maximum number of nanoseconds to wait
     * @return {@code true} if acquired; {@code false} if timed out
     * @throws InterruptedException if the current thread is interrupted
     */
    public final boolean tryAcquireSharedNanos(long arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
            doAcquireSharedNanos(arg, nanosTimeout);
    }

    /**
     * Releases in shared mode.  Implemented by unblocking one or more
     * threads if {@link #tryReleaseShared} returns true.
     *
     * <p>
     *  在共享模式下释放。如果{@link #tryReleaseShared}返回true,则通过解除一个或多个线程来实现。
     * 
     * 
     * @param arg the release argument.  This value is conveyed to
     *        {@link #tryReleaseShared} but is otherwise uninterpreted
     *        and can represent anything you like.
     * @return the value returned from {@link #tryReleaseShared}
     */
    public final boolean releaseShared(long arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    // Queue inspection methods

    /**
     * Queries whether any threads are waiting to acquire. Note that
     * because cancellations due to interrupts and timeouts may occur
     * at any time, a {@code true} return does not guarantee that any
     * other thread will ever acquire.
     *
     * <p>In this implementation, this operation returns in
     * constant time.
     *
     * <p>
     *  查询是否有任何线程正在等待获取。注意,因为中断和超时的取消可能在任何时候发生,{@code true}返回不保证任何其他线程将永远获得。
     * 
     *  <p>在此实现中,此操作在常量时间返回。
     * 
     * 
     * @return {@code true} if there may be other threads waiting to acquire
     */
    public final boolean hasQueuedThreads() {
        return head != tail;
    }

    /**
     * Queries whether any threads have ever contended to acquire this
     * synchronizer; that is if an acquire method has ever blocked.
     *
     * <p>In this implementation, this operation returns in
     * constant time.
     *
     * <p>
     * 查询是否有任何线程曾争取获取此同步器;也就是说,如果获取方法被阻止。
     * 
     *  <p>在此实现中,此操作在常量时间返回。
     * 
     * 
     * @return {@code true} if there has ever been contention
     */
    public final boolean hasContended() {
        return head != null;
    }

    /**
     * Returns the first (longest-waiting) thread in the queue, or
     * {@code null} if no threads are currently queued.
     *
     * <p>In this implementation, this operation normally returns in
     * constant time, but may iterate upon contention if other threads are
     * concurrently modifying the queue.
     *
     * <p>
     *  返回队列中的第一个(最长等待)线程,如果当前没有线程排队,则返回{@code null}。
     * 
     *  <p>在此实现中,此操作通常以恒定时间返回,但如果其他线程正在同时修改队列,则可能在争用时重复。
     * 
     * 
     * @return the first (longest-waiting) thread in the queue, or
     *         {@code null} if no threads are currently queued
     */
    public final Thread getFirstQueuedThread() {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }

    /**
     * Version of getFirstQueuedThread called when fastpath fails
     * <p>
     *  当fastpath失败时调用getFirstQueuedThread的版本
     * 
     */
    private Thread fullGetFirstQueuedThread() {
        /*
         * The first node is normally head.next. Try to get its
         * thread field, ensuring consistent reads: If thread
         * field is nulled out or s.prev is no longer head, then
         * some other thread(s) concurrently performed setHead in
         * between some of our reads. We try this twice before
         * resorting to traversal.
         * <p>
         *  第一个节点通常是head.next。尝试获取其线程字段,以确保一致的读取：如果线程字段为null或s.prev不再head,那么一些其他线程并发执行setHead在我们的一些读取之间。
         * 我们尝试这两次,然后求助于遍历。
         * 
         */
        Node h, s;
        Thread st;
        if (((h = head) != null && (s = h.next) != null &&
             s.prev == head && (st = s.thread) != null) ||
            ((h = head) != null && (s = h.next) != null &&
             s.prev == head && (st = s.thread) != null))
            return st;

        /*
         * Head's next field might not have been set yet, or may have
         * been unset after setHead. So we must check to see if tail
         * is actually first node. If not, we continue on, safely
         * traversing from tail back to head to find first,
         * guaranteeing termination.
         * <p>
         *  Head的下一个字段可能尚未设置,或者可能在setHead之后未设置。所以我们必须检查是否尾部实际上是第一个节点。如果没有,我们继续,安全地从尾部回到头找到第一,保证终止。
         * 
         */

        Node t = tail;
        Thread firstThread = null;
        while (t != null && t != head) {
            Thread tt = t.thread;
            if (tt != null)
                firstThread = tt;
            t = t.prev;
        }
        return firstThread;
    }

    /**
     * Returns true if the given thread is currently queued.
     *
     * <p>This implementation traverses the queue to determine
     * presence of the given thread.
     *
     * <p>
     *  如果给定的线程当前正在排队,则返回true。
     * 
     *  <p>此实现遍历队列以确定给定线程的存在。
     * 
     * 
     * @param thread the thread
     * @return {@code true} if the given thread is on the queue
     * @throws NullPointerException if the thread is null
     */
    public final boolean isQueued(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    /**
     * Returns {@code true} if the apparent first queued thread, if one
     * exists, is waiting in exclusive mode.  If this method returns
     * {@code true}, and the current thread is attempting to acquire in
     * shared mode (that is, this method is invoked from {@link
     * #tryAcquireShared}) then it is guaranteed that the current thread
     * is not the first queued thread.  Used only as a heuristic in
     * ReentrantReadWriteLock.
     * <p>
     * 如果明显的第一个排队的线程(如果存在)返回{@code true},则以独占模式等待。
     * 如果此方法返回{@code true},并且当前线程尝试以共享模式获取(即,此方法从{@link #tryAcquireShared}调用),那么它保证当前线程不是第一个排队线。
     * 仅用作ReentrantReadWriteLock中的启发式算法。
     * 
     */
    final boolean apparentlyFirstQueuedIsExclusive() {
        Node h, s;
        return (h = head) != null &&
            (s = h.next)  != null &&
            !s.isShared()         &&
            s.thread != null;
    }

    /**
     * Queries whether any threads have been waiting to acquire longer
     * than the current thread.
     *
     * <p>An invocation of this method is equivalent to (but may be
     * more efficient than):
     *  <pre> {@code
     * getFirstQueuedThread() != Thread.currentThread() &&
     * hasQueuedThreads()}</pre>
     *
     * <p>Note that because cancellations due to interrupts and
     * timeouts may occur at any time, a {@code true} return does not
     * guarantee that some other thread will acquire before the current
     * thread.  Likewise, it is possible for another thread to win a
     * race to enqueue after this method has returned {@code false},
     * due to the queue being empty.
     *
     * <p>This method is designed to be used by a fair synchronizer to
     * avoid <a href="AbstractQueuedSynchronizer.html#barging">barging</a>.
     * Such a synchronizer's {@link #tryAcquire} method should return
     * {@code false}, and its {@link #tryAcquireShared} method should
     * return a negative value, if this method returns {@code true}
     * (unless this is a reentrant acquire).  For example, the {@code
     * tryAcquire} method for a fair, reentrant, exclusive mode
     * synchronizer might look like this:
     *
     *  <pre> {@code
     * protected boolean tryAcquire(int arg) {
     *   if (isHeldExclusively()) {
     *     // A reentrant acquire; increment hold count
     *     return true;
     *   } else if (hasQueuedPredecessors()) {
     *     return false;
     *   } else {
     *     // try to acquire normally
     *   }
     * }}</pre>
     *
     * <p>
     *  查询任何线程是否已经等待获取时间长于当前线程。
     * 
     *  <p>此方法的调用等效于(但可能比)更有效：<pre> {@code getFirstQueuedThread()！= Thread.currentThread()&& hasQueuedThreads()}
     *  </pre>。
     * 
     *  <p>请注意,因为由于中断和超时而导致的取消可能在任何时间发生,{@code true}返回并不保证其他线程将在当前线程之前获取。
     * 同样,由于队列为空,因此在此方法返回{@code false}之后,另一个线程可能赢得比赛以排队。
     * 
     *  <p>此方法旨在由公平同步器使用,以避免<a href="AbstractQueuedSynchronizer.html#barging">驳船</a>。
     * 这种同步器的{@link #tryAcquire}方法应该返回{@code false},并且其{@link #tryAcquireShared}方法应返回一个负值,如果此方法返回{@code true}
     * (除非这是可重入获取) 。
     *  <p>此方法旨在由公平同步器使用,以避免<a href="AbstractQueuedSynchronizer.html#barging">驳船</a>。
     * 例如,用于公平,可重入,独占模式同步器的{@code tryAcquire}方法可能如下所示：。
     * 
     * <pre> {@code protected boolean tryAcquire(int arg){if(isHeldExclusively()){//可重入获取; increment hold count return true; }
     *  else if(hasQueuedPredecessors()){return false; } else {// try to acquired usual}}} </pre>。
     * 
     * 
     * @return {@code true} if there is a queued thread preceding the
     *         current thread, and {@code false} if the current thread
     *         is at the head of the queue or the queue is empty
     * @since 1.7
     */
    public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        Node s;
        return h != t &&
            ((s = h.next) == null || s.thread != Thread.currentThread());
    }


    // Instrumentation and monitoring methods

    /**
     * Returns an estimate of the number of threads waiting to
     * acquire.  The value is only an estimate because the number of
     * threads may change dynamically while this method traverses
     * internal data structures.  This method is designed for use in
     * monitoring system state, not for synchronization
     * control.
     *
     * <p>
     *  返回等待获取的线程数的估计值。该值只是一个估计值,因为线程的数量可能会在此方法遍历内部数据结构时动态更改。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return the estimated number of threads waiting to acquire
     */
    public final int getQueueLength() {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev) {
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire.  Because the actual set of threads may change
     * dynamically while constructing this result, the returned
     * collection is only a best-effort estimate.  The elements of the
     * returned collection are in no particular order.  This method is
     * designed to facilitate construction of subclasses that provide
     * more extensive monitoring facilities.
     *
     * <p>
     *  返回包含可能正在等待获取的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。该方法被设计为便于构建提供更广泛的监测设施的子类。
     * 
     * 
     * @return the collection of threads
     */
    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire in exclusive mode. This has the same properties
     * as {@link #getQueuedThreads} except that it only returns
     * those threads waiting due to an exclusive acquire.
     *
     * <p>
     *  返回包含可能正在等待以独占模式获取的线程的集合。这具有与{@link #getQueuedThreads}相同的属性,除了它只返回由于独占获取而等待的线程。
     * 
     * 
     * @return the collection of threads
     */
    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (!p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    /**
     * Returns a collection containing threads that may be waiting to
     * acquire in shared mode. This has the same properties
     * as {@link #getQueuedThreads} except that it only returns
     * those threads waiting due to a shared acquire.
     *
     * <p>
     *  返回包含可能在共享模式下等待获取的线程的集合。这具有与{@link #getQueuedThreads}相同的属性,除了它只返回由于共享获取而等待的线程。
     * 
     * 
     * @return the collection of threads
     */
    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    /**
     * Returns a string identifying this synchronizer, as well as its state.
     * The state, in brackets, includes the String {@code "State ="}
     * followed by the current value of {@link #getState}, and either
     * {@code "nonempty"} or {@code "empty"} depending on whether the
     * queue is empty.
     *
     * <p>
     * 返回标识此同步器的字符串及其状态。
     * 括号中的状态包括后跟{@link #getState}的当前值的字符串{@code"State ="},以及{@code"非空"}或{@code"空"},具体取决于队列为空。
     * 
     * 
     * @return a string identifying this synchronizer, as well as its state
     */
    public String toString() {
        long s = getState();
        String q  = hasQueuedThreads() ? "non" : "";
        return super.toString() +
            "[State = " + s + ", " + q + "empty queue]";
    }


    // Internal support methods for Conditions

    /**
     * Returns true if a node, always one that was initially placed on
     * a condition queue, is now waiting to reacquire on sync queue.
     * <p>
     *  如果某个节点(始终位于条件队列上的节点)现在正在等待在同步队列上重新获取,则返回true。
     * 
     * 
     * @param node the node
     * @return true if is reacquiring
     */
    final boolean isOnSyncQueue(Node node) {
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null) // If has successor, it must be on queue
            return true;
        /*
         * node.prev can be non-null, but not yet on queue because
         * the CAS to place it on queue can fail. So we have to
         * traverse from tail to make sure it actually made it.  It
         * will always be near the tail in calls to this method, and
         * unless the CAS failed (which is unlikely), it will be
         * there, so we hardly ever traverse much.
         * <p>
         *  node.prev可以是非空的,但尚未在队列上,因为CAS将其放置在队列上可能会失败。所以我们必须从尾部走过,以确保它实际上。
         * 它总是在调用这个方法的尾部附近,并且除非CAS失败(这是不太可能的),它将在那里,所以我们几乎没有横越。
         * 
         */
        return findNodeFromTail(node);
    }

    /**
     * Returns true if node is on sync queue by searching backwards from tail.
     * Called only when needed by isOnSyncQueue.
     * <p>
     *  如果节点在同步队列上,通过从尾部向后搜索,则返回true。仅在需要时由isOnSyncQueue调用。
     * 
     * 
     * @return true if present
     */
    private boolean findNodeFromTail(Node node) {
        Node t = tail;
        for (;;) {
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }
    }

    /**
     * Transfers a node from a condition queue onto sync queue.
     * Returns true if successful.
     * <p>
     *  将节点从条件队列传输到同步队列。如果成功,返回true。
     * 
     * 
     * @param node the node
     * @return true if successfully transferred (else the node was
     * cancelled before signal)
     */
    final boolean transferForSignal(Node node) {
        /*
         * If cannot change waitStatus, the node has been cancelled.
         * <p>
         *  如果无法更改waitStatus,则该节点已取消。
         * 
         */
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
            return false;

        /*
         * Splice onto queue and try to set waitStatus of predecessor to
         * indicate that thread is (probably) waiting. If cancelled or
         * attempt to set waitStatus fails, wake up to resync (in which
         * case the waitStatus can be transiently and harmlessly wrong).
         * <p>
         *  接合到队列并尝试设置前导的waitStatus以指示线程(可能)等待。如果取消或尝试设置waitStatus失败,唤醒以重新同步(在这种情况下,waitStatus可能会暂时和无害地错误)。
         * 
         */
        Node p = enq(node);
        int ws = p.waitStatus;
        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
            LockSupport.unpark(node.thread);
        return true;
    }

    /**
     * Transfers node, if necessary, to sync queue after a cancelled wait.
     * Returns true if thread was cancelled before being signalled.
     *
     * <p>
     *  如果需要,传输节点,在取消等待后同步队列。如果线程在发出信号之前被取消,则返回true。
     * 
     * 
     * @param node the node
     * @return true if cancelled before the node was signalled
     */
    final boolean transferAfterCancelledWait(Node node) {
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
            enq(node);
            return true;
        }
        /*
         * If we lost out to a signal(), then we can't proceed
         * until it finishes its enq().  Cancelling during an
         * incomplete transfer is both rare and transient, so just
         * spin.
         * <p>
         * 如果我们失去一个signal(),那么我们不能继续,直到它完成它的enq()。在不完全转移期间取消是罕见的和短暂的,所以只是旋转。
         * 
         */
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    /**
     * Invokes release with current state value; returns saved state.
     * Cancels node and throws exception on failure.
     * <p>
     *  使用当前状态值调用释放;返回保存的状态。在失败时取消节点并抛出异常。
     * 
     * 
     * @param node the condition node for this wait
     * @return previous sync state
     */
    final long fullyRelease(Node node) {
        boolean failed = true;
        try {
            long savedState = getState();
            if (release(savedState)) {
                failed = false;
                return savedState;
            } else {
                throw new IllegalMonitorStateException();
            }
        } finally {
            if (failed)
                node.waitStatus = Node.CANCELLED;
        }
    }

    // Instrumentation methods for conditions

    /**
     * Queries whether the given ConditionObject
     * uses this synchronizer as its lock.
     *
     * <p>
     *  查询给定的ConditionObject是否使用此同步器作为其锁。
     * 
     * 
     * @param condition the condition
     * @return {@code true} if owned
     * @throws NullPointerException if the condition is null
     */
    public final boolean owns(ConditionObject condition) {
        return condition.isOwnedBy(this);
    }

    /**
     * Queries whether any threads are waiting on the given condition
     * associated with this synchronizer. Note that because timeouts
     * and interrupts may occur at any time, a {@code true} return
     * does not guarantee that a future {@code signal} will awaken
     * any threads.  This method is designed primarily for use in
     * monitoring of the system state.
     *
     * <p>
     *  查询任何线程是否正在等待与此同步器关联的给定条件。注意,因为超时和中断可能在任何时候发生,{@code true}返回不保证未来的{@code signal}将唤醒任何线程。
     * 该方法主要用于监视系统状态。
     * 
     * 
     * @param condition the condition
     * @return {@code true} if there are any waiting threads
     * @throws IllegalMonitorStateException if exclusive synchronization
     *         is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this synchronizer
     * @throws NullPointerException if the condition is null
     */
    public final boolean hasWaiters(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    /**
     * Returns an estimate of the number of threads waiting on the
     * given condition associated with this synchronizer. Note that
     * because timeouts and interrupts may occur at any time, the
     * estimate serves only as an upper bound on the actual number of
     * waiters.  This method is designed for use in monitoring of the
     * system state, not for synchronization control.
     *
     * <p>
     *  返回等待与此同步器关联的给定条件的线程数的估计。注意,因为超时和中断可以在任何时间发生,所以估计仅用作实际数量的服务者的上限。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @param condition the condition
     * @return the estimated number of waiting threads
     * @throws IllegalMonitorStateException if exclusive synchronization
     *         is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this synchronizer
     * @throws NullPointerException if the condition is null
     */
    public final int getWaitQueueLength(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    /**
     * Returns a collection containing those threads that may be
     * waiting on the given condition associated with this
     * synchronizer.  Because the actual set of threads may change
     * dynamically while constructing this result, the returned
     * collection is only a best-effort estimate. The elements of the
     * returned collection are in no particular order.
     *
     * <p>
     *  返回包含可能在与此同步器关联的给定条件上等待的线程的集合。因为实际的线程集合可能在构造此结果时动态地改变,所返回的集合仅是最大努力估计。返回的集合的元素没有特定的顺序。
     * 
     * 
     * @param condition the condition
     * @return the collection of threads
     * @throws IllegalMonitorStateException if exclusive synchronization
     *         is not held
     * @throws IllegalArgumentException if the given condition is
     *         not associated with this synchronizer
     * @throws NullPointerException if the condition is null
     */
    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }

    /**
     * Condition implementation for a {@link
     * AbstractQueuedLongSynchronizer} serving as the basis of a {@link
     * Lock} implementation.
     *
     * <p>Method documentation for this class describes mechanics,
     * not behavioral specifications from the point of view of Lock
     * and Condition users. Exported versions of this class will in
     * general need to be accompanied by documentation describing
     * condition semantics that rely on those of the associated
     * {@code AbstractQueuedLongSynchronizer}.
     *
     * <p>This class is Serializable, but all fields are transient,
     * so deserialized conditions have no waiters.
     *
     * <p>
     * {@link AbstractQueuedLongSynchronizer}的条件实现作为{@link Lock}实现的基础。
     * 
     *  <p>此类的方法文档描述了机械,而不是Lock和Condition用户的行为规范。
     * 这个类的导出版本通常需要伴随着描述条件语义的文档,这些语义依赖于相关联的{@code AbstractQueuedLongSynchronizer}的语义。
     * 
     *  <p>此类是Serializable,但所有字段都是短暂的,因此反序列化条件没有服务器。
     * 
     * 
     * @since 1.6
     */
    public class ConditionObject implements Condition, java.io.Serializable {
        private static final long serialVersionUID = 1173984872572414699L;
        /** First node of condition queue. */
        private transient Node firstWaiter;
        /** Last node of condition queue. */
        private transient Node lastWaiter;

        /**
         * Creates a new {@code ConditionObject} instance.
         * <p>
         *  创建一个新的{@code ConditionObject}实例。
         * 
         */
        public ConditionObject() { }

        // Internal methods

        /**
         * Adds a new waiter to wait queue.
         * <p>
         *  添加新的服务员等待队列。
         * 
         * 
         * @return its new wait node
         */
        private Node addConditionWaiter() {
            Node t = lastWaiter;
            // If lastWaiter is cancelled, clean out.
            if (t != null && t.waitStatus != Node.CONDITION) {
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        /**
         * Removes and transfers nodes until hit non-cancelled one or
         * null. Split out from signal in part to encourage compilers
         * to inline the case of no waiters.
         * <p>
         *  删除和传输节点,直到命中未取消一个或null。分离出信号部分,鼓励编译器内联没有服务员的情况。
         * 
         * 
         * @param first (non-null) the first node on condition queue
         */
        private void doSignal(Node first) {
            do {
                if ( (firstWaiter = first.nextWaiter) == null)
                    lastWaiter = null;
                first.nextWaiter = null;
            } while (!transferForSignal(first) &&
                     (first = firstWaiter) != null);
        }

        /**
         * Removes and transfers all nodes.
         * <p>
         *  删除和传输所有节点。
         * 
         * 
         * @param first (non-null) the first node on condition queue
         */
        private void doSignalAll(Node first) {
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        /**
         * Unlinks cancelled waiter nodes from condition queue.
         * Called only while holding lock. This is called when
         * cancellation occurred during condition wait, and upon
         * insertion of a new waiter when lastWaiter is seen to have
         * been cancelled. This method is needed to avoid garbage
         * retention in the absence of signals. So even though it may
         * require a full traversal, it comes into play only when
         * timeouts or cancellations occur in the absence of
         * signals. It traverses all nodes rather than stopping at a
         * particular target to unlink all pointers to garbage nodes
         * without requiring many re-traversals during cancellation
         * storms.
         * <p>
         *  从条件队列中取消链接已取消的服务器节点。仅在握住锁时调用。在条件等待期间发生取消时,以及在看到lastWaiter已取消时插入新服务员时,将调用此函数。需要此方法以避免在没有信号的情况下的垃圾保留。
         * 因此,即使它可能需要完全遍历,它仅在没有信号的情况下发生超时或取消时才起作用。它遍历所有节点,而不是在特定目标处停止,以将所有指针解除链接到垃圾节点,而不需要在取消风暴期间多次重新遍历。
         * 
         */
        private void unlinkCancelledWaiters() {
            Node t = firstWaiter;
            Node trail = null;
            while (t != null) {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION) {
                    t.nextWaiter = null;
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;
                    if (next == null)
                        lastWaiter = trail;
                }
                else
                    trail = t;
                t = next;
            }
        }

        // public methods

        /**
         * Moves the longest-waiting thread, if one exists, from the
         * wait queue for this condition to the wait queue for the
         * owning lock.
         *
         * <p>
         * 将等待时间最长的线程(如果存在)从此条件的等待队列移动到拥有锁的等待队列。
         * 
         * 
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        public final void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        /**
         * Moves all threads from the wait queue for this condition to
         * the wait queue for the owning lock.
         *
         * <p>
         *  将所有线程从此条件的等待队列移动到拥有锁的等待队列。
         * 
         * 
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        public final void signalAll() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        /**
         * Implements uninterruptible condition wait.
         * <ol>
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * </ol>
         * <p>
         *  实现不间断条件等待。
         * <ol>
         *  <li>保存由{@link #getState}返回的锁定状态。
         *  <li>以保存的状态作为参数调用{@link #release},如果失败则抛出IllegalMonitorStateException。 <li>在发出信号之前阻止。
         *  <li>以保存的状态作为参数调用{@link #acquire}的专用版本,重新获取。
         * </ol>
         */
        public final void awaitUninterruptibly() {
            Node node = addConditionWaiter();
            long savedState = fullyRelease(node);
            boolean interrupted = false;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            if (acquireQueued(node, savedState) || interrupted)
                selfInterrupt();
        }

        /*
         * For interruptible waits, we need to track whether to throw
         * InterruptedException, if interrupted while blocked on
         * condition, versus reinterrupt current thread, if
         * interrupted while blocked waiting to re-acquire.
         * <p>
         *  对于可中断的等待,我们需要跟踪是否抛出InterruptedException,如果在条件阻塞的情况下中断,与重新中断当前线程,如果在阻塞等待重新获取时中断。
         * 
         */

        /** Mode meaning to reinterrupt on exit from wait */
        private static final int REINTERRUPT =  1;
        /** Mode meaning to throw InterruptedException on exit from wait */
        private static final int THROW_IE    = -1;

        /**
         * Checks for interrupt, returning THROW_IE if interrupted
         * before signalled, REINTERRUPT if after signalled, or
         * 0 if not interrupted.
         * <p>
         *  检查中断,如果在发出信号之前中断,返回THROW_IE,如果发出信号后返回REINTERRUPT,如果未中断则返回0。
         * 
         */
        private int checkInterruptWhileWaiting(Node node) {
            return Thread.interrupted() ?
                (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) :
                0;
        }

        /**
         * Throws InterruptedException, reinterrupts current thread, or
         * does nothing, depending on mode.
         * <p>
         *  抛出InterruptedException,重新中断当前线程,或不做任何事情,具体取决于模式。
         * 
         */
        private void reportInterruptAfterWait(int interruptMode)
            throws InterruptedException {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        /**
         * Implements interruptible condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled or interrupted.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * </ol>
         * <p>
         *  实现可中断条件等待。
         * <ol>
         *  <li>如果当前线程中断,则抛出InterruptedException。 <li>保存由{@link #getState}返回的锁定状态。
         *  <li>以保存的状态作为参数调用{@link #release},如果失败则抛出IllegalMonitorStateException。 <li>在发出信号或中断前阻止。
         *  <li>以保存的状态作为参数调用{@link #acquire}的专用版本,重新获取。 <li>如果在步骤4中被阻止时中断,请抛出InterruptedException。
         * </ol>
         */
        public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            long savedState = fullyRelease(node);
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        /**
         * Implements timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * </ol>
         * <p>
         * 实现定时条件等待。
         * <ol>
         *  <li>如果当前线程中断,则抛出InterruptedException。 <li>保存由{@link #getState}返回的锁定状态。
         *  <li>以保存的状态作为参数调用{@link #release},如果失败则抛出IllegalMonitorStateException。 <li>在发出信号,中断或超时之前阻止。
         *  <li>以保存的状态作为参数调用{@link #acquire}的专用版本,重新获取。 <li>如果在步骤4中被阻止时中断,请抛出InterruptedException。
         * </ol>
         */
        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            long savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return deadline - System.nanoTime();
        }

        /**
         * Implements absolute timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * <li> If timed out while blocked in step 4, return false, else true.
         * </ol>
         * <p>
         *  实现绝对定时条件等待。
         * <ol>
         *  <li>如果当前线程中断,则抛出InterruptedException。 <li>保存由{@link #getState}返回的锁定状态。
         *  <li>以保存的状态作为参数调用{@link #release},如果失败则抛出IllegalMonitorStateException。 <li>在发出信号,中断或超时之前阻止。
         *  <li>以保存的状态作为参数调用{@link #acquire}的专用版本,重新获取。 <li>如果在步骤4中被阻止时中断,请抛出InterruptedException。
         *  <li>如果在步骤4中被阻止时超时,则返回false,否则为true。
         * </ol>
         */
        public final boolean awaitUntil(Date deadline)
                throws InterruptedException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            long savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (System.currentTimeMillis() > abstime) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        /**
         * Implements timed condition wait.
         * <ol>
         * <li> If current thread is interrupted, throw InterruptedException.
         * <li> Save lock state returned by {@link #getState}.
         * <li> Invoke {@link #release} with saved state as argument,
         *      throwing IllegalMonitorStateException if it fails.
         * <li> Block until signalled, interrupted, or timed out.
         * <li> Reacquire by invoking specialized version of
         *      {@link #acquire} with saved state as argument.
         * <li> If interrupted while blocked in step 4, throw InterruptedException.
         * <li> If timed out while blocked in step 4, return false, else true.
         * </ol>
         * <p>
         *  实现定时条件等待。
         * <ol>
         * <li>如果当前线程中断,则抛出InterruptedException。 <li>保存由{@link #getState}返回的锁定状态。
         *  <li>以保存的状态作为参数调用{@link #release},如果失败则抛出IllegalMonitorStateException。 <li>在发出信号,中断或超时之前阻止。
         *  <li>以保存的状态作为参数调用{@link #acquire}的专用版本,重新获取。 <li>如果在步骤4中被阻止时中断,请抛出InterruptedException。
         *  <li>如果在步骤4中被阻止时超时,则返回false,否则为true。
         * </ol>
         */
        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            long savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        //  support for instrumentation

        /**
         * Returns true if this condition was created by the given
         * synchronization object.
         *
         * <p>
         *  如果此条件由给定的同步对象创建,则返回true。
         * 
         * 
         * @return {@code true} if owned
         */
        final boolean isOwnedBy(AbstractQueuedLongSynchronizer sync) {
            return sync == AbstractQueuedLongSynchronizer.this;
        }

        /**
         * Queries whether any threads are waiting on this condition.
         * Implements {@link AbstractQueuedLongSynchronizer#hasWaiters(ConditionObject)}.
         *
         * <p>
         *  查询任何线程是否正在等待此条件。实现{@link AbstractQueuedLongSynchronizer#hasWaiters(ConditionObject)}。
         * 
         * 
         * @return {@code true} if there are any waiting threads
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final boolean hasWaiters() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        /**
         * Returns an estimate of the number of threads waiting on
         * this condition.
         * Implements {@link AbstractQueuedLongSynchronizer#getWaitQueueLength(ConditionObject)}.
         *
         * <p>
         *  返回等待此条件的线程数的估计值。实现{@link AbstractQueuedLongSynchronizer#getWaitQueueLength(ConditionObject)}。
         * 
         * 
         * @return the estimated number of waiting threads
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final int getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        /**
         * Returns a collection containing those threads that may be
         * waiting on this Condition.
         * Implements {@link AbstractQueuedLongSynchronizer#getWaitingThreads(ConditionObject)}.
         *
         * <p>
         *  返回包含可能在此Condition上等待的线程的集合。
         * 实现{@link AbstractQueuedLongSynchronizer#getWaitingThreads(ConditionObject)}。
         * 
         * 
         * @return the collection of threads
         * @throws IllegalMonitorStateException if {@link #isHeldExclusively}
         *         returns {@code false}
         */
        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }

    /**
     * Setup to support compareAndSet. We need to natively implement
     * this here: For the sake of permitting future enhancements, we
     * cannot explicitly subclass AtomicLong, which would be
     * efficient and useful otherwise. So, as the lesser of evils, we
     * natively implement using hotspot intrinsics API. And while we
     * are at it, we do the same for other CASable fields (which could
     * otherwise be done with atomic field updaters).
     * <p>
     *  安装程序支持compareAndSet。我们需要在这里本地实现：为了允许将来的增强,我们不能显式地子类化AtomicLong,否则将是高效和有用的。
     * 因此,作为邪恶的较小者,我们使用热点内在API本地实现。虽然我们在它,我们对其他CASable字段(否则可以用原子场更新器)做同样的。
     * 
     */
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                (AbstractQueuedLongSynchronizer.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                (AbstractQueuedLongSynchronizer.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                (AbstractQueuedLongSynchronizer.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * CAS head field. Used only by enq.
     * <p>
     * CAS头字段。仅供enq使用。
     * 
     */
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    /**
     * CAS tail field. Used only by enq.
     * <p>
     *  CAS尾场。仅由enq使用。
     * 
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    /**
     * CAS waitStatus field of a node.
     * <p>
     *  CAS waitStatus字段的一个节点。
     * 
     */
    private static final boolean compareAndSetWaitStatus(Node node,
                                                         int expect,
                                                         int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,
                                        expect, update);
    }

    /**
     * CAS next field of a node.
     * <p>
     *  CAS下一个节点的字段。
     */
    private static final boolean compareAndSetNext(Node node,
                                                   Node expect,
                                                   Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }
}
