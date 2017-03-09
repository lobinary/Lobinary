/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Runtime exceptions emitted by JMX implementations.
 *
 * <p>
 *  JMX实现发出的运行时异常。
 * 
 * 
 * @since 1.5
 */
public class JMRuntimeException extends RuntimeException   {

    /* Serial version */
    private static final long serialVersionUID = 6573344628407841861L;

    /**
     * Default constructor.
     * <p>
     *  默认构造函数。
     * 
     */
    public JMRuntimeException() {
        super();
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * <p>
     *  允许指定特定错误消息的构造方法。
     * 
     * 
     * @param message the detail message.
     */
    public JMRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructor with a nested exception.  This constructor is
     * package-private because it arrived too late for the JMX 1.2
     * specification.  A later version may make it public.
     * <p>
     *  具有嵌套异常的构造方法。这个构造函数是package-private的,因为它对于JMX 1.2规范来得太晚了。以后的版本可能会公开。
     */
    JMRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
