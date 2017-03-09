/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * A generic credential exception.
 *
 * <p>
 *  通用凭据异常。
 * 
 * 
 * @since 1.5
 */
public class CredentialException extends LoginException {

    private static final long serialVersionUID = -4772893876810601859L;

    /**
     * Constructs a CredentialException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造没有详细消息的CredentialException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CredentialException() {
        super();
    }

    /**
     * Constructs a CredentialException with the specified detail message.
     * A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的CredentialException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public CredentialException(String msg) {
        super(msg);
    }
}
