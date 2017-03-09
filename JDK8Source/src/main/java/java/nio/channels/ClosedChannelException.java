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
 * Checked exception thrown when an attempt is made to invoke or complete an
 * I/O operation upon channel that is closed, or at least closed to that
 * operation.  That this exception is thrown does not necessarily imply that
 * the channel is completely closed.  A socket channel whose write half has
 * been shut down, for example, may still be open for reading.
 *
 * <p>
 *  尝试在关闭或至少关闭该操作的通道上调用或完成I / O操作时抛出的检查异常。抛出此异常并不一定意味着通道完全关闭。例如,一半已被关闭的套接字通道仍然可以打开以供读取。
 * 
 * 
 * @since 1.4
 */

public class ClosedChannelException
    extends java.io.IOException
{

    private static final long serialVersionUID = 882777185433553857L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     */
    public ClosedChannelException() { }

}
