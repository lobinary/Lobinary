/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import sun.security.util.Debug;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * A class for building certification paths (also known as certificate chains).
 * <p>
 * This class uses a provider-based architecture.
 * To create a {@code CertPathBuilder}, call
 * one of the static {@code getInstance} methods, passing in the
 * algorithm name of the {@code CertPathBuilder} desired and optionally
 * the name of the provider desired.
 *
 * <p>Once a {@code CertPathBuilder} object has been created, certification
 * paths can be constructed by calling the {@link #build build} method and
 * passing it an algorithm-specific set of parameters. If successful, the
 * result (including the {@code CertPath} that was built) is returned
 * in an object that implements the {@code CertPathBuilderResult}
 * interface.
 *
 * <p>The {@link #getRevocationChecker} method allows an application to specify
 * additional algorithm-specific parameters and options used by the
 * {@code CertPathBuilder} when checking the revocation status of certificates.
 * Here is an example demonstrating how it is used with the PKIX algorithm:
 *
 * <pre>
 * CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX");
 * PKIXRevocationChecker rc = (PKIXRevocationChecker)cpb.getRevocationChecker();
 * rc.setOptions(EnumSet.of(Option.PREFER_CRLS));
 * params.addCertPathChecker(rc);
 * CertPathBuilderResult cpbr = cpb.build(params);
 * </pre>
 *
 * <p>Every implementation of the Java platform is required to support the
 * following standard {@code CertPathBuilder} algorithm:
 * <ul>
 * <li>{@code PKIX}</li>
 * </ul>
 * This algorithm is described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathBuilder">
 * CertPathBuilder section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * The static methods of this class are guaranteed to be thread-safe.
 * Multiple threads may concurrently invoke the static methods defined in
 * this class with no ill effects.
 * <p>
 * However, this is not true for the non-static methods defined by this class.
 * Unless otherwise documented by a specific provider, threads that need to
 * access a single {@code CertPathBuilder} instance concurrently should
 * synchronize amongst themselves and provide the necessary locking. Multiple
 * threads each manipulating a different {@code CertPathBuilder} instance
 * need not synchronize.
 *
 * <p>
 *  用于构建认证路径(也称为证书链)的类。
 * <p>
 *  此类使用基于提供程序的体系结构。
 * 要创建{@code CertPathBuilder},请调用其中一个静态{@code getInstance}方法,传入所需的{@code CertPathBuilder}的算法名称和可选的所需提供程序
 * 的名称。
 *  此类使用基于提供程序的体系结构。
 * 
 *  <p>创建{@code CertPathBuilder}对象后,可以通过调用{@link #build build}方法并传递一组特定于算法的参数来构建认证路径。
 * 如果成功,结果(包括构建的{@code CertPath})在实现{@code CertPathBuilderResult}接口的对象中返回。
 * 
 *  <p> {@link #getRevocationChecker}方法允许应用程序在检查证书的吊销状态时指定{@code CertPathBuilder}使用的其他算法特定的参数和选项。
 * 这里是一个例子演示如何使用PKIX算法：。
 * 
 * <pre>
 *  CertPathBuilder cpb = CertPathBuilder.getInstance("PKIX"); PKIXRevocationChecker rc =(PKIXRevocation
 * Checker)cpb.getRevocationChecker(); rc.setOptions(EnumSet.of(Option.PREFER_CRLS)); params.addCertPath
 * Checker(rc); CertPathBuilderResult cpbr = cpb.build(params);。
 * </pre>
 * 
 * <p>每个Java平台的实施都需要支持以下标准{@code CertPathBuilder}算法：
 * <ul>
 *  <li> {@ code PKIX} </li>
 * </ul>
 *  此算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathBuilder">
 *  CertPathBuilder部分</a>的Java密码体系结构标准算法名称文档。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  这个类的静态方法被保证是线程安全的。多线程可以同时调用这个类中定义的静态方法,没有不良影响。
 * <p>
 *  然而,这不是真的这个类定义的非静态方法。除非特定提供程序另有说明,需要并发访问单个{@code CertPathBuilder}实例的线程应在它们之间同步并提供必要的锁定。
 * 每个操作不同{@code CertPathBuilder}实例的多个线程不需要同步。
 * 
 * 
 * @see CertPath
 *
 * @since       1.4
 * @author      Sean Mullan
 * @author      Yassir Elley
 */
public class CertPathBuilder {

    /*
     * Constant to lookup in the Security properties file to determine
     * the default certpathbuilder type. In the Security properties file,
     * the default certpathbuilder type is given as:
     * <pre>
     * certpathbuilder.type=PKIX
     * </pre>
     * <p>
     *  在安全属性文件中查找的常量以确定缺省certpathbuilder类型。在安全属性文件中,缺省certpathbuilder类型为：
     * <pre>
     *  certpathbuilder.type = PKIX
     * </pre>
     */
    private static final String CPB_TYPE = "certpathbuilder.type";
    private final CertPathBuilderSpi builderSpi;
    private final Provider provider;
    private final String algorithm;

    /**
     * Creates a {@code CertPathBuilder} object of the given algorithm,
     * and encapsulates the given provider implementation (SPI object) in it.
     *
     * <p>
     *  创建给定算法的{@code CertPathBuilder}对象,并将给定的提供程序实现(SPI对象)封装在其中。
     * 
     * 
     * @param builderSpi the provider implementation
     * @param provider the provider
     * @param algorithm the algorithm name
     */
    protected CertPathBuilder(CertPathBuilderSpi builderSpi, Provider provider,
        String algorithm)
    {
        this.builderSpi = builderSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }

    /**
     * Returns a {@code CertPathBuilder} object that implements the
     * specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new CertPathBuilder object encapsulating the
     * CertPathBuilderSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathBuilder}对象。
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的CertPathBuilder对象,该对象封装来自支持指定算法的第一个Provider的CertPathBuilderSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathBuilder}
     *  algorithm.  See the CertPathBuilder section in the <a href=
     *  "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathBuilder">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return a {@code CertPathBuilder} object that implements the
     *          specified algorithm.
     *
     * @throws NoSuchAlgorithmException if no Provider supports a
     *          CertPathBuilderSpi implementation for the
     *          specified algorithm.
     *
     * @see java.security.Provider
     */
    public static CertPathBuilder getInstance(String algorithm)
            throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("CertPathBuilder",
            CertPathBuilderSpi.class, algorithm);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a {@code CertPathBuilder} object that implements the
     * specified algorithm.
     *
     * <p> A new CertPathBuilder object encapsulating the
     * CertPathBuilderSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathBuilder}对象。
     * 
     *  <p>返回一封新的CertPathBuilder对象,其中包含来自指定提供程序的CertPathBuilderSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathBuilder}
     *  algorithm.  See the CertPathBuilder section in the <a href=
     *  "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathBuilder">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return a {@code CertPathBuilder} object that implements the
     *          specified algorithm.
     *
     * @throws NoSuchAlgorithmException if a CertPathBuilderSpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @throws NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null or empty.
     *
     * @see java.security.Provider
     */
    public static CertPathBuilder getInstance(String algorithm, String provider)
           throws NoSuchAlgorithmException, NoSuchProviderException {
        Instance instance = GetInstance.getInstance("CertPathBuilder",
            CertPathBuilderSpi.class, algorithm, provider);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a {@code CertPathBuilder} object that implements the
     * specified algorithm.
     *
     * <p> A new CertPathBuilder object encapsulating the
     * CertPathBuilderSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathBuilder}对象。
     * 
     *  <p>返回一个新的CertPathBuilder对象,该对象封装来自指定的Provider对象的CertPathBuilderSpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathBuilder}
     *  algorithm.  See the CertPathBuilder section in the <a href=
     *  "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathBuilder">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the provider.
     *
     * @return a {@code CertPathBuilder} object that implements the
     *          specified algorithm.
     *
     * @exception NoSuchAlgorithmException if a CertPathBuilderSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null.
     *
     * @see java.security.Provider
     */
    public static CertPathBuilder getInstance(String algorithm,
            Provider provider) throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("CertPathBuilder",
            CertPathBuilderSpi.class, algorithm, provider);
        return new CertPathBuilder((CertPathBuilderSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns the provider of this {@code CertPathBuilder}.
     *
     * <p>
     *  返回此{@code CertPathBuilder}的提供程序。
     * 
     * 
     * @return the provider of this {@code CertPathBuilder}
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Returns the name of the algorithm of this {@code CertPathBuilder}.
     *
     * <p>
     *  返回此{@code CertPathBuilder}的算法的名称。
     * 
     * 
     * @return the name of the algorithm of this {@code CertPathBuilder}
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Attempts to build a certification path using the specified algorithm
     * parameter set.
     *
     * <p>
     *  尝试使用指定的算法参数集构建认证路径。
     * 
     * 
     * @param params the algorithm parameters
     * @return the result of the build algorithm
     * @throws CertPathBuilderException if the builder is unable to construct
     *  a certification path that satisfies the specified parameters
     * @throws InvalidAlgorithmParameterException if the specified parameters
     * are inappropriate for this {@code CertPathBuilder}
     */
    public final CertPathBuilderResult build(CertPathParameters params)
        throws CertPathBuilderException, InvalidAlgorithmParameterException
    {
        return builderSpi.engineBuild(params);
    }

    /**
     * Returns the default {@code CertPathBuilder} type as specified by
     * the {@code certpathbuilder.type} security property, or the string
     * {@literal "PKIX"} if no such property exists.
     *
     * <p>The default {@code CertPathBuilder} type can be used by
     * applications that do not want to use a hard-coded type when calling one
     * of the {@code getInstance} methods, and want to provide a default
     * type in case a user does not specify its own.
     *
     * <p>The default {@code CertPathBuilder} type can be changed by
     * setting the value of the {@code certpathbuilder.type} security property
     * to the desired type.
     *
     * <p>
     * 返回由{@code certpathbuilder.type}安全属性指定的默认{@code CertPathBuilder}类型,如果没有此类属性,则返回字符串{@literal"PKIX"}。
     * 
     *  <p>默认的{@code CertPathBuilder}类型可以由不想在调用{@code getInstance}方法时使用硬编码类型的应用程序使用,并且希望提供默认类型以防用户没有指定自己的。
     * 
     *  <p>可以通过将{@code certpathbuilder.type}安全属性的值设置为所需类型来更改默认的{@code CertPathBuilder}类型。
     * 
     * 
     * @see java.security.Security security properties
     * @return the default {@code CertPathBuilder} type as specified
     * by the {@code certpathbuilder.type} security property, or the string
     * {@literal "PKIX"} if no such property exists.
     */
    public final static String getDefaultType() {
        String cpbtype =
            AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    return Security.getProperty(CPB_TYPE);
                }
            });
        return (cpbtype == null) ? "PKIX" : cpbtype;
    }

    /**
     * Returns a {@code CertPathChecker} that the encapsulated
     * {@code CertPathBuilderSpi} implementation uses to check the revocation
     * status of certificates. A PKIX implementation returns objects of
     * type {@code PKIXRevocationChecker}. Each invocation of this method
     * returns a new instance of {@code CertPathChecker}.
     *
     * <p>The primary purpose of this method is to allow callers to specify
     * additional input parameters and options specific to revocation checking.
     * See the class description for an example.
     *
     * <p>
     * 
     * @return a {@code CertPathChecker}
     * @throws UnsupportedOperationException if the service provider does not
     *         support this method
     * @since 1.8
     */
    public final CertPathChecker getRevocationChecker() {
        return builderSpi.engineGetRevocationChecker();
    }
}
