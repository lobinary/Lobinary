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

import java.io.IOException;
import java.util.concurrent.Future;  // javadoc

/**
 * A channel that supports asynchronous I/O operations. Asynchronous I/O
 * operations will usually take one of two forms:
 *
 * <ol>
 * <li><pre>{@link Future}&lt;V&gt; <em>operation</em>(<em>...</em>)</pre></li>
 * <li><pre>void <em>operation</em>(<em>...</em> A attachment, {@link
 *   CompletionHandler}&lt;V,? super A&gt; handler)</pre></li>
 * </ol>
 *
 * where <i>operation</i> is the name of the I/O operation (read or write for
 * example), <i>V</i> is the result type of the I/O operation, and <i>A</i> is
 * the type of an object attached to the I/O operation to provide context when
 * consuming the result. The attachment is important for cases where a
 * <em>state-less</em> {@code CompletionHandler} is used to consume the result
 * of many I/O operations.
 *
 * <p> In the first form, the methods defined by the {@link Future Future}
 * interface may be used to check if the operation has completed, wait for its
 * completion, and to retrieve the result. In the second form, a {@link
 * CompletionHandler} is invoked to consume the result of the I/O operation when
 * it completes or fails.
 *
 * <p> A channel that implements this interface is <em>asynchronously
 * closeable</em>: If an I/O operation is outstanding on the channel and the
 * channel's {@link #close close} method is invoked, then the I/O operation
 * fails with the exception {@link AsynchronousCloseException}.
 *
 * <p> Asynchronous channels are safe for use by multiple concurrent threads.
 * Some channel implementations may support concurrent reading and writing, but
 * may not allow more than one read and one write operation to be outstanding at
 * any given time.
 *
 * <h2>Cancellation</h2>
 *
 * <p> The {@code Future} interface defines the {@link Future#cancel cancel}
 * method to cancel execution. This causes all threads waiting on the result of
 * the I/O operation to throw {@link java.util.concurrent.CancellationException}.
 * Whether the underlying I/O operation can be cancelled is highly implementation
 * specific and therefore not specified. Where cancellation leaves the channel,
 * or the entity to which it is connected, in an inconsistent state, then the
 * channel is put into an implementation specific <em>error state</em> that
 * prevents further attempts to initiate I/O operations that are <i>similar</i>
 * to the operation that was cancelled. For example, if a read operation is
 * cancelled but the implementation cannot guarantee that bytes have not been
 * read from the channel then it puts the channel into an error state; further
 * attempts to initiate a {@code read} operation cause an unspecified runtime
 * exception to be thrown. Similarly, if a write operation is cancelled but the
 * implementation cannot guarantee that bytes have not been written to the
 * channel then subsequent attempts to initiate a {@code write} will fail with
 * an unspecified runtime exception.
 *
 * <p> Where the {@link Future#cancel cancel} method is invoked with the {@code
 * mayInterruptIfRunning} parameter set to {@code true} then the I/O operation
 * may be interrupted by closing the channel. In that case all threads waiting
 * on the result of the I/O operation throw {@code CancellationException} and
 * any other I/O operations outstanding on the channel complete with the
 * exception {@link AsynchronousCloseException}.
 *
 * <p> Where the {@code cancel} method is invoked to cancel read or write
 * operations then it is recommended that all buffers used in the I/O operations
 * be discarded or care taken to ensure that the buffers are not accessed while
 * the channel remains open.
 *
 * <p>
 *  支持异步I / O操作的通道。异步I / O操作通常采用以下两种形式之一：
 * 
 * <ol>
 *  <li> <pre> {@ link Future}&lt; V&gt; </em>(<em> </em>)</em>(<em> </em> </em>附件,{@link CompletionHandler}
 * &lt; V ,? super A&gt;处理程序)</pre> </li>。
 * </ol>
 * 
 *  其中<i>操作</i>是I / O操作的名称(例如读或写),</i>是I / O操作的结果类型,<A> </i>是附加到I / O操作以在使用结果时提供上下文的对象的类型。
 * 对于使用<em>无状态</em> {@code CompletionHandler}来消耗许多I / O操作的结果的情况,附件很重要。
 * 
 *  <p>在第一种形式中,由{@link Future Future}接口定义的方法可用于检查操作是否已完成,等待其完成以及检索结果。
 * 在第二种形式中,调用{@link CompletionHandler}以在I / O操作完成或失败时使用I / O操作的结果。
 * 
 *  <p>实现此接口的通道是<em>可异步关闭</em>：如果通道上的I / O操作未完成,并且调用了通道的{@link #close close}方法,则I / O操作失败,异常{@link AsynchronousCloseException}
 * 。
 * 
 * <p>异步通道可安全地用于多个并发线程。一些通道实现可以支持并发读取和写入,但是可能不允许在任何给定时间多于一个读取和一个写入操作是突出的。
 * 
 *  <h2>取消</h2>
 * 
 *  <p> {@code Future}界面定义了{@link Future#cancel cancel}方法以取消执行。
 * 
 *  @since 1.7
 */

public interface AsynchronousChannel
    extends Channel
{
    /**
     * Closes this channel.
     *
     * <p> Any outstanding asynchronous operations upon this channel will
     * complete with the exception {@link AsynchronousCloseException}. After a
     * channel is closed, further attempts to initiate asynchronous I/O
     * operations complete immediately with cause {@link ClosedChannelException}.
     *
     * <p>  This method otherwise behaves exactly as specified by the {@link
     * Channel} interface.
     *
     * <p>
     * 这会导致所有等待I / O操作结果的线程抛出{@link java.util.concurrent.CancellationException}。
     * 底层I / O操作是否可以被取消是高度实现特定的,因此没有指定。
     * 在取消离开信道或其连接的实体处于不一致状态的情况下,则信道被置于实现特定的错误状态,防止进一步尝试发起I / O操作, <i>类似于</i>已取消的操作。
     * 例如,如果读取操作被取消,但是实现不能保证字节没有从通道读取,则它将通道置于错误状态;进一步尝试启动{@code read}操作会导致抛出未指定的运行时异常。
     * 类似地,如果写操作被取消,但是实现不能保证字节没有被写入通道,则随后尝试启动{@code write}将会失败,并出现未指定的运行时异常。
     * 
     * <p>如果{@link Future#cancel cancel}方法的{@code mayInterruptIfRunning}参数设置为{@code true},那么I / O操作可能会由于关闭通道
     * 而中断。
     * 在这种情况下,所有等待I / O操作结果的线程抛出{@code CancellationException}和通道上未完成的任何其他I / O操作都会完成异常{@link AsynchronousCloseException}
     * 。
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    @Override
    void close() throws IOException;
}
