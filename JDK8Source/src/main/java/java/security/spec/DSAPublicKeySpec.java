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
 * This class specifies a DSA public key with its associated parameters.
 *
 * <p>
 *  此类指定具有其关联参数的DSA公钥。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see DSAPrivateKeySpec
 * @see X509EncodedKeySpec
 *
 * @since 1.2
 */

public class DSAPublicKeySpec implements KeySpec {

    private BigInteger y;
    private BigInteger p;
    private BigInteger q;
    private BigInteger g;

    /**
     * Creates a new DSAPublicKeySpec with the specified parameter values.
     *
     * <p>
     *  使用指定的参数值创建新的DSAPublicKeySpec。
     * 
     * 
     * @param y the public key.
     *
     * @param p the prime.
     *
     * @param q the sub-prime.
     *
     * @param g the base.
     */
    public DSAPublicKeySpec(BigInteger y, BigInteger p, BigInteger q,
                            BigInteger g) {
        this.y = y;
        this.p = p;
        this.q = q;
        this.g = g;
    }

    /**
     * Returns the public key {@code y}.
     *
     * <p>
     *  返回公钥{@code y}。
     * 
     * 
     * @return the public key {@code y}.
     */
    public BigInteger getY() {
        return this.y;
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
