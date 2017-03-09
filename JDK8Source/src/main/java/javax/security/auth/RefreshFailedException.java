/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth;

/**
 * Signals that a {@code refresh} operation failed.
 *
 * <p> This exception is thrown by credentials implementing
 * the {@code Refreshable} interface when the {@code refresh}
 * method fails.
 *
 * <p>
 *  表示{@code refresh}操作失败。
 * 
 *  <p>当{@code refresh}方法失败时,实施{@code Refreshable}接口的凭据会抛出此异常。
 * 
 */
public class RefreshFailedException extends Exception {

    private static final long serialVersionUID = 5058444488565265840L;

    /**
     * Constructs a RefreshFailedException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的RefreshFailedException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public RefreshFailedException() {
        super();
    }

    /**
     * Constructs a RefreshFailedException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的RefreshFailedException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public RefreshFailedException(String msg) {
        super(msg);
    }
}
