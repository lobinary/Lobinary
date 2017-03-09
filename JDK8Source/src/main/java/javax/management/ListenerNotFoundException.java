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
 * The specified MBean listener does not exist in the repository.
 *
 * <p>
 *  指定的MBean侦听器不存在于存储库中。
 * 
 * 
 * @since 1.5
 */
public class ListenerNotFoundException extends OperationsException   {

    /* Serial version */
    private static final long serialVersionUID = -7242605822448519061L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public ListenerNotFoundException() {
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
    public ListenerNotFoundException(String message) {
        super(message);
    }

}
