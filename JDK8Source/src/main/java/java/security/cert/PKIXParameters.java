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
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Parameters used as input for the PKIX {@code CertPathValidator}
 * algorithm.
 * <p>
 * A PKIX {@code CertPathValidator} uses these parameters to
 * validate a {@code CertPath} according to the PKIX certification path
 * validation algorithm.
 *
 * <p>To instantiate a {@code PKIXParameters} object, an
 * application must specify one or more <i>most-trusted CAs</i> as defined by
 * the PKIX certification path validation algorithm. The most-trusted CAs
 * can be specified using one of two constructors. An application
 * can call {@link #PKIXParameters(Set) PKIXParameters(Set)},
 * specifying a {@code Set} of {@code TrustAnchor} objects, each
 * of which identify a most-trusted CA. Alternatively, an application can call
 * {@link #PKIXParameters(KeyStore) PKIXParameters(KeyStore)}, specifying a
 * {@code KeyStore} instance containing trusted certificate entries, each
 * of which will be considered as a most-trusted CA.
 * <p>
 * Once a {@code PKIXParameters} object has been created, other parameters
 * can be specified (by calling {@link #setInitialPolicies setInitialPolicies}
 * or {@link #setDate setDate}, for instance) and then the
 * {@code PKIXParameters} is passed along with the {@code CertPath}
 * to be validated to {@link CertPathValidator#validate
 * CertPathValidator.validate}.
 * <p>
 * Any parameter that is not set (or is set to {@code null}) will
 * be set to the default value for that parameter. The default value for the
 * {@code date} parameter is {@code null}, which indicates
 * the current time when the path is validated. The default for the
 * remaining parameters is the least constrained.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this class are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * <p>
 *  用作PKIX {@code CertPathValidator}算法输入的参数。
 * <p>
 *  PKIX {@code CertPathValidator}根据PKIX认证路径验证算法使用这些参数验证{@code CertPath}。
 * 
 *  <p>要实例化{@code PKIXParameters}对象,应用程序必须指定一个或多个由PKIX认证路径验证算法定义的最受信任的CA </i>。最可信的CA可以使用两个构造函数之一指定。
 * 应用程序可以调用{@link #PKIXParameters(Set)PKIXParameters(Set)},指定{@code Set} {@code TrustAnchor}对象,每个对象标识一个最
 * 可信的CA.或者,应用程序可以调用{@link #PKIXParameters(KeyStore)PKIXParameters(KeyStore)},指定包含受信任证书条目的{@code KeyStore}
 * 实例,每个条目都将被视为最受信任的CA.。
 *  <p>要实例化{@code PKIXParameters}对象,应用程序必须指定一个或多个由PKIX认证路径验证算法定义的最受信任的CA </i>。最可信的CA可以使用两个构造函数之一指定。
 * <p>
 *  一旦创建了{@code PKIXParameters}对象,就可以指定其他参数(例如通过调用{@link #setInitialPolicies setInitialPolicies}或{@link #setDate setDate}
 * ),然后传递{@code PKIXParameters}并将{@code CertPath}验证为{@link CertPathValidator#validate CertPathValidator.validate}
 * 。
 * <p>
 * 未设置(或设置为{@code null})的任何参数都将设置为该参数的默认值。 {@code date}参数的默认值为{@code null},表示路径验证的当前时间。其余参数的默认值为最小约束。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CertPathValidator
 *
 * @since       1.4
 * @author      Sean Mullan
 * @author      Yassir Elley
 */
public class PKIXParameters implements CertPathParameters {

    private Set<TrustAnchor> unmodTrustAnchors;
    private Date date;
    private List<PKIXCertPathChecker> certPathCheckers;
    private String sigProvider;
    private boolean revocationEnabled = true;
    private Set<String> unmodInitialPolicies;
    private boolean explicitPolicyRequired = false;
    private boolean policyMappingInhibited = false;
    private boolean anyPolicyInhibited = false;
    private boolean policyQualifiersRejected = true;
    private List<CertStore> certStores;
    private CertSelector certSelector;

    /**
     * Creates an instance of {@code PKIXParameters} with the specified
     * {@code Set} of most-trusted CAs. Each element of the
     * set is a {@link TrustAnchor TrustAnchor}.
     * <p>
     * Note that the {@code Set} is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  使用指定的{@code Set}最受信任的CA创建{@code PKIXParameters}的实例。集合的每个元素是{@link TrustAnchor TrustAnchor}。
     * <p>
     *  请注意,复制{@code Set}以防止后续修改。
     * 
     * 
     * @param trustAnchors a {@code Set} of {@code TrustAnchor}s
     * @throws InvalidAlgorithmParameterException if the specified
     * {@code Set} is empty {@code (trustAnchors.isEmpty() == true)}
     * @throws NullPointerException if the specified {@code Set} is
     * {@code null}
     * @throws ClassCastException if any of the elements in the {@code Set}
     * are not of type {@code java.security.cert.TrustAnchor}
     */
    public PKIXParameters(Set<TrustAnchor> trustAnchors)
        throws InvalidAlgorithmParameterException
    {
        setTrustAnchors(trustAnchors);

        this.unmodInitialPolicies = Collections.<String>emptySet();
        this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        this.certStores = new ArrayList<CertStore>();
    }

    /**
     * Creates an instance of {@code PKIXParameters} that
     * populates the set of most-trusted CAs from the trusted
     * certificate entries contained in the specified {@code KeyStore}.
     * Only keystore entries that contain trusted {@code X509Certificates}
     * are considered; all other certificate types are ignored.
     *
     * <p>
     *  创建{@code PKIXParameters}的实例,该实例从指定的{@code KeyStore}中包含的受信任证书条目填充一组最受信任的CA.仅考虑包含受信任的{@code X509Certificates}
     * 的密钥库条目;所有其他证书类型将被忽略。
     * 
     * 
     * @param keystore a {@code KeyStore} from which the set of
     * most-trusted CAs will be populated
     * @throws KeyStoreException if the keystore has not been initialized
     * @throws InvalidAlgorithmParameterException if the keystore does
     * not contain at least one trusted certificate entry
     * @throws NullPointerException if the keystore is {@code null}
     */
    public PKIXParameters(KeyStore keystore)
        throws KeyStoreException, InvalidAlgorithmParameterException
    {
        if (keystore == null)
            throw new NullPointerException("the keystore parameter must be " +
                "non-null");
        Set<TrustAnchor> hashSet = new HashSet<TrustAnchor>();
        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (keystore.isCertificateEntry(alias)) {
                Certificate cert = keystore.getCertificate(alias);
                if (cert instanceof X509Certificate)
                    hashSet.add(new TrustAnchor((X509Certificate)cert, null));
            }
        }
        setTrustAnchors(hashSet);
        this.unmodInitialPolicies = Collections.<String>emptySet();
        this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        this.certStores = new ArrayList<CertStore>();
    }

    /**
     * Returns an immutable {@code Set} of the most-trusted
     * CAs.
     *
     * <p>
     *  返回不可变的{@code Set}最受信任的CA.
     * 
     * 
     * @return an immutable {@code Set} of {@code TrustAnchor}s
     * (never {@code null})
     *
     * @see #setTrustAnchors
     */
    public Set<TrustAnchor> getTrustAnchors() {
        return this.unmodTrustAnchors;
    }

    /**
     * Sets the {@code Set} of most-trusted CAs.
     * <p>
     * Note that the {@code Set} is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  设置最受信任的CA的{@code Set}。
     * <p>
     *  请注意,复制{@code Set}以防止后续修改。
     * 
     * 
     * @param trustAnchors a {@code Set} of {@code TrustAnchor}s
     * @throws InvalidAlgorithmParameterException if the specified
     * {@code Set} is empty {@code (trustAnchors.isEmpty() == true)}
     * @throws NullPointerException if the specified {@code Set} is
     * {@code null}
     * @throws ClassCastException if any of the elements in the set
     * are not of type {@code java.security.cert.TrustAnchor}
     *
     * @see #getTrustAnchors
     */
    public void setTrustAnchors(Set<TrustAnchor> trustAnchors)
        throws InvalidAlgorithmParameterException
    {
        if (trustAnchors == null) {
            throw new NullPointerException("the trustAnchors parameters must" +
                " be non-null");
        }
        if (trustAnchors.isEmpty()) {
            throw new InvalidAlgorithmParameterException("the trustAnchors " +
                "parameter must be non-empty");
        }
        for (Iterator<TrustAnchor> i = trustAnchors.iterator(); i.hasNext(); ) {
            if (!(i.next() instanceof TrustAnchor)) {
                throw new ClassCastException("all elements of set must be "
                    + "of type java.security.cert.TrustAnchor");
            }
        }
        this.unmodTrustAnchors = Collections.unmodifiableSet
                (new HashSet<TrustAnchor>(trustAnchors));
    }

    /**
     * Returns an immutable {@code Set} of initial
     * policy identifiers (OID strings), indicating that any one of these
     * policies would be acceptable to the certificate user for the purposes of
     * certification path processing. The default return value is an empty
     * {@code Set}, which is interpreted as meaning that any policy would
     * be acceptable.
     *
     * <p>
     * 返回一个不变的{@code Set}初始策略标识符(OID字符串),指示证书用户可以接受这些策略中的任何一个以用于证书路径处理。
     * 默认返回值是一个空的{@code Set},这被解释为意味着任何策略都是可以接受的。
     * 
     * 
     * @return an immutable {@code Set} of initial policy OIDs in
     * {@code String} format, or an empty {@code Set} (implying any
     * policy is acceptable). Never returns {@code null}.
     *
     * @see #setInitialPolicies
     */
    public Set<String> getInitialPolicies() {
        return this.unmodInitialPolicies;
    }

    /**
     * Sets the {@code Set} of initial policy identifiers
     * (OID strings), indicating that any one of these
     * policies would be acceptable to the certificate user for the purposes of
     * certification path processing. By default, any policy is acceptable
     * (i.e. all policies), so a user that wants to allow any policy as
     * acceptable does not need to call this method, or can call it
     * with an empty {@code Set} (or {@code null}).
     * <p>
     * Note that the {@code Set} is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  设置初始策略标识符(OID字符串)的{@code Set},指示证书用户可接受这些策略中的任何一个,以便进行证书路径处理。
     * 默认情况下,任何策略都是可接受的(即所有策略),因此,希望允许任何策略为可接受的用户不需要调用此方法,或者可以使用空的{@code Set}(或{@code null })。
     * <p>
     *  请注意,复制{@code Set}以防止后续修改。
     * 
     * 
     * @param initialPolicies a {@code Set} of initial policy
     * OIDs in {@code String} format (or {@code null})
     * @throws ClassCastException if any of the elements in the set are
     * not of type {@code String}
     *
     * @see #getInitialPolicies
     */
    public void setInitialPolicies(Set<String> initialPolicies) {
        if (initialPolicies != null) {
            for (Iterator<String> i = initialPolicies.iterator();
                        i.hasNext();) {
                if (!(i.next() instanceof String))
                    throw new ClassCastException("all elements of set must be "
                        + "of type java.lang.String");
            }
            this.unmodInitialPolicies =
                Collections.unmodifiableSet(new HashSet<String>(initialPolicies));
        } else
            this.unmodInitialPolicies = Collections.<String>emptySet();
    }

    /**
     * Sets the list of {@code CertStore}s to be used in finding
     * certificates and CRLs. May be {@code null}, in which case
     * no {@code CertStore}s will be used. The first
     * {@code CertStore}s in the list may be preferred to those that
     * appear later.
     * <p>
     * Note that the {@code List} is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  设置要用于查找证书和CRL的{@code CertStore}的列表。可能是{@code null},在这种情况下不会使用{@code CertStore}。
     * 列表中的第一个{@code CertStore}可能优先于稍后显示的那些。
     * <p>
     *  请注意,复制{@code List}以防止后续修改。
     * 
     * 
     * @param stores a {@code List} of {@code CertStore}s (or
     * {@code null})
     * @throws ClassCastException if any of the elements in the list are
     * not of type {@code java.security.cert.CertStore}
     *
     * @see #getCertStores
     */
    public void setCertStores(List<CertStore> stores) {
        if (stores == null) {
            this.certStores = new ArrayList<CertStore>();
        } else {
            for (Iterator<CertStore> i = stores.iterator(); i.hasNext();) {
                if (!(i.next() instanceof CertStore)) {
                    throw new ClassCastException("all elements of list must be "
                        + "of type java.security.cert.CertStore");
                }
            }
            this.certStores = new ArrayList<CertStore>(stores);
        }
    }

    /**
     * Adds a {@code CertStore} to the end of the list of
     * {@code CertStore}s used in finding certificates and CRLs.
     *
     * <p>
     *  在用于查找证书和CRL的{@code CertStore}列表的末尾添加{@code CertStore}。
     * 
     * 
     * @param store the {@code CertStore} to add. If {@code null},
     * the store is ignored (not added to list).
     */
    public void addCertStore(CertStore store) {
        if (store != null) {
            this.certStores.add(store);
        }
    }

    /**
     * Returns an immutable {@code List} of {@code CertStore}s that
     * are used to find certificates and CRLs.
     *
     * <p>
     *  返回用于查找证书和CRL的{@code CertStore}的不可变{@code List}。
     * 
     * 
     * @return an immutable {@code List} of {@code CertStore}s
     * (may be empty, but never {@code null})
     *
     * @see #setCertStores
     */
    public List<CertStore> getCertStores() {
        return Collections.unmodifiableList
                (new ArrayList<CertStore>(this.certStores));
    }

    /**
     * Sets the RevocationEnabled flag. If this flag is true, the default
     * revocation checking mechanism of the underlying PKIX service provider
     * will be used. If this flag is false, the default revocation checking
     * mechanism will be disabled (not used).
     * <p>
     * When a {@code PKIXParameters} object is created, this flag is set
     * to true. This setting reflects the most common strategy for checking
     * revocation, since each service provider must support revocation
     * checking to be PKIX compliant. Sophisticated applications should set
     * this flag to false when it is not practical to use a PKIX service
     * provider's default revocation checking mechanism or when an alternative
     * revocation checking mechanism is to be substituted (by also calling the
     * {@link #addCertPathChecker addCertPathChecker} or {@link
     * #setCertPathCheckers setCertPathCheckers} methods).
     *
     * <p>
     * 设置RevocationEnabled标志。如果此标志为true,将使用底层PKIX服务提供程序的默认撤销检查机制。如果此标志为false,则将禁用(不使用)默认撤销检查机制。
     * <p>
     *  创建{@code PKIXParameters}对象时,此标志设置为true。此设置反映了检查撤销的最常见策略,因为每个服务提供商必须支持撤消检查以符合PKIX。
     * 当使用PKIX服务提供程序的默认撤销检查机制或替换备用撤销检查机制(通过调用{@link #addCertPathChecker addCertPathChecker}或{@link})时,复杂的应用程
     * 序应将此标志设置为false #setCertPathCheckers setCertPathCheckers}方法)。
     *  创建{@code PKIXParameters}对象时,此标志设置为true。此设置反映了检查撤销的最常见策略,因为每个服务提供商必须支持撤消检查以符合PKIX。
     * 
     * 
     * @param val the new value of the RevocationEnabled flag
     */
    public void setRevocationEnabled(boolean val) {
        revocationEnabled = val;
    }

    /**
     * Checks the RevocationEnabled flag. If this flag is true, the default
     * revocation checking mechanism of the underlying PKIX service provider
     * will be used. If this flag is false, the default revocation checking
     * mechanism will be disabled (not used). See the {@link
     * #setRevocationEnabled setRevocationEnabled} method for more details on
     * setting the value of this flag.
     *
     * <p>
     *  检查RevocationEnabled标志。如果此标志为true,将使用底层PKIX服务提供程序的默认撤销检查机制。如果此标志为false,则将禁用(不使用)默认撤销检查机制。
     * 有关设置此标志的值的更多详细信息,请参阅{@link #setRevocationEnabled setRevocationEnabled}方法。
     * 
     * 
     * @return the current value of the RevocationEnabled flag
     */
    public boolean isRevocationEnabled() {
        return revocationEnabled;
    }

    /**
     * Sets the ExplicitPolicyRequired flag. If this flag is true, an
     * acceptable policy needs to be explicitly identified in every certificate.
     * By default, the ExplicitPolicyRequired flag is false.
     *
     * <p>
     *  设置ExplicitPolicyRequired标志。如果此标志为真,则需要在每个证书中明确标识可接受的策略。默认情况下,ExplicitPolicyRequired标志为false。
     * 
     * 
     * @param val {@code true} if explicit policy is to be required,
     * {@code false} otherwise
     */
    public void setExplicitPolicyRequired(boolean val) {
        explicitPolicyRequired = val;
    }

    /**
     * Checks if explicit policy is required. If this flag is true, an
     * acceptable policy needs to be explicitly identified in every certificate.
     * By default, the ExplicitPolicyRequired flag is false.
     *
     * <p>
     * 检查是否需要显式策略。如果此标志为真,则需要在每个证书中明确标识可接受的策略。默认情况下,ExplicitPolicyRequired标志为false。
     * 
     * 
     * @return {@code true} if explicit policy is required,
     * {@code false} otherwise
     */
    public boolean isExplicitPolicyRequired() {
        return explicitPolicyRequired;
    }

    /**
     * Sets the PolicyMappingInhibited flag. If this flag is true, policy
     * mapping is inhibited. By default, policy mapping is not inhibited (the
     * flag is false).
     *
     * <p>
     *  设置PolicyMappingInhibited标志。如果此标志为真,则禁止策略映射。默认情况下,不禁止策略映射(标志为false)。
     * 
     * 
     * @param val {@code true} if policy mapping is to be inhibited,
     * {@code false} otherwise
     */
    public void setPolicyMappingInhibited(boolean val) {
        policyMappingInhibited = val;
    }

    /**
     * Checks if policy mapping is inhibited. If this flag is true, policy
     * mapping is inhibited. By default, policy mapping is not inhibited (the
     * flag is false).
     *
     * <p>
     *  检查策略映射是否禁止。如果此标志为真,则禁止策略映射。默认情况下,不禁止策略映射(标志为false)。
     * 
     * 
     * @return true if policy mapping is inhibited, false otherwise
     */
    public boolean isPolicyMappingInhibited() {
        return policyMappingInhibited;
    }

    /**
     * Sets state to determine if the any policy OID should be processed
     * if it is included in a certificate. By default, the any policy OID
     * is not inhibited ({@link #isAnyPolicyInhibited isAnyPolicyInhibited()}
     * returns {@code false}).
     *
     * <p>
     *  设置状态以确定是否应当处理任何策略OID(如果它包含在证书中)。
     * 默认情况下,不会禁止任何策略OID({@link #isAnyPolicyInhibited isAnyPolicyInhibited()}返回{@code false})。
     * 
     * 
     * @param val {@code true} if the any policy OID is to be
     * inhibited, {@code false} otherwise
     */
    public void setAnyPolicyInhibited(boolean val) {
        anyPolicyInhibited = val;
    }

    /**
     * Checks whether the any policy OID should be processed if it
     * is included in a certificate.
     *
     * <p>
     *  检查是否应该处理任何策略OID(如果它包含在证书中)。
     * 
     * 
     * @return {@code true} if the any policy OID is inhibited,
     * {@code false} otherwise
     */
    public boolean isAnyPolicyInhibited() {
        return anyPolicyInhibited;
    }

    /**
     * Sets the PolicyQualifiersRejected flag. If this flag is true,
     * certificates that include policy qualifiers in a certificate
     * policies extension that is marked critical are rejected.
     * If the flag is false, certificates are not rejected on this basis.
     *
     * <p> When a {@code PKIXParameters} object is created, this flag is
     * set to true. This setting reflects the most common (and simplest)
     * strategy for processing policy qualifiers. Applications that want to use
     * a more sophisticated policy must set this flag to false.
     * <p>
     * Note that the PKIX certification path validation algorithm specifies
     * that any policy qualifier in a certificate policies extension that is
     * marked critical must be processed and validated. Otherwise the
     * certification path must be rejected. If the policyQualifiersRejected flag
     * is set to false, it is up to the application to validate all policy
     * qualifiers in this manner in order to be PKIX compliant.
     *
     * <p>
     *  设置PolicyQualifiersRejected标志。如果此标志为真,那么在标记为关键的证书策略扩展中包含策略限定符的证书将被拒绝。如果标志为假,则证书不会在此基础上被拒绝。
     * 
     *  <p>创建{@code PKIXParameters}对象时,此标志设置为true。此设置反映了处理策略限定符的最常见(最简单)策略。要使用更复杂策略的应用程序必须将此标志设置为false。
     * <p>
     * 请注意,PKIX认证路径验证算法指定必须处理和验证标记为关键的证书策略扩展中的任何策略限定符。否则,必须拒绝认证路径。
     * 如果policyQualifiersRejected标志设置为false,则由应用程序以此方式验证所有策略限定符以符合PKIX标准。
     * 
     * 
     * @param qualifiersRejected the new value of the PolicyQualifiersRejected
     * flag
     * @see #getPolicyQualifiersRejected
     * @see PolicyQualifierInfo
     */
    public void setPolicyQualifiersRejected(boolean qualifiersRejected) {
        policyQualifiersRejected = qualifiersRejected;
    }

    /**
     * Gets the PolicyQualifiersRejected flag. If this flag is true,
     * certificates that include policy qualifiers in a certificate policies
     * extension that is marked critical are rejected.
     * If the flag is false, certificates are not rejected on this basis.
     *
     * <p> When a {@code PKIXParameters} object is created, this flag is
     * set to true. This setting reflects the most common (and simplest)
     * strategy for processing policy qualifiers. Applications that want to use
     * a more sophisticated policy must set this flag to false.
     *
     * <p>
     *  获取PolicyQualifiersRejected标志。如果此标志为真,那么在标记为关键的证书策略扩展中包含策略限定符的证书将被拒绝。如果标志为假,则证书不会在此基础上被拒绝。
     * 
     *  <p>创建{@code PKIXParameters}对象时,此标志设置为true。此设置反映了处理策略限定符的最常见(最简单)策略。要使用更复杂策略的应用程序必须将此标志设置为false。
     * 
     * 
     * @return the current value of the PolicyQualifiersRejected flag
     * @see #setPolicyQualifiersRejected
     */
    public boolean getPolicyQualifiersRejected() {
        return policyQualifiersRejected;
    }

    /**
     * Returns the time for which the validity of the certification path
     * should be determined. If {@code null}, the current time is used.
     * <p>
     * Note that the {@code Date} returned is copied to protect against
     * subsequent modifications.
     *
     * <p>
     *  返回应确定认证路径有效性的时间。如果{@code null},则使用当前时间。
     * <p>
     *  请注意,系统会复制返回的{@code Date},以防后续修改。
     * 
     * 
     * @return the {@code Date}, or {@code null} if not set
     * @see #setDate
     */
    public Date getDate() {
        if (date == null)
            return null;
        else
            return (Date) this.date.clone();
    }

    /**
     * Sets the time for which the validity of the certification path
     * should be determined. If {@code null}, the current time is used.
     * <p>
     * Note that the {@code Date} supplied here is copied to protect
     * against subsequent modifications.
     *
     * <p>
     *  设置应确定认证路径有效性的时间。如果{@code null},则使用当前时间。
     * <p>
     *  请注意,此处提供的{@code Date}已复制,以防后续修改。
     * 
     * 
     * @param date the {@code Date}, or {@code null} for the
     * current time
     * @see #getDate
     */
    public void setDate(Date date) {
        if (date != null)
            this.date = (Date) date.clone();
        else
            date = null;
    }

    /**
     * Sets a {@code List} of additional certification path checkers. If
     * the specified {@code List} contains an object that is not a
     * {@code PKIXCertPathChecker}, it is ignored.
     * <p>
     * Each {@code PKIXCertPathChecker} specified implements
     * additional checks on a certificate. Typically, these are checks to
     * process and verify private extensions contained in certificates.
     * Each {@code PKIXCertPathChecker} should be instantiated with any
     * initialization parameters needed to execute the check.
     * <p>
     * This method allows sophisticated applications to extend a PKIX
     * {@code CertPathValidator} or {@code CertPathBuilder}.
     * Each of the specified {@code PKIXCertPathChecker}s will be called,
     * in turn, by a PKIX {@code CertPathValidator} or
     * {@code CertPathBuilder} for each certificate processed or
     * validated.
     * <p>
     * Regardless of whether these additional {@code PKIXCertPathChecker}s
     * are set, a PKIX {@code CertPathValidator} or
     * {@code CertPathBuilder} must perform all of the required PKIX
     * checks on each certificate. The one exception to this rule is if the
     * RevocationEnabled flag is set to false (see the {@link
     * #setRevocationEnabled setRevocationEnabled} method).
     * <p>
     * Note that the {@code List} supplied here is copied and each
     * {@code PKIXCertPathChecker} in the list is cloned to protect
     * against subsequent modifications.
     *
     * <p>
     * 设置其他认证路径检查程序的{@code List}。如果指定的{@code List}包含不是{@code PKIXCertPathChecker}的对象,则会被忽略。
     * <p>
     *  每个{@code PKIXCertPathChecker}指定对证书实施其他检查。通常,这些是检查以处理和验证证书中包含的私有扩展。
     * 每个{@code PKIXCertPathChecker}都应该使用执行检查所需的任何初始化参数进行实例化。
     * <p>
     *  此方法允许复杂的应用程序扩展PKIX {@code CertPathValidator}或{@code CertPathBuilder}。
     * 每个指定的{@code PKIXCertPathChecker}将被处理或验证的每个证书的PKIX {@code CertPathValidator}或{@code CertPathBuilder}依次
     * 调用。
     *  此方法允许复杂的应用程序扩展PKIX {@code CertPathValidator}或{@code CertPathBuilder}。
     * <p>
     *  无论是否设置这些附加的{@code PKIXCertPathChecker},PKIX {@code CertPathValidator}或{@code CertPathBuilder}必须对每个证书
     * 执行所有必需的PKIX检查。
     * 此规则的一个例外是如果RevocationEnabled标志设置为false(请参阅{@link #setRevocationEnabled setRevocationEnabled}方法)。
     * <p>
     *  请注意,此处提供的{@code List}已复制,列表中的每个{@code PKIXCertPathChecker}都将被克隆,以防后续修改。
     * 
     * 
     * @param checkers a {@code List} of {@code PKIXCertPathChecker}s.
     * May be {@code null}, in which case no additional checkers will be
     * used.
     * @throws ClassCastException if any of the elements in the list
     * are not of type {@code java.security.cert.PKIXCertPathChecker}
     * @see #getCertPathCheckers
     */
    public void setCertPathCheckers(List<PKIXCertPathChecker> checkers) {
        if (checkers != null) {
            List<PKIXCertPathChecker> tmpList =
                        new ArrayList<PKIXCertPathChecker>();
            for (PKIXCertPathChecker checker : checkers) {
                tmpList.add((PKIXCertPathChecker)checker.clone());
            }
            this.certPathCheckers = tmpList;
        } else {
            this.certPathCheckers = new ArrayList<PKIXCertPathChecker>();
        }
    }

    /**
     * Returns the {@code List} of certification path checkers.
     * The returned {@code List} is immutable, and each
     * {@code PKIXCertPathChecker} in the {@code List} is cloned
     * to protect against subsequent modifications.
     *
     * <p>
     *  返回认证路径检查器的{@code List}。
     * 返回的{@code List}是不可变的,{@code List}中的每个{@code PKIXCertPathChecker}都是克隆的,以防后续修改。
     * 
     * 
     * @return an immutable {@code List} of
     * {@code PKIXCertPathChecker}s (may be empty, but not
     * {@code null})
     * @see #setCertPathCheckers
     */
    public List<PKIXCertPathChecker> getCertPathCheckers() {
        List<PKIXCertPathChecker> tmpList = new ArrayList<PKIXCertPathChecker>();
        for (PKIXCertPathChecker ck : certPathCheckers) {
            tmpList.add((PKIXCertPathChecker)ck.clone());
        }
        return Collections.unmodifiableList(tmpList);
    }

    /**
     * Adds a {@code PKIXCertPathChecker} to the list of certification
     * path checkers. See the {@link #setCertPathCheckers setCertPathCheckers}
     * method for more details.
     * <p>
     * Note that the {@code PKIXCertPathChecker} is cloned to protect
     * against subsequent modifications.
     *
     * <p>
     * 将{@code PKIXCertPathChecker}添加到认证路径检查程序的列表中。
     * 有关详细信息,请参阅{@link #setCertPathCheckers setCertPathCheckers}方法。
     * <p>
     *  请注意,克隆{@code PKIXCertPathChecker}以防止后续修改。
     * 
     * 
     * @param checker a {@code PKIXCertPathChecker} to add to the list of
     * checks. If {@code null}, the checker is ignored (not added to list).
     */
    public void addCertPathChecker(PKIXCertPathChecker checker) {
        if (checker != null) {
            certPathCheckers.add((PKIXCertPathChecker)checker.clone());
        }
    }

    /**
     * Returns the signature provider's name, or {@code null}
     * if not set.
     *
     * <p>
     *  返回签名提供程序的名称,如果未设置,则返回{@code null}。
     * 
     * 
     * @return the signature provider's name (or {@code null})
     * @see #setSigProvider
     */
    public String getSigProvider() {
        return this.sigProvider;
    }

    /**
     * Sets the signature provider's name. The specified provider will be
     * preferred when creating {@link java.security.Signature Signature}
     * objects. If {@code null} or not set, the first provider found
     * supporting the algorithm will be used.
     *
     * <p>
     *  设置签名提供程序的名称。创建{@link java.security.Signature Signature}对象时,将首选指定的提供程序。
     * 如果{@code null}或未设置,将使用找到的支持该算法的第一个提供者。
     * 
     * 
     * @param sigProvider the signature provider's name (or {@code null})
     * @see #getSigProvider
    */
    public void setSigProvider(String sigProvider) {
        this.sigProvider = sigProvider;
    }

    /**
     * Returns the required constraints on the target certificate.
     * The constraints are returned as an instance of {@code CertSelector}.
     * If {@code null}, no constraints are defined.
     *
     * <p>Note that the {@code CertSelector} returned is cloned
     * to protect against subsequent modifications.
     *
     * <p>
     *  返回目标证书上的必需约束。约束作为{@code CertSelector}的实例返回。如果{@code null},没有定义约束。
     * 
     *  <p>请注意,所返回的{@code CertSelector}已克隆,以防后续修改。
     * 
     * 
     * @return a {@code CertSelector} specifying the constraints
     * on the target certificate (or {@code null})
     * @see #setTargetCertConstraints
     */
    public CertSelector getTargetCertConstraints() {
        if (certSelector != null) {
            return (CertSelector) certSelector.clone();
        } else {
            return null;
        }
    }

    /**
     * Sets the required constraints on the target certificate.
     * The constraints are specified as an instance of
     * {@code CertSelector}. If {@code null}, no constraints are
     * defined.
     *
     * <p>Note that the {@code CertSelector} specified is cloned
     * to protect against subsequent modifications.
     *
     * <p>
     *  设置目标证书上的必需约束。约束被指定为{@code CertSelector}的实例。如果{@code null},没有定义约束。
     * 
     *  <p>请注意,指定的{@code CertSelector}已克隆,以防后续修改。
     * 
     * 
     * @param selector a {@code CertSelector} specifying the constraints
     * on the target certificate (or {@code null})
     * @see #getTargetCertConstraints
     */
    public void setTargetCertConstraints(CertSelector selector) {
        if (selector != null)
            certSelector = (CertSelector) selector.clone();
        else
            certSelector = null;
    }

    /**
     * Makes a copy of this {@code PKIXParameters} object. Changes
     * to the copy will not affect the original and vice versa.
     *
     * <p>
     *  创建此{@code PKIXParameters}对象的副本。对副本的更改不会影响原始副本,反之亦然。
     * 
     * 
     * @return a copy of this {@code PKIXParameters} object
     */
    public Object clone() {
        try {
            PKIXParameters copy = (PKIXParameters)super.clone();

            // must clone these because addCertStore, et al. modify them
            if (certStores != null) {
                copy.certStores = new ArrayList<CertStore>(certStores);
            }
            if (certPathCheckers != null) {
                copy.certPathCheckers =
                    new ArrayList<PKIXCertPathChecker>(certPathCheckers.size());
                for (PKIXCertPathChecker checker : certPathCheckers) {
                    copy.certPathCheckers.add(
                                    (PKIXCertPathChecker)checker.clone());
                }
            }

            // other class fields are immutable to public, don't bother
            // to clone the read-only fields.
            return copy;
        } catch (CloneNotSupportedException e) {
            /* Cannot happen */
            throw new InternalError(e.toString(), e);
        }
    }

    /**
     * Returns a formatted string describing the parameters.
     *
     * <p>
     *  返回描述参数的格式化字符串。
     * 
     * @return a formatted string describing the parameters.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[\n");

        /* start with trusted anchor info */
        if (unmodTrustAnchors != null) {
            sb.append("  Trust Anchors: " + unmodTrustAnchors.toString()
                + "\n");
        }

        /* now, append initial state information */
        if (unmodInitialPolicies != null) {
            if (unmodInitialPolicies.isEmpty()) {
                sb.append("  Initial Policy OIDs: any\n");
            } else {
                sb.append("  Initial Policy OIDs: ["
                    + unmodInitialPolicies.toString() + "]\n");
            }
        }

        /* now, append constraints on all certificates in the path */
        sb.append("  Validity Date: " + String.valueOf(date) + "\n");
        sb.append("  Signature Provider: " + String.valueOf(sigProvider) + "\n");
        sb.append("  Default Revocation Enabled: " + revocationEnabled + "\n");
        sb.append("  Explicit Policy Required: " + explicitPolicyRequired + "\n");
        sb.append("  Policy Mapping Inhibited: " + policyMappingInhibited + "\n");
        sb.append("  Any Policy Inhibited: " + anyPolicyInhibited + "\n");
        sb.append("  Policy Qualifiers Rejected: " + policyQualifiersRejected + "\n");

        /* now, append target cert requirements */
        sb.append("  Target Cert Constraints: " + String.valueOf(certSelector) + "\n");

        /* finally, append miscellaneous parameters */
        if (certPathCheckers != null)
            sb.append("  Certification Path Checkers: ["
                + certPathCheckers.toString() + "]\n");
        if (certStores != null)
            sb.append("  CertStores: [" + certStores.toString() + "]\n");
        sb.append("]");
        return sb.toString();
    }
}
