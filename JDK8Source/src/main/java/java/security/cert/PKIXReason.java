/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The {@code PKIXReason} enumerates the potential PKIX-specific reasons
 * that an X.509 certification path may be invalid according to the PKIX
 * (RFC 3280) standard. These reasons are in addition to those of the
 * {@code CertPathValidatorException.BasicReason} enumeration.
 *
 * <p>
 *  {@code PKIXReason}枚举了潜在的PKIX特定原因,根据PKIX(RFC 3280)标准,X.509认证路径可能无效。
 * 这些原因是{@code CertPathValidatorException.BasicReason}枚举的原因。
 * 
 * 
 * @since 1.7
 */
public enum PKIXReason implements CertPathValidatorException.Reason {
    /**
     * The certificate does not chain correctly.
     * <p>
     *  证书链接不正确。
     * 
     */
    NAME_CHAINING,

    /**
     * The certificate's key usage is invalid.
     * <p>
     *  证书的密钥用法无效。
     * 
     */
    INVALID_KEY_USAGE,

    /**
     * The policy constraints have been violated.
     * <p>
     *  违反了政策约束。
     * 
     */
    INVALID_POLICY,

    /**
     * No acceptable trust anchor found.
     * <p>
     *  未找到可接受的信任锚。
     * 
     */
    NO_TRUST_ANCHOR,

    /**
     * The certificate contains one or more unrecognized critical
     * extensions.
     * <p>
     *  证书包含一个或多个无法识别的关键扩展。
     * 
     */
    UNRECOGNIZED_CRIT_EXT,

    /**
     * The certificate is not a CA certificate.
     * <p>
     *  证书不是CA证书。
     * 
     */
    NOT_CA_CERT,

    /**
     * The path length constraint has been violated.
     * <p>
     *  路径长度约束已被违反。
     * 
     */
    PATH_TOO_LONG,

    /**
     * The name constraints have been violated.
     * <p>
     *  名称约束已被违反。
     */
    INVALID_NAME
}
