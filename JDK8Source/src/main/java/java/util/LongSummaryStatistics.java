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
package java.util;

import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.Collector;

/**
 * A state object for collecting statistics such as count, min, max, sum, and
 * average.
 *
 * <p>This class is designed to work with (though does not require)
 * {@linkplain java.util.stream streams}. For example, you can compute
 * summary statistics on a stream of longs with:
 * <pre> {@code
 * LongSummaryStatistics stats = longStream.collect(LongSummaryStatistics::new,
 *                                                  LongSummaryStatistics::accept,
 *                                                  LongSummaryStatistics::combine);
 * }</pre>
 *
 * <p>{@code LongSummaryStatistics} can be used as a
 * {@linkplain java.util.stream.Stream#collect(Collector)} reduction}
 * target for a {@linkplain java.util.stream.Stream stream}. For example:
 *
 * <pre> {@code
 * LongSummaryStatistics stats = people.stream()
 *                                     .collect(Collectors.summarizingLong(Person::getAge));
 *}</pre>
 *
 * This computes, in a single pass, the count of people, as well as the minimum,
 * maximum, sum, and average of their ages.
 *
 * @implNote This implementation is not thread safe. However, it is safe to use
 * {@link java.util.stream.Collectors#summarizingLong(java.util.function.ToLongFunction)
 * Collectors.toLongStatistics()} on a parallel stream, because the parallel
 * implementation of {@link java.util.stream.Stream#collect Stream.collect()}
 * provides the necessary partitioning, isolation, and merging of results for
 * safe and efficient parallel execution.
 *
 * <p>This implementation does not check for overflow of the sum.
 * <p>
 *  用于收集统计数据的状态对象,例如count,min,max,sum和average。
 * 
 *  <p>此类设计用于(虽然不需要){@linkplain java.util.stream streams}。
 * 例如,您可以通过以下方式计算longs流上的汇总统计信息：<pre> {@code LongSummaryStatistics stats = longStream.collect(LongSummaryStatistics :: new,LongSummaryStatistics :: accept,LongSummaryStatistics :: combine); }
 *  </pre>。
 *  <p>此类设计用于(虽然不需要){@linkplain java.util.stream streams}。
 * 
 *  <p> {@ code LongSummaryStatistics}可以用作{@linkplain java.util.stream.Stream stream}的{@linkplain java.util.stream.Stream#collect(Collector)}
 *  reduce}目标。
 * 例如：。
 * 
 *  <pre> {@code LongSummaryStatistics stats = people.stream().collect(Collectors.summarizingLong(Person :: getAge));。
 * </pre>
 * 
 *  这在单次通过中计算人的计数,以及他们的年龄的最小值,最大值,总和和平均值。
 * 
 *  @implNote这个实现不是线程安全的。
 * 但是,在并行流上使用{@link java.util.stream.Collectors#summarizingLong(java.util.function.ToLongFunction)Collectors.toLongStatistics()}
 * 是安全的,因为并行实现{@link java.util .stream.Stream#collect Stream.collect()}提供必要的分区,隔离和合并结果,以实现安全有效的并行执行。
 *  @implNote这个实现不是线程安全的。
 * 
 *  <p>此实现不检查和的溢出。
 * 
 * 
 * @since 1.8
 */
public class LongSummaryStatistics implements LongConsumer, IntConsumer {
    private long count;
    private long sum;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    /**
     * Construct an empty instance with zero count, zero sum,
     * {@code Long.MAX_VALUE} min, {@code Long.MIN_VALUE} max and zero
     * average.
     * <p>
     * 使用零计数,零和,{@code Long.MAX_VALUE}分钟,{@code Long.MIN_VALUE}最大和零平均值构造一个空实例。
     * 
     */
    public LongSummaryStatistics() { }

    /**
     * Records a new {@code int} value into the summary information.
     *
     * <p>
     *  在摘要信息中记录一个新的{@code int}值。
     * 
     * 
     * @param value the input value
     */
    @Override
    public void accept(int value) {
        accept((long) value);
    }

    /**
     * Records a new {@code long} value into the summary information.
     *
     * <p>
     *  在摘要信息中记录一个新的{@code long}值。
     * 
     * 
     * @param value the input value
     */
    @Override
    public void accept(long value) {
        ++count;
        sum += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    /**
     * Combines the state of another {@code LongSummaryStatistics} into this
     * one.
     *
     * <p>
     *  将另一个{@code LongSummaryStatistics}的状态合并到此。
     * 
     * 
     * @param other another {@code LongSummaryStatistics}
     * @throws NullPointerException if {@code other} is null
     */
    public void combine(LongSummaryStatistics other) {
        count += other.count;
        sum += other.sum;
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    /**
     * Returns the count of values recorded.
     *
     * <p>
     *  返回记录的值的计数。
     * 
     * 
     * @return the count of values
     */
    public final long getCount() {
        return count;
    }

    /**
     * Returns the sum of values recorded, or zero if no values have been
     * recorded.
     *
     * <p>
     *  返回记录的值的总和,如果未记录任何值,则返回零。
     * 
     * 
     * @return the sum of values, or zero if none
     */
    public final long getSum() {
        return sum;
    }

    /**
     * Returns the minimum value recorded, or {@code Long.MAX_VALUE} if no
     * values have been recorded.
     *
     * <p>
     *  返回记录的最小值,如果未记录任何值,则返回{@code Long.MAX_VALUE}。
     * 
     * 
     * @return the minimum value, or {@code Long.MAX_VALUE} if none
     */
    public final long getMin() {
        return min;
    }

    /**
     * Returns the maximum value recorded, or {@code Long.MIN_VALUE} if no
     * values have been recorded
     *
     * <p>
     *  返回记录的最大值,如果未记录任何值,则返回{@code Long.MIN_VALUE}
     * 
     * 
     * @return the maximum value, or {@code Long.MIN_VALUE} if none
     */
    public final long getMax() {
        return max;
    }

    /**
     * Returns the arithmetic mean of values recorded, or zero if no values have been
     * recorded.
     *
     * <p>
     *  返回记录的值的算术平均值,如果未记录任何值,则返回零。
     * 
     * 
     * @return The arithmetic mean of values, or zero if none
     */
    public final double getAverage() {
        return getCount() > 0 ? (double) getSum() / getCount() : 0.0d;
    }

    @Override
    /**
     * {@inheritDoc}
     *
     * Returns a non-empty string representation of this object suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     * <p>
     *  {@inheritDoc}
     * 
     */
    public String toString() {
        return String.format(
            "%s{count=%d, sum=%d, min=%d, average=%f, max=%d}",
            this.getClass().getSimpleName(),
            getCount(),
            getSum(),
            getMin(),
            getAverage(),
            getMax());
    }
}
