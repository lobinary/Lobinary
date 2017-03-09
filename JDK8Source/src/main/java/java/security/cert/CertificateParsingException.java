/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.cert;

/**
 * Certificate Parsing Exception. This is thrown whenever an
 * invalid DER-encoded certificate is parsed or unsupported DER features
 * are found in the Certificate.
 *
 * <p>
 *  证书分析异常。每当解析无效的DER编码的证书或在证书中找到不支持的DER功能时,都会抛出此错误。
 * 
 * 
 * @author Hemma Prafullchandra
 */
public class CertificateParsingException extends CertificateException {

    private static final long serialVersionUID = -7989222416793322029L;

    /**
     * Constructs a CertificateParsingException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个没有详细消息的CertificateParsingException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CertificateParsingException() {
        super();
    }

    /**
     * Constructs a CertificateParsingException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  使用指定的详细消息构造CertificateParsingException。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param message the detail message.
     */
    public CertificateParsingException(String message) {
        super(message);
    }

    /**
     * Creates a {@code CertificateParsingException} with the specified
     * detail message and cause.
     *
     * <p>
     *  使用指定的详细消息和原因创建{@code CertificateParsingException}。
     * 
     * 
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CertificateParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a {@code CertificateParsingException} with the
     * specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of
     * {@code cause}).
     *
     * <p>
     *  创建具有指定原因的{@code CertificateParsingException}和{@code(cause == null?null：cause.toString())}的详细消息(通常包含{@code cause}
     * 的类和详细信息) 。
     * 
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public CertificateParsingException(Throwable cause) {
        super(cause);
    }
}
