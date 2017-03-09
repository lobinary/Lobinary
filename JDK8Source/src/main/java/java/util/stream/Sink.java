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
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/**
 * An extension of {@link Consumer} used to conduct values through the stages of
 * a stream pipeline, with additional methods to manage size information,
 * control flow, etc.  Before calling the {@code accept()} method on a
 * {@code Sink} for the first time, you must first call the {@code begin()}
 * method to inform it that data is coming (optionally informing the sink how
 * much data is coming), and after all data has been sent, you must call the
 * {@code end()} method.  After calling {@code end()}, you should not call
 * {@code accept()} without again calling {@code begin()}.  {@code Sink} also
 * offers a mechanism by which the sink can cooperatively signal that it does
 * not wish to receive any more data (the {@code cancellationRequested()}
 * method), which a source can poll before sending more data to the
 * {@code Sink}.
 *
 * <p>A sink may be in one of two states: an initial state and an active state.
 * It starts out in the initial state; the {@code begin()} method transitions
 * it to the active state, and the {@code end()} method transitions it back into
 * the initial state, where it can be re-used.  Data-accepting methods (such as
 * {@code accept()} are only valid in the active state.
 *
 * @apiNote
 * A stream pipeline consists of a source, zero or more intermediate stages
 * (such as filtering or mapping), and a terminal stage, such as reduction or
 * for-each.  For concreteness, consider the pipeline:
 *
 * <pre>{@code
 *     int longestStringLengthStartingWithA
 *         = strings.stream()
 *                  .filter(s -> s.startsWith("A"))
 *                  .mapToInt(String::length)
 *                  .max();
 * }</pre>
 *
 * <p>Here, we have three stages, filtering, mapping, and reducing.  The
 * filtering stage consumes strings and emits a subset of those strings; the
 * mapping stage consumes strings and emits ints; the reduction stage consumes
 * those ints and computes the maximal value.
 *
 * <p>A {@code Sink} instance is used to represent each stage of this pipeline,
 * whether the stage accepts objects, ints, longs, or doubles.  Sink has entry
 * points for {@code accept(Object)}, {@code accept(int)}, etc, so that we do
 * not need a specialized interface for each primitive specialization.  (It
 * might be called a "kitchen sink" for this omnivorous tendency.)  The entry
 * point to the pipeline is the {@code Sink} for the filtering stage, which
 * sends some elements "downstream" -- into the {@code Sink} for the mapping
 * stage, which in turn sends integral values downstream into the {@code Sink}
 * for the reduction stage. The {@code Sink} implementations associated with a
 * given stage is expected to know the data type for the next stage, and call
 * the correct {@code accept} method on its downstream {@code Sink}.  Similarly,
 * each stage must implement the correct {@code accept} method corresponding to
 * the data type it accepts.
 *
 * <p>The specialized subtypes such as {@link Sink.OfInt} override
 * {@code accept(Object)} to call the appropriate primitive specialization of
 * {@code accept}, implement the appropriate primitive specialization of
 * {@code Consumer}, and re-abstract the appropriate primitive specialization of
 * {@code accept}.
 *
 * <p>The chaining subtypes such as {@link ChainedInt} not only implement
 * {@code Sink.OfInt}, but also maintain a {@code downstream} field which
 * represents the downstream {@code Sink}, and implement the methods
 * {@code begin()}, {@code end()}, and {@code cancellationRequested()} to
 * delegate to the downstream {@code Sink}.  Most implementations of
 * intermediate operations will use these chaining wrappers.  For example, the
 * mapping stage in the above example would look like:
 *
 * <pre>{@code
 *     IntSink is = new Sink.ChainedReference<U>(sink) {
 *         public void accept(U u) {
 *             downstream.accept(mapper.applyAsInt(u));
 *         }
 *     };
 * }</pre>
 *
 * <p>Here, we implement {@code Sink.ChainedReference<U>}, meaning that we expect
 * to receive elements of type {@code U} as input, and pass the downstream sink
 * to the constructor.  Because the next stage expects to receive integers, we
 * must call the {@code accept(int)} method when emitting values to the downstream.
 * The {@code accept()} method applies the mapping function from {@code U} to
 * {@code int} and passes the resulting value to the downstream {@code Sink}.
 *
 * <p>
 *  {@link Consumer}的扩展,用于通过流管道的各个阶段来管理值,使用额外的方法来管理大小信息,控制流等。
 * 在{@code Sink }第一次,您必须首先调用{@code begin()}方法来通知它数据来了(可选地通知接收器有多少数据来了),并且所有数据发送后,您必须调用{@code end()}方法。
 * 调用{@code end()}后,不应再调用{@code begin()},而是调用{@code accept()}。
 *  {@code Sink}还提供了一种机制,通过它,接收器可以合作地发信号通知它不希望接收任何更多的数据({@code cancellationRequested()}方法),源可以在将更多数据发送到{ @code Sink}
 * 。
 * 调用{@code end()}后,不应再调用{@code begin()},而是调用{@code accept()}。
 * 
 *  宿可以处于两种状态之一：初始状态和活动状态。它从初始状态开始; {@code begin()}方法将其转换为活动状态,而{@code end()}方法将其转换回初始状态,在那里可以重新使用。
 * 数据接受方法(例如{@code accept()}仅在活动状态下有效。
 * 
 * @apiNote流管道包括源,零个或多个中间阶段(例如过滤或映射)和终端阶段,例如reduce或for-each。为了具体,考虑管道：
 * 
 *  <pre> {@ code int longestStringLengthStartingWithA = strings.stream().filter(s  - > s.startsWith("A")).mapToInt(String :: length).max }
 *  </pre>。
 * 
 *  <p>这里,我们有三个阶段,过滤,映射和缩减。过滤阶段使用字符串并发出那些字符串的子集;映射阶段使用字符串并发出int;减少阶段消耗这些int并计算最大值。
 * 
 * <p> {@code Sink}实例用于表示此管道的每个阶段,无论阶段是否接受对象,ints,longs或double。
 *  Sink具有{@code accept(Object)},{@code accept(int)}等的入口点,因此我们不需要为每个原始特化提供专门的接口。
 *  (对于这种杂乱趋势,它可能被称为"厨房接收器"。
 * )流水线的入口点是过滤阶段的{@code Sink},它将一些元素"下游"发送到{@code Sink}对于映射阶段,其依次将积分值下游发送到用于缩减阶段的{@code Sink}。
 * 与给定阶段相关联的{@code Sink}实现应该知道下一阶段的数据类型,并在其下游{@code Sink}上调用正确的{@code accept}方法。
 * 类似地,每个阶段必须实现与它接受的数据类型相对应的正确的{@code accept}方法。
 * 
 *  <p>诸如{@link Sink.OfInt}之类的专用子类型重写{@code accept(Object)}以调用{@code accept}的适当原始特化,实现{@code Consumer}的适
 * 当原语专业化,重新抽象{@code accept}的适当的原始特化。
 * 
 * <p>链接子类型如{@link ChainedInt}不仅实现{@code Sink.OfInt},而且维护一个代表下游{@code Sink}的{@code downstream}字段,并实现方法{@代码begin()}
 * ,{@code end()}和{@code cancellationRequested()}来委托给下游{@code Sink}。
 * 大多数中间操作的实现将使用这些链接包装器。例如,上面示例中的映射阶段将如下所示：。
 * 
 *  <pre> {@ code IntSink is = new Sink.ChainedReference <U>(sink){public void accept(U u){downstream.accept(mapper.applyAsInt(u)); }
 * }; } </pre>。
 * 
 *  <p>这里,我们实现{@code Sink.ChainedReference <U>},这意味着我们期望接收{@code U}类型的元素作为输入,并将下游sink传递给构造函数。
 * 因为下一个阶段期望接收整数,所以当向下游发送值时,必须调用{@code accept(int)}方法。
 *  {@code accept()}方法将映射函数从{@code U}应用到{@code int},并将结果值传递到下游{@code Sink}。
 * 
 * 
 * @param <T> type of elements for value streams
 * @since 1.8
 */
interface Sink<T> extends Consumer<T> {
    /**
     * Resets the sink state to receive a fresh data set.  This must be called
     * before sending any data to the sink.  After calling {@link #end()},
     * you may call this method to reset the sink for another calculation.
     * <p>
     *  复位sink状态以接收新的数据集。在将任何数据发送到接收器之前必须调用此函数。调用{@link #end()}后,您可以调用此方法来重置sink以进行另一个计算。
     * 
     * 
     * @param size The exact size of the data to be pushed downstream, if
     * known or {@code -1} if unknown or infinite.
     *
     * <p>Prior to this call, the sink must be in the initial state, and after
     * this call it is in the active state.
     */
    default void begin(long size) {}

    /**
     * Indicates that all elements have been pushed.  If the {@code Sink} is
     * stateful, it should send any stored state downstream at this time, and
     * should clear any accumulated state (and associated resources).
     *
     * <p>Prior to this call, the sink must be in the active state, and after
     * this call it is returned to the initial state.
     * <p>
     *  表示所有元素都已推送。如果{@code Sink}是有状态的,它应该在此时向下游发送任何存储状态,并且应该清除任何累积状态(和相关资源)。
     * 
     * <p>在此调用之前,接收器必须处于活动状态,并且在此调用之后,它返回到初始状态。
     * 
     */
    default void end() {}

    /**
     * Indicates that this {@code Sink} does not wish to receive any more data.
     *
     * @implSpec The default implementation always returns false.
     *
     * <p>
     *  表示此{@code Sink}不希望再接收任何数据。
     * 
     *  @implSpec默认实现总是返回false。
     * 
     * 
     * @return true if cancellation is requested
     */
    default boolean cancellationRequested() {
        return false;
    }

    /**
     * Accepts an int value.
     *
     * @implSpec The default implementation throws IllegalStateException.
     *
     * <p>
     *  接受int值。
     * 
     *  @implSpec默认实现抛出IllegalStateException。
     * 
     * 
     * @throws IllegalStateException if this sink does not accept int values
     */
    default void accept(int value) {
        throw new IllegalStateException("called wrong accept method");
    }

    /**
     * Accepts a long value.
     *
     * @implSpec The default implementation throws IllegalStateException.
     *
     * <p>
     *  接受长值。
     * 
     *  @implSpec默认实现抛出IllegalStateException。
     * 
     * 
     * @throws IllegalStateException if this sink does not accept long values
     */
    default void accept(long value) {
        throw new IllegalStateException("called wrong accept method");
    }

    /**
     * Accepts a double value.
     *
     * @implSpec The default implementation throws IllegalStateException.
     *
     * <p>
     *  接受双精度值。
     * 
     *  @implSpec默认实现抛出IllegalStateException。
     * 
     * 
     * @throws IllegalStateException if this sink does not accept double values
     */
    default void accept(double value) {
        throw new IllegalStateException("called wrong accept method");
    }

    /**
     * {@code Sink} that implements {@code Sink<Integer>}, re-abstracts
     * {@code accept(int)}, and wires {@code accept(Integer)} to bridge to
     * {@code accept(int)}.
     * <p>
     *  实现{@code Sink <Integer>},重新抽象{@code accept(int)}和连线{@code accept(Integer)}以桥接到{@code accept(int)}的{@code Sink}
     * 。
     * 
     */
    interface OfInt extends Sink<Integer>, IntConsumer {
        @Override
        void accept(int value);

        @Override
        default void accept(Integer i) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            accept(i.intValue());
        }
    }

    /**
     * {@code Sink} that implements {@code Sink<Long>}, re-abstracts
     * {@code accept(long)}, and wires {@code accept(Long)} to bridge to
     * {@code accept(long)}.
     * <p>
     *  实现{@code Sink <Long>},重新抽象{@code接受(长)}和连线{@code接受(长)}到{@code接受(长)}的{@code Sink}。
     * 
     */
    interface OfLong extends Sink<Long>, LongConsumer {
        @Override
        void accept(long value);

        @Override
        default void accept(Long i) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Sink.OfLong.accept(Long)");
            accept(i.longValue());
        }
    }

    /**
     * {@code Sink} that implements {@code Sink<Double>}, re-abstracts
     * {@code accept(double)}, and wires {@code accept(Double)} to bridge to
     * {@code accept(double)}.
     * <p>
     *  实现{@code Sink <Double>},重新抽象{@code accept(double)}和{@code accept(Double)}以连接到{@code accept(double)}的
     * {@code Sink}。
     * 
     */
    interface OfDouble extends Sink<Double>, DoubleConsumer {
        @Override
        void accept(double value);

        @Override
        default void accept(Double i) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            accept(i.doubleValue());
        }
    }

    /**
     * Abstract {@code Sink} implementation for creating chains of
     * sinks.  The {@code begin}, {@code end}, and
     * {@code cancellationRequested} methods are wired to chain to the
     * downstream {@code Sink}.  This implementation takes a downstream
     * {@code Sink} of unknown input shape and produces a {@code Sink<T>}.  The
     * implementation of the {@code accept()} method must call the correct
     * {@code accept()} method on the downstream {@code Sink}.
     * <p>
     *  用于创建链接链的抽象{@code Sink}实现。
     *  {@code begin},{@code end}和{@code cancellationRequested}方法连接到下游{@code Sink}。
     * 这个实现采用未知输入形状的下游{@code Sink},并产生{@code Sink <T>}。
     *  {@code accept()}方法的实现必须在下游{@code Sink}上调用正确的{@code accept()}方法。
     * 
     */
    static abstract class ChainedReference<T, E_OUT> implements Sink<T> {
        protected final Sink<? super E_OUT> downstream;

        public ChainedReference(Sink<? super E_OUT> downstream) {
            this.downstream = Objects.requireNonNull(downstream);
        }

        @Override
        public void begin(long size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }

        @Override
        public boolean cancellationRequested() {
            return downstream.cancellationRequested();
        }
    }

    /**
     * Abstract {@code Sink} implementation designed for creating chains of
     * sinks.  The {@code begin}, {@code end}, and
     * {@code cancellationRequested} methods are wired to chain to the
     * downstream {@code Sink}.  This implementation takes a downstream
     * {@code Sink} of unknown input shape and produces a {@code Sink.OfInt}.
     * The implementation of the {@code accept()} method must call the correct
     * {@code accept()} method on the downstream {@code Sink}.
     * <p>
     * 摘要{@code Sink}实现旨在创建水槽链。 {@code begin},{@code end}和{@code cancellationRequested}方法连接到下游{@code Sink}。
     * 这个实现采用未知输入形状的下游{@code Sink},并产生{@code Sink.OfInt}。
     *  {@code accept()}方法的实现必须在下游{@code Sink}上调用正确的{@code accept()}方法。
     * 
     */
    static abstract class ChainedInt<E_OUT> implements Sink.OfInt {
        protected final Sink<? super E_OUT> downstream;

        public ChainedInt(Sink<? super E_OUT> downstream) {
            this.downstream = Objects.requireNonNull(downstream);
        }

        @Override
        public void begin(long size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }

        @Override
        public boolean cancellationRequested() {
            return downstream.cancellationRequested();
        }
    }

    /**
     * Abstract {@code Sink} implementation designed for creating chains of
     * sinks.  The {@code begin}, {@code end}, and
     * {@code cancellationRequested} methods are wired to chain to the
     * downstream {@code Sink}.  This implementation takes a downstream
     * {@code Sink} of unknown input shape and produces a {@code Sink.OfLong}.
     * The implementation of the {@code accept()} method must call the correct
     * {@code accept()} method on the downstream {@code Sink}.
     * <p>
     *  摘要{@code Sink}实现旨在创建水槽链。
     *  {@code begin},{@code end}和{@code cancellationRequested}方法连接到下游{@code Sink}。
     * 该实现采用未知输入形状的下游{@code Sink},并产生{@code Sink.OfLong}。
     *  {@code accept()}方法的实现必须在下游{@code Sink}上调用正确的{@code accept()}方法。
     * 
     */
    static abstract class ChainedLong<E_OUT> implements Sink.OfLong {
        protected final Sink<? super E_OUT> downstream;

        public ChainedLong(Sink<? super E_OUT> downstream) {
            this.downstream = Objects.requireNonNull(downstream);
        }

        @Override
        public void begin(long size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }

        @Override
        public boolean cancellationRequested() {
            return downstream.cancellationRequested();
        }
    }

    /**
     * Abstract {@code Sink} implementation designed for creating chains of
     * sinks.  The {@code begin}, {@code end}, and
     * {@code cancellationRequested} methods are wired to chain to the
     * downstream {@code Sink}.  This implementation takes a downstream
     * {@code Sink} of unknown input shape and produces a {@code Sink.OfDouble}.
     * The implementation of the {@code accept()} method must call the correct
     * {@code accept()} method on the downstream {@code Sink}.
     * <p>
     *  摘要{@code Sink}实现旨在创建水槽链。
     *  {@code begin},{@code end}和{@code cancellationRequested}方法连接到下游{@code Sink}。
     * 这个实现采用未知输入形状的下游{@code Sink},并产生{@code Sink.OfDouble}。
     */
    static abstract class ChainedDouble<E_OUT> implements Sink.OfDouble {
        protected final Sink<? super E_OUT> downstream;

        public ChainedDouble(Sink<? super E_OUT> downstream) {
            this.downstream = Objects.requireNonNull(downstream);
        }

        @Override
        public void begin(long size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }

        @Override
        public boolean cancellationRequested() {
            return downstream.cancellationRequested();
        }
    }
}
