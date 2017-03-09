/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.IOException;

/**
 * Thrown to indicate that there is an error creating or accessing a Socket.
 *
 * <p>
 *  抛出以指示创建或访问Socket时出现错误。
 * 
 * 
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class SocketException extends IOException {
    private static final long serialVersionUID = -5935874303556886934L;

    /**
     * Constructs a new {@code SocketException} with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的{@code SocketException}。
     * 
     * 
     * @param msg the detail message.
     */
    public SocketException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new {@code SocketException} with no detail message.
     * <p>
     *  构造一个没有详细消息的新{@code SocketException}。
     */
    public SocketException() {
    }
}
