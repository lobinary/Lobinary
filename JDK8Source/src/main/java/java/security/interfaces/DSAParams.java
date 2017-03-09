/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.math.BigInteger;

/**
 * Interface to a DSA-specific set of key parameters, which defines a
 * DSA <em>key family</em>. DSA (Digital Signature Algorithm) is defined
 * in NIST's FIPS-186.
 *
 * <p>
 *  与DSA特定的一组关键参数的接口,用于定义DSA <em>键系列</em>。 DSA(数字签名算法)在NIST的FIPS-186中定义。
 * 
 * 
 * @see DSAKey
 * @see java.security.Key
 * @see java.security.Signature
 *
 * @author Benjamin Renaud
 * @author Josh Bloch
 */
public interface DSAParams {

    /**
     * Returns the prime, {@code p}.
     *
     * <p>
     *  返回素数{@code p}。
     * 
     * 
     * @return the prime, {@code p}.
     */
    public BigInteger getP();

    /**
     * Returns the subprime, {@code q}.
     *
     * <p>
     *  返回子代码{@code q}。
     * 
     * 
     * @return the subprime, {@code q}.
     */
    public BigInteger getQ();

    /**
     * Returns the base, {@code g}.
     *
     * <p>
     *  返回基数{@code g}。
     * 
     * @return the base, {@code g}.
     */
    public BigInteger getG();
}
