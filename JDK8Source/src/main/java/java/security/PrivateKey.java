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
 * A private key.
 * The purpose of this interface is to group (and provide type safety
 * for) all private key interfaces.
 * <p>
 * Note: The specialized private key interfaces extend this interface.
 * See, for example, the {@code DSAPrivateKey} interface in
 * {@link java.security.interfaces}.
 * <p>
 * Implementations should override the default {@code destroy} and
 * {@code isDestroyed} methods from the
 * {@link javax.security.auth.Destroyable} interface to enable
 * sensitive key information to be destroyed, cleared, or in the case
 * where such information is immutable, unreferenced.
 * Finally, since {@code PrivateKey} is {@code Serializable}, implementations
 * should also override
 * {@link java.io.ObjectOutputStream#writeObject(java.lang.Object)}
 * to prevent keys that have been destroyed from being serialized.
 *
 * <p>
 *  私钥。此接口的目的是为所有私钥接口分组(并为其提供类型安全性)。
 * <p>
 *  注意：专用私钥接口扩展此接口。例如,请参阅{@link java.security.interfaces}中的{@code DSAPrivateKey}界面。
 * <p>
 *  实现应覆盖{@link javax.security.auth.Destroyable}界面中的默认{@code destroy}和{@code isDestroyed}方法,以使敏感的关键信息能够被
 * 销毁,清除,或者在这种信息不可变,未引用。
 * 
 * @see Key
 * @see PublicKey
 * @see Certificate
 * @see Signature#initVerify
 * @see java.security.interfaces.DSAPrivateKey
 * @see java.security.interfaces.RSAPrivateKey
 * @see java.security.interfaces.RSAPrivateCrtKey
 *
 * @author Benjamin Renaud
 * @author Josh Bloch
 */

public interface PrivateKey extends Key, javax.security.auth.Destroyable {

    // Declare serialVersionUID to be compatible with JDK1.1
    /**
     * The class fingerprint that is set to indicate serialization
     * compatibility with a previous version of the class.
     * <p>
     * 最后,由于{@code PrivateKey}是{@code Serializable},实现还应该重写{@link java.io.ObjectOutputStream#writeObject(java.lang.Object)}
     * ,以防止被破坏的密钥被序列化。
     * 
     */
    static final long serialVersionUID = 6034044314589513430L;
}
