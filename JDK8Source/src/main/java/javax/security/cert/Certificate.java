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

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

/**
 * <p>Abstract class for managing a variety of identity certificates.
 * An identity certificate is a guarantee by a principal that
 * a public key is that of another principal.  (A principal represents
 * an entity such as an individual user, a group, or a corporation.)
 *<p>
 * This class is an abstraction for certificates that have different
 * formats but important common uses.  For example, different types of
 * certificates, such as X.509 and PGP, share general certificate
 * functionality (like encoding and verifying) and
 * some types of information (like a public key).
 * <p>
 * X.509, PGP, and SDSI certificates can all be implemented by
 * subclassing the Certificate class, even though they contain different
 * sets of information, and they store and retrieve the information in
 * different ways.
 *
 * <p><em>Note: The classes in the package {@code javax.security.cert}
 * exist for compatibility with earlier versions of the
 * Java Secure Sockets Extension (JSSE). New applications should instead
 * use the standard Java SE certificate classes located in
 * {@code java.security.cert}.</em></p>
 *
 * <p>
 *  <p>用于管理各种身份证书的抽象类。身份证书是由主体保证公钥是另一个主体的保证。 (主体表示诸如个人用户,群组或公司的实体。)
 * p>
 *  此类是具有不同格式但重要的常见用途的证书的抽象。例如,不同类型的证书(例如X.509和PGP)共享通用证书功能(如编码和验证)和某些类型的信息(如公钥)。
 * <p>
 *  X.509,PGP和SDSI证书都可以通过子类化证书类实现,即使它们包含不同的信息集合,它们以不同的方式存储和检索信息。
 * 
 *  <p> <em>注意：包{@code javax.security.cert}中的类是为了与早期版本的Java安全套接字扩展(JSSE)兼容而存在。
 * 新应用程序应使用位于{@code java.security.cert}中的标准Java SE证书类。</em> </p>。
 * 
 * 
 * @since 1.4
 * @see X509Certificate
 *
 * @author Hemma Prafullchandra
 */
public abstract class Certificate {

    /**
     * Compares this certificate for equality with the specified
     * object. If the {@code other} object is an
     * {@code instanceof} {@code Certificate}, then
     * its encoded form is retrieved and compared with the
     * encoded form of this certificate.
     *
     * <p>
     *  将此证书与指定对象的等同性进行比较。
     * 如果{@code other}对象是{@code instanceof} {@code Certificate},则会检索其编码形式,并与此证书的编码形式进行比较。
     * 
     * 
     * @param other the object to test for equality with this certificate.
     * @return true if the encoded forms of the two certificates
     *         match, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Certificate))
            return false;
        try {
            byte[] thisCert = this.getEncoded();
            byte[] otherCert = ((Certificate)other).getEncoded();

            if (thisCert.length != otherCert.length)
                return false;
            for (int i = 0; i < thisCert.length; i++)
                 if (thisCert[i] != otherCert[i])
                     return false;
            return true;
        } catch (CertificateException e) {
            return false;
        }
    }

    /**
     * Returns a hashcode value for this certificate from its
     * encoded form.
     *
     * <p>
     *  从其编码形式返回此证书的哈希码值。
     * 
     * 
     * @return the hashcode value.
     */
    public int hashCode() {
        int     retval = 0;
        try {
            byte[] certData = this.getEncoded();
            for (int i = 1; i < certData.length; i++) {
                 retval += certData[i] * i;
            }
            return (retval);
        } catch (CertificateException e) {
            return (retval);
        }
    }

    /**
     * Returns the encoded form of this certificate. It is
     * assumed that each certificate type would have only a single
     * form of encoding; for example, X.509 certificates would
     * be encoded as ASN.1 DER.
     *
     * <p>
     * 返回此证书的编码形式。假设每个证书类型只有一种形式的编码;例如,X.509证书将被编码为ASN.1 DER。
     * 
     * 
     * @return encoded form of this certificate
     * @exception CertificateEncodingException on internal certificate
     *            encoding failure
     */
    public abstract byte[] getEncoded() throws CertificateEncodingException;

    /**
     * Verifies that this certificate was signed using the
     * private key that corresponds to the specified public key.
     *
     * <p>
     *  验证此证书是否使用与指定的公钥相对应的私钥进行签名。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException if there's no default provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public abstract void verify(PublicKey key)
        throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException,
        SignatureException;

    /**
     * Verifies that this certificate was signed using the
     * private key that corresponds to the specified public key.
     * This method uses the signature verification engine
     * supplied by the specified provider.
     *
     * <p>
     *  验证此证书是否使用与指定的公钥相对应的私钥进行签名。此方法使用由指定提供程序提供的签名验证引擎。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the name of the signature provider.
     * @exception NoSuchAlgorithmException on unsupported signature algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception NoSuchProviderException on incorrect provider.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     */
    public abstract void verify(PublicKey key, String sigProvider)
        throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, NoSuchProviderException,
        SignatureException;

    /**
     * Returns a string representation of this certificate.
     *
     * <p>
     *  返回此证书的字符串表示形式。
     * 
     * 
     * @return a string representation of this certificate.
     */
    public abstract String toString();

    /**
     * Gets the public key from this certificate.
     *
     * <p>
     *  从此证书获取公钥。
     * 
     * @return the public key.
     */
    public abstract PublicKey getPublicKey();
}
