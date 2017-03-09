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
 * Thrown to indicate that an unknown service exception has
 * occurred. Either the MIME type returned by a URL connection does
 * not make sense, or the application is attempting to write to a
 * read-only URL connection.
 *
 * <p>
 *  抛出以指示发生未知服务异常。由URL连接返回的MIME类型没有意义,或者应用程序正在尝试写入只读URL连接。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public class UnknownServiceException extends IOException {
    private static final long serialVersionUID = -4169033248853639508L;

    /**
     * Constructs a new {@code UnknownServiceException} with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的新{@code UnknownServiceException}。
     * 
     */
    public UnknownServiceException() {
    }

    /**
     * Constructs a new {@code UnknownServiceException} with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造新的{@code UnknownServiceException}。
     * 
     * @param   msg   the detail message.
     */
    public UnknownServiceException(String msg) {
        super(msg);
    }
}
