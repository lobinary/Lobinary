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
 * When a <CODE>java.lang.Error</CODE> occurs in the agent it should be caught and
 * re-thrown as a <CODE>RuntimeErrorException</CODE>.
 *
 * <p>
 *  当在代理中发生<CODE> java.lang.Error </CODE>时,应该被捕获并重新抛出为<CODE> RuntimeErrorException </CODE>。
 * 
 * 
 * @since 1.5
 */
public class RuntimeErrorException extends JMRuntimeException   {

    /* Serial version */
    private static final long serialVersionUID = 704338937753949796L;

    /**
    /* <p>
    /* 
     * @serial The encapsulated {@link Error}
     */
    private java.lang.Error error ;

    /**
     * Default constructor.
     *
     * <p>
     *  默认构造函数。
     * 
     * 
     * @param e the wrapped error.
     */
    public RuntimeErrorException(java.lang.Error e) {
      super();
      error = e ;
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * <p>
     *  允许指定特定错误消息的构造方法。
     * 
     * 
     * @param e the wrapped error.
     * @param message the detail message.
     */
    public RuntimeErrorException(java.lang.Error e, String message) {
       super(message);
       error = e ;
    }

    /**
     * Returns the actual {@link Error} thrown.
     *
     * <p>
     *  返回实际的{@link错误}抛出。
     * 
     * 
     * @return the wrapped {@link Error}.
     */
    public java.lang.Error getTargetError()  {
        return error ;
    }

    /**
     * Returns the actual {@link Error} thrown.
     *
     * <p>
     *  返回实际的{@link错误}抛出。
     * 
     * @return the wrapped {@link Error}.
     */
    public Throwable getCause() {
        return error;
    }
}
