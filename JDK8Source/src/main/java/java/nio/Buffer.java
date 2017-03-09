/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio;

import java.util.Spliterator;

/**
 * A container for data of a specific primitive type.
 *
 * <p> A buffer is a linear, finite sequence of elements of a specific
 * primitive type.  Aside from its content, the essential properties of a
 * buffer are its capacity, limit, and position: </p>
 *
 * <blockquote>
 *
 *   <p> A buffer's <i>capacity</i> is the number of elements it contains.  The
 *   capacity of a buffer is never negative and never changes.  </p>
 *
 *   <p> A buffer's <i>limit</i> is the index of the first element that should
 *   not be read or written.  A buffer's limit is never negative and is never
 *   greater than its capacity.  </p>
 *
 *   <p> A buffer's <i>position</i> is the index of the next element to be
 *   read or written.  A buffer's position is never negative and is never
 *   greater than its limit.  </p>
 *
 * </blockquote>
 *
 * <p> There is one subclass of this class for each non-boolean primitive type.
 *
 *
 * <h2> Transferring data </h2>
 *
 * <p> Each subclass of this class defines two categories of <i>get</i> and
 * <i>put</i> operations: </p>
 *
 * <blockquote>
 *
 *   <p> <i>Relative</i> operations read or write one or more elements starting
 *   at the current position and then increment the position by the number of
 *   elements transferred.  If the requested transfer exceeds the limit then a
 *   relative <i>get</i> operation throws a {@link BufferUnderflowException}
 *   and a relative <i>put</i> operation throws a {@link
 *   BufferOverflowException}; in either case, no data is transferred.  </p>
 *
 *   <p> <i>Absolute</i> operations take an explicit element index and do not
 *   affect the position.  Absolute <i>get</i> and <i>put</i> operations throw
 *   an {@link IndexOutOfBoundsException} if the index argument exceeds the
 *   limit.  </p>
 *
 * </blockquote>
 *
 * <p> Data may also, of course, be transferred in to or out of a buffer by the
 * I/O operations of an appropriate channel, which are always relative to the
 * current position.
 *
 *
 * <h2> Marking and resetting </h2>
 *
 * <p> A buffer's <i>mark</i> is the index to which its position will be reset
 * when the {@link #reset reset} method is invoked.  The mark is not always
 * defined, but when it is defined it is never negative and is never greater
 * than the position.  If the mark is defined then it is discarded when the
 * position or the limit is adjusted to a value smaller than the mark.  If the
 * mark is not defined then invoking the {@link #reset reset} method causes an
 * {@link InvalidMarkException} to be thrown.
 *
 *
 * <h2> Invariants </h2>
 *
 * <p> The following invariant holds for the mark, position, limit, and
 * capacity values:
 *
 * <blockquote>
 *     <tt>0</tt> <tt>&lt;=</tt>
 *     <i>mark</i> <tt>&lt;=</tt>
 *     <i>position</i> <tt>&lt;=</tt>
 *     <i>limit</i> <tt>&lt;=</tt>
 *     <i>capacity</i>
 * </blockquote>
 *
 * <p> A newly-created buffer always has a position of zero and a mark that is
 * undefined.  The initial limit may be zero, or it may be some other value
 * that depends upon the type of the buffer and the manner in which it is
 * constructed.  Each element of a newly-allocated buffer is initialized
 * to zero.
 *
 *
 * <h2> Clearing, flipping, and rewinding </h2>
 *
 * <p> In addition to methods for accessing the position, limit, and capacity
 * values and for marking and resetting, this class also defines the following
 * operations upon buffers:
 *
 * <ul>
 *
 *   <li><p> {@link #clear} makes a buffer ready for a new sequence of
 *   channel-read or relative <i>put</i> operations: It sets the limit to the
 *   capacity and the position to zero.  </p></li>
 *
 *   <li><p> {@link #flip} makes a buffer ready for a new sequence of
 *   channel-write or relative <i>get</i> operations: It sets the limit to the
 *   current position and then sets the position to zero.  </p></li>
 *
 *   <li><p> {@link #rewind} makes a buffer ready for re-reading the data that
 *   it already contains: It leaves the limit unchanged and sets the position
 *   to zero.  </p></li>
 *
 * </ul>
 *
 *
 * <h2> Read-only buffers </h2>
 *
 * <p> Every buffer is readable, but not every buffer is writable.  The
 * mutation methods of each buffer class are specified as <i>optional
 * operations</i> that will throw a {@link ReadOnlyBufferException} when
 * invoked upon a read-only buffer.  A read-only buffer does not allow its
 * content to be changed, but its mark, position, and limit values are mutable.
 * Whether or not a buffer is read-only may be determined by invoking its
 * {@link #isReadOnly isReadOnly} method.
 *
 *
 * <h2> Thread safety </h2>
 *
 * <p> Buffers are not safe for use by multiple concurrent threads.  If a
 * buffer is to be used by more than one thread then access to the buffer
 * should be controlled by appropriate synchronization.
 *
 *
 * <h2> Invocation chaining </h2>
 *
 * <p> Methods in this class that do not otherwise have a value to return are
 * specified to return the buffer upon which they are invoked.  This allows
 * method invocations to be chained; for example, the sequence of statements
 *
 * <blockquote><pre>
 * b.flip();
 * b.position(23);
 * b.limit(42);</pre></blockquote>
 *
 * can be replaced by the single, more compact statement
 *
 * <blockquote><pre>
 * b.flip().position(23).limit(42);</pre></blockquote>
 *
 *
 * <p>
 *  用于特定原始类型的数据的容器。
 * 
 *  <p>缓冲区是特定原始类型的元素的线性有限序列。除了它的内容,缓冲区的基本属性是它的容量,极限和位置：</p>
 * 
 * <blockquote>
 * 
 *  <p>缓冲区的<i>容量</i>是其包含的元素数量。缓冲区的容量从不为负,从不改变。 </p>
 * 
 *  <p>缓冲区的<i>限制</i>是不应读取或写入的第一个元素的索引。缓冲区的限制永远不是负的,永远不会大于其容量。 </p>
 * 
 *  <p>缓冲区<i>位置</i>是要读取或写入的下一个元素的索引。缓冲区的位置从不为负,并且永远不会大于其限制。 </p>
 * 
 * </blockquote>
 * 
 *  <p>每个非布尔基元类型都有一个这个类的子类。
 * 
 *  <h2>传输数据</h2>
 * 
 *  <p>此类的每个子类定义<i> get </i>和<i> put </i>操作的两个类别：</p>
 * 
 * <blockquote>
 * 
 * <p> <i>相对</i>操作读取或写入从当前位置开始的一个或多个元素,然后将位置增加传送的元素数量。
 * 如果请求的传输超过限制,则相对<i> get </i>操作抛出{@link BufferUnderflowException}和相对</i>操作抛出{@link BufferOverflowException}
 * ;在任一情况下,不传送数据。
 * <p> <i>相对</i>操作读取或写入从当前位置开始的一个或多个元素,然后将位置增加传送的元素数量。 </p>。
 * 
 *  <p> <i>绝对</i>操作采用明确的元素索引,不影响位置。
 * 如果索引参数超过限制,则绝对<i> get </i>和<i> put </i>操作将抛出一个{@link IndexOutOfBoundsException}。 </p>。
 * 
 * </blockquote>
 * 
 *  当然,数据也可以通过适当通道的I / O操作(其总是相对于当前位置)传送到缓冲器或从缓冲器传出。
 * 
 *  <h2>标记和重置</h2>
 * 
 *  <p>缓冲区的<i>标记</i>是在调用{@link #reset reset}方法时,其位置将被重置的索引。标记不总是定义的,但是当它被定义时,它从不是负的,并且从不大于位置。
 * 如果定义了标记,则当位置或限制被调整为小于标记的值时,则丢弃该标记。
 * 如果没有定义标记,那么调用{@link #reset reset}方法会抛出{@link InvalidMarkException}。
 * 
 *  <h2>不变量</h2>
 * 
 *  <p>标记,位置,限制和容量值的以下不变式成立：
 * 
 * <blockquote>
 * <tt> 0 </tt> <tt> <= </tt> <i>标记</i> <tt> <= </tt> <i> position </i> <tt> </tt> <i>限制</i> <tt>&lt; = 
 * </tt> <i>容量</i>。
 * </blockquote>
 * 
 *  <p>新创建的缓冲区始终具有零位置和未定义的标记。初始限制可以是零,或者它可以是取决于缓冲器的类型和其被构造的方式的一些其它值。新分配的缓冲器的每个元素被初始化为零。
 * 
 *  <h2>清除,翻转和倒回</h2>
 * 
 *  <p>除了访问位置,限制和容量值以及标记和重置的方法之外,此类还定义了对缓冲区的以下操作：
 * 
 * <ul>
 * 
 *  <li> <p> {@link #clear}为新的通道读取或相对</i>操作序列准备了一个缓冲区：它将容量限制和位置设置为零。 </p> </li>
 * 
 *  <li> <p> {@link #flip}为新的通道写入或相对<i> get </i>操作准备一个缓冲区：它将限制设置为当前位置,然后将位置设置为零。 </p> </li>
 * 
 *  <li> <p> {@link #rewind}使缓冲区准备好重新读取其已包含的数据：它保持不变的限制,并将位置设置为零。 </p> </li>
 * 
 * </ul>
 * 
 *  <h2>只读缓冲区</h2>
 * 
 * <p>每个缓冲区都是可读的,但不是每个缓冲区都是可写的。
 * 每个缓冲区类的突变方法被指定为<i>可选操作</i>,当在只读缓冲区上调用时将抛出{@link ReadOnlyBufferException}。
 * 只读缓冲区不允许更改其内容,但其标记,位置和限制值是可变的。缓冲器是否是只读的可以通过调用其{@link #isReadOnly isReadOnly}方法来确定。
 * 
 *  <h2>线程安全</h2>
 * 
 *  <p>缓冲区不适用于多个并发线程。如果一个缓冲区要由多个线程使用,则应该通过适当的同步来控制对缓冲区的访问。
 * 
 *  <h2>调用链</h2>
 * 
 *  <p>此类中没有返回值的方法被指定为返回调用它们的缓冲区。这允许方法调用链接;例如,语句的序列
 * 
 *  <blockquote> <pre> b.flip(); b.position(23); b.limit(42); </pre> </blockquote>
 * 
 *  可以由单个,更紧凑的语句替换
 * 
 *  <blockquote> <pre> b.flip()。position(23).limit(42); </pre> </blockquote>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class Buffer {

    /**
     * The characteristics of Spliterators that traverse and split elements
     * maintained in Buffers.
     * <p>
     *  遍历和拆分缓冲区中维护的元素的Spliterators的特性。
     * 
     */
    static final int SPLITERATOR_CHARACTERISTICS =
        Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.ORDERED;

    // Invariants: mark <= position <= limit <= capacity
    private int mark = -1;
    private int position = 0;
    private int limit;
    private int capacity;

    // Used only by direct buffers
    // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
    long address;

    // Creates a new buffer with the given mark, position, limit, and capacity,
    // after checking invariants.
    //
    Buffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
        limit(lim);
        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                                                   + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }

    /**
     * Returns this buffer's capacity.
     *
     * <p>
     *  返回此缓冲区的容量。
     * 
     * 
     * @return  The capacity of this buffer
     */
    public final int capacity() {
        return capacity;
    }

    /**
     * Returns this buffer's position.
     *
     * <p>
     *  返回此缓冲区的位置。
     * 
     * 
     * @return  The position of this buffer
     */
    public final int position() {
        return position;
    }

    /**
     * Sets this buffer's position.  If the mark is defined and larger than the
     * new position then it is discarded.
     *
     * <p>
     *  设置此缓冲区的位置。如果标记被定义并大于新位置,则它被丢弃。
     * 
     * 
     * @param  newPosition
     *         The new position value; must be non-negative
     *         and no larger than the current limit
     *
     * @return  This buffer
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on <tt>newPosition</tt> do not hold
     */
    public final Buffer position(int newPosition) {
        if ((newPosition > limit) || (newPosition < 0))
            throw new IllegalArgumentException();
        position = newPosition;
        if (mark > position) mark = -1;
        return this;
    }

    /**
     * Returns this buffer's limit.
     *
     * <p>
     *  返回此缓冲区的限制。
     * 
     * 
     * @return  The limit of this buffer
     */
    public final int limit() {
        return limit;
    }

    /**
     * Sets this buffer's limit.  If the position is larger than the new limit
     * then it is set to the new limit.  If the mark is defined and larger than
     * the new limit then it is discarded.
     *
     * <p>
     * 设置此缓冲区的限制。如果位置大于新限制,则将其设置为新限制。如果标记已定义且大于新限制,则将其丢弃。
     * 
     * 
     * @param  newLimit
     *         The new limit value; must be non-negative
     *         and no larger than this buffer's capacity
     *
     * @return  This buffer
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on <tt>newLimit</tt> do not hold
     */
    public final Buffer limit(int newLimit) {
        if ((newLimit > capacity) || (newLimit < 0))
            throw new IllegalArgumentException();
        limit = newLimit;
        if (position > limit) position = limit;
        if (mark > limit) mark = -1;
        return this;
    }

    /**
     * Sets this buffer's mark at its position.
     *
     * <p>
     *  将此缓冲区的标记设置在其位置。
     * 
     * 
     * @return  This buffer
     */
    public final Buffer mark() {
        mark = position;
        return this;
    }

    /**
     * Resets this buffer's position to the previously-marked position.
     *
     * <p> Invoking this method neither changes nor discards the mark's
     * value. </p>
     *
     * <p>
     *  将此缓冲器的位置重置为先前标记的位置。
     * 
     *  <p>调用此方法既不会更改也不会丢弃商标的值。 </p>
     * 
     * 
     * @return  This buffer
     *
     * @throws  InvalidMarkException
     *          If the mark has not been set
     */
    public final Buffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }

    /**
     * Clears this buffer.  The position is set to zero, the limit is set to
     * the capacity, and the mark is discarded.
     *
     * <p> Invoke this method before using a sequence of channel-read or
     * <i>put</i> operations to fill this buffer.  For example:
     *
     * <blockquote><pre>
     * buf.clear();     // Prepare buffer for reading
     * in.read(buf);    // Read data</pre></blockquote>
     *
     * <p> This method does not actually erase the data in the buffer, but it
     * is named as if it did because it will most often be used in situations
     * in which that might as well be the case. </p>
     *
     * <p>
     *  清除此缓冲区。位置设置为零,极限设置为容量,标记将被丢弃。
     * 
     *  <p>在使用通道读取或<i> put </i>操作序列填充此缓冲区之前调用此方法。例如：
     * 
     *  <blockquote> <pre> buf.clear(); //准备读取缓冲区in.read(buf); //读取数据</pre> </blockquote>
     * 
     *  <p>这种方法实际上并不擦除缓冲区中的数据,但它被命名为它的确如此,因为它最常用于可能情况下的情况。 </p>
     * 
     * 
     * @return  This buffer
     */
    public final Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }

    /**
     * Flips this buffer.  The limit is set to the current position and then
     * the position is set to zero.  If the mark is defined then it is
     * discarded.
     *
     * <p> After a sequence of channel-read or <i>put</i> operations, invoke
     * this method to prepare for a sequence of channel-write or relative
     * <i>get</i> operations.  For example:
     *
     * <blockquote><pre>
     * buf.put(magic);    // Prepend header
     * in.read(buf);      // Read data into rest of buffer
     * buf.flip();        // Flip buffer
     * out.write(buf);    // Write header + data to channel</pre></blockquote>
     *
     * <p> This method is often used in conjunction with the {@link
     * java.nio.ByteBuffer#compact compact} method when transferring data from
     * one place to another.  </p>
     *
     * <p>
     *  翻转此缓冲区。将限制设置为当前位置,然后将位置设置为零。如果定义了标记,则将其丢弃。
     * 
     *  <p>在一系列通道读取或</i>操作之后,调用此方法以准备通道写入或相对获取</i>操作的序列。例如：
     * 
     *  <blockquote> <pre> buf.put(magic); // Prepend header in.read(buf); //将数据读入缓冲区的其余部分buf.flip(); // Fli
     * p buffer out.write(buf); //将标题+数据写入通道</pre> </blockquote>。
     * 
     * <p>在将数据从一个地方传输到另一个地方时,此方法通常与{@link java.nio.ByteBuffer#compact compact}方法结合使用。 </p>
     * 
     * 
     * @return  This buffer
     */
    public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Rewinds this buffer.  The position is set to zero and the mark is
     * discarded.
     *
     * <p> Invoke this method before a sequence of channel-write or <i>get</i>
     * operations, assuming that the limit has already been set
     * appropriately.  For example:
     *
     * <blockquote><pre>
     * out.write(buf);    // Write remaining data
     * buf.rewind();      // Rewind buffer
     * buf.get(array);    // Copy data into array</pre></blockquote>
     *
     * <p>
     *  回退此缓冲区。位置设置为零,标记将被丢弃。
     * 
     *  <p>在通道写入或<i> get </i>操作序列之前调用此方法,假设已经适当地设置了限制。例如：
     * 
     *  <blockquote> <pre> out.write(buf); //写剩余数据buf.rewind(); // Rewind buffer buf.get(array); //将数据复制到数组</pre>
     *  </blockquote>。
     * 
     * 
     * @return  This buffer
     */
    public final Buffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }

    /**
     * Returns the number of elements between the current position and the
     * limit.
     *
     * <p>
     *  返回当前位置和限制之间的元素数。
     * 
     * 
     * @return  The number of elements remaining in this buffer
     */
    public final int remaining() {
        return limit - position;
    }

    /**
     * Tells whether there are any elements between the current position and
     * the limit.
     *
     * <p>
     *  告诉当前位置和限制之间是否有任何元素。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, there is at least one element
     *          remaining in this buffer
     */
    public final boolean hasRemaining() {
        return position < limit;
    }

    /**
     * Tells whether or not this buffer is read-only.
     *
     * <p>
     *  告诉这个缓冲区是否是只读的。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this buffer is read-only
     */
    public abstract boolean isReadOnly();

    /**
     * Tells whether or not this buffer is backed by an accessible
     * array.
     *
     * <p> If this method returns <tt>true</tt> then the {@link #array() array}
     * and {@link #arrayOffset() arrayOffset} methods may safely be invoked.
     * </p>
     *
     * <p>
     *  指示此缓冲区是否由可访问数组支持。
     * 
     *  <p>如果此方法返回<tt> true </tt>,则可以安全地调用{@link #array()数组}和{@link #arrayOffset()arrayOffset}方法。
     * </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this buffer
     *          is backed by an array and is not read-only
     *
     * @since 1.6
     */
    public abstract boolean hasArray();

    /**
     * Returns the array that backs this
     * buffer&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> This method is intended to allow array-backed buffers to be
     * passed to native code more efficiently. Concrete subclasses
     * provide more strongly-typed return values for this method.
     *
     * <p> Modifications to this buffer's content will cause the returned
     * array's content to be modified, and vice versa.
     *
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * <p>
     *  返回支持此缓冲区的数组&nbsp;&nbsp; <i>(可选操作)</i>。
     * 
     *  <p>此方法旨在使支持数组的缓冲区更有效地传递到本机代码。具体子类为此方法提供更强类型的返回值。
     * 
     *  <p>修改此缓冲区的内容将导致返回的数组的内容被修改,反之亦然。
     * 
     * <p>在调用此方法之前调用{@link #hasArray hasArray}方法,以确保此缓冲区具有可访问的后备数组。 </p>
     * 
     * 
     * @return  The array that backs this buffer
     *
     * @throws  ReadOnlyBufferException
     *          If this buffer is backed by an array but is read-only
     *
     * @throws  UnsupportedOperationException
     *          If this buffer is not backed by an accessible array
     *
     * @since 1.6
     */
    public abstract Object array();

    /**
     * Returns the offset within this buffer's backing array of the first
     * element of the buffer&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> If this buffer is backed by an array then buffer position <i>p</i>
     * corresponds to array index <i>p</i>&nbsp;+&nbsp;<tt>arrayOffset()</tt>.
     *
     * <p> Invoke the {@link #hasArray hasArray} method before invoking this
     * method in order to ensure that this buffer has an accessible backing
     * array.  </p>
     *
     * <p>
     *  返回缓冲区的第一个元素(可选操作)</i>在此缓冲区的后备数组中的偏移量。
     * 
     *  <p>如果此缓冲区由数组支持,则缓冲区位置<i> p </i>对应于数组索引<i> p </i>&nbsp; <tt> arrayOffset()</tt>。
     * 
     *  <p>在调用此方法之前调用{@link #hasArray hasArray}方法,以确保此缓冲区具有可访问的后备数组。 </p>
     * 
     * 
     * @return  The offset within this buffer's array
     *          of the first element of the buffer
     *
     * @throws  ReadOnlyBufferException
     *          If this buffer is backed by an array but is read-only
     *
     * @throws  UnsupportedOperationException
     *          If this buffer is not backed by an accessible array
     *
     * @since 1.6
     */
    public abstract int arrayOffset();

    /**
     * Tells whether or not this buffer is
     * <a href="ByteBuffer.html#direct"><i>direct</i></a>.
     *
     * <p>
     *  指出此缓冲区是否为<a href="ByteBuffer.html#direct"> <i>直接</i> </a>。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this buffer is direct
     *
     * @since 1.6
     */
    public abstract boolean isDirect();


    // -- Package-private methods for bounds checking, etc. --

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferUnderflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * <p>
     *  根据限制检查当前位置,如果它不小于限制,则抛出一个{@link BufferUnderflowException},然后增加位置。
     * 
     * 
     * @return  The current position value, before it is incremented
     */
    final int nextGetIndex() {                          // package-private
        if (position >= limit)
            throw new BufferUnderflowException();
        return position++;
    }

    final int nextGetIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferUnderflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the current position against the limit, throwing a {@link
     * BufferOverflowException} if it is not smaller than the limit, and then
     * increments the position.
     *
     * <p>
     *  根据限制检查当前位置,如果它不小于限制,则抛出一个{@link BufferOverflowException},然后增加位置。
     * 
     * 
     * @return  The current position value, before it is incremented
     */
    final int nextPutIndex() {                          // package-private
        if (position >= limit)
            throw new BufferOverflowException();
        return position++;
    }

    final int nextPutIndex(int nb) {                    // package-private
        if (limit - position < nb)
            throw new BufferOverflowException();
        int p = position;
        position += nb;
        return p;
    }

    /**
     * Checks the given index against the limit, throwing an {@link
     * IndexOutOfBoundsException} if it is not smaller than the limit
     * or is smaller than zero.
     * <p>
     *  根据限制检查给定的索引,如果它不小于限制或小于零,则抛出{@link IndexOutOfBoundsException}。
     */
    final int checkIndex(int i) {                       // package-private
        if ((i < 0) || (i >= limit))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int checkIndex(int i, int nb) {               // package-private
        if ((i < 0) || (nb > limit - i))
            throw new IndexOutOfBoundsException();
        return i;
    }

    final int markValue() {                             // package-private
        return mark;
    }

    final void truncate() {                             // package-private
        mark = -1;
        position = 0;
        limit = 0;
        capacity = 0;
    }

    final void discardMark() {                          // package-private
        mark = -1;
    }

    static void checkBounds(int off, int len, int size) { // package-private
        if ((off | len | (off + len) | (size - (off + len))) < 0)
            throw new IndexOutOfBoundsException();
    }

}
