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

package java.security.spec;

import java.math.BigInteger;

/**
 * This class specifies an RSA private key, as defined in the PKCS#1
 * standard, using the Chinese Remainder Theorem (CRT) information values for
 * efficiency.
 *
 * <p>
 *  该类指定了PKCS#1标准中定义的RSA私钥,使用中国剩余定理(CRT)信息值来提高效率。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see PKCS8EncodedKeySpec
 * @see RSAPrivateKeySpec
 * @see RSAPublicKeySpec
 */

public class RSAPrivateCrtKeySpec extends RSAPrivateKeySpec {

    private final BigInteger publicExponent;
    private final BigInteger primeP;
    private final BigInteger primeQ;
    private final BigInteger primeExponentP;
    private final BigInteger primeExponentQ;
    private final BigInteger crtCoefficient;



   /**
    * Creates a new {@code RSAPrivateCrtKeySpec}
    * given the modulus, publicExponent, privateExponent,
    * primeP, primeQ, primeExponentP, primeExponentQ, and
    * crtCoefficient as defined in PKCS#1.
    *
    * <p>
    *  创建一个新的{@code RSAPrivateCrtKeySpec}给定在PKCS#1中定义的模数,publicExponent,privateExponent,primeP,primeQ,prime
    * ExponentP,primeExponentQ和crtCoefficient。
    * 
    * 
    * @param modulus the modulus n
    * @param publicExponent the public exponent e
    * @param privateExponent the private exponent d
    * @param primeP the prime factor p of n
    * @param primeQ the prime factor q of n
    * @param primeExponentP this is d mod (p-1)
    * @param primeExponentQ this is d mod (q-1)
    * @param crtCoefficient the Chinese Remainder Theorem
    * coefficient q-1 mod p
    */
    public RSAPrivateCrtKeySpec(BigInteger modulus,
                                BigInteger publicExponent,
                                BigInteger privateExponent,
                                BigInteger primeP,
                                BigInteger primeQ,
                                BigInteger primeExponentP,
                                BigInteger primeExponentQ,
                                BigInteger crtCoefficient) {
        super(modulus, privateExponent);
        this.publicExponent = publicExponent;
        this.primeP = primeP;
        this.primeQ = primeQ;
        this.primeExponentP = primeExponentP;
        this.primeExponentQ = primeExponentQ;
        this.crtCoefficient = crtCoefficient;
    }

    /**
     * Returns the public exponent.
     *
     * <p>
     *  返回公共指数。
     * 
     * 
     * @return the public exponent
     */
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    /**
     * Returns the primeP.

     * <p>
     *  返回primeP。
     * 
     * 
     * @return the primeP
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
     * @return the primeQ
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
     * @return the primeExponentP
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
     * @return the primeExponentQ
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
     * @return the crtCoefficient
     */
    public BigInteger getCrtCoefficient() {
        return this.crtCoefficient;
    }
}
