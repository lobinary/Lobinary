/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2002, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Container;

/**
 * <p>
 * This interface is implemented by BeanContexts' that have an AWT Container
 * associated with them.
 * </p>
 *
 * <p>
 * <p>
 *  该接口由BeanContexts实现,它具有与它们相关联的AWT容器。
 * </p>
 * 
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 *
 * @see java.beans.beancontext.BeanContext
 * @see java.beans.beancontext.BeanContextSupport
 */

public interface BeanContextContainerProxy {

    /**
     * Gets the <code>java.awt.Container</code> associated
     * with this <code>BeanContext</code>.
     * <p>
     * 
     * @return the <code>java.awt.Container</code> associated
     * with this <code>BeanContext</code>.
     */
    Container getContainer();
}
