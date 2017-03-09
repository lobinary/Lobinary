/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1997, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that an AWT component is not in an appropriate state for
 * the requested operation.
 *
 * <p>
 *  表示AWT组件对于所请求的操作不处于适当的状态。
 * 
 * 
 * @author      Jonni Kanerva
 */
public class IllegalComponentStateException extends IllegalStateException {
    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -1889339587208144238L;

    /**
     * Constructs an IllegalComponentStateException with no detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的IllegalComponentStateException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public IllegalComponentStateException() {
        super();
    }

    /**
     * Constructs an IllegalComponentStateException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造具有指定详细消息的IllegalComponentStateException。详细消息是描述此特殊异常的字符串。
     * 
     * @param s the String that contains a detailed message
     */
    public IllegalComponentStateException(String s) {
        super(s);
    }
}
