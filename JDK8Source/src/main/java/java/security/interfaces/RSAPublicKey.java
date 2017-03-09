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
 * The interface to an RSA public key.
 *
 * <p>
 *  RSA公钥的接口。
 * 
 * 
 * @author Jan Luehe
 *
 */

public interface RSAPublicKey extends java.security.PublicKey, RSAKey
{
    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     * <p>
     *  类型指纹,设置为指示与先前版本的类型的序列化兼容性。
     * 
     */
    static final long serialVersionUID = -8727434096241101194L;

    /**
     * Returns the public exponent.
     *
     * <p>
     *  返回公共指数。
     * 
     * @return the public exponent
     */
    public BigInteger getPublicExponent();
}
