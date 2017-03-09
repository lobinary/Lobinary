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
 * path validation algorithm.
 *
 * <p>Instances of {@code PKIXCertPathValidatorResult} are returned by the
 * {@link CertPathValidator#validate validate} method of
 * {@code CertPathValidator} objects implementing the PKIX algorithm.
 *
 * <p> All {@code PKIXCertPathValidatorResult} objects contain the
 * valid policy tree and subject public key resulting from the
 * validation algorithm, as well as a {@code TrustAnchor} describing
 * the certification authority (CA) that served as a trust anchor for the
 * certification path.
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
 *  此类表示PKIX认证路径验证算法的成功结果。
 * 
 *  <p> {@code PKIXCertPathValidatorResult}的实例由实现PKIX算法的{@code CertPathValidator}对象的{@link CertPathValidator#validate validate}
 * 方法返回。
 * 
 *  <p>所有{@code PKIXCertPathValidatorResult}对象都包含由验证算法产生的有效策略树和主体公钥,以及描述用作证书的信任锚的证书颁发机构(CA)的{@code TrustAnchor}
 * 路径。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see CertPathValidatorResult
 *
 * @since       1.4
 * @author      Yassir Elley
 * @author      Sean Mullan
 */
public class PKIXCertPathValidatorResult implements CertPathValidatorResult {

    private TrustAnchor trustAnchor;
    private PolicyNode policyTree;
    private PublicKey subjectPublicKey;

    /**
     * Creates an instance of {@code PKIXCertPathValidatorResult}
     * containing the specified parameters.
     *
     * <p>
     *  创建包含指定参数的{@code PKIXCertPathValidatorResult}实例。
     * 
     * 
     * @param trustAnchor a {@code TrustAnchor} describing the CA that
     * served as a trust anchor for the certification path
     * @param policyTree the immutable valid policy tree, or {@code null}
     * if there are no valid policies
     * @param subjectPublicKey the public key of the subject
     * @throws NullPointerException if the {@code subjectPublicKey} or
     * {@code trustAnchor} parameters are {@code null}
     */
    public PKIXCertPathValidatorResult(TrustAnchor trustAnchor,
        PolicyNode policyTree, PublicKey subjectPublicKey)
    {
        if (subjectPublicKey == null)
            throw new NullPointerException("subjectPublicKey must be non-null");
        if (trustAnchor == null)
            throw new NullPointerException("trustAnchor must be non-null");
        this.trustAnchor = trustAnchor;
        this.policyTree = policyTree;
        this.subjectPublicKey = subjectPublicKey;
    }

    /**
     * Returns the {@code TrustAnchor} describing the CA that served
     * as a trust anchor for the certification path.
     *
     * <p>
     *  返回描述用作认证路径的信任锚的CA的{@code TrustAnchor}。
     * 
     * 
     * @return the {@code TrustAnchor} (never {@code null})
     */
    public TrustAnchor getTrustAnchor() {
        return trustAnchor;
    }

    /**
     * Returns the root node of the valid policy tree resulting from the
     * PKIX certification path validation algorithm. The
     * {@code PolicyNode} object that is returned and any objects that
     * it returns through public methods are immutable.
     *
     * <p>Most applications will not need to examine the valid policy tree.
     * They can achieve their policy processing goals by setting the
     * policy-related parameters in {@code PKIXParameters}. However, more
     * sophisticated applications, especially those that process policy
     * qualifiers, may need to traverse the valid policy tree using the
     * {@link PolicyNode#getParent PolicyNode.getParent} and
     * {@link PolicyNode#getChildren PolicyNode.getChildren} methods.
     *
     * <p>
     *  返回由PKIX认证路径验证算法生成的有效策略树的根节点。返回的{@code PolicyNode}对象和通过公共方法返回的任何对象都是不可变的。
     * 
     * <p>大多数应用程序不需要检查有效的策略树。他们可以通过在{@code PKIXParameters}中设置策略相关参数来实现其策略处理目标。
     * 但是,更复杂的应用程序,特别是那些处理策略限定符的应用程序,可能需要使用{@link PolicyNode#getParent PolicyNode.getParent}和{@link PolicyNode#getChildren PolicyNode.getChildren}
     * 方法遍历有效的策略树。
     * <p>大多数应用程序不需要检查有效的策略树。他们可以通过在{@code PKIXParameters}中设置策略相关参数来实现其策略处理目标。
     * 
     * 
     * @return the root node of the valid policy tree, or {@code null}
     * if there are no valid policies
     */
    public PolicyNode getPolicyTree() {
        return policyTree;
    }

    /**
     * Returns the public key of the subject (target) of the certification
     * path, including any inherited public key parameters if applicable.
     *
     * <p>
     *  返回认证路径主题(目标)的公钥,包括任何继承的公钥参数(如果适用)。
     * 
     * 
     * @return the public key of the subject (never {@code null})
     */
    public PublicKey getPublicKey() {
        return subjectPublicKey;
    }

    /**
     * Returns a copy of this object.
     *
     * <p>
     *  返回此对象的副本。
     * 
     * 
     * @return the copy
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            /* Cannot happen */
            throw new InternalError(e.toString(), e);
        }
    }

    /**
     * Return a printable representation of this
     * {@code PKIXCertPathValidatorResult}.
     *
     * <p>
     *  返回此{@code PKIXCertPathValidatorResult}的可打印表示。
     * 
     * @return a {@code String} describing the contents of this
     *         {@code PKIXCertPathValidatorResult}
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("PKIXCertPathValidatorResult: [\n");
        sb.append("  Trust Anchor: " + trustAnchor.toString() + "\n");
        sb.append("  Policy Tree: " + String.valueOf(policyTree) + "\n");
        sb.append("  Subject Public Key: " + subjectPublicKey + "\n");
        sb.append("]");
        return sb.toString();
    }
}
