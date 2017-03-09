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
 * Signals that a user account has expired.
 *
 * <p> This exception is thrown by LoginModules when they determine
 * that an account has expired.  For example, a {@code LoginModule},
 * after successfully authenticating a user, may determine that the
 * user's account has expired.  In this case the {@code LoginModule}
 * throws this exception to notify the application.  The application can
 * then take the appropriate steps to notify the user.
 *
 * <p>
 *  表示用户帐户已过期。
 * 
 *  <p>当LoginModules确定帐户已过期时,抛出此异常。例如,{@code LoginModule}在成功认证用户之后可以确定用户的帐户已过期。
 * 在这种情况下,{@code LoginModule}会抛出此异常以通知应用程序。然后应用程序可以采取适当的步骤通知用户。
 * 
 */
public class AccountExpiredException extends AccountException {

    private static final long serialVersionUID = -6064064890162661560L;

    /**
     * Constructs a AccountExpiredException with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的AccountExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public AccountExpiredException() {
        super();
    }

    /**
     * Constructs a AccountExpiredException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的AccountExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public AccountExpiredException(String msg) {
        super(msg);
    }
}
