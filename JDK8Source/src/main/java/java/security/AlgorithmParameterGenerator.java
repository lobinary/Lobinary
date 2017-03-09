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

import java.security.spec.AlgorithmParameterSpec;

/**
 * The {@code AlgorithmParameterGenerator} class is used to generate a
 * set of
 * parameters to be used with a certain algorithm. Parameter generators
 * are constructed using the {@code getInstance} factory methods
 * (static methods that return instances of a given class).
 *
 * <P>The object that will generate the parameters can be initialized
 * in two different ways: in an algorithm-independent manner, or in an
 * algorithm-specific manner:
 *
 * <ul>
 * <li>The algorithm-independent approach uses the fact that all parameter
 * generators share the concept of a "size" and a
 * source of randomness. The measure of size is universally shared
 * by all algorithm parameters, though it is interpreted differently
 * for different algorithms. For example, in the case of parameters for
 * the <i>DSA</i> algorithm, "size" corresponds to the size
 * of the prime modulus (in bits).
 * When using this approach, algorithm-specific parameter generation
 * values - if any - default to some standard values, unless they can be
 * derived from the specified size.
 *
 * <li>The other approach initializes a parameter generator object
 * using algorithm-specific semantics, which are represented by a set of
 * algorithm-specific parameter generation values. To generate
 * Diffie-Hellman system parameters, for example, the parameter generation
 * values usually
 * consist of the size of the prime modulus and the size of the
 * random exponent, both specified in number of bits.
 * </ul>
 *
 * <P>In case the client does not explicitly initialize the
 * AlgorithmParameterGenerator
 * (via a call to an {@code init} method), each provider must supply (and
 * document) a default initialization. For example, the Sun provider uses a
 * default modulus prime size of 1024 bits for the generation of DSA
 * parameters.
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code AlgorithmParameterGenerator} algorithms and
 * keysizes in parentheses:
 * <ul>
 * <li>{@code DiffieHellman} (1024)</li>
 * <li>{@code DSA} (1024)</li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameterGenerator">
 * AlgorithmParameterGenerator section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 *  {@code AlgorithmParameterGenerator}类用于生成要与特定算法一起使用的一组参数。
 * 参数生成器使用{@code getInstance}工厂方法(返回给定类的实例的静态方法)构造。
 * 
 *  <P>可以以两种不同的方式初始化将生成参数的对象：以与算法无关的方式或以特定于算法的方式：
 * 
 * <ul>
 *  <li>独立于算法的方法使用所有参数生成器共享"大小"和随机源的概念的事实。尺寸的度量被所有算法参数普遍地共享,但是对于不同的算法被不同地解释。
 * 例如,在用于DSA算法的参数的情况下,"大小"对应于素数模数的大小(以比特为单位)。当使用此方法时,特定于算法的参数生成值(如果有)默认为一些标准值,除非它们可以从指定的大小派生。
 * 
 * <li>另一种方法使用特定于算法的语义来初始化参数生成器对象,其由一组特定于算法的参数生成值表示。
 * 为了生成Diffie-Hellman系统参数,例如,参数生成值通常由素数模数的大小和随机指数的大小组成,二者都以比特数指定。
 * </ul>
 * 
 *  <P>如果客户端没有显式初始化AlgorithmParameterGenerator(通过调用{@code init}方法),每个提供程序必须提供(并记录)默认初始化。
 * 例如,Sun提供程序使用1024位的默认模量素数大小来生成DSA参数。
 * 
 *  <p>每个Java平台的实现都需要支持括号中的以下标准{@code AlgorithmParameterGenerator}算法和键值：
 * <ul>
 *  <li> {@ code DiffieHellman}(1024)</li> <li> {@ code DSA}(1024)</li>
 * </ul>
 *  这些算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameterGenerator">
 *  Java加密体系结构标准算法名称文档的AlgorithmParameterGenerator部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see AlgorithmParameters
 * @see java.security.spec.AlgorithmParameterSpec
 *
 * @since 1.2
 */

public class AlgorithmParameterGenerator {

    // The provider
    private Provider provider;

    // The provider implementation (delegate)
    private AlgorithmParameterGeneratorSpi paramGenSpi;

    // The algorithm
    private String algorithm;

    /**
     * Creates an AlgorithmParameterGenerator object.
     *
     * <p>
     *  创建AlgorithmParameterGenerator对象。
     * 
     * 
     * @param paramGenSpi the delegate
     * @param provider the provider
     * @param algorithm the algorithm
     */
    protected AlgorithmParameterGenerator
    (AlgorithmParameterGeneratorSpi paramGenSpi, Provider provider,
     String algorithm) {
        this.paramGenSpi = paramGenSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }

    /**
     * Returns the standard name of the algorithm this parameter
     * generator is associated with.
     *
     * <p>
     *  返回此参数生成器关联的算法的标准名称。
     * 
     * 
     * @return the string name of the algorithm.
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Returns an AlgorithmParameterGenerator object for generating
     * a set of parameters to be used with the specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new AlgorithmParameterGenerator object encapsulating the
     * AlgorithmParameterGeneratorSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回AlgorithmParameterGenerator对象,用于生成要与指定算法一起使用的一组参数。
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 返回一个新的AlgorithmParameterGenerator对象,该对象封装了来自支持指定算法的第一个Provider的AlgorithmParameterGeneratorSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the algorithm this
     * parameter generator is associated with.
     * See the AlgorithmParameterGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameterGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return the new AlgorithmParameterGenerator object.
     *
     * @exception NoSuchAlgorithmException if no Provider supports an
     *          AlgorithmParameterGeneratorSpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     */
    public static AlgorithmParameterGenerator getInstance(String algorithm)
        throws NoSuchAlgorithmException {
            try {
                Object[] objs = Security.getImpl(algorithm,
                                                 "AlgorithmParameterGenerator",
                                                 (String)null);
                return new AlgorithmParameterGenerator
                    ((AlgorithmParameterGeneratorSpi)objs[0],
                     (Provider)objs[1],
                     algorithm);
            } catch(NoSuchProviderException e) {
                throw new NoSuchAlgorithmException(algorithm + " not found");
            }
    }

    /**
     * Returns an AlgorithmParameterGenerator object for generating
     * a set of parameters to be used with the specified algorithm.
     *
     * <p> A new AlgorithmParameterGenerator object encapsulating the
     * AlgorithmParameterGeneratorSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回AlgorithmParameterGenerator对象,用于生成要与指定算法一起使用的一组参数。
     * 
     *  <p>返回一个新的AlgorithmParameterGenerator对象,用于封装来自指定提供程序的AlgorithmParameterGeneratorSpi实现。
     * 指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the algorithm this
     * parameter generator is associated with.
     * See the AlgorithmParameterGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameterGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the string name of the Provider.
     *
     * @return the new AlgorithmParameterGenerator object.
     *
     * @exception NoSuchAlgorithmException if an AlgorithmParameterGeneratorSpi
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
    public static AlgorithmParameterGenerator getInstance(String algorithm,
                                                          String provider)
        throws NoSuchAlgorithmException, NoSuchProviderException
    {
        if (provider == null || provider.length() == 0)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm,
                                         "AlgorithmParameterGenerator",
                                         provider);
        return new AlgorithmParameterGenerator
            ((AlgorithmParameterGeneratorSpi)objs[0], (Provider)objs[1],
             algorithm);
    }

    /**
     * Returns an AlgorithmParameterGenerator object for generating
     * a set of parameters to be used with the specified algorithm.
     *
     * <p> A new AlgorithmParameterGenerator object encapsulating the
     * AlgorithmParameterGeneratorSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回AlgorithmParameterGenerator对象,用于生成要与指定算法一起使用的一组参数。
     * 
     *  <p>返回一个新的AlgorithmParameterGenerator对象,用于封装来自指定的Provider对象的AlgorithmParameterGeneratorSpi实现。
     * 请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the string name of the algorithm this
     * parameter generator is associated with.
     * See the AlgorithmParameterGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#AlgorithmParameterGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the Provider object.
     *
     * @return the new AlgorithmParameterGenerator object.
     *
     * @exception NoSuchAlgorithmException if an AlgorithmParameterGeneratorSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static AlgorithmParameterGenerator getInstance(String algorithm,
                                                          Provider provider)
        throws NoSuchAlgorithmException
    {
        if (provider == null)
            throw new IllegalArgumentException("missing provider");
        Object[] objs = Security.getImpl(algorithm,
                                         "AlgorithmParameterGenerator",
                                         provider);
        return new AlgorithmParameterGenerator
            ((AlgorithmParameterGeneratorSpi)objs[0], (Provider)objs[1],
             algorithm);
    }

    /**
     * Returns the provider of this algorithm parameter generator object.
     *
     * <p>
     *  返回此算法参数生成器对象的提供程序。
     * 
     * 
     * @return the provider of this algorithm parameter generator object
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Initializes this parameter generator for a certain size.
     * To create the parameters, the {@code SecureRandom}
     * implementation of the highest-priority installed provider is used as
     * the source of randomness.
     * (If none of the installed providers supply an implementation of
     * {@code SecureRandom}, a system-provided source of randomness is
     * used.)
     *
     * <p>
     * 将此参数生成器初始化为特定大小。为了创建参数,使用最高优先级的安装提供程序的{@code SecureRandom}实现作为随机性的源。
     *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。)。
     * 
     * 
     * @param size the size (number of bits).
     */
    public final void init(int size) {
        paramGenSpi.engineInit(size, new SecureRandom());
    }

    /**
     * Initializes this parameter generator for a certain size and source
     * of randomness.
     *
     * <p>
     *  初始化此参数生成器以获得特定大小和随机源。
     * 
     * 
     * @param size the size (number of bits).
     * @param random the source of randomness.
     */
    public final void init(int size, SecureRandom random) {
        paramGenSpi.engineInit(size, random);
    }

    /**
     * Initializes this parameter generator with a set of algorithm-specific
     * parameter generation values.
     * To generate the parameters, the {@code SecureRandom}
     * implementation of the highest-priority installed provider is used as
     * the source of randomness.
     * (If none of the installed providers supply an implementation of
     * {@code SecureRandom}, a system-provided source of randomness is
     * used.)
     *
     * <p>
     *  使用一组特定于算法的参数生成值初始化此参数生成器。为了生成参数,将使用最高优先级的安装提供程序的{@code SecureRandom}实现作为随机源。
     *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。)。
     * 
     * 
     * @param genParamSpec the set of algorithm-specific parameter generation values.
     *
     * @exception InvalidAlgorithmParameterException if the given parameter
     * generation values are inappropriate for this parameter generator.
     */
    public final void init(AlgorithmParameterSpec genParamSpec)
        throws InvalidAlgorithmParameterException {
            paramGenSpi.engineInit(genParamSpec, new SecureRandom());
    }

    /**
     * Initializes this parameter generator with a set of algorithm-specific
     * parameter generation values.
     *
     * <p>
     *  使用一组特定于算法的参数生成值初始化此参数生成器。
     * 
     * 
     * @param genParamSpec the set of algorithm-specific parameter generation values.
     * @param random the source of randomness.
     *
     * @exception InvalidAlgorithmParameterException if the given parameter
     * generation values are inappropriate for this parameter generator.
     */
    public final void init(AlgorithmParameterSpec genParamSpec,
                           SecureRandom random)
        throws InvalidAlgorithmParameterException {
            paramGenSpi.engineInit(genParamSpec, random);
    }

    /**
     * Generates the parameters.
     *
     * <p>
     *  生成参数。
     * 
     * @return the new AlgorithmParameters object.
     */
    public final AlgorithmParameters generateParameters() {
        return paramGenSpi.engineGenerateParameters();
    }
}
