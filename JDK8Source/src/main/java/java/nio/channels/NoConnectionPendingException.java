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
 * Unchecked exception thrown when the {@link SocketChannel#finishConnect
 * finishConnect} method of a {@link SocketChannel} is invoked without first
 * successfully invoking its {@link SocketChannel#connect connect} method.
 *
 * <p>
 *  在未调用其{@link SocketChannel#connect connect}方法的情况下调用{@link SocketChannel}的{@link SocketChannel#finishConnect finishConnect}
 * 方法时抛出未经检查的异常。
 * 
 * 
 * @since 1.4
 */

public class NoConnectionPendingException
    extends IllegalStateException
{

    private static final long serialVersionUID = -8296561183633134743L;

    /**
     * Constructs an instance of this class.
     * <p>
     */
    public NoConnectionPendingException() { }

}
