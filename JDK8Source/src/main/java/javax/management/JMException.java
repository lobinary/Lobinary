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
 * Exceptions thrown by JMX implementations.
 * It does not include the runtime exceptions.
 *
 * <p>
 *  JMX实现抛出的异常。它不包括运行时异常。
 * 
 * 
 * @since 1.5
 */
public class JMException extends java.lang.Exception   {

    /* Serial version */
    private static final long serialVersionUID = 350520924977331825L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public JMException() {
        super();
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * <p>
     *  允许指定特定错误消息的构造方法。
     * 
     * @param msg the detail message.
     */
    public JMException(String msg) {
        super(msg);
    }

}
