/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security.cert;

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.util.Collection;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * A class for retrieving {@code Certificate}s and {@code CRL}s
 * from a repository.
 * <p>
 * This class uses a provider-based architecture.
 * To create a {@code CertStore}, call one of the static
 * {@code getInstance} methods, passing in the type of
 * {@code CertStore} desired, any applicable initialization parameters
 * and optionally the name of the provider desired.
 * <p>
 * Once the {@code CertStore} has been created, it can be used to
 * retrieve {@code Certificate}s and {@code CRL}s by calling its
 * {@link #getCertificates(CertSelector selector) getCertificates} and
 * {@link #getCRLs(CRLSelector selector) getCRLs} methods.
 * <p>
 * Unlike a {@link java.security.KeyStore KeyStore}, which provides access
 * to a cache of private keys and trusted certificates, a
 * {@code CertStore} is designed to provide access to a potentially
 * vast repository of untrusted certificates and CRLs. For example, an LDAP
 * implementation of {@code CertStore} provides access to certificates
 * and CRLs stored in one or more directories using the LDAP protocol and the
 * schema as defined in the RFC service attribute.
 *
 * <p> Every implementation of the Java platform is required to support the
 * following standard {@code CertStore} type:
 * <ul>
 * <li>{@code Collection}</li>
 * </ul>
 * This type is described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertStore">
 * CertStore section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other types are supported.
 *
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * All public methods of {@code CertStore} objects must be thread-safe.
 * That is, multiple threads may concurrently invoke these methods on a
 * single {@code CertStore} object (or more than one) with no
 * ill effects. This allows a {@code CertPathBuilder} to search for a
 * CRL while simultaneously searching for further certificates, for instance.
 * <p>
 * The static methods of this class are also guaranteed to be thread-safe.
 * Multiple threads may concurrently invoke the static methods defined in
 * this class with no ill effects.
 *
 * <p>
 *  用于从存储库检索{@code Certificate}和{@code CRL}的类。
 * <p>
 *  此类使用基于提供程序的体系结构。
 * 要创建{@code CertStore},请调用其中一个静态{@code getInstance}方法,传入所需的{@code CertStore}类型,任何适用的初始化参数以及可选的所需提供程序的名称
 * 。
 *  此类使用基于提供程序的体系结构。
 * <p>
 *  {@code CertStore}创建完成后,您可以通过调用{@link #getCertificates(CertSelector selector)getCertificates}和{@link #getCRLs}
 * 来检索{@code Certificate}和{@code CRL} (CRLSelector selector)getCRLs}方法。
 * <p>
 *  与{@link java.security.KeyStore KeyStore}(提供对私钥和可信证书的缓存的访问)不同,{@code CertStore}旨在提供对潜在的大量不可信证书和CRL的存储
 * 库的访问。
 * 例如,{@code CertStore}的LDAP实现使用LDAP协议和RFC服务属性中定义的模式提供对存储在一个或多个目录中的证书和CRL的访问。
 * 
 *  <p>每个Java平台的实现都需要支持以下标准{@code CertStore}类型：
 * <ul>
 *  <li> {@ code Collection} </li>
 * </ul>
 *  此类型在<a href =中描述
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertStore">
 * Java Cryptography Architecture标准算法名称文档的CertStore部分</a>。请查看您的实现的发行版文档,看看是否支持任何其他类型。
 * 
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  {@code CertStore}对象的所有公共方法必须是线程安全的。也就是说,多个线程可以同时在单个{@code CertStore}对象(或多个对象)上调用这些方法,没有不良影响。
 * 这允许{@code CertPathBuilder}搜索CRL,同时例如搜索另外的证书。
 * <p>
 *  这个类的静态方法也保证是线程安全的。多线程可以同时调用这个类中定义的静态方法,没有不良影响。
 * 
 * 
 * @since       1.4
 * @author      Sean Mullan, Steve Hanna
 */
public class CertStore {
    /*
     * Constant to lookup in the Security properties file to determine
     * the default certstore type. In the Security properties file, the
     * default certstore type is given as:
     * <pre>
     * certstore.type=LDAP
     * </pre>
     * <p>
     *  在安全性属性文件中查找的常量以确定缺省certstore类型。在安全属性文件中,默认certstore类型为：
     * <pre>
     *  certstore.type = LDAP
     * </pre>
     */
    private static final String CERTSTORE_TYPE = "certstore.type";
    private CertStoreSpi storeSpi;
    private Provider provider;
    private String type;
    private CertStoreParameters params;

    /**
     * Creates a {@code CertStore} object of the given type, and
     * encapsulates the given provider implementation (SPI object) in it.
     *
     * <p>
     *  创建给定类型的{@code CertStore}对象,并将给定的提供程序实现(SPI对象)封装在其中。
     * 
     * 
     * @param storeSpi the provider implementation
     * @param provider the provider
     * @param type the type
     * @param params the initialization parameters (may be {@code null})
     */
    protected CertStore(CertStoreSpi storeSpi, Provider provider,
                        String type, CertStoreParameters params) {
        this.storeSpi = storeSpi;
        this.provider = provider;
        this.type = type;
        if (params != null)
            this.params = (CertStoreParameters) params.clone();
    }

    /**
     * Returns a {@code Collection} of {@code Certificate}s that
     * match the specified selector. If no {@code Certificate}s
     * match the selector, an empty {@code Collection} will be returned.
     * <p>
     * For some {@code CertStore} types, the resulting
     * {@code Collection} may not contain <b>all</b> of the
     * {@code Certificate}s that match the selector. For instance,
     * an LDAP {@code CertStore} may not search all entries in the
     * directory. Instead, it may just search entries that are likely to
     * contain the {@code Certificate}s it is looking for.
     * <p>
     * Some {@code CertStore} implementations (especially LDAP
     * {@code CertStore}s) may throw a {@code CertStoreException}
     * unless a non-null {@code CertSelector} is provided that
     * includes specific criteria that can be used to find the certificates.
     * Issuer and/or subject names are especially useful criteria.
     *
     * <p>
     *  返回与指定的选择器匹配的{@code Certificate}的{@code Collection}。
     * 如果没有{@code Certificate}与选择器匹配,则会返回一个空的{@code Collection}。
     * <p>
     * 对于某些{@code CertStore}类型,生成的{@code Collection}可能不包含与选择器匹配的{@code Certificate}的<b>全部</b>。
     * 例如,LDAP {@code CertStore}不能搜索目录中的所有条目。相反,它可能只是搜索可能包含它正在寻找的{@code Certificate}的条目。
     * <p>
     *  某些{@code CertStore}实现(尤其是LDAP {@code CertStore})可能会抛出{@code CertStoreException},除非提供了包含可用于查找证书的特定条件的
     * 非空{@code CertSelector}。
     * 发行人和/或主题名称是特别有用的标准。
     * 
     * 
     * @param selector A {@code CertSelector} used to select which
     *  {@code Certificate}s should be returned. Specify {@code null}
     *  to return all {@code Certificate}s (if supported).
     * @return A {@code Collection} of {@code Certificate}s that
     *         match the specified selector (never {@code null})
     * @throws CertStoreException if an exception occurs
     */
    public final Collection<? extends Certificate> getCertificates
            (CertSelector selector) throws CertStoreException {
        return storeSpi.engineGetCertificates(selector);
    }

    /**
     * Returns a {@code Collection} of {@code CRL}s that
     * match the specified selector. If no {@code CRL}s
     * match the selector, an empty {@code Collection} will be returned.
     * <p>
     * For some {@code CertStore} types, the resulting
     * {@code Collection} may not contain <b>all</b> of the
     * {@code CRL}s that match the selector. For instance,
     * an LDAP {@code CertStore} may not search all entries in the
     * directory. Instead, it may just search entries that are likely to
     * contain the {@code CRL}s it is looking for.
     * <p>
     * Some {@code CertStore} implementations (especially LDAP
     * {@code CertStore}s) may throw a {@code CertStoreException}
     * unless a non-null {@code CRLSelector} is provided that
     * includes specific criteria that can be used to find the CRLs.
     * Issuer names and/or the certificate to be checked are especially useful.
     *
     * <p>
     *  返回与指定的选择器匹配的{@code CRL}的{@code Collection}。如果{@code CRL}与选择器不匹配,则将返回一个空的{@code Collection}。
     * <p>
     *  对于某些{@code CertStore}类型,生成的{@code Collection}可能不包含与选择器匹配的{@code CRL}的<b>全部</b>。
     * 例如,LDAP {@code CertStore}不能搜索目录中的所有条目。相反,它可能只是搜索可能包含它正在寻找的{@code CRL}的条目。
     * <p>
     *  某些{@code CertStore}实现(尤其是LDAP {@code CertStore})可能会抛出{@code CertStoreException},除非提供了包含可用于查找CRL的特定条件
     * 的非空{@code CRLSelector}。
     * 发行人名称和/或要检查的证书特别有用。
     * 
     * 
     * @param selector A {@code CRLSelector} used to select which
     *  {@code CRL}s should be returned. Specify {@code null}
     *  to return all {@code CRL}s (if supported).
     * @return A {@code Collection} of {@code CRL}s that
     *         match the specified selector (never {@code null})
     * @throws CertStoreException if an exception occurs
     */
    public final Collection<? extends CRL> getCRLs(CRLSelector selector)
            throws CertStoreException {
        return storeSpi.engineGetCRLs(selector);
    }

    /**
     * Returns a {@code CertStore} object that implements the specified
     * {@code CertStore} type and is initialized with the specified
     * parameters.
     *
     * <p> This method traverses the list of registered security Providers,
     * starting with the most preferred Provider.
     * A new CertStore object encapsulating the
     * CertStoreSpi implementation from the first
     * Provider that supports the specified type is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>The {@code CertStore} that is returned is initialized with the
     * specified {@code CertStoreParameters}. The type of parameters
     * needed may vary between different types of {@code CertStore}s.
     * Note that the specified {@code CertStoreParameters} object is
     * cloned.
     *
     * <p>
     * 返回实现指定的{@code CertStore}类型并使用指定参数初始化的{@code CertStore}对象。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最常用的提供程序开始。将返回一个新的CertStore对象,该对象封装来自支持指定类型的第一个Provider的CertStoreSpi实现。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的{@code CertStore}使用指定的{@code CertStoreParameters}初始化。所需的参数类型可能因不同类型的{@code CertStore}而异。
     * 请注意,克隆指定的{@code CertStoreParameters}对象。
     * 
     * 
     * @param type the name of the requested {@code CertStore} type.
     * See the CertStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard types.
     *
     * @param params the initialization parameters (may be {@code null}).
     *
     * @return a {@code CertStore} object that implements the specified
     *          {@code CertStore} type.
     *
     * @throws NoSuchAlgorithmException if no Provider supports a
     *          CertStoreSpi implementation for the specified type.
     *
     * @throws InvalidAlgorithmParameterException if the specified
     *          initialization parameters are inappropriate for this
     *          {@code CertStore}.
     *
     * @see java.security.Provider
     */
    public static CertStore getInstance(String type, CertStoreParameters params)
            throws InvalidAlgorithmParameterException,
            NoSuchAlgorithmException {
        try {
            Instance instance = GetInstance.getInstance("CertStore",
                CertStoreSpi.class, type, params);
            return new CertStore((CertStoreSpi)instance.impl,
                instance.provider, type, params);
        } catch (NoSuchAlgorithmException e) {
            return handleException(e);
        }
    }

    private static CertStore handleException(NoSuchAlgorithmException e)
            throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Throwable cause = e.getCause();
        if (cause instanceof InvalidAlgorithmParameterException) {
            throw (InvalidAlgorithmParameterException)cause;
        }
        throw e;
    }

    /**
     * Returns a {@code CertStore} object that implements the specified
     * {@code CertStore} type.
     *
     * <p> A new CertStore object encapsulating the
     * CertStoreSpi implementation from the specified provider
     * is returned.  The specified provider must be registered
     * in the security provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>The {@code CertStore} that is returned is initialized with the
     * specified {@code CertStoreParameters}. The type of parameters
     * needed may vary between different types of {@code CertStore}s.
     * Note that the specified {@code CertStoreParameters} object is
     * cloned.
     *
     * <p>
     *  返回实现指定的{@code CertStore}类型的{@code CertStore}对象。
     * 
     *  <p>返回一个新的CertStore对象,该对象封装来自指定提供程序的CertStoreSpi实现。指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     *  <p>返回的{@code CertStore}使用指定的{@code CertStoreParameters}初始化。所需的参数类型可能因不同类型的{@code CertStore}而异。
     * 请注意,克隆指定的{@code CertStoreParameters}对象。
     * 
     * 
     * @param type the requested {@code CertStore} type.
     * See the CertStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard types.
     *
     * @param params the initialization parameters (may be {@code null}).
     *
     * @param provider the name of the provider.
     *
     * @return a {@code CertStore} object that implements the
     *          specified type.
     *
     * @throws NoSuchAlgorithmException if a CertStoreSpi
     *          implementation for the specified type is not
     *          available from the specified provider.
     *
     * @throws InvalidAlgorithmParameterException if the specified
     *          initialization parameters are inappropriate for this
     *          {@code CertStore}.
     *
     * @throws NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null or empty.
     *
     * @see java.security.Provider
     */
    public static CertStore getInstance(String type,
            CertStoreParameters params, String provider)
            throws InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException {
        try {
            Instance instance = GetInstance.getInstance("CertStore",
                CertStoreSpi.class, type, params, provider);
            return new CertStore((CertStoreSpi)instance.impl,
                instance.provider, type, params);
        } catch (NoSuchAlgorithmException e) {
            return handleException(e);
        }
    }

    /**
     * Returns a {@code CertStore} object that implements the specified
     * {@code CertStore} type.
     *
     * <p> A new CertStore object encapsulating the
     * CertStoreSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>The {@code CertStore} that is returned is initialized with the
     * specified {@code CertStoreParameters}. The type of parameters
     * needed may vary between different types of {@code CertStore}s.
     * Note that the specified {@code CertStoreParameters} object is
     * cloned.
     *
     * <p>
     * 返回实现指定的{@code CertStore}类型的{@code CertStore}对象。
     * 
     *  <p>返回一个新的CertStore对象,该对象封装来自指定的Provider对象的CertStoreSpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     *  <p>返回的{@code CertStore}使用指定的{@code CertStoreParameters}初始化。所需的参数类型可能因不同类型的{@code CertStore}而异。
     * 请注意,克隆指定的{@code CertStoreParameters}对象。
     * 
     * 
     * @param type the requested {@code CertStore} type.
     * See the CertStore section in the <a href=
     * "{@docRoot}/../technotes/guides/security/StandardNames.html#CertStore">
     * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
     * for information about standard types.
     *
     * @param params the initialization parameters (may be {@code null}).
     *
     * @param provider the provider.
     *
     * @return a {@code CertStore} object that implements the
     *          specified type.
     *
     * @exception NoSuchAlgorithmException if a CertStoreSpi
     *          implementation for the specified type is not available
     *          from the specified Provider object.
     *
     * @throws InvalidAlgorithmParameterException if the specified
     *          initialization parameters are inappropriate for this
     *          {@code CertStore}
     *
     * @exception IllegalArgumentException if the {@code provider} is
     *          null.
     *
     * @see java.security.Provider
     */
    public static CertStore getInstance(String type, CertStoreParameters params,
            Provider provider) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException {
        try {
            Instance instance = GetInstance.getInstance("CertStore",
                CertStoreSpi.class, type, params, provider);
            return new CertStore((CertStoreSpi)instance.impl,
                instance.provider, type, params);
        } catch (NoSuchAlgorithmException e) {
            return handleException(e);
        }
    }

    /**
     * Returns the parameters used to initialize this {@code CertStore}.
     * Note that the {@code CertStoreParameters} object is cloned before
     * it is returned.
     *
     * <p>
     *  返回用于初始化此{@code CertStore}的参数。请注意,{@code CertStoreParameters}对象在被返回之前被克隆。
     * 
     * 
     * @return the parameters used to initialize this {@code CertStore}
     * (may be {@code null})
     */
    public final CertStoreParameters getCertStoreParameters() {
        return (params == null ? null : (CertStoreParameters) params.clone());
    }

    /**
     * Returns the type of this {@code CertStore}.
     *
     * <p>
     *  返回此{@code CertStore}的类型。
     * 
     * 
     * @return the type of this {@code CertStore}
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Returns the provider of this {@code CertStore}.
     *
     * <p>
     *  返回此{@code CertStore}的提供程序。
     * 
     * 
     * @return the provider of this {@code CertStore}
     */
    public final Provider getProvider() {
        return this.provider;
    }

    /**
     * Returns the default {@code CertStore} type as specified by the
     * {@code certstore.type} security property, or the string
     * {@literal "LDAP"} if no such property exists.
     *
     * <p>The default {@code CertStore} type can be used by applications
     * that do not want to use a hard-coded type when calling one of the
     * {@code getInstance} methods, and want to provide a default
     * {@code CertStore} type in case a user does not specify its own.
     *
     * <p>The default {@code CertStore} type can be changed by setting
     * the value of the {@code certstore.type} security property to the
     * desired type.
     *
     * <p>
     *  返回由{@code certstore.type}安全属性指定的默认{@code CertStore}类型,如果没有此类属性,则返回字符串{@literal"LDAP"}。
     * 
     *  <p>当调用{@code getInstance}方法之一时,并且想要提供一个默认的{@code CertStore}方法时,不希望使用硬编码类型的应用程序可以使用默认的{@code CertStore}
     * 如果用户没有指定自己的类型。
     * 
     * @see java.security.Security security properties
     * @return the default {@code CertStore} type as specified by the
     * {@code certstore.type} security property, or the string
     * {@literal "LDAP"} if no such property exists.
     */
    public final static String getDefaultType() {
        String cstype;
        cstype = AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return Security.getProperty(CERTSTORE_TYPE);
            }
        });
        if (cstype == null) {
            cstype = "LDAP";
        }
        return cstype;
    }
}
