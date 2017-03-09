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
 * Written by Doug Lea, Bill Scherer, and Michael Scott with
 * assistance from members of JCP JSR-166 Expert Group and released to
 * the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea,Bill Scherer和Michael Scott撰写,JCP JSR-166专家组成员的帮助下发布,并发布到公共领域,如http://creativecommons.org/p
 * ublicdomain/zero/1.0/。
 * 
 */

package java.util.concurrent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * A synchronization point at which threads can pair and swap elements
 * within pairs.  Each thread presents some object on entry to the
 * {@link #exchange exchange} method, matches with a partner thread,
 * and receives its partner's object on return.  An Exchanger may be
 * viewed as a bidirectional form of a {@link SynchronousQueue}.
 * Exchangers may be useful in applications such as genetic algorithms
 * and pipeline designs.
 *
 * <p><b>Sample Usage:</b>
 * Here are the highlights of a class that uses an {@code Exchanger}
 * to swap buffers between threads so that the thread filling the
 * buffer gets a freshly emptied one when it needs it, handing off the
 * filled one to the thread emptying the buffer.
 *  <pre> {@code
 * class FillAndEmpty {
 *   Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
 *   DataBuffer initialEmptyBuffer = ... a made-up type
 *   DataBuffer initialFullBuffer = ...
 *
 *   class FillingLoop implements Runnable {
 *     public void run() {
 *       DataBuffer currentBuffer = initialEmptyBuffer;
 *       try {
 *         while (currentBuffer != null) {
 *           addToBuffer(currentBuffer);
 *           if (currentBuffer.isFull())
 *             currentBuffer = exchanger.exchange(currentBuffer);
 *         }
 *       } catch (InterruptedException ex) { ... handle ... }
 *     }
 *   }
 *
 *   class EmptyingLoop implements Runnable {
 *     public void run() {
 *       DataBuffer currentBuffer = initialFullBuffer;
 *       try {
 *         while (currentBuffer != null) {
 *           takeFromBuffer(currentBuffer);
 *           if (currentBuffer.isEmpty())
 *             currentBuffer = exchanger.exchange(currentBuffer);
 *         }
 *       } catch (InterruptedException ex) { ... handle ...}
 *     }
 *   }
 *
 *   void start() {
 *     new Thread(new FillingLoop()).start();
 *     new Thread(new EmptyingLoop()).start();
 *   }
 * }}</pre>
 *
 * <p>Memory consistency effects: For each pair of threads that
 * successfully exchange objects via an {@code Exchanger}, actions
 * prior to the {@code exchange()} in each thread
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * those subsequent to a return from the corresponding {@code exchange()}
 * in the other thread.
 *
 * <p>
 *  线程可以配对和交换对中的元素的同步点。每个线程在进入{@link #exchange exchange}方法时提供一些对象,与合作伙伴线程匹配,并在返回时接收其合作伙伴的对象。
 * 交换机可以被视为{@link SynchronousQueue}的双向形式。交换器在诸如遗传算法和管线设计的应用中可能是有用的。
 * 
 *  <p> <b>示例用法：</b>以下是使用{@code Exchanger}在线程之间交换缓冲区的类的亮点,以便填充缓冲区的线程在需要时清除空间,将填充的一个切换到清空缓冲器的线程。
 *  <pre> {@code class FillAndEmpty {Exchanger <DataBuffer> exchanger = new Exchanger <DataBuffer>(); DataBuffer initialEmptyBuffer = ...一个组成类型DataBuffer initialFullBuffer = ...。
 *  <p> <b>示例用法：</b>以下是使用{@code Exchanger}在线程之间交换缓冲区的类的亮点,以便填充缓冲区的线程在需要时清除空间,将填充的一个切换到清空缓冲器的线程。
 * 
 *  类FillingLoop实现Runnable {public void run(){DataBuffer currentBuffer = initialEmptyBuffer; try {while(currentBuffer！= null){addToBuffer(currentBuffer); if(currentBuffer.isFull())currentBuffer = exchanger.exchange(currentBuffer); }
 * } catch(InterruptedException ex){... handle ...}}}。
 * 
 * 类EmptyingLoop实现Runnable {public void run(){DataBuffer currentBuffer = initialFullBuffer; try {while(currentBuffer！= null){takeFromBuffer(currentBuffer); if(currentBuffer.isEmpty())currentBuffer = exchange.exchange(currentBuffer); }
 * } catch(InterruptedException ex){... handle ...}}}。
 * 
 *  void start(){new Thread(new FillingLoop())。start(); new Thread(new EmptyingLoop())。
 * start(); }}} </pre>。
 * 
 *  <p>内存一致性效果：对于通过{@code Exchanger}成功交换对象的每对线程,每个线程中{@code exchange()}之前的操作<a href ="package-summary.html#MemoryVisibility ">
 *  <i>发生在之前</i> </a>从另一个线程中的相应{@code exchange()}返回。
 * 
 * 
 * @since 1.5
 * @author Doug Lea and Bill Scherer and Michael Scott
 * @param <V> The type of objects that may be exchanged
 */
public class Exchanger<V> {

    /*
     * Overview: The core algorithm is, for an exchange "slot",
     * and a participant (caller) with an item:
     *
     * for (;;) {
     *   if (slot is empty) {                       // offer
     *     place item in a Node;
     *     if (can CAS slot from empty to node) {
     *       wait for release;
     *       return matching item in node;
     *     }
     *   }
     *   else if (can CAS slot from node to empty) { // release
     *     get the item in node;
     *     set matching item in node;
     *     release waiting thread;
     *   }
     *   // else retry on CAS failure
     * }
     *
     * This is among the simplest forms of a "dual data structure" --
     * see Scott and Scherer's DISC 04 paper and
     * http://www.cs.rochester.edu/research/synchronization/pseudocode/duals.html
     *
     * This works great in principle. But in practice, like many
     * algorithms centered on atomic updates to a single location, it
     * scales horribly when there are more than a few participants
     * using the same Exchanger. So the implementation instead uses a
     * form of elimination arena, that spreads out this contention by
     * arranging that some threads typically use different slots,
     * while still ensuring that eventually, any two parties will be
     * able to exchange items. That is, we cannot completely partition
     * across threads, but instead give threads arena indices that
     * will on average grow under contention and shrink under lack of
     * contention. We approach this by defining the Nodes that we need
     * anyway as ThreadLocals, and include in them per-thread index
     * and related bookkeeping state. (We can safely reuse per-thread
     * nodes rather than creating them fresh each time because slots
     * alternate between pointing to a node vs null, so cannot
     * encounter ABA problems. However, we do need some care in
     * resetting them between uses.)
     *
     * Implementing an effective arena requires allocating a bunch of
     * space, so we only do so upon detecting contention (except on
     * uniprocessors, where they wouldn't help, so aren't used).
     * Otherwise, exchanges use the single-slot slotExchange method.
     * On contention, not only must the slots be in different
     * locations, but the locations must not encounter memory
     * contention due to being on the same cache line (or more
     * generally, the same coherence unit).  Because, as of this
     * writing, there is no way to determine cacheline size, we define
     * a value that is enough for common platforms.  Additionally,
     * extra care elsewhere is taken to avoid other false/unintended
     * sharing and to enhance locality, including adding padding (via
     * sun.misc.Contended) to Nodes, embedding "bound" as an Exchanger
     * field, and reworking some park/unpark mechanics compared to
     * LockSupport versions.
     *
     * The arena starts out with only one used slot. We expand the
     * effective arena size by tracking collisions; i.e., failed CASes
     * while trying to exchange. By nature of the above algorithm, the
     * only kinds of collision that reliably indicate contention are
     * when two attempted releases collide -- one of two attempted
     * offers can legitimately fail to CAS without indicating
     * contention by more than one other thread. (Note: it is possible
     * but not worthwhile to more precisely detect contention by
     * reading slot values after CAS failures.)  When a thread has
     * collided at each slot within the current arena bound, it tries
     * to expand the arena size by one. We track collisions within
     * bounds by using a version (sequence) number on the "bound"
     * field, and conservatively reset collision counts when a
     * participant notices that bound has been updated (in either
     * direction).
     *
     * The effective arena size is reduced (when there is more than
     * one slot) by giving up on waiting after a while and trying to
     * decrement the arena size on expiration. The value of "a while"
     * is an empirical matter.  We implement by piggybacking on the
     * use of spin->yield->block that is essential for reasonable
     * waiting performance anyway -- in a busy exchanger, offers are
     * usually almost immediately released, in which case context
     * switching on multiprocessors is extremely slow/wasteful.  Arena
     * waits just omit the blocking part, and instead cancel. The spin
     * count is empirically chosen to be a value that avoids blocking
     * 99% of the time under maximum sustained exchange rates on a
     * range of test machines. Spins and yields entail some limited
     * randomness (using a cheap xorshift) to avoid regular patterns
     * that can induce unproductive grow/shrink cycles. (Using a
     * pseudorandom also helps regularize spin cycle duration by
     * making branches unpredictable.)  Also, during an offer, a
     * waiter can "know" that it will be released when its slot has
     * changed, but cannot yet proceed until match is set.  In the
     * mean time it cannot cancel the offer, so instead spins/yields.
     * Note: It is possible to avoid this secondary check by changing
     * the linearization point to be a CAS of the match field (as done
     * in one case in the Scott & Scherer DISC paper), which also
     * increases asynchrony a bit, at the expense of poorer collision
     * detection and inability to always reuse per-thread nodes. So
     * the current scheme is typically a better tradeoff.
     *
     * On collisions, indices traverse the arena cyclically in reverse
     * order, restarting at the maximum index (which will tend to be
     * sparsest) when bounds change. (On expirations, indices instead
     * are halved until reaching 0.) It is possible (and has been
     * tried) to use randomized, prime-value-stepped, or double-hash
     * style traversal instead of simple cyclic traversal to reduce
     * bunching.  But empirically, whatever benefits these may have
     * don't overcome their added overhead: We are managing operations
     * that occur very quickly unless there is sustained contention,
     * so simpler/faster control policies work better than more
     * accurate but slower ones.
     *
     * Because we use expiration for arena size control, we cannot
     * throw TimeoutExceptions in the timed version of the public
     * exchange method until the arena size has shrunken to zero (or
     * the arena isn't enabled). This may delay response to timeout
     * but is still within spec.
     *
     * Essentially all of the implementation is in methods
     * slotExchange and arenaExchange. These have similar overall
     * structure, but differ in too many details to combine. The
     * slotExchange method uses the single Exchanger field "slot"
     * rather than arena array elements. However, it still needs
     * minimal collision detection to trigger arena construction.
     * (The messiest part is making sure interrupt status and
     * InterruptedExceptions come out right during transitions when
     * both methods may be called. This is done by using null return
     * as a sentinel to recheck interrupt status.)
     *
     * As is too common in this sort of code, methods are monolithic
     * because most of the logic relies on reads of fields that are
     * maintained as local variables so can't be nicely factored --
     * mainly, here, bulky spin->yield->block/cancel code), and
     * heavily dependent on intrinsics (Unsafe) to use inlined
     * embedded CAS and related memory access operations (that tend
     * not to be as readily inlined by dynamic compilers when they are
     * hidden behind other methods that would more nicely name and
     * encapsulate the intended effects). This includes the use of
     * putOrderedX to clear fields of the per-thread Nodes between
     * uses. Note that field Node.item is not declared as volatile
     * even though it is read by releasing threads, because they only
     * do so after CAS operations that must precede access, and all
     * uses by the owning thread are otherwise acceptably ordered by
     * other operations. (Because the actual points of atomicity are
     * slot CASes, it would also be legal for the write to Node.match
     * in a release to be weaker than a full volatile write. However,
     * this is not done because it could allow further postponement of
     * the write, delaying progress.)
     * <p>
     *  概述：核心算法是,对于交换"槽",并且参与者(呼叫者)具有项目：
     * 
     *  for(;;){if(slot is empty){//在节点中提供地点项目; if(can CAS slot from empty to node){wait for release;返回节点中的匹配项; }
     * } else if(can CAS slot from node to empty){//释放获取节点中的项;在节点中设置匹配项;释放等待线程; } // else retry on CAS failu
     * re}。
     * 
     *  这是"双数据结构"的最简单形式之一 - 参见Scott和Scherer的DISC 04论文和http://www.cs.rochester.edu/research/synchronization/p
     * seudocode/duals.html。
     * 
     * 这在原理上很好。但在实践中,像许多算法集中在一个单一的位置的原子更新,当有多个参与者使用相同的交换器时,它可怕地扩展。
     * 因此,实现改为使用消除竞技场的形式,通过安排某些线程通常使用不同的时隙,同时仍然确保任何两方将能够交换项目来扩展这种争用。
     * 也就是说,我们不能完全跨线程划分,而是给线程竞技场指数,它们将平均在竞争下增长,并在缺乏竞争的情况下收缩。
     * 我们通过定义我们需要的节点作为ThreadLocals来处理它,并且在每个线程索引和相关的记帐状态中包括它们。
     *  (我们可以安全地重用每个线程的节点,而不是每次都创建它们,因为槽在指向一个节点和空值之间交替,所以不能遇到ABA问题,但是,我们确实需要在使用之间重置它们。
     * 
     * 实现有效的领域需要分配一堆空间,所以我们只在检测到争用时这样做(除了在单处理器上,他们不会帮助,所以没有使用)。否则,交换使用单时隙slotExchange方法。
     * 在争用时,不仅时隙必须在不同的位置,而且由于位于相同的高速缓存线(或更一般地,相同的相干单元)上,所以位置不必遭遇存储器争用。
     * 因为,在撰写本文时,没有办法确定缓存行大小,因此我们定义了一个足以用于通用平台的值。
     * 另外,在其他地方需要特别小心,以避免其他假的/非预期的共享和增强局部性,包括添加填充(通过sun.misc.Contended)到节点,嵌入"绑定"为交换器字段,并重做一些公园/到LockSupport
     * 版本。
     * 因为,在撰写本文时,没有办法确定缓存行大小,因此我们定义了一个足以用于通用平台的值。
     * 
     * 竞技场开始只有一个使用的插槽。我们通过跟踪碰撞来扩展有效的竞技场尺寸;即尝试交换时失败的CASes。
     * 由于上述算法的本质,可靠地指示争用的唯一种类的冲突是当两个尝试释放冲突时 - 两个尝试的提议中的一个可以合法地失败到CAS而不指示由多于一个的其他线程的争用。
     *  (注意：在CAS失败之后通过读取时隙值来更精确地检测争用是可能的,但是不值得)。当线程在当前竞技场中的每个时隙发生冲突时,它试图将竞技场大小扩展一。
     * 我们通过使用"绑定"字段上的版本(序列)号来跟踪边界内的冲突,并且当参与者注意到绑定已经被更新(在任一方向上)时,保守地重置冲突计数。
     * 
     * 有效竞技场尺寸减少(当有多于一个槽时)通过放弃等待一段时间并且试图在到期时减小竞技场尺寸。 "a while"的值是一个经验问题。
     * 我们通过搭载使用spin-> yield-> block来实现,这对于合理的等待性能是至关重要的 - 在繁忙的交换机中,提供通常几乎立即被释放,在这种情况下,多处理器上的上下文切换极其缓慢/浪费。
     *  Arena等待只是省略阻塞部分,而是取消。自旋计数经验性地选择为避免在一系列测试机器上在最大持续交换速率下阻塞99％的时间的值。
     * 自旋和产量需要一些有限的随机性(使用廉价的移位),以避免可能诱发无效增长/收缩周期的规则模式。 (使用伪随机也有助于通过使分支不可预测来使旋转周期持续时间正常化。
     * )另外,在提议期间,服务员可以"知道"当其时隙已经改变时,服务器将被释放,但是直到匹配被设置才能继续。在同一时间,它不能取消报价,所以旋转/收益率。
     * 注意：可以通过将线性化点更改为匹配字段的CAS来避免这种二次检查(如Scott&Scherer DISC论文中的一个案例中所做的那样),这也增加了异步性,但代价是较差的碰撞检测和不能总是重用每线程节点。
     * )另外,在提议期间,服务员可以"知道"当其时隙已经改变时,服务器将被释放,但是直到匹配被设置才能继续。在同一时间,它不能取消报价,所以旋转/收益率。因此,当前方案通常是更好的权衡。
     * 
     * 在碰撞时,索引以相反的顺序循环地运行竞技场,当界限改变时以最大索引(其将倾向于是最稀疏的)重新开始。
     *  (在到期时,索引被减半,直到达到0.)可以(并且已经尝试)使用随机化,原始值步长或双哈希样式遍历,而不是简单的循环遍历来减少聚束。
     * 但是根据经验,这些可能带来的好处无法克服它们增加的开销：我们正在管理运行非常快,除非有持续争用,因此更简单/更快的控制策略比更精确但更慢的控制策略。
     * 
     *  因为我们使用竞技场大小控制的到期,所以我们不能在公共交换方法的定时版本中抛出TimeoutExceptions,直到竞技场尺寸缩小到零(或竞技场未启用)。这可能会延迟对超时的响应,但仍在规范范围内。
     * 
     *  基本上所有的实现都是在方法slotExchange和arenaExchange。这些具有类似的总体结构,但是在太多细节上不同以组合。
     *  slotExchange方法使用单个"交换器"字段"slot",而不是竞技场数组元素。然而,它仍然需要最小的碰撞检测来触发竞技场构造。
     *  (最麻烦的部分是确保中断状态和InterruptedExceptions在转换期间出现,当两个方法都可以被调用时,这是通过使用null返回作为一个哨兵重新检查中断状态。
     * 
     * 在这种类型的代码中太常见,方法是单片的,因为大多数逻辑依赖于作为局部变量维护的字段的读取,所以不能被很好地计算 - 主要是这里,庞大的spin-> yield-> block /取消代码),并且严重依赖
     * 于内在函数(Unsafe)来使用内联嵌入式CAS和相关的内存访问操作(当它们隐藏在其他方法之后时,它们不会被动态编译器轻松地内联,从而更好地命名和封装预期效果)。
     * 这包括使用putOrderedX来清除使用之间的每线程节点的字段。
     * 请注意,即使Node.item是通过释放线程来读取的,它也不被声明为volatile,因为它们只在必须在访问之前的CAS操作之后才这样做,并且拥有线程的所有使用都被其他操作可接受地排序。
     *  (因为实际的原子性点是slot CASes,在发布中对Node.match的写操作比完全volatile操作要弱一些也是合法的。但是,这并不是因为它允许进一步推迟写操作,推迟进展。)。
     * 
     */

    /**
     * The byte distance (as a shift value) between any two used slots
     * in the arena.  1 << ASHIFT should be at least cacheline size.
     * <p>
     *  在竞技场中任意两个使用的时隙之间的字节距离(作为移位值)。 1 << ASHIFT应该至少是缓存行大小。
     * 
     */
    private static final int ASHIFT = 7;

    /**
     * The maximum supported arena index. The maximum allocatable
     * arena size is MMASK + 1. Must be a power of two minus one, less
     * than (1<<(31-ASHIFT)). The cap of 255 (0xff) more than suffices
     * for the expected scaling limits of the main algorithms.
     * <p>
     * 支持的最大竞技场索引。最大可分配竞技场大小是MMASK + 1.必须是2的幂减1,小于(1 <<(31-ASHIFT))。 255(0xff)的上限足以满足主算法的预期缩放限制。
     * 
     */
    private static final int MMASK = 0xff;

    /**
     * Unit for sequence/version bits of bound field. Each successful
     * change to the bound also adds SEQ.
     * <p>
     *  绑定字段的序列/版本位的单位。每个成功的更改绑定也添加SEQ。
     * 
     */
    private static final int SEQ = MMASK + 1;

    /** The number of CPUs, for sizing and spin control */
    private static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * The maximum slot index of the arena: The number of slots that
     * can in principle hold all threads without contention, or at
     * most the maximum indexable value.
     * <p>
     *  竞技场的最大时隙索引：原则上可以保持所有线程而不竞争的时隙数,或者最多为最大可索引值。
     * 
     */
    static final int FULL = (NCPU >= (MMASK << 1)) ? MMASK : NCPU >>> 1;

    /**
     * The bound for spins while waiting for a match. The actual
     * number of iterations will on average be about twice this value
     * due to randomization. Note: Spinning is disabled when NCPU==1.
     * <p>
     *  在等待匹配的时候旋转。由于随机化,实际的迭代次数将平均约为该值的两倍。注意：当NCPU == 1时,旋转被禁用。
     * 
     */
    private static final int SPINS = 1 << 10;

    /**
     * Value representing null arguments/returns from public
     * methods. Needed because the API originally didn't disallow null
     * arguments, which it should have.
     * <p>
     *  表示来自公共方法的空参数/返回值。需要,因为API最初不允许null参数,它应该有。
     * 
     */
    private static final Object NULL_ITEM = new Object();

    /**
     * Sentinel value returned by internal exchange methods upon
     * timeout, to avoid need for separate timed versions of these
     * methods.
     * <p>
     *  超时后由内部交换方法返回的Sentinel值,以避免需要这些方法的单独的定时版本。
     * 
     */
    private static final Object TIMED_OUT = new Object();

    /**
     * Nodes hold partially exchanged data, plus other per-thread
     * bookkeeping. Padded via @sun.misc.Contended to reduce memory
     * contention.
     * <p>
     *  节点保存部分交换的数据,以及其他每线程簿记。 Padded via @ sun.misc.Contended以减少内存争用。
     * 
     */
    @sun.misc.Contended static final class Node {
        int index;              // Arena index
        int bound;              // Last recorded value of Exchanger.bound
        int collides;           // Number of CAS failures at current bound
        int hash;               // Pseudo-random for spins
        Object item;            // This thread's current item
        volatile Object match;  // Item provided by releasing thread
        volatile Thread parked; // Set to this thread when parked, else null
    }

    /** The corresponding thread local class */
    static final class Participant extends ThreadLocal<Node> {
        public Node initialValue() { return new Node(); }
    }

    /**
     * Per-thread state
     * <p>
     *  每线程状态
     * 
     */
    private final Participant participant;

    /**
     * Elimination array; null until enabled (within slotExchange).
     * Element accesses use emulation of volatile gets and CAS.
     * <p>
     *  消除阵列; null,直到启用(在slotExchange内)。元素访问使用volatile gets和CAS的仿真。
     * 
     */
    private volatile Node[] arena;

    /**
     * Slot used until contention detected.
     * <p>
     *  使用的插槽,直到检测到争用。
     * 
     */
    private volatile Node slot;

    /**
     * The index of the largest valid arena position, OR'ed with SEQ
     * number in high bits, incremented on each update.  The initial
     * update from 0 to SEQ is used to ensure that the arena array is
     * constructed only once.
     * <p>
     *  最大有效竞技场位置的索引,与高位中的SEQ编号进行"或"运算,在每次更新时递增。从0到SEQ的初始更新用于确保竞技场阵列仅构造一次。
     * 
     */
    private volatile int bound;

    /**
     * Exchange function when arenas enabled. See above for explanation.
     *
     * <p>
     * 启用场景时的交换功能。见上面的解释。
     * 
     * 
     * @param item the (non-null) item to exchange
     * @param timed true if the wait is timed
     * @param ns if timed, the maximum wait time, else 0L
     * @return the other thread's item; or null if interrupted; or
     * TIMED_OUT if timed and timed out
     */
    private final Object arenaExchange(Object item, boolean timed, long ns) {
        Node[] a = arena;
        Node p = participant.get();
        for (int i = p.index;;) {                      // access slot at i
            int b, m, c; long j;                       // j is raw array offset
            Node q = (Node)U.getObjectVolatile(a, j = (i << ASHIFT) + ABASE);
            if (q != null && U.compareAndSwapObject(a, j, q, null)) {
                Object v = q.item;                     // release
                q.match = item;
                Thread w = q.parked;
                if (w != null)
                    U.unpark(w);
                return v;
            }
            else if (i <= (m = (b = bound) & MMASK) && q == null) {
                p.item = item;                         // offer
                if (U.compareAndSwapObject(a, j, null, p)) {
                    long end = (timed && m == 0) ? System.nanoTime() + ns : 0L;
                    Thread t = Thread.currentThread(); // wait
                    for (int h = p.hash, spins = SPINS;;) {
                        Object v = p.match;
                        if (v != null) {
                            U.putOrderedObject(p, MATCH, null);
                            p.item = null;             // clear for next use
                            p.hash = h;
                            return v;
                        }
                        else if (spins > 0) {
                            h ^= h << 1; h ^= h >>> 3; h ^= h << 10; // xorshift
                            if (h == 0)                // initialize hash
                                h = SPINS | (int)t.getId();
                            else if (h < 0 &&          // approx 50% true
                                     (--spins & ((SPINS >>> 1) - 1)) == 0)
                                Thread.yield();        // two yields per wait
                        }
                        else if (U.getObjectVolatile(a, j) != p)
                            spins = SPINS;       // releaser hasn't set match yet
                        else if (!t.isInterrupted() && m == 0 &&
                                 (!timed ||
                                  (ns = end - System.nanoTime()) > 0L)) {
                            U.putObject(t, BLOCKER, this); // emulate LockSupport
                            p.parked = t;              // minimize window
                            if (U.getObjectVolatile(a, j) == p)
                                U.park(false, ns);
                            p.parked = null;
                            U.putObject(t, BLOCKER, null);
                        }
                        else if (U.getObjectVolatile(a, j) == p &&
                                 U.compareAndSwapObject(a, j, p, null)) {
                            if (m != 0)                // try to shrink
                                U.compareAndSwapInt(this, BOUND, b, b + SEQ - 1);
                            p.item = null;
                            p.hash = h;
                            i = p.index >>>= 1;        // descend
                            if (Thread.interrupted())
                                return null;
                            if (timed && m == 0 && ns <= 0L)
                                return TIMED_OUT;
                            break;                     // expired; restart
                        }
                    }
                }
                else
                    p.item = null;                     // clear offer
            }
            else {
                if (p.bound != b) {                    // stale; reset
                    p.bound = b;
                    p.collides = 0;
                    i = (i != m || m == 0) ? m : m - 1;
                }
                else if ((c = p.collides) < m || m == FULL ||
                         !U.compareAndSwapInt(this, BOUND, b, b + SEQ + 1)) {
                    p.collides = c + 1;
                    i = (i == 0) ? m : i - 1;          // cyclically traverse
                }
                else
                    i = m + 1;                         // grow
                p.index = i;
            }
        }
    }

    /**
     * Exchange function used until arenas enabled. See above for explanation.
     *
     * <p>
     *  在启用场所之前使用的交换功能。见上面的解释。
     * 
     * 
     * @param item the item to exchange
     * @param timed true if the wait is timed
     * @param ns if timed, the maximum wait time, else 0L
     * @return the other thread's item; or null if either the arena
     * was enabled or the thread was interrupted before completion; or
     * TIMED_OUT if timed and timed out
     */
    private final Object slotExchange(Object item, boolean timed, long ns) {
        Node p = participant.get();
        Thread t = Thread.currentThread();
        if (t.isInterrupted()) // preserve interrupt status so caller can recheck
            return null;

        for (Node q;;) {
            if ((q = slot) != null) {
                if (U.compareAndSwapObject(this, SLOT, q, null)) {
                    Object v = q.item;
                    q.match = item;
                    Thread w = q.parked;
                    if (w != null)
                        U.unpark(w);
                    return v;
                }
                // create arena on contention, but continue until slot null
                if (NCPU > 1 && bound == 0 &&
                    U.compareAndSwapInt(this, BOUND, 0, SEQ))
                    arena = new Node[(FULL + 2) << ASHIFT];
            }
            else if (arena != null)
                return null; // caller must reroute to arenaExchange
            else {
                p.item = item;
                if (U.compareAndSwapObject(this, SLOT, null, p))
                    break;
                p.item = null;
            }
        }

        // await release
        int h = p.hash;
        long end = timed ? System.nanoTime() + ns : 0L;
        int spins = (NCPU > 1) ? SPINS : 1;
        Object v;
        while ((v = p.match) == null) {
            if (spins > 0) {
                h ^= h << 1; h ^= h >>> 3; h ^= h << 10;
                if (h == 0)
                    h = SPINS | (int)t.getId();
                else if (h < 0 && (--spins & ((SPINS >>> 1) - 1)) == 0)
                    Thread.yield();
            }
            else if (slot != p)
                spins = SPINS;
            else if (!t.isInterrupted() && arena == null &&
                     (!timed || (ns = end - System.nanoTime()) > 0L)) {
                U.putObject(t, BLOCKER, this);
                p.parked = t;
                if (slot == p)
                    U.park(false, ns);
                p.parked = null;
                U.putObject(t, BLOCKER, null);
            }
            else if (U.compareAndSwapObject(this, SLOT, p, null)) {
                v = timed && ns <= 0L && !t.isInterrupted() ? TIMED_OUT : null;
                break;
            }
        }
        U.putOrderedObject(p, MATCH, null);
        p.item = null;
        p.hash = h;
        return v;
    }

    /**
     * Creates a new Exchanger.
     * <p>
     *  创建新的交换器。
     * 
     */
    public Exchanger() {
        participant = new Participant();
    }

    /**
     * Waits for another thread to arrive at this exchange point (unless
     * the current thread is {@linkplain Thread#interrupt interrupted}),
     * and then transfers the given object to it, receiving its object
     * in return.
     *
     * <p>If another thread is already waiting at the exchange point then
     * it is resumed for thread scheduling purposes and receives the object
     * passed in by the current thread.  The current thread returns immediately,
     * receiving the object passed to the exchange by that other thread.
     *
     * <p>If no other thread is already waiting at the exchange then the
     * current thread is disabled for thread scheduling purposes and lies
     * dormant until one of two things happens:
     * <ul>
     * <li>Some other thread enters the exchange; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread.
     * </ul>
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * for the exchange,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>
     *  等待另一个线程到达这个交换点(除非当前线程是{@linkplain Thread#interrupt interrupted}),然后将给定的对象传递给它,接收它的对象。
     * 
     *  <p>如果另一个线程已经在交换点处等待,则它将恢复用于线程调度目的,并接收当前线程传入的对象。当前线程立即返回,接收由其他线程传递给交换的对象。
     * 
     *  <p>如果没有其他线程已经在交换等待,则当前线程被禁用用于线程调度目的,并处于休眠状态,直到发生以下两种情况之一：
     * <ul>
     *  <li>其他线程进入交换;或<li>一些其他线程{@linkplain线程#中断中断}当前线程。
     * </ul>
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>是{@linkplain线程#中断}在等待交换时,
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * 
     * @param x the object to exchange
     * @return the object provided by the other thread
     * @throws InterruptedException if the current thread was
     *         interrupted while waiting
     */
    @SuppressWarnings("unchecked")
    public V exchange(V x) throws InterruptedException {
        Object v;
        Object item = (x == null) ? NULL_ITEM : x; // translate null args
        if ((arena != null ||
             (v = slotExchange(item, false, 0L)) == null) &&
            ((Thread.interrupted() || // disambiguates null return
              (v = arenaExchange(item, false, 0L)) == null)))
            throw new InterruptedException();
        return (v == NULL_ITEM) ? null : (V)v;
    }

    /**
     * Waits for another thread to arrive at this exchange point (unless
     * the current thread is {@linkplain Thread#interrupt interrupted} or
     * the specified waiting time elapses), and then transfers the given
     * object to it, receiving its object in return.
     *
     * <p>If another thread is already waiting at the exchange point then
     * it is resumed for thread scheduling purposes and receives the object
     * passed in by the current thread.  The current thread returns immediately,
     * receiving the object passed to the exchange by that other thread.
     *
     * <p>If no other thread is already waiting at the exchange then the
     * current thread is disabled for thread scheduling purposes and lies
     * dormant until one of three things happens:
     * <ul>
     * <li>Some other thread enters the exchange; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>The specified waiting time elapses.
     * </ul>
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * for the exchange,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then {@link
     * TimeoutException} is thrown.  If the time is less than or equal
     * to zero, the method will not wait at all.
     *
     * <p>
     *  等待另一个线程到达此交换点(除非当前线程为{@linkplain线程#中断中断}或指定的等待时间消逝),然后将给定对象传递给它,接收其对象。
     * 
     * <p>如果另一个线程已经在交换点处等待,则它将恢复用于线程调度目的,并接收当前线程传入的对象。当前线程立即返回,接收由其他线程传递给交换的对象。
     * 
     *  <p>如果没有其他线程已经在交换等待,则当前线程被禁用用于线程调度目的,并且休眠,直到发生以下三种情况之一：
     * <ul>
     *  <li>其他线程进入交换;或<li>一些其他线程{@linkplain线程#中断中断}当前线程;或<li>指定的等待时间已过。
     * </ul>
     * 
     * @param x the object to exchange
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return the object provided by the other thread
     * @throws InterruptedException if the current thread was
     *         interrupted while waiting
     * @throws TimeoutException if the specified waiting time elapses
     *         before another thread enters the exchange
     */
    @SuppressWarnings("unchecked")
    public V exchange(V x, long timeout, TimeUnit unit)
        throws InterruptedException, TimeoutException {
        Object v;
        Object item = (x == null) ? NULL_ITEM : x;
        long ns = unit.toNanos(timeout);
        if ((arena != null ||
             (v = slotExchange(item, true, ns)) == null) &&
            ((Thread.interrupted() ||
              (v = arenaExchange(item, true, ns)) == null)))
            throw new InterruptedException();
        if (v == TIMED_OUT)
            throw new TimeoutException();
        return (v == NULL_ITEM) ? null : (V)v;
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe U;
    private static final long BOUND;
    private static final long SLOT;
    private static final long MATCH;
    private static final long BLOCKER;
    private static final int ABASE;
    static {
        int s;
        try {
            U = sun.misc.Unsafe.getUnsafe();
            Class<?> ek = Exchanger.class;
            Class<?> nk = Node.class;
            Class<?> ak = Node[].class;
            Class<?> tk = Thread.class;
            BOUND = U.objectFieldOffset
                (ek.getDeclaredField("bound"));
            SLOT = U.objectFieldOffset
                (ek.getDeclaredField("slot"));
            MATCH = U.objectFieldOffset
                (nk.getDeclaredField("match"));
            BLOCKER = U.objectFieldOffset
                (tk.getDeclaredField("parkBlocker"));
            s = U.arrayIndexScale(ak);
            // ABASE absorbs padding in front of element 0
            ABASE = U.arrayBaseOffset(ak) + (1 << ASHIFT);

        } catch (Exception e) {
            throw new Error(e);
        }
        if ((s & (s-1)) != 0 || s > (1 << ASHIFT))
            throw new Error("Unsupported array scale");
    }

}
