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

/*
/* <p>
 */

package java.nio.channels.spi;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.nio.ch.Interruptible;


/**
 * Base implementation class for interruptible channels.
 *
 * <p> This class encapsulates the low-level machinery required to implement
 * the asynchronous closing and interruption of channels.  A concrete channel
 * class must invoke the {@link #begin begin} and {@link #end end} methods
 * before and after, respectively, invoking an I/O operation that might block
 * indefinitely.  In order to ensure that the {@link #end end} method is always
 * invoked, these methods should be used within a
 * <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block:
 *
 * <blockquote><pre>
 * boolean completed = false;
 * try {
 *     begin();
 *     completed = ...;    // Perform blocking I/O operation
 *     return ...;         // Return result
 * } finally {
 *     end(completed);
 * }</pre></blockquote>
 *
 * <p> The <tt>completed</tt> argument to the {@link #end end} method tells
 * whether or not the I/O operation actually completed, that is, whether it had
 * any effect that would be visible to the invoker.  In the case of an
 * operation that reads bytes, for example, this argument should be
 * <tt>true</tt> if, and only if, some bytes were actually transferred into the
 * invoker's target buffer.
 *
 * <p> A concrete channel class must also implement the {@link
 * #implCloseChannel implCloseChannel} method in such a way that if it is
 * invoked while another thread is blocked in a native I/O operation upon the
 * channel then that operation will immediately return, either by throwing an
 * exception or by returning normally.  If a thread is interrupted or the
 * channel upon which it is blocked is asynchronously closed then the channel's
 * {@link #end end} method will throw the appropriate exception.
 *
 * <p> This class performs the synchronization required to implement the {@link
 * java.nio.channels.Channel} specification.  Implementations of the {@link
 * #implCloseChannel implCloseChannel} method need not synchronize against
 * other threads that might be attempting to close the channel.  </p>
 *
 *
 * <p>
 *  可中断通道的基本实现类。
 * 
 *  <p>此类封装了实现异步关闭和中断通道所需的低级机制。
 * 具体的通道类必须分别调用{@ link #begin begin}和{@link #end end}方法之前和之后调用可能无限阻塞的I / O操作。
 * 为了确保始终调用{@link #end end}方法,应在<tt> try </tt>&nbsp; ...&nbsp; <tt> finally </tt>块中使用这些方法：。
 * 
 *  <blockquote> <pre> boolean completed = false; try {begin(); completed = ...; //执行阻塞I / O操作return ...; // Return result}
 *  finally {end(completed); } </pre> </blockquote>。
 * 
 *  <p> {@link #end end}方法的<tt>完成</tt>参数指示I / O操作是否实际完成,即是否具有调用程序可见的任何效果。
 * 例如,在读取字节的操作的情况下,此参数应为<tt> true </tt> if,且仅当某些字节实际上传输到调用器的目标缓冲区中时。
 * 
 * <p>具体的通道类必须以这样一种方式实现{@link #implCloseChannel implCloseChannel}方法,即如果在通道上的另一个线程在本地I / O操作中被阻塞时被调用,那么该操
 * 作将立即返回,通过抛出异常或正常返回。
 * 如果一个线程被中断或被阻塞的通道被异步关闭,那么通道的{@link #end end}方法将抛出相应的异常。
 * 
 *  <p>此类执行实现{@link java.nio.channels.Channel}规范所需的同步。
 *  {@link #implCloseChannel implCloseChannel}方法的实施不需要与可能尝试关闭频道的其他线程同步。 </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class AbstractInterruptibleChannel
    implements Channel, InterruptibleChannel
{

    private final Object closeLock = new Object();
    private volatile boolean open = true;

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected AbstractInterruptibleChannel() { }

    /**
     * Closes this channel.
     *
     * <p> If the channel has already been closed then this method returns
     * immediately.  Otherwise it marks the channel as closed and then invokes
     * the {@link #implCloseChannel implCloseChannel} method in order to
     * complete the close operation.  </p>
     *
     * <p>
     *  关闭此频道。
     * 
     *  <p>如果频道已关闭,则此方法会立即返回。否则,它将该通道标记为已关闭,然后调用{@link #implCloseChannel implCloseChannel}方法以完成关闭操作。 </p>
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    public final void close() throws IOException {
        synchronized (closeLock) {
            if (!open)
                return;
            open = false;
            implCloseChannel();
        }
    }

    /**
     * Closes this channel.
     *
     * <p> This method is invoked by the {@link #close close} method in order
     * to perform the actual work of closing the channel.  This method is only
     * invoked if the channel has not yet been closed, and it is never invoked
     * more than once.
     *
     * <p> An implementation of this method must arrange for any other thread
     * that is blocked in an I/O operation upon this channel to return
     * immediately, either by throwing an exception or by returning normally.
     * </p>
     *
     * <p>
     *  关闭此频道。
     * 
     *  <p>此方法由{@link #close close}方法调用,以便执行关闭渠道的实际工作。仅当通道尚未关闭时才调用此方法,并且它不会被多次调用。
     * 
     * <p>此方法的实现必须安排在此通道上的I / O操作中阻塞的任何其他线程通过抛出异常或正常返回立即返回。
     * </p>
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs while closing the channel
     */
    protected abstract void implCloseChannel() throws IOException;

    public final boolean isOpen() {
        return open;
    }


    // -- Interruption machinery --

    private Interruptible interruptor;
    private volatile Thread interrupted;

    /**
     * Marks the beginning of an I/O operation that might block indefinitely.
     *
     * <p> This method should be invoked in tandem with the {@link #end end}
     * method, using a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block as
     * shown <a href="#be">above</a>, in order to implement asynchronous
     * closing and interruption for this channel.  </p>
     * <p>
     *  标记可能无限阻止的I / O操作的开始。
     * 
     *  <p>此方法应与{@link #end end}方法一起调用,使用<tt> try </tt>&nbsp; ...&nbsp; <tt> finally </tt> a href ="#be"> </a>
     * ,以便为此频道实现异步关闭和中断。
     *  </p>。
     * 
     */
    protected final void begin() {
        if (interruptor == null) {
            interruptor = new Interruptible() {
                    public void interrupt(Thread target) {
                        synchronized (closeLock) {
                            if (!open)
                                return;
                            open = false;
                            interrupted = target;
                            try {
                                AbstractInterruptibleChannel.this.implCloseChannel();
                            } catch (IOException x) { }
                        }
                    }};
        }
        blockedOn(interruptor);
        Thread me = Thread.currentThread();
        if (me.isInterrupted())
            interruptor.interrupt(me);
    }

    /**
     * Marks the end of an I/O operation that might block indefinitely.
     *
     * <p> This method should be invoked in tandem with the {@link #begin
     * begin} method, using a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block
     * as shown <a href="#be">above</a>, in order to implement asynchronous
     * closing and interruption for this channel.  </p>
     *
     * <p>
     *  标记可能无限阻止的I / O操作的结束。
     * 
     *  <p>此方法应与{@link #begin begin}方法一起调用,使用<tt> try </tt>&nbsp; ...&nbsp; <tt> finally </tt> a href ="#be"
     * 
     * @param  completed
     *         <tt>true</tt> if, and only if, the I/O operation completed
     *         successfully, that is, had some effect that would be visible to
     *         the operation's invoker
     *
     * @throws  AsynchronousCloseException
     *          If the channel was asynchronously closed
     *
     * @throws  ClosedByInterruptException
     *          If the thread blocked in the I/O operation was interrupted
     */
    protected final void end(boolean completed)
        throws AsynchronousCloseException
    {
        blockedOn(null);
        Thread interrupted = this.interrupted;
        if (interrupted != null && interrupted == Thread.currentThread()) {
            interrupted = null;
            throw new ClosedByInterruptException();
        }
        if (!completed && !open)
            throw new AsynchronousCloseException();
    }


    // -- sun.misc.SharedSecrets --
    static void blockedOn(Interruptible intr) {         // package-private
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(),
                                                             intr);
    }
}
