/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * An object for traversing and partitioning elements of a source.  The source
 * of elements covered by a Spliterator could be, for example, an array, a
 * {@link Collection}, an IO channel, or a generator function.
 *
 * <p>A Spliterator may traverse elements individually ({@link
 * #tryAdvance tryAdvance()}) or sequentially in bulk
 * ({@link #forEachRemaining forEachRemaining()}).
 *
 * <p>A Spliterator may also partition off some of its elements (using
 * {@link #trySplit}) as another Spliterator, to be used in
 * possibly-parallel operations.  Operations using a Spliterator that
 * cannot split, or does so in a highly imbalanced or inefficient
 * manner, are unlikely to benefit from parallelism.  Traversal
 * and splitting exhaust elements; each Spliterator is useful for only a single
 * bulk computation.
 *
 * <p>A Spliterator also reports a set of {@link #characteristics()} of its
 * structure, source, and elements from among {@link #ORDERED},
 * {@link #DISTINCT}, {@link #SORTED}, {@link #SIZED}, {@link #NONNULL},
 * {@link #IMMUTABLE}, {@link #CONCURRENT}, and {@link #SUBSIZED}. These may
 * be employed by Spliterator clients to control, specialize or simplify
 * computation.  For example, a Spliterator for a {@link Collection} would
 * report {@code SIZED}, a Spliterator for a {@link Set} would report
 * {@code DISTINCT}, and a Spliterator for a {@link SortedSet} would also
 * report {@code SORTED}.  Characteristics are reported as a simple unioned bit
 * set.
 *
 * Some characteristics additionally constrain method behavior; for example if
 * {@code ORDERED}, traversal methods must conform to their documented ordering.
 * New characteristics may be defined in the future, so implementors should not
 * assign meanings to unlisted values.
 *
 * <p><a name="binding">A Spliterator that does not report {@code IMMUTABLE} or
 * {@code CONCURRENT} is expected to have a documented policy concerning:
 * when the spliterator <em>binds</em> to the element source; and detection of
 * structural interference of the element source detected after binding.</a>  A
 * <em>late-binding</em> Spliterator binds to the source of elements at the
 * point of first traversal, first split, or first query for estimated size,
 * rather than at the time the Spliterator is created.  A Spliterator that is
 * not <em>late-binding</em> binds to the source of elements at the point of
 * construction or first invocation of any method.  Modifications made to the
 * source prior to binding are reflected when the Spliterator is traversed.
 * After binding a Spliterator should, on a best-effort basis, throw
 * {@link ConcurrentModificationException} if structural interference is
 * detected.  Spliterators that do this are called <em>fail-fast</em>.  The
 * bulk traversal method ({@link #forEachRemaining forEachRemaining()}) of a
 * Spliterator may optimize traversal and check for structural interference
 * after all elements have been traversed, rather than checking per-element and
 * failing immediately.
 *
 * <p>Spliterators can provide an estimate of the number of remaining elements
 * via the {@link #estimateSize} method.  Ideally, as reflected in characteristic
 * {@link #SIZED}, this value corresponds exactly to the number of elements
 * that would be encountered in a successful traversal.  However, even when not
 * exactly known, an estimated value value may still be useful to operations
 * being performed on the source, such as helping to determine whether it is
 * preferable to split further or traverse the remaining elements sequentially.
 *
 * <p>Despite their obvious utility in parallel algorithms, spliterators are not
 * expected to be thread-safe; instead, implementations of parallel algorithms
 * using spliterators should ensure that the spliterator is only used by one
 * thread at a time.  This is generally easy to attain via <em>serial
 * thread-confinement</em>, which often is a natural consequence of typical
 * parallel algorithms that work by recursive decomposition.  A thread calling
 * {@link #trySplit()} may hand over the returned Spliterator to another thread,
 * which in turn may traverse or further split that Spliterator.  The behaviour
 * of splitting and traversal is undefined if two or more threads operate
 * concurrently on the same spliterator.  If the original thread hands a
 * spliterator off to another thread for processing, it is best if that handoff
 * occurs before any elements are consumed with {@link #tryAdvance(Consumer)
 * tryAdvance()}, as certain guarantees (such as the accuracy of
 * {@link #estimateSize()} for {@code SIZED} spliterators) are only valid before
 * traversal has begun.
 *
 * <p>Primitive subtype specializations of {@code Spliterator} are provided for
 * {@link OfInt int}, {@link OfLong long}, and {@link OfDouble double} values.
 * The subtype default implementations of
 * {@link Spliterator#tryAdvance(java.util.function.Consumer)}
 * and {@link Spliterator#forEachRemaining(java.util.function.Consumer)} box
 * primitive values to instances of their corresponding wrapper class.  Such
 * boxing may undermine any performance advantages gained by using the primitive
 * specializations.  To avoid boxing, the corresponding primitive-based methods
 * should be used.  For example,
 * {@link Spliterator.OfInt#tryAdvance(java.util.function.IntConsumer)}
 * and {@link Spliterator.OfInt#forEachRemaining(java.util.function.IntConsumer)}
 * should be used in preference to
 * {@link Spliterator.OfInt#tryAdvance(java.util.function.Consumer)} and
 * {@link Spliterator.OfInt#forEachRemaining(java.util.function.Consumer)}.
 * Traversal of primitive values using boxing-based methods
 * {@link #tryAdvance tryAdvance()} and
 * {@link #forEachRemaining(java.util.function.Consumer) forEachRemaining()}
 * does not affect the order in which the values, transformed to boxed values,
 * are encountered.
 *
 * @apiNote
 * <p>Spliterators, like {@code Iterators}s, are for traversing the elements of
 * a source.  The {@code Spliterator} API was designed to support efficient
 * parallel traversal in addition to sequential traversal, by supporting
 * decomposition as well as single-element iteration.  In addition, the
 * protocol for accessing elements via a Spliterator is designed to impose
 * smaller per-element overhead than {@code Iterator}, and to avoid the inherent
 * race involved in having separate methods for {@code hasNext()} and
 * {@code next()}.
 *
 * <p>For mutable sources, arbitrary and non-deterministic behavior may occur if
 * the source is structurally interfered with (elements added, replaced, or
 * removed) between the time that the Spliterator binds to its data source and
 * the end of traversal.  For example, such interference will produce arbitrary,
 * non-deterministic results when using the {@code java.util.stream} framework.
 *
 * <p>Structural interference of a source can be managed in the following ways
 * (in approximate order of decreasing desirability):
 * <ul>
 * <li>The source cannot be structurally interfered with.
 * <br>For example, an instance of
 * {@link java.util.concurrent.CopyOnWriteArrayList} is an immutable source.
 * A Spliterator created from the source reports a characteristic of
 * {@code IMMUTABLE}.</li>
 * <li>The source manages concurrent modifications.
 * <br>For example, a key set of a {@link java.util.concurrent.ConcurrentHashMap}
 * is a concurrent source.  A Spliterator created from the source reports a
 * characteristic of {@code CONCURRENT}.</li>
 * <li>The mutable source provides a late-binding and fail-fast Spliterator.
 * <br>Late binding narrows the window during which interference can affect
 * the calculation; fail-fast detects, on a best-effort basis, that structural
 * interference has occurred after traversal has commenced and throws
 * {@link ConcurrentModificationException}.  For example, {@link ArrayList},
 * and many other non-concurrent {@code Collection} classes in the JDK, provide
 * a late-binding, fail-fast spliterator.</li>
 * <li>The mutable source provides a non-late-binding but fail-fast Spliterator.
 * <br>The source increases the likelihood of throwing
 * {@code ConcurrentModificationException} since the window of potential
 * interference is larger.</li>
 * <li>The mutable source provides a late-binding and non-fail-fast Spliterator.
 * <br>The source risks arbitrary, non-deterministic behavior after traversal
 * has commenced since interference is not detected.
 * </li>
 * <li>The mutable source provides a non-late-binding and non-fail-fast
 * Spliterator.
 * <br>The source increases the risk of arbitrary, non-deterministic behavior
 * since non-detected interference may occur after construction.
 * </li>
 * </ul>
 *
 * <p><b>Example.</b> Here is a class (not a very useful one, except
 * for illustration) that maintains an array in which the actual data
 * are held in even locations, and unrelated tag data are held in odd
 * locations. Its Spliterator ignores the tags.
 *
 * <pre> {@code
 * class TaggedArray<T> {
 *   private final Object[] elements; // immutable after construction
 *   TaggedArray(T[] data, Object[] tags) {
 *     int size = data.length;
 *     if (tags.length != size) throw new IllegalArgumentException();
 *     this.elements = new Object[2 * size];
 *     for (int i = 0, j = 0; i < size; ++i) {
 *       elements[j++] = data[i];
 *       elements[j++] = tags[i];
 *     }
 *   }
 *
 *   public Spliterator<T> spliterator() {
 *     return new TaggedArraySpliterator<>(elements, 0, elements.length);
 *   }
 *
 *   static class TaggedArraySpliterator<T> implements Spliterator<T> {
 *     private final Object[] array;
 *     private int origin; // current index, advanced on split or traversal
 *     private final int fence; // one past the greatest index
 *
 *     TaggedArraySpliterator(Object[] array, int origin, int fence) {
 *       this.array = array; this.origin = origin; this.fence = fence;
 *     }
 *
 *     public void forEachRemaining(Consumer<? super T> action) {
 *       for (; origin < fence; origin += 2)
 *         action.accept((T) array[origin]);
 *     }
 *
 *     public boolean tryAdvance(Consumer<? super T> action) {
 *       if (origin < fence) {
 *         action.accept((T) array[origin]);
 *         origin += 2;
 *         return true;
 *       }
 *       else // cannot advance
 *         return false;
 *     }
 *
 *     public Spliterator<T> trySplit() {
 *       int lo = origin; // divide range in half
 *       int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
 *       if (lo < mid) { // split out left half
 *         origin = mid; // reset this Spliterator's origin
 *         return new TaggedArraySpliterator<>(array, lo, mid);
 *       }
 *       else       // too small to split
 *         return null;
 *     }
 *
 *     public long estimateSize() {
 *       return (long)((fence - origin) / 2);
 *     }
 *
 *     public int characteristics() {
 *       return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
 *     }
 *   }
 * }}</pre>
 *
 * <p>As an example how a parallel computation framework, such as the
 * {@code java.util.stream} package, would use Spliterator in a parallel
 * computation, here is one way to implement an associated parallel forEach,
 * that illustrates the primary usage idiom of splitting off subtasks until
 * the estimated amount of work is small enough to perform
 * sequentially. Here we assume that the order of processing across
 * subtasks doesn't matter; different (forked) tasks may further split
 * and process elements concurrently in undetermined order.  This
 * example uses a {@link java.util.concurrent.CountedCompleter};
 * similar usages apply to other parallel task constructions.
 *
 * <pre>{@code
 * static <T> void parEach(TaggedArray<T> a, Consumer<T> action) {
 *   Spliterator<T> s = a.spliterator();
 *   long targetBatchSize = s.estimateSize() / (ForkJoinPool.getCommonPoolParallelism() * 8);
 *   new ParEach(null, s, action, targetBatchSize).invoke();
 * }
 *
 * static class ParEach<T> extends CountedCompleter<Void> {
 *   final Spliterator<T> spliterator;
 *   final Consumer<T> action;
 *   final long targetBatchSize;
 *
 *   ParEach(ParEach<T> parent, Spliterator<T> spliterator,
 *           Consumer<T> action, long targetBatchSize) {
 *     super(parent);
 *     this.spliterator = spliterator; this.action = action;
 *     this.targetBatchSize = targetBatchSize;
 *   }
 *
 *   public void compute() {
 *     Spliterator<T> sub;
 *     while (spliterator.estimateSize() > targetBatchSize &&
 *            (sub = spliterator.trySplit()) != null) {
 *       addToPendingCount(1);
 *       new ParEach<>(this, sub, action, targetBatchSize).fork();
 *     }
 *     spliterator.forEachRemaining(action);
 *     propagateCompletion();
 *   }
 * }}</pre>
 *
 * @implNote
 * If the boolean system property {@code org.openjdk.java.util.stream.tripwire}
 * is set to {@code true} then diagnostic warnings are reported if boxing of
 * primitive values occur when operating on primitive subtype specializations.
 *
 * <p>
 *  用于遍历和分割源的元素的对象。分割器覆盖的元素的源可以是例如数组,{@link集合},IO通道或生成器函数。
 * 
 *  <p> Spliterator可以单独遍历元素({@link #tryAdvance tryAdvance()})或批量遍历元素({@link #forEachRemaining forEachRemaining()}
 * )。
 * 
 *  <p>分割器还可以将其某些元素(使用{@link #trySplit})分隔为另一个Spliterator,以在可能的并行操作中使用。
 * 使用不能分裂的分裂器或者以非常不平衡或低效的方式进行操作的操作不太可能受益于并行性。穿越和分裂排气元件;每个分割器仅用于单个批量计算。
 * 
 * <p> Spliterator还会报告{@link #ORDERED},{@link #DISTINCT},{@link #SORTED},{@ link# @link #SIZED},{@link #NONNULL}
 * ,{@link #IMMUTABLE},{@link #CONCURRENT}和{@link #SUBSIZED}。
 * 这些可以由Spliterator客户端用来控制,专门化或简化计算。
 * 例如,{@link Collection}的分割器会报告{@code SIZED},{@link Set}的分割器会报告{@code DISTINCT},而{@link SortedSet}的分割器也会
 * 报告{@code SORTED}。
 * 这些可以由Spliterator客户端用来控制,专门化或简化计算。特性被报告为简单的联合位集合。
 * 
 *  一些特性另外限制方法行为;例如,如果{@code ORDERED},遍历方法必须符合其记录的顺序。新的特性可能在未来被定义,因此实现者不应该给未列出的值赋予意义。
 * 
 * <p> <a name="binding">不报告{@code IMMUTABLE}或{@code CONCURRENT}的Spliterator应具有有关以下事项的书面政策：拆分器<em>绑定到</em>
 * 元素源;以及在绑定之后检测到的元素源的结构干扰的检测。
 * </a>后绑定</em>分离器在第一遍历,第一分割或第一查询的点处结合到元素的源大小,而不是在创建Spliterator时。不是后期绑定</em>的分割器在构建点或首次调用任何方法时绑定到元素的源。
 * 在绑定之前对源进行的修改会在遍历Spliterator时反映。绑定后,如果检测到结构性干扰,则尽力而为地调用{@link ConcurrentModificationException}。
 * 执行此操作的拆分器称为<em> fail-fast </em>。
 *  Spliterator的批量遍历方法({@link #forEachRemaining forEachRemaining()})可以优化遍历并且在遍历所有元素之后检查结构干扰,而不是立即检查每个元素并
 * 且失败。
 * 执行此操作的拆分器称为<em> fail-fast </em>。
 * 
 * <p>离散器可以通过{@link #estimateSize}方法估计剩余元素的数量。理想情况下,如特征{@link #SIZED}中所反映的,该值完全对应于在成功遍历中将遇到的元素的数量。
 * 然而,即使当不完全知道时,估计值值对于在源上执行的操作仍可能是有用的,诸如帮助确定是否优选地进一步拆分或遍历其余元素。
 * 
 * <p>尽管它们在并行算法中有着显而易见的效用,但是预期分割器不是线程安全的;相反,使用分裂器的并行算法的实现应当确保分裂器一次仅由一个线程使用。
 * 这通常很容易通过串行线程限制</em>来实现,这通常是通过递归分解工作的典型并行算法的自然结果。
 * 线程调用{@link #trySplit()}可以将返回的Spliterator移交到另一个线程,而这又可以遍历或进一步拆分该Spliterator。
 * 如果两个或多个线程在同一个分割器上并发运行,则分割和遍历的行为是未定义的。
 * 如果原始线程将分割器关闭到另一个线程进行处理,最好是在任何元素被{@link #tryAdvance(Consumer)tryAdvance()}消耗之前发生切换,作为某些保证(例如{ @link #estimateSize()}
 *  for {@code SIZED}分隔符)仅在遍历开始之前有效。
 * 如果两个或多个线程在同一个分割器上并发运行,则分割和遍历的行为是未定义的。
 * 
 * <p> {@code Spliterator}的原始子类型专用化适用于{@link OfInt int},{@link Of Long long}和{@link OfDouble double}值。
 *  {@link Spliterator#tryAdvance(java.util.function.Consumer)}和{@link Spliterator#forEachRemaining(java.util.function.Consumer)}
 * 框架原始值的子类型默认实现到其相应的包装类的实例。
 * <p> {@code Spliterator}的原始子类型专用化适用于{@link OfInt int},{@link Of Long long}和{@link OfDouble double}值。
 * 这种拳击可能破坏通过使用原始专门化获得的任何性能优点。为了避免装箱,应该使用相应的基于原始的方法。
 * 例如,{@link Spliterator.OfInt#tryAdvance(java.util.function.IntConsumer)}和{@link Spliterator.OfInt#forEachRemaining(java.util.function.IntConsumer)}
 * 应优先使用{@link Spliterator .OfInt#tryAdvance(java.util.function.Consumer)}和{@link Spliterator.OfInt#forEachRemaining(java.util.function.Consumer)}
 * 。
 * 这种拳击可能破坏通过使用原始专门化获得的任何性能优点。为了避免装箱,应该使用相应的基于原始的方法。
 * 使用基于boxing的方法遍历原始值{@link #tryAdvance tryAdvance()}和{@link #forEachRemaining(java.util.function.Consumer)forEachRemaining()}
 * 不会影响值转换为boxed值,遇到。
 * 这种拳击可能破坏通过使用原始专门化获得的任何性能优点。为了避免装箱,应该使用相应的基于原始的方法。
 * 
 * @apiNote <p>像{@code Iterators}一样,Spliterators用于遍历源的元素。
 *  {@code Spliterator} API旨在通过支持分解以及单元素迭代,支持高效的并行遍历以及顺序遍历。
 * 此外,用于通过分离器访问元素的协议被设计为比{@code迭代器}施加更小的每单元开销,并且避免对于{@code hasNext()}和{@code下一个()}。
 * 
 *  <p>对于可变源,如果源在结构上干扰(元素添加,替换或删除)Spliterator绑定到其数据源的时间和遍历结束之间,可能会发生任意和非确定性行为。
 * 例如,当使用{@code java.util.stream}框架时,这种干扰将产生任意的,非确定性的结果。
 * 
 *  <p>源的结构干扰可以以以下方式管理(以大致降低的愿望顺序)：
 * <ul>
 * <li>源不能在结构上干扰。 <br>例如,{@link java.util.concurrent.CopyOnWriteArrayList}的实例是一个不可变的源。
 * 从源创建的Spliterator报告{@code IMMUTABLE}的特性。</li> <li>源管理并发修改。
 *  <br>例如,{@link java.util.concurrent.ConcurrentHashMap}的键集是并发源。从源创建的分割器报告{@code CONCURRENT}的特性。
 * </li> <li>可变源提供了后期绑定和故障快速Spliterator。
 *  <br>后期绑定会缩小窗口,在此期间干扰会影响计算; fail-fast以尽力而为的方式检测在遍历开始后发生结构性干扰并抛出{@link ConcurrentModificationException}
 * 。
 * </li> <li>可变源提供了后期绑定和故障快速Spliterator。
 * 例如,{@link ArrayList}和JDK中的许多其他非并发{@code Collection}类提供了一个后期绑定,故障快速的分割器。
 * </li> <li>后期绑定但失败快速的Spliterator。 <br>源增加了投射{@code ConcurrentModificationException}的可能性,因为潜在干扰的窗口较大。
 * </li> <li>可变源提供了后期绑定和非故障快速Spliterator。 <br>由于未检测到干扰,因此在遍历开始后,源会冒任意,非确定性行为。
 * </li>
 * <li>可变来源提供非延迟绑定和非故障快速拆分器。 <br>来源增加了任意,非确定性行为的风险,因为在构建之后可能会发生非检测到的干扰。
 * </li>
 * </ul>
 * 
 *  <p> <b>示例</b>这里是一个类(不是非常有用的类,除了插图)维护一个数组,其中实际数据保存在偶数位置,无关标签数据保持奇数位置。它的Spliterator忽略标签。
 * 
 *  <pre> {@code class TaggedArray <T> {private final Object [] elements; //不可变后构造TaggedArray(T [] data,Object [] tags){int size = data.length; if(tags.length！= size)throw new IllegalArgumentException(); this.elements = new Object [2 * size]; for(int i = 0,j = 0; i <size; ++ i){elements [j ++] = data [i]; elements [j ++] = tags [i]; }}。
 * 
 *  public Spliterator <T> spliterator(){return new TaggedArraySpliterator <>(elements,0,element.length); }
 * }。
 * 
 *  静态类TaggedArraySpliterator <T>实现分离器<T> {private final Object [] array; private int origin; // current index,advanced on split或traversal private final int fence; //一个过去最大的索引。
 * 
 *  TaggedArraySpliterator(Object [] array,int origin,int fence){this.array = array; this.origin = origin; this.fence = fence; }
 * }。
 * 
 *  public void forEachRemaining(Consumer <?super T> action){for(; origin <fence; origin + = 2)action.accept((T)array [origin]); }}。
 * 
 * public boolean tryAdvance(Consumer <?super T> action){if(origin <fence){action.accept((T)array [origin]);原点+ = 2; return true; } else //不能提前返回false; }}。
 * 
 *  public Spliterator <T> trySplit(){int lo = origin; // divide range in half int mid =((lo + fence)>>> 1)&〜1; // force midpoint to be even if(lo <mid){// split out left half origin = mid; // reset this Spliterator's origin return TaggedArraySpliterator <>(array,lo,mid); }
 *  else //太小无法分割return null; }}。
 * 
 *  public long estimateSize(){return(long)((fence  -  origin)/ 2); }}
 * 
 *  public int characteristics(){return ORDERED | SIZED | IMMUTABLE | SUBSIZED; }}}} </pre>
 * 
 *  <p>作为一个例子,如并行计算框架,如{@code java.util.stream}包,将在并行计算中使用Spliterator,这里是一个实现相关并行forEach的方法,它说明了主要用法分割子任
 * 务的习语,直到估计的工作量小到足以依次执行。
 * 这里我们假设处理子任务的顺序无关紧要;不同的(分叉)任务可以以未确定的顺序进一步拆分和处理元素。
 * 此示例使用{@link java.util.concurrent.CountedCompleter};类似的用法适用于其他并行任务结构。
 * 
 * <pre> {@ code static <T> void parEach(TaggedArray <T> a,Consumer <T> action){Spliterator <T> s = a.spliterator long targetBatchSize = s.estimateSize()/(ForkJoinPool.getCommonPoolParallelism()* 8); new ParEach(null,s,action,targetBatchSize).invoke(); }
 * }。
 * 
 *  静态类ParEach <T>扩展CountedCompleter <Void> {final Spliterator <T> spliterator;最终消费者行动; final long targetBatchSize;。
 * 
 *  ParEach(ParEach <T> parent,Spliterator <T> spliterator,Consumer <T> action,long targetBatchSize){super(parent); this.spliterator = spliterator; this.action = action; this.targetBatchSize = targetBatchSize; }
 * }。
 * 
 *  public void compute(){Spliterator <T> sub; while(spliterator.estimateSize()> targetBatchSize &&(sub = spliterator.trySplit())！= null){addToPendingCount(1); new ParEach <>(this,sub,action,targetBatchSize).fork(); }
 *  spliterator.forEachRemaining(action); propagateCompletion(); }}} </pre>。
 * 
 *  @implNote如果布尔系统属性{@code org.openjdk.java.util.stream.tripwire}设置为{@code true},则当对原始子类型专门化操作时出现原始值的框时
 * ,将报告诊断警告。
 * 
 * 
 * @param <T> the type of elements returned by this Spliterator
 *
 * @see Collection
 * @since 1.8
 */
public interface Spliterator<T> {
    /**
     * If a remaining element exists, performs the given action on it,
     * returning {@code true}; else returns {@code false}.  If this
     * Spliterator is {@link #ORDERED} the action is performed on the
     * next element in encounter order.  Exceptions thrown by the
     * action are relayed to the caller.
     *
     * <p>
     *  如果剩余元素存在,执行给定的操作,返回{@code true};否则返回{@code false}。如果此拆分器为{@link #ORDERED},则对遇到顺序中的下一个元素执行操作。
     * 操作抛出的异常中继到调用者。
     * 
     * 
     * @param action The action
     * @return {@code false} if no remaining elements existed
     * upon entry to this method, else {@code true}.
     * @throws NullPointerException if the specified action is null
     */
    boolean tryAdvance(Consumer<? super T> action);

    /**
     * Performs the given action for each remaining element, sequentially in
     * the current thread, until all elements have been processed or the action
     * throws an exception.  If this Spliterator is {@link #ORDERED}, actions
     * are performed in encounter order.  Exceptions thrown by the action
     * are relayed to the caller.
     *
     * @implSpec
     * The default implementation repeatedly invokes {@link #tryAdvance} until
     * it returns {@code false}.  It should be overridden whenever possible.
     *
     * <p>
     * 对当前线程中的每个剩余元素依次执行给定的操作,直到所有元素都被处理或操作抛出异常。如果此拆分器为{@link #ORDERED},则按照遇到顺序执行操作。操作抛出的异常中继到调用者。
     * 
     *  @implSpec默认实现重复调用{@link #tryAdvance},直到它返回{@code false}。应尽可能覆盖它。
     * 
     * 
     * @param action The action
     * @throws NullPointerException if the specified action is null
     */
    default void forEachRemaining(Consumer<? super T> action) {
        do { } while (tryAdvance(action));
    }

    /**
     * If this spliterator can be partitioned, returns a Spliterator
     * covering elements, that will, upon return from this method, not
     * be covered by this Spliterator.
     *
     * <p>If this Spliterator is {@link #ORDERED}, the returned Spliterator
     * must cover a strict prefix of the elements.
     *
     * <p>Unless this Spliterator covers an infinite number of elements,
     * repeated calls to {@code trySplit()} must eventually return {@code null}.
     * Upon non-null return:
     * <ul>
     * <li>the value reported for {@code estimateSize()} before splitting,
     * must, after splitting, be greater than or equal to {@code estimateSize()}
     * for this and the returned Spliterator; and</li>
     * <li>if this Spliterator is {@code SUBSIZED}, then {@code estimateSize()}
     * for this spliterator before splitting must be equal to the sum of
     * {@code estimateSize()} for this and the returned Spliterator after
     * splitting.</li>
     * </ul>
     *
     * <p>This method may return {@code null} for any reason,
     * including emptiness, inability to split after traversal has
     * commenced, data structure constraints, and efficiency
     * considerations.
     *
     * @apiNote
     * An ideal {@code trySplit} method efficiently (without
     * traversal) divides its elements exactly in half, allowing
     * balanced parallel computation.  Many departures from this ideal
     * remain highly effective; for example, only approximately
     * splitting an approximately balanced tree, or for a tree in
     * which leaf nodes may contain either one or two elements,
     * failing to further split these nodes.  However, large
     * deviations in balance and/or overly inefficient {@code
     * trySplit} mechanics typically result in poor parallel
     * performance.
     *
     * <p>
     *  如果此分隔器可以分区,则返回覆盖元素的分离器,从该方法返回时,不会被此分离器覆盖。
     * 
     *  <p>如果此Spliterator为{@link #ORDERED},则返回的Spliterator必须包含元素的严格前缀。
     * 
     *  <p>除非此Spliterator覆盖无限数量的元素,否则重复调用{@code trySplit()}必须最终返回{@code null}。非空返回时：
     * <ul>
     *  <li>在分割之前为{@code estimateSize()}报告的值必须大于或等于{@code estimateSize()}为此和返回的Spliterator;和</li> <li>如果此拆分器
     * 为{@code SUBSIZED},则此拆分器之前的{@code estimateSize()}必须等于{@code estimateSize拆分后的拆分器。
     * </li>。
     * </ul>
     * 
     *  <p>此方法可能会由于任何原因返回{@code null},包括空白,在遍历开始后无法分割,数据结构约束和效率注意事项。
     * 
     * @apiNote一个理想的{@code trySplit}方法有效地(没有遍历)将其元素完全分成一半,允许平衡的并行计算。
     * 许多偏离这种理想的方法仍然非常有效;例如,仅近似分割近似平衡的树,或者对于其中叶节点可以包含一个或两个元素的树,无法进一步分裂这些节点。
     * 然而,平衡的大偏差和/或过度低效的{@code trySplit}机制通常导致差的并行性能。
     * 
     * 
     * @return a {@code Spliterator} covering some portion of the
     * elements, or {@code null} if this spliterator cannot be split
     */
    Spliterator<T> trySplit();

    /**
     * Returns an estimate of the number of elements that would be
     * encountered by a {@link #forEachRemaining} traversal, or returns {@link
     * Long#MAX_VALUE} if infinite, unknown, or too expensive to compute.
     *
     * <p>If this Spliterator is {@link #SIZED} and has not yet been partially
     * traversed or split, or this Spliterator is {@link #SUBSIZED} and has
     * not yet been partially traversed, this estimate must be an accurate
     * count of elements that would be encountered by a complete traversal.
     * Otherwise, this estimate may be arbitrarily inaccurate, but must decrease
     * as specified across invocations of {@link #trySplit}.
     *
     * @apiNote
     * Even an inexact estimate is often useful and inexpensive to compute.
     * For example, a sub-spliterator of an approximately balanced binary tree
     * may return a value that estimates the number of elements to be half of
     * that of its parent; if the root Spliterator does not maintain an
     * accurate count, it could estimate size to be the power of two
     * corresponding to its maximum depth.
     *
     * <p>
     *  返回{@link #forEachRemaining}遍历所遇到的元素数量的估计值,如果计算无限,未知或过于昂贵,则返回{@link Long#MAX_VALUE}。
     * 
     *  <p>如果此分页器为{@link #SIZED},但尚未部分横切或拆分,或此分割器为{@link #SUBSIZED}且尚未部分遍历,则此估计值必须是元素的准确计数这将被完全遍历所遇到。
     * 否则,此估计可能是任意不准确的,但必须按照{@link #trySplit}的调用中指定的减少。
     * 
     * @apiNote即使一个不精确的估计通常是有用的和廉价的计算。
     * 例如,近似平衡的二叉树的子分裂器可以返回将元素的数量估计为其父亲的数量的一半的值;如果根分裂器不保持准确的计数,则它可以将大小估计为对应于其最大深度的2的幂。
     * 
     * 
     * @return the estimated size, or {@code Long.MAX_VALUE} if infinite,
     *         unknown, or too expensive to compute.
     */
    long estimateSize();

    /**
     * Convenience method that returns {@link #estimateSize()} if this
     * Spliterator is {@link #SIZED}, else {@code -1}.
     * @implSpec
     * The default implementation returns the result of {@code estimateSize()}
     * if the Spliterator reports a characteristic of {@code SIZED}, and
     * {@code -1} otherwise.
     *
     * <p>
     *  如果此Spliceator为{@link #SIZED},则返回{@link #estimateSize()},否则为{@code -1}。
     *  @implSpec如果Splitator报告{@code SIZED}的特性,则默认实现返回{@code estimateSize()}的结果,否则返回{@code -1}。
     * 
     * 
     * @return the exact size, if known, else {@code -1}.
     */
    default long getExactSizeIfKnown() {
        return (characteristics() & SIZED) == 0 ? -1L : estimateSize();
    }

    /**
     * Returns a set of characteristics of this Spliterator and its
     * elements. The result is represented as ORed values from {@link
     * #ORDERED}, {@link #DISTINCT}, {@link #SORTED}, {@link #SIZED},
     * {@link #NONNULL}, {@link #IMMUTABLE}, {@link #CONCURRENT},
     * {@link #SUBSIZED}.  Repeated calls to {@code characteristics()} on
     * a given spliterator, prior to or in-between calls to {@code trySplit},
     * should always return the same result.
     *
     * <p>If a Spliterator reports an inconsistent set of
     * characteristics (either those returned from a single invocation
     * or across multiple invocations), no guarantees can be made
     * about any computation using this Spliterator.
     *
     * @apiNote The characteristics of a given spliterator before splitting
     * may differ from the characteristics after splitting.  For specific
     * examples see the characteristic values {@link #SIZED}, {@link #SUBSIZED}
     * and {@link #CONCURRENT}.
     *
     * <p>
     *  返回此Spliterator及其元素的一组特性。
     * 结果表示为来自{@link #ORDERED},{@link #DISTINCT},{@link #SORTED},{@link #SIZED},{@link #NONNULL},{@link #IMMUTABLE}
     * 的ORed值, {@link #CONCURRENT},{@link #SUBSIZED}。
     *  返回此Spliterator及其元素的一组特性。在对{@code trySplit}调用之前或之间的给定拆分器上重复调用{@code characteristics()}时,应始终返回相同的结果。
     * 
     *  <p>如果Spliterator报告一组不一致的特性(从单个调用或多个调用返回的特性),则不能保证使用此Spliterator的任何计算。
     * 
     * @apiNote分割之前给定分裂器的特性可能与分裂后的特性不同。有关具体示例,请参阅特征值{@link #SIZED},{@link #SUBSIZED}和{@link #CONCURRENT}。
     * 
     * 
     * @return a representation of characteristics
     */
    int characteristics();

    /**
     * Returns {@code true} if this Spliterator's {@link
     * #characteristics} contain all of the given characteristics.
     *
     * @implSpec
     * The default implementation returns true if the corresponding bits
     * of the given characteristics are set.
     *
     * <p>
     *  如果此Splitator的{@link #characteristics}包含所有给定的特征,则返回{@code true}。
     * 
     *  @implSpec如果给定特性的相应位已设置,则默认实现将返回true。
     * 
     * 
     * @param characteristics the characteristics to check for
     * @return {@code true} if all the specified characteristics are present,
     * else {@code false}
     */
    default boolean hasCharacteristics(int characteristics) {
        return (characteristics() & characteristics) == characteristics;
    }

    /**
     * If this Spliterator's source is {@link #SORTED} by a {@link Comparator},
     * returns that {@code Comparator}. If the source is {@code SORTED} in
     * {@linkplain Comparable natural order}, returns {@code null}.  Otherwise,
     * if the source is not {@code SORTED}, throws {@link IllegalStateException}.
     *
     * @implSpec
     * The default implementation always throws {@link IllegalStateException}.
     *
     * <p>
     *  如果{@link Comparator}为{@link #SORTED},则此分页器的来源为{@code Comparator}。
     * 如果源是{@link SURTED}在{@linkplain Comparable natural order}中,则返回{@code null}。
     * 否则,如果源不是{@code SORTED},则抛出{@link IllegalStateException}。
     * 
     *  @implSpec默认实现总是抛出{@link IllegalStateException}。
     * 
     * 
     * @return a Comparator, or {@code null} if the elements are sorted in the
     * natural order.
     * @throws IllegalStateException if the spliterator does not report
     *         a characteristic of {@code SORTED}.
     */
    default Comparator<? super T> getComparator() {
        throw new IllegalStateException();
    }

    /**
     * Characteristic value signifying that an encounter order is defined for
     * elements. If so, this Spliterator guarantees that method
     * {@link #trySplit} splits a strict prefix of elements, that method
     * {@link #tryAdvance} steps by one element in prefix order, and that
     * {@link #forEachRemaining} performs actions in encounter order.
     *
     * <p>A {@link Collection} has an encounter order if the corresponding
     * {@link Collection#iterator} documents an order. If so, the encounter
     * order is the same as the documented order. Otherwise, a collection does
     * not have an encounter order.
     *
     * @apiNote Encounter order is guaranteed to be ascending index order for
     * any {@link List}. But no order is guaranteed for hash-based collections
     * such as {@link HashSet}. Clients of a Spliterator that reports
     * {@code ORDERED} are expected to preserve ordering constraints in
     * non-commutative parallel computations.
     * <p>
     *  表示为元素定义遇到顺序的特征值。
     * 如果是这样,此Spliterator保证方法{@link #trySplit}分隔一个严格的元素前缀,该方法{@link #tryAdvance}按前缀顺序排列一个元素,{@link #forEachRemaining}
     * 按照遭遇顺序执行操作。
     *  表示为元素定义遇到顺序的特征值。
     * 
     *  <p>如果相应的{@link Collection#iterator}记录了订单,则{@link Collection}会有订单。如果是,则遇到的订单与已记录的订单相同。否则,集合没有遇到顺序。
     * 
     * @apiNote任何{@link List}的Encounter顺序都保证为升序索引顺序。但是对于基于散列的集合(如{@link HashSet})没有任何顺序。
     * 报告{@code ORDERED}的Spliterator的客户端应保留非交换并行计算中的顺序约束。
     * 
     */
    public static final int ORDERED    = 0x00000010;

    /**
     * Characteristic value signifying that, for each pair of
     * encountered elements {@code x, y}, {@code !x.equals(y)}. This
     * applies for example, to a Spliterator based on a {@link Set}.
     * <p>
     *  特征值表示对于每一对遇到的元素{@code x,y},{@code！x.equals(y)}。这适用于例如基于{@link Set}的分割器。
     * 
     */
    public static final int DISTINCT   = 0x00000001;

    /**
     * Characteristic value signifying that encounter order follows a defined
     * sort order. If so, method {@link #getComparator()} returns the associated
     * Comparator, or {@code null} if all elements are {@link Comparable} and
     * are sorted by their natural ordering.
     *
     * <p>A Spliterator that reports {@code SORTED} must also report
     * {@code ORDERED}.
     *
     * @apiNote The spliterators for {@code Collection} classes in the JDK that
     * implement {@link NavigableSet} or {@link SortedSet} report {@code SORTED}.
     * <p>
     *  表示遭遇顺序遵循定义的排序顺序的特征值。
     * 如果是这样,方法{@link #getComparator()}返回关联的Comparator,或{@code null}(如果所有元素都是{@link Comparable})并按其自然排序排序。
     * 
     *  <p>报告{@code SORTED}的Spliterator也必须报告{@code ORDERED}。
     * 
     *  @apiNote用于实施{@link NavigableSet}或{@link SortedSet}报告{@code SORTED}的JDK中{@code Collection}类的分隔符。
     * 
     */
    public static final int SORTED     = 0x00000004;

    /**
     * Characteristic value signifying that the value returned from
     * {@code estimateSize()} prior to traversal or splitting represents a
     * finite size that, in the absence of structural source modification,
     * represents an exact count of the number of elements that would be
     * encountered by a complete traversal.
     *
     * @apiNote Most Spliterators for Collections, that cover all elements of a
     * {@code Collection} report this characteristic. Sub-spliterators, such as
     * those for {@link HashSet}, that cover a sub-set of elements and
     * approximate their reported size do not.
     * <p>
     *  特征值表示在遍历或拆分之前从{@code estimateSize()}返回的值表示在没有结构源修改的情况下,表示完整的遍历。
     * 
     * @apiNote用于集合的大多数拆分器,覆盖{@code Collection}的所有元素报告此特性。子分割器,例如{@link HashSet}的子分割器,它们覆盖一组元素并且近似它们报告的大小。
     * 
     */
    public static final int SIZED      = 0x00000040;

    /**
     * Characteristic value signifying that the source guarantees that
     * encountered elements will not be {@code null}. (This applies,
     * for example, to most concurrent collections, queues, and maps.)
     * <p>
     *  特征值表示源保证遇到的元素不会是{@code null}。 (这适用于例如大多数并发集合,队列和映射。)
     * 
     */
    public static final int NONNULL    = 0x00000100;

    /**
     * Characteristic value signifying that the element source cannot be
     * structurally modified; that is, elements cannot be added, replaced, or
     * removed, so such changes cannot occur during traversal. A Spliterator
     * that does not report {@code IMMUTABLE} or {@code CONCURRENT} is expected
     * to have a documented policy (for example throwing
     * {@link ConcurrentModificationException}) concerning structural
     * interference detected during traversal.
     * <p>
     *  特征值表示元素源不能在结构上修改;也就是说,不能添加,替换或删除元素,因此在遍历期间不会发生这样的更改。
     * 不报告{@code IMMUTABLE}或{@code CONCURRENT}的Spliterator应具有有关在遍历期间检测到的结构性干扰的文档化策略(例如,抛出{@link ConcurrentModificationException}
     * )。
     *  特征值表示元素源不能在结构上修改;也就是说,不能添加,替换或删除元素,因此在遍历期间不会发生这样的更改。
     * 
     */
    public static final int IMMUTABLE  = 0x00000400;

    /**
     * Characteristic value signifying that the element source may be safely
     * concurrently modified (allowing additions, replacements, and/or removals)
     * by multiple threads without external synchronization. If so, the
     * Spliterator is expected to have a documented policy concerning the impact
     * of modifications during traversal.
     *
     * <p>A top-level Spliterator should not report both {@code CONCURRENT} and
     * {@code SIZED}, since the finite size, if known, may change if the source
     * is concurrently modified during traversal. Such a Spliterator is
     * inconsistent and no guarantees can be made about any computation using
     * that Spliterator. Sub-spliterators may report {@code SIZED} if the
     * sub-split size is known and additions or removals to the source are not
     * reflected when traversing.
     *
     * @apiNote Most concurrent collections maintain a consistency policy
     * guaranteeing accuracy with respect to elements present at the point of
     * Spliterator construction, but possibly not reflecting subsequent
     * additions or removals.
     * <p>
     *  特征值表示元素源可以由多个线程安全地同时修改(允许添加,替换和/或删除)而不需要外部同步。如果是这样,则期望Spliterator具有关于遍历期间修改的影响的文档化策略。
     * 
     * <p>顶层Spliterator不应同时报告{@code CONCURRENT}和{@code SIZED},因为如果已知的话,如果在遍历期间同时修改源,则有限大小可能会更改。
     * 这种分裂器是不一致的,并且不能保证使用该分裂器的任何计算。如果子分割大小已知,并且遍历时没有反映源的添加或删除,则子分割器可以报告{@code SIZED}。
     * 
     *  @apiNote大多数并发集合维护一致性策略,以确保相对于在分割器构造时出现的元素的准确性,但可能不反映后续的添加或删除。
     * 
     */
    public static final int CONCURRENT = 0x00001000;

    /**
     * Characteristic value signifying that all Spliterators resulting from
     * {@code trySplit()} will be both {@link #SIZED} and {@link #SUBSIZED}.
     * (This means that all child Spliterators, whether direct or indirect, will
     * be {@code SIZED}.)
     *
     * <p>A Spliterator that does not report {@code SIZED} as required by
     * {@code SUBSIZED} is inconsistent and no guarantees can be made about any
     * computation using that Spliterator.
     *
     * @apiNote Some spliterators, such as the top-level spliterator for an
     * approximately balanced binary tree, will report {@code SIZED} but not
     * {@code SUBSIZED}, since it is common to know the size of the entire tree
     * but not the exact sizes of subtrees.
     * <p>
     *  特征值表示{@code trySplit()}产生的所有拆分器都将是{@link #SIZED}和{@link #SUBSIZED}。
     *  (这意味着所有子级拆分器,无论是直接还是间接,都将是{@code SIZED}。)。
     * 
     *  <p>根据{@code SUBSIZED}的要求,不报告{@code SIZED}的Spliterator不一致,因此无法保证使用该Spliterator的任何计算。
     * 
     *  @apiNote某些分割器,例如用于近似平衡的二叉树的顶层分割器,将报告{@code SIZED},但不报告{@code SUBSIZED},因为知道整个树的大小是常见的,而不是精确的子树的大小。
     * 
     */
    public static final int SUBSIZED = 0x00004000;

    /**
     * A Spliterator specialized for primitive values.
     *
     * <p>
     *  专用于原始值的Spliterator。
     * 
     * 
     * @param <T> the type of elements returned by this Spliterator.  The
     * type must be a wrapper type for a primitive type, such as {@code Integer}
     * for the primitive {@code int} type.
     * @param <T_CONS> the type of primitive consumer.  The type must be a
     * primitive specialization of {@link java.util.function.Consumer} for
     * {@code T}, such as {@link java.util.function.IntConsumer} for
     * {@code Integer}.
     * @param <T_SPLITR> the type of primitive Spliterator.  The type must be
     * a primitive specialization of Spliterator for {@code T}, such as
     * {@link Spliterator.OfInt} for {@code Integer}.
     *
     * @see Spliterator.OfInt
     * @see Spliterator.OfLong
     * @see Spliterator.OfDouble
     * @since 1.8
     */
    public interface OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
            extends Spliterator<T> {
        @Override
        T_SPLITR trySplit();

        /**
         * If a remaining element exists, performs the given action on it,
         * returning {@code true}; else returns {@code false}.  If this
         * Spliterator is {@link #ORDERED} the action is performed on the
         * next element in encounter order.  Exceptions thrown by the
         * action are relayed to the caller.
         *
         * <p>
         * 如果剩余元素存在,执行给定的操作,返回{@code true};否则返回{@code false}。如果此拆分器为{@link #ORDERED},则对遇到顺序中的下一个元素执行操作。
         * 操作抛出的异常中继到调用者。
         * 
         * 
         * @param action The action
         * @return {@code false} if no remaining elements existed
         * upon entry to this method, else {@code true}.
         * @throws NullPointerException if the specified action is null
         */
        @SuppressWarnings("overloads")
        boolean tryAdvance(T_CONS action);

        /**
         * Performs the given action for each remaining element, sequentially in
         * the current thread, until all elements have been processed or the
         * action throws an exception.  If this Spliterator is {@link #ORDERED},
         * actions are performed in encounter order.  Exceptions thrown by the
         * action are relayed to the caller.
         *
         * @implSpec
         * The default implementation repeatedly invokes {@link #tryAdvance}
         * until it returns {@code false}.  It should be overridden whenever
         * possible.
         *
         * <p>
         *  对当前线程中的每个剩余元素依次执行给定的操作,直到所有元素都被处理或操作抛出异常。如果此拆分器为{@link #ORDERED},则按照遇到顺序执行操作。操作抛出的异常中继到调用者。
         * 
         *  @implSpec默认实现重复调用{@link #tryAdvance},直到它返回{@code false}。应尽可能覆盖它。
         * 
         * 
         * @param action The action
         * @throws NullPointerException if the specified action is null
         */
        @SuppressWarnings("overloads")
        default void forEachRemaining(T_CONS action) {
            do { } while (tryAdvance(action));
        }
    }

    /**
     * A Spliterator specialized for {@code int} values.
     * <p>
     *  专用于{@code int}值的Spliterator。
     * 
     * 
     * @since 1.8
     */
    public interface OfInt extends OfPrimitive<Integer, IntConsumer, OfInt> {

        @Override
        OfInt trySplit();

        @Override
        boolean tryAdvance(IntConsumer action);

        @Override
        default void forEachRemaining(IntConsumer action) {
            do { } while (tryAdvance(action));
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code IntConsumer} then it is cast
         * to {@code IntConsumer} and passed to
         * {@link #tryAdvance(java.util.function.IntConsumer)}; otherwise
         * the action is adapted to an instance of {@code IntConsumer}, by
         * boxing the argument of {@code IntConsumer}, and then passed to
         * {@link #tryAdvance(java.util.function.IntConsumer)}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code IntConsumer}的实例,那么它将被转换为{@code IntConsumer}并传递给{@link #tryAdvance(java.util.function.IntConsumer)}
         * ;否则,通过将{@code IntConsumer}的参数加框,然后传递给{@link #tryAdvance(java.util.function.IntConsumer)},该操作适用于{@code IntConsumer}
         * 的实例。
         * 
         */
        @Override
        default boolean tryAdvance(Consumer<? super Integer> action) {
            if (action instanceof IntConsumer) {
                return tryAdvance((IntConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
                return tryAdvance((IntConsumer) action::accept);
            }
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code IntConsumer} then it is cast
         * to {@code IntConsumer} and passed to
         * {@link #forEachRemaining(java.util.function.IntConsumer)}; otherwise
         * the action is adapted to an instance of {@code IntConsumer}, by
         * boxing the argument of {@code IntConsumer}, and then passed to
         * {@link #forEachRemaining(java.util.function.IntConsumer)}.
         * <p>
         * {@inheritDoc} @implSpec如果操作是{@code IntConsumer}的实例,那么它将被转换为{@code IntConsumer}并传递给{@link #forEachRemaining(java.util.function.IntConsumer)}
         * ;否则,通过将{@code IntConsumer}的参数加框,然后传递给{@link #forEachRemaining(java.util.function.IntConsumer)},该操作适用于
         * {@code IntConsumer}的实例。
         * 
         */
        @Override
        default void forEachRemaining(Consumer<? super Integer> action) {
            if (action instanceof IntConsumer) {
                forEachRemaining((IntConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
                forEachRemaining((IntConsumer) action::accept);
            }
        }
    }

    /**
     * A Spliterator specialized for {@code long} values.
     * <p>
     *  专为{@code long}值设置的Spliterator。
     * 
     * 
     * @since 1.8
     */
    public interface OfLong extends OfPrimitive<Long, LongConsumer, OfLong> {

        @Override
        OfLong trySplit();

        @Override
        boolean tryAdvance(LongConsumer action);

        @Override
        default void forEachRemaining(LongConsumer action) {
            do { } while (tryAdvance(action));
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code LongConsumer} then it is cast
         * to {@code LongConsumer} and passed to
         * {@link #tryAdvance(java.util.function.LongConsumer)}; otherwise
         * the action is adapted to an instance of {@code LongConsumer}, by
         * boxing the argument of {@code LongConsumer}, and then passed to
         * {@link #tryAdvance(java.util.function.LongConsumer)}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code LongConsumer}的实例,那么它将被转换为{@code LongConsumer}并传递给{@link #tryAdvance(java.util.function.LongConsumer)}
         * ;否则,通过将{@code LongConsumer}的参数加框,然后传递给{@link #tryAdvance(java.util.function.LongConsumer)},将该操作调整为{@code LongConsumer}
         * 的实例。
         * 
         */
        @Override
        default boolean tryAdvance(Consumer<? super Long> action) {
            if (action instanceof LongConsumer) {
                return tryAdvance((LongConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
                return tryAdvance((LongConsumer) action::accept);
            }
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code LongConsumer} then it is cast
         * to {@code LongConsumer} and passed to
         * {@link #forEachRemaining(java.util.function.LongConsumer)}; otherwise
         * the action is adapted to an instance of {@code LongConsumer}, by
         * boxing the argument of {@code LongConsumer}, and then passed to
         * {@link #forEachRemaining(java.util.function.LongConsumer)}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code LongConsumer}的实例,那么它被转换为{@code LongConsumer}并传递给{@link #forEachRemaining(java.util.function.LongConsumer)}
         * ;否则,通过将{@code LongConsumer}的参数加框,然后传递给{@link #forEachRemaining(java.util.function.LongConsumer)},将该操作
         * 调整为{@code LongConsumer}的实例。
         * 
         */
        @Override
        default void forEachRemaining(Consumer<? super Long> action) {
            if (action instanceof LongConsumer) {
                forEachRemaining((LongConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
                forEachRemaining((LongConsumer) action::accept);
            }
        }
    }

    /**
     * A Spliterator specialized for {@code double} values.
     * <p>
     *  专为{@code double}值设置的Spliterator。
     * 
     * 
     * @since 1.8
     */
    public interface OfDouble extends OfPrimitive<Double, DoubleConsumer, OfDouble> {

        @Override
        OfDouble trySplit();

        @Override
        boolean tryAdvance(DoubleConsumer action);

        @Override
        default void forEachRemaining(DoubleConsumer action) {
            do { } while (tryAdvance(action));
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code DoubleConsumer} then it is
         * cast to {@code DoubleConsumer} and passed to
         * {@link #tryAdvance(java.util.function.DoubleConsumer)}; otherwise
         * the action is adapted to an instance of {@code DoubleConsumer}, by
         * boxing the argument of {@code DoubleConsumer}, and then passed to
         * {@link #tryAdvance(java.util.function.DoubleConsumer)}.
         * <p>
         * {@inheritDoc} @implSpec如果操作是{@code DoubleConsumer}的实例,则会转换为{@code DoubleConsumer}并传递给{@link #tryAdvance(java.util.function.DoubleConsumer)}
         * ;否则,通过将{@code DoubleConsumer}的参数加框,然后传递给{@link #tryAdvance(java.util.function.DoubleConsumer)},该操作适用于
         * {@code DoubleConsumer}的实例。
         * 
         */
        @Override
        default boolean tryAdvance(Consumer<? super Double> action) {
            if (action instanceof DoubleConsumer) {
                return tryAdvance((DoubleConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
                return tryAdvance((DoubleConsumer) action::accept);
            }
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code DoubleConsumer} then it is
         * cast to {@code DoubleConsumer} and passed to
         * {@link #forEachRemaining(java.util.function.DoubleConsumer)};
         * otherwise the action is adapted to an instance of
         * {@code DoubleConsumer}, by boxing the argument of
         * {@code DoubleConsumer}, and then passed to
         * {@link #forEachRemaining(java.util.function.DoubleConsumer)}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code DoubleConsumer}的实例,那么它将被转换为{@code DoubleConsumer}并传递给{@link #forEachRemaining(java.util.function.DoubleConsumer)}
         * ;否则,通过将{@code DoubleConsumer}的参数加框,然后传递给{@link #forEachRemaining(java.util.function.DoubleConsumer)},
         */
        @Override
        default void forEachRemaining(Consumer<? super Double> action) {
            if (action instanceof DoubleConsumer) {
                forEachRemaining((DoubleConsumer) action);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(),
                                  "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
                forEachRemaining((DoubleConsumer) action::accept);
            }
        }
    }
}
