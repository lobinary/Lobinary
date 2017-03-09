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
 * Certificate is not yet valid exception. This is thrown whenever
 * the current {@code Date} or the specified {@code Date}
 * is before the {@code notBefore} date/time in the Certificate
 * validity period.
 *
 * <p>
 *  证书尚未生效。每当当前{@code Date}或指定的{@code Date}在证书有效期内的{@code notBefore}日期/时间之前时,都会抛出此错误。
 * 
 * 
 * @author Hemma Prafullchandra
 */
public class CertificateNotYetValidException extends CertificateException {

    static final long serialVersionUID = 4355919900041064702L;

    /**
     * Constructs a CertificateNotYetValidException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     * <p>
     *  构造没有详细消息的CertificateNotYetValidException。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CertificateNotYetValidException() {
        super();
    }

    /**
     * Constructs a CertificateNotYetValidException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有指定详细消息的CertificateNotYetValidException。详细消息是描述此特殊异常的字符串。
     * 
     * @param message the detail message.
     */
    public CertificateNotYetValidException(String message) {
        super(message);
    }
}
