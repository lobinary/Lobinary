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
 * Signals that a {@code destroy} operation failed.
 *
 * <p> This exception is thrown by credentials implementing
 * the {@code Destroyable} interface when the {@code destroy}
 * method fails.
 *
 * <p>
 *  表示{@code destroy}操作失败。
 * 
 *  <p>当{@code destroy}方法失败时,执行{@code Destroyable}接口的凭据会抛出此异常。
 * 
 */
public class DestroyFailedException extends Exception {

    private static final long serialVersionUID = -7790152857282749162L;

    /**
     * Constructs a DestroyFailedException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的DestroyFailedException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public DestroyFailedException() {
        super();
    }

    /**
     * Constructs a DestroyFailedException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的DestroyFailedException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public DestroyFailedException(String msg) {
        super(msg);
    }
}
