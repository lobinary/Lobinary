/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/**
 * This class specifies the set of parameters used to generate an RSA
 * key pair.
 *
 * <p>
 *  此类指定用于生成RSA密钥对的参数集。
 * 
 * 
 * @author Jan Luehe
 *
 * @see java.security.KeyPairGenerator#initialize(java.security.spec.AlgorithmParameterSpec)
 *
 * @since 1.3
 */

public class RSAKeyGenParameterSpec implements AlgorithmParameterSpec {

    private int keysize;
    private BigInteger publicExponent;

    /**
     * The public-exponent value F0 = 3.
     * <p>
     *  公共指数值F0 = 3。
     * 
     */
    public static final BigInteger F0 = BigInteger.valueOf(3);

    /**
     * The public exponent-value F4 = 65537.
     * <p>
     *  公共指数值F4 = 65537。
     * 
     */
    public static final BigInteger F4 = BigInteger.valueOf(65537);

    /**
     * Constructs a new {@code RSAParameterSpec} object from the
     * given keysize and public-exponent value.
     *
     * <p>
     *  从给定的keysize和public-exponent值构造一个新的{@code RSAParameterSpec}对象。
     * 
     * 
     * @param keysize the modulus size (specified in number of bits)
     * @param publicExponent the public exponent
     */
    public RSAKeyGenParameterSpec(int keysize, BigInteger publicExponent) {
        this.keysize = keysize;
        this.publicExponent = publicExponent;
    }

    /**
     * Returns the keysize.
     *
     * <p>
     *  返回keysize。
     * 
     * 
     * @return the keysize.
     */
    public int getKeysize() {
        return keysize;
    }

    /**
     * Returns the public-exponent value.
     *
     * <p>
     *  返回公共指数值。
     * 
     * @return the public-exponent value.
     */
    public BigInteger getPublicExponent() {
        return publicExponent;
    }
}
