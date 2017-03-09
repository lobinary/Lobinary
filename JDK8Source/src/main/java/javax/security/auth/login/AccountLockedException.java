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
 * Signals that an account was locked.
 *
 * <p> This exception may be thrown by a LoginModule if it
 * determines that authentication is being attempted on a
 * locked account.
 *
 * <p>
 *  表示帐户已锁定。
 * 
 *  <p>如果LoginModule确定正在对锁定的帐户尝试身份验证,则此异常可能会抛出。
 * 
 * 
 * @since 1.5
 */
public class AccountLockedException extends AccountException {

    private static final long serialVersionUID = 8280345554014066334L;

    /**
     * Constructs a AccountLockedException with no detail message.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的AccountLockedException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public AccountLockedException() {
        super();
    }

    /**
     * Constructs a AccountLockedException with the specified
     * detail message. A detail message is a String that describes
     * this particular exception.
     *
     * <p>
     *
     * <p>
     *  构造具有指定详细消息的AccountLockedException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param msg the detail message.
     */
    public AccountLockedException(String msg) {
        super(msg);
    }
}
