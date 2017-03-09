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
 * Exception which occurs when trying to register an  object in the MBean server that is not a JMX compliant MBean.
 *
 * <p>
 *  尝试在不是符合JMX的MBean的MBean服务器中注册对象时发生的异常。
 * 
 * 
 * @since 1.5
 */
public class NotCompliantMBeanException  extends OperationsException {


    /* Serial version */
    private static final long serialVersionUID = 5175579583207963577L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public NotCompliantMBeanException()  {
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
    public NotCompliantMBeanException(String message)  {
        super(message);
    }

 }
