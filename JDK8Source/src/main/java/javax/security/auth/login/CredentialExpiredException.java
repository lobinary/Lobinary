/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Signals that a {@code Credential} has expired.
 *
 * <p> This exception is thrown by LoginModules when they determine
 * that a {@code Credential} has expired.
 * For example, a {@code LoginModule} authenticating a user
 * in its {@code login} method may determine that the user's
 * password, although entered correctly, has expired.  In this case
 * the {@code LoginModule} throws this exception to notify
 * the application.  The application can then take the appropriate
 * steps to assist the user in updating the password.
 *
 * <p>
 *  表示{@code Credential}已过期。
 * 
 *  <p>当LoginModules确定{@code Credential}已过期时,抛出此异常。
 * 例如,在{@code login}方法中对用户进行身份验证的{@code LoginModule}可能会确定用户的密码(尽管输入正确)已过期。
 * 在这种情况下,{@code LoginModule}会抛出此异常以通知应用程序。应用程序然后可以采取适当的步骤来帮助用户更新密码。
 * 
 */
public class CredentialExpiredException extends CredentialException {

    private static final long serialVersionUID = -5344739593859737937L;

    /**
     * Constructs a CredentialExpiredException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的CredentialExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CredentialExpiredException() {
        super();
    }

    /**
     * Constructs a CredentialExpiredException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的CredentialExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public CredentialExpiredException(String msg) {
        super(msg);
    }
}
