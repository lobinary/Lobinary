/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;


/**
 * Signals that an Abstract Window Toolkit exception has occurred.
 *
 * <p>
 *  表示发生了Abstract Window Toolkit异常。
 * 
 * 
 * @author      Arthur van Hoff
 */
public class AWTException extends Exception {

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -1900414231151323879L;

    /**
     * Constructs an instance of <code>AWTException</code> with the
     * specified detail message. A detail message is an
     * instance of <code>String</code> that describes this particular
     * exception.
     * <p>
     *  使用指定的详细消息构造<code> AWTException </code>的实例。详细消息是描述此特定异常的<code> String </code>的实例。
     * 
     * @param   msg     the detail message
     * @since   JDK1.0
     */
    public AWTException(String msg) {
        super(msg);
    }
}
