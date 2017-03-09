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
package java.util.function;

import java.util.Objects;

/**
 * Represents an operation on a single {@code int}-valued operand that produces
 * an {@code int}-valued result.  This is the primitive type specialization of
 * {@link UnaryOperator} for {@code int}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(int)}.
 *
 * <p>
 *  表示对生成{@code int}值计算结果的单个{@code int}值操作数的操作。这是{@link UnaryOperator}的{@code int}的原始类型专门化。
 * 
 *  <p>这是<a href="package-summary.html">功能介面</a>,其功能方法为{@link #applyAsInt(int)}。
 * 
 * 
 * @see UnaryOperator
 * @since 1.8
 */
@FunctionalInterface
public interface IntUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * <p>
     *  将此运算符应用于给定的操作数。
     * 
     * 
     * @param operand the operand
     * @return the operator result
     */
    int applyAsInt(int operand);

    /**
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * <p>
     *  返回一个组合运算符,它首先将{@code before}运算符应用于其输入,然后将此运算符应用于结果。如果对任一操作符的求值抛出异常,则将其中继到组合算子的调用者。
     * 
     * 
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     * operator and then applies this operator
     * @throws NullPointerException if before is null
     *
     * @see #andThen(IntUnaryOperator)
     */
    default IntUnaryOperator compose(IntUnaryOperator before) {
        Objects.requireNonNull(before);
        return (int v) -> applyAsInt(before.applyAsInt(v));
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * <p>
     *  返回一个组合运算符,它首先将此运算符应用于其输入,然后将{@code after}运算符应用于结果。如果对任一操作符的求值抛出异常,则将其中继到组合算子的调用者。
     * 
     * 
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     * applies the {@code after} operator
     * @throws NullPointerException if after is null
     *
     * @see #compose(IntUnaryOperator)
     */
    default IntUnaryOperator andThen(IntUnaryOperator after) {
        Objects.requireNonNull(after);
        return (int t) -> after.applyAsInt(applyAsInt(t));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * <p>
     *  返回一个一元运算符,它总是返回其输入参数。
     * 
     * @return a unary operator that always returns its input argument
     */
    static IntUnaryOperator identity() {
        return t -> t;
    }
}
