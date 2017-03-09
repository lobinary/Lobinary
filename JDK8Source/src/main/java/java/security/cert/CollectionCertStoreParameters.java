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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Parameters used as input for the Collection {@code CertStore}
 * algorithm.
 * <p>
 * This class is used to provide necessary configuration parameters
 * to implementations of the Collection {@code CertStore}
 * algorithm. The only parameter included in this class is the
 * {@code Collection} from which the {@code CertStore} will
 * retrieve certificates and CRLs.
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
 *  用作Collection {@code CertStore}算法的输入的参数。
 * <p>
 *  此类用于向Collection {@code CertStore}算法的实现提供必要的配置参数。
 * 此类中包含的唯一参数是{@code Collection},{@code CertStore}将从中检索证书和CRL。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @since       1.4
 * @author      Steve Hanna
 * @see         java.util.Collection
 * @see         CertStore
 */
public class CollectionCertStoreParameters
    implements CertStoreParameters {

    private Collection<?> coll;

    /**
     * Creates an instance of {@code CollectionCertStoreParameters}
     * which will allow certificates and CRLs to be retrieved from the
     * specified {@code Collection}. If the specified
     * {@code Collection} contains an object that is not a
     * {@code Certificate} or {@code CRL}, that object will be
     * ignored by the Collection {@code CertStore}.
     * <p>
     * The {@code Collection} is <b>not</b> copied. Instead, a
     * reference is used. This allows the caller to subsequently add or
     * remove {@code Certificates} or {@code CRL}s from the
     * {@code Collection}, thus changing the set of
     * {@code Certificates} or {@code CRL}s available to the
     * Collection {@code CertStore}. The Collection {@code CertStore}
     * will not modify the contents of the {@code Collection}.
     * <p>
     * If the {@code Collection} will be modified by one thread while
     * another thread is calling a method of a Collection {@code CertStore}
     * that has been initialized with this {@code Collection}, the
     * {@code Collection} must have fail-fast iterators.
     *
     * <p>
     *  创建{@code CollectionCertStoreParameters}的实例,这将允许从指定的{@code集合}检索证书和CRL。
     * 如果指定的{@code集合}包含不是{@code Certificate}或{@code CRL}的对象,该集合{@code CertStore}将会忽略该对象。
     * <p>
     * {@code集合}已复制<b>不</b>。相反,使用引用。
     * 这样,调用者可以随后从{@code集合}中添加或删除{@code Certificates}或{@code CRL},从而将集合{@code Certificates}或{@code CRL}更改为集合
     * {@code CertStore}。
     * {@code集合}已复制<b>不</b>。相反,使用引用。集合{@code CertStore}不会修改{@code集合}的内容。
     * <p>
     *  如果{@code Collection}将被一个线程修改,而另一个线程正在调用使用此{@code集合}初始化的Collection {@code CertStore}的方法,则{@code Collection}
     * 快速迭代器。
     * 
     * 
     * @param collection a {@code Collection} of
     *        {@code Certificate}s and {@code CRL}s
     * @exception NullPointerException if {@code collection} is
     * {@code null}
     */
    public CollectionCertStoreParameters(Collection<?> collection) {
        if (collection == null)
            throw new NullPointerException();
        coll = collection;
    }

    /**
     * Creates an instance of {@code CollectionCertStoreParameters} with
     * the default parameter values (an empty and immutable
     * {@code Collection}).
     * <p>
     *  使用默认参数值(一个空的和不可变的{@code Collection})创建{@code CollectionCertStoreParameters}的实例。
     * 
     */
    public CollectionCertStoreParameters() {
        coll = Collections.EMPTY_SET;
    }

    /**
     * Returns the {@code Collection} from which {@code Certificate}s
     * and {@code CRL}s are retrieved. This is <b>not</b> a copy of the
     * {@code Collection}, it is a reference. This allows the caller to
     * subsequently add or remove {@code Certificates} or
     * {@code CRL}s from the {@code Collection}.
     *
     * <p>
     *  返回检索到{@code Certificate}和{@code CRL}的{@code Collection}。这是<b>不是</b> {@code集合}的副本,它是一个参考。
     * 这允许调用者随后从{@code Collection}添加或删除{@code Certificates}或{@code CRL}。
     * 
     * 
     * @return the {@code Collection} (never null)
     */
    public Collection<?> getCollection() {
        return coll;
    }

    /**
     * Returns a copy of this object. Note that only a reference to the
     * {@code Collection} is copied, and not the contents.
     *
     * <p>
     *  返回此对象的副本。请注意,只会复制对{@code Collection}的引用,而不是内容。
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
     * Returns a formatted string describing the parameters.
     *
     * <p>
     *  返回描述参数的格式化字符串。
     * 
     * @return a formatted string describing the parameters
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("CollectionCertStoreParameters: [\n");
        sb.append("  collection: " + coll + "\n");
        sb.append("]");
        return sb.toString();
    }
}
