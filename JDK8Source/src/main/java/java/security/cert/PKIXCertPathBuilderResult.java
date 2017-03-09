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

import java.security.PublicKey;

/**
 * This class represents the successful result of the PKIX certification
 * path builder algorithm. All certification paths that are built and
 * returned using this algorithm are also validated according to the PKIX
 * certification path validation algorithm.
 *
 * <p>Instances of {@code PKIXCertPathBuilderResult} are returned by
 * the {@code build} method of {@code CertPathBuilder}
 * objects implementing the PKIX algorithm.
 *
 * <p>All {@code PKIXCertPathBuilderResult} objects contain the
 * certification path constructed by the build algorithm, the
 * valid policy tree and subject public key resulting from the build
 * algorithm, and a {@code TrustAnchor} describing the certification
 * authority (CA) that served as a trust anchor for the certification path.
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
 *  此类表示PKIX认证路径构建器算法的成功结果。使用此算法构建和返回的所有认证路径也根据PKIX认证路径验证算法进行验证。
 * 
 *  <p> {@code PKIXCertPathBuilderResult}的实例由实现PKIX算法的{@code CertPathBuilder}对象的{@code build}方法返回。
 * 
 *  <p>所有{@code PKIXCertPathBuilderResult}对象都包含由构建算法构建的认证路径,由构建算法生成的有效策略树和主题公钥以及描述所提供的认证中心(CA)的{@code TrustAnchor}
 * 作为认证路径的信任锚点。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CertPathBuilderResult
 *
 * @since       1.4
 * @author      Anne Anderson
 */
public class PKIXCertPathBuilderResult extends PKIXCertPathValidatorResult
    implements CertPathBuilderResult {

    private CertPath certPath;

    /**
     * Creates an instance of {@code PKIXCertPathBuilderResult}
     * containing the specified parameters.
     *
     * <p>
     * 
     * @param certPath the validated {@code CertPath}
     * @param trustAnchor a {@code TrustAnchor} describing the CA that
     * served as a trust anchor for the certification path
     * @param policyTree the immutable valid policy tree, or {@code null}
     * if there are no valid policies
     * @param subjectPublicKey the public key of the subject
     * @throws NullPointerException if the {@code certPath},
     * {@code trustAnchor} or {@code subjectPublicKey} parameters
     * are {@code null}
     */
    public PKIXCertPathBuilderResult(CertPath certPath,
        TrustAnchor trustAnchor, PolicyNode policyTree,
        PublicKey subjectPublicKey)
    {
        super(trustAnchor, policyTree, subjectPublicKey);
        if (certPath == null)
            throw new NullPointerException("certPath must be non-null");
        this.certPath = certPath;
    }

    /**
     * Returns the built and validated certification path. The
     * {@code CertPath} object does not include the trust anchor.
     * Instead, use the {@link #getTrustAnchor() getTrustAnchor()} method to
     * obtain the {@code TrustAnchor} that served as the trust anchor
     * for the certification path.
     *
     * <p>
     *  创建包含指定参数的{@code PKIXCertPathBuilderResult}实例。
     * 
     * 
     * @return the built and validated {@code CertPath} (never
     * {@code null})
     */
    public CertPath getCertPath() {
        return certPath;
    }

    /**
     * Return a printable representation of this
     * {@code PKIXCertPathBuilderResult}.
     *
     * <p>
     * 返回已构建并验证的认证路径。 {@code CertPath}对象不包括信任锚。
     * 而应使用{@link #getTrustAnchor()getTrustAnchor()}方法来获取用作认证路径的信任锚的{@code TrustAnchor}。
     * 
     * 
     * @return a {@code String} describing the contents of this
     *         {@code PKIXCertPathBuilderResult}
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("PKIXCertPathBuilderResult: [\n");
        sb.append("  Certification Path: " + certPath + "\n");
        sb.append("  Trust Anchor: " + getTrustAnchor().toString() + "\n");
        sb.append("  Policy Tree: " + String.valueOf(getPolicyTree()) + "\n");
        sb.append("  Subject Public Key: " + getPublicKey() + "\n");
        sb.append("]");
        return sb.toString();
    }
}
