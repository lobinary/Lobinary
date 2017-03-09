/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.security.spec.RSAOtherPrimeInfo;

/**
 * The interface to an RSA multi-prime private key, as defined in the
 * PKCS#1 v2.1, using the <i>Chinese Remainder Theorem</i>
 * (CRT) information values.
 *
 * <p>
 *  使用<i>中国剩余定理</i>(CRT)信息值,到PKCS#1 v2.1中定义的RSA多主密钥的接口。
 * 
 * 
 * @author Valerie Peng
 *
 *
 * @see java.security.spec.RSAPrivateKeySpec
 * @see java.security.spec.RSAMultiPrimePrivateCrtKeySpec
 * @see RSAPrivateKey
 * @see RSAPrivateCrtKey
 *
 * @since 1.4
 */

public interface RSAMultiPrimePrivateCrtKey extends RSAPrivateKey {

    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     * <p>
     *  类型指纹,设置为指示与先前版本的类型的序列化兼容性。
     * 
     */
    static final long serialVersionUID = 618058533534628008L;

    /**
     * Returns the public exponent.
     *
     * <p>
     *  返回公共指数。
     * 
     * 
     * @return the public exponent.
     */
    public BigInteger getPublicExponent();

    /**
     * Returns the primeP.
     *
     * <p>
     *  返回primeP。
     * 
     * 
     * @return the primeP.
     */
    public BigInteger getPrimeP();

    /**
     * Returns the primeQ.
     *
     * <p>
     *  返回primeQ。
     * 
     * 
     * @return the primeQ.
     */
    public BigInteger getPrimeQ();

    /**
     * Returns the primeExponentP.
     *
     * <p>
     *  返回primeExponentP。
     * 
     * 
     * @return the primeExponentP.
     */
    public BigInteger getPrimeExponentP();

    /**
     * Returns the primeExponentQ.
     *
     * <p>
     *  返回primeExponentQ。
     * 
     * 
     * @return the primeExponentQ.
     */
    public BigInteger getPrimeExponentQ();

    /**
     * Returns the crtCoefficient.
     *
     * <p>
     *  返回crtCoefficient。
     * 
     * 
     * @return the crtCoefficient.
     */
    public BigInteger getCrtCoefficient();

    /**
     * Returns the otherPrimeInfo or null if there are only
     * two prime factors (p and q).
     *
     * <p>
     *  返回otherPrimeInfo或null,如果只有两个素因子(p和q)。
     * 
     * @return the otherPrimeInfo.
     */
    public RSAOtherPrimeInfo[] getOtherPrimeInfo();
}
