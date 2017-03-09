/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.login;

/**
 * This is the basic login exception.
 *
 * <p>
 *  这是基本的登录异常。
 * 
 * 
 * @see javax.security.auth.login.LoginContext
 */

public class LoginException extends java.security.GeneralSecurityException {

    private static final long serialVersionUID = -4679091624035232488L;

    /**
     * Constructs a LoginException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的LoginException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public LoginException() {
        super();
    }

    /**
     * Constructs a LoginException with the specified detail message.
     * A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的LoginException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public LoginException(String msg) {
        super(msg);
    }
}
