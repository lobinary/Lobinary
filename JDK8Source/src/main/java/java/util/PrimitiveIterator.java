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
 * A base type for primitive specializations of {@code Iterator}.  Specialized
 * subtypes are provided for {@link OfInt int}, {@link OfLong long}, and
 * {@link OfDouble double} values.
 *
 * <p>The specialized subtype default implementations of {@link Iterator#next}
 * and {@link Iterator#forEachRemaining(java.util.function.Consumer)} box
 * primitive values to instances of their corresponding wrapper class.  Such
 * boxing may offset any advantages gained when using the primitive
 * specializations.  To avoid boxing, the corresponding primitive-based methods
 * should be used.  For example, {@link PrimitiveIterator.OfInt#nextInt()} and
 * {@link PrimitiveIterator.OfInt#forEachRemaining(java.util.function.IntConsumer)}
 * should be used in preference to {@link PrimitiveIterator.OfInt#next()} and
 * {@link PrimitiveIterator.OfInt#forEachRemaining(java.util.function.Consumer)}.
 *
 * <p>Iteration of primitive values using boxing-based methods
 * {@link Iterator#next next()} and
 * {@link Iterator#forEachRemaining(java.util.function.Consumer) forEachRemaining()},
 * does not affect the order in which the values, transformed to boxed values,
 * are encountered.
 *
 * @implNote
 * If the boolean system property {@code org.openjdk.java.util.stream.tripwire}
 * is set to {@code true} then diagnostic warnings are reported if boxing of
 * primitive values occur when operating on primitive subtype specializations.
 *
 * <p>
 *  {@code Iterator}的基本类型的基本类型。
 * 为{@link OfInt int},{@link Of Long long}和{@link OfDouble double}值提供了专门的子类型。
 * 
 *  <p> {@link Iterator#next}和{@link Iterator#forEachRemaining(java.util.function.Consumer)}的特殊子类型默认实现框架
 * 原始值到其相应的包装类的实例。
 * 这样的拳击可以抵消当使用原始特化时获得的任何优点。为了避免装箱,应该使用相应的基于原始的方法。
 * 例如,{@link PrimitiveIterator.OfInt#nextInt()}和{@link PrimitiveIterator.OfInt#forEachRemaining(java.util.function.IntConsumer)}
 * 应该优先于{@link PrimitiveIterator.OfInt#next()}和{@link PrimitiveIterator.OfInt#forEachRemaining(java.util.function.Consumer)}
 * 。
 * 这样的拳击可以抵消当使用原始特化时获得的任何优点。为了避免装箱,应该使用相应的基于原始的方法。
 * 
 *  <p>使用基于boxing的方法迭代原始值{@link迭代器#next next()}和{@link迭代器#forEachRemaining(java.util.function.Consumer)forEachRemaining()}
 * 不影响顺序将遇到转换为方框值的值。
 * 
 *  @implNote如果布尔系统属性{@code org.openjdk.java.util.stream.tripwire}设置为{@code true},则当对原始子类型专门化操作时出现原始值的框时
 * ,将报告诊断警告。
 * 
 * 
 * @param <T> the type of elements returned by this PrimitiveIterator.  The
 *        type must be a wrapper type for a primitive type, such as
 *        {@code Integer} for the primitive {@code int} type.
 * @param <T_CONS> the type of primitive consumer.  The type must be a
 *        primitive specialization of {@link java.util.function.Consumer} for
 *        {@code T}, such as {@link java.util.function.IntConsumer} for
 *        {@code Integer}.
 *
 * @since 1.8
 */
public interface PrimitiveIterator<T, T_CONS> extends Iterator<T> {

    /**
     * Performs the given action for each remaining element, in the order
     * elements occur when iterating, until all elements have been processed
     * or the action throws an exception.  Errors or runtime exceptions
     * thrown by the action are relayed to the caller.
     *
     * <p>
     * 对每个剩余元素执行给定的操作,在元素排序中,元素会在迭代时发生,直到所有元素都被处理或操作抛出异常。操作抛出的错误或运行时异常中继到调用者。
     * 
     * 
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     */
    @SuppressWarnings("overloads")
    void forEachRemaining(T_CONS action);

    /**
     * An Iterator specialized for {@code int} values.
     * <p>
     *  一个专用于{@code int}值的迭代器。
     * 
     * 
     * @since 1.8
     */
    public static interface OfInt extends PrimitiveIterator<Integer, IntConsumer> {

        /**
         * Returns the next {@code int} element in the iteration.
         *
         * <p>
         *  返回迭代中的下一个{@code int}元素。
         * 
         * 
         * @return the next {@code int} element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        int nextInt();

        /**
         * Performs the given action for each remaining element until all elements
         * have been processed or the action throws an exception.  Actions are
         * performed in the order of iteration, if that order is specified.
         * Exceptions thrown by the action are relayed to the caller.
         *
         * @implSpec
         * <p>The default implementation behaves as if:
         * <pre>{@code
         *     while (hasNext())
         *         action.accept(nextInt());
         * }</pre>
         *
         * <p>
         *  对每个剩余元素执行给定的操作,直到所有元素都已处理或操作抛出异常。如果指定了该顺序,则按照迭代的顺序执行操作。操作抛出的异常中继到调用者。
         * 
         *  @implSpec <p>默认实现行为如下：<pre> {@ code while(hasNext())action.accept(nextInt()); } </pre>
         * 
         * 
         * @param action The action to be performed for each element
         * @throws NullPointerException if the specified action is null
         */
        default void forEachRemaining(IntConsumer action) {
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextInt());
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * The default implementation boxes the result of calling
         * {@link #nextInt()}, and returns that boxed result.
         * <p>
         *  {@inheritDoc} @implSpec默认实现框调用{@link #nextInt()}的结果,并返回盒装结果。
         * 
         */
        @Override
        default Integer next() {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()");
            return nextInt();
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code IntConsumer} then it is cast
         * to {@code IntConsumer} and passed to {@link #forEachRemaining};
         * otherwise the action is adapted to an instance of
         * {@code IntConsumer}, by boxing the argument of {@code IntConsumer},
         * and then passed to {@link #forEachRemaining}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code IntConsumer}的实例,那么它将被转换为{@code IntConsumer}并传递给{@link #forEachRemaining}
         * ;否则,通过将{@code IntConsumer}的参数加框,然后传递给{@link #forEachRemaining},该操作适用于{@code IntConsumer}的实例。
         * 
         */
        @Override
        default void forEachRemaining(Consumer<? super Integer> action) {
            if (action instanceof IntConsumer) {
                forEachRemaining((IntConsumer) action);
            }
            else {
                // The method reference action::accept is never null
                Objects.requireNonNull(action);
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
                forEachRemaining((IntConsumer) action::accept);
            }
        }

    }

    /**
     * An Iterator specialized for {@code long} values.
     * <p>
     *  专用于{@code long}值的迭代器。
     * 
     * 
     * @since 1.8
     */
    public static interface OfLong extends PrimitiveIterator<Long, LongConsumer> {

        /**
         * Returns the next {@code long} element in the iteration.
         *
         * <p>
         *  返回迭代中的下一个{@code long}元素。
         * 
         * 
         * @return the next {@code long} element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        long nextLong();

        /**
         * Performs the given action for each remaining element until all elements
         * have been processed or the action throws an exception.  Actions are
         * performed in the order of iteration, if that order is specified.
         * Exceptions thrown by the action are relayed to the caller.
         *
         * @implSpec
         * <p>The default implementation behaves as if:
         * <pre>{@code
         *     while (hasNext())
         *         action.accept(nextLong());
         * }</pre>
         *
         * <p>
         * 对每个剩余元素执行给定的操作,直到所有元素都已处理或操作抛出异常。如果指定了该顺序,则按照迭代的顺序执行操作。操作抛出的异常中继到调用者。
         * 
         *  @implSpec <p>默认实现行为如下：<pre> {@ code while(hasNext())action.accept(nextLong()); } </pre>
         * 
         * 
         * @param action The action to be performed for each element
         * @throws NullPointerException if the specified action is null
         */
        default void forEachRemaining(LongConsumer action) {
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextLong());
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * The default implementation boxes the result of calling
         * {@link #nextLong()}, and returns that boxed result.
         * <p>
         *  {@inheritDoc} @implSpec默认实现框调用{@link #nextLong()}的结果,并返回该框结果。
         * 
         */
        @Override
        default Long next() {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()");
            return nextLong();
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code LongConsumer} then it is cast
         * to {@code LongConsumer} and passed to {@link #forEachRemaining};
         * otherwise the action is adapted to an instance of
         * {@code LongConsumer}, by boxing the argument of {@code LongConsumer},
         * and then passed to {@link #forEachRemaining}.
         * <p>
         *  {@inheritDoc} @implSpec如果操作是{@code LongConsumer}的实例,那么它将被转换为{@code LongConsumer}并传递给{@link #forEachRemaining}
         * ;否则,通过将{@code LongConsumer}的参数加框,然后传递给{@link #forEachRemaining},该操作适用于{@code LongConsumer}的实例。
         * 
         */
        @Override
        default void forEachRemaining(Consumer<? super Long> action) {
            if (action instanceof LongConsumer) {
                forEachRemaining((LongConsumer) action);
            }
            else {
                // The method reference action::accept is never null
                Objects.requireNonNull(action);
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
                forEachRemaining((LongConsumer) action::accept);
            }
        }
    }

    /**
     * An Iterator specialized for {@code double} values.
     * <p>
     *  一个专用于{@code double}值的迭代器。
     * 
     * 
     * @since 1.8
     */
    public static interface OfDouble extends PrimitiveIterator<Double, DoubleConsumer> {

        /**
         * Returns the next {@code double} element in the iteration.
         *
         * <p>
         *  返回迭代中的下一个{@code double}元素。
         * 
         * 
         * @return the next {@code double} element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        double nextDouble();

        /**
         * Performs the given action for each remaining element until all elements
         * have been processed or the action throws an exception.  Actions are
         * performed in the order of iteration, if that order is specified.
         * Exceptions thrown by the action are relayed to the caller.
         *
         * @implSpec
         * <p>The default implementation behaves as if:
         * <pre>{@code
         *     while (hasNext())
         *         action.accept(nextDouble());
         * }</pre>
         *
         * <p>
         *  对每个剩余元素执行给定的操作,直到所有元素都已处理或操作抛出异常。如果指定了该顺序,则按照迭代的顺序执行操作。操作抛出的异常中继到调用者。
         * 
         *  @implSpec <p>默认实现行为如下：<pre> {@ code while(hasNext())action.accept(nextDouble()); } </pre>
         * 
         * 
         * @param action The action to be performed for each element
         * @throws NullPointerException if the specified action is null
         */
        default void forEachRemaining(DoubleConsumer action) {
            Objects.requireNonNull(action);
            while (hasNext())
                action.accept(nextDouble());
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * The default implementation boxes the result of calling
         * {@link #nextDouble()}, and returns that boxed result.
         * <p>
         *  {@inheritDoc} @implSpec默认实现框调用{@link #nextDouble()}的结果,并返回该盒装的结果。
         * 
         */
        @Override
        default Double next() {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()");
            return nextDouble();
        }

        /**
         * {@inheritDoc}
         * @implSpec
         * If the action is an instance of {@code DoubleConsumer} then it is
         * cast to {@code DoubleConsumer} and passed to
         * {@link #forEachRemaining}; otherwise the action is adapted to
         * an instance of {@code DoubleConsumer}, by boxing the argument of
         * {@code DoubleConsumer}, and then passed to
         * {@link #forEachRemaining}.
         * <p>
         * {@inheritDoc} @implSpec如果操作是{@code DoubleConsumer}的实例,则会转换为{@code DoubleConsumer}并传递给{@link #forEachRemaining}
         * ;否则,通过将{@code DoubleConsumer}的参数加框,然后传递给{@link #forEachRemaining},该操作适用于{@code DoubleConsumer}的实例。
         */
        @Override
        default void forEachRemaining(Consumer<? super Double> action) {
            if (action instanceof DoubleConsumer) {
                forEachRemaining((DoubleConsumer) action);
            }
            else {
                // The method reference action::accept is never null
                Objects.requireNonNull(action);
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
                forEachRemaining((DoubleConsumer) action::accept);
            }
        }
    }
}
