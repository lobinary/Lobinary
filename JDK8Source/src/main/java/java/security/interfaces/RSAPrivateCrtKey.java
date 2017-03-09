/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The interface to an RSA private key, as defined in the PKCS#1 standard,
 * using the <i>Chinese Remainder Theorem</i> (CRT) information values.
 *
 * <p>
 *  与PKCS#1标准中定义的RSA私钥的接口使用<i>中国剩余定理</i>(CRT)信息值。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see RSAPrivateKey
 */

public interface RSAPrivateCrtKey extends RSAPrivateKey {

    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     * <p>
     *  类型指纹,设置为指示与先前版本的类型的序列化兼容性。
     * 
     */
    static final long serialVersionUID = -5682214253527700368L;

    /**
     * Returns the public exponent.
     *
     * <p>
     *  返回公共指数。
     * 
     * 
     * @return the public exponent
     */
    public BigInteger getPublicExponent();

    /**
     * Returns the primeP.

     * <p>
     *  返回primeP。
     * 
     * 
     * @return the primeP
     */
    public BigInteger getPrimeP();

    /**
     * Returns the primeQ.
     *
     * <p>
     *  返回primeQ。
     * 
     * 
     * @return the primeQ
     */
    public BigInteger getPrimeQ();

    /**
     * Returns the primeExponentP.
     *
     * <p>
     *  返回primeExponentP。
     * 
     * 
     * @return the primeExponentP
     */
    public BigInteger getPrimeExponentP();

    /**
     * Returns the primeExponentQ.
     *
     * <p>
     *  返回primeExponentQ。
     * 
     * 
     * @return the primeExponentQ
     */
    public BigInteger getPrimeExponentQ();

    /**
     * Returns the crtCoefficient.
     *
     * <p>
     *  返回crtCoefficient。
     * 
     * @return the crtCoefficient
     */
    public BigInteger getCrtCoefficient();
}
