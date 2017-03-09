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
 * A class for validating certification paths (also known as certificate
 * chains).
 * <p>
 * This class uses a provider-based architecture.
 * To create a {@code CertPathValidator},
 * call one of the static {@code getInstance} methods, passing in the
 * algorithm name of the {@code CertPathValidator} desired and
 * optionally the name of the provider desired.
 *
 * <p>Once a {@code CertPathValidator} object has been created, it can
 * be used to validate certification paths by calling the {@link #validate
 * validate} method and passing it the {@code CertPath} to be validated
 * and an algorithm-specific set of parameters. If successful, the result is
 * returned in an object that implements the
 * {@code CertPathValidatorResult} interface.
 *
 * <p>The {@link #getRevocationChecker} method allows an application to specify
 * additional algorithm-specific parameters and options used by the
 * {@code CertPathValidator} when checking the revocation status of
 * certificates. Here is an example demonstrating how it is used with the PKIX
 * algorithm:
 *
 * <pre>
 * CertPathValidator cpv = CertPathValidator.getInstance("PKIX");
 * PKIXRevocationChecker rc = (PKIXRevocationChecker)cpv.getRevocationChecker();
 * rc.setOptions(EnumSet.of(Option.SOFT_FAIL));
 * params.addCertPathChecker(rc);
 * CertPathValidatorResult cpvr = cpv.validate(path, params);
 * </pre>
 *
 * <p>Every implementation of the Java platform is required to support the
 * following standard {@code CertPathValidator} algorithm:
 * <ul>
 * <li>{@code PKIX}</li>
 * </ul>
 * This algorithm is described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathValidator">
 * CertPathValidator section</a> of the
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
 * access a single {@code CertPathValidator} instance concurrently should
 * synchronize amongst themselves and provide the necessary locking. Multiple
 * threads each manipulating a different {@code CertPathValidator}
 * instance need not synchronize.
 *
 * <p>
 *  用于验证证书路径(也称为证书链)的类。
 * <p>
 *  此类使用基于提供程序的体系结构。
 * 要创建{@code CertPathValidator},请调用其中一个静态{@code getInstance}方法,传入所需的{@code CertPathValidator}的算法名称和可选的所需
 * 提供程序的名称。
 *  此类使用基于提供程序的体系结构。
 * 
 *  <p>一旦创建了{@code CertPathValidator}对象,就可以通过调用{@link #validate validate}方法并将其传递给要验证的{@code CertPath}来验证
 * 证书路径,特定的参数集。
 * 如果成功,将在实现{@code CertPathValidatorResult}接口的对象中返回结果。
 * 
 *  <p> {@link #getRevocationChecker}方法允许应用程序在检查证书的吊销状态时指定{@code CertPathValidator}使用的其他算法特定的参数和选项。
 * 这里是一个例子演示如何使用PKIX算法：。
 * 
 * <pre>
 *  CertPathValidator cpv = CertPathValidator.getInstance("PKIX"); PKIXRevocationChecker rc =(PKIXRevoca
 * tionChecker)cpv.getRevocationChecker(); rc.setOptions(EnumSet.of(Option.SOFT_FAIL)); params.addCertPa
 * thChecker(rc); CertPathValidatorResult cpvr = cpv.validate(path,params);。
 * </pre>
 * 
 * <p>每个Java平台的实施都需要支持以下标准{@code CertPathValidator}算法：
 * <ul>
 *  <li> {@ code PKIX} </li>
 * </ul>
 *  此算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathValidator">
 *  Java Cryptography Architecture标准算法名称文档的CertPathValidator部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  这个类的静态方法被保证是线程安全的。多线程可以同时调用这个类中定义的静态方法,没有不良影响。
 * <p>
 *  然而,这不是真的这个类定义的非静态方法。除非特定提供程序另有说明,需要同时访问单个{@code CertPathValidator}实例的线程应在它们之间同步并提供必要的锁定。
 * 每个操作不同{@code CertPathValidator}实例的多个线程不需要同步。
 * 
 * 
 * @see CertPath
 *
 * @since       1.4
 * @author      Yassir Elley
 */
public class CertPathValidator {

    /*
     * Constant to lookup in the Security properties file to determine
     * the default certpathvalidator type. In the Security properties file,
     * the default certpathvalidator type is given as:
     * <pre>
     * certpathvalidator.type=PKIX
     * </pre>
     * <p>
     *  在安全属性文件中查找的常量以确定缺省certpathvalidator类型。在安全属性文件中,缺省certpathvalidator类型为：
     * <pre>
     *  certpathvalidator.type = PKIX
     * </pre>
     */
    private static final String CPV_TYPE = "certpathvalidator.type";
    private final CertPathValidatorSpi validatorSpi;
    private final Provider provider;
    private final String algorithm;

    /**
     * Creates a {@code CertPathValidator} object of the given algorithm,
     * and encapsulates the given provider implementation (SPI object) in it.
     *
     * <p>
     *  创建给定算法的{@code CertPathValidator}对象,并将给定的提供程序实现(SPI对象)封装在其中。
     * 
     * 
     * @param validatorSpi the provider implementation
     * @param provider the provider
     * @param algorithm the algorithm name
     */
    protected CertPathValidator(CertPathValidatorSpi validatorSpi,
        Provider provider, String algorithm)
    {
        this.validatorSpi = validatorSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }

    /**
     * Returns a {@code CertPathValidator} object that implements the
     * specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new CertPathValidator object encapsulating the
     * CertPathValidatorSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathValidator}对象。
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的CertPathValidator对象,该对象封装来自支持指定算法的第一个Provider的CertPathValidatorSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathValidator}
     *  algorithm. See the CertPathValidator section in the <a href=
     *  "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathValidator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return a {@code CertPathValidator} object that implements the
     *          specified algorithm.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          CertPathValidatorSpi implementation for the
     *          specified algorithm.
     *
     * @see java.security.Provider
     */
    public static CertPathValidator getInstance(String algorithm)
            throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("CertPathValidator",
            CertPathValidatorSpi.class, algorithm);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a {@code CertPathValidator} object that implements the
     * specified algorithm.
     *
     * <p> A new CertPathValidator object encapsulating the
     * CertPathValidatorSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathValidator}对象。
     * 
     *  <p>将返回一封新的CertPathValidator对象,该对象封装来自指定提供程序的CertPathValidatorSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathValidator}
     *  algorithm. See the CertPathValidator section in the <a href=
     *  "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathValidator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return a {@code CertPathValidator} object that implements the
     *          specified algorithm.
     *
     * @exception NoSuchAlgorithmException if a CertPathValidatorSpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null or empty.
     *
     * @see java.security.Provider
     */
    public static CertPathValidator getInstance(String algorithm,
            String provider) throws NoSuchAlgorithmException,
            NoSuchProviderException {
        Instance instance = GetInstance.getInstance("CertPathValidator",
            CertPathValidatorSpi.class, algorithm, provider);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a {@code CertPathValidator} object that implements the
     * specified algorithm.
     *
     * <p> A new CertPathValidator object encapsulating the
     * CertPathValidatorSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回实现指定算法的{@code CertPathValidator}对象。
     * 
     *  <p>返回一个新的CertPathValidator对象,该对象封装来自指定的Provider对象的CertPathValidatorSpi实现。
     * 请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the name of the requested {@code CertPathValidator}
     * algorithm. See the CertPathValidator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertPathValidator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the provider.
     *
     * @return a {@code CertPathValidator} object that implements the
     *          specified algorithm.
     *
     * @exception NoSuchAlgorithmException if a CertPathValidatorSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null.
     *
     * @see java.security.Provider
     */
    public static CertPathValidator getInstance(String algorithm,
            Provider provider) throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("CertPathValidator",
            CertPathValidatorSpi.class, algorithm, provider);
        return new CertPathValidator((CertPathValidatorSpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns the {@code Provider} of this
     * {@code CertPathValidator}.
     *
     * <p>
     *  返回此{@code CertPathValidator}的{@code Provider}。
     * 
     * 
     * @return the {@code Provider} of this {@code CertPathValidator}
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Returns the algorithm name of this {@code CertPathValidator}.
     *
     * <p>
     *  返回此{@code CertPathValidator}的算法名称。
     * 
     * 
     * @return the algorithm name of this {@code CertPathValidator}
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Validates the specified certification path using the specified
     * algorithm parameter set.
     * <p>
     * The {@code CertPath} specified must be of a type that is
     * supported by the validation algorithm, otherwise an
     * {@code InvalidAlgorithmParameterException} will be thrown. For
     * example, a {@code CertPathValidator} that implements the PKIX
     * algorithm validates {@code CertPath} objects of type X.509.
     *
     * <p>
     *  使用指定的算法参数集验证指定的认证路径。
     * <p>
     * 指定的{@code CertPath}必须是验证算法支持的类型,否则将抛出{@code InvalidAlgorithmParameterException}。
     * 例如,实现PKIX算法的{@code CertPathValidator}会验证类型X.509的{@code CertPath}对象。
     * 
     * 
     * @param certPath the {@code CertPath} to be validated
     * @param params the algorithm parameters
     * @return the result of the validation algorithm
     * @exception CertPathValidatorException if the {@code CertPath}
     * does not validate
     * @exception InvalidAlgorithmParameterException if the specified
     * parameters or the type of the specified {@code CertPath} are
     * inappropriate for this {@code CertPathValidator}
     */
    public final CertPathValidatorResult validate(CertPath certPath,
        CertPathParameters params)
        throws CertPathValidatorException, InvalidAlgorithmParameterException
    {
        return validatorSpi.engineValidate(certPath, params);
    }

    /**
     * Returns the default {@code CertPathValidator} type as specified by
     * the {@code certpathvalidator.type} security property, or the string
     * {@literal "PKIX"} if no such property exists.
     *
     * <p>The default {@code CertPathValidator} type can be used by
     * applications that do not want to use a hard-coded type when calling one
     * of the {@code getInstance} methods, and want to provide a default
     * type in case a user does not specify its own.
     *
     * <p>The default {@code CertPathValidator} type can be changed by
     * setting the value of the {@code certpathvalidator.type} security
     * property to the desired type.
     *
     * <p>
     *  返回由{@code certpathvalidator.type}安全属性指定的默认{@code CertPathValidator}类型,如果没有此类属性,则返回字符串{@literal"PKIX"}
     * 。
     * 
     *  <p>默认的{@code CertPathValidator}类型可以由不想在调用{@code getInstance}方法时使用硬编码类型的应用程序使用,并且希望提供默认类型以防用户没有指定自己的。
     * 
     *  <p>可以通过将{@code certpathvalidator.type}安全属性的值设置为所需类型来更改默认的{@code CertPathValidator}类型。
     * 
     * 
     * @see java.security.Security security properties
     * @return the default {@code CertPathValidator} type as specified
     * by the {@code certpathvalidator.type} security property, or the string
     * {@literal "PKIX"} if no such property exists.
     */
    public final static String getDefaultType() {
        String cpvtype =
            AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    return Security.getProperty(CPV_TYPE);
                }
            });
        return (cpvtype == null) ? "PKIX" : cpvtype;
    }

    /**
     * Returns a {@code CertPathChecker} that the encapsulated
     * {@code CertPathValidatorSpi} implementation uses to check the revocation
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
        return validatorSpi.engineGetRevocationChecker();
    }
}
