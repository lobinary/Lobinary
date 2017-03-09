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
 * Thrown to indicate that there is an error in the underlying
 * protocol, such as a TCP error.
 *
 * <p>
 *  抛出以指示底层协议中存在错误,例如TCP错误。
 * 
 * 
 * @author  Chris Warth
 * @since   JDK1.0
 */
public
class ProtocolException extends IOException {
    private static final long serialVersionUID = -6098449442062388080L;

    /**
     * Constructs a new {@code ProtocolException} with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的{@code ProtocolException}。
     * 
     * 
     * @param   host   the detail message.
     */
    public ProtocolException(String host) {
        super(host);
    }

    /**
     * Constructs a new {@code ProtocolException} with no detail message.
     * <p>
     *  构造一个没有详细消息的新{@code ProtocolException}。
     */
    public ProtocolException() {
    }
}
