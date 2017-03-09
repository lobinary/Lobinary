/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;



/**
 * Represents exceptions thrown in the MBean server when using the
 * java.lang.reflect classes to invoke methods on MBeans. It "wraps" the
 * actual java.lang.Exception thrown.
 *
 * <p>
 *  表示在使用java.lang.reflect类调用MBean上的方法时在MBean服务器中抛出的异常。它"包装"抛出的实际java.lang.Exception。
 * 
 * 
 * @since 1.5
 */
public class ReflectionException extends JMException   {

    /* Serial version */
    private static final long serialVersionUID = 9170809325636915553L;

    /**
    /* <p>
    /* 
     * @serial The wrapped {@link Exception}
     */
    private java.lang.Exception exception ;


    /**
     * Creates a <CODE>ReflectionException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE>.
     *
     * <p>
     *  创建包装实际<CODE> java.lang.Exception </CODE>的<CODE> ReflectionException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     */
    public ReflectionException(java.lang.Exception e) {
        super() ;
        exception = e ;
    }

    /**
     * Creates a <CODE>ReflectionException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE> with
     * a detail message.
     *
     * <p>
     *  创建一个包含详细消息的包含实际<CODE> java.lang.Exception </CODE>的<CODE> ReflectionException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public ReflectionException(java.lang.Exception e, String message) {
        super(message) ;
        exception = e ;
    }

    /**
     * Returns the actual {@link Exception} thrown.
     *
     * <p>
     *  返回实际的{@link Exception}抛出。
     * 
     * 
     * @return the wrapped {@link Exception}.
     */
    public java.lang.Exception getTargetException()  {
        return exception ;
    }

    /**
     * Returns the actual {@link Exception} thrown.
     *
     * <p>
     *  返回实际的{@link Exception}抛出。
     * 
     * @return the wrapped {@link Exception}.
     */
    public Throwable getCause() {
        return exception;
    }
}
