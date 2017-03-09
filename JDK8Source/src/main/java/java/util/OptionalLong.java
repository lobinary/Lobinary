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

import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a {@code long} value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code getAsLong()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(long) orElse()}
 * (return a default value if value not present) and
 * {@link #ifPresent(java.util.function.LongConsumer) ifPresent()} (execute a block
 * of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code OptionalLong} may have unpredictable results and should be avoided.
 *
 * <p>
 *  可能包含或可能不包含{@code long}值的容器对象。如果一个值存在,{@code isPresent()}将返回{@code true}和{@code getAsLong()}将返回值。
 * 
 *  <p>提供依赖于包含值是否存在的其他方法,例如{@link #orElse(long)或Else()}(如果值不存在则返回默认值)和{@link #ifPresent java.util.function.LongConsumer)ifPresent()}
 * (如果值存在,执行一个代码块)。
 * 
 *  <p>这是<a href="../lang/doc-files/ValueBased.html">以价值为基础的</a>类;对{@code OptionalLong}的实例使用身份敏感操作(包括引用相
 * 等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 
 * 
 * @since 1.8
 */
public final class OptionalLong {
    /**
     * Common instance for {@code empty()}.
     * <p>
     *  {@code empty()}的常见实例。
     * 
     */
    private static final OptionalLong EMPTY = new OptionalLong();

    /**
     * If true then the value is present, otherwise indicates no value is present
     * <p>
     *  如果为真,则该值存在,否则表示不存在值
     * 
     */
    private final boolean isPresent;
    private final long value;

    /**
     * Construct an empty instance.
     *
     * @implNote generally only one empty instance, {@link OptionalLong#EMPTY},
     * should exist per VM.
     * <p>
     *  构造一个空实例。
     * 
     *  @implNote通常只有一个空实例,{@link OptionalLong#EMPTY},应该存在每个VM。
     * 
     */
    private OptionalLong() {
        this.isPresent = false;
        this.value = 0;
    }

    /**
     * Returns an empty {@code OptionalLong} instance.  No value is present for this
     * OptionalLong.
     *
     * @apiNote Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * <p>
     *  返回一个空的{@code OptionalLong}实例。此OptionalLong不存在任何值。
     * 
     * @apiNote虽然这样做可能是诱人的,但是通过将{@code ==}与{@code Option.empty()}返回的实例进行比较,可以避免测试对象是否为空。不能保证它是一个单身人士。
     * 而应使用{@link #isPresent()}。
     * 
     * 
     *  @return an empty {@code OptionalLong}.
     */
    public static OptionalLong empty() {
        return EMPTY;
    }

    /**
     * Construct an instance with the value present.
     *
     * <p>
     *  构造具有值的实例。
     * 
     * 
     * @param value the long value to be present
     */
    private OptionalLong(long value) {
        this.isPresent = true;
        this.value = value;
    }

    /**
     * Return an {@code OptionalLong} with the specified value present.
     *
     * <p>
     *  返回具有指定值的{@code OptionalLong}。
     * 
     * 
     * @param value the value to be present
     * @return an {@code OptionalLong} with the value present
     */
    public static OptionalLong of(long value) {
        return new OptionalLong(value);
    }

    /**
     * If a value is present in this {@code OptionalLong}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * <p>
     *  如果此{@code OptionalLong}中存在值,则返回该值,否则throws {@code NoSuchElementException}。
     * 
     * 
     * @return the value held by this {@code OptionalLong}
     * @throws NoSuchElementException if there is no value present
     *
     * @see OptionalLong#isPresent()
     */
    public long getAsLong() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * <p>
     *  如果存在值,则返回{@code true},否则返回{@code false}。
     * 
     * 
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Have the specified consumer accept the value if a value is present,
     * otherwise do nothing.
     *
     * <p>
     *  如果值存在,指定的消费者接受该值,否则不做任何操作。
     * 
     * 
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     * null
     */
    public void ifPresent(LongConsumer consumer) {
        if (isPresent)
            consumer.accept(value);
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * <p>
     *  返回值(如果存在),否则返回{@code other}。
     * 
     * 
     * @param other the value to be returned if there is no value present
     * @return the value, if present, otherwise {@code other}
     */
    public long orElse(long other) {
        return isPresent ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * <p>
     *  返回值(如果存在),否则调用{@code other}并返回该调用的结果。
     * 
     * 
     * @param other a {@code LongSupplier} whose result is returned if no value
     * is present
     * @return the value if present otherwise the result of {@code other.getAsLong()}
     * @throws NullPointerException if value is not present and {@code other} is
     * null
     */
    public long orElseGet(LongSupplier other) {
        return isPresent ? value : other.getAsLong();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @apiNote A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * <p>
     *  返回包含的值(如果存在),否则抛出要由提供的供应商创建的异常。
     * 
     *  @apiNote对具有空参数列表的异常构造函数的方法引用可以用作供应商。例如,{@code IllegalStateException :: new}
     * 
     * 
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     * @throws NullPointerException if no value is present and
     * {@code exceptionSupplier} is null
     */
    public<X extends Throwable> long orElseThrow(Supplier<X> exceptionSupplier) throws X {
        if (isPresent) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this OptionalLong. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code OptionalLong} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code ==}.
     * </ul>
     *
     * <p>
     *  指示某个其他对象是否"等于"此OptionalLong。另一个对象被认为是等于：
     * <ul>
     *  <li>它也是一个{@code OptionalLong}和; <li>两个实例都没有值; <li>当前值通过{@code ==}彼此"相等"。
     * </ul>
     * 
     * 
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalLong)) {
            return false;
        }

        OptionalLong other = (OptionalLong) obj;
        return (isPresent && other.isPresent)
                ? value == other.value
                : isPresent == other.isPresent;
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * <p>
     *  返回当前值的哈希码值(如果有)或0(零)(如果没有值)。
     * 
     * 
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return isPresent ? Long.hashCode(value) : 0;
    }

    /**
     * {@inheritDoc}
     *
     * Returns a non-empty string representation of this object suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * @implSpec If a value is present the result must include its string
     * representation in the result. Empty and present instances must be
     * unambiguously differentiable.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 返回适用于调试的此对象的非空字符串表示。确切的呈现格式是未指定的,并且可以在实现和版本之间变化。
     * 
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent
                ? String.format("OptionalLong[%s]", value)
                : "OptionalLong.empty";
    }
}
