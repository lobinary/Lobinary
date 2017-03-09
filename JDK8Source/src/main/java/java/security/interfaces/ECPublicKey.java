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

import java.security.PublicKey;
import java.security.spec.ECPoint;

/**
 * The interface to an elliptic curve (EC) public key.
 *
 * <p>
 *  椭圆曲线(EC)公钥的接口。
 * 
 * 
 * @author Valerie Peng
 *
 *
 * @see PublicKey
 * @see ECKey
 * @see java.security.spec.ECPoint
 *
 * @since 1.5
 */
public interface ECPublicKey extends PublicKey, ECKey {

   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility.
    * <p>
    *  设置为指示序列化兼容性的类指纹。
    * 
    */
    static final long serialVersionUID = -3314988629879632826L;

    /**
     * Returns the public point W.
     * <p>
     *  返回公共点W.
     * 
     * @return the public point W.
     */
    ECPoint getW();
}
