/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.activation;

/**
 * This exception is thrown by the RMI runtime when activation
 * fails during a remote call to an activatable object.
 *
 * <p>
 *  在对可激活对象的远程调用期间激活失败时,RMI运行时抛出此异常。
 * 
 * 
 * @author      Ann Wollrath
 * @since       1.2
 */
public class ActivateFailedException extends java.rmi.RemoteException {

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = 4863550261346652506L;

    /**
     * Constructs an <code>ActivateFailedException</code> with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> ActivateFailedException </code>。
     * 
     * 
     * @param s the detail message
     * @since 1.2
     */
    public ActivateFailedException(String s) {
        super(s);
    }

    /**
     * Constructs an <code>ActivateFailedException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> ActivateFailedException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since 1.2
     */
    public ActivateFailedException(String s, Exception ex) {
        super(s, ex);
    }
}
