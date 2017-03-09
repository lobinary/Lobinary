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
 * Represents a function that accepts an int-valued argument and produces a
 * double-valued result.  This is the {@code int}-to-{@code double} primitive
 * specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(int)}.
 *
 * <p>
 *  表示接受一个int值参数并生成一个双值结果的函数。这是{@link Function}的{@code int} -to  -  {@ code double}原始专业化。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法为{@link #applyAsDouble(int)}。
 * 
 * 
 * @see Function
 * @since 1.8
 */
@FunctionalInterface
public interface IntToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * <p>
     * 
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(int value);
}
