/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

import java.beans.beancontext.BeanContextMembershipEvent;

import java.util.EventListener;

/**
 * <p>
 * Compliant BeanContexts fire events on this interface when the state of
 * the membership of the BeanContext changes.
 * </p>
 *
 * <p>
 * <p>
 *  符合BeanContexts的火事件在这个接口上的BeanContext的成员状态改变。
 * </p>
 * 
 * 
 * @author      Laurence P. G. Cable
 * @since       1.2
 * @see         java.beans.beancontext.BeanContext
 */

public interface BeanContextMembershipListener extends EventListener {

    /**
     * Called when a child or list of children is added to a
     * <code>BeanContext</code> that this listener is registered with.
     * <p>
     *  当子元素或子元素列表添加到此监听器注册的<code> BeanContext </code>时调用。
     * 
     * 
     * @param bcme The <code>BeanContextMembershipEvent</code>
     * describing the change that occurred.
     */
    void childrenAdded(BeanContextMembershipEvent bcme);

    /**
     * Called when a child or list of children is removed
     * from a <code>BeanContext</code> that this listener
     * is registered with.
     * <p>
     *  当从此监听器注册的<code> BeanContext </code>中删除子节点或子节点列表时调用。
     * 
     * @param bcme The <code>BeanContextMembershipEvent</code>
     * describing the change that occurred.
     */
    void childrenRemoved(BeanContextMembershipEvent bcme);
}
