/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The CRLReason enumeration specifies the reason that a certificate
 * is revoked, as defined in <a href="http://www.ietf.org/rfc/rfc3280.txt">
 * RFC 3280: Internet X.509 Public Key Infrastructure Certificate and CRL
 * Profile</a>.
 *
 * <p>
 *  CRLReason枚举指定了证书被撤销的原因,如<a href="http://www.ietf.org/rfc/rfc3280.txt"> RFC 3280：Internet X.509公钥基础结构
 * 证书和CRL中所定义的个人资料</a>。
 * 
 * 
 * @author Sean Mullan
 * @since 1.7
 * @see X509CRLEntry#getRevocationReason
 * @see CertificateRevokedException#getRevocationReason
 */
public enum CRLReason {
    /**
     * This reason indicates that it is unspecified as to why the
     * certificate has been revoked.
     * <p>
     *  此原因表示未指定证书为什么被撤销。
     * 
     */
    UNSPECIFIED,

    /**
     * This reason indicates that it is known or suspected that the
     * certificate subject's private key has been compromised. It applies
     * to end-entity certificates only.
     * <p>
     *  这个原因表明已知或怀疑证书主体的私钥已经泄密。它仅适用于终端实体证书。
     * 
     */
    KEY_COMPROMISE,

    /**
     * This reason indicates that it is known or suspected that the
     * certificate subject's private key has been compromised. It applies
     * to certificate authority (CA) certificates only.
     * <p>
     *  这个原因表明已知或怀疑证书主体的私钥已经泄密。它仅适用于证书颁发机构(CA)证书。
     * 
     */
    CA_COMPROMISE,

    /**
     * This reason indicates that the subject's name or other information
     * has changed.
     * <p>
     *  此原因表示主题的名称或其他信息已更改。
     * 
     */
    AFFILIATION_CHANGED,

    /**
     * This reason indicates that the certificate has been superseded.
     * <p>
     *  此原因表示证书已被替代。
     * 
     */
    SUPERSEDED,

    /**
     * This reason indicates that the certificate is no longer needed.
     * <p>
     *  此原因表示不再需要证书。
     * 
     */
    CESSATION_OF_OPERATION,

    /**
     * This reason indicates that the certificate has been put on hold.
     * <p>
     *  此原因表示证书已被暂停。
     * 
     */
    CERTIFICATE_HOLD,

    /**
     * Unused reason.
     * <p>
     *  未使用的原因。
     * 
     */
    UNUSED,

    /**
     * This reason indicates that the certificate was previously on hold
     * and should be removed from the CRL. It is for use with delta CRLs.
     * <p>
     *  此原因表示证书以前已暂停,应从CRL中删除。它用于delta CRL。
     * 
     */
    REMOVE_FROM_CRL,

    /**
     * This reason indicates that the privileges granted to the subject of
     * the certificate have been withdrawn.
     * <p>
     *  此原因表示授予证书主题的特权已撤消。
     * 
     */
    PRIVILEGE_WITHDRAWN,

    /**
     * This reason indicates that it is known or suspected that the
     * certificate subject's private key has been compromised. It applies
     * to authority attribute (AA) certificates only.
     * <p>
     *  这个原因表明已知或怀疑证书主体的私钥已经泄密。它仅适用于授权属性(AA)证书。
     */
    AA_COMPROMISE
}
