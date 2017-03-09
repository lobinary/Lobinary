/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.util.stream;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

/**
 * A sequence of primitive double-valued elements supporting sequential and parallel
 * aggregate operations.  This is the {@code double} primitive specialization of
 * {@link Stream}.
 *
 * <p>The following example illustrates an aggregate operation using
 * {@link Stream} and {@link DoubleStream}, computing the sum of the weights of the
 * red widgets:
 *
 * <pre>{@code
 *     double sum = widgets.stream()
 *                         .filter(w -> w.getColor() == RED)
 *                         .mapToDouble(w -> w.getWeight())
 *                         .sum();
 * }</pre>
 *
 * See the class documentation for {@link Stream} and the package documentation
 * for <a href="package-summary.html">java.util.stream</a> for additional
 * specification of streams, stream operations, stream pipelines, and
 * parallelism.
 *
 * <p>
 *  支持顺序和并行聚合操作的原始双值元素序列。这是{@link Stream}的{@code double}原始专业化。
 * 
 *  <p>以下示例说明了使用{@link Stream}和{@link DoubleStream}计算红色小部件的权重之和的聚合操作：
 * 
 *  <pre> {@ code double sum = widgets.stream().filter(w  - > w.getColor()== RED).mapToDouble(w  - > w.getWeight()).sum }
 *  </pre>。
 * 
 *  请参阅{@link Stream}的类文档和<a href="package-summary.html"> java.util.stream </a>的软件包文档,了解有关流,流操作,流管道和并行性的
 * 其他规范。
 * 
 * 
 * @since 1.8
 * @see Stream
 * @see <a href="package-summary.html">java.util.stream</a>
 */
public interface DoubleStream extends BaseStream<Double, DoubleStream> {

    /**
     * Returns a stream consisting of the elements of this stream that match
     * the given predicate.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回由与给定谓词匹配的此流的元素组成的流。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to each element to determine if it
     *                  should be included
     * @return the new stream
     */
    DoubleStream filter(DoublePredicate predicate);

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回由将给定函数应用于此流的元素的结果组成的流。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    DoubleStream map(DoubleUnaryOperator mapper);

    /**
     * Returns an object-valued {@code Stream} consisting of the results of
     * applying the given function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">
     *     intermediate operation</a>.
     *
     * <p>
     *  返回由将给定函数应用于此流的元素的结果组成的对象值{@code Stream}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param <U> the element type of the new stream
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper);

    /**
     * Returns an {@code IntStream} consisting of the results of applying the
     * given function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     * 返回一个{@code IntStream},它包含将给定函数应用于此流的元素的结果。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    IntStream mapToInt(DoubleToIntFunction mapper);

    /**
     * Returns a {@code LongStream} consisting of the results of applying the
     * given function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回一个{@code LongStream},其中包含将给定函数应用于此流的元素的结果。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    LongStream mapToLong(DoubleToLongFunction mapper);

    /**
     * Returns a stream consisting of the results of replacing each element of
     * this stream with the contents of a mapped stream produced by applying
     * the provided mapping function to each element.  Each mapped stream is
     * {@link java.util.stream.BaseStream#close() closed} after its contents
     * have been placed into this stream.  (If a mapped stream is {@code null}
     * an empty stream is used, instead.)
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回由将此流的每个元素替换为通过将提供的映射函数应用于每个元素而生成的映射流的内容的结果组成的流。
     * 每个映射流在其内容被放入此流之后是{@link java.util.stream.BaseStream#close()closed}。 (如果映射流为{@code null},则使用空流)。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element which produces a
     *               {@code DoubleStream} of new values
     * @return the new stream
     * @see Stream#flatMap(Function)
     */
    DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper);

    /**
     * Returns a stream consisting of the distinct elements of this stream. The
     * elements are compared for equality according to
     * {@link java.lang.Double#compare(double, double)}.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * <p>
     *  返回由此流的不同元素组成的流。根据{@link java.lang.Double#compare(double,double)}比较元素的相等性。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * 
     * @return the result stream
     */
    DoubleStream distinct();

    /**
     * Returns a stream consisting of the elements of this stream in sorted
     * order. The elements are compared for equality according to
     * {@link java.lang.Double#compare(double, double)}.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * <p>
     *  以排序顺序返回由此流的元素组成的流。根据{@link java.lang.Double#compare(double,double)}比较元素的相等性。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * 
     * @return the result stream
     */
    DoubleStream sorted();

    /**
     * Returns a stream consisting of the elements of this stream, additionally
     * performing the provided action on each element as elements are consumed
     * from the resulting stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>For parallel stream pipelines, the action may be called at
     * whatever time and in whatever thread the element is made available by the
     * upstream operation.  If the action modifies shared state,
     * it is responsible for providing the required synchronization.
     *
     * @apiNote This method exists mainly to support debugging, where you want
     * to see the elements as they flow past a certain point in a pipeline:
     * <pre>{@code
     *     DoubleStream.of(1, 2, 3, 4)
     *         .filter(e -> e > 2)
     *         .peek(e -> System.out.println("Filtered value: " + e))
     *         .map(e -> e * e)
     *         .peek(e -> System.out.println("Mapped value: " + e))
     *         .sum();
     * }</pre>
     *
     * <p>
     * 返回由此流的元素组成的流,另外对每个元素执行提供的操作,因为元素从结果流中消耗。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     *  对于并行流管道,可以在任何时间调用动作,并且在任何线程中,元素被上游操作使得可用。如果操作修改共享状态,它负责提供所需的同步。
     * 
     *  @apiNote这个方法主要用于支持调试,当你想要看到的元素流经过一个管道中的某一点：<pre> {@ code DoubleStream.of(1,2,3,4).filter - > e> 2).peek(e-> System.out.println("Filtered value："+ e)).map(e-> e * e).peek(e-> System.out.println映射值："+ e)).sum(); }
     *  </pre>。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements as
     *               they are consumed from the stream
     * @return the new stream
     */
    DoubleStream peek(DoubleConsumer action);

    /**
     * Returns a stream consisting of the elements of this stream, truncated
     * to be no longer than {@code maxSize} in length.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * stateful intermediate operation</a>.
     *
     * @apiNote
     * While {@code limit()} is generally a cheap operation on sequential
     * stream pipelines, it can be quite expensive on ordered parallel pipelines,
     * especially for large values of {@code maxSize}, since {@code limit(n)}
     * is constrained to return not just any <em>n</em> elements, but the
     * <em>first n</em> elements in the encounter order.  Using an unordered
     * stream source (such as {@link #generate(DoubleSupplier)}) or removing the
     * ordering constraint with {@link #unordered()} may result in significant
     * speedups of {@code limit()} in parallel pipelines, if the semantics of
     * your situation permit.  If consistency with encounter order is required,
     * and you are experiencing poor performance or memory utilization with
     * {@code limit()} in parallel pipelines, switching to sequential execution
     * with {@link #sequential()} may improve performance.
     *
     * <p>
     *  返回由此流的元素组成的流,其截断长度不超过{@code maxSize}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短暂的有状态中间操作</a>。
     * 
     * @apiNote虽然{@code limit()}通常是顺序流管道上的廉价操作,但对于有序并行流水线来说可能相当昂贵,特别是对于大的{@code maxSize},因为{@code limit(n)}限
     * 制为不仅返回任何<em> n </em>元素,而是返回遇到顺序中的<em>前n个</em>元素。
     * 使用无序流源(例如{@link #generate(DoubleSupplier)})或者使用{@link #unordered()}删除排序约束可能会导致平行管道中的{@code limit()}的显
     * 着加速,如果您的情况许可的语义。
     * 如果需要与遇到顺序一致,并且在并行管道中使用{@code limit()}时遇到性能或内存利用率较低,使用{@link #sequential()}切换到顺序执行可能会提高性能。
     * 
     * 
     * @param maxSize the number of elements the stream should be limited to
     * @return the new stream
     * @throws IllegalArgumentException if {@code maxSize} is negative
     */
    DoubleStream limit(long maxSize);

    /**
     * Returns a stream consisting of the remaining elements of this stream
     * after discarding the first {@code n} elements of the stream.
     * If this stream contains fewer than {@code n} elements then an
     * empty stream will be returned.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * @apiNote
     * While {@code skip()} is generally a cheap operation on sequential
     * stream pipelines, it can be quite expensive on ordered parallel pipelines,
     * especially for large values of {@code n}, since {@code skip(n)}
     * is constrained to skip not just any <em>n</em> elements, but the
     * <em>first n</em> elements in the encounter order.  Using an unordered
     * stream source (such as {@link #generate(DoubleSupplier)}) or removing the
     * ordering constraint with {@link #unordered()} may result in significant
     * speedups of {@code skip()} in parallel pipelines, if the semantics of
     * your situation permit.  If consistency with encounter order is required,
     * and you are experiencing poor performance or memory utilization with
     * {@code skip()} in parallel pipelines, switching to sequential execution
     * with {@link #sequential()} may improve performance.
     *
     * <p>
     *  在丢弃流的第一个{@code n}元素后,返回由此流的剩余元素组成的流。如果此流包含少于{@code n}个元素,则将返回一个空流。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * @apiNote虽然{@code skip()}通常是顺序流管道上的廉价操作,但对于有序并行流水线来说可能相当昂贵,特别是对于{@code n}的大值,因为{@code skip(n)}限制为不仅跳过任
     * 何<em> n </em>元素,而是跳过遇到顺序中的<em>前n个</em>元素。
     * 使用无序的流源(例如{@link #generate(DoubleSupplier)})或使用{@link #unordered()}删除排序约束可能会导致并行管道中的{@code skip()}显着加
     * 速,如果您的情况许可的语义。
     * 如果需要与遇到顺序一致,并且在并行管道中使用{@code skip()}时遇到性能或内存利用率较低,使用{@link #sequential()}切换到顺序执行可能会提高性能。
     * 
     * 
     * @param n the number of leading elements to skip
     * @return the new stream
     * @throws IllegalArgumentException if {@code n} is negative
     */
    DoubleStream skip(long n);

    /**
     * Performs an action for each element of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>For parallel stream pipelines, this operation does <em>not</em>
     * guarantee to respect the encounter order of the stream, as doing so
     * would sacrifice the benefit of parallelism.  For any given element, the
     * action may be performed at whatever time and in whatever thread the
     * library chooses.  If the action accesses shared state, it is
     * responsible for providing the required synchronization.
     *
     * <p>
     *  对此流的每个元素执行操作。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  <p>对于并行流流水线,此操作不会保证尊重流的遭遇顺序,因为这样做会牺牲并行性的好处。对于任何给定的元素,动作可以在任何时间和在库选择的任何线程中执行。如果操作访问共享状态,则它负责提供所需的同步。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements
     */
    void forEach(DoubleConsumer action);

    /**
     * Performs an action for each element of this stream, guaranteeing that
     * each element is processed in encounter order for streams that have a
     * defined encounter order.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     * 对此流的每个元素执行操作,确保每个元素按照遇到顺序处理,以便具有定义的遇到顺序的流。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements
     * @see #forEach(DoubleConsumer)
     */
    void forEachOrdered(DoubleConsumer action);

    /**
     * Returns an array containing the elements of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  返回包含此流的元素的数组。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return an array containing the elements of this stream
     */
    double[] toArray();

    /**
     * Performs a <a href="package-summary.html#Reduction">reduction</a> on the
     * elements of this stream, using the provided identity value and an
     * <a href="package-summary.html#Associativity">associative</a>
     * accumulation function, and returns the reduced value.  This is equivalent
     * to:
     * <pre>{@code
     *     double result = identity;
     *     for (double element : this stream)
     *         result = accumulator.applyAsDouble(result, element)
     *     return result;
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     *
     * <p>The {@code identity} value must be an identity for the accumulator
     * function. This means that for all {@code x},
     * {@code accumulator.apply(identity, x)} is equal to {@code x}.
     * The {@code accumulator} function must be an
     * <a href="package-summary.html#Associativity">associative</a> function.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote Sum, min, max, and average are all special cases of reduction.
     * Summing a stream of numbers can be expressed as:

     * <pre>{@code
     *     double sum = numbers.reduce(0, (a, b) -> a+b);
     * }</pre>
     *
     * or more compactly:
     *
     * <pre>{@code
     *     double sum = numbers.reduce(0, Double::sum);
     * }</pre>
     *
     * <p>While this may seem a more roundabout way to perform an aggregation
     * compared to simply mutating a running total in a loop, reduction
     * operations parallelize more gracefully, without needing additional
     * synchronization and with greatly reduced risk of data races.
     *
     * <p>
     *  使用提供的身份值和<a href="package-summary.html#Associativity">关联关系对此流的元素执行<a href="package-summary.html#Reduction">
     * 缩小</a> </a>累积函数,并返回减小的值。
     * 这等效于：<pre> {@ code double result = identity; for(double element：this stream)result = accumulator.applyAsDouble(result,element)return result; }
     *  </pre>。
     * 
     *  但不限于顺序执行。
     * 
     *  <p> {@code identity}值必须是累加器函数的标识。
     * 这意味着对于所有{@code x},{@code accumulator.apply(identity,x)}等于{@code x}。
     *  {@code accumum}函数必须是<a href="package-summary.html#Associativity">关联</a>函数。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote Sum,min,max和average都是减少的特殊情况。对数字流求和可以表示为：
     * 
     *  <pre> {@ code double sum = numbers.reduce(0,(a,b) - > a + b); } </pre>
     * 
     *  或更紧凑：
     * 
     * <pre> {@ code double sum = numbers.reduce(0,Double :: sum); } </pre>
     * 
     *  与简单地在循环中改变运行总计相比,这可能似乎是一种更迂回的方式来执行聚合,但是减少操作更平缓地并行化,而不需要额外的同步并且大大降低数据竞争的风险。
     * 
     * 
     * @param identity the identity value for the accumulating function
     * @param op an <a href="package-summary.html#Associativity">associative</a>,
     *           <a href="package-summary.html#NonInterference">non-interfering</a>,
     *           <a href="package-summary.html#Statelessness">stateless</a>
     *           function for combining two values
     * @return the result of the reduction
     * @see #sum()
     * @see #min()
     * @see #max()
     * @see #average()
     */
    double reduce(double identity, DoubleBinaryOperator op);

    /**
     * Performs a <a href="package-summary.html#Reduction">reduction</a> on the
     * elements of this stream, using an
     * <a href="package-summary.html#Associativity">associative</a> accumulation
     * function, and returns an {@code OptionalDouble} describing the reduced
     * value, if any. This is equivalent to:
     * <pre>{@code
     *     boolean foundAny = false;
     *     double result = null;
     *     for (double element : this stream) {
     *         if (!foundAny) {
     *             foundAny = true;
     *             result = element;
     *         }
     *         else
     *             result = accumulator.applyAsDouble(result, element);
     *     }
     *     return foundAny ? OptionalDouble.of(result) : OptionalDouble.empty();
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     *
     * <p>The {@code accumulator} function must be an
     * <a href="package-summary.html#Associativity">associative</a> function.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  使用<a href="package-summary.html#Associativity">关联</a>累积对此流的元素执行<a href="package-summary.html#Reduction">
     * 缩小</a>函数,并返回描述缩减值的{@code OptionalDouble}(如果有)。
     * 这等效于：<pre> {@ code boolean foundAny = false; double result = null; for(double element：this stream){if(！foundAny){foundAny = true; result = element; }
     *  else result = accumulator.applyAsDouble(result,element); } return foundAny? OptionalDouble.of(result
     * )：OptionalDouble.empty(); } </pre>。
     * 
     *  但不限于顺序执行。
     * 
     *  <p> {@code accumum}函数必须是<a href="package-summary.html#Associativity">关联</a>函数。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param op an <a href="package-summary.html#Associativity">associative</a>,
     *           <a href="package-summary.html#NonInterference">non-interfering</a>,
     *           <a href="package-summary.html#Statelessness">stateless</a>
     *           function for combining two values
     * @return the result of the reduction
     * @see #reduce(double, DoubleBinaryOperator)
     */
    OptionalDouble reduce(DoubleBinaryOperator op);

    /**
     * Performs a <a href="package-summary.html#MutableReduction">mutable
     * reduction</a> operation on the elements of this stream.  A mutable
     * reduction is one in which the reduced value is a mutable result container,
     * such as an {@code ArrayList}, and elements are incorporated by updating
     * the state of the result rather than by replacing the result.  This
     * produces a result equivalent to:
     * <pre>{@code
     *     R result = supplier.get();
     *     for (double element : this stream)
     *         accumulator.accept(result, element);
     *     return result;
     * }</pre>
     *
     * <p>Like {@link #reduce(double, DoubleBinaryOperator)}, {@code collect}
     * operations can be parallelized without requiring additional
     * synchronization.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     * 对此流的元素执行<a href="package-summary.html#MutableReduction">可变缩减</a>操作。
     * 可变减少是其中减少的值是可变的结果容器,例如{@code ArrayList}的减少,并且通过更新结果的状态而不是通过替换结果来并入元素。
     * 这产生等效的结果：<pre> {@ code R result = supplier.get(); for(double element：this stream)accumulator.accept(result,element);返回结果; }
     *  </pre>。
     * 可变减少是其中减少的值是可变的结果容器,例如{@code ArrayList}的减少,并且通过更新结果的状态而不是通过替换结果来并入元素。
     * 
     *  <p>与{@link #reduce(double,DoubleBinaryOperator)}一样,{@code collect}操作可以并行化,而不需要额外的同步。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param <R> type of the result
     * @param supplier a function that creates a new result container. For a
     *                 parallel execution, this function may be called
     *                 multiple times and must return a fresh value each time.
     * @param accumulator an <a href="package-summary.html#Associativity">associative</a>,
     *                    <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                    <a href="package-summary.html#Statelessness">stateless</a>
     *                    function for incorporating an additional element into a result
     * @param combiner an <a href="package-summary.html#Associativity">associative</a>,
     *                    <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                    <a href="package-summary.html#Statelessness">stateless</a>
     *                    function for combining two values, which must be
     *                    compatible with the accumulator function
     * @return the result of the reduction
     * @see Stream#collect(Supplier, BiConsumer, BiConsumer)
     */
    <R> R collect(Supplier<R> supplier,
                  ObjDoubleConsumer<R> accumulator,
                  BiConsumer<R, R> combiner);

    /**
     * Returns the sum of elements in this stream.
     *
     * Summation is a special case of a <a
     * href="package-summary.html#Reduction">reduction</a>. If
     * floating-point summation were exact, this method would be
     * equivalent to:
     *
     * <pre>{@code
     *     return reduce(0, Double::sum);
     * }</pre>
     *
     * However, since floating-point summation is not exact, the above
     * code is not necessarily equivalent to the summation computation
     * done by this method.
     *
     * <p>If any stream element is a NaN or the sum is at any point a NaN
     * then the sum will be NaN.
     *
     * The value of a floating-point sum is a function both
     * of the input values as well as the order of addition
     * operations. The order of addition operations of this method is
     * intentionally not defined to allow for implementation
     * flexibility to improve the speed and accuracy of the computed
     * result.
     *
     * In particular, this method may be implemented using compensated
     * summation or other technique to reduce the error bound in the
     * numerical sum compared to a simple summation of {@code double}
     * values.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote Elements sorted by increasing absolute magnitude tend
     * to yield more accurate results.
     *
     * <p>
     *  返回此流中的元素的总和。
     * 
     *  汇总是<a href="package-summary.html#Reduction">缩减</a>的特殊情况。如果浮点求和是精确的,则此方法将等效于：
     * 
     *  <pre> {@ code return reduce(0,Double :: sum); } </pre>
     * 
     *  然而,由于浮点求和不是精确的,上述代码不一定等同于通过该方法进行的求和计算。
     * 
     *  <p>如果任何流元素是NaN或总和在任何点都是NaN,那么和将是NaN。
     * 
     * 浮点和的值是输入值以及加法运算的顺序的函数。该方法的加法运算的顺序被有意地定义为不允许实现灵活性以提高计算结果的速度和精度。
     * 
     *  特别地,该方法可以使用补偿求和或其他技术来实现,以减少与{@code double}值的简单求和相比的数值求和中的误差界限。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote按增加绝对值排序的元素往往产生更准确的结果。
     * 
     * 
     * @return the sum of elements in this stream
     */
    double sum();

    /**
     * Returns an {@code OptionalDouble} describing the minimum element of this
     * stream, or an empty OptionalDouble if this stream is empty.  The minimum
     * element will be {@code Double.NaN} if any stream element was NaN. Unlike
     * the numerical comparison operators, this method considers negative zero
     * to be strictly smaller than positive zero. This is a special case of a
     * <a href="package-summary.html#Reduction">reduction</a> and is
     * equivalent to:
     * <pre>{@code
     *     return reduce(Double::min);
     * }</pre>
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  返回描述此流的最小元素的{@code OptionalDouble},如果此流为空,则返回空Optional4ouble。如果任何流元素是NaN,则最小元素将是{@code Double.NaN}。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况,等效于：<pre> {@ code return reduce(Double :: min); }
     *  </pre>。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return an {@code OptionalDouble} containing the minimum element of this
     * stream, or an empty optional if the stream is empty
     */
    OptionalDouble min();

    /**
     * Returns an {@code OptionalDouble} describing the maximum element of this
     * stream, or an empty OptionalDouble if this stream is empty.  The maximum
     * element will be {@code Double.NaN} if any stream element was NaN. Unlike
     * the numerical comparison operators, this method considers negative zero
     * to be strictly smaller than positive zero. This is a
     * special case of a
     * <a href="package-summary.html#Reduction">reduction</a> and is
     * equivalent to:
     * <pre>{@code
     *     return reduce(Double::max);
     * }</pre>
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     * 返回描述此流的最大元素的{@code OptionalDouble},如果此流为空,则返回空Optional4ouble。如果任何流元素为NaN,则最大元素为{@code Double.NaN}。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况,等效于：<pre> {@ code return reduce(Double :: max); }
     *  </pre>。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return an {@code OptionalDouble} containing the maximum element of this
     * stream, or an empty optional if the stream is empty
     */
    OptionalDouble max();

    /**
     * Returns the count of elements in this stream.  This is a special case of
     * a <a href="package-summary.html#Reduction">reduction</a> and is
     * equivalent to:
     * <pre>{@code
     *     return mapToLong(e -> 1L).sum();
     * }</pre>
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal operation</a>.
     *
     * <p>
     *  返回此流中的元素数。
     * 这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况,相当于：<pre> {@ code return mapToLong(e  - > 1L).sum(); }
     *  </pre>。
     *  返回此流中的元素数。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return the count of elements in this stream
     */
    long count();

    /**
     * Returns an {@code OptionalDouble} describing the arithmetic
     * mean of elements of this stream, or an empty optional if this
     * stream is empty.
     *
     * If any recorded value is a NaN or the sum is at any point a NaN
     * then the average will be NaN.
     *
     * <p>The average returned can vary depending upon the order in
     * which values are recorded.
     *
     * This method may be implemented using compensated summation or
     * other technique to reduce the error bound in the {@link #sum
     * numerical sum} used to compute the average.
     *
     *  <p>The average is a special case of a <a
     *  href="package-summary.html#Reduction">reduction</a>.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote Elements sorted by increasing absolute magnitude tend
     * to yield more accurate results.
     *
     * <p>
     *  返回描述此流的元素的算术平均值的{@code OptionalDouble},如果此流为空,则返回空可选。
     * 
     *  如果任何记录值是NaN或总和在任何点NaN,则平均值将是NaN。
     * 
     *  <p>返回的平均值可能会根据记录值的顺序而有所不同。
     * 
     *  该方法可以使用补偿求和或其他技术来实现,以减少用于计算平均值的{@link #sum数值和}中的误差界限。
     * 
     *  <p>平均值是<a href="package-summary.html#Reduction">缩小</a>的特殊情况。
     * 
     * <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote按增加绝对值排序的元素往往产生更准确的结果。
     * 
     * 
     * @return an {@code OptionalDouble} containing the average element of this
     * stream, or an empty optional if the stream is empty
     */
    OptionalDouble average();

    /**
     * Returns a {@code DoubleSummaryStatistics} describing various summary data
     * about the elements of this stream.  This is a special
     * case of a <a href="package-summary.html#Reduction">reduction</a>.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  返回{@code DoubleSummaryStatistics}描述有关此流元素的各种摘要数据。
     * 这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return a {@code DoubleSummaryStatistics} describing various summary data
     * about the elements of this stream
     */
    DoubleSummaryStatistics summaryStatistics();

    /**
     * Returns whether any elements of this stream match the provided
     * predicate.  May not evaluate the predicate on all elements if not
     * necessary for determining the result.  If the stream is empty then
     * {@code false} is returned and the predicate is not evaluated.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * @apiNote
     * This method evaluates the <em>existential quantification</em> of the
     * predicate over the elements of the stream (for some x P(x)).
     *
     * <p>
     *  返回此流的任何元素是否与提供的谓词匹配。如果不是必需的,可以不评估所有元素的谓词以确定结果。如果流是空的,则返回{@code false},并且不评估谓词。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     *  @apiNote此方法计算流的元素(对于某些x P(x))的谓词的<em>存在量化</em>。
     * 
     * 
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to elements of this stream
     * @return {@code true} if any elements of the stream match the provided
     * predicate, otherwise {@code false}
     */
    boolean anyMatch(DoublePredicate predicate);

    /**
     * Returns whether all elements of this stream match the provided predicate.
     * May not evaluate the predicate on all elements if not necessary for
     * determining the result.  If the stream is empty then {@code true} is
     * returned and the predicate is not evaluated.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * @apiNote
     * This method evaluates the <em>universal quantification</em> of the
     * predicate over the elements of the stream (for all x P(x)).  If the
     * stream is empty, the quantification is said to be <em>vacuously
     * satisfied</em> and is always {@code true} (regardless of P(x)).
     *
     * <p>
     *  返回此流的所有元素是否与提供的谓词匹配。如果不是必需的,可以不评估所有元素的谓词以确定结果。如果流是空的,则返回{@code true},并且不评估谓词。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     * @apiNote此方法评估流的元素(对于所有x P(x))的谓词的<em>通用量化</em>。如果流是空的,则量化被认为是真空满足的并且总是{@code true}(不考虑P(x))。
     * 
     * 
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to elements of this stream
     * @return {@code true} if either all elements of the stream match the
     * provided predicate or the stream is empty, otherwise {@code false}
     */
    boolean allMatch(DoublePredicate predicate);

    /**
     * Returns whether no elements of this stream match the provided predicate.
     * May not evaluate the predicate on all elements if not necessary for
     * determining the result.  If the stream is empty then {@code true} is
     * returned and the predicate is not evaluated.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * @apiNote
     * This method evaluates the <em>universal quantification</em> of the
     * negated predicate over the elements of the stream (for all x ~P(x)).  If
     * the stream is empty, the quantification is said to be vacuously satisfied
     * and is always {@code true}, regardless of P(x).
     *
     * <p>
     *  返回此流的任何元素是否与提供的谓词匹配。如果不是必需的,可以不评估所有元素的谓词以确定结果。如果流是空的,则返回{@code true},并且不评估谓词。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     *  @apiNote此方法计算流的元素(对于所有x〜P(x))的否定谓词的<em>通用量化</em>。如果流是空的,则量化被认为是真空满足的,并且总是{@code true},而不管P(x)。
     * 
     * 
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to elements of this stream
     * @return {@code true} if either no elements of the stream match the
     * provided predicate or the stream is empty, otherwise {@code false}
     */
    boolean noneMatch(DoublePredicate predicate);

    /**
     * Returns an {@link OptionalDouble} describing the first element of this
     * stream, or an empty {@code OptionalDouble} if the stream is empty.  If
     * the stream has no encounter order, then any element may be returned.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * <p>
     *  返回描述此流的第一个元素的{@link OptionalDouble},如果流为空,则返回空的{@code OptionalDouble}。如果流没有遇到顺序,则可以返回任何元素。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     * 
     * @return an {@code OptionalDouble} describing the first element of this
     * stream, or an empty {@code OptionalDouble} if the stream is empty
     */
    OptionalDouble findFirst();

    /**
     * Returns an {@link OptionalDouble} describing some element of the stream,
     * or an empty {@code OptionalDouble} if the stream is empty.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * <p>The behavior of this operation is explicitly nondeterministic; it is
     * free to select any element in the stream.  This is to allow for maximal
     * performance in parallel operations; the cost is that multiple invocations
     * on the same source may not return the same result.  (If a stable result
     * is desired, use {@link #findFirst()} instead.)
     *
     * <p>
     *  返回描述流的某个元素的{@link OptionalDouble},如果流为空,则返回空的{@code OptionalDouble}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     * <p>此操作的行为是明确不确定的;它可以自由地选择流中的任何元素。这是为了允许并行操作的最大性能;成本是同一源上的多个调用可能不会返回相同的结果。
     *  (如果需要稳定的结果,请改用{@link #findFirst()}。)。
     * 
     * 
     * @return an {@code OptionalDouble} describing some element of this stream,
     * or an empty {@code OptionalDouble} if the stream is empty
     * @see #findFirst()
     */
    OptionalDouble findAny();

    /**
     * Returns a {@code Stream} consisting of the elements of this stream,
     * boxed to {@code Double}.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回由此流的元素组成的{@code Stream},加入{@code Double}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @return a {@code Stream} consistent of the elements of this stream,
     * each boxed to a {@code Double}
     */
    Stream<Double> boxed();

    @Override
    DoubleStream sequential();

    @Override
    DoubleStream parallel();

    @Override
    PrimitiveIterator.OfDouble iterator();

    @Override
    Spliterator.OfDouble spliterator();


    // Static factories

    /**
     * Returns a builder for a {@code DoubleStream}.
     *
     * <p>
     *  返回{@code DoubleStream}的构建器。
     * 
     * 
     * @return a stream builder
     */
    public static Builder builder() {
        return new Streams.DoubleStreamBuilderImpl();
    }

    /**
     * Returns an empty sequential {@code DoubleStream}.
     *
     * <p>
     *  返回一个空的顺序{@code DoubleStream}。
     * 
     * 
     * @return an empty sequential stream
     */
    public static DoubleStream empty() {
        return StreamSupport.doubleStream(Spliterators.emptyDoubleSpliterator(), false);
    }

    /**
     * Returns a sequential {@code DoubleStream} containing a single element.
     *
     * <p>
     *  返回包含单个元素的序列{@code DoubleStream}。
     * 
     * 
     * @param t the single element
     * @return a singleton sequential stream
     */
    public static DoubleStream of(double t) {
        return StreamSupport.doubleStream(new Streams.DoubleStreamBuilderImpl(t), false);
    }

    /**
     * Returns a sequential ordered stream whose elements are the specified values.
     *
     * <p>
     *  返回其元素为指定值的顺序有序流。
     * 
     * 
     * @param values the elements of the new stream
     * @return the new stream
     */
    public static DoubleStream of(double... values) {
        return Arrays.stream(values);
    }

    /**
     * Returns an infinite sequential ordered {@code DoubleStream} produced by iterative
     * application of a function {@code f} to an initial element {@code seed},
     * producing a {@code Stream} consisting of {@code seed}, {@code f(seed)},
     * {@code f(f(seed))}, etc.
     *
     * <p>The first element (position {@code 0}) in the {@code DoubleStream}
     * will be the provided {@code seed}.  For {@code n > 0}, the element at
     * position {@code n}, will be the result of applying the function {@code f}
     *  to the element at position {@code n - 1}.
     *
     * <p>
     *  返回通过将函数{@code f}迭代应用于初始元素{@code seed}生成的无限连续有序{@code DoubleStream},生成由{@code seed},{@code} f(seed)},
     * {@code f(f(seed))}等。
     * 
     *  <p> {@code DoubleStream}中的第一个元素(位置{@code 0})将是提供的{@code seed}。
     * 对于{@code n> 0},位置{@code n}处的元素将是在位置{@code n  -  1}处的元素应用函数{@code f}的结果。
     * 
     * 
     * @param seed the initial element
     * @param f a function to be applied to to the previous element to produce
     *          a new element
     * @return a new sequential {@code DoubleStream}
     */
    public static DoubleStream iterate(final double seed, final DoubleUnaryOperator f) {
        Objects.requireNonNull(f);
        final PrimitiveIterator.OfDouble iterator = new PrimitiveIterator.OfDouble() {
            double t = seed;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public double nextDouble() {
                double v = t;
                t = f.applyAsDouble(t);
                return v;
            }
        };
        return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL), false);
    }

    /**
     * Returns an infinite sequential unordered stream where each element is
     * generated by the provided {@code DoubleSupplier}.  This is suitable for
     * generating constant streams, streams of random elements, etc.
     *
     * <p>
     *  返回无限连续无序流,其中每个元素都由提供的{@code DoubleSupplier}生成。这适用于生成恒定流,随机元素流等。
     * 
     * 
     * @param s the {@code DoubleSupplier} for generated elements
     * @return a new infinite sequential unordered {@code DoubleStream}
     */
    public static DoubleStream generate(DoubleSupplier s) {
        Objects.requireNonNull(s);
        return StreamSupport.doubleStream(
                new StreamSpliterators.InfiniteSupplyingSpliterator.OfDouble(Long.MAX_VALUE, s), false);
    }

    /**
     * Creates a lazily concatenated stream whose elements are all the
     * elements of the first stream followed by all the elements of the
     * second stream.  The resulting stream is ordered if both
     * of the input streams are ordered, and parallel if either of the input
     * streams is parallel.  When the resulting stream is closed, the close
     * handlers for both input streams are invoked.
     *
     * @implNote
     * Use caution when constructing streams from repeated concatenation.
     * Accessing an element of a deeply concatenated stream can result in deep
     * call chains, or even {@code StackOverflowException}.
     *
     * <p>
     * 创建一个延迟连接的流,其元素是第一个流的所有元素,后跟第二个流的所有元素。如果两个输入流都被排序,则产生的流被排序,并且如果任一输入流是并行的,则是并行的。
     * 当关闭结果流时,调用两个输入流的关闭处理程序。
     * 
     *  @implNote从重复连接构造流时要小心。访问深度并置的流的元素可能导致深度调用链,甚至是{@code StackOverflowException}。
     * 
     * 
     * @param a the first stream
     * @param b the second stream
     * @return the concatenation of the two input streams
     */
    public static DoubleStream concat(DoubleStream a, DoubleStream b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        Spliterator.OfDouble split = new Streams.ConcatSpliterator.OfDouble(
                a.spliterator(), b.spliterator());
        DoubleStream stream = StreamSupport.doubleStream(split, a.isParallel() || b.isParallel());
        return stream.onClose(Streams.composedClose(a, b));
    }

    /**
     * A mutable builder for a {@code DoubleStream}.
     *
     * <p>A stream builder has a lifecycle, which starts in a building
     * phase, during which elements can be added, and then transitions to a built
     * phase, after which elements may not be added.  The built phase
     * begins when the {@link #build()} method is called, which creates an
     * ordered stream whose elements are the elements that were added to the
     * stream builder, in the order they were added.
     *
     * <p>
     *  {@code DoubleStream}的可变构建器。
     * 
     *  <p>流构建器具有生命周期,它始于构建阶段,在此阶段中可以添加元素,然后转换到构建阶段,之后可以不添加元素。
     * 构建阶段从调用{@link #build()}方法开始,它创建一个有序流,其元素是添加到流构建器的元素,按添加顺序排列。
     * 
     * 
     * @see DoubleStream#builder()
     * @since 1.8
     */
    public interface Builder extends DoubleConsumer {

        /**
         * Adds an element to the stream being built.
         *
         * <p>
         *  向正在构建的流中添加一个元素。
         * 
         * 
         * @throws IllegalStateException if the builder has already transitioned
         * to the built state
         */
        @Override
        void accept(double t);

        /**
         * Adds an element to the stream being built.
         *
         * @implSpec
         * The default implementation behaves as if:
         * <pre>{@code
         *     accept(t)
         *     return this;
         * }</pre>
         *
         * <p>
         *  向正在构建的流中添加一个元素。
         * 
         *  @implSpec默认实现的行为如下：<pre> {@ code accept(t)return this; } </pre>
         * 
         * 
         * @param t the element to add
         * @return {@code this} builder
         * @throws IllegalStateException if the builder has already transitioned
         * to the built state
         */
        default Builder add(double t) {
            accept(t);
            return this;
        }

        /**
         * Builds the stream, transitioning this builder to the built state.
         * An {@code IllegalStateException} is thrown if there are further
         * attempts to operate on the builder after it has entered the built
         * state.
         *
         * <p>
         * 
         * @return the built stream
         * @throws IllegalStateException if the builder has already transitioned
         * to the built state
         */
        DoubleStream build();
    }
}
