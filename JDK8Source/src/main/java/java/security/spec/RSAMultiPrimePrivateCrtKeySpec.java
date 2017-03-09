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

package java.security.spec;

import java.math.BigInteger;

/**
 * This class specifies an RSA multi-prime private key, as defined in the
 * PKCS#1 v2.1, using the Chinese Remainder Theorem (CRT) information
 * values for efficiency.
 *
 * <p>
 *  该类指定了PKCS#1 v2.1中定义的RSA多主密钥,使用中国剩余定理(CRT)信息值来提高效率。
 * 
 * 
 * @author Valerie Peng
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see PKCS8EncodedKeySpec
 * @see RSAPrivateKeySpec
 * @see RSAPublicKeySpec
 * @see RSAOtherPrimeInfo
 *
 * @since 1.4
 */

public class RSAMultiPrimePrivateCrtKeySpec extends RSAPrivateKeySpec {

    private final BigInteger publicExponent;
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger primeExponentP;
    private final BigInteger primeExponentQ;
    private final BigInteger crtCoefficient;
    private final RSAOtherPrimeInfo otherPrimeInfo[];

   /**
    * Creates a new {@code RSAMultiPrimePrivateCrtKeySpec}
    * given the modulus, publicExponent, privateExponent,
    * primeP, primeQ, primeExponentP, primeExponentQ,
    * crtCoefficient, and otherPrimeInfo as defined in PKCS#1 v2.1.
    *
    * <p>Note that the contents of {@code otherPrimeInfo}
    * are copied to protect against subsequent modification when
    * constructing this object.
    *
    * <p>
    *  创建一个新的{@code RSAMultiPrimePrivateCrtKeySpec}给定在PKCS#1 v2.1中定义的模数,publicExponent,privateExponent,prim
    * eP,primeQ,primeExponentP,primeExponentQ,crtCoefficient和otherPrimeInfo。
    * 
    *  <p>请注意,复制{@code otherPrimeInfo}的内容以防止在构建此对象时进行后续修改。
    * 
    * 
    * @param modulus the modulus n.
    * @param publicExponent the public exponent e.
    * @param privateExponent the private exponent d.
    * @param primeP the prime factor p of n.
    * @param primeQ the prime factor q of n.
    * @param primeExponentP this is d mod (p-1).
    * @param primeExponentQ this is d mod (q-1).
    * @param crtCoefficient the Chinese Remainder Theorem
    * coefficient q-1 mod p.
    * @param otherPrimeInfo triplets of the rest of primes, null can be
    * specified if there are only two prime factors (p and q).
    * @exception NullPointerException if any of the parameters, i.e.
    * {@code modulus},
    * {@code publicExponent}, {@code privateExponent},
    * {@code primeP}, {@code primeQ},
    * {@code primeExponentP}, {@code primeExponentQ},
    * {@code crtCoefficient}, is null.
    * @exception IllegalArgumentException if an empty, i.e. 0-length,
    * {@code otherPrimeInfo} is specified.
    */
    public RSAMultiPrimePrivateCrtKeySpec(BigInteger modulus,
                                BigInteger publicExponent,
                                BigInteger privateExponent,
                                BigInteger primeP,
                                BigInteger primeQ,
                                BigInteger primeExponentP,
                                BigInteger primeExponentQ,
                                BigInteger crtCoefficient,
                                RSAOtherPrimeInfo[] otherPrimeInfo) {
        super(modulus, privateExponent);
        if (modulus == null) {
            throw new NullPointerException("the modulus parameter must be " +
                                            "non-null");
        }
        if (publicExponent == null) {
            throw new NullPointerException("the publicExponent parameter " +
                                            "must be non-null");
        }
        if (privateExponent == null) {
            throw new NullPointerException("the privateExponent parameter " +
                                            "must be non-null");
        }
        if (primeP == null) {
            throw new NullPointerException("the primeP parameter " +
                                            "must be non-null");
        }
        if (primeQ == null) {
            throw new NullPointerException("the primeQ parameter " +
                                            "must be non-null");
        }
        if (primeExponentP == null) {
            throw new NullPointerException("the primeExponentP parameter " +
                                            "must be non-null");
        }
        if (primeExponentQ == null) {
            throw new NullPointerException("the primeExponentQ parameter " +
                                            "must be non-null");
        }
        if (crtCoefficient == null) {
            throw new NullPointerException("the crtCoefficient parameter " +
                                            "must be non-null");
        }
        this.publicExponent = publicExponent;
        this.primeP = primeP;
        this.primeQ = primeQ;
        this.primeExponentP = primeExponentP;
        this.primeExponentQ = primeExponentQ;
        this.crtCoefficient = crtCoefficient;
        if (otherPrimeInfo == null)  {
            this.otherPrimeInfo = null;
        } else if (otherPrimeInfo.length == 0) {
            throw new IllegalArgumentException("the otherPrimeInfo " +
                                                "parameter must not be empty");
        } else {
            this.otherPrimeInfo = otherPrimeInfo.clone();
        }
    }

    /**
     * Returns the public exponent.
     *
     * <p>
     *  返回公共指数。
     * 
     * 
     * @return the public exponent.
     */
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    /**
     * Returns the primeP.
     *
     * <p>
     *  返回primeP。
     * 
     * 
     * @return the primeP.
     */
    public BigInteger getPrimeP() {
        return this.primeP;
    }

    /**
     * Returns the primeQ.
     *
     * <p>
     *  返回primeQ。
     * 
     * 
     * @return the primeQ.
     */
    public BigInteger getPrimeQ() {
        return this.primeQ;
    }

    /**
     * Returns the primeExponentP.
     *
     * <p>
     *  返回primeExponentP。
     * 
     * 
     * @return the primeExponentP.
     */
    public BigInteger getPrimeExponentP() {
        return this.primeExponentP;
    }

    /**
     * Returns the primeExponentQ.
     *
     * <p>
     *  返回primeExponentQ。
     * 
     * 
     * @return the primeExponentQ.
     */
    public BigInteger getPrimeExponentQ() {
        return this.primeExponentQ;
    }

    /**
     * Returns the crtCoefficient.
     *
     * <p>
     *  返回crtCoefficient。
     * 
     * 
     * @return the crtCoefficient.
     */
    public BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }

    /**
     * Returns a copy of the otherPrimeInfo or null if there are
     * only two prime factors (p and q).
     *
     * <p>
     *  返回otherPrimeInfo的副本,如果只有两个素因子(p和q),则返回null。
     * 
     * @return the otherPrimeInfo. Returns a new array each
     * time this method is called.
     */
    public RSAOtherPrimeInfo[] getOtherPrimeInfo() {
        if (otherPrimeInfo == null) return null;
        return otherPrimeInfo.clone();
    }
}
