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

package java.security;

/**
 * <p>A public key. This interface contains no methods or constants.
 * It merely serves to group (and provide type safety for) all public key
 * interfaces.
 *
 * Note: The specialized public key interfaces extend this interface.
 * See, for example, the DSAPublicKey interface in
 * {@code java.security.interfaces}.
 *
 * <p>
 *  <p>公开金钥。此接口不包含方法或常量。它仅用于对所有公钥接口进行分组(并为其提供类型安全性)。
 * 
 *  注意：专用公钥接口扩展此接口。例如,参见{@code java.security.interfaces}中的DSAPublicKey接口。
 * 
 * 
 * @see Key
 * @see PrivateKey
 * @see Certificate
 * @see Signature#initVerify
 * @see java.security.interfaces.DSAPublicKey
 * @see java.security.interfaces.RSAPublicKey
 *
 */

public interface PublicKey extends Key {
    // Declare serialVersionUID to be compatible with JDK1.1
    /**
     * The class fingerprint that is set to indicate serialization
     * compatibility with a previous version of the class.
     * <p>
     */
    static final long serialVersionUID = 7187392471159151072L;
}
