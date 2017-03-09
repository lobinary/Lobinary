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
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code AlgorithmParameterGenerator} class, which
 * is used to generate a set of parameters to be used with a certain algorithm.
 *
 * <p> All the abstract methods in this class must be implemented by each
 * cryptographic service provider who wishes to supply the implementation
 * of a parameter generator for a particular algorithm.
 *
 * <p> In case the client does not explicitly initialize the
 * AlgorithmParameterGenerator (via a call to an {@code engineInit}
 * method), each provider must supply (and document) a default initialization.
 * For example, the Sun provider uses a default modulus prime size of 1024
 * bits for the generation of DSA parameters.
 *
 * <p>
 *  此类为{@code AlgorithmParameterGenerator}类定义了<i>服务提供程序接口</i>(<b> SPI </b>),用于生成要与某个算法一起使用的一组参数。
 * 
 *  <p>此类中的所有抽象方法必须由希望为特定算法提供参数生成器的实现的每个加密服务提供者实现。
 * 
 *  <p>如果客户端没有显式初始化AlgorithmParameterGenerator(通过调用{@code engineInit}方法),每个提供程序必须提供(并记录)默认初始化。
 * 例如,Sun提供程序使用1024位的默认模量素数大小来生成DSA参数。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see AlgorithmParameterGenerator
 * @see AlgorithmParameters
 * @see java.security.spec.AlgorithmParameterSpec
 *
 * @since 1.2
 */

public abstract class AlgorithmParameterGeneratorSpi {

    /**
     * Initializes this parameter generator for a certain size
     * and source of randomness.
     *
     * <p>
     *  初始化此参数生成器以获得特定大小和随机源。
     * 
     * 
     * @param size the size (number of bits).
     * @param random the source of randomness.
     */
    protected abstract void engineInit(int size, SecureRandom random);

    /**
     * Initializes this parameter generator with a set of
     * algorithm-specific parameter generation values.
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
    protected abstract void engineInit(AlgorithmParameterSpec genParamSpec,
                                       SecureRandom random)
        throws InvalidAlgorithmParameterException;

    /**
     * Generates the parameters.
     *
     * <p>
     *  生成参数。
     * 
     * @return the new AlgorithmParameters object.
     */
    protected abstract AlgorithmParameters engineGenerateParameters();
}
