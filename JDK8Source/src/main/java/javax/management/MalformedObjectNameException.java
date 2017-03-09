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
 * The format of the string does not correspond to a valid ObjectName.
 *
 * <p>
 *  字符串的格式不对应于有效的ObjectName。
 * 
 * 
 * @since 1.5
 */
public class MalformedObjectNameException extends OperationsException   {

    /* Serial version */
    private static final long serialVersionUID = -572689714442915824L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public MalformedObjectNameException() {
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
    public MalformedObjectNameException(String message) {
        super(message);
    }
}
