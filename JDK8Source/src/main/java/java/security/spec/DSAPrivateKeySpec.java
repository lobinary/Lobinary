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

package java.security.spec;

import java.math.BigInteger;

/**
 * This class specifies a DSA private key with its associated parameters.
 *
 * <p>
 *  此类指定具有其关联参数的DSA私钥。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see DSAPublicKeySpec
 * @see PKCS8EncodedKeySpec
 *
 * @since 1.2
 */

public class DSAPrivateKeySpec implements KeySpec {

    private BigInteger x;
    private BigInteger p;
    private BigInteger q;
    private BigInteger g;

    /**
     * Creates a new DSAPrivateKeySpec with the specified parameter values.
     *
     * <p>
     *  使用指定的参数值创建新的DSAPrivateKeySpec。
     * 
     * 
     * @param x the private key.
     *
     * @param p the prime.
     *
     * @param q the sub-prime.
     *
     * @param g the base.
     */
    public DSAPrivateKeySpec(BigInteger x, BigInteger p, BigInteger q,
                             BigInteger g) {
        this.x = x;
        this.p = p;
        this.q = q;
        this.g = g;
    }

    /**
     * Returns the private key {@code x}.
     *
     * <p>
     *  返回私钥{@code x}。
     * 
     * 
     * @return the private key {@code x}.
     */
    public BigInteger getX() {
        return this.x;
    }

    /**
     * Returns the prime {@code p}.
     *
     * <p>
     *  返回素数{@code p}。
     * 
     * 
     * @return the prime {@code p}.
     */
    public BigInteger getP() {
        return this.p;
    }

    /**
     * Returns the sub-prime {@code q}.
     *
     * <p>
     *  返回子素数{@code q}。
     * 
     * 
     * @return the sub-prime {@code q}.
     */
    public BigInteger getQ() {
        return this.q;
    }

    /**
     * Returns the base {@code g}.
     *
     * <p>
     *  返回基数{@code g}。
     * 
     * @return the base {@code g}.
     */
    public BigInteger getG() {
        return this.g;
    }
}
