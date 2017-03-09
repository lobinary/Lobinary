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
 * This class represents the ASN.1 encoding of a private key,
 * encoded according to the ASN.1 type {@code PrivateKeyInfo}.
 * The {@code PrivateKeyInfo} syntax is defined in the PKCS#8 standard
 * as follows:
 *
 * <pre>
 * PrivateKeyInfo ::= SEQUENCE {
 *   version Version,
 *   privateKeyAlgorithm PrivateKeyAlgorithmIdentifier,
 *   privateKey PrivateKey,
 *   attributes [0] IMPLICIT Attributes OPTIONAL }
 *
 * Version ::= INTEGER
 *
 * PrivateKeyAlgorithmIdentifier ::= AlgorithmIdentifier
 *
 * PrivateKey ::= OCTET STRING
 *
 * Attributes ::= SET OF Attribute
 * </pre>
 *
 * <p>
 *  此类表示根据ASN.1类型{@code PrivateKeyInfo}进行编码的私钥的ASN.1编码。 {@code PrivateKeyInfo}语法在PKCS#8标准中定义如下：
 * 
 * <pre>
 *  PrivateKeyInfo :: = SEQUENCE {version Version,privateKeyAlgorithm PrivateKeyAlgorithmIdentifier,privateKey PrivateKey,attributes [0] IMPLICIT Attributes OPTIONAL}
 * 。
 * 
 *  Version :: = INTEGER
 * 
 *  PrivateKeyAlgorithmIdentifier :: = AlgorithmIdentifier
 * 
 *  PrivateKey :: = OCTET STRING
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see EncodedKeySpec
 * @see X509EncodedKeySpec
 *
 * @since 1.2
 */

public class PKCS8EncodedKeySpec extends EncodedKeySpec {

    /**
     * Creates a new PKCS8EncodedKeySpec with the given encoded key.
     *
     * <p>
     *  Attributes :: = SET OF属性
     * </pre>
     * 
     * 
     * @param encodedKey the key, which is assumed to be
     * encoded according to the PKCS #8 standard. The contents of
     * the array are copied to protect against subsequent modification.
     * @exception NullPointerException if {@code encodedKey}
     * is null.
     */
    public PKCS8EncodedKeySpec(byte[] encodedKey) {
        super(encodedKey);
    }

    /**
     * Returns the key bytes, encoded according to the PKCS #8 standard.
     *
     * <p>
     *  使用给定的编码密钥创建新的PKCS8EncodedKeySpec。
     * 
     * 
     * @return the PKCS #8 encoding of the key. Returns a new array
     * each time this method is called.
     */
    public byte[] getEncoded() {
        return super.getEncoded();
    }

    /**
     * Returns the name of the encoding format associated with this
     * key specification.
     *
     * <p>
     *  返回根据PKCS#8标准编码的关键字节。
     * 
     * 
     * @return the string {@code "PKCS#8"}.
     */
    public final String getFormat() {
        return "PKCS#8";
    }
}
