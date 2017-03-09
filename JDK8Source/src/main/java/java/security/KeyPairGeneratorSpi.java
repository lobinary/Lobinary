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
 * <p> This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code KeyPairGenerator} class, which is used to generate
 * pairs of public and private keys.
 *
 * <p> All the abstract methods in this class must be implemented by each
 * cryptographic service provider who wishes to supply the implementation
 * of a key pair generator for a particular algorithm.
 *
 * <p> In case the client does not explicitly initialize the KeyPairGenerator
 * (via a call to an {@code initialize} method), each provider must
 * supply (and document) a default initialization.
 * For example, the <i>Sun</i> provider uses a default modulus size (keysize)
 * of 1024 bits.
 *
 * <p>
 *  <p>此类为{@code KeyPairGenerator}类定义了<i>服务提供程序接口</i>(<b> SPI </b>),用于生成公钥和私钥对。
 * 
 *  <p>该类中的所有抽象方法必须由希望为特定算法提供密钥对生成器的实现的每个加密服务提供者实现。
 * 
 *  <p>如果客户端没有显式初始化KeyPairGenerator(通过调用{@code initialize}方法),每个提供程序必须提供(并记录)默认初始化。
 * 例如,<i> Sun </i>提供程序使用1024位的默认模数大小(keysize)。
 * 
 * 
 * @author Benjamin Renaud
 *
 *
 * @see KeyPairGenerator
 * @see java.security.spec.AlgorithmParameterSpec
 */

public abstract class KeyPairGeneratorSpi {

    /**
     * Initializes the key pair generator for a certain keysize, using
     * the default parameter set.
     *
     * <p>
     *  使用默认参数集初始化某个键大小的键对生成器。
     * 
     * 
     * @param keysize the keysize. This is an
     * algorithm-specific metric, such as modulus length, specified in
     * number of bits.
     *
     * @param random the source of randomness for this generator.
     *
     * @exception InvalidParameterException if the {@code keysize} is not
     * supported by this KeyPairGeneratorSpi object.
     */
    public abstract void initialize(int keysize, SecureRandom random);

    /**
     * Initializes the key pair generator using the specified parameter
     * set and user-provided source of randomness.
     *
     * <p>This concrete method has been added to this previously-defined
     * abstract class. (For backwards compatibility, it cannot be abstract.)
     * It may be overridden by a provider to initialize the key pair
     * generator. Such an override
     * is expected to throw an InvalidAlgorithmParameterException if
     * a parameter is inappropriate for this key pair generator.
     * If this method is not overridden, it always throws an
     * UnsupportedOperationException.
     *
     * <p>
     *  使用指定的参数集和用户提供的随机源初始化密钥对生成器。
     * 
     *  <p>这个具体方法已添加到此前定义的抽象类中。 (为了向后兼容,它不能是抽象的。)提供者可以重写它来初始化密钥对生成器。
     * 如果参数对于此密钥对生成器不合适,则此类覆盖预期将抛出InvalidAlgorithmParameterException。
     * 如果此方法未被覆盖,它总是引发UnsupportedOperationException。
     * 
     * @param params the parameter set used to generate the keys.
     *
     * @param random the source of randomness for this generator.
     *
     * @exception InvalidAlgorithmParameterException if the given parameters
     * are inappropriate for this key pair generator.
     *
     * @since 1.2
     */
    public void initialize(AlgorithmParameterSpec params,
                           SecureRandom random)
        throws InvalidAlgorithmParameterException {
            throw new UnsupportedOperationException();
    }

    /**
     * Generates a key pair. Unless an initialization method is called
     * using a KeyPairGenerator interface, algorithm-specific defaults
     * will be used. This will generate a new key pair every time it
     * is called.
     *
     * <p>
     * 
     * 
     * @return the newly generated {@code KeyPair}
     */
    public abstract KeyPair generateKeyPair();
}
