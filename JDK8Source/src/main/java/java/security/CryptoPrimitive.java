/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * An enumeration of cryptographic primitives.
 *
 * <p>
 *  加密原语的枚举。
 * 
 * 
 * @since 1.7
 */
public enum CryptoPrimitive {
    /**
     * Hash function
     * <p>
     *  散列函数
     * 
     */
    MESSAGE_DIGEST,

    /**
     * Cryptographic random number generator
     * <p>
     *  密码随机数生成器
     * 
     */
    SECURE_RANDOM,

    /**
     * Symmetric primitive: block cipher
     * <p>
     *  对称原语：块密码
     * 
     */
    BLOCK_CIPHER,

    /**
     * Symmetric primitive: stream cipher
     * <p>
     *  对称原语：流密码
     * 
     */
    STREAM_CIPHER,

    /**
     * Symmetric primitive: message authentication code
     * <p>
     *  对称原语：消息认证代码
     * 
     */
    MAC,

    /**
     * Symmetric primitive: key wrap
     * <p>
     *  对称原语：键包
     * 
     */
    KEY_WRAP,

    /**
     * Asymmetric primitive: public key encryption
     * <p>
     *  不对称原语：公钥加密
     * 
     */
    PUBLIC_KEY_ENCRYPTION,

    /**
     * Asymmetric primitive: signature scheme
     * <p>
     *  不对称原语：签名方案
     * 
     */
    SIGNATURE,

    /**
     * Asymmetric primitive: key encapsulation mechanism
     * <p>
     *  不对称原语：密钥封装机制
     * 
     */
    KEY_ENCAPSULATION,

    /**
     * Asymmetric primitive: key agreement and key distribution
     * <p>
     *  不对称原语：密钥协商和密钥分发
     */
    KEY_AGREEMENT
}
