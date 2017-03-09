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


package javax.security.cert;

/**
 * Certificate is not yet valid exception. This is thrown whenever
 * the current {@code Date} or the specified {@code Date}
 * is before the {@code notBefore} date/time in the Certificate
 * validity period.
 *
 * <p><em>Note: The classes in the package {@code javax.security.cert}
 * exist for compatibility with earlier versions of the
 * Java Secure Sockets Extension (JSSE). New applications should instead
 * use the standard Java SE certificate classes located in
 * {@code java.security.cert}.</em></p>
 *
 * <p>
 *  证书尚未生效。每当当前{@code Date}或指定的{@code Date}在证书有效期内的{@code notBefore}日期/时间之前时,都会抛出此错误。
 * 
 *  <p> <em>注意：包{@code javax.security.cert}中的类是为了与早期版本的Java安全套接字扩展(JSSE)兼容而存在。
 * 新应用程序应使用位于{@code java.security.cert}中的标准Java SE证书类。</em> </p>。
 * 
 * 
 * @since 1.4
 * @author Hemma Prafullchandra
 */
public class CertificateNotYetValidException extends CertificateException {

    private static final long serialVersionUID = -8976172474266822818L;
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
