/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * This exception, designed for use by the JCA/JCE engine classes,
 * is thrown when an invalid parameter is passed
 * to a method.
 *
 * <p>
 *  当将无效的参数传递给方法时,会抛出此异常,旨在供JCA / JCE引擎类使用。
 * 
 * 
 * @author Benjamin Renaud
 */

public class InvalidParameterException extends IllegalArgumentException {

    private static final long serialVersionUID = -857968536935667808L;

    /**
     * Constructs an InvalidParameterException with no detail message.
     * A detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造无详细消息的InvalidParameterException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public InvalidParameterException() {
        super();
    }

    /**
     * Constructs an InvalidParameterException with the specified
     * detail message.  A detail message is a String that describes
     * this particular exception.
     *
     * <p>
     *  构造具有指定详细消息的InvalidParameterException。详细消息是描述此特殊异常的字符串。
     * 
     * @param msg the detail message.
     */
    public InvalidParameterException(String msg) {
        super(msg);
    }
}
