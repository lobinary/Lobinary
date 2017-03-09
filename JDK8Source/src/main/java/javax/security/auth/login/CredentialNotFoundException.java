/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that a credential was not found.
 *
 * <p> This exception may be thrown by a LoginModule if it is unable
 * to locate a credential necessary to perform authentication.
 *
 * <p>
 *  表示找不到凭证的信号。
 * 
 *  <p>如果LoginModule无法找到执行身份验证所需的凭据,则可能会抛出此异常。
 * 
 * 
 * @since 1.5
 */
public class CredentialNotFoundException extends CredentialException {

    private static final long serialVersionUID = -7779934467214319475L;

    /**
     * Constructs a CredentialNotFoundException with no detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的CredentialNotFoundException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CredentialNotFoundException() {
        super();
    }

    /**
     * Constructs a CredentialNotFoundException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的CredentialNotFoundException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public CredentialNotFoundException(String msg) {
        super(msg);
    }
}
