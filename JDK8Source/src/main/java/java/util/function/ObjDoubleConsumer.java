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
package java.util.function;

/**
 * Represents an operation that accepts an object-valued and a
 * {@code double}-valued argument, and returns no result.  This is the
 * {@code (reference, double)} specialization of {@link BiConsumer}.
 * Unlike most other functional interfaces, {@code ObjDoubleConsumer} is
 * expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, double)}.
 *
 * <p>
 *  表示接受对象值和{@code double}值参数的操作,并且不返回任何结果。这是{@link BiConsumer}的{@code(reference,double)}专业化。
 * 与大多数其他功能接口不同,{@code ObjDoubleConsumer}预计通过副作用操作。
 * 
 *  <p>这是一个<a href="package-summary.html">功能介面</a>,其功能方法是{@link #accept(Object,double)}。
 * 
 * @param <T> the type of the object argument to the operation
 *
 * @see BiConsumer
 * @since 1.8
 */
@FunctionalInterface
public interface ObjDoubleConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * <p>
     * 
     * 
     * @param t the first input argument
     * @param value the second input argument
     */
    void accept(T t, double value);
}
