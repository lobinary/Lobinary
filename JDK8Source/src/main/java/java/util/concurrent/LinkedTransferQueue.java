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

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * An unbounded {@link TransferQueue} based on linked nodes.
 * This queue orders elements FIFO (first-in-first-out) with respect
 * to any given producer.  The <em>head</em> of the queue is that
 * element that has been on the queue the longest time for some
 * producer.  The <em>tail</em> of the queue is that element that has
 * been on the queue the shortest time for some producer.
 *
 * <p>Beware that, unlike in most collections, the {@code size} method
 * is <em>NOT</em> a constant-time operation. Because of the
 * asynchronous nature of these queues, determining the current number
 * of elements requires a traversal of the elements, and so may report
 * inaccurate results if this collection is modified during traversal.
 * Additionally, the bulk operations {@code addAll},
 * {@code removeAll}, {@code retainAll}, {@code containsAll},
 * {@code equals}, and {@code toArray} are <em>not</em> guaranteed
 * to be performed atomically. For example, an iterator operating
 * concurrently with an {@code addAll} operation might view only some
 * of the added elements.
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>Memory consistency effects: As with other concurrent
 * collections, actions in a thread prior to placing an object into a
 * {@code LinkedTransferQueue}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions subsequent to the access or removal of that element from
 * the {@code LinkedTransferQueue} in another thread.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  基于链接节点的无限{@link TransferQueue}。该队列相对于任何给定的生产者对元素FIFO(先进先出)进行排序。队列的<em>头</em>是某个生产者在队列上最长时间的那个元素。
 * 队列的<em>尾</em>是某个生产者在队列上的最短时间的元素。
 * 
 *  <p>请注意,与大多数集合不同,{@code size}方法是<em>不是</em>恒定时间操作。
 * 由于这些队列的异步性质,确定元素的当前数量需要遍历元素,因此如果在遍历期间修改此集合,则可能报告不准确的结果。
 * 此外,批量操作{@code addAll},{@code removeAll},{@code retainAll},{@code containsAll},{@code equals}和{@code toArray}
 * 不是<em> </em>保证以原子方式执行。
 * 由于这些队列的异步性质,确定元素的当前数量需要遍历元素,因此如果在遍历期间修改此集合,则可能报告不准确的结果。例如,与{@code addAll}操作同时运行的迭代器可能只查看一些添加的元素。
 * 
 *  <p>此类及其迭代器实现{@link Collection}和{@link Iterator}接口的所有<em>可选</em>方法。
 * 
 * <p>内存一致性效果：与其他并发集合一样,在将对象放置到{@code LinkedTransferQueue} <a href="package-summary.html#MemoryVisibility">
 *  <i>发生之前< / i> </a>在从另一个线程中的{@code LinkedTransferQueue}访问或删除该元素之后。
 * 
 *  <p>此类是的成员
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 *  Java集合框架</a>。
 * 
 * 
 * @since 1.7
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */
public class LinkedTransferQueue<E> extends AbstractQueue<E>
    implements TransferQueue<E>, java.io.Serializable {
    private static final long serialVersionUID = -3223113410248163686L;

    /*
     * *** Overview of Dual Queues with Slack ***
     *
     * Dual Queues, introduced by Scherer and Scott
     * (http://www.cs.rice.edu/~wns1/papers/2004-DISC-DDS.pdf) are
     * (linked) queues in which nodes may represent either data or
     * requests.  When a thread tries to enqueue a data node, but
     * encounters a request node, it instead "matches" and removes it;
     * and vice versa for enqueuing requests. Blocking Dual Queues
     * arrange that threads enqueuing unmatched requests block until
     * other threads provide the match. Dual Synchronous Queues (see
     * Scherer, Lea, & Scott
     * http://www.cs.rochester.edu/u/scott/papers/2009_Scherer_CACM_SSQ.pdf)
     * additionally arrange that threads enqueuing unmatched data also
     * block.  Dual Transfer Queues support all of these modes, as
     * dictated by callers.
     *
     * A FIFO dual queue may be implemented using a variation of the
     * Michael & Scott (M&S) lock-free queue algorithm
     * (http://www.cs.rochester.edu/u/scott/papers/1996_PODC_queues.pdf).
     * It maintains two pointer fields, "head", pointing to a
     * (matched) node that in turn points to the first actual
     * (unmatched) queue node (or null if empty); and "tail" that
     * points to the last node on the queue (or again null if
     * empty). For example, here is a possible queue with four data
     * elements:
     *
     *  head                tail
     *    |                   |
     *    v                   v
     *    M -> U -> U -> U -> U
     *
     * The M&S queue algorithm is known to be prone to scalability and
     * overhead limitations when maintaining (via CAS) these head and
     * tail pointers. This has led to the development of
     * contention-reducing variants such as elimination arrays (see
     * Moir et al http://portal.acm.org/citation.cfm?id=1074013) and
     * optimistic back pointers (see Ladan-Mozes & Shavit
     * http://people.csail.mit.edu/edya/publications/OptimisticFIFOQueue-journal.pdf).
     * However, the nature of dual queues enables a simpler tactic for
     * improving M&S-style implementations when dual-ness is needed.
     *
     * In a dual queue, each node must atomically maintain its match
     * status. While there are other possible variants, we implement
     * this here as: for a data-mode node, matching entails CASing an
     * "item" field from a non-null data value to null upon match, and
     * vice-versa for request nodes, CASing from null to a data
     * value. (Note that the linearization properties of this style of
     * queue are easy to verify -- elements are made available by
     * linking, and unavailable by matching.) Compared to plain M&S
     * queues, this property of dual queues requires one additional
     * successful atomic operation per enq/deq pair. But it also
     * enables lower cost variants of queue maintenance mechanics. (A
     * variation of this idea applies even for non-dual queues that
     * support deletion of interior elements, such as
     * j.u.c.ConcurrentLinkedQueue.)
     *
     * Once a node is matched, its match status can never again
     * change.  We may thus arrange that the linked list of them
     * contain a prefix of zero or more matched nodes, followed by a
     * suffix of zero or more unmatched nodes. (Note that we allow
     * both the prefix and suffix to be zero length, which in turn
     * means that we do not use a dummy header.)  If we were not
     * concerned with either time or space efficiency, we could
     * correctly perform enqueue and dequeue operations by traversing
     * from a pointer to the initial node; CASing the item of the
     * first unmatched node on match and CASing the next field of the
     * trailing node on appends. (Plus some special-casing when
     * initially empty).  While this would be a terrible idea in
     * itself, it does have the benefit of not requiring ANY atomic
     * updates on head/tail fields.
     *
     * We introduce here an approach that lies between the extremes of
     * never versus always updating queue (head and tail) pointers.
     * This offers a tradeoff between sometimes requiring extra
     * traversal steps to locate the first and/or last unmatched
     * nodes, versus the reduced overhead and contention of fewer
     * updates to queue pointers. For example, a possible snapshot of
     * a queue is:
     *
     *  head           tail
     *    |              |
     *    v              v
     *    M -> M -> U -> U -> U -> U
     *
     * The best value for this "slack" (the targeted maximum distance
     * between the value of "head" and the first unmatched node, and
     * similarly for "tail") is an empirical matter. We have found
     * that using very small constants in the range of 1-3 work best
     * over a range of platforms. Larger values introduce increasing
     * costs of cache misses and risks of long traversal chains, while
     * smaller values increase CAS contention and overhead.
     *
     * Dual queues with slack differ from plain M&S dual queues by
     * virtue of only sometimes updating head or tail pointers when
     * matching, appending, or even traversing nodes; in order to
     * maintain a targeted slack.  The idea of "sometimes" may be
     * operationalized in several ways. The simplest is to use a
     * per-operation counter incremented on each traversal step, and
     * to try (via CAS) to update the associated queue pointer
     * whenever the count exceeds a threshold. Another, that requires
     * more overhead, is to use random number generators to update
     * with a given probability per traversal step.
     *
     * In any strategy along these lines, because CASes updating
     * fields may fail, the actual slack may exceed targeted
     * slack. However, they may be retried at any time to maintain
     * targets.  Even when using very small slack values, this
     * approach works well for dual queues because it allows all
     * operations up to the point of matching or appending an item
     * (hence potentially allowing progress by another thread) to be
     * read-only, thus not introducing any further contention. As
     * described below, we implement this by performing slack
     * maintenance retries only after these points.
     *
     * As an accompaniment to such techniques, traversal overhead can
     * be further reduced without increasing contention of head
     * pointer updates: Threads may sometimes shortcut the "next" link
     * path from the current "head" node to be closer to the currently
     * known first unmatched node, and similarly for tail. Again, this
     * may be triggered with using thresholds or randomization.
     *
     * These ideas must be further extended to avoid unbounded amounts
     * of costly-to-reclaim garbage caused by the sequential "next"
     * links of nodes starting at old forgotten head nodes: As first
     * described in detail by Boehm
     * (http://portal.acm.org/citation.cfm?doid=503272.503282) if a GC
     * delays noticing that any arbitrarily old node has become
     * garbage, all newer dead nodes will also be unreclaimed.
     * (Similar issues arise in non-GC environments.)  To cope with
     * this in our implementation, upon CASing to advance the head
     * pointer, we set the "next" link of the previous head to point
     * only to itself; thus limiting the length of connected dead lists.
     * (We also take similar care to wipe out possibly garbage
     * retaining values held in other Node fields.)  However, doing so
     * adds some further complexity to traversal: If any "next"
     * pointer links to itself, it indicates that the current thread
     * has lagged behind a head-update, and so the traversal must
     * continue from the "head".  Traversals trying to find the
     * current tail starting from "tail" may also encounter
     * self-links, in which case they also continue at "head".
     *
     * It is tempting in slack-based scheme to not even use CAS for
     * updates (similarly to Ladan-Mozes & Shavit). However, this
     * cannot be done for head updates under the above link-forgetting
     * mechanics because an update may leave head at a detached node.
     * And while direct writes are possible for tail updates, they
     * increase the risk of long retraversals, and hence long garbage
     * chains, which can be much more costly than is worthwhile
     * considering that the cost difference of performing a CAS vs
     * write is smaller when they are not triggered on each operation
     * (especially considering that writes and CASes equally require
     * additional GC bookkeeping ("write barriers") that are sometimes
     * more costly than the writes themselves because of contention).
     *
     * *** Overview of implementation ***
     *
     * We use a threshold-based approach to updates, with a slack
     * threshold of two -- that is, we update head/tail when the
     * current pointer appears to be two or more steps away from the
     * first/last node. The slack value is hard-wired: a path greater
     * than one is naturally implemented by checking equality of
     * traversal pointers except when the list has only one element,
     * in which case we keep slack threshold at one. Avoiding tracking
     * explicit counts across method calls slightly simplifies an
     * already-messy implementation. Using randomization would
     * probably work better if there were a low-quality dirt-cheap
     * per-thread one available, but even ThreadLocalRandom is too
     * heavy for these purposes.
     *
     * With such a small slack threshold value, it is not worthwhile
     * to augment this with path short-circuiting (i.e., unsplicing
     * interior nodes) except in the case of cancellation/removal (see
     * below).
     *
     * We allow both the head and tail fields to be null before any
     * nodes are enqueued; initializing upon first append.  This
     * simplifies some other logic, as well as providing more
     * efficient explicit control paths instead of letting JVMs insert
     * implicit NullPointerExceptions when they are null.  While not
     * currently fully implemented, we also leave open the possibility
     * of re-nulling these fields when empty (which is complicated to
     * arrange, for little benefit.)
     *
     * All enqueue/dequeue operations are handled by the single method
     * "xfer" with parameters indicating whether to act as some form
     * of offer, put, poll, take, or transfer (each possibly with
     * timeout). The relative complexity of using one monolithic
     * method outweighs the code bulk and maintenance problems of
     * using separate methods for each case.
     *
     * Operation consists of up to three phases. The first is
     * implemented within method xfer, the second in tryAppend, and
     * the third in method awaitMatch.
     *
     * 1. Try to match an existing node
     *
     *    Starting at head, skip already-matched nodes until finding
     *    an unmatched node of opposite mode, if one exists, in which
     *    case matching it and returning, also if necessary updating
     *    head to one past the matched node (or the node itself if the
     *    list has no other unmatched nodes). If the CAS misses, then
     *    a loop retries advancing head by two steps until either
     *    success or the slack is at most two. By requiring that each
     *    attempt advances head by two (if applicable), we ensure that
     *    the slack does not grow without bound. Traversals also check
     *    if the initial head is now off-list, in which case they
     *    start at the new head.
     *
     *    If no candidates are found and the call was untimed
     *    poll/offer, (argument "how" is NOW) return.
     *
     * 2. Try to append a new node (method tryAppend)
     *
     *    Starting at current tail pointer, find the actual last node
     *    and try to append a new node (or if head was null, establish
     *    the first node). Nodes can be appended only if their
     *    predecessors are either already matched or are of the same
     *    mode. If we detect otherwise, then a new node with opposite
     *    mode must have been appended during traversal, so we must
     *    restart at phase 1. The traversal and update steps are
     *    otherwise similar to phase 1: Retrying upon CAS misses and
     *    checking for staleness.  In particular, if a self-link is
     *    encountered, then we can safely jump to a node on the list
     *    by continuing the traversal at current head.
     *
     *    On successful append, if the call was ASYNC, return.
     *
     * 3. Await match or cancellation (method awaitMatch)
     *
     *    Wait for another thread to match node; instead cancelling if
     *    the current thread was interrupted or the wait timed out. On
     *    multiprocessors, we use front-of-queue spinning: If a node
     *    appears to be the first unmatched node in the queue, it
     *    spins a bit before blocking. In either case, before blocking
     *    it tries to unsplice any nodes between the current "head"
     *    and the first unmatched node.
     *
     *    Front-of-queue spinning vastly improves performance of
     *    heavily contended queues. And so long as it is relatively
     *    brief and "quiet", spinning does not much impact performance
     *    of less-contended queues.  During spins threads check their
     *    interrupt status and generate a thread-local random number
     *    to decide to occasionally perform a Thread.yield. While
     *    yield has underdefined specs, we assume that it might help,
     *    and will not hurt, in limiting impact of spinning on busy
     *    systems.  We also use smaller (1/2) spins for nodes that are
     *    not known to be front but whose predecessors have not
     *    blocked -- these "chained" spins avoid artifacts of
     *    front-of-queue rules which otherwise lead to alternating
     *    nodes spinning vs blocking. Further, front threads that
     *    represent phase changes (from data to request node or vice
     *    versa) compared to their predecessors receive additional
     *    chained spins, reflecting longer paths typically required to
     *    unblock threads during phase changes.
     *
     *
     * ** Unlinking removed interior nodes **
     *
     * In addition to minimizing garbage retention via self-linking
     * described above, we also unlink removed interior nodes. These
     * may arise due to timed out or interrupted waits, or calls to
     * remove(x) or Iterator.remove.  Normally, given a node that was
     * at one time known to be the predecessor of some node s that is
     * to be removed, we can unsplice s by CASing the next field of
     * its predecessor if it still points to s (otherwise s must
     * already have been removed or is now offlist). But there are two
     * situations in which we cannot guarantee to make node s
     * unreachable in this way: (1) If s is the trailing node of list
     * (i.e., with null next), then it is pinned as the target node
     * for appends, so can only be removed later after other nodes are
     * appended. (2) We cannot necessarily unlink s given a
     * predecessor node that is matched (including the case of being
     * cancelled): the predecessor may already be unspliced, in which
     * case some previous reachable node may still point to s.
     * (For further explanation see Herlihy & Shavit "The Art of
     * Multiprocessor Programming" chapter 9).  Although, in both
     * cases, we can rule out the need for further action if either s
     * or its predecessor are (or can be made to be) at, or fall off
     * from, the head of list.
     *
     * Without taking these into account, it would be possible for an
     * unbounded number of supposedly removed nodes to remain
     * reachable.  Situations leading to such buildup are uncommon but
     * can occur in practice; for example when a series of short timed
     * calls to poll repeatedly time out but never otherwise fall off
     * the list because of an untimed call to take at the front of the
     * queue.
     *
     * When these cases arise, rather than always retraversing the
     * entire list to find an actual predecessor to unlink (which
     * won't help for case (1) anyway), we record a conservative
     * estimate of possible unsplice failures (in "sweepVotes").
     * We trigger a full sweep when the estimate exceeds a threshold
     * ("SWEEP_THRESHOLD") indicating the maximum number of estimated
     * removal failures to tolerate before sweeping through, unlinking
     * cancelled nodes that were not unlinked upon initial removal.
     * We perform sweeps by the thread hitting threshold (rather than
     * background threads or by spreading work to other threads)
     * because in the main contexts in which removal occurs, the
     * caller is already timed-out, cancelled, or performing a
     * potentially O(n) operation (e.g. remove(x)), none of which are
     * time-critical enough to warrant the overhead that alternatives
     * would impose on other threads.
     *
     * Because the sweepVotes estimate is conservative, and because
     * nodes become unlinked "naturally" as they fall off the head of
     * the queue, and because we allow votes to accumulate even while
     * sweeps are in progress, there are typically significantly fewer
     * such nodes than estimated.  Choice of a threshold value
     * balances the likelihood of wasted effort and contention, versus
     * providing a worst-case bound on retention of interior nodes in
     * quiescent queues. The value defined below was chosen
     * empirically to balance these under various timeout scenarios.
     *
     * Note that we cannot self-link unlinked interior nodes during
     * sweeps. However, the associated garbage chains terminate when
     * some successor ultimately falls off the head of the list and is
     * self-linked.
     * <p>
     *  ***双队列与松弛概述***
     * 
     *  由Scherer和Scott(http://www.cs.rice.edu/~wns1/papers/2004-DISC-DDS.pdf)介绍的双队列是(链接)队列,其中节点可以表示数据或请求。
     * 当线程尝试将数据节点排入队列,但遇到请求节点时,它会"匹配"并删除它;并且反之亦然,用于入队请求。阻塞双队列排列线程排队不匹配的请求块,直到其他线程提供匹配。
     * 双同步队列(参见Scherer,Lea和Scott http://www.cs.rochester.edu/u/scott/papers/2009_Scherer_CACM_SSQ.pdf)另外排列使不
     * 匹配数据排队的线程也被阻塞。
     * 当线程尝试将数据节点排入队列,但遇到请求节点时,它会"匹配"并删除它;并且反之亦然,用于入队请求。阻塞双队列排列线程排队不匹配的请求块,直到其他线程提供匹配。双传输队列支持所有这些模式,由呼叫者指定。
     * 
     * FIFO双队列可以使用Michael&Scott(M&S)无锁队列算法(http://www.cs.rochester.edu/u/scott/papers/1996_PODC_queues.pdf)的
     * 变体来实现。
     * 它维护两个指针字段"head",指向(匹配的)节点,该节点又指向第一个实际(不匹配的)队列节点(如果为空,则为null);和"tail"指向队列上的最后一个节点(或者如果为空则再次为null)。
     * 例如,这里是具有四个数据元素的可能队列：。
     * 
     *  头尾| | v v M→U→U→U→U
     * 
     *  已知当维护(通过CAS)这些头部和尾部指针时,M&S队列算法易于具有可扩展性和开销限制。
     * 这导致争用减少变体的发展,例如消除数组(参见Moir等人http://portal.acm.org/citation.cfm?id=1074013)和乐观的返回指针(参见Ladan-Mozes&Shav
     * it http ：//people.csail.mit.edu/edya/publications/OptimisticFIFOQueue-journal.pdf)。
     *  已知当维护(通过CAS)这些头部和尾部指针时,M&S队列算法易于具有可扩展性和开销限制。然而,双队列的本质使得能够在需要双重时改进M&S风格实现的更简单的策略。
     * 
     * 在双队列中,每个节点必须原子地保持其匹配状态。
     * 虽然有其他可能的变体,我们在这里实现这里为：对于数据模式节点,匹配需要在匹配时从非空数据值CASing"项"字段为空,并且对于请求节点反之亦然,CASING从null到数据值。
     *  (注意,这种类型的队列的线性化属性很容易验证 - 元素通过链接可用,并且通过匹配不可用。)与纯M&S队列相比,双队列的这个属性需要一个额外的成功的原子操作每个enq / deq对。
     * 但它也支持更低成本的队列维护机制。 (这种思想的变体甚至适用于支持删除内部元素的非双队列,例如j.u.c.ConcurrentLinkedQueue。)。
     * 
     * 一旦节点匹配,其匹配状态就不能再次改变。因此,我们可以安排它们的链接列表包含零个或多个匹配节点的前缀,后面是零个或多个不匹配节点的后缀。
     *  (注意,我们允许前缀和后缀都为零长度,这反过来意味着我们不使用虚拟头)。
     * 如果我们不关心时间或空间效率,我们可以正确地执行入队和出队操作从指针到初始节点的遍历;在匹配时采用第一个不匹配节点的项目,并且在追加上采用追踪节点的下一个字段。 (加一些特殊的外壳,最初为空)。
     * 虽然这本身就是一个可怕的想法,但它确实具有不需要对头/尾字段进行任何原子更新的好处。
     * 
     *  我们在这里介绍一种介于永远和总是更新队列(头和尾)指针的极端之间的方法。这提供了在有时需要额外的遍历步骤来定位第一和/或最后的不匹配节点之间的折衷,而与减少的开销和对队列指针的更少的更少的竞争相比。
     * 例如,队列的可能快照是：。
     * 
     *  头尾| | v v M→M→U→U→U→U
     * 
     * 该"松弛"("头部"和第一不匹配节点的值之间的目标最大距离,以及类似地"尾部"的最大值)的最佳值是经验问题。我们已经发现,在一系列平台上使用在1-3范围内的非常小的常数最好。
     * 较大的值引入了高速缓存未命中和长遍历链的风险的增加的成本,而较小的值增加CAS竞争和开销。
     * 
     *  具有松弛的双队列与普通M&S双队列不同在于,当​​匹配,附加或甚至遍历节点时仅仅有时更新头部或尾部指针;以便保持目标松弛。 "有时"的想法可以以几种方式实施。
     * 最简单的是使用在每个遍历步骤上递增的每操作计数器,并且每当计数超过阈值时尝试(经由CAS)更新相关联的队列指针。另一个需要更多开销的方法是使用随机数生成器以每个遍历步骤的给定概率更新。
     * 
     * 在沿着这些线的任何策略中,因为更新字段的CAS可能失败,所以实际的松弛可能超过目标松弛。然而,他们可以在任何时候重试以保持目标。
     * 即使使用非常小的松弛值,这种方法也适用于双队列,因为它允许所有操作直到匹配或附加项(因此潜在地允许由另一个线程进度)的点是只读的,因此不引入任何进一步争用。
     * 如下所述,我们通过在这些点之后执行松弛维护重试来实现。
     * 
     *  作为这样的技术的伴随,可以进一步减少遍历开销,而不增加头指针更新的争用：线程有时可以将"当前"头部"节点的"下一个"链接路径快捷地更接近当前已知的第一不匹配节点,以及类似地尾。
     * 再次,这可以使用阈值或随机化来触发。
     * 
     * 这些想法必须进一步扩展,以避免由从旧的被遗忘的头节点开始的节点的顺序"下一个"链路引起的无限量的昂贵的回收垃圾。如先前由Boehm(http：//portal.acm。
     *  org / citation.cfm?doid = 503272.503282)如果GC延迟注意任何任意旧的节点已成为垃圾,所有更新的死节点也将被无人认领。
     * 为了在我们的实现中处理这一点,在CASing提前头指针时,我们设置前一个头的"下一个"链接仅指向它自己;从而限制连接的死表的长度。 (我们也同样谨慎地消除可能在其他节点字段中保存的垃圾保留值。
     * )然而,这样做为遍历增加了一些进一步的复杂性：如果任何"下一个"指针链接到自身,则指示当前线程已经滞后头更新,因此遍历必须从"头"继续。
     * 尝试从"尾部"开始寻找当前尾部的遍历也可能遇到自链接,在这种情况下,它们还在"头部"继续。
     * 
     * 它是诱人的基于松弛的计划甚至不使用CAS更新(类似于Ladan-Mozes和Shavit)。然而,这不能在上述链接遗忘机制下的头更新,因为更新可能在分离的节点离开头。
     * 虽然直接写入对于尾部更新是可能的,但是它们增加了长回滚的风险,并且因此增加了长的垃圾链,这可能比值得考虑的成本高得多,考虑到执行CAS vs写的成本差别较小(特别是考虑到写和CAS同样需要额外的GC记录
     * ("写屏障"),由于争用,它们有时比写自己更昂贵)。
     * 它是诱人的基于松弛的计划甚至不使用CAS更新(类似于Ladan-Mozes和Shavit)。然而,这不能在上述链接遗忘机制下的头更新,因为更新可能在分离的节点离开头。
     * 
     *  ***实施概述***
     * 
     *  我们使用基于阈值的方法来更新,具有两个松弛阈值,即,当当前指针看起来距离第一个/最后一个节点两个或更多个步长时,我们更新头/尾。
     * 松弛值是硬连线的：大于1的路径自然地通过检查遍历指针的相等性来实现,除非当列表仅具有一个元素时,在这种情况下,我们将松弛阈值保持为1。避免跨方法调用跟踪显式计数会稍微简化已经凌乱的实现。
     * 使用随机化可能会更好地工作,如果有一个低质量的廉价每线程一个可用,但即使ThreadLocalRandom太重了这些目的。
     * 
     * 利用这样小的松弛阈值,除了在消除/去除(见下文)的情况之外,不值得用路径短路(即,不切割内部节点)来增大它。
     * 
     *  我们允许头和尾字段在任何节点入队之前为空;首次附加时初始化。这简化了一些其他逻辑,以及提供更高效的显式控制路径,而不是让JVM在它们为null时插入隐式NullPointerExceptions。
     * 虽然目前尚未完全实施,但我们也保留了空白时重新归零这些字段的可能性(这是很复杂的安排,没有什么好处)。
     * 
     *  所有入队/出队操作都由单个方法"xfer"处理,参数指示是否作为某种形式的offer,put,poll,take或transfer(每个都可能有超时)。
     * 使用一个单片方法的相对复杂性胜过对每种情况使用单独方法的代码批量和维护问题。
     * 
     *  操作由三个阶段组成。第一个在方法xfer中实现,第二个在tryAppend中,第三个在方法awaitMatch中。
     * 
     *  尝试匹配现有节点
     * 
     * 从头开始,跳过已经匹配的节点,直到找到相反模式的不匹配节点(如果存在),在这种情况下匹配它并返回,如果必要的话,将头更新到匹配的节点(或者如果列表具有没有其他不匹配的节点)。
     * 如果CAS错过,则循环重试前进头两个步骤,直到成功或松弛至多两个。通过要求每次尝试前进两次(如果适用),我们确保松弛不会无限制地增长。
     * 遍历还检查初始磁头现在是否在列表中,在这种情况下,它们从新磁头开始。
     * 
     *  如果没有找到候选人并且调用没有投票/提议,(参数"如何"是现在)返回。
     * 
     *  2.尝试附加一个新节点(方法tryAppend)
     * 
     *  从当前尾指针开始,找到实际的最后一个节点,并尝试附加一个新节点(或者如果head为null,则建立第一个节点)。只有当它们的前导已经匹配或者具有相同的模式时,才可以附加节点。
     * 如果我们检测不到,则在遍历期间必须附加具有相反模式的新节点,因此我们必须在阶段1重新开始。遍历和更新步骤另外类似于阶段1：在CAS未命中时重试并检查陈旧度。
     * 特别地,如果遇到自链接,则我们可以通过在当前头部继续遍历来安全地跳转到列表上的节点。
     * 
     *  在成功追加后,如果调用是ASYNC,则返回。
     * 
     *  等待匹配或取消(方法awaitMatch)
     * 
     * 等待另一个线程匹配节点;而是取消当前线程是否中断或等待超时。在多处理器上,我们使用队列前转：如果一个节点看起来是队列中的第一个不匹配的节点,它在阻塞前旋转一个位。
     * 在任一情况下,在阻塞之前,它尝试不分离当前"头"和第一不匹配节点之间的任何节点。
     * 
     *  队列前队列大大提高了大量竞争队列的​​性能。只要它相对简短和"安静",纺纱不会影响较少争夺队列的性能。在自旋期间,线程检查其中断状态并生成线程局部随机数以决定偶尔执行Thread.yield。
     * 虽然产量低于规格,但我们认为它可能有助于限制纺纱对繁忙系统的影响,并且不会受到伤害。
     * 我们还使用较小的(1/2)自旋,用于不知道为前面但前面没有阻塞的节点 - 这些"链接"自旋避免了队列前规则的伪像,否则会导致交替的节点旋转和阻塞。
     * 此外,与它们的前代相比,表示相位改变(从数据到请求节点或反之亦然)的前线程接收额外的链接自旋,反映通常在相位改变期间解锁线程所需的更长路径。
     * 
     *  **取消链接删除的内部节点**
     * 
     * 除了通过上述自连接使垃圾保留最小化之外,我们还取消关联删除的内部节点。这些可能是由于超时或中断的等待,或调用remove(x)或Iterator.remove。
     * 通常,给定一个已知为要移除的某个节点的前导的节点,如果它仍然指向s,则可以通过对其前导的下一个字段进行CASing来取消舍弃(否则s必须已经删除或现在脱机)。
     * 但是在两种情况下,我们不能保证以这种方式使节点不可达：(1)如果s是list的下游节点(即,具有null next),则它被固定为用于追加的目标节点,因此只能在以后添加其他节点后删除。
     *  (2)对于给定的匹配的前趋节点(包括被取消的情况),我们不一定可以解除链接：前导可能已经是未剪接的,在这种情况下,一些先前可达节点仍然可以指向s。
     *  (进一步解释见Herlihy&Shavit"The Art of Multiprocessor Programming"第9章)。
     * 虽然在这两种情况下,如果任何一个或其前身是(或可以被设定)在名单的首领,或从名单的首长脱离,我们可以排除进一步行动的必要性。
     * 
     * 在不考虑这些的情况下,可以使无限数量的假定移除的节点保持可达。
     * 导致这种累积的情况不常见,但在实践中可能发生;例如当一系列用于轮询的短定时调用重复超时但从未以其他方式脱离列表时,由于在队列的前面进行的未定时调用。
     * 
     *  当这些情况出现时,我们记录对可能的非陷阱故障(在"sweepVotes"中)的保守估计,而不是总是重新遍历整个列表以找到实际的前导去链接(其不会有助于情况(1))。
     * 当估计超过阈值("SWEEP_THRESHOLD")时,我们触发全扫描,该阈值指示在扫描之前允许的估计去除失败的最大数目,解除在初始去除时未被取消链接的被取消节点。
     * 我们通过线程命中阈值执行扫描(而不是后台线程或通过将工作扩展到其他线程),因为在发生删除的主上下文中,调用者已经超时,取消或执行潜在的O(n)操作(例如remove(x)),它们都不是时间关键的,足以保
     * 证替代方案将施加在其他线程上的开销。
     * 当估计超过阈值("SWEEP_THRESHOLD")时,我们触发全扫描,该阈值指示在扫描之前允许的估计去除失败的最大数目,解除在初始去除时未被取消链接的被取消节点。
     * 
     * 因为sweepVotes估计是保守的,并且因为节点随着它们离开队列的头部而"自然地"解链,并且因为即使在扫描正在进行时我们允许投票累积,通常比估计的节点少得多。
     * 阈值的选择平衡了浪费努力和争用的可能性,而不是提供对静止队列中的内部节点的保留的最坏情况限制。下面定义的值是根据经验选择的,以在各种超时情况下平衡这些值。
     * 
     *  注意,我们不能在扫描期间自链接未链接的内部节点。然而,当一些后继最终从列表的头部落下并且是自链接的时,相关联的垃圾链终止。
     * 
     */

    /** True if on multiprocessor */
    private static final boolean MP =
        Runtime.getRuntime().availableProcessors() > 1;

    /**
     * The number of times to spin (with randomly interspersed calls
     * to Thread.yield) on multiprocessor before blocking when a node
     * is apparently the first waiter in the queue.  See above for
     * explanation. Must be a power of two. The value is empirically
     * derived -- it works pretty well across a variety of processors,
     * numbers of CPUs, and OSes.
     * <p>
     *  在阻塞之前,当节点显然是队列中的第一个服务器时,在多处理器上旋转(随机散布调用Thread.yield)的次数。见上面的解释。必须是二的幂。
     * 该值是凭经验得出的 - 它在各种处理器,CPU数量和操作系统方面表现良好。
     * 
     */
    private static final int FRONT_SPINS   = 1 << 7;

    /**
     * The number of times to spin before blocking when a node is
     * preceded by another node that is apparently spinning.  Also
     * serves as an increment to FRONT_SPINS on phase changes, and as
     * base average frequency for yielding during spins. Must be a
     * power of two.
     * <p>
     *  当节点被明显旋转的另一个节点排在前面时阻塞之前旋转的次数。也用作对相位变化的FRONT_SPINS的增量,以及作为旋转期间产生的基本平均频率。必须是二的幂。
     * 
     */
    private static final int CHAINED_SPINS = FRONT_SPINS >>> 1;

    /**
     * The maximum number of estimated removal failures (sweepVotes)
     * to tolerate before sweeping through the queue unlinking
     * cancelled nodes that were not unlinked upon initial
     * removal. See above for explanation. The value must be at least
     * two to avoid useless sweeps when removing trailing nodes.
     * <p>
     * 在清除队列之前允许的估计删除失败(sweepVotes)的最大数量取消关联在初始删除时未取消链接的已取消节点。见上面的解释。该值必须至少为2,以在删除拖尾节点时避免无用的扫描。
     * 
     */
    static final int SWEEP_THRESHOLD = 32;

    /**
     * Queue nodes. Uses Object, not E, for items to allow forgetting
     * them after use.  Relies heavily on Unsafe mechanics to minimize
     * unnecessary ordering constraints: Writes that are intrinsically
     * ordered wrt other accesses or CASes use simple relaxed forms.
     * <p>
     *  队列节点。使用对象,而不是E,用于允许在使用后忘记它们的项目。严重依赖不安全的机制,以最小化不必要的顺序约束：写入本质排序wrt其他访问或CAS使用简单的放宽形式。
     * 
     */
    static final class Node {
        final boolean isData;   // false if this is a request node
        volatile Object item;   // initially non-null if isData; CASed to match
        volatile Node next;
        volatile Thread waiter; // null until waiting

        // CAS methods for fields
        final boolean casNext(Node cmp, Node val) {
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        final boolean casItem(Object cmp, Object val) {
            // assert cmp == null || cmp.getClass() != Node.class;
            return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
        }

        /**
         * Constructs a new node.  Uses relaxed write because item can
         * only be seen after publication via casNext.
         * <p>
         *  构造一个新节点。使用宽松的写作,因为项目只能通过casNext发布后才能看到。
         * 
         */
        Node(Object item, boolean isData) {
            UNSAFE.putObject(this, itemOffset, item); // relaxed write
            this.isData = isData;
        }

        /**
         * Links node to itself to avoid garbage retention.  Called
         * only after CASing head field, so uses relaxed write.
         * <p>
         *  将节点链接到自身以避免垃圾回收。仅在CASing头字段之后调用,因此使用宽松写入。
         * 
         */
        final void forgetNext() {
            UNSAFE.putObject(this, nextOffset, this);
        }

        /**
         * Sets item to self and waiter to null, to avoid garbage
         * retention after matching or cancelling. Uses relaxed writes
         * because order is already constrained in the only calling
         * contexts: item is forgotten only after volatile/atomic
         * mechanics that extract items.  Similarly, clearing waiter
         * follows either CAS or return from park (if ever parked;
         * else we don't care).
         * <p>
         *  将项目设置为self和waiter为null,以避免匹配或取消后的垃圾回收。
         * 使用轻松的写入,因为顺序已经被限制在唯一的调用上下文中：item仅在提取项目的volatile / atomic机制之后被忘记。同样,清除服务员遵循CAS或从公园返回(如果已停放;否则我们不在乎)。
         * 
         */
        final void forgetContents() {
            UNSAFE.putObject(this, itemOffset, this);
            UNSAFE.putObject(this, waiterOffset, null);
        }

        /**
         * Returns true if this node has been matched, including the
         * case of artificial matches due to cancellation.
         * <p>
         *  如果此节点已匹配,则返回true,包括由于取消而导致的人为匹配的情况。
         * 
         */
        final boolean isMatched() {
            Object x = item;
            return (x == this) || ((x == null) == isData);
        }

        /**
         * Returns true if this is an unmatched request node.
         * <p>
         *  如果这是不匹配的请求节点,则返回true。
         * 
         */
        final boolean isUnmatchedRequest() {
            return !isData && item == null;
        }

        /**
         * Returns true if a node with the given mode cannot be
         * appended to this node because this node is unmatched and
         * has opposite data mode.
         * <p>
         *  如果具有给定模式的节点无法附加到此节点,则返回true,因为此节点不匹配且具有相反的数据模式。
         * 
         */
        final boolean cannotPrecede(boolean haveData) {
            boolean d = isData;
            Object x;
            return d != haveData && (x = item) != this && (x != null) == d;
        }

        /**
         * Tries to artificially match a data node -- used by remove.
         * <p>
         *  尝试人工匹配数据节点 - 由remove使用。
         * 
         */
        final boolean tryMatchData() {
            // assert isData;
            Object x = item;
            if (x != null && x != this && casItem(x, null)) {
                LockSupport.unpark(waiter);
                return true;
            }
            return false;
        }

        private static final long serialVersionUID = -3375979862319811754L;

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long itemOffset;
        private static final long nextOffset;
        private static final long waiterOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> k = Node.class;
                itemOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("item"));
                nextOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("next"));
                waiterOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("waiter"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    /** head of the queue; null until first enqueue */
    transient volatile Node head;

    /** tail of the queue; null until first append */
    private transient volatile Node tail;

    /** The number of apparent failures to unsplice removed nodes */
    private transient volatile int sweepVotes;

    // CAS methods for fields
    private boolean casTail(Node cmp, Node val) {
        return UNSAFE.compareAndSwapObject(this, tailOffset, cmp, val);
    }

    private boolean casHead(Node cmp, Node val) {
        return UNSAFE.compareAndSwapObject(this, headOffset, cmp, val);
    }

    private boolean casSweepVotes(int cmp, int val) {
        return UNSAFE.compareAndSwapInt(this, sweepVotesOffset, cmp, val);
    }

    /*
     * Possible values for "how" argument in xfer method.
     * <p>
     * xfer方法中"how"参数的可能值。
     * 
     */
    private static final int NOW   = 0; // for untimed poll, tryTransfer
    private static final int ASYNC = 1; // for offer, put, add
    private static final int SYNC  = 2; // for transfer, take
    private static final int TIMED = 3; // for timed poll, tryTransfer

    @SuppressWarnings("unchecked")
    static <E> E cast(Object item) {
        // assert item == null || item.getClass() != Node.class;
        return (E) item;
    }

    /**
     * Implements all queuing methods. See above for explanation.
     *
     * <p>
     *  实现所有排队方法。见上面的解释。
     * 
     * 
     * @param e the item or null for take
     * @param haveData true if this is a put, else a take
     * @param how NOW, ASYNC, SYNC, or TIMED
     * @param nanos timeout in nanosecs, used only if mode is TIMED
     * @return an item if matched, else e
     * @throws NullPointerException if haveData mode but e is null
     */
    private E xfer(E e, boolean haveData, int how, long nanos) {
        if (haveData && (e == null))
            throw new NullPointerException();
        Node s = null;                        // the node to append, if needed

        retry:
        for (;;) {                            // restart on append race

            for (Node h = head, p = h; p != null;) { // find & match first node
                boolean isData = p.isData;
                Object item = p.item;
                if (item != p && (item != null) == isData) { // unmatched
                    if (isData == haveData)   // can't match
                        break;
                    if (p.casItem(item, e)) { // match
                        for (Node q = p; q != h;) {
                            Node n = q.next;  // update by 2 unless singleton
                            if (head == h && casHead(h, n == null ? q : n)) {
                                h.forgetNext();
                                break;
                            }                 // advance and retry
                            if ((h = head)   == null ||
                                (q = h.next) == null || !q.isMatched())
                                break;        // unless slack < 2
                        }
                        LockSupport.unpark(p.waiter);
                        return LinkedTransferQueue.<E>cast(item);
                    }
                }
                Node n = p.next;
                p = (p != n) ? n : (h = head); // Use head if p offlist
            }

            if (how != NOW) {                 // No matches available
                if (s == null)
                    s = new Node(e, haveData);
                Node pred = tryAppend(s, haveData);
                if (pred == null)
                    continue retry;           // lost race vs opposite mode
                if (how != ASYNC)
                    return awaitMatch(s, pred, e, (how == TIMED), nanos);
            }
            return e; // not waiting
        }
    }

    /**
     * Tries to append node s as tail.
     *
     * <p>
     *  尝试将节点追加为尾。
     * 
     * 
     * @param s the node to append
     * @param haveData true if appending in data mode
     * @return null on failure due to losing race with append in
     * different mode, else s's predecessor, or s itself if no
     * predecessor
     */
    private Node tryAppend(Node s, boolean haveData) {
        for (Node t = tail, p = t;;) {        // move p to last node and append
            Node n, u;                        // temps for reads of next & tail
            if (p == null && (p = head) == null) {
                if (casHead(null, s))
                    return s;                 // initialize
            }
            else if (p.cannotPrecede(haveData))
                return null;                  // lost race vs opposite mode
            else if ((n = p.next) != null)    // not last; keep traversing
                p = p != t && t != (u = tail) ? (t = u) : // stale tail
                    (p != n) ? n : null;      // restart if off list
            else if (!p.casNext(null, s))
                p = p.next;                   // re-read on CAS failure
            else {
                if (p != t) {                 // update if slack now >= 2
                    while ((tail != t || !casTail(t, s)) &&
                           (t = tail)   != null &&
                           (s = t.next) != null && // advance and retry
                           (s = s.next) != null && s != t);
                }
                return p;
            }
        }
    }

    /**
     * Spins/yields/blocks until node s is matched or caller gives up.
     *
     * <p>
     *  旋转/产量/块直到节点匹配或呼叫者放弃。
     * 
     * 
     * @param s the waiting node
     * @param pred the predecessor of s, or s itself if it has no
     * predecessor, or null if unknown (the null case does not occur
     * in any current calls but may in possible future extensions)
     * @param e the comparison value for checking match
     * @param timed if true, wait only until timeout elapses
     * @param nanos timeout in nanosecs, used only if timed is true
     * @return matched item, or e if unmatched on interrupt or timeout
     */
    private E awaitMatch(Node s, Node pred, E e, boolean timed, long nanos) {
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        Thread w = Thread.currentThread();
        int spins = -1; // initialized after first item and cancel checks
        ThreadLocalRandom randomYields = null; // bound if needed

        for (;;) {
            Object item = s.item;
            if (item != e) {                  // matched
                // assert item != s;
                s.forgetContents();           // avoid garbage
                return LinkedTransferQueue.<E>cast(item);
            }
            if ((w.isInterrupted() || (timed && nanos <= 0)) &&
                    s.casItem(e, s)) {        // cancel
                unsplice(pred, s);
                return e;
            }

            if (spins < 0) {                  // establish spins at/near front
                if ((spins = spinsFor(pred, s.isData)) > 0)
                    randomYields = ThreadLocalRandom.current();
            }
            else if (spins > 0) {             // spin
                --spins;
                if (randomYields.nextInt(CHAINED_SPINS) == 0)
                    Thread.yield();           // occasionally yield
            }
            else if (s.waiter == null) {
                s.waiter = w;                 // request unpark then recheck
            }
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos > 0L)
                    LockSupport.parkNanos(this, nanos);
            }
            else {
                LockSupport.park(this);
            }
        }
    }

    /**
     * Returns spin/yield value for a node with given predecessor and
     * data mode. See above for explanation.
     * <p>
     *  返回具有给定前导和数据模式的节点的spin / yield值。见上面的解释。
     * 
     */
    private static int spinsFor(Node pred, boolean haveData) {
        if (MP && pred != null) {
            if (pred.isData != haveData)      // phase change
                return FRONT_SPINS + CHAINED_SPINS;
            if (pred.isMatched())             // probably at front
                return FRONT_SPINS;
            if (pred.waiter == null)          // pred apparently spinning
                return CHAINED_SPINS;
        }
        return 0;
    }

    /* -------------- Traversal methods -------------- */

    /**
     * Returns the successor of p, or the head node if p.next has been
     * linked to self, which will only be true if traversing with a
     * stale pointer that is now off the list.
     * <p>
     *  返回p的后继节点,或者如果p.next已经链接到self,则返回头节点,如果遍历列表中的陈旧指针,则返回true。
     * 
     */
    final Node succ(Node p) {
        Node next = p.next;
        return (p == next) ? head : next;
    }

    /**
     * Returns the first unmatched node of the given mode, or null if
     * none.  Used by methods isEmpty, hasWaitingConsumer.
     * <p>
     *  返回给定模式的第一个不匹配节点,如果没有,则返回null。由方法使用isEmpty,hasWaitingConsumer。
     * 
     */
    private Node firstOfMode(boolean isData) {
        for (Node p = head; p != null; p = succ(p)) {
            if (!p.isMatched())
                return (p.isData == isData) ? p : null;
        }
        return null;
    }

    /**
     * Version of firstOfMode used by Spliterator
     * <p>
     *  Spliterator使用的firstOfMode的版本
     * 
     */
    final Node firstDataNode() {
        for (Node p = head; p != null;) {
            Object item = p.item;
            if (p.isData) {
                if (item != null && item != p)
                    return p;
            }
            else if (item == null)
                break;
            if (p == (p = p.next))
                p = head;
        }
        return null;
    }

    /**
     * Returns the item in the first unmatched node with isData; or
     * null if none.  Used by peek.
     * <p>
     *  使用isData返回第一个不匹配节点中的项;或null如果没有。用于偷看。
     * 
     */
    private E firstDataItem() {
        for (Node p = head; p != null; p = succ(p)) {
            Object item = p.item;
            if (p.isData) {
                if (item != null && item != p)
                    return LinkedTransferQueue.<E>cast(item);
            }
            else if (item == null)
                return null;
        }
        return null;
    }

    /**
     * Traverses and counts unmatched nodes of the given mode.
     * Used by methods size and getWaitingConsumerCount.
     * <p>
     *  遍历和计数给定模式的不匹配节点。由方法size和getWaitingConsumerCount使用。
     * 
     */
    private int countOfMode(boolean data) {
        int count = 0;
        for (Node p = head; p != null; ) {
            if (!p.isMatched()) {
                if (p.isData != data)
                    return 0;
                if (++count == Integer.MAX_VALUE) // saturated
                    break;
            }
            Node n = p.next;
            if (n != p)
                p = n;
            else {
                count = 0;
                p = head;
            }
        }
        return count;
    }

    final class Itr implements Iterator<E> {
        private Node nextNode;   // next node to return item for
        private E nextItem;      // the corresponding item
        private Node lastRet;    // last returned node, to support remove
        private Node lastPred;   // predecessor to unlink lastRet

        /**
         * Moves to next node after prev, or first node if prev null.
         * <p>
         *  在prev之后移动到下一个节点,或者如果prev null,则移动到第一个节点。
         * 
         */
        private void advance(Node prev) {
            /*
             * To track and avoid buildup of deleted nodes in the face
             * of calls to both Queue.remove and Itr.remove, we must
             * include variants of unsplice and sweep upon each
             * advance: Upon Itr.remove, we may need to catch up links
             * from lastPred, and upon other removes, we might need to
             * skip ahead from stale nodes and unsplice deleted ones
             * found while advancing.
             * <p>
             *  为了在面对Queue.remove和Itr.remove的调用时跟踪和避免已删除节点的累积,我们必须包括unsplice的变体并扫描每个提前：在Itr.remove中,我们可能需要从lastPred追
             * 上链接,并且对其他删除,我们可能需要从过时的节点向前跳过并且在推进时发现删除的节点。
             * 
             */

            Node r, b; // reset lastPred upon possible deletion of lastRet
            if ((r = lastRet) != null && !r.isMatched())
                lastPred = r;    // next lastPred is old lastRet
            else if ((b = lastPred) == null || b.isMatched())
                lastPred = null; // at start of list
            else {
                Node s, n;       // help with removal of lastPred.next
                while ((s = b.next) != null &&
                       s != b && s.isMatched() &&
                       (n = s.next) != null && n != s)
                    b.casNext(s, n);
            }

            this.lastRet = prev;

            for (Node p = prev, s, n;;) {
                s = (p == null) ? head : p.next;
                if (s == null)
                    break;
                else if (s == p) {
                    p = null;
                    continue;
                }
                Object item = s.item;
                if (s.isData) {
                    if (item != null && item != s) {
                        nextItem = LinkedTransferQueue.<E>cast(item);
                        nextNode = s;
                        return;
                    }
                }
                else if (item == null)
                    break;
                // assert s.isMatched();
                if (p == null)
                    p = s;
                else if ((n = s.next) == null)
                    break;
                else if (s == n)
                    p = null;
                else
                    p.casNext(s, n);
            }
            nextNode = null;
            nextItem = null;
        }

        Itr() {
            advance(null);
        }

        public final boolean hasNext() {
            return nextNode != null;
        }

        public final E next() {
            Node p = nextNode;
            if (p == null) throw new NoSuchElementException();
            E e = nextItem;
            advance(p);
            return e;
        }

        public final void remove() {
            final Node lastRet = this.lastRet;
            if (lastRet == null)
                throw new IllegalStateException();
            this.lastRet = null;
            if (lastRet.tryMatchData())
                unsplice(lastPred, lastRet);
        }
    }

    /** A customized variant of Spliterators.IteratorSpliterator */
    static final class LTQSpliterator<E> implements Spliterator<E> {
        static final int MAX_BATCH = 1 << 25;  // max batch array size;
        final LinkedTransferQueue<E> queue;
        Node current;    // current node; null until initialized
        int batch;          // batch size for splits
        boolean exhausted;  // true when no more nodes
        LTQSpliterator(LinkedTransferQueue<E> queue) {
            this.queue = queue;
        }

        public Spliterator<E> trySplit() {
            Node p;
            final LinkedTransferQueue<E> q = this.queue;
            int b = batch;
            int n = (b <= 0) ? 1 : (b >= MAX_BATCH) ? MAX_BATCH : b + 1;
            if (!exhausted &&
                ((p = current) != null || (p = q.firstDataNode()) != null) &&
                p.next != null) {
                Object[] a = new Object[n];
                int i = 0;
                do {
                    if ((a[i] = p.item) != null)
                        ++i;
                    if (p == (p = p.next))
                        p = q.firstDataNode();
                } while (p != null && i < n);
                if ((current = p) == null)
                    exhausted = true;
                if (i > 0) {
                    batch = i;
                    return Spliterators.spliterator
                        (a, 0, i, Spliterator.ORDERED | Spliterator.NONNULL |
                         Spliterator.CONCURRENT);
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> action) {
            Node p;
            if (action == null) throw new NullPointerException();
            final LinkedTransferQueue<E> q = this.queue;
            if (!exhausted &&
                ((p = current) != null || (p = q.firstDataNode()) != null)) {
                exhausted = true;
                do {
                    Object e = p.item;
                    if (p == (p = p.next))
                        p = q.firstDataNode();
                    if (e != null)
                        action.accept((E)e);
                } while (p != null);
            }
        }

        @SuppressWarnings("unchecked")
        public boolean tryAdvance(Consumer<? super E> action) {
            Node p;
            if (action == null) throw new NullPointerException();
            final LinkedTransferQueue<E> q = this.queue;
            if (!exhausted &&
                ((p = current) != null || (p = q.firstDataNode()) != null)) {
                Object e;
                do {
                    e = p.item;
                    if (p == (p = p.next))
                        p = q.firstDataNode();
                } while (e == null && p != null);
                if ((current = p) == null)
                    exhausted = true;
                if (e != null) {
                    action.accept((E)e);
                    return true;
                }
            }
            return false;
        }

        public long estimateSize() { return Long.MAX_VALUE; }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.NONNULL |
                Spliterator.CONCURRENT;
        }
    }

    /**
     * Returns a {@link Spliterator} over the elements in this queue.
     *
     * <p>The returned spliterator is
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#CONCURRENT},
     * {@link Spliterator#ORDERED}, and {@link Spliterator#NONNULL}.
     *
     * @implNote
     * The {@code Spliterator} implements {@code trySplit} to permit limited
     * parallelism.
     *
     * <p>
     *  在此队列中的元素上返回{@link Spliterator}。
     * 
     *  <p>返回的分隔符为<a href="package-summary.html#Weakly"> <i>弱一致</i> </a>。
     * 
     * <p> {@code Spliterator}报告{@link Spliterator#CONCURRENT},{@link Spliterator#ORDERED}和{@link Spliterator#NONNULL}
     * 。
     * 
     *  @implNote {@code Spliterator}实现{@code trySplit}以允许有限的并行性。
     * 
     * 
     * @return a {@code Spliterator} over the elements in this queue
     * @since 1.8
     */
    public Spliterator<E> spliterator() {
        return new LTQSpliterator<E>(this);
    }

    /* -------------- Removal methods -------------- */

    /**
     * Unsplices (now or later) the given deleted/cancelled node with
     * the given predecessor.
     *
     * <p>
     *  Unsplices(现在或以后)给定的删除/取消节点与给定的前任。
     * 
     * 
     * @param pred a node that was at one time known to be the
     * predecessor of s, or null or s itself if s is/was at head
     * @param s the node to be unspliced
     */
    final void unsplice(Node pred, Node s) {
        s.forgetContents(); // forget unneeded fields
        /*
         * See above for rationale. Briefly: if pred still points to
         * s, try to unlink s.  If s cannot be unlinked, because it is
         * trailing node or pred might be unlinked, and neither pred
         * nor s are head or offlist, add to sweepVotes, and if enough
         * votes have accumulated, sweep.
         * <p>
         *  见上文的理由。简而言之：如果pred仍然指向s,请尝试取消链接s。
         * 如果s无法取消链接,因为它是拖尾节点或pred可能已取消链接,并且pred和s都不是head或offlist,请添加到sweepVotes,如果有足够的投票已累积,则清除。
         * 
         */
        if (pred != null && pred != s && pred.next == s) {
            Node n = s.next;
            if (n == null ||
                (n != s && pred.casNext(s, n) && pred.isMatched())) {
                for (;;) {               // check if at, or could be, head
                    Node h = head;
                    if (h == pred || h == s || h == null)
                        return;          // at head or list empty
                    if (!h.isMatched())
                        break;
                    Node hn = h.next;
                    if (hn == null)
                        return;          // now empty
                    if (hn != h && casHead(h, hn))
                        h.forgetNext();  // advance head
                }
                if (pred.next != pred && s.next != s) { // recheck if offlist
                    for (;;) {           // sweep now if enough votes
                        int v = sweepVotes;
                        if (v < SWEEP_THRESHOLD) {
                            if (casSweepVotes(v, v + 1))
                                break;
                        }
                        else if (casSweepVotes(v, 0)) {
                            sweep();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Unlinks matched (typically cancelled) nodes encountered in a
     * traversal from head.
     * <p>
     *  取消链接在从头开始的遍历中遇到的匹配(通常是取消的)节点。
     * 
     */
    private void sweep() {
        for (Node p = head, s, n; p != null && (s = p.next) != null; ) {
            if (!s.isMatched())
                // Unmatched nodes are never self-linked
                p = s;
            else if ((n = s.next) == null) // trailing node is pinned
                break;
            else if (s == n)    // stale
                // No need to also check for p == s, since that implies s == n
                p = head;
            else
                p.casNext(s, n);
        }
    }

    /**
     * Main implementation of remove(Object)
     * <p>
     *  主要实现remove(Object)
     * 
     */
    private boolean findAndRemove(Object e) {
        if (e != null) {
            for (Node pred = null, p = head; p != null; ) {
                Object item = p.item;
                if (p.isData) {
                    if (item != null && item != p && e.equals(item) &&
                        p.tryMatchData()) {
                        unsplice(pred, p);
                        return true;
                    }
                }
                else if (item == null)
                    break;
                pred = p;
                if ((p = p.next) == pred) { // stale
                    pred = null;
                    p = head;
                }
            }
        }
        return false;
    }

    /**
     * Creates an initially empty {@code LinkedTransferQueue}.
     * <p>
     *  创建最初为空的{@code LinkedTransferQueue}。
     * 
     */
    public LinkedTransferQueue() {
    }

    /**
     * Creates a {@code LinkedTransferQueue}
     * initially containing the elements of the given collection,
     * added in traversal order of the collection's iterator.
     *
     * <p>
     *  创建一个最初包含给定集合的元素的{@code LinkedTransferQueue},以集合的迭代器的遍历顺序添加。
     * 
     * 
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if the specified collection or any
     *         of its elements are null
     */
    public LinkedTransferQueue(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never block.
     *
     * <p>
     *  在此队列的尾部插入指定的元素。由于队列是无界的,这个方法永远不会阻塞。
     * 
     * 
     * @throws NullPointerException if the specified element is null
     */
    public void put(E e) {
        xfer(e, true, ASYNC, 0);
    }

    /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never block or
     * return {@code false}.
     *
     * <p>
     *  在此队列的尾部插入指定的元素。由于队列是无界的,所以这个方法不会阻塞或返回{@code false}。
     * 
     * 
     * @return {@code true} (as specified by
     *  {@link java.util.concurrent.BlockingQueue#offer(Object,long,TimeUnit)
     *  BlockingQueue.offer})
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(E e, long timeout, TimeUnit unit) {
        xfer(e, true, ASYNC, 0);
        return true;
    }

    /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never return {@code false}.
     *
     * <p>
     *  在此队列的尾部插入指定的元素。由于队列是无界的,这个方法永远不会返回{@code false}。
     * 
     * 
     * @return {@code true} (as specified by {@link Queue#offer})
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(E e) {
        xfer(e, true, ASYNC, 0);
        return true;
    }

    /**
     * Inserts the specified element at the tail of this queue.
     * As the queue is unbounded, this method will never throw
     * {@link IllegalStateException} or return {@code false}.
     *
     * <p>
     *  在此队列的尾部插入指定的元素。由于队列是无界的,这个方法不会抛出{@link IllegalStateException}或返回{@code false}。
     * 
     * 
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws NullPointerException if the specified element is null
     */
    public boolean add(E e) {
        xfer(e, true, ASYNC, 0);
        return true;
    }

    /**
     * Transfers the element to a waiting consumer immediately, if possible.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * otherwise returning {@code false} without enqueuing the element.
     *
     * <p>
     * 如果可能,立即将元素传输给等待的消费者。
     * 
     *  <p>更准确地说,如果存在已经等待接收它的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}),则立即传输指定的元素,否则返回{@code false}
     * ,而不将元素排队。
     * 
     * 
     * @throws NullPointerException if the specified element is null
     */
    public boolean tryTransfer(E e) {
        return xfer(e, true, NOW, 0) == null;
    }

    /**
     * Transfers the element to a consumer, waiting if necessary to do so.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * else inserts the specified element at the tail of this queue
     * and waits until the element is received by a consumer.
     *
     * <p>
     *  将元素传输给消费者,如果必要,请等待。
     * 
     *  <p>更精确地说,如果存在已等待接收消息的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}中),则立即传输指定的元素,否则插入指定的元素在此队
     * 列的尾部,并等待,直到消费者接收到该元素。
     * 
     * 
     * @throws NullPointerException if the specified element is null
     */
    public void transfer(E e) throws InterruptedException {
        if (xfer(e, true, SYNC, 0) != null) {
            Thread.interrupted(); // failure possible only due to interrupt
            throw new InterruptedException();
        }
    }

    /**
     * Transfers the element to a consumer if it is possible to do so
     * before the timeout elapses.
     *
     * <p>More precisely, transfers the specified element immediately
     * if there exists a consumer already waiting to receive it (in
     * {@link #take} or timed {@link #poll(long,TimeUnit) poll}),
     * else inserts the specified element at the tail of this queue
     * and waits until the element is received by a consumer,
     * returning {@code false} if the specified wait time elapses
     * before the element can be transferred.
     *
     * <p>
     *  如果在超时时间之前可以这样做,将元素传递给消费者。
     * 
     *  <p>更精确地说,如果存在已等待接收消息的消费者(在{@link #take}或定时{@link #poll(long,TimeUnit)poll}中),则立即传输指定的元素,否则插入指定的元素在此队
     * 列的尾部等待,直到消费者接收到元素,如果在元素可以传输之前经过了指定的等待时间,则返回{@code false}。
     * 
     * 
     * @throws NullPointerException if the specified element is null
     */
    public boolean tryTransfer(E e, long timeout, TimeUnit unit)
        throws InterruptedException {
        if (xfer(e, true, TIMED, unit.toNanos(timeout)) == null)
            return true;
        if (!Thread.interrupted())
            return false;
        throw new InterruptedException();
    }

    public E take() throws InterruptedException {
        E e = xfer(null, false, SYNC, 0);
        if (e != null)
            return e;
        Thread.interrupted();
        throw new InterruptedException();
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E e = xfer(null, false, TIMED, unit.toNanos(timeout));
        if (e != null || !Thread.interrupted())
            return e;
        throw new InterruptedException();
    }

    public E poll() {
        return xfer(null, false, NOW, 0);
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public int drainTo(Collection<? super E> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        int n = 0;
        for (E e; (e = poll()) != null;) {
            c.add(e);
            ++n;
        }
        return n;
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        int n = 0;
        for (E e; n < maxElements && (e = poll()) != null;) {
            c.add(e);
            ++n;
        }
        return n;
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     * The elements will be returned in order from first (head) to last (tail).
     *
     * <p>The returned iterator is
     * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
     *
     * <p>
     *  以正确的顺序返回此队列中的元素的迭代器。元素将按从头(头)到尾(尾)的顺序返回。
     * 
     *  <p>返回的迭代器为<a href="package-summary.html#Weakly"> <i>弱一致</i> </a>。
     * 
     * 
     * @return an iterator over the elements in this queue in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    public E peek() {
        return firstDataItem();
    }

    /**
     * Returns {@code true} if this queue contains no elements.
     *
     * <p>
     *  如果此队列不包含元素,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this queue contains no elements
     */
    public boolean isEmpty() {
        for (Node p = head; p != null; p = succ(p)) {
            if (!p.isMatched())
                return !p.isData;
        }
        return true;
    }

    public boolean hasWaitingConsumer() {
        return firstOfMode(false) != null;
    }

    /**
     * Returns the number of elements in this queue.  If this queue
     * contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * <p>Beware that, unlike in most collections, this method is
     * <em>NOT</em> a constant-time operation. Because of the
     * asynchronous nature of these queues, determining the current
     * number of elements requires an O(n) traversal.
     *
     * <p>
     * 返回此队列中的元素数。如果此队列包含的元素超过{@code Integer.MAX_VALUE}个元素,则返回{@code Integer.MAX_VALUE}。
     * 
     *  <p>请注意,与大多数集合不同,此方法是<em>不是</em>恒定时操作。由于这些队列的异步性质,确定元素的当前数量需要O(n)遍历。
     * 
     * 
     * @return the number of elements in this queue
     */
    public int size() {
        return countOfMode(true);
    }

    public int getWaitingConsumerCount() {
        return countOfMode(false);
    }

    /**
     * Removes a single instance of the specified element from this queue,
     * if it is present.  More formally, removes an element {@code e} such
     * that {@code o.equals(e)}, if this queue contains one or more such
     * elements.
     * Returns {@code true} if this queue contained the specified element
     * (or equivalently, if this queue changed as a result of the call).
     *
     * <p>
     *  从此队列中删除指定元素的单个实例(如果存在)。更正式地,如果此队列包含一个或多个这样的元素,则删除{@code e} {@code o.equals(e)}的元素。
     * 如果此队列包含指定的元素(或等效地,如果此队列作为调用的结果而更改),则返回{@code true}。
     * 
     * 
     * @param o element to be removed from this queue, if present
     * @return {@code true} if this queue changed as a result of the call
     */
    public boolean remove(Object o) {
        return findAndRemove(o);
    }

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * <p>
     *  如果此队列包含指定的元素,则返回{@code true}。更正式地说,当且仅当此队列包含至少一个{@code e}元素{@code o.equals(e)}时,返回{@code true}。
     * 
     * 
     * @param o object to be checked for containment in this queue
     * @return {@code true} if this queue contains the specified element
     */
    public boolean contains(Object o) {
        if (o == null) return false;
        for (Node p = head; p != null; p = succ(p)) {
            Object item = p.item;
            if (p.isData) {
                if (item != null && item != p && o.equals(item))
                    return true;
            }
            else if (item == null)
                break;
        }
        return false;
    }

    /**
     * Always returns {@code Integer.MAX_VALUE} because a
     * {@code LinkedTransferQueue} is not capacity constrained.
     *
     * <p>
     *  始终返回{@code Integer.MAX_VALUE},因为{@code LinkedTransferQueue}不受容量限制。
     * 
     * 
     * @return {@code Integer.MAX_VALUE} (as specified by
     *         {@link java.util.concurrent.BlockingQueue#remainingCapacity()
     *         BlockingQueue.remainingCapacity})
     */
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    /**
     * Saves this queue to a stream (that is, serializes it).
     *
     * <p>
     *  将此队列保存到流(即,序列化它)。
     * 
     * 
     * @param s the stream
     * @throws java.io.IOException if an I/O error occurs
     * @serialData All of the elements (each an {@code E}) in
     * the proper order, followed by a null
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        s.defaultWriteObject();
        for (E e : this)
            s.writeObject(e);
        // Use trailing null as sentinel
        s.writeObject(null);
    }

    /**
     * Reconstitutes this queue from a stream (that is, deserializes it).
     * <p>
     *  从流重构此队列(即,反序列化它)。
     * 
     * @param s the stream
     * @throws ClassNotFoundException if the class of a serialized object
     *         could not be found
     * @throws java.io.IOException if an I/O error occurs
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        for (;;) {
            @SuppressWarnings("unchecked")
            E item = (E) s.readObject();
            if (item == null)
                break;
            else
                offer(item);
        }
    }

    // Unsafe mechanics

    private static final sun.misc.Unsafe UNSAFE;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long sweepVotesOffset;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> k = LinkedTransferQueue.class;
            headOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("head"));
            tailOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("tail"));
            sweepVotesOffset = UNSAFE.objectFieldOffset
                (k.getDeclaredField("sweepVotes"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
