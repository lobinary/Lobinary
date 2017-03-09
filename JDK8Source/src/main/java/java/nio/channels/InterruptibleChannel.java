/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

/*
/* <p>
 */

package java.nio.channels;

import java.io.IOException;


/**
 * A channel that can be asynchronously closed and interrupted.
 *
 * <p> A channel that implements this interface is <i>asynchronously
 * closeable:</i> If a thread is blocked in an I/O operation on an
 * interruptible channel then another thread may invoke the channel's {@link
 * #close close} method.  This will cause the blocked thread to receive an
 * {@link AsynchronousCloseException}.
 *
 * <p> A channel that implements this interface is also <i>interruptible:</i>
 * If a thread is blocked in an I/O operation on an interruptible channel then
 * another thread may invoke the blocked thread's {@link Thread#interrupt()
 * interrupt} method.  This will cause the channel to be closed, the blocked
 * thread to receive a {@link ClosedByInterruptException}, and the blocked
 * thread's interrupt status to be set.
 *
 * <p> If a thread's interrupt status is already set and it invokes a blocking
 * I/O operation upon a channel then the channel will be closed and the thread
 * will immediately receive a {@link ClosedByInterruptException}; its interrupt
 * status will remain set.
 *
 * <p> A channel supports asynchronous closing and interruption if, and only
 * if, it implements this interface.  This can be tested at runtime, if
 * necessary, via the <tt>instanceof</tt> operator.
 *
 *
 * <p>
 *  可以异步关闭和中断的通道。
 * 
 *  <p>实现此接口的通道是<i>可异步关闭：</i>如果线程在可中断通道上的I / O操作中被阻塞,则另一个线程可调用通道的{@link #close close}方法。
 * 这将导致被阻塞的线程接收{@link AsynchronousCloseException}。
 * 
 *  <p>实现此接口的通道也是可中断的：</i>如果线程在可中断通道上的I / O操作中被阻塞,则另一个线程可以调用被阻塞的线程的{@link Thread#interrupt )interrupt}方法
 * 。
 * 这将导致通道被关闭,被阻塞的线程接收一个{@link ClosedByInterruptException},并且被阻塞的线程的中断状态被设置。
 * 
 *  <p>如果线程的中断状态已经设置,并且它在一个通道上调用阻塞I / O操作,那么通道将被关闭,线程将立即接收{@link ClosedByInterruptException};其中断状态将保持置1。
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public interface InterruptibleChannel
    extends Channel
{

    /**
     * Closes this channel.
     *
     * <p> Any thread currently blocked in an I/O operation upon this channel
     * will receive an {@link AsynchronousCloseException}.
     *
     * <p> This method otherwise behaves exactly as specified by the {@link
     * Channel#close Channel} interface.  </p>
     *
     * <p>
     * 
     *  <p>一个通道支持异步关闭和中断,如果,只有当它实现了这个接口。如果需要,可以在运行时通过<tt> instanceof </tt>运算符对其进行测试。
     * 
     * 
     * @throws  IOException  If an I/O error occurs
     */
    public void close() throws IOException;

}
