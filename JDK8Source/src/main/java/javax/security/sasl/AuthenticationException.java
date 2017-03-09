/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.sasl;

/**
 * This exception is thrown by a SASL mechanism implementation
 * to indicate that the SASL
 * exchange has failed due to reasons related to authentication, such as
 * an invalid identity, passphrase, or key.
 * <p>
 * Note that the lack of an AuthenticationException does not mean that
 * the failure was not due to an authentication error.  A SASL mechanism
 * implementation might throw the more general SaslException instead of
 * AuthenticationException if it is unable to determine the nature
 * of the failure, or if does not want to disclose the nature of
 * the failure, for example, due to security reasons.
 *
 * <p>
 *  SASL机制实现抛出此异常以指示SASL交换由于与认证相关的原因(例如无效的标识,密码或密钥)而失败。
 * <p>
 *  请注意,缺少AuthenticationException并不意味着失败不是由于身份验证错误。
 * 如果SASL机制实现无法确定故障的性质,或者如果不想披露故障的性质(例如,由于安全原因),则SASL机制实现可能抛出更一般的SaslException而不是AuthenticationException
 * 。
 *  请注意,缺少AuthenticationException并不意味着失败不是由于身份验证错误。
 * 
 * 
 * @since 1.5
 *
 * @author Rosanna Lee
 * @author Rob Weltman
 */
public class AuthenticationException extends SaslException {
    /**
     * Constructs a new instance of {@code AuthenticationException}.
     * The root exception and the detailed message are null.
     * <p>
     *  构造{@code AuthenticationException}的新实例。根异常和详细消息为null。
     * 
     */
    public AuthenticationException () {
        super();
    }

    /**
     * Constructs a new instance of {@code AuthenticationException}
     * with a detailed message.
     * The root exception is null.
     * <p>
     *  使用详细消息构造{@code AuthenticationException}的新实例。根异常为null。
     * 
     * 
     * @param detail A possibly null string containing details of the exception.
     *
     * @see java.lang.Throwable#getMessage
     */
    public AuthenticationException (String detail) {
        super(detail);
    }

    /**
     * Constructs a new instance of {@code AuthenticationException} with a detailed message
     * and a root exception.
     *
     * <p>
     *  构造具有详细消息和根异常的{@code AuthenticationException}的新实例。
     * 
     * @param detail A possibly null string containing details of the exception.
     * @param ex A possibly null root exception that caused this exception.
     *
     * @see java.lang.Throwable#getMessage
     * @see #getCause
     */
    public AuthenticationException (String detail, Throwable ex) {
        super(detail, ex);
    }

    /** Use serialVersionUID from JSR 28 RI for interoperability */
    private static final long serialVersionUID = -3579708765071815007L;
}
