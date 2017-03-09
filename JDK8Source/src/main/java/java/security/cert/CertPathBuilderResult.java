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

/**
 * A specification of the result of a certification path builder algorithm.
 * All results returned by the {@link CertPathBuilder#build
 * CertPathBuilder.build} method must implement this interface.
 * <p>
 * At a minimum, a {@code CertPathBuilderResult} contains the
 * {@code CertPath} built by the {@code CertPathBuilder} instance.
 * Implementations of this interface may add methods to return implementation
 * or algorithm specific information, such as debugging information or
 * certification path validation results.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this interface are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * <p>
 *  认证路径构建器算法的结果的规范。 {@link CertPathBuilder#build CertPathBuilder.build}方法返回的所有结果都必须实现此接口。
 * <p>
 *  至少,{@code CertPathBuilderResult}包含由{@code CertPathBuilder}实例构建的{@code CertPath}。
 * 该接口的实现可以添加返回实现或算法特定信息的方法,诸如调试信息或认证路径验证结果。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此接口中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * @see CertPathBuilder
 *
 * @since       1.4
 * @author      Sean Mullan
 */
public interface CertPathBuilderResult extends Cloneable {

    /**
     * Returns the built certification path.
     *
     * <p>
     * 
     * 
     * @return the certification path (never {@code null})
     */
    CertPath getCertPath();

    /**
     * Makes a copy of this {@code CertPathBuilderResult}. Changes to the
     * copy will not affect the original and vice versa.
     *
     * <p>
     *  返回已构建的认证路径。
     * 
     * 
     * @return a copy of this {@code CertPathBuilderResult}
     */
    Object clone();
}
