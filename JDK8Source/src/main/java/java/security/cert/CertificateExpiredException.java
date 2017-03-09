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
 * Certificate Expired Exception. This is thrown whenever the current
 * {@code Date} or the specified {@code Date} is after the
 * {@code notAfter} date/time specified in the validity period
 * of the certificate.
 *
 * <p>
 *  证书已过期。每当当前{@code Date}或指定的{@code Date}位于证书有效期内指定的{@code notAfter}日期/时间之后时,就会抛出此异常。
 * 
 * 
 * @author Hemma Prafullchandra
 */
public class CertificateExpiredException extends CertificateException {

    private static final long serialVersionUID = 9071001339691533771L;

    /**
     * Constructs a CertificateExpiredException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造一个没有详细消息的CertificateExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CertificateExpiredException() {
        super();
    }

    /**
     * Constructs a CertificateExpiredException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的CertificateExpiredException。详细消息是描述此特殊异常的字符串。
     * 
     * @param message the detail message.
     */
    public CertificateExpiredException(String message) {
        super(message);
    }
}
