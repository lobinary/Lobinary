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
 * The MBean is already registered in the repository.
 *
 * <p>
 *  MBean已在注册库中注册。
 * 
 * 
 * @since 1.5
 */
public class InstanceAlreadyExistsException extends OperationsException   {

    /* Serial version */
    private static final long serialVersionUID = 8893743928912733931L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public InstanceAlreadyExistsException() {
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
    public InstanceAlreadyExistsException(String message) {
        super(message);
    }

 }
