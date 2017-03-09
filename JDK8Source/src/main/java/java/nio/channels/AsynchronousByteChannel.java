/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

/**
 * An asynchronous channel that can read and write bytes.
 *
 * <p> Some channels may not allow more than one read or write to be outstanding
 * at any given time. If a thread invokes a read method before a previous read
 * operation has completed then a {@link ReadPendingException} will be thrown.
 * Similarly, if a write method is invoked before a previous write has completed
 * then {@link WritePendingException} is thrown. Whether or not other kinds of
 * I/O operations may proceed concurrently with a read operation depends upon
 * the type of the channel.
 *
 * <p> Note that {@link java.nio.ByteBuffer ByteBuffers} are not safe for use by
 * multiple concurrent threads. When a read or write operation is initiated then
 * care must be taken to ensure that the buffer is not accessed until the
 * operation completes.
 *
 * <p>
 *  可读取和写入字节的异步通道。
 * 
 *  <p>某些频道可能不允许在任何给定时间有多个读取或写入未完成。如果线程在上一个读取操作完成之前调用读取方法,那么将抛出{@link ReadPendingException}。
 * 类似地,如果在上一个写入完成之前调用写入方法,则抛出{@link WritePendingException}。其他种类的I / O操作是否可以与读取操作同时进行取决于通道的类型。
 * 
 *  <p>请注意,{@link java.nio.ByteBuffer ByteBuffers}不能安全地用于多个并发线程。当启动读取或写入操作时,必须小心确保在操作完成之前不访问缓冲区。
 * 
 * 
 * @see Channels#newInputStream(AsynchronousByteChannel)
 * @see Channels#newOutputStream(AsynchronousByteChannel)
 *
 * @since 1.7
 */

public interface AsynchronousByteChannel
    extends AsynchronousChannel
{
    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> This method initiates an asynchronous read operation to read a
     * sequence of bytes from this channel into the given buffer. The {@code
     * handler} parameter is a completion handler that is invoked when the read
     * operation completes (or fails). The result passed to the completion
     * handler is the number of bytes read or {@code -1} if no bytes could be
     * read because the channel has reached end-of-stream.
     *
     * <p> The read operation may read up to <i>r</i> bytes from the channel,
     * where <i>r</i> is the number of bytes remaining in the buffer, that is,
     * {@code dst.remaining()} at the time that the read is attempted. Where
     * <i>r</i> is 0, the read operation completes immediately with a result of
     * {@code 0} without initiating an I/O operation.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is read, where
     * <tt>0</tt>&nbsp;<tt>&lt;</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * This byte sequence will be transferred into the buffer so that the first
     * byte in the sequence is at index <i>p</i> and the last byte is at index
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>,
     * where <i>p</i> is the buffer's position at the moment the read is
     * performed. Upon completion the buffer's position will be equal to
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>; its limit will not have changed.
     *
     * <p> Buffers are not safe for use by multiple concurrent threads so care
     * should be taken to not access the buffer until the operation has
     * completed.
     *
     * <p> This method may be invoked at any time. Some channel types may not
     * allow more than one read to be outstanding at any given time. If a thread
     * initiates a read operation before a previous read operation has
     * completed then a {@link ReadPendingException} will be thrown.
     *
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     *  <p>此方法启动异步读取操作,以将字节序列从此通道读取到给定缓冲区中。 {@code handler}参数是在读取操作完成(或失败)时调用的完成处理程序。
     * 传递给完成处理程序的结果是读取的字节数或{@code -1}如果没有字节可以读取,因为通道已达到流结束。
     * 
     * <p>读取操作可以从通道读取最多r个字节,其中,</i>是缓冲器中剩余的字节数,即{@code dst。 remaining()}。
     * 其中<i> r </i>为0,读操作立即以{@code 0}的结果完成,而不启动I / O操作。
     * 
     *  <p>假设读取一个长度为n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 这个字节序列将被传送到缓冲器中,使得序列中的第一字节在索引p i,并且最后一个字节在索引p <p> <tt> + < / tt>&nbsp; <i> n </i>&nbsp; <tt>  -  </tt>
     * &nbsp; <tt> 1 </tt>,其中<i> p </i>执行读取。
     *  <p>假设读取一个长度为n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 完成后,缓冲区的位置将等于<i> p </i>&lt; tt> + </tt>&nbsp; <i> n </i>;其极限不会改变。
     * 
     *  <p>缓冲区对于多个并发线程是不安全的,所以在操作完成之前应注意不要访问缓冲区。
     * 
     *  <p>此方法可能随时被调用。某些通道类型可能不允许在任何给定时间超过一个读取未完成。如果线程在上一个读操作完成之前启动读操作,那么将抛出{@link ReadPendingException}。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   dst
     *          The buffer into which bytes are to be transferred
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The completion handler
     *
     * @throws  IllegalArgumentException
     *          If the buffer is read-only
     * @throws  ReadPendingException
     *          If the channel does not allow more than one read to be outstanding
     *          and a previous read has not completed
     * @throws  ShutdownChannelGroupException
     *          If the channel is associated with a {@link AsynchronousChannelGroup
     *          group} that has terminated
     */
    <A> void read(ByteBuffer dst,
                  A attachment,
                  CompletionHandler<Integer,? super A> handler);

    /**
     * Reads a sequence of bytes from this channel into the given buffer.
     *
     * <p> This method initiates an asynchronous read operation to read a
     * sequence of bytes from this channel into the given buffer. The method
     * behaves in exactly the same manner as the {@link
     * #read(ByteBuffer,Object,CompletionHandler)
     * read(ByteBuffer,Object,CompletionHandler)} method except that instead
     * of specifying a completion handler, this method returns a {@code Future}
     * representing the pending result. The {@code Future}'s {@link Future#get()
     * get} method returns the number of bytes read or {@code -1} if no bytes
     * could be read because the channel has reached end-of-stream.
     *
     * <p>
     *  从该通道读取一个字节序列到给定的缓冲区。
     * 
     * <p>此方法启动异步读取操作,以将字节序列从此通道读取到给定缓冲区中。
     * 该方法的行为方式与{@link #read(ByteBuffer,Object,CompletionHandler)读取(ByteBuffer,Object,CompletionHandler)}方法完
     * 全相同,不同之处在于此方法不是指定完成处理程序,而是返回{@code Future}表示未决结果。
     * <p>此方法启动异步读取操作,以将字节序列从此通道读取到给定缓冲区中。
     *  {@code Future}的{@link Future#get()get}方法返回读取的字节数,如果没有字节可以读取,则返回{@code -1},因为通道已达到流结束。
     * 
     * 
     * @param   dst
     *          The buffer into which bytes are to be transferred
     *
     * @return  A Future representing the result of the operation
     *
     * @throws  IllegalArgumentException
     *          If the buffer is read-only
     * @throws  ReadPendingException
     *          If the channel does not allow more than one read to be outstanding
     *          and a previous read has not completed
     */
    Future<Integer> read(ByteBuffer dst);

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * <p> This method initiates an asynchronous write operation to write a
     * sequence of bytes to this channel from the given buffer. The {@code
     * handler} parameter is a completion handler that is invoked when the write
     * operation completes (or fails). The result passed to the completion
     * handler is the number of bytes written.
     *
     * <p> The write operation may write up to <i>r</i> bytes to the channel,
     * where <i>r</i> is the number of bytes remaining in the buffer, that is,
     * {@code src.remaining()} at the time that the write is attempted. Where
     * <i>r</i> is 0, the write operation completes immediately with a result of
     * {@code 0} without initiating an I/O operation.
     *
     * <p> Suppose that a byte sequence of length <i>n</i> is written, where
     * <tt>0</tt>&nbsp;<tt>&lt;</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;<i>r</i>.
     * This byte sequence will be transferred from the buffer starting at index
     * <i>p</i>, where <i>p</i> is the buffer's position at the moment the
     * write is performed; the index of the last byte written will be
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>.
     * Upon completion the buffer's position will be equal to
     * <i>p</i>&nbsp;<tt>+</tt>&nbsp;<i>n</i>; its limit will not have changed.
     *
     * <p> Buffers are not safe for use by multiple concurrent threads so care
     * should be taken to not access the buffer until the operation has
     * completed.
     *
     * <p> This method may be invoked at any time. Some channel types may not
     * allow more than one write to be outstanding at any given time. If a thread
     * initiates a write operation before a previous write operation has
     * completed then a {@link WritePendingException} will be thrown.
     *
     * <p>
     *  从给定缓冲区向此通道写入一个字节序列。
     * 
     *  <p>此方法启动异步写入操作,从给定缓冲区向此通道写入一系列字节。 {@code handler}参数是在写操作完成(或失败)时调用的完成处理程序。传递给完成处理程序的结果是写入的字节数。
     * 
     *  <p>写操作可以写入到通道的<r>字节,其中<i> r是缓冲器中剩余的字节数,即{@code src。 remaining()}。
     * 其中<i> r </i>为0,写操作立即以{@code 0}的结果完成,而不启动I / O操作。
     * 
     * <p>假设写入了长度为<i> n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 这个字节序列将从在索引p处开始的缓冲器传送,其中<p>是在执行写入时的缓冲器的位置;所写的最后一个字节的索引将为<i> p </i> <tt> + </tt> <i> n </i> <tt>  -  </tt>
     *  tt> 1 </tt>。
     * <p>假设写入了长度为<i> n的字节序列,其中<tt> 0 </tt> <tt> </tt>&lt; / n >&nbsp; <tt>&lt; = </tt>&nbsp; <i> r </i>。
     * 完成后,缓冲区的位置将等于<i> p </i>&lt; tt> + </tt>&nbsp; <i> n </i>;其极限不会改变。
     * 
     * 
     * @param   <A>
     *          The type of the attachment
     * @param   src
     *          The buffer from which bytes are to be retrieved
     * @param   attachment
     *          The object to attach to the I/O operation; can be {@code null}
     * @param   handler
     *          The completion handler object
     *
     * @throws  WritePendingException
     *          If the channel does not allow more than one write to be outstanding
     *          and a previous write has not completed
     * @throws  ShutdownChannelGroupException
     *          If the channel is associated with a {@link AsynchronousChannelGroup
     *          group} that has terminated
     */
    <A> void write(ByteBuffer src,
                   A attachment,
                   CompletionHandler<Integer,? super A> handler);

    /**
     * Writes a sequence of bytes to this channel from the given buffer.
     *
     * <p> This method initiates an asynchronous write operation to write a
     * sequence of bytes to this channel from the given buffer. The method
     * behaves in exactly the same manner as the {@link
     * #write(ByteBuffer,Object,CompletionHandler)
     * write(ByteBuffer,Object,CompletionHandler)} method except that instead
     * of specifying a completion handler, this method returns a {@code Future}
     * representing the pending result. The {@code Future}'s {@link Future#get()
     * get} method returns the number of bytes written.
     *
     * <p>
     *  <p>缓冲区对于多个并发线程是不安全的,所以在操作完成之前应注意不要访问缓冲区。
     * 
     *  <p>此方法可能随时被调用。某些通道类型可能不允许在任何给定时间超过一个写入未完成。如果线程在上一个写操作完成之前启动写操作,那么将抛出{@link WritePendingException}。
     * 
     * 
     * @param   src
     *          The buffer from which bytes are to be retrieved
     *
     * @return A Future representing the result of the operation
     *
     * @throws  WritePendingException
     *          If the channel does not allow more than one write to be outstanding
     *          and a previous write has not completed
     */
    Future<Integer> write(ByteBuffer src);
}
