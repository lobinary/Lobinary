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
 * Represents runtime exceptions thrown in the agent when performing operations on MBeans.
 * It wraps the actual <CODE>java.lang.RuntimeException</CODE> thrown.
 *
 * <p>
 *  表示在对MBean执行操作时在代理中抛出的运行时异常。它包装实际的<CODE> java.lang.RuntimeException </CODE>抛出。
 * 
 * 
 * @since 1.5
 */
public class RuntimeOperationsException extends JMRuntimeException   {

    /* Serial version */
    private static final long serialVersionUID = -8408923047489133588L;

    /**
    /* <p>
    /* 
     * @serial The encapsulated {@link RuntimeException}
     */
    private java.lang.RuntimeException runtimeException ;


    /**
     * Creates a <CODE>RuntimeOperationsException</CODE> that wraps the actual <CODE>java.lang.RuntimeException</CODE>.
     *
     * <p>
     *  创建包装实际<CODE> java.lang.RuntimeException </CODE>的<CODE> RuntimeOperationsException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     */
    public RuntimeOperationsException(java.lang.RuntimeException e) {
        super() ;
        runtimeException = e ;
    }

    /**
     * Creates a <CODE>RuntimeOperationsException</CODE> that wraps the actual <CODE>java.lang.RuntimeException</CODE>
     * with a detailed message.
     *
     * <p>
     *  创建一个包含详细消息的包含实际<CODE> java.lang.RuntimeException </CODE>的<CODE> RuntimeOperationsException </CODE>。
     * 
     * 
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public RuntimeOperationsException(java.lang.RuntimeException e, String message) {
        super(message);
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
