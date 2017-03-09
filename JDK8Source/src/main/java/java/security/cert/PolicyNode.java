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

import java.util.Iterator;
import java.util.Set;

/**
 * An immutable valid policy tree node as defined by the PKIX certification
 * path validation algorithm.
 *
 * <p>One of the outputs of the PKIX certification path validation
 * algorithm is a valid policy tree, which includes the policies that
 * were determined to be valid, how this determination was reached,
 * and any policy qualifiers encountered. This tree is of depth
 * <i>n</i>, where <i>n</i> is the length of the certification
 * path that has been validated.
 *
 * <p>Most applications will not need to examine the valid policy tree.
 * They can achieve their policy processing goals by setting the
 * policy-related parameters in {@code PKIXParameters}. However,
 * the valid policy tree is available for more sophisticated applications,
 * especially those that process policy qualifiers.
 *
 * <p>{@link PKIXCertPathValidatorResult#getPolicyTree()
 * PKIXCertPathValidatorResult.getPolicyTree} returns the root node of the
 * valid policy tree. The tree can be traversed using the
 * {@link #getChildren getChildren} and {@link #getParent getParent} methods.
 * Data about a particular node can be retrieved using other methods of
 * {@code PolicyNode}.
 *
 * <p><b>Concurrent Access</b>
 * <p>All {@code PolicyNode} objects must be immutable and
 * thread-safe. Multiple threads may concurrently invoke the methods defined
 * in this class on a single {@code PolicyNode} object (or more than one)
 * with no ill effects. This stipulation applies to all public fields and
 * methods of this class and any added or overridden by subclasses.
 *
 * <p>
 *  由PKIX认证路径验证算法定义的不可变的有效策略树节点。
 * 
 *  <p> PKIX认证路径验证算法的输出之一是有效的策略树,其中包括确定为有效的策略,如何达到此确定以及遇到的任何策略限定符。
 * 该树是深度<i> </i>,其中<i> n </i>是已经验证的认证路径的长度。
 * 
 *  <p>大多数应用程序不需要检查有效的策略树。他们可以通过在{@code PKIXParameters}中设置策略相关参数来实现其策略处理目标。
 * 但是,有效的策略树可用于更复杂的应用程序,特别是那些处理策略限定符的应用程序。
 * 
 *  <p> {@ link PKIXCertPathValidatorResult#getPolicyTree()PKIXCertPathValidatorResult.getPolicyTree}返回有
 * 效策略树的根节点。
 * 可以使用{@link #getChildren getChildren}和{@link #getParent getParent}方法遍历树。
 * 有关特定节点的数据可以使用{@code PolicyNode}的其他方法检索。
 * 
 * <p> <b>并发访问</b> <p>所有{@code PolicyNode}对象必须是不可变的和线程安全的。
 * 多线程可以并发地在单个{@code PolicyNode}对象(或多个线程)上调用此类中定义的方法,而没有不良影响。这一规定适用于该类的所有公共领域和方法,以及任何由子类添加或覆盖的。
 * 
 * 
 * @since       1.4
 * @author      Sean Mullan
 */
public interface PolicyNode {

    /**
     * Returns the parent of this node, or {@code null} if this is the
     * root node.
     *
     * <p>
     *  返回此节点的父代,如果这是根节点,则返回{@code null}。
     * 
     * 
     * @return the parent of this node, or {@code null} if this is the
     * root node
     */
    PolicyNode getParent();

    /**
     * Returns an iterator over the children of this node. Any attempts to
     * modify the children of this node through the
     * {@code Iterator}'s remove method must throw an
     * {@code UnsupportedOperationException}.
     *
     * <p>
     *  在此节点的子节点上返回一个迭代器。
     * 任何通过{@code Iterator}的remove方法修改这个节点的子节点的尝试都必须抛出{@code UnsupportedOperationException}。
     * 
     * 
     * @return an iterator over the children of this node
     */
    Iterator<? extends PolicyNode> getChildren();

    /**
     * Returns the depth of this node in the valid policy tree.
     *
     * <p>
     *  返回有效策略树中此节点的深度。
     * 
     * 
     * @return the depth of this node (0 for the root node, 1 for its
     * children, and so on)
     */
    int getDepth();

    /**
     * Returns the valid policy represented by this node.
     *
     * <p>
     *  返回此节点表示的有效策略。
     * 
     * 
     * @return the {@code String} OID of the valid policy
     * represented by this node. For the root node, this method always returns
     * the special anyPolicy OID: "2.5.29.32.0".
     */
    String getValidPolicy();

    /**
     * Returns the set of policy qualifiers associated with the
     * valid policy represented by this node.
     *
     * <p>
     *  返回与此节点表示的有效策略相关联的策略限定符集。
     * 
     * 
     * @return an immutable {@code Set} of
     * {@code PolicyQualifierInfo}s. For the root node, this
     * is always an empty {@code Set}.
     */
    Set<? extends PolicyQualifierInfo> getPolicyQualifiers();

    /**
     * Returns the set of expected policies that would satisfy this
     * node's valid policy in the next certificate to be processed.
     *
     * <p>
     *  返回将在下一个要处理的证书中满足此节点的有效策略的预期策略集。
     * 
     * 
     * @return an immutable {@code Set} of expected policy
     * {@code String} OIDs. For the root node, this method
     * always returns a {@code Set} with one element, the
     * special anyPolicy OID: "2.5.29.32.0".
     */
    Set<String> getExpectedPolicies();

    /**
     * Returns the criticality indicator of the certificate policy extension
     * in the most recently processed certificate.
     *
     * <p>
     *  返回最近处理的证书中的证书策略扩展的临界指标。
     * 
     * @return {@code true} if extension marked critical,
     * {@code false} otherwise. For the root node, {@code false}
     * is always returned.
     */
    boolean isCritical();
}
