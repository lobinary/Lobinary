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

/**
 * Represents a function that accepts two arguments and produces a long-valued
 * result.  This is the {@code long}-producing primitive specialization for
 * {@link BiFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(Object, Object)}.
 *
 * <p>
 *  表示接受两个参数并生成长值结果的函数。这是{@link BiFunction}的{@code long}生成原语专业化。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法为{@link #applyAsLong(Object,Object)}。
 * 
 * 
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 *
 * @see BiFunction
 * @since 1.8
 */
@FunctionalInterface
public interface ToLongBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * <p>
     * 
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLong(T t, U u);
}
