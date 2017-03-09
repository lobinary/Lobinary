/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;

import java.security.spec.AlgorithmParameterSpec;

import java.security.Provider.Service;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;
import sun.security.util.Debug;

/**
 * The KeyPairGenerator class is used to generate pairs of
 * public and private keys. Key pair generators are constructed using the
 * {@code getInstance} factory methods (static methods that
 * return instances of a given class).
 *
 * <p>A Key pair generator for a particular algorithm creates a public/private
 * key pair that can be used with this algorithm. It also associates
 * algorithm-specific parameters with each of the generated keys.
 *
 * <p>There are two ways to generate a key pair: in an algorithm-independent
 * manner, and in an algorithm-specific manner.
 * The only difference between the two is the initialization of the object:
 *
 * <ul>
 * <li><b>Algorithm-Independent Initialization</b>
 * <p>All key pair generators share the concepts of a keysize and a
 * source of randomness. The keysize is interpreted differently for different
 * algorithms (e.g., in the case of the <i>DSA</i> algorithm, the keysize
 * corresponds to the length of the modulus).
 * There is an
 * {@link #initialize(int, java.security.SecureRandom) initialize}
 * method in this KeyPairGenerator class that takes these two universally
 * shared types of arguments. There is also one that takes just a
 * {@code keysize} argument, and uses the {@code SecureRandom}
 * implementation of the highest-priority installed provider as the source
 * of randomness. (If none of the installed providers supply an implementation
 * of {@code SecureRandom}, a system-provided source of randomness is
 * used.)
 *
 * <p>Since no other parameters are specified when you call the above
 * algorithm-independent {@code initialize} methods, it is up to the
 * provider what to do about the algorithm-specific parameters (if any) to be
 * associated with each of the keys.
 *
 * <p>If the algorithm is the <i>DSA</i> algorithm, and the keysize (modulus
 * size) is 512, 768, or 1024, then the <i>Sun</i> provider uses a set of
 * precomputed values for the {@code p}, {@code q}, and
 * {@code g} parameters. If the modulus size is not one of the above
 * values, the <i>Sun</i> provider creates a new set of parameters. Other
 * providers might have precomputed parameter sets for more than just the
 * three modulus sizes mentioned above. Still others might not have a list of
 * precomputed parameters at all and instead always create new parameter sets.
 *
 * <li><b>Algorithm-Specific Initialization</b>
 * <p>For situations where a set of algorithm-specific parameters already
 * exists (e.g., so-called <i>community parameters</i> in DSA), there are two
 * {@link #initialize(java.security.spec.AlgorithmParameterSpec)
 * initialize} methods that have an {@code AlgorithmParameterSpec}
 * argument. One also has a {@code SecureRandom} argument, while the
 * the other uses the {@code SecureRandom}
 * implementation of the highest-priority installed provider as the source
 * of randomness. (If none of the installed providers supply an implementation
 * of {@code SecureRandom}, a system-provided source of randomness is
 * used.)
 * </ul>
 *
 * <p>In case the client does not explicitly initialize the KeyPairGenerator
 * (via a call to an {@code initialize} method), each provider must
 * supply (and document) a default initialization.
 * For example, the <i>Sun</i> provider uses a default modulus size (keysize)
 * of 1024 bits.
 *
 * <p>Note that this class is abstract and extends from
 * {@code KeyPairGeneratorSpi} for historical reasons.
 * Application developers should only take notice of the methods defined in
 * this {@code KeyPairGenerator} class; all the methods in
 * the superclass are intended for cryptographic service providers who wish to
 * supply their own implementations of key pair generators.
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code KeyPairGenerator} algorithms and keysizes in
 * parentheses:
 * <ul>
 * <li>{@code DiffieHellman} (1024)</li>
 * <li>{@code DSA} (1024)</li>
 * <li>{@code RSA} (1024, 2048)</li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
 * KeyPairGenerator section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 *  KeyPairGenerator类用于生成公钥和私钥对。密钥对生成器使用{@code getInstance}工厂方法(返回给定类的实例的静态方法)构造。
 * 
 *  <p>特定算法的密钥对生成器创建可以与此算法一起使用的公钥/私钥对。它还将算法特定的参数与每个生成的密钥相关联。
 * 
 *  <p>有两种方式来生成密钥对：以独立于算法的方式,以及以算法特定的方式。两者之间的唯一区别是对象的初始化：
 * 
 * <ul>
 * <li> <b>算法独立初始化</b> <p>所有密钥对生成器共享keysize和随机源的概念。针对不同的算法对密钥大小进行不同的解释(例如,在DSA算法的情况下,密钥大小对应于模的长度)。
 * 在这个KeyPairGenerator类中有一个{@link #initialize(int,java.security.SecureRandom)initialize}方法,它接受这两个通用共享类型的
 * 参数。
 * <li> <b>算法独立初始化</b> <p>所有密钥对生成器共享keysize和随机源的概念。针对不同的算法对密钥大小进行不同的解释(例如,在DSA算法的情况下,密钥大小对应于模的长度)。
 * 还有一个只需要一个{@code keysize}参数,并使用最高优先级的安装提供程序的{@code SecureRandom}实现作为随机源。
 *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。)。
 * 
 *  <p>由于在调用上述与算法无关的{@code initialize}方法时没有指定其他参数,因此由提供者对与每个算法相关的算法特定参数(如果有的话)做什么,键。
 * 
 * <p>如果算法是<DSA </i>算法,并且keysize(模数大小)是512,768或1024,则Sun提供者使用一组预计算值用于{@code p},{@code q}和{@code g}参数。
 * 如果模量大小不是上述值之一,则<i> Sun </i>提供器创建一组新的参数。其他提供者可能具有不止于上述三个模量大小的预计算参数集。还有一些可能没有预先计算参数的列表,而是总是创建新的参数集。
 * 
 *  <li> <b>算法特定初始化</b> <p>对于一组算法特定参数已经存在的情况(例如,DSA中的所谓社群参数</i>),两个具有{@code AlgorithmParameterSpec}参数的{@link #initialize(java.security.spec.AlgorithmParameterSpec)initialize}
 * 方法。
 * 一个还有一个{@code SecureRandom}参数,而另一个使用最高优先级的安装提供程序的{@code SecureRandom}实现作为随机源。
 *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。)。
 * </ul>
 * 
 *  <p>如果客户端没有显式初始化KeyPairGenerator(通过调用{@code initialize}方法),每个提供程序必须提供(并记录)默认初始化。
 * 例如,<i> Sun </i>提供程序使用1024位的默认模数大小(keysize)。
 * 
 * <p>请注意,这个类是抽象的,并且从{@code KeyPairGeneratorSpi}扩展为历史原因。
 * 应用程序开发人员应该只注意这个{@code KeyPairGenerator}类中定义的方法;超类中的所有方法都面向希望提供其自己的密钥对生成器的实现的加密服务提供者。
 * 
 *  <p>每个Java平台的实现都需要在括号中支持以下标准{@code KeyPairGenerator}算法和键值：
 * <ul>
 *  <li> {@ code DiffieHellman}(1024)</li> <li> {@ code DSA}(1024)</li> <li> {@ code RSA}(1024,2048)</li>
 * 。
 * </ul>
 *  这些算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
 *  Java密码体系结构标准算法名称文档的KeyPairGenerator部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * 
 * @author Benjamin Renaud
 *
 * @see java.security.spec.AlgorithmParameterSpec
 */

public abstract class KeyPairGenerator extends KeyPairGeneratorSpi {

    private static final Debug pdebug =
                        Debug.getInstance("provider", "Provider");
    private static final boolean skipDebug =
        Debug.isOn("engine=") && !Debug.isOn("keypairgenerator");

    private final String algorithm;

    // The provider
    Provider provider;

    /**
     * Creates a KeyPairGenerator object for the specified algorithm.
     *
     * <p>
     *  为指定的算法创建一个KeyPairGenerator对象。
     * 
     * 
     * @param algorithm the standard string name of the algorithm.
     * See the KeyPairGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     */
    protected KeyPairGenerator(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Returns the standard name of the algorithm for this key pair generator.
     * See the KeyPairGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * <p>
     *  返回此密钥对生成器的算法的标准名称。请参阅<a href =中的KeyPairGenerator部分
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     *  Java加密架构标准算法名称文档</a>以获取有关标准算法名称的信息。
     * 
     * 
     * @return the standard string name of the algorithm.
     */
    public String getAlgorithm() {
        return this.algorithm;
    }

    private static KeyPairGenerator getInstance(Instance instance,
            String algorithm) {
        KeyPairGenerator kpg;
        if (instance.impl instanceof KeyPairGenerator) {
            kpg = (KeyPairGenerator)instance.impl;
        } else {
            KeyPairGeneratorSpi spi = (KeyPairGeneratorSpi)instance.impl;
            kpg = new Delegate(spi, algorithm);
        }
        kpg.provider = instance.provider;

        if (!skipDebug && pdebug != null) {
            pdebug.println("KeyPairGenerator." + algorithm +
                " algorithm from: " + kpg.provider.getName());
        }

        return kpg;
    }

    /**
     * Returns a KeyPairGenerator object that generates public/private
     * key pairs for the specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new KeyPairGenerator object encapsulating the
     * KeyPairGeneratorSpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回一个KeyPairGenerator对象,该对象为指定的算法生成公钥/私钥对。
     * 
     * <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。
     * 将返回一个新的KeyPairGenerator对象,该对象封装了来自支持指定算法的第一个Provider的KeyPairGeneratorSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the standard string name of the algorithm.
     * See the KeyPairGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return the new KeyPairGenerator object.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          KeyPairGeneratorSpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     */
    public static KeyPairGenerator getInstance(String algorithm)
            throws NoSuchAlgorithmException {
        List<Service> list =
                GetInstance.getServices("KeyPairGenerator", algorithm);
        Iterator<Service> t = list.iterator();
        if (t.hasNext() == false) {
            throw new NoSuchAlgorithmException
                (algorithm + " KeyPairGenerator not available");
        }
        // find a working Spi or KeyPairGenerator subclass
        NoSuchAlgorithmException failure = null;
        do {
            Service s = t.next();
            try {
                Instance instance =
                    GetInstance.getInstance(s, KeyPairGeneratorSpi.class);
                if (instance.impl instanceof KeyPairGenerator) {
                    return getInstance(instance, algorithm);
                } else {
                    return new Delegate(instance, t, algorithm);
                }
            } catch (NoSuchAlgorithmException e) {
                if (failure == null) {
                    failure = e;
                }
            }
        } while (t.hasNext());
        throw failure;
    }

    /**
     * Returns a KeyPairGenerator object that generates public/private
     * key pairs for the specified algorithm.
     *
     * <p> A new KeyPairGenerator object encapsulating the
     * KeyPairGeneratorSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回一个KeyPairGenerator对象,该对象为指定的算法生成公钥/私钥对。
     * 
     *  <p>将返回一个新的KeyPairGenerator对象,用于封装来自指定提供程序的KeyPairGeneratorSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the standard string name of the algorithm.
     * See the KeyPairGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the string name of the provider.
     *
     * @return the new KeyPairGenerator object.
     *
     * @exception NoSuchAlgorithmException if a KeyPairGeneratorSpi
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
    public static KeyPairGenerator getInstance(String algorithm,
            String provider)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Instance instance = GetInstance.getInstance("KeyPairGenerator",
                KeyPairGeneratorSpi.class, algorithm, provider);
        return getInstance(instance, algorithm);
    }

    /**
     * Returns a KeyPairGenerator object that generates public/private
     * key pairs for the specified algorithm.
     *
     * <p> A new KeyPairGenerator object encapsulating the
     * KeyPairGeneratorSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回一个KeyPairGenerator对象,该对象为指定的算法生成公钥/私钥对。
     * 
     *  <p>返回一个新的KeyPairGenerator对象,该对象封装了来自指定的Provider对象的KeyPairGeneratorSpi实现。
     * 请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the standard string name of the algorithm.
     * See the KeyPairGenerator section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyPairGenerator">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the provider.
     *
     * @return the new KeyPairGenerator object.
     *
     * @exception NoSuchAlgorithmException if a KeyPairGeneratorSpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static KeyPairGenerator getInstance(String algorithm,
            Provider provider) throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("KeyPairGenerator",
                KeyPairGeneratorSpi.class, algorithm, provider);
        return getInstance(instance, algorithm);
    }

    /**
     * Returns the provider of this key pair generator object.
     *
     * <p>
     *  返回此密钥对生成器对象的提供程序。
     * 
     * 
     * @return the provider of this key pair generator object
     */
    public final Provider getProvider() {
        disableFailover();
        return this.provider;
    }

    void disableFailover() {
        // empty, overridden in Delegate
    }

    /**
     * Initializes the key pair generator for a certain keysize using
     * a default parameter set and the {@code SecureRandom}
     * implementation of the highest-priority installed provider as the source
     * of randomness.
     * (If none of the installed providers supply an implementation of
     * {@code SecureRandom}, a system-provided source of randomness is
     * used.)
     *
     * <p>
     * 使用默认参数集和最高优先级安装的提供程序的{@code SecureRandom}实现来初始化某个keysize的密钥对生成器作为随机源。
     *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。)。
     * 
     * 
     * @param keysize the keysize. This is an
     * algorithm-specific metric, such as modulus length, specified in
     * number of bits.
     *
     * @exception InvalidParameterException if the {@code keysize} is not
     * supported by this KeyPairGenerator object.
     */
    public void initialize(int keysize) {
        initialize(keysize, JCAUtil.getSecureRandom());
    }

    /**
     * Initializes the key pair generator for a certain keysize with
     * the given source of randomness (and a default parameter set).
     *
     * <p>
     *  使用给定的随机源(和默认参数集)初始化某个键大小的键对生成器。
     * 
     * 
     * @param keysize the keysize. This is an
     * algorithm-specific metric, such as modulus length, specified in
     * number of bits.
     * @param random the source of randomness.
     *
     * @exception InvalidParameterException if the {@code keysize} is not
     * supported by this KeyPairGenerator object.
     *
     * @since 1.2
     */
    public void initialize(int keysize, SecureRandom random) {
        // This does nothing, because either
        // 1. the implementation object returned by getInstance() is an
        //    instance of KeyPairGenerator which has its own
        //    initialize(keysize, random) method, so the application would
        //    be calling that method directly, or
        // 2. the implementation returned by getInstance() is an instance
        //    of Delegate, in which case initialize(keysize, random) is
        //    overridden to call the corresponding SPI method.
        // (This is a special case, because the API and SPI method have the
        // same name.)
    }

    /**
     * Initializes the key pair generator using the specified parameter
     * set and the {@code SecureRandom}
     * implementation of the highest-priority installed provider as the source
     * of randomness.
     * (If none of the installed providers supply an implementation of
     * {@code SecureRandom}, a system-provided source of randomness is
     * used.).
     *
     * <p>This concrete method has been added to this previously-defined
     * abstract class.
     * This method calls the KeyPairGeneratorSpi
     * {@link KeyPairGeneratorSpi#initialize(
     * java.security.spec.AlgorithmParameterSpec,
     * java.security.SecureRandom) initialize} method,
     * passing it {@code params} and a source of randomness (obtained
     * from the highest-priority installed provider or system-provided if none
     * of the installed providers supply one).
     * That {@code initialize} method always throws an
     * UnsupportedOperationException if it is not overridden by the provider.
     *
     * <p>
     *  使用指定的参数集和最高优先级安装的提供程序的{@code SecureRandom}实现来初始化密钥对生成器作为随机源。
     *  (如果安装的提供程序没有提供{@code SecureRandom}的实现,则使用系统提供的随机源。
     * 
     *  <p>这个具体方法已添加到此前定义的抽象类中。
     * 此方法调用KeyPairGeneratorSpi {@link KeyPairGeneratorSpi#initialize(java.security.spec.AlgorithmParameterSpec,java.security.SecureRandom)initialize}
     * 方法,传递它{@code params}和随机性源(从最高优先级安装提供商或系统提供,如果没有安装的提供商提供)。
     *  <p>这个具体方法已添加到此前定义的抽象类中。 {@code initialize}方法如果没有被提供者覆盖,总会抛出UnsupportedOperationException异常。
     * 
     * 
     * @param params the parameter set used to generate the keys.
     *
     * @exception InvalidAlgorithmParameterException if the given parameters
     * are inappropriate for this key pair generator.
     *
     * @since 1.2
     */
    public void initialize(AlgorithmParameterSpec params)
            throws InvalidAlgorithmParameterException {
        initialize(params, JCAUtil.getSecureRandom());
    }

    /**
     * Initializes the key pair generator with the given parameter
     * set and source of randomness.
     *
     * <p>This concrete method has been added to this previously-defined
     * abstract class.
     * This method calls the KeyPairGeneratorSpi {@link
     * KeyPairGeneratorSpi#initialize(
     * java.security.spec.AlgorithmParameterSpec,
     * java.security.SecureRandom) initialize} method,
     * passing it {@code params} and {@code random}.
     * That {@code initialize}
     * method always throws an
     * UnsupportedOperationException if it is not overridden by the provider.
     *
     * <p>
     *  使用给定的参数集和随机源初始化密钥对生成器。
     * 
     * <p>这个具体方法已添加到此前定义的抽象类中。
     * 此方法调用KeyPairGeneratorSpi {@link KeyPairGeneratorSpi#initialize(java.security.spec.AlgorithmParameterSpec,java.security.SecureRandom)initialize}
     * 方法,传递它{@code params}和{@code random}。
     * <p>这个具体方法已添加到此前定义的抽象类中。 {@code initialize}方法如果没有被提供者覆盖,总会抛出UnsupportedOperationException异常。
     * 
     * 
     * @param params the parameter set used to generate the keys.
     * @param random the source of randomness.
     *
     * @exception InvalidAlgorithmParameterException if the given parameters
     * are inappropriate for this key pair generator.
     *
     * @since 1.2
     */
    public void initialize(AlgorithmParameterSpec params,
                           SecureRandom random)
        throws InvalidAlgorithmParameterException
    {
        // This does nothing, because either
        // 1. the implementation object returned by getInstance() is an
        //    instance of KeyPairGenerator which has its own
        //    initialize(params, random) method, so the application would
        //    be calling that method directly, or
        // 2. the implementation returned by getInstance() is an instance
        //    of Delegate, in which case initialize(params, random) is
        //    overridden to call the corresponding SPI method.
        // (This is a special case, because the API and SPI method have the
        // same name.)
    }

    /**
     * Generates a key pair.
     *
     * <p>If this KeyPairGenerator has not been initialized explicitly,
     * provider-specific defaults will be used for the size and other
     * (algorithm-specific) values of the generated keys.
     *
     * <p>This will generate a new key pair every time it is called.
     *
     * <p>This method is functionally equivalent to
     * {@link #generateKeyPair() generateKeyPair}.
     *
     * <p>
     *  生成密钥对。
     * 
     *  <p>如果此KeyPairGenerator未显式初始化,则提供程序特定的默认值将用于生成的键的大小和其他(特定于算法)值。
     * 
     *  <p>这将在每次被调用时生成一个新的密钥对。
     * 
     *  <p>此方法在功能上等同于{@link #generateKeyPair()generateKeyPair}。
     * 
     * 
     * @return the generated key pair
     *
     * @since 1.2
     */
    public final KeyPair genKeyPair() {
        return generateKeyPair();
    }

    /**
     * Generates a key pair.
     *
     * <p>If this KeyPairGenerator has not been initialized explicitly,
     * provider-specific defaults will be used for the size and other
     * (algorithm-specific) values of the generated keys.
     *
     * <p>This will generate a new key pair every time it is called.
     *
     * <p>This method is functionally equivalent to
     * {@link #genKeyPair() genKeyPair}.
     *
     * <p>
     *  生成密钥对。
     * 
     *  <p>如果此KeyPairGenerator未显式初始化,则提供程序特定的默认值将用于生成的键的大小和其他(特定于算法)值。
     * 
     *  <p>这将在每次被调用时生成一个新的密钥对。
     * 
     *  <p>此方法在功能上等同于{@link #genKeyPair()genKeyPair}。
     * 
     * 
     * @return the generated key pair
     */
    public KeyPair generateKeyPair() {
        // This does nothing (except returning null), because either:
        //
        // 1. the implementation object returned by getInstance() is an
        //    instance of KeyPairGenerator which has its own implementation
        //    of generateKeyPair (overriding this one), so the application
        //    would be calling that method directly, or
        //
        // 2. the implementation returned by getInstance() is an instance
        //    of Delegate, in which case generateKeyPair is
        //    overridden to invoke the corresponding SPI method.
        //
        // (This is a special case, because in JDK 1.1.x the generateKeyPair
        // method was used both as an API and a SPI method.)
        return null;
    }


    /*
     * The following class allows providers to extend from KeyPairGeneratorSpi
     * rather than from KeyPairGenerator. It represents a KeyPairGenerator
     * with an encapsulated, provider-supplied SPI object (of type
     * KeyPairGeneratorSpi).
     * If the provider implementation is an instance of KeyPairGeneratorSpi,
     * the getInstance() methods above return an instance of this class, with
     * the SPI object encapsulated.
     *
     * Note: All SPI methods from the original KeyPairGenerator class have been
     * moved up the hierarchy into a new class (KeyPairGeneratorSpi), which has
     * been interposed in the hierarchy between the API (KeyPairGenerator)
     * and its original parent (Object).
     * <p>
     * 以下类允许提供程序从KeyPairGeneratorSpi而不是从KeyPairGenerator扩展。
     * 它表示具有封装的,提供商提供的SPI对象(类型为KeyPairGeneratorSpi)的KeyPairGenerator。
     * 如果提供程序实现是KeyPairGeneratorSpi的实例,上面的getInstance()方法返回此类的实例,并封装SPI对象。
     * 
     *  注意：来自原始KeyPairGenerator类的所有SPI方法已经从层次结构向上移动到新类(KeyPairGeneratorSpi)中,该类已经插入在API(KeyPairGenerator)和其原
     */

    //
    // error failover notes:
    //
    //  . we failover if the implementation throws an error during init
    //    by retrying the init on other providers
    //
    //  . we also failover if the init succeeded but the subsequent call
    //    to generateKeyPair() fails. In order for this to work, we need
    //    to remember the parameters to the last successful call to init
    //    and initialize() the next spi using them.
    //
    //  . although not specified, KeyPairGenerators could be thread safe,
    //    so we make sure we do not interfere with that
    //
    //  . failover is not available, if:
    //    . getInstance(algorithm, provider) was used
    //    . a provider extends KeyPairGenerator rather than
    //      KeyPairGeneratorSpi (JDK 1.1 style)
    //    . once getProvider() is called
    //

    private static final class Delegate extends KeyPairGenerator {

        // The provider implementation (delegate)
        private volatile KeyPairGeneratorSpi spi;

        private final Object lock = new Object();

        private Iterator<Service> serviceIterator;

        private final static int I_NONE   = 1;
        private final static int I_SIZE   = 2;
        private final static int I_PARAMS = 3;

        private int initType;
        private int initKeySize;
        private AlgorithmParameterSpec initParams;
        private SecureRandom initRandom;

        // constructor
        Delegate(KeyPairGeneratorSpi spi, String algorithm) {
            super(algorithm);
            this.spi = spi;
        }

        Delegate(Instance instance, Iterator<Service> serviceIterator,
                String algorithm) {
            super(algorithm);
            spi = (KeyPairGeneratorSpi)instance.impl;
            provider = instance.provider;
            this.serviceIterator = serviceIterator;
            initType = I_NONE;

            if (!skipDebug && pdebug != null) {
                pdebug.println("KeyPairGenerator." + algorithm +
                    " algorithm from: " + provider.getName());
            }
        }

        /**
         * Update the active spi of this class and return the next
         * implementation for failover. If no more implemenations are
         * available, this method returns null. However, the active spi of
         * this class is never set to null.
         * <p>
         * 始父(Object)之间的层次结构中。
         * 
         */
        private KeyPairGeneratorSpi nextSpi(KeyPairGeneratorSpi oldSpi,
                boolean reinit) {
            synchronized (lock) {
                // somebody else did a failover concurrently
                // try that spi now
                if ((oldSpi != null) && (oldSpi != spi)) {
                    return spi;
                }
                if (serviceIterator == null) {
                    return null;
                }
                while (serviceIterator.hasNext()) {
                    Service s = serviceIterator.next();
                    try {
                        Object inst = s.newInstance(null);
                        // ignore non-spis
                        if (inst instanceof KeyPairGeneratorSpi == false) {
                            continue;
                        }
                        if (inst instanceof KeyPairGenerator) {
                            continue;
                        }
                        KeyPairGeneratorSpi spi = (KeyPairGeneratorSpi)inst;
                        if (reinit) {
                            if (initType == I_SIZE) {
                                spi.initialize(initKeySize, initRandom);
                            } else if (initType == I_PARAMS) {
                                spi.initialize(initParams, initRandom);
                            } else if (initType != I_NONE) {
                                throw new AssertionError
                                    ("KeyPairGenerator initType: " + initType);
                            }
                        }
                        provider = s.getProvider();
                        this.spi = spi;
                        return spi;
                    } catch (Exception e) {
                        // ignore
                    }
                }
                disableFailover();
                return null;
            }
        }

        void disableFailover() {
            serviceIterator = null;
            initType = 0;
            initParams = null;
            initRandom = null;
        }

        // engine method
        public void initialize(int keysize, SecureRandom random) {
            if (serviceIterator == null) {
                spi.initialize(keysize, random);
                return;
            }
            RuntimeException failure = null;
            KeyPairGeneratorSpi mySpi = spi;
            do {
                try {
                    mySpi.initialize(keysize, random);
                    initType = I_SIZE;
                    initKeySize = keysize;
                    initParams = null;
                    initRandom = random;
                    return;
                } catch (RuntimeException e) {
                    if (failure == null) {
                        failure = e;
                    }
                    mySpi = nextSpi(mySpi, false);
                }
            } while (mySpi != null);
            throw failure;
        }

        // engine method
        public void initialize(AlgorithmParameterSpec params,
                SecureRandom random) throws InvalidAlgorithmParameterException {
            if (serviceIterator == null) {
                spi.initialize(params, random);
                return;
            }
            Exception failure = null;
            KeyPairGeneratorSpi mySpi = spi;
            do {
                try {
                    mySpi.initialize(params, random);
                    initType = I_PARAMS;
                    initKeySize = 0;
                    initParams = params;
                    initRandom = random;
                    return;
                } catch (Exception e) {
                    if (failure == null) {
                        failure = e;
                    }
                    mySpi = nextSpi(mySpi, false);
                }
            } while (mySpi != null);
            if (failure instanceof RuntimeException) {
                throw (RuntimeException)failure;
            }
            // must be an InvalidAlgorithmParameterException
            throw (InvalidAlgorithmParameterException)failure;
        }

        // engine method
        public KeyPair generateKeyPair() {
            if (serviceIterator == null) {
                return spi.generateKeyPair();
            }
            RuntimeException failure = null;
            KeyPairGeneratorSpi mySpi = spi;
            do {
                try {
                    return mySpi.generateKeyPair();
                } catch (RuntimeException e) {
                    if (failure == null) {
                        failure = e;
                    }
                    mySpi = nextSpi(mySpi, true);
                }
            } while (mySpi != null);
            throw failure;
        }
    }

}
