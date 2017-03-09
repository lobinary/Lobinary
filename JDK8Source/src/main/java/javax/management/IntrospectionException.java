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
 * An exception occurred during the introspection of an MBean.
 *
 * <p>
 *  在MBean的内省期间发生异常。
 * 
 * 
 * @since 1.5
 */
public class IntrospectionException extends OperationsException   {

    /* Serial version */
    private static final long serialVersionUID = 1054516935875481725L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public IntrospectionException() {
        super();
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * <p>
     *  允许指定特定错误消息的构造方法。
     * 
     * @param message the detail message.
     */
    public IntrospectionException(String message) {
        super(message);
    }
}
