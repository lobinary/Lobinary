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
package java.util.stream;

/**
 * An enum describing the known shape specializations for stream abstractions.
 * Each will correspond to a specific subinterface of {@link BaseStream}
 * (e.g., {@code REFERENCE} corresponds to {@code Stream}, {@code INT_VALUE}
 * corresponds to {@code IntStream}).  Each may also correspond to
 * specializations of value-handling abstractions such as {@code Spliterator},
 * {@code Consumer}, etc.
 *
 * @apiNote
 * This enum is used by implementations to determine compatibility between
 * streams and operations (i.e., if the output shape of a stream is compatible
 * with the input shape of the next operation).
 *
 * <p>Some APIs require you to specify both a generic type and a stream shape
 * for input or output elements, such as {@link TerminalOp} which has both
 * generic type parameters for its input types, and a getter for the
 * input shape.  When representing primitive streams in this way, the
 * generic type parameter should correspond to the wrapper type for that
 * primitive type.
 *
 * <p>
 *  描述流抽象的已知形状特化的枚举。
 * 每个将对应于{@link BaseStream}的特定子接口(例如,{@code REFERENCE}对应于{@code Stream},{@code INT_VALUE}对应于{@code IntStream}
 * )。
 *  描述流抽象的已知形状特化的枚举。每个也可以对应于价值处理抽象的特殊化,例如{@code Spliterator},{@code Consumer}等。
 * 
 *  @apiNote此枚举由实现使用,以确定流和操作之间的兼容性(即,如果流的输出形状与下一个操作的输入形状兼容)。
 * 
 *  <p>一些API要求您为输入或输出元素指定泛型类型和流形状,例如{@link TerminalOp}(其输入类型具有通用类型参数)和输入形状的getter。
 * 当以这种方式表示原始流时,通用类型参数应该对应于该原始类型的包装类型。
 * 
 * 
 * @since 1.8
 */
enum StreamShape {
    /**
     * The shape specialization corresponding to {@code Stream} and elements
     * that are object references.
     * <p>
     *  对应于{@code Stream}的形状专业化以及对象引用的元素。
     * 
     */
    REFERENCE,
    /**
     * The shape specialization corresponding to {@code IntStream} and elements
     * that are {@code int} values.
     * <p>
     *  对应于{@code IntStream}的形状特殊化和{@code int}值的元素。
     * 
     */
    INT_VALUE,
    /**
     * The shape specialization corresponding to {@code LongStream} and elements
     * that are {@code long} values.
     * <p>
     *  对应于{@code LongStream}的形状专业化和是{@code long}值的元素。
     * 
     */
    LONG_VALUE,
    /**
     * The shape specialization corresponding to {@code DoubleStream} and
     * elements that are {@code double} values.
     * <p>
     * 对应于{@code DoubleStream}的形状特殊化和{@code double}值的元素。
     */
    DOUBLE_VALUE
}
