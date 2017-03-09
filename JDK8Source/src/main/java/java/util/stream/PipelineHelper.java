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
import java.util.function.IntFunction;

/**
 * Helper class for executing <a href="package-summary.html#StreamOps">
 * stream pipelines</a>, capturing all of the information about a stream
 * pipeline (output shape, intermediate operations, stream flags, parallelism,
 * etc) in one place.
 *
 * <p>
 * A {@code PipelineHelper} describes the initial segment of a stream pipeline,
 * including its source, intermediate operations, and may additionally
 * incorporate information about the terminal (or stateful) operation which
 * follows the last intermediate operation described by this
 * {@code PipelineHelper}. The {@code PipelineHelper} is passed to the
 * {@link TerminalOp#evaluateParallel(PipelineHelper, java.util.Spliterator)},
 * {@link TerminalOp#evaluateSequential(PipelineHelper, java.util.Spliterator)},
 * and {@link AbstractPipeline#opEvaluateParallel(PipelineHelper, java.util.Spliterator,
 * java.util.function.IntFunction)}, methods, which can use the
 * {@code PipelineHelper} to access information about the pipeline such as
 * head shape, stream flags, and size, and use the helper methods
 * such as {@link #wrapAndCopyInto(Sink, Spliterator)},
 * {@link #copyInto(Sink, Spliterator)}, and {@link #wrapSink(Sink)} to execute
 * pipeline operations.
 *
 * <p>
 *  用于执行<a href="package-summary.html#StreamOps">流管道</a>的帮助程序类,用于捕获有关流管道(输出形状,中间操作,流标记,并行性等)的所有信息地点。
 * 
 * <p>
 *  {@code PipelineHelper}描述流管线的初始段,包括其源,中间操作,并且可以另外结合关于在由{@code PipelineHelper}描述的最后一个中间操作之后的终端(或有状态)操作
 * 的信息。
 *  {@code PipelineHelper}传递到{@link TerminalOp#evaluateParallel(PipelineHelper,java.util.Spliterator)},{@link TerminalOp#evaluateSequential(PipelineHelper,java.util.Spliterator)}
 * 和{@link AbstractPipeline# opEvaluateParallel(PipelineHelper,java.util.Spliterator,java.util.function.IntFunction)}
 * 方法,它们可以使用{@code PipelineHelper}来访问关于流水线的信息,例如头部形状,流标记和大小,以及使用诸如{@link #wrapAndCopyInto(Sink,Spliterator)}
 * ,{@link #copyInto(Sink,Spliterator)}和{@link #wrapSink(Sink)}等辅助方法来执行流水线操作。
 * 
 * 
 * @param <P_OUT> type of output elements from the pipeline
 * @since 1.8
 */
abstract class PipelineHelper<P_OUT> {

    /**
     * Gets the stream shape for the source of the pipeline segment.
     *
     * <p>
     *  获取流形式的流水线段的源。
     * 
     * 
     * @return the stream shape for the source of the pipeline segment.
     */
    abstract StreamShape getSourceShape();

    /**
     * Gets the combined stream and operation flags for the output of the described
     * pipeline.  This will incorporate stream flags from the stream source, all
     * the intermediate operations and the terminal operation.
     *
     * <p>
     * 获取所描述流水线的输出的组合流和操作标志。这将合并来自流源的流标志,所有中间操作和终端操作。
     * 
     * 
     * @return the combined stream and operation flags
     * @see StreamOpFlag
     */
    abstract int getStreamAndOpFlags();

    /**
     * Returns the exact output size of the portion of the output resulting from
     * applying the pipeline stages described by this {@code PipelineHelper} to
     * the the portion of the input described by the provided
     * {@code Spliterator}, if known.  If not known or known infinite, will
     * return {@code -1}.
     *
     * @apiNote
     * The exact output size is known if the {@code Spliterator} has the
     * {@code SIZED} characteristic, and the operation flags
     * {@link StreamOpFlag#SIZED} is known on the combined stream and operation
     * flags.
     *
     * <p>
     *  返回由将{@code PipelineHelper}描述的流水线阶段应用于由提供的{@code Spliterator}描述的输入部分(如果已知)产生的输出部分的确切输出大小。
     * 如果不知道或已知无限,将返回{@code -1}。
     * 
     *  @apiNote如果{@code Spliterator}具有{@code SIZED}特性,并且在组合的流和操作标志上已知操作标志{@link StreamOpFlag#SIZED},则确切的输出大
     * 小是已知的。
     * 
     * 
     * @param spliterator the spliterator describing the relevant portion of the
     *        source data
     * @return the exact size if known, or -1 if infinite or unknown
     */
    abstract<P_IN> long exactOutputSizeIfKnown(Spliterator<P_IN> spliterator);

    /**
     * Applies the pipeline stages described by this {@code PipelineHelper} to
     * the provided {@code Spliterator} and send the results to the provided
     * {@code Sink}.
     *
     * @implSpec
     * The implementation behaves as if:
     * <pre>{@code
     *     intoWrapped(wrapSink(sink), spliterator);
     * }</pre>
     *
     * <p>
     *  将{@code PipelineHelper}描述的流水线阶段应用到提供的{@code Spliterator},并将结果发送到提供的{@code Sink}。
     * 
     *  @implSpec实现行为如下：<pre> {@ code intoWrapped(wrapSink(sink),spliterator); } </pre>
     * 
     * 
     * @param sink the {@code Sink} to receive the results
     * @param spliterator the spliterator describing the source input to process
     */
    abstract<P_IN, S extends Sink<P_OUT>> S wrapAndCopyInto(S sink, Spliterator<P_IN> spliterator);

    /**
     * Pushes elements obtained from the {@code Spliterator} into the provided
     * {@code Sink}.  If the stream pipeline is known to have short-circuiting
     * stages in it (see {@link StreamOpFlag#SHORT_CIRCUIT}), the
     * {@link Sink#cancellationRequested()} is checked after each
     * element, stopping if cancellation is requested.
     *
     * @implSpec
     * This method conforms to the {@code Sink} protocol of calling
     * {@code Sink.begin} before pushing elements, via {@code Sink.accept}, and
     * calling {@code Sink.end} after all elements have been pushed.
     *
     * <p>
     *  将从{@code Spliterator}获取的元素推送到提供的{@code Sink}中。
     * 如果流管道已知具有短路阶段(参见{@link StreamOpFlag#SHORT_CIRCUIT}),则在每个元素之后检查{@link Sink#cancellationRequested()},如果
     * 请求取消,则停止。
     *  将从{@code Spliterator}获取的元素推送到提供的{@code Sink}中。
     * 
     *  @implSpec此方法符合在调用{@code Sink.begin}之前通过{@code Sink.accept}调用元素的{@code Sink}协议,并在所有元素被推入后调用{@code Sink.end}
     *  。
     * 
     * 
     * @param wrappedSink the destination {@code Sink}
     * @param spliterator the source {@code Spliterator}
     */
    abstract<P_IN> void copyInto(Sink<P_IN> wrappedSink, Spliterator<P_IN> spliterator);

    /**
     * Pushes elements obtained from the {@code Spliterator} into the provided
     * {@code Sink}, checking {@link Sink#cancellationRequested()} after each
     * element, and stopping if cancellation is requested.
     *
     * @implSpec
     * This method conforms to the {@code Sink} protocol of calling
     * {@code Sink.begin} before pushing elements, via {@code Sink.accept}, and
     * calling {@code Sink.end} after all elements have been pushed or if
     * cancellation is requested.
     *
     * <p>
     * 将从{@code Spliterator}获得的元素推送到提供的{@code Sink},在每个元素后检查{@link Sink#cancellationRequested()},如果请求取消,则停止。
     * 
     *  @implSpec此方法符合在调用{@code Sink.begin}之前通过{@code Sink.accept}调用元素的{@code Sink}协议,并在所有元素被推入后调用{@code Sink.end}
     * 或如果要求取消预订。
     * 
     * 
     * @param wrappedSink the destination {@code Sink}
     * @param spliterator the source {@code Spliterator}
     */
    abstract <P_IN> void copyIntoWithCancel(Sink<P_IN> wrappedSink, Spliterator<P_IN> spliterator);

    /**
     * Takes a {@code Sink} that accepts elements of the output type of the
     * {@code PipelineHelper}, and wrap it with a {@code Sink} that accepts
     * elements of the input type and implements all the intermediate operations
     * described by this {@code PipelineHelper}, delivering the result into the
     * provided {@code Sink}.
     *
     * <p>
     *  使用{@code Sink}接受{@code PipelineHelper}的输出类型的元素,并用接受输入类型的元素的{@code Sink}将其包装,并实现此{@ code}所描述的所有中间操作。
     * 代码PipelineHelper},将结果传递到提供的{@code Sink}。
     * 
     * 
     * @param sink the {@code Sink} to receive the results
     * @return a {@code Sink} that implements the pipeline stages and sends
     *         results to the provided {@code Sink}
     */
    abstract<P_IN> Sink<P_IN> wrapSink(Sink<P_OUT> sink);

    /**
     *
     * <p>
     * 
     * @param spliterator
     * @param <P_IN>
     * @return
     */
    abstract<P_IN> Spliterator<P_OUT> wrapSpliterator(Spliterator<P_IN> spliterator);

    /**
     * Constructs a @{link Node.Builder} compatible with the output shape of
     * this {@code PipelineHelper}.
     *
     * <p>
     *  构造与此{@code PipelineHelper}的输出形状兼容的@ {link Node.Builder}。
     * 
     * 
     * @param exactSizeIfKnown if >=0 then a builder will be created that has a
     *        fixed capacity of exactly sizeIfKnown elements; if < 0 then the
     *        builder has variable capacity.  A fixed capacity builder will fail
     *        if an element is added after the builder has reached capacity.
     * @param generator a factory function for array instances
     * @return a {@code Node.Builder} compatible with the output shape of this
     *         {@code PipelineHelper}
     */
    abstract Node.Builder<P_OUT> makeNodeBuilder(long exactSizeIfKnown,
                                                 IntFunction<P_OUT[]> generator);

    /**
     * Collects all output elements resulting from applying the pipeline stages
     * to the source {@code Spliterator} into a {@code Node}.
     *
     * @implNote
     * If the pipeline has no intermediate operations and the source is backed
     * by a {@code Node} then that {@code Node} will be returned (or flattened
     * and then returned). This reduces copying for a pipeline consisting of a
     * stateful operation followed by a terminal operation that returns an
     * array, such as:
     * <pre>{@code
     *     stream.sorted().toArray();
     * }</pre>
     *
     * <p>
     *  收集将来自{@code Spliterator}的流水线阶段应用到{@code Node}中的所有输出元素。
     * 
     *  @implNote如果管道没有中间操作,并且源由{@code Node}支持,那么{@code Node}将返回(或展平然后返回)。
     * 
     * @param spliterator the source {@code Spliterator}
     * @param flatten if true and the pipeline is a parallel pipeline then the
     *        {@code Node} returned will contain no children, otherwise the
     *        {@code Node} may represent the root in a tree that reflects the
     *        shape of the computation tree.
     * @param generator a factory function for array instances
     * @return the {@code Node} containing all output elements
     */
    abstract<P_IN> Node<P_OUT> evaluate(Spliterator<P_IN> spliterator,
                                        boolean flatten,
                                        IntFunction<P_OUT[]> generator);
}
