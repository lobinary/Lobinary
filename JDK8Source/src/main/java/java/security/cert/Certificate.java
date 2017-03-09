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

import java.util.Arrays;

import java.security.Provider;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

import sun.security.x509.X509CertImpl;

/**
 * <p>Abstract class for managing a variety of identity certificates.
 * An identity certificate is a binding of a principal to a public key which
 * is vouched for by another principal.  (A principal represents
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
 * <p>
 *  <p>用于管理各种身份证书的抽象类。身份证书是主体与由另一主体支持的公钥的绑定。 (主体表示诸如个人用户,群组或公司的实体。)
 * p>
 *  此类是具有不同格式但重要的常见用途的证书的抽象。例如,不同类型的证书(例如X.509和PGP)共享通用证书功能(如编码和验证)和某些类型的信息(如公钥)。
 * <p>
 *  X.509,PGP和SDSI证书都可以通过子类化证书类实现,即使它们包含不同的信息集合,它们以不同的方式存储和检索信息。
 * 
 * 
 * @see X509Certificate
 * @see CertificateFactory
 *
 * @author Hemma Prafullchandra
 */

public abstract class Certificate implements java.io.Serializable {

    private static final long serialVersionUID = -3585440601605666277L;

    // the certificate type
    private final String type;

    /** Cache the hash code for the certiticate */
    private int hash = -1; // Default to -1

    /**
     * Creates a certificate of the specified type.
     *
     * <p>
     *  创建指定类型的证书。
     * 
     * 
     * @param type the standard name of the certificate type.
     * See the CertificateFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard certificate types.
     */
    protected Certificate(String type) {
        this.type = type;
    }

    /**
     * Returns the type of this certificate.
     *
     * <p>
     *  返回此证书的类型。
     * 
     * 
     * @return the type of this certificate.
     */
    public final String getType() {
        return this.type;
    }

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
     * @return true iff the encoded forms of the two certificates
     * match, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Certificate)) {
            return false;
        }
        try {
            byte[] thisCert = X509CertImpl.getEncodedInternal(this);
            byte[] otherCert = X509CertImpl.getEncodedInternal((Certificate)other);

            return Arrays.equals(thisCert, otherCert);
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
        int h = hash;
        if (h == -1) {
            try {
                h = Arrays.hashCode(X509CertImpl.getEncodedInternal(this));
            } catch (CertificateException e) {
                h = 0;
            }
            hash = h;
        }
        return h;
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
     * @return the encoded form of this certificate
     *
     * @exception CertificateEncodingException if an encoding error occurs.
     */
    public abstract byte[] getEncoded()
        throws CertificateEncodingException;

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
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
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
     * Verifies that this certificate was signed using the
     * private key that corresponds to the specified public key.
     * This method uses the signature verification engine
     * supplied by the specified provider. Note that the specified
     * Provider object does not have to be registered in the provider list.
     *
     * <p> This method was added to version 1.8 of the Java Platform
     * Standard Edition. In order to maintain backwards compatibility with
     * existing service providers, this method cannot be {@code abstract}
     * and by default throws an {@code UnsupportedOperationException}.
     *
     * <p>
     *  验证此证书是否使用与指定的公钥相对应的私钥进行签名。此方法使用由指定提供程序提供的签名验证引擎。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     *  <p>此方法已添加到Java Platform Standard Edition的1.8版中。
     * 为了保持与现有服务提供程序的向后兼容性,此方法不能是{@code abstract},默认情况下会抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @param key the PublicKey used to carry out the verification.
     * @param sigProvider the signature provider.
     *
     * @exception NoSuchAlgorithmException on unsupported signature
     * algorithms.
     * @exception InvalidKeyException on incorrect key.
     * @exception SignatureException on signature errors.
     * @exception CertificateException on encoding errors.
     * @exception UnsupportedOperationException if the method is not supported
     * @since 1.8
     */
    public void verify(PublicKey key, Provider sigProvider)
        throws CertificateException, NoSuchAlgorithmException,
        InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException();
    }

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
     * 
     * @return the public key.
     */
    public abstract PublicKey getPublicKey();

    /**
     * Alternate Certificate class for serialization.
     * <p>
     *  用于序列化的备用证书类。
     * 
     * 
     * @since 1.3
     */
    protected static class CertificateRep implements java.io.Serializable {

        private static final long serialVersionUID = -8563758940495660020L;

        private String type;
        private byte[] data;

        /**
         * Construct the alternate Certificate class with the Certificate
         * type and Certificate encoding bytes.
         *
         * <p>
         *
         * <p>
         *  使用证书类型和证书编码字节构造备用证书类。
         * 
         * <p>
         * 
         * 
         * @param type the standard name of the Certificate type. <p>
         *
         * @param data the Certificate data.
         */
        protected CertificateRep(String type, byte[] data) {
            this.type = type;
            this.data = data;
        }

        /**
         * Resolve the Certificate Object.
         *
         * <p>
         *
         * <p>
         *  解析证书对象。
         * 
         * <p>
         * 
         * 
         * @return the resolved Certificate Object
         *
         * @throws java.io.ObjectStreamException if the Certificate
         *      could not be resolved
         */
        protected Object readResolve() throws java.io.ObjectStreamException {
            try {
                CertificateFactory cf = CertificateFactory.getInstance(type);
                return cf.generateCertificate
                        (new java.io.ByteArrayInputStream(data));
            } catch (CertificateException e) {
                throw new java.io.NotSerializableException
                                ("java.security.cert.Certificate: " +
                                type +
                                ": " +
                                e.getMessage());
            }
        }
    }

    /**
     * Replace the Certificate to be serialized.
     *
     * <p>
     * 
     * @return the alternate Certificate object to be serialized
     *
     * @throws java.io.ObjectStreamException if a new object representing
     * this Certificate could not be created
     * @since 1.3
     */
    protected Object writeReplace() throws java.io.ObjectStreamException {
        try {
            return new CertificateRep(type, getEncoded());
        } catch (CertificateException e) {
            throw new java.io.NotSerializableException
                                ("java.security.cert.Certificate: " +
                                type +
                                ": " +
                                e.getMessage());
        }
    }
}
