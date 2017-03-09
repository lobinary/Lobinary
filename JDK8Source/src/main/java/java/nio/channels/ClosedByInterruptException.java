/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
 *
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
 *
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio.channels;


/**
 * Checked exception received by a thread when another thread interrupts it
 * while it is blocked in an I/O operation upon a channel.  Before this
 * exception is thrown the channel will have been closed and the interrupt
 * status of the previously-blocked thread will have been set.
 *
 * <p>
 *  当另一个线程中断一个线程时,在线程被I / O操作阻塞时,该线程接收到检查异常。在抛出此异常之前,通道将被关闭,并且先前阻塞的线程的中断状态将被设置。
 * 
 * 
 * @since 1.4
 */

public class ClosedByInterruptException
    extends AsynchronousCloseException
{

    private static final long serialVersionUID = -4488191543534286750L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     */
    public ClosedByInterruptException() { }

}
