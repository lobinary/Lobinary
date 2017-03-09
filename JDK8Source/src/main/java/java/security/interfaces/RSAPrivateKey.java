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
 * The interface to an RSA private key.
 *
 * <p>
 *  到RSA私钥的接口。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see RSAPrivateCrtKey
 */

public interface RSAPrivateKey extends java.security.PrivateKey, RSAKey
{

    /**
     * The type fingerprint that is set to indicate
     * serialization compatibility with a previous
     * version of the type.
     * <p>
     *  类型指纹,设置为指示与先前版本的类型的序列化兼容性。
     * 
     */
    static final long serialVersionUID = 5187144804936595022L;

    /**
     * Returns the private exponent.
     *
     * <p>
     *  返回私有指数。
     * 
     * @return the private exponent
     */
    public BigInteger getPrivateExponent();
}
