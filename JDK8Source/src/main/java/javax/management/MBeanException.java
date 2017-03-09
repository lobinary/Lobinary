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
 * Represents "user defined" exceptions thrown by MBean methods
 * in the agent. It "wraps" the actual "user defined" exception thrown.
 * This exception will be built by the MBeanServer when a call to an
 * MBean method results in an unknown exception.
 *
 * <p>
 *  表示代理中MBean方法抛出的"用户定义"异常。它"包装"实际抛出的"用户定义"异常。当对MBean方法的调用导致未知异常时,MBeanServer将构建此异常。
 * 
 * 
 * @since 1.5
 */
public class MBeanException extends JMException   {


    /* Serial version */
    private static final long serialVersionUID = 4066342430588744142L;

    /**
    /* <p>
    /* 
     * @serial Encapsulated {@link Exception}
     */
    private java.lang.Exception exception ;


    /**
     * Creates an <CODE>MBeanException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE>.
     *
     * <p>
     *  创建包装实际<CODE> java.lang.Exception </CODE>的<CODE> MBeanException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     */
    public MBeanException(java.lang.Exception e) {
        super() ;
        exception = e ;
    }

    /**
     * Creates an <CODE>MBeanException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE> with
     * a detail message.
     *
     * <p>
     *  创建一个包含详细消息的包含实际<CODE> java.lang.Exception </CODE>的<CODE> MBeanException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public MBeanException(java.lang.Exception e, String message) {
        super(message) ;
        exception = e ;
    }


    /**
     * Return the actual {@link Exception} thrown.
     *
     * <p>
     *  返回实际的{@link Exception}抛出。
     * 
     * 
     * @return the wrapped exception.
     */
    public Exception getTargetException()  {
        return exception;
    }

    /**
     * Return the actual {@link Exception} thrown.
     *
     * <p>
     *  返回实际的{@link Exception}抛出。
     * 
     * @return the wrapped exception.
     */
    public Throwable getCause() {
        return exception;
    }
}
