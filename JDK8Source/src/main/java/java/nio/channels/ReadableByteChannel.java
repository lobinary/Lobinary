/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * A channel that can read bytes.
 *
 * <p> Only one read operation upon a readable channel may be in progress at
 * any given time.  If one thread initiates a read operation upon a channel
 * then any other thread that attempts to initiate another read operation will
 * block until the first operation is complete.  Whether or not other kinds of
 * I/O operations may proceed concurrently with a read operation depends upon
 * the type of the channel. </p>
 *
 *
 * <p>
 *  可读取字节的通道。
 * 
 *  <p>在任何给定时间,在可读通道上只能进行一次读取操作。如果一个线程在通道上启动读取操作,那么尝试启动另一读取操作的任何其他线程将阻塞,直到第一操作完成。
 * 其他种类的I / O操作是否可以与读取操作同时进行取决于通道的类型。 </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public interface ReadableByteChannel extends Channel {

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> An attempt is made to read up to <i>r</i> bytes from the channel,
     * where <i>r</i> is the number of bytes remaining in the buffer, that is,
     * <tt>dst.remaining()</tt>, at the moment this method is invoked.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is read, where
     * <tt>0</tt>&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * This byte sequence will be transferred into the buffer so that the first
     * byte in the sequence is at index <i>p</i> and the last byte is at index
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>,
     * where <i>p</i> is the buffer's position at the moment this method is
     * invoked.  Upon return the buffer's position will be equal to
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>; its limit will not have changed.
     *
     * <p> A read operation might not fill the buffer, and in fact it might not
     * read any bytes at all.  Whether or not it does so depends upon the
     * nature and state of the channel.  A socket channel in non-blocking mode,
     * for example, cannot read any more bytes than are immediately available
     * from the socket's input buffer; similarly, a file channel cannot read
     * any more bytes than remain in the file.  It is guaranteed, however, that
     * if a channel is in blocking mode and there is at least one byte
     * remaining in the buffer then this method will block until at least one
     * byte is read.
     *
     * <p> This method may be invoked at any time.  If another thread has
     * already initiated a read operation upon this channel, however, then an
     * invocation of this method will block until the first operation is
     * complete. </p>
     *
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>尝试从通道读取<i> r </i>个字节,其中<i> r </i>是缓冲区中剩余的字节数,即<tt> dst .remaining()</tt>,此时调用此方法。
     * 
     *  <p>假设读取长度为n的字节序列,其中<tt> 0 </tt> <tt> <= </tt>&lt; i>&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 这个字节序列将被传送到缓冲器中,使得序列中的第一个字节在索引p i,最后一个字节在索引p i <tt> + < / tt>&nbsp; <i> n </i>&nbsp; <tt>  -  </tt>&n
     * bsp; <tt> 1 </tt>,其中<i> p </i>这个方法被调用。
     *  <p>假设读取长度为n的字节序列,其中<tt> 0 </tt> <tt> <= </tt>&lt; i>&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 
     * @param  dst
     *         The buffer into which bytes are to be transferred
     *
     * @return  The number of bytes read, possibly zero, or <tt>-1</tt> if the
     *          channel has reached end-of-stream
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
    public int read(ByteBuffer dst) throws IOException;

}
