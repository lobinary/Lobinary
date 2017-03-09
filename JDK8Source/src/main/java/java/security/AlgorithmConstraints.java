/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Set;

/**
 * This interface specifies constraints for cryptographic algorithms,
 * keys (key sizes), and other algorithm parameters.
 * <p>
 * {@code AlgorithmConstraints} objects are immutable.  An implementation
 * of this interface should not provide methods that can change the state
 * of an instance once it has been created.
 * <p>
 * Note that {@code AlgorithmConstraints} can be used to represent the
 * restrictions described by the security properties
 * {@code jdk.certpath.disabledAlgorithms} and
 * {@code jdk.tls.disabledAlgorithms}, or could be used by a
 * concrete {@code PKIXCertPathChecker} to check whether a specified
 * certificate in the certification path contains the required algorithm
 * constraints.
 *
 * <p>
 *  此接口规定了加密算法,密钥(密钥大小)和其他算法参数的约束。
 * <p>
 *  {@code AlgorithmConstraints}对象是不可变的。此接口的实现不应提供可以在实例创建后更改实例状态的方法。
 * <p>
 *  请注意,{@code AlgorithmConstraints}可用于表示安全属性{@code jdk.certpath.disabledAlgorithms}和{@code jdk.tls.disabledAlgorithms}
 * 描述的限制,或者可以由具体的{@code PKIXCertPathChecker }以检查认证路径中的指定证书是否包含所需的算法约束。
 * 
 * 
 * @see javax.net.ssl.SSLParameters#getAlgorithmConstraints
 * @see javax.net.ssl.SSLParameters#setAlgorithmConstraints(AlgorithmConstraints)
 *
 * @since 1.7
 */

public interface AlgorithmConstraints {

    /**
     * Determines whether an algorithm is granted permission for the
     * specified cryptographic primitives.
     *
     * <p>
     *  确定是否为指定的加密原语授予了算法的权限。
     * 
     * 
     * @param primitives a set of cryptographic primitives
     * @param algorithm the algorithm name
     * @param parameters the algorithm parameters, or null if no additional
     *     parameters
     *
     * @return true if the algorithm is permitted and can be used for all
     *     of the specified cryptographic primitives
     *
     * @throws IllegalArgumentException if primitives or algorithm is null
     *     or empty
     */
    public boolean permits(Set<CryptoPrimitive> primitives,
            String algorithm, AlgorithmParameters parameters);

    /**
     * Determines whether a key is granted permission for the specified
     * cryptographic primitives.
     * <p>
     * This method is usually used to check key size and key usage.
     *
     * <p>
     *  确定是否为密钥授予了指定加密原语的权限。
     * <p>
     *  此方法通常用于检查密钥大小和密钥用法。
     * 
     * 
     * @param primitives a set of cryptographic primitives
     * @param key the key
     *
     * @return true if the key can be used for all of the specified
     *     cryptographic primitives
     *
     * @throws IllegalArgumentException if primitives is null or empty,
     *     or the key is null
     */
    public boolean permits(Set<CryptoPrimitive> primitives, Key key);

    /**
     * Determines whether an algorithm and the corresponding key are granted
     * permission for the specified cryptographic primitives.
     *
     * <p>
     * 
     * @param primitives a set of cryptographic primitives
     * @param algorithm the algorithm name
     * @param key the key
     * @param parameters the algorithm parameters, or null if no additional
     *     parameters
     *
     * @return true if the key and the algorithm can be used for all of the
     *     specified cryptographic primitives
     *
     * @throws IllegalArgumentException if primitives or algorithm is null
     *     or empty, or the key is null
     */
    public boolean permits(Set<CryptoPrimitive> primitives,
                String algorithm, Key key, AlgorithmParameters parameters);

}
