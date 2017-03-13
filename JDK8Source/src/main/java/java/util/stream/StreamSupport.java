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

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;

/**
 * Low-level utility methods for creating and manipulating streams.
 *
 * <p>This class is mostly for library writers presenting stream views
 * of data structures; most static stream methods intended for end users are in
 * the various {@code Stream} classes.
 *
 * <p>
 *  用于创建和操作流的低级实用程序方法
 * 
 *  <p>此类主要用于呈现数据结构的流视图的库编写器;旨在面向最终用户的大多数静态流方法在各种{@code Stream}类中
 * 
 * 
 * @since 1.8
 */
public final class StreamSupport {

    // Suppresses default constructor, ensuring non-instantiability.
    private StreamSupport() {}

    /**
     * Creates a new sequential or parallel {@code Stream} from a
     * {@code Spliterator}.
     *
     * <p>The spliterator is only traversed, split, or queried for estimated
     * size after the terminal operation of the stream pipeline commences.
     *
     * <p>It is strongly recommended the spliterator report a characteristic of
     * {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <a href="../Spliterator.html#binding">late-binding</a>.  Otherwise,
     * {@link #stream(java.util.function.Supplier, int, boolean)} should be used
     * to reduce the scope of potential interference with the source.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code Spliterator}创建新的顺序或并行{@code Stream}
     * 
     *  <p>在流管道的终端操作开始之后,分裂器仅被遍历,分割或查询估计的大小
     * 
     * <p>强烈建议Spliterator报告{@code IMMUTABLE}或{@code CONCURRENT}的特性,或<a href=\"/Spliteratorhtml#binding\">延迟绑
     * 定</a>否则,{@link #stream(javautilfunctionSupplier,int,boolean)}可用于减少源的潜在干扰范围有关详细信息,请参见<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param <T> the type of stream elements
     * @param spliterator a {@code Spliterator} describing the stream elements
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code Stream}
     */
    public static <T> Stream<T> stream(Spliterator<T> spliterator, boolean parallel) {
        Objects.requireNonNull(spliterator);
        return new ReferencePipeline.Head<>(spliterator,
                                            StreamOpFlag.fromCharacteristics(spliterator),
                                            parallel);
    }

    /**
     * Creates a new sequential or parallel {@code Stream} from a
     * {@code Supplier} of {@code Spliterator}.
     *
     * <p>The {@link Supplier#get()} method will be invoked on the supplier no
     * more than once, and only after the terminal operation of the stream pipeline
     * commences.
     *
     * <p>For spliterators that report a characteristic of {@code IMMUTABLE}
     * or {@code CONCURRENT}, or that are
     * <a href="../Spliterator.html#binding">late-binding</a>, it is likely
     * more efficient to use {@link #stream(java.util.Spliterator, boolean)}
     * instead.
     * <p>The use of a {@code Supplier} in this form provides a level of
     * indirection that reduces the scope of potential interference with the
     * source.  Since the supplier is only invoked after the terminal operation
     * commences, any modifications to the source up to the start of the
     * terminal operation are reflected in the stream result.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code Spliterator}的{@code供应商}创建新的顺序或并行{@code Stream}
     * 
     *  <p> {@link Supplier#get()}方法将在供应商上调用不超过一次,并且只有在流管道的终端操作开始之后
     * 
     * <p>对于报告{@code IMMUTABLE}或{@code CONCURRENT}的特征或者是<a href=\"/Spliteratorhtml#binding\">延迟绑定</a>的分隔符,可能
     * 更有效请使用{@link #stream(javautilSpliterator,boolean)}代替<p>在此形式中使用{@code Supplier}可提供一个间接级别,从而减少潜在干扰源的范围。
     * 由于供应商仅在终端操作开始,对终端操作开始之前对源的任何修改都反映在流结果中有关更多详细信息,请参见<a href=\"package-summaryhtml#NonInterference\">无干扰
     * </a>。
     * 
     * 
     * @param <T> the type of stream elements
     * @param supplier a {@code Supplier} of a {@code Spliterator}
     * @param characteristics Spliterator characteristics of the supplied
     *        {@code Spliterator}.  The characteristics must be equal to
     *        {@code supplier.get().characteristics()}, otherwise undefined
     *        behavior may occur when terminal operation commences.
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code Stream}
     * @see #stream(java.util.Spliterator, boolean)
     */
    public static <T> Stream<T> stream(Supplier<? extends Spliterator<T>> supplier,
                                       int characteristics,
                                       boolean parallel) {
        Objects.requireNonNull(supplier);
        return new ReferencePipeline.Head<>(supplier,
                                            StreamOpFlag.fromCharacteristics(characteristics),
                                            parallel);
    }

    /**
     * Creates a new sequential or parallel {@code IntStream} from a
     * {@code Spliterator.OfInt}.
     *
     * <p>The spliterator is only traversed, split, or queried for estimated size
     * after the terminal operation of the stream pipeline commences.
     *
     * <p>It is strongly recommended the spliterator report a characteristic of
     * {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <a href="../Spliterator.html#binding">late-binding</a>.  Otherwise,
     * {@link #intStream(java.util.function.Supplier, int, boolean)} should be
     * used to reduce the scope of potential interference with the source.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SpliteratorOfInt}创建新的顺序或并行{@code IntStream}
     * 
     * <p>在流管道的终端操作开始之后,分裂器仅被遍历,分割或查询估计的大小
     * 
     *  <p>强烈建议Spliterator报告{@code IMMUTABLE}或{@code CONCURRENT}的特性,或<a href=\"/Spliteratorhtml#binding\">延迟
     * 绑定</a>否则,{@link #intStream(javautilfunctionSupplier,int,boolean)}可用于缩小与来源的潜在干扰范围有关详情,请参阅<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param spliterator a {@code Spliterator.OfInt} describing the stream elements
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code IntStream}
     */
    public static IntStream intStream(Spliterator.OfInt spliterator, boolean parallel) {
        return new IntPipeline.Head<>(spliterator,
                                      StreamOpFlag.fromCharacteristics(spliterator),
                                      parallel);
    }

    /**
     * Creates a new sequential or parallel {@code IntStream} from a
     * {@code Supplier} of {@code Spliterator.OfInt}.
     *
     * <p>The {@link Supplier#get()} method will be invoked on the supplier no
     * more than once, and only after the terminal operation of the stream pipeline
     * commences.
     *
     * <p>For spliterators that report a characteristic of {@code IMMUTABLE}
     * or {@code CONCURRENT}, or that are
     * <a href="../Spliterator.html#binding">late-binding</a>, it is likely
     * more efficient to use {@link #intStream(java.util.Spliterator.OfInt, boolean)}
     * instead.
     * <p>The use of a {@code Supplier} in this form provides a level of
     * indirection that reduces the scope of potential interference with the
     * source.  Since the supplier is only invoked after the terminal operation
     * commences, any modifications to the source up to the start of the
     * terminal operation are reflected in the stream result.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SpliteratorOfInt}的{@code Supplier}创建新的顺序或并行{@code IntStream}
     * 
     * <p> {@link Supplier#get()}方法将在供应商上调用不超过一次,并且只有在流管道的终端操作开始之后
     * 
     * <p>对于报告{@code IMMUTABLE}或{@code CONCURRENT}的特征或者是<a href=\"/Spliteratorhtml#binding\">延迟绑定</a>的分隔符,可能
     * 更有效请使用{@link #intStream(javautilSpliteratorOfInt,boolean)}而不是<p>在此形式中使用{@code Supplier}提供了一个间接级别,可以减少
     * 对源的潜在干扰的范围由于供应商仅在终端操作开始,对终端操作开始时对源的任何修改都会反映在流结果中有关详细信息,请参见<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param supplier a {@code Supplier} of a {@code Spliterator.OfInt}
     * @param characteristics Spliterator characteristics of the supplied
     *        {@code Spliterator.OfInt}.  The characteristics must be equal to
     *        {@code supplier.get().characteristics()}, otherwise undefined
     *        behavior may occur when terminal operation commences.
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code IntStream}
     * @see #intStream(java.util.Spliterator.OfInt, boolean)
     */
    public static IntStream intStream(Supplier<? extends Spliterator.OfInt> supplier,
                                      int characteristics,
                                      boolean parallel) {
        return new IntPipeline.Head<>(supplier,
                                      StreamOpFlag.fromCharacteristics(characteristics),
                                      parallel);
    }

    /**
     * Creates a new sequential or parallel {@code LongStream} from a
     * {@code Spliterator.OfLong}.
     *
     * <p>The spliterator is only traversed, split, or queried for estimated
     * size after the terminal operation of the stream pipeline commences.
     *
     * <p>It is strongly recommended the spliterator report a characteristic of
     * {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <a href="../Spliterator.html#binding">late-binding</a>.  Otherwise,
     * {@link #longStream(java.util.function.Supplier, int, boolean)} should be
     * used to reduce the scope of potential interference with the source.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SpliteratorOfLong}创建新的顺序或并行{@code LongStream}
     * 
     * <p>在流管道的终端操作开始之后,分裂器仅被遍历,分割或查询估计的大小
     * 
     *  <p>强烈建议Spliterator报告{@code IMMUTABLE}或{@code CONCURRENT}的特性,或<a href=\"/Spliteratorhtml#binding\">延迟
     * 绑定</a>否则,{@link #longStream(javautilfunctionSupplier,int,boolean)}可用于缩小对源的潜在干扰范围有关详细信息,请参见<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param spliterator a {@code Spliterator.OfLong} describing the stream elements
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code LongStream}
     */
    public static LongStream longStream(Spliterator.OfLong spliterator,
                                        boolean parallel) {
        return new LongPipeline.Head<>(spliterator,
                                       StreamOpFlag.fromCharacteristics(spliterator),
                                       parallel);
    }

    /**
     * Creates a new sequential or parallel {@code LongStream} from a
     * {@code Supplier} of {@code Spliterator.OfLong}.
     *
     * <p>The {@link Supplier#get()} method will be invoked on the supplier no
     * more than once, and only after the terminal operation of the stream pipeline
     * commences.
     *
     * <p>For spliterators that report a characteristic of {@code IMMUTABLE}
     * or {@code CONCURRENT}, or that are
     * <a href="../Spliterator.html#binding">late-binding</a>, it is likely
     * more efficient to use {@link #longStream(java.util.Spliterator.OfLong, boolean)}
     * instead.
     * <p>The use of a {@code Supplier} in this form provides a level of
     * indirection that reduces the scope of potential interference with the
     * source.  Since the supplier is only invoked after the terminal operation
     * commences, any modifications to the source up to the start of the
     * terminal operation are reflected in the stream result.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SpliteratorOfLong}的{@code供应商}创建新的顺序或并行{@code LongStream}
     * 
     * <p> {@link Supplier#get()}方法将在供应商上调用不超过一次,并且只有在流管道的终端操作开始之后
     * 
     * <p>对于报告{@code IMMUTABLE}或{@code CONCURRENT}的特征或者是<a href=\"/Spliteratorhtml#binding\">延迟绑定</a>的分隔符,可能
     * 更有效请使用{@link #longStream(javautilSpliteratorOfLong,boolean)}而不是<p>在此表单中使用{@code Supplier}提供了一个间接级别,可以
     * 减少潜在干扰源的范围。
     * 因为供应商之后才被调用终端操作开始,对终端操作开始之前对源的任何修改都反映在流结果中有关更多详细信息,请参见<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param supplier a {@code Supplier} of a {@code Spliterator.OfLong}
     * @param characteristics Spliterator characteristics of the supplied
     *        {@code Spliterator.OfLong}.  The characteristics must be equal to
     *        {@code supplier.get().characteristics()}, otherwise undefined
     *        behavior may occur when terminal operation commences.
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code LongStream}
     * @see #longStream(java.util.Spliterator.OfLong, boolean)
     */
    public static LongStream longStream(Supplier<? extends Spliterator.OfLong> supplier,
                                        int characteristics,
                                        boolean parallel) {
        return new LongPipeline.Head<>(supplier,
                                       StreamOpFlag.fromCharacteristics(characteristics),
                                       parallel);
    }

    /**
     * Creates a new sequential or parallel {@code DoubleStream} from a
     * {@code Spliterator.OfDouble}.
     *
     * <p>The spliterator is only traversed, split, or queried for estimated size
     * after the terminal operation of the stream pipeline commences.
     *
     * <p>It is strongly recommended the spliterator report a characteristic of
     * {@code IMMUTABLE} or {@code CONCURRENT}, or be
     * <a href="../Spliterator.html#binding">late-binding</a>.  Otherwise,
     * {@link #doubleStream(java.util.function.Supplier, int, boolean)} should
     * be used to reduce the scope of potential interference with the source.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SpliteratorOfDouble}创建新的顺序或并行{@code DoubleStream}
     * 
     * <p>在流管道的终端操作开始之后,分裂器仅被遍历,分割或查询估计的大小
     * 
     *  <p>强烈建议Spliterator报告{@code IMMUTABLE}或{@code CONCURRENT}的特性,或<a href=\"/Spliteratorhtml#binding\">延迟
     * 绑定</a>否则,{@link #doubleStream(javautilfunctionSupplier,int,boolean)}可用于缩小与来源的潜在干扰范围有关详情,请参阅<a href=\"package-summaryhtml#NonInterference\">
     * 无干扰</a>。
     * 
     * 
     * @param spliterator A {@code Spliterator.OfDouble} describing the stream elements
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code DoubleStream}
     */
    public static DoubleStream doubleStream(Spliterator.OfDouble spliterator,
                                            boolean parallel) {
        return new DoublePipeline.Head<>(spliterator,
                                         StreamOpFlag.fromCharacteristics(spliterator),
                                         parallel);
    }

    /**
     * Creates a new sequential or parallel {@code DoubleStream} from a
     * {@code Supplier} of {@code Spliterator.OfDouble}.
     *
     * <p>The {@link Supplier#get()} method will be invoked on the supplier no
     * more than once, and only after the terminal operation of the stream pipeline
     * commences.
     *
     * <p>For spliterators that report a characteristic of {@code IMMUTABLE}
     * or {@code CONCURRENT}, or that are
     * <a href="../Spliterator.html#binding">late-binding</a>, it is likely
     * more efficient to use {@link #doubleStream(java.util.Spliterator.OfDouble, boolean)}
     * instead.
     * <p>The use of a {@code Supplier} in this form provides a level of
     * indirection that reduces the scope of potential interference with the
     * source.  Since the supplier is only invoked after the terminal operation
     * commences, any modifications to the source up to the start of the
     * terminal operation are reflected in the stream result.  See
     * <a href="package-summary.html#NonInterference">Non-Interference</a> for
     * more details.
     *
     * <p>
     *  从{@code SplitatorOfDouble}的{@code Supplier}创建新的顺序或平行{@code DoubleStream}
     * 
     * <p> {@link Supplier#get()}方法将在供应商上调用不超过一次,并且只有在流管道的终端操作开始之后
     * 
     * <p>对于报告{@code IMMUTABLE}或{@code CONCURRENT}的特征或者是<a href=\"/Spliteratorhtml#binding\">延迟绑定</a>的分隔符,可能
     * 
     * @param supplier A {@code Supplier} of a {@code Spliterator.OfDouble}
     * @param characteristics Spliterator characteristics of the supplied
     *        {@code Spliterator.OfDouble}.  The characteristics must be equal to
     *        {@code supplier.get().characteristics()}, otherwise undefined
     *        behavior may occur when terminal operation commences.
     * @param parallel if {@code true} then the returned stream is a parallel
     *        stream; if {@code false} the returned stream is a sequential
     *        stream.
     * @return a new sequential or parallel {@code DoubleStream}
     * @see #doubleStream(java.util.Spliterator.OfDouble, boolean)
     */
    public static DoubleStream doubleStream(Supplier<? extends Spliterator.OfDouble> supplier,
                                            int characteristics,
                                            boolean parallel) {
        return new DoublePipeline.Head<>(supplier,
                                         StreamOpFlag.fromCharacteristics(characteristics),
                                         parallel);
    }
}
