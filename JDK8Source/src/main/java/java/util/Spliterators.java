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
 * Static classes and methods for operating on or creating instances of
 * {@link Spliterator} and its primitive specializations
 * {@link Spliterator.OfInt}, {@link Spliterator.OfLong}, and
 * {@link Spliterator.OfDouble}.
 *
 * <p>
 *  用于操作或创建{@link Spliterator}及其原始特殊化{@link Spliterator.OfInt},{@link Spliterator.OfLong}和{@link Spliterator.OfDouble}
 * 的实例的静态类和方法。
 * 
 * 
 * @see Spliterator
 * @since 1.8
 */
public final class Spliterators {

    // Suppresses default constructor, ensuring non-instantiability.
    private Spliterators() {}

    // Empty spliterators

    /**
     * Creates an empty {@code Spliterator}
     *
     * <p>The empty spliterator reports {@link Spliterator#SIZED} and
     * {@link Spliterator#SUBSIZED}.  Calls to
     * {@link java.util.Spliterator#trySplit()} always return {@code null}.
     *
     * <p>
     *  创建空的{@code Spliterator}
     * 
     *  <p>空Spliceator报告{@link Spliterator#SIZED}和{@link Spliterator#SUBSIZED}。
     * 对{@link java.util.Spliterator#trySplit()}的调用始终返回{@code null}。
     * 
     * 
     * @param <T> Type of elements
     * @return An empty spliterator
     */
    @SuppressWarnings("unchecked")
    public static <T> Spliterator<T> emptySpliterator() {
        return (Spliterator<T>) EMPTY_SPLITERATOR;
    }

    private static final Spliterator<Object> EMPTY_SPLITERATOR =
            new EmptySpliterator.OfRef<>();

    /**
     * Creates an empty {@code Spliterator.OfInt}
     *
     * <p>The empty spliterator reports {@link Spliterator#SIZED} and
     * {@link Spliterator#SUBSIZED}.  Calls to
     * {@link java.util.Spliterator#trySplit()} always return {@code null}.
     *
     * <p>
     *  创建一个空的{@code Spliterator.OfInt}
     * 
     *  <p>空Spliceator报告{@link Spliterator#SIZED}和{@link Spliterator#SUBSIZED}。
     * 对{@link java.util.Spliterator#trySplit()}的调用始终返回{@code null}。
     * 
     * 
     * @return An empty spliterator
     */
    public static Spliterator.OfInt emptyIntSpliterator() {
        return EMPTY_INT_SPLITERATOR;
    }

    private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR =
            new EmptySpliterator.OfInt();

    /**
     * Creates an empty {@code Spliterator.OfLong}
     *
     * <p>The empty spliterator reports {@link Spliterator#SIZED} and
     * {@link Spliterator#SUBSIZED}.  Calls to
     * {@link java.util.Spliterator#trySplit()} always return {@code null}.
     *
     * <p>
     *  创建空的{@code Spliterator.OfLong}
     * 
     *  <p>空Spliceator报告{@link Spliterator#SIZED}和{@link Spliterator#SUBSIZED}。
     * 对{@link java.util.Spliterator#trySplit()}的调用始终返回{@code null}。
     * 
     * 
     * @return An empty spliterator
     */
    public static Spliterator.OfLong emptyLongSpliterator() {
        return EMPTY_LONG_SPLITERATOR;
    }

    private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR =
            new EmptySpliterator.OfLong();

    /**
     * Creates an empty {@code Spliterator.OfDouble}
     *
     * <p>The empty spliterator reports {@link Spliterator#SIZED} and
     * {@link Spliterator#SUBSIZED}.  Calls to
     * {@link java.util.Spliterator#trySplit()} always return {@code null}.
     *
     * <p>
     *  创建一个空的{@code Spliterator.OfDouble}
     * 
     *  <p>空Spliceator报告{@link Spliterator#SIZED}和{@link Spliterator#SUBSIZED}。
     * 对{@link java.util.Spliterator#trySplit()}的调用始终返回{@code null}。
     * 
     * 
     * @return An empty spliterator
     */
    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return EMPTY_DOUBLE_SPLITERATOR;
    }

    private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR =
            new EmptySpliterator.OfDouble();

    // Array-based spliterators

    /**
     * Creates a {@code Spliterator} covering the elements of a given array,
     * using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(Object[])}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的Spliterator特性集,创建覆盖给定数组元素的{@code Spliterator}。
     * 
     * <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 大多数其他需要数组的Spliterator的情况下,应该使用{@link Arrays#spliterator(Object [])}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param <T> Type of elements
     * @param array The array, assumed to be unmodified during use
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @see Arrays#spliterator(Object[])
     */
    public static <T> Spliterator<T> spliterator(Object[] array,
                                                 int additionalCharacteristics) {
        return new ArraySpliterator<>(Objects.requireNonNull(array),
                                      additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator} covering a range of elements of a given
     * array, using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(Object[])}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的一组spliterator特性,创建覆盖给定数组元素范围的{@code Spliterator}。
     * 
     *  <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 大多数其他需要数组的Spliterator的情况下,应该使用{@link Arrays#spliterator(Object [])}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param <T> Type of elements
     * @param array The array, assumed to be unmodified during use
     * @param fromIndex The least index (inclusive) to cover
     * @param toIndex One past the greatest index to cover
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is negative,
     *         {@code toIndex} is less than {@code fromIndex}, or
     *         {@code toIndex} is greater than the array size
     * @see Arrays#spliterator(Object[], int, int)
     */
    public static <T> Spliterator<T> spliterator(Object[] array, int fromIndex, int toIndex,
                                                 int additionalCharacteristics) {
        checkFromToBounds(Objects.requireNonNull(array).length, fromIndex, toIndex);
        return new ArraySpliterator<>(array, fromIndex, toIndex, additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfInt} covering the elements of a given array,
     * using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(int[])}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的一组spliterator特性,创建覆盖给定数组元素的{@code Spliterator.OfInt}。
     * 
     * <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 对于需要数组的Spliterator的大多数其他情况,应该使用{@link Arrays#spliterator(int [])}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @see Arrays#spliterator(int[])
     */
    public static Spliterator.OfInt spliterator(int[] array,
                                                int additionalCharacteristics) {
        return new IntArraySpliterator(Objects.requireNonNull(array), additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfInt} covering a range of elements of a
     * given array, using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(int[], int, int)}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的Spliterator特性集,创建覆盖给定数组元素范围的{@code Spliterator.OfInt}。
     * 
     *  <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 大多数其他需要数组的Spliterator的情况,应该使用{@link Arrays#spliterator(int [],int,int)}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param fromIndex The least index (inclusive) to cover
     * @param toIndex One past the greatest index to cover
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is negative,
     *         {@code toIndex} is less than {@code fromIndex}, or
     *         {@code toIndex} is greater than the array size
     * @see Arrays#spliterator(int[], int, int)
     */
    public static Spliterator.OfInt spliterator(int[] array, int fromIndex, int toIndex,
                                                int additionalCharacteristics) {
        checkFromToBounds(Objects.requireNonNull(array).length, fromIndex, toIndex);
        return new IntArraySpliterator(array, fromIndex, toIndex, additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfLong} covering the elements of a given array,
     * using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(long[])}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的一组spliterator特性,创建覆盖给定数组元素的{@code Spliterator.OfLong}。
     * 
     * <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 大多数其他需要数组的Spliterator的情况下,应该使用{@link Arrays#spliterator(long [])}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @see Arrays#spliterator(long[])
     */
    public static Spliterator.OfLong spliterator(long[] array,
                                                 int additionalCharacteristics) {
        return new LongArraySpliterator(Objects.requireNonNull(array), additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfLong} covering a range of elements of a
     * given array, using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(long[], int, int)}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report.  (For example, if it is
     * known the array will not be further modified, specify {@code IMMUTABLE};
     * if the array data is considered to have an an encounter order, specify
     * {@code ORDERED}).  The method {@link Arrays#spliterator(long[], int, int)} can
     * often be used instead, which returns a spliterator that reports
     * {@code SIZED}, {@code SUBSIZED}, {@code IMMUTABLE}, and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的一组spliterator特性,创建覆盖给定数组元素范围的{@code Spliterator.OfLong}。
     * 
     *  <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 对于需要数组的Spliterator的大多数其他情况,应该使用{@link Arrays#spliterator(long [],int,int)}。
     * 
     * <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。呼叫者可以提供用于报告器的附加特性。
     *  (例如,如果已知阵列不会被进一步修改,请指定{@code IMMUTABLE};如果数组数据被认为具有遇到顺序,请指定{@code ORDERED})。
     * 通常可以使用方法{@link Arrays#spliterator(long [],int,int)},它返回一个报告{@code SIZED},{@code SUBSIZED},{@code IMMUTABLE}
     * 和{ @code ORDERED}。
     *  (例如,如果已知阵列不会被进一步修改,请指定{@code IMMUTABLE};如果数组数据被认为具有遇到顺序,请指定{@code ORDERED})。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param fromIndex The least index (inclusive) to cover
     * @param toIndex One past the greatest index to cover
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is negative,
     *         {@code toIndex} is less than {@code fromIndex}, or
     *         {@code toIndex} is greater than the array size
     * @see Arrays#spliterator(long[], int, int)
     */
    public static Spliterator.OfLong spliterator(long[] array, int fromIndex, int toIndex,
                                                 int additionalCharacteristics) {
        checkFromToBounds(Objects.requireNonNull(array).length, fromIndex, toIndex);
        return new LongArraySpliterator(array, fromIndex, toIndex, additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfDouble} covering the elements of a given array,
     * using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(double[])}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report; it is common to
     * additionally specify {@code IMMUTABLE} and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的一组spliterator特性,创建覆盖给定数组元素的{@code Spliterator.OfDouble}。
     * 
     *  <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 大多数其他需要数组的Spliterator的情况,应该使用{@link Arrays#spliterator(double [])}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。
     * 呼叫者可以提供用于报告的分裂器的附加特征;通常另外指定{@code IMMUTABLE}和{@code ORDERED}。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @see Arrays#spliterator(double[])
     */
    public static Spliterator.OfDouble spliterator(double[] array,
                                                   int additionalCharacteristics) {
        return new DoubleArraySpliterator(Objects.requireNonNull(array), additionalCharacteristics);
    }

    /**
     * Creates a {@code Spliterator.OfDouble} covering a range of elements of a
     * given array, using a customized set of spliterator characteristics.
     *
     * <p>This method is provided as an implementation convenience for
     * Spliterators which store portions of their elements in arrays, and need
     * fine control over Spliterator characteristics.  Most other situations in
     * which a Spliterator for an array is needed should use
     * {@link Arrays#spliterator(double[], int, int)}.
     *
     * <p>The returned spliterator always reports the characteristics
     * {@code SIZED} and {@code SUBSIZED}.  The caller may provide additional
     * characteristics for the spliterator to report.  (For example, if it is
     * known the array will not be further modified, specify {@code IMMUTABLE};
     * if the array data is considered to have an an encounter order, specify
     * {@code ORDERED}).  The method {@link Arrays#spliterator(long[], int, int)} can
     * often be used instead, which returns a spliterator that reports
     * {@code SIZED}, {@code SUBSIZED}, {@code IMMUTABLE}, and {@code ORDERED}.
     *
     * <p>
     *  使用自定义的Spliterator特性集,创建覆盖给定数组元素范围的{@code Spliterator.OfDouble}。
     * 
     * <p>提供此方法作为对于将其元素的部分存储在数组中并且需要对分割器特性进行精细控制的分割器的实现方便。
     * 对于需要数组的Spliterator的大多数其他情况,应该使用{@link Arrays#spliterator(double [],int,int)}。
     * 
     *  <p>返回的分词器总是报告{@code SIZED}和{@code SUBSIZED}的特征。呼叫者可以提供用于报告器的附加特性。
     *  (例如,如果已知阵列不会被进一步修改,请指定{@code IMMUTABLE};如果数组数据被认为具有遇到顺序,请指定{@code ORDERED})。
     * 通常可以使用方法{@link Arrays#spliterator(long [],int,int)},它返回一个报告{@code SIZED},{@code SUBSIZED},{@code IMMUTABLE}
     * 和{ @code ORDERED}。
     *  (例如,如果已知阵列不会被进一步修改,请指定{@code IMMUTABLE};如果数组数据被认为具有遇到顺序,请指定{@code ORDERED})。
     * 
     * 
     * @param array The array, assumed to be unmodified during use
     * @param fromIndex The least index (inclusive) to cover
     * @param toIndex One past the greatest index to cover
     * @param additionalCharacteristics Additional spliterator characteristics
     *        of this spliterator's source or elements beyond {@code SIZED} and
     *        {@code SUBSIZED} which are are always reported
     * @return A spliterator for an array
     * @throws NullPointerException if the given array is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex} is negative,
     *         {@code toIndex} is less than {@code fromIndex}, or
     *         {@code toIndex} is greater than the array size
     * @see Arrays#spliterator(double[], int, int)
     */
    public static Spliterator.OfDouble spliterator(double[] array, int fromIndex, int toIndex,
                                                   int additionalCharacteristics) {
        checkFromToBounds(Objects.requireNonNull(array).length, fromIndex, toIndex);
        return new DoubleArraySpliterator(array, fromIndex, toIndex, additionalCharacteristics);
    }

    /**
     * Validate inclusive start index and exclusive end index against the length
     * of an array.
     * <p>
     *  根据数组的长度验证包含起始索引和排除结束索引。
     * 
     * 
     * @param arrayLength The length of the array
     * @param origin The inclusive start index
     * @param fence The exclusive end index
     * @throws ArrayIndexOutOfBoundsException if the start index is greater than
     * the end index, if the start index is negative, or the end index is
     * greater than the array length
     */
    private static void checkFromToBounds(int arrayLength, int origin, int fence) {
        if (origin > fence) {
            throw new ArrayIndexOutOfBoundsException(
                    "origin(" + origin + ") > fence(" + fence + ")");
        }
        if (origin < 0) {
            throw new ArrayIndexOutOfBoundsException(origin);
        }
        if (fence > arrayLength) {
            throw new ArrayIndexOutOfBoundsException(fence);
        }
    }

    // Iterator-based spliterators

    /**
     * Creates a {@code Spliterator} using the given collection's
     * {@link java.util.Collection#iterator()} as the source of elements, and
     * reporting its {@link java.util.Collection#size()} as its initial size.
     *
     * <p>The spliterator is
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the collection's iterator, and
     * implements {@code trySplit} to permit limited parallelism.
     *
     * <p>
     *  使用给定集合的{@link java.util.Collection#iterator()}作为元素来源创建{@code Spliterator},并将其{@link java.util.Collection#size()}
     * 作为其初始大小进行报告。
     * 
     *  <p>分割器是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>,继承集合的迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     * 
     * @param <T> Type of elements
     * @param c The collection
     * @param characteristics Characteristics of this spliterator's source or
     *        elements.  The characteristics {@code SIZED} and {@code SUBSIZED}
     *        are additionally reported unless {@code CONCURRENT} is supplied.
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given collection is {@code null}
     */
    public static <T> Spliterator<T> spliterator(Collection<? extends T> c,
                                                 int characteristics) {
        return new IteratorSpliterator<>(Objects.requireNonNull(c),
                                         characteristics);
    }

    /**
     * Creates a {@code Spliterator} using a given {@code Iterator}
     * as the source of elements, and with a given initially reported size.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned, or the initially reported
     * size is not equal to the actual number of elements in the source.
     *
     * <p>
     * 使用给定的{@code Iterator}作为元素的来源,并使用给定的最初报告的大小创建{@code Spliterator}。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在返回分割器之后被操作,或者初始报告的大小不等于源中的元素的实际数量,则分割和遍历的行为是未定义的。
     * 
     * 
     * @param <T> Type of elements
     * @param iterator The iterator for the source
     * @param size The number of elements in the source, to be reported as
     *        initial {@code estimateSize}
     * @param characteristics Characteristics of this spliterator's source or
     *        elements.  The characteristics {@code SIZED} and {@code SUBSIZED}
     *        are additionally reported unless {@code CONCURRENT} is supplied.
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static <T> Spliterator<T> spliterator(Iterator<? extends T> iterator,
                                                 long size,
                                                 int characteristics) {
        return new IteratorSpliterator<>(Objects.requireNonNull(iterator), size,
                                         characteristics);
    }

    /**
     * Creates a {@code Spliterator} using a given {@code Iterator}
     * as the source of elements, with no initial size estimate.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned.
     *
     * <p>
     *  使用给定的{@code Iterator}作为元素的源,创建{@code Spliterator},而无需初始大小估算。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在拆分器返回后运行,则拆分和遍历的行为是未定义的。
     * 
     * 
     * @param <T> Type of elements
     * @param iterator The iterator for the source
     * @param characteristics Characteristics of this spliterator's source
     *        or elements ({@code SIZED} and {@code SUBSIZED}, if supplied, are
     *        ignored and are not reported.)
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator,
                                                            int characteristics) {
        return new IteratorSpliterator<>(Objects.requireNonNull(iterator), characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfInt} using a given
     * {@code IntStream.IntIterator} as the source of elements, and with a given
     * initially reported size.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned, or the initially reported
     * size is not equal to the actual number of elements in the source.
     *
     * <p>
     *  使用给定的{@code IntStream.IntIterator}作为元素的源,并使用给定的最初报告的大小创建{@code Spliterator.OfInt}。
     * 
     * <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在返回分割器之后被操作,或者初始报告的大小不等于源中的元素的实际数量,则分割和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param size The number of elements in the source, to be reported as
     *        initial {@code estimateSize}.
     * @param characteristics Characteristics of this spliterator's source or
     *        elements.  The characteristics {@code SIZED} and {@code SUBSIZED}
     *        are additionally reported unless {@code CONCURRENT} is supplied.
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt iterator,
                                                long size,
                                                int characteristics) {
        return new IntIteratorSpliterator(Objects.requireNonNull(iterator),
                                          size, characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfInt} using a given
     * {@code IntStream.IntIterator} as the source of elements, with no initial
     * size estimate.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned.
     *
     * <p>
     *  使用给定的{@code IntStream.IntIterator}作为元素的源,创建{@code Spliterator.OfInt},没有初始大小估计。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在拆分器返回后运行,则拆分和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param characteristics Characteristics of this spliterator's source
     *        or elements ({@code SIZED} and {@code SUBSIZED}, if supplied, are
     *        ignored and are not reported.)
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt iterator,
                                                           int characteristics) {
        return new IntIteratorSpliterator(Objects.requireNonNull(iterator), characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfLong} using a given
     * {@code LongStream.LongIterator} as the source of elements, and with a
     * given initially reported size.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned, or the initially reported
     * size is not equal to the actual number of elements in the source.
     *
     * <p>
     *  使用给定的{@code LongStream.LongIterator}作为元素源,并使用给定的最初报告的大小创建{@code Spliterator.OfLong}。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     * 元素的遍历应通过拼接器完成。如果迭代器在返回分割器之后被操作,或者初始报告的大小不等于源中的元素的实际数量,则分割和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param size The number of elements in the source, to be reported as
     *        initial {@code estimateSize}.
     * @param characteristics Characteristics of this spliterator's source or
     *        elements.  The characteristics {@code SIZED} and {@code SUBSIZED}
     *        are additionally reported unless {@code CONCURRENT} is supplied.
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong iterator,
                                                 long size,
                                                 int characteristics) {
        return new LongIteratorSpliterator(Objects.requireNonNull(iterator),
                                           size, characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfLong} using a given
     * {@code LongStream.LongIterator} as the source of elements, with no
     * initial size estimate.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned.
     *
     * <p>
     *  使用给定的{@code LongStream.LongIterator}作为元素的源,创建{@code Spliterator.OfLong},而无需初始大小估算。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在拆分器返回后运行,则拆分和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param characteristics Characteristics of this spliterator's source
     *        or elements ({@code SIZED} and {@code SUBSIZED}, if supplied, are
     *        ignored and are not reported.)
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong iterator,
                                                            int characteristics) {
        return new LongIteratorSpliterator(Objects.requireNonNull(iterator), characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfDouble} using a given
     * {@code DoubleStream.DoubleIterator} as the source of elements, and with a
     * given initially reported size.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned, or the initially reported
     * size is not equal to the actual number of elements in the source.
     *
     * <p>
     *  使用给定的{@code DoubleStream.DoubleIterator}作为元素的来源,并使用给定的最初报告的大小创建{@code Spliterator.OfDouble}。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在返回分割器之后被操作,或者初始报告的大小不等于源中的元素的实际数量,则分割和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param size The number of elements in the source, to be reported as
     *        initial {@code estimateSize}
     * @param characteristics Characteristics of this spliterator's source or
     *        elements.  The characteristics {@code SIZED} and {@code SUBSIZED}
     *        are additionally reported unless {@code CONCURRENT} is supplied.
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble iterator,
                                                   long size,
                                                   int characteristics) {
        return new DoubleIteratorSpliterator(Objects.requireNonNull(iterator),
                                             size, characteristics);
    }

    /**
     * Creates a {@code Spliterator.OfDouble} using a given
     * {@code DoubleStream.DoubleIterator} as the source of elements, with no
     * initial size estimate.
     *
     * <p>The spliterator is not
     * <em><a href="Spliterator.html#binding">late-binding</a></em>, inherits
     * the <em>fail-fast</em> properties of the iterator, and implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>Traversal of elements should be accomplished through the spliterator.
     * The behaviour of splitting and traversal is undefined if the iterator is
     * operated on after the spliterator is returned.
     *
     * <p>
     * 使用给定的{@code DoubleStream.DoubleIterator}作为元素来源创建{@code Spliterator.OfDouble},而无需初始大小估算。
     * 
     *  <p>分割器不是<em> <a href="Spliterator.html#binding">延迟绑定</a> </em>继承迭代器的<em> fail-fast </em>属性,并实现{@code trySplit}
     * 以允许有限的并行性。
     * 
     *  元素的遍历应通过拼接器完成。如果迭代器在拆分器返回后运行,则拆分和遍历的行为是未定义的。
     * 
     * 
     * @param iterator The iterator for the source
     * @param characteristics Characteristics of this spliterator's source
     *        or elements ({@code SIZED} and {@code SUBSIZED}, if supplied, are
     *        ignored and are not reported.)
     * @return A spliterator from an iterator
     * @throws NullPointerException if the given iterator is {@code null}
     */
    public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble iterator,
                                                              int characteristics) {
        return new DoubleIteratorSpliterator(Objects.requireNonNull(iterator), characteristics);
    }

    // Iterators from Spliterators

    /**
     * Creates an {@code Iterator} from a {@code Spliterator}.
     *
     * <p>Traversal of elements should be accomplished through the iterator.
     * The behaviour of traversal is undefined if the spliterator is operated
     * after the iterator is returned.
     *
     * <p>
     *  从{@code Spliterator}创建{@code迭代器}。
     * 
     *  <p>元素的遍历应该通过迭代器完成。如果在迭代器返回后操作分割器,则遍历的行为是未定义的。
     * 
     * 
     * @param <T> Type of elements
     * @param spliterator The spliterator
     * @return An iterator
     * @throws NullPointerException if the given spliterator is {@code null}
     */
    public static<T> Iterator<T> iterator(Spliterator<? extends T> spliterator) {
        Objects.requireNonNull(spliterator);
        class Adapter implements Iterator<T>, Consumer<T> {
            boolean valueReady = false;
            T nextElement;

            @Override
            public void accept(T t) {
                valueReady = true;
                nextElement = t;
            }

            @Override
            public boolean hasNext() {
                if (!valueReady)
                    spliterator.tryAdvance(this);
                return valueReady;
            }

            @Override
            public T next() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }

        return new Adapter();
    }

    /**
     * Creates an {@code PrimitiveIterator.OfInt} from a
     * {@code Spliterator.OfInt}.
     *
     * <p>Traversal of elements should be accomplished through the iterator.
     * The behaviour of traversal is undefined if the spliterator is operated
     * after the iterator is returned.
     *
     * <p>
     *  从{@code Spliterator.OfInt}创建{@code PrimitiveIterator.OfInt}。
     * 
     *  <p>元素的遍历应该通过迭代器完成。如果在迭代器返回后操作分割器,则遍历的行为是未定义的。
     * 
     * 
     * @param spliterator The spliterator
     * @return An iterator
     * @throws NullPointerException if the given spliterator is {@code null}
     */
    public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt spliterator) {
        Objects.requireNonNull(spliterator);
        class Adapter implements PrimitiveIterator.OfInt, IntConsumer {
            boolean valueReady = false;
            int nextElement;

            @Override
            public void accept(int t) {
                valueReady = true;
                nextElement = t;
            }

            @Override
            public boolean hasNext() {
                if (!valueReady)
                    spliterator.tryAdvance(this);
                return valueReady;
            }

            @Override
            public int nextInt() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }

        return new Adapter();
    }

    /**
     * Creates an {@code PrimitiveIterator.OfLong} from a
     * {@code Spliterator.OfLong}.
     *
     * <p>Traversal of elements should be accomplished through the iterator.
     * The behaviour of traversal is undefined if the spliterator is operated
     * after the iterator is returned.
     *
     * <p>
     *  从{@code Spliterator.OfLong}创建{@code PrimitiveIterator.OfLong}。
     * 
     *  <p>元素的遍历应该通过迭代器完成。如果在迭代器返回后操作分割器,则遍历的行为是未定义的。
     * 
     * 
     * @param spliterator The spliterator
     * @return An iterator
     * @throws NullPointerException if the given spliterator is {@code null}
     */
    public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong spliterator) {
        Objects.requireNonNull(spliterator);
        class Adapter implements PrimitiveIterator.OfLong, LongConsumer {
            boolean valueReady = false;
            long nextElement;

            @Override
            public void accept(long t) {
                valueReady = true;
                nextElement = t;
            }

            @Override
            public boolean hasNext() {
                if (!valueReady)
                    spliterator.tryAdvance(this);
                return valueReady;
            }

            @Override
            public long nextLong() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }

        return new Adapter();
    }

    /**
     * Creates an {@code PrimitiveIterator.OfDouble} from a
     * {@code Spliterator.OfDouble}.
     *
     * <p>Traversal of elements should be accomplished through the iterator.
     * The behaviour of traversal is undefined if the spliterator is operated
     * after the iterator is returned.
     *
     * <p>
     *  从{@code Spliterator.OfDouble}创建{@code PrimitiveIterator.OfDouble}。
     * 
     * <p>元素的遍历应该通过迭代器完成。如果在迭代器返回后操作分割器,则遍历的行为是未定义的。
     * 
     * 
     * @param spliterator The spliterator
     * @return An iterator
     * @throws NullPointerException if the given spliterator is {@code null}
     */
    public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble spliterator) {
        Objects.requireNonNull(spliterator);
        class Adapter implements PrimitiveIterator.OfDouble, DoubleConsumer {
            boolean valueReady = false;
            double nextElement;

            @Override
            public void accept(double t) {
                valueReady = true;
                nextElement = t;
            }

            @Override
            public boolean hasNext() {
                if (!valueReady)
                    spliterator.tryAdvance(this);
                return valueReady;
            }

            @Override
            public double nextDouble() {
                if (!valueReady && !hasNext())
                    throw new NoSuchElementException();
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        }

        return new Adapter();
    }

    // Implementations

    private static abstract class EmptySpliterator<T, S extends Spliterator<T>, C> {

        EmptySpliterator() { }

        public S trySplit() {
            return null;
        }

        public boolean tryAdvance(C consumer) {
            Objects.requireNonNull(consumer);
            return false;
        }

        public void forEachRemaining(C consumer) {
            Objects.requireNonNull(consumer);
        }

        public long estimateSize() {
            return 0;
        }

        public int characteristics() {
            return Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        private static final class OfRef<T>
                extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
                implements Spliterator<T> {
            OfRef() { }
        }

        private static final class OfInt
                extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
                implements Spliterator.OfInt {
            OfInt() { }
        }

        private static final class OfLong
                extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
                implements Spliterator.OfLong {
            OfLong() { }
        }

        private static final class OfDouble
                extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
                implements Spliterator.OfDouble {
            OfDouble() { }
        }
    }

    // Array-based spliterators

    /**
     * A Spliterator designed for use by sources that traverse and split
     * elements maintained in an unmodifiable {@code Object[]} array.
     * <p>
     *  一个Spliterator,设计用于遍历和拆分保存在不可修改的{@code Object []}数组中的元素。
     * 
     */
    static final class ArraySpliterator<T> implements Spliterator<T> {
        /**
         * The array, explicitly typed as Object[]. Unlike in some other
         * classes (see for example CR 6260652), we do not need to
         * screen arguments to ensure they are exactly of type Object[]
         * so long as no methods write into the array or serialize it,
         * which we ensure here by defining this class as final.
         * <p>
         *  数组,显式地键入Object []。
         * 与一些其他类不同(参见例如CR 6260652),我们不需要屏幕参数,以确保它们完全是Object []类型,只要没有方法写入数组或序列化它,我们在这里通过定义类作为final。
         * 
         */
        private final Object[] array;
        private int index;        // current index, modified on advance/split
        private final int fence;  // one past last index
        private final int characteristics;

        /**
         * Creates a spliterator covering all of the given array.
         * <p>
         *  创建一个覆盖所有给定数组的spliterator。
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param additionalCharacteristics Additional spliterator characteristics
         * of this spliterator's source or elements beyond {@code SIZED} and
         * {@code SUBSIZED} which are are always reported
         */
        public ArraySpliterator(Object[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        /**
         * Creates a spliterator covering the given array and range
         * <p>
         *  创建覆盖给定数组和范围的spliterator
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param origin the least index (inclusive) to cover
         * @param fence one past the greatest index to cover
         * @param additionalCharacteristics Additional spliterator characteristics
         * of this spliterator's source or elements beyond {@code SIZED} and
         * {@code SUBSIZED} which are are always reported
         */
        public ArraySpliterator(Object[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        @Override
        public Spliterator<T> trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new ArraySpliterator<>(array, lo, index = mid, characteristics);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Object[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept((T)a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                @SuppressWarnings("unchecked") T e = (T) array[index++];
                action.accept(e);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    /**
     * A Spliterator.OfInt designed for use by sources that traverse and split
     * elements maintained in an unmodifiable {@code int[]} array.
     * <p>
     *  Spliterator.OfInt设计用于遍历和拆分在不可修改的{@code int []}数组中维护的元素的源。
     * 
     */
    static final class IntArraySpliterator implements Spliterator.OfInt {
        private final int[] array;
        private int index;        // current index, modified on advance/split
        private final int fence;  // one past last index
        private final int characteristics;

        /**
         * Creates a spliterator covering all of the given array.
         * <p>
         *  创建一个覆盖所有给定数组的spliterator。
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public IntArraySpliterator(int[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        /**
         * Creates a spliterator covering the given array and range
         * <p>
         *  创建覆盖给定数组和范围的spliterator
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param origin the least index (inclusive) to cover
         * @param fence one past the greatest index to cover
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public IntArraySpliterator(int[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        @Override
        public OfInt trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new IntArraySpliterator(array, lo, index = mid, characteristics);
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            int[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept(a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super Integer> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    /**
     * A Spliterator.OfLong designed for use by sources that traverse and split
     * elements maintained in an unmodifiable {@code int[]} array.
     * <p>
     *  Splitator.OfLong设计用于遍历和拆分维护在不可修改的{@code int []}数组中的元素。
     * 
     */
    static final class LongArraySpliterator implements Spliterator.OfLong {
        private final long[] array;
        private int index;        // current index, modified on advance/split
        private final int fence;  // one past last index
        private final int characteristics;

        /**
         * Creates a spliterator covering all of the given array.
         * <p>
         *  创建一个覆盖所有给定数组的spliterator。
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public LongArraySpliterator(long[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        /**
         * Creates a spliterator covering the given array and range
         * <p>
         *  创建覆盖给定数组和范围的spliterator
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param origin the least index (inclusive) to cover
         * @param fence one past the greatest index to cover
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public LongArraySpliterator(long[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        @Override
        public OfLong trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new LongArraySpliterator(array, lo, index = mid, characteristics);
        }

        @Override
        public void forEachRemaining(LongConsumer action) {
            long[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept(a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(LongConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super Long> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    /**
     * A Spliterator.OfDouble designed for use by sources that traverse and split
     * elements maintained in an unmodifiable {@code int[]} array.
     * <p>
     *  Splitator.OfDouble设计为由遍历和拆分在不可修改的{@code int []}数组中维护的元素的源使用。
     * 
     */
    static final class DoubleArraySpliterator implements Spliterator.OfDouble {
        private final double[] array;
        private int index;        // current index, modified on advance/split
        private final int fence;  // one past last index
        private final int characteristics;

        /**
         * Creates a spliterator covering all of the given array.
         * <p>
         *  创建一个覆盖所有给定数组的spliterator。
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public DoubleArraySpliterator(double[] array, int additionalCharacteristics) {
            this(array, 0, array.length, additionalCharacteristics);
        }

        /**
         * Creates a spliterator covering the given array and range
         * <p>
         *  创建覆盖给定数组和范围的spliterator
         * 
         * 
         * @param array the array, assumed to be unmodified during use
         * @param origin the least index (inclusive) to cover
         * @param fence one past the greatest index to cover
         * @param additionalCharacteristics Additional spliterator characteristics
         *        of this spliterator's source or elements beyond {@code SIZED} and
         *        {@code SUBSIZED} which are are always reported
         */
        public DoubleArraySpliterator(double[] array, int origin, int fence, int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        @Override
        public OfDouble trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                   ? null
                   : new DoubleArraySpliterator(array, lo, index = mid, characteristics);
        }

        @Override
        public void forEachRemaining(DoubleConsumer action) {
            double[] a; int i, hi; // hoist accesses and checks from loop
            if (action == null)
                throw new NullPointerException();
            if ((a = array).length >= (hi = fence) &&
                (i = index) >= 0 && i < (index = hi)) {
                do { action.accept(a[i]); } while (++i < hi);
            }
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() { return (long)(fence - index); }

        @Override
        public int characteristics() {
            return characteristics;
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    //

    /**
     * An abstract {@code Spliterator} that implements {@code trySplit} to
     * permit limited parallelism.
     *
     * <p>An extending class need only
     * implement {@link #tryAdvance(java.util.function.Consumer) tryAdvance}.
     * The extending class should override
     * {@link #forEachRemaining(java.util.function.Consumer) forEach} if it can
     * provide a more performant implementation.
     *
     * @apiNote
     * This class is a useful aid for creating a spliterator when it is not
     * possible or difficult to efficiently partition elements in a manner
     * allowing balanced parallel computation.
     *
     * <p>An alternative to using this class, that also permits limited
     * parallelism, is to create a spliterator from an iterator
     * (see {@link #spliterator(Iterator, long, int)}.  Depending on the
     * circumstances using an iterator may be easier or more convenient than
     * extending this class, such as when there is already an iterator
     * available to use.
     *
     * <p>
     * 实现{@code trySplit}以允许有限并行性的抽象{@code Spliterator}。
     * 
     *  <p>扩展类只需实现{@link #tryAdvance(java.util.function.Consumer)tryAdvance}。
     * 扩展类应该覆盖{@link #forEachRemaining(java.util.function.Consumer)forEach},如果它可以提供更高性能的实现。
     * 
     *  @apiNote当不可能或难以以允许平衡并行计算的方式高效地划分元素时,该类是用于创建分裂器的有用辅助。
     * 
     *  <p>使用这个类的一个替代方法,也允许有限的并行性,是从迭代器创建一个spliterator(参见{@link #spliterator(Iterator,long,int)}。
     * 根据使用迭代器的情况可能更容易或者比扩展这个类更方便,比如当已经有一个迭代器可用时。
     * 
     * 
     * @see #spliterator(Iterator, long, int)
     * @since 1.8
     */
    public static abstract class AbstractSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10;  // batch array size increment
        static final int MAX_BATCH = 1 << 25;  // max batch array size;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator reporting the given estimated size and
         * additionalCharacteristics.
         *
         * <p>
         *  创建报告给定估计大小和其他特征的分隔符。
         * 
         * 
         * @param est the estimated size of this spliterator if known, otherwise
         *        {@code Long.MAX_VALUE}.
         * @param additionalCharacteristics properties of this spliterator's
         *        source or elements.  If {@code SIZED} is reported then this
         *        spliterator will additionally report {@code SUBSIZED}.
         */
        protected AbstractSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingConsumer<T> implements Consumer<T> {
            Object value;

            @Override
            public void accept(T value) {
                this.value = value;
            }
        }

        /**
         * {@inheritDoc}
         *
         * This implementation permits limited parallelism.
         * <p>
         *  {@inheritDoc}
         * 
         *  这种实现允许有限的并行性。
         * 
         */
        @Override
        public Spliterator<T> trySplit() {
            /*
             * Split into arrays of arithmetically increasing batch
             * sizes.  This will only improve parallel performance if
             * per-element Consumer actions are more costly than
             * transferring them into an array.  The use of an
             * arithmetic progression in split sizes provides overhead
             * vs parallelism bounds that do not particularly favor or
             * penalize cases of lightweight vs heavyweight element
             * operations, across combinations of #elements vs #cores,
             * whether or not either are known.  We generate
             * O(sqrt(#elements)) splits, allowing O(sqrt(#cores))
             * potential speedup.
             * <p>
             * 拆分成算术增加批量大小的数组。这只会提高并行性能,如果per-element Consumer操作比将它们转换到数组更昂贵。
             * 使用分割大小的算术级数提供了开销与并行性界限,不特别优先或惩罚轻量级与重量级元素操作的情况,不论是否为#elements与#cores的组合,无论是否已知。
             * 我们生成O(sqrt(#elements))拆分,允许O(sqrt(#cores))潜在加速。
             * 
             */
            HoldingConsumer<T> holder = new HoldingConsumer<>();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new ArraySpliterator<>(a, 0, j, characteristics());
            }
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the estimated size as reported when
         * created and, if the estimate size is known, decreases in size when
         * split.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的估计大小,如果估计大小已知,则拆分时大小减小。
         * 
         */
        @Override
        public long estimateSize() {
            return est;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the characteristics as reported when
         * created.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的特性。
         * 
         */
        @Override
        public int characteristics() {
            return characteristics;
        }
    }

    /**
     * An abstract {@code Spliterator.OfInt} that implements {@code trySplit} to
     * permit limited parallelism.
     *
     * <p>To implement a spliterator an extending class need only
     * implement {@link #tryAdvance(java.util.function.IntConsumer)}
     * tryAdvance}.  The extending class should override
     * {@link #forEachRemaining(java.util.function.IntConsumer)} forEach} if it
     * can provide a more performant implementation.
     *
     * @apiNote
     * This class is a useful aid for creating a spliterator when it is not
     * possible or difficult to efficiently partition elements in a manner
     * allowing balanced parallel computation.
     *
     * <p>An alternative to using this class, that also permits limited
     * parallelism, is to create a spliterator from an iterator
     * (see {@link #spliterator(java.util.PrimitiveIterator.OfInt, long, int)}.
     * Depending on the circumstances using an iterator may be easier or more
     * convenient than extending this class. For example, if there is already an
     * iterator available to use then there is no need to extend this class.
     *
     * <p>
     *  实现{@code trySplit}以允许有限并行性的抽象{@code Spliterator.OfInt}。
     * 
     *  <p>要实现分割器,扩展类只需要实现{@link #tryAdvance(java.util.function.IntConsumer)} tryAdvance}。
     * 扩展类应该重写{@link #forEachRemaining(java.util.function.IntConsumer)} forEach}如果它可以提供更高性能的实现。
     * 
     *  @apiNote当不可能或难以以允许平衡并行计算的方式高效地划分元素时,该类是用于创建分裂器的有用辅助。
     * 
     * <p>另一种使用这个类的方法,也允许有限的并行性,是从迭代器创建一个分割器(见{@link #spliterator(java.util.PrimitiveIterator.OfInt,long,int)}
     * 。
     * 根据情况使用迭代器可能比扩展这个类更容易或更方便。例如,如果已经有一个可用的迭代器,那么不需要扩展这个类。
     * 
     * 
     * @see #spliterator(java.util.PrimitiveIterator.OfInt, long, int)
     * @since 1.8
     */
    public static abstract class AbstractIntSpliterator implements Spliterator.OfInt {
        static final int MAX_BATCH = AbstractSpliterator.MAX_BATCH;
        static final int BATCH_UNIT = AbstractSpliterator.BATCH_UNIT;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator reporting the given estimated size and
         * characteristics.
         *
         * <p>
         *  创建报告给定的估计大小和特征的分割器。
         * 
         * 
         * @param est the estimated size of this spliterator if known, otherwise
         *        {@code Long.MAX_VALUE}.
         * @param additionalCharacteristics properties of this spliterator's
         *        source or elements.  If {@code SIZED} is reported then this
         *        spliterator will additionally report {@code SUBSIZED}.
         */
        protected AbstractIntSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingIntConsumer implements IntConsumer {
            int value;

            @Override
            public void accept(int value) {
                this.value = value;
            }
        }

        /**
         * {@inheritDoc}
         *
         * This implementation permits limited parallelism.
         * <p>
         *  {@inheritDoc}
         * 
         *  这种实现允许有限的并行性。
         * 
         */
        @Override
        public Spliterator.OfInt trySplit() {
            HoldingIntConsumer holder = new HoldingIntConsumer();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                int[] a = new int[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new IntArraySpliterator(a, 0, j, characteristics());
            }
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the estimated size as reported when
         * created and, if the estimate size is known, decreases in size when
         * split.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的估计大小,如果估计大小已知,则拆分时大小减小。
         * 
         */
        @Override
        public long estimateSize() {
            return est;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the characteristics as reported when
         * created.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的特性。
         * 
         */
        @Override
        public int characteristics() {
            return characteristics;
        }
    }

    /**
     * An abstract {@code Spliterator.OfLong} that implements {@code trySplit}
     * to permit limited parallelism.
     *
     * <p>To implement a spliterator an extending class need only
     * implement {@link #tryAdvance(java.util.function.LongConsumer)}
     * tryAdvance}.  The extending class should override
     * {@link #forEachRemaining(java.util.function.LongConsumer)} forEach} if it
     * can provide a more performant implementation.
     *
     * @apiNote
     * This class is a useful aid for creating a spliterator when it is not
     * possible or difficult to efficiently partition elements in a manner
     * allowing balanced parallel computation.
     *
     * <p>An alternative to using this class, that also permits limited
     * parallelism, is to create a spliterator from an iterator
     * (see {@link #spliterator(java.util.PrimitiveIterator.OfLong, long, int)}.
     * Depending on the circumstances using an iterator may be easier or more
     * convenient than extending this class. For example, if there is already an
     * iterator available to use then there is no need to extend this class.
     *
     * <p>
     *  实现{@code trySplit}以允许有限并行性的抽象{@code Spliterator.OfLong}。
     * 
     *  <p>要实现分隔符,扩展类只需要实现{@link #tryAdvance(java.util.function.LongConsumer)} tryAdvance}。
     * 扩展类应该重写{@link #forEachRemaining(java.util.function.LongConsumer)} forEach}如果它可以提供更高性能的实现。
     * 
     *  @apiNote当不可能或难以以允许平衡并行计算的方式高效地划分元素时,该类是用于创建分裂器的有用辅助。
     * 
     * <p>另一种使用这个类的方法,也允许有限的并行性,是从迭代器创建一个分割器(见{@link #spliterator(java.util.PrimitiveIterator.OfLong,long,int)}
     * 。
     * 根据情况使用迭代器可能比扩展这个类更容易或更方便。例如,如果已经有一个可用的迭代器,那么不需要扩展这个类。
     * 
     * 
     * @see #spliterator(java.util.PrimitiveIterator.OfLong, long, int)
     * @since 1.8
     */
    public static abstract class AbstractLongSpliterator implements Spliterator.OfLong {
        static final int MAX_BATCH = AbstractSpliterator.MAX_BATCH;
        static final int BATCH_UNIT = AbstractSpliterator.BATCH_UNIT;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator reporting the given estimated size and
         * characteristics.
         *
         * <p>
         *  创建报告给定的估计大小和特征的分割器。
         * 
         * 
         * @param est the estimated size of this spliterator if known, otherwise
         *        {@code Long.MAX_VALUE}.
         * @param additionalCharacteristics properties of this spliterator's
         *        source or elements.  If {@code SIZED} is reported then this
         *        spliterator will additionally report {@code SUBSIZED}.
         */
        protected AbstractLongSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingLongConsumer implements LongConsumer {
            long value;

            @Override
            public void accept(long value) {
                this.value = value;
            }
        }

        /**
         * {@inheritDoc}
         *
         * This implementation permits limited parallelism.
         * <p>
         *  {@inheritDoc}
         * 
         *  这种实现允许有限的并行性。
         * 
         */
        @Override
        public Spliterator.OfLong trySplit() {
            HoldingLongConsumer holder = new HoldingLongConsumer();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                long[] a = new long[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new LongArraySpliterator(a, 0, j, characteristics());
            }
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the estimated size as reported when
         * created and, if the estimate size is known, decreases in size when
         * split.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的估计大小,如果估计大小已知,则拆分时大小减小。
         * 
         */
        @Override
        public long estimateSize() {
            return est;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the characteristics as reported when
         * created.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的特性。
         * 
         */
        @Override
        public int characteristics() {
            return characteristics;
        }
    }

    /**
     * An abstract {@code Spliterator.OfDouble} that implements
     * {@code trySplit} to permit limited parallelism.
     *
     * <p>To implement a spliterator an extending class need only
     * implement {@link #tryAdvance(java.util.function.DoubleConsumer)}
     * tryAdvance}.  The extending class should override
     * {@link #forEachRemaining(java.util.function.DoubleConsumer)} forEach} if
     * it can provide a more performant implementation.
     *
     * @apiNote
     * This class is a useful aid for creating a spliterator when it is not
     * possible or difficult to efficiently partition elements in a manner
     * allowing balanced parallel computation.
     *
     * <p>An alternative to using this class, that also permits limited
     * parallelism, is to create a spliterator from an iterator
     * (see {@link #spliterator(java.util.PrimitiveIterator.OfDouble, long, int)}.
     * Depending on the circumstances using an iterator may be easier or more
     * convenient than extending this class. For example, if there is already an
     * iterator available to use then there is no need to extend this class.
     *
     * <p>
     *  实现{@code trySplit}以允许有限并行性的抽象{@code Spliterator.OfDouble}。
     * 
     *  <p>要实现分隔符,扩展类只需要实现{@link #tryAdvance(java.util.function.DoubleConsumer)} tryAdvance}。
     * 扩展类应该重写{@link #forEachRemaining(java.util.function.DoubleConsumer)} forEach}如果它可以提供更高性能的实现。
     * 
     *  @apiNote当不可能或难以以允许平衡并行计算的方式高效地划分元素时,该类是用于创建分裂器的有用辅助。
     * 
     * <p>另一种使用这个类的方法,也允许有限的并行性,是从迭代器创建一个分割器(见{@link #spliterator(java.util.PrimitiveIterator.OfDouble,long,int)}
     * 。
     * 根据情况使用迭代器可能比扩展这个类更容易或更方便。例如,如果已经有一个可用的迭代器,那么不需要扩展这个类。
     * 
     * 
     * @see #spliterator(java.util.PrimitiveIterator.OfDouble, long, int)
     * @since 1.8
     */
    public static abstract class AbstractDoubleSpliterator implements Spliterator.OfDouble {
        static final int MAX_BATCH = AbstractSpliterator.MAX_BATCH;
        static final int BATCH_UNIT = AbstractSpliterator.BATCH_UNIT;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator reporting the given estimated size and
         * characteristics.
         *
         * <p>
         *  创建报告给定的估计大小和特征的分割器。
         * 
         * 
         * @param est the estimated size of this spliterator if known, otherwise
         *        {@code Long.MAX_VALUE}.
         * @param additionalCharacteristics properties of this spliterator's
         *        source or elements.  If {@code SIZED} is reported then this
         *        spliterator will additionally report {@code SUBSIZED}.
         */
        protected AbstractDoubleSpliterator(long est, int additionalCharacteristics) {
            this.est = est;
            this.characteristics = ((additionalCharacteristics & Spliterator.SIZED) != 0)
                                   ? additionalCharacteristics | Spliterator.SUBSIZED
                                   : additionalCharacteristics;
        }

        static final class HoldingDoubleConsumer implements DoubleConsumer {
            double value;

            @Override
            public void accept(double value) {
                this.value = value;
            }
        }

        /**
         * {@inheritDoc}
         *
         * This implementation permits limited parallelism.
         * <p>
         *  {@inheritDoc}
         * 
         *  这种实现允许有限的并行性。
         * 
         */
        @Override
        public Spliterator.OfDouble trySplit() {
            HoldingDoubleConsumer holder = new HoldingDoubleConsumer();
            long s = est;
            if (s > 1 && tryAdvance(holder)) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                double[] a = new double[n];
                int j = 0;
                do { a[j] = holder.value; } while (++j < n && tryAdvance(holder));
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new DoubleArraySpliterator(a, 0, j, characteristics());
            }
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the estimated size as reported when
         * created and, if the estimate size is known, decreases in size when
         * split.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的估计大小,如果估计大小已知,则拆分时大小减小。
         * 
         */
        @Override
        public long estimateSize() {
            return est;
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec
         * This implementation returns the characteristics as reported when
         * created.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec此实现返回创建时报告的特性。
         * 
         */
        @Override
        public int characteristics() {
            return characteristics;
        }
    }

    // Iterator-based Spliterators

    /**
     * A Spliterator using a given Iterator for element
     * operations. The spliterator implements {@code trySplit} to
     * permit limited parallelism.
     * <p>
     *  使用给定迭代器的Spliterator用于元素操作。 spliterator实现{@code trySplit}以允许有限的并行性。
     * 
     */
    static class IteratorSpliterator<T> implements Spliterator<T> {
        static final int BATCH_UNIT = 1 << 10;  // batch array size increment
        static final int MAX_BATCH = 1 << 25;  // max batch array size;
        private final Collection<? extends T> collection; // null OK
        private Iterator<? extends T> it;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator using the given given
         * collection's {@link java.util.Collection#iterator()) for traversal,
         * and reporting its {@link java.util.Collection#size()) as its initial
         * size.
         *
         * <p>
         *  使用给定的集合的{@link java.util.Collection#iterator())创建一个分割器进行遍历,并报告其{@link java.util.Collection#size())作为其初始大小。
         * 
         * 
         * @param c the collection
         * @param characteristics properties of this spliterator's
         *        source or elements.
         */
        public IteratorSpliterator(Collection<? extends T> collection, int characteristics) {
            this.collection = collection;
            this.it = null;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        /**
         * Creates a spliterator using the given iterator
         * for traversal, and reporting the given initial size
         * and characteristics.
         *
         * <p>
         *  使用给定的迭代器创建一个spliterator用于遍历,并报告给定的初始大小和特性。
         * 
         * 
         * @param iterator the iterator for the source
         * @param size the number of elements in the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public IteratorSpliterator(Iterator<? extends T> iterator, long size, int characteristics) {
            this.collection = null;
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        /**
         * Creates a spliterator using the given iterator
         * for traversal, and reporting the given initial size
         * and characteristics.
         *
         * <p>
         *  使用给定的迭代器创建一个spliterator用于遍历,并报告给定的初始大小和特性。
         * 
         * 
         * @param iterator the iterator for the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public IteratorSpliterator(Iterator<? extends T> iterator, int characteristics) {
            this.collection = null;
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        @Override
        public Spliterator<T> trySplit() {
            /*
             * Split into arrays of arithmetically increasing batch
             * sizes.  This will only improve parallel performance if
             * per-element Consumer actions are more costly than
             * transferring them into an array.  The use of an
             * arithmetic progression in split sizes provides overhead
             * vs parallelism bounds that do not particularly favor or
             * penalize cases of lightweight vs heavyweight element
             * operations, across combinations of #elements vs #cores,
             * whether or not either are known.  We generate
             * O(sqrt(#elements)) splits, allowing O(sqrt(#cores))
             * potential speedup.
             * <p>
             * 拆分成算术增加批量大小的数组。这只会提高并行性能,如果per-element Consumer操作比将它们转换到数组更昂贵。
             * 使用分割大小的算术级数提供了开销与并行性界限,不特别优先或惩罚轻量级与重量级元素操作的情况,不论是否为#elements与#cores的组合,无论是否已知。
             * 我们生成O(sqrt(#elements))拆分,允许O(sqrt(#cores))潜在加速。
             * 
             */
            Iterator<? extends T> i;
            long s;
            if ((i = it) == null) {
                i = it = collection.iterator();
                s = est = (long) collection.size();
            }
            else
                s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                Object[] a = new Object[n];
                int j = 0;
                do { a[j] = i.next(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new ArraySpliterator<>(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if (action == null) throw new NullPointerException();
            Iterator<? extends T> i;
            if ((i = it) == null) {
                i = it = collection.iterator();
                est = (long)collection.size();
            }
            i.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            if (action == null) throw new NullPointerException();
            if (it == null) {
                it = collection.iterator();
                est = (long) collection.size();
            }
            if (it.hasNext()) {
                action.accept(it.next());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            if (it == null) {
                it = collection.iterator();
                return est = (long)collection.size();
            }
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super T> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    /**
     * A Spliterator.OfInt using a given IntStream.IntIterator for element
     * operations. The spliterator implements {@code trySplit} to
     * permit limited parallelism.
     * <p>
     *  Spliterator.OfInt使用给定的IntStream.IntIterator元素操作。 spliterator实现{@code trySplit}以允许有限的并行性。
     * 
     */
    static final class IntIteratorSpliterator implements Spliterator.OfInt {
        static final int BATCH_UNIT = IteratorSpliterator.BATCH_UNIT;
        static final int MAX_BATCH = IteratorSpliterator.MAX_BATCH;
        private PrimitiveIterator.OfInt it;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator using the given iterator
         * for traversal, and reporting the given initial size
         * and characteristics.
         *
         * <p>
         *  使用给定的迭代器创建一个spliterator用于遍历,并报告给定的初始大小和特性。
         * 
         * 
         * @param iterator the iterator for the source
         * @param size the number of elements in the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public IntIteratorSpliterator(PrimitiveIterator.OfInt iterator, long size, int characteristics) {
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        /**
         * Creates a spliterator using the given iterator for a
         * source of unknown size, reporting the given
         * characteristics.
         *
         * <p>
         *  使用给定的迭代器为未知大小的源创建分隔符,报告给定的特征。
         * 
         * 
         * @param iterator the iterator for the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public IntIteratorSpliterator(PrimitiveIterator.OfInt iterator, int characteristics) {
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        @Override
        public OfInt trySplit() {
            PrimitiveIterator.OfInt i = it;
            long s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                int[] a = new int[n];
                int j = 0;
                do { a[j] = i.nextInt(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new IntArraySpliterator(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(IntConsumer action) {
            if (action == null) throw new NullPointerException();
            it.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (action == null) throw new NullPointerException();
            if (it.hasNext()) {
                action.accept(it.nextInt());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super Integer> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    static final class LongIteratorSpliterator implements Spliterator.OfLong {
        static final int BATCH_UNIT = IteratorSpliterator.BATCH_UNIT;
        static final int MAX_BATCH = IteratorSpliterator.MAX_BATCH;
        private PrimitiveIterator.OfLong it;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator using the given iterator
         * for traversal, and reporting the given initial size
         * and characteristics.
         *
         * <p>
         *  使用给定的迭代器创建一个spliterator用于遍历,并报告给定的初始大小和特性。
         * 
         * 
         * @param iterator the iterator for the source
         * @param size the number of elements in the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public LongIteratorSpliterator(PrimitiveIterator.OfLong iterator, long size, int characteristics) {
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        /**
         * Creates a spliterator using the given iterator for a
         * source of unknown size, reporting the given
         * characteristics.
         *
         * <p>
         *  使用给定的迭代器为未知大小的源创建分隔符,报告给定的特征。
         * 
         * 
         * @param iterator the iterator for the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public LongIteratorSpliterator(PrimitiveIterator.OfLong iterator, int characteristics) {
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        @Override
        public OfLong trySplit() {
            PrimitiveIterator.OfLong i = it;
            long s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                long[] a = new long[n];
                int j = 0;
                do { a[j] = i.nextLong(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new LongArraySpliterator(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(LongConsumer action) {
            if (action == null) throw new NullPointerException();
            it.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(LongConsumer action) {
            if (action == null) throw new NullPointerException();
            if (it.hasNext()) {
                action.accept(it.nextLong());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super Long> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }

    static final class DoubleIteratorSpliterator implements Spliterator.OfDouble {
        static final int BATCH_UNIT = IteratorSpliterator.BATCH_UNIT;
        static final int MAX_BATCH = IteratorSpliterator.MAX_BATCH;
        private PrimitiveIterator.OfDouble it;
        private final int characteristics;
        private long est;             // size estimate
        private int batch;            // batch size for splits

        /**
         * Creates a spliterator using the given iterator
         * for traversal, and reporting the given initial size
         * and characteristics.
         *
         * <p>
         *  使用给定的迭代器创建一个spliterator用于遍历,并报告给定的初始大小和特性。
         * 
         * 
         * @param iterator the iterator for the source
         * @param size the number of elements in the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, long size, int characteristics) {
            this.it = iterator;
            this.est = size;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                                   ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                                   : characteristics;
        }

        /**
         * Creates a spliterator using the given iterator for a
         * source of unknown size, reporting the given
         * characteristics.
         *
         * <p>
         *  使用给定的迭代器为未知大小的源创建分隔符,报告给定的特征。
         * 
         * @param iterator the iterator for the source
         * @param characteristics properties of this spliterator's
         * source or elements.
         */
        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble iterator, int characteristics) {
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = characteristics & ~(Spliterator.SIZED | Spliterator.SUBSIZED);
        }

        @Override
        public OfDouble trySplit() {
            PrimitiveIterator.OfDouble i = it;
            long s = est;
            if (s > 1 && i.hasNext()) {
                int n = batch + BATCH_UNIT;
                if (n > s)
                    n = (int) s;
                if (n > MAX_BATCH)
                    n = MAX_BATCH;
                double[] a = new double[n];
                int j = 0;
                do { a[j] = i.nextDouble(); } while (++j < n && i.hasNext());
                batch = j;
                if (est != Long.MAX_VALUE)
                    est -= j;
                return new DoubleArraySpliterator(a, 0, j, characteristics);
            }
            return null;
        }

        @Override
        public void forEachRemaining(DoubleConsumer action) {
            if (action == null) throw new NullPointerException();
            it.forEachRemaining(action);
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (action == null) throw new NullPointerException();
            if (it.hasNext()) {
                action.accept(it.nextDouble());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            return est;
        }

        @Override
        public int characteristics() { return characteristics; }

        @Override
        public Comparator<? super Double> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
}
