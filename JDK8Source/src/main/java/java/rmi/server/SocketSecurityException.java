/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

/**
 * An obsolete subclass of {@link ExportException}.
 *
 * <p>
 *  {@link ExportException}的过时子类。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @deprecated This class is obsolete. Use {@link ExportException} instead.
 */
@Deprecated
public class SocketSecurityException extends ExportException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -7622072999407781979L;

    /**
     * Constructs an <code>SocketSecurityException</code> with the specified
     * detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> SocketSecurityException </code>。
     * 
     * 
     * @param s the detail message.
     * @since JDK1.1
     */
    public SocketSecurityException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>SocketSecurityException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> SocketSecurityException </code>。
     * 
     * @param s the detail message.
     * @param ex the nested exception
     * @since JDK1.1
     */
    public SocketSecurityException(String s, Exception ex) {
        super(s, ex);
    }

}
