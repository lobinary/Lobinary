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

package java.security;

import java.security.spec.KeySpec;
import java.security.spec.InvalidKeySpecException;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code KeyFactory} class.
 * All the abstract methods in this class must be implemented by each
 * cryptographic service provider who wishes to supply the implementation
 * of a key factory for a particular algorithm.
 *
 * <P> Key factories are used to convert <I>keys</I> (opaque
 * cryptographic keys of type {@code Key}) into <I>key specifications</I>
 * (transparent representations of the underlying key material), and vice
 * versa.
 *
 * <P> Key factories are bi-directional. That is, they allow you to build an
 * opaque key object from a given key specification (key material), or to
 * retrieve the underlying key material of a key object in a suitable format.
 *
 * <P> Multiple compatible key specifications may exist for the same key.
 * For example, a DSA public key may be specified using
 * {@code DSAPublicKeySpec} or
 * {@code X509EncodedKeySpec}. A key factory can be used to translate
 * between compatible key specifications.
 *
 * <P> A provider should document all the key specifications supported by its
 * key factory.
 *
 * <p>
 *  此类为{@code KeyFactory}类定义了<i>服务提供程序接口</i>(<b> SPI </b>)。该类中的所有抽象方法必须由希望为特定算法提供关键工厂的实现的每个加密服务提供者实现。
 * 
 *  <P>关键工厂用于将<I>键</I>({@code Key}类型的不透明密码键)转换为<I>键规范</I>(底层键材料的透明表示)反之亦然。
 * 
 *  主要工厂是双向的。也就是说,它们允许您从给定的键规范(键材料)构建不透明的键对象,或者以合适的格式检索键对象的基础键材料。
 * 
 *  <P>对于同一个键,可能存在多个兼容的键规范。例如,可以使用{@code DSAPublicKeySpec}或{@code X509EncodedKeySpec}来指定DSA公钥。
 * 关键工厂可用于在兼容的关键规格之间转换。
 * 
 *  <P>提供者应记录其关键工厂支持的所有关键规格。
 * 
 * 
 * @author Jan Luehe
 *
 *
 * @see KeyFactory
 * @see Key
 * @see PublicKey
 * @see PrivateKey
 * @see java.security.spec.KeySpec
 * @see java.security.spec.DSAPublicKeySpec
 * @see java.security.spec.X509EncodedKeySpec
 *
 * @since 1.2
 */

public abstract class KeyFactorySpi {

    /**
     * Generates a public key object from the provided key
     * specification (key material).
     *
     * <p>
     *  根据提供的密钥规范(密钥材料)生成公钥对象。
     * 
     * 
     * @param keySpec the specification (key material) of the public key.
     *
     * @return the public key.
     *
     * @exception InvalidKeySpecException if the given key specification
     * is inappropriate for this key factory to produce a public key.
     */
    protected abstract PublicKey engineGeneratePublic(KeySpec keySpec)
        throws InvalidKeySpecException;

    /**
     * Generates a private key object from the provided key
     * specification (key material).
     *
     * <p>
     *  从提供的密钥规范(密钥材料)生成私钥对象。
     * 
     * 
     * @param keySpec the specification (key material) of the private key.
     *
     * @return the private key.
     *
     * @exception InvalidKeySpecException if the given key specification
     * is inappropriate for this key factory to produce a private key.
     */
    protected abstract PrivateKey engineGeneratePrivate(KeySpec keySpec)
        throws InvalidKeySpecException;

    /**
     * Returns a specification (key material) of the given key
     * object.
     * {@code keySpec} identifies the specification class in which
     * the key material should be returned. It could, for example, be
     * {@code DSAPublicKeySpec.class}, to indicate that the
     * key material should be returned in an instance of the
     * {@code DSAPublicKeySpec} class.
     *
     * <p>
     * 返回给定键对象的规范(键材料)。 {@code keySpec}标识应当返回密钥材料的规范类。
     * 例如,它可以是{@code DSAPublicKeySpec.class},以指示密钥材料应在{@code DSAPublicKeySpec}类的实例中返回。
     * 
     * 
     * @param <T> the type of the key specification to be returned
     *
     * @param key the key.
     *
     * @param keySpec the specification class in which
     * the key material should be returned.
     *
     * @return the underlying key specification (key material) in an instance
     * of the requested specification class.

     * @exception InvalidKeySpecException if the requested key specification is
     * inappropriate for the given key, or the given key cannot be dealt with
     * (e.g., the given key has an unrecognized format).
     */
    protected abstract <T extends KeySpec>
        T engineGetKeySpec(Key key, Class<T> keySpec)
        throws InvalidKeySpecException;

    /**
     * Translates a key object, whose provider may be unknown or
     * potentially untrusted, into a corresponding key object of this key
     * factory.
     *
     * <p>
     * 
     * @param key the key whose provider is unknown or untrusted.
     *
     * @return the translated key.
     *
     * @exception InvalidKeyException if the given key cannot be processed
     * by this key factory.
     */
    protected abstract Key engineTranslateKey(Key key)
        throws InvalidKeyException;

}
