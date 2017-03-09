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

package java.security;

import java.io.*;
import java.util.Date;

/**
 * <p>This is an interface of abstract methods for managing a
 * variety of identity certificates.
 * An identity certificate is a guarantee by a principal that
 * a public key is that of another principal.  (A principal represents
 * an entity such as an individual user, a group, or a corporation.)
 *
 * <p>In particular, this interface is intended to be a common
 * abstraction for constructs that have different formats but
 * important common uses.  For example, different types of
 * certificates, such as X.509 certificates and PGP certificates,
 * share general certificate functionality (the need to encode and
 * decode certificates) and some types of information, such as a
 * public key, the principal whose key it is, and the guarantor
 * guaranteeing that the public key is that of the specified
 * principal. So an implementation of X.509 certificates and an
 * implementation of PGP certificates can both utilize the Certificate
 * interface, even though their formats and additional types and
 * amounts of information stored are different.
 *
 * <p><b>Important</b>: This interface is useful for cataloging and
 * grouping objects sharing certain common uses. It does not have any
 * semantics of its own. In particular, a Certificate object does not
 * make any statement as to the <i>validity</i> of the binding. It is
 * the duty of the application implementing this interface to verify
 * the certificate and satisfy itself of its validity.
 *
 * <p>
 *  <p>这是一个用于管理各种身份证书的抽象方法的接口。身份证书是由主体保证公钥是另一个主体的保证。 (主体表示诸如个人用户,群组或公司的实体。)
 * 
 *  <p>特别是,这个接口是为具有不同格式但重要的常见用途的结构的一个通用抽象。
 * 例如,不同类型的证书(例如X.509证书和PGP证书)共享通用证书功能(需要编码和解码证书)和某些类型的信息,例如公钥,其密钥的主体,以及担保人保证公钥是指定主体的公钥。
 * 因此,X.509证书的实现和PGP证书的实现都可以利用证书接口,即使它们的格式和存储的附加类型和数量的信息不同。
 * 
 * <p> <b>重要</b>：此界面对于共享某些常见用途的对象进行编目和分组非常有用。它没有自己的任何语义。特别地,Certificate对象不对绑定的<i>有效性</i>做出任何声明。
 * 实现此接口的应用程序的职责是验证证书并满足其有效性。
 * 
 * 
 * @author Benjamin Renaud
 * @deprecated A new certificate handling package is created in the Java platform.
 *             This Certificate interface is entirely deprecated and
 *             is here to allow for a smooth transition to the new
 *             package.
 * @see java.security.cert.Certificate
 */
@Deprecated
public interface Certificate {

    /**
     * Returns the guarantor of the certificate, that is, the principal
     * guaranteeing that the public key associated with this certificate
     * is that of the principal associated with this certificate. For X.509
     * certificates, the guarantor will typically be a Certificate Authority
     * (such as the United States Postal Service or Verisign, Inc.).
     *
     * <p>
     *  返回证书的保证人,即保证与此证书相关联的公钥是与此证书相关联的主体的公钥的主体。对于X.509证书,保证人通常是证书机构(例如美国邮政局或Verisign公司)。
     * 
     * 
     * @return the guarantor which guaranteed the principal-key
     * binding.
     */
    public abstract Principal getGuarantor();

    /**
     * Returns the principal of the principal-key pair being guaranteed by
     * the guarantor.
     *
     * <p>
     *  返回保证人担保的主体 - 密钥对的主体。
     * 
     * 
     * @return the principal to which this certificate is bound.
     */
    public abstract Principal getPrincipal();

    /**
     * Returns the key of the principal-key pair being guaranteed by
     * the guarantor.
     *
     * <p>
     *  返回由保证者保证的主键密钥对的密钥。
     * 
     * 
     * @return the public key that this certificate certifies belongs
     * to a particular principal.
     */
    public abstract PublicKey getPublicKey();

    /**
     * Encodes the certificate to an output stream in a format that can
     * be decoded by the {@code decode} method.
     *
     * <p>
     *  以可以通过{@code decode}方法解码的格式将证书编码为输出流。
     * 
     * 
     * @param stream the output stream to which to encode the
     * certificate.
     *
     * @exception KeyException if the certificate is not
     * properly initialized, or data is missing, etc.
     *
     * @exception IOException if a stream exception occurs while
     * trying to output the encoded certificate to the output stream.
     *
     * @see #decode
     * @see #getFormat
     */
    public abstract void encode(OutputStream stream)
        throws KeyException, IOException;

    /**
     * Decodes a certificate from an input stream. The format should be
     * that returned by {@code getFormat} and produced by
     * {@code encode}.
     *
     * <p>
     *  解码来自输入流的证书。格式应该是{@code getFormat}返回的并且由{@code encode}生成的格式。
     * 
     * 
     * @param stream the input stream from which to fetch the data
     * being decoded.
     *
     * @exception KeyException if the certificate is not properly initialized,
     * or data is missing, etc.
     *
     * @exception IOException if an exception occurs while trying to input
     * the encoded certificate from the input stream.
     *
     * @see #encode
     * @see #getFormat
     */
    public abstract void decode(InputStream stream)
        throws KeyException, IOException;


    /**
     * Returns the name of the coding format. This is used as a hint to find
     * an appropriate parser. It could be "X.509", "PGP", etc. This is
     * the format produced and understood by the {@code encode}
     * and {@code decode} methods.
     *
     * <p>
     *  返回编码格式的名称。这用作提示找到一个合适的解析器。它可以是"X.509","PGP"等。这是由{@code encode}和{@code decode}方法生成和理解的格式。
     * 
     * 
     * @return the name of the coding format.
     */
    public abstract String getFormat();

    /**
     * Returns a string that represents the contents of the certificate.
     *
     * <p>
     *  返回表示证书内容的字符串。
     * 
     * @param detailed whether or not to give detailed information
     * about the certificate
     *
     * @return a string representing the contents of the certificate
     */
    public String toString(boolean detailed);
}
