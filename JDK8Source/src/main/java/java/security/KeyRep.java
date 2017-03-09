/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.util.Locale;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

/**
 * Standardized representation for serialized Key objects.
 *
 * <p>
 *
 * Note that a serialized Key may contain sensitive information
 * which should not be exposed in untrusted environments.  See the
 * <a href="../../../platform/serialization/spec/security.html">
 * Security Appendix</a>
 * of the Serialization Specification for more information.
 *
 * <p>
 *  序列化Key对象的标准化表示。
 * 
 * <p>
 * 
 *  请注意,序列化密钥可能包含不应在不受信任的环境中公开的敏感信息。看到
 * <a href="../../../platform/serialization/spec/security.html">
 *  有关详细信息,请参阅序列化规范的安全附录</a>。
 * 
 * 
 * @see Key
 * @see KeyFactory
 * @see javax.crypto.spec.SecretKeySpec
 * @see java.security.spec.X509EncodedKeySpec
 * @see java.security.spec.PKCS8EncodedKeySpec
 *
 * @since 1.5
 */

public class KeyRep implements Serializable {

    private static final long serialVersionUID = -4757683898830641853L;

    /**
     * Key type.
     *
     * <p>
     *  键类型。
     * 
     * 
     * @since 1.5
     */
    public static enum Type {

        /** Type for secret keys. */
        SECRET,

        /** Type for public keys. */
        PUBLIC,

        /** Type for private keys. */
        PRIVATE,

    }

    private static final String PKCS8 = "PKCS#8";
    private static final String X509 = "X.509";
    private static final String RAW = "RAW";

    /**
     * Either one of Type.SECRET, Type.PUBLIC, or Type.PRIVATE
     *
     * <p>
     *  Type.SECRET,Type.PUBLIC或Type.PRIVATE中的一个
     * 
     * 
     * @serial
     */
    private Type type;

    /**
     * The Key algorithm
     *
     * <p>
     *  密钥算法
     * 
     * 
     * @serial
     */
    private String algorithm;

    /**
     * The Key encoding format
     *
     * <p>
     *  密钥编码格式
     * 
     * 
     * @serial
     */
    private String format;

    /**
     * The encoded Key bytes
     *
     * <p>
     *  编码的Key字节
     * 
     * 
     * @serial
     */
    private byte[] encoded;

    /**
     * Construct the alternate Key class.
     *
     * <p>
     *
     * <p>
     *  构造备用Key类。
     * 
     * <p>
     * 
     * 
     * @param type either one of Type.SECRET, Type.PUBLIC, or Type.PRIVATE
     * @param algorithm the algorithm returned from
     *          {@code Key.getAlgorithm()}
     * @param format the encoding format returned from
     *          {@code Key.getFormat()}
     * @param encoded the encoded bytes returned from
     *          {@code Key.getEncoded()}
     *
     * @exception NullPointerException
     *          if type is {@code null},
     *          if algorithm is {@code null},
     *          if format is {@code null},
     *          or if encoded is {@code null}
     */
    public KeyRep(Type type, String algorithm,
                String format, byte[] encoded) {

        if (type == null || algorithm == null ||
            format == null || encoded == null) {
            throw new NullPointerException("invalid null input(s)");
        }

        this.type = type;
        this.algorithm = algorithm;
        this.format = format.toUpperCase(Locale.ENGLISH);
        this.encoded = encoded.clone();
    }

    /**
     * Resolve the Key object.
     *
     * <p> This method supports three Type/format combinations:
     * <ul>
     * <li> Type.SECRET/"RAW" - returns a SecretKeySpec object
     * constructed using encoded key bytes and algorithm
     * <li> Type.PUBLIC/"X.509" - gets a KeyFactory instance for
     * the key algorithm, constructs an X509EncodedKeySpec with the
     * encoded key bytes, and generates a public key from the spec
     * <li> Type.PRIVATE/"PKCS#8" - gets a KeyFactory instance for
     * the key algorithm, constructs a PKCS8EncodedKeySpec with the
     * encoded key bytes, and generates a private key from the spec
     * </ul>
     *
     * <p>
     *
     * <p>
     *  解析Key对象。
     * 
     *  <p>此方法支持三种类型/格式组合：
     * <ul>
     *  <li> Type.SECRET /"RAW" - 返回使用编码密钥字节和算法构造的SecretKeySpec对象<li> Type.PUBLIC /"X.509" - 获取密钥算法的KeyFacto
     * ry实例,构造一个带编码的X509EncodedKeySpec密钥字节,并从规范<li> Type.PRIVATE /"PKCS#8"生成公钥 - 获取密钥算法的KeyFactory实例,使用编码的密钥
     * 
     * @return the resolved Key object
     *
     * @exception ObjectStreamException if the Type/format
     *  combination is unrecognized, if the algorithm, key format, or
     *  encoded key bytes are unrecognized/invalid, of if the
     *  resolution of the key fails for any reason
     */
    protected Object readResolve() throws ObjectStreamException {
        try {
            if (type == Type.SECRET && RAW.equals(format)) {
                return new SecretKeySpec(encoded, algorithm);
            } else if (type == Type.PUBLIC && X509.equals(format)) {
                KeyFactory f = KeyFactory.getInstance(algorithm);
                return f.generatePublic(new X509EncodedKeySpec(encoded));
            } else if (type == Type.PRIVATE && PKCS8.equals(format)) {
                KeyFactory f = KeyFactory.getInstance(algorithm);
                return f.generatePrivate(new PKCS8EncodedKeySpec(encoded));
            } else {
                throw new NotSerializableException
                        ("unrecognized type/format combination: " +
                        type + "/" + format);
            }
        } catch (NotSerializableException nse) {
            throw nse;
        } catch (Exception e) {
            NotSerializableException nse = new NotSerializableException
                                        ("java.security.Key: " +
                                        "[" + type + "] " +
                                        "[" + algorithm + "] " +
                                        "[" + format + "]");
            nse.initCause(e);
            throw nse;
        }
    }
}
