/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi;

/**
 * An <code>UnexpectedException</code> is thrown if the client of a
 * remote method call receives, as a result of the call, a checked
 * exception that is not among the checked exception types declared in the
 * <code>throws</code> clause of the method in the remote interface.
 *
 * <p>
 *  如果远程方法调用的客户端作为调用的结果接收到不在<code> throws </code>中声明的已检查异常类型中的已检查异常,则抛出<code> UnexpectedException </code>
 * 在远程接口中的方法的子句。
 * 
 * 
 * @author  Roger Riggs
 * @since   JDK1.1
 */
public class UnexpectedException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 1800467484195073863L;

    /**
     * Constructs an <code>UnexpectedException</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> UnexpectedException </code>。
     * 
     * 
     * @param s the detail message
     * @since JDK1.1
     */
    public UnexpectedException(String s) {
        super(s);
    }

    /**
     * Constructs a <code>UnexpectedException</code> with the specified
     * detail message and nested exception.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套异常的<code> UnexpectedException </code>。
     * 
     * @param s the detail message
     * @param ex the nested exception
     * @since JDK1.1
     */
    public UnexpectedException(String s, Exception ex) {
        super(s, ex);
    }
}
