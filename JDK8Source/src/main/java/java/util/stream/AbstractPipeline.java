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

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * Abstract base class for "pipeline" classes, which are the core
 * implementations of the Stream interface and its primitive specializations.
 * Manages construction and evaluation of stream pipelines.
 *
 * <p>An {@code AbstractPipeline} represents an initial portion of a stream
 * pipeline, encapsulating a stream source and zero or more intermediate
 * operations.  The individual {@code AbstractPipeline} objects are often
 * referred to as <em>stages</em>, where each stage describes either the stream
 * source or an intermediate operation.
 *
 * <p>A concrete intermediate stage is generally built from an
 * {@code AbstractPipeline}, a shape-specific pipeline class which extends it
 * (e.g., {@code IntPipeline}) which is also abstract, and an operation-specific
 * concrete class which extends that.  {@code AbstractPipeline} contains most of
 * the mechanics of evaluating the pipeline, and implements methods that will be
 * used by the operation; the shape-specific classes add helper methods for
 * dealing with collection of results into the appropriate shape-specific
 * containers.
 *
 * <p>After chaining a new intermediate operation, or executing a terminal
 * operation, the stream is considered to be consumed, and no more intermediate
 * or terminal operations are permitted on this stream instance.
 *
 * @implNote
 * <p>For sequential streams, and parallel streams without
 * <a href="package-summary.html#StreamOps">stateful intermediate
 * operations</a>, parallel streams, pipeline evaluation is done in a single
 * pass that "jams" all the operations together.  For parallel streams with
 * stateful operations, execution is divided into segments, where each
 * stateful operations marks the end of a segment, and each segment is
 * evaluated separately and the result used as the input to the next
 * segment.  In all cases, the source data is not consumed until a terminal
 * operation begins.
 *
 * <p>
 *  "流水线"类的抽象基类,它是Stream接口及其基本特化的核心实现。管理河流管道的建设和评价。
 * 
 *  <p> {@code AbstractPipeline}表示流管道的初始部分,封装流源和零个或多个中间操作。
 * 各个{@code AbstractPipeline}对象通常称为<em>阶段</em>,其中每个阶段描述流源或中间操作。
 * 
 *  具体的中间阶段通常由{@code AbstractPipeline}构成,它是一个形状特定的管道类,它扩展了它(例如{@code IntPipeline}),它也是抽象的,以及一个特定于操作的具体类那
 * 。
 *  {@code AbstractPipeline}包含评估管道的大部分机制,并实现操作将使用的方法;特定于形状的类添加了帮助方法,用于将结果集合处理到适当的特定于形状的容器中。
 * 
 *  <p>在链接新的中间操作或执行终端操作之后,流被认为被消耗,并且在该流实例上不允许更多的中间或终端操作。
 * 
 * @implNote <p>对于没有<a href="package-summary.html#StreamOps">有状态中间操作</a>,并行流的顺序流和并行流,流水线评估是在单次通过中执行"jams
 * "所有操作在一起。
 * 对于具有有状态操作的并行流,执行被分成段,其中每个有状态操作标记段的结束,并且单独地计算每个段,并且结果用作下一段的输入。在所有情况下,在终端操作开始之前不消耗源数据。
 * 
 * 
 * @param <E_IN>  type of input elements
 * @param <E_OUT> type of output elements
 * @param <S> type of the subclass implementing {@code BaseStream}
 * @since 1.8
 */
abstract class AbstractPipeline<E_IN, E_OUT, S extends BaseStream<E_OUT, S>>
        extends PipelineHelper<E_OUT> implements BaseStream<E_OUT, S> {
    private static final String MSG_STREAM_LINKED = "stream has already been operated upon or closed";
    private static final String MSG_CONSUMED = "source already consumed or closed";

    /**
     * Backlink to the head of the pipeline chain (self if this is the source
     * stage).
     * <p>
     *  反向链接到管道链的头部(self,如果这是源阶段)。
     * 
     */
    @SuppressWarnings("rawtypes")
    private final AbstractPipeline sourceStage;

    /**
     * The "upstream" pipeline, or null if this is the source stage.
     * <p>
     *  "上游"流水线,如果这是源阶段,则为null。
     * 
     */
    @SuppressWarnings("rawtypes")
    private final AbstractPipeline previousStage;

    /**
     * The operation flags for the intermediate operation represented by this
     * pipeline object.
     * <p>
     *  由此管道对象表示的中间操作的操作标志。
     * 
     */
    protected final int sourceOrOpFlags;

    /**
     * The next stage in the pipeline, or null if this is the last stage.
     * Effectively final at the point of linking to the next pipeline.
     * <p>
     *  管道中的下一个阶段,如果这是最后一个阶段,则为null。在连接到下一个管道的点有效地最终。
     * 
     */
    @SuppressWarnings("rawtypes")
    private AbstractPipeline nextStage;

    /**
     * The number of intermediate operations between this pipeline object
     * and the stream source if sequential, or the previous stateful if parallel.
     * Valid at the point of pipeline preparation for evaluation.
     * <p>
     *  这个管道对象和流源之间的中间操作的数量,如果是连续的,或者如果并行,则是先前的有状态的。在管道准备评估的时候有效。
     * 
     */
    private int depth;

    /**
     * The combined source and operation flags for the source and all operations
     * up to and including the operation represented by this pipeline object.
     * Valid at the point of pipeline preparation for evaluation.
     * <p>
     *  源和所有操作的合并源和操作标志,直到并包括此管道对象表示的操作。在管道准备评估的时候有效。
     * 
     */
    private int combinedFlags;

    /**
     * The source spliterator. Only valid for the head pipeline.
     * Before the pipeline is consumed if non-null then {@code sourceSupplier}
     * must be null. After the pipeline is consumed if non-null then is set to
     * null.
     * <p>
     * 源拼接器。只对头管道有效。在非流水线消耗之前,{@code sourceSupplier}必须为null。在流水线消耗后,如果非空,则设置为null。
     * 
     */
    private Spliterator<?> sourceSpliterator;

    /**
     * The source supplier. Only valid for the head pipeline. Before the
     * pipeline is consumed if non-null then {@code sourceSpliterator} must be
     * null. After the pipeline is consumed if non-null then is set to null.
     * <p>
     *  源供应商。只对头管道有效。如果非空,则在流水线消耗之前,{@code sourceSpliterator}必须为空。在流水线消耗后,如果非空,则设置为null。
     * 
     */
    private Supplier<? extends Spliterator<?>> sourceSupplier;

    /**
     * True if this pipeline has been linked or consumed
     * <p>
     *  如果此管道已链接或已消耗,则为true
     * 
     */
    private boolean linkedOrConsumed;

    /**
     * True if there are any stateful ops in the pipeline; only valid for the
     * source stage.
     * <p>
     *  如果有任何有状态操作正在运行,则为true;仅对源阶段有效。
     * 
     */
    private boolean sourceAnyStateful;

    private Runnable sourceCloseAction;

    /**
     * True if pipeline is parallel, otherwise the pipeline is sequential; only
     * valid for the source stage.
     * <p>
     *  如果管道是并行的,则为真,否则管道是顺序的;仅对源阶段有效。
     * 
     */
    private boolean parallel;

    /**
     * Constructor for the head of a stream pipeline.
     *
     * <p>
     *  流管道头的构造函数。
     * 
     * 
     * @param source {@code Supplier<Spliterator>} describing the stream source
     * @param sourceFlags The source flags for the stream source, described in
     * {@link StreamOpFlag}
     * @param parallel True if the pipeline is parallel
     */
    AbstractPipeline(Supplier<? extends Spliterator<?>> source,
                     int sourceFlags, boolean parallel) {
        this.previousStage = null;
        this.sourceSupplier = source;
        this.sourceStage = this;
        this.sourceOrOpFlags = sourceFlags & StreamOpFlag.STREAM_MASK;
        // The following is an optimization of:
        // StreamOpFlag.combineOpFlags(sourceOrOpFlags, StreamOpFlag.INITIAL_OPS_VALUE);
        this.combinedFlags = (~(sourceOrOpFlags << 1)) & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = parallel;
    }

    /**
     * Constructor for the head of a stream pipeline.
     *
     * <p>
     *  流管道头的构造函数。
     * 
     * 
     * @param source {@code Spliterator} describing the stream source
     * @param sourceFlags the source flags for the stream source, described in
     * {@link StreamOpFlag}
     * @param parallel {@code true} if the pipeline is parallel
     */
    AbstractPipeline(Spliterator<?> source,
                     int sourceFlags, boolean parallel) {
        this.previousStage = null;
        this.sourceSpliterator = source;
        this.sourceStage = this;
        this.sourceOrOpFlags = sourceFlags & StreamOpFlag.STREAM_MASK;
        // The following is an optimization of:
        // StreamOpFlag.combineOpFlags(sourceOrOpFlags, StreamOpFlag.INITIAL_OPS_VALUE);
        this.combinedFlags = (~(sourceOrOpFlags << 1)) & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = parallel;
    }

    /**
     * Constructor for appending an intermediate operation stage onto an
     * existing pipeline.
     *
     * <p>
     *  用于将中间操作阶段附加到现有管道上的构造函数。
     * 
     * 
     * @param previousStage the upstream pipeline stage
     * @param opFlags the operation flags for the new stage, described in
     * {@link StreamOpFlag}
     */
    AbstractPipeline(AbstractPipeline<?, E_IN, ?> previousStage, int opFlags) {
        if (previousStage.linkedOrConsumed)
            throw new IllegalStateException(MSG_STREAM_LINKED);
        previousStage.linkedOrConsumed = true;
        previousStage.nextStage = this;

        this.previousStage = previousStage;
        this.sourceOrOpFlags = opFlags & StreamOpFlag.OP_MASK;
        this.combinedFlags = StreamOpFlag.combineOpFlags(opFlags, previousStage.combinedFlags);
        this.sourceStage = previousStage.sourceStage;
        if (opIsStateful())
            sourceStage.sourceAnyStateful = true;
        this.depth = previousStage.depth + 1;
    }


    // Terminal evaluation methods

    /**
     * Evaluate the pipeline with a terminal operation to produce a result.
     *
     * <p>
     *  使用终端操作评估流水线以产生结果。
     * 
     * 
     * @param <R> the type of result
     * @param terminalOp the terminal operation to be applied to the pipeline.
     * @return the result
     */
    final <R> R evaluate(TerminalOp<E_OUT, R> terminalOp) {
        assert getOutputShape() == terminalOp.inputShape();
        if (linkedOrConsumed)
            throw new IllegalStateException(MSG_STREAM_LINKED);
        linkedOrConsumed = true;

        return isParallel()
               ? terminalOp.evaluateParallel(this, sourceSpliterator(terminalOp.getOpFlags()))
               : terminalOp.evaluateSequential(this, sourceSpliterator(terminalOp.getOpFlags()));
    }

    /**
     * Collect the elements output from the pipeline stage.
     *
     * <p>
     *  收集从流水线阶段输出的元素。
     * 
     * 
     * @param generator the array generator to be used to create array instances
     * @return a flat array-backed Node that holds the collected output elements
     */
    @SuppressWarnings("unchecked")
    final Node<E_OUT> evaluateToArrayNode(IntFunction<E_OUT[]> generator) {
        if (linkedOrConsumed)
            throw new IllegalStateException(MSG_STREAM_LINKED);
        linkedOrConsumed = true;

        // If the last intermediate operation is stateful then
        // evaluate directly to avoid an extra collection step
        if (isParallel() && previousStage != null && opIsStateful()) {
            return opEvaluateParallel(previousStage, previousStage.sourceSpliterator(0), generator);
        }
        else {
            return evaluate(sourceSpliterator(0), true, generator);
        }
    }

    /**
     * Gets the source stage spliterator if this pipeline stage is the source
     * stage.  The pipeline is consumed after this method is called and
     * returns successfully.
     *
     * <p>
     *  如果此流水线级是源级,则获取源级分段器。在调用此方法并成功返回后,将耗用管道。
     * 
     * 
     * @return the source stage spliterator
     * @throws IllegalStateException if this pipeline stage is not the source
     *         stage.
     */
    @SuppressWarnings("unchecked")
    final Spliterator<E_OUT> sourceStageSpliterator() {
        if (this != sourceStage)
            throw new IllegalStateException();

        if (linkedOrConsumed)
            throw new IllegalStateException(MSG_STREAM_LINKED);
        linkedOrConsumed = true;

        if (sourceStage.sourceSpliterator != null) {
            @SuppressWarnings("unchecked")
            Spliterator<E_OUT> s = sourceStage.sourceSpliterator;
            sourceStage.sourceSpliterator = null;
            return s;
        }
        else if (sourceStage.sourceSupplier != null) {
            @SuppressWarnings("unchecked")
            Spliterator<E_OUT> s = (Spliterator<E_OUT>) sourceStage.sourceSupplier.get();
            sourceStage.sourceSupplier = null;
            return s;
        }
        else {
            throw new IllegalStateException(MSG_CONSUMED);
        }
    }

    // BaseStream

    @Override
    @SuppressWarnings("unchecked")
    public final S sequential() {
        sourceStage.parallel = false;
        return (S) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final S parallel() {
        sourceStage.parallel = true;
        return (S) this;
    }

    @Override
    public void close() {
        linkedOrConsumed = true;
        sourceSupplier = null;
        sourceSpliterator = null;
        if (sourceStage.sourceCloseAction != null) {
            Runnable closeAction = sourceStage.sourceCloseAction;
            sourceStage.sourceCloseAction = null;
            closeAction.run();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public S onClose(Runnable closeHandler) {
        Runnable existingHandler = sourceStage.sourceCloseAction;
        sourceStage.sourceCloseAction =
                (existingHandler == null)
                ? closeHandler
                : Streams.composeWithExceptions(existingHandler, closeHandler);
        return (S) this;
    }

    // Primitive specialization use co-variant overrides, hence is not final
    @Override
    @SuppressWarnings("unchecked")
    public Spliterator<E_OUT> spliterator() {
        if (linkedOrConsumed)
            throw new IllegalStateException(MSG_STREAM_LINKED);
        linkedOrConsumed = true;

        if (this == sourceStage) {
            if (sourceStage.sourceSpliterator != null) {
                @SuppressWarnings("unchecked")
                Spliterator<E_OUT> s = (Spliterator<E_OUT>) sourceStage.sourceSpliterator;
                sourceStage.sourceSpliterator = null;
                return s;
            }
            else if (sourceStage.sourceSupplier != null) {
                @SuppressWarnings("unchecked")
                Supplier<Spliterator<E_OUT>> s = (Supplier<Spliterator<E_OUT>>) sourceStage.sourceSupplier;
                sourceStage.sourceSupplier = null;
                return lazySpliterator(s);
            }
            else {
                throw new IllegalStateException(MSG_CONSUMED);
            }
        }
        else {
            return wrap(this, () -> sourceSpliterator(0), isParallel());
        }
    }

    @Override
    public final boolean isParallel() {
        return sourceStage.parallel;
    }


    /**
     * Returns the composition of stream flags of the stream source and all
     * intermediate operations.
     *
     * <p>
     *  返回流源和所有中间操作的流标志的组成。
     * 
     * 
     * @return the composition of stream flags of the stream source and all
     *         intermediate operations
     * @see StreamOpFlag
     */
    final int getStreamFlags() {
        return StreamOpFlag.toStreamFlags(combinedFlags);
    }

    /**
     * Prepare the pipeline for a parallel execution.  As the pipeline is built,
     * the flags and depth indicators are set up for a sequential execution.
     * If the execution is parallel, and there are any stateful operations, then
     * some of these need to be adjusted, as well as adjusting for flags from
     * the terminal operation (such as back-propagating UNORDERED).
     * Need not be called for a sequential execution.
     *
     * <p>
     * 准备并行执行管道。随着流水线的建立,标志和深度指示器被设置为顺序执行。
     * 如果执行是并行的,并且存在任何有状态操作,则这些操作中的一些需要被调整,以及调整来自终端操作的标志(诸如反向传播UNORDERED)。不需要调用顺序执行。
     * 
     * 
     * @param terminalFlags Operation flags for the terminal operation
     */
    private void parallelPrepare(int terminalFlags) {
        @SuppressWarnings("rawtypes")
        AbstractPipeline backPropagationHead = sourceStage;
        if (sourceStage.sourceAnyStateful) {
            int depth = 1;
            for (  @SuppressWarnings("rawtypes") AbstractPipeline u = sourceStage, p = sourceStage.nextStage;
                 p != null;
                 u = p, p = p.nextStage) {
                int thisOpFlags = p.sourceOrOpFlags;
                if (p.opIsStateful()) {
                    // If the stateful operation is a short-circuit operation
                    // then move the back propagation head forwards
                    // NOTE: there are no size-injecting ops
                    if (StreamOpFlag.SHORT_CIRCUIT.isKnown(thisOpFlags)) {
                        backPropagationHead = p;
                        // Clear the short circuit flag for next pipeline stage
                        // This stage encapsulates short-circuiting, the next
                        // stage may not have any short-circuit operations, and
                        // if so spliterator.forEachRemaining should be be used
                        // for traversal
                        thisOpFlags = thisOpFlags & ~StreamOpFlag.IS_SHORT_CIRCUIT;
                    }

                    depth = 0;
                    // The following injects size, it is equivalent to:
                    // StreamOpFlag.combineOpFlags(StreamOpFlag.IS_SIZED, p.combinedFlags);
                    thisOpFlags = (thisOpFlags & ~StreamOpFlag.NOT_SIZED) | StreamOpFlag.IS_SIZED;
                }
                p.depth = depth++;
                p.combinedFlags = StreamOpFlag.combineOpFlags(thisOpFlags, u.combinedFlags);
            }
        }

        // Apply the upstream terminal flags
        if (terminalFlags != 0) {
            int upstreamTerminalFlags = terminalFlags & StreamOpFlag.UPSTREAM_TERMINAL_OP_MASK;
            for ( @SuppressWarnings("rawtypes") AbstractPipeline p = backPropagationHead; p.nextStage != null; p = p.nextStage) {
                p.combinedFlags = StreamOpFlag.combineOpFlags(upstreamTerminalFlags, p.combinedFlags);
            }

            combinedFlags = StreamOpFlag.combineOpFlags(terminalFlags, combinedFlags);
        }
    }

    /**
     * Get the source spliterator for this pipeline stage.  For a sequential or
     * stateless parallel pipeline, this is the source spliterator.  For a
     * stateful parallel pipeline, this is a spliterator describing the results
     * of all computations up to and including the most recent stateful
     * operation.
     * <p>
     *  获取此流水线阶段的源拼贴器。对于顺序或无状态并行流水线,这是源拼贴器。对于有状态并行流水线,这是描述直到并包括最近的有状态操作的所有计算的结果的分裂器。
     * 
     */
    @SuppressWarnings("unchecked")
    private Spliterator<?> sourceSpliterator(int terminalFlags) {
        // Get the source spliterator of the pipeline
        Spliterator<?> spliterator = null;
        if (sourceStage.sourceSpliterator != null) {
            spliterator = sourceStage.sourceSpliterator;
            sourceStage.sourceSpliterator = null;
        }
        else if (sourceStage.sourceSupplier != null) {
            spliterator = (Spliterator<?>) sourceStage.sourceSupplier.get();
            sourceStage.sourceSupplier = null;
        }
        else {
            throw new IllegalStateException(MSG_CONSUMED);
        }

        if (isParallel()) {
            // @@@ Merge parallelPrepare with the loop below and use the
            //     spliterator characteristics to determine if SIZED
            //     should be injected
            parallelPrepare(terminalFlags);

            // Adapt the source spliterator, evaluating each stateful op
            // in the pipeline up to and including this pipeline stage
            for ( @SuppressWarnings("rawtypes") AbstractPipeline u = sourceStage, p = sourceStage.nextStage, e = this;
                 u != e;
                 u = p, p = p.nextStage) {

                if (p.opIsStateful()) {
                    spliterator = p.opEvaluateParallelLazy(u, spliterator);
                }
            }
        }
        else if (terminalFlags != 0)  {
            combinedFlags = StreamOpFlag.combineOpFlags(terminalFlags, combinedFlags);
        }

        return spliterator;
    }


    // PipelineHelper

    @Override
    final StreamShape getSourceShape() {
        @SuppressWarnings("rawtypes")
        AbstractPipeline p = AbstractPipeline.this;
        while (p.depth > 0) {
            p = p.previousStage;
        }
        return p.getOutputShape();
    }

    @Override
    final <P_IN> long exactOutputSizeIfKnown(Spliterator<P_IN> spliterator) {
        return StreamOpFlag.SIZED.isKnown(getStreamAndOpFlags()) ? spliterator.getExactSizeIfKnown() : -1;
    }

    @Override
    final <P_IN, S extends Sink<E_OUT>> S wrapAndCopyInto(S sink, Spliterator<P_IN> spliterator) {
        copyInto(wrapSink(Objects.requireNonNull(sink)), spliterator);
        return sink;
    }

    @Override
    final <P_IN> void copyInto(Sink<P_IN> wrappedSink, Spliterator<P_IN> spliterator) {
        Objects.requireNonNull(wrappedSink);

        if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(getStreamAndOpFlags())) {
            wrappedSink.begin(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(wrappedSink);
            wrappedSink.end();
        }
        else {
            copyIntoWithCancel(wrappedSink, spliterator);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    final <P_IN> void copyIntoWithCancel(Sink<P_IN> wrappedSink, Spliterator<P_IN> spliterator) {
        @SuppressWarnings({"rawtypes","unchecked"})
        AbstractPipeline p = AbstractPipeline.this;
        while (p.depth > 0) {
            p = p.previousStage;
        }
        wrappedSink.begin(spliterator.getExactSizeIfKnown());
        p.forEachWithCancel(spliterator, wrappedSink);
        wrappedSink.end();
    }

    @Override
    final int getStreamAndOpFlags() {
        return combinedFlags;
    }

    final boolean isOrdered() {
        return StreamOpFlag.ORDERED.isKnown(combinedFlags);
    }

    @Override
    @SuppressWarnings("unchecked")
    final <P_IN> Sink<P_IN> wrapSink(Sink<E_OUT> sink) {
        Objects.requireNonNull(sink);

        for ( @SuppressWarnings("rawtypes") AbstractPipeline p=AbstractPipeline.this; p.depth > 0; p=p.previousStage) {
            sink = p.opWrapSink(p.previousStage.combinedFlags, sink);
        }
        return (Sink<P_IN>) sink;
    }

    @Override
    @SuppressWarnings("unchecked")
    final <P_IN> Spliterator<E_OUT> wrapSpliterator(Spliterator<P_IN> sourceSpliterator) {
        if (depth == 0) {
            return (Spliterator<E_OUT>) sourceSpliterator;
        }
        else {
            return wrap(this, () -> sourceSpliterator, isParallel());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    final <P_IN> Node<E_OUT> evaluate(Spliterator<P_IN> spliterator,
                                      boolean flatten,
                                      IntFunction<E_OUT[]> generator) {
        if (isParallel()) {
            // @@@ Optimize if op of this pipeline stage is a stateful op
            return evaluateToNode(this, spliterator, flatten, generator);
        }
        else {
            Node.Builder<E_OUT> nb = makeNodeBuilder(
                    exactOutputSizeIfKnown(spliterator), generator);
            return wrapAndCopyInto(nb, spliterator).build();
        }
    }


    // Shape-specific abstract methods, implemented by XxxPipeline classes

    /**
     * Get the output shape of the pipeline.  If the pipeline is the head,
     * then it's output shape corresponds to the shape of the source.
     * Otherwise, it's output shape corresponds to the output shape of the
     * associated operation.
     *
     * <p>
     *  获取管道的输出形状。如果管道是头,则其输出形状对应于源的形状。否则,其输出形状对应于关联操作的输出形状。
     * 
     * 
     * @return the output shape
     */
    abstract StreamShape getOutputShape();

    /**
     * Collect elements output from a pipeline into a Node that holds elements
     * of this shape.
     *
     * <p>
     *  将流水线中的元素输出收集到保存此形状元素的节点中。
     * 
     * 
     * @param helper the pipeline helper describing the pipeline stages
     * @param spliterator the source spliterator
     * @param flattenTree true if the returned node should be flattened
     * @param generator the array generator
     * @return a Node holding the output of the pipeline
     */
    abstract <P_IN> Node<E_OUT> evaluateToNode(PipelineHelper<E_OUT> helper,
                                               Spliterator<P_IN> spliterator,
                                               boolean flattenTree,
                                               IntFunction<E_OUT[]> generator);

    /**
     * Create a spliterator that wraps a source spliterator, compatible with
     * this stream shape, and operations associated with a {@link
     * PipelineHelper}.
     *
     * <p>
     *  创建一个包装源拼贴器的分割器,与此流形状兼容,以及与{@link PipelineHelper}相关联的操作。
     * 
     * 
     * @param ph the pipeline helper describing the pipeline stages
     * @param supplier the supplier of a spliterator
     * @return a wrapping spliterator compatible with this shape
     */
    abstract <P_IN> Spliterator<E_OUT> wrap(PipelineHelper<E_OUT> ph,
                                            Supplier<Spliterator<P_IN>> supplier,
                                            boolean isParallel);

    /**
     * Create a lazy spliterator that wraps and obtains the supplied the
     * spliterator when a method is invoked on the lazy spliterator.
     * <p>
     *  创建一个延迟拆分器,当在延迟拆分器上调用方法时,封装和获取提供的拆分器。
     * 
     * 
     * @param supplier the supplier of a spliterator
     */
    abstract Spliterator<E_OUT> lazySpliterator(Supplier<? extends Spliterator<E_OUT>> supplier);

    /**
     * Traverse the elements of a spliterator compatible with this stream shape,
     * pushing those elements into a sink.   If the sink requests cancellation,
     * no further elements will be pulled or pushed.
     *
     * <p>
     *  遍历与此流形状兼容的分割器的元素,将这些元素推入一个sink。如果宿请求取消,则不会拉动或推送更多元素。
     * 
     * 
     * @param spliterator the spliterator to pull elements from
     * @param sink the sink to push elements to
     */
    abstract void forEachWithCancel(Spliterator<E_OUT> spliterator, Sink<E_OUT> sink);

    /**
     * Make a node builder compatible with this stream shape.
     *
     * <p>
     * 使节点构建器与此流形状兼容。
     * 
     * 
     * @param exactSizeIfKnown if {@literal >=0}, then a node builder will be
     * created that has a fixed capacity of at most sizeIfKnown elements. If
     * {@literal < 0}, then the node builder has an unfixed capacity. A fixed
     * capacity node builder will throw exceptions if an element is added after
     * builder has reached capacity, or is built before the builder has reached
     * capacity.
     *
     * @param generator the array generator to be used to create instances of a
     * T[] array. For implementations supporting primitive nodes, this parameter
     * may be ignored.
     * @return a node builder
     */
    @Override
    abstract Node.Builder<E_OUT> makeNodeBuilder(long exactSizeIfKnown,
                                                 IntFunction<E_OUT[]> generator);


    // Op-specific abstract methods, implemented by the operation class

    /**
     * Returns whether this operation is stateful or not.  If it is stateful,
     * then the method
     * {@link #opEvaluateParallel(PipelineHelper, java.util.Spliterator, java.util.function.IntFunction)}
     * must be overridden.
     *
     * <p>
     *  返回此操作是否有状态。
     * 如果它是有状态的,那么必须覆盖方法{@link #opEvaluateParallel(PipelineHelper,java.util.Spliterator,java.util.function.IntFunction)}
     * 。
     *  返回此操作是否有状态。
     * 
     * 
     * @return {@code true} if this operation is stateful
     */
    abstract boolean opIsStateful();

    /**
     * Accepts a {@code Sink} which will receive the results of this operation,
     * and return a {@code Sink} which accepts elements of the input type of
     * this operation and which performs the operation, passing the results to
     * the provided {@code Sink}.
     *
     * @apiNote
     * The implementation may use the {@code flags} parameter to optimize the
     * sink wrapping.  For example, if the input is already {@code DISTINCT},
     * the implementation for the {@code Stream#distinct()} method could just
     * return the sink it was passed.
     *
     * <p>
     *  接受将接收此操作的结果的{@code Sink},并返回{@code Sink},它接受​​此操作的输入类型的元素,并执行操作,将结果传递给提供的{@code Sink }。
     * 
     *  @apiNote实现可以使用{@code flags}参数来优化sink包。
     * 例如,如果输入已经是{@code DISTINCT},{@code Stream#distinct()}方法的实现可能只返回它传递的sink。
     * 
     * 
     * @param flags The combined stream and operation flags up to, but not
     *        including, this operation
     * @param sink sink to which elements should be sent after processing
     * @return a sink which accepts elements, perform the operation upon
     *         each element, and passes the results (if any) to the provided
     *         {@code Sink}.
     */
    abstract Sink<E_IN> opWrapSink(int flags, Sink<E_OUT> sink);

    /**
     * Performs a parallel evaluation of the operation using the specified
     * {@code PipelineHelper} which describes the upstream intermediate
     * operations.  Only called on stateful operations.  If {@link
     * #opIsStateful()} returns true then implementations must override the
     * default implementation.
     *
     * @implSpec The default implementation always throw
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  使用指定的{@code PipelineHelper}执行操作的并行评估,该操作描述上游中间操作。只有有状态的操作。
     * 如果{@link #opIsStateful()}返回true,那么实现必须覆盖默认实现。
     * 
     *  @implSpec默认实现总是抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param helper the pipeline helper describing the pipeline stages
     * @param spliterator the source {@code Spliterator}
     * @param generator the array generator
     * @return a {@code Node} describing the result of the evaluation
     */
    <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> helper,
                                          Spliterator<P_IN> spliterator,
                                          IntFunction<E_OUT[]> generator) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    /**
     * Returns a {@code Spliterator} describing a parallel evaluation of the
     * operation, using the specified {@code PipelineHelper} which describes the
     * upstream intermediate operations.  Only called on stateful operations.
     * It is not necessary (though acceptable) to do a full computation of the
     * result here; it is preferable, if possible, to describe the result via a
     * lazily evaluated spliterator.
     *
     * @implSpec The default implementation behaves as if:
     * <pre>{@code
     *     return evaluateParallel(helper, i -> (E_OUT[]) new
     * Object[i]).spliterator();
     * }</pre>
     * and is suitable for implementations that cannot do better than a full
     * synchronous evaluation.
     *
     * <p>
     * 使用描述上游中间操作的指定{@code PipelineHelper}返回描述操作的并行计算的{@code Spliterator}。只有有状态的操作。
     * 没有必要(虽然可以接受)在这里做一个完整的计算结果;如果可能,优选地通过延迟评估的分裂器来描述结果。
     * 
     * 
     * @param helper the pipeline helper
     * @param spliterator the source {@code Spliterator}
     * @return a {@code Spliterator} describing the result of the evaluation
     */
    @SuppressWarnings("unchecked")
    <P_IN> Spliterator<E_OUT> opEvaluateParallelLazy(PipelineHelper<E_OUT> helper,
                                                     Spliterator<P_IN> spliterator) {
        return opEvaluateParallel(helper, spliterator, i -> (E_OUT[]) new Object[i]).spliterator();
    }
}
