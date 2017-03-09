/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
import java.security.PrivateKey;

/**
 * The interface to an elliptic curve (EC) private key.
 *
 * <p>
 *  椭圆曲线(EC)私钥的接口。
 * 
 * 
 * @author Valerie Peng
 *
 *
 * @see PrivateKey
 * @see ECKey
 *
 * @since 1.5
 */
public interface ECPrivateKey extends PrivateKey, ECKey {
   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility.
    * <p>
    *  设置为指示序列化兼容性的类指纹。
    * 
    */
    static final long serialVersionUID = -7896394956925609184L;

    /**
     * Returns the private value S.
     * <p>
     *  返回私有值S.
     * 
     * @return the private value S.
     */
    BigInteger getS();
}
