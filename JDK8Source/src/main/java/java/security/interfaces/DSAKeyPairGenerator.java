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

package java.security.interfaces;

import java.security.*;

/**
 * An interface to an object capable of generating DSA key pairs.
 *
 * <p>The {@code initialize} methods may each be called any number
 * of times. If no {@code initialize} method is called on a
 * DSAKeyPairGenerator, the default is to generate 1024-bit keys, using
 * precomputed p, q and g parameters and an instance of SecureRandom as
 * the random bit source.
 *
 * <p>Users wishing to indicate DSA-specific parameters, and to generate a key
 * pair suitable for use with the DSA algorithm typically
 *
 * <ol>
 *
 * <li>Get a key pair generator for the DSA algorithm by calling the
 * KeyPairGenerator {@code getInstance} method with "DSA"
 * as its argument.
 *
 * <li>Initialize the generator by casting the result to a DSAKeyPairGenerator
 * and calling one of the
 * {@code initialize} methods from this DSAKeyPairGenerator interface.
 *
 * <li>Generate a key pair by calling the {@code generateKeyPair}
 * method from the KeyPairGenerator class.
 *
 * </ol>
 *
 * <p>Note: it is not always necessary to do do algorithm-specific
 * initialization for a DSA key pair generator. That is, it is not always
 * necessary to call an {@code initialize} method in this interface.
 * Algorithm-independent initialization using the {@code initialize} method
 * in the KeyPairGenerator
 * interface is all that is needed when you accept defaults for algorithm-specific
 * parameters.
 *
 * <p>Note: Some earlier implementations of this interface may not support
 * larger sizes of DSA parameters such as 2048 and 3072-bit.
 *
 * <p>
 *  到能够生成DSA密钥对的对象的接口。
 * 
 *  <p> {@code initialize}方法可以每次调用任意次数。
 * 如果在DSAKeyPairGenerator上没有调用{@code initialize}方法,则默认使用预计算的p,q和g参数以及SecureRandom实例作为随机位源生成1024位密钥。
 * 
 *  <p>希望指示DSA特定参数并且生成适合于与DSA算法一起使用的密钥对的用户
 * 
 * <ol>
 * 
 *  <li>通过调用以"DSA"为参数的KeyPairGenerator {@code getInstance}方法,获取DSA算法的密钥对生成器。
 * 
 *  <li>通过将结果转换为DSAKeyPairGenerator并从此DSAKeyPairGenerator接口调用{@code initialize}方法之一来初始化生成器。
 * 
 *  <li>通过从KeyPairGenerator类调用{@code generateKeyPair}方法来生成密钥对。
 * 
 * </ol>
 * 
 * @see java.security.KeyPairGenerator
 */
public interface DSAKeyPairGenerator {

    /**
     * Initializes the key pair generator using the DSA family parameters
     * (p,q and g) and an optional SecureRandom bit source. If a
     * SecureRandom bit source is needed but not supplied, i.e. null, a
     * default SecureRandom instance will be used.
     *
     * <p>
     * 
     *  <p>注意：并不总是需要对DSA密钥对生成器进行特定于算法的初始化。也就是说,并不总是需要在此接口中调用{@code initialize}方法。
     * 当接受算法特定参数的默认值时,需要使用KeyPairGenerator接口中的{@code initialize}方法进行独立于算法的初始化。
     * 
     * <p>注意：此接口的某些早期实现可能不支持更大尺寸的DSA参数,例如2048和3072位。
     * 
     * 
     * @param params the parameters to use to generate the keys.
     *
     * @param random the random bit source to use to generate key bits;
     * can be null.
     *
     * @exception InvalidParameterException if the {@code params}
     * value is invalid, null, or unsupported.
     */
   public void initialize(DSAParams params, SecureRandom random)
   throws InvalidParameterException;

    /**
     * Initializes the key pair generator for a given modulus length
     * (instead of parameters), and an optional SecureRandom bit source.
     * If a SecureRandom bit source is needed but not supplied, i.e.
     * null, a default SecureRandom instance will be used.
     *
     * <p>If {@code genParams} is true, this method generates new
     * p, q and g parameters. If it is false, the method uses precomputed
     * parameters for the modulus length requested. If there are no
     * precomputed parameters for that modulus length, an exception will be
     * thrown. It is guaranteed that there will always be
     * default parameters for modulus lengths of 512 and 1024 bits.
     *
     * <p>
     *  使用DSA系列参数(p,q和g)和可选的SecureRandom位源初始化密钥对生成器。如果需要SecureRandom位源但未提供,即为null,则将使用默认的SecureRandom实例。
     * 
     * 
     * @param modlen the modulus length in bits. Valid values are any
     * multiple of 64 between 512 and 1024, inclusive, 2048, and 3072.
     *
     * @param random the random bit source to use to generate key bits;
     * can be null.
     *
     * @param genParams whether or not to generate new parameters for
     * the modulus length requested.
     *
     * @exception InvalidParameterException if {@code modlen} is
     * invalid, or unsupported, or if {@code genParams} is false and there
     * are no precomputed parameters for the requested modulus length.
     */
    public void initialize(int modlen, boolean genParams, SecureRandom random)
    throws InvalidParameterException;
}
