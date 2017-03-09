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
 * A specification of {@code CertStore} parameters.
 * <p>
 * The purpose of this interface is to group (and provide type safety for)
 * all {@code CertStore} parameter specifications. All
 * {@code CertStore} parameter specifications must implement this
 * interface.
 * <p>
 * Typically, a {@code CertStoreParameters} object is passed as a parameter
 * to one of the {@link CertStore#getInstance CertStore.getInstance} methods.
 * The {@code getInstance} method returns a {@code CertStore} that
 * is used for retrieving {@code Certificate}s and {@code CRL}s. The
 * {@code CertStore} that is returned is initialized with the specified
 * parameters. The type of parameters needed may vary between different types
 * of {@code CertStore}s.
 *
 * <p>
 *  {@code CertStore}参数的规范。
 * <p>
 *  此接口的目的是对所有{@code CertStore}参数规范进行分组(并提供类型安全性)。所有{@code CertStore}参数规范必须实现此接口。
 * <p>
 *  通常,{@code CertStoreParameters}对象作为参数传递到{@link CertStore#getInstance CertStore.getInstance}方法之一。
 *  {@code getInstance}方法返回用于检索{@code Certificate}和{@code CRL}的{@code CertStore}。
 * 返回的{@code CertStore}将使用指定的参数初始化。所需的参数类型可能因不同类型的{@code CertStore}而异。
 * 
 * 
 * @see CertStore#getInstance
 *
 * @since       1.4
 * @author      Steve Hanna
 */
public interface CertStoreParameters extends Cloneable {

    /**
     * Makes a copy of this {@code CertStoreParameters}.
     * <p>
     * The precise meaning of "copy" may depend on the class of
     * the {@code CertStoreParameters} object. A typical implementation
     * performs a "deep copy" of this object, but this is not an absolute
     * requirement. Some implementations may perform a "shallow copy" of some
     * or all of the fields of this object.
     * <p>
     * Note that the {@code CertStore.getInstance} methods make a copy
     * of the specified {@code CertStoreParameters}. A deep copy
     * implementation of {@code clone} is safer and more robust, as it
     * prevents the caller from corrupting a shared {@code CertStore} by
     * subsequently modifying the contents of its initialization parameters.
     * However, a shallow copy implementation of {@code clone} is more
     * appropriate for applications that need to hold a reference to a
     * parameter contained in the {@code CertStoreParameters}. For example,
     * a shallow copy clone allows an application to release the resources of
     * a particular {@code CertStore} initialization parameter immediately,
     * rather than waiting for the garbage collection mechanism. This should
     * be done with the utmost care, since the {@code CertStore} may still
     * be in use by other threads.
     * <p>
     * Each subclass should state the precise behavior of this method so
     * that users and developers know what to expect.
     *
     * <p>
     *  制作此{@code CertStoreParameters}的副本。
     * <p>
     *  "copy"的精确含义可能取决于{@code CertStoreParameters}对象的类。典型的实现执行该对象的"深度复制",但这不是绝对的要求。
     * 一些实现可以执行该对象的一些或所有字段的"浅拷贝"。
     * <p>
     * 请注意,{@code CertStore.getInstance}方法创建指定的{@code CertStoreParameters}的副本。
     * 
     * @return a copy of this {@code CertStoreParameters}
     */
    Object clone();
}
