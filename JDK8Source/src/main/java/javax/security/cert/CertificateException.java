/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * This exception indicates one of a variety of certificate problems.
 *
 * <p><em>Note: The classes in the package {@code javax.security.cert}
 * exist for compatibility with earlier versions of the
 * Java Secure Sockets Extension (JSSE). New applications should instead
 * use the standard Java SE certificate classes located in
 * {@code java.security.cert}.</em></p>
 *
 * <p>
 *  此异常表示各种证书问题之一。
 * 
 *  <p> <em>注意：包{@code javax.security.cert}中的类是为了与早期版本的Java安全套接字扩展(JSSE)兼容而存在。
 * 新应用程序应使用位于{@code java.security.cert}中的标准Java SE证书类。</em> </p>。
 * 
 * 
 * @author Hemma Prafullchandra
 * @since 1.4
 * @see Certificate
 */
public class CertificateException extends Exception {

    private static final long serialVersionUID = -5757213374030785290L;
    /**
     * Constructs a certificate exception with no detail message. A detail
     * message is a String that describes this particular exception.
     * <p>
     *  构造一个没有详细消息的证书异常。详细消息是描述此特殊异常的字符串。
     * 
     */
    public CertificateException() {
        super();
    }

    /**
     * Constructs a certificate exception with the given detail
     * message. A detail message is a String that describes this
     * particular exception.
     *
     * <p>
     *  构造具有给定详细消息的证书异常。详细消息是描述此特殊异常的字符串。
     * 
     * @param msg the detail message.
     */
    public CertificateException(String msg) {
        super(msg);
    }
}
