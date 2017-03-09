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

package java.security;

import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class is used as an opaque representation of cryptographic parameters.
 *
 * <p>An {@code AlgorithmParameters} object for managing the parameters
 * for a particular algorithm can be obtained by
 * calling one of the {@code getInstance} factory methods
 * (static methods that return instances of a given class).
 *
 * <p>Once an {@code AlgorithmParameters} object is obtained, it must be
 * initialized via a call to {@code init}, using an appropriate parameter
 * specification or parameter encoding.
 *
 * <p>A transparent parameter specification is obtained from an
 * {@code AlgorithmParameters} object via a call to
 * {@code getParameterSpec}, and a byte encoding of the parameters is
 * obtained via a call to {@code getEncoded}.
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code AlgorithmParameters} algorithms:
 * <ul>
 * <li>{@code AES}</li>
 * <li>{@code DES}</li>
 * <li>{@code DESede}</li>
 * <li>{@code DiffieHellman}</li>
 * <li>{@code DSA}</li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameters">
 * AlgorithmParameters section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 *  此类用作加密参数的不透明表示。
 * 
 *  <p>通过调用{@code getInstance}工厂方法(返回给定类的实例的静态方法)之一,可以获得用于管理特定算法的参数的{@code AlgorithmParameters}对象。
 * 
 *  <p>获取{@code AlgorithmParameters}对象后,必须使用适当的参数规范或参数编码,通过调用{@code init}初始化。
 * 
 *  <p>通过调用{@code getParameterSpec}从{@code AlgorithmParameters}对象获得透明参数规范,并通过调用{@code getEncoded}获得参数的字节
 * 编码。
 * 
 *  <p>每个Java平台的实现都需要支持以下标准{@code AlgorithmParameters}算法：
 * <ul>
 *  <li> {@ code AES} </li> <li> {@ code DES} </li> <li> {@ code DESede} </li> <li> {@ code DiffieHellman}
 *  > {@ code DSA} </li>。
 * </ul>
 *  这些算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameters">
 *  Java加密体系结构标准算法名称文档的AlgorithmParameters部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.spec.AlgorithmParameterSpec
 * @see java.security.spec.DSAParameterSpec
 * @see KeyPairGenerator
 *
 * @since 1.2
 */

public class AlgorithmParameters {

    // The provider
    private Provider provider;

    // The provider implementation (delegate)
    private AlgorithmParametersSpi paramSpi;

    // The algorithm
    private String algorithm;

    // Has this object been initialized?
    private boolean initialized = false;

    /**
     * Creates an AlgorithmParameters object.
     *
     * <p>
     *  创建AlgorithmParameters对象。
     * 
     * 
     * @param paramSpi the delegate
     * @param provider the provider
     * @param algorithm the algorithm
     */
    protected AlgorithmParameters(AlgorithmParametersSpi paramSpi,
                                  Provider provider, String algorithm)
    {
        this.paramSpi = paramSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }

    /**
     * Returns the name of the algorithm associated with this parameter object.
     *
     * <p>
     *  返回与此参数对象关联的算法的名称。
     * 
     * 
     * @return the algorithm name.
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Returns a parameter object for the specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new AlgorithmParameters object encapsulating the
     * AlgorithmParametersSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p> The returned parameter object must be initialized via a call to
     * {@code init}, using an appropriate parameter specification or
     * parameter encoding.
     *
     * <p>
     * 返回指定算法的参数对象。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 返回一个新的AlgorithmParameters对象,该对象封装了来自支持指定算法的第一个Provider的AlgorithmParametersSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的参数对象必须通过调用{@code init}初始化,使用适当的参数规范或参数编码。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the AlgorithmParameters section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameters">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return the new parameter object.
     *
     * @exception NoSuchAlgorithmException if no Provider supports an
     *          AlgorithmParametersSpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     */
    public static AlgorithmParameters getInstance(String algorithm)
    throws NoSuchAlgorithmException {
        try {
            Object[] objs = Security.getImpl(algorithm, "AlgorithmParameters",
                                             (String)null);
            return new AlgorithmParameters((AlgorithmParametersSpi)objs[0],
                                           (Provider)objs[1],
                                           algorithm);
        } catch(NoSuchProviderException e) {
            throw new NoSuchAlgorithmException(algorithm + " not found");
        }
    }

    /**
     * Returns a parameter object for the specified algorithm.
     *
     * <p> A new AlgorithmParameters object encapsulating the
     * AlgorithmParametersSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>The returned parameter object must be initialized via a call to
     * {@code init}, using an appropriate parameter specification or
     * parameter encoding.
     *
     * <p>
     *  返回指定算法的参数对象。
     * 
     *  <p>返回一个新的AlgorithmParameters对象,用于封装来自指定提供程序的AlgorithmParametersSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的参数对象必须通过调用{@code init}初始化,使用适当的参数规范或参数编码。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the AlgorithmParameters section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameters">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return the new parameter object.
     *
     * @exception NoSuchAlgorithmException if an AlgorithmParametersSpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the provider name is null
     *          or empty.
     *
     * @see Provider
     */
    public static AlgorithmParameters getInstance(String algorithm,
                                                  String provider)
        throws NoSuchAlgorithmException, NoSuchProviderException
    {
        if (provider == null || provider.length() == 0)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm, "AlgorithmParameters",
                                         provider);
        return new AlgorithmParameters((AlgorithmParametersSpi)objs[0],
                                       (Provider)objs[1],
                                       algorithm);
    }

    /**
     * Returns a parameter object for the specified algorithm.
     *
     * <p> A new AlgorithmParameters object encapsulating the
     * AlgorithmParametersSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>The returned parameter object must be initialized via a call to
     * {@code init}, using an appropriate parameter specification or
     * parameter encoding.
     *
     * <p>
     *  返回指定算法的参数对象。
     * 
     *  <p>返回一个新的AlgorithmParameters对象,用于封装来自指定的Provider对象的AlgorithmParametersSpi实现。
     * 请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * <p>返回的参数对象必须通过调用{@code init}初始化,使用适当的参数规范或参数编码。
     * 
     * 
     * @param algorithm the name of the algorithm requested.
     * See the AlgorithmParameters section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameters">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return the new parameter object.
     *
     * @exception NoSuchAlgorithmException if an AlgorithmParameterGeneratorSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static AlgorithmParameters getInstance(String algorithm,
                                                  Provider provider)
        throws NoSuchAlgorithmException
    {
        if (provider == null)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm, "AlgorithmParameters",
                                         provider);
        return new AlgorithmParameters((AlgorithmParametersSpi)objs[0],
                                       (Provider)objs[1],
                                       algorithm);
    }

    /**
     * Returns the provider of this parameter object.
     *
     * <p>
     *  返回此参数对象的提供程序。
     * 
     * 
     * @return the provider of this parameter object
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Initializes this parameter object using the parameters
     * specified in {@code paramSpec}.
     *
     * <p>
     *  使用{@code paramSpec}中指定的参数初始化此参数对象。
     * 
     * 
     * @param paramSpec the parameter specification.
     *
     * @exception InvalidParameterSpecException if the given parameter
     * specification is inappropriate for the initialization of this parameter
     * object, or if this parameter object has already been initialized.
     */
    public final void init(AlgorithmParameterSpec paramSpec)
        throws InvalidParameterSpecException
    {
        if (this.initialized)
            throw new InvalidParameterSpecException("already initialized");
        paramSpi.engineInit(paramSpec);
        this.initialized = true;
    }

    /**
     * Imports the specified parameters and decodes them according to the
     * primary decoding format for parameters. The primary decoding
     * format for parameters is ASN.1, if an ASN.1 specification for this type
     * of parameters exists.
     *
     * <p>
     *  导入指定的参数,根据参数的主解码格式进行解码。如果存在这种类型的参数的ASN.1规范,则参数的主解码格式是ASN.1。
     * 
     * 
     * @param params the encoded parameters.
     *
     * @exception IOException on decoding errors, or if this parameter object
     * has already been initialized.
     */
    public final void init(byte[] params) throws IOException {
        if (this.initialized)
            throw new IOException("already initialized");
        paramSpi.engineInit(params);
        this.initialized = true;
    }

    /**
     * Imports the parameters from {@code params} and decodes them
     * according to the specified decoding scheme.
     * If {@code format} is null, the
     * primary decoding format for parameters is used. The primary decoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * <p>
     *  从{@code params}导入参数,并根据指定的解码方案对它们进行解码。如果{@code format}为null,则使用参数的主解码格式。
     * 如果存在用于这些参数的ASN.1规范,则主解码格式是ASN.1。
     * 
     * 
     * @param params the encoded parameters.
     *
     * @param format the name of the decoding scheme.
     *
     * @exception IOException on decoding errors, or if this parameter object
     * has already been initialized.
     */
    public final void init(byte[] params, String format) throws IOException {
        if (this.initialized)
            throw new IOException("already initialized");
        paramSpi.engineInit(params, format);
        this.initialized = true;
    }

    /**
     * Returns a (transparent) specification of this parameter object.
     * {@code paramSpec} identifies the specification class in which
     * the parameters should be returned. It could, for example, be
     * {@code DSAParameterSpec.class}, to indicate that the
     * parameters should be returned in an instance of the
     * {@code DSAParameterSpec} class.
     *
     * <p>
     *  返回此参数对象的(透明)规范。 {@code paramSpec}标识应返回参数的规范类。
     * 例如,它可以是{@code DSAParameterSpec.class},以指示参数应在{@code DSAParameterSpec}类的实例中返回。
     * 
     * 
     * @param <T> the type of the parameter specification to be returrned
     * @param paramSpec the specification class in which
     * the parameters should be returned.
     *
     * @return the parameter specification.
     *
     * @exception InvalidParameterSpecException if the requested parameter
     * specification is inappropriate for this parameter object, or if this
     * parameter object has not been initialized.
     */
    public final <T extends AlgorithmParameterSpec>
        T getParameterSpec(Class<T> paramSpec)
        throws InvalidParameterSpecException
    {
        if (this.initialized == false) {
            throw new InvalidParameterSpecException("not initialized");
        }
        return paramSpi.engineGetParameterSpec(paramSpec);
    }

    /**
     * Returns the parameters in their primary encoding format.
     * The primary encoding format for parameters is ASN.1, if an ASN.1
     * specification for this type of parameters exists.
     *
     * <p>
     *  返回主要编码格式的参数。如果存在此类型的参数的ASN.1规范,则参数的主要编码格式为ASN.1。
     * 
     * 
     * @return the parameters encoded using their primary encoding format.
     *
     * @exception IOException on encoding errors, or if this parameter object
     * has not been initialized.
     */
    public final byte[] getEncoded() throws IOException
    {
        if (this.initialized == false) {
            throw new IOException("not initialized");
        }
        return paramSpi.engineGetEncoded();
    }

    /**
     * Returns the parameters encoded in the specified scheme.
     * If {@code format} is null, the
     * primary encoding format for parameters is used. The primary encoding
     * format is ASN.1, if an ASN.1 specification for these parameters
     * exists.
     *
     * <p>
     * 返回在指定方案中编码的参数。如果{@code format}为null,则使用参数的主要编码格式。如果存在这些参数的ASN.1规范,则主要编码格式为ASN.1。
     * 
     * 
     * @param format the name of the encoding format.
     *
     * @return the parameters encoded using the specified encoding scheme.
     *
     * @exception IOException on encoding errors, or if this parameter object
     * has not been initialized.
     */
    public final byte[] getEncoded(String format) throws IOException
    {
        if (this.initialized == false) {
            throw new IOException("not initialized");
        }
        return paramSpi.engineGetEncoded(format);
    }

    /**
     * Returns a formatted string describing the parameters.
     *
     * <p>
     *  返回描述参数的格式化字符串。
     * 
     * @return a formatted string describing the parameters, or null if this
     * parameter object has not been initialized.
     */
    public final String toString() {
        if (this.initialized == false) {
            return null;
        }
        return paramSpi.engineToString();
    }
}
