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

import java.util.function.DoubleConsumer;
import java.util.stream.Collector;

/**
 * A state object for collecting statistics such as count, min, max, sum, and
 * average.
 *
 * <p>This class is designed to work with (though does not require)
 * {@linkplain java.util.stream streams}. For example, you can compute
 * summary statistics on a stream of doubles with:
 * <pre> {@code
 * DoubleSummaryStatistics stats = doubleStream.collect(DoubleSummaryStatistics::new,
 *                                                      DoubleSummaryStatistics::accept,
 *                                                      DoubleSummaryStatistics::combine);
 * }</pre>
 *
 * <p>{@code DoubleSummaryStatistics} can be used as a
 * {@linkplain java.util.stream.Stream#collect(Collector) reduction}
 * target for a {@linkplain java.util.stream.Stream stream}. For example:
 *
 * <pre> {@code
 * DoubleSummaryStatistics stats = people.stream()
 *     .collect(Collectors.summarizingDouble(Person::getWeight));
 *}</pre>
 *
 * This computes, in a single pass, the count of people, as well as the minimum,
 * maximum, sum, and average of their weights.
 *
 * @implNote This implementation is not thread safe. However, it is safe to use
 * {@link java.util.stream.Collectors#summarizingDouble(java.util.function.ToDoubleFunction)
 * Collectors.toDoubleStatistics()} on a parallel stream, because the parallel
 * implementation of {@link java.util.stream.Stream#collect Stream.collect()}
 * provides the necessary partitioning, isolation, and merging of results for
 * safe and efficient parallel execution.
 * <p>
 *  用于收集统计数据的状态对象,例如count,min,max,sum和average。
 * 
 *  <p>此类设计用于(虽然不需要){@linkplain java.util.stream streams}。
 * 例如,您可以使用以下方法计算双精度流的汇总统计信息：<pre> {@code DoubleSummaryStatistics stats = doubleStream.collect(DoubleSummaryStatistics :: new,DoubleSummaryStatistics :: accept,DoubleSummaryStatistics :: combine); }
 *  </pre>。
 *  <p>此类设计用于(虽然不需要){@linkplain java.util.stream streams}。
 * 
 *  <p> {@ code DoubleSummaryStatistics}可用作{@linkplain java.util.stream.Stream stream}的{@linkplain java.util.stream.Stream#collect(Collector)reduction}
 * 目标。
 * 例如：。
 * 
 *  <pre> {@code DoubleSummaryStatistics stats = people.stream().collect(Collectors.summarizingDouble(Person :: getWeight));。
 * </pre>
 * 
 *  这在单次通过中计算人的计数,以及它们的权重的最小值,最大值,总和和平均值。
 * 
 *  @implNote这个实现不是线程安全的。
 * 但是,在并行流上使用{@link java.util.stream.Collectors#summarizingDouble(java.util.function.ToDoubleFunction)Collectors.toDoubleStatistics()}
 * 是安全的,因为并行实现{@link java.util .stream.Stream#collect Stream.collect()}提供必要的分区,隔离和合并结果,以实现安全有效的并行执行。
 *  @implNote这个实现不是线程安全的。
 * 
 * 
 * @since 1.8
 */
public class DoubleSummaryStatistics implements DoubleConsumer {
    private long count;
    private double sum;
    private double sumCompensation; // Low order bits of sum
    private double simpleSum; // Used to compute right sum for non-finite inputs
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    /**
     * Construct an empty instance with zero count, zero sum,
     * {@code Double.POSITIVE_INFINITY} min, {@code Double.NEGATIVE_INFINITY}
     * max and zero average.
     * <p>
     * 使用零计数,零和,{@code Double.POSITIVE_INFINITY} min,{@code Double.NEGATIVE_INFINITY}最大和零平均值构造一个空实例。
     * 
     */
    public DoubleSummaryStatistics() { }

    /**
     * Records another value into the summary information.
     *
     * <p>
     *  在摘要信息中记录另一个值。
     * 
     * 
     * @param value the input value
     */
    @Override
    public void accept(double value) {
        ++count;
        simpleSum += value;
        sumWithCompensation(value);
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    /**
     * Combines the state of another {@code DoubleSummaryStatistics} into this
     * one.
     *
     * <p>
     *  将另一个{@code DoubleSummaryStatistics}的状态合并到此状态。
     * 
     * 
     * @param other another {@code DoubleSummaryStatistics}
     * @throws NullPointerException if {@code other} is null
     */
    public void combine(DoubleSummaryStatistics other) {
        count += other.count;
        simpleSum += other.simpleSum;
        sumWithCompensation(other.sum);
        sumWithCompensation(other.sumCompensation);
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    /**
     * Incorporate a new double value using Kahan summation /
     * compensated summation.
     * <p>
     *  使用Kahan求和/补偿求和合并新的双精度值。
     * 
     */
    private void sumWithCompensation(double value) {
        double tmp = value - sumCompensation;
        double velvel = sum + tmp; // Little wolf of rounding error
        sumCompensation = (velvel - sum) - tmp;
        sum = velvel;
    }

    /**
     * Return the count of values recorded.
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
     * If any recorded value is a NaN or the sum is at any point a NaN
     * then the sum will be NaN.
     *
     * <p> The value of a floating-point sum is a function both of the
     * input values as well as the order of addition operations. The
     * order of addition operations of this method is intentionally
     * not defined to allow for implementation flexibility to improve
     * the speed and accuracy of the computed result.
     *
     * In particular, this method may be implemented using compensated
     * summation or other technique to reduce the error bound in the
     * numerical sum compared to a simple summation of {@code double}
     * values.
     *
     * @apiNote Values sorted by increasing absolute magnitude tend to yield
     * more accurate results.
     *
     * <p>
     *  返回记录的值的总和,如果未记录任何值,则返回零。
     * 
     *  如果任何记录值是NaN或总和在任何点NaN,则总和将是NaN。
     * 
     *  <p>浮点和的值是输入值以及加法运算的顺序的函数。该方法的加法运算的顺序被有意地定义为不允许实现灵活性以提高计算结果的速度和精度。
     * 
     *  特别地,该方法可以使用补偿求和或其他技术来实现,以减少与{@code double}值的简单求和相比的数值求和中的误差界限。
     * 
     *  @apiNote通过增加绝对值排序的值往往产生更准确的结果。
     * 
     * 
     * @return the sum of values, or zero if none
     */
    public final double getSum() {
        // Better error bounds to add both terms as the final sum
        double tmp =  sum + sumCompensation;
        if (Double.isNaN(tmp) && Double.isInfinite(simpleSum))
            // If the compensated sum is spuriously NaN from
            // accumulating one or more same-signed infinite values,
            // return the correctly-signed infinity stored in
            // simpleSum.
            return simpleSum;
        else
            return tmp;
    }

    /**
     * Returns the minimum recorded value, {@code Double.NaN} if any recorded
     * value was NaN or {@code Double.POSITIVE_INFINITY} if no values were
     * recorded. Unlike the numerical comparison operators, this method
     * considers negative zero to be strictly smaller than positive zero.
     *
     * <p>
     *  如果记录的值为NaN,则返回最小记录值{@code Double.NaN},如果未记录任何值,则返回{@code Double.POSITIVE_INFINITY}。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 
     * 
     * @return the minimum recorded value, {@code Double.NaN} if any recorded
     * value was NaN or {@code Double.POSITIVE_INFINITY} if no values were
     * recorded
     */
    public final double getMin() {
        return min;
    }

    /**
     * Returns the maximum recorded value, {@code Double.NaN} if any recorded
     * value was NaN or {@code Double.NEGATIVE_INFINITY} if no values were
     * recorded. Unlike the numerical comparison operators, this method
     * considers negative zero to be strictly smaller than positive zero.
     *
     * <p>
     * 如果记录的值为NaN,则返回最大记录值{@code Double.NaN},如果未记录任何值,则返回{@code Double.NEGATIVE_INFINITY}。
     * 与数值比较运算符不同,此方法认为负零严格小于正零。
     * 
     * 
     * @return the maximum recorded value, {@code Double.NaN} if any recorded
     * value was NaN or {@code Double.NEGATIVE_INFINITY} if no values were
     * recorded
     */
    public final double getMax() {
        return max;
    }

    /**
     * Returns the arithmetic mean of values recorded, or zero if no
     * values have been recorded.
     *
     * If any recorded value is a NaN or the sum is at any point a NaN
     * then the average will be code NaN.
     *
     * <p>The average returned can vary depending upon the order in
     * which values are recorded.
     *
     * This method may be implemented using compensated summation or
     * other technique to reduce the error bound in the {@link #getSum
     * numerical sum} used to compute the average.
     *
     * @apiNote Values sorted by increasing absolute magnitude tend to yield
     * more accurate results.
     *
     * <p>
     *  返回记录的值的算术平均值,如果未记录任何值,则返回零。
     * 
     *  如果任何记录值是NaN或总和在任何点NaN,则平均值将是代码NaN。
     * 
     *  <p>返回的平均值可能会根据记录值的顺序而有所不同。
     * 
     *  该方法可以使用补偿求和或其他技术来实现,以减少用于计算平均值的{@link #getSum数字和}中的误差界限。
     * 
     * 
     * @return the arithmetic mean of values, or zero if none
     */
    public final double getAverage() {
        return getCount() > 0 ? getSum() / getCount() : 0.0d;
    }

    /**
     * {@inheritDoc}
     *
     * Returns a non-empty string representation of this object suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     * <p>
     *  @apiNote通过增加绝对值排序的值往往产生更准确的结果。
     * 
     */
    @Override
    public String toString() {
        return String.format(
            "%s{count=%d, sum=%f, min=%f, average=%f, max=%f}",
            this.getClass().getSimpleName(),
            getCount(),
            getSum(),
            getMin(),
            getAverage(),
            getMax());
    }
}
