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

/**
 * The Key interface is the top-level interface for all keys. It
 * defines the functionality shared by all key objects. All keys
 * have three characteristics:
 *
 * <UL>
 *
 * <LI>An Algorithm
 *
 * <P>This is the key algorithm for that key. The key algorithm is usually
 * an encryption or asymmetric operation algorithm (such as DSA or
 * RSA), which will work with those algorithms and with related
 * algorithms (such as MD5 with RSA, SHA-1 with RSA, Raw DSA, etc.)
 * The name of the algorithm of a key is obtained using the
 * {@link #getAlgorithm() getAlgorithm} method.
 *
 * <LI>An Encoded Form
 *
 * <P>This is an external encoded form for the key used when a standard
 * representation of the key is needed outside the Java Virtual Machine,
 * as when transmitting the key to some other party. The key
 * is encoded according to a standard format (such as
 * X.509 {@code SubjectPublicKeyInfo} or PKCS#8), and
 * is returned using the {@link #getEncoded() getEncoded} method.
 * Note: The syntax of the ASN.1 type {@code SubjectPublicKeyInfo}
 * is defined as follows:
 *
 * <pre>
 * SubjectPublicKeyInfo ::= SEQUENCE {
 *   algorithm AlgorithmIdentifier,
 *   subjectPublicKey BIT STRING }
 *
 * AlgorithmIdentifier ::= SEQUENCE {
 *   algorithm OBJECT IDENTIFIER,
 *   parameters ANY DEFINED BY algorithm OPTIONAL }
 * </pre>
 *
 * For more information, see
 * <a href="http://www.ietf.org/rfc/rfc3280.txt">RFC 3280:
 * Internet X.509 Public Key Infrastructure Certificate and CRL Profile</a>.
 *
 * <LI>A Format
 *
 * <P>This is the name of the format of the encoded key. It is returned
 * by the {@link #getFormat() getFormat} method.
 *
 * </UL>
 *
 * Keys are generally obtained through key generators, certificates,
 * or various Identity classes used to manage keys.
 * Keys may also be obtained from key specifications (transparent
 * representations of the underlying key material) through the use of a key
 * factory (see {@link KeyFactory}).
 *
 * <p> A Key should use KeyRep as its serialized representation.
 * Note that a serialized Key may contain sensitive information
 * which should not be exposed in untrusted environments.  See the
 * <a href="../../../platform/serialization/spec/security.html">
 * Security Appendix</a>
 * of the Serialization Specification for more information.
 *
 * <p>
 *  Key接口是所有键的顶级接口。它定义所有键对象共享的功能。所有键有三个特点：
 * 
 * <UL>
 * 
 *  <LI>算法
 * 
 *  <P>这是该键的关键算法。密钥算法通常是加密或非对称运算算法(例如DSA或RSA),它们将与这些算法和相关算法(例如具有RSA的MD5,具有RSA的SHA-1,原始DSA等)一起工作。
 * 名称的密钥算法是使用{@link #getAlgorithm()getAlgorithm}方法获得的。
 * 
 *  <LI>编码表单
 * 
 *  <P>这是当在Java虚拟机外部需要密钥的标准表示时使用的密钥的外部编码形式,如当将密钥发送给某个其他方时。
 * 密钥根据标准格式(例如X.509 {@code SubjectPublicKeyInfo}或PKCS#8)进行编码,并使用{@link #getEncoded()getEncoded}方法返回。
 * 注意：ASN.1类型{@code SubjectPublicKeyInfo}的语法定义如下：。
 * 
 * <pre>
 *  SubjectPublicKeyInfo :: = SEQUENCE {algorithm AlgorithmIdentifier,subjectPublicKey BIT STRING}
 * 
 *  AlgorithmIdentifier :: = SEQUENCE {algorithm OBJECT IDENTIFIER,parameters ANY DEFINED BY algorithm OPTIONAL}
 * 。
 * </pre>
 * 
 * 有关详细信息,请参见<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构证书和CRL配置文件</a>。
 * 
 *  <LI> A格式
 * 
 * 
 * @see PublicKey
 * @see PrivateKey
 * @see KeyPair
 * @see KeyPairGenerator
 * @see KeyFactory
 * @see KeyRep
 * @see java.security.spec.KeySpec
 * @see Identity
 * @see Signer
 *
 * @author Benjamin Renaud
 */

public interface Key extends java.io.Serializable {

    // Declare serialVersionUID to be compatible with JDK1.1

   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility with a previous
    * version of the class.
    * <p>
    *  <P>这是已编码密钥的格式名称。它由{@link #getFormat()getFormat}方法返回。
    * 
    * </UL>
    * 
    *  密钥通常通过密钥生成器,证书或用于管理密钥的各种身份类获得。还可以通过使用密钥工厂(参见{@link KeyFactory})从密钥规范(底层密钥材料的透明表示)获得密钥。
    * 
    *  <p> Key应该使用KeyRep作为它的序列化表示。请注意,序列化密钥可能包含不应在不受信任的环境中公开的敏感信息。看到
    * <a href="../../../platform/serialization/spec/security.html">
    *  有关详细信息,请参阅序列化规范的安全附录</a>。
    * 
    */
    static final long serialVersionUID = 6603384152749567654L;

    /**
     * Returns the standard algorithm name for this key. For
     * example, "DSA" would indicate that this key is a DSA key.
     * See Appendix A in the <a href=
     * "../../../technotes/guides/security/crypto/CryptoSpec.html#AppA">
     * Java Cryptography Architecture API Specification &amp; Reference </a>
     * for information about standard algorithm names.
     *
     * <p>
     *  类指纹,设置为指示序列化与该类的先前版本的兼容性。
     * 
     * 
     * @return the name of the algorithm associated with this key.
     */
    public String getAlgorithm();

    /**
     * Returns the name of the primary encoding format of this key,
     * or null if this key does not support encoding.
     * The primary encoding format is
     * named in terms of the appropriate ASN.1 data format, if an
     * ASN.1 specification for this key exists.
     * For example, the name of the ASN.1 data format for public
     * keys is <I>SubjectPublicKeyInfo</I>, as
     * defined by the X.509 standard; in this case, the returned format is
     * {@code "X.509"}. Similarly,
     * the name of the ASN.1 data format for private keys is
     * <I>PrivateKeyInfo</I>,
     * as defined by the PKCS #8 standard; in this case, the returned format is
     * {@code "PKCS#8"}.
     *
     * <p>
     *  返回此键的标准算法名称。例如,"DSA"将指示该密钥是DSA密钥。请参阅<a href =中的附录A.
     * "../../../technotes/guides/security/crypto/CryptoSpec.html#AppA">
     *  Java加密架构API规范&amp;有关标准算法名称的信息,请参阅</a>。
     * 
     * 
     * @return the primary encoding format of the key.
     */
    public String getFormat();

    /**
     * Returns the key in its primary encoding format, or null
     * if this key does not support encoding.
     *
     * <p>
     * 返回此键的主要编码格式的名称,如果此键不支持编码,则返回null。主编码格式根据适当的ASN.1数据格式命名,如果此键的ASN.1规范存在。
     * 例如,用于公钥的ASN.1数据格式的名称是由X.509标准定义的<I> SubjectPublicKeyInfo </I>;在这种情况下,返回的格式是{@code"X.509"}。
     * 类似地,用于私钥的ASN.1数据格式的名称是由PKCS#8标准定义的<I> PrivateKeyInfo </I>;在这种情况下,返回的格式是{@code"PKCS#8"}。
     * 
     * 
     * @return the encoded key, or null if the key does not support
     * encoding.
     */
    public byte[] getEncoded();
}
