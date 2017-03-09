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
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;

/**
 * An immutable container for describing an ordered sequence of elements of some
 * type {@code T}.
 *
 * <p>A {@code Node} contains a fixed number of elements, which can be accessed
 * via the {@link #count}, {@link #spliterator}, {@link #forEach},
 * {@link #asArray}, or {@link #copyInto} methods.  A {@code Node} may have zero
 * or more child {@code Node}s; if it has no children (accessed via
 * {@link #getChildCount} and {@link #getChild(int)}, it is considered <em>flat
 * </em> or a <em>leaf</em>; if it has children, it is considered an
 * <em>internal</em> node.  The size of an internal node is the sum of sizes of
 * its children.
 *
 * @apiNote
 * <p>A {@code Node} typically does not store the elements directly, but instead
 * mediates access to one or more existing (effectively immutable) data
 * structures such as a {@code Collection}, array, or a set of other
 * {@code Node}s.  Commonly {@code Node}s are formed into a tree whose shape
 * corresponds to the computation tree that produced the elements that are
 * contained in the leaf nodes.  The use of {@code Node} within the stream
 * framework is largely to avoid copying data unnecessarily during parallel
 * operations.
 *
 * <p>
 *  一个不可变容器,用于描述某种类型{@code T}的元素的有序序列。
 * 
 *  <p> {@code Node}包含固定数量的元素,可以通过{@link #count},{@link #spliterator},{@link #forEach},{@link #asArray},
 * 或{@link #copyInto}方法。
 *  {@code Node}可能有零个或多个子{@code Node};如果没有子元素(通过{@link #getChildCount}和{@link #getChild(int)}访问),则视为<em>
 * 平面</em>或<em>叶</em>;如果子节点,它被认为是一个<em>内部</em>节点。
 * 内部节点的大小是其子节点的大小之和。
 * 
 *  @apiNote <p> {@code Node}通常不直接存储元素,而是介入对一个或多个现有(有效不可变)数据结构的访问,例如{@code Collection},数组或一组其他{@code节点}。
 * 通常{@codeNode}被形成为树,其形状对应于产生包含在叶节点中的元素的计算树。在流框架中使用{@code Node}主要是为了避免在并行操作期间不必要地复制数据。
 * 
 * 
 * @param <T> the type of elements.
 * @since 1.8
 */
interface Node<T> {

    /**
     * Returns a {@link Spliterator} describing the elements contained in this
     * {@code Node}.
     *
     * <p>
     *  返回{@link Spliterator},描述此{@code Node}中包含的元素。
     * 
     * 
     * @return a {@code Spliterator} describing the elements contained in this
     *         {@code Node}
     */
    Spliterator<T> spliterator();

    /**
     * Traverses the elements of this node, and invoke the provided
     * {@code Consumer} with each element.  Elements are provided in encounter
     * order if the source for the {@code Node} has a defined encounter order.
     *
     * <p>
     * 遍历此节点的元素,并为每个元素调用提供的{@code Consumer}。如果{@code Node}的源有一个定义的遇到顺序,则按遇到顺序提供元素。
     * 
     * 
     * @param consumer a {@code Consumer} that is to be invoked with each
     *        element in this {@code Node}
     */
    void forEach(Consumer<? super T> consumer);

    /**
     * Returns the number of child nodes of this node.
     *
     * @implSpec The default implementation returns zero.
     *
     * <p>
     *  返回此节点的子节点数。
     * 
     *  @implSpec默认实现返回零。
     * 
     * 
     * @return the number of child nodes
     */
    default int getChildCount() {
        return 0;
    }

    /**
     * Retrieves the child {@code Node} at a given index.
     *
     * @implSpec The default implementation always throws
     * {@code IndexOutOfBoundsException}.
     *
     * <p>
     *  在给定索引处检索子{{codeNode}}。
     * 
     *  @implSpec默认实现总是抛出{@code IndexOutOfBoundsException}。
     * 
     * 
     * @param i the index to the child node
     * @return the child node
     * @throws IndexOutOfBoundsException if the index is less than 0 or greater
     *         than or equal to the number of child nodes
     */
    default Node<T> getChild(int i) {
        throw new IndexOutOfBoundsException();
    }

    /**
     * Return a node describing a subsequence of the elements of this node,
     * starting at the given inclusive start offset and ending at the given
     * exclusive end offset.
     *
     * <p>
     *  返回描述该节点的元素的子序列的节点,从给定的包含开始偏移开始并且在给定排他结束偏移处结束。
     * 
     * 
     * @param from The (inclusive) starting offset of elements to include, must
     *             be in range 0..count().
     * @param to The (exclusive) end offset of elements to include, must be
     *           in range 0..count().
     * @param generator A function to be used to create a new array, if needed,
     *                  for reference nodes.
     * @return the truncated node
     */
    default Node<T> truncate(long from, long to, IntFunction<T[]> generator) {
        if (from == 0 && to == count())
            return this;
        Spliterator<T> spliterator = spliterator();
        long size = to - from;
        Node.Builder<T> nodeBuilder = Nodes.builder(size, generator);
        nodeBuilder.begin(size);
        for (int i = 0; i < from && spliterator.tryAdvance(e -> { }); i++) { }
        for (int i = 0; (i < size) && spliterator.tryAdvance(nodeBuilder); i++) { }
        nodeBuilder.end();
        return nodeBuilder.build();
    }

    /**
     * Provides an array view of the contents of this node.
     *
     * <p>Depending on the underlying implementation, this may return a
     * reference to an internal array rather than a copy.  Since the returned
     * array may be shared, the returned array should not be modified.  The
     * {@code generator} function may be consulted to create the array if a new
     * array needs to be created.
     *
     * <p>
     *  提供此节点的内容的数组视图。
     * 
     *  <p>根据底层实现,这可能返回对内部数组的引用,而不是副本。由于返回的数组可能是共享的,返回的数组不应该被修改。如果需要创建新数组,可以参考{@code generator}函数创建数组。
     * 
     * 
     * @param generator a factory function which takes an integer parameter and
     *        returns a new, empty array of that size and of the appropriate
     *        array type
     * @return an array containing the contents of this {@code Node}
     */
    T[] asArray(IntFunction<T[]> generator);

    /**
     * Copies the content of this {@code Node} into an array, starting at a
     * given offset into the array.  It is the caller's responsibility to ensure
     * there is sufficient room in the array, otherwise unspecified behaviour
     * will occur if the array length is less than the number of elements
     * contained in this node.
     *
     * <p>
     *  将此{@code Node}的内容复制到数组中,从数组的给定偏移量开始。调用者的责任是确保数组中有足够的空间,否则如果数组长度小于此节点中包含的元素数量,则会发生未指定的行为。
     * 
     * 
     * @param array the array into which to copy the contents of this
     *       {@code Node}
     * @param offset the starting offset within the array
     * @throws IndexOutOfBoundsException if copying would cause access of data
     *         outside array bounds
     * @throws NullPointerException if {@code array} is {@code null}
     */
    void copyInto(T[] array, int offset);

    /**
     * Gets the {@code StreamShape} associated with this {@code Node}.
     *
     * @implSpec The default in {@code Node} returns
     * {@code StreamShape.REFERENCE}
     *
     * <p>
     *  获取与此{@code Node}相关联的{@code StreamShape}。
     * 
     *  @implSpec {@code Node}中的默认值返回{@code StreamShape.REFERENCE}
     * 
     * 
     * @return the stream shape associated with this node
     */
    default StreamShape getShape() {
        return StreamShape.REFERENCE;
    }

    /**
     * Returns the number of elements contained in this node.
     *
     * <p>
     *  返回此节点中包含的元素数。
     * 
     * 
     * @return the number of elements contained in this node
     */
    long count();

    /**
     * A mutable builder for a {@code Node} that implements {@link Sink}, which
     * builds a flat node containing the elements that have been pushed to it.
     * <p>
     * 用于实现{@link Sink}的{@code Node}的可变构建器,它构建一个包含已推送到其中的元素的平面节点。
     * 
     */
    interface Builder<T> extends Sink<T> {

        /**
         * Builds the node.  Should be called after all elements have been
         * pushed and signalled with an invocation of {@link Sink#end()}.
         *
         * <p>
         *  构建节点。应该在所有元素都被推送并通过调用{@link Sink#end()}发出信号后调用。
         * 
         * 
         * @return the resulting {@code Node}
         */
        Node<T> build();

        /**
         * Specialized @{code Node.Builder} for int elements
         * <p>
         *  专门用于int元素的@ {code Node.Builder}
         * 
         */
        interface OfInt extends Node.Builder<Integer>, Sink.OfInt {
            @Override
            Node.OfInt build();
        }

        /**
         * Specialized @{code Node.Builder} for long elements
         * <p>
         *  专门用于长元素的{{Node.Builder}
         * 
         */
        interface OfLong extends Node.Builder<Long>, Sink.OfLong {
            @Override
            Node.OfLong build();
        }

        /**
         * Specialized @{code Node.Builder} for double elements
         * <p>
         *  专门用于双元素的@ {code Node.Builder}
         * 
         */
        interface OfDouble extends Node.Builder<Double>, Sink.OfDouble {
            @Override
            Node.OfDouble build();
        }
    }

    public interface OfPrimitive<T, T_CONS, T_ARR,
                                 T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>,
                                 T_NODE extends OfPrimitive<T, T_CONS, T_ARR, T_SPLITR, T_NODE>>
            extends Node<T> {

        /**
         * {@inheritDoc}
         *
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @return a {@link Spliterator.OfPrimitive} describing the elements of
         *         this node
         */
        @Override
        T_SPLITR spliterator();

        /**
         * Traverses the elements of this node, and invoke the provided
         * {@code action} with each element.
         *
         * <p>
         *  遍历这个节点的元素,并调用每个元素提供的{@code action}。
         * 
         * 
         * @param action a consumer that is to be invoked with each
         *        element in this {@code Node.OfPrimitive}
         */
        @SuppressWarnings("overloads")
        void forEach(T_CONS action);

        @Override
        default T_NODE getChild(int i) {
            throw new IndexOutOfBoundsException();
        }

        T_NODE truncate(long from, long to, IntFunction<T[]> generator);

        /**
         * {@inheritDoc}
         *
         * @implSpec the default implementation invokes the generator to create
         * an instance of a boxed primitive array with a length of
         * {@link #count()} and then invokes {@link #copyInto(T[], int)} with
         * that array at an offset of 0.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec默认实现调用生成器创建一个长度为{@link #count()}的boxed原始数组的实例,然后使用该数组调用{@link #copyInto(T [],int)}偏移量为0。
         * 
         */
        @Override
        default T[] asArray(IntFunction<T[]> generator) {
            if (java.util.stream.Tripwire.ENABLED)
                java.util.stream.Tripwire.trip(getClass(), "{0} calling Node.OfPrimitive.asArray");

            long size = count();
            if (size >= Nodes.MAX_ARRAY_SIZE)
                throw new IllegalArgumentException(Nodes.BAD_SIZE);
            T[] boxed = generator.apply((int) count());
            copyInto(boxed, 0);
            return boxed;
        }

        /**
         * Views this node as a primitive array.
         *
         * <p>Depending on the underlying implementation this may return a
         * reference to an internal array rather than a copy.  It is the callers
         * responsibility to decide if either this node or the array is utilized
         * as the primary reference for the data.</p>
         *
         * <p>
         *  将此节点视为原始数组。
         * 
         *  <p>根据底层实现,这可能返回对内部数组的引用,而不是副本。调用者负责决定是否将该节点或数组用作数据的主要引用。</p>
         * 
         * 
         * @return an array containing the contents of this {@code Node}
         */
        T_ARR asPrimitiveArray();

        /**
         * Creates a new primitive array.
         *
         * <p>
         *  创建新的基本数组。
         * 
         * 
         * @param count the length of the primitive array.
         * @return the new primitive array.
         */
        T_ARR newArray(int count);

        /**
         * Copies the content of this {@code Node} into a primitive array,
         * starting at a given offset into the array.  It is the caller's
         * responsibility to ensure there is sufficient room in the array.
         *
         * <p>
         *  将此{@code Node}的内容复制到原始数组中,从数组的给定偏移量开始。这是呼叫者的责任,以确保在阵列中有足够的空间。
         * 
         * 
         * @param array the array into which to copy the contents of this
         *              {@code Node}
         * @param offset the starting offset within the array
         * @throws IndexOutOfBoundsException if copying would cause access of
         *         data outside array bounds
         * @throws NullPointerException if {@code array} is {@code null}
         */
        void copyInto(T_ARR array, int offset);
    }

    /**
     * Specialized {@code Node} for int elements
     * <p>
     *  专门用于int元素的{@code Node}
     * 
     */
    interface OfInt extends OfPrimitive<Integer, IntConsumer, int[], Spliterator.OfInt, OfInt> {

        /**
         * {@inheritDoc}
         *
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @param consumer a {@code Consumer} that is to be invoked with each
         *        element in this {@code Node}.  If this is an
         *        {@code IntConsumer}, it is cast to {@code IntConsumer} so the
         *        elements may be processed without boxing.
         */
        @Override
        default void forEach(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                forEach((IntConsumer) consumer);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                spliterator().forEachRemaining(consumer);
            }
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec the default implementation invokes {@link #asPrimitiveArray()} to
         * obtain an int[] array then and copies the elements from that int[]
         * array into the boxed Integer[] array.  This is not efficient and it
         * is recommended to invoke {@link #copyInto(Object, int)}.
         * <p>
         *  {@inheritDoc}
         * 
         * @implSpec默认实现调用{@link #asPrimitiveArray()}获得一个int []数组,然后将该int []数组中的元素复制到框中的Integer []数组中。
         * 这不是有效的,建议调用{@link #copyInto(Object,int)}。
         * 
         */
        @Override
        default void copyInto(Integer[] boxed, int offset) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");

            int[] array = asPrimitiveArray();
            for (int i = 0; i < array.length; i++) {
                boxed[offset + i] = array[i];
            }
        }

        @Override
        default Node.OfInt truncate(long from, long to, IntFunction<Integer[]> generator) {
            if (from == 0 && to == count())
                return this;
            long size = to - from;
            Spliterator.OfInt spliterator = spliterator();
            Node.Builder.OfInt nodeBuilder = Nodes.intBuilder(size);
            nodeBuilder.begin(size);
            for (int i = 0; i < from && spliterator.tryAdvance((IntConsumer) e -> { }); i++) { }
            for (int i = 0; (i < size) && spliterator.tryAdvance((IntConsumer) nodeBuilder); i++) { }
            nodeBuilder.end();
            return nodeBuilder.build();
        }

        @Override
        default int[] newArray(int count) {
            return new int[count];
        }

        /**
         * {@inheritDoc}
         * @implSpec The default in {@code Node.OfInt} returns
         * {@code StreamShape.INT_VALUE}
         * <p>
         *  {@inheritDoc} @implSpec {@code Node.OfInt}中的默认值返回{@code StreamShape.INT_VALUE}
         * 
         */
        default StreamShape getShape() {
            return StreamShape.INT_VALUE;
        }
    }

    /**
     * Specialized {@code Node} for long elements
     * <p>
     *  为长元素专门{@code Node}
     * 
     */
    interface OfLong extends OfPrimitive<Long, LongConsumer, long[], Spliterator.OfLong, OfLong> {

        /**
         * {@inheritDoc}
         *
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @param consumer A {@code Consumer} that is to be invoked with each
         *        element in this {@code Node}.  If this is an
         *        {@code LongConsumer}, it is cast to {@code LongConsumer} so
         *        the elements may be processed without boxing.
         */
        @Override
        default void forEach(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                forEach((LongConsumer) consumer);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                spliterator().forEachRemaining(consumer);
            }
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec the default implementation invokes {@link #asPrimitiveArray()}
         * to obtain a long[] array then and copies the elements from that
         * long[] array into the boxed Long[] array.  This is not efficient and
         * it is recommended to invoke {@link #copyInto(Object, int)}.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec默认实现调用{@link #asPrimitiveArray()}获得一个long []数组,然后将这个long []数组中的元素复制到方框Long []数组中。
         * 这不是有效的,建议调用{@link #copyInto(Object,int)}。
         * 
         */
        @Override
        default void copyInto(Long[] boxed, int offset) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");

            long[] array = asPrimitiveArray();
            for (int i = 0; i < array.length; i++) {
                boxed[offset + i] = array[i];
            }
        }

        @Override
        default Node.OfLong truncate(long from, long to, IntFunction<Long[]> generator) {
            if (from == 0 && to == count())
                return this;
            long size = to - from;
            Spliterator.OfLong spliterator = spliterator();
            Node.Builder.OfLong nodeBuilder = Nodes.longBuilder(size);
            nodeBuilder.begin(size);
            for (int i = 0; i < from && spliterator.tryAdvance((LongConsumer) e -> { }); i++) { }
            for (int i = 0; (i < size) && spliterator.tryAdvance((LongConsumer) nodeBuilder); i++) { }
            nodeBuilder.end();
            return nodeBuilder.build();
        }

        @Override
        default long[] newArray(int count) {
            return new long[count];
        }

        /**
         * {@inheritDoc}
         * @implSpec The default in {@code Node.OfLong} returns
         * {@code StreamShape.LONG_VALUE}
         * <p>
         *  {@inheritDoc} @implSpec {@code Node.OfLong}中的默认值会返回{@code StreamShape.LONG_VALUE}
         * 
         */
        default StreamShape getShape() {
            return StreamShape.LONG_VALUE;
        }
    }

    /**
     * Specialized {@code Node} for double elements
     * <p>
     *  专门用于{@code Node}的双元素
     * 
     */
    interface OfDouble extends OfPrimitive<Double, DoubleConsumer, double[], Spliterator.OfDouble, OfDouble> {

        /**
         * {@inheritDoc}
         *
         * <p>
         *  {@inheritDoc}
         * 
         * 
         * @param consumer A {@code Consumer} that is to be invoked with each
         *        element in this {@code Node}.  If this is an
         *        {@code DoubleConsumer}, it is cast to {@code DoubleConsumer}
         *        so the elements may be processed without boxing.
         */
        @Override
        default void forEach(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                forEach((DoubleConsumer) consumer);
            }
            else {
                if (Tripwire.ENABLED)
                    Tripwire.trip(getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                spliterator().forEachRemaining(consumer);
            }
        }

        //

        /**
         * {@inheritDoc}
         *
         * @implSpec the default implementation invokes {@link #asPrimitiveArray()}
         * to obtain a double[] array then and copies the elements from that
         * double[] array into the boxed Double[] array.  This is not efficient
         * and it is recommended to invoke {@link #copyInto(Object, int)}.
         * <p>
         *  {@inheritDoc}
         * 
         *  @implSpec默认实现调用{@link #asPrimitiveArray()}获得一个double []数组,然后将该double []数组中的元素复制到方框Double []数组中。
         * 这不是有效的,建议调用{@link #copyInto(Object,int)}。
         * 
         */
        @Override
        default void copyInto(Double[] boxed, int offset) {
            if (Tripwire.ENABLED)
                Tripwire.trip(getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");

            double[] array = asPrimitiveArray();
            for (int i = 0; i < array.length; i++) {
                boxed[offset + i] = array[i];
            }
        }

        @Override
        default Node.OfDouble truncate(long from, long to, IntFunction<Double[]> generator) {
            if (from == 0 && to == count())
                return this;
            long size = to - from;
            Spliterator.OfDouble spliterator = spliterator();
            Node.Builder.OfDouble nodeBuilder = Nodes.doubleBuilder(size);
            nodeBuilder.begin(size);
            for (int i = 0; i < from && spliterator.tryAdvance((DoubleConsumer) e -> { }); i++) { }
            for (int i = 0; (i < size) && spliterator.tryAdvance((DoubleConsumer) nodeBuilder); i++) { }
            nodeBuilder.end();
            return nodeBuilder.build();
        }

        @Override
        default double[] newArray(int count) {
            return new double[count];
        }

        /**
         * {@inheritDoc}
         *
         * @implSpec The default in {@code Node.OfDouble} returns
         * {@code StreamShape.DOUBLE_VALUE}
         * <p>
         */
        default StreamShape getShape() {
            return StreamShape.DOUBLE_VALUE;
        }
    }
}
