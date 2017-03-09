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

import java.util.*;

import java.security.Provider.Service;
import java.security.spec.KeySpec;
import java.security.spec.InvalidKeySpecException;

import sun.security.util.Debug;
import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * Key factories are used to convert <I>keys</I> (opaque
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
 * <P> The following is an example of how to use a key factory in order to
 * instantiate a DSA public key from its encoding.
 * Assume Alice has received a digital signature from Bob.
 * Bob also sent her his public key (in encoded format) to verify
 * his signature. Alice then performs the following actions:
 *
 * <pre>
 * X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(bobEncodedPubKey);
 * KeyFactory keyFactory = KeyFactory.getInstance("DSA");
 * PublicKey bobPubKey = keyFactory.generatePublic(bobPubKeySpec);
 * Signature sig = Signature.getInstance("DSA");
 * sig.initVerify(bobPubKey);
 * sig.update(data);
 * sig.verify(signature);
 * </pre>
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code KeyFactory} algorithms:
 * <ul>
 * <li>{@code DiffieHellman}</li>
 * <li>{@code DSA}</li>
 * <li>{@code RSA}</li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyFactory">
 * KeyFactory section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 *
 * <p>
 *  关键工厂用于将<I>密钥</I>({@code Key}类型的不透明加密密钥)转换为<I>密钥规范</I>(底层密钥材料的透明表示),反之亦然。
 * 
 *  主要工厂是双向的。也就是说,它们允许您从给定的键规范(键材料)构建不透明的键对象,或者以合适的格式检索键对象的基础键材料。
 * 
 *  <P>对于同一个键,可能存在多个兼容的键规范。例如,可以使用{@code DSAPublicKeySpec}或{@code X509EncodedKeySpec}来指定DSA公钥。
 * 关键工厂可用于在兼容的关键规格之间转换。
 * 
 *  <P>以下是如何使用密钥工厂以从其编码实例化DSA公钥的示例。假设Alice已经从Bob接收到数字签名。 Bob还向她发送了他的公钥(以编码格式)以验证他的签名。然后Alice执行以下操作：
 * 
 * <pre>
 *  X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(bobEncodedPubKey); KeyFactory keyFactory =
 *  KeyFactory.getInstance("DSA"); PublicKey bobPubKey = keyFactory.generatePublic(bobPubKeySpec);签名sig 
 * = Signature.getInstance("DSA"); sig.initVerify(bobPubKey); sig.update(data); sig.verify(signature);。
 * </pre>
 * 
 * <p>每个Java平台的实现都需要支持以下标准{@code KeyFactory}算法：
 * <ul>
 *  <li> {@ code DiffieHellman} </li> <li> {@ code DSA} </li> <li> {@ code RSA} </li>
 * </ul>
 *  这些算法在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyFactory">
 *  Java密码体系结构标准算法名称文档的KeyFactory部分</a>。有关实现的信息,请参阅发行文档,以了解是否支持任何其他算法。
 * 
 * 
 * @author Jan Luehe
 *
 * @see Key
 * @see PublicKey
 * @see PrivateKey
 * @see java.security.spec.KeySpec
 * @see java.security.spec.DSAPublicKeySpec
 * @see java.security.spec.X509EncodedKeySpec
 *
 * @since 1.2
 */

public class KeyFactory {

    private static final Debug debug =
                        Debug.getInstance("jca", "KeyFactory");

    // The algorithm associated with this key factory
    private final String algorithm;

    // The provider
    private Provider provider;

    // The provider implementation (delegate)
    private volatile KeyFactorySpi spi;

    // lock for mutex during provider selection
    private final Object lock = new Object();

    // remaining services to try in provider selection
    // null once provider is selected
    private Iterator<Service> serviceIterator;

    /**
     * Creates a KeyFactory object.
     *
     * <p>
     *  创建KeyFactory对象。
     * 
     * 
     * @param keyFacSpi the delegate
     * @param provider the provider
     * @param algorithm the name of the algorithm
     * to associate with this {@code KeyFactory}
     */
    protected KeyFactory(KeyFactorySpi keyFacSpi, Provider provider,
                         String algorithm) {
        this.spi = keyFacSpi;
        this.provider = provider;
        this.algorithm = algorithm;
    }

    private KeyFactory(String algorithm) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        List<Service> list = GetInstance.getServices("KeyFactory", algorithm);
        serviceIterator = list.iterator();
        // fetch and instantiate initial spi
        if (nextSpi(null) == null) {
            throw new NoSuchAlgorithmException
                (algorithm + " KeyFactory not available");
        }
    }

    /**
     * Returns a KeyFactory object that converts
     * public/private keys of the specified algorithm.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new KeyFactory object encapsulating the
     * KeyFactorySpi implementation from the first
     * Provider that supports the specified algorithm is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回一个KeyFactory对象,用于转换指定算法的公钥/私钥。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。将返回一个新的KeyFactory对象,该对象封装了来自支持指定算法的第一个Provider的KeyFactorySpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested key algorithm.
     * See the KeyFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @return the new KeyFactory object.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          KeyFactorySpi implementation for the
     *          specified algorithm.
     *
     * @see Provider
     */
    public static KeyFactory getInstance(String algorithm)
            throws NoSuchAlgorithmException {
        return new KeyFactory(algorithm);
    }

    /**
     * Returns a KeyFactory object that converts
     * public/private keys of the specified algorithm.
     *
     * <p> A new KeyFactory object encapsulating the
     * KeyFactorySpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回一个KeyFactory对象,用于转换指定算法的公钥/私钥。
     * 
     *  <p>返回一个新的KeyFactory对象,用于封装来自指定提供者的KeyFactorySpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the name of the requested key algorithm.
     * See the KeyFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the name of the provider.
     *
     * @return the new KeyFactory object.
     *
     * @exception NoSuchAlgorithmException if a KeyFactorySpi
     *          implementation for the specified algorithm is not
     *          available from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the provider name is null
     *          or empty.
     *
     * @see Provider
     */
    public static KeyFactory getInstance(String algorithm, String provider)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Instance instance = GetInstance.getInstance("KeyFactory",
            KeyFactorySpi.class, algorithm, provider);
        return new KeyFactory((KeyFactorySpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns a KeyFactory object that converts
     * public/private keys of the specified algorithm.
     *
     * <p> A new KeyFactory object encapsulating the
     * KeyFactorySpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回一个KeyFactory对象,用于转换指定算法的公钥/私钥。
     * 
     * <p>返回一个新的KeyFactory对象,该对象封装来自指定的Provider对象的KeyFactorySpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the name of the requested key algorithm.
     * See the KeyFactory section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#KeyFactory">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard algorithm names.
     *
     * @param provider the provider.
     *
     * @return the new KeyFactory object.
     *
     * @exception NoSuchAlgorithmException if a KeyFactorySpi
     *          implementation for the specified algorithm is not available
     *          from the specified Provider object.
     *
     * @exception IllegalArgumentException if the specified provider is null.
     *
     * @see Provider
     *
     * @since 1.4
     */
    public static KeyFactory getInstance(String algorithm, Provider provider)
            throws NoSuchAlgorithmException {
        Instance instance = GetInstance.getInstance("KeyFactory",
            KeyFactorySpi.class, algorithm, provider);
        return new KeyFactory((KeyFactorySpi)instance.impl,
            instance.provider, algorithm);
    }

    /**
     * Returns the provider of this key factory object.
     *
     * <p>
     *  返回此关键工厂对象的提供程序。
     * 
     * 
     * @return the provider of this key factory object
     */
    public final Provider getProvider() {
        synchronized (lock) {
            // disable further failover after this call
            serviceIterator = null;
            return provider;
        }
    }

    /**
     * Gets the name of the algorithm
     * associated with this {@code KeyFactory}.
     *
     * <p>
     *  获取与此{@code KeyFactory}相关联的算法的名称。
     * 
     * 
     * @return the name of the algorithm associated with this
     * {@code KeyFactory}
     */
    public final String getAlgorithm() {
        return this.algorithm;
    }

    /**
     * Update the active KeyFactorySpi of this class and return the next
     * implementation for failover. If no more implemenations are
     * available, this method returns null. However, the active spi of
     * this class is never set to null.
     * <p>
     *  更新此类的活动KeyFactorySpi,并返回故障转移的下一个实现。如果没有更多的实现可用,此方法返回null。但是,此类的活动spi从不设置为null。
     * 
     */
    private KeyFactorySpi nextSpi(KeyFactorySpi oldSpi) {
        synchronized (lock) {
            // somebody else did a failover concurrently
            // try that spi now
            if ((oldSpi != null) && (oldSpi != spi)) {
                return spi;
            }
            if (serviceIterator == null) {
                return null;
            }
            while (serviceIterator.hasNext()) {
                Service s = serviceIterator.next();
                try {
                    Object obj = s.newInstance(null);
                    if (obj instanceof KeyFactorySpi == false) {
                        continue;
                    }
                    KeyFactorySpi spi = (KeyFactorySpi)obj;
                    provider = s.getProvider();
                    this.spi = spi;
                    return spi;
                } catch (NoSuchAlgorithmException e) {
                    // ignore
                }
            }
            serviceIterator = null;
            return null;
        }
    }

    /**
     * Generates a public key object from the provided key specification
     * (key material).
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
    public final PublicKey generatePublic(KeySpec keySpec)
            throws InvalidKeySpecException {
        if (serviceIterator == null) {
            return spi.engineGeneratePublic(keySpec);
        }
        Exception failure = null;
        KeyFactorySpi mySpi = spi;
        do {
            try {
                return mySpi.engineGeneratePublic(keySpec);
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                }
                mySpi = nextSpi(mySpi);
            }
        } while (mySpi != null);
        if (failure instanceof RuntimeException) {
            throw (RuntimeException)failure;
        }
        if (failure instanceof InvalidKeySpecException) {
            throw (InvalidKeySpecException)failure;
        }
        throw new InvalidKeySpecException
                ("Could not generate public key", failure);
    }

    /**
     * Generates a private key object from the provided key specification
     * (key material).
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
    public final PrivateKey generatePrivate(KeySpec keySpec)
            throws InvalidKeySpecException {
        if (serviceIterator == null) {
            return spi.engineGeneratePrivate(keySpec);
        }
        Exception failure = null;
        KeyFactorySpi mySpi = spi;
        do {
            try {
                return mySpi.engineGeneratePrivate(keySpec);
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                }
                mySpi = nextSpi(mySpi);
            }
        } while (mySpi != null);
        if (failure instanceof RuntimeException) {
            throw (RuntimeException)failure;
        }
        if (failure instanceof InvalidKeySpecException) {
            throw (InvalidKeySpecException)failure;
        }
        throw new InvalidKeySpecException
                ("Could not generate private key", failure);
    }

    /**
     * Returns a specification (key material) of the given key object.
     * {@code keySpec} identifies the specification class in which
     * the key material should be returned. It could, for example, be
     * {@code DSAPublicKeySpec.class}, to indicate that the
     * key material should be returned in an instance of the
     * {@code DSAPublicKeySpec} class.
     *
     * <p>
     *  返回给定键对象的规范(键材料)。 {@code keySpec}标识应当返回密钥材料的规范类。
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
     *
     * @exception InvalidKeySpecException if the requested key specification is
     * inappropriate for the given key, or the given key cannot be processed
     * (e.g., the given key has an unrecognized algorithm or format).
     */
    public final <T extends KeySpec> T getKeySpec(Key key, Class<T> keySpec)
            throws InvalidKeySpecException {
        if (serviceIterator == null) {
            return spi.engineGetKeySpec(key, keySpec);
        }
        Exception failure = null;
        KeyFactorySpi mySpi = spi;
        do {
            try {
                return mySpi.engineGetKeySpec(key, keySpec);
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                }
                mySpi = nextSpi(mySpi);
            }
        } while (mySpi != null);
        if (failure instanceof RuntimeException) {
            throw (RuntimeException)failure;
        }
        if (failure instanceof InvalidKeySpecException) {
            throw (InvalidKeySpecException)failure;
        }
        throw new InvalidKeySpecException
                ("Could not get key spec", failure);
    }

    /**
     * Translates a key object, whose provider may be unknown or potentially
     * untrusted, into a corresponding key object of this key factory.
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
    public final Key translateKey(Key key) throws InvalidKeyException {
        if (serviceIterator == null) {
            return spi.engineTranslateKey(key);
        }
        Exception failure = null;
        KeyFactorySpi mySpi = spi;
        do {
            try {
                return mySpi.engineTranslateKey(key);
            } catch (Exception e) {
                if (failure == null) {
                    failure = e;
                }
                mySpi = nextSpi(mySpi);
            }
        } while (mySpi != null);
        if (failure instanceof RuntimeException) {
            throw (RuntimeException)failure;
        }
        if (failure instanceof InvalidKeyException) {
            throw (InvalidKeyException)failure;
        }
        throw new InvalidKeyException
                ("Could not translate key", failure);
    }

}
