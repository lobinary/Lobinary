/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * <p>
 * The <code> TooManyListenersException </code> Exception is used as part of
 * the Java Event model to annotate and implement a unicast special case of
 * a multicast Event Source.
 * </p>
 * <p>
 * The presence of a "throws TooManyListenersException" clause on any given
 * concrete implementation of the normally multicast "void addXyzEventListener"
 * event listener registration pattern is used to annotate that interface as
 * implementing a unicast Listener special case, that is, that one and only
 * one Listener may be registered on the particular event listener source
 * concurrently.
 * </p>
 *
 * <p>
 * <p>
 *  <code> TooManyListenersException </code>异常用作Java事件模型的一部分,以注释和实现多播事件源的单播特殊情况。
 * </p>
 * <p>
 *  在通常多播"void addXyzEventListener"事件侦听器注册模式的任何给定的具体实现上存在"throws TooManyListenersException"子句用于将该接口注释为实现
 * 单播侦听器特殊情况,即只有一个侦听器可以同时在特定事件侦听器源上注册。
 * </p>
 * 
 * @see java.util.EventObject
 * @see java.util.EventListener
 *
 * @author Laurence P. G. Cable
 * @since  JDK1.1
 */

public class TooManyListenersException extends Exception {
    private static final long serialVersionUID = 5074640544770687831L;

    /**
     * Constructs a TooManyListenersException with no detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     * 
     */

    public TooManyListenersException() {
        super();
    }

    /**
     * Constructs a TooManyListenersException with the specified detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的TooManyListenersException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param s the detail message
     */

    public TooManyListenersException(String s) {
        super(s);
    }
}
