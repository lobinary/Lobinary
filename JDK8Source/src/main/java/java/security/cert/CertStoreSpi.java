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

import java.security.InvalidAlgorithmParameterException;
import java.util.Collection;

/**
 * The <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@link CertStore CertStore} class. All {@code CertStore}
 * implementations must include a class (the SPI class) that extends
 * this class ({@code CertStoreSpi}), provides a constructor with
 * a single argument of type {@code CertStoreParameters}, and implements
 * all of its methods. In general, instances of this class should only be
 * accessed through the {@code CertStore} class.
 * For details, see the Java Cryptography Architecture.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * The public methods of all {@code CertStoreSpi} objects must be
 * thread-safe. That is, multiple threads may concurrently invoke these
 * methods on a single {@code CertStoreSpi} object (or more than one)
 * with no ill effects. This allows a {@code CertPathBuilder} to search
 * for a CRL while simultaneously searching for further certificates, for
 * instance.
 * <p>
 * Simple {@code CertStoreSpi} implementations will probably ensure
 * thread safety by adding a {@code synchronized} keyword to their
 * {@code engineGetCertificates} and {@code engineGetCRLs} methods.
 * More sophisticated ones may allow truly concurrent access.
 *
 * <p>
 *  {@link CertStore CertStore}类的<i>服务提供程序接口</i>(<b> SPI </b>)。
 * 所有{@code CertStore}实现必须包括一个扩展这个类({@code CertStoreSpi})的类(SPI类),它提供了一个类型为{@code CertStoreParameters}的单
 * 一参数的构造函数,并实现所有的方法。
 *  {@link CertStore CertStore}类的<i>服务提供程序接口</i>(<b> SPI </b>)。通常,此类的实例只应通过{@code CertStore}类访问。
 * 有关详细信息,请参阅Java加密体系结构。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  所有{@code CertStoreSpi}对象的公共方法必须是线程安全的。也就是说,多个线程可以同时在单个{@code CertStoreSpi}对象(或多个对象)上调用这些方法,没有不良影响。
 * 这允许{@code CertPathBuilder}搜索CRL,同时例如搜索另外的证书。
 * <p>
 *  简单的{@code CertStoreSpi}实施可能会通过向其{@code engineGetCertificates}和{@code engineGetCRLs}方法中添加{@code synchronized}
 * 关键字来确保线程安全。
 * 更复杂的可以允许真正的并发访问。
 * 
 * 
 * @since       1.4
 * @author      Steve Hanna
 */
public abstract class CertStoreSpi {

    /**
     * The sole constructor.
     *
     * <p>
     *  唯一的构造函数。
     * 
     * 
     * @param params the initialization parameters (may be {@code null})
     * @throws InvalidAlgorithmParameterException if the initialization
     * parameters are inappropriate for this {@code CertStoreSpi}
     */
    public CertStoreSpi(CertStoreParameters params)
    throws InvalidAlgorithmParameterException { }

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
     * unless a non-null {@code CertSelector} is provided that includes
     * specific criteria that can be used to find the certificates. Issuer
     * and/or subject names are especially useful criteria.
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
    public abstract Collection<? extends Certificate> engineGetCertificates
            (CertSelector selector) throws CertStoreException;

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
     * unless a non-null {@code CRLSelector} is provided that includes
     * specific criteria that can be used to find the CRLs. Issuer names
     * and/or the certificate to be checked are especially useful.
     *
     * <p>
     * 
     * @param selector A {@code CRLSelector} used to select which
     *  {@code CRL}s should be returned. Specify {@code null}
     *  to return all {@code CRL}s (if supported).
     * @return A {@code Collection} of {@code CRL}s that
     *         match the specified selector (never {@code null})
     * @throws CertStoreException if an exception occurs
     */
    public abstract Collection<? extends CRL> engineGetCRLs
            (CRLSelector selector) throws CertStoreException;
}
