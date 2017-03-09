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
 * The interface to a DSA public key. DSA (Digital Signature Algorithm)
 * is defined in NIST's FIPS-186.
 *
 * <p>
 *  到DSA公钥的接口。 DSA(数字签名算法)在NIST的FIPS-186中定义。
 * 
 * 
 * @see java.security.Key
 * @see java.security.Signature
 * @see DSAKey
 * @see DSAPrivateKey
 *
 * @author Benjamin Renaud
 */
public interface DSAPublicKey extends DSAKey, java.security.PublicKey {

    // Declare serialVersionUID to be compatible with JDK1.1

   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility with a previous
    * version of the class.
    * <p>
    *  类指纹,设置为指示序列化与该类的先前版本的兼容性。
    * 
    */
    static final long serialVersionUID = 1234526332779022332L;

    /**
     * Returns the value of the public key, {@code y}.
     *
     * <p>
     *  返回公钥{@code y}的值。
     * 
     * @return the value of the public key, {@code y}.
     */
    public BigInteger getY();
}
