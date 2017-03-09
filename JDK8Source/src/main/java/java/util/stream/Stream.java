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
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * A sequence of elements supporting sequential and parallel aggregate
 * operations.  The following example illustrates an aggregate operation using
 * {@link Stream} and {@link IntStream}:
 *
 * <pre>{@code
 *     int sum = widgets.stream()
 *                      .filter(w -> w.getColor() == RED)
 *                      .mapToInt(w -> w.getWeight())
 *                      .sum();
 * }</pre>
 *
 * In this example, {@code widgets} is a {@code Collection<Widget>}.  We create
 * a stream of {@code Widget} objects via {@link Collection#stream Collection.stream()},
 * filter it to produce a stream containing only the red widgets, and then
 * transform it into a stream of {@code int} values representing the weight of
 * each red widget. Then this stream is summed to produce a total weight.
 *
 * <p>In addition to {@code Stream}, which is a stream of object references,
 * there are primitive specializations for {@link IntStream}, {@link LongStream},
 * and {@link DoubleStream}, all of which are referred to as "streams" and
 * conform to the characteristics and restrictions described here.
 *
 * <p>To perform a computation, stream
 * <a href="package-summary.html#StreamOps">operations</a> are composed into a
 * <em>stream pipeline</em>.  A stream pipeline consists of a source (which
 * might be an array, a collection, a generator function, an I/O channel,
 * etc), zero or more <em>intermediate operations</em> (which transform a
 * stream into another stream, such as {@link Stream#filter(Predicate)}), and a
 * <em>terminal operation</em> (which produces a result or side-effect, such
 * as {@link Stream#count()} or {@link Stream#forEach(Consumer)}).
 * Streams are lazy; computation on the source data is only performed when the
 * terminal operation is initiated, and source elements are consumed only
 * as needed.
 *
 * <p>Collections and streams, while bearing some superficial similarities,
 * have different goals.  Collections are primarily concerned with the efficient
 * management of, and access to, their elements.  By contrast, streams do not
 * provide a means to directly access or manipulate their elements, and are
 * instead concerned with declaratively describing their source and the
 * computational operations which will be performed in aggregate on that source.
 * However, if the provided stream operations do not offer the desired
 * functionality, the {@link #iterator()} and {@link #spliterator()} operations
 * can be used to perform a controlled traversal.
 *
 * <p>A stream pipeline, like the "widgets" example above, can be viewed as
 * a <em>query</em> on the stream source.  Unless the source was explicitly
 * designed for concurrent modification (such as a {@link ConcurrentHashMap}),
 * unpredictable or erroneous behavior may result from modifying the stream
 * source while it is being queried.
 *
 * <p>Most stream operations accept parameters that describe user-specified
 * behavior, such as the lambda expression {@code w -> w.getWeight()} passed to
 * {@code mapToInt} in the example above.  To preserve correct behavior,
 * these <em>behavioral parameters</em>:
 * <ul>
 * <li>must be <a href="package-summary.html#NonInterference">non-interfering</a>
 * (they do not modify the stream source); and</li>
 * <li>in most cases must be <a href="package-summary.html#Statelessness">stateless</a>
 * (their result should not depend on any state that might change during execution
 * of the stream pipeline).</li>
 * </ul>
 *
 * <p>Such parameters are always instances of a
 * <a href="../function/package-summary.html">functional interface</a> such
 * as {@link java.util.function.Function}, and are often lambda expressions or
 * method references.  Unless otherwise specified these parameters must be
 * <em>non-null</em>.
 *
 * <p>A stream should be operated on (invoking an intermediate or terminal stream
 * operation) only once.  This rules out, for example, "forked" streams, where
 * the same source feeds two or more pipelines, or multiple traversals of the
 * same stream.  A stream implementation may throw {@link IllegalStateException}
 * if it detects that the stream is being reused. However, since some stream
 * operations may return their receiver rather than a new stream object, it may
 * not be possible to detect reuse in all cases.
 *
 * <p>Streams have a {@link #close()} method and implement {@link AutoCloseable},
 * but nearly all stream instances do not actually need to be closed after use.
 * Generally, only streams whose source is an IO channel (such as those returned
 * by {@link Files#lines(Path, Charset)}) will require closing.  Most streams
 * are backed by collections, arrays, or generating functions, which require no
 * special resource management.  (If a stream does require closing, it can be
 * declared as a resource in a {@code try}-with-resources statement.)
 *
 * <p>Stream pipelines may execute either sequentially or in
 * <a href="package-summary.html#Parallelism">parallel</a>.  This
 * execution mode is a property of the stream.  Streams are created
 * with an initial choice of sequential or parallel execution.  (For example,
 * {@link Collection#stream() Collection.stream()} creates a sequential stream,
 * and {@link Collection#parallelStream() Collection.parallelStream()} creates
 * a parallel one.)  This choice of execution mode may be modified by the
 * {@link #sequential()} or {@link #parallel()} methods, and may be queried with
 * the {@link #isParallel()} method.
 *
 * <p>
 *  支持顺序和并行聚合操作的元素序列。以下示例说明使用{@link Stream}和{@link IntStream}的聚合操作：
 * 
 *  <pre> {@ code int sum = widgets.stream().filter(w  - > w.getColor()== RED).mapToInt(w  - > w.getWeight()).sum }
 *  </pre>。
 * 
 *  在本示例中,{@code widgets}是一个{@code Collection <Widget>}。
 * 我们通过{@link Collection#stream Collection.stream()}创建一个{@code Widget}对象流,对它进行过滤,产生一个只包含红色小部件的流,然后将其转换成{@code int}
 * 表示每个红色小部件的权重的值。
 *  在本示例中,{@code widgets}是一个{@code Collection <Widget>}。然后将该流相加以产生总重量。
 * 
 *  <p>除了{@code Stream}(对象引用流)之外,还有{@link IntStream},{@link LongStream}和{@link DoubleStream}的原始特殊化,所有这些都
 * 被引用作为"流",并符合这里描述的特性和限制。
 * 
 * <p>要执行计算,流<a href="package-summary.html#StreamOps">操作</a>会合并到流媒体流</em>中。
 * 流流水线包括源(其可以是数组,集合,生成器函数,I / O通道等),零个或多个中间操作(其将流转换成另一个流,例如{@link Stream#filter(Predicate)})和终端操作</em>(
 * 其产生结果或副作用,例如{@link Stream#count()}或{链接流#forEach(消费者)})。
 * <p>要执行计算,流<a href="package-summary.html#StreamOps">操作</a>会合并到流媒体流</em>中。
 * 流是懒惰的;仅当终端操作被启动时才执行对源数据的计算,并且仅在需要时消耗源元素。
 * 
 *  <p>集合和流,虽然具有一些表面的相似性,有不同的目标。集合主要涉及对其元素的有效管理和访问。
 * 相比之下,流不提供直接访问或操纵其元素的手段,而是涉及以声明方式描述它们的源和将在该源上集合地执行的计算操作。
 * 但是,如果提供的流操作不提供所需的功能,则{@link #iterator()}和{@link #spliterator()}操作可用于执行受控遍历。
 * 
 * <p>流水线,如上面的"窗口小部件"示例,可以被视为流源上的<em>查询</em>。
 * 除非源被明确地设计用于并发修改(例如{@link ConcurrentHashMap}),不可预测或错误的行为可能源于在查询流源时修改它。
 * 
 *  <p>大多数流操作接受描述用户指定行为的参数,例如上面示例中传递给{@code mapToInt}的lambda表达式{@code w  - > w.getWeight()}。
 * 为了保持正确的行为,这些<em>行为参数</em>：。
 * <ul>
 *  <li>必须<a href="package-summary.html#NonInterference">无干扰</a>(它们不会修改流来源);和</li> <li>在大多数情况下必须是<a href="package-summary.html#Statelessness">
 * 无状态</a>(其结果不应取决于在执行流管道期间可能更改的任何状态)。
 * </li>。
 * </ul>
 * 
 *  <p>此类参数始终是<a href="../function/package-summary.html">功能界面</a>(例如{@link java.util.function.Function})
 * 的实例,通常lambda表达式或方法引用。
 * 除非另有说明,这些参数必须为<em>非空</em>。
 * 
 * <p>应该对流进行操作(调用中间或终端流操作)一次。这排除了例如"分叉"流,其中相同源馈送两个或更多个流水线,或同一流的多个遍历。
 * 如果流实现检测到流被重用,则可能抛出{@link IllegalStateException}。然而,由于一些流操作可能返回它们的接收器而不是新的流对象,所以在所有情况下可能不能检测重用。
 * 
 *  <p>流具有{@link #close()}方法并实现{@link AutoCloseable},但几乎所有流实例实际上都不需要在使用后关闭。
 * 通常,只有源是IO通道的流(例如由{@link Files#lines(Path,Charset)}返回的流)将需要关闭。大多数流由集合,数组或生成函数支持,不需要特殊的资源管理。
 *  (如果流不需要关闭,它可以在{@code try} -with-resources语句中声明为资源。)。
 * 
 * <p>流管道可以按顺序执行,也可以在<a href="package-summary.html#Parallelism">并行</a>中执行。此执行模式是流的属性。流创建有顺序或并行执行的初始选择。
 *  (例如,{@link Collection#stream()Collection.stream()}创建一个顺序流,{@link Collection#parallelStream()Collection.parallelStream()}
 * 创建一个并行流。
 * <p>流管道可以按顺序执行,也可以在<a href="package-summary.html#Parallelism">并行</a>中执行。此执行模式是流的属性。流创建有顺序或并行执行的初始选择。
 * )这种执行模式的选择可以通过{@link #sequential()}或{@link #parallel()}方法修改,也可以使用{@link #isParallel()}方法查询。
 * 
 * 
 * @param <T> the type of the stream elements
 * @since 1.8
 * @see IntStream
 * @see LongStream
 * @see DoubleStream
 * @see <a href="package-summary.html">java.util.stream</a>
 */
public interface Stream<T> extends BaseStream<T, Stream<T>> {

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
    Stream<T> filter(Predicate<? super T> predicate);

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
     * @param <R> The element type of the new stream
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);

    /**
     * Returns an {@code IntStream} consisting of the results of applying the
     * given function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">
     *     intermediate operation</a>.
     *
     * <p>
     *  返回一个{@code IntStream},它包含将给定函数应用于此流的元素的结果。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    IntStream mapToInt(ToIntFunction<? super T> mapper);

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
    LongStream mapToLong(ToLongFunction<? super T> mapper);

    /**
     * Returns a {@code DoubleStream} consisting of the results of applying the
     * given function to the elements of this stream.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回一个{@code DoubleStream},其中包含将给定函数应用于此流的元素的结果。
     * 
     * <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element
     * @return the new stream
     */
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);

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
     * @apiNote
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     *
     * <p><b>Examples.</b>
     *
     * <p>If {@code orders} is a stream of purchase orders, and each purchase
     * order contains a collection of line items, then the following produces a
     * stream containing all the line items in all the orders:
     * <pre>{@code
     *     orders.flatMap(order -> order.getLineItems().stream())...
     * }</pre>
     *
     * <p>If {@code path} is the path to a file, then the following produces a
     * stream of the {@code words} contained in that file:
     * <pre>{@code
     *     Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
     *     Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
     * }</pre>
     * The {@code mapper} function passed to {@code flatMap} splits a line,
     * using a simple regular expression, into an array of words, and then
     * creates a stream of words from that array.
     *
     * <p>
     *  返回由将此流的每个元素替换为通过将提供的映射函数应用于每个元素而生成的映射流的内容的结果组成的流。
     * 每个映射流在其内容被放入此流之后是{@link java.util.stream.BaseStream#close()closed}。 (如果映射流为{@code null},则使用空流)。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     *  @apiNote {@code flatMap()}操作的效果是对流的元素应用一对多变换,然后将生成的元素展平为新流。
     * 
     *  <p> <b>示例。</b>
     * 
     *  <p>如果{@code订单}是采购订单流,并且每个采购订单都包含一组订单项,则以下内容会生成包含所有订单中所有订单项的流：<pre> {@ code orders .flatMap(order  - > order.getLineItems()。
     * stream())...} </pre>。
     * 
     * <p>如果{@code path}是文件的路径,那么以下将生成该文件中包含的{@code words}的流：<pre> {@ code Stream <String> lines = Files.lines path,StandardCharsets.UTF_8); Stream <String> words = lines.flatMap(line  - > Stream.of(line.split("+"))); }
     *  </pre>传递给{@code flatMap}的{@code mapper}函数使用简单的正则表达式将一行划分为单词数组,然后从该数组创建单词流。
     * 
     * 
     * @param <R> The element type of the new stream
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element which produces a stream
     *               of new values
     * @return the new stream
     */
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    /**
     * Returns an {@code IntStream} consisting of the results of replacing each
     * element of this stream with the contents of a mapped stream produced by
     * applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回一个{@code IntStream},其中包含将此流的每个元素替换为通过将提供的映射函数应用于每个元素而生成的映射流的内容的结果。
     * 每个映射流在其内容被放入此流之后是{@link java.util.stream.BaseStream#close()closed}。 (如果映射流为{@code null},则使用空流)。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element which produces a stream
     *               of new values
     * @return the new stream
     * @see #flatMap(Function)
     */
    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);

    /**
     * Returns an {@code LongStream} consisting of the results of replacing each
     * element of this stream with the contents of a mapped stream produced by
     * applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回一个{@code LongStream},其中包含将此流的每个元素替换为通过将提供的映射函数应用于每个元素而生成的映射流的内容的结果。
     * 每个映射流在其内容被放入此流之后是{@link java.util.stream.BaseStream#close()closed}。 (如果映射流为{@code null},则使用空流)。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element which produces a stream
     *               of new values
     * @return the new stream
     * @see #flatMap(Function)
     */
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);

    /**
     * Returns an {@code DoubleStream} consisting of the results of replacing
     * each element of this stream with the contents of a mapped stream produced
     * by applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have placed been into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     * 返回一个{@code DoubleStream},其中包含将此流的每个元素替换为通过将提供的映射函数应用于每个元素而生成的映射流的内容的结果。
     * 每个映射的流都是{@link java.util.stream.BaseStream#close()closed},它的内容已经放到这个流中。 (如果映射流为{@code null},则使用空流)。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *               <a href="package-summary.html#Statelessness">stateless</a>
     *               function to apply to each element which produces a stream
     *               of new values
     * @return the new stream
     * @see #flatMap(Function)
     */
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);

    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream.
     *
     * <p>For ordered streams, the selection of distinct elements is stable
     * (for duplicated elements, the element appearing first in the encounter
     * order is preserved.)  For unordered streams, no stability guarantees
     * are made.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * @apiNote
     * Preserving stability for {@code distinct()} in parallel pipelines is
     * relatively expensive (requires that the operation act as a full barrier,
     * with substantial buffering overhead), and stability is often not needed.
     * Using an unordered stream source (such as {@link #generate(Supplier)})
     * or removing the ordering constraint with {@link #unordered()} may result
     * in significantly more efficient execution for {@code distinct()} in parallel
     * pipelines, if the semantics of your situation permit.  If consistency
     * with encounter order is required, and you are experiencing poor performance
     * or memory utilization with {@code distinct()} in parallel pipelines,
     * switching to sequential execution with {@link #sequential()} may improve
     * performance.
     *
     * <p>
     *  返回由此流的不同元素(根据{@link Object#equals(Object)})组成的流。
     * 
     *  <p>对于有序流,对不同元素的选择是稳定的(对于重复的元素,保留在遇到顺序中首先出现的元素。)对于无序流,不进行稳定性保证。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * @apiNote保持并行管道中的{@code distinct()}的稳定性相对昂贵(要求操作充当完全屏障,具有大量缓冲开销),并且通常不需要稳定性。
     * 使用无序流源(例如{@link #generate(Supplier)})或使用{@link #unordered()}删除排序约束可能会导致平行管道中{@code distinct()}的执行效率明显
     * 更高,如果你的情况允许的语义。
     * @apiNote保持并行管道中的{@code distinct()}的稳定性相对昂贵(要求操作充当完全屏障,具有大量缓冲开销),并且通常不需要稳定性。
     * 如果需要与遇到顺序一致,并且在并行管道中使用{@code distinct()}时遇到性能或内存利用率较低,使用{@link #sequential()}切换到顺序执行可能会提高性能。
     * 
     * 
     * @return the new stream
     */
    Stream<T> distinct();

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to natural order.  If the elements of this stream are not
     * {@code Comparable}, a {@code java.lang.ClassCastException} may be thrown
     * when the terminal operation is executed.
     *
     * <p>For ordered streams, the sort is stable.  For unordered streams, no
     * stability guarantees are made.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * <p>
     *  返回由此流的元素组成的流,根据自然顺序排序。
     * 如果此流的元素不是{@code Comparable},则在执行终端操作时可能会抛出{@code java.lang.ClassCastException}。
     * 
     *  <p>对于有序流,排序是稳定的。对于无序流,不进行稳定性保证。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * 
     * @return the new stream
     */
    Stream<T> sorted();

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to the provided {@code Comparator}.
     *
     * <p>For ordered streams, the sort is stable.  For unordered streams, no
     * stability guarantees are made.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">stateful
     * intermediate operation</a>.
     *
     * <p>
     *  返回由此流的元素组成的流,根据提供的{@code Comparator}进行排序。
     * 
     *  <p>对于有序流,排序是稳定的。对于无序流,不进行稳定性保证。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">有状态中间操作</a>。
     * 
     * 
     * @param comparator a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                   <a href="package-summary.html#Statelessness">stateless</a>
     *                   {@code Comparator} to be used to compare stream elements
     * @return the new stream
     */
    Stream<T> sorted(Comparator<? super T> comparator);

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
     *     Stream.of("one", "two", "three", "four")
     *         .filter(e -> e.length() > 3)
     *         .peek(e -> System.out.println("Filtered value: " + e))
     *         .map(String::toUpperCase)
     *         .peek(e -> System.out.println("Mapped value: " + e))
     *         .collect(Collectors.toList());
     * }</pre>
     *
     * <p>
     * 返回由此流的元素组成的流,另外对每个元素执行提供的操作,因为元素从结果流中消耗。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     *  对于并行流管道,可以在任何时间调用动作,并且在任何线程中,元素被上游操作使得可用。如果操作修改共享状态,它负责提供所需的同步。
     * 
     *  @apiNote这个方法主要用于支持调试,当你想要看到的元素,当他们流经某个管道中的某个点：<pre> {@ code Stream.of("one","two","three" "four").filter(e  - > e.length()> 3).peek(e  - > System.out.println("Filtered value："+ e)).map(String :: toUpperCase).peek e  - > System.out.println("Mapped value："+ e)).collect(Collectors.toList()); }
     *  </pre>。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *                 non-interfering</a> action to perform on the elements as
     *                 they are consumed from the stream
     * @return the new stream
     */
    Stream<T> peek(Consumer<? super T> action);

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
     * stream source (such as {@link #generate(Supplier)}) or removing the
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
     * 使用无序流源(例如{@link #generate(Supplier)})或使用{@link #unordered()}删除排序约束可能会导致并行管道中的{@code limit()}显着加速,如果您的
     * 情况许可的语义。
     * 如果需要与遇到顺序一致,并且在并行管道中使用{@code limit()}时遇到性能或内存利用率较低,使用{@link #sequential()}切换到顺序执行可能会提高性能。
     * 
     * 
     * @param maxSize the number of elements the stream should be limited to
     * @return the new stream
     * @throws IllegalArgumentException if {@code maxSize} is negative
     */
    Stream<T> limit(long maxSize);

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
     * stream source (such as {@link #generate(Supplier)}) or removing the
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
     * 使用无序流源(例如{@link #generate(Supplier)})或使用{@link #unordered()}删除排序约束可能会导致并行流水线中的{@code skip()}大幅加速,如果您的
     * 情况许可的语义。
     * 如果需要与遇到顺序一致,并且在并行管道中使用{@code skip()}时遇到性能或内存利用率较低,使用{@link #sequential()}切换到顺序执行可能会提高性能。
     * 
     * 
     * @param n the number of leading elements to skip
     * @return the new stream
     * @throws IllegalArgumentException if {@code n} is negative
     */
    Stream<T> skip(long n);

    /**
     * Performs an action for each element of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>The behavior of this operation is explicitly nondeterministic.
     * For parallel stream pipelines, this operation does <em>not</em>
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
     *  <p>此操作的行为是明确不确定的。对于并行流流水线,该操作不会保证尊重流的遭遇顺序,因为这样做会牺牲并行性的好处。对于任何给定的元素,动作可以在任何时间和在库选择的任何线程中执行。
     * 如果操作访问共享状态,则它负责提供所需的同步。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements
     */
    void forEach(Consumer<? super T> action);

    /**
     * Performs an action for each element of this stream, in the encounter
     * order of the stream if the stream has a defined encounter order.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>This operation processes the elements one at a time, in encounter
     * order if one exists.  Performing the action for one element
     * <a href="../concurrent/package-summary.html#MemoryVisibility"><i>happens-before</i></a>
     * performing the action for subsequent elements, but for any given element,
     * the action may be performed in whatever thread the library chooses.
     *
     * <p>
     * 如果流具有定义的遭遇顺序,则以流的遭遇顺序对该流的每个元素执行动作。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  <p>此操作一次处理一个元素,如果存在,按遇到顺序处理元素。
     * 对一个元素执行操作<a href="../concurrent/package-summary.html#MemoryVisibility"> <i>发生在之前</i> </a>对后续元素执行操作,但对
     * 于任何给定的元素元素,动作可以在库选择的任何线程中执行。
     *  <p>此操作一次处理一个元素,如果存在,按遇到顺序处理元素。
     * 
     * 
     * @param action a <a href="package-summary.html#NonInterference">
     *               non-interfering</a> action to perform on the elements
     * @see #forEach(Consumer)
     */
    void forEachOrdered(Consumer<? super T> action);

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
    Object[] toArray();

    /**
     * Returns an array containing the elements of this stream, using the
     * provided {@code generator} function to allocate the returned array, as
     * well as any additional arrays that might be required for a partitioned
     * execution or for resizing.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote
     * The generator function takes an integer, which is the size of the
     * desired array, and produces an array of the desired size.  This can be
     * concisely expressed with an array constructor reference:
     * <pre>{@code
     *     Person[] men = people.stream()
     *                          .filter(p -> p.getGender() == MALE)
     *                          .toArray(Person[]::new);
     * }</pre>
     *
     * <p>
     *  使用提供的{@code generator}函数分配返回的数组以及分区执行或调整大小可能需要的任何其他数组,返回包含此流的元素的数组。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote生成器函数接受一个整数,它是所需数组的大小,并生成所需大小的数组。
     * 这可以简洁地用数组构造函数引用表示：<pre> {@ code Person [] men = people.stream().filter(p  - > p.getGender()== MALE).toArray新); }
     *  </pre>。
     *  @apiNote生成器函数接受一个整数,它是所需数组的大小,并生成所需大小的数组。
     * 
     * 
     * @param <A> the element type of the resulting array
     * @param generator a function which produces a new array of the desired
     *                  type and the provided length
     * @return an array containing the elements in this stream
     * @throws ArrayStoreException if the runtime type of the array returned
     * from the array generator is not a supertype of the runtime type of every
     * element in this stream
     */
    <A> A[] toArray(IntFunction<A[]> generator);

    /**
     * Performs a <a href="package-summary.html#Reduction">reduction</a> on the
     * elements of this stream, using the provided identity value and an
     * <a href="package-summary.html#Associativity">associative</a>
     * accumulation function, and returns the reduced value.  This is equivalent
     * to:
     * <pre>{@code
     *     T result = identity;
     *     for (T element : this stream)
     *         result = accumulator.apply(result, element)
     *     return result;
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     *
     * <p>The {@code identity} value must be an identity for the accumulator
     * function. This means that for all {@code t},
     * {@code accumulator.apply(identity, t)} is equal to {@code t}.
     * The {@code accumulator} function must be an
     * <a href="package-summary.html#Associativity">associative</a> function.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote Sum, min, max, average, and string concatenation are all special
     * cases of reduction. Summing a stream of numbers can be expressed as:
     *
     * <pre>{@code
     *     Integer sum = integers.reduce(0, (a, b) -> a+b);
     * }</pre>
     *
     * or:
     *
     * <pre>{@code
     *     Integer sum = integers.reduce(0, Integer::sum);
     * }</pre>
     *
     * <p>While this may seem a more roundabout way to perform an aggregation
     * compared to simply mutating a running total in a loop, reduction
     * operations parallelize more gracefully, without needing additional
     * synchronization and with greatly reduced risk of data races.
     *
     * <p>
     * 使用提供的身份值和<a href="package-summary.html#Associativity">关联关系对此流的元素执行<a href="package-summary.html#Reduction">
     * 缩小</a> </a>累积函数,并返回减小的值。
     * 这等效于：<pre> {@ code T result = identity; for(T element：this stream)result = accumulator.apply(result,element)return result; }
     *  </pre>。
     * 
     *  但不限于顺序执行。
     * 
     *  <p> {@code identity}值必须是累加器函数的标识。
     * 这意味着对于所有{@code t},{@code accumulator.apply(identity,t)}等于{@code t}。
     *  {@code accumum}函数必须是<a href="package-summary.html#Associativity">关联</a>函数。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote sum,min,max,average和string concatenation都是减少的特殊情况。对数字流求和可以表示为：
     * 
     *  <pre> {@ code Integer sum = integers.reduce(0,(a,b) - > a + b); } </pre>
     * 
     *  要么：
     * 
     *  <pre> {@ code Integer sum = integers.reduce(0,Integer :: sum); } </pre>
     * 
     *  与简单地在循环中改变运行总计相比,这可能似乎是一种更迂回的方式来执行聚合,但是减少操作更平缓地并行化,而不需要额外的同步并且大大降低数据竞争的风险。
     * 
     * 
     * @param identity the identity value for the accumulating function
     * @param accumulator an <a href="package-summary.html#Associativity">associative</a>,
     *                    <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                    <a href="package-summary.html#Statelessness">stateless</a>
     *                    function for combining two values
     * @return the result of the reduction
     */
    T reduce(T identity, BinaryOperator<T> accumulator);

    /**
     * Performs a <a href="package-summary.html#Reduction">reduction</a> on the
     * elements of this stream, using an
     * <a href="package-summary.html#Associativity">associative</a> accumulation
     * function, and returns an {@code Optional} describing the reduced value,
     * if any. This is equivalent to:
     * <pre>{@code
     *     boolean foundAny = false;
     *     T result = null;
     *     for (T element : this stream) {
     *         if (!foundAny) {
     *             foundAny = true;
     *             result = element;
     *         }
     *         else
     *             result = accumulator.apply(result, element);
     *     }
     *     return foundAny ? Optional.of(result) : Optional.empty();
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
     * 使用<a href="package-summary.html#Associativity">关联</a>累积对此流的元素执行<a href="package-summary.html#Reduction">
     * 缩小</a>函数,并返回描述缩减值的{@code Optional}(如果有)。
     * 这等效于：<pre> {@ code boolean foundAny = false; T result = null; for(T element：this stream){if(！foundAny){foundAny = true; result = element; }
     *  else result = accumulator.apply(result,element); } return foundAny? Optional.of(result)：Optional.emp
     * ty(); } </pre>。
     * 
     *  但不限于顺序执行。
     * 
     *  <p> {@code accumum}函数必须是<a href="package-summary.html#Associativity">关联</a>函数。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param accumulator an <a href="package-summary.html#Associativity">associative</a>,
     *                    <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                    <a href="package-summary.html#Statelessness">stateless</a>
     *                    function for combining two values
     * @return an {@link Optional} describing the result of the reduction
     * @throws NullPointerException if the result of the reduction is null
     * @see #reduce(Object, BinaryOperator)
     * @see #min(Comparator)
     * @see #max(Comparator)
     */
    Optional<T> reduce(BinaryOperator<T> accumulator);

    /**
     * Performs a <a href="package-summary.html#Reduction">reduction</a> on the
     * elements of this stream, using the provided identity, accumulation and
     * combining functions.  This is equivalent to:
     * <pre>{@code
     *     U result = identity;
     *     for (T element : this stream)
     *         result = accumulator.apply(result, element)
     *     return result;
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     *
     * <p>The {@code identity} value must be an identity for the combiner
     * function.  This means that for all {@code u}, {@code combiner(identity, u)}
     * is equal to {@code u}.  Additionally, the {@code combiner} function
     * must be compatible with the {@code accumulator} function; for all
     * {@code u} and {@code t}, the following must hold:
     * <pre>{@code
     *     combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)
     * }</pre>
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote Many reductions using this form can be represented more simply
     * by an explicit combination of {@code map} and {@code reduce} operations.
     * The {@code accumulator} function acts as a fused mapper and accumulator,
     * which can sometimes be more efficient than separate mapping and reduction,
     * such as when knowing the previously reduced value allows you to avoid
     * some computation.
     *
     * <p>
     *  使用提供的身份,累积和合并功能对此流的元素执行<a href="package-summary.html#Reduction">缩减</a>。
     * 这等效于：<pre> {@ code U result = identity; for(T element：this stream)result = accumulator.apply(result,element)return result; }
     *  </pre>。
     *  使用提供的身份,累积和合并功能对此流的元素执行<a href="package-summary.html#Reduction">缩减</a>。
     * 
     *  但不限于顺序执行。
     * 
     * <p> {@code identity}值必须是组合器函数的标识。这意味着对于所有{@code u},{@code combiner(identity,u)}等于{@code u}。
     * 此外,{@code combiner}函数必须与{@code accumum}函数兼容;对于所有{@code u}和{@code t},以下必须包含：<pre> {@ code combiner.apply(u,accumulator.apply(identity,t))== accumulator.apply(u,t) }
     *  </pre>。
     * <p> {@code identity}值必须是组合器函数的标识。这意味着对于所有{@code u},{@code combiner(identity,u)}等于{@code u}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote使用此形式的许多缩减可以通过{@code map}和{@code reduce}操作的显式组合更简单地表示。
     *  {@code accumulator}函数充当融合映射器和累加器,它有时比单独映射和缩减更有效,例如当知道先前减小的值允许您避免一些计算时。
     * 
     * 
     * @param <U> The type of the result
     * @param identity the identity value for the combiner function
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
     * @see #reduce(BinaryOperator)
     * @see #reduce(Object, BinaryOperator)
     */
    <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);

    /**
     * Performs a <a href="package-summary.html#MutableReduction">mutable
     * reduction</a> operation on the elements of this stream.  A mutable
     * reduction is one in which the reduced value is a mutable result container,
     * such as an {@code ArrayList}, and elements are incorporated by updating
     * the state of the result rather than by replacing the result.  This
     * produces a result equivalent to:
     * <pre>{@code
     *     R result = supplier.get();
     *     for (T element : this stream)
     *         accumulator.accept(result, element);
     *     return result;
     * }</pre>
     *
     * <p>Like {@link #reduce(Object, BinaryOperator)}, {@code collect} operations
     * can be parallelized without requiring additional synchronization.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * @apiNote There are many existing classes in the JDK whose signatures are
     * well-suited for use with method references as arguments to {@code collect()}.
     * For example, the following will accumulate strings into an {@code ArrayList}:
     * <pre>{@code
     *     List<String> asList = stringStream.collect(ArrayList::new, ArrayList::add,
     *                                                ArrayList::addAll);
     * }</pre>
     *
     * <p>The following will take a stream of strings and concatenates them into a
     * single string:
     * <pre>{@code
     *     String concat = stringStream.collect(StringBuilder::new, StringBuilder::append,
     *                                          StringBuilder::append)
     *                                 .toString();
     * }</pre>
     *
     * <p>
     *  对此流的元素执行<a href="package-summary.html#MutableReduction">可变缩减</a>操作。
     * 可变减少是其中减少的值是可变的结果容器,例如{@code ArrayList}的减少,并且通过更新结果的状态而不是通过替换结果来并入元素。
     * 这产生等效的结果：<pre> {@ code R result = supplier.get(); for(T element：this stream)accumulator.accept(result,element);返回结果; }
     *  </pre>。
     * 可变减少是其中减少的值是可变的结果容器,例如{@code ArrayList}的减少,并且通过更新结果的状态而不是通过替换结果来并入元素。
     * 
     * <p>与{@link #reduce(Object,BinaryOperator)}类似,{@code collect}操作可以并行化,而不需要额外的同步。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  @apiNote JDK中有许多现有的类,其签名非常适合与方法引用一起使用,作为{@code collect()}的参数。
     * 例如,以下将会将字符串累积到{@code ArrayList}中：<pre> {@ code List <String> asList = stringStream.collect(ArrayList :: new,ArrayList :: add,ArrayList :: addAll); }
     *  </pre>。
     *  @apiNote JDK中有许多现有的类,其签名非常适合与方法引用一起使用,作为{@code collect()}的参数。
     * 
     *  <p>下面将使用一串字符串,并将它们连接成一个字符串：<pre> {@ code String concat = stringStream.collect(StringBuilder :: new,StringBuilder :: append,StringBuilder :: append).toString ); }
     *  </pre>。
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
     */
    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);

    /**
     * Performs a <a href="package-summary.html#MutableReduction">mutable
     * reduction</a> operation on the elements of this stream using a
     * {@code Collector}.  A {@code Collector}
     * encapsulates the functions used as arguments to
     * {@link #collect(Supplier, BiConsumer, BiConsumer)}, allowing for reuse of
     * collection strategies and composition of collect operations such as
     * multiple-level grouping or partitioning.
     *
     * <p>If the stream is parallel, and the {@code Collector}
     * is {@link Collector.Characteristics#CONCURRENT concurrent}, and
     * either the stream is unordered or the collector is
     * {@link Collector.Characteristics#UNORDERED unordered},
     * then a concurrent reduction will be performed (see {@link Collector} for
     * details on concurrent reduction.)
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>When executed in parallel, multiple intermediate results may be
     * instantiated, populated, and merged so as to maintain isolation of
     * mutable data structures.  Therefore, even when executed in parallel
     * with non-thread-safe data structures (such as {@code ArrayList}), no
     * additional synchronization is needed for a parallel reduction.
     *
     * @apiNote
     * The following will accumulate strings into an ArrayList:
     * <pre>{@code
     *     List<String> asList = stringStream.collect(Collectors.toList());
     * }</pre>
     *
     * <p>The following will classify {@code Person} objects by city:
     * <pre>{@code
     *     Map<String, List<Person>> peopleByCity
     *         = personStream.collect(Collectors.groupingBy(Person::getCity));
     * }</pre>
     *
     * <p>The following will classify {@code Person} objects by state and city,
     * cascading two {@code Collector}s together:
     * <pre>{@code
     *     Map<String, Map<String, List<Person>>> peopleByStateAndCity
     *         = personStream.collect(Collectors.groupingBy(Person::getState,
     *                                                      Collectors.groupingBy(Person::getCity)));
     * }</pre>
     *
     * <p>
     *  使用{@code Collector}对此流的元素执行<a href="package-summary.html#MutableReduction">可变缩减</a>操作。
     *  {@code Collector}封装了用作{@link #collect(Supplier,BiConsumer,BiConsumer)}参数的函数,允许重用收集策略和组合收集操作,例如多级分组或分
     * 区。
     *  使用{@code Collector}对此流的元素执行<a href="package-summary.html#MutableReduction">可变缩减</a>操作。
     * 
     * <p>如果流是并行的,并且{@code Collector}是{@link Collector.Characteristics#CONCURRENT concurrent},并且流是无序的或收集器是{@link Collector.Characteristics#UNORDERED unordered}
     * ,则将执行并发还原(请参阅{@link Collector}有关并发还原的详细信息。
     * )。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     *  <p>当并行执行时,可以实例化,填充和合并多个中间结果,以便保持可变数据结构的隔离。因此,即使在与非线程安全数据结构(例如{@code ArrayList})并行执行时,并行还原不需要额外的同步。
     * 
     *  @apiNote以下将会将字符串累积到一个ArrayList中：<pre> {@ code List <String> asList = stringStream.collect(Collectors.toList()); }
     *  </pre>。
     * 
     *  <p>以下将按城市对{@code Person}对象进行分类：<pre> {@ code Map <String,List <Person >> peopleByCity = personStream.collect(Collectors.groupingBy(Person :: getCity)); }
     *  </pre>。
     * 
     *  <p>下面将按州和城市来分类{@code Person}对象,将两个{@code Collector}级联在一起：<pre> {@ code Map <String,Map <String,List <Person >>> peopleByStateAndCity = personStream.collect(Collectors.groupingBy(Person :: getState,Collectors.groupingBy(Person :: getCity))); }
     *  </pre>。
     * 
     * 
     * @param <R> the type of the result
     * @param <A> the intermediate accumulation type of the {@code Collector}
     * @param collector the {@code Collector} describing the reduction
     * @return the result of the reduction
     * @see #collect(Supplier, BiConsumer, BiConsumer)
     * @see Collectors
     */
    <R, A> R collect(Collector<? super T, A, R> collector);

    /**
     * Returns the minimum element of this stream according to the provided
     * {@code Comparator}.  This is a special case of a
     * <a href="package-summary.html#Reduction">reduction</a>.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal operation</a>.
     *
     * <p>
     * 根据提供的{@code Comparator}返回此流的最小元素。这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param comparator a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                   <a href="package-summary.html#Statelessness">stateless</a>
     *                   {@code Comparator} to compare elements of this stream
     * @return an {@code Optional} describing the minimum element of this stream,
     * or an empty {@code Optional} if the stream is empty
     * @throws NullPointerException if the minimum element is null
     */
    Optional<T> min(Comparator<? super T> comparator);

    /**
     * Returns the maximum element of this stream according to the provided
     * {@code Comparator}.  This is a special case of a
     * <a href="package-summary.html#Reduction">reduction</a>.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  根据提供的{@code Comparator}返回此流的最大元素。这是<a href="package-summary.html#Reduction">缩减</a>的特殊情况。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @param comparator a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                   <a href="package-summary.html#Statelessness">stateless</a>
     *                   {@code Comparator} to compare elements of this stream
     * @return an {@code Optional} describing the maximum element of this stream,
     * or an empty {@code Optional} if the stream is empty
     * @throws NullPointerException if the maximum element is null
     */
    Optional<T> max(Comparator<? super T> comparator);

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
    boolean anyMatch(Predicate<? super T> predicate);

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
     * 返回此流的所有元素是否与提供的谓词匹配。如果不是必需的,可以不评估所有元素的谓词以确定结果。如果流是空的,则返回{@code true},并且不评估谓词。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     *  @apiNote此方法评估流的元素(对于所有x P(x))的谓词的<em>通用量化</em>。如果流是空的,则量化被认为是真空满足的并且总是{@code true}(不考虑P(x))。
     * 
     * 
     * @param predicate a <a href="package-summary.html#NonInterference">non-interfering</a>,
     *                  <a href="package-summary.html#Statelessness">stateless</a>
     *                  predicate to apply to elements of this stream
     * @return {@code true} if either all elements of the stream match the
     * provided predicate or the stream is empty, otherwise {@code false}
     */
    boolean allMatch(Predicate<? super T> predicate);

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
    boolean noneMatch(Predicate<? super T> predicate);

    /**
     * Returns an {@link Optional} describing the first element of this stream,
     * or an empty {@code Optional} if the stream is empty.  If the stream has
     * no encounter order, then any element may be returned.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * terminal operation</a>.
     *
     * <p>
     *  返回描述此流的第一个元素的{@link可选},如果流为空,则返回空的{@code Optional}。如果流没有遇到顺序,则可以返回任何元素。
     * 
     * <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     * 
     * @return an {@code Optional} describing the first element of this stream,
     * or an empty {@code Optional} if the stream is empty
     * @throws NullPointerException if the element selected is null
     */
    Optional<T> findFirst();

    /**
     * Returns an {@link Optional} describing some element of the stream, or an
     * empty {@code Optional} if the stream is empty.
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
     *  返回描述流的一些元素的{@link可选},如果流为空,则返回空的{@code Optional}。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">短路终端操作</a>。
     * 
     *  <p>此操作的行为是明确不确定的;它可以自由地选择流中的任何元素。这是为了允许并行操作的最大性能;成本是同一源上的多个调用可能不会返回相同的结果。
     *  (如果需要稳定的结果,请改用{@link #findFirst()}。)。
     * 
     * 
     * @return an {@code Optional} describing some element of this stream, or an
     * empty {@code Optional} if the stream is empty
     * @throws NullPointerException if the element selected is null
     * @see #findFirst()
     */
    Optional<T> findAny();

    // Static factories

    /**
     * Returns a builder for a {@code Stream}.
     *
     * <p>
     *  返回{@code Stream}的构建器。
     * 
     * 
     * @param <T> type of elements
     * @return a stream builder
     */
    public static<T> Builder<T> builder() {
        return new Streams.StreamBuilderImpl<>();
    }

    /**
     * Returns an empty sequential {@code Stream}.
     *
     * <p>
     *  返回一个空的顺序{@code Stream}。
     * 
     * 
     * @param <T> the type of stream elements
     * @return an empty sequential stream
     */
    public static<T> Stream<T> empty() {
        return StreamSupport.stream(Spliterators.<T>emptySpliterator(), false);
    }

    /**
     * Returns a sequential {@code Stream} containing a single element.
     *
     * <p>
     *  返回包含单个元素的序列{@code Stream}。
     * 
     * 
     * @param t the single element
     * @param <T> the type of stream elements
     * @return a singleton sequential stream
     */
    public static<T> Stream<T> of(T t) {
        return StreamSupport.stream(new Streams.StreamBuilderImpl<>(t), false);
    }

    /**
     * Returns a sequential ordered stream whose elements are the specified values.
     *
     * <p>
     *  返回其元素为指定值的顺序有序流。
     * 
     * 
     * @param <T> the type of stream elements
     * @param values the elements of the new stream
     * @return the new stream
     */
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a stream from an array is safe
    public static<T> Stream<T> of(T... values) {
        return Arrays.stream(values);
    }

    /**
     * Returns an infinite sequential ordered {@code Stream} produced by iterative
     * application of a function {@code f} to an initial element {@code seed},
     * producing a {@code Stream} consisting of {@code seed}, {@code f(seed)},
     * {@code f(f(seed))}, etc.
     *
     * <p>The first element (position {@code 0}) in the {@code Stream} will be
     * the provided {@code seed}.  For {@code n > 0}, the element at position
     * {@code n}, will be the result of applying the function {@code f} to the
     * element at position {@code n - 1}.
     *
     * <p>
     *  返回通过将函数{@code f}迭代应用于初始元素{@code seed}产生的无限连续有序{@code Stream},产生由{@code seed},{@code} f(seed)},{@code f(f(seed))}
     * 等。
     * 
     *  <p> {@code Stream}中的第一个元素(位置{@code 0})将是提供的{@code seed}。
     * 对于{@code n> 0},位置{@code n}处的元素将是在位置{@code n  -  1}处的元素应用函数{@code f}的结果。
     * 
     * 
     * @param <T> the type of stream elements
     * @param seed the initial element
     * @param f a function to be applied to to the previous element to produce
     *          a new element
     * @return a new sequential {@code Stream}
     */
    public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) {
        Objects.requireNonNull(f);
        final Iterator<T> iterator = new Iterator<T>() {
            @SuppressWarnings("unchecked")
            T t = (T) Streams.NONE;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return t = (t == Streams.NONE) ? seed : f.apply(t);
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
    }

    /**
     * Returns an infinite sequential unordered stream where each element is
     * generated by the provided {@code Supplier}.  This is suitable for
     * generating constant streams, streams of random elements, etc.
     *
     * <p>
     * 返回无限连续无序流,其中每个元素都由提供的{@code Supplier}生成。这适用于生成恒定流,随机元素流等。
     * 
     * 
     * @param <T> the type of stream elements
     * @param s the {@code Supplier} of generated elements
     * @return a new infinite sequential unordered {@code Stream}
     */
    public static<T> Stream<T> generate(Supplier<T> s) {
        Objects.requireNonNull(s);
        return StreamSupport.stream(
                new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<>(Long.MAX_VALUE, s), false);
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
     *  创建一个延迟连接的流,其元素是第一个流的所有元素,后跟第二个流的所有元素。如果两个输入流都被排序,则产生的流被排序,并且如果任一输入流是并行的,则是并行的。
     * 当关闭结果流时,调用两个输入流的关闭处理程序。
     * 
     *  @implNote从重复连接构造流时要小心。访问深度并置的流的元素可能导致深度调用链,甚至是{@code StackOverflowException}。
     * 
     * 
     * @param <T> The type of stream elements
     * @param a the first stream
     * @param b the second stream
     * @return the concatenation of the two input streams
     */
    public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        @SuppressWarnings("unchecked")
        Spliterator<T> split = new Streams.ConcatSpliterator.OfRef<>(
                (Spliterator<T>) a.spliterator(), (Spliterator<T>) b.spliterator());
        Stream<T> stream = StreamSupport.stream(split, a.isParallel() || b.isParallel());
        return stream.onClose(Streams.composedClose(a, b));
    }

    /**
     * A mutable builder for a {@code Stream}.  This allows the creation of a
     * {@code Stream} by generating elements individually and adding them to the
     * {@code Builder} (without the copying overhead that comes from using
     * an {@code ArrayList} as a temporary buffer.)
     *
     * <p>A stream builder has a lifecycle, which starts in a building
     * phase, during which elements can be added, and then transitions to a built
     * phase, after which elements may not be added.  The built phase begins
     * when the {@link #build()} method is called, which creates an ordered
     * {@code Stream} whose elements are the elements that were added to the stream
     * builder, in the order they were added.
     *
     * <p>
     *  {@code Stream}的可变构建器。
     * 这允许通过单独生成元素并将它们添加到{@code Builder}(不使用{@code ArrayList}作为临时缓冲区的复制开销)来创建{@code Stream}。
     * 
     *  <p>流构建器具有生命周期,它始于构建阶段,在此阶段中可以添加元素,然后转换到构建阶段,之后可以不添加元素。
     * 构建阶段在调用{@link #build()}方法时开始,它创建了一个有序的{@code Stream},其元素是添加到流构建器中的元素,按添加顺序排列。
     * 
     * 
     * @param <T> the type of stream elements
     * @see Stream#builder()
     * @since 1.8
     */
    public interface Builder<T> extends Consumer<T> {

        /**
         * Adds an element to the stream being built.
         *
         * <p>
         *  向正在构建的流中添加一个元素。
         * 
         * 
         * @throws IllegalStateException if the builder has already transitioned to
         * the built state
         */
        @Override
        void accept(T t);

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
         * 向正在构建的流中添加一个元素。
         * 
         *  @implSpec默认实现的行为如下：<pre> {@ code accept(t)return this; } </pre>
         * 
         * 
         * @param t the element to add
         * @return {@code this} builder
         * @throws IllegalStateException if the builder has already transitioned to
         * the built state
         */
        default Builder<T> add(T t) {
            accept(t);
            return this;
        }

        /**
         * Builds the stream, transitioning this builder to the built state.
         * An {@code IllegalStateException} is thrown if there are further attempts
         * to operate on the builder after it has entered the built state.
         *
         * <p>
         * 
         * @return the built stream
         * @throws IllegalStateException if the builder has already transitioned to
         * the built state
         */
        Stream<T> build();

    }
}
