/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.security.Provider;
import java.security.Security;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * This class defines the functionality of a certificate factory, which is
 * used to generate certificate, certification path ({@code CertPath})
 * and certificate revocation list (CRL) objects from their encodings.
 *
 * <p>For encodings consisting of multiple certificates, use
 * {@code generateCertificates} when you want to
 * parse a collection of possibly unrelated certificates. Otherwise,
 * use {@code generateCertPath} when you want to generate
 * a {@code CertPath} (a certificate chain) and subsequently
 * validate it with a {@code CertPathValidator}.
 *
 * <p>A certificate factory for X.509 must return certificates that are an
 * instance of {@code java.security.cert.X509Certificate}, and CRLs
 * that are an instance of {@code java.security.cert.X509CRL}.
 *
 * <p>The following example reads a file with Base64 encoded certificates,
 * which are each bounded at the beginning by -----BEGIN CERTIFICATE-----, and
 * bounded at the end by -----END CERTIFICATE-----. We convert the
 * {@code FileInputStream} (which does not support {@code mark}
 * and {@code reset}) to a {@code BufferedInputStream} (which
 * supports those methods), so that each call to
 * {@code generateCertificate} consumes only one certificate, and the
 * read position of the input stream is positioned to the next certificate in
 * the file:
 *
 * <pre>{@code
 * FileInputStream fis = new FileInputStream(filename);
 * BufferedInputStream bis = new BufferedInputStream(fis);
 *
 * CertificateFactory cf = CertificateFactory.getInstance("X.509");
 *
 * while (bis.available() > 0) {
 *    Certificate cert = cf.generateCertificate(bis);
 *    System.out.println(cert.toString());
 * }
 * }</pre>
 *
 * <p>The following example parses a PKCS#7-formatted certificate reply stored
 * in a file and extracts all the certificates from it:
 *
 * <pre>
 * FileInputStream fis = new FileInputStream(filename);
 * CertificateFactory cf = CertificateFactory.getInstance("X.509");
 * Collection c = cf.generateCertificates(fis);
 * Iterator i = c.iterator();
 * while (i.hasNext()) {
 *    Certificate cert = (Certificate)i.next();
 *    System.out.println(cert);
 * }
 * </pre>
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code CertificateFactory} type:
 * <ul>
 * <li>{@code X.509}</li>
 * </ul>
 * and the following standard {@code CertPath} encodings:
 * <ul>
 * <li>{@code PKCS7}</li>
 * <li>{@code PkiPath}</li>
 * </ul>
 * The type and encodings are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
 * CertificateFactory section</a> and the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
 * CertPath Encodings section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other types or encodings are supported.
 *
 * <p>
 *  此类定义证书工厂的功能,用于从其编码生成证书,证书路径({@code CertPath})和证书吊销列表(CRL)对象。
 * 
 *  <p>对于由多个证书组成的编码,当您要解析可能不相关的证书的集合时,请使用{@code generateCertificates}。
 * 否则,当您要生成{@code CertPath}(证书链)并随后使用{@code CertPathValidator}验证它时,使用{@code generateCertPath}。
 * 
 *  <p> X.509的证书工厂必须返回作为{@code java.security.cert.X509Certificate}的实例的证书和作为{@code java.security.cert.X509CRL}
 * 的实例的CRL。
 * 
 *  <p>以下示例读取带有Base64编码证书的文件,每个文件的开头均由----- BEGIN CERTIFICATE -----进行了限定,最后由----- END CERTIFICATE  - ---
 * 。
 * 我们将{@code FileInputStream}(不支持{@code mark}和{@code reset})转换为{@code BufferedInputStream}(它支持这些方法),以便每次
 * 调用{@code generateCertificate}只有一个证书,并且输入流的读取位置位于文件中的下一个证书：。
 * 
 * <pre> {@ code FileInputStream fis = new FileInputStream(filename); BufferedInputStream bis = new BufferedInputStream(fis);。
 * 
 *  CertificateFactory cf = CertificateFactory.getInstance("X.509");
 * 
 *  while(bis.available()> 0){证书cert = cf.generateCertificate(bis); System.out.println(cert.toString()); }
 * } </pre>。
 * 
 *  <p>以下示例解析存储在文件中的PKCS#7格式的证书回复,并从中提取所有证书：
 * 
 * <pre>
 *  FileInputStream fis = new FileInputStream(filename); CertificateFactory cf = CertificateFactory.getI
 * nstance("X.509");集合c = cf.generateCertificates(fis);迭代器i = c.iterator(); while(i.hasNext()){Certificate cert =(Certificate)i.next(); System.out.println(cert); }
 * }。
 * </pre>
 * 
 *  <p>每个Java平台的实施都需要支持以下标准{@code CertificateFactory}类型：
 * <ul>
 *  <li> {@ code X.509} </li>
 * </ul>
 *  和以下标准{@code CertPath}编码：
 * <ul>
 *  <li> {@ code PKCS7} </li> <li> {@ code PkiPath} </li>
 * </ul>
 *  类型和编码在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
 *  CertificateFactory部分</a>和<a href =
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
 *  Java密码术体系结构标准算法名称文档的CertPath编码部分</a>。请查看您的实现的发行版文档,看看是否支持任何其他类型或编码。
 * 
 * 
 * @author Hemma Prafullchandra
 * @author Jan Luehe
 * @author Sean Mullan
 *
 * @see Certificate
 * @see X509Certificate
 * @see CertPath
 * @see CRL
 * @see X509CRL
 *
 * @since 1.2
 */

public class CertificateFactory {

    // The certificate type
    private String type;

    // The provider
    private Provider provider;

    // The provider implementation
    private CertificateFactorySpi certFacSpi;

    /**
     * Creates a CertificateFactory object of the given type, and encapsulates
     * the given provider implementation (SPI object) in it.
     *
     * <p>
     *  创建给定类型的CertificateFactory对象,并将给定的提供程序实现(SPI对象)封装在其中。
     * 
     * 
     * @param certFacSpi the provider implementation.
     * @param provider the provider.
     * @param type the certificate type.
     */
    protected CertificateFactory(CertificateFactorySpi certFacSpi,
                                 Provider provider, String type)
    {
        this.certFacSpi = certFacSpi;
        this.provider = provider;
        this.type = type;
    }

    /**
     * Returns a certificate factory object that implements the
     * specified certificate type.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new CertificateFactory object encapsulating the
     * CertificateFactorySpi implementation from the first
     * Provider that supports the specified type is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     * 返回实现指定证书类型的证书工厂对象。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的CertificateFactory对象,该对象封装来自支持指定类型的第一个Provider的CertificateFactorySpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param type the name of the requested certificate type.
     * See the CertificateFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard certificate types.
     *
     * @return a certificate factory object for the specified type.
     *
     * @exception CertificateException if no Provider supports a
     *          CertificateFactorySpi implementation for the
     *          specified type.
     *
     * @see java.security.Provider
     */
    public static final CertificateFactory getInstance(String type)
            throws CertificateException {
        try {
            Instance instance = GetInstance.getInstance("CertificateFactory",
                CertificateFactorySpi.class, type);
            return new CertificateFactory((CertificateFactorySpi)instance.impl,
                instance.provider, type);
        } catch (NoSuchAlgorithmException e) {
            throw new CertificateException(type + " not found", e);
        }
    }

    /**
     * Returns a certificate factory object for the specified
     * certificate type.
     *
     * <p> A new CertificateFactory object encapsulating the
     * CertificateFactorySpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回指定证书类型的证书工厂对象。
     * 
     *  <p>返回一个新的CertificateFactory对象,该对象封装来自指定提供程序的CertificateFactorySpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param type the certificate type.
     * See the CertificateFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard certificate types.
     *
     * @param provider the name of the provider.
     *
     * @return a certificate factory object for the specified type.
     *
     * @exception CertificateException if a CertificateFactorySpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the provider name is null
     *          or empty.
     *
     * @see java.security.Provider
     */
    public static final CertificateFactory getInstance(String type,
            String provider) throws CertificateException,
            NoSuchProviderException {
        try {
            Instance instance = GetInstance.getInstance("CertificateFactory",
                CertificateFactorySpi.class, type, provider);
            return new CertificateFactory((CertificateFactorySpi)instance.impl,
                instance.provider, type);
        } catch (NoSuchAlgorithmException e) {
            throw new CertificateException(type + " not found", e);
        }
    }

    /**
     * Returns a certificate factory object for the specified
     * certificate type.
     *
     * <p> A new CertificateFactory object encapsulating the
     * CertificateFactorySpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回指定证书类型的证书工厂对象。
     * 
     *  <p>返回一个新的CertificateFactory对象,该对象封装来自指定的Provider对象的CertificateFactorySpi实现。
     * 请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param type the certificate type.
     * See the CertificateFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertificateFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard certificate types.
     * @param provider the provider.
     *
     * @return a certificate factory object for the specified type.
     *
     * @exception CertificateException if a CertificateFactorySpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null.
     *
     * @see java.security.Provider
     *
     * @since 1.4
     */
    public static final CertificateFactory getInstance(String type,
            Provider provider) throws CertificateException {
        try {
            Instance instance = GetInstance.getInstance("CertificateFactory",
                CertificateFactorySpi.class, type, provider);
            return new CertificateFactory((CertificateFactorySpi)instance.impl,
                instance.provider, type);
        } catch (NoSuchAlgorithmException e) {
            throw new CertificateException(type + " not found", e);
        }
    }

    /**
     * Returns the provider of this certificate factory.
     *
     * <p>
     *  返回此证书工厂的提供程序。
     * 
     * 
     * @return the provider of this certificate factory.
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Returns the name of the certificate type associated with this
     * certificate factory.
     *
     * <p>
     *  返回与此证书工厂关联的证书类型的名称。
     * 
     * 
     * @return the name of the certificate type associated with this
     * certificate factory.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Generates a certificate object and initializes it with
     * the data read from the input stream {@code inStream}.
     *
     * <p>In order to take advantage of the specialized certificate format
     * supported by this certificate factory,
     * the returned certificate object can be typecast to the corresponding
     * certificate class. For example, if this certificate
     * factory implements X.509 certificates, the returned certificate object
     * can be typecast to the {@code X509Certificate} class.
     *
     * <p>In the case of a certificate factory for X.509 certificates, the
     * certificate provided in {@code inStream} must be DER-encoded and
     * may be supplied in binary or printable (Base64) encoding. If the
     * certificate is provided in Base64 encoding, it must be bounded at
     * the beginning by -----BEGIN CERTIFICATE-----, and must be bounded at
     * the end by -----END CERTIFICATE-----.
     *
     * <p>Note that if the given input stream does not support
     * {@link java.io.InputStream#mark(int) mark} and
     * {@link java.io.InputStream#reset() reset}, this method will
     * consume the entire input stream. Otherwise, each call to this
     * method consumes one certificate and the read position of the
     * input stream is positioned to the next available byte after
     * the inherent end-of-certificate marker. If the data in the input stream
     * does not contain an inherent end-of-certificate marker (other
     * than EOF) and there is trailing data after the certificate is parsed, a
     * {@code CertificateException} is thrown.
     *
     * <p>
     *  生成证书对象,并使用从输入流{@code inStream}读取的数据进行初始化。
     * 
     * <p>为了利用此证书工厂支持的专门的证书格式,返回的证书对象可以类型转换到相应的证书类。
     * 例如,如果此证书工厂实现X.509证书,则返回的证书对象可以类型转换为{@code X509Certificate}类。
     * 
     *  <p>对于X.509证书的证书工厂,{@code inStream}中提供的证书必须是DER编码的,并且可以以二进制或可打印(Base64)编码提供。
     * 如果证书以Base64编码提供,则必须在开始时由----- BEGIN CERTIFICATE -----进行限制,并且必须在末尾通过----- END CERTIFICATE ----- 。
     * 
     *  <p>请注意,如果给定的输入流不支持{@link java.io.InputStream#mark(int)mark}和{@link java.io.InputStream#reset()reset}
     * ,此方法将使用整个输入流。
     * 否则,每次调用此方法都会消耗一个证书,并且输入流的读取位置将位于固有的证书结束标记之后的下一个可用字节。
     * 如果输入流中的数据不包含固有的证书结束标记(除了EOF),并且在解析证书后存在结尾数据,则会抛出{@code CertificateException}。
     * 
     * 
     * @param inStream an input stream with the certificate data.
     *
     * @return a certificate object initialized with the data
     * from the input stream.
     *
     * @exception CertificateException on parsing errors.
     */
    public final Certificate generateCertificate(InputStream inStream)
        throws CertificateException
    {
        return certFacSpi.engineGenerateCertificate(inStream);
    }

    /**
     * Returns an iteration of the {@code CertPath} encodings supported
     * by this certificate factory, with the default encoding first. See
     * the CertPath Encodings section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard encoding names and their formats.
     * <p>
     * Attempts to modify the returned {@code Iterator} via its
     * {@code remove} method result in an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  返回此证书工厂支持的{@code CertPath}编码的迭代,首先使用默认编码。请参阅<a href =中的CertPath编码部分
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
     * Java加密架构标准算法名称文档</a>,以获取有关标准编码名称及其格式的信息。
     * <p>
     *  尝试通过其{@code remove}方法修改返回的{@code Iterator}会导致{@code UnsupportedOperationException}。
     * 
     * 
     * @return an {@code Iterator} over the names of the supported
     *         {@code CertPath} encodings (as {@code String}s)
     * @since 1.4
     */
    public final Iterator<String> getCertPathEncodings() {
        return(certFacSpi.engineGetCertPathEncodings());
    }

    /**
     * Generates a {@code CertPath} object and initializes it with
     * the data read from the {@code InputStream} inStream. The data
     * is assumed to be in the default encoding. The name of the default
     * encoding is the first element of the {@code Iterator} returned by
     * the {@link #getCertPathEncodings getCertPathEncodings} method.
     *
     * <p>
     *  生成{@code CertPath}对象,并使用从{@code InputStream} inStream读取的数据进行初始化。数据假定为采用默认编码。
     * 默认编码的名称是{@link #getCertPathEncodings getCertPathEncodings}方法返回的{@code Iterator}的第一个元素。
     * 
     * 
     * @param inStream an {@code InputStream} containing the data
     * @return a {@code CertPath} initialized with the data from the
     *   {@code InputStream}
     * @exception CertificateException if an exception occurs while decoding
     * @since 1.4
     */
    public final CertPath generateCertPath(InputStream inStream)
        throws CertificateException
    {
        return(certFacSpi.engineGenerateCertPath(inStream));
    }

    /**
     * Generates a {@code CertPath} object and initializes it with
     * the data read from the {@code InputStream} inStream. The data
     * is assumed to be in the specified encoding. See
     * the CertPath Encodings section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard encoding names and their formats.
     *
     * <p>
     *  生成{@code CertPath}对象,并使用从{@code InputStream} inStream读取的数据进行初始化。数据假定为指定的编码。
     * 请参阅<a href =中的CertPath编码部分。
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathEncodings">
     *  Java加密架构标准算法名称文档</a>,以获取有关标准编码名称及其格式的信息。
     * 
     * 
     * @param inStream an {@code InputStream} containing the data
     * @param encoding the encoding used for the data
     * @return a {@code CertPath} initialized with the data from the
     *   {@code InputStream}
     * @exception CertificateException if an exception occurs while decoding or
     *   the encoding requested is not supported
     * @since 1.4
     */
    public final CertPath generateCertPath(InputStream inStream,
        String encoding) throws CertificateException
    {
        return(certFacSpi.engineGenerateCertPath(inStream, encoding));
    }

    /**
     * Generates a {@code CertPath} object and initializes it with
     * a {@code List} of {@code Certificate}s.
     * <p>
     * The certificates supplied must be of a type supported by the
     * {@code CertificateFactory}. They will be copied out of the supplied
     * {@code List} object.
     *
     * <p>
     *  生成{@code CertPath}对象,并使用{@code Certificate}的{@code List}对其进行初始化。
     * <p>
     *  所提供的证书必须是{@code CertificateFactory}支持的类型。它们将从提供的{@code List}对象中复制。
     * 
     * 
     * @param certificates a {@code List} of {@code Certificate}s
     * @return a {@code CertPath} initialized with the supplied list of
     *   certificates
     * @exception CertificateException if an exception occurs
     * @since 1.4
     */
    public final CertPath
        generateCertPath(List<? extends Certificate> certificates)
        throws CertificateException
    {
        return(certFacSpi.engineGenerateCertPath(certificates));
    }

    /**
     * Returns a (possibly empty) collection view of the certificates read
     * from the given input stream {@code inStream}.
     *
     * <p>In order to take advantage of the specialized certificate format
     * supported by this certificate factory, each element in
     * the returned collection view can be typecast to the corresponding
     * certificate class. For example, if this certificate
     * factory implements X.509 certificates, the elements in the returned
     * collection can be typecast to the {@code X509Certificate} class.
     *
     * <p>In the case of a certificate factory for X.509 certificates,
     * {@code inStream} may contain a sequence of DER-encoded certificates
     * in the formats described for
     * {@link #generateCertificate(java.io.InputStream) generateCertificate}.
     * In addition, {@code inStream} may contain a PKCS#7 certificate
     * chain. This is a PKCS#7 <i>SignedData</i> object, with the only
     * significant field being <i>certificates</i>. In particular, the
     * signature and the contents are ignored. This format allows multiple
     * certificates to be downloaded at once. If no certificates are present,
     * an empty collection is returned.
     *
     * <p>Note that if the given input stream does not support
     * {@link java.io.InputStream#mark(int) mark} and
     * {@link java.io.InputStream#reset() reset}, this method will
     * consume the entire input stream.
     *
     * <p>
     *  返回从给定输入流{@code inStream}读取的证书的(可能为空)集合视图。
     * 
     * <p>为了利用此CertificateFactory所支持的专门的证书格式,在返回的集合视图中的每个元素都可以强制转换为相应的证书类。
     * 例如,如果此CertificateFactory实现X.509证书,则可将返回集合中的元素类型强制转换为{@code x509证书}类。
     * 
     *  <p>在一个证书工厂X.509证书的情况下,@code {} inStream中可能包含DER编码证书的在{@link #generateCertificate(java.io.InputStream中)generateCertificate所描述}
     * 格式的序列。
     * 此外,{@code inStream}可能包含PKCS#7证书链。这是一个PKCS#7 <i> SignedData </i>对象,唯一有效的字段是<i>证书</i>。特别地,签名和内容被忽略。
     * 此格式允许立即下载多个证书。如果不存在证书,则返回空集合。
     * 
     *  <p>请注意,如果给定的输入流不支持{@link java.io.InputStream#mark(int)mark}和{@link java.io.InputStream#reset()reset}
     * ,此方法将使用整个输入流。
     * 
     * 
     * @param inStream the input stream with the certificates.
     *
     * @return a (possibly empty) collection view of
     * java.security.cert.Certificate objects
     * initialized with the data from the input stream.
     *
     * @exception CertificateException on parsing errors.
     */
    public final Collection<? extends Certificate> generateCertificates
            (InputStream inStream) throws CertificateException {
        return certFacSpi.engineGenerateCertificates(inStream);
    }

    /**
     * Generates a certificate revocation list (CRL) object and initializes it
     * with the data read from the input stream {@code inStream}.
     *
     * <p>In order to take advantage of the specialized CRL format
     * supported by this certificate factory,
     * the returned CRL object can be typecast to the corresponding
     * CRL class. For example, if this certificate
     * factory implements X.509 CRLs, the returned CRL object
     * can be typecast to the {@code X509CRL} class.
     *
     * <p>Note that if the given input stream does not support
     * {@link java.io.InputStream#mark(int) mark} and
     * {@link java.io.InputStream#reset() reset}, this method will
     * consume the entire input stream. Otherwise, each call to this
     * method consumes one CRL and the read position of the input stream
     * is positioned to the next available byte after the inherent
     * end-of-CRL marker. If the data in the
     * input stream does not contain an inherent end-of-CRL marker (other
     * than EOF) and there is trailing data after the CRL is parsed, a
     * {@code CRLException} is thrown.
     *
     * <p>
     *  生成证书撤销列表(CRL)对象,并使用从输入流{@code inStream}读取的数据对其进行初始化。
     * 
     * <p>为了利用此证书工厂支持的特殊CRL格式,返回的CRL对象可以类型转换到相应的CRL类。
     * 例如,如果此证书工厂实现X.509 CRL,则返回的CRL对象可以类型转换为{@code X509CRL}类。
     * 
     *  <p>请注意,如果给定的输入流不支持{@link java.io.InputStream#mark(int)mark}和{@link java.io.InputStream#reset()reset}
     * ,此方法将使用整个输入流。
     * 否则,每次调用此方法将消耗一个CRL,并且输入流的读取位置将位于CRL标记结束后的下一个可用字节。
     * 如果输入流中的数据不包含固有的CRL结束标记(除了EOF),并且在解析CRL之后存在结尾数据,则会抛出{@code CRLException}。
     * 
     * 
     * @param inStream an input stream with the CRL data.
     *
     * @return a CRL object initialized with the data
     * from the input stream.
     *
     * @exception CRLException on parsing errors.
     */
    public final CRL generateCRL(InputStream inStream)
        throws CRLException
    {
        return certFacSpi.engineGenerateCRL(inStream);
    }

    /**
     * Returns a (possibly empty) collection view of the CRLs read
     * from the given input stream {@code inStream}.
     *
     * <p>In order to take advantage of the specialized CRL format
     * supported by this certificate factory, each element in
     * the returned collection view can be typecast to the corresponding
     * CRL class. For example, if this certificate
     * factory implements X.509 CRLs, the elements in the returned
     * collection can be typecast to the {@code X509CRL} class.
     *
     * <p>In the case of a certificate factory for X.509 CRLs,
     * {@code inStream} may contain a sequence of DER-encoded CRLs.
     * In addition, {@code inStream} may contain a PKCS#7 CRL
     * set. This is a PKCS#7 <i>SignedData</i> object, with the only
     * significant field being <i>crls</i>. In particular, the
     * signature and the contents are ignored. This format allows multiple
     * CRLs to be downloaded at once. If no CRLs are present,
     * an empty collection is returned.
     *
     * <p>Note that if the given input stream does not support
     * {@link java.io.InputStream#mark(int) mark} and
     * {@link java.io.InputStream#reset() reset}, this method will
     * consume the entire input stream.
     *
     * <p>
     *  返回从给定输入流{@code inStream}读取的CRL的(可能为空)集合视图。
     * 
     *  <p>为了利用此证书工厂支持的专用CRL格式,返回的集合视图中的每个元素都可以类型转换为相应的CRL类。
     * 例如,如果此证书工厂实现X.509 CRL,则返回的集合中的元素可以类型转换为{@code X509CRL}类。
     * 
     * <p>在X.509 CRL的证书工厂的情况下,{@code inStream}可能包含一系列DER编码的CRL。此外,{@code inStream}可能包含PKCS#7 CRL集。
     * 
     * @param inStream the input stream with the CRLs.
     *
     * @return a (possibly empty) collection view of
     * java.security.cert.CRL objects initialized with the data from the input
     * stream.
     *
     * @exception CRLException on parsing errors.
     */
    public final Collection<? extends CRL> generateCRLs(InputStream inStream)
            throws CRLException {
        return certFacSpi.engineGenerateCRLs(inStream);
    }
}
