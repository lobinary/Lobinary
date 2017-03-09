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
 * Represents a function that accepts a long-valued argument and produces an
 * int-valued result.  This is the {@code long}-to-{@code int} primitive
 * specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(long)}.
 *
 * <p>
 *  表示接受长值参数并生成一个int值结果的函数。这是{@link Function}的{@code long} -to  -  {@ code int}原语专业化。
 * 
 *  <p>这是<a href="package-summary.html">功能介面</a>,其功能方法是{@link #applyAsInt(long)}。
 * 
 * 
 * @see Function
 * @since 1.8
 */
@FunctionalInterface
public interface LongToIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * <p>
     * 
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(long value);
}
