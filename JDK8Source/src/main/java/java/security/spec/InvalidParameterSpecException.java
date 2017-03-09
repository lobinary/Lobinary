/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.security.spec;

import java.security.GeneralSecurityException;

/**
 * This is the exception for invalid parameter specifications.
 *
 * <p>
 *  这是无效参数规范的例外。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.AlgorithmParameters
 * @see AlgorithmParameterSpec
 * @see DSAParameterSpec
 *
 * @since 1.2
 */

public class InvalidParameterSpecException extends GeneralSecurityException {

    private static final long serialVersionUID = -970468769593399342L;

    /**
     * Constructs an InvalidParameterSpecException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造无详细消息的InvalidParameterSpecException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public InvalidParameterSpecException() {
        super();
    }

    /**
     * Constructs an InvalidParameterSpecException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  使用指定的详细消息构造InvalidParameterSpecException。详细消息是描述此特殊异常的字符串。
     * 
     * @param msg the detail message.
     */
    public InvalidParameterSpecException(String msg) {
        super(msg);
    }
}
