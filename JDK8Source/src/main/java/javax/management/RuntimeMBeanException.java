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
 * Represents runtime exceptions thrown by MBean methods in
 * the agent. It "wraps" the actual <CODE>java.lang.RuntimeException</CODE> exception thrown.
 * This exception will be built by the MBeanServer when a call to an
 * MBean method throws a runtime exception.
 *
 * <p>
 *  表示代理中MBean方法抛出的运行时异常。它"封装"实际的<CODE> java.lang.RuntimeException </CODE>抛出的异常。
 * 当对MBean方法的调用抛出运行时异常时,MBeanServer将构建此异常。
 * 
 * 
 * @since 1.5
 */
public class RuntimeMBeanException extends JMRuntimeException   {

    /* Serial version */
    private static final long serialVersionUID = 5274912751982730171L;

    /**
    /* <p>
    /* 
     * @serial The encapsulated {@link RuntimeException}
     */
    private java.lang.RuntimeException runtimeException ;


    /**
     * Creates a <CODE>RuntimeMBeanException</CODE> that wraps the actual <CODE>java.lang.RuntimeException</CODE>.
     *
     * <p>
     *  创建包装实际<CODE> java.lang.RuntimeException </CODE>的<CODE> RuntimeMBeanException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     */
    public RuntimeMBeanException(java.lang.RuntimeException e) {
        super() ;
        runtimeException = e ;
    }

    /**
     * Creates a <CODE>RuntimeMBeanException</CODE> that wraps the actual <CODE>java.lang.RuntimeException</CODE> with
     * a detailed message.
     *
     * <p>
     *  创建一个包含详细消息的包含实际<CODE> java.lang.RuntimeException </CODE>的<CODE> RuntimeMBeanException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public RuntimeMBeanException(java.lang.RuntimeException e, String message) {
        super(message) ;
        runtimeException = e ;
    }

    /**
     * Returns the actual {@link RuntimeException} thrown.
     *
     * <p>
     *  返回实际的{@link RuntimeException}抛出。
     * 
     * 
     * @return the wrapped {@link RuntimeException}.
     */
    public java.lang.RuntimeException getTargetException()  {
        return runtimeException ;
    }

    /**
     * Returns the actual {@link RuntimeException} thrown.
     *
     * <p>
     *  返回实际的{@link RuntimeException}抛出。
     * 
     * @return the wrapped {@link RuntimeException}.
     */
    public Throwable getCause() {
        return runtimeException;
    }
}
