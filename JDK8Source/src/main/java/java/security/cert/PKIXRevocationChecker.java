/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A {@code PKIXCertPathChecker} for checking the revocation status of
 * certificates with the PKIX algorithm.
 *
 * <p>A {@code PKIXRevocationChecker} checks the revocation status of
 * certificates with the Online Certificate Status Protocol (OCSP) or
 * Certificate Revocation Lists (CRLs). OCSP is described in RFC 2560 and
 * is a network protocol for determining the status of a certificate. A CRL
 * is a time-stamped list identifying revoked certificates, and RFC 5280
 * describes an algorithm for determining the revocation status of certificates
 * using CRLs.
 *
 * <p>Each {@code PKIXRevocationChecker} must be able to check the revocation
 * status of certificates with OCSP and CRLs. By default, OCSP is the
 * preferred mechanism for checking revocation status, with CRLs as the
 * fallback mechanism. However, this preference can be switched to CRLs with
 * the {@link Option#PREFER_CRLS PREFER_CRLS} option. In addition, the fallback
 * mechanism can be disabled with the {@link Option#NO_FALLBACK NO_FALLBACK}
 * option.
 *
 * <p>A {@code PKIXRevocationChecker} is obtained by calling the
 * {@link CertPathValidator#getRevocationChecker getRevocationChecker} method
 * of a PKIX {@code CertPathValidator}. Additional parameters and options
 * specific to revocation can be set (by calling the
 * {@link #setOcspResponder setOcspResponder} method for instance). The
 * {@code PKIXRevocationChecker} is added to a {@code PKIXParameters} object
 * using the {@link PKIXParameters#addCertPathChecker addCertPathChecker}
 * or {@link PKIXParameters#setCertPathCheckers setCertPathCheckers} method,
 * and then the {@code PKIXParameters} is passed along with the {@code CertPath}
 * to be validated to the {@link CertPathValidator#validate validate} method
 * of a PKIX {@code CertPathValidator}. When supplying a revocation checker in
 * this manner, it will be used to check revocation irrespective of the setting
 * of the {@link PKIXParameters#isRevocationEnabled RevocationEnabled} flag.
 * Similarly, a {@code PKIXRevocationChecker} may be added to a
 * {@code PKIXBuilderParameters} object for use with a PKIX
 * {@code CertPathBuilder}.
 *
 * <p>Note that when a {@code PKIXRevocationChecker} is added to
 * {@code PKIXParameters}, it clones the {@code PKIXRevocationChecker};
 * thus any subsequent modifications to the {@code PKIXRevocationChecker}
 * have no effect.
 *
 * <p>Any parameter that is not set (or is set to {@code null}) will be set to
 * the default value for that parameter.
 *
 * <p><b>Concurrent Access</b>
 *
 * <p>Unless otherwise specified, the methods defined in this class are not
 * thread-safe. Multiple threads that need to access a single object
 * concurrently should synchronize amongst themselves and provide the
 * necessary locking. Multiple threads each manipulating separate objects
 * need not synchronize.
 *
 * <p>
 *  用于使用PKIX算法检查证书的吊销状态的{@code PKIXCertPathChecker}
 * 
 *  <p> {@code PKIXRevocationChecker}使用在线证书状态协议(OCSP)或证书吊销列表(CRL)来检查证书的吊销状态。
 * OCSP在RFC 2560中描述,并且是用于确定证书A的状态的网络协议CRL是标识撤销证书的带时间戳的列表,RFC 5280描述了用于使用CRL确定证书的撤销状态的算法。
 * 
 * <p>每个{@code PKIXRevocationChecker}必须能够检查具有OCSP和CRL的证书的吊销状态默认情况下,OCSP是检查吊销状态的首选机制,CRL作为回退机制但是,此首选项可以切换
 * 为带有{@link Option#PREFER_CRLS PREFER_CRLS}选项的CRL此外,可以使用{@link Option#NO_FALLBACK NO_FALLBACK}选项禁用回退机制。
 * 
 * <p>通过调用PKIX {@code CertPathValidator}的{@link CertPathValidator#getRevocationChecker getRevocationChecker}
 * 方法获得{@code PKIXRevocationChecker}。
 * 可以设置特定于撤销的其他参数和选项(通过调用{@link #setOcspResponder setOcspResponder}方法)使用{@link PKIXParameters#addCertPathChecker addCertPathChecker}
 * 或{@link PKIXParameters#setCertPathCheckers setCertPathCheckers}方法将{@code PKIXRevocationChecker}添加到{@code PKIXParameters}
 * 对象,然后使用{@code PKIXParameters }与要验证的{@code CertPath}一起传递到PKIX {@code CertPathValidator}的{@link CertPathValidator#validate validate}
 * 方法,当以这种方式提供撤销检查器时,它将用于检查撤销,而不管{@link PKIXParameters#isRevocationEnabled RevocationEnabled}标志的设置如何,{@code PKIXRevocationChecker}
 * 可以添加到{@code PKIXBuilderParameters}对象用于与PKIX {@code CertPathBuilder}。
 * 
 * <p>请注意,当{@code PKIXRevocationChecker}添加到{@code PKIXParameters}时,它会克隆{@code PKIXRevocationChecker};因此对
 * {@code PKIXRevocationChecker}的任何后续修改都没有效果。
 * 
 *  <p>未设置(或设置为{@code null})的任何参数都将设置为该参数的默认值
 * 
 *  <p> <b>并发访问</b>
 * 
 *  <p>除非另有说明,否则此类中定义的方法不是线程安全的需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定多个线程,每个操作单独的对象不需要同步
 * 
 * 
 * @since 1.8
 *
 * @see <a href="http://www.ietf.org/rfc/rfc2560.txt"><i>RFC&nbsp;2560: X.509
 * Internet Public Key Infrastructure Online Certificate Status Protocol -
 * OCSP</i></a>, <br><a
 * href="http://www.ietf.org/rfc/rfc5280.txt"><i>RFC&nbsp;5280: Internet X.509
 * Public Key Infrastructure Certificate and Certificate Revocation List (CRL)
 * Profile</i></a>
 */
public abstract class PKIXRevocationChecker extends PKIXCertPathChecker {
    private URI ocspResponder;
    private X509Certificate ocspResponderCert;
    private List<Extension> ocspExtensions = Collections.<Extension>emptyList();
    private Map<X509Certificate, byte[]> ocspResponses = Collections.emptyMap();
    private Set<Option> options = Collections.emptySet();

    /**
     * Default constructor.
     * <p>
     *  默认构造函数
     * 
     */
    protected PKIXRevocationChecker() {}

    /**
     * Sets the URI that identifies the location of the OCSP responder. This
     * overrides the {@code ocsp.responderURL} security property and any
     * responder specified in a certificate's Authority Information Access
     * Extension, as defined in RFC 5280.
     *
     * <p>
     * 设置标识OCSP响应程序位置的URI这将覆盖{@code ocspresponderURL}安全属性和在证书的Authority信息访问扩展中指定的任何响应程序,如RFC 5280中定义
     * 
     * 
     * @param uri the responder URI
     */
    public void setOcspResponder(URI uri) {
        this.ocspResponder = uri;
    }

    /**
     * Gets the URI that identifies the location of the OCSP responder. This
     * overrides the {@code ocsp.responderURL} security property. If this
     * parameter or the {@code ocsp.responderURL} property is not set, the
     * location is determined from the certificate's Authority Information
     * Access Extension, as defined in RFC 5280.
     *
     * <p>
     *  获取标识OCSP响应程序位置的URI这将覆盖{@code ocspresponderURL}安全属性如果未设置此参数或{@code ocspresponderURL}属性,则根据定义的证书的Autho
     * rity信息访问扩展来确定位置在RFC 5280。
     * 
     * 
     * @return the responder URI, or {@code null} if not set
     */
    public URI getOcspResponder() {
        return ocspResponder;
    }

    /**
     * Sets the OCSP responder's certificate. This overrides the
     * {@code ocsp.responderCertSubjectName},
     * {@code ocsp.responderCertIssuerName},
     * and {@code ocsp.responderCertSerialNumber} security properties.
     *
     * <p>
     *  设置OCSP响应程序的证书这将覆盖{@code ocspresponderCertSubjectName},{@code ocspresponderCertIssuerName}和{@code ocspresponderCertSerialNumber}
     * 安全属性。
     * 
     * 
     * @param cert the responder's certificate
     */
    public void setOcspResponderCert(X509Certificate cert) {
        this.ocspResponderCert = cert;
    }

    /**
     * Gets the OCSP responder's certificate. This overrides the
     * {@code ocsp.responderCertSubjectName},
     * {@code ocsp.responderCertIssuerName},
     * and {@code ocsp.responderCertSerialNumber} security properties. If this
     * parameter or the aforementioned properties are not set, then the
     * responder's certificate is determined as specified in RFC 2560.
     *
     * <p>
     * 获取OCSP响应者的证书这将覆盖{@code ocspresponderCertSubjectName},{@code ocspresponderCertIssuerName}和{@code ocspresponderCertSerialNumber}
     * 安全属性如果未设置此参数或上述属性,则响应者的证书根据RFC 2560。
     * 
     * 
     * @return the responder's certificate, or {@code null} if not set
     */
    public X509Certificate getOcspResponderCert() {
        return ocspResponderCert;
    }

    // request extensions; single extensions not supported
    /**
     * Sets the optional OCSP request extensions.
     *
     * <p>
     *  设置可选的OCSP请求扩展
     * 
     * 
     * @param extensions a list of extensions. The list is copied to protect
     *        against subsequent modification.
     */
    public void setOcspExtensions(List<Extension> extensions)
    {
        this.ocspExtensions = (extensions == null)
                              ? Collections.<Extension>emptyList()
                              : new ArrayList<Extension>(extensions);
    }

    /**
     * Gets the optional OCSP request extensions.
     *
     * <p>
     *  获取可选的OCSP请求扩展
     * 
     * 
     * @return an unmodifiable list of extensions. The list is empty if no
     *         extensions have been specified.
     */
    public List<Extension> getOcspExtensions() {
        return Collections.unmodifiableList(ocspExtensions);
    }

    /**
     * Sets the OCSP responses. These responses are used to determine
     * the revocation status of the specified certificates when OCSP is used.
     *
     * <p>
     *  设置OCSP响应这些响应用于在使用OCSP时确定指定证书的撤销状态
     * 
     * 
     * @param responses a map of OCSP responses. Each key is an
     *        {@code X509Certificate} that maps to the corresponding
     *        DER-encoded OCSP response for that certificate. A deep copy of
     *        the map is performed to protect against subsequent modification.
     */
    public void setOcspResponses(Map<X509Certificate, byte[]> responses)
    {
        if (responses == null) {
            this.ocspResponses = Collections.<X509Certificate, byte[]>emptyMap();
        } else {
            Map<X509Certificate, byte[]> copy = new HashMap<>(responses.size());
            for (Map.Entry<X509Certificate, byte[]> e : responses.entrySet()) {
                copy.put(e.getKey(), e.getValue().clone());
            }
            this.ocspResponses = copy;
        }
    }

    /**
     * Gets the OCSP responses. These responses are used to determine
     * the revocation status of the specified certificates when OCSP is used.
     *
     * <p>
     *  获取OCSP响应这些响应用于在使用OCSP时确定指定证书的吊销状态
     * 
     * 
     * @return a map of OCSP responses. Each key is an
     *        {@code X509Certificate} that maps to the corresponding
     *        DER-encoded OCSP response for that certificate. A deep copy of
     *        the map is returned to protect against subsequent modification.
     *        Returns an empty map if no responses have been specified.
     */
    public Map<X509Certificate, byte[]> getOcspResponses() {
        Map<X509Certificate, byte[]> copy = new HashMap<>(ocspResponses.size());
        for (Map.Entry<X509Certificate, byte[]> e : ocspResponses.entrySet()) {
            copy.put(e.getKey(), e.getValue().clone());
        }
        return copy;
    }

    /**
     * Sets the revocation options.
     *
     * <p>
     *  设置撤销选项
     * 
     * 
     * @param options a set of revocation options. The set is copied to protect
     *        against subsequent modification.
     */
    public void setOptions(Set<Option> options) {
        this.options = (options == null)
                       ? Collections.<Option>emptySet()
                       : new HashSet<Option>(options);
    }

    /**
     * Gets the revocation options.
     *
     * <p>
     *  获取撤销选项
     * 
     * 
     * @return an unmodifiable set of revocation options. The set is empty if
     *         no options have been specified.
     */
    public Set<Option> getOptions() {
        return Collections.unmodifiableSet(options);
    }

    /**
     * Returns a list containing the exceptions that are ignored by the
     * revocation checker when the {@link Option#SOFT_FAIL SOFT_FAIL} option
     * is set. The list is cleared each time {@link #init init} is called.
     * The list is ordered in ascending order according to the certificate
     * index returned by {@link CertPathValidatorException#getIndex getIndex}
     * method of each entry.
     * <p>
     * An implementation of {@code PKIXRevocationChecker} is responsible for
     * adding the ignored exceptions to the list.
     *
     * <p>
     * 返回包含在设置{@link选项#SOFT_FAIL SOFT_FAIL}选项时被撤销检查器忽略的异常的列表每次调用{@link #init init}时将清除列表。
     * 列表按升序排序每个条目的{@link CertPathValidatorException#getIndex getIndex}方法返回的证书索引。
     * <p>
     *  {@code PKIXRevocationChecker}的实现负责将忽略的异常添加到列表中
     * 
     * 
     * @return an unmodifiable list containing the ignored exceptions. The list
     *         is empty if no exceptions have been ignored.
     */
    public abstract List<CertPathValidatorException> getSoftFailExceptions();

    @Override
    public PKIXRevocationChecker clone() {
        PKIXRevocationChecker copy = (PKIXRevocationChecker)super.clone();
        copy.ocspExtensions = new ArrayList<>(ocspExtensions);
        copy.ocspResponses = new HashMap<>(ocspResponses);
        // deep-copy the encoded responses, since they are mutable
        for (Map.Entry<X509Certificate, byte[]> entry :
                 copy.ocspResponses.entrySet())
        {
            byte[] encoded = entry.getValue();
            entry.setValue(encoded.clone());
        }
        copy.options = new HashSet<>(options);
        return copy;
    }

    /**
     * Various revocation options that can be specified for the revocation
     * checking mechanism.
     * <p>
     *  可以为撤销检查机制指定的各种撤销选项
     * 
     */
    public enum Option {
        /**
         * Only check the revocation status of end-entity certificates.
         * <p>
         *  只检查终端实体证书的撤销状态
         * 
         */
        ONLY_END_ENTITY,
        /**
         * Prefer CRLs to OSCP. The default behavior is to prefer OCSP. Each
         * PKIX implementation should document further details of their
         * specific preference rules and fallback policies.
         * <p>
         * 优先CRL到OSCP默认行为是喜欢OCSP每个PKIX实现应该记录他们的特定偏好规则和回退策略的进一步细节
         * 
         */
        PREFER_CRLS,
        /**
         * Disable the fallback mechanism.
         * <p>
         *  停用回退机制
         * 
         */
        NO_FALLBACK,
        /**
         * Allow revocation check to succeed if the revocation status cannot be
         * determined for one of the following reasons:
         * <ul>
         *  <li>The CRL or OCSP response cannot be obtained because of a
         *      network error.
         *  <li>The OCSP responder returns one of the following errors
         *      specified in section 2.3 of RFC 2560: internalError or tryLater.
         * </ul><br>
         * Note that these conditions apply to both OCSP and CRLs, and unless
         * the {@code NO_FALLBACK} option is set, the revocation check is
         * allowed to succeed only if both mechanisms fail under one of the
         * conditions as stated above.
         * Exceptions that cause the network errors are ignored but can be
         * later retrieved by calling the
         * {@link #getSoftFailExceptions getSoftFailExceptions} method.
         * <p>
         *  如果由于以下原因之一而无法确定吊销状态,则允许吊销检查成功：
         * <ul>
         * <li>由于网络错误,无法获取CRL或OCSP响应<li> OCSP响应程序返回RFC 2560第23节中指定的以下错误之一：internalError或tryLater </ul> <br>请注意,这
         */
        SOFT_FAIL
    }
}
