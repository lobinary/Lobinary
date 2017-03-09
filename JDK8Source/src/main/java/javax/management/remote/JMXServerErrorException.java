/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2007, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.remote;

import java.io.IOException;

// imports for javadoc
import javax.management.MBeanServer;

/**
 * Exception thrown as the result of a remote {@link MBeanServer}
 * method invocation when an <code>Error</code> is thrown while
 * processing the invocation in the remote MBean server.  A
 * <code>JMXServerErrorException</code> instance contains the original
 * <code>Error</code> that occurred as its cause.
 *
 * <p>
 *  在处理远程MBean服务器中的调用时抛出<code>错误</code>时,作为远程​​{@link MBeanServer}方法调用的结果抛出异常。
 *  <code> JMXServerErrorException </code>实例包含作为其原因发生的原始<code>错误</code>。
 * 
 * 
 * @see java.rmi.ServerError
 * @since 1.5
 */
public class JMXServerErrorException extends IOException {

    private static final long serialVersionUID = 3996732239558744666L;

    /**
     * Constructs a <code>JMXServerErrorException</code> with the specified
     * detail message and nested error.
     *
     * <p>
     *  构造具有指定的详细消息和嵌套错误的<code> JMXServerErrorException </code>。
     * 
     * 
     * @param s the detail message.
     * @param err the nested error.  An instance of this class can be
     * constructed where this parameter is null, but the standard
     * connectors will never do so.
     */
    public JMXServerErrorException(String s, Error err) {
        super(s);
        cause = err;
    }

    public Throwable getCause() {
        return cause;
    }

    /**
    /* <p>
    /* 
     * @serial An {@link Error} that caused this exception to be thrown.
     * @see #getCause()
     **/
    private final Error cause;
}
