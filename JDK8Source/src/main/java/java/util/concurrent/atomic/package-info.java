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
 * A small toolkit of classes that support lock-free thread-safe
 * programming on single variables.  In essence, the classes in this
 * package extend the notion of {@code volatile} values, fields, and
 * array elements to those that also provide an atomic conditional update
 * operation of the form:
 *
 *  <pre> {@code boolean compareAndSet(expectedValue, updateValue);}</pre>
 *
 * <p>This method (which varies in argument types across different
 * classes) atomically sets a variable to the {@code updateValue} if it
 * currently holds the {@code expectedValue}, reporting {@code true} on
 * success.  The classes in this package also contain methods to get and
 * unconditionally set values, as well as a weaker conditional atomic
 * update operation {@code weakCompareAndSet} described below.
 *
 * <p>The specifications of these methods enable implementations to
 * employ efficient machine-level atomic instructions that are available
 * on contemporary processors.  However on some platforms, support may
 * entail some form of internal locking.  Thus the methods are not
 * strictly guaranteed to be non-blocking --
 * a thread may block transiently before performing the operation.
 *
 * <p>Instances of classes
 * {@link java.util.concurrent.atomic.AtomicBoolean},
 * {@link java.util.concurrent.atomic.AtomicInteger},
 * {@link java.util.concurrent.atomic.AtomicLong}, and
 * {@link java.util.concurrent.atomic.AtomicReference}
 * each provide access and updates to a single variable of the
 * corresponding type.  Each class also provides appropriate utility
 * methods for that type.  For example, classes {@code AtomicLong} and
 * {@code AtomicInteger} provide atomic increment methods.  One
 * application is to generate sequence numbers, as in:
 *
 *  <pre> {@code
 * class Sequencer {
 *   private final AtomicLong sequenceNumber
 *     = new AtomicLong(0);
 *   public long next() {
 *     return sequenceNumber.getAndIncrement();
 *   }
 * }}</pre>
 *
 * <p>It is straightforward to define new utility functions that, like
 * {@code getAndIncrement}, apply a function to a value atomically.
 * For example, given some transformation
 * <pre> {@code long transform(long input)}</pre>
 *
 * write your utility method as follows:
 *  <pre> {@code
 * long getAndTransform(AtomicLong var) {
 *   long prev, next;
 *   do {
 *     prev = var.get();
 *     next = transform(prev);
 *   } while (!var.compareAndSet(prev, next));
 *   return prev; // return next; for transformAndGet
 * }}</pre>
 *
 * <p>The memory effects for accesses and updates of atomics generally
 * follow the rules for volatiles, as stated in
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4">
 * The Java Language Specification (17.4 Memory Model)</a>:
 *
 * <ul>
 *
 *   <li> {@code get} has the memory effects of reading a
 * {@code volatile} variable.
 *
 *   <li> {@code set} has the memory effects of writing (assigning) a
 * {@code volatile} variable.
 *
 *   <li> {@code lazySet} has the memory effects of writing (assigning)
 *   a {@code volatile} variable except that it permits reorderings with
 *   subsequent (but not previous) memory actions that do not themselves
 *   impose reordering constraints with ordinary non-{@code volatile}
 *   writes.  Among other usage contexts, {@code lazySet} may apply when
 *   nulling out, for the sake of garbage collection, a reference that is
 *   never accessed again.
 *
 *   <li>{@code weakCompareAndSet} atomically reads and conditionally
 *   writes a variable but does <em>not</em>
 *   create any happens-before orderings, so provides no guarantees
 *   with respect to previous or subsequent reads and writes of any
 *   variables other than the target of the {@code weakCompareAndSet}.
 *
 *   <li> {@code compareAndSet}
 *   and all other read-and-update operations such as {@code getAndIncrement}
 *   have the memory effects of both reading and
 *   writing {@code volatile} variables.
 * </ul>
 *
 * <p>In addition to classes representing single values, this package
 * contains <em>Updater</em> classes that can be used to obtain
 * {@code compareAndSet} operations on any selected {@code volatile}
 * field of any selected class.
 *
 * {@link java.util.concurrent.atomic.AtomicReferenceFieldUpdater},
 * {@link java.util.concurrent.atomic.AtomicIntegerFieldUpdater}, and
 * {@link java.util.concurrent.atomic.AtomicLongFieldUpdater} are
 * reflection-based utilities that provide access to the associated
 * field types.  These are mainly of use in atomic data structures in
 * which several {@code volatile} fields of the same node (for
 * example, the links of a tree node) are independently subject to
 * atomic updates.  These classes enable greater flexibility in how
 * and when to use atomic updates, at the expense of more awkward
 * reflection-based setup, less convenient usage, and weaker
 * guarantees.
 *
 * <p>The
 * {@link java.util.concurrent.atomic.AtomicIntegerArray},
 * {@link java.util.concurrent.atomic.AtomicLongArray}, and
 * {@link java.util.concurrent.atomic.AtomicReferenceArray} classes
 * further extend atomic operation support to arrays of these types.
 * These classes are also notable in providing {@code volatile} access
 * semantics for their array elements, which is not supported for
 * ordinary arrays.
 *
 * <p id="weakCompareAndSet">The atomic classes also support method
 * {@code weakCompareAndSet}, which has limited applicability.  On some
 * platforms, the weak version may be more efficient than {@code
 * compareAndSet} in the normal case, but differs in that any given
 * invocation of the {@code weakCompareAndSet} method may return {@code
 * false} <em>spuriously</em> (that is, for no apparent reason).  A
 * {@code false} return means only that the operation may be retried if
 * desired, relying on the guarantee that repeated invocation when the
 * variable holds {@code expectedValue} and no other thread is also
 * attempting to set the variable will eventually succeed.  (Such
 * spurious failures may for example be due to memory contention effects
 * that are unrelated to whether the expected and current values are
 * equal.)  Additionally {@code weakCompareAndSet} does not provide
 * ordering guarantees that are usually needed for synchronization
 * control.  However, the method may be useful for updating counters and
 * statistics when such updates are unrelated to the other
 * happens-before orderings of a program.  When a thread sees an update
 * to an atomic variable caused by a {@code weakCompareAndSet}, it does
 * not necessarily see updates to any <em>other</em> variables that
 * occurred before the {@code weakCompareAndSet}.  This may be
 * acceptable when, for example, updating performance statistics, but
 * rarely otherwise.
 *
 * <p>The {@link java.util.concurrent.atomic.AtomicMarkableReference}
 * class associates a single boolean with a reference.  For example, this
 * bit might be used inside a data structure to mean that the object
 * being referenced has logically been deleted.
 *
 * The {@link java.util.concurrent.atomic.AtomicStampedReference}
 * class associates an integer value with a reference.  This may be
 * used for example, to represent version numbers corresponding to
 * series of updates.
 *
 * <p>Atomic classes are designed primarily as building blocks for
 * implementing non-blocking data structures and related infrastructure
 * classes.  The {@code compareAndSet} method is not a general
 * replacement for locking.  It applies only when critical updates for an
 * object are confined to a <em>single</em> variable.
 *
 * <p>Atomic classes are not general purpose replacements for
 * {@code java.lang.Integer} and related classes.  They do <em>not</em>
 * define methods such as {@code equals}, {@code hashCode} and
 * {@code compareTo}.  (Because atomic variables are expected to be
 * mutated, they are poor choices for hash table keys.)  Additionally,
 * classes are provided only for those types that are commonly useful in
 * intended applications.  For example, there is no atomic class for
 * representing {@code byte}.  In those infrequent cases where you would
 * like to do so, you can use an {@code AtomicInteger} to hold
 * {@code byte} values, and cast appropriately.
 *
 * You can also hold floats using
 * {@link java.lang.Float#floatToRawIntBits} and
 * {@link java.lang.Float#intBitsToFloat} conversions, and doubles using
 * {@link java.lang.Double#doubleToRawLongBits} and
 * {@link java.lang.Double#longBitsToDouble} conversions.
 *
 * <p>
 *  一个小工具包,支持对单变量进行无锁线程安全编程。本质上,这个包中的类将{@code volatile}值,字段和数组元素的概念扩展到还提供以下形式的原子条件更新操作的概念：
 * 
 *  <pre> {@code boolean compareAndSet(expectedValue,updateValue);} </pre>
 * 
 *  <p>此方法(在不同类之间的参数类型不同)原子性地将变量设置为{@code updateValue},如果它当前持有{@code expectedValue},则报告{@code true}成功。
 * 此包中的类还包含获取和无条件设置值的方法,以及下面描述的较弱的条件原子更新操作{@code weakCompareAndSet}。
 * 
 *  这些方法的规范使得实现可以使用在当代处理器上可用的有效的机器级原子指令。然而在一些平台上,支撑可能需要某种形式的内部锁定。因此,这些方法不是严格保证是非阻塞的 - 线程可以在执行操作之前暂时阻塞。
 * 
 * <p>类{@link java.util.concurrent.atomic.AtomicBoolean},{@link java.util.concurrent.atomic.AtomicInteger}
 * ,{@link java.util.concurrent.atomic.AtomicLong}和{ @link java.util.concurrent.atomic.AtomicReference}每
 * 个都提供对相应类型的单个变量的访问和更新。
 * 每个类还为该类型提供适当的实用程序方法。例如,类{@code AtomicLong}和{@code AtomicInteger}提供原子增量方法。一个应用程序是生成序列号,如：。
 * 
 *  <pre> {@code class Sequencer {private final AtomicLong sequenceNumber = new AtomicLong(0); public long next(){return sequenceNumber.getAndIncrement(); }
 * }} </pre>。
 * 
 *  <p>直接定义新的效用函数,像{@code getAndIncrement},将函数原子地应用到值。
 * 例如,给定一些变换<pre> {@code long transform(long input)} </pre>。
 * 
 *  写你的实用程序方法如下：<pre> {@code long getAndTransform(AtomicLong var){long prev,next; do {prev = var.get(); next = transform(prev); }
 *  while(！var.compareAndSet(prev,next)); return prev; // return next; for transformAndGet}} </pre>。
 * 
 *  <p>对于原子的访问和更新的记忆效应通常遵循挥发物的规则,如所述
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4">
 *  Java语言规范(17.4内存模型)</a>：
 * 
 * <ul>
 * 
 *  <li> {@code get}具有读取{@code volatile}变量的记忆效应。
 * 
 * <li> {@code set}具有写入(分配){@code volatile}变量的记忆效应。
 * 
 *  <li> {@code lazySet}具有写入(分配){@code volatile}变量的记忆效应,除了它允许对后续(但不是以前的)内存操作重新排序,而这些内存操作本身不会对普通非{ @code volatile}
 * 写入。
 * 在其他使用上下文中,{@code lazySet}可能会在为了垃圾回收而清除一个从不会再次访问的引用时应用。
 * 
 *  <li> {@ code weakCompareAndSet}以原子方式读取并有条件地写入变量,但不会<em> </em>创建任何发生前的排序,因此不会保证以前或后续读取和写入任何变量{@code weakCompareAndSet}
 * 的目标。
 * 
 * 
 * @since 1.5
 */
package java.util.concurrent.atomic;
