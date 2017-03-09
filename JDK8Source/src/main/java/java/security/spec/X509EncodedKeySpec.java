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
 * This class represents the ASN.1 encoding of a public key,
 * encoded according to the ASN.1 type {@code SubjectPublicKeyInfo}.
 * The {@code SubjectPublicKeyInfo} syntax is defined in the X.509
 * standard as follows:
 *
 * <pre>
 * SubjectPublicKeyInfo ::= SEQUENCE {
 *   algorithm AlgorithmIdentifier,
 *   subjectPublicKey BIT STRING }
 * </pre>
 *
 * <p>
 *  此类表示根据ASN.1类型{@code SubjectPublicKeyInfo}进行编码的公钥的ASN.1编码。
 *  {@code SubjectPublicKeyInfo}语法在X.509标准中定义如下：。
 * 
 * <pre>
 *  SubjectPublicKeyInfo :: = SEQUENCE {algorithm AlgorithmIdentifier,subjectPublicKey BIT STRING}
 * </pre>
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see java.security.Key
 * @see java.security.KeyFactory
 * @see KeySpec
 * @see EncodedKeySpec
 * @see PKCS8EncodedKeySpec
 *
 * @since 1.2
 */

public class X509EncodedKeySpec extends EncodedKeySpec {

    /**
     * Creates a new X509EncodedKeySpec with the given encoded key.
     *
     * <p>
     *  使用给定的编码键创建新的X509EncodedKeySpec。
     * 
     * 
     * @param encodedKey the key, which is assumed to be
     * encoded according to the X.509 standard. The contents of the
     * array are copied to protect against subsequent modification.
     * @exception NullPointerException if {@code encodedKey}
     * is null.
     */
    public X509EncodedKeySpec(byte[] encodedKey) {
        super(encodedKey);
    }

    /**
     * Returns the key bytes, encoded according to the X.509 standard.
     *
     * <p>
     *  返回根据X.509标准编码的关键字节。
     * 
     * 
     * @return the X.509 encoding of the key. Returns a new array
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
     *  返回与此键规范关联的编码格式的名称。
     * 
     * @return the string {@code "X.509"}.
     */
    public final String getFormat() {
        return "X.509";
    }
}
