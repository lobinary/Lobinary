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
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * Base interface for streams, which are sequences of elements supporting
 * sequential and parallel aggregate operations.  The following example
 * illustrates an aggregate operation using the stream types {@link Stream}
 * and {@link IntStream}, computing the sum of the weights of the red widgets:
 *
 * <pre>{@code
 *     int sum = widgets.stream()
 *                      .filter(w -> w.getColor() == RED)
 *                      .mapToInt(w -> w.getWeight())
 *                      .sum();
 * }</pre>
 *
 * See the class documentation for {@link Stream} and the package documentation
 * for <a href="package-summary.html">java.util.stream</a> for additional
 * specification of streams, stream operations, stream pipelines, and
 * parallelism, which governs the behavior of all stream types.
 *
 * <p>
 *  流的基本接口,它是支持顺序和并行聚合操作的元素序列。以下示例说明使用流类型{@link Stream}和{@link IntStream}的聚合操作,计算红色小部件的权重总和：
 * 
 *  <pre> {@ code int sum = widgets.stream().filter(w  - > w.getColor()== RED).mapToInt(w  - > w.getWeight()).sum }
 *  </pre>。
 * 
 *  请参阅{@link Stream}的类文档和<a href="package-summary.html"> java.util.stream </a>的软件包文档,了解有关流,流操作,流管道和并行性的
 * 其他规范,它控制所有流类型的行为。
 * 
 * 
 * @param <T> the type of the stream elements
 * @param <S> the type of of the stream implementing {@code BaseStream}
 * @since 1.8
 * @see Stream
 * @see IntStream
 * @see LongStream
 * @see DoubleStream
 * @see <a href="package-summary.html">java.util.stream</a>
 */
public interface BaseStream<T, S extends BaseStream<T, S>>
        extends AutoCloseable {
    /**
     * Returns an iterator for the elements of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  返回此流的元素的迭代器。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return the element iterator for this stream
     */
    Iterator<T> iterator();

    /**
     * Returns a spliterator for the elements of this stream.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">terminal
     * operation</a>.
     *
     * <p>
     *  返回此流的元素的分隔符。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">终端操作</a>。
     * 
     * 
     * @return the element spliterator for this stream
     */
    Spliterator<T> spliterator();

    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * <p>
     *  返回此流(如果要执行终端操作)是否将并行执行。在调用终端流操作方法后调用此方法可能会产生不可预测的结果。
     * 
     * 
     * @return {@code true} if this stream would execute in parallel if executed
     */
    boolean isParallel();

    /**
     * Returns an equivalent stream that is sequential.  May return
     * itself, either because the stream was already sequential, or because
     * the underlying stream state was modified to be sequential.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回顺序的等效流。可能返回自己,因为流已经是顺序的,或者因为底层流状态被修改为顺序。
     * 
     * <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @return a sequential stream
     */
    S sequential();

    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回与之并行的等效流。可能返回自身,或者因为流已经是并行的,或者因为底层流状态被修改为并行。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @return a parallel stream
     */
    S parallel();

    /**
     * Returns an equivalent stream that is
     * <a href="package-summary.html#Ordering">unordered</a>.  May return
     * itself, either because the stream was already unordered, or because
     * the underlying stream state was modified to be unordered.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回<a href="package-summary.html#Ordering">无序</a>的等效流。可能返回自己,因为流已经是无序的,或者因为底层流状态被修改为无序。
     * 
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @return an unordered stream
     */
    S unordered();

    /**
     * Returns an equivalent stream with an additional close handler.  Close
     * handlers are run when the {@link #close()} method
     * is called on the stream, and are executed in the order they were
     * added.  All close handlers are run, even if earlier close handlers throw
     * exceptions.  If any close handler throws an exception, the first
     * exception thrown will be relayed to the caller of {@code close()}, with
     * any remaining exceptions added to that exception as suppressed exceptions
     * (unless one of the remaining exceptions is the same exception as the
     * first exception, since an exception cannot suppress itself.)  May
     * return itself.
     *
     * <p>This is an <a href="package-summary.html#StreamOps">intermediate
     * operation</a>.
     *
     * <p>
     *  返回具有额外关闭处理程序的等效流。当在流上调用{@link #close()}方法时,将运行关闭处理程序,并按添加的顺序执行。所有关闭处理程序都运行,即使较早的关闭处理程序抛出异常。
     * 如果任何close处理程序抛出异常,抛出的第一个异常将被转发给{@code close()}的调用者,任何剩余的异常作为抑制异常添加到该异常中(除非剩下的异常之一是相同的异常第一个异常,因为异常不能抑制
     * 自身。
     *  返回具有额外关闭处理程序的等效流。当在流上调用{@link #close()}方法时,将运行关闭处理程序,并按添加的顺序执行。所有关闭处理程序都运行,即使较早的关闭处理程序抛出异常。)可能返回自身。
     * 
     * 
     * @param closeHandler A task to execute when the stream is closed
     * @return a stream with a handler that is run if the stream is closed
     */
    S onClose(Runnable closeHandler);

    /**
     * Closes this stream, causing all close handlers for this stream pipeline
     * to be called.
     *
     * <p>
     *  <p>这是<a href="package-summary.html#StreamOps">中间操作</a>。
     * 
     * 
     * @see AutoCloseable#close()
     */
    @Override
    void close();
}
