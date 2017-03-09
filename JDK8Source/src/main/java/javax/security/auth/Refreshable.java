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
 * Objects such as credentials may optionally implement this
 * interface to provide the capability to refresh itself.
 * For example, a credential with a particular time-restricted lifespan
 * may implement this interface to allow callers to refresh the time period
 * for which it is valid.
 *
 * <p>
 *  诸如凭证的对象可以可选地实现该接口以提供刷新自身的能力。例如,具有特定时间限制的寿命的证书可以实现此接口以允许呼叫者刷新其有效的时间段。
 * 
 * 
 * @see javax.security.auth.Subject
 */
public interface Refreshable {

    /**
     * Determine if this {@code Object} is current.
     *
     * <p>
     *
     * <p>
     *  确定此{@code Object}是否为最新的。
     * 
     * <p>
     * 
     * 
     * @return true if this {@code Object} is currently current,
     *          false otherwise.
     */
    boolean isCurrent();

    /**
     * Update or extend the validity period for this
     * {@code Object}.
     *
     * <p>
     *
     * <p>
     *  更新或延长此{@code Object}的有效期。
     * 
     * 
     * @exception SecurityException if the caller does not have permission
     *          to update or extend the validity period for this
     *          {@code Object}. <p>
     *
     * @exception RefreshFailedException if the refresh attempt failed.
     */
    void refresh() throws RefreshFailedException;
}
