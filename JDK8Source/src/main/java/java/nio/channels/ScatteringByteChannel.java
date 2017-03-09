/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;


/**
 * A channel that can read bytes into a sequence of buffers.
 *
 * <p> A <i>scattering</i> read operation reads, in a single invocation, a
 * sequence of bytes into one or more of a given sequence of buffers.
 * Scattering reads are often useful when implementing network protocols or
 * file formats that, for example, group data into segments consisting of one
 * or more fixed-length headers followed by a variable-length body.  Similar
 * <i>gathering</i> write operations are defined in the {@link
 * GatheringByteChannel} interface.  </p>
 *
 *
 * <p>
 *  可以将字节读入缓冲区序列的通道。
 * 
 *  散射读操作在单次调用中将字节序列读取到给定缓冲器序列中的一个或多个中。当实现网络协议或文件格式(例如,将数据分组成由一个或多个固定长度的报头以及可变长度的主体组成的段)时,分散读取通常是有用的。
 * 在{@link GatheringByteChannel}接口中定义类似的<i>收集</i>写入操作。 </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public interface ScatteringByteChannel
    extends ReadableByteChannel
{

    /**
     * Reads a sequence of bytes from this channel into a subsequence of the
     * given buffers.
     *
     * <p> An invocation of this method attempts to read up to <i>r</i> bytes
     * from this channel, where <i>r</i> is the total number of bytes remaining
     * the specified subsequence of the given buffer array, that is,
     *
     * <blockquote><pre>
     * dsts[offset].remaining()
     *     + dsts[offset+1].remaining()
     *     + ... + dsts[offset+length-1].remaining()</pre></blockquote>
     *
     * at the moment that this method is invoked.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is read, where
     * <tt>0</tt>&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * Up to the first <tt>dsts[offset].remaining()</tt> bytes of this sequence
     * are transferred into buffer <tt>dsts[offset]</tt>, up to the next
     * <tt>dsts[offset+1].remaining()</tt> bytes are transferred into buffer
     * <tt>dsts[offset+1]</tt>, and so forth, until the entire byte sequence
     * is transferred into the given buffers.  As many bytes as possible are
     * transferred into each buffer, hence the final position of each updated
     * buffer, except the last updated buffer, is guaranteed to be equal to
     * that buffer's limit.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a read operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. </p>
     *
     * <p>
     *  将来自该通道的字节序列读取到给定缓冲器的子序列中。
     * 
     *  <p>此方法的调用尝试从此通道读取最多r个字节,其中<i> r </i>是给定缓冲区阵列的指定子序列剩余的字节总数, 那是,
     * 
     *  <blockquote> <pre> dsts [offset] .remaining()+ dsts [offset + 1] .remaining()+ ... + dsts [offset + 
     * length-1] .remaining()</。
     * 
     *  在调用此方法的时刻。
     * 
     * <p>假设读取长度为n的字节序列,其中<tt> 0 </tt> <tt> <= </tt>&lt; i>&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 直到该序列的第一个<tt> dsts [offset] .remaining()</tt>字节传输到缓冲区<tt> dsts [offset] </tt>,直到下一个<tt> dsts [ 尽可能多的字
     * 节被传送到每个缓冲器,因此除了最后更新的缓冲器之外,每个更新的缓冲器的最终位置被保证等于该缓冲器的限制。
     * 
     * @param  dsts
     *         The buffers into which bytes are to be transferred
     *
     * @param  offset
     *         The offset within the buffer array of the first buffer into
     *         which bytes are to be transferred; must be non-negative and no
     *         larger than <tt>dsts.length</tt>
     *
     * @param  length
     *         The maximum number of buffers to be accessed; must be
     *         non-negative and no larger than
     *         <tt>dsts.length</tt>&nbsp;-&nbsp;<tt>offset</tt>
     *
     * @return The number of bytes read, possibly zero,
     *         or <tt>-1</tt> if the channel has reached end-of-stream
     *
     * @throws  IndexOutOfBoundsException
     *          If the preconditions on the <tt>offset</tt> and <tt>length</tt>
     *          parameters do not hold
     *
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public long read(ByteBuffer[] dsts, int offset, int length)
        throws IOException;

    /**
     * Reads a sequence of bytes from this channel into the given buffers.
     *
     * <p> An invocation of this method of the form <tt>c.read(dsts)</tt>
     * behaves in exactly the same manner as the invocation
     *
     * <blockquote><pre>
     * c.read(dsts, 0, dsts.length);</pre></blockquote>
     *
     * <p>
     * <p>假设读取长度为n的字节序列,其中<tt> 0 </tt> <tt> <= </tt>&lt; i>&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 
     *  <p>此方法可能随时被调用。如果另一个线程已经在该通道上启动了读取操作,则该方法的调用将阻塞,直到第一操作完成。 </p>
     * 
     * 
     * @param  dsts
     *         The buffers into which bytes are to be transferred
     *
     * @return The number of bytes read, possibly zero,
     *         or <tt>-1</tt> if the channel has reached end-of-stream
     *
     * @throws  NonReadableChannelException
     *          If this channel was not opened for reading
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  AsynchronousCloseException
     *          If another thread closes this channel
     *          while the read operation is in progress
     *
     * @throws  ClosedByInterruptException
     *          If another thread interrupts the current thread
     *          while the read operation is in progress, thereby
     *          closing the channel and setting the current thread's
     *          interrupt status
     *
     * @throws  IOException
     *          If some other I/O error occurs
     */
    public long read(ByteBuffer[] dsts) throws IOException;

}
