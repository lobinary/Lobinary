/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class represents a public or private key in encoded format.
 *
 * <p>
 *  此类表示编码格式的公钥或私钥。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see X509EncodedKeySpec
 * @see PKCS8EncodedKeySpec
 *
 * @since 1.2
 */

public abstract class EncodedKeySpec implements KeySpec {

    private byte[] encodedKey;

    /**
     * Creates a new EncodedKeySpec with the given encoded key.
     *
     * <p>
     *  使用给定的编码密钥创建新的EncodedKeySpec。
     * 
     * 
     * @param encodedKey the encoded key. The contents of the
     * array are copied to protect against subsequent modification.
     * @exception NullPointerException if {@code encodedKey}
     * is null.
     */
    public EncodedKeySpec(byte[] encodedKey) {
        this.encodedKey = encodedKey.clone();
    }

    /**
     * Returns the encoded key.
     *
     * <p>
     *  返回编码的键。
     * 
     * 
     * @return the encoded key. Returns a new array each time
     * this method is called.
     */
    public byte[] getEncoded() {
        return this.encodedKey.clone();
    }

    /**
     * Returns the name of the encoding format associated with this
     * key specification.
     *
     * <p>If the opaque representation of a key
     * (see {@link java.security.Key Key}) can be transformed
     * (see {@link java.security.KeyFactory KeyFactory})
     * into this key specification (or a subclass of it),
     * {@code getFormat} called
     * on the opaque key returns the same value as the
     * {@code getFormat} method
     * of this key specification.
     *
     * <p>
     *  返回与此键规范关联的编码格式的名称。
     * 
     *  <p>如果可以将键的不透明表示(参见{@link java.security.Key Key})(参见{@link java.security.KeyFactory KeyFactory})转换为此键
     * 
     * @return a string representation of the encoding format.
     */
    public abstract String getFormat();
}
