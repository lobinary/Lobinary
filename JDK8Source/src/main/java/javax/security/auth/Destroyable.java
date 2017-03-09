/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth;

/**
 * Objects such as credentials may optionally implement this interface
 * to provide the capability to destroy its contents.
 *
 * <p>
 *  诸如凭证的对象可以可选地实现该接口以提供破坏其内容的能力。
 * 
 * 
 * @see javax.security.auth.Subject
 */
public interface Destroyable {

    /**
     * Destroy this {@code Object}.
     *
     * <p> Sensitive information associated with this {@code Object}
     * is destroyed or cleared.  Subsequent calls to certain methods
     * on this {@code Object} will result in an
     * {@code IllegalStateException} being thrown.
     *
     * <p>
     * The default implementation throws {@code DestroyFailedException}.
     *
     * <p>
     *  销毁这个{@code Object}。
     * 
     *  <p>与此{@code Object}相关联的敏感信息被销毁或清除。对此{@code Object}的某些方法的后续调用将导致{@code IllegalStateException}被抛出。
     * 
     * <p>
     *  默认实现throws {@code DestroyFailedException}。
     * 
     * 
     * @exception DestroyFailedException if the destroy operation fails. <p>
     *
     * @exception SecurityException if the caller does not have permission
     *          to destroy this {@code Object}.
     */
    public default void destroy() throws DestroyFailedException {
        throw new DestroyFailedException();
    }

    /**
     * Determine if this {@code Object} has been destroyed.
     *
     * <p>
     * The default implementation returns false.
     *
     * <p>
     * 
     * @return true if this {@code Object} has been destroyed,
     *          false otherwise.
     */
    public default boolean isDestroyed() {
        return false;
    }
}
