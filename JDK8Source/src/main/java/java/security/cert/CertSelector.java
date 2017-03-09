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
 * A selector that defines a set of criteria for selecting
 * {@code Certificate}s. Classes that implement this interface
 * are often used to specify which {@code Certificate}s should
 * be retrieved from a {@code CertStore}.
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
 *  定义用于选择{@code Certificate}的一组条件的选择器。实现此接口的类通常用于指定应从{@code CertStore}检索哪些{@code Certificate}。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此接口中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @see Certificate
 * @see CertStore
 * @see CertStore#getCertificates
 *
 * @author      Steve Hanna
 * @since       1.4
 */
public interface CertSelector extends Cloneable {

    /**
     * Decides whether a {@code Certificate} should be selected.
     *
     * <p>
     * 
     * @param   cert    the {@code Certificate} to be checked
     * @return  {@code true} if the {@code Certificate}
     * should be selected, {@code false} otherwise
     */
    boolean match(Certificate cert);

    /**
     * Makes a copy of this {@code CertSelector}. Changes to the
     * copy will not affect the original and vice versa.
     *
     * <p>
     *  决定是否应选择{@code Certificate}。
     * 
     * 
     * @return a copy of this {@code CertSelector}
     */
    Object clone();
}
