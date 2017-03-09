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

import java.util.Spliterator;

/**
 * An operation in a stream pipeline that takes a stream as input and produces
 * a result or side-effect.  A {@code TerminalOp} has an input type and stream
 * shape, and a result type.  A {@code TerminalOp} also has a set of
 * <em>operation flags</em> that describes how the operation processes elements
 * of the stream (such as short-circuiting or respecting encounter order; see
 * {@link StreamOpFlag}).
 *
 * <p>A {@code TerminalOp} must provide a sequential and parallel implementation
 * of the operation relative to a given stream source and set of intermediate
 * operations.
 *
 * <p>
 *  在流管道中的操作,其将流作为输入并产生结果或副作用。 {@code TerminalOp}具有输入类型和流形状以及结果类型。
 *  {@code TerminalOp}还具有一组<em>操作标志</em>,其描述操作如何处理流的元素(例如短路或遵守遇到顺序;参见{@link StreamOpFlag})。
 * 
 *  <p> {@code TerminalOp}必须提供与给定流源和中间操作集相关的操作的顺序和并行实现。
 * 
 * 
 * @param <E_IN> the type of input elements
 * @param <R>    the type of the result
 * @since 1.8
 */
interface TerminalOp<E_IN, R> {
    /**
     * Gets the shape of the input type of this operation.
     *
     * @implSpec The default returns {@code StreamShape.REFERENCE}.
     *
     * <p>
     *  获取此操作的输入类型的形状。
     * 
     *  @implSpec默认返回{@code StreamShape.REFERENCE}。
     * 
     * 
     * @return StreamShape of the input type of this operation
     */
    default StreamShape inputShape() { return StreamShape.REFERENCE; }

    /**
     * Gets the stream flags of the operation.  Terminal operations may set a
     * limited subset of the stream flags defined in {@link StreamOpFlag}, and
     * these flags are combined with the previously combined stream and
     * intermediate operation flags for the pipeline.
     *
     * @implSpec The default implementation returns zero.
     *
     * <p>
     *  获取操作的流标志。终端操作可以设置在{@link StreamOpFlag}中定义的流标志的有限子集,并且这些标志与用于流水线的先前组合的流和中间操作标志组合。
     * 
     *  @implSpec默认实现返回零。
     * 
     * 
     * @return the stream flags for this operation
     * @see StreamOpFlag
     */
    default int getOpFlags() { return 0; }

    /**
     * Performs a parallel evaluation of the operation using the specified
     * {@code PipelineHelper}, which describes the upstream intermediate
     * operations.
     *
     * @implSpec The default performs a sequential evaluation of the operation
     * using the specified {@code PipelineHelper}.
     *
     * <p>
     *  使用指定的{@code PipelineHelper}执行操作的并行计算,它描述了上游中间操作。
     * 
     *  @implSpec默认情况下使用指定的{@code PipelineHelper}执行操作的顺序评估。
     * 
     * 
     * @param helper the pipeline helper
     * @param spliterator the source spliterator
     * @return the result of the evaluation
     */
    default <P_IN> R evaluateParallel(PipelineHelper<E_IN> helper,
                                      Spliterator<P_IN> spliterator) {
        if (Tripwire.ENABLED)
            Tripwire.trip(getClass(), "{0} triggering TerminalOp.evaluateParallel serial default");
        return evaluateSequential(helper, spliterator);
    }

    /**
     * Performs a sequential evaluation of the operation using the specified
     * {@code PipelineHelper}, which describes the upstream intermediate
     * operations.
     *
     * <p>
     * 
     * @param helper the pipeline helper
     * @param spliterator the source spliterator
     * @return the result of the evaluation
     */
    <P_IN> R evaluateSequential(PipelineHelper<E_IN> helper,
                                Spliterator<P_IN> spliterator);
}
