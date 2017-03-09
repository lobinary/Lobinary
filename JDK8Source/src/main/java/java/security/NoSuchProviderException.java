/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * This exception is thrown when a particular security provider is
 * requested but is not available in the environment.
 *
 * <p>
 *  在请求特定安全提供程序但在环境中不可用时抛出此异常。
 * 
 * 
 * @author Benjamin Renaud
 */

public class NoSuchProviderException extends GeneralSecurityException {

    private static final long serialVersionUID = 8488111756688534474L;

    /**
     * Constructs a NoSuchProviderException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造没有详细消息的NoSuchProviderException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public NoSuchProviderException() {
        super();
    }

    /**
     * Constructs a NoSuchProviderException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的NoSuchProviderException。详细消息是描述此特殊异常的字符串。
     * 
     * @param msg the detail message.
     */
    public NoSuchProviderException(String msg) {
        super(msg);
    }
}
