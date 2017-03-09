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
 * Represents exceptions thrown in the MBean server when performing operations
 * on MBeans.
 *
 * <p>
 *  表示在对MBean执行操作时在MBean服务器中抛出的异常。
 * 
 * 
 * @since 1.5
 */
public class OperationsException extends JMException   {

    /* Serial version */
    private static final long serialVersionUID = -4967597595580536216L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public OperationsException() {
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
    public OperationsException(String message) {
        super(message);
    }

}
